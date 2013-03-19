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

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.util.MBTestUtil
	;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roberto Díaz
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MBAttachmentsFolderCreationTest {

	@Before
	public void setUp() throws Exception {
		_user = TestPropsValues.getUser();
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
	}

	@Test
	public void testAddMessageAttachmentCreatesFolder() throws Exception {
		int expectedFolderCount =
			DLFolderLocalServiceUtil.getDLFoldersCount() + _createFolders(3);

		addMessageAttachment();

		int currentFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertNotNull(_message);
		Assert.assertEquals(expectedFolderCount, currentFolderCount);
	}

	@Test
	public void testAddMessageCreatesNoFolder() throws Exception {
		int expectedFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addMessage();

		int currentFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertNotNull(_message);
		Assert.assertEquals(expectedFolderCount, currentFolderCount);
	}

	@Test
	public void testDeleteMBMessageWithAttachments() throws Exception {
		addMessageAttachment();

		Folder threadAttachmentsFolder = _message.getThreadAttachmentsFolder();
		Folder attachmentsFolder = _message.getAttachmentsFolder();

		Assert.assertNotNull(threadAttachmentsFolder);
		Assert.assertNotNull(attachmentsFolder);

		try {
			MBMessageLocalServiceUtil.deleteMBMessage(_message.getMessageId());
		}
		catch (Exception e) {
			Assert.fail(
				"An exception was thrown, " +
				"testDeleteMBMessageWithAttachments failed");
		}
	}

	protected void addMessage() throws Exception {
		_category = MBTestUtil.addCategory(_getServiceContext());

		_message = MBTestUtil.addMessage(
			_category.getCategoryId(), ServiceTestUtil.randomString(), true,
			_getServiceContext());
	}

	protected void addMessageAttachment() throws Exception {
		ServiceContext serviceContext = _getServiceContext();

		_message = MBMessageLocalServiceUtil.addMessage(
			_user.getUserId(), _user.getFullName(), _group.getGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, "Subject", "Body",
			MBMessageConstants.DEFAULT_FORMAT,
			_getInputStreamOVPs("company_logo.png"), false, 0, false,
			serviceContext);
	}

	private int _createFolders(int i) {
		return i;
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

	private ServiceContext _getServiceContext() throws Exception {
		return ServiceTestUtil.getServiceContext
			(_group.getGroupId(), _user.getUserId());
	}

	private MBCategory _category;
	private Group _group;
	private MBMessage _message;
	private User _user;

}