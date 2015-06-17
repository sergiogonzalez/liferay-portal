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

package com.liferay.item.selector.taglib;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.DefaultItemSelectorReturnType;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class ReturnTypeUtilTest {

	@Test
	public void testParseFirstDraggableReturnType() throws Exception {
		Set<ItemSelectorReturnType> itemSelectorReturnTypeSet = new HashSet<>();

		itemSelectorReturnTypeSet.add(DefaultItemSelectorReturnType.BASE_64);
		itemSelectorReturnTypeSet.add(DefaultItemSelectorReturnType.URL);
		itemSelectorReturnTypeSet.add(
			DefaultItemSelectorReturnType.UPLOADABLE_BASE_64);

		ReturnType returnType = ReturnTypeUtil.parseFirstDraggableReturnType(
			itemSelectorReturnTypeSet);

		Assert.assertEquals(ReturnType.BASE_64, returnType);

		Assert.assertEquals(3, itemSelectorReturnTypeSet.size());
	}

	@Test
	public void testParseFirstExistingFileReturnType() throws Exception {
		Set<ItemSelectorReturnType> itemSelectorReturnTypeSet = new HashSet<>();

		itemSelectorReturnTypeSet.add(DefaultItemSelectorReturnType.BASE_64);
		itemSelectorReturnTypeSet.add(DefaultItemSelectorReturnType.URL);
		itemSelectorReturnTypeSet.add(
			DefaultItemSelectorReturnType.UPLOADABLE_BASE_64);

		ReturnType returnType = ReturnTypeUtil.parseFirstExistingFileReturnType(
			itemSelectorReturnTypeSet);

		Assert.assertEquals(ReturnType.URL, returnType);

		Assert.assertEquals(3, itemSelectorReturnTypeSet.size());
	}

}