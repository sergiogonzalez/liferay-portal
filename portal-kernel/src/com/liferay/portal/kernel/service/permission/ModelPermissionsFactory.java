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

package com.liferay.portal.kernel.service.permission;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jorge Ferrer
 */
public class ModelPermissionsFactory {

	public static final String MODEL_PERMISSIONS_PREFIX = "modelPermissions";

	public static ModelPermissions create(HttpServletRequest request) {
		return _createModelPermissions(request.getParameterMap(), null);
	}

	public static ModelPermissions create(
		HttpServletRequest request, String className) {

		return _createModelPermissions(request.getParameterMap(), className);
	}

	public static ModelPermissions create(
		Map<String, String[]> modelPermissionsParameterMap) {

		ModelPermissions modelPermissions = null;

		for (Map.Entry<String, String[]> entry :
				modelPermissionsParameterMap.entrySet()) {

			Role role = RoleLocalServiceUtil.fetchRole(
				CompanyThreadLocal.getCompanyId(), entry.getKey());

			if (_log.isInfoEnabled()) {
				_log.info("Unable to get role " + entry.getKey());

				continue;
			}

			if (modelPermissions == null) {
				modelPermissions = new ModelPermissions();
			}

			modelPermissions.addRolePermissions(
				role.getName(), entry.getValue());
		}

		return modelPermissions;
	}

	public static ModelPermissions create(PortletRequest portletRequest) {
		return _createModelPermissions(portletRequest.getParameterMap(), null);
	}

	public static ModelPermissions create(
		PortletRequest portletRequest, String className) {

		return _createModelPermissions(
			portletRequest.getParameterMap(), className);
	}

	public static ModelPermissions create(
		String[] groupPermissions, String[] guestPermissions) {

		ModelPermissions modelPermissions = new ModelPermissions();

		modelPermissions.addRolePermissions(
			RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE, groupPermissions);
		modelPermissions.addRolePermissions(
			RoleConstants.GUEST, guestPermissions);

		return modelPermissions;
	}

	public static ModelPermissions createWithDefaultPermissions(
		String className) {

		ModelPermissions modelPermissions = new ModelPermissions();

		List<String> modelResourceGroupDefaultActions =
			ResourceActionsUtil.getModelResourceGroupDefaultActions(className);

		modelPermissions.addRolePermissions(
			RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE,
			modelResourceGroupDefaultActions.toArray(
				new String[modelResourceGroupDefaultActions.size()]));

		List<String> modelResourceGuestDefaultActions =
			ResourceActionsUtil.getModelResourceGuestDefaultActions(className);

		modelPermissions.addRolePermissions(
			RoleConstants.GUEST,
			modelResourceGuestDefaultActions.toArray(
				new String[modelResourceGuestDefaultActions.size()]));

		return modelPermissions;
	}

	private static String _addClassNamePostfix(
		String parameterName, String className) {

		if (Validator.isNull(className)) {
			return parameterName;
		}

		return parameterName + StringPool.UNDERLINE + className;
	}

	private static ModelPermissions _createModelPermissions(
		Map<String, String[]> parameterMap, String className) {

		Map<String, String[]> modelPermissionsParameterMap =
			_getModelPermissionsParameterMap(parameterMap, className);

		if (!modelPermissionsParameterMap.isEmpty()) {
			return create(modelPermissionsParameterMap);
		}

		String[] groupPermissions = parameterMap.get(
			_addClassNamePostfix("groupPermissions", className));
		String[] guestPermissions = parameterMap.get(
			_addClassNamePostfix("guestPermissions", className));

		if ((groupPermissions != null) || (guestPermissions != null)) {
			return create(groupPermissions, guestPermissions);
		}

		if (Validator.isNull(className)) {
			return null;
		}

		return createWithDefaultPermissions(className);
	}

	private static Map<String, String[]> _getModelPermissionsParameterMap(
		Map<String, String[]> parameterMap, String className) {

		Map<String, String[]> modelPermissionsParameterMap = new HashMap<>();

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String parameterName = entry.getKey();

			if (!parameterName.startsWith(MODEL_PERMISSIONS_PREFIX)) {
				continue;
			}

			parameterName = parameterName.substring(
				MODEL_PERMISSIONS_PREFIX.length());

			if (Validator.isNotNull(className)) {
				if (!parameterName.startsWith(className)) {
					continue;
				}

				parameterName = parameterName.substring(className.length());
			}

			modelPermissionsParameterMap.put(parameterName, entry.getValue());
		}

		return modelPermissionsParameterMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModelPermissionsFactory.class);

}