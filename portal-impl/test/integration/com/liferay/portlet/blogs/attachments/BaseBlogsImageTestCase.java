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

package com.liferay.portlet.blogs.attachments;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.User;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.blogs.constants.BlogsConstants;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public abstract class BaseBlogsImageTestCase {

	@Test
	public void testAddImage() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntry(blogsEntry));

		Assert.assertEquals("image1.jpg", imageFileEntry.getTitle());
	}

	@Test
	public void testAddOriginalImage() throws Exception {
		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			user.getUserId(), group.getGroupId());

		int initialFolderFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				group.getGroupId(), folder.getFolderId());

		addBlogsEntry("image1.jpg");

		int portletFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				group.getGroupId(), folder.getFolderId());

		Assert.assertEquals(
			initialFolderFileEntriesCount + 1, portletFileEntriesCount);

		PortletFileRepositoryUtil.getPortletFileEntry(
			group.getGroupId(), folder.getFolderId(), "image1.jpg");
	}

	@Test
	public void testAddOriginalImageWhenUpdatingBlogsEntry() throws Exception {
		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			user.getUserId(), group.getGroupId());

		int initialFolderFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				group.getGroupId(), folder.getFolderId());

		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		updateBlogsEntry(blogsEntry.getEntryId(), "image2.jpg");

		int portletFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				group.getGroupId(), folder.getFolderId());

		Assert.assertEquals(
			initialFolderFileEntriesCount + 2, portletFileEntriesCount);

		PortletFileRepositoryUtil.getPortletFileEntry(
			group.getGroupId(), folder.getFolderId(), "image2.jpg");
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testImageDeletedWhenDeletingBlogsEntry() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntry(blogsEntry));

		BlogsEntryLocalServiceUtil.deleteEntry(blogsEntry);

		PortletFileRepositoryUtil.getPortletFileEntry(
			imageFileEntry.getFileEntryId());
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testImageDeletedWhenEmptyImageSelector() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntry(blogsEntry));

		ImageSelector imageSelector = new ImageSelector(
			0, StringPool.BLANK, StringPool.BLANK);

		blogsEntry = updateBlogsEntry(blogsEntry.getEntryId(), imageSelector);

		Assert.assertEquals(0, getImageFileEntry(blogsEntry));

		PortletFileRepositoryUtil.getPortletFileEntry(
			imageFileEntry.getFileEntryId());
	}

	@Test
	public void testImageNotChangedWhenNullimageSelector() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntry(blogsEntry));

		ImageSelector imageSelector = null;

		blogsEntry = updateBlogsEntry(blogsEntry.getEntryId(), imageSelector);

		Assert.assertEquals(
			imageFileEntry.getFileEntryId(), getImageFileEntry(blogsEntry));

		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			user.getUserId(), group.getGroupId());

		PortletFileRepositoryUtil.getPortletFileEntry(
			group.getGroupId(), folder.getFolderId(), "image1.jpg");

		PortletFileRepositoryUtil.getPortletFileEntry(
			imageFileEntry.getFileEntryId());
	}

	@Test
	public void testImageStoredInBlogsRepository() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntry(blogsEntry));

		Repository repository = RepositoryLocalServiceUtil.getRepository(
			imageFileEntry.getRepositoryId());

		Assert.assertEquals(BlogsConstants.SERVICE_NAME, repository.getName());
	}

	@Test
	public void testImageStoredInImageFolder() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntry(blogsEntry));

		Folder imageFolder = imageFileEntry.getFolder();

		Assert.assertNotEquals(
			BlogsConstants.SERVICE_NAME, imageFolder.getName());
	}

	@Test
	public void testOriginalImageNotDeletedWhenEmptyimageSelector()
		throws Exception {

		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		ImageSelector imageSelector = new ImageSelector(0);

		updateBlogsEntry(blogsEntry.getEntryId(), imageSelector);

		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			user.getUserId(), group.getGroupId());

		PortletFileRepositoryUtil.getPortletFileEntry(
			group.getGroupId(), folder.getFolderId(), "image1.jpg");
	}

	@Test
	public void testOriginalImageNotDeletedWhenNullimageSelector()
		throws Exception {

		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			user.getUserId(), group.getGroupId());

		int initialFolderFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				group.getGroupId(), folder.getFolderId());

		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		ImageSelector imageSelector = null;

		updateBlogsEntry(blogsEntry.getEntryId(), imageSelector);

		int portletFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				group.getGroupId(), folder.getFolderId());

		Assert.assertEquals(
			initialFolderFileEntriesCount + 1, portletFileEntriesCount);

		PortletFileRepositoryUtil.getPortletFileEntry(
			group.getGroupId(), folder.getFolderId(), "image1.jpg");
	}

	@Test
	public void testOriginalImageStoredInBlogsRepository() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntry(blogsEntry));

		Repository repository = RepositoryLocalServiceUtil.getRepository(
			imageFileEntry.getRepositoryId());

		Assert.assertEquals(BlogsConstants.SERVICE_NAME, repository.getName());
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testPreviousImageDeletedWhenChangingImage() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntry(blogsEntry));

		updateBlogsEntry(blogsEntry.getEntryId(), "image2.jpg");

		PortletFileRepositoryUtil.getPortletFileEntry(
			imageFileEntry.getFileEntryId());
	}

	@Test
	public void testPreviousOriginalImageNotDeletedWhenChangingImage()
		throws Exception {

		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		updateBlogsEntry(blogsEntry.getEntryId(), "image2.jpg");

		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			user.getUserId(), group.getGroupId());

		PortletFileRepositoryUtil.getPortletFileEntry(
			group.getGroupId(), folder.getFolderId(), "image1.jpg");
	}

	@Test
	public void testUpdateImage() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		blogsEntry = updateBlogsEntry(blogsEntry.getEntryId(), "image2.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntry(blogsEntry));

		Assert.assertEquals("image2.jpg", imageFileEntry.getTitle());
	}

	protected abstract BlogsEntry addBlogsEntry(String imageTitle)
		throws Exception;

	protected abstract long getImageFileEntry(BlogsEntry blogsEntry);

	protected abstract FileEntry getTempFileEntry(
			long userId, String title, ServiceContext serviceContext)
		throws PortalException;

	protected abstract BlogsEntry updateBlogsEntry(
			long blogsEntryId, ImageSelector imageSelector)
		throws Exception;

	protected abstract BlogsEntry updateBlogsEntry(
			long blogsEntryId, String imageTitle)
		throws Exception;

	@DeleteAfterTestRun
	protected Group group;

	protected User user;

}