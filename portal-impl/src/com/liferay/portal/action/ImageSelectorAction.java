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
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.FileSizeException;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Sergio González
 */
public class ImageSelectorAction extends JSONAction {

	@Override
	public String getJSON(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		UploadServletRequest uploadPortletRequest =
			PortalUtil.getUploadServletRequest(request);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		UploadException uploadException = (UploadException)request.getAttribute(
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

			inputStream = uploadPortletRequest.getFileAsStream(
				"imageSelectorFileName");

			String mimeType = uploadPortletRequest.getContentType(
				"imageSelectorFileName");

			FileEntry fileEntry = TempFileEntryUtil.addTempFileEntry(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				_TEMP_FOLDER_NAME, StringUtil.randomString() + fileName,
				inputStream, mimeType);

			imageJSONObject.put("fileEntryId", fileEntry.getFileEntryId());

			imageJSONObject.put(
				"url",
				PortletFileRepositoryUtil.getPortletFileEntryURL(
					themeDisplay, fileEntry, StringPool.BLANK));

			jsonObject.put("image", imageJSONObject);

			jsonObject.put("success", Boolean.TRUE);
		}
		catch (Exception e) {
			jsonObject.put("success", Boolean.FALSE);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}

		return jsonObject.toString();
	}

	private static final String _TEMP_FOLDER_NAME =
		ImageSelectorAction.class.getName();

}