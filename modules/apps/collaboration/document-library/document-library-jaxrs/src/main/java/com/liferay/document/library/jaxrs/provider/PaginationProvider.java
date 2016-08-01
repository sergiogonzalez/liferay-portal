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

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Carlos Sierra Andrés
 */
@Provider
public class PaginationProvider implements ContextProvider<Pagination> {

	public static final int DEFAULT_ITEMS_PER_PAGE = 30;

	public static final int DEFAULT_PAGE = 1;

	@Override
	public Pagination createContext(Message message) {
		String queryString = (String)message.getContextualProperty(
			Message.QUERY_STRING);

		Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
			queryString);

		int itemsPerPage = MapUtil.getInteger(
			parameterMap, "per_page", DEFAULT_ITEMS_PER_PAGE);

		int page = MapUtil.getInteger(parameterMap, "page", DEFAULT_PAGE);

		return new DefaultPagination(page, itemsPerPage);
	}

	private static class DefaultPagination implements Pagination {
		public DefaultPagination(int page, int itemsPerPage) {
			_page = page;
			_itemsPerPage = itemsPerPage;
		}

		@Override
		public <T> Page<T> createPage(Collection<T> items, int totalItems) {
			return new Page<>(items, totalItems, getPage(), getItemsPerPage());
		}

		public int getEndPosition() {
			return ((_page) * _itemsPerPage);
		}

		public int getItemsPerPage() {
			return _itemsPerPage;
		}

		public int getPage() {
			return _page;
		}

		public int getStartPosition() {
			return (_page - 1) * _itemsPerPage;
		}

		private final int _itemsPerPage;
		private final int _page;

	}

}