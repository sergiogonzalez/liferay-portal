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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.LocalRepositoryFactory;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.BaseRepositoryFactory;
import com.liferay.portal.service.RepositoryLocalService;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;

/**
 * @author Adolfo Pérez
 */
public class PortletLocalRepositoryFactoryImpl
	extends BaseRepositoryFactory<LocalRepository>
	implements LocalRepositoryFactory {

	@Override
	protected LocalRepository createExternalRepository(
			long repositoryId, long classNameId)
		throws PortalException, SystemException {

		throw new UnsupportedOperationException(
			"This factory cannot be used to create external repositories");
	}

	@Override
	protected LocalRepository createExternalRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		throw new UnsupportedOperationException(
			"This factory cannot be used to create external repositories");
	}

	@Override
	protected LocalRepository createLiferayRepositoryInstance(
		long groupId, long repositoryId, long dlFolderId) {

		return new PortletLocalRepository(
			getRepositoryLocalService(), getRepositoryService(),
			getDlAppHelperLocalService(), getDlFileEntryLocalService(),
			getDlFileEntryService(), getDlFileEntryTypeLocalService(),
			getDlFileVersionLocalService(), getDlFileVersionService(),
			getDlFolderLocalService(), getDlFolderService(),
			getResourceLocalService(), groupId, repositoryId, dlFolderId);
	}

	@Override
	protected long getFileEntryRepositoryId(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntryLocalService dlFileEntryLocalService =
			getDlFileEntryLocalService();

		DLFileEntry dlFileEntry = dlFileEntryLocalService.getFileEntry(
			fileEntryId);

		return dlFileEntry.getRepositoryId();
	}

	@Override
	protected long getFileVersionRepositoryId(long fileVersionId)
		throws PortalException, SystemException {

		DLFileVersionLocalService dlFileVersionLocalService =
			getDlFileVersionLocalService();

		DLFileVersion dlFileVersion = dlFileVersionLocalService.getFileVersion(
			fileVersionId);

		return dlFileVersion.getRepositoryId();
	}

	@Override
	protected long getFolderRepositoryId(long folderId)
		throws PortalException, SystemException {

		DLFolderLocalService dlFolderLocalService = getDlFolderLocalService();

		DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

		return dlFolder.getRepositoryId();
	}

	@Override
	protected Repository getRepository(long repositoryId)
		throws SystemException {

		RepositoryLocalService repositoryLocalService =
			getRepositoryLocalService();

		return repositoryLocalService.fetchRepository(repositoryId);
	}

}