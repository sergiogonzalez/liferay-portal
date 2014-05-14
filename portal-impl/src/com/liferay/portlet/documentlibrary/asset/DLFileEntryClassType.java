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

package com.liferay.portlet.documentlibrary.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.asset.NoSuchClassTypeException;
import com.liferay.portlet.asset.model.ClassTypeField;
import com.liferay.portlet.asset.model.DDMStructureClassType;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class DLFileEntryClassType extends DDMStructureClassType {

	public DLFileEntryClassType(
			long classTypeId, String name, String languageId) {

		super(classTypeId, name, languageId);
	}

	@Override
	public List<ClassTypeField> getClassTypeFields()
		throws PortalException, SystemException {

		try {
			List<ClassTypeField> classTypeFields =
				new ArrayList<ClassTypeField>();

			DLFileEntryType dlFileEntryType =
				DLFileEntryTypeLocalServiceUtil.getDLFileEntryType(
					getClassTypeId());

			List<DDMStructure> ddmStructures =
				dlFileEntryType.getDDMStructures();

			for (DDMStructure ddmStructure : ddmStructures) {
				classTypeFields.addAll(super.getClassTypeFields(ddmStructure));
			}

			return classTypeFields;
		}
		catch (NoSuchFileEntryTypeException e) {
			throw new NoSuchClassTypeException(e);
		}
		catch (PortalException e) {
			throw e;
		}
		catch (SystemException e) {
			throw e;
		}
	}

}