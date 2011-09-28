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
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.model.WorkflowDefinitionLinkModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;

/**
 * The base model implementation for the WorkflowDefinitionLink service. Represents a row in the &quot;WorkflowDefinitionLink&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.WorkflowDefinitionLinkModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link WorkflowDefinitionLinkImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowDefinitionLinkImpl
 * @see com.liferay.portal.model.WorkflowDefinitionLink
 * @see com.liferay.portal.model.WorkflowDefinitionLinkModel
 * @generated
 */
public class WorkflowDefinitionLinkModelImpl extends BaseModelImpl<WorkflowDefinitionLink>
	implements WorkflowDefinitionLinkModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a workflow definition link model instance should use the {@link com.liferay.portal.model.WorkflowDefinitionLink} interface instead.
	 */
	public static final String TABLE_NAME = "WorkflowDefinitionLink";
	public static final Object[][] TABLE_COLUMNS = {
			{ "workflowDefinitionLinkId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "classNameId", Types.BIGINT },
			{ "classPK", Types.BIGINT },
			{ "typePK", Types.BIGINT },
			{ "workflowDefinitionName", Types.VARCHAR },
			{ "workflowDefinitionVersion", Types.INTEGER }
		};
	public static final String TABLE_SQL_CREATE = "create table WorkflowDefinitionLink (workflowDefinitionLinkId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,typePK LONG,workflowDefinitionName VARCHAR(75) null,workflowDefinitionVersion INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table WorkflowDefinitionLink";
	public static final String ORDER_BY_JPQL = " ORDER BY workflowDefinitionLink.workflowDefinitionName ASC";
	public static final String ORDER_BY_SQL = " ORDER BY WorkflowDefinitionLink.workflowDefinitionName ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.WorkflowDefinitionLink"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.WorkflowDefinitionLink"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.model.WorkflowDefinitionLink"),
			true);
	public static long TYPEPK_COLUMN_BITMASK = 1L;
	public static long GROUPID_COLUMN_BITMASK = 2L;
	public static long COMPANYID_COLUMN_BITMASK = 4L;
	public static long CLASSNAMEID_COLUMN_BITMASK = 8L;
	public static long WORKFLOWDEFINITIONVERSION_COLUMN_BITMASK = 16L;
	public static long CLASSPK_COLUMN_BITMASK = 32L;
	public static long WORKFLOWDEFINITIONNAME_COLUMN_BITMASK = 64L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.WorkflowDefinitionLink"));

	public WorkflowDefinitionLinkModelImpl() {
	}

	public long getPrimaryKey() {
		return _workflowDefinitionLinkId;
	}

	public void setPrimaryKey(long primaryKey) {
		setWorkflowDefinitionLinkId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_workflowDefinitionLinkId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return WorkflowDefinitionLink.class;
	}

	public String getModelClassName() {
		return WorkflowDefinitionLink.class.getName();
	}

	public long getWorkflowDefinitionLinkId() {
		return _workflowDefinitionLinkId;
	}

	public void setWorkflowDefinitionLinkId(long workflowDefinitionLinkId) {
		_workflowDefinitionLinkId = workflowDefinitionLinkId;
	}

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

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	public long getClassNameId() {
		return _classNameId;
	}

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

	public long getClassPK() {
		return _classPK;
	}

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

	public long getTypePK() {
		return _typePK;
	}

	public void setTypePK(long typePK) {
		_columnBitmask |= TYPEPK_COLUMN_BITMASK;

		if (!_setOriginalTypePK) {
			_setOriginalTypePK = true;

			_originalTypePK = _typePK;
		}

		_typePK = typePK;
	}

	public long getOriginalTypePK() {
		return _originalTypePK;
	}

	public String getWorkflowDefinitionName() {
		if (_workflowDefinitionName == null) {
			return StringPool.BLANK;
		}
		else {
			return _workflowDefinitionName;
		}
	}

	public void setWorkflowDefinitionName(String workflowDefinitionName) {
		_columnBitmask |= WORKFLOWDEFINITIONNAME_COLUMN_BITMASK;

		if (_originalWorkflowDefinitionName == null) {
			_originalWorkflowDefinitionName = _workflowDefinitionName;
		}

		_workflowDefinitionName = workflowDefinitionName;
	}

	public String getOriginalWorkflowDefinitionName() {
		return GetterUtil.getString(_originalWorkflowDefinitionName);
	}

	public int getWorkflowDefinitionVersion() {
		return _workflowDefinitionVersion;
	}

	public void setWorkflowDefinitionVersion(int workflowDefinitionVersion) {
		_columnBitmask |= WORKFLOWDEFINITIONVERSION_COLUMN_BITMASK;

		if (!_setOriginalWorkflowDefinitionVersion) {
			_setOriginalWorkflowDefinitionVersion = true;

			_originalWorkflowDefinitionVersion = _workflowDefinitionVersion;
		}

		_workflowDefinitionVersion = workflowDefinitionVersion;
	}

	public int getOriginalWorkflowDefinitionVersion() {
		return _originalWorkflowDefinitionVersion;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public WorkflowDefinitionLink toEscapedModel() {
		if (isEscapedModel()) {
			return (WorkflowDefinitionLink)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (WorkflowDefinitionLink)ProxyUtil.newProxyInstance(_classLoader,
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
					WorkflowDefinitionLink.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		WorkflowDefinitionLinkImpl workflowDefinitionLinkImpl = new WorkflowDefinitionLinkImpl();

		workflowDefinitionLinkImpl.setWorkflowDefinitionLinkId(getWorkflowDefinitionLinkId());
		workflowDefinitionLinkImpl.setGroupId(getGroupId());
		workflowDefinitionLinkImpl.setCompanyId(getCompanyId());
		workflowDefinitionLinkImpl.setUserId(getUserId());
		workflowDefinitionLinkImpl.setUserName(getUserName());
		workflowDefinitionLinkImpl.setCreateDate(getCreateDate());
		workflowDefinitionLinkImpl.setModifiedDate(getModifiedDate());
		workflowDefinitionLinkImpl.setClassNameId(getClassNameId());
		workflowDefinitionLinkImpl.setClassPK(getClassPK());
		workflowDefinitionLinkImpl.setTypePK(getTypePK());
		workflowDefinitionLinkImpl.setWorkflowDefinitionName(getWorkflowDefinitionName());
		workflowDefinitionLinkImpl.setWorkflowDefinitionVersion(getWorkflowDefinitionVersion());

		workflowDefinitionLinkImpl.resetOriginalValues();

		return workflowDefinitionLinkImpl;
	}

	public int compareTo(WorkflowDefinitionLink workflowDefinitionLink) {
		int value = 0;

		value = getWorkflowDefinitionName()
					.compareTo(workflowDefinitionLink.getWorkflowDefinitionName());

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

		WorkflowDefinitionLink workflowDefinitionLink = null;

		try {
			workflowDefinitionLink = (WorkflowDefinitionLink)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = workflowDefinitionLink.getPrimaryKey();

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
		WorkflowDefinitionLinkModelImpl workflowDefinitionLinkModelImpl = this;

		workflowDefinitionLinkModelImpl._originalGroupId = workflowDefinitionLinkModelImpl._groupId;

		workflowDefinitionLinkModelImpl._setOriginalGroupId = false;

		workflowDefinitionLinkModelImpl._originalCompanyId = workflowDefinitionLinkModelImpl._companyId;

		workflowDefinitionLinkModelImpl._setOriginalCompanyId = false;

		workflowDefinitionLinkModelImpl._originalClassNameId = workflowDefinitionLinkModelImpl._classNameId;

		workflowDefinitionLinkModelImpl._setOriginalClassNameId = false;

		workflowDefinitionLinkModelImpl._originalClassPK = workflowDefinitionLinkModelImpl._classPK;

		workflowDefinitionLinkModelImpl._setOriginalClassPK = false;

		workflowDefinitionLinkModelImpl._originalTypePK = workflowDefinitionLinkModelImpl._typePK;

		workflowDefinitionLinkModelImpl._setOriginalTypePK = false;

		workflowDefinitionLinkModelImpl._originalWorkflowDefinitionName = workflowDefinitionLinkModelImpl._workflowDefinitionName;

		workflowDefinitionLinkModelImpl._originalWorkflowDefinitionVersion = workflowDefinitionLinkModelImpl._workflowDefinitionVersion;

		workflowDefinitionLinkModelImpl._setOriginalWorkflowDefinitionVersion = false;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<WorkflowDefinitionLink> toCacheModel() {
		WorkflowDefinitionLinkCacheModel workflowDefinitionLinkCacheModel = new WorkflowDefinitionLinkCacheModel();

		workflowDefinitionLinkCacheModel.workflowDefinitionLinkId = getWorkflowDefinitionLinkId();

		workflowDefinitionLinkCacheModel.groupId = getGroupId();

		workflowDefinitionLinkCacheModel.companyId = getCompanyId();

		workflowDefinitionLinkCacheModel.userId = getUserId();

		workflowDefinitionLinkCacheModel.userName = getUserName();

		String userName = workflowDefinitionLinkCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			workflowDefinitionLinkCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			workflowDefinitionLinkCacheModel.createDate = createDate.getTime();
		}
		else {
			workflowDefinitionLinkCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			workflowDefinitionLinkCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			workflowDefinitionLinkCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		workflowDefinitionLinkCacheModel.classNameId = getClassNameId();

		workflowDefinitionLinkCacheModel.classPK = getClassPK();

		workflowDefinitionLinkCacheModel.typePK = getTypePK();

		workflowDefinitionLinkCacheModel.workflowDefinitionName = getWorkflowDefinitionName();

		String workflowDefinitionName = workflowDefinitionLinkCacheModel.workflowDefinitionName;

		if ((workflowDefinitionName != null) &&
				(workflowDefinitionName.length() == 0)) {
			workflowDefinitionLinkCacheModel.workflowDefinitionName = null;
		}

		workflowDefinitionLinkCacheModel.workflowDefinitionVersion = getWorkflowDefinitionVersion();

		return workflowDefinitionLinkCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{workflowDefinitionLinkId=");
		sb.append(getWorkflowDefinitionLinkId());
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
		sb.append(", typePK=");
		sb.append(getTypePK());
		sb.append(", workflowDefinitionName=");
		sb.append(getWorkflowDefinitionName());
		sb.append(", workflowDefinitionVersion=");
		sb.append(getWorkflowDefinitionVersion());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(40);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.WorkflowDefinitionLink");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>workflowDefinitionLinkId</column-name><column-value><![CDATA[");
		sb.append(getWorkflowDefinitionLinkId());
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
			"<column><column-name>typePK</column-name><column-value><![CDATA[");
		sb.append(getTypePK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>workflowDefinitionName</column-name><column-value><![CDATA[");
		sb.append(getWorkflowDefinitionName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>workflowDefinitionVersion</column-name><column-value><![CDATA[");
		sb.append(getWorkflowDefinitionVersion());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = WorkflowDefinitionLink.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			WorkflowDefinitionLink.class
		};
	private long _workflowDefinitionLinkId;
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
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private long _typePK;
	private long _originalTypePK;
	private boolean _setOriginalTypePK;
	private String _workflowDefinitionName;
	private String _originalWorkflowDefinitionName;
	private int _workflowDefinitionVersion;
	private int _originalWorkflowDefinitionVersion;
	private boolean _setOriginalWorkflowDefinitionVersion;
	private transient ExpandoBridge _expandoBridge;
	private long _columnBitmask;
	private WorkflowDefinitionLink _escapedModelProxy;
}