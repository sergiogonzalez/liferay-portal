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

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the GoogleDriveEntry service. Represents a row in the &quot;GoogleDriveEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.document.library.google.drive.model.impl.GoogleDriveEntryModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.document.library.google.drive.model.impl.GoogleDriveEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GoogleDriveEntry
 * @see com.liferay.document.library.google.drive.model.impl.GoogleDriveEntryImpl
 * @see com.liferay.document.library.google.drive.model.impl.GoogleDriveEntryModelImpl
 * @generated
 */
@ProviderType
public interface GoogleDriveEntryModel extends BaseModel<GoogleDriveEntry>,
	GroupedModel, ShardedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a google drive entry model instance should use the {@link GoogleDriveEntry} interface instead.
	 */

	/**
	 * Returns the primary key of this google drive entry.
	 *
	 * @return the primary key of this google drive entry
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this google drive entry.
	 *
	 * @param primaryKey the primary key of this google drive entry
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the entry ID of this google drive entry.
	 *
	 * @return the entry ID of this google drive entry
	 */
	public long getEntryId();

	/**
	 * Sets the entry ID of this google drive entry.
	 *
	 * @param entryId the entry ID of this google drive entry
	 */
	public void setEntryId(long entryId);

	/**
	 * Returns the group ID of this google drive entry.
	 *
	 * @return the group ID of this google drive entry
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this google drive entry.
	 *
	 * @param groupId the group ID of this google drive entry
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this google drive entry.
	 *
	 * @return the company ID of this google drive entry
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this google drive entry.
	 *
	 * @param companyId the company ID of this google drive entry
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this google drive entry.
	 *
	 * @return the user ID of this google drive entry
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this google drive entry.
	 *
	 * @param userId the user ID of this google drive entry
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this google drive entry.
	 *
	 * @return the user uuid of this google drive entry
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this google drive entry.
	 *
	 * @param userUuid the user uuid of this google drive entry
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this google drive entry.
	 *
	 * @return the user name of this google drive entry
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this google drive entry.
	 *
	 * @param userName the user name of this google drive entry
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this google drive entry.
	 *
	 * @return the create date of this google drive entry
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this google drive entry.
	 *
	 * @param createDate the create date of this google drive entry
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this google drive entry.
	 *
	 * @return the modified date of this google drive entry
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this google drive entry.
	 *
	 * @param modifiedDate the modified date of this google drive entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the file entry ID of this google drive entry.
	 *
	 * @return the file entry ID of this google drive entry
	 */
	public long getFileEntryId();

	/**
	 * Sets the file entry ID of this google drive entry.
	 *
	 * @param fileEntryId the file entry ID of this google drive entry
	 */
	public void setFileEntryId(long fileEntryId);

	/**
	 * Returns the google drive ID of this google drive entry.
	 *
	 * @return the google drive ID of this google drive entry
	 */
	@AutoEscape
	public String getGoogleDriveId();

	/**
	 * Sets the google drive ID of this google drive entry.
	 *
	 * @param googleDriveId the google drive ID of this google drive entry
	 */
	public void setGoogleDriveId(String googleDriveId);

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public Object clone();

	@Override
	public int compareTo(GoogleDriveEntry googleDriveEntry);

	@Override
	public int hashCode();

	@Override
	public CacheModel<GoogleDriveEntry> toCacheModel();

	@Override
	public GoogleDriveEntry toEscapedModel();

	@Override
	public GoogleDriveEntry toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}