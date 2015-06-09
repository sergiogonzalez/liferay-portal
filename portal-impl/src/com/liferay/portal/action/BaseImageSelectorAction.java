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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.struts.PortletAction;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
public abstract class BaseImageSelectorAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	protected abstract void checkPermission(
			long groupId, PermissionChecker permissionChecker)
		throws PortalException;

	protected JSONObject getImageJSONObject(ActionRequest actionRequest)
		throws Exception {

		throw new UnsupportedOperationException();
	}

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