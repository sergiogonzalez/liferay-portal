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
import com.liferay.my.subscriptions.web.internal.util.MySubscriptionsUtil;
import com.liferay.my.subscriptions.web.internal.util.UnsubscribeUtil;
import com.liferay.portal.kernel.exception.NoSuchTicketException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
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
		"javax.portlet.name=" + UnsubscribePortlet.PORTLET_NAME,
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
			Subscription subscription = _unsubscribe(key, userId, request);

			request.setAttribute(
				"email", _userLocalService.getUser(userId).getEmailAddress());
			request.setAttribute(
				"subscriptionTitle",
				MySubscriptionsUtil.getTitle(
					request.getLocale(), subscription));

			return "/unsubscribe/unsubscribed.jsp";
		}
		catch (PortalException pe) {
			SessionErrors.add(request, pe.getClass(), pe);

			return "/unsubscribe/error.jsp";
		}
	}

	private Subscription _unsubscribe(
			String key, long userId, RenderRequest request)
		throws PortalException {

		Ticket ticket = UnsubscribeUtil.getTicket(_ticketLocalService, key);

		long subscriptionId = ticket.getClassPK();

		Subscription subscription = UnsubscribeUtil.getFromSession(request);

		if ((subscription != null) &&
			(subscription.getSubscriptionId() == subscriptionId)) {

			return subscription;
		}

		if (ticket.isExpired()) {
			_ticketLocalService.deleteTicket(ticket);
			throw new NoSuchTicketException("{ticketKey=" + key + "}");
		}

		subscription = _subscriptionLocalService.getSubscription(
			subscriptionId);

		UnsubscribeUtil.checkUser(userId, subscription);

		_subscriptionLocalService.deleteSubscription(subscription);

		UnsubscribeUtil.saveToSession(request, subscription);

		return subscription;
	}

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private TicketLocalService _ticketLocalService;

	@Reference
	private UserLocalService _userLocalService;

}