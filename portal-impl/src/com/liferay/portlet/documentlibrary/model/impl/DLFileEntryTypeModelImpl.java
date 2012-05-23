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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeModel;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeSoap;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the DLFileEntryType service. Represents a row in the &quot;DLFileEntryType&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.documentlibrary.model.DLFileEntryTypeModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DLFileEntryTypeImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryTypeImpl
 * @see com.liferay.portlet.documentlibrary.model.DLFileEntryType
 * @see com.liferay.portlet.documentlibrary.model.DLFileEntryTypeModel
 * @generated
 */
@JSON(strict = true)
public class DLFileEntryTypeModelImpl extends BaseModelImpl<DLFileEntryType>
	implements DLFileEntryTypeModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a document library file entry type model instance should use the {@link com.liferay.portlet.documentlibrary.model.DLFileEntryType} interface instead.
	 */
	public static final String TABLE_NAME = "DLFileEntryType";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "fileEntryTypeId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "name", Types.VARCHAR },
			{ "description", Types.VARCHAR }
		};
	public static final String TABLE_SQL_CREATE = "create table DLFileEntryType (uuid_ VARCHAR(75) null,fileEntryTypeId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,description STRING null)";
	public static final String TABLE_SQL_DROP = "drop table DLFileEntryType";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.documentlibrary.model.DLFileEntryType"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.documentlibrary.model.DLFileEntryType"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portlet.documentlibrary.model.DLFileEntryType"),
			true);
	public static long COMPANYID_COLUMN_BITMASK = 1L;
	public static long GROUPID_COLUMN_BITMASK = 2L;
	public static long NAME_COLUMN_BITMASK = 4L;
	public static long UUID_COLUMN_BITMASK = 8L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static DLFileEntryType toModel(DLFileEntryTypeSoap soapModel) {
		DLFileEntryType model = new DLFileEntryTypeImpl();

		model.setUuid(soapModel.getUuid());
		model.setFileEntryTypeId(soapModel.getFileEntryTypeId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<DLFileEntryType> toModels(
		DLFileEntryTypeSoap[] soapModels) {
		List<DLFileEntryType> models = new ArrayList<DLFileEntryType>(soapModels.length);

		for (DLFileEntryTypeSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final String MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME = "DLFileEntryTypes_DLFolders";
	public static final Object[][] MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_COLUMNS =
		{
			{ "fileEntryTypeId", Types.BIGINT },
			{ "folderId", Types.BIGINT }
		};
	public static final String MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_SQL_CREATE =
		"create table DLFileEntryTypes_DLFolders (fileEntryTypeId LONG not null,folderId LONG not null,primary key (fileEntryTypeId, folderId))";
	public static final boolean FINDER_CACHE_ENABLED_DLFILEENTRYTYPES_DLFOLDERS = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.DLFileEntryTypes_DLFolders"),
			true);
	public static final String MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_NAME =
		"DLFileEntryTypes_DDMStructures";
	public static final Object[][] MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_COLUMNS =
		{
			{ "fileEntryTypeId", Types.BIGINT },
			{ "structureId", Types.BIGINT }
		};
	public static final String MAPPING_TABLE_DLFILEENTRYTYPES_DDMSTRUCTURES_SQL_CREATE =
		"create table DLFileEntryTypes_DDMStructures (fileEntryTypeId LONG not null,structureId LONG not null,primary key (fileEntryTypeId, structureId))";
	public static final boolean FINDER_CACHE_ENABLED_DLFILEENTRYTYPES_DDMSTRUCTURES =
		GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.DLFileEntryTypes_DDMStructures"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.documentlibrary.model.DLFileEntryType"));

	public DLFileEntryTypeModelImpl() {
	}

	public long getPrimaryKey() {
		return _fileEntryTypeId;
	}

	public void setPrimaryKey(long primaryKey) {
		setFileEntryTypeId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_fileEntryTypeId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return DLFileEntryType.class;
	}

	public String getModelClassName() {
		return DLFileEntryType.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("fileEntryTypeId", getFileEntryTypeId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long fileEntryTypeId = (Long)attributes.get("fileEntryTypeId");

		if (fileEntryTypeId != null) {
			setFileEntryTypeId(fileEntryTypeId);
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

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}
	}

	@JSON
	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

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
	public long getFileEntryTypeId() {
		return _fileEntryTypeId;
	}

	public void setFileEntryTypeId(long fileEntryTypeId) {
		_fileEntryTypeId = fileEntryTypeId;
	}

	@JSON
	public long getGroupId() {
		return _groupId;
	}

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
	public long getCompanyId() {
		return _companyId;
	}

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

	@JSON
	public String getUserName() {
		if (_userName == null) {
			return StringPool.BLANK;
		}
		else {
			return _userName;
		}
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	@JSON
	public String getName() {
		if (_name == null) {
			return StringPool.BLANK;
		}
		else {
			return _name;
		}
	}

	public void setName(String name) {
		_columnBitmask |= NAME_COLUMN_BITMASK;

		if (_originalName == null) {
			_originalName = _name;
		}

		_name = name;
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
	}

	@JSON
	public String getDescription() {
		if (_description == null) {
			return StringPool.BLANK;
		}
		else {
			return _description;
		}
	}

	public void setDescription(String description) {
		_description = description;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public DLFileEntryType toEscapedModel() {
		if (_escapedModelProxy == null) {
			_escapedModelProxy = (DLFileEntryType)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelProxyInterfaces,
					new AutoEscapeBeanHandler(this));
		}

		return _escapedModelProxy;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			DLFileEntryType.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		DLFileEntryTypeImpl dlFileEntryTypeImpl = new DLFileEntryTypeImpl();

		dlFileEntryTypeImpl.setUuid(getUuid());
		dlFileEntryTypeImpl.setFileEntryTypeId(getFileEntryTypeId());
		dlFileEntryTypeImpl.setGroupId(getGroupId());
		dlFileEntryTypeImpl.setCompanyId(getCompanyId());
		dlFileEntryTypeImpl.setUserId(getUserId());
		dlFileEntryTypeImpl.setUserName(getUserName());
		dlFileEntryTypeImpl.setCreateDate(getCreateDate());
		dlFileEntryTypeImpl.setModifiedDate(getModifiedDate());
		dlFileEntryTypeImpl.setName(getName());
		dlFileEntryTypeImpl.setDescription(getDescription());

		dlFileEntryTypeImpl.resetOriginalValues();

		return dlFileEntryTypeImpl;
	}

	public int compareTo(DLFileEntryType dlFileEntryType) {
		long primaryKey = dlFileEntryType.getPrimaryKey();

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
		if (obj == null) {
			return false;
		}

		DLFileEntryType dlFileEntryType = null;

		try {
			dlFileEntryType = (DLFileEntryType)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = dlFileEntryType.getPrimaryKey();

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
		DLFileEntryTypeModelImpl dlFileEntryTypeModelImpl = this;

		dlFileEntryTypeModelImpl._originalUuid = dlFileEntryTypeModelImpl._uuid;

		dlFileEntryTypeModelImpl._originalGroupId = dlFileEntryTypeModelImpl._groupId;

		dlFileEntryTypeModelImpl._setOriginalGroupId = false;

		dlFileEntryTypeModelImpl._originalCompanyId = dlFileEntryTypeModelImpl._companyId;

		dlFileEntryTypeModelImpl._setOriginalCompanyId = false;

		dlFileEntryTypeModelImpl._originalName = dlFileEntryTypeModelImpl._name;

		dlFileEntryTypeModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<DLFileEntryType> toCacheModel() {
		DLFileEntryTypeCacheModel dlFileEntryTypeCacheModel = new DLFileEntryTypeCacheModel();

		dlFileEntryTypeCacheModel.uuid = getUuid();

		String uuid = dlFileEntryTypeCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			dlFileEntryTypeCacheModel.uuid = null;
		}

		dlFileEntryTypeCacheModel.fileEntryTypeId = getFileEntryTypeId();

		dlFileEntryTypeCacheModel.groupId = getGroupId();

		dlFileEntryTypeCacheModel.companyId = getCompanyId();

		dlFileEntryTypeCacheModel.userId = getUserId();

		dlFileEntryTypeCacheModel.userName = getUserName();

		String userName = dlFileEntryTypeCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			dlFileEntryTypeCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			dlFileEntryTypeCacheModel.createDate = createDate.getTime();
		}
		else {
			dlFileEntryTypeCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			dlFileEntryTypeCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			dlFileEntryTypeCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		dlFileEntryTypeCacheModel.name = getName();

		String name = dlFileEntryTypeCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			dlFileEntryTypeCacheModel.name = null;
		}

		dlFileEntryTypeCacheModel.description = getDescription();

		String description = dlFileEntryTypeCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			dlFileEntryTypeCacheModel.description = null;
		}

		return dlFileEntryTypeCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", fileEntryTypeId=");
		sb.append(getFileEntryTypeId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.documentlibrary.model.DLFileEntryType");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>fileEntryTypeId</column-name><column-value><![CDATA[");
		sb.append(getFileEntryTypeId());
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
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = DLFileEntryType.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			DLFileEntryType.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _fileEntryTypeId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _originalName;
	private String _description;
	private long _columnBitmask;
	private DLFileEntryType _escapedModelProxy;
}