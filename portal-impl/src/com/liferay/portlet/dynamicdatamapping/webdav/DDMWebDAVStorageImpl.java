/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.webdav;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webdav.BaseWebDAVStorageImpl;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Fernández
 */
public class DDMWebDAVStorageImpl extends BaseWebDAVStorageImpl {

	public Resource getResource(WebDAVRequest webDavRequest)
		throws WebDAVException {

		String[] pathArray = webDavRequest.getPathArray();

		if (pathArray.length == 4) {
			String type = pathArray[2];
			String typeId = pathArray[3];

			if (type.equals(_TYPE_STRUCTURES)) {
				try {
					DDMStructure structure =
						DDMStructureLocalServiceUtil.getStructure(
							Long.valueOf(typeId));

					return toResource(webDavRequest, structure, false);
				}
				catch (Exception e) {
					return null;
				}
			}
		}

		return null;
	}

	public List<Resource> getResources(WebDAVRequest webDavRequest)
			throws WebDAVException {

		try {
			String[] pathArray = webDavRequest.getPathArray();

			if (pathArray.length == 3) {
				String type = pathArray[2];

				if (type.equals(_TYPE_STRUCTURES)) {
					return getStructures(webDavRequest);
				}
			}

			return new ArrayList<Resource>();
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	protected List<Resource> getStructures(WebDAVRequest webDavRequest)
			throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		long groupId = webDavRequest.getGroupId();

		List<DDMStructure> structures =
			DDMStructureLocalServiceUtil.getStructures(groupId);

		for (DDMStructure structure : structures) {
			Resource resource = toResource(webDavRequest, structure, true);

			resources.add(resource);
		}

		return resources;
	}

	protected Resource toResource(
		WebDAVRequest webDavRequest, DDMStructure structure,
		boolean appendPath) {

		String parentPath = getRootPath() + webDavRequest.getPath();
		String name = StringPool.BLANK;

		if (appendPath) {
			name = structure.getName();
		}

		return new DDMStructureResourceImpl(structure, parentPath, name);
	}

	private static final String _TYPE_STRUCTURES = "ddmStructures";

}