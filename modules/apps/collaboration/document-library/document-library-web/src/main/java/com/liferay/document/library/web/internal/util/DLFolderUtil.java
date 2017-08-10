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

package com.liferay.document.library.web.internal.util;

import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alejandro Tardín
 */
public class DLFolderUtil {

	public static List<Folder> getFolders(
			long repositoryId, long folderId, boolean mountFolderVisible,
			SearchContainer searchContainer,
			PermissionChecker permissionChecker, String[] actionIds)
		throws PortalException {

		List<Folder> allFolders = DLAppServiceUtil.getFolders(
			repositoryId, folderId, mountFolderVisible,
			searchContainer.getStart(), searchContainer.getEnd());

		List<Folder> filteredFolders = new ArrayList<>();

		for (Folder folder : allFolders) {
			if (_hasPermissions(folder, permissionChecker, actionIds)) {
				filteredFolders.add(folder);
			}
		}

		return filteredFolders;
	}

	private static boolean _hasPermissions(
			Folder folder, PermissionChecker permissionChecker,
			String[] actionIds)
		throws PortalException {

		for (String actionId : actionIds) {
			if (!DLFolderPermission.contains(
					permissionChecker, folder, actionId)) {

				return false;
			}
		}

		return true;
	}

}