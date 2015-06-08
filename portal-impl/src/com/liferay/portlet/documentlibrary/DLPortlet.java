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
import com.liferay.portlet.documentlibrary.action.CompareVersionsAction;
import com.liferay.portlet.documentlibrary.action.EditEntryAction;
import com.liferay.portlet.documentlibrary.action.EditFileEntryAction;
import com.liferay.portlet.documentlibrary.action.EditFileEntryTypeAction;
import com.liferay.portlet.documentlibrary.action.EditFileShortcutAction;
import com.liferay.portlet.documentlibrary.action.EditFolderAction;
import com.liferay.portlet.documentlibrary.action.EditRepositoryAction;
import com.liferay.portlet.documentlibrary.action.GetFileAction;
import com.liferay.portlet.documentlibrary.action.SearchAction;
import com.liferay.portlet.documentlibrary.action.ViewAction;
import com.liferay.portlet.mvc.ActionableMVCPortlet;
import com.liferay.portlet.mvc.util.ForwardMVCPortletAction;

import javax.portlet.PortletException;

/**
 * @author Iván Zaera
 */
public class DLPortlet extends ActionableMVCPortlet {

	@Override
	public void init() throws PortletException {
		super.init();

		registerMVCPortletAction(
			new CompareVersionsAction(), "/document_library/compare_versions");

		registerMVCPortletAction(
			new EditEntryAction(this), "/document_library/edit_entry",
			"/document_library/move_entry");

		registerMVCPortletAction(
			new EditFileEntryAction(
				this, "/html/portlet/document_library/edit_file_entry.jsp"),
			"/document_library/edit_file_entry");

		registerMVCPortletAction(
			new EditFileEntryTypeAction(this),
			"/document_library/edit_file_entry_type");

		registerMVCPortletAction(
			new ForwardMVCPortletAction(
				"/html/portlet/document_library/view_file_entry_type.jsp"),
			"/document_library/view_file_entry_type");

		registerMVCPortletAction(
			new EditFileEntryAction(
				this,
				"/html/portlet/document_library/" +
					"upload_multiple_file_entries.jsp"),
			"/document_library/upload_multiple_file_entries");

		registerMVCPortletAction(
			new EditFileEntryAction(
				this, "/html/portlet/document_library/view_file_entry.jsp"),
			"/document_library/view_file_entry");

		registerMVCPortletAction(
			new EditFileEntryAction(
				this, "/html/portlet/document_library/view_file_entry.jsp"),
			"/document_library/upload_file_entry");

		registerMVCPortletAction(
			new EditFileShortcutAction(this),
			"/document_library/edit_file_shortcut");

		registerMVCPortletAction(
			new EditFolderAction(
				this, "/html/portlet/document_library/edit_folder.jsp"),
			"/document_library/edit_folder");

		registerMVCPortletAction(
			new EditFolderAction(
				this, "/html/portlet/document_library/select_folder.jsp"),
			"/document_library/select_folder");

		registerMVCPortletAction(
			new EditRepositoryAction(this),
			"/document_library/edit_repository");

		registerMVCPortletAction(
			new GetFileAction(), "/document_library/get_file");

		registerMVCPortletAction(
			new SearchAction(), "/document_library/search");

		registerMVCPortletAction(
			new ViewAction(
				"/html/portlet/document_library/error.jsp",
				"/html/portlet/document_library/view.jsp"),
			StringPool.BLANK, "/document_library/select_file_entry",
			"/document_library/view");
	}

}