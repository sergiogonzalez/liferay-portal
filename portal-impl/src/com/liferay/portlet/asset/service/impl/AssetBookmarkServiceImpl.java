/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetBookmark;
import com.liferay.portlet.asset.service.base.AssetBookmarkServiceBaseImpl;

import java.util.Date;

/**
 * The implementation of the favorite asset remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.asset.service.AssetBookmarkService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Juan Fernández
 * @see com.liferay.portlet.asset.service.base.AssetBookmarkServiceBaseImpl
 * @see com.liferay.portlet.asset.service.AssetBookmarkServiceUtil
 */
public class AssetBookmarkServiceImpl extends AssetBookmarkServiceBaseImpl {

	public AssetBookmark addAssetBookmark(
		long userId, String className, long classPK,
		ServiceContext serviceContext)
		throws PortalException, SystemException {

		return assetBookmarkLocalService.addAssetBookmark(
			userId, className, classPK, serviceContext);
	}

	public void deleteAssetBookmark(long userId, long classPK)
		throws PortalException, SystemException {

		assetBookmarkLocalService.deleteAssetBookmark(userId, classPK);
	}

}