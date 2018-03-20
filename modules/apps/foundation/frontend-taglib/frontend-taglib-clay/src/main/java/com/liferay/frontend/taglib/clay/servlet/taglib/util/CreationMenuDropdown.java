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

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Lancha
 */
public class CreationMenuDropdown implements Serializable {

	public CreationMenuDropdown(HttpServletRequest request) {
		_request = request;
	}

	public void addDropdownItem(
			ThrowableConsumer<DropdownItem> throwableConsumer)
		throws Exception {

		addDropdownItem(throwableConsumer, "primary");
	}

	public void addDropdownItem(
			ThrowableConsumer<DropdownItem> throwableConsumer, String type)
		throws Exception {

		DropdownItem dropdownItem = new DropdownItem();

		throwableConsumer.accept(dropdownItem);

		if (type.equals("primary")) {
			_primaryDropdownItemList.add(dropdownItem);
		}
		else if (type.equals("favorite")) {
			_favoriteDropdownItemList.add(dropdownItem);
		}
		else {
			_restDropdownItemList.add(dropdownItem);
		}
	}

	public String getHelpText() {
		return _helpText;
	}

	@JSON(name = "primaryItems")
	public DropdownItemList getPrimaryDropdownItemList() {
		return _primaryDropdownItemList;
	}

	@JSON(name = "secondaryItems")
	public DropdownItemList getSecondaryDropdownItemList() throws Exception {
		DropdownItemList secondaryDropdownItemList = new DropdownItemList();

		if (!_favoriteDropdownItemList.isEmpty()) {
			secondaryDropdownItemList.addGroup(
				dropdownGroupItem -> {
					dropdownGroupItem.setDropdownItems(
						_favoriteDropdownItemList);
					dropdownGroupItem.setLabel(
						LanguageUtil.get(_request, "favorites"));

					if (!_restDropdownItemList.isEmpty()) {
						dropdownGroupItem.setSeparator(true);
					}
				});
		}

		if (!_restDropdownItemList.isEmpty()) {
			secondaryDropdownItemList.addGroup(
				dropdownGroupItem -> {
					dropdownGroupItem.setDropdownItems(_restDropdownItemList);
				});
		}

		return secondaryDropdownItemList;
	}

	public String getViewMoreURL() {
		return _viewMoreURL;
	}

	public void setCaption(String caption) {
		_caption = caption;
	}

	public void setHelpText(String helpText) {
		_helpText = helpText;
	}

	public void setViewMoreURL(String viewMoreURL) {
		_viewMoreURL = viewMoreURL;
	}

	private String _caption;
	private DropdownItemList _favoriteDropdownItemList = new DropdownItemList();
	private String _helpText;
	private DropdownItemList _primaryDropdownItemList = new DropdownItemList();
	private final HttpServletRequest _request;
	private DropdownItemList _restDropdownItemList = new DropdownItemList();
	private String _viewMoreURL;

}