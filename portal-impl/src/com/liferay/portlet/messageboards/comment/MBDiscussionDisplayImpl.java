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

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.DiscussionDisplay;
import com.liferay.portal.kernel.comment.DiscussionRootComment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBTreeWalker;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.trash.util.TrashUtil;

/**
 * @author André de Oliveira
 */
public class MBDiscussionDisplayImpl implements DiscussionDisplay {

	public MBDiscussionDisplayImpl(
			String className, long classPK, MBMessageDisplay mbMessageDisplay,
			MBMessageLocalService mbMessageLocalService)
		throws PortalException {

		_className = className;
		_classPK = classPK;
		_mbMessageDisplay = mbMessageDisplay;
		_mbMessageLocalService = mbMessageLocalService;
	}

	@Override
	public DiscussionRootComment createDiscussionRootComment()
		throws PortalException {

		MBTreeWalker mbTreeWalker = _mbMessageDisplay.getTreeWalker();

		if (mbTreeWalker != null) {
			return new MBTreeWalkerDiscussionRootCommentImpl(mbTreeWalker);
		}

		return new MBThreadDiscussionRootCommentImpl(
			_mbMessageDisplay.getThread());
	}

	@Override
	public Comment getParent(Comment comment) throws PortalException {
		MBMessage mbMessage = getMBMessage(comment);

		MBMessage parent = _mbMessageLocalService.getMessage(
			mbMessage.getParentMessageId());

		return new MBCommentImpl(parent);
	}

	@Override
	public String getRatingsClassName() {
		return MBDiscussion.class.getName();
	}

	@Override
	public long getThreadId() {
		MBThread mbThread = _mbMessageDisplay.getThread();

		return mbThread.getThreadId();
	}

	@Override
	public String getWorkflowDefinitionLinkClassName() {
		return MBDiscussion.class.getName();
	}

	@Override
	public boolean isInTrash() throws PortalException {
		return TrashUtil.isInTrash(_className, _classPK);
	}

	@Override
	public boolean isInTrash(Comment comment) throws PortalException {
		MBMessage mbMessage = getMBMessage(comment);

		return TrashUtil.isInTrash(
			mbMessage.getClassName(), mbMessage.getClassPK());
	}

	protected MBMessage getMBMessage(Comment comment) {
		return ((MBCommentImpl)comment).getMBMessage();
	}

	private final String _className;
	private final long _classPK;
	private final MBMessageDisplay _mbMessageDisplay;
	private final MBMessageLocalService _mbMessageLocalService;

}