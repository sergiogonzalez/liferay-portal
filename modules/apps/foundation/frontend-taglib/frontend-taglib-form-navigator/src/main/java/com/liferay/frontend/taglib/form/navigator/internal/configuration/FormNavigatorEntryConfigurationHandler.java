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

package com.liferay.frontend.taglib.form.navigator.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.StringReader;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.frontend.taglib.form.navigator.internal.configuration.FormNavigatorConfiguration",
	immediate = true, service = FormNavigatorEntryConfigurationHandler.class
)
public class FormNavigatorEntryConfigurationHandler {

	public Optional<List<String>> getFormNavigatorEntryKeys(
		String formNavigatorId, String categoryKey, String context) {

		try {
			Properties formNavigatorEntryKeysProperties =
				_getFormNavigatorEntryKeysProperties(formNavigatorId);

			String formNavigatorEntryKeys = null;

			if (Validator.isNotNull(context)) {
				formNavigatorEntryKeys =
					formNavigatorEntryKeysProperties.getProperty(
						context + StringPool.PERIOD + categoryKey);
			}

			if (formNavigatorEntryKeys == null) {
				formNavigatorEntryKeys =
					formNavigatorEntryKeysProperties.getProperty(categoryKey);
			}

			if (formNavigatorEntryKeys != null) {
				return Optional.of(_splitKeys(formNavigatorEntryKeys));
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return Optional.empty();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_formNavigatorConfiguration = ConfigurableUtil.createConfigurable(
			FormNavigatorConfiguration.class, properties);
	}

	private void _addProperties(
		StringBundler sb,
		FormNavigatorConfiguration formNavigatorConfiguration) {

		String[] formNavigatorEntryKeys =
			formNavigatorConfiguration.formNavigatorEntryKeys();

		for (String line : formNavigatorEntryKeys) {
			sb.append(line);
			sb.append(StringPool.NEW_LINE);
		}
	}

	private Properties _getFormNavigatorEntryKeysProperties(
			String formNavigatorId)
		throws InvalidSyntaxException, IOException {

		StringBundler sb = new StringBundler();

		String curFormNavigatorId =
			_formNavigatorConfiguration.formNavigatorId();

		if (curFormNavigatorId.equals(formNavigatorId)) {
			_addProperties(sb, _formNavigatorConfiguration);
		}

		Properties properties = new Properties();

		properties.load(new StringReader(sb.toString()));

		return properties;
	}

	private List<String> _splitKeys(String formNavigatorEntryKeys) {
		return Arrays.stream(StringUtil.split(formNavigatorEntryKeys)).map(
			String::trim).collect(Collectors.toList());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FormNavigatorEntryConfigurationHandler.class);

	private FormNavigatorConfiguration _formNavigatorConfiguration;

}