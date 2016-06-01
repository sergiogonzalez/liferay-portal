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

import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBCommentConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBCommentServiceUtil;
import com.liferay.knowledge.base.service.KBFolderLocalServiceUtil;
import com.liferay.knowledge.base.util.KnowledgeBaseUtil;
import com.liferay.knowledge.base.web.constants.KBWebKeys;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 * @author Roberto Díaz
 */
public class KBSuggestionListDisplayContext {

	public KBSuggestionListDisplayContext(
		HttpServletRequest request, String templatePath, KBArticle kbArticle) {

		_request = request;

		_navigation = ParamUtil.getString(_request, "navigation", "all");

		_templatePath = templatePath;
		_kbArticle = kbArticle;

		_groupId = kbArticle.getGroupId();
	}

	public KBSuggestionListDisplayContext(
		HttpServletRequest request, String templatePath, long groupId) {

		_request = request;

		_navigation = ParamUtil.getString(_request, "navigation", "all");

		_templatePath = templatePath;
		_groupId = groupId;
	}

	public String getEmptyResultsMessage() {
		if (_navigation.equals("new")) {
			return "there-are-no-new-suggestions";
		}
		else if (_navigation.equals("in-progress")) {
			return "there-are-no-suggestions-in-progress";
		}
		else if (_navigation.equals("resolved")) {
			return "there-are-no-resolved-suggestions";
		}

		return "there-are-no-suggestions";
	}

	public Map<String, String> getOrderColumns() {
		Map<String, String> orderColumns = new HashMap<>();

		if (_navigation.equals("all")) {
			orderColumns.put("status", "status");
		}

		orderColumns.put("modified-date", "modified-date");
		orderColumns.put("user-name", "user-name");

		return orderColumns;
	}

	public String getViewSuggestionURL(PortletURL portletURL)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			KBWebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletId = portletDisplay.getId();

		portletURL.setParameter("expanded", Boolean.TRUE.toString());

		if (_kbArticle == null) {
			portletURL.setParameter("mvcPath", "/admin/view_suggestions.jsp");
		}
		else if (Validator.isNull(_kbArticle.getUrlTitle()) ||
				 portletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ADMIN)) {

			if (portletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ADMIN)) {
				portletURL.setParameter(
					"mvcPath", _templatePath + "view_article.jsp");
			}

			portletURL.setParameter(
				"resourceClassNameId",
				String.valueOf(_kbArticle.getClassNameId()));
			portletURL.setParameter(
				"resourcePrimKey",
				String.valueOf(_kbArticle.getResourcePrimKey()));
		}
		else {
			portletURL.setParameter("urlTitle", _kbArticle.getUrlTitle());

			if (_kbArticle.getKbFolderId() !=
					KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				KBFolder kbFolder = KBFolderLocalServiceUtil.getKBFolder(
					_kbArticle.getKbFolderId());

				portletURL.setParameter(
					"kbFolderUrlTitle", kbFolder.getUrlTitle());
			}
		}

		return portletURL.toString() + "#kbSuggestions";
	}

	public String getViewSuggestionURL(RenderResponse renderResponse)
		throws PortalException {

		return getViewSuggestionURL(renderResponse.createRenderURL());
	}

	public boolean isShowKBArticleTitle() {
		if (_kbArticle == null) {
			return true;
		}

		return false;
	}

	public void populateOrderByColAndOrderByType(
		SearchContainer searchContainer) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		String orderByCol = ParamUtil.getString(
			_request, "orderByCol",
			_navigation.equals("all") ? "status" : "modified-date");

		if (!_navigation.equals("all") && orderByCol.equals("status")) {
			orderByCol = "modified-date";
		}

		String orderByType = ParamUtil.getString(_request, "orderByType");

		boolean storeOrderByPreference = ParamUtil.getBoolean(
			_request, "storeOrderByPreference", true);

		if (storeOrderByPreference && Validator.isNotNull(orderByCol) &&
			Validator.isNotNull(orderByType)) {

			portalPreferences.setValue(
				KBPortletKeys.KNOWLEDGE_BASE_ADMIN, "suggestions-order-by-col",
				orderByCol);
			portalPreferences.setValue(
				KBPortletKeys.KNOWLEDGE_BASE_ADMIN, "suggestions-order-by-type",
				orderByType);
		}
		else if (Validator.isNull(orderByType)) {
			orderByType = portalPreferences.getValue(
				KBPortletKeys.KNOWLEDGE_BASE_ADMIN, "suggestions-order-by-type",
				"desc");
		}

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);
	}

	public void populateResultsAndTotal(
			SearchContainer<KBComment> searchContainer)
		throws PortalException {

		int status = _getStatus();

		if (_kbArticle == null) {
			if (status == KBCommentConstants.STATUS_ANY) {
				searchContainer.setTotal(
					KBCommentServiceUtil.getKBCommentsCount(_groupId));

				searchContainer.setResults(
					KBCommentServiceUtil.getKBComments(
						_groupId, searchContainer.getStart(),
						searchContainer.getEnd(),
						KnowledgeBaseUtil.getKBCommentOrderByComparator(
							searchContainer.getOrderByCol(),
							searchContainer.getOrderByType())));
			}
			else {
				searchContainer.setTotal(
					KBCommentServiceUtil.getKBCommentsCount(_groupId, status));

				searchContainer.setResults(
					KBCommentServiceUtil.getKBComments(
						_groupId, status, searchContainer.getStart(),
						searchContainer.getEnd(),
						KnowledgeBaseUtil.getKBCommentOrderByComparator(
							searchContainer.getOrderByCol(),
							searchContainer.getOrderByType())));
			}
		}
		else {
			if (status == KBCommentConstants.STATUS_ANY) {
				searchContainer.setTotal(
					KBCommentServiceUtil.getKBCommentsCount(
						_groupId, KBArticleConstants.getClassName(),
						_kbArticle.getClassPK()));

				searchContainer.setResults(
					KBCommentServiceUtil.getKBComments(
						_groupId, KBArticleConstants.getClassName(),
						_kbArticle.getClassPK(), searchContainer.getStart(),
						searchContainer.getEnd(),
						KnowledgeBaseUtil.getKBCommentOrderByComparator(
							searchContainer.getOrderByCol(),
							searchContainer.getOrderByType())));
			}
			else {
				searchContainer.setTotal(
					KBCommentServiceUtil.getKBCommentsCount(
						_groupId, KBArticleConstants.getClassName(),
						_kbArticle.getClassPK(), status));

				searchContainer.setResults(
					KBCommentServiceUtil.getKBComments(
						_groupId, KBArticleConstants.getClassName(),
						_kbArticle.getClassPK(), status,
						searchContainer.getStart(), searchContainer.getEnd(),
						KnowledgeBaseUtil.getKBCommentOrderByComparator(
							searchContainer.getOrderByCol(),
							searchContainer.getOrderByType())));
			}
		}
	}

	private int _getStatus() {
		if (_navigation.equals("new")) {
			return KBCommentConstants.STATUS_NEW;
		}
		else if (_navigation.equals("in-progress")) {
			return KBCommentConstants.STATUS_IN_PROGRESS;
		}
		else if (_navigation.equals("resolved")) {
			return KBCommentConstants.STATUS_COMPLETED;
		}

		return KBCommentConstants.STATUS_ANY;
	}

	private final long _groupId;
	private KBArticle _kbArticle;
	private final String _navigation;
	private final HttpServletRequest _request;
	private final String _templatePath;

}