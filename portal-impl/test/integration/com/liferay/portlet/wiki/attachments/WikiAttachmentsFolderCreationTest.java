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

package com.liferay.portlet.wiki.attachments;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.util.WikiTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class WikiAttachmentsFolderCreationTest {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddSecondWikiPageAttachmentCreatesNoFolder()
		throws Exception {

		int originalFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiPageAttachment();

		int firstFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiPageAttachment();

		int finalFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertNotSame(originalFolderCount, firstFolderCount);
		Assert.assertEquals(firstFolderCount, finalFolderCount);
	}

	@Test
	public void testAddWikiNodeCreatesNoFolder() throws Exception {
		int expectedFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiNode();
		int currentFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertNotNull(_node);
		Assert.assertEquals(expectedFolderCount, currentFolderCount);
	}

	@Test
	public void testAddWikiPageAttachmentsCreatesFolder() throws Exception {
		int initialFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		int firstFolderCount = assertAttachFileToPage1InNode1FromGroup1(
			initialFolderCount + createFolders(3));

		int secondFolderCount = assertAttachFileToPage2InNode1FromGroup1(
			firstFolderCount + createFolders(1));

		int thirdFolderCount = assertAttachFileToPage2InNode2FromGroup1(
			secondFolderCount + createFolders(2));

		int finalFolderCount = assertAttachFileToPage1InNode1FromGroup2(
			thirdFolderCount + createFolders(3));

		Assert.assertEquals(
			initialFolderCount + createFolders(9), finalFolderCount);
	}

	@Test
	public void testAddWikiPageCreatesNoFolder() throws Exception {
		int expectedFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiPage();
		int currentFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertNotNull(_page);
		Assert.assertEquals(expectedFolderCount, currentFolderCount);
	}

	@Test
	public void testDeleteWikiPageWithAttachments() throws Exception {
		addWikiPageAttachment();

		Folder attachmentsFolder = _page.getAttachmentsFolder();
		Folder nodeAttachmentsFolder = _page.getNodeAttachmentsFolder();

		Assert.assertNotNull(attachmentsFolder);
		Assert.assertNotNull(nodeAttachmentsFolder);

		try {
			WikiPageLocalServiceUtil.deletePage(
				_page.getNodeId(), _page.getTitle(), _page.getVersion());
		}
		catch (Exception e) {
			Assert.fail(
				"An exception was thrown, " +
				"testDeleteWikiPageWithAttachments failed");
		}
	}

	protected void addWikiNode() throws Exception {
		if (_group == null) {
			_group = GroupTestUtil.addGroup();
		}

		_node = WikiTestUtil.addNode(
			TestPropsValues.getUserId(), _group.getGroupId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(50));
	}

	protected void addWikiPage() throws Exception {
		if (_node == null) {
			addWikiNode();
		}

		_page =  WikiTestUtil.addPage(
			_node.getUserId(), _group.getGroupId(), _node.getNodeId(),
			ServiceTestUtil.randomString(), true);
	}

	protected void addWikiPageAttachment() throws Exception {
		if (_page == null) {
			addWikiPage();
		}

		WikiTestUtil.addWikiAttachment(
			_page.getUserId(), _page.getNodeId(), _page.getTitle(), getClass());
	}

	protected int assertAttachFile(int expectedFolderCount) throws Exception {
		addWikiPageAttachment();

		int currentFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertEquals(expectedFolderCount, currentFolderCount );

		return currentFolderCount;
	}

	protected int assertAttachFileToPage1InNode1FromGroup1(
			int expectedFolderCount)
		throws Exception {

		return assertAttachFile(expectedFolderCount);
	}

	protected int assertAttachFileToPage1InNode1FromGroup2(
			int expectedFolderCount)
		throws Exception {

		_group = null;
		_page = null;
		_node = null;

		return assertAttachFile(expectedFolderCount);
	}

	protected int assertAttachFileToPage2InNode1FromGroup1(
			int expectedFolderCount)
		throws Exception {

		_page = null;

		return assertAttachFile(expectedFolderCount);
	}

	protected int assertAttachFileToPage2InNode2FromGroup1(
			int expectedFolderCount)
		throws Exception {

		_page = null;
		_node = null;

		return assertAttachFile(expectedFolderCount);
	}

	private int createFolders(int i) {
		return i;
	}

	private Group _group;
	private WikiNode _node;
	private WikiPage _page;

}