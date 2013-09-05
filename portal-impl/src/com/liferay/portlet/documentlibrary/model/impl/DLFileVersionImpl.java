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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jorge Ferrer
 * @author Alexander Chow
 */
public class DLFileVersionImpl extends DLFileVersionBaseImpl {

	public DLFileVersionImpl() {
	}

	@Override
	public String buildTreePath() throws PortalException, SystemException {
		StringBundler sb = new StringBundler();

		buildTreePath(sb, this);

		return sb.toString();
	}

	@Override
	public InputStream getContentStream(boolean incrementCounter)
		throws PortalException, SystemException {

		return DLFileEntryLocalServiceUtil.getFileAsStream(
			PrincipalThreadLocal.getUserId(), getFileEntryId(), getVersion(),
			incrementCounter);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
				getCompanyId(), DLFileEntry.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public String getExtraSettings() {
		if (_extraSettingsProperties == null) {
			return super.getExtraSettings();
		}
		else {
			return _extraSettingsProperties.toString();
		}
	}

	@Override
	public UnicodeProperties getExtraSettingsProperties() {
		if (_extraSettingsProperties == null) {
			_extraSettingsProperties = new UnicodeProperties(true);

			try {
				_extraSettingsProperties.load(super.getExtraSettings());
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}

		return _extraSettingsProperties;
	}

	@Override
	public DLFileEntry getFileEntry() throws PortalException, SystemException {
		return DLFileEntryLocalServiceUtil.getFileEntry(getFileEntryId());
	}

	@Override
	public DLFolder getFolder() throws PortalException, SystemException {
		if (getFolderId() <= 0) {
			return new DLFolderImpl();
		}

		return DLFolderLocalServiceUtil.getFolder(getFolderId());
	}

	@Override
	public String getIcon() {
		return DLUtil.getFileIcon(getExtension());
	}

	@Override
	public DLFolder getTrashContainer()
		throws PortalException, SystemException {

		DLFolder dlFolder = null;

		try {
			dlFolder = getFolder();
		}
		catch (NoSuchFolderException nsfe) {
			return null;
		}

		if (dlFolder.isInTrash()) {
			return dlFolder;
		}

		return dlFolder.getTrashContainer();
	}

	@Override
	public boolean isInTrashContainer()
		throws PortalException, SystemException {

		if (getTrashContainer() != null) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void setExtraSettings(String extraSettings) {
		_extraSettingsProperties = null;

		super.setExtraSettings(extraSettings);
	}

	@Override
	public void setExtraSettingsProperties(
		UnicodeProperties extraSettingsProperties) {

		_extraSettingsProperties = extraSettingsProperties;

		super.setExtraSettings(_extraSettingsProperties.toString());
	}

	protected void buildTreePath(StringBundler sb, Group group)
		throws PortalException, SystemException {

		if (group == null) {
			sb.append(StringPool.SLASH);
		}
		else {
			buildTreePath(sb, group.getParentGroup());

			sb.append(group.getGroupId());
			sb.append(StringPool.SLASH);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileVersionImpl.class);

	private transient ExpandoBridge _expandoBridge;
	private UnicodeProperties _extraSettingsProperties;

}