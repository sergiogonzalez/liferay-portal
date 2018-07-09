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

package com.liferay.document.library.google.drive.service;

import com.liferay.document.library.google.drive.model.GoogleDriveFileReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.File;
import java.io.IOException;

/**
 * @author Adolfo Pérez
 */
public interface GoogleDriveManager {

	public GoogleDriveFileReference checkOut(
			FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException;

	public void delete(FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException;

	public String getAuthorizationURL(String state, String redirectUri);

	public File getContentFile(
			FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException;

	public boolean hasValidCredential(long userId) throws IOException;

	public boolean isGoogleDriveFile(FileEntry fileEntry);

	public void requestAuthorizationToken(
			long userId, String code, String redirectUri)
		throws IOException;

	public GoogleDriveFileReference requestEditAccess(
			FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException;

}