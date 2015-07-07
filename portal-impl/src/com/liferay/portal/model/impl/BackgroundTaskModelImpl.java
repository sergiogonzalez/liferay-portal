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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.BackgroundTaskModel;
import com.liferay.portal.model.BackgroundTaskSoap;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;

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
 * The base model implementation for the BackgroundTask service. Represents a row in the &quot;BackgroundTask&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link BackgroundTaskModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link BackgroundTaskImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BackgroundTaskImpl
 * @see BackgroundTask
 * @see BackgroundTaskModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class BackgroundTaskModelImpl extends BaseModelImpl<BackgroundTask>
	implements BackgroundTaskModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a background task model instance should use the {@link BackgroundTask} interface instead.
	 */
	public static final String TABLE_NAME = "BackgroundTask";
	public static final Object[][] TABLE_COLUMNS = {
			{ "mvccVersion", Types.BIGINT },
			{ "backgroundTaskId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "name", Types.VARCHAR },
			{ "servletContextNames", Types.VARCHAR },
			{ "taskExecutorClassName", Types.VARCHAR },
			{ "taskContextMap", Types.CLOB },
			{ "completed", Types.BOOLEAN },
			{ "completionDate", Types.TIMESTAMP },
			{ "status", Types.INTEGER },
			{ "statusMessage", Types.CLOB }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("backgroundTaskId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("servletContextNames", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("taskExecutorClassName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("taskContextMap", Types.CLOB);
		TABLE_COLUMNS_MAP.put("completed", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("completionDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("status", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("statusMessage", Types.CLOB);
	}

	public static final String TABLE_SQL_CREATE = "create table BackgroundTask (mvccVersion LONG default 0,backgroundTaskId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(255) null,servletContextNames VARCHAR(255) null,taskExecutorClassName VARCHAR(200) null,taskContextMap TEXT null,completed BOOLEAN,completionDate DATE null,status INTEGER,statusMessage TEXT null)";
	public static final String TABLE_SQL_DROP = "drop table BackgroundTask";
	public static final String ORDER_BY_JPQL = " ORDER BY backgroundTask.createDate ASC";
	public static final String ORDER_BY_SQL = " ORDER BY BackgroundTask.createDate ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.BackgroundTask"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.BackgroundTask"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.model.BackgroundTask"),
			true);
	public static final long COMPANYID_COLUMN_BITMASK = 1L;
	public static final long COMPLETED_COLUMN_BITMASK = 2L;
	public static final long GROUPID_COLUMN_BITMASK = 4L;
	public static final long NAME_COLUMN_BITMASK = 8L;
	public static final long STATUS_COLUMN_BITMASK = 16L;
	public static final long TASKEXECUTORCLASSNAME_COLUMN_BITMASK = 32L;
	public static final long CREATEDATE_COLUMN_BITMASK = 64L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static BackgroundTask toModel(BackgroundTaskSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		BackgroundTask model = new BackgroundTaskImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setBackgroundTaskId(soapModel.getBackgroundTaskId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setName(soapModel.getName());
		model.setServletContextNames(soapModel.getServletContextNames());
		model.setTaskExecutorClassName(soapModel.getTaskExecutorClassName());
		model.setTaskContextMap(soapModel.getTaskContextMap());
		model.setCompleted(soapModel.getCompleted());
		model.setCompletionDate(soapModel.getCompletionDate());
		model.setStatus(soapModel.getStatus());
		model.setStatusMessage(soapModel.getStatusMessage());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<BackgroundTask> toModels(BackgroundTaskSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<BackgroundTask> models = new ArrayList<BackgroundTask>(soapModels.length);

		for (BackgroundTaskSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.BackgroundTask"));

	public BackgroundTaskModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _backgroundTaskId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setBackgroundTaskId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _backgroundTaskId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return BackgroundTask.class;
	}

	@Override
	public String getModelClassName() {
		return BackgroundTask.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("backgroundTaskId", getBackgroundTaskId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("servletContextNames", getServletContextNames());
		attributes.put("taskExecutorClassName", getTaskExecutorClassName());
		attributes.put("taskContextMap", getTaskContextMap());
		attributes.put("completed", getCompleted());
		attributes.put("completionDate", getCompletionDate());
		attributes.put("status", getStatus());
		attributes.put("statusMessage", getStatusMessage());

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

		Long backgroundTaskId = (Long)attributes.get("backgroundTaskId");

		if (backgroundTaskId != null) {
			setBackgroundTaskId(backgroundTaskId);
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

		String servletContextNames = (String)attributes.get(
				"servletContextNames");

		if (servletContextNames != null) {
			setServletContextNames(servletContextNames);
		}

		String taskExecutorClassName = (String)attributes.get(
				"taskExecutorClassName");

		if (taskExecutorClassName != null) {
			setTaskExecutorClassName(taskExecutorClassName);
		}

		Map<String, Serializable> taskContextMap = (Map<String, Serializable>)attributes.get(
				"taskContextMap");

		if (taskContextMap != null) {
			setTaskContextMap(taskContextMap);
		}

		Boolean completed = (Boolean)attributes.get("completed");

		if (completed != null) {
			setCompleted(completed);
		}

		Date completionDate = (Date)attributes.get("completionDate");

		if (completionDate != null) {
			setCompletionDate(completionDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		String statusMessage = (String)attributes.get("statusMessage");

		if (statusMessage != null) {
			setStatusMessage(statusMessage);
		}
	}

	@JSON
	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@JSON
	@Override
	public long getBackgroundTaskId() {
		return _backgroundTaskId;
	}

	@Override
	public void setBackgroundTaskId(long backgroundTaskId) {
		_backgroundTaskId = backgroundTaskId;
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
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_columnBitmask = -1L;

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
	public String getName() {
		if (_name == null) {
			return StringPool.BLANK;
		}
		else {
			return _name;
		}
	}

	@Override
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
	@Override
	public String getServletContextNames() {
		if (_servletContextNames == null) {
			return StringPool.BLANK;
		}
		else {
			return _servletContextNames;
		}
	}

	@Override
	public void setServletContextNames(String servletContextNames) {
		_servletContextNames = servletContextNames;
	}

	@JSON
	@Override
	public String getTaskExecutorClassName() {
		if (_taskExecutorClassName == null) {
			return StringPool.BLANK;
		}
		else {
			return _taskExecutorClassName;
		}
	}

	@Override
	public void setTaskExecutorClassName(String taskExecutorClassName) {
		_columnBitmask |= TASKEXECUTORCLASSNAME_COLUMN_BITMASK;

		if (_originalTaskExecutorClassName == null) {
			_originalTaskExecutorClassName = _taskExecutorClassName;
		}

		_taskExecutorClassName = taskExecutorClassName;
	}

	public String getOriginalTaskExecutorClassName() {
		return GetterUtil.getString(_originalTaskExecutorClassName);
	}

	@JSON
	@Override
	public Map<String, Serializable> getTaskContextMap() {
		return _taskContextMap;
	}

	@Override
	public void setTaskContextMap(Map<String, Serializable> taskContextMap) {
		_taskContextMap = taskContextMap;
	}

	@JSON
	@Override
	public boolean getCompleted() {
		return _completed;
	}

	@Override
	public boolean isCompleted() {
		return _completed;
	}

	@Override
	public void setCompleted(boolean completed) {
		_columnBitmask |= COMPLETED_COLUMN_BITMASK;

		if (!_setOriginalCompleted) {
			_setOriginalCompleted = true;

			_originalCompleted = _completed;
		}

		_completed = completed;
	}

	public boolean getOriginalCompleted() {
		return _originalCompleted;
	}

	@JSON
	@Override
	public Date getCompletionDate() {
		return _completionDate;
	}

	@Override
	public void setCompletionDate(Date completionDate) {
		_completionDate = completionDate;
	}

	@JSON
	@Override
	public int getStatus() {
		return _status;
	}

	@Override
	public void setStatus(int status) {
		_columnBitmask |= STATUS_COLUMN_BITMASK;

		if (!_setOriginalStatus) {
			_setOriginalStatus = true;

			_originalStatus = _status;
		}

		_status = status;
	}

	public int getOriginalStatus() {
		return _originalStatus;
	}

	@JSON
	@Override
	public String getStatusMessage() {
		if (_statusMessage == null) {
			return StringPool.BLANK;
		}
		else {
			return _statusMessage;
		}
	}

	@Override
	public void setStatusMessage(String statusMessage) {
		_statusMessage = statusMessage;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			BackgroundTask.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public BackgroundTask toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (BackgroundTask)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		BackgroundTaskImpl backgroundTaskImpl = new BackgroundTaskImpl();

		backgroundTaskImpl.setMvccVersion(getMvccVersion());
		backgroundTaskImpl.setBackgroundTaskId(getBackgroundTaskId());
		backgroundTaskImpl.setGroupId(getGroupId());
		backgroundTaskImpl.setCompanyId(getCompanyId());
		backgroundTaskImpl.setUserId(getUserId());
		backgroundTaskImpl.setUserName(getUserName());
		backgroundTaskImpl.setCreateDate(getCreateDate());
		backgroundTaskImpl.setModifiedDate(getModifiedDate());
		backgroundTaskImpl.setName(getName());
		backgroundTaskImpl.setServletContextNames(getServletContextNames());
		backgroundTaskImpl.setTaskExecutorClassName(getTaskExecutorClassName());
		backgroundTaskImpl.setTaskContextMap(getTaskContextMap());
		backgroundTaskImpl.setCompleted(getCompleted());
		backgroundTaskImpl.setCompletionDate(getCompletionDate());
		backgroundTaskImpl.setStatus(getStatus());
		backgroundTaskImpl.setStatusMessage(getStatusMessage());

		backgroundTaskImpl.resetOriginalValues();

		return backgroundTaskImpl;
	}

	@Override
	public int compareTo(BackgroundTask backgroundTask) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(),
				backgroundTask.getCreateDate());

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

		if (!(obj instanceof BackgroundTask)) {
			return false;
		}

		BackgroundTask backgroundTask = (BackgroundTask)obj;

		long primaryKey = backgroundTask.getPrimaryKey();

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
		BackgroundTaskModelImpl backgroundTaskModelImpl = this;

		backgroundTaskModelImpl._originalGroupId = backgroundTaskModelImpl._groupId;

		backgroundTaskModelImpl._setOriginalGroupId = false;

		backgroundTaskModelImpl._originalCompanyId = backgroundTaskModelImpl._companyId;

		backgroundTaskModelImpl._setOriginalCompanyId = false;

		backgroundTaskModelImpl._setModifiedDate = false;

		backgroundTaskModelImpl._originalName = backgroundTaskModelImpl._name;

		backgroundTaskModelImpl._originalTaskExecutorClassName = backgroundTaskModelImpl._taskExecutorClassName;

		backgroundTaskModelImpl._originalCompleted = backgroundTaskModelImpl._completed;

		backgroundTaskModelImpl._setOriginalCompleted = false;

		backgroundTaskModelImpl._originalStatus = backgroundTaskModelImpl._status;

		backgroundTaskModelImpl._setOriginalStatus = false;

		backgroundTaskModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<BackgroundTask> toCacheModel() {
		BackgroundTaskCacheModel backgroundTaskCacheModel = new BackgroundTaskCacheModel();

		backgroundTaskCacheModel.mvccVersion = getMvccVersion();

		backgroundTaskCacheModel.backgroundTaskId = getBackgroundTaskId();

		backgroundTaskCacheModel.groupId = getGroupId();

		backgroundTaskCacheModel.companyId = getCompanyId();

		backgroundTaskCacheModel.userId = getUserId();

		backgroundTaskCacheModel.userName = getUserName();

		String userName = backgroundTaskCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			backgroundTaskCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			backgroundTaskCacheModel.createDate = createDate.getTime();
		}
		else {
			backgroundTaskCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			backgroundTaskCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			backgroundTaskCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		backgroundTaskCacheModel.name = getName();

		String name = backgroundTaskCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			backgroundTaskCacheModel.name = null;
		}

		backgroundTaskCacheModel.servletContextNames = getServletContextNames();

		String servletContextNames = backgroundTaskCacheModel.servletContextNames;

		if ((servletContextNames != null) &&
				(servletContextNames.length() == 0)) {
			backgroundTaskCacheModel.servletContextNames = null;
		}

		backgroundTaskCacheModel.taskExecutorClassName = getTaskExecutorClassName();

		String taskExecutorClassName = backgroundTaskCacheModel.taskExecutorClassName;

		if ((taskExecutorClassName != null) &&
				(taskExecutorClassName.length() == 0)) {
			backgroundTaskCacheModel.taskExecutorClassName = null;
		}

		backgroundTaskCacheModel.taskContextMap = getTaskContextMap();

		backgroundTaskCacheModel.completed = getCompleted();

		Date completionDate = getCompletionDate();

		if (completionDate != null) {
			backgroundTaskCacheModel.completionDate = completionDate.getTime();
		}
		else {
			backgroundTaskCacheModel.completionDate = Long.MIN_VALUE;
		}

		backgroundTaskCacheModel.status = getStatus();

		backgroundTaskCacheModel.statusMessage = getStatusMessage();

		String statusMessage = backgroundTaskCacheModel.statusMessage;

		if ((statusMessage != null) && (statusMessage.length() == 0)) {
			backgroundTaskCacheModel.statusMessage = null;
		}

		return backgroundTaskCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{mvccVersion=");
		sb.append(getMvccVersion());
		sb.append(", backgroundTaskId=");
		sb.append(getBackgroundTaskId());
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
		sb.append(", servletContextNames=");
		sb.append(getServletContextNames());
		sb.append(", taskExecutorClassName=");
		sb.append(getTaskExecutorClassName());
		sb.append(", taskContextMap=");
		sb.append(getTaskContextMap());
		sb.append(", completed=");
		sb.append(getCompleted());
		sb.append(", completionDate=");
		sb.append(getCompletionDate());
		sb.append(", status=");
		sb.append(getStatus());
		sb.append(", statusMessage=");
		sb.append(getStatusMessage());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(52);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.BackgroundTask");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>mvccVersion</column-name><column-value><![CDATA[");
		sb.append(getMvccVersion());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>backgroundTaskId</column-name><column-value><![CDATA[");
		sb.append(getBackgroundTaskId());
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
			"<column><column-name>servletContextNames</column-name><column-value><![CDATA[");
		sb.append(getServletContextNames());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>taskExecutorClassName</column-name><column-value><![CDATA[");
		sb.append(getTaskExecutorClassName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>taskContextMap</column-name><column-value><![CDATA[");
		sb.append(getTaskContextMap());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>completed</column-name><column-value><![CDATA[");
		sb.append(getCompleted());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>completionDate</column-name><column-value><![CDATA[");
		sb.append(getCompletionDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>status</column-name><column-value><![CDATA[");
		sb.append(getStatus());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusMessage</column-name><column-value><![CDATA[");
		sb.append(getStatusMessage());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = BackgroundTask.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			BackgroundTask.class
		};
	private long _mvccVersion;
	private long _backgroundTaskId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _name;
	private String _originalName;
	private String _servletContextNames;
	private String _taskExecutorClassName;
	private String _originalTaskExecutorClassName;
	private Map<String, Serializable> _taskContextMap;
	private boolean _completed;
	private boolean _originalCompleted;
	private boolean _setOriginalCompleted;
	private Date _completionDate;
	private int _status;
	private int _originalStatus;
	private boolean _setOriginalStatus;
	private String _statusMessage;
	private long _columnBitmask;
	private BackgroundTask _escapedModel;
}