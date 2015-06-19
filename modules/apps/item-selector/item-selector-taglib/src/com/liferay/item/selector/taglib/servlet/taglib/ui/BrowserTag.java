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

package com.liferay.item.selector.taglib.servlet.taglib.ui;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.taglib.ReturnType;
import com.liferay.item.selector.taglib.ReturnTypeUtil;
import com.liferay.item.selector.taglib.util.ServletContextUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import java.util.HashSet;
import java.util.Set;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Roberto Díaz
 */
public class BrowserTag extends IncludeTag {

	public void setDesiredItemSelectorReturnTypes(
		Set<ItemSelectorReturnType> desiredItemSelectorReturnTypes) {

		_desiredItemSelectorReturnTypes = desiredItemSelectorReturnTypes;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setDisplayStyleURL(PortletURL displayStyleURL) {
		_displayStyleURL = displayStyleURL;
	}

	public void setItemSelectedEventName(String itemSelectedEventName) {
		_itemSelectedEventName = itemSelectedEventName;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
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

	public void setUploadURL(PortletURL uploadURL) {
		_uploadURL = uploadURL;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_desiredItemSelectorReturnTypes = new HashSet<>();
		_displayStyle = null;
		_displayStyleURL = null;
		_itemSelectedEventName = null;
		_searchContainer = null;
		_tabName = null;
		_uploadMessage = null;
		_uploadURL = null;
	}

	protected String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		return _DEFAULT_DISPLAY_STYLE;
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
			"liferay-ui:item-selector-browser:displayStyle", getDisplayStyle());
		request.setAttribute(
			"liferay-ui:item-selector-browser:displayStyleURL",
			_displayStyleURL);

		try {
			request.setAttribute(
				"liferay-ui:item-selector-browser:firstDraggableReturnType",
				getDraggableReturnType());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("No valid draggable return type found");
			}
		}

		try {
			request.setAttribute(
				"liferay-ui:item-selector-browser:firstExistingFileReturnType",
				ReturnTypeUtil.parseFirstExistingFileReturnType(
					_desiredItemSelectorReturnTypes));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("No valid existing files return type found");
			}
		}

		request.setAttribute(
			"liferay-ui:item-selector-browser:itemSelectedEventName",
			_itemSelectedEventName);
		request.setAttribute(
			"liferay-ui:item-selector-browser:searchContainer",
			_searchContainer);
		request.setAttribute(
			"liferay-ui:item-selector-browser:tabName", _tabName);
		request.setAttribute(
			"liferay-ui:item-selector-browser:uploadMessage",
			getUploadMessage());
		request.setAttribute(
			"liferay-ui:item-selector-browser:uploadURL", _uploadURL);
	}

	protected ReturnType getDraggableReturnType() throws Exception {
		ReturnType firstDraggableReturnType =
			ReturnTypeUtil.parseFirstDraggableReturnType(
				_desiredItemSelectorReturnTypes);

		if ((firstDraggableReturnType == ReturnType.UPLOADABLE_BASE_64) &&
			(_uploadURL == null)) {

			firstDraggableReturnType = null;
		}
		return firstDraggableReturnType;
	}

	private static final String _DEFAULT_DISPLAY_STYLE = "icon";

	private static final String _PAGE = "/taglib/ui/browser/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(BrowserTag.class);

	private Set<ItemSelectorReturnType> _desiredItemSelectorReturnTypes;
	private String _displayStyle;
	private PortletURL _displayStyleURL;
	private String _itemSelectedEventName;
	private SearchContainer<?> _searchContainer;
	private String _tabName;
	private String _uploadMessage;
	private PortletURL _uploadURL;

}