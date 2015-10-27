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

package com.liferay.portal.kernel.repository.registry;

import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryConfiguration;

import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public interface RepositoryDefiner {

	public String getClassName();

	public RepositoryConfiguration getRepositoryConfiguration();

	public String getRepositoryTypeLabel(Locale locale);

	public boolean isExternalRepository();

	public void registerCapabilities(
		CapabilityRegistry<DocumentRepository> capabilityRegistry);

	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry);

	public void registerRepositoryFactory(
		RepositoryFactoryRegistry repositoryFactoryRegistry);

}