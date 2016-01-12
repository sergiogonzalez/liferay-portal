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

package com.liferay.document.library.web.portlet.configuration.icon;

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.document.library.web.display.context.logic.FileEntryDisplayContextHelper;
import com.liferay.document.library.web.portlet.action.ActionUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Roberto Díaz
 */
public class EditFileEntryPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public EditFileEntryPortletConfigurationIcon(
		PortletRequest portletRequest) {

		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "edit";
	}

	@Override
	public String getURL() {
		String url = StringPool.BLANK;

		try {
			FileEntry fileEntry = ActionUtil.getFileEntry(portletRequest);

			PortletURL editURL = PortalUtil.getControlPanelPortletURL(
				portletRequest, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
				PortletRequest.RENDER_PHASE);

			editURL.setParameter(
				"mvcRenderCommandName", "/document_library/edit_file_entry");

			editURL.setParameter("redirect", themeDisplay.getURLCurrent());
			editURL.setParameter(
				"fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

			url = editURL.toString();
		}
		catch (Exception e) {
		}

		return url;
	}

	@Override
	public boolean isShow() {
		try {
			FileEntry fileEntry = ActionUtil.getFileEntry(portletRequest);

			FileEntryDisplayContextHelper fileEntryDisplayContextHelper =
				new FileEntryDisplayContextHelper(
					themeDisplay.getPermissionChecker(), fileEntry);

			return fileEntryDisplayContextHelper.isEditActionAvailable();
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

}