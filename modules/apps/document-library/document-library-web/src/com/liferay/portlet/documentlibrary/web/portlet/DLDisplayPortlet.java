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
		"com.liferay.portlet.css-class-wrapper=portlet-document-library-display",
		"com.liferay.portlet.header-portlet-css=/document_library/css/main.css",
		"com.liferay.portlet.header-portlet-css=/document_library_display/css/main.css",
		"com.liferay.portlet.icon=/icons/document_library_display.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.use-default-template=true",
		"com.liferay.portlet.webdav-storage-token=document_library",
		"javax.portlet.display-name=Documents and Media Display",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-template=/document_library_display/configuration.jsp",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-action=/document_library_display/view",
		"javax.portlet.name=" + DLWebKeys.DOCUMENT_LIBRARY_DISPLAY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supported-public-render-parameter=categoryId",
		"javax.portlet.supported-public-render-parameter=resetCur",
		"javax.portlet.supported-public-render-parameter=tag",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {DLDisplayPortlet.class, Portlet.class}
)
public class DLDisplayPortlet extends DLPortlet {

	@Override
	public void init() throws PortletException {
		super.init();

		registerMVCPortletAction(
			new ForwardMVCPortletAction("/document_library_display/search.jsp"),
			"/document_library_display/search");

		registerMVCPortletAction(
			new ForwardMVCPortletAction(
				"/document_library_display/select_add_file_entry_type.jsp"),
			"/document_library_display/select_add_file_entry_type");

		registerMVCPortletAction(
			new ViewAction(
				"/document_library/error.jsp",
				"/document_library_display/view.jsp"),
			"/document_library_display/view", StringPool.BLANK);
	}

}