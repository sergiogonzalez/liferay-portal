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

package com.liferay.reading.time.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.reading.time.calculator.ReadingTimeCalculator;
import com.liferay.reading.time.model.ReadingTimeEntry;
import com.liferay.reading.time.service.ReadingTimeEntryLocalService;

import java.time.Duration;

import java.util.Locale;
import java.util.Optional;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
public abstract class BaseReadingTimeModelListener
	<T extends BaseModel<T> & GroupedModel> extends BaseModelListener<T> {

	@Override
	public void onAfterCreate(T model) throws ModelListenerException {
		_getReadingTime(model).ifPresent(
			readingTime -> _addReadingTimeEntry(model, readingTime));
	}

	@Override
	public void onAfterUpdate(T model) throws ModelListenerException {
		_getReadingTime(model).ifPresent(
			readingTime -> {
				ReadingTimeEntry readingTimeEntry = _getReadingTimeEntry(model);

				if (readingTimeEntry == null) {
					_addReadingTimeEntry(model, readingTime);
				}
				else {
					readingTimeEntry.setReadingTime(readingTime.toMillis());

					readingTimeEntryLocalService.updateReadingTimeEntry(
						readingTimeEntry);
				}
			});
	}

	@Override
	public void onBeforeRemove(T model) {
		ReadingTimeEntry readingTimeEntry = _getReadingTimeEntry(model);

		if (readingTimeEntry != null) {
			readingTimeEntryLocalService.deleteReadingTimeEntry(
				readingTimeEntry);
		}
	}

	protected abstract String getContent(T model);

	protected abstract String getContentType(T model);

	protected abstract Locale getLocale(T model);

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected ReadingTimeCalculator readingTimeCalculator;

	@Reference
	protected ReadingTimeEntryLocalService readingTimeEntryLocalService;

	private void _addReadingTimeEntry(T model, Duration readingTime) {
		readingTimeEntryLocalService.addReadingTimeEntry(
			classNameLocalService.getClassNameId(model.getModelClass()),
			(Long)model.getPrimaryKeyObj(), readingTime.getSeconds());
	}

	private Optional<Duration> _getReadingTime(T model) {
		return readingTimeCalculator.calculate(
			getContent(model), getContentType(model), getLocale(model));
	}

	private ReadingTimeEntry _getReadingTimeEntry(T model) {
		return readingTimeEntryLocalService.fetchReadingTimeEntry(
			model.getGroupId(),
			classNameLocalService.getClassNameId(model.getModelClass()),
			(Long)model.getPrimaryKeyObj());
	}

}