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

package com.liferay.document.library.jaxrs.util;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
public class AggregateOrderByComparator<T> extends OrderByComparator<T> {

	public AggregateOrderByComparator(List<OrderByComparator<T>> comparators) {
		_comparators = new ArrayList<>(comparators);
	}

	public AggregateOrderByComparator(OrderByComparator<T>... comparators) {
		_comparators = new ArrayList<>(Arrays.asList(comparators));
	}

	public int compare(T o1, T o2) {
		for (OrderByComparator<T> comparator : _comparators) {
			int compare = comparator.compare(o1, o2);

			if (compare != 0) {
				return compare;
			}
		}

		return 0;
	}

	public String getOrderBy() {
		if (_comparators.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(_comparators.size() * 2);

		sb.append(_comparators.get(0).getOrderBy());

		for (int i = 1; i < _comparators.size(); i++) {
			OrderByComparator<T> comparator = _comparators.get(i);

			sb.append(",");

			sb.append(comparator.getOrderBy());
		}

		return sb.toString();
	}

	public Comparator<T> reversed() {
		return Collections.reverseOrder(this);
	}

	private final List<OrderByComparator<T>> _comparators;

}