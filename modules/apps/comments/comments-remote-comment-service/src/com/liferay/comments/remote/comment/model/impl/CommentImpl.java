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

package com.liferay.comments.remote.comment.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.comment.DiscussionComment;

/**
 * The extended model implementation for the Comment service. Represents a row in the &quot;Comment&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.comments.remote.comment.model.Comment} interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class CommentImpl extends CommentBaseImpl {

	public CommentImpl() {
	}

	public CommentImpl(DiscussionComment discussionComment) {
		setBody(discussionComment.getBody());
		setCommentId(discussionComment.getCommentId());
		setCreateDate(discussionComment.getCreateDate());
		setModifiedDate(discussionComment.getModifiedDate());
		setUserId(discussionComment.getUserId());
		setUserName(discussionComment.getUserName());
	}

}