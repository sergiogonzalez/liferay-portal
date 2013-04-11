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

package com.liferay.portlet.documentlibrary;

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.documentlibrary.service.BaseDLAppTestCase;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class DLFolderImplTest extends BaseDLAppTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		folder = DLAppTestUtil.addFolder(
			group.getGroupId(), parentFolder.getParentFolderId(),
			ServiceTestUtil.randomString());

		Folder folder1 = DLAppTestUtil.addFolder(
			group.getGroupId(), folder.getFolderId(),
			ServiceTestUtil.randomString());

		Folder folder11 = DLAppTestUtil.addFolder(
			group.getGroupId(), folder1.getFolderId(),
			ServiceTestUtil.randomString());

		Folder folder12 = DLAppTestUtil.addFolder(
			group.getGroupId(), folder1.getFolderId(),
			ServiceTestUtil.randomString());

		Folder folder2 = DLAppTestUtil.addFolder(
			group.getGroupId(), folder.getFolderId(),
			ServiceTestUtil.randomString());

		folderIds = new ArrayList<Long>();

		folderIds.add(folder1.getFolderId());
		folderIds.add(folder11.getFolderId());
		folderIds.add(folder12.getFolderId());
		folderIds.add(folder2.getFolderId());
	}

	@Test
	public void testGetDescendantFolderIds() throws Exception {
		List<Long> descendantFolderIds = folder.getDescendantFolderIds();

		Assert.assertEquals(4, descendantFolderIds.size());

		Iterator<Long> iterator = descendantFolderIds.iterator();

		while (iterator.hasNext()) {
			long descendantFolderId = iterator.next();

			if (folderIds.contains(descendantFolderId)) {
				folderIds.remove(descendantFolderId);

				iterator.remove();
			}
		}

		Assert.assertEquals(0, descendantFolderIds.size());
	}

	@Test
	public void testGetDescendants() throws Exception {
		List<Folder> descendants = folder.getDescendants();

		Assert.assertEquals(4, descendants.size());

		Iterator<Folder> iterator = descendants.iterator();

		while (iterator.hasNext()) {
			Folder descendant = iterator.next();

			if (folderIds.contains(descendant.getFolderId())) {
				folderIds.remove(descendant.getFolderId());

				iterator.remove();
			}
		}

		Assert.assertEquals(0, descendants.size());
	}

	protected Folder folder;
	protected List<Long> folderIds;

}