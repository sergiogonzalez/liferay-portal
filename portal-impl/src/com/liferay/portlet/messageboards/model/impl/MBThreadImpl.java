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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.Repository;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Mika Koivisto
 */
public class MBThreadImpl extends MBThreadBaseImpl {

	public MBThreadImpl() {
	}

	public Folder addAttachmentsFolder()
		throws PortalException, SystemException {

		if (_attachmentsFolderId > 0) {
			return PortletFileRepositoryUtil.getPortletFolder(
				_attachmentsFolderId);
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
				getGroupId(), PortletKeys.MESSAGE_BOARDS, serviceContext);

		MBMessage message = MBMessageLocalServiceUtil.getMessage(
			getRootMessageId());

		return PortletFileRepositoryUtil.addPortletFolder(
			message.getUserId(), repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			String.valueOf(getThreadId()), serviceContext);
	}

	public Folder getAttachmentsFolder()
		throws PortalException, SystemException {

		if (_attachmentsFolderId > 0) {
			return PortletFileRepositoryUtil.getPortletFolder(
				_attachmentsFolderId);
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				getGroupId(), PortletKeys.MESSAGE_BOARDS);

		if (repository == null) {
			return null;
		}

		MBMessage message = MBMessageLocalServiceUtil.getMessage(
			getRootMessageId());

		return PortletFileRepositoryUtil.getPortletFolder(
			message.getUserId(), repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			String.valueOf(getThreadId()), serviceContext);
	}

	public Lock getLock() {
		try {
			return LockLocalServiceUtil.getLock(
				MBThread.class.getName(), getThreadId());
		}
		catch (Exception e) {
		}

		return null;
	}

	public MBCategory getTrashContainer() {
		try {
			MBCategory category = MBCategoryLocalServiceUtil.getCategory(
				getCategoryId());

			if (category.isInTrash()) {
				return category;
			}

			return category.getTrashContainer();
		}
		catch (Exception e) {
			return null;
		}
	}

	public boolean hasLock(long userId) {
		try {
			return LockLocalServiceUtil.hasLock(
				userId, MBThread.class.getName(), getThreadId());
		}
		catch (Exception e) {
		}

		return false;
	}

	public boolean isInTrashContainer() {
		if (getTrashContainer() != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isLocked() {
		try {
			if (isInTrash() || isInTrashContainer()) {
				return true;
			}

			return LockLocalServiceUtil.isLocked(
				MBThread.class.getName(), getThreadId());
		}
		catch (Exception e) {
		}

		return false;
	}

	public void setAttachmentsFolderId(long attachmentsFolderId) {
		_attachmentsFolderId = attachmentsFolderId;
	}

	private long _attachmentsFolderId;

}