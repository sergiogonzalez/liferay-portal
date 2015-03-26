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

package com.liferay.item.selector.number;

import com.liferay.document.selector.BaseItemSelectorCriterion;

import java.util.Set;

/**
 * @author Iván Zaera
 */
public class NumberItemSelectorCriterion extends BaseItemSelectorCriterion {

	public NumberItemSelectorCriterion() {
		super(_AVAILABLE_RETURN_TYPES);
	}

	public int getMaxValue() {
		return _maxValue;
	}

	public int getMinValue() {
		return _minValue;
	}

	public void setMaxValue(int maxValue) {
		_maxValue = maxValue;
	}

	public void setMinValue(int minValue) {
		_minValue = minValue;
	}

	private static final Set<Class<?>> _AVAILABLE_RETURN_TYPES =
		getInmutableSet(Integer.class, String.class);

	private int _maxValue = 10;
	private int _minValue = 1;

}