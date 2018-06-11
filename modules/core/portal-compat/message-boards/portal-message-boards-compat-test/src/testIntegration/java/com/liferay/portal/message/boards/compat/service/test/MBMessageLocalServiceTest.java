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

package com.liferay.portal.message.boards.compat.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.message.boards.kernel.service.MBThreadLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.capabilities.WorkflowCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.message.boards.compat.test.util.MBTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.trash.kernel.util.TrashUtil;

import java.io.File;
import java.io.InputStream;

import java.text.DateFormat;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class MBMessageLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddHtmlSubjectWithEmptyBodyMessage() throws Exception {
		String subject = "<u>subject</u>";
		String body = StringPool.BLANK;
		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			_group.getGroupId(), MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			subject, body, "html", inputStreamOVPs, false, 0.0, false,
			serviceContext);

		Assert.assertEquals(subject, message.getSubject());
		Assert.assertEquals(HtmlUtil.escape(subject), message.getBody());
	}

	@Test
	public void testAddMessageAttachment() throws Exception {
		MBMessage message = addMessage(null, false);

		byte[] fileBytes = FileUtil.getBytes(
			getClass(), "dependencies/company_logo.png");

		File file = FileUtil.createTempFile(fileBytes);

		MBMessageLocalServiceUtil.addMessageAttachment(
			TestPropsValues.getUserId(), message.getMessageId(), "test", file,
			"image/png");

		Assert.assertEquals(1, message.getAttachmentsFileEntriesCount());
	}

	@Test
	public void testAddXSSMessageWithInvalidFormat() throws Exception {
		String subject = "<script>alert(1)</script>";
		String body = "<script>alert(2)</script>";
		String format = "text/plain";
		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			_group.getGroupId(), MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			subject, body, format, inputStreamOVPs, false, 0.0, false,
			serviceContext);

		Assert.assertEquals(subject, message.getSubject());
		Assert.assertEquals(StringPool.BLANK, message.getBody());
		Assert.assertEquals("html", message.getFormat());
	}

	@Test
	public void testAddXSSSubjectWithEmptyBodyMessage() throws Exception {
		String subject = "<script>alert(1)</script>";
		String body = StringPool.BLANK;
		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			_group.getGroupId(), MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			subject, body, "html", inputStreamOVPs, false, 0.0, false,
			serviceContext);

		Assert.assertEquals(subject, message.getSubject());
		Assert.assertEquals(HtmlUtil.escape(subject), message.getBody());
	}

	@Test
	public void testDeleteAttachmentsWhenUpdatingMessageAndTrashDisabled()
		throws Exception {

		TrashUtil.disableTrash(_group);

		MBMessage message = addMessage(null, true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBMessageLocalServiceUtil.updateMessage(
			TestPropsValues.getUserId(), message.getMessageId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			Collections.<ObjectValuePair<String, InputStream>>emptyList(),
			Collections.<String>emptyList(), 0, false, serviceContext);

		Assert.assertEquals(
			0,
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				message.getGroupId(), message.getAttachmentsFolderId()));
	}

	@Test
	public void testDeleteAttachmentsWhenUpdatingMessageAndTrashEnabled()
		throws Exception {

		MBMessage message = addMessage(null, true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBMessageLocalServiceUtil.updateMessage(
			TestPropsValues.getUserId(), message.getMessageId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			Collections.<ObjectValuePair<String, InputStream>>emptyList(),
			Collections.<String>emptyList(), 0, false, serviceContext);

		List<FileEntry> fileEntries =
			PortletFileRepositoryUtil.getPortletFileEntries(
				message.getGroupId(), message.getAttachmentsFolderId());

		Assert.assertEquals(fileEntries.toString(), 1, fileEntries.size());

		FileEntry fileEntry = fileEntries.get(0);

		WorkflowCapability workflowCapability =
			fileEntry.getRepositoryCapability(WorkflowCapability.class);

		Assert.assertEquals(
			WorkflowConstants.STATUS_IN_TRASH,
			workflowCapability.getStatus(fileEntry));
	}

	@Test
	public void testDeleteMessageAttachment() throws Exception {
		MBMessage message = addMessage(null, false);

		byte[] fileBytes = FileUtil.getBytes(
			getClass(), "dependencies/company_logo.png");

		File file = FileUtil.createTempFile(fileBytes);

		MBMessageLocalServiceUtil.addMessageAttachment(
			TestPropsValues.getUserId(), message.getMessageId(), "test", file,
			"image/png");

		Assert.assertEquals(1, message.getAttachmentsFileEntriesCount());

		MBMessageLocalServiceUtil.deleteMessageAttachment(
			message.getMessageId(), "test");

		Assert.assertEquals(0, message.getAttachmentsFileEntriesCount());
	}

	@Test
	public void testThreadLastPostDate() throws Exception {
		Date date = new Date();

		MBMessage parentMessage = addMessage(null, false, date);

		MBMessage firstReplyMessage = addMessage(
			parentMessage, false, new Date(date.getTime() + Time.SECOND));

		MBMessage secondReplyMessage = addMessage(
			parentMessage, false, new Date(date.getTime() + Time.SECOND * 2));

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			PropsValues.INDEX_DATE_FORMAT_PATTERN);

		MBThread mbThread = MBThreadLocalServiceUtil.getThread(
			parentMessage.getThreadId());

		Assert.assertEquals(
			dateFormat.format(mbThread.getLastPostDate()),
			dateFormat.format(secondReplyMessage.getModifiedDate()));

		MBMessageLocalServiceUtil.deleteMessage(
			secondReplyMessage.getMessageId());

		mbThread = MBThreadLocalServiceUtil.getThread(
			parentMessage.getThreadId());

		Assert.assertEquals(
			dateFormat.format(mbThread.getLastPostDate()),
			dateFormat.format(firstReplyMessage.getModifiedDate()));
	}

	@Test
	public void testUpdateThreadAndMessageWhenBodyChanges() throws Exception {
		Date date = new Date();

		MBMessage message = addMessage(null, false, date);

		MBThread mbThread = MBThreadLocalServiceUtil.getThread(
			message.getThreadId());

		Assert.assertEquals(mbThread.getModifiedDate(), date);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBMessageLocalServiceUtil.updateMessage(
			message.getUserId(), message.getMessageId(), message.getSubject(),
			RandomTestUtil.randomString(), Collections.emptyList(),
			Collections.emptyList(), 0, false, serviceContext);

		mbThread = MBThreadLocalServiceUtil.getThread(message.getThreadId());

		Assert.assertNotEquals(mbThread.getModifiedDate(), date);
	}

	@Test
	public void testUpdateThreadAndMessageWhenSubjectChanges()
		throws Exception {

		Date date = new Date();

		MBMessage message = addMessage(null, false, date);

		MBThread mbThread = MBThreadLocalServiceUtil.getThread(
			message.getThreadId());

		Assert.assertEquals(mbThread.getModifiedDate(), date);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBMessageLocalServiceUtil.updateMessage(
			message.getUserId(), message.getMessageId(),
			RandomTestUtil.randomString(), message.getBody(),
			Collections.emptyList(), Collections.emptyList(), 0, false,
			serviceContext);

		mbThread = MBThreadLocalServiceUtil.getThread(message.getThreadId());

		Assert.assertNotEquals(mbThread.getModifiedDate(), date);
	}

	protected MBMessage addMessage(
			MBMessage parentMessage, boolean addAttachments)
		throws Exception {

		return addMessage(parentMessage, addAttachments, new Date());
	}

	protected MBMessage addMessage(
			MBMessage parentMessage, boolean addAttachments, Date date)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setCreateDate(date);
		serviceContext.setModifiedDate(date);

		long categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;
		long parentMessageId = MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID;
		long threadId = 0;

		if (parentMessage != null) {
			categoryId = parentMessage.getCategoryId();
			parentMessageId = parentMessage.getMessageId();
			threadId = parentMessage.getThreadId();
		}

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();

		if (addAttachments) {
			inputStreamOVPs = MBTestUtil.getInputStreamOVPs(
				"attachment.txt", getClass(), StringPool.BLANK);
		}

		return MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			_group.getGroupId(), categoryId, threadId, parentMessageId,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			MBMessageConstants.DEFAULT_FORMAT, inputStreamOVPs, false, 0.0,
			false, serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

}