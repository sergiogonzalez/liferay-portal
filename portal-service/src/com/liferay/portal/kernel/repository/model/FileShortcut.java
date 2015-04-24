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

package com.liferay.portal.kernel.repository.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.trash.model.TrashEntry;

/**
 * @author Adolfo Pérez
 * @author Roberto Díaz
 * @
 */
public interface FileShortcut
	extends RepositoryEntry, RepositoryModel<FileShortcut> {

	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException;

	public long getFileShortcutId();

	public FileVersion getFileVersion() throws PortalException;

	public Folder getFolder() throws PortalException;

	public long getFolderId();

	public int getStatus();

	public long getToFileEntryId();

	public String getToTitle();

	public TrashEntry getTrashEntry() throws PortalException;

	public TrashHandler getTrashHandler();

	public boolean isInHiddenFolder();

	public boolean isInTrash();

	public boolean isInTrashContainer();

	public boolean isInTrashExplicitly();

}