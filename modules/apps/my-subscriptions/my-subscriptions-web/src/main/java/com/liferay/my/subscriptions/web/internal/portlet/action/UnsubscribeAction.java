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

package com.liferay.my.subscriptions.web.internal.portlet.action;

import com.liferay.my.subscriptions.web.internal.portlet.UnsubscribePortlet;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true, property = {"path=" + UnsubscribeAction.ACTION_NAME},
	service = StrutsAction.class
)
public class UnsubscribeAction extends BaseStrutsAction {

	public static final String ACTION_NAME = "/portal/unsubscribe";

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			request, UnsubscribePortlet.PORTLET_NAME,
			PortletRequest.RENDER_PHASE);

		liferayPortletURL.setWindowState(LiferayWindowState.MAXIMIZED);

		liferayPortletURL.setParameter(
			"mvcRenderCommandName", UnsubscribeMVCRenderCommand.COMMAND_NAME);

		liferayPortletURL.setParameter(
			"userId", request.getParameter("userId"));
		liferayPortletURL.setParameter("key", request.getParameter("key"));

		response.sendRedirect(liferayPortletURL.toString());

		return null;
	}

}