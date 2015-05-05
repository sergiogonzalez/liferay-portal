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

import java.io.File;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseStoreWrapper implements Store {

	public BaseStoreWrapper(Store store) {
		_store = store;
	}

	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException {

		getStore().addDirectory(companyId, repositoryId, dirName);
	}

	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException {

		getStore().addFile(companyId, repositoryId, fileName, bytes);
	}

	public void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException {

		getStore().addFile(companyId, repositoryId, fileName, file);
	}

	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException {

		getStore().addFile(companyId, repositoryId, fileName, is);
	}

	public void checkRoot(long companyId) {
		getStore().checkRoot(companyId);
	}

	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException {

		getStore().copyFileVersion(
			companyId, repositoryId, fileName, fromVersionLabel,
			toVersionLabel);
	}

	public void deleteDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		getStore().deleteDirectory(companyId, repositoryId, dirName);
	}

	public void deleteFile(long companyId, long repositoryId, String fileName)
		throws PortalException {

		getStore().deleteFile(companyId, repositoryId, fileName);
	}

	public void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		getStore().deleteFile(companyId, repositoryId, fileName, versionLabel);
	}

	public File getFile(long companyId, long repositoryId, String fileName)
		throws PortalException {

		return getStore().getFile(companyId, repositoryId, fileName);
	}

	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		return getStore().getFile(
			companyId, repositoryId, fileName, versionLabel);
	}

	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		return getStore().getFileAsBytes(companyId, repositoryId, fileName);
	}

	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		return getStore().getFileAsBytes(
			companyId, repositoryId, fileName, versionLabel);
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		return getStore().getFileAsStream(companyId, repositoryId, fileName);
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		return getStore().getFileAsStream(
			companyId, repositoryId, fileName, versionLabel);
	}

	public String[] getFileNames(long companyId, long repositoryId) {
		return getStore().getFileNames(companyId, repositoryId);
	}

	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		return getStore().getFileNames(companyId, repositoryId, dirName);
	}

	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException {

		return getStore().getFileSize(companyId, repositoryId, fileName);
	}

	public boolean hasDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		return getStore().hasDirectory(companyId, repositoryId, dirName);
	}

	public boolean hasFile(long companyId, long repositoryId, String fileName)
		throws PortalException {

		return getStore().hasFile(companyId, repositoryId, fileName);
	}

	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		return getStore().hasFile(
			companyId, repositoryId, fileName, versionLabel);
	}

	public void move(String srcDir, String destDir) {
		getStore().move(srcDir, destDir);
	}

	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException {

		getStore().updateFile(
			companyId, repositoryId, newRepositoryId, fileName);
	}

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException {

		getStore().updateFile(companyId, repositoryId, fileName, newFileName);
	}

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, byte[] bytes)
		throws PortalException {

		getStore().updateFile(
			companyId, repositoryId, fileName, versionLabel, bytes);
	}

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, File file)
		throws PortalException {

		getStore().updateFile(
			companyId, repositoryId, fileName, versionLabel, file);
	}

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws PortalException {

		getStore().updateFile(
			companyId, repositoryId, fileName, versionLabel, is);
	}

	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException {

		getStore().updateFileVersion(
			companyId, repositoryId, fileName, fromVersionLabel,
			toVersionLabel);
	}

	protected Store getStore() {
		return _store;
	}

	private final Store _store;

}