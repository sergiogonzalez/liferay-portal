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

import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true)
public class DDMFormLayoutJSONDeserializerImpl
	implements DDMFormLayoutJSONDeserializer {

	@Override
	public DDMFormLayout deserialize(String serializedDDMFormLayout)
		throws PortalException {

		return deserialize(serializedDDMFormLayout, null);
	}

	@Override
	public DDMFormLayout deserialize(
			String serializedDDMFormLayout,
			Function<JSONObject, DDMFormLayoutColumn>
				ddmFormLayoutColumnJSONObjectTransformer)
		throws PortalException {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			serializedDDMFormLayout);

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		setDDMFormLayoutDefaultLocale(
			jsonObject.getString("defaultLanguageId"), ddmFormLayout);
		setDDMFormLayoutPages(
			jsonObject.getJSONArray("pages"),
			ddmFormLayoutColumnJSONObjectTransformer, ddmFormLayout);
		setDDMFormLayoutPageTitlesDefaultLocale(ddmFormLayout);
		_setDDMFormLayoutPaginationMode(
			jsonObject.getString("paginationMode"), ddmFormLayout);

		return ddmFormLayout;
	}

	protected void setDDMFormLayoutDefaultLocale(
		String defaultLanguageId, DDMFormLayout ddmFormLayout) {

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		ddmFormLayout.setDefaultLocale(defaultLocale);
	}

	protected void setDDMFormLayoutPages(
		JSONArray jsonArray,
		Function<JSONObject, DDMFormLayoutColumn>
			ddmFormLayoutColumnJSONObjectTransformer,
		DDMFormLayout ddmFormLayout) {

		Optional<Function<JSONObject, DDMFormLayoutColumn>>
			ddmFormLayoutColumnJSONObjectTransformerOptional =
				Optional.ofNullable(ddmFormLayoutColumnJSONObjectTransformer);

		DDMFormLayoutPagesJSONDeserializerHelper
			ddmFormLayoutPagesJSONDeserializerHelper =
				new DDMFormLayoutPagesJSONDeserializerHelper(
					ddmFormLayoutColumnJSONObjectTransformerOptional);

		List<DDMFormLayoutPage> ddmFormLayoutPages =
			ddmFormLayoutPagesJSONDeserializerHelper.getDDMFormLayoutPages(
				jsonArray);

		ddmFormLayout.setDDMFormLayoutPages(ddmFormLayoutPages);
	}

	protected void setDDMFormLayoutPageTitlesDefaultLocale(
		DDMFormLayout ddmFormLayout) {

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout.getDDMFormLayoutPages()) {

			LocalizedValue title = ddmFormLayoutPage.getTitle();

			title.setDefaultLocale(ddmFormLayout.getDefaultLocale());
		}
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	private void _setDDMFormLayoutPaginationMode(
		String paginationMode, DDMFormLayout ddmFormLayout) {

		ddmFormLayout.setPaginationMode(paginationMode);
	}

	private JSONFactory _jsonFactory;

}