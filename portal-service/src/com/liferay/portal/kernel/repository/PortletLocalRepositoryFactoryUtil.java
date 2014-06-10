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

package com.liferay.portal.kernel.repository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Adolfo Pérez
 */
public class PortletLocalRepositoryFactoryUtil {

	public static LocalRepository create(long repositoryId)
		throws PortalException, SystemException {

		return getPortletLocalRepositoryFactory().create(repositoryId);
	}

	public static LocalRepository create(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		return getPortletLocalRepositoryFactory().create(
			folderId, fileEntryId, fileVersionId);
	}

	public static LocalRepositoryFactory getPortletLocalRepositoryFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortletLocalRepositoryFactoryUtil.class);

		return _portletLocalRepositoryFactory;
	}

	public void setPortletLocalRepositoryFactory(
		LocalRepositoryFactory localRepositoryFactory) {

		PortalRuntimePermission.checkSetBeanProperty(
			PortletLocalRepositoryFactoryUtil.class);

		_portletLocalRepositoryFactory = localRepositoryFactory;
	}

	private static LocalRepositoryFactory _portletLocalRepositoryFactory;

}