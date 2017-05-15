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

import com.liferay.dynamic.data.mapping.io.DDMFormFieldJSONObjectTransformer;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true)
public class DDMFormJSONSerializerImpl implements DDMFormJSONSerializer {

	@Override
	public String serialize(DDMForm ddmForm) {
		JSONObject jsonObject = jsonFactory.createJSONObject();

		addAvailableLanguageIds(jsonObject, ddmForm.getAvailableLocales());
		addDefaultLanguageId(jsonObject, ddmForm.getDefaultLocale());
		addFields(jsonObject, ddmForm.getDDMFormFields());
		addRules(jsonObject, ddmForm.getDDMFormRules());
		addSuccessPageSettings(
			jsonObject, ddmForm.getDDMFormSuccessPageSettings());

		return jsonObject.toString();
	}

	protected void addAvailableLanguageIds(
		JSONObject jsonObject, Set<Locale> availableLocales) {

		JSONArray jsonArray = jsonFactory.createJSONArray();

		for (Locale availableLocale : availableLocales) {
			jsonArray.put(LocaleUtil.toLanguageId(availableLocale));
		}

		jsonObject.put("availableLanguageIds", jsonArray);
	}

	protected void addDefaultLanguageId(
		JSONObject jsonObject, Locale defaultLocale) {

		jsonObject.put(
			"defaultLanguageId", LocaleUtil.toLanguageId(defaultLocale));
	}

	protected void addFields(
		JSONObject jsonObject, List<DDMFormField> ddmFormFields) {

		jsonObject.put("fields", fieldsToJSONArray(ddmFormFields));
	}

	protected void addRules(
		JSONObject jsonObject, List<DDMFormRule> ddmFormRules) {

		if (ddmFormRules.isEmpty()) {
			return;
		}

		jsonObject.put("rules", rulesToJSONArray(ddmFormRules));
	}

	protected void addSuccessPageSettings(
		JSONObject jsonObject,
		DDMFormSuccessPageSettings ddmFormSuccessPageSettings) {

		jsonObject.put("successPage", toJSONObject(ddmFormSuccessPageSettings));
	}

	protected JSONArray fieldsToJSONArray(List<DDMFormField> ddmFormFields) {
		JSONArray jsonArray = jsonFactory.createJSONArray();

		for (DDMFormField ddmFormField : ddmFormFields) {
			jsonArray.put(toJSONObject(ddmFormField));
		}

		return jsonArray;
	}

	protected JSONArray ruleActionsToJSONArray(List<String> ruleActions) {
		JSONArray jsonArray = jsonFactory.createJSONArray();

		for (String ruleAction : ruleActions) {
			jsonArray.put(ruleAction);
		}

		return jsonArray;
	}

	protected JSONArray rulesToJSONArray(List<DDMFormRule> ddmFormRules) {
		JSONArray jsonArray = jsonFactory.createJSONArray();

		for (DDMFormRule ddmFormRule : ddmFormRules) {
			jsonArray.put(toJSONObject(ddmFormRule));
		}

		return jsonArray;
	}

	protected JSONObject toJSONObject(DDMFormField ddmFormField) {
		return ddmFormFieldJSONObjectTransformer.transform(ddmFormField);
	}

	protected JSONObject toJSONObject(DDMFormRule ddmFormRule) {
		JSONObject jsonObject = jsonFactory.createJSONObject();

		jsonObject.put(
			"actions", ruleActionsToJSONArray(ddmFormRule.getActions()));
		jsonObject.put("condition", ddmFormRule.getCondition());
		jsonObject.put("enabled", ddmFormRule.isEnabled());

		return jsonObject;
	}

	protected JSONObject toJSONObject(
		DDMFormSuccessPageSettings ddmFormSuccessPageSettings) {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		jsonObject.put("body", ddmFormSuccessPageSettings.getBody());
		jsonObject.put("enabled", ddmFormSuccessPageSettings.isEnabled());
		jsonObject.put("title", ddmFormSuccessPageSettings.getTitle());

		return jsonObject;
	}

	@Reference
	protected DDMFormFieldJSONObjectTransformer
		ddmFormFieldJSONObjectTransformer;

	@Reference
	protected JSONFactory jsonFactory;

}