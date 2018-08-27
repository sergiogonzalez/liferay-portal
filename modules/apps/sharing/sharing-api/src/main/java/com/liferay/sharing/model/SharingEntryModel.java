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

package com.liferay.sharing.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.AttachedModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the SharingEntry service. Represents a row in the &quot;SharingEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.sharing.model.impl.SharingEntryModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.sharing.model.impl.SharingEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SharingEntry
 * @see com.liferay.sharing.model.impl.SharingEntryImpl
 * @see com.liferay.sharing.model.impl.SharingEntryModelImpl
 * @generated
 */
@ProviderType
public interface SharingEntryModel extends AttachedModel, BaseModel<SharingEntry>,
	ShardedModel, StagedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a sharing entry model instance should use the {@link SharingEntry} interface instead.
	 */

	/**
	 * Returns the primary key of this sharing entry.
	 *
	 * @return the primary key of this sharing entry
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this sharing entry.
	 *
	 * @param primaryKey the primary key of this sharing entry
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this sharing entry.
	 *
	 * @return the uuid of this sharing entry
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this sharing entry.
	 *
	 * @param uuid the uuid of this sharing entry
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the sharing entry ID of this sharing entry.
	 *
	 * @return the sharing entry ID of this sharing entry
	 */
	public long getSharingEntryId();

	/**
	 * Sets the sharing entry ID of this sharing entry.
	 *
	 * @param sharingEntryId the sharing entry ID of this sharing entry
	 */
	public void setSharingEntryId(long sharingEntryId);

	/**
	 * Returns the group ID of this sharing entry.
	 *
	 * @return the group ID of this sharing entry
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this sharing entry.
	 *
	 * @param groupId the group ID of this sharing entry
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this sharing entry.
	 *
	 * @return the company ID of this sharing entry
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this sharing entry.
	 *
	 * @param companyId the company ID of this sharing entry
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the create date of this sharing entry.
	 *
	 * @return the create date of this sharing entry
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this sharing entry.
	 *
	 * @param createDate the create date of this sharing entry
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this sharing entry.
	 *
	 * @return the modified date of this sharing entry
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this sharing entry.
	 *
	 * @param modifiedDate the modified date of this sharing entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the from user ID of this sharing entry.
	 *
	 * @return the from user ID of this sharing entry
	 */
	public long getFromUserId();

	/**
	 * Sets the from user ID of this sharing entry.
	 *
	 * @param fromUserId the from user ID of this sharing entry
	 */
	public void setFromUserId(long fromUserId);

	/**
	 * Returns the from user uuid of this sharing entry.
	 *
	 * @return the from user uuid of this sharing entry
	 */
	public String getFromUserUuid();

	/**
	 * Sets the from user uuid of this sharing entry.
	 *
	 * @param fromUserUuid the from user uuid of this sharing entry
	 */
	public void setFromUserUuid(String fromUserUuid);

	/**
	 * Returns the to user ID of this sharing entry.
	 *
	 * @return the to user ID of this sharing entry
	 */
	public long getToUserId();

	/**
	 * Sets the to user ID of this sharing entry.
	 *
	 * @param toUserId the to user ID of this sharing entry
	 */
	public void setToUserId(long toUserId);

	/**
	 * Returns the to user uuid of this sharing entry.
	 *
	 * @return the to user uuid of this sharing entry
	 */
	public String getToUserUuid();

	/**
	 * Sets the to user uuid of this sharing entry.
	 *
	 * @param toUserUuid the to user uuid of this sharing entry
	 */
	public void setToUserUuid(String toUserUuid);

	/**
	 * Returns the fully qualified class name of this sharing entry.
	 *
	 * @return the fully qualified class name of this sharing entry
	 */
	@Override
	public String getClassName();

	public void setClassName(String className);

	/**
	 * Returns the class name ID of this sharing entry.
	 *
	 * @return the class name ID of this sharing entry
	 */
	@Override
	public long getClassNameId();

	/**
	 * Sets the class name ID of this sharing entry.
	 *
	 * @param classNameId the class name ID of this sharing entry
	 */
	@Override
	public void setClassNameId(long classNameId);

	/**
	 * Returns the class pk of this sharing entry.
	 *
	 * @return the class pk of this sharing entry
	 */
	@Override
	public long getClassPK();

	/**
	 * Sets the class pk of this sharing entry.
	 *
	 * @param classPK the class pk of this sharing entry
	 */
	@Override
	public void setClassPK(long classPK);

	/**
	 * Returns the shareable of this sharing entry.
	 *
	 * @return the shareable of this sharing entry
	 */
	public boolean getShareable();

	/**
	 * Returns <code>true</code> if this sharing entry is shareable.
	 *
	 * @return <code>true</code> if this sharing entry is shareable; <code>false</code> otherwise
	 */
	public boolean isShareable();

	/**
	 * Sets whether this sharing entry is shareable.
	 *
	 * @param shareable the shareable of this sharing entry
	 */
	public void setShareable(boolean shareable);

	/**
	 * Returns the action IDs of this sharing entry.
	 *
	 * @return the action IDs of this sharing entry
	 */
	public long getActionIds();

	/**
	 * Sets the action IDs of this sharing entry.
	 *
	 * @param actionIds the action IDs of this sharing entry
	 */
	public void setActionIds(long actionIds);

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
	public int compareTo(SharingEntry sharingEntry);

	@Override
	public int hashCode();

	@Override
	public CacheModel<SharingEntry> toCacheModel();

	@Override
	public SharingEntry toEscapedModel();

	@Override
	public SharingEntry toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}