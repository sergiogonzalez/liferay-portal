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

package com.liferay.document.library.google.drive.service.impl;

import com.liferay.document.library.google.drive.model.GoogleDriveEntry;
import com.liferay.document.library.google.drive.service.base.GoogleDriveEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Adolfo Pérez
 */
public class GoogleDriveEntryLocalServiceImpl
	extends GoogleDriveEntryLocalServiceBaseImpl {

	@Override
	public GoogleDriveEntry addEntry(
			FileEntry fileEntry, String googleDriveId,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		GoogleDriveEntry googleDriveEntry =
			googleDriveEntryLocalService.createGoogleDriveEntry(
				counterLocalService.increment());

		googleDriveEntry.setGroupId(fileEntry.getGroupId());
		googleDriveEntry.setCompanyId(fileEntry.getCompanyId());
		googleDriveEntry.setUserId(user.getUserId());
		googleDriveEntry.setUserName(user.getFullName());
		googleDriveEntry.setCreateDate(serviceContext.getCreateDate());
		googleDriveEntry.setFileEntryId(fileEntry.getFileEntryId());
		googleDriveEntry.setGoogleDriveId(googleDriveId);
		googleDriveEntry.setUserUuid(user.getUserUuid());

		return googleDriveEntryLocalService.updateGoogleDriveEntry(
			googleDriveEntry);
	}

	@Override
	public void deleteEntry(FileEntry fileEntry) throws PortalException {
		GoogleDriveEntry googleDriveEntry =
			googleDriveEntryPersistence.findByG_F(
				fileEntry.getGroupId(), fileEntry.getFileEntryId());

		googleDriveEntryLocalService.deleteGoogleDriveEntry(googleDriveEntry);
	}

	@Override
	public GoogleDriveEntry fetchEntry(FileEntry fileEntry) {
		return googleDriveEntryPersistence.fetchByG_F(
			fileEntry.getGroupId(), fileEntry.getFileEntryId());
	}

	@Override
	public GoogleDriveEntry getEntry(FileEntry fileEntry)
		throws PortalException {

		return googleDriveEntryPersistence.findByG_F(
			fileEntry.getGroupId(), fileEntry.getFileEntryId());
	}

}