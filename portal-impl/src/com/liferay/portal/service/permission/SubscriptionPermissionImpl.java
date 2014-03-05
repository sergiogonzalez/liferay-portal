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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.permission.BlogsEntryPermission;
import com.liferay.portlet.blogs.service.permission.BlogsPermission;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.permission.BookmarksEntryPermission;
import com.liferay.portlet.bookmarks.service.permission.BookmarksFolderPermission;
import com.liferay.portlet.bookmarks.service.permission.BookmarksPermission;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.permission.JournalFolderPermission;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.messageboards.service.permission.MBPermission;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;

/**
 * @author Mate Thurzo
 * @author Raymond Augé
 */
public class SubscriptionPermissionImpl implements SubscriptionPermission {

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #check(PermissionChecker,
	 *             String, long, String, long)}
	 */
	@Deprecated
	@Override
	public void check(
			PermissionChecker permissionChecker, String className, long classPK)
		throws PortalException, SystemException {

		check(permissionChecker, className, classPK, null, 0);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, String subscriptionClassName,
			long subscriptionClassPK, String inferredClassName,
			long inferredClassPK)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, subscriptionClassName, subscriptionClassPK,
				inferredClassName, inferredClassPK)) {

			throw new PrincipalException();
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #contains(PermissionChecker,
	 *             String, long, String, long)}
	 */
	@Deprecated
	@Override
	public boolean contains(
			PermissionChecker permissionChecker, String className, long classPK)
		throws PortalException, SystemException {

		return contains(permissionChecker, className, classPK, null, 0);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, String subscriptionClassName,
			long subscriptionClassPK, String inferredClassName,
			long inferredClassPK)
		throws PortalException, SystemException {

		if (subscriptionClassName == null) {
			return false;
		}

		if (Validator.isNotNull(inferredClassName)) {
			Boolean hasPermission = hasPermission(
				permissionChecker, inferredClassName, inferredClassPK,
				ActionKeys.VIEW);

			if ((hasPermission == null) || !hasPermission) {
				return false;
			}
		}

		Boolean hasPermission = hasPermission(
			permissionChecker, subscriptionClassName, subscriptionClassPK,
			ActionKeys.SUBSCRIBE);

		if (hasPermission != null) {
			return hasPermission;
		}

		return true;
	}

	protected Boolean hasPermission(
			PermissionChecker permissionChecker, String className, long classPK,
			String actionId)
		throws PortalException, SystemException {

		MBDiscussion mbDiscussion =
			MBDiscussionLocalServiceUtil.fetchDiscussion(className, classPK);

		if (mbDiscussion != null) {
			if (className.equals(Layout.class.getName())) {
				return LayoutPermissionUtil.contains(
					permissionChecker, classPK, ActionKeys.VIEW);
			}

			MBThread mbThread = MBThreadLocalServiceUtil.fetchThread(
				mbDiscussion.getThreadId());

			if (className.equals(WorkflowInstance.class.getName())) {
				return permissionChecker.hasPermission(
					mbThread.getGroupId(), PortletKeys.WORKFLOW_DEFINITIONS,
					mbThread.getGroupId(), ActionKeys.VIEW);
			}

			return MBDiscussionPermission.contains(
				permissionChecker, mbThread.getCompanyId(),
				mbThread.getGroupId(), className, classPK, mbThread.getUserId(),
				ActionKeys.VIEW);
		}

		if (className.equals(BlogsEntry.class.getName())) {
			Group group = GroupLocalServiceUtil.fetchGroup(classPK);

			if (group == null) {
				return BlogsEntryPermission.contains(
					permissionChecker, classPK, actionId);
			}

			return BlogsPermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(BookmarksEntry.class.getName())) {
			return BookmarksEntryPermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(BookmarksFolder.class.getName())) {
			Group group = GroupLocalServiceUtil.fetchGroup(classPK);

			if (group == null) {
				BookmarksFolder folder =
					BookmarksFolderLocalServiceUtil.fetchBookmarksFolder(
						classPK);

				if (folder == null) {
					return null;
				}

				return BookmarksFolderPermission.contains(
					permissionChecker, folder, actionId);
			}

			return BookmarksPermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(DLFileEntry.class.getName())) {
			DLFileVersion fileVersion =
				DLFileVersionLocalServiceUtil.fetchDLFileVersion(classPK);

			if (fileVersion != null) {
				classPK = fileVersion.getFileEntryId();
			}

			return DLFileEntryPermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(DLFolder.class.getName())) {
			Group group = GroupLocalServiceUtil.fetchGroup(classPK);

			if (group == null) {
				DLFolder folder = DLFolderLocalServiceUtil.fetchFolder(classPK);

				if (folder == null) {
					return null;
				}

				return DLFolderPermission.contains(
					permissionChecker, folder, actionId);
			}

			return DLPermission.contains(permissionChecker, classPK, actionId);
		}
		else if (className.equals(JournalArticle.class.getName())) {
			JournalArticle article =
				JournalArticleLocalServiceUtil.fetchJournalArticle(classPK);

			if (article == null) {
				return false;
			}

			return JournalArticlePermission.contains(
				permissionChecker, article.getResourcePrimKey(), actionId);
		}
		else if (className.equals(JournalFolder.class.getName())) {
			Group group = GroupLocalServiceUtil.fetchGroup(classPK);

			if (group == null) {
				JournalFolder folder =
					JournalFolderLocalServiceUtil.fetchFolder(classPK);

				if (folder == null) {
					return null;
				}

				return JournalFolderPermission.contains(
					permissionChecker, folder, actionId);
			}

			return JournalPermission.contains(
				permissionChecker, classPK, actionId);
		}

		else if (className.equals(MBCategory.class.getName())) {
			Group group = GroupLocalServiceUtil.fetchGroup(classPK);

			if (group == null) {
				return MBCategoryPermission.contains(
					permissionChecker, classPK, actionId);
			}

			return MBPermission.contains(permissionChecker, classPK, actionId);
		}
		else if (className.equals(MBMessage.class.getName())) {
			return MBMessagePermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(MBThread.class.getName())) {
			MBThread mbThread = MBThreadLocalServiceUtil.fetchThread(classPK);

			if (mbThread == null) {
				return false;
			}

			return MBMessagePermission.contains(
				permissionChecker, mbThread.getRootMessageId(), actionId);
		}
		else if (className.equals(WikiNode.class.getName())) {
			return WikiNodePermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(WikiPage.class.getName())) {
			WikiPage page = WikiPageLocalServiceUtil.fetchWikiPage(classPK);

			if (page == null) {
				return null;
			}

			return WikiPagePermission.contains(
				permissionChecker, page.getResourcePrimKey(), actionId);
		}

		return null;
	}

}