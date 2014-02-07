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

package com.liferay.portlet.documentlibrary.notifications;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.notifications.BaseContentUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Roberto Díaz
 */
public class DocumentLibraryUserNotificationHandler
	extends BaseContentUserNotificationHandler<FileEntry> {

	public DocumentLibraryUserNotificationHandler() {
		super(
			PortletKeys.DOCUMENT_LIBRARY, "/document_library/view_file_entry",
			_NOTIFICATION_TITLES);
	}

	@Override
	protected FileEntry fetchClassedModel(long classPK)
		throws PortalException, SystemException {

		try {
			return DLAppLocalServiceUtil.getFileEntry(classPK);
		}
		catch (NoSuchFileEntryException nsfee) {
			return null;
		}
	}

	@Override
	protected String getTitle(FileEntry file) {
		return file.getTitle();
	}

	@Override
	protected long getUserId(FileEntry file) {
		return file.getUserId();
	}

	@Override
	protected void initPortletURL(
			PortletURL portletURL, boolean inPage, FileEntry file)
		throws PortletException {

		if (inPage) {
			portletURL.setParameter(
				"fileEntryId", String.valueOf(file.getFileEntryId()));
		}
		else {
			portletURL.setParameter(
				"fileEntryId", String.valueOf(file.getFileEntryId()));
			portletURL.setWindowState(WindowState.MAXIMIZED);
		}
	}

	private static final Map<Integer, String> _NOTIFICATION_TITLES =
			new HashMap<Integer, String>();

	static {
		_NOTIFICATION_TITLES.put(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			"x-wrote-a-new-file-entry");
		_NOTIFICATION_TITLES.put(
			UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
			"x-updated-a-file-entry");
	}

}