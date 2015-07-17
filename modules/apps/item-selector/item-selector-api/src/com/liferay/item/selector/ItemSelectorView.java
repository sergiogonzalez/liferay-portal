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

package com.liferay.item.selector;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Renders the HTML that is going to be shown by the item selector.
 *
 * @author Iván Zaera
 */
public interface ItemSelectorView<T extends ItemSelectorCriterion> {

	/**
	 * Returns the ItemSelectorCriterion class this class belongs to.
	 *
	 * @return the ItemSelectorCriterion class.
	 */
	public Class<T> getItemSelectorCriterionClass();

	/**
	 * Returns a List of the ReturnTypes that the view layer could return.
	 *
	 * @return a List of ReturnTypes.
	 */
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes();

	/**
	 * Returns the title of the view. Used to show the tab label.
	 *
	 * @param  locale the locale that the title should be retrieved for.
	 * @return
	 */
	public String getTitle(Locale locale);

	/**
	 * Renders the view layer HTML.
	 *
	 * @param  servletRequest the servletRequest with which the item selector is
	 *         rendered.
	 * @param  servletResponse the servletResponse with which the item selector
	 *         isb rendered.
	 * @param  itemSelectorCriterion the instance of the ItemSelectorCriterion.
	 *         Some important information for the view rendered could be
	 *         retrieved from this param.
	 * @param  portletURL the url used to render the item selector.
	 * @param  itemSelectedEventName the event name that should be fired by
	 *         views.
	 * @throws IOException
	 * @throws javax.servlet.ServletException
	 */
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			T itemSelectorCriterion, PortletURL portletURL,
			String itemSelectedEventName)
		throws IOException, ServletException;

}