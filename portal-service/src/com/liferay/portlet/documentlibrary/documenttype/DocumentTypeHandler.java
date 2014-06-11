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

package com.liferay.portlet.documentlibrary.documenttype;

import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.List;
import java.util.UUID;

/**
 * @author Iván Zaera
 */
public interface DocumentTypeHandler {

	public UUID getId();

	public String getSelectFileButtonHTML(
		DLFileEntryType dlFileEntryType, String callbackFunction);

	public List<DDMStructure> getVisibleDDMStructures(
		DLFileEntryType dlFileEntryType);

	public boolean handles(DLFileEntryType dlFileEntryType);

	public boolean isDownloadable(DLFileEntryType dlFileEntryType);

	public void processUpload(
		boolean newFileEntry, DLFileEntry dlFileEntry, String jsonPayload);

}