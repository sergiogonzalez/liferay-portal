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

package com.liferay.vulcan.wiring.osgi.internal.resource;

import com.liferay.vulcan.binary.BinaryFunction;
import com.liferay.vulcan.identifier.Identifier;
import com.liferay.vulcan.pagination.Page;
import com.liferay.vulcan.pagination.SingleModel;
import com.liferay.vulcan.resource.Routes;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Alejandro Hernández
 */
public class RoutesImpl<T> implements Routes<T> {

	@Override
	public Optional<Function<String, BinaryFunction<T>>>
		getBinaryFunctionOptional() {

		return Optional.ofNullable(_binaryFunction);
	}

	@Override
	public Optional<Function<Identifier, Page<T>>> getPageFunctionOptional() {
		return Optional.ofNullable(_pageFunction);
	}

	@Override
	public Optional<Function<Identifier, SingleModel<T>>>
		getSingleModelFunctionOptional() {

		return Optional.ofNullable(_singleModelFunction);
	}

	public void setBinaryFunction(
		Function<String, BinaryFunction<T>> binaryFunction) {

		_binaryFunction = binaryFunction;
	}

	public void setPageFunction(Function<Identifier, Page<T>> pageFunction) {
		_pageFunction = pageFunction;
	}

	public void setSingleModelFunction(
		Function<Identifier, SingleModel<T>> singleModelFunction) {

		_singleModelFunction = singleModelFunction;
	}

	private Function<String, BinaryFunction<T>> _binaryFunction;
	private Function<Identifier, Page<T>> _pageFunction;
	private Function<Identifier, SingleModel<T>> _singleModelFunction;

}