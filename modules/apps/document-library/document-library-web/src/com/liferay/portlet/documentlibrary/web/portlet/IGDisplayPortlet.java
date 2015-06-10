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

package com.liferay.portlet.documentlibrary.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.action.ForwardMVCPortletAction;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.documentlibrary.action.EditFileEntryAction;
import com.liferay.portlet.documentlibrary.action.EditFileShortcutAction;
import com.liferay.portlet.documentlibrary.action.EditFolderAction;
import com.liferay.portlet.documentlibrary.action.ViewAction;
import com.liferay.portlet.documentlibrary.web.constants.DLWebKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-image-gallery-display",
		"com.liferay.portlet.footer-portlet-javascript=/document_library/js/main.js",
		"com.liferay.portlet.footer-portlet-javascript=/document_library/js/upload.js",
		"com.liferay.portlet.header-portlet-css=/image_gallery_display/css/main.css",
		"com.liferay.portlet.header-portlet-css=/document_library/css/main.css",
		"com.liferay.portlet.icon=/icons/image_gallery.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.use-default-template=true",
		"com.liferay.portlet.webdav-storage-token=document_library",
		"javax.portlet.display-name=Media Gallery",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-template=/image_gallery_display/configuration.jsp",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-action=/image_gallery_display/view",
		"javax.portlet.name=" + DLWebKeys.IMAGE_GALLERY_DISPLAY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supported-public-render-parameter=categoryId",
		"javax.portlet.supported-public-render-parameter=resetCur",
		"javax.portlet.supported-public-render-parameter=tag",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {IGDisplayPortlet.class, Portlet.class}
)
public class IGDisplayPortlet extends DLPortlet {

	@Override
	public void init() throws PortletException {
		super.init();

		registerMVCPortletAction(
			new EditFileEntryAction(
				this, "/html/portlet/document_library/edit_file_entry.jsp"),
			"/image_gallery_display/edit_image");

		registerMVCPortletAction(
			new EditFileEntryAction(
				this, "/html/portlet/document_library/view_file_entry.jsp"),
			"/image_gallery_display/view_image");

		registerMVCPortletAction(
			new EditFileShortcutAction(),
			"/image_gallery_display/edit_shortcut");

		registerMVCPortletAction(
			new EditFolderAction(
				"/html/portlet/document_library/edit_folder.jsp"),
			"/image_gallery_display/edit_folder");

		registerMVCPortletAction(
			new EditFolderAction(
				"/html/portlet/document_library/select_folder.jsp"),
			"/image_gallery_display/select_folder");

		registerMVCPortletAction(
			new ForwardMVCPortletAction(
				"/html/portlet/image_gallery_display/embedded_player.jsp"),
			"/image_gallery_display/embedded_player");

		registerMVCPortletAction(
			new ForwardMVCPortletAction(
				"/html/portlet/image_gallery_display/search.jsp"),
			"/image_gallery_display/search");

		registerMVCPortletAction(
			new ForwardMVCPortletAction(
				"/html/portlet/document_library_display/" +
					"select_add_file_entry_type.jsp"),
			"/image_gallery_display/select_add_file_entry_type");

		registerMVCPortletAction(
			new ViewAction(
				"/html/portlet/image_gallery_display/error.jsp",
				"/html/portlet/image_gallery_display/view.jsp"),
			"/image_gallery_display/view", StringPool.BLANK);
	}

}