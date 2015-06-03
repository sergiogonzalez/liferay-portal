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

package com.liferay.portlet.mvc.util;

import com.liferay.portlet.mvc.MVCPortletAction;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Iván Zaera
 */
public class ForwardMVCPortletAction implements MVCPortletAction {

	public ForwardMVCPortletAction(String renderPath) {
		this(renderPath, null, null);
	}

	public ForwardMVCPortletAction(
		String renderPath, String serveResourcePath) {

		this(renderPath, serveResourcePath, null);
	}

	public ForwardMVCPortletAction(
		String renderPath, String serveResourcePath, String processActionPath) {

		_renderPath = renderPath;
		_serveResourcePath = serveResourcePath;
		_processActionPath = processActionPath;
	}

	@Override
	public String processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		return _processActionPath;
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		return _renderPath;
	}

	@Override
	public String serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		return _serveResourcePath;
	}

	private final String _processActionPath;
	private final String _renderPath;
	private final String _serveResourcePath;

}