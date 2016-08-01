/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.document.library.jaxrs.provider;

import com.liferay.document.library.jaxrs.GroupRepr;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Providers;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Carlos Sierra Andrés
 */
public abstract class DelegatingMessageBodyWriter<F, T> implements MessageBodyWriter<Object> {

	protected abstract Class<F> getOriginClass();

	protected abstract Class<T> getDestination();

	public boolean isWriteable(
		Class<?> type, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		return getOriginClass().isAssignableFrom(type) ||
		   Collection.class.isAssignableFrom(type) &&
		   ((Class<?>)((ParameterizedType)genericType).getActualTypeArguments()[0]).isAssignableFrom(getOriginClass());
	}

	public long getSize(
		Object group, Class<?> type, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		return -1;
	}

	public void writeTo(
		Object object, Class<?> type, Type genericType, Annotation[] annotations,
		MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
		OutputStream entityStream) throws IOException, WebApplicationException {

		if (Collection.class.isAssignableFrom(type)) {
			MessageBodyWriter collectionMessageBodyWriter =
				_providers.getMessageBodyWriter(
					Collection.class,
					new ParameterizedType() {
						public Type[] getActualTypeArguments() {
							return new Class[]{getDestination()};
						}

						public Type getRawType() {
							return Collection.class;
						}

						public Type getOwnerType() {
							return null;
						}
					},
					annotations, mediaType);

			Collection<Object> collection = (Collection)object;

			collection = collection.stream().map(getOriginClass()::cast).map(
				this::map).collect(Collectors.toList());

			collectionMessageBodyWriter.writeTo(
				collection, Collection.class,
					GroupRepr.class, annotations, mediaType,
				httpHeaders, entityStream);
		}
		else {
			MessageBodyWriter messageBodyWriter =
				_providers.getMessageBodyWriter(
					GroupRepr.class, type, annotations, mediaType);

			Object destination = map((F)object);

			messageBodyWriter.writeTo(
				destination, getDestination(), type, annotations,
				mediaType, httpHeaders, entityStream);
		}
	}

	protected abstract T map(F object);

	@Context
	Providers _providers;

	@Context
	HttpServletRequest _httpServletRequest;
}
