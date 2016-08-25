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

package com.liferay.item.selector.test;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnTypeProvider;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component
public class TestItemSelectorReturnTypeProvider
	implements ItemSelectorReturnTypeProvider {

	public List<String> getItemSelectorViewKeys() {
		return ListUtil.fromArray(new String[] {"test-view"});
	}

	public List<ItemSelectorReturnType> populateItemSelectorReturnTypes(
		List<ItemSelectorReturnType> itemSelectorReturnTypes) {

		itemSelectorReturnTypes.clear();

		itemSelectorReturnTypes.add(new TestItemSelectorReturnType());

		return itemSelectorReturnTypes;
	}

}