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

package com.liferay.blogs.toolbar.item;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.toolbar.item.PortletToolbarMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.ResourcePermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(property = {"javax.portlet.name=33", "struts.action="})
public class BlogsPortletToolbarItem  implements PortletToolbarMenuItem {

	@Override
	public MenuItem getMenuItem(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!_resourcePermissionChecker.checkResource(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_ENTRY)) {

			return null;
		}

		PortletURL addEntry = PortletURLFactoryUtil.create(
			portletRequest, PortletKeys.BLOGS, themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		addEntry.setParameter("struts_action", "/blogs/edit_entry");

		String currentURL = PortalUtil.getCurrentURL(portletRequest);

		addEntry.setParameter("redirect", currentURL);
		addEntry.setParameter("backURL", currentURL);

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setURL(addEntry.toString());
		urlMenuItem.setLabel(
			LanguageUtil.get(themeDisplay.getLocale(), "add-entry"));

		return urlMenuItem;
	}

	@Reference(target = "resource.name=com.liferay.portlet.blogs", unbind = "-")
	protected void setResourcePermissionChecker(
		ResourcePermissionChecker resourcePermissionChecker) {

		_resourcePermissionChecker = resourcePermissionChecker;
	}

	ResourcePermissionChecker _resourcePermissionChecker;

}