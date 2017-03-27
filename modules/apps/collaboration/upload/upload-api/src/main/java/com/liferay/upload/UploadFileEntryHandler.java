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

package com.liferay.upload;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.upload.UploadPortletRequest;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Alejandro Tardín
 */
public interface UploadFileEntryHandler {

	public static final String TEMP_FOLDER_NAME =
		UploadFileEntryHandler.class.getName();

	public FileEntry addFileEntry(PortletRequest portletRequest)
		throws IOException, PortalException;

	public void checkPermission(
			UploadPortletRequest uploadPortletRequest, long groupId,
			PermissionChecker permissionChecker)
		throws PortalException;

	public default void customizeFileJSONObject(JSONObject jsonObject) {
	}

	public default void doHandleUploadException(
			PortletRequest portletRequest, PortletResponse portletResponse,
			PortalException pe, JSONObject jsonObject)
		throws PortalException {

		throw pe;
	}

}