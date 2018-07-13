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

package com.liferay.asset.auto.tagger.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the AssetAutoTaggerEntry service. Represents a row in the &quot;AssetAutoTaggerEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.asset.auto.tagger.model.impl.AssetAutoTaggerEntryModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.asset.auto.tagger.model.impl.AssetAutoTaggerEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetAutoTaggerEntry
 * @see com.liferay.asset.auto.tagger.model.impl.AssetAutoTaggerEntryImpl
 * @see com.liferay.asset.auto.tagger.model.impl.AssetAutoTaggerEntryModelImpl
 * @generated
 */
@ProviderType
public interface AssetAutoTaggerEntryModel extends BaseModel<AssetAutoTaggerEntry>,
	ShardedModel, StagedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a asset auto tagger entry model instance should use the {@link AssetAutoTaggerEntry} interface instead.
	 */

	/**
	 * Returns the primary key of this asset auto tagger entry.
	 *
	 * @return the primary key of this asset auto tagger entry
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this asset auto tagger entry.
	 *
	 * @param primaryKey the primary key of this asset auto tagger entry
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this asset auto tagger entry.
	 *
	 * @return the uuid of this asset auto tagger entry
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this asset auto tagger entry.
	 *
	 * @param uuid the uuid of this asset auto tagger entry
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the asset auto tagger entry ID of this asset auto tagger entry.
	 *
	 * @return the asset auto tagger entry ID of this asset auto tagger entry
	 */
	public long getAssetAutoTaggerEntryId();

	/**
	 * Sets the asset auto tagger entry ID of this asset auto tagger entry.
	 *
	 * @param assetAutoTaggerEntryId the asset auto tagger entry ID of this asset auto tagger entry
	 */
	public void setAssetAutoTaggerEntryId(long assetAutoTaggerEntryId);

	/**
	 * Returns the group ID of this asset auto tagger entry.
	 *
	 * @return the group ID of this asset auto tagger entry
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this asset auto tagger entry.
	 *
	 * @param groupId the group ID of this asset auto tagger entry
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this asset auto tagger entry.
	 *
	 * @return the company ID of this asset auto tagger entry
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this asset auto tagger entry.
	 *
	 * @param companyId the company ID of this asset auto tagger entry
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the create date of this asset auto tagger entry.
	 *
	 * @return the create date of this asset auto tagger entry
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this asset auto tagger entry.
	 *
	 * @param createDate the create date of this asset auto tagger entry
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this asset auto tagger entry.
	 *
	 * @return the modified date of this asset auto tagger entry
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this asset auto tagger entry.
	 *
	 * @param modifiedDate the modified date of this asset auto tagger entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the asset entry ID of this asset auto tagger entry.
	 *
	 * @return the asset entry ID of this asset auto tagger entry
	 */
	public long getAssetEntryId();

	/**
	 * Sets the asset entry ID of this asset auto tagger entry.
	 *
	 * @param assetEntryId the asset entry ID of this asset auto tagger entry
	 */
	public void setAssetEntryId(long assetEntryId);

	/**
	 * Returns the asset tag ID of this asset auto tagger entry.
	 *
	 * @return the asset tag ID of this asset auto tagger entry
	 */
	public long getAssetTagId();

	/**
	 * Sets the asset tag ID of this asset auto tagger entry.
	 *
	 * @param assetTagId the asset tag ID of this asset auto tagger entry
	 */
	public void setAssetTagId(long assetTagId);

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
	public int compareTo(AssetAutoTaggerEntry assetAutoTaggerEntry);

	@Override
	public int hashCode();

	@Override
	public CacheModel<AssetAutoTaggerEntry> toCacheModel();

	@Override
	public AssetAutoTaggerEntry toEscapedModel();

	@Override
	public AssetAutoTaggerEntry toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}