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
import com.liferay.my.subscriptions.web.internal.util.MySubscriptionsUtil;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.SubscriptionLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true, property = {"path=/portal/resubscribe"},
	service = StrutsAction.class
)
public class ResubscribeAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		Subscription subscription =
			(Subscription)request.getSession().getAttribute(
				MySubscriptionsPortletKeys.LAST_UNSUBSCRIBED_SUBSCRIPTION_KEY);

		if (subscription == null) {
			throw new Exception("There is no subscription in the session");
		}

		_subscriptionLocalService.addSubscription(
			subscription.getUserId(), subscription.getGroupId(),
			subscription.getClassName(), subscription.getClassPK(),
			subscription.getFrequency());

		request.getSession().removeAttribute(
			MySubscriptionsPortletKeys.LAST_UNSUBSCRIBED_SUBSCRIPTION_KEY);

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			request, MySubscriptionsPortletKeys.MY_SUBSCRIPTIONS,
			PortletRequest.RENDER_PHASE);

		liferayPortletURL.setParameter(
			"mvcRenderCommandName", "/mysubscriptions/resubscribed");

		liferayPortletURL.setParameter(
			"subscriptionTitle",
			MySubscriptionsUtil.getTitle(request.getLocale(), subscription));
		liferayPortletURL.setParameter(
			"email",
			_userLocalService.getUser(
				subscription.getUserId()).getEmailAddress());

		response.sendRedirect(liferayPortletURL.toString());

		return null;
	}

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}