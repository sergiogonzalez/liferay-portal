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

package com.liferay.friendly.url.internal.exportimport.util;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.friendly.url.exportimport.util.FriendlyURLExportImportHelper;
import com.liferay.friendly.url.model.FriendlyURL;
import com.liferay.friendly.url.service.FriendlyURLLocalService;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = FriendlyURLExportImportHelper.class)
public class FriendlyURLExportImportHelperImpl
	implements FriendlyURLExportImportHelper {

	@Override
	public void exportFriendlyURLs(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws PortletDataException {

		long classNameId = PortalUtil.getClassNameId(
			stagedModel.getModelClassName());

		List<FriendlyURL> friendlyURLs =
			_friendlyURLLocalService.getFriendlyURLs(
				stagedModel.getCompanyId(),
				portletDataContext.getScopeGroupId(), classNameId,
				(long)stagedModel.getPrimaryKeyObj());

		for (FriendlyURL friendlyURL : friendlyURLs) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, stagedModel, friendlyURL,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
	}

	@Override
	public void importFriendlyURLs(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws PortletDataException {

		List<Element> friendlyURLElements =
			portletDataContext.getReferenceDataElements(
				stagedModel, FriendlyURL.class);

		for (Element friendlyURLElement : friendlyURLElements) {
			String path = friendlyURLElement.attributeValue("path");

			FriendlyURL friendlyURL =
				(FriendlyURL)portletDataContext.getZipEntryAsObject(path);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, friendlyURL);
		}
	}

	@Reference(unbind = "-")
	protected void setFriendlyURLLocalService(
		FriendlyURLLocalService friendlyURLLocalService) {

		_friendlyURLLocalService = friendlyURLLocalService;
	}

	private FriendlyURLLocalService _friendlyURLLocalService;

}