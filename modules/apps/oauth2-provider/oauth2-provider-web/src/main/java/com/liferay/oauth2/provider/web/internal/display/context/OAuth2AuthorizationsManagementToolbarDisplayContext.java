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

package com.liferay.oauth2.provider.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 */
public class OAuth2AuthorizationsManagementToolbarDisplayContext {

	public OAuth2AuthorizationsManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		PortletURL currentURLObj) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_currentURLObj = currentURLObj;

		_request = _liferayPortletRequest.getHttpServletRequest();

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			liferayPortletRequest);
	}

	public List<DropdownItem> getActionDropdownItems() {
		DropdownItemList dropdownItems = new DropdownItemList();

		dropdownItems.add(
			dropdownItem -> {
				dropdownItem.setHref(
					StringBundler.concat(
						"javascript:", _liferayPortletResponse.getNamespace(),
						"revokeOAuth2Authorizations();"));
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					LanguageUtil.get(_request, "revoke-authorizations"));
				dropdownItem.setQuickAction(true);
			});

		return dropdownItems;
	}

	public String getDisplayStyle() {
		return "list";
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "order-by"));
					});
			}
		};
	}

	public String getOrderByCol() {
		return ParamUtil.getString(_request, "orderByCol", "createDate");
	}

	public OrderByComparator<OAuth2Authorization> getOrderByComparator() {
		String orderByType = getOrderByType();
		String orderByCol = getOrderByCol();

		String columnName = "createDate";

		for (String orderByColumn : _orderByColumns) {
			if (orderByCol.equals(orderByColumn)) {
				columnName = orderByColumn;
			}
		}

		return OrderByComparatorFactoryUtil.create(
			"OAuth2Authorization", columnName, orderByType.equals("asc"));
	}

	public String getOrderByType() {
		return ParamUtil.getString(_request, "orderByType", "desc");
	}

	public PortletURL getSortingURL() throws PortletException {
		PortletURL currentSortingURL = _getCurrentSortingURL();

		currentSortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return currentSortingURL;
	}

	private PortletURL _getCurrentSortingURL() throws PortletException {
		PortletURL sortingURL = PortletURLUtil.clone(
			_currentURLObj, _liferayPortletResponse);

		sortingURL.setParameter(SearchContainer.DEFAULT_CUR_PARAM, "0");

		return sortingURL;
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				for (String orderByCol : _orderByColumns) {
					add(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.setActive(
									orderByCol.equals(getOrderByCol()));
								dropdownItem.setHref(
									_getCurrentSortingURL(), "orderByCol",
									orderByCol);
								dropdownItem.setLabel(
									LanguageUtil.get(_request, orderByCol));
							}));
				}
			}
		};
	}

	private static String[] _orderByColumns = {
		"createDate", "userId", "userName", "accessTokenCreateDate",
		"accessTokenExpirationDate", "refreshTokenCreateDate",
		"refreshTokenExpirationDate", "remoteIPInfo"
	};

	private final PortletURL _currentURLObj;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PortalPreferences _portalPreferences;
	private final HttpServletRequest _request;

}