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

package com.liferay.url.item.selector.web.display.context;

import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.url.item.selector.web.URLItemSelectorView;

import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class URLItemSelectorViewDisplayContext
	<T extends ItemSelectorCriterion, S extends ItemSelectorReturnType> {

	public URLItemSelectorViewDisplayContext(
		T itemSelectorCriterion, URLItemSelectorView<T, S> urlItemSelectorView,
		String itemSelectedEventName, PortletURL portletURL) {

		_itemSelectorCriterion = itemSelectorCriterion;
		_urlItemSelectorView = urlItemSelectorView;
		_itemSelectedEventName = itemSelectedEventName;
		_portletURL = portletURL;
	}

	public String getDisplayStyle(HttpServletRequest request) {
		return ParamUtil.getString(request, "displayStyle");
	}

	public long getFolderId(HttpServletRequest request) {
		return ParamUtil.getLong(
			request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public T getItemSelectorCriterion() {
		return _itemSelectorCriterion;
	}

	public String[] getMimeTypes() {
		return _urlItemSelectorView.getMimeTypes();
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public long getRepositoryId(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return ParamUtil.getLong(
			request, "repositoryId", themeDisplay.getScopeGroupId());
	}

	public String getTitle(Locale locale) {
		return _urlItemSelectorView.getTitle(locale);
	}

	private final String _itemSelectedEventName;
	private final T _itemSelectorCriterion;
	private final PortletURL _portletURL;
	private final URLItemSelectorView<T, S> _urlItemSelectorView;

}