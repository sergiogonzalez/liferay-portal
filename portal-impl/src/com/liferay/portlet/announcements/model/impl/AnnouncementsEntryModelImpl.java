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

package com.liferay.portlet.announcements.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.model.AnnouncementsEntryModel;
import com.liferay.announcements.kernel.model.AnnouncementsEntrySoap;

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
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the AnnouncementsEntry service. Represents a row in the &quot;AnnouncementsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link AnnouncementsEntryModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AnnouncementsEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsEntryImpl
 * @see AnnouncementsEntry
 * @see AnnouncementsEntryModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class AnnouncementsEntryModelImpl extends BaseModelImpl<AnnouncementsEntry>
	implements AnnouncementsEntryModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a announcements entry model instance should use the {@link AnnouncementsEntry} interface instead.
	 */
	public static final String TABLE_NAME = "AnnouncementsEntry";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "entryId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "classNameId", Types.BIGINT },
			{ "classPK", Types.BIGINT },
			{ "title", Types.VARCHAR },
			{ "content", Types.CLOB },
			{ "url", Types.VARCHAR },
			{ "type_", Types.VARCHAR },
			{ "displayDate", Types.TIMESTAMP },
			{ "expirationDate", Types.TIMESTAMP },
			{ "priority", Types.INTEGER },
			{ "alert", Types.BOOLEAN }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("entryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("content", Types.CLOB);
		TABLE_COLUMNS_MAP.put("url", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("displayDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("expirationDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("priority", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("alert", Types.BOOLEAN);
	}

	public static final String TABLE_SQL_CREATE = "create table AnnouncementsEntry (uuid_ VARCHAR(75) null,entryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,title VARCHAR(75) null,content TEXT null,url STRING null,type_ VARCHAR(75) null,displayDate DATE null,expirationDate DATE null,priority INTEGER,alert BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table AnnouncementsEntry";
	public static final String ORDER_BY_JPQL = " ORDER BY announcementsEntry.priority ASC, announcementsEntry.modifiedDate ASC";
	public static final String ORDER_BY_SQL = " ORDER BY AnnouncementsEntry.priority ASC, AnnouncementsEntry.modifiedDate ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.announcements.kernel.model.AnnouncementsEntry"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.announcements.kernel.model.AnnouncementsEntry"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.announcements.kernel.model.AnnouncementsEntry"),
			true);
	public static final long ALERT_COLUMN_BITMASK = 1L;
	public static final long CLASSNAMEID_COLUMN_BITMASK = 2L;
	public static final long CLASSPK_COLUMN_BITMASK = 4L;
	public static final long COMPANYID_COLUMN_BITMASK = 8L;
	public static final long USERID_COLUMN_BITMASK = 16L;
	public static final long UUID_COLUMN_BITMASK = 32L;
	public static final long PRIORITY_COLUMN_BITMASK = 64L;
	public static final long MODIFIEDDATE_COLUMN_BITMASK = 128L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static AnnouncementsEntry toModel(AnnouncementsEntrySoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		AnnouncementsEntry model = new AnnouncementsEntryImpl();

		model.setUuid(soapModel.getUuid());
		model.setEntryId(soapModel.getEntryId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setTitle(soapModel.getTitle());
		model.setContent(soapModel.getContent());
		model.setUrl(soapModel.getUrl());
		model.setType(soapModel.getType());
		model.setDisplayDate(soapModel.getDisplayDate());
		model.setExpirationDate(soapModel.getExpirationDate());
		model.setPriority(soapModel.getPriority());
		model.setAlert(soapModel.isAlert());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<AnnouncementsEntry> toModels(
		AnnouncementsEntrySoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<AnnouncementsEntry> models = new ArrayList<AnnouncementsEntry>(soapModels.length);

		for (AnnouncementsEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.announcements.kernel.model.AnnouncementsEntry"));

	public AnnouncementsEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _entryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _entryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return AnnouncementsEntry.class;
	}

	@Override
	public String getModelClassName() {
		return AnnouncementsEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("entryId", getEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("title", getTitle());
		attributes.put("content", getContent());
		attributes.put("url", getUrl());
		attributes.put("type", getType());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("priority", getPriority());
		attributes.put("alert", isAlert());

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

		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Boolean alert = (Boolean)attributes.get("alert");

		if (alert != null) {
			setAlert(alert);
		}
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
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
	public long getEntryId() {
		return _entryId;
	}

	@Override
	public void setEntryId(long entryId) {
		_entryId = entryId;
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
			return "";
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
			return "";
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

		_columnBitmask = -1L;

		_modifiedDate = modifiedDate;
	}

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return "";
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@JSON
	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_columnBitmask |= CLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@JSON
	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		_columnBitmask |= CLASSPK_COLUMN_BITMASK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	@JSON
	@Override
	public String getTitle() {
		if (_title == null) {
			return "";
		}
		else {
			return _title;
		}
	}

	@Override
	public void setTitle(String title) {
		_title = title;
	}

	@JSON
	@Override
	public String getContent() {
		if (_content == null) {
			return "";
		}
		else {
			return _content;
		}
	}

	@Override
	public void setContent(String content) {
		_content = content;
	}

	@JSON
	@Override
	public String getUrl() {
		if (_url == null) {
			return "";
		}
		else {
			return _url;
		}
	}

	@Override
	public void setUrl(String url) {
		_url = url;
	}

	@JSON
	@Override
	public String getType() {
		if (_type == null) {
			return "";
		}
		else {
			return _type;
		}
	}

	@Override
	public void setType(String type) {
		_type = type;
	}

	@JSON
	@Override
	public Date getDisplayDate() {
		return _displayDate;
	}

	@Override
	public void setDisplayDate(Date displayDate) {
		_displayDate = displayDate;
	}

	@JSON
	@Override
	public Date getExpirationDate() {
		return _expirationDate;
	}

	@Override
	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	@JSON
	@Override
	public int getPriority() {
		return _priority;
	}

	@Override
	public void setPriority(int priority) {
		_columnBitmask = -1L;

		_priority = priority;
	}

	@JSON
	@Override
	public boolean getAlert() {
		return _alert;
	}

	@JSON
	@Override
	public boolean isAlert() {
		return _alert;
	}

	@Override
	public void setAlert(boolean alert) {
		_columnBitmask |= ALERT_COLUMN_BITMASK;

		if (!_setOriginalAlert) {
			_setOriginalAlert = true;

			_originalAlert = _alert;
		}

		_alert = alert;
	}

	public boolean getOriginalAlert() {
		return _originalAlert;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				AnnouncementsEntry.class.getName()), getClassNameId());
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			AnnouncementsEntry.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public AnnouncementsEntry toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (AnnouncementsEntry)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		AnnouncementsEntryImpl announcementsEntryImpl = new AnnouncementsEntryImpl();

		announcementsEntryImpl.setUuid(getUuid());
		announcementsEntryImpl.setEntryId(getEntryId());
		announcementsEntryImpl.setCompanyId(getCompanyId());
		announcementsEntryImpl.setUserId(getUserId());
		announcementsEntryImpl.setUserName(getUserName());
		announcementsEntryImpl.setCreateDate(getCreateDate());
		announcementsEntryImpl.setModifiedDate(getModifiedDate());
		announcementsEntryImpl.setClassNameId(getClassNameId());
		announcementsEntryImpl.setClassPK(getClassPK());
		announcementsEntryImpl.setTitle(getTitle());
		announcementsEntryImpl.setContent(getContent());
		announcementsEntryImpl.setUrl(getUrl());
		announcementsEntryImpl.setType(getType());
		announcementsEntryImpl.setDisplayDate(getDisplayDate());
		announcementsEntryImpl.setExpirationDate(getExpirationDate());
		announcementsEntryImpl.setPriority(getPriority());
		announcementsEntryImpl.setAlert(isAlert());

		announcementsEntryImpl.resetOriginalValues();

		return announcementsEntryImpl;
	}

	@Override
	public int compareTo(AnnouncementsEntry announcementsEntry) {
		int value = 0;

		if (getPriority() < announcementsEntry.getPriority()) {
			value = -1;
		}
		else if (getPriority() > announcementsEntry.getPriority()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = DateUtil.compareTo(getModifiedDate(),
				announcementsEntry.getModifiedDate());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AnnouncementsEntry)) {
			return false;
		}

		AnnouncementsEntry announcementsEntry = (AnnouncementsEntry)obj;

		long primaryKey = announcementsEntry.getPrimaryKey();

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
		AnnouncementsEntryModelImpl announcementsEntryModelImpl = this;

		announcementsEntryModelImpl._originalUuid = announcementsEntryModelImpl._uuid;

		announcementsEntryModelImpl._originalCompanyId = announcementsEntryModelImpl._companyId;

		announcementsEntryModelImpl._setOriginalCompanyId = false;

		announcementsEntryModelImpl._originalUserId = announcementsEntryModelImpl._userId;

		announcementsEntryModelImpl._setOriginalUserId = false;

		announcementsEntryModelImpl._setModifiedDate = false;

		announcementsEntryModelImpl._originalClassNameId = announcementsEntryModelImpl._classNameId;

		announcementsEntryModelImpl._setOriginalClassNameId = false;

		announcementsEntryModelImpl._originalClassPK = announcementsEntryModelImpl._classPK;

		announcementsEntryModelImpl._setOriginalClassPK = false;

		announcementsEntryModelImpl._originalAlert = announcementsEntryModelImpl._alert;

		announcementsEntryModelImpl._setOriginalAlert = false;

		announcementsEntryModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<AnnouncementsEntry> toCacheModel() {
		AnnouncementsEntryCacheModel announcementsEntryCacheModel = new AnnouncementsEntryCacheModel();

		announcementsEntryCacheModel.uuid = getUuid();

		String uuid = announcementsEntryCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			announcementsEntryCacheModel.uuid = null;
		}

		announcementsEntryCacheModel.entryId = getEntryId();

		announcementsEntryCacheModel.companyId = getCompanyId();

		announcementsEntryCacheModel.userId = getUserId();

		announcementsEntryCacheModel.userName = getUserName();

		String userName = announcementsEntryCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			announcementsEntryCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			announcementsEntryCacheModel.createDate = createDate.getTime();
		}
		else {
			announcementsEntryCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			announcementsEntryCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			announcementsEntryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		announcementsEntryCacheModel.classNameId = getClassNameId();

		announcementsEntryCacheModel.classPK = getClassPK();

		announcementsEntryCacheModel.title = getTitle();

		String title = announcementsEntryCacheModel.title;

		if ((title != null) && (title.length() == 0)) {
			announcementsEntryCacheModel.title = null;
		}

		announcementsEntryCacheModel.content = getContent();

		String content = announcementsEntryCacheModel.content;

		if ((content != null) && (content.length() == 0)) {
			announcementsEntryCacheModel.content = null;
		}

		announcementsEntryCacheModel.url = getUrl();

		String url = announcementsEntryCacheModel.url;

		if ((url != null) && (url.length() == 0)) {
			announcementsEntryCacheModel.url = null;
		}

		announcementsEntryCacheModel.type = getType();

		String type = announcementsEntryCacheModel.type;

		if ((type != null) && (type.length() == 0)) {
			announcementsEntryCacheModel.type = null;
		}

		Date displayDate = getDisplayDate();

		if (displayDate != null) {
			announcementsEntryCacheModel.displayDate = displayDate.getTime();
		}
		else {
			announcementsEntryCacheModel.displayDate = Long.MIN_VALUE;
		}

		Date expirationDate = getExpirationDate();

		if (expirationDate != null) {
			announcementsEntryCacheModel.expirationDate = expirationDate.getTime();
		}
		else {
			announcementsEntryCacheModel.expirationDate = Long.MIN_VALUE;
		}

		announcementsEntryCacheModel.priority = getPriority();

		announcementsEntryCacheModel.alert = isAlert();

		return announcementsEntryCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(35);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", entryId=");
		sb.append(getEntryId());
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
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", title=");
		sb.append(getTitle());
		sb.append(", content=");
		sb.append(getContent());
		sb.append(", url=");
		sb.append(getUrl());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", displayDate=");
		sb.append(getDisplayDate());
		sb.append(", expirationDate=");
		sb.append(getExpirationDate());
		sb.append(", priority=");
		sb.append(getPriority());
		sb.append(", alert=");
		sb.append(isAlert());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(55);

		sb.append("<model><model-name>");
		sb.append("com.liferay.announcements.kernel.model.AnnouncementsEntry");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>entryId</column-name><column-value><![CDATA[");
		sb.append(getEntryId());
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
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>title</column-name><column-value><![CDATA[");
		sb.append(getTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>content</column-name><column-value><![CDATA[");
		sb.append(getContent());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>url</column-name><column-value><![CDATA[");
		sb.append(getUrl());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>displayDate</column-name><column-value><![CDATA[");
		sb.append(getDisplayDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>expirationDate</column-name><column-value><![CDATA[");
		sb.append(getExpirationDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>priority</column-name><column-value><![CDATA[");
		sb.append(getPriority());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>alert</column-name><column-value><![CDATA[");
		sb.append(isAlert());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = AnnouncementsEntry.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			AnnouncementsEntry.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _entryId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private String _title;
	private String _content;
	private String _url;
	private String _type;
	private Date _displayDate;
	private Date _expirationDate;
	private int _priority;
	private boolean _alert;
	private boolean _originalAlert;
	private boolean _setOriginalAlert;
	private long _columnBitmask;
	private AnnouncementsEntry _escapedModel;
}