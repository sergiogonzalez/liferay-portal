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
import java.util.Optional;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.cxf.jaxrs.ext.MessageContext;

/**
 * @author Carlos Sierra Andrés
 */
@Produces("*/*")
@Provider
public class OptionalBodyWriter implements MessageBodyWriter<Optional<?>> {

	public static Response createEmptyResponse(String resourceName) {
		return Response.
			status(422).
			entity(new ResourceMissingEntity(
				"Not found", resourceName, ""
			)).build();
	}

	public static Class<?> getTargetClass(Class<?> type, Type genericType) {
		if (type.isAssignableFrom(Optional.class)) {
			if (genericType instanceof ParameterizedType) {
				ParameterizedType parameterizedType =
					(ParameterizedType)genericType;

				return (Class<?>)parameterizedType.getActualTypeArguments()[0];
			}
		}

		return null;
	}

	@Override
	public long getSize(
		Optional<?> optional, Class<?> type, Type genericType,
		Annotation[] annotations, MediaType mediaType) {

		//Read MessageBodyWriter#getSize
		return -1;
	}

	@Context
	Providers _providers;

	@Context
	MessageContext _messageContext;

	@Override
	public boolean isWriteable(
		Class<?> type, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		Class<?> targetClass = getTargetClass(type, genericType);

		if (targetClass == null) {
			return false;
		}

		MessageBodyWriter<?> realMessageBodyWriter =
			_providers.getMessageBodyWriter(
				targetClass, targetClass, annotations, mediaType);

		if (realMessageBodyWriter == null) {
			return false;
		}

		return realMessageBodyWriter.isWriteable(
			targetClass, targetClass, annotations, mediaType);
	}

	@Override
	public void writeTo(
			Optional<?> optional, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream)
		throws IOException, WebApplicationException {

		final Class<?> targetClass = getTargetClass(type, genericType);

		if (targetClass == null) {
			return;
		}

		if (!optional.isPresent()) {
			Response response = createEmptyResponse(
				targetClass.getSimpleName());

			throw new WebApplicationException(response);
		}

		MessageBodyWriter<Object> realMessageBodyWriter =
			(MessageBodyWriter<Object>) _providers.getMessageBodyWriter(
				targetClass, targetClass, annotations, mediaType);

		realMessageBodyWriter.writeTo(
			optional.get(), targetClass, targetClass, annotations, mediaType,
			httpHeaders, entityStream);
	}

	@XmlRootElement
	public static class Errors {

		public Errors() {
		}

		public Errors(String resource, String field, String code) {
			_resource = resource;
			_field = field;
			_code = code;
		}

		public String getCode() {
			return _code;
		}

		public String getField() {
			return _field;
		}

		public String getResource() {
			return _resource;
		}

		public void setCode(String code) {
			_code = code;
		}

		public void setField(String field) {
			_field = field;
		}

		public void setResource(String resource) {
			_resource = resource;
		}

		private String _code;
		private String _field;
		private String _resource;

	}

	@XmlRootElement
	private static class ResourceMissingEntity {

		String _message;
		String _resource;
		String _field;

		public ResourceMissingEntity() {
		}

		public ResourceMissingEntity(
			String message, String resource, String field) {

			_message = message;
			_resource = resource;
			_field = field;
		}

		@XmlElement
		public Errors getErrors() {
			return new Errors(_resource, _field, "missing");
		}

		public String getField() {
			return _field;
		}

		public String getMessage() {
			return _message;
		}

		public String getResource() {
			return _resource;
		}

		public void setField(String field) {
			_field = field;
		}

		public void setMessage(String message) {
			_message = message;
		}

		public void setResource(String resource) {
			_resource = resource;
		}

	}

}