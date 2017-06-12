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

package com.liferay.vulcan.message.json;

import com.liferay.vulcan.list.FunctionalList;
import com.liferay.vulcan.message.RequestInfo;
import com.liferay.vulcan.pagination.Page;

import java.util.List;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public interface PageMessageMapper<T> {

	public String getMediaType();

	public default void mapCollectionURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	public default void mapCurrentPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	public default void mapFirstPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	public default void mapItemEmbeddedResourceField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Object value) {
	}

	public default void mapItemEmbeddedResourceLink(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		String url) {
	}

	public default void mapItemEmbeddedResourceTypes(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, List<String> types) {
	}

	public default void mapItemEmbeddedResourceURL(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {
	}

	public default void mapItemField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName,
		Object value) {
	}

	public default void mapItemLink(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName, String url) {
	}

	public default void mapItemLinkedResourceURL(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {
	}

	public default void mapItemSelfURL(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String url) {
	}

	public default void mapItemTotalCount(
		JSONObjectBuilder jsonObjectBuilder, int totalCount) {
	}

	public default void mapItemTypes(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, List<String> types) {
	}

	public default void mapLastPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	public default void mapNextPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	public default void mapPageCount(
		JSONObjectBuilder jsonObjectBuilder, int count) {
	}

	public default void mapPreviousPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	public default void onFinish(
		JSONObjectBuilder jsonObjectBuilder, Page<T> page, Class<T> modelClass,
		RequestInfo requestInfo) {
	}

	public default void onFinishItem(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, T item, Class<T> modelClass,
		RequestInfo requestInfo) {
	}

	public default void onStart(
		JSONObjectBuilder jsonObjectBuilder, Page<T> page, Class<T> modelClass,
		RequestInfo requestInfo) {
	}

	public default void onStartItem(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, T item, Class<T> modelClass,
		RequestInfo requestInfo) {
	}

	public default boolean supports(
		Page<T> page, Class<T> modelClass, RequestInfo requestInfo) {

		return true;
	}

}