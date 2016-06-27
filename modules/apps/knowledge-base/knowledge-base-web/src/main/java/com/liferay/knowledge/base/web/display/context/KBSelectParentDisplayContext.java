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

package com.liferay.knowledge.base.web.display.context;

import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleServiceUtil;
import com.liferay.knowledge.base.service.KBFolderServiceUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class KBSelectParentDisplayContext {

	public KBSelectParentDisplayContext(
		long parentResourceClassNameId, long parentResourcePrimKey,
		HttpServletRequest request,
		LiferayPortletResponse liferayPortletResponse) {

		_parentResourceClassNameId = parentResourceClassNameId;
		_parentResourcePrimKey = parentResourcePrimKey;
		_request = request;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public void populatePortletBreadcrumbEntries(PortletURL portletURL)
		throws Exception {

		_doPopulatePortletBreadcrumbEntries(
			_parentResourceClassNameId, _parentResourcePrimKey, portletURL);
	}

	private void _doPopulatePortletBreadcrumbEntries(
			long parentResourceClassNameId, long parentResourcePrimKey,
			PortletURL portletURL)
		throws Exception {

		PortletURL currentURL = PortletURLUtil.clone(
			portletURL, _liferayPortletResponse);

		currentURL.setParameter(
			"parentResourceClassNameId",
			String.valueOf(parentResourceClassNameId));
		currentURL.setParameter(
			"parentResourcePrimKey", String.valueOf(parentResourcePrimKey));

		long kbFolderClassNameId = PortalUtil.getClassNameId(
			KBFolderConstants.getClassName());

		if (parentResourcePrimKey ==
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			PortalUtil.addPortletBreadcrumbEntry(
				_request, themeDisplay.translate("home"),
				currentURL.toString());
		}
		else if (parentResourceClassNameId == kbFolderClassNameId) {
			KBFolder kbFolder = KBFolderServiceUtil.getKBFolder(
				_parentResourcePrimKey);

			_doPopulatePortletBreadcrumbEntries(
				kbFolder.getClassNameId(), kbFolder.getParentKBFolderId(),
				currentURL);

			PortalUtil.addPortletBreadcrumbEntry(
				_request, kbFolder.getName(), currentURL.toString());
		}
		else {
			KBArticle kbArticle = KBArticleServiceUtil.getLatestKBArticle(
				parentResourcePrimKey, WorkflowConstants.STATUS_ANY);

			_doPopulatePortletBreadcrumbEntries(
				kbArticle.getParentResourceClassNameId(),
				kbArticle.getParentResourcePrimKey(), currentURL);

			PortalUtil.addPortletBreadcrumbEntry(
				_request, kbArticle.getTitle(), currentURL.toString());
		}
	}

	private final LiferayPortletResponse _liferayPortletResponse;
	private final long _parentResourceClassNameId;
	private final long _parentResourcePrimKey;
	private final HttpServletRequest _request;

}