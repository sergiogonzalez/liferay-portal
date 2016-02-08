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

package com.liferay.portal.lock.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lock.model.Lock;
import com.liferay.portal.lock.model.LockModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the Lock service. Represents a row in the &quot;Lock_&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link LockModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link LockImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LockImpl
 * @see Lock
 * @see LockModel
 * @generated
 */
@ProviderType
public class LockModelImpl extends BaseModelImpl<Lock> implements LockModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a lock model instance should use the {@link Lock} interface instead.
	 */
	public static final String TABLE_NAME = "Lock_";
	public static final Object[][] TABLE_COLUMNS = {
			{ "mvccVersion", Types.BIGINT },
			{ "uuid_", Types.VARCHAR },
			{ "lockId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "className", Types.VARCHAR },
			{ "key_", Types.VARCHAR },
			{ "owner", Types.VARCHAR },
			{ "inheritable", Types.BOOLEAN },
			{ "expirationDate", Types.TIMESTAMP }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("lockId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("className", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("key_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("owner", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("inheritable", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("expirationDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE = "create table Lock_ (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,lockId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,className VARCHAR(75) null,key_ VARCHAR(200) null,owner VARCHAR(1024) null,inheritable BOOLEAN,expirationDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table Lock_";
	public static final String ORDER_BY_JPQL = " ORDER BY lock.lockId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY Lock_.lockId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.lock.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.portal.lock.model.Lock"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.lock.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.portal.lock.model.Lock"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.lock.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.lock.model.Lock"),
			true);
	public static final long CLASSNAME_COLUMN_BITMASK = 1L;
	public static final long COMPANYID_COLUMN_BITMASK = 2L;
	public static final long EXPIRATIONDATE_COLUMN_BITMASK = 4L;
	public static final long KEY_COLUMN_BITMASK = 8L;
	public static final long UUID_COLUMN_BITMASK = 16L;
	public static final long LOCKID_COLUMN_BITMASK = 32L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.lock.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.portal.lock.model.Lock"));

	public LockModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _lockId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setLockId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _lockId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return Lock.class;
	}

	@Override
	public String getModelClassName() {
		return Lock.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("lockId", getLockId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("className", getClassName());
		attributes.put("key", getKey());
		attributes.put("owner", getOwner());
		attributes.put("inheritable", getInheritable());
		attributes.put("expirationDate", getExpirationDate());

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

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long lockId = (Long)attributes.get("lockId");

		if (lockId != null) {
			setLockId(lockId);
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

		String className = (String)attributes.get("className");

		if (className != null) {
			setClassName(className);
		}

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		String owner = (String)attributes.get("owner");

		if (owner != null) {
			setOwner(owner);
		}

		Boolean inheritable = (Boolean)attributes.get("inheritable");

		if (inheritable != null) {
			setInheritable(inheritable);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
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
	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@Override
	public long getLockId() {
		return _lockId;
	}

	@Override
	public void setLockId(long lockId) {
		_lockId = lockId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
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

	@Override
	public String getUserName() {
		if (_userName == null) {
			return StringPool.BLANK;
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public String getClassName() {
		if (_className == null) {
			return StringPool.BLANK;
		}
		else {
			return _className;
		}
	}

	@Override
	public void setClassName(String className) {
		_columnBitmask |= CLASSNAME_COLUMN_BITMASK;

		if (_originalClassName == null) {
			_originalClassName = _className;
		}

		_className = className;
	}

	public String getOriginalClassName() {
		return GetterUtil.getString(_originalClassName);
	}

	@Override
	public String getKey() {
		if (_key == null) {
			return StringPool.BLANK;
		}
		else {
			return _key;
		}
	}

	@Override
	public void setKey(String key) {
		_columnBitmask |= KEY_COLUMN_BITMASK;

		if (_originalKey == null) {
			_originalKey = _key;
		}

		_key = key;
	}

	public String getOriginalKey() {
		return GetterUtil.getString(_originalKey);
	}

	@Override
	public String getOwner() {
		if (_owner == null) {
			return StringPool.BLANK;
		}
		else {
			return _owner;
		}
	}

	@Override
	public void setOwner(String owner) {
		_owner = owner;
	}

	@Override
	public boolean getInheritable() {
		return _inheritable;
	}

	@Override
	public boolean isInheritable() {
		return _inheritable;
	}

	@Override
	public void setInheritable(boolean inheritable) {
		_inheritable = inheritable;
	}

	@Override
	public Date getExpirationDate() {
		return _expirationDate;
	}

	@Override
	public void setExpirationDate(Date expirationDate) {
		_columnBitmask |= EXPIRATIONDATE_COLUMN_BITMASK;

		if (_originalExpirationDate == null) {
			_originalExpirationDate = _expirationDate;
		}

		_expirationDate = expirationDate;
	}

	public Date getOriginalExpirationDate() {
		return _originalExpirationDate;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			Lock.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Lock toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (Lock)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		LockImpl lockImpl = new LockImpl();

		lockImpl.setMvccVersion(getMvccVersion());
		lockImpl.setUuid(getUuid());
		lockImpl.setLockId(getLockId());
		lockImpl.setCompanyId(getCompanyId());
		lockImpl.setUserId(getUserId());
		lockImpl.setUserName(getUserName());
		lockImpl.setCreateDate(getCreateDate());
		lockImpl.setClassName(getClassName());
		lockImpl.setKey(getKey());
		lockImpl.setOwner(getOwner());
		lockImpl.setInheritable(getInheritable());
		lockImpl.setExpirationDate(getExpirationDate());

		lockImpl.resetOriginalValues();

		return lockImpl;
	}

	@Override
	public int compareTo(Lock lock) {
		long primaryKey = lock.getPrimaryKey();

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

		if (!(obj instanceof Lock)) {
			return false;
		}

		Lock lock = (Lock)obj;

		long primaryKey = lock.getPrimaryKey();

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
		LockModelImpl lockModelImpl = this;

		lockModelImpl._originalUuid = lockModelImpl._uuid;

		lockModelImpl._originalCompanyId = lockModelImpl._companyId;

		lockModelImpl._setOriginalCompanyId = false;

		lockModelImpl._originalClassName = lockModelImpl._className;

		lockModelImpl._originalKey = lockModelImpl._key;

		lockModelImpl._originalExpirationDate = lockModelImpl._expirationDate;

		lockModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Lock> toCacheModel() {
		LockCacheModel lockCacheModel = new LockCacheModel();

		lockCacheModel.mvccVersion = getMvccVersion();

		lockCacheModel.uuid = getUuid();

		String uuid = lockCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			lockCacheModel.uuid = null;
		}

		lockCacheModel.lockId = getLockId();

		lockCacheModel.companyId = getCompanyId();

		lockCacheModel.userId = getUserId();

		lockCacheModel.userName = getUserName();

		String userName = lockCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			lockCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			lockCacheModel.createDate = createDate.getTime();
		}
		else {
			lockCacheModel.createDate = Long.MIN_VALUE;
		}

		lockCacheModel.className = getClassName();

		String className = lockCacheModel.className;

		if ((className != null) && (className.length() == 0)) {
			lockCacheModel.className = null;
		}

		lockCacheModel.key = getKey();

		String key = lockCacheModel.key;

		if ((key != null) && (key.length() == 0)) {
			lockCacheModel.key = null;
		}

		lockCacheModel.owner = getOwner();

		String owner = lockCacheModel.owner;

		if ((owner != null) && (owner.length() == 0)) {
			lockCacheModel.owner = null;
		}

		lockCacheModel.inheritable = getInheritable();

		Date expirationDate = getExpirationDate();

		if (expirationDate != null) {
			lockCacheModel.expirationDate = expirationDate.getTime();
		}
		else {
			lockCacheModel.expirationDate = Long.MIN_VALUE;
		}

		return lockCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(getMvccVersion());
		sb.append(", uuid=");
		sb.append(getUuid());
		sb.append(", lockId=");
		sb.append(getLockId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", className=");
		sb.append(getClassName());
		sb.append(", key=");
		sb.append(getKey());
		sb.append(", owner=");
		sb.append(getOwner());
		sb.append(", inheritable=");
		sb.append(getInheritable());
		sb.append(", expirationDate=");
		sb.append(getExpirationDate());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(40);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.lock.model.Lock");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>mvccVersion</column-name><column-value><![CDATA[");
		sb.append(getMvccVersion());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lockId</column-name><column-value><![CDATA[");
		sb.append(getLockId());
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
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>className</column-name><column-value><![CDATA[");
		sb.append(getClassName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>key</column-name><column-value><![CDATA[");
		sb.append(getKey());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>owner</column-name><column-value><![CDATA[");
		sb.append(getOwner());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>inheritable</column-name><column-value><![CDATA[");
		sb.append(getInheritable());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>expirationDate</column-name><column-value><![CDATA[");
		sb.append(getExpirationDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = Lock.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			Lock.class
		};
	private long _mvccVersion;
	private String _uuid;
	private String _originalUuid;
	private long _lockId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private String _className;
	private String _originalClassName;
	private String _key;
	private String _originalKey;
	private String _owner;
	private boolean _inheritable;
	private Date _expirationDate;
	private Date _originalExpirationDate;
	private long _columnBitmask;
	private Lock _escapedModel;
}