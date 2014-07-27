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

package com.liferay.portal.repository.registry;

import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.event.RepositoryEventHandler;
import com.liferay.portal.kernel.repository.event.RepositoryEventListener;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryCreator;
import com.liferay.portal.kernel.repository.registry.RepositoryCreatorRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public class DefaultRepositoryRegistry
	implements CapabilityRegistry, RepositoryConfiguration,
		RepositoryCreatorRegistry, RepositoryEventRegistry {

	public DefaultRepositoryRegistry() {
		_exportedCapabilities = new HashSet<Class<? extends Capability>>();

		_supportedCapabilities =
			new HashMap<Class<? extends Capability>, Capability>();
	}

	@Override
	public <S extends Capability, T extends S> void addExportedCapability(
		Class<S> capabilityClass, T capability) {

		_exportedCapabilities.add(capabilityClass);

		addSupportedCapability(capabilityClass, capability);
	}

	@Override
	public <S extends Capability, T extends S> void addSupportedCapability(
		Class<S> capabilityClass, T capability) {

		_supportedCapabilities.put(capabilityClass, capability);
	}

	@Override
	public Set<Class<? extends Capability>> getExportedCapabilities() {
		return _exportedCapabilities;
	}

	@Override
	public RepositoryCreator getRepositoryCreator() {
		return _repositoryCreator;
	}

	@Override
	public RepositoryEventHandler getRepositoryEventHandler() {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Map<Class<? extends Capability>, Capability>
		getSupportedCapabilities() {

		return _supportedCapabilities;
	}

	@Override
	public <S extends RepositoryEventType, T>
		void registerRepositoryEventListener(
			Class<S> eventTypeClass, Class<T> modelClass,
			RepositoryEventListener<S, T> action) {

		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void setRepositoryCreator(RepositoryCreator repositoryCreator) {
		if (_repositoryCreator != null) {
			throw new IllegalStateException(
				"Repository creator is already set");
		}

		_repositoryCreator = repositoryCreator;
	}

	private Set<Class<? extends Capability>> _exportedCapabilities;
	private RepositoryCreator _repositoryCreator;
	private Map<Class<? extends Capability>, Capability> _supportedCapabilities;

}