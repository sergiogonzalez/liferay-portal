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
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.LocalRepositoryFactory;
import com.liferay.portal.util.RepositoryUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Adolfo Pérez
 */
public class CachedLocalRepositoryFactory implements LocalRepositoryFactory {

	public CachedLocalRepositoryFactory(
		LocalRepositoryFactory localRepositoryFactory) {

		_localRepositoryFactory = localRepositoryFactory;
	}

	@Override
	public LocalRepository create(long repositoryId)
		throws PortalException, SystemException {

		LocalRepository localRepositoryImpl =
			_localRepositoriesByRepositoryId.get(repositoryId);

		if (localRepositoryImpl != null) {
			return localRepositoryImpl;
		}

		localRepositoryImpl = _localRepositoryFactory.create(repositoryId);

		_localRepositoriesByRepositoryId.put(repositoryId, localRepositoryImpl);

		return localRepositoryImpl;
	}

	@Override
	public LocalRepository create(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		long repositoryEntryId = RepositoryUtil.getRepositoryEntryId(
			folderId, fileEntryId, fileVersionId);

		LocalRepository localRepositoryImpl =
			_localRepositoriesByRepositoryEntryId.get(repositoryEntryId);

		if (localRepositoryImpl != null) {
			return localRepositoryImpl;
		}

		localRepositoryImpl = _localRepositoryFactory.create(
			folderId, fileEntryId, fileVersionId);

		_localRepositoriesByRepositoryEntryId.put(
			repositoryEntryId, localRepositoryImpl);

		return localRepositoryImpl;
	}

	private Map<Long, LocalRepository> _localRepositoriesByRepositoryEntryId =
		new ConcurrentHashMap<Long, LocalRepository>();
	private Map<Long, LocalRepository> _localRepositoriesByRepositoryId =
		new ConcurrentHashMap<Long, LocalRepository>();
	private LocalRepositoryFactory _localRepositoryFactory;

}