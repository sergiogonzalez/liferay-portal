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

package com.liferay.blogs.portlet.toolbar.contributor;

import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.portal.kernel.portlet.toolbar.contributor.BasePortletToolbarContributor;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourcePermissionChecker;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS,
		"mvc.render.command.name=-", "mvc.render.command.name=/blogs/view"
	},
	service = {
		BlogsPortletToolbarContributor.class, PortletToolbarContributor.class
	}
)
public class BlogsPortletToolbarContributor
	extends BasePortletToolbarContributor {

	protected void addPortletTitleMenuItem(
		List<MenuItem> menuItems, PortletRequest portletRequest,
		ThemeDisplay themeDisplay) {

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIcon("icon-plus-sign-2");

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, BlogsPortletKeys.BLOGS, themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "/blogs/edit_entry");

		String currentURL = PortalUtil.getCurrentURL(portletRequest);

		portletURL.setParameter("redirect", currentURL);
		portletURL.setParameter("backURL", currentURL);

		urlMenuItem.setURL(portletURL.toString());

		menuItems.add(urlMenuItem);
	}

	@Override
	protected List<MenuItem> getPortletTitleMenuItems(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!_resourcePermissionChecker.checkResource(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_ENTRY)) {

			return Collections.emptyList();
		}

		List<MenuItem> menuItems = new ArrayList<>();

		addPortletTitleMenuItem(menuItems, portletRequest, themeDisplay);

		return menuItems;
	}

	@Reference(
		target = "(resource.name=com.liferay.portlet.blogs)", unbind = "-"
	)
	protected void setResourcePermissionChecker(
		ResourcePermissionChecker resourcePermissionChecker) {

		_resourcePermissionChecker = resourcePermissionChecker;
	}

	private ResourcePermissionChecker _resourcePermissionChecker;

}