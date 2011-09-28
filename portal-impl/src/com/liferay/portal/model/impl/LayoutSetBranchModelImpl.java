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
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.LayoutSetBranchModel;
import com.liferay.portal.model.LayoutSetBranchSoap;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The base model implementation for the LayoutSetBranch service. Represents a row in the &quot;LayoutSetBranch&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.LayoutSetBranchModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link LayoutSetBranchImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetBranchImpl
 * @see com.liferay.portal.model.LayoutSetBranch
 * @see com.liferay.portal.model.LayoutSetBranchModel
 * @generated
 */
@JSON(strict = true)
public class LayoutSetBranchModelImpl extends BaseModelImpl<LayoutSetBranch>
	implements LayoutSetBranchModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a layout set branch model instance should use the {@link com.liferay.portal.model.LayoutSetBranch} interface instead.
	 */
	public static final String TABLE_NAME = "LayoutSetBranch";
	public static final Object[][] TABLE_COLUMNS = {
			{ "layoutSetBranchId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "privateLayout", Types.BOOLEAN },
			{ "name", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "master", Types.BOOLEAN }
		};
	public static final String TABLE_SQL_CREATE = "create table LayoutSetBranch (layoutSetBranchId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,privateLayout BOOLEAN,name VARCHAR(75) null,description STRING null,master BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table LayoutSetBranch";
	public static final String ORDER_BY_JPQL = " ORDER BY layoutSetBranch.name ASC";
	public static final String ORDER_BY_SQL = " ORDER BY LayoutSetBranch.name ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.LayoutSetBranch"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.LayoutSetBranch"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.model.LayoutSetBranch"),
			true);
	public static long GROUPID_COLUMN_BITMASK = 1L;
	public static long NAME_COLUMN_BITMASK = 2L;
	public static long PRIVATELAYOUT_COLUMN_BITMASK = 4L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static LayoutSetBranch toModel(LayoutSetBranchSoap soapModel) {
		LayoutSetBranch model = new LayoutSetBranchImpl();

		model.setLayoutSetBranchId(soapModel.getLayoutSetBranchId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setPrivateLayout(soapModel.getPrivateLayout());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setMaster(soapModel.getMaster());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<LayoutSetBranch> toModels(
		LayoutSetBranchSoap[] soapModels) {
		List<LayoutSetBranch> models = new ArrayList<LayoutSetBranch>(soapModels.length);

		for (LayoutSetBranchSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.LayoutSetBranch"));

	public LayoutSetBranchModelImpl() {
	}

	public long getPrimaryKey() {
		return _layoutSetBranchId;
	}

	public void setPrimaryKey(long primaryKey) {
		setLayoutSetBranchId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_layoutSetBranchId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return LayoutSetBranch.class;
	}

	public String getModelClassName() {
		return LayoutSetBranch.class.getName();
	}

	@JSON
	public long getLayoutSetBranchId() {
		return _layoutSetBranchId;
	}

	public void setLayoutSetBranchId(long layoutSetBranchId) {
		_layoutSetBranchId = layoutSetBranchId;
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
		_companyId = companyId;
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
	public boolean getPrivateLayout() {
		return _privateLayout;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public void setPrivateLayout(boolean privateLayout) {
		_columnBitmask |= PRIVATELAYOUT_COLUMN_BITMASK;

		if (!_setOriginalPrivateLayout) {
			_setOriginalPrivateLayout = true;

			_originalPrivateLayout = _privateLayout;
		}

		_privateLayout = privateLayout;
	}

	public boolean getOriginalPrivateLayout() {
		return _originalPrivateLayout;
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

	@JSON
	public boolean getMaster() {
		return _master;
	}

	public boolean isMaster() {
		return _master;
	}

	public void setMaster(boolean master) {
		_master = master;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public LayoutSetBranch toEscapedModel() {
		if (isEscapedModel()) {
			return (LayoutSetBranch)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (LayoutSetBranch)ProxyUtil.newProxyInstance(_classLoader,
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
					LayoutSetBranch.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		LayoutSetBranchImpl layoutSetBranchImpl = new LayoutSetBranchImpl();

		layoutSetBranchImpl.setLayoutSetBranchId(getLayoutSetBranchId());
		layoutSetBranchImpl.setGroupId(getGroupId());
		layoutSetBranchImpl.setCompanyId(getCompanyId());
		layoutSetBranchImpl.setUserId(getUserId());
		layoutSetBranchImpl.setUserName(getUserName());
		layoutSetBranchImpl.setCreateDate(getCreateDate());
		layoutSetBranchImpl.setModifiedDate(getModifiedDate());
		layoutSetBranchImpl.setPrivateLayout(getPrivateLayout());
		layoutSetBranchImpl.setName(getName());
		layoutSetBranchImpl.setDescription(getDescription());
		layoutSetBranchImpl.setMaster(getMaster());

		layoutSetBranchImpl.resetOriginalValues();

		return layoutSetBranchImpl;
	}

	public int compareTo(LayoutSetBranch layoutSetBranch) {
		int value = 0;

		value = getName().compareTo(layoutSetBranch.getName());

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

		LayoutSetBranch layoutSetBranch = null;

		try {
			layoutSetBranch = (LayoutSetBranch)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = layoutSetBranch.getPrimaryKey();

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
		LayoutSetBranchModelImpl layoutSetBranchModelImpl = this;

		layoutSetBranchModelImpl._originalGroupId = layoutSetBranchModelImpl._groupId;

		layoutSetBranchModelImpl._setOriginalGroupId = false;

		layoutSetBranchModelImpl._originalPrivateLayout = layoutSetBranchModelImpl._privateLayout;

		layoutSetBranchModelImpl._setOriginalPrivateLayout = false;

		layoutSetBranchModelImpl._originalName = layoutSetBranchModelImpl._name;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<LayoutSetBranch> toCacheModel() {
		LayoutSetBranchCacheModel layoutSetBranchCacheModel = new LayoutSetBranchCacheModel();

		layoutSetBranchCacheModel.layoutSetBranchId = getLayoutSetBranchId();

		layoutSetBranchCacheModel.groupId = getGroupId();

		layoutSetBranchCacheModel.companyId = getCompanyId();

		layoutSetBranchCacheModel.userId = getUserId();

		layoutSetBranchCacheModel.userName = getUserName();

		String userName = layoutSetBranchCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			layoutSetBranchCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			layoutSetBranchCacheModel.createDate = createDate.getTime();
		}
		else {
			layoutSetBranchCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			layoutSetBranchCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			layoutSetBranchCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		layoutSetBranchCacheModel.privateLayout = getPrivateLayout();

		layoutSetBranchCacheModel.name = getName();

		String name = layoutSetBranchCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			layoutSetBranchCacheModel.name = null;
		}

		layoutSetBranchCacheModel.description = getDescription();

		String description = layoutSetBranchCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			layoutSetBranchCacheModel.description = null;
		}

		layoutSetBranchCacheModel.master = getMaster();

		return layoutSetBranchCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{layoutSetBranchId=");
		sb.append(getLayoutSetBranchId());
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
		sb.append(", privateLayout=");
		sb.append(getPrivateLayout());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", master=");
		sb.append(getMaster());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(37);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.LayoutSetBranch");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>layoutSetBranchId</column-name><column-value><![CDATA[");
		sb.append(getLayoutSetBranchId());
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
			"<column><column-name>privateLayout</column-name><column-value><![CDATA[");
		sb.append(getPrivateLayout());
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
			"<column><column-name>master</column-name><column-value><![CDATA[");
		sb.append(getMaster());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = LayoutSetBranch.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			LayoutSetBranch.class
		};
	private long _layoutSetBranchId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _privateLayout;
	private boolean _originalPrivateLayout;
	private boolean _setOriginalPrivateLayout;
	private String _name;
	private String _originalName;
	private String _description;
	private boolean _master;
	private transient ExpandoBridge _expandoBridge;
	private long _columnBitmask;
	private LayoutSetBranch _escapedModelProxy;
}