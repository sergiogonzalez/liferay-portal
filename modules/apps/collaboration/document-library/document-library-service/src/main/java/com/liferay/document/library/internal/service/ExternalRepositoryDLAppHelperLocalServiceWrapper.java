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

package com.liferay.document.library.internal.service;

import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLAppHelperLocalServiceWrapper;
import com.liferay.document.library.kernel.util.DLAppHelperThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.repository.registry.RepositoryClassDefinitionCatalogUtil;

import java.io.Serializable;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ExternalRepositoryDLAppHelperLocalServiceWrapper
	extends DLAppHelperLocalServiceWrapper {

	public ExternalRepositoryDLAppHelperLocalServiceWrapper() {
		super(null);
	}

	public ExternalRepositoryDLAppHelperLocalServiceWrapper(
		DLAppHelperLocalService dlAppHelperLocalService) {

		super(dlAppHelperLocalService);
	}

	@Override
	public void addFolder(
			long userId, Folder folder, ServiceContext serviceContext)
		throws PortalException {

		if (_isEnabled(folder)) {
			super.addFolder(userId, folder, serviceContext);
		}
	}

	@Override
	public void deleteFileEntry(FileEntry fileEntry) throws PortalException {
		if (_isEnabled(fileEntry)) {
			super.deleteFileEntry(fileEntry);
		}
	}

	@Override
	public void deleteFolder(Folder folder) throws PortalException {
		if (_isEnabled(folder)) {
			super.deleteFolder(folder);
		}
	}

	@Override
	public void updateFileEntry(
			long userId, FileEntry fileEntry, FileVersion sourceFileVersion,
			FileVersion destinationFileVersion, long assetClassPK)
		throws PortalException {

		if (_isEnabled(fileEntry)) {
			super.updateFileEntry(
				userId, fileEntry, sourceFileVersion, destinationFileVersion,
				assetClassPK);
		}
	}

	@Override
	public void updateFileEntry(
			long userId, FileEntry fileEntry, FileVersion sourceFileVersion,
			FileVersion destinationFileVersion, ServiceContext serviceContext)
		throws PortalException {

		if (_isEnabled(fileEntry)) {
			super.updateFileEntry(
				userId, fileEntry, sourceFileVersion, destinationFileVersion,
				serviceContext);
		}
	}

	@Override
	public void updateFolder(
			long userId, Folder folder, ServiceContext serviceContext)
		throws PortalException {

		if (_isEnabled(folder)) {
			super.updateFolder(userId, folder, serviceContext);
		}
	}

	@Override
	public void updateStatus(
			long userId, FileEntry fileEntry, FileVersion latestFileVersion,
			int oldStatus, int newStatus, ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		if (_isEnabled(fileEntry)) {
			super.updateStatus(
				userId, fileEntry, latestFileVersion, oldStatus, newStatus,
				serviceContext, workflowContext);
		}
	}

	private boolean _isEnabled(FileEntry fileEntry) {
		if (!DLAppHelperThreadLocal.isEnabled()) {
			return false;
		}

		if (_isExternalRepository(fileEntry.getRepositoryId())) {
			return false;
		}

		return true;
	}

	private boolean _isEnabled(Folder folder) {
		if (!DLAppHelperThreadLocal.isEnabled()) {
			return false;
		}

		if (!folder.isMountPoint() &&
			_isExternalRepository(folder.getRepositoryId())) {

			return false;
		}

		return true;
	}

	private boolean _isExternalRepository(long repositoryId) {
		com.liferay.portal.kernel.model.Repository repository =
			_repositoryLocalService.fetchRepository(repositoryId);

		if (repository == null) {
			return false;
		}

		Collection<String> externalRepositoryClassNames =
			RepositoryClassDefinitionCatalogUtil.
				getExternalRepositoryClassNames();

		return externalRepositoryClassNames.contains(repository.getClassName());
	}

	@Reference
	private RepositoryLocalService _repositoryLocalService;

}