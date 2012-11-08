/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

/**
 * @author Eudaldo Alonso
 */
public class WikiPageAttachmentUtil {

	public static WikiPage getPageByFileEntryId(long fileEntryId)
		throws PortalException, SystemException {

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			fileEntryId);

		long folderId = fileEntry.getFolderId();

		Folder folder = PortletFileRepositoryUtil.getPortletFolder(folderId);

		long resourcePrimKey = GetterUtil.getLong(folder.getName());

		return WikiPageLocalServiceUtil.getPage(resourcePrimKey);
	}

	public static long getPageFolderId(
			long groupId, long userId, long nodeId, long pageId)
		throws PortalException, SystemException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		long repositoryId = PortletFileRepositoryUtil.getPortletRepository(
			groupId, PortletKeys.WIKI, serviceContext);

		Folder nodeFolder = PortletFileRepositoryUtil.getPortletFolder(
			userId, repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			String.valueOf(nodeId), serviceContext);

		Folder pageFolder = PortletFileRepositoryUtil.getPortletFolder(
			userId, repositoryId, nodeFolder.getFolderId(),
			String.valueOf(pageId), serviceContext);

		return pageFolder.getFolderId();
	}

}