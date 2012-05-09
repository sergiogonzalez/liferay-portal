/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.trash.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link TrashEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TrashEntry
 * @generated
 */
public class TrashEntryWrapper implements TrashEntry, ModelWrapper<TrashEntry> {
	public TrashEntryWrapper(TrashEntry trashEntry) {
		_trashEntry = trashEntry;
	}

	public Class<?> getModelClass() {
		return TrashEntry.class;
	}

	public String getModelClassName() {
		return TrashEntry.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("entryId", getEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("status", getStatus());

		return attributes;
	}

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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	/**
	* Returns the primary key of this trash entry.
	*
	* @return the primary key of this trash entry
	*/
	public long getPrimaryKey() {
		return _trashEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this trash entry.
	*
	* @param primaryKey the primary key of this trash entry
	*/
	public void setPrimaryKey(long primaryKey) {
		_trashEntry.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the entry ID of this trash entry.
	*
	* @return the entry ID of this trash entry
	*/
	public long getEntryId() {
		return _trashEntry.getEntryId();
	}

	/**
	* Sets the entry ID of this trash entry.
	*
	* @param entryId the entry ID of this trash entry
	*/
	public void setEntryId(long entryId) {
		_trashEntry.setEntryId(entryId);
	}

	/**
	* Returns the group ID of this trash entry.
	*
	* @return the group ID of this trash entry
	*/
	public long getGroupId() {
		return _trashEntry.getGroupId();
	}

	/**
	* Sets the group ID of this trash entry.
	*
	* @param groupId the group ID of this trash entry
	*/
	public void setGroupId(long groupId) {
		_trashEntry.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this trash entry.
	*
	* @return the company ID of this trash entry
	*/
	public long getCompanyId() {
		return _trashEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this trash entry.
	*
	* @param companyId the company ID of this trash entry
	*/
	public void setCompanyId(long companyId) {
		_trashEntry.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this trash entry.
	*
	* @return the user ID of this trash entry
	*/
	public long getUserId() {
		return _trashEntry.getUserId();
	}

	/**
	* Sets the user ID of this trash entry.
	*
	* @param userId the user ID of this trash entry
	*/
	public void setUserId(long userId) {
		_trashEntry.setUserId(userId);
	}

	/**
	* Returns the user uuid of this trash entry.
	*
	* @return the user uuid of this trash entry
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _trashEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this trash entry.
	*
	* @param userUuid the user uuid of this trash entry
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_trashEntry.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this trash entry.
	*
	* @return the user name of this trash entry
	*/
	public java.lang.String getUserName() {
		return _trashEntry.getUserName();
	}

	/**
	* Sets the user name of this trash entry.
	*
	* @param userName the user name of this trash entry
	*/
	public void setUserName(java.lang.String userName) {
		_trashEntry.setUserName(userName);
	}

	/**
	* Returns the create date of this trash entry.
	*
	* @return the create date of this trash entry
	*/
	public java.util.Date getCreateDate() {
		return _trashEntry.getCreateDate();
	}

	/**
	* Sets the create date of this trash entry.
	*
	* @param createDate the create date of this trash entry
	*/
	public void setCreateDate(java.util.Date createDate) {
		_trashEntry.setCreateDate(createDate);
	}

	/**
	* Returns the fully qualified class name of this trash entry.
	*
	* @return the fully qualified class name of this trash entry
	*/
	public java.lang.String getClassName() {
		return _trashEntry.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_trashEntry.setClassName(className);
	}

	/**
	* Returns the class name ID of this trash entry.
	*
	* @return the class name ID of this trash entry
	*/
	public long getClassNameId() {
		return _trashEntry.getClassNameId();
	}

	/**
	* Sets the class name ID of this trash entry.
	*
	* @param classNameId the class name ID of this trash entry
	*/
	public void setClassNameId(long classNameId) {
		_trashEntry.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this trash entry.
	*
	* @return the class p k of this trash entry
	*/
	public long getClassPK() {
		return _trashEntry.getClassPK();
	}

	/**
	* Sets the class p k of this trash entry.
	*
	* @param classPK the class p k of this trash entry
	*/
	public void setClassPK(long classPK) {
		_trashEntry.setClassPK(classPK);
	}

	/**
	* Returns the type settings of this trash entry.
	*
	* @return the type settings of this trash entry
	*/
	public java.lang.String getTypeSettings() {
		return _trashEntry.getTypeSettings();
	}

	/**
	* Sets the type settings of this trash entry.
	*
	* @param typeSettings the type settings of this trash entry
	*/
	public void setTypeSettings(java.lang.String typeSettings) {
		_trashEntry.setTypeSettings(typeSettings);
	}

	/**
	* Returns the status of this trash entry.
	*
	* @return the status of this trash entry
	*/
	public int getStatus() {
		return _trashEntry.getStatus();
	}

	/**
	* Sets the status of this trash entry.
	*
	* @param status the status of this trash entry
	*/
	public void setStatus(int status) {
		_trashEntry.setStatus(status);
	}

	public boolean isNew() {
		return _trashEntry.isNew();
	}

	public void setNew(boolean n) {
		_trashEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _trashEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_trashEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _trashEntry.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _trashEntry.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_trashEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _trashEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_trashEntry.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new TrashEntryWrapper((TrashEntry)_trashEntry.clone());
	}

	public int compareTo(com.liferay.portlet.trash.model.TrashEntry trashEntry) {
		return _trashEntry.compareTo(trashEntry);
	}

	@Override
	public int hashCode() {
		return _trashEntry.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.trash.model.TrashEntry> toCacheModel() {
		return _trashEntry.toCacheModel();
	}

	public com.liferay.portlet.trash.model.TrashEntry toEscapedModel() {
		return new TrashEntryWrapper(_trashEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _trashEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _trashEntry.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_trashEntry.persist();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _trashEntry.getTypeSettingsProperties();
	}

	public java.lang.String getTypeSettingsProperty(java.lang.String key) {
		return _trashEntry.getTypeSettingsProperty(key);
	}

	public java.lang.String getTypeSettingsProperty(java.lang.String key,
		java.lang.String defaultValue) {
		return _trashEntry.getTypeSettingsProperty(key, defaultValue);
	}

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_trashEntry.setTypeSettingsProperties(typeSettingsProperties);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public TrashEntry getWrappedTrashEntry() {
		return _trashEntry;
	}

	public TrashEntry getWrappedModel() {
		return _trashEntry;
	}

	public void resetOriginalValues() {
		_trashEntry.resetOriginalValues();
	}

	private TrashEntry _trashEntry;
}