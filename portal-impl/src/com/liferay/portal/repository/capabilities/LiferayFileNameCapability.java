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
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.FileNameCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.capabilities.util.DLFileEntryServiceAdapter;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryChecker;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.service.persistence.RepositoryPersistence;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

/**
 * @author Iván Zaera
 */
public class LiferayFileNameCapability implements FileNameCapability {

	public LiferayFileNameCapability(
		DocumentRepository documentRepository,
		DLFileEntryServiceAdapter dlFileEntryServiceAdapter,
		RepositoryEntryChecker repositoryEntryChecker,
		RepositoryPersistence repositoryPersistence) {

		_documentRepository = documentRepository;
		_dlFileEntryServiceAdapter = dlFileEntryServiceAdapter;
		_repositoryEntryChecker = repositoryEntryChecker;
		_repositoryPersistence = repositoryPersistence;
	}

	@Override
	public FileEntry getFileEntry(
			long folderId, String fileName, boolean includePWC)
		throws PortalException {

		DLFileEntry dlFileEntry =
			_dlFileEntryServiceAdapter.getFileEntryByFileName(
				_getGroupId(), folderId, fileName, includePWC);

		_repositoryEntryChecker.checkDLFileEntry(dlFileEntry);

		return new LiferayFileEntry(dlFileEntry);
	}

	private long _getGroupId() {
		if (_groupId == -1) {
			long repositoryId = _documentRepository.getRepositoryId();

			Repository repository = _repositoryPersistence.fetchByPrimaryKey(
				repositoryId);

			if (repository == null) {
				_groupId = repositoryId;
			}
			else {
				_groupId = repository.getGroupId();
			}
		}

		return _groupId;
	}

	private final DLFileEntryServiceAdapter _dlFileEntryServiceAdapter;
	private final DocumentRepository _documentRepository;
	private long _groupId = -1;
	private final RepositoryEntryChecker _repositoryEntryChecker;
	private final RepositoryPersistence _repositoryPersistence;

}