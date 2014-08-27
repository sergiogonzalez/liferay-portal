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
import com.liferay.portal.kernel.comment.CommentTreeNode;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBTreeWalker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class MBCommentTreeNodeImpl implements CommentTreeNode {

	public MBCommentTreeNodeImpl(
		MBMessage mbMessage, MBTreeWalker mbTreeWalker) {

		_mbMessage = mbMessage;
		_mbTreeWalker = mbTreeWalker;
	}

	@Override
	public List<CommentTreeNode> getChildren() {
		List<MBMessage> mbMessages = _mbTreeWalker.getChildren(_mbMessage);

		List<CommentTreeNode> children = new ArrayList<CommentTreeNode>(
			mbMessages.size());

		for (MBMessage mbMessage : mbMessages) {
			children.add(new MBCommentTreeNodeImpl(mbMessage, _mbTreeWalker));
		}

		return children;
	}

	@Override
	public Comment getComment() {
		return new MBCommentImpl(_mbMessage);
	}

	private final MBMessage _mbMessage;
	private final MBTreeWalker _mbTreeWalker;

}