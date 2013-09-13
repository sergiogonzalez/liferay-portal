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

package com.liferay.portal.convert;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLContentLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.store.Store;
import com.liferay.portlet.documentlibrary.store.StoreFactory;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.util.MBTestUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.util.WikiTestUtil;

import java.io.InputStream;

import java.util.List;

import org.junit.After;
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
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class ConvertDocumentLibraryTest {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		cleanRepositories();

		_group = GroupTestUtil.addGroup();

		_convertProcess = (ConvertProcess)InstancePool.get(
			_CONVERT_DOCUMENT_LIBRARY_CLASSNAME);

		_convertProcess.setParameterValues(new String[] {_DB_STORE_CLASSNAME});
	}

	@After
	public void tearDown() throws Exception {
		Store store = (Store)InstanceFactory.newInstance(
			ClassLoaderUtil.getPortalClassLoader(),
			_FILE_SYSTEM_STORE_CLASSNAME);

		StoreFactory.setInstance(store);

		PropsValues.DL_STORE_IMPL = _FILE_SYSTEM_STORE_CLASSNAME;

		GroupLocalServiceUtil.deleteGroup(_group);

		cleanRepositories();
	}

	@Test
	public void testMigrateDL() throws Exception {
		Folder folder = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceTestUtil.randomString());

		DLAppTestUtil.addFileEntry(
			TestPropsValues.getGroupId(), folder.getFolderId(),
			ServiceTestUtil.randomString() + ".txt");

		_convertProcess.convert();

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getDLFileEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			dlFileEntries.get(0).getFileEntryId());

		String fileEntryTitle = fileEntry.getTitle();

		Assert.assertTrue(fileEntryTitle.endsWith(".txt"));
	}

	@Test
	public void testMigrateImages() throws Exception {
		addImage();

		_convertProcess.convert();

		List<Image> images = ImageLocalServiceUtil.getImages();

		Image image = images.get(0);

		String expectedImageType = image.getType();

		Assert.assertEquals(expectedImageType, "jpg");
	}

	@Test
	public void testMigrateMB() throws Exception {
		MBMessage mbMessage = addMBMessageAttachement();

		_convertProcess.convert();

		List<FileEntry> mbMessageAttachments =
			mbMessage.getAttachmentsFileEntries();

		if (mbMessageAttachments.isEmpty()) {
			Assert.fail();
		}

		FileEntry fileEntry = mbMessageAttachments.get(0);

		String fileEntryTitle = fileEntry.getTitle();

		Assert.assertTrue(fileEntryTitle.endsWith(".docx"));
	}

	@Test
	public void testMigratePortlets() throws Exception {
		_convertProcess.convert();

		Store sourceStore = StoreFactory.getInstance();

		Assert.assertEquals(
			_DB_STORE_CLASSNAME, sourceStore.getClass().getCanonicalName());

		String status = MaintenanceUtil.getStatus();

		String[] expectedMesages = getExpectedMessages();

		Assert.assertTrue(status.contains(expectedMesages[0]));
		Assert.assertTrue(status.contains(expectedMesages[1]));
		Assert.assertTrue(status.contains(expectedMesages[2]));
		Assert.assertTrue(status.contains(expectedMesages[3]));
	}

	@Test
	public void testMigrateWiki() throws Exception {
		WikiPage wikiPage = addWikiPage();

		addWikiPageAttachment(wikiPage);

		_convertProcess.convert();

		List<FileEntry> wikiPageAttachments =
			wikiPage.getAttachmentsFileEntries();

		FileEntry fileEntry = wikiPageAttachments.get(0);

		String fileEntryTitle = fileEntry.getTitle();

		Assert.assertTrue(fileEntryTitle.endsWith(".docx"));
	}

	protected void addImage() throws Exception {
		Image image = ImageLocalServiceUtil.createImage(
			CounterLocalServiceUtil.increment());

		ImageLocalServiceUtil.addImage(image);

		ImageLocalServiceUtil.updateImage(
			image.getImageId(),
			FileUtil.getBytes(
				getClass().getResourceAsStream("dependencies/liferay.jpg")));
	}

	protected MBMessage addMBMessageAttachement() throws Exception {
		List<ObjectValuePair<String, InputStream>> objectValuePairs =
			MBTestUtil.getInputStreamOVPs(
				"OSX_Test.docx", getClass(), StringPool.BLANK);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		return MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(),
			TestPropsValues.getUser().getFullName(), _group.getGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, "Subject", "Body",
			MBMessageConstants.DEFAULT_FORMAT, objectValuePairs, false, 0,
			false, serviceContext);
	}

	protected WikiPage addWikiPage() throws Exception {
		WikiNode wikiNode = WikiTestUtil.addNode(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(50));

		return WikiTestUtil.addPage(
			wikiNode.getUserId(), _group.getGroupId(), wikiNode.getNodeId(),
			ServiceTestUtil.randomString(), true);
	}

	protected void addWikiPageAttachment(WikiPage wikiPage) throws Exception {
		WikiTestUtil.addWikiAttachment(
			wikiPage.getUserId(), wikiPage.getNodeId(), wikiPage.getTitle(),
			getClass());
	}

	protected void cleanRepositories() throws Exception {
		if (ImageLocalServiceUtil.getImagesCount() > 0) {
			List<Image> images = ImageLocalServiceUtil.getImages();

			for (Image image : images) {
				ImageLocalServiceUtil.deleteImage(image);
			}
		}

		if (DLFileEntryLocalServiceUtil.getFileEntriesCount() > 0) {
			List<DLFileEntry> dlFileEntries =
				DLFileEntryLocalServiceUtil.getDLFileEntries(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (DLFileEntry dlFileEntry : dlFileEntries) {
				DLAppLocalServiceUtil.deleteFileEntry(
					dlFileEntry.getFileEntryId());
			}
		}

		if (DLContentLocalServiceUtil.getDLContentsCount() > 0) {
			List<DLContent> dlContents =
				DLContentLocalServiceUtil.getDLContents(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (DLContent dlContent : dlContents) {
				DLContentLocalServiceUtil.deleteDLContent(
					dlContent.getContentId());
			}
		}
	}

	protected String[] getExpectedMessages() throws Exception {
		String[] expectedMessages = new String[4];

		expectedMessages[0] =
			"Migrating " + ImageLocalServiceUtil.getImagesCount() + " images";
		expectedMessages[1] =
			"Migrating " + DLFileEntryLocalServiceUtil.getFileEntriesCount() +
				" documents and media files";
		expectedMessages[2] =
			"Migrating message boards attachments in " +
			MBMessageLocalServiceUtil.getMBMessagesCount() + " messages";
		expectedMessages[3] =
			"Migrating wiki page attachments in " +
			WikiPageLocalServiceUtil.getWikiPagesCount() + " pages";

		return expectedMessages;
	}

	private static final String _CONVERT_DOCUMENT_LIBRARY_CLASSNAME =
		"com.liferay.portal.convert.ConvertDocumentLibrary";

	private String _DB_STORE_CLASSNAME =
		"com.liferay.portlet.documentlibrary.store.DBStore";

	private String _FILE_SYSTEM_STORE_CLASSNAME =
		"com.liferay.portlet.documentlibrary.store.FileSystemStore";

	private ConvertProcess _convertProcess;
	private Group _group;

}