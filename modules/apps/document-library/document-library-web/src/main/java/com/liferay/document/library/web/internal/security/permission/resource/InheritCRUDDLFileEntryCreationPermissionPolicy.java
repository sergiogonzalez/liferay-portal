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

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = "file.entry.creation.permission.policy.name=crud",
	service = DLFileEntryCreationPermissionPolicy.class
)
public class InheritCRUDDLFileEntryCreationPermissionPolicy
	implements DLFileEntryCreationPermissionPolicy {

	@Override
	public ModelPermissions getModelPermissions(long groupId, long folderId)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		long primKey = 0;
		String modelResource = null;

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			primKey = folderId;

			modelResource = DLFolderConstants.getClassName();
		}
		else {
			primKey = groupId;

			modelResource = "com.liferay.document.library";
		}

		ModelPermissions modelPermissions = new ModelPermissions();

		_addDLFolderModelPermissions(
			group.getCompanyId(), primKey, modelResource, modelPermissions);

		_addDLFileEntryGroupDefaultModelPermissions(modelPermissions);

		_addDLFileEntryGuestDefaultModelPermissions(modelPermissions);

		return modelPermissions;
	}

	private void _addDLFileEntryGroupDefaultModelPermissions(
		ModelPermissions modelPermissions) {

		List<String> groupDefaultActions =
			ResourceActionsUtil.getModelResourceGroupDefaultActions(
				DLFileEntryConstants.getClassName());

		Stream<String> stream = groupDefaultActions.stream();

		String[] filteredGroupDefaultActions = stream.filter(
			guestDefaultAction -> !_resourceActions.contains(guestDefaultAction)
		).toArray(
			value -> new String[value]
		);

		modelPermissions.addRolePermissions(
			RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE,
			filteredGroupDefaultActions);
	}

	private void _addDLFileEntryGuestDefaultModelPermissions(
		ModelPermissions modelPermissions) {

		List<String> guestDefaultActions =
			ResourceActionsUtil.getModelResourceGuestDefaultActions(
				DLFileEntryConstants.getClassName());

		Stream<String> stream = guestDefaultActions.stream();

		String[] filteredGuestDefaultActions = stream.filter(
			guestDefaultAction -> !_resourceActions.contains(guestDefaultAction)
		).toArray(
			value -> new String[value]
		);

		modelPermissions.addRolePermissions(
			RoleConstants.GUEST, filteredGuestDefaultActions);
	}

	private void _addDLFolderModelPermissions(
			long companyId, long primKey, String modelResource,
			ModelPermissions modelPermissions)
		throws PortalException {

		Map<Long, Set<String>> availableResourcePermissionActionIds =
			_resourcePermissionLocalService.
				getAvailableResourcePermissionActionIds(
					companyId, modelResource,
					ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(primKey),
					_resourceActions);

		for (Map.Entry<Long, Set<String>> entry :
				availableResourcePermissionActionIds.entrySet()) {

			Set<String> actionIds = entry.getValue();

			Role role = _roleLocalService.getRole(entry.getKey());

			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			modelPermissions.addRolePermissions(
				role.getName(),
				actionIds.toArray(new String[actionIds.size()]));
		}
	}

	@Reference
	private GroupLocalService _groupLocalService;

	private final List<String> _resourceActions = Arrays.asList(
		ActionKeys.DELETE, ActionKeys.UPDATE, ActionKeys.VIEW);

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}