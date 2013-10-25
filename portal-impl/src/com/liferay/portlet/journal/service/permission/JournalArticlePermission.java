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

package com.liferay.portlet.journal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.journal.NoSuchFolderException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class JournalArticlePermission {

	public static void check(
			PermissionChecker permissionChecker, JournalArticle article,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, article, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long resourcePrimKey,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, resourcePrimKey, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, String articleId,
			double version, String actionId)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, groupId, articleId, version, actionId)) {

			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, String articleId,
			int status, String actionId)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, groupId, articleId, status, actionId)) {

			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, String articleId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, articleId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, JournalArticle article,
			String actionId)
		throws PortalException, SystemException {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, article.getGroupId(),
			JournalArticle.class.getName(), article.getResourcePrimKey(),
			PortletKeys.JOURNAL, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (article.isPending()) {
			hasPermission = WorkflowPermissionUtil.hasPermission(
				permissionChecker, article.getGroupId(),
				JournalArticle.class.getName(), article.getResourcePrimKey(),
				actionId);

			if (hasPermission != null) {
				return hasPermission.booleanValue();
			}
		}
		else if (article.isDraft() && actionId.equals(ActionKeys.VIEW) &&
				 !_hasPermission(
					 permissionChecker, article, ActionKeys.UPDATE)) {

			return false;
		}

		if ((article.getFolderId() !=
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			!actionId.equals(ActionKeys.EXPIRE)) {

			try {
				JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(
					article.getFolderId());

				if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE &&
					!JournalFolderPermission.contains(
						permissionChecker, folder, ActionKeys.ACCESS) &&
					!JournalFolderPermission.contains(
						permissionChecker, folder, ActionKeys.VIEW)) {

					return false;
				}

				if (JournalFolderPermission.contains(
						permissionChecker, folder, actionId)) {

					return true;
				}
			}
			catch (NoSuchFolderException nsfe) {
				if (!article.isInTrash()) {
					throw nsfe;
				}
			}
		}

		return _hasPermission(permissionChecker, article, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long resourcePrimKey,
			String actionId)
		throws PortalException, SystemException {

		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(resourcePrimKey);

		return contains(permissionChecker, article, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String articleId,
			double version, String actionId)
		throws PortalException, SystemException {

		JournalArticle article = JournalArticleLocalServiceUtil.getArticle(
			groupId, articleId, version);

		return contains(permissionChecker, article, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String articleId,
			int status, String actionId)
		throws PortalException, SystemException {

		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(
				groupId, articleId, status);

		return contains(permissionChecker, article, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String articleId,
			String actionId)
		throws PortalException, SystemException {

		JournalArticle article = JournalArticleLocalServiceUtil.getArticle(
			groupId, articleId);

		return contains(permissionChecker, article, actionId);
	}

	private static boolean _hasPermission(
		PermissionChecker permissionChecker, JournalArticle article,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				article.getCompanyId(), JournalArticle.class.getName(),
				article.getResourcePrimKey(), article.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			article.getGroupId(), JournalArticle.class.getName(),
			article.getResourcePrimKey(), actionId);
	}

}