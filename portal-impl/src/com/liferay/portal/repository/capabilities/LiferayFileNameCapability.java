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

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.FileNameCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.repository.capabilities.util.DLFileEntryServiceAdapter;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryChecker;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

/**
 * @author Iván Zaera
 */
public class LiferayFileNameCapability implements FileNameCapability {

	public LiferayFileNameCapability(
		DLFileEntryServiceAdapter dlFileEntryServiceAdapter,
		RepositoryEntryChecker repositoryEntryChecker) {

		_dlFileEntryServiceAdapter = dlFileEntryServiceAdapter;
		_repositoryEntryChecker = repositoryEntryChecker;
	}

	@Override
	public FileEntry getFileEntry(long groupId, long folderId, String fileName)
		throws PortalException {

		DLFileEntry dlFileEntry =
			_dlFileEntryServiceAdapter.fetchFileEntryByFileName(
				groupId, folderId, fileName);

		if (dlFileEntry == null) {
			throw new NoSuchFileEntryException(
				"No file entry with {groupId=" + groupId + ", folderId=" +
					folderId + ", fileName=" + fileName + "}");
		}

		_repositoryEntryChecker.checkDLFileEntry(dlFileEntry);

		return new LiferayFileEntry(dlFileEntry);
	}

	private final DLFileEntryServiceAdapter _dlFileEntryServiceAdapter;
	private final RepositoryEntryChecker _repositoryEntryChecker;

}