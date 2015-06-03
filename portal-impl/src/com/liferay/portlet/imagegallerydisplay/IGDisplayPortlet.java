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

package com.liferay.portlet.imagegallerydisplay;

import com.liferay.portlet.documentlibrary.action.EditFileEntryAction;
import com.liferay.portlet.mvc.ActionableMVCPortlet;

import javax.portlet.PortletException;

/**
 * @author Iván Zaera
 */
public class IGDisplayPortlet extends ActionableMVCPortlet {

	@Override
	public void init() throws PortletException {
		super.init();

		registerMVCPortletAction(
			new EditFileEntryAction(
				this, "/html/portlet/document_library/edit_file_entry.jsp"),
			"/image_gallery_display/edit_image");

		registerMVCPortletAction(
			new EditFileEntryAction(
				this, "/html/portlet/document_library/view_file_entry.jsp"),
			"/image_gallery_display/view_image");
	}

}