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

package com.liferay.dynamic.data.mapping.type.numeric.internal;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"ddm.form.field.type.data.domain=number",
		"ddm.form.field.type.display.order:Integer=8",
		"ddm.form.field.type.icon=caret-double",
		"ddm.form.field.type.js.class.name=Liferay.DDM.Field.Numeric",
		"ddm.form.field.type.js.module=liferay-ddm-form-field-numeric",
		"ddm.form.field.type.label=numeric-field-type-label",
		"ddm.form.field.type.name=numeric"
	},
	service = DDMFormFieldType.class
)
public class NumericDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings() {

		return NumericDDMFormFieldTypeSettings.class;
	}

	@Override
	public String getName() {
		return "numeric";
	}

}