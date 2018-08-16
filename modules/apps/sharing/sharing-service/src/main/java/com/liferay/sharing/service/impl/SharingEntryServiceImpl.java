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

package com.liferay.sharing.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.sharing.constants.SharingEntryActionKey;
import com.liferay.sharing.internal.util.SharingPermissionCheckerTracker;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingPermissionChecker;
import com.liferay.sharing.service.base.SharingEntryServiceBaseImpl;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author Sergio González
 */
public class SharingEntryServiceImpl extends SharingEntryServiceBaseImpl {

	@Override
	public SharingEntry addSharingEntry(
			long toUserId, long classNameId, long classPK, long groupId,
			Collection<SharingEntryActionKey> sharingEntryActionKeys,
			ServiceContext serviceContext)
		throws PortalException {

		_checkSharingPermission(
			getUserId(), classNameId, classPK, groupId, sharingEntryActionKeys);

		return sharingEntryLocalService.addSharingEntry(
			getUserId(), toUserId, classNameId, classPK, groupId,
			sharingEntryActionKeys, serviceContext);
	}

	@ServiceReference(type = ClassNameLocalService.class)
	protected ClassNameLocalService classNameLocalService;

	@ServiceReference(type = SharingPermissionCheckerTracker.class)
	protected SharingPermissionCheckerTracker sharingPermissionCheckerTracker;

	private void _checkSharingPermission(
			long fromUserId, long classNameId, long classPK, long groupId,
			Collection<SharingEntryActionKey> sharingEntryActionKeys)
		throws PortalException {

		SharingPermissionChecker sharingPermissionChecker =
			sharingPermissionCheckerTracker.getSharingPermissionChecker(
				classNameId);

		if (sharingPermissionChecker == null) {
			throw new PrincipalException(
				"sharing permission checker is null for class name id " +
					classNameId);
		}

		if (sharingPermissionChecker.hasPermission(
				getPermissionChecker(), classPK, groupId,
				sharingEntryActionKeys)) {

			return;
		}

		ClassName className = classNameLocalService.fetchByClassNameId(
			classNameId);

		String resourceName = String.valueOf(classNameId);

		if (className != null) {
			resourceName = className.getClassName();
		}

		Stream<SharingEntryActionKey> sharingEntryActionKeyStream =
			sharingEntryActionKeys.stream();

		String[] actionIds = sharingEntryActionKeyStream.map(
			SharingEntryActionKey::getActionId
		).toArray(
			String[]::new
		);

		throw new PrincipalException.MustHavePermission(
			fromUserId, resourceName, classPK, actionIds);
	}

}