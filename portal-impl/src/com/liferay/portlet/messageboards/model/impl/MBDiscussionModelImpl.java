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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBDiscussionModel;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the MBDiscussion service. Represents a row in the &quot;MBDiscussion&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.messageboards.model.MBDiscussionModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link MBDiscussionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBDiscussionImpl
 * @see com.liferay.portlet.messageboards.model.MBDiscussion
 * @see com.liferay.portlet.messageboards.model.MBDiscussionModel
 * @generated
 */
public class MBDiscussionModelImpl extends BaseModelImpl<MBDiscussion>
	implements MBDiscussionModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a message boards discussion model instance should use the {@link com.liferay.portlet.messageboards.model.MBDiscussion} interface instead.
	 */
	public static final String TABLE_NAME = "MBDiscussion";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "discussionId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "classNameId", Types.BIGINT },
			{ "classPK", Types.BIGINT },
			{ "threadId", Types.BIGINT }
		};
	public static final String TABLE_SQL_CREATE = "create table MBDiscussion (uuid_ VARCHAR(75) null,discussionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,threadId LONG)";
	public static final String TABLE_SQL_DROP = "drop table MBDiscussion";
	public static final String ORDER_BY_JPQL = " ORDER BY mbDiscussion.discussionId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY MBDiscussion.discussionId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.messageboards.model.MBDiscussion"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.messageboards.model.MBDiscussion"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portlet.messageboards.model.MBDiscussion"),
			true);
	public static long CLASSNAMEID_COLUMN_BITMASK = 1L;
	public static long CLASSPK_COLUMN_BITMASK = 2L;
	public static long COMPANYID_COLUMN_BITMASK = 4L;
	public static long GROUPID_COLUMN_BITMASK = 8L;
	public static long THREADID_COLUMN_BITMASK = 16L;
	public static long UUID_COLUMN_BITMASK = 32L;
	public static long DISCUSSIONID_COLUMN_BITMASK = 64L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.messageboards.model.MBDiscussion"));

	public MBDiscussionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _discussionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setDiscussionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _discussionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return MBDiscussion.class;
	}

	@Override
	public String getModelClassName() {
		return MBDiscussion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("discussionId", getDiscussionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("threadId", getThreadId());

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

		Long discussionId = (Long)attributes.get("discussionId");

		if (discussionId != null) {
			setDiscussionId(discussionId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long threadId = (Long)attributes.get("threadId");

		if (threadId != null) {
			setThreadId(threadId);
		}
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
	public long getDiscussionId() {
		return _discussionId;
	}

	@Override
	public void setDiscussionId(long discussionId) {
		_discussionId = discussionId;
	}

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
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
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

	@Override
	public long getThreadId() {
		return _threadId;
	}

	@Override
	public void setThreadId(long threadId) {
		_columnBitmask |= THREADID_COLUMN_BITMASK;

		if (!_setOriginalThreadId) {
			_setOriginalThreadId = true;

			_originalThreadId = _threadId;
		}

		_threadId = threadId;
	}

	public long getOriginalThreadId() {
		return _originalThreadId;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				MBDiscussion.class.getName()), getClassNameId());
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			MBDiscussion.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public MBDiscussion toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (MBDiscussion)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		MBDiscussionImpl mbDiscussionImpl = new MBDiscussionImpl();

		mbDiscussionImpl.setUuid(getUuid());
		mbDiscussionImpl.setDiscussionId(getDiscussionId());
		mbDiscussionImpl.setGroupId(getGroupId());
		mbDiscussionImpl.setCompanyId(getCompanyId());
		mbDiscussionImpl.setUserId(getUserId());
		mbDiscussionImpl.setUserName(getUserName());
		mbDiscussionImpl.setCreateDate(getCreateDate());
		mbDiscussionImpl.setModifiedDate(getModifiedDate());
		mbDiscussionImpl.setClassNameId(getClassNameId());
		mbDiscussionImpl.setClassPK(getClassPK());
		mbDiscussionImpl.setThreadId(getThreadId());

		mbDiscussionImpl.resetOriginalValues();

		return mbDiscussionImpl;
	}

	@Override
	public int compareTo(MBDiscussion mbDiscussion) {
		long primaryKey = mbDiscussion.getPrimaryKey();

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

		if (!(obj instanceof MBDiscussion)) {
			return false;
		}

		MBDiscussion mbDiscussion = (MBDiscussion)obj;

		long primaryKey = mbDiscussion.getPrimaryKey();

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
		MBDiscussionModelImpl mbDiscussionModelImpl = this;

		mbDiscussionModelImpl._originalUuid = mbDiscussionModelImpl._uuid;

		mbDiscussionModelImpl._originalGroupId = mbDiscussionModelImpl._groupId;

		mbDiscussionModelImpl._setOriginalGroupId = false;

		mbDiscussionModelImpl._originalCompanyId = mbDiscussionModelImpl._companyId;

		mbDiscussionModelImpl._setOriginalCompanyId = false;

		mbDiscussionModelImpl._originalClassNameId = mbDiscussionModelImpl._classNameId;

		mbDiscussionModelImpl._setOriginalClassNameId = false;

		mbDiscussionModelImpl._originalClassPK = mbDiscussionModelImpl._classPK;

		mbDiscussionModelImpl._setOriginalClassPK = false;

		mbDiscussionModelImpl._originalThreadId = mbDiscussionModelImpl._threadId;

		mbDiscussionModelImpl._setOriginalThreadId = false;

		mbDiscussionModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<MBDiscussion> toCacheModel() {
		MBDiscussionCacheModel mbDiscussionCacheModel = new MBDiscussionCacheModel();

		mbDiscussionCacheModel.uuid = getUuid();

		String uuid = mbDiscussionCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			mbDiscussionCacheModel.uuid = null;
		}

		mbDiscussionCacheModel.discussionId = getDiscussionId();

		mbDiscussionCacheModel.groupId = getGroupId();

		mbDiscussionCacheModel.companyId = getCompanyId();

		mbDiscussionCacheModel.userId = getUserId();

		mbDiscussionCacheModel.userName = getUserName();

		String userName = mbDiscussionCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			mbDiscussionCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			mbDiscussionCacheModel.createDate = createDate.getTime();
		}
		else {
			mbDiscussionCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			mbDiscussionCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			mbDiscussionCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		mbDiscussionCacheModel.classNameId = getClassNameId();

		mbDiscussionCacheModel.classPK = getClassPK();

		mbDiscussionCacheModel.threadId = getThreadId();

		return mbDiscussionCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", discussionId=");
		sb.append(getDiscussionId());
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
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", threadId=");
		sb.append(getThreadId());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(37);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.messageboards.model.MBDiscussion");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>discussionId</column-name><column-value><![CDATA[");
		sb.append(getDiscussionId());
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
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>threadId</column-name><column-value><![CDATA[");
		sb.append(getThreadId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = MBDiscussion.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			MBDiscussion.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _discussionId;
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
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private long _threadId;
	private long _originalThreadId;
	private boolean _setOriginalThreadId;
	private long _columnBitmask;
	private MBDiscussion _escapedModel;
}