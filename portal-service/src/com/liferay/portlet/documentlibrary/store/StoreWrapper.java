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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.documentlibrary.service.DLConfig;

import java.io.File;
import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 */
public class StoreWrapper implements Store {

	public StoreWrapper(Store store) {
		_store = store;
	}

	@Override
	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		_store.addDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException, SystemException {

		addFile(
			companyId, repositoryId, fileName, bytes,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes,
			DLConfig dlConfig)
		throws PortalException, SystemException {

		_store.addFile(companyId, repositoryId, fileName, bytes, dlConfig);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException, SystemException {

		addFile(
			companyId, repositoryId, fileName, file,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, File file,
			DLConfig dlConfig)
		throws PortalException, SystemException {

		_store.addFile(companyId, repositoryId, fileName, file, dlConfig);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException, SystemException {

		addFile(
			companyId, repositoryId, fileName, is,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is,
			DLConfig dlConfig)
		throws PortalException, SystemException {

		_store.addFile(companyId, repositoryId, fileName, is, dlConfig);
	}

	@Override
	public void checkRoot(long companyId) throws SystemException {
		_store.checkRoot(companyId);
	}

	@Override
	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		_store.copyFileVersion(
			companyId, repositoryId, fileName, fromVersionLabel,
			toVersionLabel);
	}

	@Override
	public void deleteDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		_store.deleteDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		_store.deleteFile(companyId, repositoryId, fileName);
	}

	@Override
	public void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		_store.deleteFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public File getFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return _store.getFile(companyId, repositoryId, fileName);
	}

	@Override
	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		return _store.getFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return _store.getFileAsBytes(companyId, repositoryId, fileName);
	}

	@Override
	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		return _store.getFileAsBytes(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return _store.getFileAsStream(companyId, repositoryId, fileName);
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		return _store.getFileAsStream(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public String[] getFileNames(long companyId, long repositoryId)
		throws SystemException {

		return _store.getFileNames(companyId, repositoryId);
	}

	@Override
	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		return _store.getFileNames(companyId, repositoryId, dirName);
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return _store.getFileSize(companyId, repositoryId, fileName);
	}

	@Override
	public boolean hasDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		return _store.hasDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public boolean hasFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return _store.hasFile(companyId, repositoryId, fileName);
	}

	@Override
	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		return _store.hasFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public void move(String srcDir, String destDir) throws SystemException {
		_store.move(srcDir, destDir);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException, SystemException {

		updateFile(
			companyId, repositoryId, newRepositoryId, fileName,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName, DLConfig dlConfig)
		throws PortalException, SystemException {

		_store.updateFile(
			companyId, repositoryId, newRepositoryId, fileName, dlConfig);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException, SystemException {

		updateFile(
			companyId, repositoryId, fileName, newFileName,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, byte[] bytes)
		throws PortalException, SystemException {

		updateFile(
			companyId, repositoryId, fileName, versionLabel, bytes,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, byte[] bytes, DLConfig dlConfig)
		throws PortalException, SystemException {

		_store.updateFile(
			companyId, repositoryId, fileName, versionLabel, bytes, dlConfig);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName, DLConfig dlConfig)
		throws PortalException, SystemException {

		_store.updateFile(
			companyId, repositoryId, fileName, newFileName, dlConfig);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, File file)
		throws PortalException, SystemException {

		updateFile(
			companyId, repositoryId, fileName, versionLabel, file,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, File file, DLConfig dlConfig)
		throws PortalException, SystemException {

		_store.updateFile(
			companyId, repositoryId, fileName, versionLabel, file, dlConfig);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws PortalException, SystemException {

		updateFile(
			companyId, repositoryId, fileName, versionLabel, is,
			DLConfig.getLiberalDLConfig());
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is, DLConfig dlConfig)
		throws PortalException, SystemException {

		_store.updateFile(
			companyId, repositoryId, fileName, versionLabel, is, dlConfig);
	}

	@Override
	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		_store.updateFileVersion(
			companyId, repositoryId, fileName, fromVersionLabel,
			toVersionLabel);
	}

	private Store _store;

}