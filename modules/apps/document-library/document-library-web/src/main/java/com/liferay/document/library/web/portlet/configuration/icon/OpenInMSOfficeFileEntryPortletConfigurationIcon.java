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

import com.liferay.document.library.web.display.context.logic.FileEntryDisplayContextHelper;
import com.liferay.document.library.web.portlet.action.ActionUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class OpenInMSOfficeFileEntryPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public OpenInMSOfficeFileEntryPortletConfigurationIcon(
		PortletRequest portletRequest) {

		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "open-in-ms-office";
	}

	@Override
	public String getOnClick() {
		StringBundler sb = new StringBundler(4);

		try {
			FileEntry fileEntry = ActionUtil.getFileEntry(portletRequest);

			String webDavURL = DLUtil.getWebDavURL(
				themeDisplay, fileEntry.getFolder(), fileEntry,
				PropsValues.
					DL_FILE_ENTRY_OPEN_IN_MS_OFFICE_MANUAL_CHECK_IN_REQUIRED);

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			sb.append(portletDisplay.getNamespace());
			sb.append("openDocument('");
			sb.append(webDavURL);
			sb.append("');");
		}
		catch (Exception e) {
		}

		return sb.toString();
	}

	@Override
	public String getURL() {
		return "javascript:;";
	}

	@Override
	public boolean isShow() {
		try {
			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				portletRequest);

			FileEntry fileEntry = ActionUtil.getFileEntry(request);

			FileEntryDisplayContextHelper fileEntryDisplayContextHelper =
				new FileEntryDisplayContextHelper(
					themeDisplay.getPermissionChecker(), fileEntry);

			FileVersion fileVersion = ActionUtil.getFileVersion(
				fileEntry, request);

			return
				fileEntryDisplayContextHelper.isOpenInMsOfficeActionAvailable(
					fileVersion, request);
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

}