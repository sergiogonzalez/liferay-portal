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

package com.liferay.dynamic.data.mapping.type.text.localizable;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=text_localizable",
	service = {
		TextLocalizableDDMFormFieldTemplateContextContributor.class,
		DDMFormFieldTemplateContextContributor.class
	}
)
public class TextLocalizableDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> parameters = new HashMap<>();

		HttpServletRequest request =
			ddmFormFieldRenderingContext.getHttpServletRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		parameters.put(
			"availableLocalesMetadata",
			getAvailableLocalesMetadata(themeDisplay));

		parameters.put(
			"displayStyle",
			GetterUtil.getString(
				ddmFormField.getProperty("displayStyle"), "singleline"));

		LocalizedValue placeholder = (LocalizedValue)ddmFormField.getProperty(
			"placeholder");

		Locale locale = ddmFormFieldRenderingContext.getLocale();

		parameters.put("placeholder", getValueString(placeholder, locale));

		LocalizedValue tooltip = (LocalizedValue)ddmFormField.getProperty(
			"tooltip");

		parameters.put("tooltip", getValueString(tooltip, locale));

		return parameters;
	}

	protected JSONArray getAvailableLocalesMetadata(ThemeDisplay themeDisplay) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		Locale siteDefaultLocale = themeDisplay.getSiteDefaultLocale();

		jsonArray.put(getLocaleMetadata(siteDefaultLocale, themeDisplay));

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		for (Locale locale : availableLocales) {
			if (Objects.equals(locale, siteDefaultLocale)) {
				continue;
			}

			jsonArray.put(getLocaleMetadata(locale, themeDisplay));
		}

		return jsonArray;
	}

	protected String getLocaleIcon(Locale locale, ThemeDisplay themeDisplay) {
		String languageId = LocaleUtil.toLanguageId(locale);

		StringBuilder sb = new StringBuilder();

		sb.append(themeDisplay.getPathThemeImages());
		sb.append("/lexicon/icons.svg");
		sb.append(StringPool.POUND);
		sb.append(
			StringUtil.toLowerCase(StringUtil.replace(languageId, '_', '-')));

		return sb.toString();
	}

	protected JSONObject getLocaleMetadata(
		Locale locale, ThemeDisplay themeDisplay) {

		Locale siteDefaultLocale = themeDisplay.getSiteDefaultLocale();

		JSONObject localeMetadataJSONObject = _jsonFactory.createJSONObject();

		localeMetadataJSONObject.put(
			"dir", LanguageUtil.get(locale, "lang.dir"));
		localeMetadataJSONObject.put(
			"icon", getLocaleIcon(locale, themeDisplay));
		localeMetadataJSONObject.put(
			"label",
			HtmlUtil.escapeAttribute(locale.getDisplayName(siteDefaultLocale)));
		localeMetadataJSONObject.put(
			"languageId", LocaleUtil.toLanguageId(locale));

		return localeMetadataJSONObject;
	}

	protected String getValueString(Value value, Locale locale) {
		if (value != null) {
			return value.getString(locale);
		}

		return StringPool.BLANK;
	}

	@Reference
	private JSONFactory _jsonFactory;

}