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

package com.liferay.blogs.web.upload;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.upload.BaseUploadHandler;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourcePermissionCheckerUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portlet.blogs.BlogImageNameException;
import com.liferay.portlet.blogs.BlogImageSizeException;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.permission.BlogsPermission;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.antivirus.AntivirusScannerException;

import java.io.IOException;
import java.io.InputStream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Sergio González
 * @author Adolfo Pérez
 */
public abstract class BaseBlogsImageUploadHandler extends BaseUploadHandler {

	protected FileEntry addFileEntry(PortletRequest portletRequest)
		throws PortalException {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		InputStream inputStream = null;

		try {
			String parameterName = getParameterName();

			String fileName = uploadPortletRequest.getFileName(parameterName);
			String contentType = uploadPortletRequest.getContentType(
				parameterName);
			long size = uploadPortletRequest.getSize(parameterName);

			validateFile(fileName, size);

			inputStream = uploadPortletRequest.getFileAsStream(parameterName);

			String uniqueFileName = getUniqueFileName(
				themeDisplay, fileName);

			return addFileEntry(
				themeDisplay, uniqueFileName, inputStream, contentType);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected abstract FileEntry addFileEntry(
			ThemeDisplay themeDisplay, String fileName, InputStream inputStream,
			String contentType)
		throws PortalException;

	@Override
	protected void checkPermission(
			long groupId, PermissionChecker permissionChecker)
		throws PortalException {

		boolean containsResourcePermission =
			ResourcePermissionCheckerUtil.containsResourcePermission(
				permissionChecker, BlogsPermission.RESOURCE_NAME, groupId,
				ActionKeys.ADD_ENTRY);

		if (!containsResourcePermission) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, BlogsPermission.RESOURCE_NAME, groupId,
				ActionKeys.ADD_ENTRY);
		}
	}

	protected abstract FileEntry fetchFileEntry(
			ThemeDisplay themeDisplay, String fileName)
		throws PortalException;

	protected long getMaxFileSize() {
		return PrefsPropsUtil.getLong(PropsKeys.BLOGS_IMAGE_MAX_SIZE);
	}

	protected String getParameterName() {
		return "imageSelectorFileName";
	}

	protected String getUniqueFileName(
			ThemeDisplay themeDisplay, String fileName)
		throws PortalException {

		FileEntry fileEntry = fetchFileEntry(themeDisplay, fileName);

		if (fileEntry == null) {
			return fileName;
		}

		int suffix = 1;

		for (int i = 0; i < _UNIQUE_FILE_NAME_TRIES; i++) {
			String curFileName = FileUtil.appendParentheticalSuffix(
				fileName, String.valueOf(suffix));

			fileEntry = fetchFileEntry(themeDisplay, curFileName);

			if (fileEntry == null) {
				return curFileName;
			}

			suffix++;
		}

		throw new PortalException(
			"Unable to get a unique file name for " + fileName);
	}

	@Override
	protected void handleUploadException(
			PortletRequest portletRequest, PortletResponse portletResponse,
			PortalException pe, JSONObject jsonObject)
		throws PortalException {

		jsonObject.put("success", Boolean.FALSE);

		if (pe instanceof AntivirusScannerException ||
			pe instanceof BlogImageNameException ||
			pe instanceof BlogImageSizeException ||
			pe instanceof FileNameException) {

			String errorMessage = StringPool.BLANK;
			int errorType = 0;

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (pe instanceof AntivirusScannerException) {
				errorType =
					ServletResponseConstants.SC_FILE_ANTIVIRUS_EXCEPTION;
				AntivirusScannerException ase = (AntivirusScannerException)pe;

				errorMessage = themeDisplay.translate(ase.getMessageKey());
			}
			else if (pe instanceof BlogImageNameException) {
				errorType =
					ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION;
			}
			else if (pe instanceof BlogImageSizeException) {
				errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
			}
			else if (pe instanceof FileNameException) {
				errorType = ServletResponseConstants.SC_FILE_NAME_EXCEPTION;
			}

			JSONObject errorJSONObject = JSONFactoryUtil.createJSONObject();

			errorJSONObject.put("errorType", errorType);
			errorJSONObject.put("message", errorMessage);

			jsonObject.put("error", errorJSONObject);

			try {
				JSONPortletResponseUtil.writeJSON(
					portletRequest, portletResponse, jsonObject);
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}
		else {
			throw pe;
		}
	}

	protected void validateFile(String fileName, long size)
		throws PortalException {

		long maxSize = getMaxFileSize();

		if ((maxSize > 0) && (size > maxSize)) {
			throw new BlogImageSizeException();
		}

		String extension = FileUtil.getExtension(fileName);

		String[] imageExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.BLOGS_IMAGE_EXTENSIONS, StringPool.COMMA);

		for (String imageExtension : imageExtensions) {
			if (StringPool.STAR.equals(imageExtension) ||
				imageExtension.equals(StringPool.PERIOD + extension)) {

				return;
			}
		}

		throw new BlogImageNameException(
			"Invalid image for file name " + fileName);
	}

	private static final int _UNIQUE_FILE_NAME_TRIES = 50;

}