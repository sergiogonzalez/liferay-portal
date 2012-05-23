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

package com.liferay.portlet.dynamicdatalists.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSetModel;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSetSoap;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The base model implementation for the DDLRecordSet service. Represents a row in the &quot;DDLRecordSet&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.dynamicdatalists.model.DDLRecordSetModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DDLRecordSetImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetImpl
 * @see com.liferay.portlet.dynamicdatalists.model.DDLRecordSet
 * @see com.liferay.portlet.dynamicdatalists.model.DDLRecordSetModel
 * @generated
 */
@JSON(strict = true)
public class DDLRecordSetModelImpl extends BaseModelImpl<DDLRecordSet>
	implements DDLRecordSetModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a d d l record set model instance should use the {@link com.liferay.portlet.dynamicdatalists.model.DDLRecordSet} interface instead.
	 */
	public static final String TABLE_NAME = "DDLRecordSet";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "recordSetId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "DDMStructureId", Types.BIGINT },
			{ "recordSetKey", Types.VARCHAR },
			{ "name", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "minDisplayRows", Types.INTEGER },
			{ "scope", Types.INTEGER }
		};
	public static final String TABLE_SQL_CREATE = "create table DDLRecordSet (uuid_ VARCHAR(75) null,recordSetId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,DDMStructureId LONG,recordSetKey VARCHAR(75) null,name STRING null,description STRING null,minDisplayRows INTEGER,scope INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table DDLRecordSet";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.dynamicdatalists.model.DDLRecordSet"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.dynamicdatalists.model.DDLRecordSet"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portlet.dynamicdatalists.model.DDLRecordSet"),
			true);
	public static long COMPANYID_COLUMN_BITMASK = 1L;
	public static long GROUPID_COLUMN_BITMASK = 2L;
	public static long RECORDSETKEY_COLUMN_BITMASK = 4L;
	public static long UUID_COLUMN_BITMASK = 8L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static DDLRecordSet toModel(DDLRecordSetSoap soapModel) {
		DDLRecordSet model = new DDLRecordSetImpl();

		model.setUuid(soapModel.getUuid());
		model.setRecordSetId(soapModel.getRecordSetId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setDDMStructureId(soapModel.getDDMStructureId());
		model.setRecordSetKey(soapModel.getRecordSetKey());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setMinDisplayRows(soapModel.getMinDisplayRows());
		model.setScope(soapModel.getScope());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<DDLRecordSet> toModels(DDLRecordSetSoap[] soapModels) {
		List<DDLRecordSet> models = new ArrayList<DDLRecordSet>(soapModels.length);

		for (DDLRecordSetSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.dynamicdatalists.model.DDLRecordSet"));

	public DDLRecordSetModelImpl() {
	}

	public long getPrimaryKey() {
		return _recordSetId;
	}

	public void setPrimaryKey(long primaryKey) {
		setRecordSetId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_recordSetId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return DDLRecordSet.class;
	}

	public String getModelClassName() {
		return DDLRecordSet.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("recordSetId", getRecordSetId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("DDMStructureId", getDDMStructureId());
		attributes.put("recordSetKey", getRecordSetKey());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("minDisplayRows", getMinDisplayRows());
		attributes.put("scope", getScope());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long recordSetId = (Long)attributes.get("recordSetId");

		if (recordSetId != null) {
			setRecordSetId(recordSetId);
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

		Long DDMStructureId = (Long)attributes.get("DDMStructureId");

		if (DDMStructureId != null) {
			setDDMStructureId(DDMStructureId);
		}

		String recordSetKey = (String)attributes.get("recordSetKey");

		if (recordSetKey != null) {
			setRecordSetKey(recordSetKey);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Integer minDisplayRows = (Integer)attributes.get("minDisplayRows");

		if (minDisplayRows != null) {
			setMinDisplayRows(minDisplayRows);
		}

		Integer scope = (Integer)attributes.get("scope");

		if (scope != null) {
			setScope(scope);
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
	public long getRecordSetId() {
		return _recordSetId;
	}

	public void setRecordSetId(long recordSetId) {
		_recordSetId = recordSetId;
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
	public long getDDMStructureId() {
		return _DDMStructureId;
	}

	public void setDDMStructureId(long DDMStructureId) {
		_DDMStructureId = DDMStructureId;
	}

	@JSON
	public String getRecordSetKey() {
		if (_recordSetKey == null) {
			return StringPool.BLANK;
		}
		else {
			return _recordSetKey;
		}
	}

	public void setRecordSetKey(String recordSetKey) {
		_columnBitmask |= RECORDSETKEY_COLUMN_BITMASK;

		if (_originalRecordSetKey == null) {
			_originalRecordSetKey = _recordSetKey;
		}

		_recordSetKey = recordSetKey;
	}

	public String getOriginalRecordSetKey() {
		return GetterUtil.getString(_originalRecordSetKey);
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

	public String getName(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getName(languageId);
	}

	public String getName(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getName(languageId, useDefault);
	}

	public String getName(String languageId) {
		return LocalizationUtil.getLocalization(getName(), languageId);
	}

	public String getName(String languageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(getName(), languageId,
			useDefault);
	}

	public String getNameCurrentLanguageId() {
		return _nameCurrentLanguageId;
	}

	@JSON
	public String getNameCurrentValue() {
		Locale locale = getLocale(_nameCurrentLanguageId);

		return getName(locale);
	}

	public Map<Locale, String> getNameMap() {
		return LocalizationUtil.getLocalizationMap(getName());
	}

	public void setName(String name) {
		_name = name;
	}

	public void setName(String name, Locale locale) {
		setName(name, locale, LocaleUtil.getDefault());
	}

	public void setName(String name, Locale locale, Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(name)) {
			setName(LocalizationUtil.updateLocalization(getName(), "Name",
					name, languageId, defaultLanguageId));
		}
		else {
			setName(LocalizationUtil.removeLocalization(getName(), "Name",
					languageId));
		}
	}

	public void setNameCurrentLanguageId(String languageId) {
		_nameCurrentLanguageId = languageId;
	}

	public void setNameMap(Map<Locale, String> nameMap) {
		setNameMap(nameMap, LocaleUtil.getDefault());
	}

	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale) {
		if (nameMap == null) {
			return;
		}

		setName(LocalizationUtil.updateLocalization(nameMap, getName(), "Name",
				LocaleUtil.toLanguageId(defaultLocale)));
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

	public String getDescription(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getDescription(languageId);
	}

	public String getDescription(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getDescription(languageId, useDefault);
	}

	public String getDescription(String languageId) {
		return LocalizationUtil.getLocalization(getDescription(), languageId);
	}

	public String getDescription(String languageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(getDescription(), languageId,
			useDefault);
	}

	public String getDescriptionCurrentLanguageId() {
		return _descriptionCurrentLanguageId;
	}

	@JSON
	public String getDescriptionCurrentValue() {
		Locale locale = getLocale(_descriptionCurrentLanguageId);

		return getDescription(locale);
	}

	public Map<Locale, String> getDescriptionMap() {
		return LocalizationUtil.getLocalizationMap(getDescription());
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDescription(String description, Locale locale) {
		setDescription(description, locale, LocaleUtil.getDefault());
	}

	public void setDescription(String description, Locale locale,
		Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(description)) {
			setDescription(LocalizationUtil.updateLocalization(
					getDescription(), "Description", description, languageId,
					defaultLanguageId));
		}
		else {
			setDescription(LocalizationUtil.removeLocalization(
					getDescription(), "Description", languageId));
		}
	}

	public void setDescriptionCurrentLanguageId(String languageId) {
		_descriptionCurrentLanguageId = languageId;
	}

	public void setDescriptionMap(Map<Locale, String> descriptionMap) {
		setDescriptionMap(descriptionMap, LocaleUtil.getDefault());
	}

	public void setDescriptionMap(Map<Locale, String> descriptionMap,
		Locale defaultLocale) {
		if (descriptionMap == null) {
			return;
		}

		setDescription(LocalizationUtil.updateLocalization(descriptionMap,
				getDescription(), "Description",
				LocaleUtil.toLanguageId(defaultLocale)));
	}

	@JSON
	public int getMinDisplayRows() {
		return _minDisplayRows;
	}

	public void setMinDisplayRows(int minDisplayRows) {
		_minDisplayRows = minDisplayRows;
	}

	@JSON
	public int getScope() {
		return _scope;
	}

	public void setScope(int scope) {
		_scope = scope;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public DDLRecordSet toEscapedModel() {
		if (_escapedModelProxy == null) {
			_escapedModelProxy = (DDLRecordSet)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelProxyInterfaces,
					new AutoEscapeBeanHandler(this));
		}

		return _escapedModelProxy;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			DDLRecordSet.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		DDLRecordSetImpl ddlRecordSetImpl = new DDLRecordSetImpl();

		ddlRecordSetImpl.setUuid(getUuid());
		ddlRecordSetImpl.setRecordSetId(getRecordSetId());
		ddlRecordSetImpl.setGroupId(getGroupId());
		ddlRecordSetImpl.setCompanyId(getCompanyId());
		ddlRecordSetImpl.setUserId(getUserId());
		ddlRecordSetImpl.setUserName(getUserName());
		ddlRecordSetImpl.setCreateDate(getCreateDate());
		ddlRecordSetImpl.setModifiedDate(getModifiedDate());
		ddlRecordSetImpl.setDDMStructureId(getDDMStructureId());
		ddlRecordSetImpl.setRecordSetKey(getRecordSetKey());
		ddlRecordSetImpl.setName(getName());
		ddlRecordSetImpl.setDescription(getDescription());
		ddlRecordSetImpl.setMinDisplayRows(getMinDisplayRows());
		ddlRecordSetImpl.setScope(getScope());

		ddlRecordSetImpl.resetOriginalValues();

		return ddlRecordSetImpl;
	}

	public int compareTo(DDLRecordSet ddlRecordSet) {
		long primaryKey = ddlRecordSet.getPrimaryKey();

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

		DDLRecordSet ddlRecordSet = null;

		try {
			ddlRecordSet = (DDLRecordSet)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = ddlRecordSet.getPrimaryKey();

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
		DDLRecordSetModelImpl ddlRecordSetModelImpl = this;

		ddlRecordSetModelImpl._originalUuid = ddlRecordSetModelImpl._uuid;

		ddlRecordSetModelImpl._originalGroupId = ddlRecordSetModelImpl._groupId;

		ddlRecordSetModelImpl._setOriginalGroupId = false;

		ddlRecordSetModelImpl._originalCompanyId = ddlRecordSetModelImpl._companyId;

		ddlRecordSetModelImpl._setOriginalCompanyId = false;

		ddlRecordSetModelImpl._originalRecordSetKey = ddlRecordSetModelImpl._recordSetKey;

		ddlRecordSetModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<DDLRecordSet> toCacheModel() {
		DDLRecordSetCacheModel ddlRecordSetCacheModel = new DDLRecordSetCacheModel();

		ddlRecordSetCacheModel.uuid = getUuid();

		String uuid = ddlRecordSetCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			ddlRecordSetCacheModel.uuid = null;
		}

		ddlRecordSetCacheModel.recordSetId = getRecordSetId();

		ddlRecordSetCacheModel.groupId = getGroupId();

		ddlRecordSetCacheModel.companyId = getCompanyId();

		ddlRecordSetCacheModel.userId = getUserId();

		ddlRecordSetCacheModel.userName = getUserName();

		String userName = ddlRecordSetCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			ddlRecordSetCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			ddlRecordSetCacheModel.createDate = createDate.getTime();
		}
		else {
			ddlRecordSetCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			ddlRecordSetCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			ddlRecordSetCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		ddlRecordSetCacheModel.DDMStructureId = getDDMStructureId();

		ddlRecordSetCacheModel.recordSetKey = getRecordSetKey();

		String recordSetKey = ddlRecordSetCacheModel.recordSetKey;

		if ((recordSetKey != null) && (recordSetKey.length() == 0)) {
			ddlRecordSetCacheModel.recordSetKey = null;
		}

		ddlRecordSetCacheModel.name = getName();

		String name = ddlRecordSetCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			ddlRecordSetCacheModel.name = null;
		}

		ddlRecordSetCacheModel.description = getDescription();

		String description = ddlRecordSetCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			ddlRecordSetCacheModel.description = null;
		}

		ddlRecordSetCacheModel.minDisplayRows = getMinDisplayRows();

		ddlRecordSetCacheModel.scope = getScope();

		return ddlRecordSetCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", recordSetId=");
		sb.append(getRecordSetId());
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
		sb.append(", DDMStructureId=");
		sb.append(getDDMStructureId());
		sb.append(", recordSetKey=");
		sb.append(getRecordSetKey());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", minDisplayRows=");
		sb.append(getMinDisplayRows());
		sb.append(", scope=");
		sb.append(getScope());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(46);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.dynamicdatalists.model.DDLRecordSet");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>recordSetId</column-name><column-value><![CDATA[");
		sb.append(getRecordSetId());
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
			"<column><column-name>DDMStructureId</column-name><column-value><![CDATA[");
		sb.append(getDDMStructureId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>recordSetKey</column-name><column-value><![CDATA[");
		sb.append(getRecordSetKey());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>minDisplayRows</column-name><column-value><![CDATA[");
		sb.append(getMinDisplayRows());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>scope</column-name><column-value><![CDATA[");
		sb.append(getScope());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = DDLRecordSet.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			DDLRecordSet.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _recordSetId;
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
	private long _DDMStructureId;
	private String _recordSetKey;
	private String _originalRecordSetKey;
	private String _name;
	private String _nameCurrentLanguageId;
	private String _description;
	private String _descriptionCurrentLanguageId;
	private int _minDisplayRows;
	private int _scope;
	private long _columnBitmask;
	private DDLRecordSet _escapedModelProxy;
}