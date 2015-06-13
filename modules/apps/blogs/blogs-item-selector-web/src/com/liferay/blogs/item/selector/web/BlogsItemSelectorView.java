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

package com.liferay.blogs.item.selector.web;

import com.liferay.blogs.item.selector.criterion.BlogsItemSelectorCriterion;
import com.liferay.blogs.item.selector.web.display.context.BlogsItemSelectorViewDisplayContext;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.DefaultItemSelectorReturnType;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component
public class BlogsItemSelectorView
	implements ItemSelectorView
		<BlogsItemSelectorCriterion, DefaultItemSelectorReturnType> {

	public static final String BLOGS_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT =
		"BLOGS_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT";

	@Override
	public Class<BlogsItemSelectorCriterion> getItemSelectorCriterionClass() {
		return BlogsItemSelectorCriterion.class;
	}

	@Override
	public Set<DefaultItemSelectorReturnType>
		getSupportedItemSelectorReturnTypes() {

		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"content/Language", locale);

		return resourceBundle.getString("blogs-images");
	}

	@Override
	public void renderHTML(
			ServletRequest request, ServletResponse response,
			BlogsItemSelectorCriterion blogsItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(
			"/o/blogs-item-selector-web/blogs_attachments.jsp");

		BlogsItemSelectorViewDisplayContext
			blogsItemSelectorViewDisplayContext =
				new BlogsItemSelectorViewDisplayContext(
					blogsItemSelectorCriterion, this, itemSelectedEventName,
					portletURL);

		request.setAttribute(
			BLOGS_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT,
			blogsItemSelectorViewDisplayContext);

		requestDispatcher.include(request, response);
	}

	private static final Set<DefaultItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableSet(
			SetUtil.fromArray(
				new DefaultItemSelectorReturnType[] {
					DefaultItemSelectorReturnType.FILE_ENTRY,
					DefaultItemSelectorReturnType.URL
				}));

}