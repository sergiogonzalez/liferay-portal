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

package com.liferay.document.library.google.docs.context;

import com.liferay.document.library.google.docs.util.GoogleDocsConstants;
import com.liferay.document.library.google.docs.util.GoogleDocsMetadataHelper;
import com.liferay.document.library.google.docs.util.ResourceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.UIItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLToolbarItem;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.context.BaseDLViewFileVersionDisplayContext;
import com.liferay.portlet.documentlibrary.context.DLMenuItemKeys;
import com.liferay.portlet.documentlibrary.context.DLToolbarItemKeys;
import com.liferay.portlet.documentlibrary.context.DLViewFileVersionDisplayContext;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class GoogleDocsDLViewFileVersionDisplayContext
	extends BaseDLViewFileVersionDisplayContext {

	public GoogleDocsDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext parentDLDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		super(_UUID, parentDLDisplayContext, request, response, fileVersion);
	}

	@Override
	public List<DDMStructure> getDDMStructures() throws PortalException {
		List<DDMStructure> ddmStructures = super.getDDMStructures();

		Iterator<DDMStructure> iterator = ddmStructures.iterator();

		while (iterator.hasNext()) {
			DDMStructure ddmStructure = iterator.next();

			String structureKey = ddmStructure.getStructureKey();

			if (structureKey.equals(
					GoogleDocsConstants.DDM_STRUCTURE_KEY_GOOGLE_DOCS)) {

				iterator.remove();

				break;
			}
		}

		return ddmStructures;
	}

	@Override
	public List<MenuItem> getMenuItems() throws PortalException {
		List<MenuItem> menuItems = super.getMenuItems();

		_removeUIItem(menuItems, DLMenuItemKeys.DOWNLOAD);
		_removeUIItem(menuItems, DLMenuItemKeys.OPEN_IN_MS_OFFICE);

		_insertEditInGoogleMenuItem(menuItems);

		return menuItems;
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		List<ToolbarItem> toolbarItems = super.getToolbarItems();

		_removeUIItem(toolbarItems, DLToolbarItemKeys.DOWNLOAD);
		_removeUIItem(toolbarItems, DLToolbarItemKeys.OPEN_IN_MS_OFFICE);

		_insertEditInGoogleToolbarItem(toolbarItems);

		return toolbarItems;
	}

	private int _getIndex(List<? extends UIItem> uiItems, String key) {
		for (int i = 0; i < uiItems.size(); i++) {
			UIItem menuItem = uiItems.get(i);

			if (key.equals(menuItem.getKey())) {
				return i;
			}
		}

		return -1;
	}

	private void _insertEditInGoogleMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		int index = _getIndex(menuItems, DLMenuItemKeys.EDIT);

		if (index == -1) {
			index = 0;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setIconCssClass("icon-edit");
		urlMenuItem.setKey(GoogleDocsMenuItemKeys.EDIT_IN_GOOGLE);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceUtil.getResourceBundle(
			themeDisplay.getLocale());

		String message = LanguageUtil.get(
			resourceBundle, "edit-in-google-docs");

		urlMenuItem.setMessage(message);

		urlMenuItem.setTarget("_blank");

		DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

		GoogleDocsMetadataHelper googleDocsMetadataHelper =
			new GoogleDocsMetadataHelper(dlFileVersion);

		String editURL = googleDocsMetadataHelper.getFieldValue(
			GoogleDocsConstants.DDM_FIELD_NAME_EDIT_URL);

		urlMenuItem.setURL(editURL);

		menuItems.add(index, urlMenuItem);
	}

	private void _insertEditInGoogleToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		int index = _getIndex(toolbarItems, DLToolbarItemKeys.EDIT);

		if (index == -1) {
			index = 0;
		}

		URLToolbarItem urlToolbarItem = new URLToolbarItem();

		urlToolbarItem.setIcon("icon-edit");
		urlToolbarItem.setKey(GoogleDocsToolbarItemKeys.EDIT_IN_GOOGLE);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceUtil.getResourceBundle(
			themeDisplay.getLocale());

		String message = LanguageUtil.get(
			resourceBundle, "edit-in-google-docs");

		urlToolbarItem.setLabel(message);

		urlToolbarItem.setTarget("_blank");

		DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

		GoogleDocsMetadataHelper googleDocsMetadataHelper =
			new GoogleDocsMetadataHelper(dlFileVersion);

		String editURL = googleDocsMetadataHelper.getFieldValue(
			GoogleDocsConstants.DDM_FIELD_NAME_EDIT_URL);

		urlToolbarItem.setURL(editURL);

		toolbarItems.add(index, urlToolbarItem);
	}

	private void _removeUIItem(List<? extends UIItem> uiItems, String key) {
		int index = _getIndex(uiItems, key);

		if (index != -1) {
			uiItems.remove(index);
		}
	}

	private static final UUID _UUID = UUID.fromString(
		"7B61EA79-83AE-4FFD-A77A-1D47E06EBBE9");

}