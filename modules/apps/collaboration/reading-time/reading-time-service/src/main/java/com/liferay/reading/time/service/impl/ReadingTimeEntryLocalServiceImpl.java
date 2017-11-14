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

package com.liferay.reading.time.service.impl;

import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.reading.time.model.ReadingTimeEntry;
import com.liferay.reading.time.service.base.ReadingTimeEntryLocalServiceBaseImpl;

import java.time.Duration;

/**
 * @author Alejandro Tardín
 */
public class ReadingTimeEntryLocalServiceImpl
	extends ReadingTimeEntryLocalServiceBaseImpl {

	@Override
	public ReadingTimeEntry addReadingTimeEntry(
		GroupedModel model, Duration readingTime) {

		return addReadingTimeEntry(
			model.getGroupId(),
			_classNameLocalService.getClassNameId(model.getModelClass()),
			(Long)model.getPrimaryKeyObj(), readingTime.toMillis());
	}

	@Override
	public ReadingTimeEntry addReadingTimeEntry(
		long groupId, long classNameId, long classPK, long readingTime) {

		long entryId = counterLocalService.increment();

		ReadingTimeEntry entry = readingTimeEntryPersistence.create(entryId);

		entry.setClassNameId(classNameId);
		entry.setClassPK(classPK);
		entry.setGroupId(groupId);
		entry.setReadingTime(readingTime);

		return readingTimeEntryPersistence.update(entry);
	}

	@Override
	public ReadingTimeEntry deleteReadingTimeEntry(GroupedModel model) {
		ReadingTimeEntry readingTimeEntry = fetchReadingTimeEntry(model);

		if (readingTimeEntry != null) {
			return deleteReadingTimeEntry(readingTimeEntry);
		}

		return null;
	}

	@Override
	public ReadingTimeEntry fetchReadingTimeEntry(GroupedModel model) {
		return fetchReadingTimeEntry(
			model.getGroupId(),
			_classNameLocalService.getClassNameId(model.getModelClass()),
			(Long)model.getPrimaryKeyObj());
	}

	@Override
	public ReadingTimeEntry fetchReadingTimeEntry(
		long groupId, long classNameId, long classPK) {

		return readingTimeEntryPersistence.fetchByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public ReadingTimeEntry updateReadingTimeEntry(
		GroupedModel model, Duration readingTime) {

		ReadingTimeEntry readingTimeEntry = fetchReadingTimeEntry(model);

		if (readingTimeEntry == null) {
			return addReadingTimeEntry(model, readingTime);
		}
		else {
			readingTimeEntry.setReadingTime(readingTime.toMillis());

			return updateReadingTimeEntry(readingTimeEntry);
		}
	}

	@ServiceReference(type = ClassNameLocalService.class)
	private ClassNameLocalService _classNameLocalService;

}