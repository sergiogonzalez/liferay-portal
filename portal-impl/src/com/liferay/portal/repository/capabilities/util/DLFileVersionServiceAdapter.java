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

package com.liferay.portal.repository.capabilities.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionUtil;

import java.util.List;

/**
 * @author Iván Zaera
 */
public class DLFileVersionServiceAdapter {

	public static DLFileVersionServiceAdapter create(
		DocumentRepository documentRepository) {

		if (documentRepository instanceof LocalRepository) {
			return new DLFileVersionServiceAdapter(
				DLFileVersionLocalServiceUtil.getService());
		}

		return new DLFileVersionServiceAdapter(
			DLFileVersionLocalServiceUtil.getService(),
			DLFileVersionServiceUtil.getService(),
			DLFileVersionUtil.getPersistence());
	}

	public DLFileVersionServiceAdapter(
		DLFileVersionLocalService dlFileVersionLocalService) {

		this(
			dlFileVersionLocalService, null,
			DLFileVersionUtil.getPersistence());
	}

	public DLFileVersionServiceAdapter(
		DLFileVersionLocalService dlFileVersionLocalService,
		DLFileVersionService dlFileVersionService,
		DLFileVersionPersistence dlFileVersionPersistence) {

		_dlFileVersionLocalService = dlFileVersionLocalService;
		_dlFileVersionService = dlFileVersionService;
		_dlFileVersionPersistence = dlFileVersionPersistence;
	}

	public List<DLFileVersion> getFileVersions(long fileEntryId, int status)
		throws PortalException {

		if (_dlFileVersionService != null) {
			return _dlFileVersionService.getFileVersions(fileEntryId, status);
		}

		return _dlFileVersionLocalService.getFileVersions(fileEntryId, status);
	}

	public DLFileVersion getLatestFileVersion(
			long fileEntryId, boolean excludeWorkingCopy)
		throws PortalException {

		DLFileVersion dlFileVersion = null;

		if (_dlFileVersionService != null) {
			dlFileVersion = _dlFileVersionService.getLatestFileVersion(
				fileEntryId, excludeWorkingCopy);
		}
		else {
			dlFileVersion = _dlFileVersionLocalService.getLatestFileVersion(
				fileEntryId, excludeWorkingCopy);
		}

		return dlFileVersion;
	}

	public DLFileVersion update(DLFileVersion dlFileVersion) {
		return _dlFileVersionPersistence.update(dlFileVersion);
	}

	private final DLFileVersionLocalService _dlFileVersionLocalService;
	private final DLFileVersionPersistence _dlFileVersionPersistence;
	private final DLFileVersionService _dlFileVersionService;

}