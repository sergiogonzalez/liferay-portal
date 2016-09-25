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

package com.liferay.document.library.jaxrs.provider;

import com.liferay.document.library.jaxrs.util.AggregateOrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Carlos Sierra Andrés
 */
public class OrderByComparatorSelectorUtil {

	public static <T> Optional<OrderByComparator<T>> select(
		OrderBySelector orderBySelector,
		Map<String, Function<Boolean, OrderByComparator<T>>> comparatorMap) {

		List<OrderBySelector.FieldOrder> fieldOrders = orderBySelector.select(
			comparatorMap.keySet());

		List<OrderByComparator<T>> orderByComparators = fieldOrders.stream().
			map(fo -> comparatorMap.get(fo.getFieldName()).apply(fo.isAscending())).
			filter(obc -> obc != null).collect(Collectors.toList());

		if (orderByComparators.isEmpty()) {
			return Optional.empty();
		}

		if (orderByComparators.size() == 1) {
			return Optional.of(orderByComparators.get(0));
		}

		return Optional.of(
			new AggregateOrderByComparator<>(orderByComparators));
	}

}