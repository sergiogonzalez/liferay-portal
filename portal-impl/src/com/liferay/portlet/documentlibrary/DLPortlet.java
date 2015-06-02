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

package com.liferay.portlet.documentlibrary;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.documentlibrary.action.SearchAction;
import com.liferay.portlet.documentlibrary.action.ViewAction;
import com.liferay.portlet.mvc.ActionableMVCPortlet;

/**
 * @author Iván Zaera
 */
public class DLPortlet extends ActionableMVCPortlet {

	public DLPortlet() {
		SearchAction searchAction = new SearchAction();

		registerMVCPortletAction(searchAction, "/document_library/search");

		ViewAction viewAction = new ViewAction(
			"/html/portlet/document_library/error.jsp",
			"/html/portlet/document_library/view.jsp");

		registerMVCPortletAction(
			viewAction, StringPool.BLANK, "/document_library/select_file_entry",
			"/document_library/view");
	}

}