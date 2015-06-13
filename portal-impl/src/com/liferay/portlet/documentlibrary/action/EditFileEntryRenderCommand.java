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

import com.liferay.portal.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.portlet.bridges.mvc.RenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Sergio González
 * @author Manuel de la Peña
 * @author Levente Hudák
 * @author Kenneth Chang
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.DOCUMENT_LIBRARY,
		"render.command.name=/document_library/edit_file_entry",
		"render.command.name=/document_library/upload_file_entry",
		"render.command.name=/document_library/upload_multiple_file_entries",
		"render.command.name=/document_library/view_file_entry"
	},
	service = RenderCommand.class
)
public class EditFileEntryRenderCommand implements RenderCommand {

	@Override
	public String processCommand(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			ActionUtil.getFileEntry(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileEntryException ||
				e instanceof NoSuchFileVersionException ||
				e instanceof NoSuchRepositoryEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/html/portlet/document_library/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/html/portlet/document_library/edit_file_entry.jsp";
	}

}