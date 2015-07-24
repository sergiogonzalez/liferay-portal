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

import com.liferay.portlet.RequestBackedPortletURLFactory;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * A helper class to retrieve the ItemSelectorRendering and the PortletURL for
 * the item selector.
 *
 * @author Iván Zaera
 */
public interface ItemSelector {

	/**
	 * Returns the ItemSelectorRendering.
	 *
	 * @param  portletRequest
	 * @param  portletResponse
	 * @return
	 */
	public ItemSelectorRendering getItemSelectorRendering(
		PortletRequest portletRequest, PortletResponse portletResponse);

	/**
	 * Returns the item selector PortletURL.
	 *
	 * @param  requestBackedPortletURLFactory the factory used to generate the
	 *         PortletURL
	 * @param  itemSelectedEventName the event name that should be fired by
	 *         views.
	 * @param  itemSelectorCriteria an array of the Criteria that item selector
	 *         should use to retrive the views.
	 * @return
	 */
	public PortletURL getItemSelectorURL(
		RequestBackedPortletURLFactory requestBackedPortletURLFactory,
		String itemSelectedEventName,
		ItemSelectorCriterion... itemSelectorCriteria);

}