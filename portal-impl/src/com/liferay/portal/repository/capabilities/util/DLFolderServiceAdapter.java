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
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderService;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class DLFolderServiceAdapter {

	public static DLFolderServiceAdapter create(
		DocumentRepository documentRepository) {

		if (documentRepository instanceof LocalRepository) {
			return new DLFolderServiceAdapter(
				DLFolderLocalServiceUtil.getService());
		}

		return new DLFolderServiceAdapter(
			DLFolderLocalServiceUtil.getService(),
			DLFolderServiceUtil.getService());
	}

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
			throw new PrincipalException("DL folder service is not null");
		}

		return _dlFolderLocalService.getActionableDynamicQuery();
	}

	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, boolean includeMountFolders,
			boolean hidden)
		throws PortalException {

		return _dlFolderLocalService.getFolders(
			groupId, parentFolderId, includeMountFolders, hidden);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition<?> queryDefinition)
		throws PortalException {

		List<Object> foldersAndFileEntriesAndFileShortcuts = null;

		if (_dlFolderService != null) {
			foldersAndFileEntriesAndFileShortcuts =
				_dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(
					groupId, folderId, mimeTypes, includeMountFolders,
					queryDefinition);
		}
		else {
			foldersAndFileEntriesAndFileShortcuts =
				_dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcuts(
					groupId, folderId, mimeTypes, includeMountFolders,
					queryDefinition);
		}

		return foldersAndFileEntriesAndFileShortcuts;
	}

	public boolean hasFolderLock(long userId, long folderId) {
		return _dlFolderLocalService.hasFolderLock(userId, folderId);
	}

	public Lock lockFolder(long userId, long folderId) throws PortalException {
		if (_dlFolderService != null) {
			return _dlFolderService.lockFolder(folderId);
		}

		return _dlFolderLocalService.lockFolder(userId, folderId);
	}

	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException {

		if (_dlFolderService != null) {
			_dlFolderService.unlockFolder(folderId, lockUuid);
		}
		else {
			_dlFolderLocalService.unlockFolder(folderId, lockUuid);
		}
	}

	public DLFolder update(DLFolder dlFolder) {
		return _dlFolderLocalService.updateDLFolder(dlFolder);
	}

	public void updateAssets(long folderId, boolean visible)
		throws PortalException {

		_dlFolderLocalService.updateAssets(folderId, visible);
	}

	public DLFolder updateStatus(
			long userId, long folderId, int status,
			Map<String, Serializable> workflowStatus,
			ServiceContext serviceContext)
		throws PortalException {

		return _dlFolderLocalService.updateStatus(
			userId, folderId, status, workflowStatus, serviceContext);
	}

	private final DLFolderLocalService _dlFolderLocalService;
	private final DLFolderService _dlFolderService;

}