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

package com.liferay.vulcan.liferay.internal;

import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.vulcan.contributor.ResourceMapper;
import com.liferay.vulcan.liferay.scope.GroupScoped;
import com.liferay.vulcan.resource.Resource;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public class LiferayDispatcherResource implements Resource {

	public LiferayDispatcherResource(ResourceMapper resourceMapper) {
		_resourceMapper = resourceMapper;
	}

	@Path("/{path}")
	public Resource getResource(@PathParam("path") String path) {
		_resourceMapper.mapResources(_resources::put);

		if (!_resources.containsKey(path)) {
			throw new NotFoundException();
		}

		Resource resource = _resources.get(path);

		resourceContext.initResource(resource);

		if (resource instanceof GroupScoped) {
			GroupScoped groupScoped = (GroupScoped)resource;

			groupScoped.setGroupId(GroupThreadLocal.getGroupId());
		}

		return resource;
	}

	@Context
	protected ResourceContext resourceContext;

	private final ResourceMapper _resourceMapper;
	private final Map<String, Resource> _resources = new HashMap<>();

}