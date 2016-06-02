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

package com.liferay.wiki.editor.configuration;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Sergio González
 */
public class WikiAttachmentAlloyEditorEditorConfigContributorTest
	extends PowerMockito {

	@Before
	public void setUp() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testRemoveImageButtonWithoutToolbarDoesNothing() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("extraPlugins", "plugin1,plugin2,plugin3");

		WikiAttachmentAlloyEditorEditorConfigContributor
			wikiAttachmentAlloyEditorEditorConfigContributor =
				new WikiAttachmentAlloyEditorEditorConfigContributor();

		wikiAttachmentAlloyEditorEditorConfigContributor.removeImageButton(
			jsonObject);

		Assert.assertEquals(1, jsonObject.length());
		Assert.assertEquals(
			"plugin1,plugin2,plugin3", jsonObject.getString("extraPlugins"));
	}

	@Test
	public void testRemoveImageButtonWithToolbarCameraHLineButtonDoesNothing()
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("extraPlugins", "plugin1,plugin2,plugin3");

		jsonObject.put(
			"toolbars", getToolbarsWithCameraHlineButtonJSONObject());

		WikiAttachmentAlloyEditorEditorConfigContributor
			wikiAttachmentAlloyEditorEditorConfigContributor =
				new WikiAttachmentAlloyEditorEditorConfigContributor();

		wikiAttachmentAlloyEditorEditorConfigContributor.removeImageButton(
			jsonObject);

		Assert.assertEquals(2, jsonObject.length());
		Assert.assertEquals(
			"plugin1,plugin2,plugin3", jsonObject.getString("extraPlugins"));

		JSONObject toolbarsJSONObject = jsonObject.getJSONObject("toolbars");

		JSONObject addJSONObject = toolbarsJSONObject.getJSONObject("add");

		JSONArray buttonsJSONArray = addJSONObject.getJSONArray("buttons");

		Assert.assertEquals(2, buttonsJSONArray.length());

		for (int i = 0; i < buttonsJSONArray.length(); i++) {
			String button = buttonsJSONArray.getString(i);

			if (!button.equals("camera") && !button.equals("hline")) {
				Assert.fail(button + " should not be in toolbar");
			}
		}
	}

	@Test
	public void testRemoveImageButtonWithToolbarCameraImageButton()
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("extraPlugins", "plugin1,plugin2,plugin3");

		jsonObject.put(
			"toolbars", getToolbarsWithCameraImageButtonJSONObject());

		WikiAttachmentAlloyEditorEditorConfigContributor
			wikiAttachmentAlloyEditorEditorConfigContributor =
				new WikiAttachmentAlloyEditorEditorConfigContributor();

		wikiAttachmentAlloyEditorEditorConfigContributor.removeImageButton(
			jsonObject);

		Assert.assertEquals(2, jsonObject.length());
		Assert.assertEquals(
			"plugin1,plugin2,plugin3", jsonObject.getString("extraPlugins"));

		JSONObject toolbarsJSONObject = jsonObject.getJSONObject("toolbars");

		JSONObject addJSONObject = toolbarsJSONObject.getJSONObject("add");

		JSONArray buttonsJSONArray = addJSONObject.getJSONArray("buttons");

		Assert.assertEquals(1, buttonsJSONArray.length());

		String button = buttonsJSONArray.getString(0);

		if (button.equals("image")) {
			Assert.fail("Image button was not removed");
		}

		if (!button.equals("camera")) {
			Assert.fail("Camera button should not be removed");
		}
	}

	@Test
	public void testRemoveImageButtonWithToolbarStylesDoesNothing() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("extraPlugins", "plugin1,plugin2,plugin3");

		jsonObject.put("toolbars", getToolbarsWithStylesJSONObject());

		WikiAttachmentAlloyEditorEditorConfigContributor
			wikiAttachmentAlloyEditorEditorConfigContributor =
				new WikiAttachmentAlloyEditorEditorConfigContributor();

		wikiAttachmentAlloyEditorEditorConfigContributor.removeImageButton(
			jsonObject);

		Assert.assertEquals(2, jsonObject.length());
		Assert.assertEquals(
			"plugin1,plugin2,plugin3", jsonObject.getString("extraPlugins"));

		JSONObject toolbarsJSONObject = jsonObject.getJSONObject("toolbars");

		JSONObject toolbarsStylesJSONObject = toolbarsJSONObject.getJSONObject(
			"styles");

		Assert.assertEquals(
			"AlloyEditor.Selections",
			toolbarsStylesJSONObject.getString("selections"));
		Assert.assertEquals(1, toolbarsStylesJSONObject.getInt("tabIndex"));
	}

	protected JSONObject getToolbarsAddCameraHlineButtonJSONObject()
		throws JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"buttons", JSONFactoryUtil.createJSONArray("['camera', 'hline']"));
		jsonObject.put("tabIndex", 2);

		return jsonObject;
	}

	protected JSONObject getToolbarsAddCameraImageButtonJSONObject()
		throws JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"buttons", JSONFactoryUtil.createJSONArray("['camera', 'image']"));
		jsonObject.put("tabIndex", 2);

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesSelectionsJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("selections", "AlloyEditor.Selections");
		jsonObject.put("tabIndex", 1);

		return jsonObject;
	}

	protected JSONObject getToolbarsWithCameraHlineButtonJSONObject()
		throws JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("add", getToolbarsAddCameraHlineButtonJSONObject());

		return jsonObject;
	}

	protected JSONObject getToolbarsWithCameraImageButtonJSONObject()
		throws JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("add", getToolbarsAddCameraImageButtonJSONObject());

		return jsonObject;
	}

	protected JSONObject getToolbarsWithStylesJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("styles", getToolbarsStylesSelectionsJSONObject());

		return jsonObject;
	}

}