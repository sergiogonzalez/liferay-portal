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

package com.liferay.message.boards.web.portlet;

import com.liferay.message.boards.web.constants.MessageBoardsPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.control-panel-entry-category=site_administration.content",
		"com.liferay.portlet.control-panel-entry-weight=6.0",
		"com.liferay.portlet.css-class-wrapper=portlet-message-boards",
		"com.liferay.portlet.footer-portlet-javascript=/message_boards/js/main.js",
		"com.liferay.portlet.header-portlet-css=/message_boards/css/main.css",
		"com.liferay.portlet.icon=/icons/message_boards.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.struts-path=message_boards",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Message Boards Admim",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-template=/message_boards_admin/configuration.jsp",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/message_boards/view",
		"javax.portlet.name=" + MessageBoardsPortletKeys.MESSAGE_BOARDS_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class MessageBoardsAdminPortlet extends MVCPortlet {
}