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
import com.liferay.portal.kernel.comment.CommentTreeNode;
import com.liferay.portal.kernel.comment.CommentTreeNodeDisplay;

import java.util.ArrayList;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class CommentTreeNodeDisplayImpl implements CommentTreeNodeDisplay {

	public CommentTreeNodeDisplayImpl(CommentTreeNode commentTreeNode) {

		this(-1, false, commentTreeNode);
	}

	public CommentTreeNodeDisplayImpl(
		int depth, boolean lastNode, CommentTreeNode commentTreeNode) {

		_commentTreeNode = commentTreeNode;
		_depth = depth;
		_lastNode = lastNode;
	}

	@Override
	public List<CommentTreeNodeDisplay> getChildren() {
		List<CommentTreeNode> commentTreeNodes = _commentTreeNode.getChildren();

		int size = commentTreeNodes.size();

		List<CommentTreeNodeDisplay> children =
			new ArrayList<CommentTreeNodeDisplay>(size);

		if (size > 0) {
			for (int i = 0; i < size - 1; i++) {
				children.add(createChild(false, commentTreeNodes.get(i)));
			}

			children.add(createChild(true, commentTreeNodes.get(size - 1)));
		}

		return children;
	}

	@Override
	public Comment getComment() {
		return _commentTreeNode.getComment();
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
		boolean lastNode, CommentTreeNode commentTreeNode) {

		return new CommentTreeNodeDisplayImpl(
			_depth + 1, lastNode, commentTreeNode);
	}

	private CommentTreeNode _commentTreeNode;
	private int _depth;
	private boolean _lastNode;

}