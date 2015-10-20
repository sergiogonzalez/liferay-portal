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

package com.liferay.portal.repository;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.repository.registry.RepositoryClassDefinition;
import com.liferay.portal.repository.registry.RepositoryClassDefinitionCatalog;

import java.util.Collection;

/**
 * @author Adolfo Pérez
 */
public class RepositoryCacheImpl implements CacheRegistryItem, RepositoryCache {

	@Override
	public String getRegistryName() {
		return RepositoryCacheImpl.class.getName();
	}

	@Override
	public void invalidate() {
		Collection<RepositoryClassDefinition> repositoryClassDefinitions =
			_repositoryClassDefinitionCatalog.getRepositoryClassDefinitions();

		for (RepositoryClassDefinition repositoryClassDefinition :
				repositoryClassDefinitions) {

			repositoryClassDefinition.invalidateCache();
		}
	}

	@Override
	public void invalidate(String className, long repositoryId) {
		RepositoryClassDefinition repositoryClassDefinition =
			_repositoryClassDefinitionCatalog.getRepositoryClassDefinition(
				className);

		repositoryClassDefinition.invalidateCachedRepository(repositoryId);
	}

	@BeanReference(type = RepositoryClassDefinitionCatalog.class)
	private RepositoryClassDefinitionCatalog _repositoryClassDefinitionCatalog;

}