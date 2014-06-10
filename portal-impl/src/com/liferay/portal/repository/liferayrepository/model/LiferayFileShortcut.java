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

package com.liferay.portal.repository.liferayrepository.model;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class LiferayFileShortcut extends LiferayModel implements FileShortcut {

	public LiferayFileShortcut(DLFileShortcut dlFileShortcut) {
		_dlFileShortcut = dlFileShortcut;
		_escapedModel = false;
	}

	public LiferayFileShortcut(
		DLFileShortcut dlFileShortcut, boolean escapedModel) {

		_dlFileShortcut = dlFileShortcut;
		_escapedModel = escapedModel;
	}

	@Override
	public Object clone() {
		return new LiferayFileShortcut(
			(DLFileShortcut)_dlFileShortcut.clone(), _escapedModel);
	}

	@Override
	public Map<String, Serializable> getAttributes() {
		ExpandoBridge expandoBridge = _dlFileShortcut.getExpandoBridge();

		return expandoBridge.getAttributes();
	}

	@Override
	public long getCompanyId() {
		return _dlFileShortcut.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _dlFileShortcut.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _dlFileShortcut.getExpandoBridge();
	}

	@Override
	public long getGroupId() {
		return _dlFileShortcut.getGroupId();
	}

	@Override
	public Object getModel() {
		return _dlFileShortcut;
	}

	@Override
	public Class<?> getModelClass() {
		return LiferayFileShortcut.class;
	}

	@Override
	public String getModelClassName() {
		return getModelClass().getName();
	}

	@Override
	public Date getModifiedDate() {
		return _dlFileShortcut.getModifiedDate();
	}

	@Override
	public long getPrimaryKey() {
		return _dlFileShortcut.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _dlFileShortcut.getPrimaryKeyObj();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _dlFileShortcut.getStagedModelType();
	}

	@Override
	public long getToFileEntryId() {
		return _dlFileShortcut.getToFileEntryId();
	}

	@Override
	public long getUserId() {
		return _dlFileShortcut.getUserId();
	}

	@Override
	public String getUserName() {
		return _dlFileShortcut.getUserName();
	}

	@Override
	public String getUserUuid() throws SystemException {
		return _dlFileShortcut.getUserUuid();
	}

	@Override
	public String getUuid() {
		return _dlFileShortcut.getUuid();
	}

	@Override
	public boolean isEscapedModel() {
		return _escapedModel;
	}

	@Override
	public void setCompanyId(long companyId) {
		_dlFileShortcut.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date date) {
		_dlFileShortcut.setCreateDate(date);
	}

	@Override
	public void setGroupId(long groupId) {
		_dlFileShortcut.setGroupId(groupId);
	}

	@Override
	public void setModifiedDate(Date date) {
		_dlFileShortcut.setModifiedDate(date);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_dlFileShortcut.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setUserId(long userId) {
		_dlFileShortcut.setUserId(userId);
	}

	@Override
	public void setUserName(String userName) {
		_dlFileShortcut.setUserName(userName);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_dlFileShortcut.setUserUuid(userUuid);
	}

	@Override
	public void setUuid(String uuid) {
		_dlFileShortcut.setUuid(uuid);
	}

	@Override
	public FileShortcut toEscapedModel() {
		if (isEscapedModel()) {
			return this;
		}
		else {
			return new LiferayFileShortcut(_dlFileShortcut.toEscapedModel());
		}
	}

	@Override
	public FileShortcut toUnescapedModel() {
		if (isEscapedModel()) {
			return new LiferayFileShortcut(_dlFileShortcut.toUnescapedModel());
		}
		else {
			return this;
		}
	}

	private final DLFileShortcut _dlFileShortcut;
	private final boolean _escapedModel;

}