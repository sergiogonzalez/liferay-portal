/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.service.BaseServiceTestCase;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.portlet.documentlibrary.store.Store;

import java.io.ByteArrayInputStream;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
public class DLContentLocalServiceTest extends BaseServiceTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_dlContentLocalService =
			(DLContentLocalService)PortalBeanLocatorUtil.locate(
				DLContentLocalService.class.getName());
	}

	public void testAddContentByByteArray() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String path = randomString();

		DLContent dlContent = _dlContentLocalService.addContent(
			companyId, randomString(), nextLong(), repositoryId, path,
			Store.VERSION_DEFAULT, new byte[1024]);

		DLContent newDLContent = _dlContentLocalService.getContent(
			companyId, repositoryId, path);

		assertEquals(dlContent, newDLContent);
	}

	public void testAddContentByInputStream() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String path = randomString();

		DLContent dlContent = _dlContentLocalService.addContent(
			companyId, randomString(), nextLong(), repositoryId, path,
			Store.VERSION_DEFAULT, new ByteArrayInputStream(new byte[1024]),
			1024);

		DLContent newDLContent = _dlContentLocalService.getContent(
			companyId, repositoryId, path);

		assertEquals(dlContent, newDLContent);
	}

	public void testUpdateContent() throws Exception {
		long companyId = nextLong();
		long oldRepositoryId = nextLong();
		long newRepositoryId = nextLong();
		String oldPath = randomString();
		String newPath = randomString();

		DLContent dlContent = _dlContentLocalService.addContent(
			companyId, randomString(), nextLong(), oldRepositoryId, oldPath,
			Store.VERSION_DEFAULT, new byte[1024]);

		_dlContentLocalService.updateDLContent(
			companyId, oldRepositoryId, newRepositoryId, oldPath, newPath);

		DLContent newDLContent = _dlContentLocalService.getContent(
			companyId, newRepositoryId, newPath);

		dlContent.setRepositoryId(newRepositoryId);
		dlContent.setPath(newPath);

		assertEquals(dlContent, newDLContent);

		_dlContentLocalService.deleteDLContent(newDLContent);
	}

	protected void assertEquals(
			DLContent expectedDLContent, DLContent actualDLContent)
		throws Exception {

		assertEquals(
			expectedDLContent.getContentId(), actualDLContent.getContentId());
		assertEquals(
			expectedDLContent.getGroupId(), actualDLContent.getGroupId());
		assertEquals(
			expectedDLContent.getCompanyId(), actualDLContent.getCompanyId());
		assertEquals(
			expectedDLContent.getPortletId(), actualDLContent.getPortletId());
		assertEquals(
			expectedDLContent.getRepositoryId(),
			actualDLContent.getRepositoryId());
		assertEquals(expectedDLContent.getPath(), actualDLContent.getPath());
		assertEquals(
			expectedDLContent.getVersion(), actualDLContent.getVersion());
		assertEquals(expectedDLContent.getData(), actualDLContent.getData());
		assertEquals(expectedDLContent.getSize(), actualDLContent.getSize());
	}

	private DLContentLocalService _dlContentLocalService;

}