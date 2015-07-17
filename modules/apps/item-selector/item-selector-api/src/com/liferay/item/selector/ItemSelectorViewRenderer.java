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

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

/**
 * A helper class to render the ItemSelectorView view layer. It also contains
 * the ItemSelectorCriterion instance in order to allow use its inner
 * information in the view layer.
 *
 * @author Iván Zaera
 */
public interface ItemSelectorViewRenderer {

	/**
	 * Returns an instance of ItemSelectorCriterion.
	 *
	 * @return an instance of ItemSelectorCriterion.
	 */
	public ItemSelectorCriterion getItemSelectorCriterion();

	/**
	 * Returns a List of ItemSelectorView.
	 *
	 * @return a List of ItemSelectorView.
	 */
	public ItemSelectorView<ItemSelectorCriterion> getItemSelectorView();

	/**
	 * Renders the HTML.
	 *
	 * @param  pageContext the pageContext with which the item selector is
	 *         rendered.
	 * @throws IOException
	 * @throws javax.servlet.ServletException
	 */
	public void renderHTML(PageContext pageContext)
		throws IOException, ServletException;

}