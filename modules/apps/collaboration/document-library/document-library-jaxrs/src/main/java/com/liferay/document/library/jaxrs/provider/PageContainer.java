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

/**
 * @author Carlos Sierra Andrés
 */
public class PageContainer<T> {

	public PageContainer(
		T container, int totalCount, int currentPage, int itemsPerPage) {

		_container = container;
		_totalCount = totalCount;
		_currentPage = currentPage;
		_itemsPerPage = itemsPerPage;
	}

	public T getContainer() {
		return _container;
	}

	public int getCurrentPage() {
		return _currentPage;
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
		if (getLastPage() > _currentPage) {
			return true;
		}

		return false;
	}

	public boolean hasPrevious() {
		if (_currentPage > 1) {
			return true;
		}

		return false;
	}

	private final T _container;
	private final int _currentPage;
	private final int _itemsPerPage;
	private final int _totalCount;

}