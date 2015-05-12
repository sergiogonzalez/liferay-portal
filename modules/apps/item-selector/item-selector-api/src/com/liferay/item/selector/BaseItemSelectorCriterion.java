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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Iván Zaera
 */
public abstract class BaseItemSelectorCriterion
	implements ItemSelectorCriterion {

	@Override
	public List<Class<?>> getAvailableReturnTypes() {
		return _availableReturnTypes;
	}

	@Override
	public List<Class<?>> getDesiredReturnTypes() {
		return _desiredReturnTypes;
	}

	@Override
	public void setDesiredReturnTypes(Class<?>... desiredReturnTypes) {
		List<Class<?>> desiredReturnTypesList = new ArrayList<>();

		Collections.addAll(desiredReturnTypesList, desiredReturnTypes);

		if (!_availableReturnTypes.containsAll(desiredReturnTypesList)) {
			throw new IllegalArgumentException(
				"Desired return types must be a list of available return " +
					"types");
		}

		_desiredReturnTypes = desiredReturnTypesList;
	}

	protected static List<Class<?>> getInmutableList(Class<?>... classes) {
		List<Class<?>> list = new ArrayList<>();

		Collections.addAll(list, classes);

		return Collections.unmodifiableList(list);
	}

	protected BaseItemSelectorCriterion(List<Class<?>> availableReturnTypes) {
		_availableReturnTypes = availableReturnTypes;
		_desiredReturnTypes = _availableReturnTypes;
	}

	private final List<Class<?>> _availableReturnTypes;
	private List<Class<?>> _desiredReturnTypes;

}