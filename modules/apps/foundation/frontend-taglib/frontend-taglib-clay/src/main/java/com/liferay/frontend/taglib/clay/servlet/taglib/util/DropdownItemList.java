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

import java.util.ArrayList;

/**
 * @author Brian Wing Shun Chan
 */
public class DropdownItemList extends ArrayList<DropdownItem> {

	public DropdownItemList() {
	}

	public void add(ThrowableConsumer<DropdownItem> throwableConsumer)
		throws Exception {

		DropdownItem dropdownItem = new DropdownItem();

		throwableConsumer.accept(dropdownItem);

		add(dropdownItem);
	}

	public void addCheckbox(
			ThrowableConsumer<DropdownCheckboxItem> throwableConsumer)
		throws Exception {

		DropdownCheckboxItem dropdownCheckboxItem = new DropdownCheckboxItem();

		throwableConsumer.accept(dropdownCheckboxItem);

		add(dropdownCheckboxItem);
	}

	public void addGroup(ThrowableConsumer<DropdownGroupItem> throwableConsumer)
		throws Exception {

		DropdownGroupItem dropdownGroupItem = new DropdownGroupItem();

		throwableConsumer.accept(dropdownGroupItem);

		add(dropdownGroupItem);
	}

	public void addRadio(ThrowableConsumer<DropdownRadioItem> throwableConsumer)
		throws Exception {

		DropdownRadioItem dropdownRadioItem = new DropdownRadioItem();

		throwableConsumer.accept(dropdownRadioItem);

		add(dropdownRadioItem);
	}

	public void addRadioGroup(
			ThrowableConsumer<DropdownRadioGroupItem> throwableConsumer)
		throws Exception {

		DropdownRadioGroupItem dropdownRadioGroupItem =
			new DropdownRadioGroupItem();

		throwableConsumer.accept(dropdownRadioGroupItem);

		add(dropdownRadioGroupItem);
	}

}