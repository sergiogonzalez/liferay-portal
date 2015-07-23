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

package com.liferay.portlet.usersadmin.action;

import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.security.auth.PrincipalException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Pei-Jung Lan
 */
public abstract class GetOrganizationMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			ActionUtil.getOrganization(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchOrganizationException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/html/portlet/users_admin/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return getPath();
	}

	protected abstract String getPath();

}