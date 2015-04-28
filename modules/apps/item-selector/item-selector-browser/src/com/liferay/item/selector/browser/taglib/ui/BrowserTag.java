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

package com.liferay.item.selector.browser.taglib.ui;

import com.liferay.item.selector.browser.ServletContextProvider;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class BrowserTag extends IncludeTag {

	public BrowserTag() {
		super();

		ServletContext customServletContext =
			ServletContextProvider.getServletContext();

		if (customServletContext == null) {
			throw new RuntimeException();
		}

		setServletContext(customServletContext);
	}

	@Override
	public boolean isCustomServletContext() {
		return true;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setIdPrefix(String idPrefix) {
		_idPrefix = idPrefix;
	}

	public void setItemSearchContainer(SearchContainer itemSearchContainer) {
		_itemSearchContainer = itemSearchContainer;
	}

	public void setTabName(String tabName) {
		_tabName = tabName;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_displayStyle = "icon";
		_idPrefix = null;
		_itemSearchContainer = null;
		_tabName = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"item-selector:view-entries:displayStyle", _displayStyle);
		request.setAttribute("item-selector:view-entries:idPrefix", _idPrefix);
		request.setAttribute(
			"item-selector:view-entries:itemSearchContainer",
			_itemSearchContainer);
		request.setAttribute("item-selector:view-entries:tabName", _tabName);
	}

	private static final String _PAGE = "/taglib/ui/page.jsp";

	private String _displayStyle;
	private String _idPrefix;
	private SearchContainer _itemSearchContainer;
	private String _tabName;

}