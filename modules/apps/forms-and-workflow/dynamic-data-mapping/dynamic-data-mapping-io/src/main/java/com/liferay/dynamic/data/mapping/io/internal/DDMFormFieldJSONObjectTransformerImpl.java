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

package com.liferay.dynamic.data.mapping.io.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldJSONObjectTransformer;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true)
public class DDMFormFieldJSONObjectTransformerImpl
	implements DDMFormFieldJSONObjectTransformer {

	@Override
	public JSONObject transform(DDMFormField ddmFormField) {
		return ddmFormFieldToJSONObjectTransformer.transform(ddmFormField);
	}

	@Override
	public DDMFormField transform(JSONObject jsonObject)
		throws PortalException {

		return jsonObjectToDDMFormFieldTransformer.transform(jsonObject);
	}

	@Activate
	protected void activate() {
		ddmFormFieldToJSONObjectTransformer =
			new DDMFormFieldToJSONObjectTransformer(
				ddmFormFieldTypeServicesTracker, jsonFactory);

		jsonObjectToDDMFormFieldTransformer =
			new JSONObjectToDDMFormFieldTransformer(
				ddmFormFieldTypeServicesTracker, jsonFactory);
	}

	protected DDMFormFieldToJSONObjectTransformer
		ddmFormFieldToJSONObjectTransformer;

	@Reference
	protected DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker;

	@Reference
	protected JSONFactory jsonFactory;

	protected JSONObjectToDDMFormFieldTransformer
		jsonObjectToDDMFormFieldTransformer;

}