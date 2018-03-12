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

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import com.liferay.portal.kernel.json.JSON;

import com.liferay.petra.function.UnsafeConsumer;

import java.io.Serializable;

import java.util.function.Consumer;

/**
 * @author Carlos Lancha
 */
public class CreationMenu implements Serializable {

 	public CreationMenu() {
	}

  public <E extends Exception> void addDropdownItemList(UnsafeConsumer<DropdownItemList, E> consumer) throws E {
    DropdownItemList dropdownItemList = new DropdownItemList();

    consumer.accept(dropdownItemList);

    _dropdownItemList = dropdownItemList;
  }

	@JSON(name = "items")
  public DropdownItemList getDropdownItems() {
    return _dropdownItemList;
  }

  private DropdownItemList _dropdownItemList = new DropdownItemList();

}