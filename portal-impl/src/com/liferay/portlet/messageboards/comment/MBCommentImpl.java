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
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;

import java.util.Date;

/**
 * @author André de Oliveira
 */
public class MBCommentImpl implements Comment {

	public MBCommentImpl(MBMessage mbMessage) {
		_mbMessage = mbMessage;
	}

	@Override
	public String getBody() {
		return _mbMessage.getBody();
	}

	@Override
	public long getCommentId() {
		return _mbMessage.getMessageId();
	}

	@Override
	public Date getCreateDate() {
		return _mbMessage.getCreateDate();
	}

	@Override
	public long getMessageId() {
		return _mbMessage.getMessageId();
	}

	@Override
	public Date getModifiedDate() {
		return _mbMessage.getModifiedDate();
	}

	@Override
	public long getRatingsClassPK() {
		return _mbMessage.getMessageId();
	}

	@Override
	public int getStatus() {
		return _mbMessage.getStatus();
	}

	@Override
	public long getUserId() {
		return _mbMessage.getUserId();
	}

	@Override
	public String getUserName() {
		return _mbMessage.getUserName();
	}

	@Override
	public String getUserNameNonAnonymous() {
		return PortalUtil.getUserName(_mbMessage);
	}

	@Override
	public Class<?> getWorkflowStatusModelClass() {
		return MBDiscussion.class;
	}

	@Override
	public Class<?> getWorkflowStatusModelContextClass() {
		return MBMessage.class;
	}

	@Override
	public boolean isAnonymous() {
		return _mbMessage.isAnonymous();
	}

	@Override
	public boolean isApproved() {
		return _mbMessage.isApproved();
	}

	@Override
	public boolean isChildOf(long commentId) {
		return _mbMessage.getParentMessageId() == commentId;
	}

	@Override
	public boolean isFormatBBCode() {
		return _mbMessage.isFormatBBCode();
	}

	@Override
	public boolean isPending() {
		return _mbMessage.isPending();
	}

	@Override
	public boolean isRoot() {
		return _mbMessage.isRoot();
	}

	protected MBMessage getMBMessage() {
		return _mbMessage;
	}

	private final MBMessage _mbMessage;

}