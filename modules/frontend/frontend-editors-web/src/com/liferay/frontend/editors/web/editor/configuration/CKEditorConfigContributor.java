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

import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xuggler.XugglerUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ambrin Chaudhary
 */
@Component(
		property = {"editor.name=ckeditor"},
		service = EditorConfigContributor.class
)

public class CKEditorConfigContributor extends BaseCKEditorConfigContributor {

		@Override
		public void populateConfigJSONObject(
				JSONObject jsonObject,
				Map<String, Object> inputEditorTaglibAttributes,
				ThemeDisplay themeDisplay,
				LiferayPortletResponse liferayPortletResponse) {

				super.populateConfigJSONObject(
						jsonObject, inputEditorTaglibAttributes, themeDisplay,
						liferayPortletResponse);

				jsonObject.put("autoParagraph", Boolean.FALSE);

				jsonObject.put("autoSaveTimeout", 3000);

				String colorSchemeCssClass =
					themeDisplay.getColorScheme().getCssClass();

				String cssClasses = (String)inputEditorTaglibAttributes.get(
					"liferay-ui:input-editor:cssClasses");

				jsonObject.put("bodyClass", "html-editor " +
						HtmlUtil.escape(colorSchemeCssClass) + " " +
						HtmlUtil.escape(cssClasses));

				jsonObject.put("closeNoticeTimeout", 8000);

				jsonObject.put("entities", Boolean.FALSE);

				String extraPlugins =
                    "a11yhelpbtn,imageselector,lfrpopup,media,scayt,wsc";

				boolean inlineEdit = GetterUtil.getBoolean(
						(String)inputEditorTaglibAttributes.get(
							"liferay-ui:input-editor:inlineEdit"));

				if (inlineEdit) {
					extraPlugins += ",ajaxsave,restore";
				}

				jsonObject.put("extraPlugins", extraPlugins);

				String languageId = LocaleUtil.toLanguageId(
					themeDisplay.getLocale());

				Locale locale = LocaleUtil.fromLanguageId(languageId);

				jsonObject.put("filebrowserWindowFeatures", "title=" +
					LanguageUtil.get(locale, "browse"));

				jsonObject.put("pasteFromWordRemoveFontStyles", Boolean.FALSE);

				jsonObject.put("pasteFromWordRemoveStyles", Boolean.FALSE);

				jsonObject.put("stylesSet", getStyleFormatsJSONArray());

				jsonObject.put(
						"toolbar_editInPlace",
						getToolbarEditInPlaceJSONArray(
							inputEditorTaglibAttributes));

				jsonObject.put(
						"toolbar_email", getToolbarEmailJSONArray(
							inputEditorTaglibAttributes));

				jsonObject.put(
						"toolbar_liferay",
						getToolbarLiferayJSONArray(
							inputEditorTaglibAttributes));

				jsonObject.put(
						"toolbar_liferayArticle",
						getToolbarLiferayArticleJSONArray(
							inputEditorTaglibAttributes));

				jsonObject.put(
						"toolbar_phone", getToolbarPhoneJSONArray(
							inputEditorTaglibAttributes));

				jsonObject.put(
						"toolbar_simple",
						getToolbarSimpleJSONArray(inputEditorTaglibAttributes));

				jsonObject.put(
						"toolbar_tablet",
						getToolbarTabletJSONArray(inputEditorTaglibAttributes));
		}

		protected JSONArray getStyleFormatsJSONArray() {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				String[] styleFormats = {
						"{name: 'Normal', element: 'p'}",
						"{name: 'Heading 1', element: 'h1'}",
						"{name: 'Heading 2', element: 'h2'}",
						"{name: 'Heading 3', element: 'h3'}",
						"{name: 'Heading 4', element: 'h4'}",
						"{name: 'Preformatted Text', element:'pre'}",
						"{name: 'Cited Work', element:'cite'}",
						"{name: 'Computer Code', element:'code'}",
						"{name: 'Info Message', element: 'div', attributes: " +
								"{'class': 'portlet-msg-info'}}",
						"{name: 'Alert Message', element: 'div', attributes: " +
								"{'class': 'portlet-msg-alert'}}",
						"{name: 'Error Message', element: 'div', attributes: " +
								"{'class': 'portlet-msg-error'}}"
				};

				for (String styleFormat : styleFormats) {
						jsonArray.put(toJSONObject(styleFormat));
				}

				return jsonArray;
		}

