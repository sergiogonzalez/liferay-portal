/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.comment;

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentPermissionChecker;
import com.liferay.portal.kernel.comment.CommentSectionDisplay;
import com.liferay.portal.kernel.comment.CommentTreeNodeDisplay;
import com.liferay.portal.kernel.comment.DiscussionThreadView;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.parsers.bbcode.BBCodeUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.portlet.messageboards.comment.MBCommentImpl;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBTreeWalker;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.util.comparator.MessageCreateDateComparator;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;
import com.liferay.portlet.ratings.service.persistence.RatingsEntryUtil;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsUtil;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author André de Oliveira
 */
public class CommentSectionDisplayImpl implements CommentSectionDisplay {

	public CommentSectionDisplayImpl(
			long userId, long scopeGroupId, String className, long classPK,
			PermissionChecker permissionChecker, boolean hideControls,
			boolean ratingsEnabled,
			CommentPermissionChecker commentPermissionChecker,
			DiscussionThreadView discussionThreadView,
			ThemeDisplay themeDisplay)
		throws PortalException {

		String threadView = StringUtil.toLowerCase(discussionThreadView.name());

		MBMessageDisplay messageDisplay =
			MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
				userId, scopeGroupId, className, classPK,
				WorkflowConstants.STATUS_ANY, threadView);

		MBThread thread = messageDisplay.getThread();
		MBTreeWalker treeWalker = messageDisplay.getTreeWalker();
		MBMessage rootMessage = null;
		List<MBMessage> messages = null;
		int messagesCount = 0;
		SearchContainer searchContainer = null;

		if (treeWalker != null) {
			rootMessage = treeWalker.getRoot();
			messages = treeWalker.getMessages();
			messagesCount = messages.size();
		}
		else {
			rootMessage = MBMessageLocalServiceUtil.getMessage(
				thread.getRootMessageId());
			messagesCount = MBMessageLocalServiceUtil.getThreadMessagesCount(
				rootMessage.getThreadId(), WorkflowConstants.STATUS_ANY);
		}

