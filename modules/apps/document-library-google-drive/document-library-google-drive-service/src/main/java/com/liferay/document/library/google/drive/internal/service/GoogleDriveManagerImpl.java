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

package com.liferay.document.library.google.drive.internal.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;

import com.liferay.document.library.google.drive.constants.GoogleDriveMimeTypes;
import com.liferay.document.library.google.drive.model.GoogleDriveEntry;
import com.liferay.document.library.google.drive.model.GoogleDriveFileReference;
import com.liferay.document.library.google.drive.service.GoogleDriveEntryLocalService;
import com.liferay.document.library.google.drive.service.GoogleDriveManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.security.GeneralSecurityException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = GoogleDriveManager.class)
public class GoogleDriveManagerImpl implements GoogleDriveManager {

	@Override
	public GoogleDriveFileReference checkOut(
			FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		try {
			Credential credential = _oAuth2Manager.getCredential(
				serviceContext.getUserId());

			if (credential == null) {
				throw new PrincipalException("not authorized");
			}

			com.google.api.services.drive.model.File file =
				new com.google.api.services.drive.model.File();

			file.setMimeType(
				GoogleDriveMimeTypes.APPLICATION_VND_GOOGLE_APPS_DOCUMENT);
			file.setName(fileEntry.getTitle());

			FileContent fileContent = new FileContent(
				fileEntry.getMimeType(), _getFileEntryContents(fileEntry));

			Drive drive = new Drive.Builder(
				_netHttpTransport, _jsonFactory, credential
			).build();

			Drive.Files driveFiles = drive.files();

			Drive.Files.Create driveFilesCreate = driveFiles.create(
				file, fileContent);

			com.google.api.services.drive.model.File uploadedFile =
				driveFilesCreate.execute();

			_googleDriveEntryLocalService.addEntry(
				fileEntry, uploadedFile.getId(), serviceContext);

			return new GoogleDriveFileReference(
				uploadedFile.getId(), fileEntry.getFileEntryId());
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	@Override
	public void delete(FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		try {
			String googleDriveFileId = _getGoogleDriveFileId(fileEntry);

			if (Validator.isNull(googleDriveFileId)) {
				return;
			}

			Credential credential = _oAuth2Manager.getCredential(
				serviceContext.getUserId());

			if (credential == null) {
				throw new PrincipalException("not authorized");
			}

			Drive drive = new Drive.Builder(
				_netHttpTransport, _jsonFactory, credential
			).build();

			Drive.Files driveFiles = drive.files();

			Drive.Files.Delete driveFilesDelete = driveFiles.delete(
				googleDriveFileId);

			driveFilesDelete.execute();

			_googleDriveEntryLocalService.deleteEntry(fileEntry);
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	@Override
	public String getAuthorizationURL(String state, String redirectUri) {
		return _oAuth2Manager.getAuthorizationURL(state, redirectUri);
	}

	@Override
	public File getContentFile(
			FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		try {
			String googleDriveFileId = _getGoogleDriveFileId(fileEntry);

			if (Validator.isNull(googleDriveFileId)) {
				throw new IllegalArgumentException("Not a Google Drive file");
			}

			Credential credential = _oAuth2Manager.getCredential(
				serviceContext.getUserId());

			if (credential == null) {
				throw new PrincipalException("not authorized");
			}

			Drive drive = new Drive.Builder(
				_netHttpTransport, _jsonFactory, credential
			).build();

			Drive.Files driveFiles = drive.files();

			Drive.Files.Export driveFilesExport = driveFiles.export(
				googleDriveFileId, fileEntry.getMimeType());

			try (InputStream is =
					driveFilesExport.executeMediaAsInputStream()) {

				return FileUtil.createTempFile(is);
			}
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	@Override
	public boolean hasValidCredential(long userId) throws IOException {
		Credential credential = _oAuth2Manager.getCredential(userId);

		if (credential == null) {
			return false;
		}

		if ((credential.getExpiresInSeconds() <= 0) &&
			!credential.refreshToken()) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isGoogleDriveFile(FileEntry fileEntry) {
		GoogleDriveEntry googleDriveEntry =
			_googleDriveEntryLocalService.fetchEntry(fileEntry);

		if (googleDriveEntry == null) {
			return false;
		}

		return true;
	}

	@Override
	public void requestAuthorizationToken(
			long userId, String code, String redirectUri)
		throws IOException {

		_oAuth2Manager.requestAuthorizationToken(userId, code, redirectUri);
	}

	@Override
	public GoogleDriveFileReference requestEditAccess(
			FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		try {
			String googleDriveFileId = _getGoogleDriveFileId(fileEntry);

			if (Validator.isNull(googleDriveFileId)) {
				throw new IllegalArgumentException("Not a Google Drive file");
			}

			Credential credential = _oAuth2Manager.getCredential(
				serviceContext.getUserId());

			if (credential == null) {
				throw new PrincipalException("not authorized");
			}

			return new GoogleDriveFileReference(
				googleDriveFileId, fileEntry.getFileEntryId());
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	@Activate
	protected void activate() throws GeneralSecurityException, IOException {
		_jsonFactory = JacksonFactory.getDefaultInstance();
		_netHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
	}

	private File _getFileEntryContents(FileEntry fileEntry)
		throws IOException, PortalException {

		try (InputStream is = fileEntry.getContentStream()) {
			return FileUtil.createTempFile(is);
		}
	}

	private String _getGoogleDriveFileId(FileEntry fileEntry)
		throws PortalException {

		GoogleDriveEntry googleDriveEntry =
			_googleDriveEntryLocalService.getEntry(fileEntry);

		return googleDriveEntry.getGoogleDriveId();
	}

	@Reference
	private GoogleDriveEntryLocalService _googleDriveEntryLocalService;

	private JsonFactory _jsonFactory;
	private NetHttpTransport _netHttpTransport;

	@Reference
	private OAuth2Manager _oAuth2Manager;

}