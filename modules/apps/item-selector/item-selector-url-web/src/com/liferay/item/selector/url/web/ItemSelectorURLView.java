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

package com.liferay.item.selector.url.web;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.url.web.display.context.ItemSelectorURLViewDisplayContext;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = ItemSelectorView.class)
public class ItemSelectorURLView
	implements ItemSelectorView<ImageItemSelectorCriterion> {

	public static final String ITEM_SELECTOR_URL_VIEW_DISPLAY_CONTEXT =
		"ITEM_SELECTOR_URL_VIEW_DISPLAY_CONTEXT";

	@Override
	public Class<ImageItemSelectorCriterion> getItemSelectorCriterionClass() {
		return ImageItemSelectorCriterion.class;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public Set<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "url");
	}

	@Override
	public void renderHTML(
			ServletRequest request, ServletResponse response,
			ImageItemSelectorCriterion imageItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName)
		throws IOException, ServletException {

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher("/url.jsp");

		ItemSelectorURLViewDisplayContext itemSelectorURLViewDisplayContext =
			new ItemSelectorURLViewDisplayContext(this, itemSelectedEventName);

		request.setAttribute(
			ITEM_SELECTOR_URL_VIEW_DISPLAY_CONTEXT,
			itemSelectorURLViewDisplayContext);

		requestDispatcher.include(request, response);
	}

	@Reference(
		target ="(osgi.web.symbolicname=com.liferay.item.selector.url.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static final Set<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableSet(
			SetUtil.fromArray(
				new ItemSelectorReturnType[] {
					new URLItemSelectorReturnType()
				}));

	private ServletContext _servletContext;

}