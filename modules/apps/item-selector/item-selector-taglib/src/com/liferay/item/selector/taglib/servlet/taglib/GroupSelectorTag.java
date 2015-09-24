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

package com.liferay.item.selector.taglib.servlet.taglib;

import com.liferay.item.selector.taglib.servlet.ServletContextUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.portlet.usersadmin.search.GroupSearchTerms;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Roberto Díaz
 */
public class GroupSelectorTag extends IncludeTag {

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setSearchContainer(SearchContainer<Group> searchContainer) {
		_searchContainer = searchContainer;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_displayStyle = null;
		_portletURL = null;
		_searchContainer = null;
	}

	@Override
	protected String getPage() {
		return "/group_selector/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-item-selector:group-selector:displayStyle", _displayStyle);
		request.setAttribute(
			"liferay-item-selector:group-selector:searchContainer",
			getSearchContainer(request));
		request.setAttribute(
			"liferay-item-selector:group-selector:portletURL", _portletURL);
	}

	private SearchContainer<Group> getSearchContainer(
		HttpServletRequest request) {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		try {
			PortletRequest portletRequest =
				(PortletRequest)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			PortletResponse portletResponse =
				(PortletResponse)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			LiferayPortletResponse liferayPortletResponse =
				PortalUtil.getLiferayPortletResponse(portletResponse);

			PortletURL viewGroupSelectorURL = PortletURLUtil.clone(
				_portletURL, liferayPortletResponse);

			viewGroupSelectorURL.setParameter(
				"showGroupSelector", String.valueOf(true));

			SearchContainer searchContainer = new GroupSearch(
				portletRequest, viewGroupSelectorURL);

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			User user = themeDisplay.getUser();

			String keywords = ParamUtil.getString(request, "keywords");

			List<Group> results = new ArrayList<>();
			int total = 0;

			if (Validator.isNotNull(keywords)) {
				GroupSearchTerms searchTerms =
					(GroupSearchTerms)searchContainer.getSearchTerms();

				LinkedHashMap<String, Object> groupParams =
					new LinkedHashMap<>();

				groupParams.put("site", Boolean.TRUE);
				groupParams.put("usersGroups", Long.valueOf(user.getUserId()));

				results = GroupLocalServiceUtil.search(
					themeDisplay.getCompanyId(), null,
					searchTerms.getKeywords(), groupParams,
					searchContainer.getStart(), searchContainer.getEnd(), null);

				total = GroupLocalServiceUtil.searchCount(
					themeDisplay.getCompanyId(), null,
					searchTerms.getKeywords(), groupParams);
			}
			else {
				List<Group> groups = user.getMySiteGroups(
					null, searchContainer.getEnd());

				results = ListUtil.subList(
					groups, searchContainer.getStart(),
					searchContainer.getEnd());

				total = GroupServiceUtil.getUserSitesGroupsCount(
					user.getUserId(), null);
			}

			searchContainer.setTotal(total);
			searchContainer.setResults(results);

			return searchContainer;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Could not retrieve groups", e);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupSelectorTag.class);

	private String _displayStyle;
	private PortletURL _portletURL;
	private SearchContainer<Group> _searchContainer = null;

}