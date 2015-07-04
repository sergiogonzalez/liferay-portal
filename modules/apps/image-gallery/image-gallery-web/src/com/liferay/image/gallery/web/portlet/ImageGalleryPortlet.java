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

package com.liferay.image.gallery.web.portlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.util.PortletKeys;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.use-default-template=false",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.css-class-wrapper=portlet-image-gallery-display",
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.icon=/icons/image_gallery.png",
		"com.liferay.portlet.webdav-storage-token=document_library",
		"com.liferay.portlet.display-category=category.cms",
		"javax.portlet.display-name=Image Gallery",
		"javax.portlet.name=" + PortletKeys.MEDIA_GALLERY_DISPLAY,
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.init-param.config-template=/configuration.jsp",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.supports.mime-type=text/html",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.supported-public-render-parameter=categoryId",
		"javax.portlet.supported-public-render-parameter=resetCur",
		"javax.portlet.supported-public-render-parameter=tag"
	},
	service = Portlet.class
)
public class ImageGalleryPortlet extends MVCPortlet {

}
