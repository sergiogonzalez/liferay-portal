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

package com.liferay.portal.ldap.configuration;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.ldap.constants.LDAPConstants;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Michael C. Han
 */
public abstract class CompanyScopedConfigurationProvider
	<T extends CompanyScopedConfiguration> implements ConfigurationProvider<T> {

	@Override
	public T getConfiguration(long companyId) {
		return getConfiguration(companyId, true);
	}

	@Override
	public T getConfiguration(long companyId, boolean useDefault) {
		Dictionary<String, Object> properties = getConfigurationProperties(
			companyId, useDefault);

		T configurable = Configurable.createConfigurable(
			getMetatype(), properties);

		return configurable;
	}

	@Override
	public T getConfiguration(long companyId, long index) {
		return getConfiguration(companyId, true);
	}

	@Override
	public T getConfiguration(long companyId, long index, boolean useDefault) {
		return getConfiguration(companyId, useDefault);
	}

	@Override
	public Dictionary<String, Object> getConfigurationProperties(
		long companyId) {

		return getConfigurationProperties(companyId, true);
	}

	@Override
	public Dictionary<String, Object> getConfigurationProperties(
		long companyId, boolean useDefault) {

		Configuration configuration = _configurations.get(companyId);

		if (useDefault && (configuration == null)) {
			configuration = _configurations.get(0L);
		}
		else if (!useDefault && (configuration == null)) {
			return new HashMapDictionary<>();
		}

		if (configuration == null) {
			Class<?> clazz = getMetatype();

			throw new IllegalArgumentException(
				"No instance of " + clazz.getName() + " for company " +
					companyId);
		}

		Dictionary<String, Object> properties = configuration.getProperties();

		return properties;
	}

	@Override
	public Dictionary<String, Object> getConfigurationProperties(
		long companyId, long index) {

		return getConfigurationProperties(companyId, index, true);
	}

	@Override
	public Dictionary<String, Object> getConfigurationProperties(
		long companyId, long index, boolean useDefault) {

		return getConfigurationProperties(companyId, useDefault);
	}

	@Override
	public List<T> getConfigurations(long companyId) {
		return getConfigurations(companyId, true);
	}

	@Override
	public List<T> getConfigurations(long companyId, boolean useDefault) {
		List<Dictionary<String, Object>> configurationsProperties =
			getConfigurationsProperties(companyId, useDefault);

		List<T> configurables = new ArrayList<>(
			configurationsProperties.size());

		for (Dictionary<String, Object> configurationProperties :
				configurationsProperties) {

			T configurable = Configurable.createConfigurable(
				getMetatype(), configurationProperties);

			configurables.add(configurable);
		}

		return configurables;
	}

	@Override
	public List<Dictionary<String, Object>> getConfigurationsProperties(
		long companyId) {

		return getConfigurationsProperties(companyId, true);
	}

	@Override
	public List<Dictionary<String, Object>> getConfigurationsProperties(
		long companyId, boolean useDefault) {

		Configuration configuration = _configurations.get(companyId);

		if (!useDefault && (configuration == null)) {
			return Collections.emptyList();
		}
		else if (configuration == null) {
			configuration = _configurations.get(0L);
		}

		if (configuration == null) {
			Class<?> clazz = getMetatype();

			throw new IllegalArgumentException(
				"No instance of " + clazz.getName() + " for company " +
					companyId);
		}

		List<Dictionary<String, Object>> configurationsProperties =
			new ArrayList<>();

		Dictionary<String, Object> properties = configuration.getProperties();

		configurationsProperties.add(properties);

		return configurationsProperties;
	}

	@Override
	public synchronized void registerConfiguration(
		Configuration configuration) {

		Dictionary<String, Object> properties = configuration.getProperties();

		T configurable = Configurable.createConfigurable(
			getMetatype(), properties);

		long companyId = configurable.companyId();

		_configurations.put(companyId, configuration);
	}

	@Override
	public synchronized void unregisterConfiguration(
		Configuration configuration) {

		Dictionary<String, Object> properties = configuration.getProperties();

		T configurable = Configurable.createConfigurable(
			getMetatype(), properties);

		long companyId = configurable.companyId();

		_configurations.remove(companyId);
	}

	@Override
	public void updateProperties(
		long companyId, Dictionary<String, Object> properties) {

		Configuration configuration = _configurations.get(companyId);

		Configuration defaultConfiguration = _configurations.get(0L);

		if (defaultConfiguration == null) {
			Class<?> metatype = getMetatype();

			throw new IllegalArgumentException(
				"No default configuration for " + metatype.getName());
		}

		try {
			if (configuration == null) {
				configuration = configurationAdmin.createFactoryConfiguration(
					defaultConfiguration.getFactoryPid(),
					defaultConfiguration.getBundleLocation());
			}

			properties.put(LDAPConstants.COMPANY_ID, companyId);

			configuration.update(properties);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to update configuration", ioe);
		}
	}

	@Override
	public void updateProperties(
		long companyId, long index, Dictionary<String, Object> properties) {

		updateProperties(companyId, properties);
	}

	protected abstract void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin);

	protected ConfigurationAdmin configurationAdmin;

	private final Map<Long, Configuration> _configurations = new HashMap<>();

}