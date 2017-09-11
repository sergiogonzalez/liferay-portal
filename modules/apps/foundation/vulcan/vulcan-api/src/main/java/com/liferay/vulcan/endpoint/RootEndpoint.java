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

package com.liferay.vulcan.endpoint;

import com.liferay.vulcan.pagination.Page;
import com.liferay.vulcan.pagination.SingleModel;
import com.liferay.vulcan.result.Try;

import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Declares the endpoint from which all of your APIs originate.
 *
 * <p>
 * There should only be one RootEndpoint in the application.
 * </p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public interface RootEndpoint {

	/**
	 * Returns the {@link InputStream} for a given resource identifier or an
	 * exception if an error occurred.
	 *
	 * @param  path the path from the URL.
	 * @param  id the ID of the resource.
	 * @param  binaryId the ID to the binary resource.
	 * @return the input stream of the binary file, or an exception it there was
	 *         an error.
	 */
	@GET
	@Path("/b/{path}/{id}/{binaryId}")
	public <T> Try<InputStream> getCollectionItemInputStreamTry(
		@PathParam("path") String path, @PathParam("id") String id,
		@PathParam("binaryId") String binaryId);

	/**
	 * Returns the {@link SingleModel} for a given path or an exception if an
	 * error occurred.
	 *
	 * @param  path the path from the URL.
	 * @param  id the ID of the resource.
	 * @return the single model at the path, or an exception it there was an
	 *         error.
	 */
	@GET
	@Path("/p/{path}/{id}")
	public <T> Try<SingleModel<T>> getCollectionItemSingleModelTry(
		@PathParam("path") String path, @PathParam("id") String id);

	/**
	 * Returns the collection {@link Page} for a given path or an exception if
	 * an error occurred.
	 *
	 * @param  path the path from the URL.
	 * @return the collection page at the path, or an exception if there was an
	 *         error.
	 */
	@GET
	@Path("/p/{path}")
	public <T> Try<Page<T>> getCollectionPageTry(
		@PathParam("path") String path);

	/**
	 * Returns a nested collection {@link Page} for a given set of
	 * path-id-nestedPath or an exception if an error occurred.
	 *
	 * @param  path the path from the URL.
	 * @param  id the ID of the resource.
	 * @param  nestedPath the path of the nested resource.
	 * @return the collection page at the path, or an exception if there was an
	 *         error.
	 */
	@GET
	@Path("/p/{path}/{id}/{nestedPath}")
	public <T> Try<Page<T>> getNestedCollectionPageTry(
		@PathParam("path") String path, @PathParam("id") String id,
		@PathParam("nestedPath") String nestedPath);

}