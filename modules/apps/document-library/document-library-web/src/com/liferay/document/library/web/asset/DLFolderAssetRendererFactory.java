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

package com.liferay.document.library.web.asset;

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"search.asset.type=com.liferay.portlet.documentlibrary.model.DLFolder"
	},
	service = AssetRendererFactory.class
)
public class DLFolderAssetRendererFactory extends BaseAssetRendererFactory {

	public static final String TYPE = "document_folder";

	public DLFolderAssetRendererFactory() {
		setCategorizable(false);
	}

	@Override
	public AssetRenderer getAssetRenderer(long classPK, int type)
		throws PortalException {

		Folder folder = DLAppLocalServiceUtil.getFolder(classPK);

		DLFolderAssetRenderer dlFolderAssetRenderer = new DLFolderAssetRenderer(
			folder);

		dlFolderAssetRenderer.setAssetRendererType(type);
		dlFolderAssetRenderer.setServletContext(_servletContext);

		return dlFolderAssetRenderer;
	}

	@Override
	public String getClassName() {
		return DLFolder.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "icon-folder-close";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLView(
		LiferayPortletResponse liferayPortletResponse,
		WindowState windowState) {

		LiferayPortletURL liferayPortletURL =
			liferayPortletResponse.createLiferayPortletURL(
				DLPortletKeys.DOCUMENT_LIBRARY_DISPLAY,
				PortletRequest.RENDER_PHASE);

		try {
			liferayPortletURL.setWindowState(windowState);
		}
		catch (WindowStateException wse) {
		}

		return liferayPortletURL;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		Folder folder = DLAppLocalServiceUtil.getFolder(classPK);

		return DLFolderPermission.contains(permissionChecker, folder, actionId);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/folder.png";
	}

	private ServletContext _servletContext;

}