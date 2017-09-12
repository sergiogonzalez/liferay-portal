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

package com.liferay.asset.display.template.service.impl;

import com.liferay.asset.display.template.constants.AssetDisplayTemplateActionKeys;
import com.liferay.asset.display.template.model.AssetDisplayTemplate;
import com.liferay.asset.display.template.service.base.AssetDisplayTemplateServiceBaseImpl;
import com.liferay.asset.display.template.service.permission.AssetDisplayPermission;
import com.liferay.asset.display.template.service.permission.AssetDisplayTemplatePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Pavel Savinov
 */
public class AssetDisplayTemplateServiceImpl
	extends AssetDisplayTemplateServiceBaseImpl {

	@Override
	public AssetDisplayTemplate addAssetDisplayTemplate(
			long groupId, long userId, String name, long classNameId,
			String language, String scriptContent, boolean main,
			ServiceContext serviceContext)
		throws PortalException {

		AssetDisplayPermission.check(
			getPermissionChecker(), groupId,
			AssetDisplayTemplateActionKeys.ADD_ASSET_DISPLAY_TEMPLATE);

		return assetDisplayTemplateLocalService.addAssetDisplayTemplate(
			groupId, userId, name, classNameId, language, scriptContent, main,
			serviceContext);
	}

	@Override
	public AssetDisplayTemplate deleteAssetDisplayTemplate(
			long assetDisplayTemplateId)
		throws PortalException {

		AssetDisplayTemplatePermission.check(
			getPermissionChecker(), assetDisplayTemplateId, ActionKeys.DELETE);

		return assetDisplayTemplateLocalService.deleteAssetDisplayTemplate(
			assetDisplayTemplateId);
	}

	@Override
	public AssetDisplayTemplate updateAssetDisplayTemplate(
			long assetDisplayTemplateId, String name, long classNameId,
			String language, String scriptContent, boolean main,
			ServiceContext serviceContext)
		throws PortalException {

		AssetDisplayTemplatePermission.check(
			getPermissionChecker(), assetDisplayTemplateId, ActionKeys.UPDATE);

		return assetDisplayTemplateLocalService.updateAssetDisplayTemplate(
			assetDisplayTemplateId, name, classNameId, language, scriptContent,
			main, serviceContext);
	}

}