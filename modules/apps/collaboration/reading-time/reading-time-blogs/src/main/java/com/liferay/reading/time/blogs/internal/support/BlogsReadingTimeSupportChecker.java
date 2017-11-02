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

package com.liferay.reading.time.blogs.internal.support;

import com.liferay.blogs.web.configuration.BlogsPortletInstanceConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.reading.time.support.ReadingTimeSupportChecker;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.blogs.web.configuration.BlogsPortletInstanceConfiguration",
	property = "model.class.name=com.liferay.blogs.model.BlogsEntry"
)
public class BlogsReadingTimeSupportChecker
	implements ReadingTimeSupportChecker {

	@Override
	public boolean isEnabled() {
		return _blogsPortletInstanceConfiguration.enableReadingTime();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_blogsPortletInstanceConfiguration =
			ConfigurableUtil.createConfigurable(
				BlogsPortletInstanceConfiguration.class, properties);
	}

	private volatile BlogsPortletInstanceConfiguration
		_blogsPortletInstanceConfiguration;

}