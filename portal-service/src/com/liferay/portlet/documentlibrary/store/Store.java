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
 * The interface for all file store implementations. Most, if not all
 * implementations should extend from the class {@link BaseStore}.
 *
 * @author Brian Wing Shun Chan
 * @author Edward Han
 * @see    BaseStore
 */
public interface Store {

	public static final String VERSION_DEFAULT = "1.0";

	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException;

	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException, SystemException;

	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes,
			DLConfig dlConfig)
		throws PortalException, SystemException;

	public void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException, SystemException;

	public void addFile(
			long companyId, long repositoryId, String fileName, File file,
			DLConfig dlConfig)
		throws PortalException, SystemException;

	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException, SystemException;

	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is,
			DLConfig dlConfig)
		throws PortalException, SystemException;

	public void checkRoot(long companyId) throws SystemException;

	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException;

	public void deleteDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException;

	public void deleteFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException;

	public void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException;

	public File getFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException;

	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException;

	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException;

	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException;

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException;

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException;

	public String[] getFileNames(long companyId, long repositoryId)
		throws SystemException;

	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException;

	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException;

	public boolean hasDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException;

	public boolean hasFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException;

	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException;

	public void move(String srcDir, String destDir) throws SystemException;

	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException, SystemException;

	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName, DLConfig dlConfig)
		throws PortalException, SystemException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException, SystemException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, byte[] bytes)
		throws PortalException, SystemException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, byte[] bytes, DLConfig dlConfig)
		throws PortalException, SystemException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName, DLConfig dlConfig)
		throws PortalException, SystemException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, File file)
		throws PortalException, SystemException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, File file, DLConfig dlConfig)
		throws PortalException, SystemException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws PortalException, SystemException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is, DLConfig dlConfig)
		throws PortalException, SystemException;

	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException;

}