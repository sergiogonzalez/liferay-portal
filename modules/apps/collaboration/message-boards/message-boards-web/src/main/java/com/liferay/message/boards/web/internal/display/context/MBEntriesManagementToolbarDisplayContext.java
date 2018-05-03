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

package com.liferay.message.boards.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.web.internal.security.permission.MBCategoryPermission;
import com.liferay.message.boards.web.internal.util.MBUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class MBEntriesManagementToolbarDisplayContext {

	public MBEntriesManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, PortletURL currentURLObj,
		TrashHelper trashHelper) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_currentURLObj = currentURLObj;
		_trashHelper = trashHelper;

		_request = _liferayPortletRequest.getHttpServletRequest();

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			liferayPortletRequest);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
					WebKeys.THEME_DISPLAY);

				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setHref(
								StringBundler.concat(
									"javascript:",
									_liferayPortletResponse.getNamespace(),
									"deleteEntries();"));

							boolean trashEnabled = _trashHelper.isTrashEnabled(
								themeDisplay.getScopeGroupId());

							dropdownItem.setIcon(
								trashEnabled ? "trash" : "times");

							String label = "delete";

							if (trashEnabled) {
								label = "recycle-bin";
							}

							dropdownItem.setLabel(
								LanguageUtil.get(_request, label));

							dropdownItem.setQuickAction(true);
						}));

				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setHref(
								StringBundler.concat(
									"javascript:",
									_liferayPortletResponse.getNamespace(),
									"lockEntries();"));

							dropdownItem.setIcon("lock");

							dropdownItem.setLabel(
								LanguageUtil.get(_request, "lock"));

							dropdownItem.setQuickAction(true);
						}));
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setHref(
								StringBundler.concat(
									"javascript:",
									_liferayPortletResponse.getNamespace(),
									"unlockEntries();"));

							dropdownItem.setIcon("unlock");

							dropdownItem.setLabel(
								LanguageUtil.get(_request, "unlock"));

							dropdownItem.setQuickAction(true);
						}));
			}
		};
	}

	public CreationMenu getCreationMenu() throws PortalException {
		CreationMenu creationMenu = new CreationMenu();

		MBCategory category = (MBCategory)_request.getAttribute(
			WebKeys.MESSAGE_BOARDS_CATEGORY);

		long categoryId = MBUtil.getCategoryId(_request, category);

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (MBCategoryPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), categoryId,
				ActionKeys.ADD_CATEGORY)) {

			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(
						_liferayPortletResponse.createRenderURL(),
						"mvcRenderCommandName", "/message_boards/edit_category",
						"redirect", _currentURLObj.toString(),
						"parentCategoryId", String.valueOf(categoryId));

					String label = "category[message-board]";

					if (categoryId !=
							MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

						label = "subcategory[message-board]";
					}

					dropdownItem.setLabel(LanguageUtil.get(_request, label));
				});
		}

		if (MBCategoryPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), categoryId,
				ActionKeys.ADD_MESSAGE)) {

			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(
						_liferayPortletResponse.createRenderURL(),
						"mvcRenderCommandName", "/message_boards/edit_message",
						"redirect", _currentURLObj.toString(), "mbCategoryId",
						String.valueOf(categoryId));
					dropdownItem.setLabel(LanguageUtil.get(_request, "thread"));
				});
		}

		return creationMenu;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					SafeConsumer.ignore(
						dropdownGroupItem -> {
							dropdownGroupItem.setDropdownItems(
								_getFilterNavigationDropdownItems());
							dropdownGroupItem.setLabel(
								LanguageUtil.get(
									_request, "filter-by-navigation"));
						}));
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
		if (_orderByCol != null) {
			return _orderByCol;
		}

		String orderByCol = ParamUtil.getString(_request, "orderByCol");

		if (Validator.isNotNull(orderByCol)) {
			_portalPreferences.setValue(
				MBPortletKeys.MESSAGE_BOARDS_ADMIN, "order-by-col", orderByCol);
		}
		else {
			orderByCol = _portalPreferences.getValue(
				MBPortletKeys.MESSAGE_BOARDS_ADMIN, "order-by-col",
				"modified-date");
		}

		_orderByCol = orderByCol;

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		String orderByType = ParamUtil.getString(_request, "orderByType");

		if (Validator.isNotNull(orderByType)) {
			_portalPreferences.setValue(
				MBPortletKeys.MESSAGE_BOARDS_ADMIN, "order-by-type",
				orderByType);
		}
		else {
			orderByType = _portalPreferences.getValue(
				MBPortletKeys.MESSAGE_BOARDS_ADMIN, "order-by-type", "desc");
		}

		_orderByType = orderByType;

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		MBCategory category = (MBCategory)_request.getAttribute(
			WebKeys.MESSAGE_BOARDS_CATEGORY);

		long categoryId = MBUtil.getCategoryId(_request, category);

		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			portletURL.setParameter(
				"mvcRenderCommandName", "/message_boards/view");
		}
		else {
			portletURL.setParameter(
				"mvcRenderCommandName", "/message_boards/view_category");
			portletURL.setParameter("mbCategoryId", String.valueOf(categoryId));
		}

		String keywords = ParamUtil.getString(_request, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public String getSearchActionURL() {
		PortletURL searchURL = _liferayPortletResponse.createRenderURL();

		searchURL.setParameter(
			"mvcRenderCommandName", "/message_boards_admin/search");
		searchURL.setParameter("redirect", _currentURLObj.toString());

		MBCategory category = (MBCategory)_request.getAttribute(
			WebKeys.MESSAGE_BOARDS_CATEGORY);

		long categoryId = MBUtil.getCategoryId(_request, category);

		searchURL.setParameter(
			"breadcrumbsCategoryId", String.valueOf(categoryId));
		searchURL.setParameter("searchCategoryId", String.valueOf(categoryId));

		return searchURL.toString();
	}

	public PortletURL getSortingURL() throws PortletException {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		String keywords = ParamUtil.getString(_request, "keywords");

		if (Validator.isNotNull(keywords)) {
			sortingURL.setParameter("keywords", keywords);
		}

		return sortingURL;
	}

	public ViewTypeItemList getViewTypes() throws PortletException {
		PortletURL portletURL = getPortletURL();

		return new ViewTypeItemList(portletURL, "descriptive") {
			{
				ViewTypeItem cardViewTypeItem = addCardViewTypeItem();

				cardViewTypeItem.setDisabled(true);

				addListViewTypeItem();

				ViewTypeItem tableViewTypeItem = addTableViewTypeItem();

				tableViewTypeItem.setDisabled(true);
			}

		};
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		final String entriesNavigation = ParamUtil.getString(
			_request, "entriesNavigation", "all");

		return new DropdownItemList() {
			{
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setActive(
								entriesNavigation.equals("all"));

							PortletURL navigationPortletURL =
								_liferayPortletResponse.createRenderURL();

							dropdownItem.setHref(
								navigationPortletURL, "categoryId",
								MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
								"entriesNavigation", "all");

							dropdownItem.setLabel(
								LanguageUtil.get(_request, "all"));
						}));
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setActive(
								entriesNavigation.equals("recent"));

							PortletURL navigationPortletURL =
								_liferayPortletResponse.createRenderURL();

							dropdownItem.setHref(
								navigationPortletURL, "categoryId",
								MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
								"entriesNavigation", "recent");

							dropdownItem.setLabel(
								LanguageUtil.get(_request, "recent"));
						}));
			}
		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setActive(
								"title".equals(getOrderByCol()));
							dropdownItem.setHref(
								getSortingURL(), "orderByCol", "title");
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "title"));
						}));

				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setActive(
								"modified-date".equals(getOrderByCol()));
							dropdownItem.setHref(
								getSortingURL(), "orderByCol", "modified-date");
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "modified-date"));
						}));
			}
		};
	}

	private final PortletURL _currentURLObj;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;
	private final HttpServletRequest _request;
	private final TrashHelper _trashHelper;

}