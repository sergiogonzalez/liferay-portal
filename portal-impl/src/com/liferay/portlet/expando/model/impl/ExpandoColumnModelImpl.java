/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.expando.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnModel;
import com.liferay.portlet.expando.model.ExpandoColumnSoap;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the ExpandoColumn service. Represents a row in the &quot;ExpandoColumn&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.expando.model.ExpandoColumnModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ExpandoColumnImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoColumnImpl
 * @see com.liferay.portlet.expando.model.ExpandoColumn
 * @see com.liferay.portlet.expando.model.ExpandoColumnModel
 * @generated
 */
@JSON(strict = true)
public class ExpandoColumnModelImpl extends BaseModelImpl<ExpandoColumn>
	implements ExpandoColumnModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a expando column model instance should use the {@link com.liferay.portlet.expando.model.ExpandoColumn} interface instead.
	 */
	public static final String TABLE_NAME = "ExpandoColumn";
	public static final Object[][] TABLE_COLUMNS = {
			{ "columnId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "tableId", Types.BIGINT },
			{ "name", Types.VARCHAR },
			{ "type_", Types.INTEGER },
			{ "defaultData", Types.VARCHAR },
			{ "typeSettings", Types.CLOB }
		};
	public static final String TABLE_SQL_CREATE = "create table ExpandoColumn (columnId LONG not null primary key,companyId LONG,tableId LONG,name VARCHAR(75) null,type_ INTEGER,defaultData STRING null,typeSettings TEXT null)";
	public static final String TABLE_SQL_DROP = "drop table ExpandoColumn";
	public static final String ORDER_BY_JPQL = " ORDER BY expandoColumn.name ASC";
	public static final String ORDER_BY_SQL = " ORDER BY ExpandoColumn.name ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.expando.model.ExpandoColumn"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.expando.model.ExpandoColumn"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portlet.expando.model.ExpandoColumn"),
			true);
	public static long NAME_COLUMN_BITMASK = 1L;
	public static long TABLEID_COLUMN_BITMASK = 2L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static ExpandoColumn toModel(ExpandoColumnSoap soapModel) {
		ExpandoColumn model = new ExpandoColumnImpl();

		model.setColumnId(soapModel.getColumnId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setTableId(soapModel.getTableId());
		model.setName(soapModel.getName());
		model.setType(soapModel.getType());
		model.setDefaultData(soapModel.getDefaultData());
		model.setTypeSettings(soapModel.getTypeSettings());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<ExpandoColumn> toModels(ExpandoColumnSoap[] soapModels) {
		List<ExpandoColumn> models = new ArrayList<ExpandoColumn>(soapModels.length);

		for (ExpandoColumnSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.expando.model.ExpandoColumn"));

	public ExpandoColumnModelImpl() {
	}

	public long getPrimaryKey() {
		return _columnId;
	}

	public void setPrimaryKey(long primaryKey) {
		setColumnId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_columnId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return ExpandoColumn.class;
	}

	public String getModelClassName() {
		return ExpandoColumn.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("columnId", getColumnId());
		attributes.put("companyId", getCompanyId());
		attributes.put("tableId", getTableId());
		attributes.put("name", getName());
		attributes.put("type", getType());
		attributes.put("defaultData", getDefaultData());
		attributes.put("typeSettings", getTypeSettings());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long columnId = (Long)attributes.get("columnId");

		if (columnId != null) {
			setColumnId(columnId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long tableId = (Long)attributes.get("tableId");

		if (tableId != null) {
			setTableId(tableId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String defaultData = (String)attributes.get("defaultData");

		if (defaultData != null) {
			setDefaultData(defaultData);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}
	}

	@JSON
	public long getColumnId() {
		return _columnId;
	}

	public void setColumnId(long columnId) {
		_columnId = columnId;
	}

	@JSON
	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@JSON
	public long getTableId() {
		return _tableId;
	}

	public void setTableId(long tableId) {
		_columnBitmask |= TABLEID_COLUMN_BITMASK;

		if (!_setOriginalTableId) {
			_setOriginalTableId = true;

			_originalTableId = _tableId;
		}

		_tableId = tableId;
	}

	public long getOriginalTableId() {
		return _originalTableId;
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
		_columnBitmask = -1L;

		if (_originalName == null) {
			_originalName = _name;
		}

		_name = name;
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
	}

	@JSON
	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	@JSON
	public String getDefaultData() {
		if (_defaultData == null) {
			return StringPool.BLANK;
		}
		else {
			return _defaultData;
		}
	}

	public void setDefaultData(String defaultData) {
		_defaultData = defaultData;
	}

	@JSON
	public String getTypeSettings() {
		if (_typeSettings == null) {
			return StringPool.BLANK;
		}
		else {
			return _typeSettings;
		}
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoColumn toEscapedModel() {
		if (_escapedModelProxy == null) {
			_escapedModelProxy = (ExpandoColumn)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelProxyInterfaces,
					new AutoEscapeBeanHandler(this));
		}

		return _escapedModelProxy;
	}

	@Override
	public Object clone() {
		ExpandoColumnImpl expandoColumnImpl = new ExpandoColumnImpl();

		expandoColumnImpl.setColumnId(getColumnId());
		expandoColumnImpl.setCompanyId(getCompanyId());
		expandoColumnImpl.setTableId(getTableId());
		expandoColumnImpl.setName(getName());
		expandoColumnImpl.setType(getType());
		expandoColumnImpl.setDefaultData(getDefaultData());
		expandoColumnImpl.setTypeSettings(getTypeSettings());

		expandoColumnImpl.resetOriginalValues();

		return expandoColumnImpl;
	}

	public int compareTo(ExpandoColumn expandoColumn) {
		int value = 0;

		value = getName().compareTo(expandoColumn.getName());

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

		ExpandoColumn expandoColumn = null;

		try {
			expandoColumn = (ExpandoColumn)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = expandoColumn.getPrimaryKey();

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
		ExpandoColumnModelImpl expandoColumnModelImpl = this;

		expandoColumnModelImpl._originalTableId = expandoColumnModelImpl._tableId;

		expandoColumnModelImpl._setOriginalTableId = false;

		expandoColumnModelImpl._originalName = expandoColumnModelImpl._name;

		expandoColumnModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<ExpandoColumn> toCacheModel() {
		ExpandoColumnCacheModel expandoColumnCacheModel = new ExpandoColumnCacheModel();

		expandoColumnCacheModel.columnId = getColumnId();

		expandoColumnCacheModel.companyId = getCompanyId();

		expandoColumnCacheModel.tableId = getTableId();

		expandoColumnCacheModel.name = getName();

		String name = expandoColumnCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			expandoColumnCacheModel.name = null;
		}

		expandoColumnCacheModel.type = getType();

		expandoColumnCacheModel.defaultData = getDefaultData();

		String defaultData = expandoColumnCacheModel.defaultData;

		if ((defaultData != null) && (defaultData.length() == 0)) {
			expandoColumnCacheModel.defaultData = null;
		}

		expandoColumnCacheModel.typeSettings = getTypeSettings();

		String typeSettings = expandoColumnCacheModel.typeSettings;

		if ((typeSettings != null) && (typeSettings.length() == 0)) {
			expandoColumnCacheModel.typeSettings = null;
		}

		return expandoColumnCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{columnId=");
		sb.append(getColumnId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", tableId=");
		sb.append(getTableId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", defaultData=");
		sb.append(getDefaultData());
		sb.append(", typeSettings=");
		sb.append(getTypeSettings());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(25);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.expando.model.ExpandoColumn");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>columnId</column-name><column-value><![CDATA[");
		sb.append(getColumnId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>tableId</column-name><column-value><![CDATA[");
		sb.append(getTableId());
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
			"<column><column-name>defaultData</column-name><column-value><![CDATA[");
		sb.append(getDefaultData());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>typeSettings</column-name><column-value><![CDATA[");
		sb.append(getTypeSettings());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = ExpandoColumn.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			ExpandoColumn.class
		};
	private long _columnId;
	private long _companyId;
	private long _tableId;
	private long _originalTableId;
	private boolean _setOriginalTableId;
	private String _name;
	private String _originalName;
	private int _type;
	private String _defaultData;
	private String _typeSettings;
	private long _columnBitmask;
	private ExpandoColumn _escapedModelProxy;
}