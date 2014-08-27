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

import com.liferay.portal.kernel.comment.CommentTreeNode;
import com.liferay.portal.kernel.comment.CommentsContainer;
import com.liferay.portal.kernel.comment.DiscussionRootComment;
import com.liferay.portal.kernel.comment.DiscussionTree;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBTreeWalker;
import com.liferay.portlet.messageboards.util.comparator.MessageCreateDateComparator;

import java.util.List;

/**
 * @author André de Oliveira
 */
public class MBTreeWalkerDiscussionRootCommentImpl
	implements DiscussionRootComment, DiscussionTree {

	@Override
	public CommentsContainer createCommentsContainer() {
		List<MBMessage> mbMessages = ListUtil.copy(
			ListUtil.sort(_mbMessages, new MessageCreateDateComparator(true)));

		mbMessages.remove(0);

		return new MBCommentsContainerImpl(mbMessages);
	}

	@Override
	public int getCommentsCount() {
		return _mbMessages.size() - 1;
	}

	@Override
	public long getRootCommentId() {
		return _rootMBMessage.getMessageId();
	}

	@Override
	public CommentTreeNode getRootCommentTreeNode() {
		return new MBCommentTreeNodeImpl(_rootMBMessage, _mbTreeWalker);
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