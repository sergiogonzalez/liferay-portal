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

import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletRequest;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Carlos Sierra Andrés
 */
@Provider
public class OrderBySelectorContextProvider
	implements ContextProvider<OrderBySelector> {

	@Override
	public OrderBySelector createContext(Message message) {
		ServletRequest request = (ServletRequest)message.getContextualProperty(
"HTTP.REQUEST");

String[] orders = request.getParameterValues("order");

if (orders == null) {
return availableFields -> Collections.emptyList();
}

return availableFields ->
Arrays.stream(orders).
map(this::parseOrder).
filter(Optional::isPresent).
map(Optional::get).
filter(fo -> availableFields.contains(fo.getFieldName())).
collect(Collectors.toList());
}

protected Optional<OrderBySelector.FieldOrder> parseOrder(String order) {
String[] split = order.split(":");

if (split.length != 2) {
return Optional.empty();
}

String column = split[0];

boolean asc = GetterUtil.getBoolean(split[1], false);

return Optional.of(new OrderBySelector.FieldOrder(column, asc));
}

}