		protected JSONArray getToolbarEditInPlaceJSONArray(
				Map<String, Object> inputEditorTaglibAttributes) {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				jsonArray.put(toJSONArray(
						"['Bold', 'Italic', 'Underline', 'Strike', '-', " +
								"'Subscript', 'Superscript', " +
								"'-', 'RemoveFormat']"));

				jsonArray.put(
						toJSONArray("['NumberedList', 'BulletedList', " +
						"'-', 'Outdent', 'Indent']"));

				jsonArray.put("/");

				jsonArray.put(toJSONArray("['Styles']"));

				jsonArray.put(toJSONArray(
					"['SpellChecker', 'Scayt', '-', 'SpecialChar']"));

				jsonArray.put(toJSONArray("['Undo', 'Redo']"));

				if (isShowSource(inputEditorTaglibAttributes)) {
					jsonArray.put(toJSONArray("['Source']"));
				}

				jsonArray.put(toJSONArray("['A11YBtn']"));

				return jsonArray;
		}

		protected JSONArray getToolbarEmailJSONArray(
				Map<String, Object> inputEditorTaglibAttributes) {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				jsonArray.put(toJSONArray("['Bold', 'Italic', 'Underline', " +
					"'Strike', '-', 'RemoveFormat']"));

				jsonArray.put(toJSONArray("['TextColor', 'BGColor']"));

				jsonArray.put(toJSONArray("['JustifyLeft', 'JustifyCenter', " +
					"'JustifyRight', 'JustifyBlock']"));

				jsonArray.put(toJSONArray("['FontSize']"));

				jsonArray.put(toJSONArray("['Link', 'Unlink']"));

				jsonArray.put(toJSONArray("['ImageSelector']"));

				jsonArray.put("/");

				jsonArray.put(toJSONArray("['Cut', 'Copy', 'Paste', '-', " +
						"'PasteText', 'PasteFromWord', '-', 'SelectAll', " +
						"'-', 'Undo', 'Redo' ]"));

				jsonArray.put(toJSONArray("['SpellChecker', 'Scayt']"));

				if (isShowSource(inputEditorTaglibAttributes)) {
					jsonArray.put(toJSONArray("['Source']"));
				}

				jsonArray.put(toJSONArray("['A11YBtn']"));

				return jsonArray;
		}

		protected JSONArray getToolbarLiferayJSONArray(
				Map<String, Object> inputEditorTaglibAttributes) {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				jsonArray.put(toJSONArray(
						"['Bold', 'Italic', 'Underline', 'Strike', '-', " +
								"'Subscript', 'Superscript', " +
								"'-', 'RemoveFormat']"));

				jsonArray.put(toJSONArray("['TextColor', 'BGColor']"));

				jsonArray.put(toJSONArray("['JustifyLeft', 'JustifyCenter', " +
					"'JustifyRight', 'JustifyBlock']"));

				jsonArray.put(toJSONArray("['NumberedList', 'BulletedList', " +
					"'-', 'Outdent', 'Indent']"));

				jsonArray.put("/");

				jsonArray.put(toJSONArray("['Styles', 'FontSize']"));

				jsonArray.put(toJSONArray("['Link', 'Unlink', 'Anchor']"));

				String buttons = "['Table', '-', 'ImageSelector',";

				if (XugglerUtil.isEnabled()) {
					buttons +=" 'Audio', 'Video',";
				}

				buttons += " 'Flash', '-', 'Smiley', 'SpecialChar']";

				jsonArray.put(toJSONArray(buttons));

				jsonArray.put("/");

				boolean inlineEdit = GetterUtil.getBoolean(
						(String)inputEditorTaglibAttributes.get(
							"liferay-ui:input-editor:inlineEdit"));

				if (inlineEdit) {
						jsonArray.put(
							toJSONArray("['AjaxSave', '-', 'Restore']"));
				}

				jsonArray.put(toJSONArray("['Cut', 'Copy', 'Paste', '-'," +
						"'PasteText', 'PasteFromWord', '-', 'SelectAll' , " +
						"'-', 'Undo', 'Redo']"));

				jsonArray.put(toJSONArray(
					"['Find', 'Replace', '-', 'SpellChecker', 'Scayt']"));

				if (!inlineEdit && isShowSource(inputEditorTaglibAttributes)) {
					jsonArray.put(toJSONArray("['Source']"));
				}

				jsonArray.put(toJSONArray("['A11YBtn']"));

				return jsonArray;
		}

