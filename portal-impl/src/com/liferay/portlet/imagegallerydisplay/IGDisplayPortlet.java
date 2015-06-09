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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.documentlibrary.DLPortlet;
import com.liferay.portlet.documentlibrary.action.EditFileEntryAction;
import com.liferay.portlet.documentlibrary.action.EditFileShortcutAction;
import com.liferay.portlet.documentlibrary.action.EditFolderAction;
import com.liferay.portlet.documentlibrary.action.ViewAction;
import com.liferay.portlet.mvc.util.ForwardMVCPortletAction;

import javax.portlet.PortletException;

/**
 * @author Iván Zaera
 */
public class IGDisplayPortlet extends DLPortlet {

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

		registerMVCPortletAction(
			new EditFileShortcutAction(this),
			"/image_gallery_display/edit_shortcut");

		registerMVCPortletAction(
			new EditFolderAction(
				this, "/html/portlet/document_library/edit_folder.jsp"),
			"/image_gallery_display/edit_folder");

		registerMVCPortletAction(
			new EditFolderAction(
				this, "/html/portlet/document_library/select_folder.jsp"),
			"/image_gallery_display/select_folder");

		registerMVCPortletAction(
			new ForwardMVCPortletAction(
				"/html/portlet/image_gallery_display/embedded_player.jsp"),
			"/image_gallery_display/embedded_player");

		registerMVCPortletAction(
			new ForwardMVCPortletAction(
				"/html/portlet/image_gallery_display/search.jsp"),
			"/image_gallery_display/search");

		registerMVCPortletAction(
			new ForwardMVCPortletAction(
				"/html/portlet/document_library_display/" +
					"select_add_file_entry_type.jsp"),
			"/image_gallery_display/select_add_file_entry_type");

		registerMVCPortletAction(
			new ViewAction(
				"/html/portlet/image_gallery_display/error.jsp",
				"/html/portlet/image_gallery_display/view.jsp"),
			"/image_gallery_display/view", StringPool.BLANK);
	}

}