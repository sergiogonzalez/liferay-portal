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

import com.liferay.portal.kernel.comment.CommentsContainer;
import com.liferay.portal.kernel.comment.DiscussionPage;
import com.liferay.portal.kernel.comment.DiscussionRootComment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.List;

/**
 * @author André de Oliveira
 */
public class MBThreadDiscussionRootCommentImpl
	implements DiscussionRootComment, DiscussionPage {

	@Override
	public CommentsContainer createCommentsContainer(int start, int end) {
		List<MBMessage> mbMessages =
			MBMessageLocalServiceUtil.getThreadRepliesMessages(
				_rootMBMessage.getThreadId(), WorkflowConstants.STATUS_ANY,
				start, end);

		return new MBCommentsContainerImpl(mbMessages);
	}

	@Override
	public int getCommentsCount() {
		return _threadMessagesCount - 1;
	}

	@Override
	public long getRootCommentId() {
		return _rootMBMessage.getMessageId();
	}

	MBThreadDiscussionRootCommentImpl(MBThread mbThread)
		throws PortalException {

		_rootMBMessage = MBMessageLocalServiceUtil.getMessage(
			mbThread.getRootMessageId());

		_threadMessagesCount = MBMessageLocalServiceUtil.getThreadMessagesCount(
			_rootMBMessage.getThreadId(), WorkflowConstants.STATUS_ANY);
	}

	private final MBMessage _rootMBMessage;
	private final int _threadMessagesCount;

}