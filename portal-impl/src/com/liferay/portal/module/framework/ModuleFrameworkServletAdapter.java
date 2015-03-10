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

package com.liferay.portal.module.framework;

import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.registry.collections.ServiceTrackerCollections;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 */
public class ModuleFrameworkServletAdapter extends HttpServlet {

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		if (_servlets.isEmpty()) {
			PortalUtil.sendError(
				HttpServletResponse.SC_SERVICE_UNAVAILABLE,
				new ServletException("Module framework is unavailable"),
				request, response);

			return;
		}

		HttpServlet httpServlet = _servlets.get(0);

		boolean principalSet = false;

		try {
			principalSet = _setPrincipal(request);

			httpServlet.service(request, response);
		}
		finally {
			if (principalSet) {
				PermissionThreadLocal.setPermissionChecker(null);

				PrincipalThreadLocal.setName(null);
			}
		}
	}

	private boolean _setPrincipal(HttpServletRequest request)
		throws ServletException {

		String requestURI = request.getRequestURI();

		if (!requestURI.startsWith("/o/")) {
			return false;
		}

		try {
			if (PrincipalThreadLocal.getName() == null) {
				User user = PortalUtil.getUser(request);

				if (user != null) {
					PrincipalThreadLocal.setName(user.getUserId());

					PermissionThreadLocal.setPermissionChecker(
						PermissionCheckerFactoryUtil.create(user));

					return true;
				}
			}

			return false;
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private final List<HttpServlet> _servlets = ServiceTrackerCollections.list(
		HttpServlet.class,
		"(&(bean.id=" + HttpServlet.class.getName() + ")(original.bean=*))");

}