		protected JSONArray getToolbarLiferayArticleJSONArray(
				Map<String, Object> inputEditorTaglibAttributes) {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				jsonArray.put(toJSONArray(
						"['Bold', 'Italic', 'Underline', 'Strike', '-', " +
								"'Subscript', 'Superscript', " +
								"'-', 'RemoveFormat']"));

				jsonArray.put(toJSONArray("['TextColor', 'BGColor']"));

				jsonArray.put(toJSONArray("['JustifyLeft', 'JustifyCenter', " +
					"'JustifyRight', 'JustifyBlock']"));

				jsonArray.put(toJSONArray("['NumberedList', 'BulletedList', " +
					"'-' ,'Outdent', 'Indent', '-', 'Blockquote']"));

				jsonArray.put("/");

				jsonArray.put(toJSONArray("['Styles', 'FontSize']"));

				jsonArray.put(toJSONArray("['Link', 'Unlink', 'Anchor']"));

				String buttons = "['Table', '-', 'ImageSelector',";

				if (XugglerUtil.isEnabled()) {
					buttons +=" 'Audio', 'Video',";
				}

				buttons+= " 'Flash', '-', 'LiferayPageBreak', '-', " +
					"'Smiley', 'SpecialChar']";

				jsonArray.put(toJSONArray(buttons));

				jsonArray.put("/");

				jsonArray.put(toJSONArray("['Cut', 'Copy', 'Paste', '-'," +
						"'PasteText', 'PasteFromWord', '-', 'SelectAll' , " +
						"'-', 'Undo', 'Redo']"));

				jsonArray.put(toJSONArray(
					"['Find', 'Replace', '-', 'SpellChecker', 'Scayt']"));

				if (isShowSource(inputEditorTaglibAttributes)) {
					jsonArray.put(toJSONArray("['Source']"));
				}

				jsonArray.put(toJSONArray("['A11YBtn']"));

				return jsonArray;
		}

		protected JSONArray getToolbarPhoneJSONArray(
				Map<String, Object> inputEditorTaglibAttributes) {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				jsonArray.put(toJSONArray("['Bold', 'Italic', 'Underline']"));

				jsonArray.put(toJSONArray("['NumberedList', 'BulletedList']"));

				jsonArray.put(toJSONArray("['Link', 'Unlink']"));

				jsonArray.put(toJSONArray("['ImageSelector']"));

				if (isShowSource(inputEditorTaglibAttributes)) {
					jsonArray.put(toJSONArray("['Source']"));
				}

				return jsonArray;
		}

		protected JSONArray getToolbarSimpleJSONArray(
				Map<String, Object> inputEditorTaglibAttributes) {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				jsonArray.put(toJSONArray("['Bold', 'Italic', 'Underline', " +
					"'Strike']"));

				jsonArray.put(toJSONArray("['NumberedList', 'BulletedList']"));

				jsonArray.put(toJSONArray("['Link', 'Unlink']"));

				jsonArray.put(toJSONArray("['Table', 'ImageSelector']"));

				if (isShowSource(inputEditorTaglibAttributes)) {
					jsonArray.put(toJSONArray("['Source']"));
				}

				return jsonArray;
		}

		protected JSONArray getToolbarTabletJSONArray(
				Map<String, Object> inputEditorTaglibAttributes) {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				jsonArray.put(toJSONArray("['Bold', 'Italic', " +
					"'Underline', 'Strike']"));

				jsonArray.put(toJSONArray("['JustifyLeft', 'JustifyCenter', " +
					"'JustifyRight', 'JustifyBlock']"));

				jsonArray.put(toJSONArray("['NumberedList', 'BulletedList']"));

				jsonArray.put(toJSONArray("['Styles', 'FontSize']"));

				jsonArray.put(toJSONArray("['Link', 'Unlink']"));

				jsonArray.put(toJSONArray("['ImageSelector']"));

				if (isShowSource(inputEditorTaglibAttributes)) {
					jsonArray.put(toJSONArray("['Source']"));
				}

				return jsonArray;
		}
}