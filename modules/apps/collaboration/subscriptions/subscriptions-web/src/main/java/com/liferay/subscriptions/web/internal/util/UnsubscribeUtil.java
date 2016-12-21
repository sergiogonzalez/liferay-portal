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

package com.liferay.subscriptions.web.internal.util;

import com.liferay.portal.kernel.exception.NoSuchTicketException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.RenderRequest;

/**
 * @author Alejandro Tardín
 */
public class UnsubscribeUtil {

	public static void checkUser(long userId, RenderRequest request)
		throws PortalException {

		User user = PortalUtil.getUser(request);

		if ((user != null) && (userId != user.getUserId())) {
			throw new PrincipalException();
		}
	}

	public static void checkUser(long userId, Subscription subscription)
		throws PrincipalException {

		if ((subscription != null) && (subscription.getUserId() != userId)) {
			throw new PrincipalException();
		}
	}

	public static void clearSession(RenderRequest request) {
		request.getPortletSession().removeAttribute(
			_LAST_UNSUBSCRIBED_SUBSCRIPTION_KEY);
	}

	public static Subscription getFromSession(RenderRequest request) {
		return (Subscription)request.getPortletSession().getAttribute(
			_LAST_UNSUBSCRIBED_SUBSCRIPTION_KEY);
	}

	public static Ticket getTicket(TicketLocalService service, String key)
		throws PortalException {

		Ticket ticket = service.getTicket(key);

		if (ticket.getType() != TicketConstants.TYPE_SUBSCRIPTIONS) {
			throw new NoSuchTicketException("Invalid type " + ticket.getType());
		}

		return ticket;
	}

	public static void saveToSession(
		RenderRequest request, Subscription subscription) {

		request.getPortletSession().setAttribute(
			_LAST_UNSUBSCRIBED_SUBSCRIPTION_KEY, subscription);
	}

	private static final String _LAST_UNSUBSCRIBED_SUBSCRIPTION_KEY =
		"com_liferay_mysubscriptions_web_portlet_MySubscriptionsPortlet_" +
			"LastUnsubscribedSubscription";

}