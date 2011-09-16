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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ClusterGroup;
import com.liferay.portal.model.ClusterGroupModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

/**
 * The base model implementation for the ClusterGroup service. Represents a row in the &quot;ClusterGroup&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.ClusterGroupModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ClusterGroupImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClusterGroupImpl
 * @see com.liferay.portal.model.ClusterGroup
 * @see com.liferay.portal.model.ClusterGroupModel
 * @generated
 */
public class ClusterGroupModelImpl extends BaseModelImpl<ClusterGroup>
	implements ClusterGroupModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cluster group model instance should use the {@link com.liferay.portal.model.ClusterGroup} interface instead.
	 */
	public static final String TABLE_NAME = "ClusterGroup";
	public static final Object[][] TABLE_COLUMNS = {
			{ "clusterGroupId", Types.BIGINT },
			{ "name", Types.VARCHAR },
			{ "clusterNodeIds", Types.VARCHAR },
			{ "wholeCluster", Types.BOOLEAN }
		};
	public static final String TABLE_SQL_CREATE = "create table ClusterGroup (clusterGroupId LONG not null primary key,name VARCHAR(75) null,clusterNodeIds VARCHAR(75) null,wholeCluster BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table ClusterGroup";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.ClusterGroup"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.ClusterGroup"),
			true);

	public Class<?> getModelClass() {
		return ClusterGroup.class;
	}

	public String getModelClassName() {
		return ClusterGroup.class.getName();
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ClusterGroup"));

	public ClusterGroupModelImpl() {
	}

	public long getPrimaryKey() {
		return _clusterGroupId;
	}

	public void setPrimaryKey(long primaryKey) {
		setClusterGroupId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_clusterGroupId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public long getClusterGroupId() {
		return _clusterGroupId;
	}

	public void setClusterGroupId(long clusterGroupId) {
		_clusterGroupId = clusterGroupId;
	}

	public String getName() {
		if (_name == null) {
			return StringPool.BLANK;
		}
		else {
			return _name;
		}
	}

	public void setName(String name) {
		_name = name;
	}

	public String getClusterNodeIds() {
		if (_clusterNodeIds == null) {
			return StringPool.BLANK;
		}
		else {
			return _clusterNodeIds;
		}
	}

	public void setClusterNodeIds(String clusterNodeIds) {
		_clusterNodeIds = clusterNodeIds;
	}

	public boolean getWholeCluster() {
		return _wholeCluster;
	}

	public boolean isWholeCluster() {
		return _wholeCluster;
	}

	public void setWholeCluster(boolean wholeCluster) {
		_wholeCluster = wholeCluster;
	}

	@Override
	public ClusterGroup toEscapedModel() {
		if (isEscapedModel()) {
			return (ClusterGroup)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (ClusterGroup)ProxyUtil.newProxyInstance(_classLoader,
						_escapedModelProxyInterfaces,
						new AutoEscapeBeanHandler(this));
			}

			return _escapedModelProxy;
		}
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					ClusterGroup.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		ClusterGroupImpl clusterGroupImpl = new ClusterGroupImpl();

		clusterGroupImpl.setClusterGroupId(getClusterGroupId());
		clusterGroupImpl.setName(getName());
		clusterGroupImpl.setClusterNodeIds(getClusterNodeIds());
		clusterGroupImpl.setWholeCluster(getWholeCluster());

		clusterGroupImpl.resetOriginalValues();

		return clusterGroupImpl;
	}

	public int compareTo(ClusterGroup clusterGroup) {
		long primaryKey = clusterGroup.getPrimaryKey();

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

		ClusterGroup clusterGroup = null;

		try {
			clusterGroup = (ClusterGroup)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = clusterGroup.getPrimaryKey();

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
	}

	@Override
	public CacheModel<ClusterGroup> toCacheModel() {
		ClusterGroupCacheModel clusterGroupCacheModel = new ClusterGroupCacheModel();

		clusterGroupCacheModel.clusterGroupId = getClusterGroupId();

		clusterGroupCacheModel.name = getName();

		String name = clusterGroupCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			clusterGroupCacheModel.name = null;
		}

		clusterGroupCacheModel.clusterNodeIds = getClusterNodeIds();

		String clusterNodeIds = clusterGroupCacheModel.clusterNodeIds;

		if ((clusterNodeIds != null) && (clusterNodeIds.length() == 0)) {
			clusterGroupCacheModel.clusterNodeIds = null;
		}

		clusterGroupCacheModel.wholeCluster = getWholeCluster();

		return clusterGroupCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{clusterGroupId=");
		sb.append(getClusterGroupId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", clusterNodeIds=");
		sb.append(getClusterNodeIds());
		sb.append(", wholeCluster=");
		sb.append(getWholeCluster());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(16);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.ClusterGroup");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>clusterGroupId</column-name><column-value><![CDATA[");
		sb.append(getClusterGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>clusterNodeIds</column-name><column-value><![CDATA[");
		sb.append(getClusterNodeIds());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>wholeCluster</column-name><column-value><![CDATA[");
		sb.append(getWholeCluster());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = ClusterGroup.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			ClusterGroup.class
		};
	private long _clusterGroupId;
	private String _name;
	private String _clusterNodeIds;
	private boolean _wholeCluster;
	private transient ExpandoBridge _expandoBridge;
	private ClusterGroup _escapedModelProxy;
}