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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserIdMapper;
import com.liferay.portal.model.UserIdMapperModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the UserIdMapper service. Represents a row in the &quot;UserIdMapper&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.UserIdMapperModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link UserIdMapperImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserIdMapperImpl
 * @see com.liferay.portal.model.UserIdMapper
 * @see com.liferay.portal.model.UserIdMapperModel
 * @generated
 */
public class UserIdMapperModelImpl extends BaseModelImpl<UserIdMapper>
	implements UserIdMapperModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a user ID mapper model instance should use the {@link com.liferay.portal.model.UserIdMapper} interface instead.
	 */
	public static final String TABLE_NAME = "UserIdMapper";
	public static final Object[][] TABLE_COLUMNS = {
			{ "mvccVersion", Types.BIGINT },
			{ "userIdMapperId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "type_", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "externalUserId", Types.VARCHAR }
		};
	public static final String TABLE_SQL_CREATE = "create table UserIdMapper (mvccVersion LONG default 0,userIdMapperId LONG not null primary key,userId LONG,type_ VARCHAR(75) null,description VARCHAR(75) null,externalUserId VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table UserIdMapper";
	public static final String ORDER_BY_JPQL = " ORDER BY userIdMapper.userIdMapperId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY UserIdMapper.userIdMapperId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.UserIdMapper"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.UserIdMapper"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.model.UserIdMapper"),
			true);
	public static long EXTERNALUSERID_COLUMN_BITMASK = 1L;
	public static long TYPE_COLUMN_BITMASK = 2L;
	public static long USERID_COLUMN_BITMASK = 4L;
	public static long USERIDMAPPERID_COLUMN_BITMASK = 8L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.UserIdMapper"));

	public UserIdMapperModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _userIdMapperId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setUserIdMapperId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _userIdMapperId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return UserIdMapper.class;
	}

	@Override
	public String getModelClassName() {
		return UserIdMapper.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("userIdMapperId", getUserIdMapperId());
		attributes.put("userId", getUserId());
		attributes.put("type", getType());
		attributes.put("description", getDescription());
		attributes.put("externalUserId", getExternalUserId());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long userIdMapperId = (Long)attributes.get("userIdMapperId");

		if (userIdMapperId != null) {
			setUserIdMapperId(userIdMapperId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String externalUserId = (String)attributes.get("externalUserId");

		if (externalUserId != null) {
			setExternalUserId(externalUserId);
		}
	}

	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@Override
	public long getUserIdMapperId() {
		return _userIdMapperId;
	}

	@Override
	public void setUserIdMapperId(long userIdMapperId) {
		_userIdMapperId = userIdMapperId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	@Override
	public String getType() {
		if (_type == null) {
			return StringPool.BLANK;
		}
		else {
			return _type;
		}
	}

	@Override
	public void setType(String type) {
		_columnBitmask |= TYPE_COLUMN_BITMASK;

		if (_originalType == null) {
			_originalType = _type;
		}

		_type = type;
	}

	public String getOriginalType() {
		return GetterUtil.getString(_originalType);
	}

	@Override
	public String getDescription() {
		if (_description == null) {
			return StringPool.BLANK;
		}
		else {
			return _description;
		}
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@Override
	public String getExternalUserId() {
		if (_externalUserId == null) {
			return StringPool.BLANK;
		}
		else {
			return _externalUserId;
		}
	}

	@Override
	public void setExternalUserId(String externalUserId) {
		_columnBitmask |= EXTERNALUSERID_COLUMN_BITMASK;

		if (_originalExternalUserId == null) {
			_originalExternalUserId = _externalUserId;
		}

		_externalUserId = externalUserId;
	}

	public String getOriginalExternalUserId() {
		return GetterUtil.getString(_originalExternalUserId);
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(0,
			UserIdMapper.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public UserIdMapper toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (UserIdMapper)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		UserIdMapperImpl userIdMapperImpl = new UserIdMapperImpl();

		userIdMapperImpl.setMvccVersion(getMvccVersion());
		userIdMapperImpl.setUserIdMapperId(getUserIdMapperId());
		userIdMapperImpl.setUserId(getUserId());
		userIdMapperImpl.setType(getType());
		userIdMapperImpl.setDescription(getDescription());
		userIdMapperImpl.setExternalUserId(getExternalUserId());

		userIdMapperImpl.resetOriginalValues();

		return userIdMapperImpl;
	}

	@Override
	public int compareTo(UserIdMapper userIdMapper) {
		long primaryKey = userIdMapper.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserIdMapper)) {
			return false;
		}

		UserIdMapper userIdMapper = (UserIdMapper)obj;

		long primaryKey = userIdMapper.getPrimaryKey();

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
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		UserIdMapperModelImpl userIdMapperModelImpl = this;

		userIdMapperModelImpl._originalUserId = userIdMapperModelImpl._userId;

		userIdMapperModelImpl._setOriginalUserId = false;

		userIdMapperModelImpl._originalType = userIdMapperModelImpl._type;

		userIdMapperModelImpl._originalExternalUserId = userIdMapperModelImpl._externalUserId;

		userIdMapperModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<UserIdMapper> toCacheModel() {
		UserIdMapperCacheModel userIdMapperCacheModel = new UserIdMapperCacheModel();

		userIdMapperCacheModel.mvccVersion = getMvccVersion();

		userIdMapperCacheModel.userIdMapperId = getUserIdMapperId();

		userIdMapperCacheModel.userId = getUserId();

		userIdMapperCacheModel.type = getType();

		String type = userIdMapperCacheModel.type;

		if ((type != null) && (type.length() == 0)) {
			userIdMapperCacheModel.type = null;
		}

		userIdMapperCacheModel.description = getDescription();

		String description = userIdMapperCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			userIdMapperCacheModel.description = null;
		}

		userIdMapperCacheModel.externalUserId = getExternalUserId();

		String externalUserId = userIdMapperCacheModel.externalUserId;

		if ((externalUserId != null) && (externalUserId.length() == 0)) {
			userIdMapperCacheModel.externalUserId = null;
		}

		return userIdMapperCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{mvccVersion=");
		sb.append(getMvccVersion());
		sb.append(", userIdMapperId=");
		sb.append(getUserIdMapperId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", externalUserId=");
		sb.append(getExternalUserId());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(22);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.UserIdMapper");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>mvccVersion</column-name><column-value><![CDATA[");
		sb.append(getMvccVersion());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userIdMapperId</column-name><column-value><![CDATA[");
		sb.append(getUserIdMapperId());
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
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>externalUserId</column-name><column-value><![CDATA[");
		sb.append(getExternalUserId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = UserIdMapper.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			UserIdMapper.class
		};
	private long _mvccVersion;
	private long _userIdMapperId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _type;
	private String _originalType;
	private String _description;
	private String _externalUserId;
	private String _originalExternalUserId;
	private long _columnBitmask;
	private UserIdMapper _escapedModel;
}