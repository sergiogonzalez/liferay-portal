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

package com.liferay.document.library.opener.google.drive.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.opener.google.drive.service.GoogleDriveManager;
import com.liferay.document.library.opener.google.drive.web.internal.constants.GoogleDriveOpenerWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"mvc.command.name=/document_library/open_google_docs"
	},
	service = MVCRenderCommand.class
)
public class OpenGoogleDocsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			renderRequest.setAttribute(
				GoogleDriveOpenerWebKeys.GOOGLE_DRIVE_FILE_REFERENCE,
				_googleDriveManager.requestEditAccess(
					_dlAppService.getFileEntry(
						ParamUtil.getLong(renderRequest, "fileEntryId")),
					ServiceContextFactory.getInstance(renderRequest)));

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/open_google_docs.jsp");

			requestDispatcher.include(
				_portal.getHttpServletRequest(renderRequest),
				_portal.getHttpServletResponse(renderResponse));

			return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
		}
		catch (IOException | PortalException | ServletException e) {
			throw new PortletException(e);
		}
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private GoogleDriveManager _googleDriveManager;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.opener.google.drive.web)"
	)
	private ServletContext _servletContext;

}