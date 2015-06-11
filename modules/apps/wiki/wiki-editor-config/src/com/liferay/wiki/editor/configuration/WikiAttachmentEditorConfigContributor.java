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

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.DefaultItemSelectorReturnType;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.item.selector.criterion.WikiAttachmentItemSelectorCriterion;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"javax.portlet.name=33" + WikiPortletKeys.WIKI,
		"javax.portlet.name=33" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=33" + WikiPortletKeys.WIKI_DISPLAY
	},
	service = EditorConfigContributor.class
)
public class WikiAttachmentEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		Map<String, String> fileBrowserParamsMap =
			(Map<String, String>)inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:fileBrowserParams");

		long wikiPageResourcePrimKey = GetterUtil.getLong(
			fileBrowserParamsMap.get("wikiPageResourcePrimKey"));

		if (wikiPageResourcePrimKey == 0) {
			return;
		}

		ItemSelectorCriterion attachmentItemSelectorCriterion =
			new WikiAttachmentItemSelectorCriterion(wikiPageResourcePrimKey);

		Set<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new HashSet<>();

		desiredItemSelectorReturnTypes.add(DefaultItemSelectorReturnType.URL);

		attachmentItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		String name = GetterUtil.getString(
			inputEditorTaglibAttributes.get("liferay-ui:input-editor:name"));

		PortletURL attachmentItemSelectorURL = _itemSelector.getItemSelectorURL(
			liferayPortletResponse, name + "selectItem",
			attachmentItemSelectorCriterion);

		jsonObject.put(
			"filebrowserImageBrowseLinkUrl",
			attachmentItemSelectorURL.toString());
		jsonObject.put(
			"filebrowserImageBrowseUrl", attachmentItemSelectorURL.toString());
	}

	@Reference(unbind = "-")
	public void setItemSelector(ItemSelector itemSelector) {
		_itemSelector = itemSelector;
	}

	private ItemSelector _itemSelector;

}