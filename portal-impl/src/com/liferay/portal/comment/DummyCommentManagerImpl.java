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

import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.CommentSectionDisplay;
import com.liferay.portal.kernel.comment.DiscussionThreadView;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;

/**
 * @author André de Oliveira
 */
public class DummyCommentManagerImpl implements CommentManager {

	@Override
	public void addComment(
		long userId, long groupId, String className, long classPK, String body,
		ServiceContext serviceContext) {
	}

	@Override
	public long addComment(
		long userId, long groupId, String className, long classPK,
		String userName, String subject, String body,
		Function<String, ServiceContext> serviceContextFunction) {

		return 0;
	}

	@Override
	public void addDiscussion(
		long userId, long groupId, String className, long classPK,
		String userName) {
	}

	@Override
	public CommentSectionDisplay createCommentSectionDisplay(
			long companyId, long userId, long scopeGroupId, String className,
			long classPK, String permissionClassName, long permissionClassPK,
			PermissionChecker permissionChecker, boolean hideControls,
			boolean ratingsEnabled, DiscussionThreadView discussionThreadView,
			ThemeDisplay themeDisplay)
		throws PortalException {

		return new DummyCommentSectionDisplayImpl();
	}

	@Override
	public void deleteComment(long commentId) {
	}

	@Override
	public void deleteDiscussion(String className, long classPK) {
	}

	@Override
	public int getCommentsCount(String className, long classPK) {
		return 0;
	}

}