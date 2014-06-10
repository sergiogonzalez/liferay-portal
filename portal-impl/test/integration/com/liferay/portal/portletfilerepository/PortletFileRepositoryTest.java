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

package com.liferay.portal.portletfilerepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class PortletFileRepositoryTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddMultiplePortletFileEntries() throws Exception {
		Folder folder = addFolder();

		addFileEntries(folder, 5);

		int fileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				_group.getGroupId(), folder.getFolderId());

		Assert.assertEquals(5, fileEntriesCount);
	}

	@Test
	public void testAddPortletFileEntry() throws Exception {
		FileEntry fileEntry = addFileEntry();

		FileEntry savedFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				fileEntry.getFileEntryId());

		Assert.assertNotNull(savedFileEntry);
	}

	@Test
	public void testAddPortletFolder() throws Exception {
		Folder folder = addFolder();

		Assert.assertNotNull(folder);
	}

	@Test
	public void testAddPortletFolderTwice() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		Repository repository = addRepository(serviceContext);

		Folder folder = addFolder(repository, serviceContext);

		Folder newFolder = addFolder(
			repository, folder.getName(), serviceContext);

		Assert.assertEquals(folder.getFolderId(), newFolder.getFolderId());
	}

	@Test
	public void testAddPortletRepository() throws Exception {
		Repository repository = addRepository();

		Assert.assertNotNull(repository);
	}

	@Test
	public void testDeleteFileEntryById() throws Exception {
		FileEntry fileEntry = addFileEntry();

		PortletFileRepositoryUtil.deletePortletFileEntry(
			fileEntry.getFileEntryId());

		int fileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				fileEntry.getGroupId(), fileEntry.getFolderId());

		Assert.assertEquals(0, fileEntriesCount);
	}

	@Test
	public void testDeleteFileEntryByName() throws Exception {
		FileEntry fileEntry = addFileEntry();

		PortletFileRepositoryUtil.deletePortletFileEntry(
			fileEntry.getGroupId(), fileEntry.getFolderId(),
			fileEntry.getTitle());

		int fileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				fileEntry.getGroupId(), fileEntry.getFolderId());

		Assert.assertEquals(0, fileEntriesCount);
	}

	@Test
	public void testDeleteMultipleFileEntries() throws Exception {
		Folder folder = addFolder();

		addFileEntries(folder, 5);

		PortletFileRepositoryUtil.deletePortletFileEntries(
			_group.getGroupId(), folder.getFolderId());

		int fileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				_group.getGroupId(), folder.getFolderId());

		Assert.assertEquals(0, fileEntriesCount);
	}

	@Test
	public void testDeletePortletFolder() throws Exception {
		Folder folder = addFolder();

		addFileEntry(folder);

		PortletFileRepositoryUtil.deletePortletFolder(folder.getFolderId());

		int fileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				folder.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(0, fileEntriesCount);
	}

	@Test
	public void testDeletePortletRepository() throws Exception {
		Repository repository = addRepository();

		PortletFileRepositoryUtil.deletePortletRepository(
			repository.getGroupId(), repository.getPortletId());

		Repository deletedRepository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				repository.getGroupId(), repository.getPortletId());

		Assert.assertNull(deletedRepository);
	}

	@Test
	public void testMoveFileEntryToTrashById() throws Exception {
		FileEntry fileEntry = addFileEntry();

		PortletFileRepositoryUtil.movePortletFileEntryToTrash(
			fileEntry.getUserId(), fileEntry.getFileEntryId());

		FileEntry trashedFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				fileEntry.getFileEntryId());

		Assert.assertTrue(trashedFileEntry.isInTrash());
	}

	@Test
	public void testMoveFileEntryToTrashByTitle() throws Exception {
		FileEntry fileEntry = addFileEntry();

		PortletFileRepositoryUtil.movePortletFileEntryToTrash(
			fileEntry.getGroupId(), fileEntry.getUserId(),
			fileEntry.getFolderId(), fileEntry.getTitle());

		FileEntry trashedFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				fileEntry.getFileEntryId());

		Assert.assertTrue(trashedFileEntry.isInTrash());
	}

	@Test
	public void testRestoreFileEntryFromTrashById() throws Exception {
		FileEntry fileEntry = addFileEntry();

		PortletFileRepositoryUtil.movePortletFileEntryToTrash(
			fileEntry.getUserId(), fileEntry.getFileEntryId());

		PortletFileRepositoryUtil.restorePortletFileEntryFromTrash(
			fileEntry.getUserId(), fileEntry.getFileEntryId());

		FileEntry restoredFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				fileEntry.getFileEntryId());

		Assert.assertFalse(restoredFileEntry.isInTrash());
	}

	@Test
	public void testRestoreFileEntryFromTrashByTitle() throws Exception {
		FileEntry fileEntry = addFileEntry();

		PortletFileRepositoryUtil.movePortletFileEntryToTrash(
			fileEntry.getGroupId(), fileEntry.getUserId(),
			fileEntry.getFolderId(), fileEntry.getTitle());

		PortletFileRepositoryUtil.restorePortletFileEntryFromTrash(
			fileEntry.getGroupId(), fileEntry.getUserId(),
			fileEntry.getFolderId(), fileEntry.getTitle());

		FileEntry restoredFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				fileEntry.getFileEntryId());

		Assert.assertFalse(restoredFileEntry.isInTrash());
	}

	protected void addFileEntries(Folder folder, int n)
		throws PortalException, SystemException {

		List<ObjectValuePair<String, InputStream>> inputStreamOVP =
			new ArrayList<ObjectValuePair<String, InputStream>>(n);

		for (int i = 0; i < n; i++) {
			String fileName = String.format("foo-%d.txt", i);
			InputStream content = new ByteArrayInputStream(fileName.getBytes());

			inputStreamOVP.add(
				new ObjectValuePair<String, InputStream>(fileName, content));
		}

		PortletFileRepositoryUtil.addPortletFileEntries(
			_group.getGroupId(), TestPropsValues.getUserId(), StringPool.BLANK,
			0, PortletKeys.DOCUMENT_LIBRARY, folder.getFolderId(),
			inputStreamOVP);
	}

	protected FileEntry addFileEntry() throws Exception {
		Folder folder = addFolder();

		return addFileEntry(folder);
	}

	protected FileEntry addFileEntry(Folder folder)
		throws PortalException, SystemException {

		return addFileEntry(folder, RandomTestUtil.randomString());
	}

	protected FileEntry addFileEntry(Folder folder, String name)
		throws PortalException, SystemException {

		InputStream content = new ByteArrayInputStream(name.getBytes());

		return PortletFileRepositoryUtil.addPortletFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(), StringPool.BLANK,
			0, PortletKeys.DOCUMENT_LIBRARY, folder.getFolderId(), content,
			name, "text/plain", false);
	}

	protected Folder addFolder() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		Repository repository = addRepository(serviceContext);

		return addFolder(repository, serviceContext);
	}

	protected Folder addFolder(
			Repository repository, ServiceContext serviceContext)
		throws Exception {

		return addFolder(
			repository, RandomTestUtil.randomString(), serviceContext);
	}

	protected Folder addFolder(
			Repository repository, String name, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return PortletFileRepositoryUtil.addPortletFolder(
			TestPropsValues.getUserId(), repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name, serviceContext);
	}

	protected Repository addRepository() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		return addRepository(serviceContext);
	}

	private Repository addRepository(ServiceContext serviceContext)
		throws Exception {

		return PortletFileRepositoryUtil.addPortletRepository(
			_group.getGroupId(), PortletKeys.DOCUMENT_LIBRARY, serviceContext);
	}

	private Group _group;

}