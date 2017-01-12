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

package com.liferay.blogs.demo.data.creator.internal;

import com.liferay.blogs.demo.data.creator.BlogsEntryDemoDataCreator;
import com.liferay.blogs.exception.NoSuchEntryException;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.document.library.demo.data.creator.FileEntryDemoDataCreator;
import com.liferay.document.library.demo.data.creator.RootFolderDemoDataCreator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.sql.Timestamp;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
public abstract class BaseBlogsEntryDemoDataCreator
	implements BlogsEntryDemoDataCreator {

	public BlogsEntry createBaseBlogsEntry(
			long userId, long groupId, String title, String subtitle,
			String content)
		throws IOException, PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		serviceContext.setScopeGroupId(groupId);

		ImageSelector imageSelector = new ImageSelector(
			_getRandomImageBytes(userId, groupId),
			StringUtil.randomString() + ".jpeg", "image/jpeg",
			StringPool.BLANK);

		BlogsEntry blogsEntry = blogsEntryLocalService.addEntry(
			userId, title, subtitle, null, content, getRandomDate(), false,
			false, null, null, imageSelector, null, serviceContext);

		entryIds.add(blogsEntry.getEntryId());

		return blogsEntry;
	}

	@Override
	public void delete() throws PortalException {
		try {
			for (long entryId : entryIds) {
				blogsEntryLocalService.deleteEntry(entryId);

				entryIds.remove(entryId);
			}
		}
		catch (NoSuchEntryException nsee) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsee);
			}
		}

		fileEntryDemoDataCreator.delete();
		rootFolderDemoDataCreator.delete();
	}

	protected Date getRandomDate() {
		long start = Timestamp.valueOf("2000-01-01 00:00:00").getTime();
		long end = new Date().getTime();

		long diff = end - start + 1;

		Timestamp timestamp = new Timestamp(
			start + (long)(Math.random() * diff));

		return new Date(timestamp.getTime());
	}

	@Reference(unbind = "-")
	protected void setBlogLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		this.blogsEntryLocalService = blogsEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setFileEntryDemoDataCreator(
		FileEntryDemoDataCreator fileEntryDemoDataCreator) {

		this.fileEntryDemoDataCreator = fileEntryDemoDataCreator;
	}

	@Reference(unbind = "-")
	protected void setRootFolderDemoDataCreator(
		RootFolderDemoDataCreator rootFolderDemoDataCreator) {

		this.rootFolderDemoDataCreator = rootFolderDemoDataCreator;
	}

	protected BlogsEntryLocalService blogsEntryLocalService;
	protected final List<Long> entryIds = new CopyOnWriteArrayList<>();
	protected FileEntryDemoDataCreator fileEntryDemoDataCreator;
	protected RootFolderDemoDataCreator rootFolderDemoDataCreator;

	private byte[] _getRandomImageBytes(long userId, long groupId)
		throws IOException, PortalException {

		if (_blogsEntryImagesFolder == null) {
			_blogsEntryImagesFolder = rootFolderDemoDataCreator.create(
				userId, groupId, "Blogs Images");
		}

		FileEntry fileEntry = fileEntryDemoDataCreator.create(
			userId, _blogsEntryImagesFolder.getFolderId());

		FileVersion fileVersion = fileEntry.getFileVersion();

		return FileUtil.getBytes(fileVersion.getContentStream(false));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseBlogsEntryDemoDataCreator.class);

	private Folder _blogsEntryImagesFolder;

}