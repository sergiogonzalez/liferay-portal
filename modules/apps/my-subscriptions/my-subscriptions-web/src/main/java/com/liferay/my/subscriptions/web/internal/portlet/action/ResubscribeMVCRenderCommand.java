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
import com.liferay.portal.kernel.exception.NoSuchSubscriptionException;
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
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UnsubscribePortlet.PORTLET_NAME,
		"mvc.command.name=" + ResubscribeMVCRenderCommand.COMMAND_NAME
	},
	service = MVCRenderCommand.class
)
public class ResubscribeMVCRenderCommand implements MVCRenderCommand {

	public static final String COMMAND_NAME = "/resubscribe";

	@Override
	public String render(RenderRequest request, RenderResponse response)
		throws PortletException {

		try {
			String key = ParamUtil.getString(request, "key");
			long userId = ParamUtil.getLong(request, "userId");

			Ticket ticket = _ticketLocalService.getTicket(key);

			Subscription subscription = _getFromTicket(ticket, userId);

			if (subscription == null) {
				subscription = _getFromSession(request, userId);

				Subscription newSubscription =
					_subscriptionLocalService.addSubscription(
						subscription.getUserId(), subscription.getGroupId(),
						subscription.getClassName(), subscription.getClassPK(),
						subscription.getFrequency());

				ticket.setClassPK(newSubscription.getSubscriptionId());

				_ticketLocalService.updateTicket(ticket);

				UnsubscribeUtil.clearSession(request);
			}

			request.setAttribute(
				"email",
				_userLocalService.getUser(
					subscription.getUserId()).getEmailAddress());
			request.setAttribute(
				"subscriptionTitle",
				MySubscriptionsUtil.getTitle(
					request.getLocale(), subscription));

			return "/unsubscribe/resubscribed.jsp";
		}
		catch (PortalException pe) {
			SessionErrors.add(request, pe.getClass(), pe);

			return "/unsubscribe/error.jsp";
		}
	}

	private Subscription _getFromSession(RenderRequest request, long userId)
		throws NoSuchSubscriptionException, PrincipalException {

		Subscription subscription = UnsubscribeUtil.getFromSession(request);

		if (subscription == null) {
			throw new NoSuchSubscriptionException();
		}

		UnsubscribeUtil.checkUser(userId, subscription);

		return subscription;
	}

	private Subscription _getFromTicket(Ticket ticket, long userId)
		throws PrincipalException {

		Subscription subscription = _subscriptionLocalService.fetchSubscription(
			ticket.getClassPK());

		UnsubscribeUtil.checkUser(userId, subscription);

		return subscription;
	}

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private TicketLocalService _ticketLocalService;

	@Reference
	private UserLocalService _userLocalService;

}