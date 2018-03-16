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
 * @author Carlos Lancha
 */
public class ViewTypeItemList extends ArrayList<ViewTypeItem> {

	public ViewTypeItemList() {
	}

	public void add(ThrowableConsumer<ViewTypeItem> throwableConsumer) throws Exception {
		ViewTypeItem viewTypeItem = new ViewTypeItem();

		throwableConsumer.accept(viewTypeItem);

		add(viewTypeItem);
	}

	public void addCardViewType(ThrowableConsumer<ViewTypeItem> throwableConsumer)
		throws Exception {
		ViewTypeItem viewTypeItem = new ViewTypeItem();

		viewTypeItem.setIcon("cards2");

		throwableConsumer.accept(viewTypeItem);

		add(viewTypeItem);
	}

	public void addListViewType(ThrowableConsumer<ViewTypeItem> throwableConsumer)
		throws Exception {
		ViewTypeItem viewTypeItem = new ViewTypeItem();

		viewTypeItem.setIcon("list");

		throwableConsumer.accept(viewTypeItem);

		add(viewTypeItem);
	}

	public void addTableViewType(ThrowableConsumer<ViewTypeItem> throwableConsumer)
		throws Exception {
		ViewTypeItem viewTypeItem = new ViewTypeItem();

		viewTypeItem.setIcon("table");

		throwableConsumer.accept(viewTypeItem);

		add(viewTypeItem);
	}

}