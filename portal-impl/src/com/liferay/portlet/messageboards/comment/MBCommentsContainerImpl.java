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
import com.liferay.portal.kernel.comment.CommentsContainer;
import com.liferay.portlet.messageboards.model.MBMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class MBCommentsContainerImpl implements CommentsContainer {

	@Override
	public List<Long> getClassPKs() {
		List<Long> classPKs = new ArrayList<Long>();

		for (MBMessage mbMessage : _mbMessages) {
			classPKs.add(mbMessage.getMessageId());
		}

		return classPKs;
	}

	@Override
	public List<Comment> getComments() {
		ArrayList<Comment> comments = new ArrayList<Comment>(
			_mbMessages.size());

		for (MBMessage mbMessage : _mbMessages) {
			comments.add(new MBCommentImpl(mbMessage));
		}

		return comments;
	}

	@Override
	public List<?> getSearchContainerResults() {
		return _mbMessages;
	}

	MBCommentsContainerImpl(List<MBMessage> mbMessages) {
		_mbMessages = mbMessages;
	}

	private List<MBMessage> _mbMessages;

}