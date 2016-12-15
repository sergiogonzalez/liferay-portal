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
import com.liferay.portal.kernel.exception.NoSuchTicketException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.SubscriptionLocalService;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		Subscription subscription = _unsubscribe(key, userId);

		_saveSubscriptionToSession(request, subscription);

		response.sendRedirect(_getSuccessURL(subscription, userId, request));

		return null;
	}

	private String _getSuccessURL(
			Subscription subscription, long userId, HttpServletRequest request)
		throws IOException, PortalException, PortletException {

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			request, MySubscriptionsPortletKeys.MY_SUBSCRIPTIONS,
			PortletRequest.RENDER_PHASE);

		liferayPortletURL.setWindowState(LiferayWindowState.MAXIMIZED);
		liferayPortletURL.setPortletMode(PortletMode.VIEW);

		liferayPortletURL.setParameter(
			"mvcRenderCommandName", "/mysubscriptions/unsubscribed");
		liferayPortletURL.setParameter(
			"subscriptionTitle",
			MySubscriptionsUtil.getTitle(request.getLocale(), subscription));
		liferayPortletURL.setParameter(
			"email", _userLocalService.getUser(userId).getEmailAddress());

		return liferayPortletURL.toString();
	}

	private void _saveSubscriptionToSession(
		HttpServletRequest request, Subscription subscription) {

		request.getSession().setAttribute(
			MySubscriptionsPortletKeys.LAST_UNSUBSCRIBED_SUBSCRIPTION_KEY,
			subscription);
	}

	private Subscription _unsubscribe(String key, long userId)
		throws PortalException {

		Ticket ticket = _ticketLocalService.getTicket(key);

		if (ticket.isExpired()) {
			_ticketLocalService.deleteTicket(ticket);
			throw new NoSuchTicketException("{ticketKey=" + key + "}");
		}

		long subscriptionId = Long.valueOf(ticket.getExtraInfo());

		Subscription subscription = _subscriptionLocalService.getSubscription(
			subscriptionId);

		if (subscription.getUserId() != userId) {
			throw new PrincipalException();
		}

		_subscriptionLocalService.deleteSubscription(subscription);
		_ticketLocalService.deleteTicket(ticket);

		return subscription;
	}

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private TicketLocalService _ticketLocalService;

	@Reference
	private UserLocalService _userLocalService;

}