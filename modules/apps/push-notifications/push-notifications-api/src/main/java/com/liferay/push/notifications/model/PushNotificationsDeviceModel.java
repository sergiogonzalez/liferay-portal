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

package com.liferay.push.notifications.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the PushNotificationsDevice service. Represents a row in the &quot;PushNotificationsDevice&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.push.notifications.model.impl.PushNotificationsDeviceModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.push.notifications.model.impl.PushNotificationsDeviceImpl}.
 * </p>
 *
 * @author Bruno Farache
 * @see PushNotificationsDevice
 * @see com.liferay.push.notifications.model.impl.PushNotificationsDeviceImpl
 * @see com.liferay.push.notifications.model.impl.PushNotificationsDeviceModelImpl
 * @generated
 */
@ProviderType
public interface PushNotificationsDeviceModel extends BaseModel<PushNotificationsDevice>,
	ShardedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a push notifications device model instance should use the {@link PushNotificationsDevice} interface instead.
	 */

	/**
	 * Returns the primary key of this push notifications device.
	 *
	 * @return the primary key of this push notifications device
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this push notifications device.
	 *
	 * @param primaryKey the primary key of this push notifications device
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the push notifications device ID of this push notifications device.
	 *
	 * @return the push notifications device ID of this push notifications device
	 */
	public long getPushNotificationsDeviceId();

	/**
	 * Sets the push notifications device ID of this push notifications device.
	 *
	 * @param pushNotificationsDeviceId the push notifications device ID of this push notifications device
	 */
	public void setPushNotificationsDeviceId(long pushNotificationsDeviceId);

	/**
	 * Returns the company ID of this push notifications device.
	 *
	 * @return the company ID of this push notifications device
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this push notifications device.
	 *
	 * @param companyId the company ID of this push notifications device
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this push notifications device.
	 *
	 * @return the user ID of this push notifications device
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this push notifications device.
	 *
	 * @param userId the user ID of this push notifications device
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this push notifications device.
	 *
	 * @return the user uuid of this push notifications device
	 */
	public String getUserUuid();

	/**
	 * Sets the user uuid of this push notifications device.
	 *
	 * @param userUuid the user uuid of this push notifications device
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the create date of this push notifications device.
	 *
	 * @return the create date of this push notifications device
	 */
	public Date getCreateDate();

	/**
	 * Sets the create date of this push notifications device.
	 *
	 * @param createDate the create date of this push notifications device
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Returns the platform of this push notifications device.
	 *
	 * @return the platform of this push notifications device
	 */
	@AutoEscape
	public String getPlatform();

	/**
	 * Sets the platform of this push notifications device.
	 *
	 * @param platform the platform of this push notifications device
	 */
	public void setPlatform(String platform);

	/**
	 * Returns the token of this push notifications device.
	 *
	 * @return the token of this push notifications device
	 */
	@AutoEscape
	public String getToken();

	/**
	 * Sets the token of this push notifications device.
	 *
	 * @param token the token of this push notifications device
	 */
	public void setToken(String token);

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
	public int compareTo(PushNotificationsDevice pushNotificationsDevice);

	@Override
	public int hashCode();

	@Override
	public CacheModel<PushNotificationsDevice> toCacheModel();

	@Override
	public PushNotificationsDevice toEscapedModel();

	@Override
	public PushNotificationsDevice toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}