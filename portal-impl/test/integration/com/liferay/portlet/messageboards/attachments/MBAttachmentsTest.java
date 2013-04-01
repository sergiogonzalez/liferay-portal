/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.attachments;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.model.Group;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.util.MBTestUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MBAttachmentsTest {

	public static final int NEW_FILE_ENTRY = 1;
	public static final int NEW_MESSAGE_FOLDER = 1;
	public static final int NEW_REPOSITORY_FOLDER = 1;
	public static final int NEW_THREAD_FOLDER = 1;

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		if (_group != null) {
			GroupLocalServiceUtil.deleteGroup(_group);

			_group = null;
		}

		if (_message != null) {
			_message = null;
		}

		if (_category != null) {
			_category = null;
		}
	}

	@Test
	@Transactional
	public void testAddMBMessage() throws Exception {
		int initialFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessage();

		int currentFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertNotNull(_message);
		Assert.assertEquals(initialFolderCount, currentFolderCount);
	}

	@Test
	@Transactional
	public void testAddMessageAttachment() throws Exception {
		int initialFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessageWithAttachment();

		int finalFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		int newFolders =
			NEW_REPOSITORY_FOLDER + NEW_THREAD_FOLDER + NEW_MESSAGE_FOLDER;

		Assert.assertEquals(initialFolderCount + newFolders, finalFolderCount);
	}

	@Test
	@Transactional
	public void testAddMessageAttachments() throws Exception {
		int initialFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();
		int expectedFileEntriesCount = 0;

		addMessageWithAttachment();

		expectedFileEntriesCount += NEW_FILE_ENTRY;

		int firstFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		int newFolders =
			NEW_REPOSITORY_FOLDER + NEW_THREAD_FOLDER + NEW_MESSAGE_FOLDER;

		List<FileEntry> fileEntries = _message.getAttachmentsFileEntries();

		Assert.assertEquals(expectedFileEntriesCount, fileEntries.size());
		Assert.assertEquals(initialFolderCount + newFolders, firstFolderCount);

		updateMessageWithAttachment();

		expectedFileEntriesCount += NEW_FILE_ENTRY;

		int finalFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		fileEntries = _message.getAttachmentsFileEntries();

		Assert.assertEquals(expectedFileEntriesCount, fileEntries.size());
		Assert.assertEquals(firstFolderCount, finalFolderCount);
	}

	@Test
	@Transactional
	public void testCountFoldersWhenMovingToTrashMessageAttachments()
		throws Exception {

		int initialFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		_message = MBTestUtil.addMessageWithAttachment(
			TestPropsValues.getUser(), _group.getGroupId(), "dependencies",
			"company_logo.png", getClass());

		int firstFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		int newFolders =
			NEW_REPOSITORY_FOLDER + NEW_THREAD_FOLDER + NEW_MESSAGE_FOLDER;

		Assert.assertEquals(initialFolderCount + newFolders, firstFolderCount);

		MBMessageLocalServiceUtil.moveMessageAttachmentToTrash(
			TestPropsValues.getUserId(), _message.getMessageId(),
			"company_logo.png");

		int finalFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertNotEquals(initialFolderCount, finalFoldersCount);
	}

	@Test
	@Transactional
	public void testDeleteAttachmentWhenDeletingMessage() throws Exception {
		int initialFileEntryCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		addMessageWithAttachment();

		int firstFileEntryCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		Assert.assertEquals(
			initialFileEntryCount + NEW_FILE_ENTRY, firstFileEntryCount);

		MBMessageLocalServiceUtil.deleteMessage(_message.getMessageId());

		int finalFileEntryCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		Assert.assertEquals(initialFileEntryCount, finalFileEntryCount);
	}

	@Test
	@Transactional
	public void testDeleteAttachmentWhenDeletingThread() throws Exception {
		int initialFileEntryCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		addMessageWithAttachment();

		int firstFileEntryCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		Assert.assertEquals(
			initialFileEntryCount + NEW_FILE_ENTRY, firstFileEntryCount);

		MBThreadLocalServiceUtil.deleteThread(_message.getThreadId());

		int finalFileEntryCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		Assert.assertEquals(
			firstFileEntryCount - NEW_FILE_ENTRY, finalFileEntryCount);
	}

	@Test
	@Transactional
	public void testDeleteMessageWithAttachments() throws Exception {
		addMessageWithAttachment();

		long threadAttachmentsFolderId =
			_message.getThreadAttachmentsFolderId();
		long attachmentsFolderId = _message.getAttachmentsFolderId();

		Assert.assertNotEquals(threadAttachmentsFolderId, 0L);
		Assert.assertNotEquals(attachmentsFolderId, 0L);

		MBMessageLocalServiceUtil.deleteMBMessage(_message.getMessageId());
	}

	@Test
	@Transactional
	public void testDeleteMessageWithoutAttachments() throws Exception {
		addMessage();

		long threadAttachmentsFolderId =
			_message.getThreadAttachmentsFolderId();
		long attachmentsFolderId = _message.getAttachmentsFolderId();

		Assert.assertEquals(threadAttachmentsFolderId, 0L);
		Assert.assertEquals(attachmentsFolderId, 0L);

		MBMessageLocalServiceUtil.deleteMBMessage(_message.getMessageId());
	}

	@Test
	public void testMoveToTrashAndDeleteMessage() throws Exception {
		_message = MBTestUtil.addMessageWithAttachment(
			TestPropsValues.getUser(), _group.getGroupId(), "dependencies",
			"company_logo.png", getClass());

		_trashMBAttachments(false);
	}

	@Test
	public void testMoveToTrashAndRestoreMessage() throws Exception {
		_message = MBTestUtil.addMessageWithAttachment(
			TestPropsValues.getUser(), _group.getGroupId(), "dependencies",
			"company_logo.png", getClass());

		_trashMBAttachments(true);
	}

	protected void addMessage() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		_category = MBTestUtil.addCategory(serviceContext);

		_message = MBTestUtil.addMessage(
			_category.getCategoryId(), ServiceTestUtil.randomString(), true,
			serviceContext);
	}

	protected void addMessageWithAttachment() throws Exception {
		_message = MBTestUtil.addMessageWithAttachment(
			TestPropsValues.getUser(), _group.getGroupId(), "dependencies",
			"company_logo.png", getClass());
	}

	protected void updateMessageWithAttachment() throws Exception {
		_message = MBTestUtil.updateMessageWithAttachment(
			TestPropsValues.getUser(), _message, "dependencies",
			"OSX_Test.docx", getClass());
	}

	private List<ObjectValuePair<String, InputStream>> _getInputStreamOVPs(
		String fileName) {

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<ObjectValuePair<String, InputStream>>(1);

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		ObjectValuePair<String, InputStream> inputStreamOVP =
			new ObjectValuePair<String, InputStream>(fileName, inputStream);

		inputStreamOVPs.add(inputStreamOVP);

		return inputStreamOVPs;
	}

	private void _trashMBAttachments(boolean restore) throws Exception {
		int initialNotInTrashCount = _message.getAttachmentsFileEntriesCount();
		int initialTrashEntriesCount =
			_message.getDeletedAttachmentsFileEntriesCount();

		List<FileEntry> fileEntries = _message.getAttachmentsFileEntries();

		List<String> existingFiles = new ArrayList<String>();

		for (FileEntry fileEntry : fileEntries) {
			existingFiles.add(String.valueOf(fileEntry.getFileEntryId()));
		}

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		String fileName = "OSX_Test.docx";

		_message = MBMessageLocalServiceUtil.updateMessage(
			TestPropsValues.getUserId(), _message.getMessageId(), "Subject",
			"Body", _getInputStreamOVPs(fileName), existingFiles, 0, false,
			serviceContext);

		Assert.assertEquals(
			initialNotInTrashCount + NEW_FILE_ENTRY,
			_message.getAttachmentsFileEntriesCount());
		Assert.assertEquals(
			initialTrashEntriesCount,
			_message.getDeletedAttachmentsFileEntriesCount());

		long fileEntryId =
			MBMessageLocalServiceUtil.moveMessageAttachmentToTrash(
				TestPropsValues.getUserId(), _message.getMessageId(), fileName);

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			fileEntryId);

		Assert.assertEquals(
			initialNotInTrashCount, _message.getAttachmentsFileEntriesCount());
		Assert.assertEquals(
			initialTrashEntriesCount + NEW_FILE_ENTRY,
			_message.getDeletedAttachmentsFileEntriesCount());

		if (restore) {
			MBMessageLocalServiceUtil.restoreMessageAttachmentFromTrash(
				TestPropsValues.getUserId(), _message.getMessageId(),
				fileEntry.getTitle());

			Assert.assertEquals(
				initialNotInTrashCount + NEW_FILE_ENTRY,
				_message.getAttachmentsFileEntriesCount());
			Assert.assertEquals(
				initialTrashEntriesCount,
				_message.getDeletedAttachmentsFileEntriesCount());

			MBMessageLocalServiceUtil.deleteMessageAttachment(
				_message.getMessageId(), fileName);
		}
		else {
			MBMessageLocalServiceUtil.deleteMessageAttachment(
				_message.getMessageId(), fileEntry.getTitle());

			Assert.assertEquals(
				initialNotInTrashCount,
				_message.getAttachmentsFileEntriesCount());
			Assert.assertEquals(
				initialTrashEntriesCount,
				_message.getDeletedAttachmentsFileEntriesCount());
		}
	}

	private MBCategory _category;
	private Group _group;
	private MBMessage _message;

}