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

package com.liferay.portal.repository.capabilities.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;

import java.util.List;

/**
 * @author Iván Zaera
 */
public class DLFolderServiceAdapter {

	public DLFolderServiceAdapter(DLFolderLocalService dlFolderLocalService) {
		this(dlFolderLocalService, null);
	}

	public DLFolderServiceAdapter(
		DLFolderLocalService dlFolderLocalService,
		DLFolderService dlFolderService) {

		_dlFolderLocalService = dlFolderLocalService;
		_dlFolderService = dlFolderService;
	}

	public void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws PortalException {

		if (_dlFolderService != null) {
			_dlFolderService.deleteFolder(folderId, includeTrashedEntries);
		}
		else {
			_dlFolderLocalService.deleteFolder(folderId, includeTrashedEntries);
		}
	}

	public ActionableDynamicQuery getActionableDynamicQuery()
		throws PortalException {

		if (_dlFolderService != null) {
			throw new PrincipalException(
				"Actionable dynamic query cannot be used with the Repository " +
					"interface");
		}

		return _dlFolderLocalService.getActionableDynamicQuery();
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition<?> queryDefinition)
		throws PortalException {

		List<Object> objects = null;

		if (_dlFolderService != null) {
			objects = _dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(
				groupId, folderId, mimeTypes, includeMountFolders,
				queryDefinition);
		}
		else {
			objects =
				_dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcuts(
					groupId, folderId, mimeTypes, includeMountFolders,
					queryDefinition);
		}

		return objects;
	}

	private final DLFolderLocalService _dlFolderLocalService;
	private final DLFolderService _dlFolderService;

}