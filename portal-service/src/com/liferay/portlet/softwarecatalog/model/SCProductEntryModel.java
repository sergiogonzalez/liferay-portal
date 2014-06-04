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

package com.liferay.portlet.softwarecatalog.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the SCProductEntry service. Represents a row in the &quot;SCProductEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCProductEntry
 * @see com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryImpl
 * @see com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl
 * @generated
 */
@ProviderType
public interface SCProductEntryModel extends BaseModel<SCProductEntry>,
	GroupedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a s c product entry model instance should use the {@link SCProductEntry} interface instead.
	 */

	/**
	 * Returns the primary key of this s c product entry.
	 *
	 * @return the primary key of this s c product entry
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this s c product entry.
	 *
	 * @param primaryKey the primary key of this s c product entry
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the product entry ID of this s c product entry.
	 *
	 * @return the product entry ID of this s c product entry
	 */
	public long getProductEntryId();

	/**
	 * Sets the product entry ID of this s c product entry.
	 *
	 * @param productEntryId the product entry ID of this s c product entry
	 */
	public void setProductEntryId(long productEntryId);

	/**
	 * Returns the group ID of this s c product entry.
	 *
	 * @return the group ID of this s c product entry
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this s c product entry.
	 *
	 * @param groupId the group ID of this s c product entry
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this s c product entry.
	 *
	 * @return the company ID of this s c product entry
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this s c product entry.
	 *
	 * @param companyId the company ID of this s c product entry
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this s c product entry.
	 *
	 * @return the user ID of this s c product entry
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this s c product entry.
	 *
	 * @param userId the user ID of this s c product entry
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this s c product entry.
	 *
	 * @return the user uuid of this s c product entry
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this s c product entry.
	 *
	 * @param userUuid the user uuid of this s c product entry
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this s c product entry.
	 *
	 * @return the user name of this s c product entry
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this s c product entry.
	 *
	 * @param userName the user name of this s c product entry
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this s c product entry.
	 *
	 * @return the create date of this s c product entry
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this s c product entry.
	 *
	 * @param createDate the create date of this s c product entry
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this s c product entry.
	 *
	 * @return the modified date of this s c product entry
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this s c product entry.
	 *
	 * @param modifiedDate the modified date of this s c product entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the name of this s c product entry.
	 *
	 * @return the name of this s c product entry
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this s c product entry.
	 *
	 * @param name the name of this s c product entry
	 */
	public void setName(String name);

	/**
	 * Returns the type of this s c product entry.
	 *
	 * @return the type of this s c product entry
	 */
	@AutoEscape
	public String getType();

	/**
	 * Sets the type of this s c product entry.
	 *
	 * @param type the type of this s c product entry
	 */
	public void setType(String type);

	/**
	 * Returns the tags of this s c product entry.
	 *
	 * @return the tags of this s c product entry
	 */
	@AutoEscape
	public String getTags();

	/**
	 * Sets the tags of this s c product entry.
	 *
	 * @param tags the tags of this s c product entry
	 */
	public void setTags(String tags);

	/**
	 * Returns the short description of this s c product entry.
	 *
	 * @return the short description of this s c product entry
	 */
	@AutoEscape
	public String getShortDescription();

	/**
	 * Sets the short description of this s c product entry.
	 *
	 * @param shortDescription the short description of this s c product entry
	 */
	public void setShortDescription(String shortDescription);

	/**
	 * Returns the long description of this s c product entry.
	 *
	 * @return the long description of this s c product entry
	 */
	@AutoEscape
	public String getLongDescription();

	/**
	 * Sets the long description of this s c product entry.
	 *
	 * @param longDescription the long description of this s c product entry
	 */
	public void setLongDescription(String longDescription);

	/**
	 * Returns the page u r l of this s c product entry.
	 *
	 * @return the page u r l of this s c product entry
	 */
	@AutoEscape
	public String getPageURL();

	/**
	 * Sets the page u r l of this s c product entry.
	 *
	 * @param pageURL the page u r l of this s c product entry
	 */
	public void setPageURL(String pageURL);

	/**
	 * Returns the author of this s c product entry.
	 *
	 * @return the author of this s c product entry
	 */
	@AutoEscape
	public String getAuthor();

	/**
	 * Sets the author of this s c product entry.
	 *
	 * @param author the author of this s c product entry
	 */
	public void setAuthor(String author);

	/**
	 * Returns the repo group ID of this s c product entry.
	 *
	 * @return the repo group ID of this s c product entry
	 */
	@AutoEscape
	public String getRepoGroupId();

	/**
	 * Sets the repo group ID of this s c product entry.
	 *
	 * @param repoGroupId the repo group ID of this s c product entry
	 */
	public void setRepoGroupId(String repoGroupId);

	/**
	 * Returns the repo artifact ID of this s c product entry.
	 *
	 * @return the repo artifact ID of this s c product entry
	 */
	@AutoEscape
	public String getRepoArtifactId();

	/**
	 * Sets the repo artifact ID of this s c product entry.
	 *
	 * @param repoArtifactId the repo artifact ID of this s c product entry
	 */
	public void setRepoArtifactId(String repoArtifactId);

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
	public int compareTo(SCProductEntry scProductEntry);

	@Override
	public int hashCode();

	@Override
	public CacheModel<SCProductEntry> toCacheModel();

	@Override
	public SCProductEntry toEscapedModel();

	@Override
	public SCProductEntry toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}