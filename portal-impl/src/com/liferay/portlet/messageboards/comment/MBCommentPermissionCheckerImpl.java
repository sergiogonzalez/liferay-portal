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
import com.liferay.portal.kernel.comment.CommentPermissionChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;

/**
 * @author André de Oliveira
 */
public class MBCommentPermissionCheckerImpl
	implements CommentPermissionChecker {

	@Override
	public boolean hasAddPermission() {
		return MBDiscussionPermission.contains(
			_permissionChecker, _companyId, _scopeGroupId, _permissionClassName,
			_permissionClassPK, _userId, ActionKeys.ADD_DISCUSSION);
	}

	@Override
	public boolean hasDeletePermission(Comment comment) throws PortalException {
		return MBDiscussionPermission.contains(
			_permissionChecker, _companyId, _scopeGroupId, _permissionClassName,
			_permissionClassPK, comment.getMessageId(), comment.getUserId(),
			ActionKeys.DELETE_DISCUSSION);
	}

	@Override
	public boolean hasEditPermission(Comment comment) throws PortalException {
		return MBDiscussionPermission.contains(
			_permissionChecker, _companyId, _scopeGroupId, _permissionClassName,
			_permissionClassPK, comment.getMessageId(), comment.getUserId(),
			ActionKeys.UPDATE_DISCUSSION);
	}

	@Override
	public boolean hasViewPermission() {
		return MBDiscussionPermission.contains(
			_permissionChecker, _companyId, _scopeGroupId, _permissionClassName,
			_permissionClassPK, _userId, ActionKeys.VIEW);
	}

	MBCommentPermissionCheckerImpl(
		long companyId, long userId, long scopeGroupId,
		String permissionClassName, long permissionClassPK,
		PermissionChecker permissionChecker) {

		_companyId = companyId;
		_userId = userId;
		_scopeGroupId = scopeGroupId;
		_permissionClassName = permissionClassName;
		_permissionClassPK = permissionClassPK;
		_permissionChecker = permissionChecker;
	}

	private final long _companyId;
	private final PermissionChecker _permissionChecker;
	private final String _permissionClassName;
	private final long _permissionClassPK;
	private final long _scopeGroupId;
	private final long _userId;

}