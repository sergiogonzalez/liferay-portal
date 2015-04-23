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

package com.liferay.portlet.documentlibrary.trash;

import com.liferay.portal.InvalidRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileShortcutConstants;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileShortcutPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.trash.model.TrashEntry;

import javax.portlet.PortletRequest;

/**
 * Implements trash handling for the file shortcut entity.
 *
 * @author Zsolt Berentey
 */
public class DLFileShortcutTrashHandler extends DLBaseTrashHandler {

	@Override
	public void deleteTrashEntry(long classPK) throws PortalException {
		DLAppLocalServiceUtil.deleteFileShortcut(classPK);
	}

	@Override
	public String getClassName() {
		return DLFileShortcutConstants.getClassName();
	}

	@Override
	public ContainerModel getParentContainerModel(long classPK)
		throws PortalException {

		FileShortcut fileShortcut = getFileShortcut(classPK);

		long parentFolderId = fileShortcut.getFolderId();

		if (parentFolderId <= 0) {
			return null;
		}

		return getContainerModel(parentFolderId);
	}

	@Override
	public ContainerModel getParentContainerModel(TrashedModel trashedModel)
		throws PortalException {

		DLFileShortcut dlFileShortcut = (DLFileShortcut)trashedModel;

		return getContainerModel(dlFileShortcut.getFolderId());
	}

	@Override
	public String getRestoreContainedModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		FileShortcut fileShortcut = getFileShortcut(classPK);

		return DLUtil.getDLFileEntryControlPanelLink(
			portletRequest, fileShortcut.getToFileEntryId());
	}

	@Override
	public String getRestoreContainerModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		FileShortcut fileShortcut = getFileShortcut(classPK);

		return DLUtil.getDLFolderControlPanelLink(
			portletRequest, fileShortcut.getFolderId());
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException {

		FileShortcut fileShortcut = getFileShortcut(classPK);

		return DLUtil.getAbsolutePath(
			portletRequest, fileShortcut.getFolderId());
	}

	@Override
	public TrashEntry getTrashEntry(long classPK) throws PortalException {
		FileShortcut fileShortcut = getFileShortcut(classPK);

		return fileShortcut.getTrashEntry();
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK) throws PortalException {
		FileShortcut fileShortcut = getFileShortcut(classPK);

		return new DLFileShortcutTrashRenderer(fileShortcut);
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException {

		if (trashActionId.equals(TrashActionKeys.MOVE)) {
			return DLFolderPermission.contains(
				permissionChecker, groupId, classPK, ActionKeys.ADD_SHORTCUT);
		}

		return super.hasTrashPermission(
			permissionChecker, groupId, classPK, trashActionId);
	}

	@Override
	public boolean isInTrash(long classPK) throws PortalException {
		FileShortcut fileShortcut = getFileShortcut(classPK);

		return fileShortcut.isInTrash();
	}

	@Override
	public boolean isInTrashContainer(long classPK) throws PortalException {
		FileShortcut fileShortcut = getFileShortcut(classPK);

		return fileShortcut.isInTrashContainer();
	}

	@Override
	public boolean isRestorable(long classPK) throws PortalException {
		FileShortcut fileShortcut = getFileShortcut(classPK);

		try {
			fileShortcut.getFolder();
		}
		catch (NoSuchFolderException nsfe) {
			return false;
		}

		return !fileShortcut.isInTrashContainer();
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		FileShortcut fileShortcut = getFileShortcut(classPK);

		DLAppLocalServiceUtil.updateFileShortcut(
			userId, classPK, containerModelId, fileShortcut.getToFileEntryId(),
			serviceContext);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		DLAppHelperLocalServiceUtil.moveFileShortcutFromTrash(
			userId, getFileShortcut(classPK), containerModelId, serviceContext);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException {

		DLAppHelperLocalServiceUtil.restoreFileShortcutFromTrash(
			userId, getFileShortcut(classPK));
	}

	protected FileShortcut getFileShortcut(long classPK)
		throws PortalException {

		return DLAppLocalServiceUtil.getFileShortcut(classPK);
	}

	@Override
	protected Repository getRepository(long classPK) throws PortalException {
		FileShortcut fileShortcut = getFileShortcut(classPK);

		Repository repository = RepositoryServiceUtil.getRepositoryImpl(
			0, fileShortcut.getToFileEntryId(), 0);

		if (!repository.isCapabilityProvided(TrashCapability.class)) {
			throw new InvalidRepositoryException(
				"Repository " + repository.getRepositoryId() +
					" does not support trash operations");
		}

		return repository;
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		FileShortcut fileShortcut = getFileShortcut(classPK);

		if (fileShortcut.isInHiddenFolder() &&
			actionId.equals(ActionKeys.VIEW)) {

			return false;
		}

		return DLFileShortcutPermission.contains(
			permissionChecker, classPK, actionId);
	}

}