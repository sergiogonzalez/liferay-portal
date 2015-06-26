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

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseJSPMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.util.PortletKeys;

/**
 * @author Iván Zaera
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + PortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + PortletKeys.DOCUMENT_LIBRARY_DISPLAY,
		"javax.portlet.name=" + PortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/upload_file_entry"
	},
	service = MVCResourceCommand.class
)
public class UploadFileEntryMVCResourceCommand extends BaseJSPMVCResourceCommand
	implements MVCResourceCommand {

	public UploadFileEntryMVCResourceCommand() {
		super("/html/portlet/document_library/upload_file_entry.jsp");
	}

}