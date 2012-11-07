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

package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class SitesDirectoryTag extends IncludeTag {

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setLevel(int level) {
		_level = level;
	}

	public void setLevelType(String levelType) {
		_levelType = levelType;
	}

	@Override
	protected void cleanUp() {
		_displayStyle = "descriptive";
		_level = 0;
		_levelType = "absolute";
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:sites-directory:displayStyle", _displayStyle);
		request.setAttribute(
			"liferay-ui:sites-directory:level", String.valueOf(_level));
		request.setAttribute(
			"liferay-ui:sites-directory:levelType", _levelType);
	}

	private static final String _PAGE =
		"/html/taglib/ui/sites_directory/page.jsp";

	private String _displayStyle = "descriptive";
	private int _level = 0;
	private String _levelType = "absolute";

}