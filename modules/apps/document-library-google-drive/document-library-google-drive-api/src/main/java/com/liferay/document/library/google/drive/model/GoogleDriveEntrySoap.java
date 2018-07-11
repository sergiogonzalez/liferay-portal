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

package com.liferay.document.library.google.drive.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class GoogleDriveEntrySoap implements Serializable {
	public static GoogleDriveEntrySoap toSoapModel(GoogleDriveEntry model) {
		GoogleDriveEntrySoap soapModel = new GoogleDriveEntrySoap();

		soapModel.setEntryId(model.getEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setFileEntryId(model.getFileEntryId());
		soapModel.setGoogleDriveId(model.getGoogleDriveId());

		return soapModel;
	}

	public static GoogleDriveEntrySoap[] toSoapModels(GoogleDriveEntry[] models) {
		GoogleDriveEntrySoap[] soapModels = new GoogleDriveEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static GoogleDriveEntrySoap[][] toSoapModels(
		GoogleDriveEntry[][] models) {
		GoogleDriveEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new GoogleDriveEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new GoogleDriveEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static GoogleDriveEntrySoap[] toSoapModels(
		List<GoogleDriveEntry> models) {
		List<GoogleDriveEntrySoap> soapModels = new ArrayList<GoogleDriveEntrySoap>(models.size());

		for (GoogleDriveEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new GoogleDriveEntrySoap[soapModels.size()]);
	}

	public GoogleDriveEntrySoap() {
	}

	public long getPrimaryKey() {
		return _entryId;
	}

	public void setPrimaryKey(long pk) {
		setEntryId(pk);
	}

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public String getGoogleDriveId() {
		return _googleDriveId;
	}

	public void setGoogleDriveId(String googleDriveId) {
		_googleDriveId = googleDriveId;
	}

	private long _entryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _fileEntryId;
	private String _googleDriveId;
}