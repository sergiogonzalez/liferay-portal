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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class MBThreadLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAttachmentsWhenSplittingThread() throws Exception {
		MBMessage rootMessage = addMessage();
		MBMessage splitMessage = addMessage();
		MBMessage childMessage = addMessage();

		Assert.assertEquals(
			rootMessage.getThreadId(), splitMessage.getThreadId());

		MBMessageLocalServiceUtil.addMessageAttachment(
			TestPropsValues.getUserId(), rootMessage.getMessageId(), "test",
			_attachmentFile, "image/png");

		MBMessageLocalServiceUtil.addMessageAttachment(
			TestPropsValues.getUserId(), splitMessage.getMessageId(), "test",
			_attachmentFile, "image/png");

		MBMessageLocalServiceUtil.addMessageAttachment(
			TestPropsValues.getUserId(), childMessage.getMessageId(), "test",
			_attachmentFile, "image/png");

		Assert.assertEquals(1, rootMessage.getAttachmentsFileEntriesCount());
		Assert.assertEquals(1, splitMessage.getAttachmentsFileEntriesCount());
		Assert.assertEquals(1, childMessage.getAttachmentsFileEntriesCount());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBThreadLocalServiceUtil.splitThread(
			TestPropsValues.getUserId(), splitMessage.getMessageId(),
			RandomTestUtil.randomString(), serviceContext);

		rootMessage = MBMessageLocalServiceUtil.getMBMessage(
			rootMessage.getMessageId());

		splitMessage = MBMessageLocalServiceUtil.getMBMessage(
			splitMessage.getMessageId());

		childMessage = MBMessageLocalServiceUtil.getMBMessage(
			childMessage.getMessageId());

		Assert.assertNotEquals(
			rootMessage.getThreadId(), splitMessage.getThreadId());

		Assert.assertEquals(1, rootMessage.getAttachmentsFileEntriesCount());
		Assert.assertEquals(1, splitMessage.getAttachmentsFileEntriesCount());
		Assert.assertEquals(1, childMessage.getAttachmentsFileEntriesCount());
	}

	@Test
	public void testGetNoAssetThreads() throws Exception {
		addMessage();

		MBMessage message = addMessage();

		MBThread thread = message.getThread();

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			MBThread.class.getName(), thread.getThreadId());

		Assert.assertNotNull(assetEntry);

		AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry);

		List<MBThread> threads = MBThreadLocalServiceUtil.getNoAssetThreads();

		Assert.assertEquals(1, threads.size());
		Assert.assertEquals(thread, threads.get(0));
	}

	protected MBMessage addMessage() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			_group.getGroupId(), MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);
	}

	private static final File _attachmentFile = new File(
		"portal-impl/test/integration/com/liferay/portlet/messageboards" +
			"/attachments/dependencies/company_logo.png");

	@DeleteAfterTestRun
	private Group _group;

}