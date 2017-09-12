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

package com.liferay.vulcan.application.internal.endpoint;

import com.liferay.vulcan.application.internal.identifier.IdentifierImpl;
import com.liferay.vulcan.application.internal.identifier.RootIdentifierImpl;
import com.liferay.vulcan.binary.BinaryFunction;
import com.liferay.vulcan.endpoint.RootEndpoint;
import com.liferay.vulcan.pagination.Page;
import com.liferay.vulcan.pagination.SingleModel;
import com.liferay.vulcan.resource.Routes;
import com.liferay.vulcan.result.Try;
import com.liferay.vulcan.wiring.osgi.manager.ResourceManager;

import java.io.InputStream;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class RootEndpointImpl implements RootEndpoint {

	@Override
	public <T> Try<InputStream> getCollectionItemInputStreamTry(
		String path, String id, String binaryId) {

		Try<Routes<T>> routesTry = _getRoutesTry(path);

		return routesTry.map(
			Routes::getBinaryFunctionOptional
		).map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class,
			_getSupplierNotFoundException(path + "/" + id + "/" + binaryId)
		).map(
			binaryFunction -> binaryFunction.apply(binaryId)
		).flatMap(
			binaryFunction -> _getInputStreamTry(path, id, binaryFunction)
		);
	}

	@Override
	public <T> Try<SingleModel<T>> getCollectionItemSingleModelTry(
		String path, String id) {

		Try<Routes<T>> routesTry = _getRoutesTry(path);

		return routesTry.map(
			Routes::getSingleModelFunctionOptional
		).map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class,
			_getSupplierNotFoundException(path + "/" + id)
		).map(
			singleModelFunction -> singleModelFunction.apply(
				new IdentifierImpl(path, id))
		);
	}

	@Override
	public <T> Try<Page<T>> getCollectionPageTry(String path) {
		Try<Routes<T>> routesTry = _getRoutesTry(path);

		return routesTry.map(
			Routes::getPageFunctionOptional
		).map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class, _getSupplierNotFoundException(path)
		).map(
			function -> function.apply(new RootIdentifierImpl())
		);
	}

	@Override
	public <T> Try<Page<T>> getNestedCollectionPageTry(
		String path, String id, String nestedPath) {

		Try<Routes<T>> routesTry = _getRoutesTry(nestedPath);

		return routesTry.map(
			Routes::getPageFunctionOptional
		).map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class,
			_getSupplierNotFoundException(path + "/" + id + "/" + nestedPath)
		).map(
			pageFunction -> pageFunction.apply(new IdentifierImpl(path, id))
		);
	}

	private <T> Try<InputStream> _getInputStreamTry(
		String path, String id, BinaryFunction<T> binaryFunction) {

		Try<SingleModel<T>> singleModelTry = getCollectionItemSingleModelTry(
			path, id);

		return singleModelTry.map(
			SingleModel::getModel
		).map(
			binaryFunction::apply
		);
	}

	private <T> Try<Routes<T>> _getRoutesTry(String path) {
		Try<Optional<Routes<T>>> optionalTry = Try.success(
			_resourceManager.getRoutes(path, _httpServletRequest));

		return optionalTry.map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class,
			() -> new NotFoundException("No resource found for path " + path)
		);
	}

	private Supplier<NotFoundException> _getSupplierNotFoundException(
		String path) {

		return () -> new NotFoundException("No endpoint found at path " + path);
	}

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference
	private ResourceManager _resourceManager;

}