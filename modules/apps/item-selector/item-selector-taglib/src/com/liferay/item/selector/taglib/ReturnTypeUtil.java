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

/**
 * @author Roberto Díaz
 */
public class ReturnTypeUtil {

	public static ReturnType parseFirst(Set<ItemSelectorReturnType> values)
		throws Exception {

		for (ItemSelectorReturnType value : values) {
			try {
				return parse(value);
			}
			catch (IllegalArgumentException iae) {
			}
		}

		throw new IllegalArgumentException("Invalid values " + values);
	}

	public static ReturnType parseFirstDraggableReturnType(
			Set<ItemSelectorReturnType> desiredItemSelectorReturnTypes)
		throws Exception {

		Set<ItemSelectorReturnType> itemSelectorReturnTypes = new HashSet<>(
			desiredItemSelectorReturnTypes);

		itemSelectorReturnTypes.retainAll(_draggableReturnTypes);

		return parseFirst(itemSelectorReturnTypes);
	}

	public static ReturnType parseFirstExistingFileReturnType(
			Set<ItemSelectorReturnType> desiredItemSelectorReturnTypes)
		throws Exception {

		Set<ItemSelectorReturnType> itemSelectorReturnTypes = new HashSet<>(
			desiredItemSelectorReturnTypes);

		itemSelectorReturnTypes.retainAll(_existingFileReturnTypes);

		return ReturnTypeUtil.parseFirst(itemSelectorReturnTypes);
	}

	protected static ReturnType parse(
		ItemSelectorReturnType itemSelectorReturnType) {

		if (DefaultItemSelectorReturnType.BASE_64 == itemSelectorReturnType) {
			return ReturnType.BASE_64;
		}

		if (DefaultItemSelectorReturnType.FILE_ENTRY ==
				itemSelectorReturnType) {

			return ReturnType.FILE_ENTRY;
		}

		if (DefaultItemSelectorReturnType.UPLOADABLE_BASE_64 ==
				itemSelectorReturnType) {

			return ReturnType.UPLOADABLE_BASE_64;
		}

		if (DefaultItemSelectorReturnType.URL == itemSelectorReturnType) {
			return ReturnType.URL;
		}

		throw new IllegalArgumentException(
			"Invalid item selector return type " +
				itemSelectorReturnType.getName());
	}

	private static final Set<ItemSelectorReturnType> _draggableReturnTypes =
		new HashSet<>();
	private static final Set<ItemSelectorReturnType> _existingFileReturnTypes =
		new HashSet<>();

	static {
		_draggableReturnTypes.add(DefaultItemSelectorReturnType.BASE_64);
		_draggableReturnTypes.add(
			DefaultItemSelectorReturnType.UPLOADABLE_BASE_64);

		_existingFileReturnTypes.add(DefaultItemSelectorReturnType.FILE_ENTRY);
		_existingFileReturnTypes.add(DefaultItemSelectorReturnType.URL);
	}

}