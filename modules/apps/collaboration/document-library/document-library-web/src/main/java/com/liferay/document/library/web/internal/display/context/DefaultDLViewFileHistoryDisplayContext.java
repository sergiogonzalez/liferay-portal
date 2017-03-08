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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.display.context.DLMimeTypeDisplayContext;
import com.liferay.document.library.display.context.DLViewFileHistoryDisplayContext;
import com.liferay.document.library.web.internal.display.context.logic.DLPortletInstanceSettingsHelper;
import com.liferay.document.library.web.internal.display.context.logic.FileEntryDisplayContextHelper;
import com.liferay.document.library.web.internal.display.context.logic.FileVersionDisplayContextHelper;
import com.liferay.document.library.web.internal.display.context.logic.UIItemsBuilder;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mauro Mariuzzo
 */
public class DefaultDLViewFileHistoryDisplayContext
	implements DLViewFileHistoryDisplayContext {

	public DefaultDLViewFileHistoryDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileShortcut fileShortcut,
			DLMimeTypeDisplayContext dlMimeTypeDisplayContext,
			ResourceBundle resourceBundle, StorageEngine storageEngine)
		throws PortalException {

		this(
			request, response, fileShortcut.getFileVersion(), fileShortcut,
			dlMimeTypeDisplayContext, resourceBundle, storageEngine);
	}

	public DefaultDLViewFileHistoryDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion,
		DLMimeTypeDisplayContext dlMimeTypeDisplayContext,
		ResourceBundle resourceBundle, StorageEngine storageEngine) {

		this(
			request, response, fileVersion, null, dlMimeTypeDisplayContext,
			resourceBundle, storageEngine);
	}

	@Override
	public Menu getMenu() throws PortalException {
		Menu menu = new Menu();

		menu.setDirection("left-side");
		menu.setMarkupView("lexicon");
		menu.setMenuItems(_getMenuItems());
		menu.setScroll(false);
		menu.setShowWhenSingleIcon(true);

		return menu;
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		List<ToolbarItem> toolbarItems = new ArrayList<>();

		return toolbarItems;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	private DefaultDLViewFileHistoryDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion, FileShortcut fileShortcut,
		DLMimeTypeDisplayContext dlMimeTypeDisplayContext,
		ResourceBundle resourceBundle, StorageEngine storageEngine) {

		try {
			_fileVersion = fileVersion;
			_dlMimeTypeDisplayContext = dlMimeTypeDisplayContext;
			_resourceBundle = resourceBundle;
			_storageEngine = storageEngine;

			DLRequestHelper dlRequestHelper = new DLRequestHelper(request);

			_dlPortletInstanceSettingsHelper =
				new DLPortletInstanceSettingsHelper(dlRequestHelper);

			_fileEntryDisplayContextHelper = new FileEntryDisplayContextHelper(
				dlRequestHelper.getPermissionChecker(),
				_getFileEntry(fileVersion));

			_fileVersionDisplayContextHelper =
				new FileVersionDisplayContextHelper(fileVersion);

			if (fileShortcut == null) {
				_uiItemsBuilder = new UIItemsBuilder(
					request, fileVersion, _resourceBundle);
			}
			else {
				_uiItemsBuilder = new UIItemsBuilder(
					request, fileShortcut, _resourceBundle);
			}
		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to build DefaultDLViewFileVersionDisplayContext for " +
					fileVersion,
				pe);
		}
	}

	private FileEntry _getFileEntry(FileVersion fileVersion)
		throws PortalException {

		if (fileVersion != null) {
			return fileVersion.getFileEntry();
		}

		return null;
	}

	private List<MenuItem> _getMenuItems() throws PortalException {
		List<MenuItem> menuItems = new ArrayList<>();

		if (_dlPortletInstanceSettingsHelper.isShowActions()) {
			_uiItemsBuilder.addDownloadMenuItem(menuItems);

			_uiItemsBuilder.addViewVersionMenuItem(menuItems);

			_uiItemsBuilder.addRevertToVersionMenuItem(menuItems);

			_uiItemsBuilder.addDeleteVersionMenuItem(menuItems);

			_uiItemsBuilder.addCompareToMenuItem(menuItems);
		}

		return menuItems;
	}

	private static final UUID _UUID = UUID.fromString(
		"8f4f3c55-3e93-41c5-a363-57d00161f274");

	@SuppressWarnings("unused")
	private final DLMimeTypeDisplayContext _dlMimeTypeDisplayContext;

	private final DLPortletInstanceSettingsHelper
		_dlPortletInstanceSettingsHelper;

	@SuppressWarnings("unused")
	private final FileEntryDisplayContextHelper _fileEntryDisplayContextHelper;

	@SuppressWarnings("unused")
	private final FileVersion _fileVersion;

	@SuppressWarnings("unused")
	private final FileVersionDisplayContextHelper
		_fileVersionDisplayContextHelper;

	private final ResourceBundle _resourceBundle;

	@SuppressWarnings("unused")
	private final StorageEngine _storageEngine;

	private final UIItemsBuilder _uiItemsBuilder;

}