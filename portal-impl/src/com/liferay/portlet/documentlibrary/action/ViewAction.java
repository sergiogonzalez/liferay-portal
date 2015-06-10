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

import com.liferay.portal.kernel.portlet.bridges.mvc.action.ActionContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.action.MVCPortletAction;
import com.liferay.portal.kernel.portlet.bridges.mvc.action.RenderContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.action.ResourceContext;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Sergio González
 */
public class ViewAction implements MVCPortletAction {

	public ViewAction(String errorJsp, String viewJsp) {
		_errorJsp = errorJsp;
		_viewJsp = viewJsp;
	}

	@Override
	public String processAction(
			ActionRequest actionRequest, ActionResponse actionResponse,
			ActionContext actionContext)
		throws PortletException {

		return null;
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			RenderContext renderContext)
		throws IOException, PortletException {

		try {
			ActionUtil.getFolder(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFolderException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return _errorJsp;
			}
			else {
				throw new PortletException(e);
			}
		}

		return _viewJsp;
	}

	@Override
	public String serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			ResourceContext resourceContext)
		throws IOException, PortletException {

		return null;
	}

	private final String _errorJsp;
	private final String _viewJsp;

}