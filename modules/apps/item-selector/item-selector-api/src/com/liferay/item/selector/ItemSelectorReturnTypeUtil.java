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

import java.util.Set;

/**
 * @author Sergio González
 */
public class ItemSelectorReturnTypeUtil {

	public static boolean contains(
		Set<ItemSelectorReturnType> itemSelectorReturnTypes,
		ItemSelectorReturnType itemSelectorReturnType) {

		for (ItemSelectorReturnType curItemSelectorReturnType :
				itemSelectorReturnTypes) {

			if (equals(curItemSelectorReturnType, itemSelectorReturnType)) {
				return true;
			}
		}

		return false;
	}

	public static boolean equals(
		ItemSelectorReturnType itemSelectorReturnType1,
		ItemSelectorReturnType itemSelectorReturnType2) {

		Class<? extends ItemSelectorReturnType> itemSelectorReturnType1Class =
			itemSelectorReturnType1.getClass();
		Class<? extends ItemSelectorReturnType> itemSelectorReturnType2Class =
			itemSelectorReturnType2.getClass();

		if (itemSelectorReturnType1Class.getName().equals(
				itemSelectorReturnType2Class.getName())) {

			return true;
		}

		return false;
	}

}