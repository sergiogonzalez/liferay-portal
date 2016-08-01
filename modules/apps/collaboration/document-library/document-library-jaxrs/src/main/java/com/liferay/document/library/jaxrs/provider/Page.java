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

import java.util.Collection;

/**
 * @author Carlos Sierra Andrés
 */
public class Page<T> {

	public Page(
		Collection<T> items, int totalCount, int currentPage,
		int itemsPerPage) {

		_items = items;
		_totalCount = totalCount;
		_currentPage = currentPage;
		_itemsPerPage = itemsPerPage;
	}

	public int getCurrentPage() {
		return _currentPage;
	}

	public Collection<T> getItems() {
		return _items;
	}

	public int getItemsPerPage() {
		return _itemsPerPage;
	}

	public int getLastPage() {
		return (_totalCount / _itemsPerPage);
	}

	public int getTotalCount() {
		return _totalCount;
	}

	public boolean hasNext() {
		return getLastPage() > _currentPage;
	}

	public boolean hasPrevious() {
		return _currentPage > 1;
	}

	private final int _currentPage;
	private final Collection<T> _items;
	private final int _itemsPerPage;
	private final int _totalCount;

}