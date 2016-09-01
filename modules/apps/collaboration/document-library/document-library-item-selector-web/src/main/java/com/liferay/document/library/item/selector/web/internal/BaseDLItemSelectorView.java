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

package com.liferay.document.library.item.selector.web.internal;

import com.liferay.document.library.item.selector.web.internal.display.context.DLItemSelectorViewDisplayContext;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.language.LanguageResources;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
public abstract class BaseDLItemSelectorView<T extends ItemSelectorCriterion>
	implements DLItemSelectorView<T> {

	@Override
	public String[] getMimeTypes() {
		return new String[0];
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		if (_supportedItemSelectorReturnTypes == null) {
			_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
				ListUtil.fromArray(
					new ItemSelectorReturnType[] {
						_fileEntryItemSelectorReturnType,
						_urlItemSelectorReturnType
					}));
		}

		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundleLoader resourceBundleLoader = getResourceBundleLoader();

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			LocaleUtil.toLanguageId(locale));

		return ResourceBundleUtil.getString(
			resourceBundle, "documents-and-media");
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return true;
	}

	@Override
	public void renderHTML(
			ServletRequest request, ServletResponse response, T t,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher("/documents.jsp");

		DLItemSelectorViewDisplayContext dlItemSelectorViewDisplayContext =
			new DLItemSelectorViewDisplayContext(
				t, this, _itemSelectorReturnTypeResolverHandler,
				itemSelectedEventName, search, portletURL);

		request.setAttribute(
			DL_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT,
			dlItemSelectorViewDisplayContext);

		requestDispatcher.include(request, response);
	}

	@Reference(service = FileEntryItemSelectorReturnType.class)
	public void setFileEntryItemSelectorReturnType(
		FileEntryItemSelectorReturnType fileEntryItemSelectorReturnType) {

		_fileEntryItemSelectorReturnType = fileEntryItemSelectorReturnType;
	}

	@Reference(unbind = "-")
	public void setItemSelectorReturnTypeResolverHandler(
		ItemSelectorReturnTypeResolverHandler
			itemSelectorReturnTypeResolverHandler) {

		_itemSelectorReturnTypeResolverHandler =
			itemSelectorReturnTypeResolverHandler;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.item.selector.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Reference(service = URLItemSelectorReturnType.class)
	public void setUrlItemSelectorReturnType(
		URLItemSelectorReturnType urlItemSelectorReturnType) {

		_urlItemSelectorReturnType = urlItemSelectorReturnType;
	}

	protected ResourceBundleLoader getResourceBundleLoader() {
		return LanguageResources.RESOURCE_BUNDLE_LOADER;
	}

	private static List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes;

	private FileEntryItemSelectorReturnType _fileEntryItemSelectorReturnType;
	private ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;
	private ServletContext _servletContext;
	private URLItemSelectorReturnType _urlItemSelectorReturnType;

}