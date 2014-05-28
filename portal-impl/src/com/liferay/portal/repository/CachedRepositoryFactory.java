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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.util.RepositoryUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Adolfo Pérez
 */
public class CachedRepositoryFactory implements RepositoryFactory {

	public CachedRepositoryFactory(RepositoryFactory repositoryFactory) {
		_repositoryFactory = repositoryFactory;
	}

	@Override
	public Repository create(long repositoryId)
		throws PortalException, SystemException {

		com.liferay.portal.kernel.repository.Repository repositoryImpl =
			_repositoriesByRepositoryId.get(repositoryId);

		if (repositoryImpl != null) {
			return repositoryImpl;
		}

		repositoryImpl = _repositoryFactory.create(repositoryId);

		_repositoriesByRepositoryId.put(repositoryId, repositoryImpl);

		return repositoryImpl;
	}

	@Override
	public Repository create(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		long repositoryEntryId = RepositoryUtil.getRepositoryEntryId(
			folderId, fileEntryId, fileVersionId);

		com.liferay.portal.kernel.repository.Repository repositoryImpl =
			_repositoriesByEntryId.get(repositoryEntryId);

		if (repositoryImpl != null) {
			return repositoryImpl;
		}

		repositoryImpl = _repositoryFactory.create(
			folderId, fileEntryId, fileVersionId);

		_repositoriesByEntryId.put(repositoryEntryId, repositoryImpl);

		return repositoryImpl;
	}

	private Map<Long, Repository>
		_repositoriesByEntryId = new ConcurrentHashMap<Long, Repository>();
	private Map<Long, com.liferay.portal.kernel.repository.Repository>
		_repositoriesByRepositoryId = new ConcurrentHashMap
		<Long, com.liferay.portal.kernel.repository.Repository>();
	private final RepositoryFactory _repositoryFactory;

}