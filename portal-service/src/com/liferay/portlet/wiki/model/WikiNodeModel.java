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

package com.liferay.portlet.wiki.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.WorkflowedModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the WikiNode service. Represents a row in the &quot;WikiNode&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.wiki.model.impl.WikiNodeModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.wiki.model.impl.WikiNodeImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiNode
 * @see com.liferay.portlet.wiki.model.impl.WikiNodeImpl
 * @see com.liferay.portlet.wiki.model.impl.WikiNodeModelImpl
 * @generated
 */
public interface WikiNodeModel extends BaseModel<WikiNode>, ContainerModel,
	GroupedModel, StagedModel, WorkflowedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a wiki node model instance should use the {@link WikiNode} interface instead.
	 */

	/**
	 * Returns the primary key of this wiki node.
	 *
	 * @return the primary key of this wiki node
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this wiki node.
	 *
	 * @param primaryKey the primary key of this wiki node
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this wiki node.
	 *
	 * @return the uuid of this wiki node
	 */
	@AutoEscape
	public String getUuid();

	/**
	 * Sets the uuid of this wiki node.
	 *
	 * @param uuid the uuid of this wiki node
	 */
	public void setUuid(String uuid);

	/**
	 * Returns the node ID of this wiki node.
	 *
	 * @return the node ID of this wiki node
	 */
	public long getNodeId();

	/**
	 * Sets the node ID of this wiki node.
	 *
	 * @param nodeId the node ID of this wiki node
	 */
	public void setNodeId(long nodeId);

	/**
	 * Returns the group ID of this wiki node.
	 *
	 * @return the group ID of this wiki node
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this wiki node.
	 *
	 * @param groupId the group ID of this wiki node
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this wiki node.
	 *
	 * @return the company ID of this wiki node
	 */
	public long getCompanyId();

	/**
	 * Sets the company ID of this wiki node.
	 *
	 * @param companyId the company ID of this wiki node
	 */
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this wiki node.
	 *
	 * @return the user ID of this wiki node
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this wiki node.
	 *
	 * @param userId the user ID of this wiki node
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this wiki node.
	 *
	 * @return the user uuid of this wiki node
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this wiki node.
	 *
	 * @param userUuid the user uuid of this wiki node
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this wiki node.
	 *
	 * @return the user name of this wiki node
	 */
	@AutoEscape
	public String getUserName();

	/**
	 * Sets the user name of this wiki node.
	 *
	 * @param userName the user name of this wiki node
	 */
	public void setUserName(String userName);

	/**
	 * Returns the create date of this wiki node.
	 *
	 * @return the create date of this wiki node
	 */
	public Date getCreateDate();

	/**
	 * Sets the create date of this wiki node.
	 *
	 * @param createDate the create date of this wiki node
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this wiki node.
	 *
	 * @return the modified date of this wiki node
	 */
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this wiki node.
	 *
	 * @param modifiedDate the modified date of this wiki node
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the name of this wiki node.
	 *
	 * @return the name of this wiki node
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this wiki node.
	 *
	 * @param name the name of this wiki node
	 */
	public void setName(String name);

	/**
	 * Returns the description of this wiki node.
	 *
	 * @return the description of this wiki node
	 */
	@AutoEscape
	public String getDescription();

	/**
	 * Sets the description of this wiki node.
	 *
	 * @param description the description of this wiki node
	 */
	public void setDescription(String description);

	/**
	 * Returns the last post date of this wiki node.
	 *
	 * @return the last post date of this wiki node
	 */
	public Date getLastPostDate();

	/**
	 * Sets the last post date of this wiki node.
	 *
	 * @param lastPostDate the last post date of this wiki node
	 */
	public void setLastPostDate(Date lastPostDate);

	/**
	 * Returns the status of this wiki node.
	 *
	 * @return the status of this wiki node
	 */
	public int getStatus();

	/**
	 * Sets the status of this wiki node.
	 *
	 * @param status the status of this wiki node
	 */
	public void setStatus(int status);

	/**
	 * Returns the status by user ID of this wiki node.
	 *
	 * @return the status by user ID of this wiki node
	 */
	public long getStatusByUserId();

	/**
	 * Sets the status by user ID of this wiki node.
	 *
	 * @param statusByUserId the status by user ID of this wiki node
	 */
	public void setStatusByUserId(long statusByUserId);

	/**
	 * Returns the status by user uuid of this wiki node.
	 *
	 * @return the status by user uuid of this wiki node
	 * @throws SystemException if a system exception occurred
	 */
	public String getStatusByUserUuid() throws SystemException;

	/**
	 * Sets the status by user uuid of this wiki node.
	 *
	 * @param statusByUserUuid the status by user uuid of this wiki node
	 */
	public void setStatusByUserUuid(String statusByUserUuid);

	/**
	 * Returns the status by user name of this wiki node.
	 *
	 * @return the status by user name of this wiki node
	 */
	@AutoEscape
	public String getStatusByUserName();

	/**
	 * Sets the status by user name of this wiki node.
	 *
	 * @param statusByUserName the status by user name of this wiki node
	 */
	public void setStatusByUserName(String statusByUserName);

	/**
	 * Returns the status date of this wiki node.
	 *
	 * @return the status date of this wiki node
	 */
	public Date getStatusDate();

	/**
	 * Sets the status date of this wiki node.
	 *
	 * @param statusDate the status date of this wiki node
	 */
	public void setStatusDate(Date statusDate);

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #isApproved()}
	 */
	public boolean getApproved();

	/**
	 * Returns <code>true</code> if this wiki node is approved.
	 *
	 * @return <code>true</code> if this wiki node is approved; <code>false</code> otherwise
	 */
	public boolean isApproved();

	/**
	 * Returns <code>true</code> if this wiki node is denied.
	 *
	 * @return <code>true</code> if this wiki node is denied; <code>false</code> otherwise
	 */
	public boolean isDenied();

	/**
	 * Returns <code>true</code> if this wiki node is a draft.
	 *
	 * @return <code>true</code> if this wiki node is a draft; <code>false</code> otherwise
	 */
	public boolean isDraft();

	/**
	 * Returns <code>true</code> if this wiki node is expired.
	 *
	 * @return <code>true</code> if this wiki node is expired; <code>false</code> otherwise
	 */
	public boolean isExpired();

	/**
	 * Returns <code>true</code> if this wiki node is inactive.
	 *
	 * @return <code>true</code> if this wiki node is inactive; <code>false</code> otherwise
	 */
	public boolean isInactive();

	/**
	 * Returns <code>true</code> if this wiki node is incomplete.
	 *
	 * @return <code>true</code> if this wiki node is incomplete; <code>false</code> otherwise
	 */
	public boolean isIncomplete();

	/**
	 * Returns <code>true</code> if this wiki node is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this wiki node is in the Recycle Bin; <code>false</code> otherwise
	 */
	public boolean isInTrash();

	/**
	 * Returns <code>true</code> if this wiki node is pending.
	 *
	 * @return <code>true</code> if this wiki node is pending; <code>false</code> otherwise
	 */
	public boolean isPending();

	/**
	 * Returns <code>true</code> if this wiki node is scheduled.
	 *
	 * @return <code>true</code> if this wiki node is scheduled; <code>false</code> otherwise
	 */
	public boolean isScheduled();

	/**
	 * Returns the container model ID of this wiki node.
	 *
	 * @return the container model ID of this wiki node
	 */
	public long getContainerModelId();

	/**
	 * Sets the container model ID of this wiki node.
	 *
	 * @param container model ID of this wiki node
	 */
	public void setContainerModelId(long containerModelId);

	/**
	 * Returns the container name of this wiki node.
	 *
	 * @return the container name of this wiki node
	 */
	public String getContainerModelName();

	/**
	 * Returns the parent container model ID of this wiki node.
	 *
	 * @return the parent container model ID of this wiki node
	 */
	public long getParentContainerModelId();

	/**
	 * Sets the parent container model ID of this wiki node.
	 *
	 * @param parent container model ID of this wiki node
	 */
	public void setParentContainerModelId(long parentContainerModelId);

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public Serializable getPrimaryKeyObj();

	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(WikiNode wikiNode);

	public int hashCode();

	public CacheModel<WikiNode> toCacheModel();

	public WikiNode toEscapedModel();

	public WikiNode toUnescapedModel();

	public String toString();

	public String toXmlString();
}