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

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.util.PortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Iván Zaera
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.DOCUMENT_LIBRARY,
		"mvc.command.name=/document_library/edit_file_entry",
		"mvc.command.name=/document_library/upload_file_entry",
		"mvc.command.name=/document_library/upload_multiple_file_entries",
		"mvc.command.name=/document_library/view_file_entry"
	},
	service = MVCResourceCommand.class
)
public class EditFileEntryMVCResourceCommand extends BaseMVCResourceCommand
	implements MVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		include(
			resourceRequest, resourceResponse,
			"/html/portlet/document_library/" +
				"upload_multiple_file_entries_resources.jsp");
	}

}