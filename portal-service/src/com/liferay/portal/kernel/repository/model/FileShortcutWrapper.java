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

package com.liferay.portal.kernel.repository.model;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.model.ModelWrapper;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class FileShortcutWrapper
	implements FileShortcut, ModelWrapper<FileShortcut> {

	public FileShortcutWrapper(FileShortcut fileShortcut) {
		_fileShortcut = fileShortcut;
	}

	@Override
	public Object clone() {
		return new FileShortcutWrapper((FileShortcut)_fileShortcut.clone());
	}

	@Override
	public Map<String, Serializable> getAttributes() {
		return _fileShortcut.getAttributes();
	}

	@Override
	public long getCompanyId() {
		return _fileShortcut.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _fileShortcut.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _fileShortcut.getExpandoBridge();
	}

	@Override
	public long getGroupId() {
		return _fileShortcut.getGroupId();
	}

	@Override
	public Object getModel() {
		return _fileShortcut.getModel();
	}

	@Override
	public Class<?> getModelClass() {
		return _fileShortcut.getModelClass();
	}

	@Override
	public String getModelClassName() {
		return _fileShortcut.getModelClassName();
	}

	@Override
	public Date getModifiedDate() {
		return _fileShortcut.getModifiedDate();
	}

	@Override
	public long getPrimaryKey() {
		return _fileShortcut.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _fileShortcut.getPrimaryKeyObj();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _fileShortcut.getStagedModelType();
	}

	@Override
	public long getToFileEntryId() {
		return _fileShortcut.getToFileEntryId();
	}

	@Override
	public long getUserId() {
		return _fileShortcut.getUserId();
	}

	public String getUserName() {
		return _fileShortcut.getUserName();
	}

	@Override
	public String getUserUuid() throws SystemException {
		return _fileShortcut.getUserUuid();
	}

	@Override
	public String getUuid() {
		return _fileShortcut.getUuid();
	}

	@Override
	public FileShortcut getWrappedModel() {
		return _fileShortcut;
	}

	@Override
	public boolean isEscapedModel() {
		return _fileShortcut.isEscapedModel();
	}

	@Override
	public void setCompanyId(long companyId) {
		_fileShortcut.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date date) {
		_fileShortcut.setCreateDate(date);
	}

	@Override
	public void setGroupId(long groupId) {
		_fileShortcut.setGroupId(groupId);
	}

	@Override
	public void setModifiedDate(Date date) {
		_fileShortcut.setModifiedDate(date);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_fileShortcut.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setUserId(long userId) {
		_fileShortcut.setUserId(userId);
	}

	@Override
	public void setUserName(String userName) {
		_fileShortcut.setUserName(userName);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_fileShortcut.setUserUuid(userUuid);
	}

	@Override
	public void setUuid(String uuid) {
		_fileShortcut.setUuid(uuid);
	}

	@Override
	public FileShortcut toEscapedModel() {
		return _fileShortcut.toEscapedModel();
	}

	@Override
	public FileShortcut toUnescapedModel() {
		return _fileShortcut.toUnescapedModel();
	}

	private final FileShortcut _fileShortcut;

}