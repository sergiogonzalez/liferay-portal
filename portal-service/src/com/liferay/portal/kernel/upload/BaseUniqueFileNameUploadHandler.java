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

package com.liferay.portal.kernel.upload;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;
import java.io.InputStream;

import javax.portlet.PortletRequest;

/**
 * @author Roberto Díaz
 */
public abstract class BaseUniqueFileNameUploadHandler
	extends BaseUploadHandler {

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

			String uniqueFileName = getUniqueFileName(themeDisplay, fileName);

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
			ThemeDisplay themeDisplay, String uniqueFileName,
			InputStream inputStream, String contentType)
		throws PortalException;

	protected abstract FileEntry fetchFileEntry(
			ThemeDisplay themeDisplay, String fileName)
		throws PortalException;

	protected abstract String getParameterName();

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

	protected abstract void validateFile(String fileName, long size)
		throws PortalException;

	private static final int _UNIQUE_FILE_NAME_TRIES = 50;

}