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
public class NavigationItemList extends ArrayList<NavigationItem> {

	public NavigationItemList() {
	}

	public <E extends Exception> void add(UnsafeConsumer<NavigationItem, E> consumer) throws E {
		NavigationItem navigationItem = new NavigationItem();

		consumer.accept(navigationItem);

		add(navigationItem);
	}

}