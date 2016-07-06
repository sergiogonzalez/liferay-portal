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

import java.util.List;

/**
 * Provides an interface that determines the type of entity that shall be
 * selected and information to return. The item selector uses the criterion to
 * display only the {@link ItemSelectorView} that can select that particular
 * entity type and return the specified {@link ItemSelectorReturnType}.
 *
 * <p>
 * Implementations of this interface can specify {@link ItemSelectorReturnType}s
 * that include additional fine-grained information. The information should
 * ideally be specified using primitive types (or very simple types that can be
 * serialized using JSON) in the {@link ItemSelectorReturnType} constructor.
 * Note that an empty constructor is mandatory.
 * </p>
 *
 * <p>
 * For simplicity, it is recommended that implementations extend {@link
 * BaseItemSelectorCriterion}.
 * </p>
 *
 * @author Iván Zaera
 */
public interface ItemSelectorCriterion {

	/**
	 * Returns the desired list of return types that the caller expects and can
	 * handle, ordered by preference.
	 *
	 * <p>
	 * The order of return types is important because the first return type that
	 * can be used will be used.
	 * </p>
	 *
	 * @return the return types ordered by preference
	 */
	public List<ItemSelectorReturnType> getDesiredItemSelectorReturnTypes();

	/**
	 * Sets a list of desired return types that the caller expects and can
	 * handle, ordered by preference.
	 *
	 * <p>
	 * The order of return types is important because the first return type that
	 * can be used will be used.
	 * </p>
	 *
	 * @param desiredItemSelectorReturnTypes a preference ordered list of the
	 *        return types the caller can handle
	 */
	public void setDesiredItemSelectorReturnTypes(
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes);

}