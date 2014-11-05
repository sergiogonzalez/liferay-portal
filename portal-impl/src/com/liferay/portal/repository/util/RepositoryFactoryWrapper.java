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

package com.liferay.portal.repository.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;

/**
 * @author Adolfo Pérez
 */
public class RepositoryFactoryWrapper implements RepositoryFactory {

	public RepositoryFactoryWrapper(RepositoryFactory repositoryFactory) {
		_repositoryFactory = repositoryFactory;
	}

	@Override
	public LocalRepository createLocalRepository(long repositoryId)
		throws PortalException {

		return _repositoryFactory.createLocalRepository(repositoryId);
	}

	@Override
	public Repository createRepository(long repositoryId)
		throws PortalException {

		return _repositoryFactory.createRepository(repositoryId);
	}

	private final RepositoryFactory _repositoryFactory;

}