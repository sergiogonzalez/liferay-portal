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

package com.liferay.document.library.web.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Adolfo Pérez
 */
public class RepositoryPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public RepositoryPortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "repository";
	}

	@Override
	public String getURL() {
		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletDisplay.getId(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_repository");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));

		return portletURL.toString();
	}

	@Override
	public boolean isShow() {
		User user = themeDisplay.getUser();

		if (user.isDefaultUser()) {
			return false;
		}

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		try {
			if (!DLPermission.contains(
					permissionChecker, themeDisplay.getScopeGroupId(),
					ActionKeys.ADD_REPOSITORY)) {

				return false;
			}
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

}