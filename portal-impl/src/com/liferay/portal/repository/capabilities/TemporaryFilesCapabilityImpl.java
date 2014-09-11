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

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.capabilities.ConfigurationCapability;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFilesCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

/**
 * @author Iván Zaera
 */
public class TemporaryFilesCapabilityImpl implements TemporaryFilesCapability {

	public static final long DEFAULT_TEMPORARY_FILES_TIMEOUT =
		12 * 60 * 60 * 1000;

	public static final String FOLDER_NAME_TMP = "tmp";

	public static final String PROPERTY_TEMPORARY_FILES_TIMEOUT =
		"temporaryFilesTimeout";

	public TemporaryFilesCapabilityImpl(LocalRepository localRepository) {
		_localRepository = localRepository;
	}

	@Override
	public FileEntry addTemporaryFile(
			long userId, String folderName, String fileName, String mimeType,
			InputStream inputStream)
		throws PortalException {

		Folder folder = getTempFolder(userId, folderName);

		File file = null;

		try {
			file = FileUtil.createTempFile(inputStream);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			return _localRepository.addFileEntry(
				userId, folder.getFolderId(), fileName, mimeType, fileName, "",
				"", file, serviceContext);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to write temporary file", ioe);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public void deleteExpiredTemporaryFiles() throws PortalException {
	}

	@Override
	public void deleteTemporaryFile(
			long userId, String folderName, String fileName)
		throws PortalException {

		FileEntry fileEntry = getTemporaryFile(userId, folderName, fileName);

		_localRepository.deleteFileEntry(fileEntry.getFileEntryId());
	}

	@Override
	public FileEntry getTemporaryFile(
			long userId, String folderName, String fileName)
		throws PortalException {

		Folder folder = getTempFolder(userId, folderName);

		return _localRepository.getFileEntry(folder.getFolderId(), fileName);
	}

	@Override
	public List<FileEntry> getTemporaryFiles(long userId, String folderName)
		throws PortalException {

		Folder folder = getTempFolder(userId, folderName);

		return _localRepository.getRepositoryFileEntries(
			folder.getFolderId(), 0, QueryUtil.ALL_POS, null);
	}

	@Override
	public long getTemporaryFilesTimeout() {
		ConfigurationCapability configurationCapability =
			_localRepository.getCapability(ConfigurationCapability.class);

		String minimumLifespanMilliseconds =
			configurationCapability.getProperty(
				getClass(), PROPERTY_TEMPORARY_FILES_TIMEOUT);

		if (minimumLifespanMilliseconds == null) {
			return DEFAULT_TEMPORARY_FILES_TIMEOUT;
		}

		return Long.valueOf(minimumLifespanMilliseconds);
	}

	@Override
	public void setTemporaryFilesTimeout(long temporaryFilesTimeout) {
		ConfigurationCapability configurationCapability =
			_localRepository.getCapability(ConfigurationCapability.class);

		configurationCapability.setProperty(
			getClass(), PROPERTY_TEMPORARY_FILES_TIMEOUT,
			String.valueOf(temporaryFilesTimeout));
	}

	protected Folder getTempFolder(long userId, String folderName)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Folder tmpFolder = _getFolder(
			userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, FOLDER_NAME_TMP,
			serviceContext);

		Folder userFolder = _getFolder(
			userId, tmpFolder.getFolderId(), String.valueOf(userId),
			serviceContext);

		return _getFolder(
			userId, userFolder.getFolderId(), folderName, serviceContext);
	}

	private Folder _getFolder(
			long userId, long parentFolderId, String folderName,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			return _localRepository.getFolder(parentFolderId, folderName);
		}
		catch (NoSuchFolderException nsfe) {
			return _localRepository.addFolder(
				userId, parentFolderId, folderName, "", serviceContext);
		}
	}

	private LocalRepository _localRepository;

}