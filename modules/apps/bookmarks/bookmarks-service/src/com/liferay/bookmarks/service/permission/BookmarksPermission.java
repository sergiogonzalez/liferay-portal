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

package com.liferay.bookmarks.service.permission;

import com.liferay.bookmarks.constants.BookmarksConstants;
import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourcePermissionChecker;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 */
@Component(
	property = {
		"resource.name=com.liferay.bookmarks"
	}
)
public class BookmarksPermission implements ResourcePermissionChecker {

	public static final String RESOURCE_NAME = "com.liferay.bookmarks";

	public static void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, classPK, RESOURCE_NAME, classPK,
			BookmarksPortletKeys.BOOKMARKS, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		return permissionChecker.hasPermission(
			classPK, RESOURCE_NAME, classPK, actionId);
	}

	@Override
	public Boolean checkResource(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		return contains(permissionChecker, classPK, actionId);
	}

}