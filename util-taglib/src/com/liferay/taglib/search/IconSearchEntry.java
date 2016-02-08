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

package com.liferay.taglib.search;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.io.Writer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 */
public class IconSearchEntry extends TextSearchEntry {

	public static String getPage() {
		return _PAGE;
	}

	@Override
	public Object clone() {
		IconSearchEntry iconSearchEntry = new IconSearchEntry();

		BeanPropertiesUtil.copyProperties(this, iconSearchEntry);

		return iconSearchEntry;
	}

	@Override
	public String getHref() {
		return _href;
	}

	public String getIcon() {
		return _icon;
	}

	public HttpServletRequest getRequest() {
		return _request;
	}

	public HttpServletResponse getResponse() {
		return _response;
	}

	public ServletContext getServletContext() {
		if (_servletContext == null) {
			return ServletContextPool.get(PortalUtil.getServletContextName());
		}

		return _servletContext;
	}

	public boolean isToggleRowChecker() {
		return _toggleRowChecker;
	}

	@Override
	public void print(
			Writer writer, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		request.setAttribute(
			"liferay-ui:search-container-column-icon:href", _href);
		request.setAttribute(
			"liferay-ui:search-container-column-icon:icon", _icon);
		request.setAttribute(
			"liferay-ui:search-container-column-icon:toggleRowChecker",
			_toggleRowChecker);

		RequestDispatcher requestDispatcher =
			DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
				getServletContext(), _PAGE);

		requestDispatcher.include(
			request, new PipingServletResponse(response, writer));
	}

	@Override
	public void setHref(String href) {
		_href = href;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setRequest(HttpServletRequest request) {
		_request = request;
	}

	public void setResponse(HttpServletResponse response) {
		_response = response;
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	public void setToggleRowChecker(boolean toggleRowChecker) {
		_toggleRowChecker = toggleRowChecker;
	}

	private static final String _PAGE =
		"/html/taglib/ui/search_container/icon.jsp";

	private String _href;
	private String _icon;
	private HttpServletRequest _request;
	private HttpServletResponse _response;
	private ServletContext _servletContext;
	private boolean _toggleRowChecker = false;

}