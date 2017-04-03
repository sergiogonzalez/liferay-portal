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

package com.liferay.dynamic.data.lists.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordModel;
import com.liferay.dynamic.data.lists.model.DDLRecordSoap;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the DDLRecord service. Represents a row in the &quot;DDLRecord&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link DDLRecordModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DDLRecordImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordImpl
 * @see DDLRecord
 * @see DDLRecordModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class DDLRecordModelImpl extends BaseModelImpl<DDLRecord>
	implements DDLRecordModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a ddl record model instance should use the {@link DDLRecord} interface instead.
	 */
	public static final String TABLE_NAME = "DDLRecord";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "recordId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "versionUserId", Types.BIGINT },
			{ "versionUserName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "DDMStorageId", Types.BIGINT },
			{ "recordSetId", Types.BIGINT },
			{ "recordSetVersion", Types.VARCHAR },
			{ "version", Types.VARCHAR },
			{ "displayIndex", Types.INTEGER },
			{ "lastPublishDate", Types.TIMESTAMP }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("recordId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("versionUserId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("versionUserName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("DDMStorageId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("recordSetId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("recordSetVersion", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("displayIndex", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE = "create table DDLRecord (uuid_ VARCHAR(75) null,recordId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,versionUserId LONG,versionUserName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,DDMStorageId LONG,recordSetId LONG,recordSetVersion VARCHAR(75) null,version VARCHAR(75) null,displayIndex INTEGER,lastPublishDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table DDLRecord";
	public static final String ORDER_BY_JPQL = " ORDER BY ddlRecord.recordId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY DDLRecord.recordId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.dynamic.data.lists.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.dynamic.data.lists.model.DDLRecord"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.dynamic.data.lists.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.dynamic.data.lists.model.DDLRecord"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.dynamic.data.lists.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.dynamic.data.lists.model.DDLRecord"),
			true);
	public static final long COMPANYID_COLUMN_BITMASK = 1L;
	public static final long GROUPID_COLUMN_BITMASK = 2L;
	public static final long RECORDSETID_COLUMN_BITMASK = 4L;
	public static final long RECORDSETVERSION_COLUMN_BITMASK = 8L;
	public static final long USERID_COLUMN_BITMASK = 16L;
	public static final long UUID_COLUMN_BITMASK = 32L;
	public static final long RECORDID_COLUMN_BITMASK = 64L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static DDLRecord toModel(DDLRecordSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		DDLRecord model = new DDLRecordImpl();

		model.setUuid(soapModel.getUuid());
		model.setRecordId(soapModel.getRecordId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setVersionUserId(soapModel.getVersionUserId());
		model.setVersionUserName(soapModel.getVersionUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setDDMStorageId(soapModel.getDDMStorageId());
		model.setRecordSetId(soapModel.getRecordSetId());
		model.setRecordSetVersion(soapModel.getRecordSetVersion());
		model.setVersion(soapModel.getVersion());
		model.setDisplayIndex(soapModel.getDisplayIndex());
		model.setLastPublishDate(soapModel.getLastPublishDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<DDLRecord> toModels(DDLRecordSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<DDLRecord> models = new ArrayList<DDLRecord>(soapModels.length);

		for (DDLRecordSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.dynamic.data.lists.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.dynamic.data.lists.model.DDLRecord"));

	public DDLRecordModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _recordId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setRecordId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _recordId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return DDLRecord.class;
	}

	@Override
	public String getModelClassName() {
		return DDLRecord.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("recordId", getRecordId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("versionUserId", getVersionUserId());
		attributes.put("versionUserName", getVersionUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("DDMStorageId", getDDMStorageId());
		attributes.put("recordSetId", getRecordSetId());
		attributes.put("recordSetVersion", getRecordSetVersion());
		attributes.put("version", getVersion());
		attributes.put("displayIndex", getDisplayIndex());
		attributes.put("lastPublishDate", getLastPublishDate());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long recordId = (Long)attributes.get("recordId");

		if (recordId != null) {
			setRecordId(recordId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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

		Long versionUserId = (Long)attributes.get("versionUserId");

		if (versionUserId != null) {
			setVersionUserId(versionUserId);
		}

		String versionUserName = (String)attributes.get("versionUserName");

		if (versionUserName != null) {
			setVersionUserName(versionUserName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long DDMStorageId = (Long)attributes.get("DDMStorageId");

		if (DDMStorageId != null) {
			setDDMStorageId(DDMStorageId);
		}

		Long recordSetId = (Long)attributes.get("recordSetId");

		if (recordSetId != null) {
			setRecordSetId(recordSetId);
		}

		String recordSetVersion = (String)attributes.get("recordSetVersion");

		if (recordSetVersion != null) {
			setRecordSetVersion(recordSetVersion);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Integer displayIndex = (Integer)attributes.get("displayIndex");

		if (displayIndex != null) {
			setDisplayIndex(displayIndex);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@JSON
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

	@JSON
	@Override
	public long getRecordId() {
		return _recordId;
	}

	@Override
	public void setRecordId(long recordId) {
		_recordId = recordId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@JSON
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

	@JSON
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

	@JSON
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

	@JSON
	@Override
	public long getVersionUserId() {
		return _versionUserId;
	}

	@Override
	public void setVersionUserId(long versionUserId) {
		_versionUserId = versionUserId;
	}

	@Override
	public String getVersionUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getVersionUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}
	}

	@Override
	public void setVersionUserUuid(String versionUserUuid) {
	}

	@JSON
	@Override
	public String getVersionUserName() {
		if (_versionUserName == null) {
			return StringPool.BLANK;
		}
		else {
			return _versionUserName;
		}
	}

	@Override
	public void setVersionUserName(String versionUserName) {
		_versionUserName = versionUserName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public long getDDMStorageId() {
		return _DDMStorageId;
	}

	@Override
	public void setDDMStorageId(long DDMStorageId) {
		_DDMStorageId = DDMStorageId;
	}

	@JSON
	@Override
	public long getRecordSetId() {
		return _recordSetId;
	}

	@Override
	public void setRecordSetId(long recordSetId) {
		_columnBitmask |= RECORDSETID_COLUMN_BITMASK;

		if (!_setOriginalRecordSetId) {
			_setOriginalRecordSetId = true;

			_originalRecordSetId = _recordSetId;
		}

		_recordSetId = recordSetId;
	}

	public long getOriginalRecordSetId() {
		return _originalRecordSetId;
	}

	@JSON
	@Override
	public String getRecordSetVersion() {
		if (_recordSetVersion == null) {
			return StringPool.BLANK;
		}
		else {
			return _recordSetVersion;
		}
	}

	@Override
	public void setRecordSetVersion(String recordSetVersion) {
		_columnBitmask |= RECORDSETVERSION_COLUMN_BITMASK;

		if (_originalRecordSetVersion == null) {
			_originalRecordSetVersion = _recordSetVersion;
		}

		_recordSetVersion = recordSetVersion;
	}

	public String getOriginalRecordSetVersion() {
		return GetterUtil.getString(_originalRecordSetVersion);
	}

	@JSON
	@Override
	public String getVersion() {
		if (_version == null) {
			return StringPool.BLANK;
		}
		else {
			return _version;
		}
	}

	@Override
	public void setVersion(String version) {
		_version = version;
	}

	@JSON
	@Override
	public int getDisplayIndex() {
		return _displayIndex;
	}

	@Override
	public void setDisplayIndex(int displayIndex) {
		_displayIndex = displayIndex;
	}

	@JSON
	@Override
	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				DDLRecord.class.getName()));
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			DDLRecord.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public DDLRecord toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (DDLRecord)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		DDLRecordImpl ddlRecordImpl = new DDLRecordImpl();

		ddlRecordImpl.setUuid(getUuid());
		ddlRecordImpl.setRecordId(getRecordId());
		ddlRecordImpl.setGroupId(getGroupId());
		ddlRecordImpl.setCompanyId(getCompanyId());
		ddlRecordImpl.setUserId(getUserId());
		ddlRecordImpl.setUserName(getUserName());
		ddlRecordImpl.setVersionUserId(getVersionUserId());
		ddlRecordImpl.setVersionUserName(getVersionUserName());
		ddlRecordImpl.setCreateDate(getCreateDate());
		ddlRecordImpl.setModifiedDate(getModifiedDate());
		ddlRecordImpl.setDDMStorageId(getDDMStorageId());
		ddlRecordImpl.setRecordSetId(getRecordSetId());
		ddlRecordImpl.setRecordSetVersion(getRecordSetVersion());
		ddlRecordImpl.setVersion(getVersion());
		ddlRecordImpl.setDisplayIndex(getDisplayIndex());
		ddlRecordImpl.setLastPublishDate(getLastPublishDate());

		ddlRecordImpl.resetOriginalValues();

		return ddlRecordImpl;
	}

	@Override
	public int compareTo(DDLRecord ddlRecord) {
		long primaryKey = ddlRecord.getPrimaryKey();

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

		if (!(obj instanceof DDLRecord)) {
			return false;
		}

		DDLRecord ddlRecord = (DDLRecord)obj;

		long primaryKey = ddlRecord.getPrimaryKey();

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
		DDLRecordModelImpl ddlRecordModelImpl = this;

		ddlRecordModelImpl._originalUuid = ddlRecordModelImpl._uuid;

		ddlRecordModelImpl._originalGroupId = ddlRecordModelImpl._groupId;

		ddlRecordModelImpl._setOriginalGroupId = false;

		ddlRecordModelImpl._originalCompanyId = ddlRecordModelImpl._companyId;

		ddlRecordModelImpl._setOriginalCompanyId = false;

		ddlRecordModelImpl._originalUserId = ddlRecordModelImpl._userId;

		ddlRecordModelImpl._setOriginalUserId = false;

		ddlRecordModelImpl._setModifiedDate = false;

		ddlRecordModelImpl._originalRecordSetId = ddlRecordModelImpl._recordSetId;

		ddlRecordModelImpl._setOriginalRecordSetId = false;

		ddlRecordModelImpl._originalRecordSetVersion = ddlRecordModelImpl._recordSetVersion;

		ddlRecordModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<DDLRecord> toCacheModel() {
		DDLRecordCacheModel ddlRecordCacheModel = new DDLRecordCacheModel();

		ddlRecordCacheModel.uuid = getUuid();

		String uuid = ddlRecordCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			ddlRecordCacheModel.uuid = null;
		}

		ddlRecordCacheModel.recordId = getRecordId();

		ddlRecordCacheModel.groupId = getGroupId();

		ddlRecordCacheModel.companyId = getCompanyId();

		ddlRecordCacheModel.userId = getUserId();

		ddlRecordCacheModel.userName = getUserName();

		String userName = ddlRecordCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			ddlRecordCacheModel.userName = null;
		}

		ddlRecordCacheModel.versionUserId = getVersionUserId();

		ddlRecordCacheModel.versionUserName = getVersionUserName();

		String versionUserName = ddlRecordCacheModel.versionUserName;

		if ((versionUserName != null) && (versionUserName.length() == 0)) {
			ddlRecordCacheModel.versionUserName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			ddlRecordCacheModel.createDate = createDate.getTime();
		}
		else {
			ddlRecordCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			ddlRecordCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			ddlRecordCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		ddlRecordCacheModel.DDMStorageId = getDDMStorageId();

		ddlRecordCacheModel.recordSetId = getRecordSetId();

		ddlRecordCacheModel.recordSetVersion = getRecordSetVersion();

		String recordSetVersion = ddlRecordCacheModel.recordSetVersion;

		if ((recordSetVersion != null) && (recordSetVersion.length() == 0)) {
			ddlRecordCacheModel.recordSetVersion = null;
		}

		ddlRecordCacheModel.version = getVersion();

		String version = ddlRecordCacheModel.version;

		if ((version != null) && (version.length() == 0)) {
			ddlRecordCacheModel.version = null;
		}

		ddlRecordCacheModel.displayIndex = getDisplayIndex();

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			ddlRecordCacheModel.lastPublishDate = lastPublishDate.getTime();
		}
		else {
			ddlRecordCacheModel.lastPublishDate = Long.MIN_VALUE;
		}

		return ddlRecordCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", recordId=");
		sb.append(getRecordId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", versionUserId=");
		sb.append(getVersionUserId());
		sb.append(", versionUserName=");
		sb.append(getVersionUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", DDMStorageId=");
		sb.append(getDDMStorageId());
		sb.append(", recordSetId=");
		sb.append(getRecordSetId());
		sb.append(", recordSetVersion=");
		sb.append(getRecordSetVersion());
		sb.append(", version=");
		sb.append(getVersion());
		sb.append(", displayIndex=");
		sb.append(getDisplayIndex());
		sb.append(", lastPublishDate=");
		sb.append(getLastPublishDate());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(52);

		sb.append("<model><model-name>");
		sb.append("com.liferay.dynamic.data.lists.model.DDLRecord");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>recordId</column-name><column-value><![CDATA[");
		sb.append(getRecordId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
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
			"<column><column-name>versionUserId</column-name><column-value><![CDATA[");
		sb.append(getVersionUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>versionUserName</column-name><column-value><![CDATA[");
		sb.append(getVersionUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>DDMStorageId</column-name><column-value><![CDATA[");
		sb.append(getDDMStorageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>recordSetId</column-name><column-value><![CDATA[");
		sb.append(getRecordSetId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>recordSetVersion</column-name><column-value><![CDATA[");
		sb.append(getRecordSetVersion());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>version</column-name><column-value><![CDATA[");
		sb.append(getVersion());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>displayIndex</column-name><column-value><![CDATA[");
		sb.append(getDisplayIndex());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastPublishDate</column-name><column-value><![CDATA[");
		sb.append(getLastPublishDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = DDLRecord.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			DDLRecord.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _recordId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private long _versionUserId;
	private String _versionUserName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _DDMStorageId;
	private long _recordSetId;
	private long _originalRecordSetId;
	private boolean _setOriginalRecordSetId;
	private String _recordSetVersion;
	private String _originalRecordSetVersion;
	private String _version;
	private int _displayIndex;
	private Date _lastPublishDate;
	private long _columnBitmask;
	private DDLRecord _escapedModel;
}