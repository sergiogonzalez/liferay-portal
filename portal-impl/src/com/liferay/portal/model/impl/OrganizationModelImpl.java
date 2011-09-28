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
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationModel;
import com.liferay.portal.model.OrganizationSoap;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * The base model implementation for the Organization service. Represents a row in the &quot;Organization_&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.OrganizationModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link OrganizationImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OrganizationImpl
 * @see com.liferay.portal.model.Organization
 * @see com.liferay.portal.model.OrganizationModel
 * @generated
 */
@JSON(strict = true)
public class OrganizationModelImpl extends BaseModelImpl<Organization>
	implements OrganizationModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a organization model instance should use the {@link com.liferay.portal.model.Organization} interface instead.
	 */
	public static final String TABLE_NAME = "Organization_";
	public static final Object[][] TABLE_COLUMNS = {
			{ "organizationId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "parentOrganizationId", Types.BIGINT },
			{ "leftOrganizationId", Types.BIGINT },
			{ "rightOrganizationId", Types.BIGINT },
			{ "name", Types.VARCHAR },
			{ "type_", Types.VARCHAR },
			{ "recursable", Types.BOOLEAN },
			{ "regionId", Types.BIGINT },
			{ "countryId", Types.BIGINT },
			{ "statusId", Types.INTEGER },
			{ "comments", Types.VARCHAR }
		};
	public static final String TABLE_SQL_CREATE = "create table Organization_ (organizationId LONG not null primary key,companyId LONG,parentOrganizationId LONG,leftOrganizationId LONG,rightOrganizationId LONG,name VARCHAR(100) null,type_ VARCHAR(75) null,recursable BOOLEAN,regionId LONG,countryId LONG,statusId INTEGER,comments STRING null)";
	public static final String TABLE_SQL_DROP = "drop table Organization_";
	public static final String ORDER_BY_JPQL = " ORDER BY organization.name ASC";
	public static final String ORDER_BY_SQL = " ORDER BY Organization_.name ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Organization"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Organization"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.model.Organization"),
			true);
	public static long COMPANYID_COLUMN_BITMASK = 1L;
	public static long PARENTORGANIZATIONID_COLUMN_BITMASK = 2L;
	public static long NAME_COLUMN_BITMASK = 4L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static Organization toModel(OrganizationSoap soapModel) {
		Organization model = new OrganizationImpl();

		model.setOrganizationId(soapModel.getOrganizationId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setParentOrganizationId(soapModel.getParentOrganizationId());
		model.setLeftOrganizationId(soapModel.getLeftOrganizationId());
		model.setRightOrganizationId(soapModel.getRightOrganizationId());
		model.setName(soapModel.getName());
		model.setType(soapModel.getType());
		model.setRecursable(soapModel.getRecursable());
		model.setRegionId(soapModel.getRegionId());
		model.setCountryId(soapModel.getCountryId());
		model.setStatusId(soapModel.getStatusId());
		model.setComments(soapModel.getComments());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<Organization> toModels(OrganizationSoap[] soapModels) {
		List<Organization> models = new ArrayList<Organization>(soapModels.length);

		for (OrganizationSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final String MAPPING_TABLE_GROUPS_ORGS_NAME = "Groups_Orgs";
	public static final Object[][] MAPPING_TABLE_GROUPS_ORGS_COLUMNS = {
			{ "groupId", Types.BIGINT },
			{ "organizationId", Types.BIGINT }
		};
	public static final String MAPPING_TABLE_GROUPS_ORGS_SQL_CREATE = "create table Groups_Orgs (groupId LONG not null,organizationId LONG not null,primary key (groupId, organizationId))";
	public static final boolean FINDER_CACHE_ENABLED_GROUPS_ORGS = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Groups_Orgs"), true);
	public static final String MAPPING_TABLE_USERS_ORGS_NAME = "Users_Orgs";
	public static final Object[][] MAPPING_TABLE_USERS_ORGS_COLUMNS = {
			{ "userId", Types.BIGINT },
			{ "organizationId", Types.BIGINT }
		};
	public static final String MAPPING_TABLE_USERS_ORGS_SQL_CREATE = "create table Users_Orgs (userId LONG not null,organizationId LONG not null,primary key (userId, organizationId))";
	public static final boolean FINDER_CACHE_ENABLED_USERS_ORGS = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Users_Orgs"), true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Organization"));

	public OrganizationModelImpl() {
	}

	public long getPrimaryKey() {
		return _organizationId;
	}

	public void setPrimaryKey(long primaryKey) {
		setOrganizationId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_organizationId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return Organization.class;
	}

	public String getModelClassName() {
		return Organization.class.getName();
	}

	@JSON
	public long getOrganizationId() {
		return _organizationId;
	}

	public void setOrganizationId(long organizationId) {
		_organizationId = organizationId;
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
	public long getParentOrganizationId() {
		return _parentOrganizationId;
	}

	public void setParentOrganizationId(long parentOrganizationId) {
		_columnBitmask |= PARENTORGANIZATIONID_COLUMN_BITMASK;

		if (!_setOriginalParentOrganizationId) {
			_setOriginalParentOrganizationId = true;

			_originalParentOrganizationId = _parentOrganizationId;
		}

		_parentOrganizationId = parentOrganizationId;
	}

	public long getOriginalParentOrganizationId() {
		return _originalParentOrganizationId;
	}

	@JSON
	public long getLeftOrganizationId() {
		return _leftOrganizationId;
	}

	public void setLeftOrganizationId(long leftOrganizationId) {
		_leftOrganizationId = leftOrganizationId;
	}

	@JSON
	public long getRightOrganizationId() {
		return _rightOrganizationId;
	}

	public void setRightOrganizationId(long rightOrganizationId) {
		_rightOrganizationId = rightOrganizationId;
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
	public String getType() {
		if (_type == null) {
			return StringPool.BLANK;
		}
		else {
			return _type;
		}
	}

	public void setType(String type) {
		_type = type;
	}

	@JSON
	public boolean getRecursable() {
		return _recursable;
	}

	public boolean isRecursable() {
		return _recursable;
	}

	public void setRecursable(boolean recursable) {
		_recursable = recursable;
	}

	@JSON
	public long getRegionId() {
		return _regionId;
	}

	public void setRegionId(long regionId) {
		_regionId = regionId;
	}

	@JSON
	public long getCountryId() {
		return _countryId;
	}

	public void setCountryId(long countryId) {
		_countryId = countryId;
	}

	@JSON
	public int getStatusId() {
		return _statusId;
	}

	public void setStatusId(int statusId) {
		_statusId = statusId;
	}

	@JSON
	public String getComments() {
		if (_comments == null) {
			return StringPool.BLANK;
		}
		else {
			return _comments;
		}
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public Organization toEscapedModel() {
		if (isEscapedModel()) {
			return (Organization)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (Organization)ProxyUtil.newProxyInstance(_classLoader,
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
					Organization.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		OrganizationImpl organizationImpl = new OrganizationImpl();

		organizationImpl.setOrganizationId(getOrganizationId());
		organizationImpl.setCompanyId(getCompanyId());
		organizationImpl.setParentOrganizationId(getParentOrganizationId());
		organizationImpl.setLeftOrganizationId(getLeftOrganizationId());
		organizationImpl.setRightOrganizationId(getRightOrganizationId());
		organizationImpl.setName(getName());
		organizationImpl.setType(getType());
		organizationImpl.setRecursable(getRecursable());
		organizationImpl.setRegionId(getRegionId());
		organizationImpl.setCountryId(getCountryId());
		organizationImpl.setStatusId(getStatusId());
		organizationImpl.setComments(getComments());

		organizationImpl.resetOriginalValues();

		return organizationImpl;
	}

	public int compareTo(Organization organization) {
		int value = 0;

		value = getName().compareTo(organization.getName());

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

		Organization organization = null;

		try {
			organization = (Organization)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = organization.getPrimaryKey();

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
		OrganizationModelImpl organizationModelImpl = this;

		organizationModelImpl._originalCompanyId = organizationModelImpl._companyId;

		organizationModelImpl._setOriginalCompanyId = false;

		organizationModelImpl._originalParentOrganizationId = organizationModelImpl._parentOrganizationId;

		organizationModelImpl._setOriginalParentOrganizationId = false;

		organizationModelImpl._originalName = organizationModelImpl._name;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<Organization> toCacheModel() {
		OrganizationCacheModel organizationCacheModel = new OrganizationCacheModel();

		organizationCacheModel.organizationId = getOrganizationId();

		organizationCacheModel.companyId = getCompanyId();

		organizationCacheModel.parentOrganizationId = getParentOrganizationId();

		organizationCacheModel.leftOrganizationId = getLeftOrganizationId();

		organizationCacheModel.rightOrganizationId = getRightOrganizationId();

		organizationCacheModel.name = getName();

		String name = organizationCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			organizationCacheModel.name = null;
		}

		organizationCacheModel.type = getType();

		String type = organizationCacheModel.type;

		if ((type != null) && (type.length() == 0)) {
			organizationCacheModel.type = null;
		}

		organizationCacheModel.recursable = getRecursable();

		organizationCacheModel.regionId = getRegionId();

		organizationCacheModel.countryId = getCountryId();

		organizationCacheModel.statusId = getStatusId();

		organizationCacheModel.comments = getComments();

		String comments = organizationCacheModel.comments;

		if ((comments != null) && (comments.length() == 0)) {
			organizationCacheModel.comments = null;
		}

		return organizationCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{organizationId=");
		sb.append(getOrganizationId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", parentOrganizationId=");
		sb.append(getParentOrganizationId());
		sb.append(", leftOrganizationId=");
		sb.append(getLeftOrganizationId());
		sb.append(", rightOrganizationId=");
		sb.append(getRightOrganizationId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", recursable=");
		sb.append(getRecursable());
		sb.append(", regionId=");
		sb.append(getRegionId());
		sb.append(", countryId=");
		sb.append(getCountryId());
		sb.append(", statusId=");
		sb.append(getStatusId());
		sb.append(", comments=");
		sb.append(getComments());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(40);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Organization");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>organizationId</column-name><column-value><![CDATA[");
		sb.append(getOrganizationId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>parentOrganizationId</column-name><column-value><![CDATA[");
		sb.append(getParentOrganizationId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>leftOrganizationId</column-name><column-value><![CDATA[");
		sb.append(getLeftOrganizationId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>rightOrganizationId</column-name><column-value><![CDATA[");
		sb.append(getRightOrganizationId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>recursable</column-name><column-value><![CDATA[");
		sb.append(getRecursable());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>regionId</column-name><column-value><![CDATA[");
		sb.append(getRegionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>countryId</column-name><column-value><![CDATA[");
		sb.append(getCountryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusId</column-name><column-value><![CDATA[");
		sb.append(getStatusId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>comments</column-name><column-value><![CDATA[");
		sb.append(getComments());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = Organization.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			Organization.class
		};
	private long _organizationId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _parentOrganizationId;
	private long _originalParentOrganizationId;
	private boolean _setOriginalParentOrganizationId;
	private long _leftOrganizationId;
	private long _rightOrganizationId;
	private String _name;
	private String _originalName;
	private String _type;
	private boolean _recursable;
	private long _regionId;
	private long _countryId;
	private int _statusId;
	private String _comments;
	private transient ExpandoBridge _expandoBridge;
	private long _columnBitmask;
	private Organization _escapedModelProxy;
}