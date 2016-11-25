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

import com.liferay.my.subscriptions.web.internal.constants.MySubscriptionsPortletKeys;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.util.ParamUtil;
import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	immediate = true, property = {"path=/portal/unsubscribe"},
	service = StrutsAction.class
)
public class UnsubscribeAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String key = ParamUtil.getString(request, "key");
		long userId = ParamUtil.getLong(request, "userId");

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			request, MySubscriptionsPortletKeys.MY_SUBSCRIPTIONS,
			PortletRequest.RENDER_PHASE);

		liferayPortletURL.setParameter("key", key);
		liferayPortletURL.setParameter("userId", String.valueOf(userId));

		response.sendRedirect(liferayPortletURL.toString());

		return null;
	}

}