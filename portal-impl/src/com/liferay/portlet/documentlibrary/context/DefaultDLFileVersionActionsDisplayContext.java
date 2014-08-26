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

package com.liferay.portlet.documentlibrary.context;

import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class DefaultDLFileVersionActionsDisplayContext
	implements DLFileVersionActionsDisplayContext {

	public DefaultDLFileVersionActionsDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion)
		throws PortalException {

		_request = request;
		_fileVersion = fileVersion;

		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_companyId = _themeDisplay.getCompanyId();

		if (fileVersion != null) {
			_fileEntry = fileVersion.getFileEntry();
		}

		_dlFileEntryActionsDisplayContextHelper =
			new DLFileEntryActionsDisplayContextHelper(
				_themeDisplay.getPermissionChecker(), _fileEntry, fileVersion);

		_fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

		if ((_fileEntryTypeId == -1) && (_fileEntry != null) &&
			(_fileEntry.getModel() instanceof DLFileEntry)) {

			DLFileEntry dlFileEntry = (DLFileEntry)_fileEntry.getModel();

			_fileEntryTypeId = dlFileEntry.getFileEntryTypeId();
		}

		_folderId = BeanParamUtil.getLong(_fileEntry, request, "folderId");
		_portletDisplay = _themeDisplay.getPortletDisplay();
		_scopeGroupId = _themeDisplay.getScopeGroupId();

		PortletResponse portletResponse =
			(PortletResponse)_request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			portletResponse);

		PortletRequest portletRequest =
			(PortletRequest)_request.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		_liferayPortletRequest = PortalUtil.getLiferayPortletRequest(
			portletRequest);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_portletId = portletDisplay.getId();

		if (_portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
			_portletId = ParamUtil.getString(_request, "portletResource");
		}

		DLPortletInstanceSettings dlPortletInstanceSettings =
			DLPortletInstanceSettings.getInstance(
				_themeDisplay.getLayout(), _portletId);

		_dlActionsDisplayContext = new DLActionsDisplayContext(
			_request, dlPortletInstanceSettings);

		PortletURL portletURL = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);

		_currentURL = portletURL.toString();
	}

	@Override
	public List<MenuItem> getMenuItems() throws PortalException {
		List<MenuItem> menuItems = new ArrayList<MenuItem>();

		_addDownloadMenuItem(menuItems);
		_addOpenDocumentMenuItem(menuItems);
		_addEditMenuItem(menuItems);
		_addMoveMenuItem(menuItems);
		_addCheckoutMenuItem(menuItems);
		_addCheckinMenuItem(menuItems);
		_addCancelCheckoutMenuItem(menuItems);
		_addPermissionsMenuItem(menuItems);

		return menuItems;
	}

	@Override
	public String getPublishButtonLabel() {
		String publishButtonLabel = "publish";

		if (_hasWorkflowDefinitionLink()) {
			publishButtonLabel = "submit-for-publication";
		}

		if (_isFileEntrySaveAsDraft()) {
			publishButtonLabel = "save";
		}

		return publishButtonLabel;
	}

	@Override
	public String getSaveButtonLabel() {
		String saveButtonLabel = "save";

		FileVersion fileVersion =
			_dlFileEntryActionsDisplayContextHelper.getFileVersion();

		if ((fileVersion == null) ||
			_dlFileEntryActionsDisplayContextHelper.isApproved() ||
			_dlFileEntryActionsDisplayContextHelper.isDraft()) {

			saveButtonLabel = "save-as-draft";
		}

		return saveButtonLabel;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
	public boolean isAssetMetadataVisible() {
		String portletId = _portletDisplay.getId();

		if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletId.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN) ||
			portletId.equals(PortletKeys.MEDIA_GALLERY_DISPLAY) ||
			portletId.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) ||
			portletId.equals(PortletKeys.MY_WORKFLOW_INSTANCES) ||
			portletId.equals(PortletKeys.MY_WORKFLOW_TASKS) ||
			portletId.equals(PortletKeys.TRASH)) {

			return true;
		}

		return ParamUtil.getBoolean(_request, "showAssetMetadata");
	}

	@Override
	public boolean isCancelCheckoutDocumentButtonDisabled() {
		return false;
	}

	@Override
	public boolean isCancelCheckoutDocumentButtonVisible()
		throws PortalException {

		if (isCheckinButtonVisible() ||
			(_dlFileEntryActionsDisplayContextHelper.isCheckedOut() &&
			 _dlFileEntryActionsDisplayContextHelper.
				 hasOverrideCheckoutPermission())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isCheckinButtonDisabled() {
		return false;
	}

	@Override
	public boolean isCheckinButtonVisible() throws PortalException {
		if (_dlFileEntryActionsDisplayContextHelper.hasUpdatePermission() &&
			_dlFileEntryActionsDisplayContextHelper.isLockedByMe() &&
			_dlFileEntryActionsDisplayContextHelper.isSupportsLocking()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isCheckoutDocumentButtonVisible() throws PortalException {
		if (_dlFileEntryActionsDisplayContextHelper.hasUpdatePermission() &&
			!_dlFileEntryActionsDisplayContextHelper.isCheckedOut() &&
			_dlFileEntryActionsDisplayContextHelper.isSupportsLocking()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isCheckoutDocumentDisabled() {
		return false;
	}

	@Override
	public boolean isDeleteButtonVisible() throws PortalException {
		if (_isFileEntryDeletable() && !_isFileEntryTrashable()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isDownloadButtonVisible() throws PortalException {
		return _dlFileEntryActionsDisplayContextHelper.hasViewPermission();
	}

	@Override
	public boolean isEditButtonVisible() throws PortalException {
		if (_dlFileEntryActionsDisplayContextHelper.hasUpdatePermission() &&
			!_isFileEntryCheckedOutByOther()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isMoveButtonVisible() throws PortalException {
		if (_dlFileEntryActionsDisplayContextHelper.hasUpdatePermission() &&
			!_isFileEntryCheckedOutByOther()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isMoveToTheRecycleBinButtonVisible() throws PortalException {
		if (!isDeleteButtonVisible() && _isFileEntryDeletable()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isOpenInMsOfficeButtonVisible() throws PortalException {
		if (_dlFileEntryActionsDisplayContextHelper.hasViewPermission() &&
			_dlFileEntryActionsDisplayContextHelper.isOfficeDoc() &&
			_isWebDAVEnabled() && _isIEOnWin32()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isPermissionsButtonVisible() throws PortalException {
		return
			_dlFileEntryActionsDisplayContextHelper.hasPermissionsPermission();
	}

	@Override
	public boolean isPublishButtonDisabled() {
		if ((_dlFileEntryActionsDisplayContextHelper.isCheckedOut() &&
			 !_dlFileEntryActionsDisplayContextHelper.isLockedByMe()) ||
			(_dlFileEntryActionsDisplayContextHelper.isPending() &&
			 _isDLFileEntryDraftsEnabled())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isPublishButtonVisible() {
		return true;
	}

	@Override
	public boolean isSaveButtonDisabled() {
		if (_dlFileEntryActionsDisplayContextHelper.isCheckedOut() &&
			!_dlFileEntryActionsDisplayContextHelper.isLockedByMe()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isSaveButtonVisible() {
		return _isDLFileEntryDraftsEnabled();
	}

	@Override
	public boolean isViewButtonVisible() throws PortalException {
		return _dlFileEntryActionsDisplayContextHelper.hasViewPermission();
	}

	@Override
	public boolean isViewOriginalFileButtonVisible() throws PortalException {
		return _dlFileEntryActionsDisplayContextHelper.hasViewPermission();
	}

	private void _addCancelCheckoutMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (_dlActionsDisplayContext.isShowActions() &&
			isCancelCheckoutDocumentButtonVisible()) {

			PortletURL cancelCheckoutURL =
				_liferayPortletResponse.createActionURL();

			cancelCheckoutURL.setParameter(
				"struts_action", "/document_library/edit_file_entry");
			cancelCheckoutURL.setParameter(
				Constants.CMD, Constants.CANCEL_CHECKOUT);
			cancelCheckoutURL.setParameter("redirect", _currentURL);
			cancelCheckoutURL.setParameter(
				"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

			menuItems.add(
				new URLMenuItem(
					DLMenuItems.MENU_ITEM_ID_CANCEL_CHECKOUT, "icon-remove",
					"cancel-checkout[document]", cancelCheckoutURL.toString()));
		}
	}

	private void _addCheckinMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (_dlActionsDisplayContext.isShowActions() &&
			isCheckinButtonVisible()) {

			PortletURL checkinURL = _liferayPortletResponse.createActionURL();

			checkinURL.setParameter(
				"struts_action", "/document_library/edit_file_entry");
			checkinURL.setParameter(Constants.CMD, Constants.CHECKIN);
			checkinURL.setParameter("redirect", _currentURL);
			checkinURL.setParameter(
				"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

			menuItems.add(
				new URLMenuItem(
					DLMenuItems.MENU_ITEM_ID_CHECKIN, "icon-lock", "checkin",
					checkinURL.toString()));
		}
	}

	private void _addCheckoutMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (_dlActionsDisplayContext.isShowActions() &&
			isCheckoutDocumentButtonVisible()) {

			PortletURL checkoutURL = _liferayPortletResponse.createActionURL();

			checkoutURL.setParameter(
				"struts_action", "/document_library/edit_file_entry");
			checkoutURL.setParameter(Constants.CMD, Constants.CHECKOUT);
			checkoutURL.setParameter("redirect", _currentURL);
			checkoutURL.setParameter(
				"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

			menuItems.add(
				new URLMenuItem(
					DLMenuItems.MENU_ITEM_ID_CHECKOUT, "icon-unlock",
					"checkout[document]", checkoutURL.toString()));
		}
	}

	private void _addDownloadMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (isDownloadButtonVisible()) {
			String message =
				LanguageUtil.get(_request, "download") + " (" +
					TextFormatter.formatStorageSize(
						_fileEntry.getSize(), _themeDisplay.getLocale()) + ")";

			String url = DLUtil.getDownloadURL(
				_fileEntry, _fileVersion, _themeDisplay, StringPool.BLANK,
				false, true);

			menuItems.add(
				new URLMenuItem(
					DLMenuItems.MENU_ITEM_ID_DOWNLOAD, "icon-download", message,
					url, "_blank"));
		}
	}

	private void _addEditMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (_dlActionsDisplayContext.isShowActions()) {
			if (isEditButtonVisible()) {
				PortletURL editURL = _liferayPortletResponse.createRenderURL();

				editURL.setParameter(
					"struts_action", "/document_library/edit_file_entry");
				editURL.setParameter("redirect", _currentURL);
				editURL.setParameter("backURL", _currentURL);
				editURL.setParameter(
					"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

				menuItems.add(
					new URLMenuItem(
						DLMenuItems.MENU_ITEM_ID_EDIT, "icon-edit", "edit",
						editURL.toString()));
			}
		}
	}

	private void _addMoveMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (_dlActionsDisplayContext.isShowActions() && isMoveButtonVisible()) {
			PortletURL moveURL = _liferayPortletResponse.createRenderURL();

			moveURL.setParameter(
				"struts_action", "/document_library/move_entry");
			moveURL.setParameter(
				"fileEntryIds", String.valueOf(_fileEntry.getFileEntryId()));

			PortletURL viewFolderURL =
				_liferayPortletResponse.createRenderURL();

			viewFolderURL.setParameter(
				"struts_action", "/document_library/view");
			viewFolderURL.setParameter("folderId", String.valueOf(_folderId));

			moveURL.setParameter("redirect", viewFolderURL.toString());

			menuItems.add(
				new URLMenuItem(
					DLMenuItems.MENU_ITEM_ID_MOVE, "icon-move", "move",
					moveURL.toString()));
		}
	}

	private void _addOpenDocumentMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (isOpenInMsOfficeButtonVisible()) {
			String webDavURL = DLUtil.getWebDavURL(
				_themeDisplay, _fileEntry.getFolder(), _fileEntry,
				PropsValues.
					DL_FILE_ENTRY_OPEN_IN_MS_OFFICE_MANUAL_CHECK_IN_REQUIRED,
				true);

			String onClick =
				_liferayPortletResponse.getNamespace() + "openDocument('" +
					webDavURL + "');";

			Map<String, String> context = new HashMap<String, String>();

			context.put("namespace", _liferayPortletResponse.getNamespace());

			context.put(
				"errorMessage", UnicodeLanguageUtil.get(
					_request,
					"cannot-open-the-requested-document-due-to-the-following-" +
						"reason"));

			String javascript = _processFreeMarkerTemplate(
				"/com/liferay/portlet/documentlibrary/context/" +
					"open_document_js.ftl", context);

			menuItems.add(
				new JavascriptMenuItem(
					DLMenuItems.MENU_ITEM_ID_OPEN_DOCUMENT, "icon-file-alt",
					"open-in-ms-office", onClick, javascript));
		}
	}

	private void _addPermissionsMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (_dlActionsDisplayContext.isShowActions() &&
			isPermissionsButtonVisible()) {

			PortletURL permissionsURL = PermissionsURLTag.createURL(
				null, DLFileEntryConstants.getClassName(),
				HtmlUtil.unescape(_fileEntry.getTitle()), null,
				String.valueOf(_fileEntry.getFileEntryId()),
				LiferayWindowState.POP_UP.toString(), null, _request);

			URLMenuItem urlMenuItem = new URLMenuItem(
				DLMenuItems.MENU_ITEM_ID_PERMISSIONS, "icon-lock",
				"permissions", permissionsURL.toString());

			urlMenuItem.setMethod("get");
			urlMenuItem.setUseDialog(true);

			menuItems.add(urlMenuItem);
		}
	}

	private boolean _hasWorkflowDefinitionLink() {
		try {
			return DLUtil.hasWorkflowDefinitionLink(
				_companyId, _scopeGroupId, _folderId, _fileEntryTypeId);
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to check if file entry has workflow definition link",
				e);
		}
	}

	private boolean _isDLFileEntryDraftsEnabled() {
		return PropsValues.DL_FILE_ENTRY_DRAFTS_ENABLED;
	}

	private boolean _isFileEntryCheckedOutByOther() {
		if (_dlFileEntryActionsDisplayContextHelper.isCheckedOut() &&
			!_dlFileEntryActionsDisplayContextHelper.isLockedByMe()) {

			return true;
		}

		return false;
	}

	private boolean _isFileEntryDeletable() throws PortalException {
		if (_dlFileEntryActionsDisplayContextHelper.hasDeletePermission() &&
			!_isFileEntryCheckedOutByOther()) {

			return true;
		}

		return false;
	}

	private boolean _isFileEntrySaveAsDraft() {
		if ((_dlFileEntryActionsDisplayContextHelper.isCheckedOut() ||
			 _dlFileEntryActionsDisplayContextHelper.isPending()) &&
			!_isDLFileEntryDraftsEnabled()) {

			return true;
		}

		return false;
	}

	private boolean _isFileEntryTrashable() throws PortalException {
		if (_dlFileEntryActionsDisplayContextHelper.isDLFileEntry() &&
			_isTrashEnabled()) {

			return true;
		}

		return false;
	}

	private boolean _isIEOnWin32() {
		if (_ieOnWin32 == null) {
			_ieOnWin32 = BrowserSnifferUtil.isIeOnWin32(_request);
		}

		return _ieOnWin32;
	}

	private boolean _isTrashEnabled() throws PortalException {
		if (_trashEnabled == null) {
			_trashEnabled = TrashUtil.isTrashEnabled(_scopeGroupId);
		}

		return _trashEnabled;
	}

	private boolean _isWebDAVEnabled() {
		return _portletDisplay.isWebDAVEnabled();
	}

	private String _processFreeMarkerTemplate(
		String name, Map<String, String> context) {

		try {
			return FreeMarkerUtil.process(name, context);
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to process Freemarker template", e);
		}
	}

	private static final UUID _UUID = UUID.fromString(
		"85F6C50E-3893-4E32-9D63-208528A503FA");

	private long _companyId;
	private String _currentURL;
	private DLActionsDisplayContext _dlActionsDisplayContext;
	private DLFileEntryActionsDisplayContextHelper
		_dlFileEntryActionsDisplayContextHelper;
	private FileEntry _fileEntry;
	private long _fileEntryTypeId;
	private FileVersion _fileVersion;
	private long _folderId;
	private Boolean _ieOnWin32;
	private LiferayPortletRequest _liferayPortletRequest;
	private LiferayPortletResponse _liferayPortletResponse;
	private PortletDisplay _portletDisplay;
	private String _portletId;
	private HttpServletRequest _request;
	private long _scopeGroupId;
	private ThemeDisplay _themeDisplay;
	private Boolean _trashEnabled;

}