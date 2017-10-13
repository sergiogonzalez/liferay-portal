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

package com.liferay.vulcan.exception;

/**
 * This exception will be returned inside a {@link
 * com.liferay.vulcan.result.Try} when {@link
 * com.liferay.vulcan.result.Try#filter(Predicate)} predicate returns
 * {@code false}.
 *
 * @author Alejandro Hernández
 * @review
 */
public class FalsePredicateException extends Exception {

	public FalsePredicateException(Object value) {
		super("Predicate does not match for " + value);
	}

}