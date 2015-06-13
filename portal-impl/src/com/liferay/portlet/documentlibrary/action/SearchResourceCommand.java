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

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.ResourceCommand;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.util.PortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Sergio González
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.DOCUMENT_LIBRARY,
		"render.command.name=/document_library/search"
	},
	service = ResourceCommand.class
)
public class SearchResourceCommand extends BaseResourceCommand {

	@Override
	protected void doProcessCommand(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		include(
			resourceRequest, resourceResponse,
			"/html/portlet/document_library/search_resources.jsp");
	}

}