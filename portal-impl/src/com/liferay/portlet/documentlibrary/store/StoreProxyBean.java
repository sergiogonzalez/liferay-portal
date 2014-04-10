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
import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portlet.documentlibrary.service.DLConfig;

import java.io.File;
import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 */
public class StoreProxyBean extends BaseProxyBean implements Store {

	@Override
	public void addDirectory(
		long companyId, long repositoryId, String dirName) {

		throw new UnsupportedOperationException();
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
		DLConfig dlConfig) {

		throw new UnsupportedOperationException();
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
		DLConfig dlConfig) {

		throw new UnsupportedOperationException();
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
		DLConfig dlConfig) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void checkRoot(long companyId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void copyFileVersion(
		long companyId, long repositoryId, String fileName,
		String fromVersionLabel, String toVersionLabel) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		throw new UnsupportedOperationException();
	}

	@Override
	public File getFile(long companyId, long repositoryId, String fileName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public File getFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] getFileAsBytes(
		long companyId, long repositoryId, String fileName) {

		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] getFileAsBytes(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getFileAsStream(
		long companyId, long repositoryId, String fileName) {

		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getFileAsStream(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getFileNames(long companyId, long repositoryId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		throw new UnsupportedOperationException();
	}

	@Override
	public long getFileSize(
		long companyId, long repositoryId, String fileName) {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasDirectory(
		long companyId, long repositoryId, String dirName) {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasFile(long companyId, long repositoryId, String fileName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void move(String srcDir, String destDir) {
		throw new UnsupportedOperationException();
	}

	public void reindex(String[] ids) {
		throw new UnsupportedOperationException();
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
		String fileName, DLConfig dlConfig) {

		throw new UnsupportedOperationException();
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
		long companyId, long repositoryId, String fileName, String versionLabel,
		byte[] bytes, DLConfig dlConfig) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void updateFile(
		long companyId, long repositoryId, String fileName, String newFileName,
		DLConfig dlConfig) {

		throw new UnsupportedOperationException();
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
		long companyId, long repositoryId, String fileName, String versionLabel,
		File file, DLConfig dlConfig) {

		throw new UnsupportedOperationException();
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
		long companyId, long repositoryId, String fileName, String versionLabel,
		InputStream is, DLConfig dlConfig) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void updateFileVersion(
		long companyId, long repositoryId, String fileName,
		String fromVersionLabel, String toVersionLabel) {

		throw new UnsupportedOperationException();
	}

}