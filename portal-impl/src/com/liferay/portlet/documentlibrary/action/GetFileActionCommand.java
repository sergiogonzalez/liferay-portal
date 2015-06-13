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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseActionCommand;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Charles May
 * @author Bruno Farache
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.DOCUMENT_LIBRARY,
		"render.command.name=/document_library/get_file"
	},
	service = ActionCommand.class
)
public class GetFileActionCommand extends BaseActionCommand {

	@Override
	protected void doProcessCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			portletResponse);

		_getFileActionHelper.processRequest(request, response);
	}

	private final GetFileActionHelper _getFileActionHelper =
		new GetFileActionHelper();

}