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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.io.InputStream;

/**
 * @author Alexander Chow
 */
public class FileEntryUtil {

	public static InputStream getContentStream(FileEntry fileEntry)
		throws PortalException {

		long repositoryId = DLFolderConstants.getDataRepositoryId(
			fileEntry.getRepositoryId(), fileEntry.getFolderId());

		String name = ((DLFileEntry)fileEntry.getModel()).getName();

		InputStream is = DLStoreUtil.getFileAsStream(
			fileEntry.getCompanyId(), repositoryId, name,
			fileEntry.getVersion());

		if (is == null) {
			is = new UnsyncByteArrayInputStream(new byte[0]);
		}

		return is;
	}

}