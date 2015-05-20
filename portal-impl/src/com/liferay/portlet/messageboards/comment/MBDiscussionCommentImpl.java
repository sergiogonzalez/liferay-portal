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

package com.liferay.portlet.messageboards.comment;

import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.comment.DiscussionCommentIterator;
import com.liferay.portal.kernel.comment.WorkflowableComment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBTreeWalker;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class MBDiscussionCommentImpl
	extends MBCommentImpl implements WorkflowableComment {

	public MBDiscussionCommentImpl(
		MBMessage message, MBTreeWalker treeWalker,
		List<RatingsEntry> ratingsEntries, List<RatingsStats> ratingsStats,
		String pathThemeImages) {

		super(message);

		_treeWalker = treeWalker;
		_ratingsEntries = ratingsEntries;
		_ratingsStats = ratingsStats;
		_pathThemeImages = pathThemeImages;
	}

	@Override
	public long getCompanyId() {
		MBMessage message = getMessage();

		return message.getCompanyId();
	}

	@Override
	public long getGroupId() {
		MBMessage message = getMessage();

		return message.getGroupId();
	}

	@Override
	public DiscussionComment getParentComment() throws PortalException {
		MBMessage message = getMessage();

		long parentMessageId = message.getParentMessageId();

		if (parentMessageId == 0) {
			return null;
		}

		MBMessage parentMessage = MBMessageLocalServiceUtil.getMessage(
			parentMessageId);

		return new MBDiscussionCommentImpl(
			parentMessage, _treeWalker, _ratingsEntries, _ratingsStats,
			_pathThemeImages);
	}

	@Override
	public long getParentCommentId() {
		MBMessage message = getMessage();

		return message.getParentMessageId();
	}

	@Override
	public long getPrimaryKey() {
		MBMessage message = getMessage();

		return message.getPrimaryKey();
	}

	@Override
	public RatingsEntry getRatingsEntry() {
		long classPK = getCommentId();

		for (RatingsEntry ratingsEntry : _ratingsEntries) {
			if (ratingsEntry.getClassPK() == classPK) {
				return ratingsEntry;
			}
		}

		return null;
	}

	@Override
	public RatingsStats getRatingsStats() {
		long classPK = getCommentId();

		for (RatingsStats ratingsStats : _ratingsStats) {
			if (ratingsStats.getClassPK() == classPK) {
				return ratingsStats;
			}
		}

		return RatingsStatsUtil.create(0);
	}

	@Override
	public int getStatus() {
		MBMessage message = getMessage();

		return message.getStatus();
	}

	@Override
	public List<DiscussionComment> getThreadComments() {
		List<DiscussionComment> discussionComments = new ArrayList<>();

		DiscussionCommentIterator discussionCommentIterator =
			getThreadDiscussionCommentIterator();

		while (discussionCommentIterator.hasNext()) {
			discussionComments.add(discussionCommentIterator.next());
		}

		return discussionComments;
	}

	@Override
	public int getThreadCommentsCount() {
		List<MBMessage> messages = _treeWalker.getMessages();

		return messages.size();
	}

	@Override
	public DiscussionCommentIterator getThreadDiscussionCommentIterator() {
		List<MBMessage> messages = _treeWalker.getMessages();

		int[] range = _treeWalker.getChildrenRange(getMessage());

		return new MBDiscussionCommentIterator(
			messages, range[0], range[1], _treeWalker, _pathThemeImages);
	}

	@Override
	public DiscussionCommentIterator getThreadDiscussionCommentIterator(
		int from) {

		List<MBMessage> messages = _treeWalker.getMessages();

		int[] range = _treeWalker.getChildrenRange(getMessage());

		return new MBDiscussionCommentIterator(
			messages, from, range[1], _treeWalker, _pathThemeImages);
	}

	@Override
	public String getTranslatedBody() {
		MBMessage message = getMessage();

		if (message.isFormatBBCode()) {
			return MBUtil.getBBCodeHTML(getBody(), _pathThemeImages);
		}

		return getBody();
	}

	@Override
	public User getUser() {
		return UserLocalServiceUtil.fetchUser(getUserId());
	}

	@Override
	public boolean isRoot() {
		MBMessage message = getMessage();

		return message.isRoot();
	}

	private final String _pathThemeImages;
	private final List<RatingsEntry> _ratingsEntries;
	private final List<RatingsStats> _ratingsStats;
	private final MBTreeWalker _treeWalker;

	private class MBDiscussionCommentIterator
		implements DiscussionCommentIterator {

		public MBDiscussionCommentIterator(
			List<MBMessage> messages, int from, int to, MBTreeWalker treeWalker,
			String pathThemeImages) {

			_messages = messages;
			_from = from;
			_to = to;
			_treeWalker = treeWalker;
			_pathThemeImages = pathThemeImages;
		}

		@Override
		public int getIndexPage() {
			return _from;
		}

		@Override
		public boolean hasNext() {
			if (_from < _to) {
				return true;
			}

			return false;
		}

		@Override
		public DiscussionComment next() {
			DiscussionComment discussionComment = new MBDiscussionCommentImpl(
				_messages.get(_from), _treeWalker, _ratingsEntries,
				_ratingsStats, _pathThemeImages);

			_from++;

			return discussionComment;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		private int _from;
		private final List<MBMessage> _messages;
		private final String _pathThemeImages;
		private final int _to;
		private final MBTreeWalker _treeWalker;

	}

}