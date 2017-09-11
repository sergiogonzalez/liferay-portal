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

package com.liferay.vulcan.identifier;

/**
 * Represents a resource identifier and its context.
 *
 * <p>
 * For example a resource obtained in the URL:
 * <code>/p/customers/2012/products</code> will have an identifier containing
 * <code>2012</code> and <code>"customers"</code> as the ID and type
 * respectively.
 * </p>
 *
 * @author Alejandro Hernández
 */
public interface Identifier {

	/**
	 * Returns the chain of identifiers as an URI.
	 *
	 * @return the chain of identifiers as an URI.
	 */
	public default String asURI() {
		return "/" + getType() + "/" + getId();
	}

	/**
	 * Returns the ID of this identifier.
	 *
	 * <p>
	 * For example with a resource obtained in the URL:
	 * <code>/p/customers/2012</code> this method will return <code>2012</code>.
	 * </p>
	 *
	 * @return the ID of the identifier.
	 */
	public String getId();

	/**
	 * Returns the type of this identifier.
	 *
	 * <p>
	 * For example with a resource obtained in the URL:
	 * <code>/p/customer/2012/product/100</code> this method will return
	 * <code>"product"</code>.
	 * </p>
	 *
	 * @return the type of the identifier.
	 */
	public default String getType() {
		return "";
	}

}