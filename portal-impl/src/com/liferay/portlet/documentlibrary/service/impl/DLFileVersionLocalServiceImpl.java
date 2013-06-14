/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.base.DLFileVersionLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.comparator.FileVersionVersionComparator;

import java.util.Collections;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileVersionLocalServiceImpl
	extends DLFileVersionLocalServiceBaseImpl {

	@Override
	public DLFileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		return dlFileVersionPersistence.findByPrimaryKey(fileVersionId);
	}

	@Override
	public DLFileVersion getFileVersion(long fileEntryId, String version)
		throws PortalException, SystemException {

		return dlFileVersionPersistence.findByF_V(fileEntryId, version);
	}

	@Override
	public DLFileVersion getFileVersionByUuidAndGroupId(
			String uuid, long groupId)
		throws SystemException {

		return dlFileVersionPersistence.fetchByUUID_G(uuid, groupId);
	}

	@Override
	public List<DLFileVersion> getFileVersions(long fileEntryId, int status)
		throws SystemException {

		List<DLFileVersion> dlFileVersions = null;

		if (status == WorkflowConstants.STATUS_ANY) {
			dlFileVersions = dlFileVersionPersistence.findByFileEntryId(
				fileEntryId);
		}
		else {
			dlFileVersions = dlFileVersionPersistence.findByF_S(
				fileEntryId, status);
		}

		dlFileVersions = ListUtil.copy(dlFileVersions);

		Collections.sort(dlFileVersions, new FileVersionVersionComparator());

		return dlFileVersions;
	}

	@Override
	public int getFileVersionsCount(long fileEntryId, int status)
		throws SystemException {

		return dlFileVersionPersistence.countByF_S(fileEntryId, status);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getLatestFileVersion(long,
	 *            int)}
	 */
	@Override
	public DLFileVersion getLatestFileVersion(
			long fileEntryId, boolean excludeWorkingCopy)
		throws PortalException, SystemException {

		if (excludeWorkingCopy) {
			return getLatestFileVersion(
				fileEntryId, WorkflowConstants.STATUS_APPROVED);
		}
		else {
			return getLatestFileVersion(
				fileEntryId, WorkflowConstants.STATUS_ANY);
		}
	}

	@Override
	public DLFileVersion getLatestFileVersion(long fileEntryId, int status)
		throws SystemException {

		List<DLFileVersion> dlFileVersions = dlFileVersionPersistence.findByF_S(
			fileEntryId, status);

		if (status == WorkflowConstants.STATUS_ANY) {
			dlFileVersions = dlFileVersionPersistence.findByFileEntryId(
				fileEntryId);
		}

		dlFileVersions = ListUtil.copy(dlFileVersions);

		Collections.sort(dlFileVersions, new FileVersionVersionComparator());

		return dlFileVersions.get(0);
	}

	@Override
	public DLFileVersion getLatestFileVersion(long userId, long fileEntryId)
		throws PortalException, SystemException {

		boolean excludeWorkingCopy = true;

		if (dlFileEntryLocalService.isFileEntryCheckedOut(fileEntryId)) {
			excludeWorkingCopy = !dlFileEntryLocalService.hasFileEntryLock(
				userId, fileEntryId);
		}

		if (excludeWorkingCopy) {
			return getLatestFileVersion(
				fileEntryId, WorkflowConstants.STATUS_APPROVED);
		}
		else {
			return getLatestFileVersion(
				fileEntryId, WorkflowConstants.STATUS_ANY);
		}
	}

}