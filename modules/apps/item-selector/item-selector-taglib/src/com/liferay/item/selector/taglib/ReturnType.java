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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import javax.portlet.PortletURL;

/**
 * @author Roberto Díaz
 */
public enum ReturnType implements ItemSelectorReturnType {

	BASE_64 {

		@Override
		public String getValue(PortletURL uploadURL) throws Exception {
			return StringPool.BLANK;
		}
	},
	FILE_ENTRY {

		@Override
		public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay)
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
	},
	UPLOADABLE_BASE_64 {

		@Override
		public String getValue(PortletURL uploadURL) throws Exception {
			JSONObject base64JSONObject = JSONFactoryUtil.createJSONObject();

			base64JSONObject.put("base64", StringPool.BLANK);
			base64JSONObject.put("uploadURL", uploadURL);

			return base64JSONObject.toString();
		}
	},
	URL {

		@Override
		public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay)
			throws Exception {

			return DLUtil.getImagePreviewURL(fileEntry, themeDisplay);
		}
	};

	@Override
	public String getName() {
		return name();
	}

	public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public String getValue(PortletURL uploadURL) throws Exception {
		throw new UnsupportedOperationException();
	}

}