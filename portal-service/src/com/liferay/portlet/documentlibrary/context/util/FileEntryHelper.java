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

package com.liferay.portlet.documentlibrary.context.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;

/**
 * @author Ivan Zaera
 */
public class FileEntryHelper {

	public FileEntryHelper(FileEntry fileEntry) {
		this(fileEntry, DLFileEntryTypeLocalServiceUtil.getService());
	}

	public FileEntryHelper(
		FileEntry fileEntry,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_fileEntry = fileEntry;
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	public DLFileEntryType getDLFileEntryType() {
		try {
			return _dlFileEntryTypeLocalService.getDLFileEntryType(
				_getFileEntryTypeId());
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private long _getFileEntryTypeId() {
		if (_fileEntry.getModel() instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry) _fileEntry.getModel();

			return dlFileEntry.getFileEntryTypeId();
		}

		return -1;
	}

	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private FileEntry _fileEntry;

}