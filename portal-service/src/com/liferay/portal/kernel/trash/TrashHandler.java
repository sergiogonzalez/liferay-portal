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

package com.liferay.portal.kernel.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.trash.model.TrashEntry;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletRequest;

/**
 * Represents the interface to manage the basic operations of the Recycle Bin.
 *
 * <p>
 * The basic operations are:
 * </p>
 *
 * <ul>
 * <li>
 * Deletion of entries
 * </li>
 * <li>
 * Restore of entries
 * </li>
 * </ul>
 *
 * <p>
 * The entities that support these operations are:
 * </p>
 *
 * <ul>
 * <li>
 * BlogsEntry {@link com.liferay.portlet.blogs.trash.BlogsEntryTrashHandler}
 * </li>
 * <li>
 * BookmarksEntry {@link
 * com.liferay.portlet.bookmarks.trash.BookmarksEntryTrashHandler}
 * </li>
 * <li>
 * DLFileEntry {@link
 * com.liferay.portlet.documentlibrary.trash.DLFileEntryTrashHandler}
 * </li>
 * <li>
 * DLFileShortcut {@link
 * com.liferay.portlet.documentlibrary.trash.DLFileShortcutTrashHandler}
 * </li>
 * <li>
 * DLFolder {@link
 * com.liferay.portlet.documentlibrary.trash.DLFolderTrashHandler}
 * </li>
 * <li>
 * MBThread {@link
 * com.liferay.portlet.messageboards.trash.MBThreadTrashHandler}
 * </li>
 * <li>
 * WikiNode {@link
 * com.liferay.portlet.wiki.trash.WikiNodeTrashHandler}
 * </li>
 * <li>
 * WikiPage {@link
 * com.liferay.portlet.wiki.trash.WikiPageTrashHandler}
 * </li>
 * </ul>
 *
 * @author Alexander Chow
 * @author Zsolt Berentey
 */
public interface TrashHandler {

	public void checkDuplicateTrashEntry(TrashEntry trashEntry, String newName)
		throws PortalException, SystemException;

	public void deleteTrashAttachments(Group group, Date date)
		throws PortalException, SystemException;

	public void deleteTrashEntries(long[] classPKs)
		throws PortalException, SystemException;

	public void deleteTrashEntries(long[] classPKs, boolean checkPermission)
		throws PortalException, SystemException;

	public void deleteTrashEntry(long classPK)
		throws PortalException, SystemException;

	public void deleteTrashEntry(long classPK, boolean checkPermission)
		throws PortalException, SystemException;

	public String getClassName();

	public ContainerModel getContainerModel(long containerModelId)
		throws PortalException, SystemException;

	public String getContainerModelName();

	public List<ContainerModel> getContainerModels(
			long trashEntryId, long containerModelId, int start, int end)
		throws PortalException, SystemException;

	public int getContainerModelsCount(long trashEntryId, long containerModelId)
		throws PortalException, SystemException;

	public String getDeleteMessage();

	public String getRestoreLink(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException;

	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException;

	public String getRootContainerModelName();

	public String getSubcontainerModelName();

	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException;

	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException;

	public boolean isInTrash(long classPK)
		throws PortalException, SystemException;

	public boolean isRestorable(long classPK)
		throws PortalException, SystemException;

	public void moveTrashEntry(
			long classPK, long containerModelId, ServiceContext serviceContext)
		throws PortalException, SystemException;

	public void restoreTrashEntries(long[] classPKs)
		throws PortalException, SystemException;

	public void restoreTrashEntry(long classPK)
		throws PortalException, SystemException;

	public void updateTitle(long classPK, String title)
		throws PortalException, SystemException;

}