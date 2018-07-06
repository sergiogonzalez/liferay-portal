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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link GoogleDriveEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GoogleDriveEntry
 * @generated
 */
@ProviderType
public class GoogleDriveEntryWrapper implements GoogleDriveEntry,
	ModelWrapper<GoogleDriveEntry> {
	public GoogleDriveEntryWrapper(GoogleDriveEntry googleDriveEntry) {
		_googleDriveEntry = googleDriveEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return GoogleDriveEntry.class;
	}

	@Override
	public String getModelClassName() {
		return GoogleDriveEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("entryId", getEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("googleDriveId", getGoogleDriveId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		String googleDriveId = (String)attributes.get("googleDriveId");

		if (googleDriveId != null) {
			setGoogleDriveId(googleDriveId);
		}
	}

	@Override
	public Object clone() {
		return new GoogleDriveEntryWrapper((GoogleDriveEntry)_googleDriveEntry.clone());
	}

	@Override
	public int compareTo(GoogleDriveEntry googleDriveEntry) {
		return _googleDriveEntry.compareTo(googleDriveEntry);
	}

	/**
	* Returns the company ID of this google drive entry.
	*
	* @return the company ID of this google drive entry
	*/
	@Override
	public long getCompanyId() {
		return _googleDriveEntry.getCompanyId();
	}

	/**
	* Returns the create date of this google drive entry.
	*
	* @return the create date of this google drive entry
	*/
	@Override
	public Date getCreateDate() {
		return _googleDriveEntry.getCreateDate();
	}

	/**
	* Returns the entry ID of this google drive entry.
	*
	* @return the entry ID of this google drive entry
	*/
	@Override
	public long getEntryId() {
		return _googleDriveEntry.getEntryId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _googleDriveEntry.getExpandoBridge();
	}

	/**
	* Returns the file entry ID of this google drive entry.
	*
	* @return the file entry ID of this google drive entry
	*/
	@Override
	public long getFileEntryId() {
		return _googleDriveEntry.getFileEntryId();
	}

	/**
	* Returns the google drive ID of this google drive entry.
	*
	* @return the google drive ID of this google drive entry
	*/
	@Override
	public String getGoogleDriveId() {
		return _googleDriveEntry.getGoogleDriveId();
	}

	/**
	* Returns the group ID of this google drive entry.
	*
	* @return the group ID of this google drive entry
	*/
	@Override
	public long getGroupId() {
		return _googleDriveEntry.getGroupId();
	}

	/**
	* Returns the modified date of this google drive entry.
	*
	* @return the modified date of this google drive entry
	*/
	@Override
	public Date getModifiedDate() {
		return _googleDriveEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this google drive entry.
	*
	* @return the primary key of this google drive entry
	*/
	@Override
	public long getPrimaryKey() {
		return _googleDriveEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _googleDriveEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this google drive entry.
	*
	* @return the user ID of this google drive entry
	*/
	@Override
	public long getUserId() {
		return _googleDriveEntry.getUserId();
	}

	/**
	* Returns the user name of this google drive entry.
	*
	* @return the user name of this google drive entry
	*/
	@Override
	public String getUserName() {
		return _googleDriveEntry.getUserName();
	}

	/**
	* Returns the user uuid of this google drive entry.
	*
	* @return the user uuid of this google drive entry
	*/
	@Override
	public String getUserUuid() {
		return _googleDriveEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _googleDriveEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _googleDriveEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _googleDriveEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _googleDriveEntry.isNew();
	}

	@Override
	public void persist() {
		_googleDriveEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_googleDriveEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this google drive entry.
	*
	* @param companyId the company ID of this google drive entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_googleDriveEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this google drive entry.
	*
	* @param createDate the create date of this google drive entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_googleDriveEntry.setCreateDate(createDate);
	}

	/**
	* Sets the entry ID of this google drive entry.
	*
	* @param entryId the entry ID of this google drive entry
	*/
	@Override
	public void setEntryId(long entryId) {
		_googleDriveEntry.setEntryId(entryId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_googleDriveEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_googleDriveEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_googleDriveEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the file entry ID of this google drive entry.
	*
	* @param fileEntryId the file entry ID of this google drive entry
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		_googleDriveEntry.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the google drive ID of this google drive entry.
	*
	* @param googleDriveId the google drive ID of this google drive entry
	*/
	@Override
	public void setGoogleDriveId(String googleDriveId) {
		_googleDriveEntry.setGoogleDriveId(googleDriveId);
	}

	/**
	* Sets the group ID of this google drive entry.
	*
	* @param groupId the group ID of this google drive entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_googleDriveEntry.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this google drive entry.
	*
	* @param modifiedDate the modified date of this google drive entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_googleDriveEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_googleDriveEntry.setNew(n);
	}

	/**
	* Sets the primary key of this google drive entry.
	*
	* @param primaryKey the primary key of this google drive entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_googleDriveEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_googleDriveEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this google drive entry.
	*
	* @param userId the user ID of this google drive entry
	*/
	@Override
	public void setUserId(long userId) {
		_googleDriveEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this google drive entry.
	*
	* @param userName the user name of this google drive entry
	*/
	@Override
	public void setUserName(String userName) {
		_googleDriveEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this google drive entry.
	*
	* @param userUuid the user uuid of this google drive entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_googleDriveEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<GoogleDriveEntry> toCacheModel() {
		return _googleDriveEntry.toCacheModel();
	}

	@Override
	public GoogleDriveEntry toEscapedModel() {
		return new GoogleDriveEntryWrapper(_googleDriveEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _googleDriveEntry.toString();
	}

	@Override
	public GoogleDriveEntry toUnescapedModel() {
		return new GoogleDriveEntryWrapper(_googleDriveEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _googleDriveEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof GoogleDriveEntryWrapper)) {
			return false;
		}

		GoogleDriveEntryWrapper googleDriveEntryWrapper = (GoogleDriveEntryWrapper)obj;

		if (Objects.equals(_googleDriveEntry,
					googleDriveEntryWrapper._googleDriveEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public GoogleDriveEntry getWrappedModel() {
		return _googleDriveEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _googleDriveEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _googleDriveEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_googleDriveEntry.resetOriginalValues();
	}

	private final GoogleDriveEntry _googleDriveEntry;
}