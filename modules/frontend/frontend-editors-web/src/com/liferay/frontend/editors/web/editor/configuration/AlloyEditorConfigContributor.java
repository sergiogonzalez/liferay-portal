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

package com.liferay.frontend.editors.web.editor.configuration;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.url.criterion.URLItemSelectorCriterion;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.net.URL;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@Component(
	property = {"editor.name=alloyeditor"},
	service = EditorConfigContributor.class
)
public class AlloyEditorConfigContributor extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		String contentsLanguageId = (String)inputEditorTaglibAttributes.get(
			"liferay-ui:input-editor:contentsLanguageId");

		Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

		String contentsLanguageDir = LanguageUtil.get(
			contentsLocale, "lang.dir");

		contentsLanguageId = LocaleUtil.toLanguageId(contentsLocale);

		jsonObject.put(
			"contentsLangDirection", HtmlUtil.escapeJS(contentsLanguageDir));
		jsonObject.put(
			"contentsLanguage", contentsLanguageId.replace("iw_", "he_"));
		jsonObject.put(
			"extraPlugins",
			"autolink,dragresize,dropimages,placeholder,selectionregion," +
				"tableresize,tabletools,uicore");

		String languageId = LocaleUtil.toLanguageId(themeDisplay.getLocale());

		jsonObject.put("language", languageId.replace("iw_", "he_"));
		jsonObject.put(
			"removePlugins",
			"elementspath,image,link,liststyle,resize,toolbar");

		if (liferayPortletResponse != null) {
			String name =
				liferayPortletResponse.getNamespace() +
					GetterUtil.getString(
						(String)inputEditorTaglibAttributes.get(
							"liferay-ui:input-editor:name"));

			populateFileBrowserURL(
				jsonObject, liferayPortletResponse, name + "selectDocument");

			jsonObject.put("srcNode", name);
		}

		jsonObject.put("toolbars", getToolbarsJSONObject());
	}

	@Reference(unbind = "-")
	public void setItemSelector(ItemSelector itemSelector) {
		_itemSelector = itemSelector;
	}

	protected JSONObject getStyleFormatsJSONObject() {
		String[] styleFormats = {
			"{name: 'Normal', style: { element: 'p', type: " +
				CKEDITOR_STYLE_BLOCK + "}}",
			"{name: 'Heading 1', style: { element: 'h1', type: " +
				CKEDITOR_STYLE_BLOCK + "}}",
			"{name: 'Heading 2', style: { element: 'h2', type: " +
				CKEDITOR_STYLE_BLOCK + "}}",
			"{name: 'Heading 3', style: { element: 'h3', type: " +
				CKEDITOR_STYLE_BLOCK + "}}",
			"{name: 'Heading 4', style: { element: 'h4', type: " +
				CKEDITOR_STYLE_BLOCK + "}}",
			"{name: 'Preformatted Text', style: { element:'pre', type: " +
				CKEDITOR_STYLE_BLOCK + "}}",
			"{name: 'Cited Work', style: { element:'cite', type: " +
				CKEDITOR_STYLE_INLINE + "}}",
			"{name: 'Computer Code', style: { element:'code', type: " +
				CKEDITOR_STYLE_INLINE + "}}",
			"{name: 'Info Message', style: { element: 'div', attributes: " +
				"{'class':'portlet-msg-info'}, type: " +
				CKEDITOR_STYLE_BLOCK + "}}",
			"{name: 'Alert Message', style: { element: 'div', attributes: " +
				"{'class': 'portlet-msg-alert'}, type: " +
				CKEDITOR_STYLE_BLOCK + "}}",
			"{name: 'Error Message', style: { element: 'div', attributes: " +
				"{'class': 'portlet-msg-error'}, type: " +
				CKEDITOR_STYLE_BLOCK + "}}"
		};

		JSONArray stylesJsonArray = JSONFactoryUtil.createJSONArray();

		for (String styleFormat : styleFormats) {
			stylesJsonArray.put(toJSONObject(styleFormat));
		}

		JSONObject configJsonObject = JSONFactoryUtil.createJSONObject();

		configJsonObject.put("styles", stylesJsonArray);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("name", "styles");
		jsonObject.put("cfg", configJsonObject);

		return jsonObject;
	}

	protected JSONObject getToolbarsAddJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("buttons", toJSONArray("['image', 'table', 'hline']"));
		jsonObject.put("tabIndex", 2);

		return jsonObject;
	}

	protected JSONObject getToolbarsJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("add", getToolbarsAddJSONObject());
		jsonObject.put("styles", getToolbarsStylesJSONObject());

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("selections", getToolbarsStylesSelectionsJSONArray());
		jsonObject.put("tabIndex", 1);

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesSelectionsImageJSONObject() {
		JSONObject jsonNObject = JSONFactoryUtil.createJSONObject();

		jsonNObject.put("buttons", toJSONArray("['imageLeft', 'imageRight']"));
		jsonNObject.put("name", "image");
		jsonNObject.put("test", "AlloyEditor.SelectionTest.image");

		return jsonNObject;
	}

	protected JSONArray getToolbarsStylesSelectionsJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(getToolbarsStylesSelectionsLinkJSONObject());
		jsonArray.put(getToolbarsStylesSelectionsImageJSONObject());
		jsonArray.put(getToolbarsStylesSelectionsTextJSONObject());
		jsonArray.put(getToolbarsStylesSelectionsTableJSONObject());

		return jsonArray;
	}

	protected JSONObject getToolbarsStylesSelectionsLinkJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("buttons", toJSONArray("['linkEdit']"));
		jsonObject.put("name", "link");
		jsonObject.put("test", "AlloyEditor.SelectionTest.link");

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesSelectionsTableJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"buttons",
			toJSONArray(
				"['tableRow', 'tableColumn', 'tableCell', 'tableRemove']"));
		jsonObject.put(
			"getArrowBoxClasses",
			"AlloyEditor.SelectionGetArrowBoxClasses.table");
		jsonObject.put("name", "table");
		jsonObject.put("setPosition", "AlloyEditor.SelectionSetPosition.table");
		jsonObject.put("test", "AlloyEditor.SelectionTest.table");

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesSelectionsTextJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(getStyleFormatsJSONObject());
		jsonArray.put("bold");
		jsonArray.put("italic");
		jsonArray.put("underline");
		jsonArray.put("link");
		jsonArray.put("twitter");

		jsonObject.put("buttons", jsonArray);
		jsonObject.put("name", "text");
		jsonObject.put("test", "AlloyEditor.SelectionTest.text");

		return jsonObject;
	}

	protected void populateFileBrowserURL(
		JSONObject jsonObject, LiferayPortletResponse liferayPortletResponse,
		String eventName) {

		Set<Class<?>> desiredReturnTypes = new HashSet<>();

		desiredReturnTypes.add(URL.class);

		ItemSelectorCriterion urlItemSelectorCriterion =
			new URLItemSelectorCriterion();

		urlItemSelectorCriterion.setDesiredReturnTypes(desiredReturnTypes);

		PortletURL layoutItemSelectorURL = _itemSelector.getItemSelectorURL(
			liferayPortletResponse, eventName, urlItemSelectorCriterion);

		jsonObject.put(
			"filebrowserBrowseUrl", layoutItemSelectorURL.toString());

		ItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredReturnTypes(desiredReturnTypes);

		PortletURL dlItemSelectorURL = _itemSelector.getItemSelectorURL(
			liferayPortletResponse, eventName, imageItemSelectorCriterion);

		jsonObject.put(
			"filebrowserImageBrowseLinkUrl", dlItemSelectorURL.toString());
		jsonObject.put(
			"filebrowserImageBrowseUrl", dlItemSelectorURL.toString());
	}

	private ItemSelector _itemSelector;

	private static final int CKEDITOR_STYLE_BLOCK = 1;
	private static final int CKEDITOR_STYLE_INLINE = 2;
}