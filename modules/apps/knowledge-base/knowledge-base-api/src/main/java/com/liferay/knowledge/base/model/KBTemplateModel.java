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

package com.liferay.knowledge.base.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the KBTemplate service. Represents a row in the &quot;KBTemplate&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.knowledge.base.model.impl.KBTemplateModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.knowledge.base.model.impl.KBTemplateImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KBTemplate
 * @see com.liferay.knowledge.base.model.impl.KBTemplateImpl
 * @see com.liferay.knowledge.base.model.impl.KBTemplateModelImpl
 * @generated
 */
@ProviderType
public interface KBTemplateModel extends BaseModel<KBTemplate>, ShardedModel,
	StagedGroupedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a k b template model instance should use the {@link KBTemplate} interface instead.
	 */

	/**
	 * Returns the primary key of this k b template.
	 *
	 * @return the primary key of this k b template
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this k b template.
	 *
	 * @param primaryKey the primary key of this k b template
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this k b template.
	 *
	 * @return the uuid of this k b template
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this k b template.
	 *
	 * @param uuid the uuid of this k b template
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the kb template ID of this k b template.
	 *
	 * @return the kb template ID of this k b template
	 */
	public long getKbTemplateId();

	/**
	 * Sets the kb template ID of this k b template.
	 *
	 * @param kbTemplateId the kb template ID of this k b template
	 */
	public void setKbTemplateId(long kbTemplateId);

	/**
	 * Returns the group ID of this k b template.
	 *
	 * @return the group ID of this k b template
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this k b template.
	 *
	 * @param groupId the group ID of this k b template
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this k b template.
	 *
	 * @return the company ID of this k b template
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this k b template.
	 *
	 * @param companyId the company ID of this k b template
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this k b template.
	 *
	 * @return the user ID of this k b template
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this k b template.
	 *
	 * @param userId the user ID of this k b template
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this k b template.
	 *
	 * @return the user uuid of this k b template
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this k b template.
	 *
	 * @param userUuid the user uuid of this k b template
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this k b template.
	 *
	 * @return the user name of this k b template
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this k b template.
	 *
	 * @param userName the user name of this k b template
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this k b template.
	 *
	 * @return the create date of this k b template
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this k b template.
	 *
	 * @param createDate the create date of this k b template
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this k b template.
	 *
	 * @return the modified date of this k b template
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this k b template.
	 *
	 * @param modifiedDate the modified date of this k b template
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the title of this k b template.
	 *
	 * @return the title of this k b template
	 */
	@AutoEscape
	public String getTitle();

	/**
	 * Sets the title of this k b template.
	 *
	 * @param title the title of this k b template
	 */
	public void setTitle(String title);

	/**
	 * Returns the content of this k b template.
	 *
	 * @return the content of this k b template
	 */
	@AutoEscape
	public String getContent();

	/**
	 * Sets the content of this k b template.
	 *
	 * @param content the content of this k b template
	 */
	public void setContent(String content);

	/**
	 * Returns the last publish date of this k b template.
	 *
	 * @return the last publish date of this k b template
	 */
	@Override
	public Date getLastPublishDate();

	/**
	 * Sets the last publish date of this k b template.
	 *
	 * @param lastPublishDate the last publish date of this k b template
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate);

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
	public int compareTo(KBTemplate kbTemplate);

	@Override
	public int hashCode();

	@Override
	public CacheModel<KBTemplate> toCacheModel();

	@Override
	public KBTemplate toEscapedModel();

	@Override
	public KBTemplate toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}