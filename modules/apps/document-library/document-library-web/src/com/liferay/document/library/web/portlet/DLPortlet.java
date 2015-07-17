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

package com.liferay.document.library.web.portlet;

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.autopropagated-parameters=showMountFolder",
		"com.liferay.portlet.css-class-wrapper=portlet-document-library",
		"com.liferay.portlet.display-category=category.cms",
		"com.liferay.portlet.footer-portlet-javascript=/o/ddm-web/js/main.js",
		"com.liferay.portlet.footer-portlet-javascript=/o/ddm-web/js/custom_fields.js",
		"com.liferay.portlet.footer-portlet-javascript=/html/portlet/document_library/js/main.js",
		"com.liferay.portlet.footer-portlet-javascript=/html/portlet/document_library/js/upload.js",
		"com.liferay.portlet.header-portlet-css=/html/portlet/document_library/css/main.css",
		"com.liferay.portlet.header-portlet-css=/o/ddm-web/css/main.css",
		"com.liferay.portlet.icon=/document_library/icons/document_library.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.use-default-template=true",
		"com.liferay.portlet.webdav-storage-token=document_library",
		"javax.portlet.display-name=Documents and Media",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.single-page-application-cacheable=false",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/document_library/view.jsp",
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supported-public-render-parameter=categoryId",
		"javax.portlet.supported-public-render-parameter=resetCur",
		"javax.portlet.supported-public-render-parameter=tag",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class DLPortlet extends MVCPortlet {
}