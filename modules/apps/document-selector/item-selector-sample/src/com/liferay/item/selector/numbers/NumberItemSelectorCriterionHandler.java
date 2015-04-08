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

package com.liferay.item.selector.numbers;

import com.liferay.document.selector.ItemSelectorCriterionHandler;
import com.liferay.document.selector.ItemSelectorView;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera
 */
@Component(
	service = ItemSelectorCriterionHandler.class
)
public class NumberItemSelectorCriterionHandler
	implements ItemSelectorCriterionHandler<NumberItemSelectorCriterion> {

	@Override
	public Class<NumberItemSelectorCriterion> getItemSelectorCriterionClass() {
		return NumberItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorView<NumberItemSelectorCriterion>>
		getItemSelectorViews(
			NumberItemSelectorCriterion numberItemSelectorCriterion) {

		List<ItemSelectorView<NumberItemSelectorCriterion>> itemSelectorViews =
			new ArrayList<>();

		itemSelectorViews.add(new SquareNumberItemSelectorView());
		itemSelectorViews.add(new CircleNumberItemSelectorView());

		return itemSelectorViews;
	}

}