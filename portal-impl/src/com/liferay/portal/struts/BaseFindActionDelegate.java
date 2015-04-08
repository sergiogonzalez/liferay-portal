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

package com.liferay.portal.struts;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseFindActionDelegate implements FindActionDelegate {

	@Override
	public void execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public abstract long getGroupId(long primaryKey) throws Exception;

	@Override
	public abstract String getPrimaryKeyParameterName();

	@Override
	public abstract String getStrutsAction(
		HttpServletRequest request, String portletId);

	@Override
	public abstract String[] initPortletIds();

	@Override
	public PortletURL processPortletURL(
			HttpServletRequest request, PortletURL portletURL)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void setPrimaryKeyParameter(PortletURL portletURL, long primaryKey)
		throws Exception {

		throw new UnsupportedOperationException();
	}

}