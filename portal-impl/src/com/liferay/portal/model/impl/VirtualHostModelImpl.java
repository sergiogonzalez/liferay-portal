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
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.model.VirtualHostModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the VirtualHost service. Represents a row in the &quot;VirtualHost&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link VirtualHostModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link VirtualHostImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VirtualHostImpl
 * @see VirtualHost
 * @see VirtualHostModel
 * @generated
 */
@ProviderType
public class VirtualHostModelImpl extends BaseModelImpl<VirtualHost>
	implements VirtualHostModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a virtual host model instance should use the {@link VirtualHost} interface instead.
	 */
	public static final String TABLE_NAME = "VirtualHost";
	public static final Object[][] TABLE_COLUMNS = {
			{ "mvccVersion", Types.BIGINT },
			{ "virtualHostId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "layoutSetId", Types.BIGINT },
			{ "hostname", Types.VARCHAR }
		};
	public static final String TABLE_SQL_CREATE = "create table VirtualHost (mvccVersion LONG default 0,virtualHostId LONG not null primary key,companyId LONG,layoutSetId LONG,hostname VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table VirtualHost";
	public static final String ORDER_BY_JPQL = " ORDER BY virtualHost.virtualHostId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY VirtualHost.virtualHostId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.VirtualHost"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.VirtualHost"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.model.VirtualHost"),
			true);
	public static final long COMPANYID_COLUMN_BITMASK = 1L;
	public static final long HOSTNAME_COLUMN_BITMASK = 2L;
	public static final long LAYOUTSETID_COLUMN_BITMASK = 4L;
	public static final long VIRTUALHOSTID_COLUMN_BITMASK = 8L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.VirtualHost"));

	public VirtualHostModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _virtualHostId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setVirtualHostId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _virtualHostId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return VirtualHost.class;
	}

	@Override
	public String getModelClassName() {
		return VirtualHost.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("virtualHostId", getVirtualHostId());
		attributes.put("companyId", getCompanyId());
		attributes.put("layoutSetId", getLayoutSetId());
		attributes.put("hostname", getHostname());

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

		Long virtualHostId = (Long)attributes.get("virtualHostId");

		if (virtualHostId != null) {
			setVirtualHostId(virtualHostId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long layoutSetId = (Long)attributes.get("layoutSetId");

		if (layoutSetId != null) {
			setLayoutSetId(layoutSetId);
		}

		String hostname = (String)attributes.get("hostname");

		if (hostname != null) {
			setHostname(hostname);
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
	public long getVirtualHostId() {
		return _virtualHostId;
	}

	@Override
	public void setVirtualHostId(long virtualHostId) {
		_virtualHostId = virtualHostId;
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
	public long getLayoutSetId() {
		return _layoutSetId;
	}

	@Override
	public void setLayoutSetId(long layoutSetId) {
		_columnBitmask |= LAYOUTSETID_COLUMN_BITMASK;

		if (!_setOriginalLayoutSetId) {
			_setOriginalLayoutSetId = true;

			_originalLayoutSetId = _layoutSetId;
		}

		_layoutSetId = layoutSetId;
	}

	public long getOriginalLayoutSetId() {
		return _originalLayoutSetId;
	}

	@Override
	public String getHostname() {
		if (_hostname == null) {
			return StringPool.BLANK;
		}
		else {
			return _hostname;
		}
	}

	@Override
	public void setHostname(String hostname) {
		_columnBitmask |= HOSTNAME_COLUMN_BITMASK;

		if (_originalHostname == null) {
			_originalHostname = _hostname;
		}

		_hostname = hostname;
	}

	public String getOriginalHostname() {
		return GetterUtil.getString(_originalHostname);
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			VirtualHost.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public VirtualHost toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (VirtualHost)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		VirtualHostImpl virtualHostImpl = new VirtualHostImpl();

		virtualHostImpl.setMvccVersion(getMvccVersion());
		virtualHostImpl.setVirtualHostId(getVirtualHostId());
		virtualHostImpl.setCompanyId(getCompanyId());
		virtualHostImpl.setLayoutSetId(getLayoutSetId());
		virtualHostImpl.setHostname(getHostname());

		virtualHostImpl.resetOriginalValues();

		return virtualHostImpl;
	}

	@Override
	public int compareTo(VirtualHost virtualHost) {
		long primaryKey = virtualHost.getPrimaryKey();

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

		if (!(obj instanceof VirtualHost)) {
			return false;
		}

		VirtualHost virtualHost = (VirtualHost)obj;

		long primaryKey = virtualHost.getPrimaryKey();

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
		VirtualHostModelImpl virtualHostModelImpl = this;

		virtualHostModelImpl._originalCompanyId = virtualHostModelImpl._companyId;

		virtualHostModelImpl._setOriginalCompanyId = false;

		virtualHostModelImpl._originalLayoutSetId = virtualHostModelImpl._layoutSetId;

		virtualHostModelImpl._setOriginalLayoutSetId = false;

		virtualHostModelImpl._originalHostname = virtualHostModelImpl._hostname;

		virtualHostModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<VirtualHost> toCacheModel() {
		VirtualHostCacheModel virtualHostCacheModel = new VirtualHostCacheModel();

		virtualHostCacheModel.mvccVersion = getMvccVersion();

		virtualHostCacheModel.virtualHostId = getVirtualHostId();

		virtualHostCacheModel.companyId = getCompanyId();

		virtualHostCacheModel.layoutSetId = getLayoutSetId();

		virtualHostCacheModel.hostname = getHostname();

		String hostname = virtualHostCacheModel.hostname;

		if ((hostname != null) && (hostname.length() == 0)) {
			virtualHostCacheModel.hostname = null;
		}

		return virtualHostCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(getMvccVersion());
		sb.append(", virtualHostId=");
		sb.append(getVirtualHostId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", layoutSetId=");
		sb.append(getLayoutSetId());
		sb.append(", hostname=");
		sb.append(getHostname());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(19);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.VirtualHost");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>mvccVersion</column-name><column-value><![CDATA[");
		sb.append(getMvccVersion());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>virtualHostId</column-name><column-value><![CDATA[");
		sb.append(getVirtualHostId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>layoutSetId</column-name><column-value><![CDATA[");
		sb.append(getLayoutSetId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>hostname</column-name><column-value><![CDATA[");
		sb.append(getHostname());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = VirtualHost.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			VirtualHost.class
		};
	private long _mvccVersion;
	private long _virtualHostId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _layoutSetId;
	private long _originalLayoutSetId;
	private boolean _setOriginalLayoutSetId;
	private String _hostname;
	private String _originalHostname;
	private long _columnBitmask;
	private VirtualHost _escapedModel;
}