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

package com.liferay.item.selector.taglib;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnTypeUtil;
import com.liferay.item.selector.criteria.Base64ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.util.Set;

/**
 * @author Sergio González
 */
public class ItemSelectorBrowserReturnTypeUtil
	implements ItemSelectorReturnType {

	public static final ItemSelectorReturnType _BASE_64 =
		new Base64ItemSelectorReturnType();

	public static final ItemSelectorReturnType _FILE_ENTRY =
		new FileEntryItemSelectorReturnType();

	public static final ItemSelectorReturnType _URL =
		new URLItemSelectorReturnType();

	public static ItemSelectorReturnType
		getFirstAvailableItemSelectorReturnType(
			Set<ItemSelectorReturnType> desiredItemSelectorReturnTypes) {

		for (ItemSelectorReturnType desiredItemSelectorReturnType :
				desiredItemSelectorReturnTypes) {

			if (ItemSelectorReturnTypeUtil.equals(
					desiredItemSelectorReturnType, _BASE_64) ||
				ItemSelectorReturnTypeUtil.equals(
					desiredItemSelectorReturnType, _FILE_ENTRY) ||
				ItemSelectorReturnTypeUtil.equals(
					desiredItemSelectorReturnType, _URL)) {

				return desiredItemSelectorReturnType;
			}
		}

		return null;
	}

	public static String getValue(
			ItemSelectorReturnType itemSelectorReturnType, FileEntry fileEntry,
			ThemeDisplay themeDisplay)
		throws Exception {

		if (ItemSelectorReturnTypeUtil.equals(
				_BASE_64, itemSelectorReturnType)) {

			return StringPool.BLANK;
		}
		else if (ItemSelectorReturnTypeUtil.equals(
					_FILE_ENTRY, itemSelectorReturnType)) {

			return getFileEntryValue(fileEntry, themeDisplay);
		}
		else if (ItemSelectorReturnTypeUtil.equals(
					_URL, itemSelectorReturnType)) {

			return getURLValue(fileEntry, themeDisplay);
		}

		return StringPool.BLANK;
	}

	protected static String getFileEntryValue(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		JSONObject fileEntryJSONObject = JSONFactoryUtil.createJSONObject();

		fileEntryJSONObject.put("fileEntryId", fileEntry.getFileEntryId());
		fileEntryJSONObject.put("groupId", fileEntry.getGroupId());
		fileEntryJSONObject.put("title", fileEntry.getTitle());
		fileEntryJSONObject.put(
			"url", DLUtil.getImagePreviewURL(fileEntry, themeDisplay));
		fileEntryJSONObject.put("uuid", fileEntry.getUuid());

		return fileEntryJSONObject.toString();
	}

	protected static String getURLValue(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return DLUtil.getImagePreviewURL(fileEntry, themeDisplay);
	}

}