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

package com.liferay.subscription.web.util;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.subscription.util.UnsubscribeLifecycleHookFactory;
import com.liferay.subscription.web.configuration.SubscriptionConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.subscription.web.configuration.SubscriptionConfiguration",
	immediate = true, service = UnsubscribeLifecycleHookFactory.class
)
public class UnsubscribeLifecycleHookFactoryImpl
	implements UnsubscribeLifecycleHookFactory {

	@Override
	public UnsubscribeLifecycleHookImpl create() {
		return new UnsubscribeLifecycleHookImpl(
			_configuration, _ticketLocalService, _userLocalService);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_configuration = ConfigurableUtil.createConfigurable(
			SubscriptionConfiguration.class, properties);
	}

	private volatile SubscriptionConfiguration _configuration;

	@Reference
	private TicketLocalService _ticketLocalService;

	@Reference
	private UserLocalService _userLocalService;

}