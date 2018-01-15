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

package com.liferay.portlet.messageboards.util;

import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Eudaldo Alonso
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class MBMessageAttachmentsUtil {

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.message.boards.service.impl.
	 *             MBMessageLocalServiceImpl#fetchFileEntryMessage}
	 */
	@Deprecated
	public static MBMessage fetchMessage(long fileEntryId)
		throws PortalException {

		return MBMessageLocalServiceUtil.fetchMBMessage(
			getMessageId(fileEntryId));
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.message.boards.service.impl.
	 *             MBMessageLocalServiceImpl#getFileEntryMessage}
	 */
	@Deprecated
	public static MBMessage getMessage(long fileEntryId)
		throws PortalException {

		return MBMessageLocalServiceUtil.getMBMessage(
			getMessageId(fileEntryId));
	}

	protected static long getMessageId(long fileEntryId)
		throws PortalException {

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			fileEntryId);

		Folder folder = PortletFileRepositoryUtil.getPortletFolder(
			fileEntry.getFolderId());

		return GetterUtil.getLong(folder.getName());
	}

}