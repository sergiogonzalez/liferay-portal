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

package com.liferay.portlet.wiki.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.trash.DuplicateEntryException;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.portlet.wiki.asset.WikiPageAssetRenderer;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;
/*
 * @author Eudaldo Alonso
 */
public class WikiPageTrashHandler extends BaseTrashHandler {

	public static final String CLASS_NAME = WikiPage.class.getName();

	@Override
	public void checkDuplicateTrashEntry(TrashEntry trashEntry, String newName)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(
			trashEntry.getClassPK());

		String restoredTitle = page.getTitle();

		if (Validator.isNotNull(newName)) {
			restoredTitle = newName;
		}

		String originalTitle = TrashUtil.stripTrashNamespace(restoredTitle);

		WikiPage duplicatePage = WikiPageLocalServiceUtil.fetchPage(
			page.getNodeId(), originalTitle, page.getVersion());

		if (duplicatePage != null) {
			DuplicateEntryException dee = new DuplicateEntryException();

			dee.setDuplicateEntryId(duplicatePage.getPageId());
			dee.setOldName(duplicatePage.getTitle());
			dee.setTrashEntryId(trashEntry.getEntryId());

			throw dee;
		}
	}

	/**
	 * Deletes all wiki page with the matching primary keys.
	 *
	 * @param  classPKs the primary keys of the wiki pages to be deleted
	 * @param  checkPermission whether to check permission before deleting each
	 *         folder
	 * @throws PortalException if any one of the wiki pages could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTrashEntries(long[] classPKs, boolean checkPermission)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

			if (checkPermission) {
				WikiPageServiceUtil.deletePage(
					page.getNodeId(), page.getTitle());
			}
			else {
				WikiPageLocalServiceUtil.deletePage(
					page.getNodeId(), page.getTitle());
			}
		}
	}

	/**
	 * Returns the wiki page entity's class name
	 *
	 * @return the wiki page entity's class name
	 */
	public String getClassName() {
		return CLASS_NAME;
	}

	/**
	 * Returns the trash renderer associated to the trash entry.
	 *
	 * @param  classPK the primary key of the wiki page
	 * @return the trash renderer associated to the wiki page
	 * @throws PortalException if the wiki page could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		return new WikiPageAssetRenderer(page);
	}

	/**
	 * Restores all wiki pages with the matching primary keys.
	 *
	 * @param  classPKs the primary keys of the wiki pages to be deleted
	 * @throws PortalException if any one of the wiki pages could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void restoreTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			WikiPageServiceUtil.restorePageFromTrash(classPK);
		}
	}

	@Override
	public void updateTitle(long classPK, String name)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		page.setTitle(name);

		WikiPageLocalServiceUtil.updateWikiPage(page, false);
	}

}