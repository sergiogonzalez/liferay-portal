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

package com.liferay.vulcan.identifier.converter;

import com.liferay.vulcan.identifier.Identifier;

/**
 * Instances of this interface will be used to convert from a generic identifier
 * to a domain implementation.
 *
 * The class of the identifier can then be provided as a parameter in
 * {@link com.liferay.vulcan.resource.builder.RoutesBuilder} methods.
 *
 * @author Alejandro Hernández
 */
public interface IdentifierConverter<T extends Identifier> {

	/**
	 * Converts a generic {@code Identifier} to a domain implementation.
	 *
	 * @param  identifier the generic identifier.
	 * @return a domain {@code Identifier} implementation;
	 */
	public T convert(Identifier identifier);

}