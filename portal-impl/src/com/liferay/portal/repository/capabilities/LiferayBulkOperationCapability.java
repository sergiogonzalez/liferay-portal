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
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.model.RepositoryModelOperation;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class LiferayBulkOperationCapability implements BulkOperationCapability {

	public LiferayBulkOperationCapability(long groupId, long repositoryId) {
		_groupId = groupId;
		_repositoryId = repositoryId;
	}

	@Override
	public void execute(RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		executeFileEntryBulkOperation(repositoryModelOperation);
		executeFolderBulkOperation(repositoryModelOperation);
	}

	protected void executeFileEntryBulkOperation(
			RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		int count = DLFileEntryLocalServiceUtil.getRepositoryFileEntriesCount(
			_groupId, _repositoryId);

		int pages = count / _DEFAULT_PAGE_SIZE;

		if ((count % _DEFAULT_PAGE_SIZE) > 0) {
			pages++;
		}

		int start = 0;
		int end = _DEFAULT_PAGE_SIZE;

		while (pages > 0) {
			List<DLFileEntry> dlFileEntries =
				DLFileEntryLocalServiceUtil.getRepositoryFileEntries(
					_groupId, _repositoryId, start, end);

			for (DLFileEntry fileEntry : dlFileEntries) {
				repositoryModelOperation.execute(
					new LiferayFileEntry(fileEntry));
			}

			start = end;
			end += _DEFAULT_PAGE_SIZE;
			pages--;
		}
	}

	protected void executeFolderBulkOperation(
			RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		int count = DLFolderLocalServiceUtil.getRepositoryFoldersCount(
			_groupId, _repositoryId);

		int pages = count / _DEFAULT_PAGE_SIZE;

		if ((count % _DEFAULT_PAGE_SIZE) > 0) {
			pages++;
		}

		int start = 0;
		int end = _DEFAULT_PAGE_SIZE;

		while (pages > 0) {
			List<DLFolder> dlFolders =
				DLFolderLocalServiceUtil.getRepositoryFolders(
					_groupId, _repositoryId, start, end);

			for (DLFolder folder : dlFolders) {
				repositoryModelOperation.execute(new LiferayFolder(folder));
			}

			start = end;
			end += _DEFAULT_PAGE_SIZE;
			pages--;
		}
	}

	private static final int _DEFAULT_PAGE_SIZE = 100;

	private long _groupId;
	private long _repositoryId;

}