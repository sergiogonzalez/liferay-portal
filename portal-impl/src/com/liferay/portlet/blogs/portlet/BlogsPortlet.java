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

package com.liferay.portlet.blogs.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.blogs.NoSuchEntryException;
import com.liferay.portlet.blogs.action.ActionUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class BlogsPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			ActionUtil.getEntry(renderRequest);

			if (PropsValues.BLOGS_PINGBACK_ENABLED) {
				BlogsEntry entry = (BlogsEntry)renderRequest.getAttribute(
					WebKeys.BLOGS_ENTRY);

				if ((entry != null) && entry.isAllowPingbacks()) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(renderResponse);

					response.addHeader(
						"X-Pingback",
						PortalUtil.getPortalURL(renderRequest) +
							"/xmlrpc/pingback");
				}
			}

			long assetCategoryId = ParamUtil.getLong(
				renderRequest, "categoryId");
			String assetCategoryName = ParamUtil.getString(
				renderRequest, "tag");

			if ((assetCategoryId > 0) ||
				Validator.isNotNull(assetCategoryName)) {

				include(
					"/html/portlet/blogs/view.jsp", renderRequest,
					renderResponse);

				return;
			}

			super.render(renderRequest, renderResponse);
		}
		catch (NoSuchEntryException | PrincipalException e) {
			SessionErrors.add(renderRequest, e.getClass());

			include(
				"/html/portlet/blogs/error.jsp", renderRequest, renderResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof NoSuchEntryException ||
			cause instanceof PrincipalException) {

			return true;
		}

		return false;
	}

}