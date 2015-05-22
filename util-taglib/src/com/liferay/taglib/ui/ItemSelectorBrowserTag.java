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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class ItemSelectorBrowserTag extends IncludeTag {

	public void setDesiredReturnTypes(
		List<ItemSelectorBrowserReturnType> desiredReturnTypes) {

		_desiredReturnTypes = desiredReturnTypes;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setIdPrefix(String idPrefix) {
		_idPrefix = idPrefix;
	}

	public void setSearchContainer(SearchContainer<?> searchContainer) {
		_searchContainer = searchContainer;
	}

	public void setTabName(String tabName) {
		_tabName = tabName;
	}

	public void setUploadMessage(String uploadMessage) {
		_uploadMessage = uploadMessage;
	}

	public enum ItemSelectorBrowserReturnType {

		BASE64(Base64.class), FILE_ENTRY(FileEntry.class),
		URL (java.net.URL.class);

		public static List<ItemSelectorBrowserReturnType> parse(
			Set<Class<?>> value) {

			List<ItemSelectorBrowserReturnType> itemSelectorBrowserReturnTypes =
				new ArrayList<>();

			if (value.contains(BASE64)) {
				itemSelectorBrowserReturnTypes.add(BASE64);
			}

			if (value.contains(FILE_ENTRY)) {
				itemSelectorBrowserReturnTypes.add(FILE_ENTRY);
			}

			if (value.contains(URL)) {
				itemSelectorBrowserReturnTypes.add(URL);
			}

			if (value.isEmpty()) {
				return Collections.emptyList();
			}

			return itemSelectorBrowserReturnTypes;
		}

		public ObjectValuePair<String, String> getReturnTypeAndValue(
				FileEntry fileEntry, ThemeDisplay themeDisplay)
			throws Exception {

			Class<?> clazz = this.getValue();

			if (this == BASE64) {
				return new ObjectValuePair<>(clazz.getName(), StringPool.BLANK);
			}
			else if (this == FILE_ENTRY) {
				return new ObjectValuePair<>(
					clazz.getName(),
					String.valueOf(fileEntry.getFileEntryId()));
			}
			else {
				return new ObjectValuePair<>(
					clazz.getName(),
					DLUtil.getImagePreviewURL(fileEntry, themeDisplay));
			}
		}

		public Class<?> getValue() {
			return _value;
		}

		private ItemSelectorBrowserReturnType(Class<?> value) {
			_value = value;
		}

		private final Class<?> _value;

	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_desiredReturnTypes = null;
		_displayStyle = "icon";
		_idPrefix = null;
		_searchContainer = null;
		_tabName = null;
		_uploadMessage = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected String getUploadMessage() {
		if (Validator.isNotNull(_uploadMessage)) {
			return _uploadMessage;
		}

		return LanguageUtil.get(
			request,
			"upload-a-document-by-dropping-it-right-here-or-by-pressing-plus-" +
				"icon");
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:item-selector-browser:desiredReturnTypes",
			_desiredReturnTypes);
		request.setAttribute(
			"liferay-ui:item-selector-browser:displayStyle", _displayStyle);
		request.setAttribute(
			"liferay-ui:item-selector-browser:idPrefix", _idPrefix);
		request.setAttribute(
			"liferay-ui:item-selector-browser:searchContainer",
			_searchContainer);
		request.setAttribute(
			"liferay-ui:item-selector-browser:tabName", _tabName);
		request.setAttribute(
			"liferay-ui:item-selector-browser:uploadMessage",
			getUploadMessage());
	}

	private static final String _PAGE =
		"/html/taglib/ui/item_selector_browser/page.jsp";

	private List<ItemSelectorBrowserReturnType> _desiredReturnTypes;
	private String _displayStyle;
	private String _idPrefix;
	private SearchContainer<?> _searchContainer;
	private String _tabName;
	private String _uploadMessage;

}