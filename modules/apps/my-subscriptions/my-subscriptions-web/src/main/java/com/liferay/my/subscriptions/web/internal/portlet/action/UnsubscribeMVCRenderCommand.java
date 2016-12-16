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
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.SubscriptionLocalService;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + MySubscriptionsPortletKeys.MY_SUBSCRIPTIONS,
		"mvc.command.name=" + UnsubscribeMVCRenderCommand.COMMAND_NAME
	},
	service = MVCRenderCommand.class
)
public class UnsubscribeMVCRenderCommand implements MVCRenderCommand {

	public static final String COMMAND_NAME = "/unsubscribe";

	@Override
	public String render(RenderRequest request, RenderResponse response)
		throws PortletException {

		String key = ParamUtil.getString(request, "key");
		long userId = ParamUtil.getLong(request, "userId");

		try {
			Subscription subscription = _unsubscribe(key, userId);

			_saveSubscriptionToSession(request, subscription);

			return _returnSuccess(subscription, userId, request);
		}
		catch (PortalException pe) {
			SessionErrors.add(request, pe.getClass(), pe);

			return "/error.jsp";
		}
	}

	private String _returnSuccess(
			Subscription subscription, long userId, RenderRequest request)
		throws PortalException {

		request.setAttribute(
			"email", _userLocalService.getUser(userId).getEmailAddress());
		request.setAttribute(
			"subscriptionTitle",
			MySubscriptionsUtil.getTitle(request.getLocale(), subscription));

		return "/unsubscribed.jsp";
	}

	private void _saveSubscriptionToSession(
		RenderRequest request, Subscription subscription) {

		request.getPortletSession().setAttribute(
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