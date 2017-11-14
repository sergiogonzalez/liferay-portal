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

package com.liferay.reading.time.blogs.internal.service.wrapper;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.service.BlogsEntryLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.reading.time.calculator.ReadingTimeCalculator;
import com.liferay.reading.time.service.ReadingTimeEntryLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ReadingTimeBlogsEntryLocalServiceWrapper
	extends BlogsEntryLocalServiceWrapper {

	public ReadingTimeBlogsEntryLocalServiceWrapper() {
		super(null);
	}

	public ReadingTimeBlogsEntryLocalServiceWrapper(
		BlogsEntryLocalService blogsEntryLocalService) {

		super(blogsEntryLocalService);
	}

	@Override
	public BlogsEntry addEntry(
			long userId, String title, String subtitle, String urlTitle,
			String description, String content, Date displayDate,
			boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, String coverImageCaption,
			ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		BlogsEntry blogsEntry = super.addEntry(
			userId, title, subtitle, urlTitle, description, content,
			displayDate, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector, smallImageImageSelector,
			serviceContext);

		_readingTimeCalculator.calculate(blogsEntry).ifPresent(
			readingTime -> _readingTimeEntryLocalService.addReadingTimeEntry(
				blogsEntry, readingTime));

		return blogsEntry;
	}

	@Override
	public BlogsEntry deleteEntry(BlogsEntry blogsEntry)
		throws PortalException {

		blogsEntry = super.deleteEntry(blogsEntry);

		_readingTimeEntryLocalService.deleteReadingTimeEntry(blogsEntry);

		return blogsEntry;
	}

	@Override
	public BlogsEntry updateEntry(
			long userId, long entryId, String title, String subtitle,
			String urlTitle, String description, String content,
			Date displayDate, boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, String coverImageCaption,
			ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		BlogsEntry blogsEntry = super.updateEntry(
			userId, entryId, title, subtitle, urlTitle, description, content,
			displayDate, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector, smallImageImageSelector,
			serviceContext);

		_readingTimeCalculator.calculate(blogsEntry).ifPresent(
			readingTime -> _readingTimeEntryLocalService.updateReadingTimeEntry(
				blogsEntry, readingTime));

		return blogsEntry;
	}

	@Reference
	private ReadingTimeCalculator _readingTimeCalculator;

	@Reference
	private ReadingTimeEntryLocalService _readingTimeEntryLocalService;

}