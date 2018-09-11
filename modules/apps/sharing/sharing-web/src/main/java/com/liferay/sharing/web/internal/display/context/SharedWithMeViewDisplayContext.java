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

package com.liferay.sharing.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.sharing.constants.SharingEntryActionKey;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.web.internal.util.SharingAssetUtil;

import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Sergio González
 */
public class SharedWithMeViewDisplayContext {

	public SharedWithMeViewDisplayContext(
		ThemeDisplay themeDisplay,
		SharingEntryLocalService sharingEntryLocalService) {

		_themeDisplay = themeDisplay;
		_sharingEntryLocalService = sharingEntryLocalService;
	}

	public String getAssetTypeTitle(SharingEntry sharingEntry) {
		AssetRenderer assetRenderer = SharingAssetUtil.getAssetRenderer(
			sharingEntry);

		if (assetRenderer != null) {
			AssetRendererFactory assetRendererFactory =
				assetRenderer.getAssetRendererFactory();

			return assetRendererFactory.getTypeName(_themeDisplay.getLocale());
		}

		return StringPool.BLANK;
	}

	public String getTitle(SharingEntry sharingEntry) {
		AssetRenderer assetRenderer = SharingAssetUtil.getAssetRenderer(
			sharingEntry);

		if (assetRenderer != null) {
			return assetRenderer.getTitle(_themeDisplay.getLocale());
		}

		return StringPool.BLANK;
	}

	public PortletURL getURLEdit(
			SharingEntry sharingEntry,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		AssetRenderer assetRenderer = SharingAssetUtil.getAssetRenderer(
			sharingEntry);

		if (assetRenderer != null) {
			return assetRenderer.getURLEdit(
				liferayPortletRequest, liferayPortletResponse);
		}

		return null;
	}

	public boolean hasEditPermission(SharingEntry sharingEntry) {
		return _sharingEntryLocalService.hasSharingPermission(
			sharingEntry, SharingEntryActionKey.UPDATE);
	}

	public void populateResults(SearchContainer<SharingEntry> searchContainer) {
		int total = _sharingEntryLocalService.countToUserSharingEntries(
			_themeDisplay.getUserId());

		searchContainer.setTotal(total);

		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getToUserSharingEntries(
				_themeDisplay.getUserId(), searchContainer.getStart(),
				searchContainer.getEnd());

		searchContainer.setResults(sharingEntries);
	}

	private final SharingEntryLocalService _sharingEntryLocalService;
	private final ThemeDisplay _themeDisplay;

}