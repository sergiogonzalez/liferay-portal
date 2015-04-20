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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class MBCommentImpl implements Comment {

	public MBCommentImpl(MBMessage message) {
		_message = message;
	}

	@Override
	public String getBody() {
		return _message.getBody();
	}

	@Override
	public long getCommentId() {
		return _message.getMessageId();
	}

	@Override
	public Date getCreateDate() {
		return _message.getCreateDate();
	}

	@Override
	public Class<?> getDiscussionClass() {
		return MBDiscussion.class;
	}

	@Override
	public String getDiscussionClassName() {
		return getDiscussionClass().getName();
	}

	@Override
	public long getDiscussionClassPK() {
		return getCommentId();
	}

	@Override
	public int getDiscussionStatus() {
		return _message.getStatus();
	}

	@Override
	public Object getModel() {
		return _message;
	}

	@Override
	public Class<?> getModelClass() {
		return MBMessage.class;
	}

	@Override
	public long getModelClassPK() {
		return getCommentId();
	}

	@Override
	public Date getModifiedDate() {
		return _message.getModifiedDate();
	}

	@Override
	public Comment getParentComment() throws PortalException {
		long parentMessageId = _message.getParentMessageId();

		MBMessage parentMessage = MBMessageLocalServiceUtil.getMessage(
			parentMessageId);

		return new MBCommentImpl(parentMessage);
	}

	@Override
	public long getParentCommentId() {
		return _message.getParentMessageId();
	}

	@Override
	public String getTranslatedBody() {
		return _message.getBody(true);
	}

	@Override
	public User getUser() throws PortalException {
		return UserLocalServiceUtil.fetchUser(getUserId());
	}

	@Override
	public long getUserId() {
		return _message.getUserId();
	}

	@Override
	public String getUserName() {
		return _message.getUserName();
	}

	private final MBMessage _message;

}