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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.security.permission.PermissionChecker;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Sergio González
 */
public abstract class BaseImageSelectorAction
	extends com.liferay.portal.action.BaseImageSelectorAction {

	@Override
	public void checkPermission(
			long groupId, PermissionChecker permissionChecker)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void validateFile(String fileName, String contentType, long size)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	protected abstract long getMaxFileSize();

	@Override
	protected void handleUploadException(
			ActionRequest actionRequest, ActionResponse actionResponse,
			Exception e, JSONObject jsonObject)
		throws Exception {

		throw new UnsupportedOperationException();
	}

}