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
import com.liferay.portlet.asset.service.base.AssetBookmarkLocalServiceBaseImpl;

import java.util.Date;

/**
 * The implementation of the favorite asset local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.asset.service.AssetBookmarkLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Juan Fernández
 * @see com.liferay.portlet.asset.service.base.AssetBookmarkLocalServiceBaseImpl
 * @see com.liferay.portlet.asset.service.AssetBookmarkLocalServiceUtil
 */
public class AssetBookmarkLocalServiceImpl
	extends AssetBookmarkLocalServiceBaseImpl {

	public AssetBookmark addAssetBookmark(
			long userId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		long groupId = serviceContext.getScopeGroupId();
		long favoriteId = counterLocalService.increment();

		AssetBookmark assetBookmark = assetBookmarkPersistence.create(
			favoriteId);

		assetBookmark.setUserId(userId);
		assetBookmark.setClassNameId(PortalUtil.getClassNameId(className));
		assetBookmark.setClassPK(classPK);

		assetBookmark = assetBookmarkPersistence.update(assetBookmark);

		// Asset

		updateAsset(
			groupId, userId, assetBookmark,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		return assetBookmark;
	}

	public void deleteAssetBookmark(long userId, long classPK)
		throws PortalException, SystemException {

		assetBookmarkPersistence.removeByU_CPK(userId, classPK);

		// Asset

		assetEntryLocalService.deleteEntry(
			AssetBookmark.class.getName(), classPK);
	}

	public boolean isBookmarked(long userId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		AssetBookmark assetBookmark = assetBookmarkPersistence.fetchByU_C_C(
			userId, classNameId, classPK);

		return (assetBookmark != null);
	}

	public void updateAsset(
			long groupId, long userId, AssetBookmark assetBookmark,
			long[] assetCategoryIds, String[] assetTagNames)
		throws PortalException, SystemException {

		Date now = new Date();

		assetEntryLocalService.updateEntry(
			userId, groupId, now, now, AssetBookmark.class.getName(),
			assetBookmark.getClassPK(), assetBookmark.getUuid(), 0,
			assetCategoryIds, assetTagNames, true, null, null, null,
			ContentTypes.TEXT_HTML, null, null, null, null, null, 0, 0, null,
			false);
	}

}