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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.repository.BaseLocalRepositoryFactory;
import com.liferay.portal.util.RepositoryUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Adolfo Pérez
 */
public class PortletLocalRepositoryFactoryImpl
	extends BaseLocalRepositoryFactory {

	public void afterPropertiesSet() {
		CacheRegistryUtil.register(new CacheRegistryItem() {
			@Override
			public String getRegistryName() {
				return PortletLocalRepositoryFactoryImpl.class.getName();
			}

			@Override
			public void invalidate() {
				_portletLocalRepositoriesByRepositoryEntryId.clear();
				_portletLocalRepositoriesByRepositoryId.clear();
			}
		});
	}

	@Override
	public LocalRepository create(long repositoryId)
		throws PortalException, SystemException {

		LocalRepository cachedPortletLocalRepository =
			_portletLocalRepositoriesByRepositoryId.get(repositoryId);

		if (cachedPortletLocalRepository != null) {
			return cachedPortletLocalRepository;
		}

		LocalRepository portletLocalRepository = super.create(repositoryId);

		_portletLocalRepositoriesByRepositoryId.put(
			repositoryId, portletLocalRepository);

		return portletLocalRepository;
	}

	@Override
	public LocalRepository create(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		long repositoryEntryId = RepositoryUtil.getRepositoryEntryId(
			folderId, fileEntryId, fileVersionId);

		LocalRepository cachedPortletLocalRepository =
			_portletLocalRepositoriesByRepositoryEntryId.get(repositoryEntryId);

		if (cachedPortletLocalRepository != null) {
			return cachedPortletLocalRepository;
		}

		LocalRepository portletLocalRepository = super.create(
			folderId, fileEntryId, fileVersionId);

		_portletLocalRepositoriesByRepositoryEntryId.put(
			repositoryEntryId, portletLocalRepository);

		return portletLocalRepository;
	}

	@Override
	protected LocalRepository createExternalRepository(
			long repositoryId, long classNameId)
		throws PortalException, SystemException {

		throw new UnsupportedOperationException(
			"This factory cannot be used to create external repositories");
	}

	@Override
	protected LocalRepository createExternalRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		throw new UnsupportedOperationException(
			"This factory cannot be used to create external repositories");
	}

	@Override
	protected LocalRepository createLiferayRepositoryInstance(
		long groupId, long repositoryId, long dlFolderId) {

		return new PortletLocalRepository(
			getRepositoryLocalService(), getRepositoryService(),
			getDlAppHelperLocalService(), getDlFileEntryLocalService(),
			getDlFileEntryService(), getDlFileEntryTypeLocalService(),
			getDlFileVersionLocalService(), getDlFileVersionService(),
			getDlFolderLocalService(), getDlFolderService(),
			getResourceLocalService(), groupId, repositoryId, dlFolderId);
	}

	private Map<Long, LocalRepository>
		_portletLocalRepositoriesByRepositoryEntryId =
			new ConcurrentHashMap<Long, LocalRepository>();
	private Map<Long, LocalRepository>
		_portletLocalRepositoriesByRepositoryId =
			new ConcurrentHashMap<Long, LocalRepository>();

}