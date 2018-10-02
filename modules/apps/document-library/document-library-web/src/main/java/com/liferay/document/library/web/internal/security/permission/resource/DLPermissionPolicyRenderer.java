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

package com.liferay.document.library.web.internal.security.permission.resource;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sergio González
 */
public class DLPermissionPolicyRenderer {

	public DLPermissionPolicyRenderer(
		String namespace, ModelPermissions modelPermissions) {

		_namespace = namespace;
		_modelPermissions = modelPermissions;
	}

	public void renderModelPermissions(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		PrintWriter printWriter = response.getWriter();

		StringBundler sb = new StringBundler();

		for (String roleName : _modelPermissions.getRoleNames()) {
			String[] actionIds = _modelPermissions.getActionIds(roleName);

			for (String actionId : actionIds) {
				sb.append("<input name=\"");
				sb.append(_namespace);
				sb.append(ModelPermissionsFactory.MODEL_PERMISSIONS_PREFIX);
				sb.append(roleName);
				sb.append("\" type=\"hidden\" value=\"");
				sb.append(actionId);
				sb.append("\" />");
			}
		}

		printWriter.println(sb);
	}

	private final ModelPermissions _modelPermissions;
	private final String _namespace;

}