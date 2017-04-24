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

package com.liferay.journal.web.internal.upload;

import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.journal.configuration.JournalFileUploadsConfiguration;
import com.liferay.journal.service.permission.JournalPermission;
import com.liferay.portal.kernel.exception.ImageTypeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourcePermissionCheckerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.upload.BaseUploadHandler;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TempFileEntryUtil;

import java.io.InputStream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Eduardo Garcia
 */
public class ImageJournalUploadHandler extends BaseUploadHandler {

	public ImageJournalUploadHandler(
		DLValidator dlValidator,
		JournalFileUploadsConfiguration journalFileUploadsConfiguration) {

		_dlValidator = dlValidator;
		_journalFileUploadsConfiguration = journalFileUploadsConfiguration;
	}

	@Override
	public void validateFile(String fileName, String contentType, long size)
		throws PortalException {

		_dlValidator.validateFileSize(fileName, size);

		String extension = FileUtil.getExtension(fileName);

		for (String imageExtension :
				_journalFileUploadsConfiguration.imageExtensions()) {

			if (StringPool.STAR.equals(imageExtension) ||
				imageExtension.equals(StringPool.PERIOD + extension)) {

				return;
			}
		}

		throw new ImageTypeException(
			"Invalid image type for file name " + fileName);
	}

	@Override
	protected FileEntry addFileEntry(
			long userId, long groupId, long folderId, String fileName,
			String contentType, InputStream inputStream, long size,
			ServiceContext serviceContext)
		throws PortalException {

		return TempFileEntryUtil.addTempFileEntry(
			groupId, userId, TEMP_FOLDER_NAME, fileName, inputStream,
			contentType);
	}

	@Override
	protected void checkPermission(
			long groupId, long folderId, PermissionChecker permissionChecker)
		throws PortalException {

		boolean containsResourcePermission =
			ResourcePermissionCheckerUtil.containsResourcePermission(
				permissionChecker, JournalPermission.RESOURCE_NAME, groupId,
				ActionKeys.ADD_ARTICLE);

		if (!containsResourcePermission) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, JournalPermission.RESOURCE_NAME, groupId,
				ActionKeys.ADD_ARTICLE);
		}
	}

	@Override
	protected void doHandleUploadException(
			PortletRequest portletRequest, PortletResponse portletResponse,
			PortalException pe, JSONObject jsonObject)
		throws PortalException {

		if (pe instanceof ImageTypeException) {
			JSONObject errorJSONObject = JSONFactoryUtil.createJSONObject();

			errorJSONObject.put(
				"errorType",
				ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION);
			errorJSONObject.put("message", StringPool.BLANK);

			jsonObject.put("error", errorJSONObject);
		}
		else {
			throw pe;
		}
	}

	@Override
	protected FileEntry fetchFileEntry(
		long userId, long groupId, long folderId, String fileName) {

		try {
			return TempFileEntryUtil.getTempFileEntry(
				groupId, userId, TEMP_FOLDER_NAME, fileName);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return null;
		}
	}

	@Override
	protected JSONObject getImageJSONObject(PortletRequest portletRequest)
		throws PortalException {

		JSONObject imageJSONObject = super.getImageJSONObject(portletRequest);

		imageJSONObject.put("type", "journal");

		return imageJSONObject;
	}

	@Override
	protected String getParameterName() {
		return "imageSelectorFileName";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageJournalUploadHandler.class);

	private final DLValidator _dlValidator;
	private final JournalFileUploadsConfiguration
		_journalFileUploadsConfiguration;

}