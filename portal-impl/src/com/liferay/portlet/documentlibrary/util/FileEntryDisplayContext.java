/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.trash.util.TrashUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public class FileEntryDisplayContext {

	public FileEntryDisplayContext(
		HttpServletRequest request, FileEntry fileEntry,
		FileVersion fileVersion) {

		_fileEntry = fileEntry;
		_fileVersion = fileVersion;
		_request = request;
		_initDependencies(request);
	}

	public boolean isCancelCheckoutDocumentButtonVisible()
		throws PortalException, SystemException {

		return hasUpdatePermission() && isCheckedOut() && (isLockedByMe() ||
			hasOverrideCheckoutPermission());
	}

	public boolean isCheckinButtonVisible()
		throws PortalException, SystemException {

		return hasUpdatePermission() && isLockedByMe();
	}

	public boolean isCheckoutDocumentButtonVisible()
		throws PortalException, SystemException {

		return hasUpdatePermission() && !isCheckedOut();
	}

	public boolean isDeleteButtonVisible()
		throws PortalException, SystemException {

		return hasDeletePermission() && !isLockedByOther() &&
			(!isDLFileEntry() || !isTrashEnabled());
	}

	public boolean isDownloadButtonVisible()
		throws PortalException, SystemException {

		return hasViewPermission();
	}

	public boolean isEditButtonVisible()
		throws PortalException, SystemException {

		return hasUpdatePermission() && !isLockedByOther();
	}

	public boolean isMoveButtonVisible()
		throws PortalException, SystemException {

		return hasUpdatePermission() && !isLockedByOther();
	}

	public boolean isMoveToTheRecycleBinButtonVisible()
		throws PortalException, SystemException {

		return hasDeletePermission() && !isLockedByOther() && isDLFileEntry() &&
			isTrashEnabled();
	}

	public boolean isOpenInMsOfficeButtonVisible()
		throws PortalException, SystemException {

		return hasViewPermission() && isOfficeDoc() && isWebDAVEnabled() &&
			isIEOnWin32();
	}

	public boolean isPermissionsButtonVisible()
		throws PortalException, SystemException {

		return hasPermissionsPermission();
	}

	private void _initDependencies(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

		_permissionChecker = themeDisplay.getPermissionChecker();
		_portletDisplay = themeDisplay.getPortletDisplay();
		_scopeGroupId = themeDisplay.getScopeGroupId();
	}

	private boolean hasDeletePermission()
		throws PortalException, SystemException {

		if (_hasDeletePermission == null) {
			_hasDeletePermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.DELETE);
		}

		return _hasDeletePermission;
	}

	private boolean hasOverrideCheckoutPermission()
		throws PortalException, SystemException {

		if (_hasOverrideCheckoutPermission == null) {
			_hasOverrideCheckoutPermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.OVERRIDE_CHECKOUT);
		}

		return _hasOverrideCheckoutPermission;
	}

	private boolean hasPermissionsPermission()
		throws PortalException, SystemException {

		if (_hasPermissionsPermission == null) {
			_hasPermissionsPermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.PERMISSIONS);
		}

		return _hasPermissionsPermission;
	}

	private boolean hasUpdatePermission()
		throws PortalException, SystemException {

		if (_hasUpdatePermission == null) {
			_hasUpdatePermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.UPDATE);
		}

		return _hasUpdatePermission;
	}

	private boolean hasViewPermission()
		throws PortalException, SystemException {

		if (_hasViewPermission == null) {
			_hasViewPermission = DLFileEntryPermission.contains(
				_permissionChecker, _fileEntry, ActionKeys.VIEW);
		}

		return _hasViewPermission;
	}

	private boolean isCheckedOut() {
		return _fileEntry.isCheckedOut();
	}

	private boolean isDLFileEntry() {
		return (_fileEntry.getModel() instanceof DLFileEntry);
	}

	private boolean isIEOnWin32() {
		if (_ieOnWin32 == null) {
			_ieOnWin32 = BrowserSnifferUtil.isIeOnWin32(_request);
		}

		return _ieOnWin32;
	}

	private boolean isLockedByMe() {
		return _fileEntry.hasLock();
	}

	private boolean isLockedByOther() {
		return (isCheckedOut() && !isLockedByMe());
	}

	private boolean isOfficeDoc() {
		if (_officeDoc == null) {
			_officeDoc = DLUtil.isOfficeExtension(_fileVersion.getExtension());
		}

		return _officeDoc;
	}

	private boolean isTrashEnabled() throws PortalException, SystemException {
		if (_trashEnabled == null) {
			_trashEnabled = TrashUtil.isTrashEnabled(_scopeGroupId);
		}

		return _trashEnabled;
	}

	private boolean isWebDAVEnabled() {
		return _portletDisplay.isWebDAVEnabled();
	}

	private FileEntry _fileEntry;
	private FileVersion _fileVersion;
	private Boolean _hasDeletePermission;
	private Boolean _hasOverrideCheckoutPermission;
	private Boolean _hasPermissionsPermission;
	private Boolean _hasUpdatePermission;
	private Boolean _hasViewPermission;
	private Boolean _ieOnWin32;
	private Boolean _officeDoc;
	private PermissionChecker _permissionChecker;
	private PortletDisplay _portletDisplay;
	private HttpServletRequest _request;
	private long _scopeGroupId;
	private Boolean _trashEnabled;

}