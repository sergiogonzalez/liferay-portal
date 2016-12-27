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

package com.liferay.subscriptions.web.util;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.template.MailTemplate;
import com.liferay.mail.kernel.template.MailTemplateContext;
import com.liferay.mail.kernel.template.MailTemplateContextBuilder;
import com.liferay.mail.kernel.template.MailTemplateFactoryUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.subscriptions.configuration.SubscriptionsConfiguration;
import com.liferay.subscriptions.util.UnsubscribeLifecycleHook;
import com.liferay.subscriptions.web.internal.portlet.action.UnsubscribeAction;

import java.io.IOException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.InternetHeaders;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	configurationPid = "com.liferay.subscriptions.web.configuration.SubscriptionsConfiguration",
	service = UnsubscribeLifecycleHook.class
)
public class UnsubscribeLifecycleHookImpl implements UnsubscribeLifecycleHook {

	@Override
	public void beforeSendNotificationToPersistedSubscriber(
		SubscriptionSender sender, Subscription subscription) {

		if (!sender.isBulk()) {
			Calendar calendar = Calendar.getInstance();

			calendar.add(
				Calendar.DATE,
				_configuration.unsubscriptionTicketExpirationTimeInDays());

			Ticket ticket = _ticketLocalService.addOrUpdateDistinctTicket(
				subscription.getCompanyId(), Subscription.class.getName(),
				subscription.getSubscriptionId(),
				TicketConstants.TYPE_SUBSCRIPTIONS, StringPool.BLANK,
				calendar.getTime(), sender.getServiceContext());

			_unsubscribableUserMap.put(subscription.getUserId(), ticket);
		}
	}

	@Override
	public void processMailMessage(
			SubscriptionSender sender, MailMessage mailMessage)
		throws IOException, PortalException {

		Locale locale = sender.getServiceContext().getLocale();

		User user = _userLocalService.getUserByEmailAddress(
			sender.getCompanyId(), mailMessage.getTo()[0].getAddress());

		Ticket ticket = _unsubscribableUserMap.get(user.getUserId());

		if (ticket != null) {
			try {
				String unsubscribeURL = _getUnsubscribeURL(
					sender, user, ticket);

				_addUnsubscribeHeader(mailMessage, unsubscribeURL);
				_addUnsubscribeLink(mailMessage, locale, unsubscribeURL);
			}
			finally {
				_unsubscribableUserMap.remove(user.getUserId());
			}
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_configuration = ConfigurableUtil.createConfigurable(
			SubscriptionsConfiguration.class, properties);
	}

	private void _addUnsubscribeHeader(
		MailMessage mailMessage, String unsubscribeURL) {

		InternetHeaders internetHeaders = new InternetHeaders();

		internetHeaders.setHeader(
			"List-Unsubscribe", "<" + unsubscribeURL + ">");

		mailMessage.setInternetHeaders(internetHeaders);
	}

	private void _addUnsubscribeLink(
			MailMessage mailMessage, Locale locale, String unsubscribeURL)
		throws IOException {

		MailTemplateContextBuilder mailTemplateContextBuilder =
			MailTemplateFactoryUtil.createMailTemplateContextBuilder();

		mailTemplateContextBuilder.put("[$UNSUBSCRIBE_URL$]", unsubscribeURL);

		MailTemplateContext mailTemplateContext =
			mailTemplateContextBuilder.build();

		MailTemplate bodyMailTemplate =
			MailTemplateFactoryUtil.createMailTemplate(
				mailMessage.getBody(), true);

		String processedBody = bodyMailTemplate.renderAsString(
			locale, mailTemplateContext);

		mailMessage.setBody(processedBody);
	}

	private String _getUnsubscribeURL(
		SubscriptionSender sender, User user, Ticket ticket) {

		StringBuilder sb = new StringBuilder();

		sb.append(sender.getContextAttribute("[$PORTAL_URL$]"));
		sb.append(PortalUtil.getPathMain());
		sb.append(UnsubscribeAction.ACTION_NAME);
		sb.append("?key=");
		sb.append(ticket.getKey());
		sb.append("&userId=");
		sb.append(user.getUserId());

		return sb.toString();
	}

	private volatile SubscriptionsConfiguration _configuration;

	@Reference
	private TicketLocalService _ticketLocalService;

	private final Map<Number, Ticket> _unsubscribableUserMap = new HashMap<>();

	@Reference
	private UserLocalService _userLocalService;

}