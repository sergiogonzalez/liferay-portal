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

package com.liferay.knowledge.base.web.util;

import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBArticleServiceUtil;
import com.liferay.knowledge.base.service.KBFolderServiceUtil;
import com.liferay.knowledge.base.util.comparator.KBArticleCreateDateComparator;
import com.liferay.knowledge.base.util.comparator.KBArticleModifiedDateComparator;
import com.liferay.knowledge.base.util.comparator.KBArticlePriorityComparator;
import com.liferay.knowledge.base.util.comparator.KBArticleStatusComparator;
import com.liferay.knowledge.base.util.comparator.KBArticleTitleComparator;
import com.liferay.knowledge.base.util.comparator.KBArticleUserNameComparator;
import com.liferay.knowledge.base.util.comparator.KBArticleVersionComparator;
import com.liferay.knowledge.base.util.comparator.KBArticleViewCountComparator;
import com.liferay.knowledge.base.util.comparator.KBCommentCreateDateComparator;
import com.liferay.knowledge.base.util.comparator.KBCommentModifiedDateComparator;
import com.liferay.knowledge.base.util.comparator.KBCommentStatusComparator;
import com.liferay.knowledge.base.util.comparator.KBCommentUserNameComparator;
import com.liferay.knowledge.base.util.comparator.KBTemplateCreateDateComparator;
import com.liferay.knowledge.base.util.comparator.KBTemplateModifiedDateComparator;
import com.liferay.knowledge.base.util.comparator.KBTemplateTitleComparator;
import com.liferay.knowledge.base.util.comparator.KBTemplateUserNameComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class KBUtil {

	public static void addPortletBreadcrumbEntries(
			long originalParentResourceClassNameId,
			long originalParentResourcePrimKey, long parentResourceClassNameId,
			long parentResourcePrimKey, String mvcPath,
			HttpServletRequest request, RenderResponse renderResponse)
		throws PortalException {

		Map<String, Object> parameters = new HashMap<>();

		parameters.put(
			"originalParentResourceClassNameId",
			originalParentResourceClassNameId);
		parameters.put(
			"originalParentResourcePrimKey", originalParentResourcePrimKey);
		parameters.put("parentResourceClassNameId", parentResourceClassNameId);
		parameters.put("parentResourcePrimKey", parentResourcePrimKey);
		parameters.put("mvcPath", mvcPath);

		addPortletBreadcrumbEntries(parameters, request, renderResponse);
	}

	public static void addPortletBreadcrumbEntries(
			long parentResourceClassNameId, long parentResourcePrimKey,
			String mvcPath, HttpServletRequest request,
			RenderResponse renderResponse)
		throws PortalException {

		Map<String, Object> parameters = new HashMap<>();

		parameters.put("parentResourceClassNameId", parentResourceClassNameId);
		parameters.put("parentResourcePrimKey", parentResourcePrimKey);

		String mvcPathParameterValue = (String)parameters.get("mvcPath");

		if (Validator.isNull(mvcPathParameterValue) ||
			mvcPathParameterValue.equals("/admin/view.jsp") ||
			mvcPathParameterValue.equals("/admin/view_articles.jsp") ||
			mvcPathParameterValue.equals("/admin/view_folders.jsp")) {

			if (parentResourceClassNameId ==
					PortalUtil.getClassNameId(
						KBFolderConstants.getClassName())) {

				parameters.put("mvcPath", "/admin/view_folders.jsp");
			}
			else if (parentResourceClassNameId ==
						PortalUtil.getClassNameId(
							KBArticleConstants.getClassName())) {

				parameters.put("mvcPath", "/admin/view_articles.jsp");
				parameters.put("resourcePrimKey", parentResourcePrimKey);
			}
			else {
				parameters.put("mvcPath", "/admin/view.jsp");
			}
		}
		else {
			parameters.put("mvcPath", mvcPath);
		}

		addPortletBreadcrumbEntries(parameters, request, renderResponse);
	}

	public static OrderByComparator getKBArticleOrderByComparator(
		String orderByCol, String orderByType) {

		if (Validator.isNull(orderByCol) || Validator.isNull(orderByType)) {
			return null;
		}

		boolean ascending = false;

		if (orderByType.equals("asc")) {
			ascending = true;
		}

		if (orderByCol.equals("create-date")) {
			return new KBArticleCreateDateComparator(ascending);
		}
		else if (orderByCol.equals("modified-date")) {
			return new KBArticleModifiedDateComparator(ascending);
		}
		else if (orderByCol.equals("priority")) {
			return new KBArticlePriorityComparator(ascending);
		}
		else if (orderByCol.equals("status")) {
			return new KBArticleStatusComparator(ascending);
		}
		else if (orderByCol.equals("title")) {
			return new KBArticleTitleComparator(ascending);
		}
		else if (orderByCol.equals("user-name")) {
			return new KBArticleUserNameComparator(ascending);
		}
		else if (orderByCol.equals("version")) {
			return new KBArticleVersionComparator(ascending);
		}
		else if (orderByCol.equals("view-count")) {
			return new KBArticleViewCountComparator(ascending);
		}

		return null;
	}

	public static OrderByComparator<KBComment> getKBCommentOrderByComparator(
		String orderByCol, String orderByType) {

		if (Validator.isNull(orderByCol) || Validator.isNull(orderByType)) {
			return new KBCommentStatusComparator();
		}

		boolean ascending = false;

		if (orderByType.equals("asc")) {
			ascending = true;
		}

		if (orderByCol.equals("create-date")) {
			return new KBCommentCreateDateComparator(ascending);
		}
		else if (orderByCol.equals("modified-date")) {
			return new KBCommentModifiedDateComparator(ascending);
		}
		else if (orderByCol.equals("status")) {
			return new KBCommentStatusComparator(ascending);
		}
		else if (orderByCol.equals("user-name")) {
			return new KBCommentUserNameComparator(ascending);
		}

		return null;
	}

	public static OrderByComparator<KBTemplate> getKBTemplateOrderByComparator(
		String orderByCol, String orderByType) {

		if (Validator.isNull(orderByCol) || Validator.isNull(orderByType)) {
			return null;
		}

		boolean ascending = false;

		if (orderByType.equals("asc")) {
			ascending = true;
		}

		if (orderByCol.equals("create-date")) {
			return new KBTemplateCreateDateComparator(ascending);
		}
		else if (orderByCol.equals("modified-date")) {
			return new KBTemplateModifiedDateComparator(ascending);
		}
		else if (orderByCol.equals("title")) {
			return new KBTemplateTitleComparator(ascending);
		}
		else if (orderByCol.equals("user-name")) {
			return new KBTemplateUserNameComparator(ascending);
		}

		return null;
	}

	protected static void addPortletBreadcrumbEntries(
			Map<String, Object> parameters, HttpServletRequest request,
			RenderResponse renderResponse)
		throws PortalException {

		PortletURL portletURL = renderResponse.createRenderURL();

		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			Object value = entry.getValue();

			portletURL.setParameter(entry.getKey(), value.toString());
		}

		long kbFolderClassNameId = PortalUtil.getClassNameId(
			KBFolderConstants.getClassName());

		long parentResourceClassNameId = (Long)parameters.get(
			"parentResourceClassNameId");
		long parentResourcePrimKey = (Long)parameters.get(
			"parentResourcePrimKey");

		String mvcPath = (String)parameters.get("mvcPath");

		if (parentResourcePrimKey ==
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			portletURL.setParameter("mvcPath", "/admin/view.jsp");

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			PortalUtil.addPortletBreadcrumbEntry(
				request, themeDisplay.translate("home"), portletURL.toString());
		}
		else if (parentResourceClassNameId == kbFolderClassNameId) {
			KBFolder kbFolder = KBFolderServiceUtil.getKBFolder(
				parentResourcePrimKey);

			addPortletBreadcrumbEntries(
				kbFolder.getClassNameId(), kbFolder.getParentKBFolderId(),
				mvcPath, request, renderResponse);

			PortalUtil.addPortletBreadcrumbEntry(
				request, kbFolder.getName(), portletURL.toString());
		}
		else {
			KBArticle kbArticle = KBArticleServiceUtil.getLatestKBArticle(
				parentResourcePrimKey, WorkflowConstants.STATUS_ANY);

			addPortletBreadcrumbEntries(
				kbArticle.getParentResourceClassNameId(),
				kbArticle.getParentResourcePrimKey(), mvcPath, request,
				renderResponse);

			PortalUtil.addPortletBreadcrumbEntry(
				request, kbArticle.getTitle(), portletURL.toString());
		}
	}

}