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
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalService;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryService;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;

import java.util.List;

/**
 * @author Iván Zaera
 */
public class TrashEntryServiceAdapter {

	public static TrashEntryServiceAdapter create(
		DocumentRepository documentRepository) {

		if (documentRepository instanceof LocalRepository) {
			return new TrashEntryServiceAdapter(
				TrashEntryLocalServiceUtil.getService());
		}

		return new TrashEntryServiceAdapter(
			TrashEntryLocalServiceUtil.getService(),
			TrashEntryServiceUtil.getService());
	}

	public TrashEntryServiceAdapter(
		TrashEntryLocalService trashEntryLocalService) {

		this(trashEntryLocalService, null);
	}

	public TrashEntryServiceAdapter(
		TrashEntryLocalService trashEntryLocalService,
		TrashEntryService trashEntryService) {

		_trashEntryLocalService = trashEntryLocalService;
		_trashEntryService = trashEntryService;
	}

	public void deleteEntry(String className, long classPK)
		throws PortalException {

		if (_trashEntryService != null) {
			_trashEntryService.deleteEntry(className, classPK);
		}
		else {
			_trashEntryLocalService.deleteEntry(className, classPK);
		}
	}

	public void deleteTrashEntry(TrashEntry trashEntry) throws PortalException {
		if (_trashEntryService != null) {
			_trashEntryService.deleteEntry(trashEntry.getEntryId());
		}
		else {
			_trashEntryLocalService.deleteTrashEntry(trashEntry);
		}
	}

	public List<TrashEntry> getEntries(long repositoryId, String className)
		throws PortalException {

		List<TrashEntry> trashEntries = null;

		if (_trashEntryService != null) {
			trashEntries = _trashEntryService.getEntries(
				repositoryId, className);
		}
		else {
			trashEntries = _trashEntryLocalService.getEntries(
				repositoryId, className);
		}

		return trashEntries;
	}

	private final TrashEntryLocalService _trashEntryLocalService;
	private final TrashEntryService _trashEntryService;

}