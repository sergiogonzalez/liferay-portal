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
public class DefaultDocumentTypeHandler extends BaseDocumentTypeHandler {

	public DefaultDocumentTypeHandler() {
		super(ID);
	}

	@Override
	public String getSelectFileButtonHTML(
		DLFileEntryType dlFileEntryType, String callbackFunction) {

		// TODO Auto-generated method stub

		throw new UnsupportedOperationException("NOT IMPLEMENTED YET!!!");
	}

	@Override
	public List<DDMStructure> getVisibleDDMStructures(
		DLFileEntryType dlFileEntryType) {

		return dlFileEntryType.getDDMStructures();
	}

	@Override
	public boolean handles(DLFileEntryType dlFileEntryType) {
		return true;
	}

	@Override
	public boolean isDownloadable(DLFileEntryType dlFileEntryType) {
		return true;
	}

	@Override
	public void processUpload(
		boolean newFileEntry, DLFileEntry dlFileEntry, String jsonPayload) {

		// TODO Auto-generated method stub

		throw new UnsupportedOperationException("NOT IMPLEMENTED YET!!!");
	}

	private static final UUID ID = UUID.fromString(
		"3027BFF6-03F8-48CC-8909-C8FBA8735477");

}