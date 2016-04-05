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

package com.liferay.blogs.data.creator.impl;

import com.liferay.blogs.data.creator.FullBlogsEntryDataCreator;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.kernel.service.BlogsEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = FullBlogsEntryDataCreator.class)
public class FullBlogsEntryDataCreatorImpl
	implements FullBlogsEntryDataCreator {

	public BlogsEntry create(long userId, long groupId, String title)
		throws PortalException {

		String content = "This is some content";

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		BlogsEntry entry = _blogsEntryLocalService.addEntry(
			userId, title, content, serviceContext);

		_blogEntries.add(entry);

		return entry;
	}

	public void delete() throws PortalException {
		for (BlogsEntry entry : _blogEntries) {
			_blogsEntryLocalService.deleteEntry(entry);
		}
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	private final List<BlogsEntry> _blogEntries = new ArrayList();
	private BlogsEntryLocalService _blogsEntryLocalService;

}