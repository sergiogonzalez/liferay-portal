/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.model.UserNotificationEventModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

/**
 * The base model implementation for the UserNotificationEvent service. Represents a row in the &quot;UserNotificationEvent&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.UserNotificationEventModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link UserNotificationEventImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationEventImpl
 * @see com.liferay.portal.model.UserNotificationEvent
 * @see com.liferay.portal.model.UserNotificationEventModel
 * @generated
 */
public class UserNotificationEventModelImpl extends BaseModelImpl<UserNotificationEvent>
	implements UserNotificationEventModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a user notification event model instance should use the {@link com.liferay.portal.model.UserNotificationEvent} interface instead.
	 */
	public static final String TABLE_NAME = "UserNotificationEvent";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "userNotificationEventId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "type_", Types.VARCHAR },
			{ "timestamp", Types.BIGINT },
			{ "deliverBy", Types.BIGINT },
			{ "payload", Types.CLOB },
			{ "archived", Types.BOOLEAN }
		};
	public static final String TABLE_SQL_CREATE = "create table UserNotificationEvent (uuid_ VARCHAR(75) null,userNotificationEventId LONG not null primary key,companyId LONG,userId LONG,type_ VARCHAR(75) null,timestamp LONG,deliverBy LONG,payload TEXT null,archived BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table UserNotificationEvent";
	public static final String ORDER_BY_JPQL = " ORDER BY userNotificationEvent.timestamp DESC";
	public static final String ORDER_BY_SQL = " ORDER BY UserNotificationEvent.timestamp DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.UserNotificationEvent"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.UserNotificationEvent"),
			true);

	public Class<?> getModelClass() {
		return UserNotificationEvent.class;
	}

	public String getModelClassName() {
		return UserNotificationEvent.class.getName();
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.UserNotificationEvent"));

	public UserNotificationEventModelImpl() {
	}

	public long getPrimaryKey() {
		return _userNotificationEventId;
	}

	public void setPrimaryKey(long primaryKey) {
		setUserNotificationEventId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_userNotificationEventId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getUserNotificationEventId() {
		return _userNotificationEventId;
	}

	public void setUserNotificationEventId(long userNotificationEventId) {
		_userNotificationEventId = userNotificationEventId;
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

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public String getType() {
		if (_type == null) {
			return StringPool.BLANK;
		}
		else {
			return _type;
		}
	}

	public void setType(String type) {
		_type = type;
	}

	public long getTimestamp() {
		return _timestamp;
	}

	public void setTimestamp(long timestamp) {
		_timestamp = timestamp;
	}

	public long getDeliverBy() {
		return _deliverBy;
	}

	public void setDeliverBy(long deliverBy) {
		_deliverBy = deliverBy;
	}

	public String getPayload() {
		if (_payload == null) {
			return StringPool.BLANK;
		}
		else {
			return _payload;
		}
	}

	public void setPayload(String payload) {
		_payload = payload;
	}

	public boolean getArchived() {
		return _archived;
	}

	public boolean isArchived() {
		return _archived;
	}

	public void setArchived(boolean archived) {
		_archived = archived;
	}

	@Override
	public UserNotificationEvent toEscapedModel() {
		if (isEscapedModel()) {
			return (UserNotificationEvent)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (UserNotificationEvent)ProxyUtil.newProxyInstance(_classLoader,
						_escapedModelProxyInterfaces,
						new AutoEscapeBeanHandler(this));
			}

			return _escapedModelProxy;
		}
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					UserNotificationEvent.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		UserNotificationEventImpl userNotificationEventImpl = new UserNotificationEventImpl();

		userNotificationEventImpl.setUuid(getUuid());
		userNotificationEventImpl.setUserNotificationEventId(getUserNotificationEventId());
		userNotificationEventImpl.setCompanyId(getCompanyId());
		userNotificationEventImpl.setUserId(getUserId());
		userNotificationEventImpl.setType(getType());
		userNotificationEventImpl.setTimestamp(getTimestamp());
		userNotificationEventImpl.setDeliverBy(getDeliverBy());
		userNotificationEventImpl.setPayload(getPayload());
		userNotificationEventImpl.setArchived(getArchived());

		userNotificationEventImpl.resetOriginalValues();

		return userNotificationEventImpl;
	}

	public int compareTo(UserNotificationEvent userNotificationEvent) {
		int value = 0;

		if (getTimestamp() < userNotificationEvent.getTimestamp()) {
			value = -1;
		}
		else if (getTimestamp() > userNotificationEvent.getTimestamp()) {
			value = 1;
		}
		else {
			value = 0;
		}

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		UserNotificationEvent userNotificationEvent = null;

		try {
			userNotificationEvent = (UserNotificationEvent)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = userNotificationEvent.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void resetOriginalValues() {
	}

	@Override
	public CacheModel<UserNotificationEvent> toCacheModel() {
		UserNotificationEventCacheModel userNotificationEventCacheModel = new UserNotificationEventCacheModel();

		userNotificationEventCacheModel.uuid = getUuid();

		String uuid = userNotificationEventCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			userNotificationEventCacheModel.uuid = null;
		}

		userNotificationEventCacheModel.userNotificationEventId = getUserNotificationEventId();

		userNotificationEventCacheModel.companyId = getCompanyId();

		userNotificationEventCacheModel.userId = getUserId();

		userNotificationEventCacheModel.type = getType();

		String type = userNotificationEventCacheModel.type;

		if ((type != null) && (type.length() == 0)) {
			userNotificationEventCacheModel.type = null;
		}

		userNotificationEventCacheModel.timestamp = getTimestamp();

		userNotificationEventCacheModel.deliverBy = getDeliverBy();

		userNotificationEventCacheModel.payload = getPayload();

		String payload = userNotificationEventCacheModel.payload;

		if ((payload != null) && (payload.length() == 0)) {
			userNotificationEventCacheModel.payload = null;
		}

		userNotificationEventCacheModel.archived = getArchived();

		return userNotificationEventCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", userNotificationEventId=");
		sb.append(getUserNotificationEventId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", timestamp=");
		sb.append(getTimestamp());
		sb.append(", deliverBy=");
		sb.append(getDeliverBy());
		sb.append(", payload=");
		sb.append(getPayload());
		sb.append(", archived=");
		sb.append(getArchived());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(31);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.UserNotificationEvent");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userNotificationEventId</column-name><column-value><![CDATA[");
		sb.append(getUserNotificationEventId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>timestamp</column-name><column-value><![CDATA[");
		sb.append(getTimestamp());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>deliverBy</column-name><column-value><![CDATA[");
		sb.append(getDeliverBy());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>payload</column-name><column-value><![CDATA[");
		sb.append(getPayload());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>archived</column-name><column-value><![CDATA[");
		sb.append(getArchived());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = UserNotificationEvent.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			UserNotificationEvent.class
		};
	private String _uuid;
	private long _userNotificationEventId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _type;
	private long _timestamp;
	private long _deliverBy;
	private String _payload;
	private boolean _archived;
	private transient ExpandoBridge _expandoBridge;
	private UserNotificationEvent _escapedModelProxy;
}