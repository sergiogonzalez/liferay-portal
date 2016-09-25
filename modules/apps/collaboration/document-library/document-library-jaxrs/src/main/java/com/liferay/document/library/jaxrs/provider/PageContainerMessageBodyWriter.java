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

import java.io.IOException;
import java.io.OutputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

/**
 * @author Carlos Sierra Andrés
 */
@Provider
public class PageContainerMessageBodyWriter
	implements MessageBodyWriter<PageContainer<?>> {

	@Override
	public boolean isWriteable(
		Class<?> type, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		if (!(PageContainer.class.isAssignableFrom(type))) {
			return false;
		}

		Class<?> targetClass = null;

		if (genericType instanceof ParameterizedType) {
			ParameterizedType parameterizedType =
				(ParameterizedType)genericType;

			Type targetType = parameterizedType.getActualTypeArguments()[0];

			if (targetType instanceof ParameterizedType) {
				ParameterizedType para = (ParameterizedType)targetType;

				targetClass = (Class<?>)para.getRawType();
				genericType = para;
			}
			else {
				targetClass = (Class<?>)targetType;
				genericType = targetType;
			}
		}

		if (targetClass == null) {
			return false;
		}

		return (_providers.getMessageBodyWriter(
			targetClass, genericType, annotations, mediaType) != null);
	}

	@Override
	public long getSize(
		PageContainer<?> pageContainer, Class<?> type, Type genericType,
		Annotation[] annotations, MediaType mediaType) {

		return -1;
	}

	@Override
	public void writeTo(
			PageContainer<?> pageContainer, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream)
		throws IOException, WebApplicationException {

		List<Object> linkHeader = new ArrayList<>();

		int currentPage = pageContainer.getCurrentPage();

		UriBuilder requestUriBuilder = _uriInfo.getRequestUriBuilder().
			replaceQueryParam("per_page", pageContainer.getItemsPerPage());

		if (pageContainer.hasNext()) {
			UriBuilder builder = requestUriBuilder.clone();

			linkHeader.add(
				link(
					builder.replaceQueryParam(
						"page", currentPage + 1), "next"));

			linkHeader.add(
				link(
					builder.replaceQueryParam(
						"page", pageContainer.getLastPage()), "last"));
		}

		if (pageContainer.hasPrevious()) {
			UriBuilder builder = requestUriBuilder.clone();

			linkHeader.add(
				link(
					builder.replaceQueryParam(
						"page", currentPage - 1), "prev"));

			linkHeader.add(link(builder.replaceQueryParam("page", 1), "first"));
		}

		httpHeaders.addAll("Link", linkHeader);

		ParameterizedType parameterizedType = (ParameterizedType)genericType;

		Type targetType = parameterizedType.getActualTypeArguments()[0];

		Class<Object> targetClass = null;

		if (targetType instanceof ParameterizedType) {
			ParameterizedType para = (ParameterizedType)targetType;

			targetClass = (Class<Object>)para.getRawType();
			genericType = para;
		}
		else {
			targetClass = (Class<Object>)targetType;
			genericType = targetType;
		}

		@SuppressWarnings("rawtypes")
		MessageBodyWriter<Object> messageBodyWriter =
			_providers.getMessageBodyWriter(
				targetClass, genericType, annotations, mediaType);

		messageBodyWriter.writeTo(
			pageContainer.getContainer(), targetClass, genericType, annotations,
			mediaType, httpHeaders, entityStream);
	}

	private String link(UriBuilder uriBuilder, String rel) {

		String uri = uriBuilder.build().toString();

		return "<link href=\"" + uri + "\" rel=\"" + rel + "\" />";
	}

	@Context
	Providers _providers;

	@Context
	UriInfo _uriInfo;
}