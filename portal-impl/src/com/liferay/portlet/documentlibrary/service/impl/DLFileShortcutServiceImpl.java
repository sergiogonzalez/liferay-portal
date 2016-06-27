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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.document.library.kernel.exception.FileShortcutPermissionException;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.documentlibrary.service.base.DLFileShortcutServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFileShortcutPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileShortcutServiceImpl extends DLFileShortcutServiceBaseImpl {

	@Override
	public DLFileShortcut addFileShortcut(
			long groupId, long repositoryId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_SHORTCUT);

		try {
			DLFileEntryPermission.check(
				getPermissionChecker(), toFileEntryId, ActionKeys.VIEW);
		}
		catch (PrincipalException pe) {
			throw new FileShortcutPermissionException(pe);
		}

		return dlFileShortcutLocalService.addFileShortcut(
			getUserId(), groupId, repositoryId, folderId, toFileEntryId,
			serviceContext);
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId) throws PortalException {
		DLFileShortcutPermission.check(
			getPermissionChecker(), fileShortcutId, ActionKeys.DELETE);

		dlFileShortcutLocalService.deleteFileShortcut(fileShortcutId);
	}

	@Override
	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException {

		DLFileShortcutPermission.check(
			getPermissionChecker(), fileShortcutId, ActionKeys.VIEW);

		return dlFileShortcutLocalService.getFileShortcut(fileShortcutId);
	}

	@Override
	public List<DLFileShortcut> getFileShortcuts(
			long groupId, long folderId, boolean active, int status, int start,
			int end, OrderByComparator<DLFileShortcut> obc)
		throws PortalException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		QueryDefinition<?> queryDefinition = new QueryDefinition(
			status, start, end, obc);

		return dlFileShortcutFinder.filterFindByF_A(
			folderId, active, queryDefinition);
	}

	@Override
	public int getFileShortcutsCount(
			long groupId, long folderId, boolean active, int status)
		throws PortalException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.VIEW);

		QueryDefinition<?> queryDefinition = new QueryDefinition(status);

		return dlFileShortcutFinder.filterCountByF_A(
			folderId, active, queryDefinition);
	}

	@Override
	public DLFileShortcut updateFileShortcut(
			long fileShortcutId, long repositoryId, long folderId,
			long toFileEntryId, ServiceContext serviceContext)
		throws PortalException {

		DLFileShortcutPermission.check(
			getPermissionChecker(), fileShortcutId, ActionKeys.UPDATE);

		try {
			DLFileEntryPermission.check(
				getPermissionChecker(), toFileEntryId, ActionKeys.VIEW);
		}
		catch (PrincipalException pe) {
			throw new FileShortcutPermissionException(pe);
		}

		return dlFileShortcutLocalService.updateFileShortcut(
			getUserId(), fileShortcutId, repositoryId, folderId, toFileEntryId,
			serviceContext);
	}

	@Override
	public void updateFileShortcuts(
			long oldToFileEntryId, long newToFileEntryId)
		throws PortalException {

		try {
			DLFileEntryPermission.check(
				getPermissionChecker(), oldToFileEntryId, ActionKeys.VIEW);

			DLFileEntryPermission.check(
				getPermissionChecker(), newToFileEntryId, ActionKeys.VIEW);
		}
		catch (PrincipalException pe) {
			throw new FileShortcutPermissionException(pe);
		}

		dlFileShortcutLocalService.updateFileShortcuts(
			oldToFileEntryId, newToFileEntryId);
	}

}