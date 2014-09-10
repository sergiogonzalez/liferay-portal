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

package com.liferay.document.library.google.docs.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.context.util.FileEntryHelper;
import com.liferay.portlet.documentlibrary.context.util.FileVersionHelper;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Zaera
 */
public class GoogleDocsMetadataHelper {

	public GoogleDocsMetadataHelper(DLFileEntryType dlFileEntryType) {
		initDDMStructure(dlFileEntryType);
	}

	public GoogleDocsMetadataHelper(FileEntry fileEntry) {
		try {
			_fileVersion = fileEntry.getFileVersion();
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}

		FileEntryHelper fileEntryHelper = new FileEntryHelper(fileEntry);

		initDDMStructure(fileEntryHelper.getDLFileEntryType());
	}

	public GoogleDocsMetadataHelper(FileVersion fileVersion) {
		_fileVersion = fileVersion;

		FileVersionHelper fileVersionHelper = new FileVersionHelper(
			fileVersion);

		initDDMStructure(fileVersionHelper.getDLFileEntryType());
	}

	public String getFieldValue(String fieldName) {
		initFields();

		Field field = _fields.get(fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Unknown field " + fieldName);
		}

		Serializable value = field.getValue();

		if (value == null) {
			return null;
		}

		return value.toString();
	}

	public boolean isGoogleDocs() {
		if (_ddmStructure != null) {
			return true;
		}

		return false;
	}

	protected void initFields() {
		if (_fields == null) {
			_fields = new HashMap<>();

			if (_fileVersion == null) {
				return;
			}

			try {
				DLFileEntryMetadata dlFileEntryMetadata =
					DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
						_ddmStructure.getStructureId(),
						_fileVersion.getFileVersionId());

				Fields fields = StorageEngineUtil.getFields(
					dlFileEntryMetadata.getDDMStorageId());

				for (Field field : fields) {
					_fields.put(field.getName(), field);
				}
			}
			catch (PortalException pe) {
				throw new SystemException(
					"Unable to load DDM fields for file version " +
						_fileVersion.getFileVersionId(),
					pe);
			}
		}
	}

	private void initDDMStructure(DLFileEntryType dlFileEntryType) {
		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			String structureKey = ddmStructure.getStructureKey();

			if (structureKey.equals(
					GoogleDocsConstants.DDM_STRUCTURE_KEY_GOOGLE_DOCS)) {

				_ddmStructure = ddmStructure;
			}
		}
	}

	private DDMStructure _ddmStructure;
	private Map<String, Field> _fields;
	private FileVersion _fileVersion;

}