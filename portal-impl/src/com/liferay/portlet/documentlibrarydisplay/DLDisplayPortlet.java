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

package com.liferay.portlet.documentlibrarydisplay;

import com.liferay.portal.kernel.portlet.bridges.mvc.action.ForwardMVCPortletAction;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.documentlibrary.DLPortlet;
import com.liferay.portlet.documentlibrary.action.ViewAction;

import javax.portlet.PortletException;

/**
 * @author Iván Zaera
 */
public class DLDisplayPortlet extends DLPortlet {

	@Override
	public void init() throws PortletException {
		super.init();

		registerMVCPortletAction(
			new ForwardMVCPortletAction(
				"/html/portlet/document_library_display/search.jsp"),
			"/document_library_display/search");

		registerMVCPortletAction(
			new ForwardMVCPortletAction(
				"/html/portlet/document_library_display/" +
					"select_add_file_entry_type.jsp"),
			"/document_library_display/select_add_file_entry_type");

		registerMVCPortletAction(
			new ViewAction(
				"/html/portlet/document_library/error.jsp",
				"/html/portlet/document_library_display/view.jsp"),
			"/document_library_display/view", StringPool.BLANK);
	}

}