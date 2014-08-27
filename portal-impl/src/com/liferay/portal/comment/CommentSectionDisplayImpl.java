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
import com.liferay.portal.kernel.comment.CommentTreeNode;
import com.liferay.portal.kernel.comment.CommentTreeNodeDisplay;
import com.liferay.portal.kernel.comment.CommentsContainer;
import com.liferay.portal.kernel.comment.DiscussionDisplay;
import com.liferay.portal.kernel.comment.DiscussionPage;
import com.liferay.portal.kernel.comment.DiscussionRootComment;
import com.liferay.portal.kernel.comment.DiscussionTree;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.User;
import com.liferay.portal.parsers.bbcode.BBCodeUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;
import com.liferay.portlet.ratings.service.persistence.RatingsEntryUtil;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsUtil;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author André de Oliveira
 */
public class CommentSectionDisplayImpl implements CommentSectionDisplay {

	public CommentSectionDisplayImpl(
			long scopeGroupId, User user, boolean hideControls,
			boolean ratingsEnabled, DiscussionDisplay discussionDisplay,
			ThemeDisplay themeDisplay,
			CommentPermissionChecker commentPermissionChecker,
			PermissionChecker permissionChecker)
		throws PortalException {

		_scopeGroupId = scopeGroupId;
		_user = user;
		_themeDisplay = themeDisplay;
		_hideControls = hideControls;
		_ratingsEnabled = ratingsEnabled;
		_discussionDisplay = discussionDisplay;
		_discussionRootComment =
			discussionDisplay.createDiscussionRootComment();
		_commentPermissionChecker = commentPermissionChecker;
		_permissionChecker = permissionChecker;
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
		DiscussionTree discussionTree = (DiscussionTree)_discussionRootComment;

		CommentTreeNode commentTreeNode =
			discussionTree.getRootCommentTreeNode();

		CommentTreeNodeDisplay commentTreeNodeDisplay =
			new CommentTreeNodeDisplayImpl(commentTreeNode);

		return commentTreeNodeDisplay.getChildren();
	}

	@Override
	public Comment getParentComment(Comment comment) throws PortalException {
		return _discussionDisplay.getParent(comment);
	}

	@Override
	public String getRatingsClassName() {
		return _discussionDisplay.getRatingsClassName();
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
		return _discussionRootComment.getRootCommentId();
	}

	@Override
	public SearchContainer<?> getSearchContainer() {
		return _searchContainer;
	}

	@Override
	public long getThreadId() {
		return _discussionDisplay.getThreadId();
	}

	@Override
	public boolean hasAddPermission() {
		return _commentPermissionChecker.hasAddPermission();
	}

	@Override
	public boolean hasComments() {
		return _discussionRootComment.getCommentsCount() > 0;
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
			_discussionDisplay.getWorkflowDefinitionLinkClassName());
	}

	@Override
	public List<Comment> initComments(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		CommentsContainer commentsContainer;

		if (_discussionRootComment instanceof DiscussionTree) {
			DiscussionTree discussionTree =
				(DiscussionTree)_discussionRootComment;

			commentsContainer = discussionTree.createCommentsContainer();
		}
		else {
			PortletURL currentURLObj = PortletURLUtil.getCurrent(
				renderRequest, renderResponse);

			SearchContainer searchContainer = new SearchContainer<>(
				renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM,
				SearchContainer.DEFAULT_DELTA, currentURLObj, null, null);

			searchContainer.setTotal(_discussionRootComment.getCommentsCount());

			DiscussionPage discussionPage =
				(DiscussionPage)_discussionRootComment;

			commentsContainer = discussionPage.createCommentsContainer(
				searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(
				commentsContainer.getSearchContainerResults());

			_searchContainer = searchContainer;
		}

		List<Long> classPKs = commentsContainer.getClassPKs();

		String ratingsClassName = _discussionDisplay.getRatingsClassName();

		_ratingsEntries = RatingsEntryLocalServiceUtil.getEntries(
			_themeDisplay.getUserId(), ratingsClassName, classPKs);
		_ratingsStatsList = RatingsStatsLocalServiceUtil.getStats(
			ratingsClassName, classPKs);

		return commentsContainer.getComments();
	}

	@Override
	public boolean isDiscussionActionsVisible(Comment comment)
		throws PortalException {

		return !_hideControls && !_discussionDisplay.isInTrash(comment);
	}

	@Override
	public boolean isDiscussionVisible() {
		return hasComments() || _commentPermissionChecker.hasViewPermission();
	}

	@Override
	public boolean isRatingsVisible(Comment comment) throws PortalException {
		return _ratingsEnabled && !_discussionDisplay.isInTrash(comment);
	}

	@Override
	public boolean isSearchPaginatorVisible() {
		return (_searchContainer != null) &&
			(_searchContainer.getTotal() > _searchContainer.getDelta());
	}

	@Override
	public boolean isSubscriptionButtonVisible() throws PortalException {
		return _themeDisplay.isSignedIn() && !_discussionDisplay.isInTrash();
	}

	@Override
	public boolean isThreadedRepliesVisible() {
		return _discussionRootComment instanceof DiscussionTree;
	}

	@Override
	public boolean isTopChild(Comment comment) throws PortalException {
		return comment.isChildOf(_discussionRootComment.getRootCommentId());
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

	private final CommentPermissionChecker _commentPermissionChecker;
	private final DiscussionDisplay _discussionDisplay;
	private final DiscussionRootComment _discussionRootComment;
	private final boolean _hideControls;
	private final PermissionChecker _permissionChecker;
	private final boolean _ratingsEnabled;
	private List<RatingsEntry> _ratingsEntries;
	private List<RatingsStats> _ratingsStatsList;
	private final long _scopeGroupId;
	private SearchContainer<?> _searchContainer;
	private final ThemeDisplay _themeDisplay;
	private final User _user;

}