		_className = className;
		_classPK = classPK;
		_commentPermissionChecker = commentPermissionChecker;
		_hideControls = hideControls;
		_messages = messages;
		_messagesCount = messagesCount;
		_permissionChecker = permissionChecker;
		_rootMessage = rootMessage;
		_searchContainer = searchContainer;
		_ratingsEnabled = ratingsEnabled;
		_scopeGroupId = scopeGroupId;
		_themeDisplay = themeDisplay;
		_thread = thread;
		_treeWalker = treeWalker;
		_user = themeDisplay.getUser();
	}

	@Override
	public String getBodyFormatted(Comment comment) {
		String msgBody = comment.getBody();

		if (comment.isFormatBBCode()) {
			msgBody = BBCodeUtil.getBBCodeHTML(
				msgBody, _themeDisplay.getPathThemeImages());
		}

		return msgBody;
	}

	@Override
	public List<CommentTreeNodeDisplay> getCommentTreeNodeDisplays() {
		CommentTreeNodeDisplay commentTreeNodeDisplay =
			new CommentTreeNodeDisplayImpl(_rootMessage, _treeWalker);

		return commentTreeNodeDisplay.getChildren();
	}

	@Override
	public Comment getParentComment(Comment comment) throws PortalException {
		MBMessage message = getMBMessage(comment);
		return new MBCommentImpl(
			MBMessageLocalServiceUtil.getMessage(message.getParentMessageId()));
	}

	@Override
	public String getRatingsClassName() {
		return MBDiscussion.class.getName();
	}

	@Override
	public RatingsEntry getRatingsEntry(Comment comment) {
		long classPK = comment.getRatingsClassPK();

		for (RatingsEntry ratingsEntry : _ratingsEntries) {
			if (ratingsEntry.getClassPK() == classPK) {
				return ratingsEntry;
			}
		}

		return RatingsEntryUtil.create(0);
	}

	@Override
	public RatingsStats getRatingsStats(Comment comment) {
		long classPK = comment.getRatingsClassPK();

		for (RatingsStats ratingsStats : _ratingsStatsList) {
			if (ratingsStats.getClassPK() == classPK) {
				return ratingsStats;
			}
		}

		return RatingsStatsUtil.create(0);
	}

	@Override
	public long getRootCommentMessageId() {
		return _rootMessage.getMessageId();
	}

	@Override
	public SearchContainer<?> getSearchContainer() {
		return _searchContainer;
	}

	@Override
	public long getThreadId() {
		return _thread.getThreadId();
	}

	@Override
	public boolean hasAddPermission() {
		return _commentPermissionChecker.hasAddPermission();
	}

	@Override
	public boolean hasComments() {
		return _messagesCount > 1;
	}

	@Override
	public boolean hasDeletePermission(Comment comment) throws PortalException {
		return _commentPermissionChecker.hasDeletePermission(comment);
	}

	@Override
	public boolean hasEditPermission(Comment comment) throws PortalException {
		return _commentPermissionChecker.hasEditPermission(comment);
	}

	@Override
	public boolean hasWorkflowDefinitionLink() {
		return WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
			_themeDisplay.getCompanyId(), _scopeGroupId,
			MBDiscussion.class.getName());
	}

	@Override
	public List<Comment> initComments(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		List<MBMessage> messages;

		if (_messages != null) {
			messages = ListUtil.copy(
				ListUtil.sort(
					_messages, new MessageCreateDateComparator(true)));

			messages.remove(0);
		}
		else {
			PortletURL currentURLObj = PortletURLUtil.getCurrent(
				renderRequest, renderResponse);

			_searchContainer = new SearchContainer(
				renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM,
				SearchContainer.DEFAULT_DELTA, currentURLObj, null, null);

			_searchContainer.setTotal(_messagesCount - 1);

			messages = MBMessageLocalServiceUtil.getThreadRepliesMessages(
				_rootMessage.getThreadId(), WorkflowConstants.STATUS_ANY,
				_searchContainer.getStart(), _searchContainer.getEnd());

			_searchContainer.setResults(messages);
		}

		List<Comment> comments = new ArrayList<Comment>(messages.size());

		for (MBMessage mbMessage : messages) {
			comments.add(new MBCommentImpl(mbMessage));
		}

		List<Long> classPKs = new ArrayList<Long>();

		for (MBMessage curMessage : messages) {
			classPKs.add(curMessage.getMessageId());
		}

		_ratingsEntries = RatingsEntryLocalServiceUtil.getEntries(
			_themeDisplay.getUserId(), MBDiscussion.class.getName(), classPKs);
		_ratingsStatsList = RatingsStatsLocalServiceUtil.getStats(
			MBDiscussion.class.getName(), classPKs);

		return comments;
	}

	@Override
	public boolean isDiscussionActionsVisible(Comment comment)
		throws PortalException {

		MBMessage message = getMBMessage(comment);
		return !_hideControls &&
			!TrashUtil.isInTrash(message.getClassName(), message.getClassPK());
	}

	@Override
	public boolean isDiscussionVisible() {
		return (_messagesCount > 1) ||
			_commentPermissionChecker.hasViewPermission();
	}

	@Override
	public boolean isRatingsVisible(Comment comment) throws PortalException {
		MBMessage message = getMBMessage(comment);
		return _ratingsEnabled &&
			!TrashUtil.isInTrash(message.getClassName(), message.getClassPK());
	}

	@Override
	public boolean isSearchPaginatorVisible() {
		return (_searchContainer != null) &&
			(_searchContainer.getTotal() > _searchContainer.getDelta());
	}

	@Override
	public boolean isSubscriptionButtonVisible() throws PortalException {
		return _themeDisplay.isSignedIn() &&
			!TrashUtil.isInTrash(_className, _classPK);
	}

	@Override
	public boolean isThreadedRepliesVisible() {
		return _treeWalker != null;
	}

	@Override
	public boolean isTopChild(Comment comment) throws PortalException {
		MBMessage message = getMBMessage(comment);
		return message.getParentMessageId() == _rootMessage.getMessageId();
	}

	@Override
	public boolean isVisible(Comment comment) {
		return !((!comment.isApproved() &&
			((comment.getUserId() != _user.getUserId()) ||
				_user.isDefaultUser()) &&
					!_permissionChecker.isGroupAdmin(_scopeGroupId)) ||
						!_commentPermissionChecker.hasViewPermission());
	}

	@Override
	public boolean isWorkflowStatusVisible(Comment comment) {
		return !comment.isApproved();
	}

	protected MBMessage getMBMessage(Comment comment) {
		return ((MBCommentImpl)comment).getMBMessage();
	}

	private final String _className;
	private final long _classPK;
	private final CommentPermissionChecker _commentPermissionChecker;
	private final boolean _hideControls;
	private final List<MBMessage> _messages;
	private final int _messagesCount;
	private final PermissionChecker _permissionChecker;
	private final boolean _ratingsEnabled;
	private List<RatingsEntry> _ratingsEntries;
	private List<RatingsStats> _ratingsStatsList;
	private final MBMessage _rootMessage;
	private final long _scopeGroupId;
	private SearchContainer _searchContainer;
	private final ThemeDisplay _themeDisplay;
	private final MBThread _thread;
	private final MBTreeWalker _treeWalker;
	private final User _user;

}