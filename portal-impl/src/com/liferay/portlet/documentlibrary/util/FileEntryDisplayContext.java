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

package com.liferay.portlet.documentlibrary.util;

import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

/**
 * @author Iván Zaera
 */
public class FileEntryDisplayContext {

	public FileEntryDisplayContext(
		HttpServletRequest request, FileEntry fileEntry, 
		FileVersion fileVersion) {

	}

	public boolean isCancelCheckoutDocumentButtonVisible() {
		return false;
	}

	public boolean isCheckinButtonVisible() {
		return false;
	}

	public boolean isCheckoutDocumentButtonVisible() {
		return false;
	}

	public boolean isDeleteButtonVisible() {
		return false;
	}

	public boolean isDownloadButtonVisible() {
		return false;
	}

	public boolean isEditButtonVisible() {
		return false;
	}

	public boolean isMoveButtonVisible() {
		return false;
	}

	public boolean isMoveToTheRecycleBinButtonVisible() {
		return false;
	}

	public boolean isOpenInMsOfficeButtonVisible() {
		return false;
	}

	public boolean isPermissionsButtonVisible() {
		return false;
	}

}