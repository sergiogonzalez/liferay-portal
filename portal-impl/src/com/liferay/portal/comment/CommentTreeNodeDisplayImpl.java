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
import com.liferay.portal.kernel.comment.CommentTreeNodeDisplay;
import com.liferay.portlet.messageboards.comment.MBCommentImpl;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBTreeWalker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class CommentTreeNodeDisplayImpl implements CommentTreeNodeDisplay {

	public CommentTreeNodeDisplayImpl(
		int depth, boolean lastNode, MBMessage message,
		MBTreeWalker treeWalker) {

		_depth = depth;
		_message = message;
		_lastNode = lastNode;
		_treeWalker = treeWalker;
	}

	public CommentTreeNodeDisplayImpl(
		MBMessage message, MBTreeWalker treeWalker) {

		this(-1, false, message, treeWalker);
	}

	@Override
	public List<CommentTreeNodeDisplay> getChildren() {
		List<MBMessage> messages = _treeWalker.getMessages();
		int[] range = _treeWalker.getChildrenRange(_message);

		int size = range[1] - range[0];

		List<CommentTreeNodeDisplay> children =
			new ArrayList<CommentTreeNodeDisplay>(size);

		for (int i = range[0]; i < range[1]; i++) {
			MBMessage message = messages.get(i);

			boolean lastChildNode = false;

			if ((i + 1) == range[1]) {
				lastChildNode = true;
			}

			children.add(createChild(lastChildNode, message));
		}

		return children;
	}

	@Override
	public Comment getComment() {
		return new MBCommentImpl(_message);
	}

	@Override
	public int getDepth() {
		return _depth;
	}

	@Override
	public boolean isLastNode() {
		return _lastNode;
	}

	protected CommentTreeNodeDisplayImpl createChild(
		boolean lastNode, MBMessage message) {

		return new CommentTreeNodeDisplayImpl(
			_depth + 1, lastNode, message, _treeWalker);
	}

	private int _depth;
	private boolean _lastNode;
	private MBMessage _message;
	private MBTreeWalker _treeWalker;

}