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
import com.liferay.my.subscriptions.web.internal.portlet.UnsubscribePortlet;
import com.liferay.my.subscriptions.web.internal.util.MySubscriptionsUtil;
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

			Subscription subscription =
				(Subscription)request.getPortletSession().getAttribute(
					MySubscriptionsPortletKeys.
						LAST_UNSUBSCRIBED_SUBSCRIPTION_KEY);

			if (subscription == null) {
				throw new NoSuchSubscriptionException();
			}

			if (subscription.getUserId() != userId) {
				throw new PrincipalException();
			}

			Ticket ticket = _ticketLocalService.getTicket(key);

			Subscription newSubscription =
				_subscriptionLocalService.addSubscription(
					subscription.getUserId(), subscription.getGroupId(),
					subscription.getClassName(), subscription.getClassPK(),
					subscription.getFrequency());

			ticket.setClassPK(newSubscription.getSubscriptionId());
			_ticketLocalService.updateTicket(ticket);

			request.getPortletSession().removeAttribute(
				MySubscriptionsPortletKeys.LAST_UNSUBSCRIBED_SUBSCRIPTION_KEY);

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

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private TicketLocalService _ticketLocalService;

	@Reference
	private UserLocalService _userLocalService;

}