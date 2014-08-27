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

import com.liferay.portal.kernel.comment.DiscussionRootComment;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBTreeWalker;

import java.util.List;

/**
 * @author André de Oliveira
 */
public class MBTreeWalkerDiscussionRootCommentImpl
	implements DiscussionRootComment {

	@Override
	public int getCommentsCount() {
		return _mbMessages.size() - 1;
	}

	public List<MBMessage> getMessages() {
		return _mbMessages;
	}

	@Override
	public long getRootCommentId() {
		return _rootMBMessage.getMessageId();
	}

	public MBMessage getRootMBMessage() {

		// TODO This getter is going away in a few commits

		return _rootMBMessage;
	}

	public MBTreeWalker getMBTreeWalker() {

		// TODO This getter is going away in a few commits

		return _mbTreeWalker;
	}

	MBTreeWalkerDiscussionRootCommentImpl(MBTreeWalker mbTreeWalker) {
		_mbMessages = mbTreeWalker.getMessages();
		_mbTreeWalker = mbTreeWalker;
		_rootMBMessage = mbTreeWalker.getRoot();
	}

	private final List<MBMessage> _mbMessages;
	private final MBTreeWalker _mbTreeWalker;
	private final MBMessage _rootMBMessage;

}