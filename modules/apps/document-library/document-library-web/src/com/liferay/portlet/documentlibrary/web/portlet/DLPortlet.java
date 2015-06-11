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

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.action.ForwardMVCPortletAction;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.documentlibrary.action.CompareVersionsAction;
import com.liferay.portlet.documentlibrary.action.EditEntryAction;
import com.liferay.portlet.documentlibrary.action.EditFileEntryAction;
import com.liferay.portlet.documentlibrary.action.EditFileEntryTypeAction;
import com.liferay.portlet.documentlibrary.action.EditFileShortcutAction;
import com.liferay.portlet.documentlibrary.action.EditFolderAction;
import com.liferay.portlet.documentlibrary.action.EditRepositoryAction;
import com.liferay.portlet.documentlibrary.action.GetFileAction;
import com.liferay.portlet.documentlibrary.action.SearchAction;
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
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.autopropagated-parameters=showMountFolder",
		"com.liferay.portlet.css-class-wrapper=portlet-document-library",
		"com.liferay.portlet.footer-portlet-javascript=/o/ddm-web/js/main.js",
		"com.liferay.portlet.footer-portlet-javascript=/o/ddm-web/js/custom_fields.js",
		"com.liferay.portlet.header-portlet-css=/document_library/css/main.css",
		"com.liferay.portlet.header-portlet-css=/o/ddm-web/css/main.css",
		"com.liferay.portlet.header-portlet-javascript=/document_library/js/main.js",
		"com.liferay.portlet.header-portlet-javascript=/document_library/js/upload.js",
		"com.liferay.portlet.icon=/icons/document_library.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.use-default-template=true",
		"com.liferay.portlet.webdav-storage-token=document_library",
		"javax.portlet.display-name=Documents and Media",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-template=/document_library/configuration.jsp",
		"javax.portlet.init-param.single-page-application-cacheable=false",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-action=/document_library/view",
		"javax.portlet.name=" + DLWebKeys.DOCUMENT_LIBRARY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supported-public-render-parameter=categoryId",
		"javax.portlet.supported-public-render-parameter=resetCur",
		"javax.portlet.supported-public-render-parameter=tag",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {DLPortlet.class, Portlet.class}
)
public class DLPortlet extends MVCPortlet {

	public DLPortlet() {
		super("struts_action");
	}

	@Override
	public void init() throws PortletException {
		super.init();

		registerMVCPortletAction(
			new CompareVersionsAction(), "/document_library/compare_versions");

		registerMVCPortletAction(
			new EditEntryAction(), "/document_library/edit_entry",
			"/document_library/move_entry");

		registerMVCPortletAction(
			new EditFileEntryAction(
				this, "/document_library/edit_file_entry.jsp"),
			"/document_library/edit_file_entry");

		registerMVCPortletAction(
			new EditFileEntryTypeAction(),
			"/document_library/edit_file_entry_type");

		registerMVCPortletAction(
			new EditFileEntryAction(
				this, "/document_library/upload_multiple_file_entries.jsp"),
			"/document_library/upload_multiple_file_entries");

		registerMVCPortletAction(
			new EditFileEntryAction(
				this, "/document_library/view_file_entry.jsp"),
			"/document_library/view_file_entry");

		registerMVCPortletAction(
			new EditFileEntryAction(
				this, "/document_library/view_file_entry.jsp"),
			"/document_library/upload_file_entry");

		registerMVCPortletAction(
			new EditFileShortcutAction(),
			"/document_library/edit_file_shortcut");

		registerMVCPortletAction(
			new EditFolderAction("/document_library/edit_folder.jsp"),
			"/document_library/edit_folder");

		registerMVCPortletAction(
			new EditFolderAction("/document_library/select_folder.jsp"),
			"/document_library/select_folder");

		registerMVCPortletAction(
			new EditRepositoryAction(), "/document_library/edit_repository");

		registerMVCPortletAction(
			new ForwardMVCPortletAction("/document_library/select_group.jsp"),
			"/document_library/select_group");

		registerMVCPortletAction(
			new ForwardMVCPortletAction(
				"/document_library/select_restricted_file_entry_type.jsp"),
			"/document_library/select_restricted_file_entry_type");

		registerMVCPortletAction(
			new ForwardMVCPortletAction(
				"/document_library/view_file_entry_type.jsp"),
			"/document_library/view_file_entry_type");

		registerMVCPortletAction(
			new GetFileAction(), "/document_library/get_file");

		registerMVCPortletAction(
			new SearchAction(), "/document_library/search");

		registerMVCPortletAction(
			new ViewAction(
				"/document_library/error.jsp",
				"/document_library/select_file_entry.jsp"),
			"/document_library/select_file_entry");

		registerMVCPortletAction(
			new ViewAction(
				"/document_library/error.jsp", "/document_library/view.jsp"),
			"/document_library/view", StringPool.BLANK);
	}

}