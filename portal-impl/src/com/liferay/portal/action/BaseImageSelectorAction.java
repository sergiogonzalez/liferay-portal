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

package com.liferay.portal.action;

import com.liferay.portal.kernel.editor.util.EditorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.FileSizeException;

import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Sergio González
 */
public abstract class BaseImageSelectorAction extends PortletAction {

	@Override
	public void processAction(
		ActionMapping actionMapping, ActionForm actionForm,
		PortletConfig portletConfig, ActionRequest actionRequest,
		ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		checkPermission(
			themeDisplay.getScopeGroupId(),
			themeDisplay.getPermissionChecker());

		UploadException uploadException =
			(UploadException)actionRequest.getAttribute(
				WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			if (uploadException.isExceededLiferayFileItemSizeLimit()) {
				throw new LiferayFileItemException();
			}
			else if (uploadException.isExceededSizeLimit()) {
				throw new FileSizeException(uploadException.getCause());
			}

			throw new PortalException(uploadException.getCause());
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		InputStream inputStream = null;

		try {
			JSONObject imageJSONObject = JSONFactoryUtil.createJSONObject();

			imageJSONObject.put(
				"dataImageIdAttribute",
				EditorConstants.DATA_IMAGE_ID_ATTRIBUTE);

			String fileName = uploadPortletRequest.getFileName(
				"imageSelectorFileName");
			String contentType = uploadPortletRequest.getContentType(
				"imageSelectorFileName");
			long size = uploadPortletRequest.getSize("imageSelectorFileName");

			validateFile(fileName, contentType, size);

			inputStream = uploadPortletRequest.getFileAsStream(
				"imageSelectorFileName");

			String mimeType = MimeTypesUtil.getContentType(
				inputStream, fileName);

			FileEntry fileEntry = TempFileEntryUtil.addTempFileEntry(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				_TEMP_FOLDER_NAME, StringUtil.randomString() + fileName,
				inputStream, mimeType);

			imageJSONObject.put(
				"dataImageIdAttribute",
				EditorConstants.DATA_IMAGE_ID_ATTRIBUTE);

			imageJSONObject.put("fileEntryId", fileEntry.getFileEntryId());

			imageJSONObject.put(
				"url",
				PortletFileRepositoryUtil.getPortletFileEntryURL(
					themeDisplay, fileEntry, StringPool.BLANK));

			jsonObject.put("image", imageJSONObject);

			jsonObject.put("success", Boolean.TRUE);
		}
		catch (Exception e) {
			handleUploadException(actionRequest, actionResponse, e, jsonObject);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	protected abstract void checkPermission(
		long groupId, PermissionChecker permissionChecker)
		throws PortalException;

	protected abstract void handleUploadException(
		ActionRequest actionRequest, ActionResponse actionResponse,
		Exception e, JSONObject jsonObject)
		throws Exception;

	protected abstract void validateFile(
		String fileName, String contentType, long size)
		throws PortalException;

	private static final String _TEMP_FOLDER_NAME =
		BaseImageSelectorAction.class.getName();

}