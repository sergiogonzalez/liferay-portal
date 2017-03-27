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

package com.liferay.document.library.web.internal.upload;

import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.IOException;
import java.io.InputStream;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 * @author Sergio González
 * @author Alejandro Tardín
 */
@Component(service = DLUploadFileEntryHandler.class)
public class DLUploadFileEntryHandler implements UploadFileEntryHandler {

	@Override
	public FileEntry addFileEntry(PortletRequest portletRequest)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		InputStream inputStream = null;

		try {
			UploadPortletRequest uploadPortletRequest =
				PortalUtil.getUploadPortletRequest(portletRequest);

			String fileName = uploadPortletRequest.getFileName(
				"imageSelectorFileName");
			String contentType = uploadPortletRequest.getContentType(
				"imageSelectorFileName");
			long size = uploadPortletRequest.getSize("imageSelectorFileName");

			_validateFile(size);

			long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");

			String uniqueFileName = _getUniqueFileName(
				themeDisplay, fileName, folderId);

			inputStream = uploadPortletRequest.getFileAsStream(
				"imageSelectorFileName");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				DLFileEntry.class.getName(), uploadPortletRequest);

			return _dlAppService.addFileEntry(
				themeDisplay.getScopeGroupId(), folderId, uniqueFileName,
				contentType, uniqueFileName, StringPool.BLANK, StringPool.BLANK,
				inputStream, size, serviceContext);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	@Override
	public void checkPermission(
			UploadPortletRequest uploadPortletRequest, long groupId,
			PermissionChecker permissionChecker)
		throws PortalException {

		long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");

		DLFolderPermission.check(
			permissionChecker, groupId, folderId, ActionKeys.ADD_DOCUMENT);
	}

	@Override
	public String getURL(FileEntry fileEntry, ThemeDisplay themeDisplay) {
		try {
			return DLUtil.getPreviewURL(
				fileEntry, fileEntry.getLatestFileVersion(), themeDisplay,
				StringPool.BLANK);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get URL for file entry " +
						fileEntry.getFileEntryId(),
					pe);
			}
		}

		return StringPool.BLANK;
	}

	private FileEntry _fetchFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException {

		try {
			return _dlAppService.getFileEntry(groupId, folderId, fileName);
		}
		catch (PortalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return null;
		}
	}

	private String _getUniqueFileName(
			ThemeDisplay themeDisplay, String fileName, long folderId)
		throws PortalException {

		FileEntry fileEntry = _fetchFileEntry(
			themeDisplay.getScopeGroupId(), folderId, fileName);

		if (fileEntry == null) {
			return fileName;
		}

		int suffix = 1;

		for (int i = 0; i < _UNIQUE_FILE_NAME_TRIES; i++) {
			String curFileName = FileUtil.appendParentheticalSuffix(
				fileName, String.valueOf(suffix));

			fileEntry = _fetchFileEntry(
				themeDisplay.getScopeGroupId(), folderId, curFileName);

			if (fileEntry == null) {
				return curFileName;
			}

			suffix++;
		}

		throw new PortalException(
			"Unable to get a unique file name for " + fileName);
	}

	private void _validateFile(long size) throws PortalException {
		long maxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

		if ((maxSize > 0) && (size > maxSize)) {
			throw new FileSizeException(
				size + " exceeds its maximum permitted size of " + maxSize);
		}
	}

	private static final int _UNIQUE_FILE_NAME_TRIES = 50;

	private static final Log _log = LogFactoryUtil.getLog(
		DLUploadFileEntryHandler.class);

	@Reference
	private DLAppService _dlAppService;

}