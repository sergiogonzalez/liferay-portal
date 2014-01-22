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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class ErrorHeaderTag extends IncludeTag {

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		String backURL = request.getHeader(HttpHeaders.REFERER);

		if (Validator.isNull(backURL)) {
			backURL = "javascript:history.go(-1)";
		}

		request.setAttribute(
			"liferay-ui:discussion:backURL", String.valueOf(backURL));
	}

	private static final String _PAGE = "/html/taglib/ui/error_header/page.jsp";

}