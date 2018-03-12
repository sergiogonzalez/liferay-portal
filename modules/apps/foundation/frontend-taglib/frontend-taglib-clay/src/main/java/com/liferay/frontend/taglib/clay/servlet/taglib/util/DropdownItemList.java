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

import com.liferay.petra.function.UnsafeConsumer;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * @author Brian Wing Shun Chan
 */
public class DropdownItemList extends ArrayList<DropdownItem> {

	public DropdownItemList() {
	}

	public <E extends Exception> void add(UnsafeConsumer<DropdownItem, E> consumer) throws E {
		DropdownItem dropdownItem = new DropdownItem();

		consumer.accept(dropdownItem);

		add(dropdownItem);
	}

	public <E extends Exception> void addCheckbox(UnsafeConsumer<DropdownCheckboxItem, E> consumer) throws E {
		DropdownCheckboxItem dropdownCheckboxItem = new DropdownCheckboxItem();

		consumer.accept(dropdownCheckboxItem);

		add(dropdownCheckboxItem);
	}

	public <E extends Exception> void addGroup(UnsafeConsumer<DropdownGroupItem, E> consumer) throws E {
		DropdownGroupItem dropdownGroupItem = new DropdownGroupItem();

		consumer.accept(dropdownGroupItem);

		add(dropdownGroupItem);
	}

	public <E extends Exception> void addRadio(UnsafeConsumer<DropdownRadioItem, E> consumer) throws E {
		DropdownRadioItem dropdownRadioItem = new DropdownRadioItem();

		consumer.accept(dropdownRadioItem);

		add(dropdownRadioItem);
	}

	public <E extends Exception> void addRadioGroup(UnsafeConsumer<DropdownRadioGroupItem, E> consumer) throws E {
		DropdownRadioGroupItem dropdownRadioGroupItem =
			new DropdownRadioGroupItem();

		consumer.accept(dropdownRadioGroupItem);

		add(dropdownRadioGroupItem);
	}

}