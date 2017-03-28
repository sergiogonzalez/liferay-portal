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

package com.liferay.item.selector.web.internal.upload;

import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.item.selector.ItemSelectorUploadResponseHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.DefaultUploadResponseHandler;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component
public class ItemSelectorUploadResponseHandlerImpl
	implements ItemSelectorUploadResponseHandler {

	@Override
	public JSONObject onFailure(
			PortletRequest portletRequest, PortalException pe)
		throws PortalException {

		return _defaultUploadResponseHandler.onFailure(portletRequest, pe);
	}

	@Override
	public JSONObject onSuccess(
			PortletRequest portletRequest, FileEntry fileEntry)
		throws PortalException {

		JSONObject jsonObject = _defaultUploadResponseHandler.onSuccess(
			portletRequest, fileEntry);

		return _resolveValue(portletRequest, fileEntry, jsonObject);
	}

	private JSONObject _resolveValue(
			PortletRequest portletRequest, FileEntry fileEntry,
			JSONObject jsonObject)
		throws PortalException {

		String returnType = ParamUtil.getString(portletRequest, "returnType");

		ItemSelectorReturnTypeResolver itemSelectorReturnTypeResolver =
			_itemSelectorReturnTypeResolverHandler.
				getItemSelectorReturnTypeResolver(
					returnType, FileEntry.class.getName());

		if (itemSelectorReturnTypeResolver != null) {
			try {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)portletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				String resolvedValue = itemSelectorReturnTypeResolver.getValue(
					fileEntry, themeDisplay);

				JSONObject fileJSONObject = jsonObject.getJSONObject("file");

				fileJSONObject.put("resolvedValue", resolvedValue);
			}
			catch (Exception e) {
				throw new PortalException(e);
			}
		}

		return jsonObject;
	}

	@Reference
	private DefaultUploadResponseHandler _defaultUploadResponseHandler;

	@Reference
	private ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;

}