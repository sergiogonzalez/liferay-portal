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

package com.liferay.portlet.documentlibrary.context.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeService;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngine;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;

import org.testng.Assert;

/**
 * @author Ivan Zaera
 */
public class FileVersionMetadataHelperTest extends PowerMockito {

	public FileVersionMetadataHelperTest() {
		_fileVersionMetadataHelper = new FileVersionMetadataHelper(
			_fileVersion, _dlFileEntryMetadataLocalService,
			_dlFileEntryTypeService, _storageEngine);
	}

	@Test
	public void testGetDDMStructuresForDLFileVersion() throws Exception {
		DLFileVersion dlFileVersion = mock(DLFileVersion.class);

		mockDDMStructures(dlFileVersion);

		List<DDMStructure> ddmStructures =
			_fileVersionMetadataHelper.getDDMStructures();

		Assert.assertEquals(2, ddmStructures.size());
	}

	@Test
	public void testGetDDMStructuresForObject() throws Exception {
		Object object = new Object();

		mockDDMStructures(object);

		List<DDMStructure> ddmStructures =
			_fileVersionMetadataHelper.getDDMStructures();

		Assert.assertEquals(0, ddmStructures.size());
	}

	@Test
	public void testGetFieldsWithDDMStructure() throws Exception {
		DLFileVersion dlFileVersion = mock(DLFileVersion.class);

		mockDDMStructures(dlFileVersion);

		List<DDMStructure> ddmStructures =
			_fileVersionMetadataHelper.getDDMStructures();

		DDMStructure ddmStructure = ddmStructures.get(0);

		mockFields();

		Fields fields = _fileVersionMetadataHelper.getFields(ddmStructure);

		Assert.assertNotNull(fields);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetFieldsWithoutDDMStructure() throws Exception {
		DLFileVersion dlFileVersion = mock(DLFileVersion.class);

		mockDDMStructures(dlFileVersion);

		DDMStructure ddmStructure = mock(DDMStructure.class);

		_fileVersionMetadataHelper.getFields(ddmStructure);
	}

	protected void mockDDMStructures(Object model) throws PortalException {
		when(
			_fileVersion.getModel()
		).thenReturn(
			model
		);

		DLFileEntryType dlFileEntryType = mock(DLFileEntryType.class);

		when(
			_dlFileEntryTypeService.getFileEntryType(Matchers.anyLong())
		).thenReturn(
			dlFileEntryType
		);

		List<DDMStructure> ddmStructures = new ArrayList<>();

		ddmStructures.add(mock(DDMStructure.class));
		ddmStructures.add(mock(DDMStructure.class));

		when(
			dlFileEntryType.getDDMStructures()
		).thenReturn(
			ddmStructures
		);
	}

	protected void mockFields() throws PortalException {
		DLFileEntryMetadata dlFileEntryMetadata = mock(
			DLFileEntryMetadata.class);

		when(
			_dlFileEntryMetadataLocalService.getFileEntryMetadata(
				Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(
			dlFileEntryMetadata
		);

		Fields fields = mock(Fields.class);

		when(
			_storageEngine.getFields(dlFileEntryMetadata.getDDMStorageId())
		).thenReturn(
			fields
		);
	}

	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService =
		mock(DLFileEntryMetadataLocalService.class);
	private DLFileEntryTypeService _dlFileEntryTypeService = mock(
		DLFileEntryTypeService.class);
	private FileVersion _fileVersion = mock(FileVersion.class);
	private FileVersionMetadataHelper _fileVersionMetadataHelper;
	private StorageEngine _storageEngine = mock(StorageEngine.class);

}