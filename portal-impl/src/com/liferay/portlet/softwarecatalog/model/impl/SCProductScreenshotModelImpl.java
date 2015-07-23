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

package com.liferay.portlet.softwarecatalog.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshotModel;

import java.io.Serializable;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the SCProductScreenshot service. Represents a row in the &quot;SCProductScreenshot&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.softwarecatalog.model.SCProductScreenshotModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SCProductScreenshotImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCProductScreenshotImpl
 * @see com.liferay.portlet.softwarecatalog.model.SCProductScreenshot
 * @see com.liferay.portlet.softwarecatalog.model.SCProductScreenshotModel
 * @generated
 */
@ProviderType
public class SCProductScreenshotModelImpl extends BaseModelImpl<SCProductScreenshot>
	implements SCProductScreenshotModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a s c product screenshot model instance should use the {@link com.liferay.portlet.softwarecatalog.model.SCProductScreenshot} interface instead.
	 */
	public static final String TABLE_NAME = "SCProductScreenshot";
	public static final Object[][] TABLE_COLUMNS = {
			{ "productScreenshotId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "productEntryId", Types.BIGINT },
			{ "thumbnailId", Types.BIGINT },
			{ "fullImageId", Types.BIGINT },
			{ "priority", Types.INTEGER }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("productScreenshotId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("productEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("thumbnailId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("fullImageId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("priority", Types.INTEGER);
	}

	public static final String TABLE_SQL_CREATE = "create table SCProductScreenshot (productScreenshotId LONG not null primary key,companyId LONG,groupId LONG,productEntryId LONG,thumbnailId LONG,fullImageId LONG,priority INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table SCProductScreenshot";
	public static final String ORDER_BY_JPQL = " ORDER BY scProductScreenshot.productEntryId ASC, scProductScreenshot.priority ASC";
	public static final String ORDER_BY_SQL = " ORDER BY SCProductScreenshot.productEntryId ASC, SCProductScreenshot.priority ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.SCProductScreenshot"), true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.SCProductScreenshot"), true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.SCProductScreenshot"), true);
	public static final long FULLIMAGEID_COLUMN_BITMASK = 1L;
	public static final long PRIORITY_COLUMN_BITMASK = 2L;
	public static final long PRODUCTENTRYID_COLUMN_BITMASK = 4L;
	public static final long THUMBNAILID_COLUMN_BITMASK = 8L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.SCProductScreenshot"));

	public SCProductScreenshotModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _productScreenshotId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setProductScreenshotId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _productScreenshotId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return SCProductScreenshot.class;
	}

	@Override
	public String getModelClassName() {
		return SCProductScreenshot.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("productScreenshotId", getProductScreenshotId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("productEntryId", getProductEntryId());
		attributes.put("thumbnailId", getThumbnailId());
		attributes.put("fullImageId", getFullImageId());
		attributes.put("priority", getPriority());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long productScreenshotId = (Long)attributes.get("productScreenshotId");

		if (productScreenshotId != null) {
			setProductScreenshotId(productScreenshotId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long productEntryId = (Long)attributes.get("productEntryId");

		if (productEntryId != null) {
			setProductEntryId(productEntryId);
		}

		Long thumbnailId = (Long)attributes.get("thumbnailId");

		if (thumbnailId != null) {
			setThumbnailId(thumbnailId);
		}

		Long fullImageId = (Long)attributes.get("fullImageId");

		if (fullImageId != null) {
			setFullImageId(fullImageId);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	@Override
	public long getProductScreenshotId() {
		return _productScreenshotId;
	}

	@Override
	public void setProductScreenshotId(long productScreenshotId) {
		_productScreenshotId = productScreenshotId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	@Override
	public long getProductEntryId() {
		return _productEntryId;
	}

	@Override
	public void setProductEntryId(long productEntryId) {
		_columnBitmask = -1L;

		if (!_setOriginalProductEntryId) {
			_setOriginalProductEntryId = true;

			_originalProductEntryId = _productEntryId;
		}

		_productEntryId = productEntryId;
	}

	public long getOriginalProductEntryId() {
		return _originalProductEntryId;
	}

	@Override
	public long getThumbnailId() {
		return _thumbnailId;
	}

	@Override
	public void setThumbnailId(long thumbnailId) {
		_columnBitmask |= THUMBNAILID_COLUMN_BITMASK;

		if (!_setOriginalThumbnailId) {
			_setOriginalThumbnailId = true;

			_originalThumbnailId = _thumbnailId;
		}

		_thumbnailId = thumbnailId;
	}

	public long getOriginalThumbnailId() {
		return _originalThumbnailId;
	}

	@Override
	public long getFullImageId() {
		return _fullImageId;
	}

	@Override
	public void setFullImageId(long fullImageId) {
		_columnBitmask |= FULLIMAGEID_COLUMN_BITMASK;

		if (!_setOriginalFullImageId) {
			_setOriginalFullImageId = true;

			_originalFullImageId = _fullImageId;
		}

		_fullImageId = fullImageId;
	}

	public long getOriginalFullImageId() {
		return _originalFullImageId;
	}

	@Override
	public int getPriority() {
		return _priority;
	}

	@Override
	public void setPriority(int priority) {
		_columnBitmask = -1L;

		if (!_setOriginalPriority) {
			_setOriginalPriority = true;

			_originalPriority = _priority;
		}

		_priority = priority;
	}

	public int getOriginalPriority() {
		return _originalPriority;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			SCProductScreenshot.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public SCProductScreenshot toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (SCProductScreenshot)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		SCProductScreenshotImpl scProductScreenshotImpl = new SCProductScreenshotImpl();

		scProductScreenshotImpl.setProductScreenshotId(getProductScreenshotId());
		scProductScreenshotImpl.setCompanyId(getCompanyId());
		scProductScreenshotImpl.setGroupId(getGroupId());
		scProductScreenshotImpl.setProductEntryId(getProductEntryId());
		scProductScreenshotImpl.setThumbnailId(getThumbnailId());
		scProductScreenshotImpl.setFullImageId(getFullImageId());
		scProductScreenshotImpl.setPriority(getPriority());

		scProductScreenshotImpl.resetOriginalValues();

		return scProductScreenshotImpl;
	}

	@Override
	public int compareTo(SCProductScreenshot scProductScreenshot) {
		int value = 0;

		if (getProductEntryId() < scProductScreenshot.getProductEntryId()) {
			value = -1;
		}
		else if (getProductEntryId() > scProductScreenshot.getProductEntryId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (getPriority() < scProductScreenshot.getPriority()) {
			value = -1;
		}
		else if (getPriority() > scProductScreenshot.getPriority()) {
			value = 1;
		}
		else {
			value = 0;
		}

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

		if (!(obj instanceof SCProductScreenshot)) {
			return false;
		}

		SCProductScreenshot scProductScreenshot = (SCProductScreenshot)obj;

		long primaryKey = scProductScreenshot.getPrimaryKey();

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
		SCProductScreenshotModelImpl scProductScreenshotModelImpl = this;

		scProductScreenshotModelImpl._originalProductEntryId = scProductScreenshotModelImpl._productEntryId;

		scProductScreenshotModelImpl._setOriginalProductEntryId = false;

		scProductScreenshotModelImpl._originalThumbnailId = scProductScreenshotModelImpl._thumbnailId;

		scProductScreenshotModelImpl._setOriginalThumbnailId = false;

		scProductScreenshotModelImpl._originalFullImageId = scProductScreenshotModelImpl._fullImageId;

		scProductScreenshotModelImpl._setOriginalFullImageId = false;

		scProductScreenshotModelImpl._originalPriority = scProductScreenshotModelImpl._priority;

		scProductScreenshotModelImpl._setOriginalPriority = false;

		scProductScreenshotModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<SCProductScreenshot> toCacheModel() {
		SCProductScreenshotCacheModel scProductScreenshotCacheModel = new SCProductScreenshotCacheModel();

		scProductScreenshotCacheModel.productScreenshotId = getProductScreenshotId();

		scProductScreenshotCacheModel.companyId = getCompanyId();

		scProductScreenshotCacheModel.groupId = getGroupId();

		scProductScreenshotCacheModel.productEntryId = getProductEntryId();

		scProductScreenshotCacheModel.thumbnailId = getThumbnailId();

		scProductScreenshotCacheModel.fullImageId = getFullImageId();

		scProductScreenshotCacheModel.priority = getPriority();

		return scProductScreenshotCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{productScreenshotId=");
		sb.append(getProductScreenshotId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", productEntryId=");
		sb.append(getProductEntryId());
		sb.append(", thumbnailId=");
		sb.append(getThumbnailId());
		sb.append(", fullImageId=");
		sb.append(getFullImageId());
		sb.append(", priority=");
		sb.append(getPriority());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(25);

		sb.append("<model><model-name>");
		sb.append(
			"com.liferay.portlet.softwarecatalog.model.SCProductScreenshot");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>productScreenshotId</column-name><column-value><![CDATA[");
		sb.append(getProductScreenshotId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>productEntryId</column-name><column-value><![CDATA[");
		sb.append(getProductEntryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>thumbnailId</column-name><column-value><![CDATA[");
		sb.append(getThumbnailId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>fullImageId</column-name><column-value><![CDATA[");
		sb.append(getFullImageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>priority</column-name><column-value><![CDATA[");
		sb.append(getPriority());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = SCProductScreenshot.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			SCProductScreenshot.class
		};
	private long _productScreenshotId;
	private long _companyId;
	private long _groupId;
	private long _productEntryId;
	private long _originalProductEntryId;
	private boolean _setOriginalProductEntryId;
	private long _thumbnailId;
	private long _originalThumbnailId;
	private boolean _setOriginalThumbnailId;
	private long _fullImageId;
	private long _originalFullImageId;
	private boolean _setOriginalFullImageId;
	private int _priority;
	private int _originalPriority;
	private boolean _setOriginalPriority;
	private long _columnBitmask;
	private SCProductScreenshot _escapedModel;
}