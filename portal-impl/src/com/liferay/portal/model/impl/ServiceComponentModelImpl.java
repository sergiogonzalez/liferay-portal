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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ServiceComponent;
import com.liferay.portal.model.ServiceComponentModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the ServiceComponent service. Represents a row in the &quot;ServiceComponent&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.ServiceComponentModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ServiceComponentImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ServiceComponentImpl
 * @see com.liferay.portal.model.ServiceComponent
 * @see com.liferay.portal.model.ServiceComponentModel
 * @generated
 */
@ProviderType
public class ServiceComponentModelImpl extends BaseModelImpl<ServiceComponent>
	implements ServiceComponentModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a service component model instance should use the {@link com.liferay.portal.model.ServiceComponent} interface instead.
	 */
	public static final String TABLE_NAME = "ServiceComponent";
	public static final Object[][] TABLE_COLUMNS = {
			{ "mvccVersion", Types.BIGINT },
			{ "serviceComponentId", Types.BIGINT },
			{ "buildNamespace", Types.VARCHAR },
			{ "buildNumber", Types.BIGINT },
			{ "buildDate", Types.BIGINT },
			{ "data_", Types.CLOB }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("serviceComponentId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("buildNamespace", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("buildNumber", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("buildDate", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("data_", Types.CLOB);
	}

	public static final String TABLE_SQL_CREATE = "create table ServiceComponent (mvccVersion LONG default 0,serviceComponentId LONG not null primary key,buildNamespace VARCHAR(75) null,buildNumber LONG,buildDate LONG,data_ TEXT null)";
	public static final String TABLE_SQL_DROP = "drop table ServiceComponent";
	public static final String ORDER_BY_JPQL = " ORDER BY serviceComponent.buildNamespace DESC, serviceComponent.buildNumber DESC";
	public static final String ORDER_BY_SQL = " ORDER BY ServiceComponent.buildNamespace DESC, ServiceComponent.buildNumber DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.ServiceComponent"), true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.ServiceComponent"), true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.ServiceComponent"), true);
	public static final long BUILDNAMESPACE_COLUMN_BITMASK = 1L;
	public static final long BUILDNUMBER_COLUMN_BITMASK = 2L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.ServiceComponent"));

	public ServiceComponentModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _serviceComponentId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setServiceComponentId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _serviceComponentId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return ServiceComponent.class;
	}

	@Override
	public String getModelClassName() {
		return ServiceComponent.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("serviceComponentId", getServiceComponentId());
		attributes.put("buildNamespace", getBuildNamespace());
		attributes.put("buildNumber", getBuildNumber());
		attributes.put("buildDate", getBuildDate());
		attributes.put("data", getData());

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

		Long serviceComponentId = (Long)attributes.get("serviceComponentId");

		if (serviceComponentId != null) {
			setServiceComponentId(serviceComponentId);
		}

		String buildNamespace = (String)attributes.get("buildNamespace");

		if (buildNamespace != null) {
			setBuildNamespace(buildNamespace);
		}

		Long buildNumber = (Long)attributes.get("buildNumber");

		if (buildNumber != null) {
			setBuildNumber(buildNumber);
		}

		Long buildDate = (Long)attributes.get("buildDate");

		if (buildDate != null) {
			setBuildDate(buildDate);
		}

		String data = (String)attributes.get("data");

		if (data != null) {
			setData(data);
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
	public long getServiceComponentId() {
		return _serviceComponentId;
	}

	@Override
	public void setServiceComponentId(long serviceComponentId) {
		_serviceComponentId = serviceComponentId;
	}

	@Override
	public String getBuildNamespace() {
		if (_buildNamespace == null) {
			return StringPool.BLANK;
		}
		else {
			return _buildNamespace;
		}
	}

	@Override
	public void setBuildNamespace(String buildNamespace) {
		_columnBitmask = -1L;

		if (_originalBuildNamespace == null) {
			_originalBuildNamespace = _buildNamespace;
		}

		_buildNamespace = buildNamespace;
	}

	public String getOriginalBuildNamespace() {
		return GetterUtil.getString(_originalBuildNamespace);
	}

	@Override
	public long getBuildNumber() {
		return _buildNumber;
	}

	@Override
	public void setBuildNumber(long buildNumber) {
		_columnBitmask = -1L;

		if (!_setOriginalBuildNumber) {
			_setOriginalBuildNumber = true;

			_originalBuildNumber = _buildNumber;
		}

		_buildNumber = buildNumber;
	}

	public long getOriginalBuildNumber() {
		return _originalBuildNumber;
	}

	@Override
	public long getBuildDate() {
		return _buildDate;
	}

	@Override
	public void setBuildDate(long buildDate) {
		_buildDate = buildDate;
	}

	@Override
	public String getData() {
		if (_data == null) {
			return StringPool.BLANK;
		}
		else {
			return _data;
		}
	}

	@Override
	public void setData(String data) {
		_data = data;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(0,
			ServiceComponent.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public ServiceComponent toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (ServiceComponent)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		ServiceComponentImpl serviceComponentImpl = new ServiceComponentImpl();

		serviceComponentImpl.setMvccVersion(getMvccVersion());
		serviceComponentImpl.setServiceComponentId(getServiceComponentId());
		serviceComponentImpl.setBuildNamespace(getBuildNamespace());
		serviceComponentImpl.setBuildNumber(getBuildNumber());
		serviceComponentImpl.setBuildDate(getBuildDate());
		serviceComponentImpl.setData(getData());

		serviceComponentImpl.resetOriginalValues();

		return serviceComponentImpl;
	}

	@Override
	public int compareTo(ServiceComponent serviceComponent) {
		int value = 0;

		value = getBuildNamespace()
					.compareTo(serviceComponent.getBuildNamespace());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		if (getBuildNumber() < serviceComponent.getBuildNumber()) {
			value = -1;
		}
		else if (getBuildNumber() > serviceComponent.getBuildNumber()) {
			value = 1;
		}
		else {
			value = 0;
		}

		value = value * -1;

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

		if (!(obj instanceof ServiceComponent)) {
			return false;
		}

		ServiceComponent serviceComponent = (ServiceComponent)obj;

		long primaryKey = serviceComponent.getPrimaryKey();

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
		ServiceComponentModelImpl serviceComponentModelImpl = this;

		serviceComponentModelImpl._originalBuildNamespace = serviceComponentModelImpl._buildNamespace;

		serviceComponentModelImpl._originalBuildNumber = serviceComponentModelImpl._buildNumber;

		serviceComponentModelImpl._setOriginalBuildNumber = false;

		serviceComponentModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<ServiceComponent> toCacheModel() {
		ServiceComponentCacheModel serviceComponentCacheModel = new ServiceComponentCacheModel();

		serviceComponentCacheModel.mvccVersion = getMvccVersion();

		serviceComponentCacheModel.serviceComponentId = getServiceComponentId();

		serviceComponentCacheModel.buildNamespace = getBuildNamespace();

		String buildNamespace = serviceComponentCacheModel.buildNamespace;

		if ((buildNamespace != null) && (buildNamespace.length() == 0)) {
			serviceComponentCacheModel.buildNamespace = null;
		}

		serviceComponentCacheModel.buildNumber = getBuildNumber();

		serviceComponentCacheModel.buildDate = getBuildDate();

		serviceComponentCacheModel.data = getData();

		String data = serviceComponentCacheModel.data;

		if ((data != null) && (data.length() == 0)) {
			serviceComponentCacheModel.data = null;
		}

		return serviceComponentCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{mvccVersion=");
		sb.append(getMvccVersion());
		sb.append(", serviceComponentId=");
		sb.append(getServiceComponentId());
		sb.append(", buildNamespace=");
		sb.append(getBuildNamespace());
		sb.append(", buildNumber=");
		sb.append(getBuildNumber());
		sb.append(", buildDate=");
		sb.append(getBuildDate());
		sb.append(", data=");
		sb.append(getData());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(22);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.ServiceComponent");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>mvccVersion</column-name><column-value><![CDATA[");
		sb.append(getMvccVersion());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>serviceComponentId</column-name><column-value><![CDATA[");
		sb.append(getServiceComponentId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>buildNamespace</column-name><column-value><![CDATA[");
		sb.append(getBuildNamespace());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>buildNumber</column-name><column-value><![CDATA[");
		sb.append(getBuildNumber());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>buildDate</column-name><column-value><![CDATA[");
		sb.append(getBuildDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>data</column-name><column-value><![CDATA[");
		sb.append(getData());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = ServiceComponent.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			ServiceComponent.class
		};
	private long _mvccVersion;
	private long _serviceComponentId;
	private String _buildNamespace;
	private String _originalBuildNamespace;
	private long _buildNumber;
	private long _originalBuildNumber;
	private boolean _setOriginalBuildNumber;
	private long _buildDate;
	private String _data;
	private long _columnBitmask;
	private ServiceComponent _escapedModel;
}