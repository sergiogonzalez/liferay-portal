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
import com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true)
public class DDMFormJSONDeserializerImpl implements DDMFormJSONDeserializer {

	@Override
	public DDMForm deserialize(String serializedDDMForm)
		throws PortalException {

		try {
			JSONObject jsonObject = jsonFactory.createJSONObject(
				serializedDDMForm);

			DDMForm ddmForm = new DDMForm();

			setDDMFormAvailableLocales(
				jsonObject.getJSONArray("availableLanguageIds"), ddmForm);
			setDDMFormDefaultLocale(
				jsonObject.getString("defaultLanguageId"), ddmForm);
			setDDMFormFields(jsonObject.getJSONArray("fields"), ddmForm);
			setDDMFormRules(jsonObject.getJSONArray("rules"), ddmForm);
			setDDMFormLocalizedValuesDefaultLocale(ddmForm);
			setDDMFormSuccessPageSettings(
				jsonObject.getJSONObject("successPage"), ddmForm);

			return ddmForm;
		}
		catch (JSONException jsone) {
			throw new PortalException(jsone);
		}
	}

	protected Set<Locale> getAvailableLocales(JSONArray jsonArray) {
		Set<Locale> availableLocales = new HashSet<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			Locale availableLocale = LocaleUtil.fromLanguageId(
				jsonArray.getString(i));

			availableLocales.add(availableLocale);
		}

		return availableLocales;
	}

	protected DDMFormField getDDMFormField(JSONObject jsonObject)
		throws PortalException {

		return ddmFormFieldJSONObjectTransformer.transform(jsonObject);
	}

	protected List<DDMFormField> getDDMFormFields(JSONArray jsonArray)
		throws PortalException {

		List<DDMFormField> ddmFormFields = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormField ddmFormField = getDDMFormField(
				jsonArray.getJSONObject(i));

			ddmFormFields.add(ddmFormField);
		}

		return ddmFormFields;
	}

	protected DDMFormRule getDDMFormRule(JSONObject jsonObject) {
		String condition = jsonObject.getString("condition");

		List<String> actions = getDDMFormRuleActions(
			jsonObject.getJSONArray("actions"));

		DDMFormRule ddmFormRule = new DDMFormRule(condition, actions);

		boolean enabled = jsonObject.getBoolean("enabled", true);

		ddmFormRule.setEnabled(enabled);

		return ddmFormRule;
	}

	protected List<String> getDDMFormRuleActions(JSONArray jsonArray) {
		List<String> actions = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			actions.add(jsonArray.getString(i));
		}

		return actions;
	}

	protected List<DDMFormRule> getDDMFormRules(JSONArray jsonArray) {
		List<DDMFormRule> ddmFormRules = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormRule ddmFormRule = getDDMFormRule(
				jsonArray.getJSONObject(i));

			ddmFormRules.add(ddmFormRule);
		}

		return ddmFormRules;
	}

	protected void setDDMFormAvailableLocales(
		JSONArray jsonArray, DDMForm ddmForm) {

		Set<Locale> availableLocales = getAvailableLocales(jsonArray);

		ddmForm.setAvailableLocales(availableLocales);
	}

	protected void setDDMFormDefaultLocale(
		String defaultLanguageId, DDMForm ddmForm) {

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		ddmForm.setDefaultLocale(defaultLocale);
	}

	protected void setDDMFormFieldLocalizedValueDefaultLocale(
		LocalizedValue localizedValue, Locale defaultLocale) {

		if (localizedValue == null) {
			return;
		}

		localizedValue.setDefaultLocale(defaultLocale);
	}

	protected void setDDMFormFieldLocalizedValuesDefaultLocale(
		DDMFormField ddmFormField, Locale defaultLocale) {

		setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getLabel(), defaultLocale);

		setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getPredefinedValue(), defaultLocale);

		setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getStyle(), defaultLocale);

		setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getTip(), defaultLocale);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		if (ddmFormFieldOptions != null) {
			ddmFormFieldOptions.setDefaultLocale(defaultLocale);
		}

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			setDDMFormFieldLocalizedValuesDefaultLocale(
				nestedDDMFormField, defaultLocale);
		}
	}

	protected void setDDMFormFields(JSONArray jsonArray, DDMForm ddmForm)
		throws PortalException {

		List<DDMFormField> ddmFormFields = getDDMFormFields(jsonArray);

		ddmForm.setDDMFormFields(ddmFormFields);
	}

	protected void setDDMFormLocalizedValuesDefaultLocale(DDMForm ddmForm) {
		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			setDDMFormFieldLocalizedValuesDefaultLocale(
				ddmFormField, ddmForm.getDefaultLocale());
		}
	}

	protected void setDDMFormRules(JSONArray jsonArray, DDMForm ddmForm) {
		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return;
		}

		List<DDMFormRule> ddmFormRules = getDDMFormRules(jsonArray);

		ddmForm.setDDMFormRules(ddmFormRules);
	}

	protected void setDDMFormSuccessPageSettings(
		JSONObject jsonObject, DDMForm ddmForm) {

		if (jsonObject == null) {
			return;
		}

		DDMFormSuccessPageSettings ddmFormSuccessPageSettings =
			new DDMFormSuccessPageSettings(
				jsonObject.getString("body"), jsonObject.getString("title"),
				jsonObject.getBoolean("enabled"));

		ddmForm.setDDMFormSuccessPageSettings(ddmFormSuccessPageSettings);
	}

	@Reference
	protected DDMFormFieldJSONObjectTransformer
		ddmFormFieldJSONObjectTransformer;

	@Reference
	protected JSONFactory jsonFactory;

}