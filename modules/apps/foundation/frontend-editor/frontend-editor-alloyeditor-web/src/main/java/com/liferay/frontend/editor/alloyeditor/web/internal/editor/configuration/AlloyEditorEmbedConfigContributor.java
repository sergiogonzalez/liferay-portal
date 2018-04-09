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

package com.liferay.frontend.editor.alloyeditor.web.internal.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Chema Balsas
 */
@Component(
	property = "editor.name=alloyeditor",
	service = EditorConfigContributor.class
)
public class AlloyEditorEmbedConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		jsonObject.put("embedProviders", getEmbedProvidersJSONArray());
	}

	protected JSONArray getEmbedProvidersJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(getFacebookEmbedProviderJSONObject());
		jsonArray.put(getTwitchEmbedProviderJSONObject());
		jsonArray.put(getVimeoEmbedProviderJSONObject());
		jsonArray.put(getYoutubeEmbedProviderJSONObject());

		return jsonArray;
	}

	protected JSONObject getFacebookEmbedProviderJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("id", "facebook");

		JSONArray schemasJSONArray = JSONFactoryUtil.createJSONArray();

		jsonObject.put("schemas", schemasJSONArray);

		schemasJSONArray.put("(https?:\\/\\/(?:www\\.)?facebook.com\\/\\S*\\/videos\\/\\S*)");

		jsonObject.put("tpl", "<div class=\"embed-responsive embed-responsive-16by9\" data-embed-id=\"{embedId}\"><iframe allowFullScreen=\"true\" allowTransparency=\"true\" class=\"embed-responsive-item\" frameborder=\"0\" height=\"315\" src=\"https://www.facebook.com/plugins/video.php?href={embedId}&show_text=0&width=560&height=315\" scrolling=\"no\" style=\"border:none;overflow:hidden\" width=\"560\"></iframe></div>");

		return jsonObject;
	}

	protected JSONObject getTwitchEmbedProviderJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("id", "twitch");

		JSONArray schemasJSONArray = JSONFactoryUtil.createJSONArray();

		jsonObject.put("schemas", schemasJSONArray);

		schemasJSONArray.put("https?:\\/\\/(?:www\\.)?twitch.tv\\/videos\\/(\\S*)$");

		jsonObject.put("tpl", "<div class=\"embed-responsive embed-responsive-16by9\" data-embed-id=\"{embedId}\"><iframe allowfullscreen=\"true\" class=\"embed-responsive-item\" frameborder=\"0\" height=\"315\" src=\"https://player.twitch.tv/?autoplay=false&video={embedId}\" scrolling=\"no\" width=\"560\" ></iframe></div>");

		return jsonObject;
	}

	protected JSONObject getVimeoEmbedProviderJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("id", "vimeo");

		JSONArray schemasJSONArray = JSONFactoryUtil.createJSONArray();

		jsonObject.put("schemas", schemasJSONArray);

		schemasJSONArray.put("https?:\\/\\/(?:www\\.)?vimeo\\.com\\/album\\/.*\\/video\\/(\\S*)");
		schemasJSONArray.put("https?:\\/\\/(?:www\\.)?vimeo\\.com\\/channels\\/.*\\/(\\S*)");
		schemasJSONArray.put("https?:\\/\\/(?:www\\.)?vimeo\\.com\\/groups\\/.*\\/videos\\/(\\S*)");
		schemasJSONArray.put("https?:\\/\\/(?:www\\.)?vimeo\\.com\\/(\\S*)$");

		jsonObject.put("tpl", "<div class=\"embed-responsive embed-responsive-16by9\" data-embed-id=\"{embedId}\"><iframe allowfullscreen class=\"embed-responsive-item\" frameborder=\"0\" height=\"315\" mozallowfullscreen src=\"https://player.vimeo.com/video/{embedId}\" webkitallowfullscreen width=\"560\"></iframe></div>");

		return jsonObject;
	}

	protected JSONObject getYoutubeEmbedProviderJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("id", "youtube");

		JSONArray schemasJSONArray = JSONFactoryUtil.createJSONArray();

		jsonObject.put("schemas", schemasJSONArray);

		schemasJSONArray.put("https?:\\/\\/(?:www\\.)?youtube.com\\/watch\\?v=(\\S*)$");

		jsonObject.put("tpl", "<div class=\"embed-responsive embed-responsive-16by9\" data-embed-id=\"{embedId}\"><iframe allow=\"autoplay; encrypted-media\" allowfullscreen height=\"315\" class=\"embed-responsive-item\" frameborder=\"0\" src=\"https://www.youtube.com/embed/{embedId}?rel=0\" width=\"560\"></iframe></div>");

		return jsonObject;
	}

}