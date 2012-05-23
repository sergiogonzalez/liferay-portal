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

package com.liferay.portlet.dynamicdatamapping.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLinkModel;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the DDMStructureLink service. Represents a row in the &quot;DDMStructureLink&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.dynamicdatamapping.model.DDMStructureLinkModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DDMStructureLinkImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLinkImpl
 * @see com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink
 * @see com.liferay.portlet.dynamicdatamapping.model.DDMStructureLinkModel
 * @generated
 */
public class DDMStructureLinkModelImpl extends BaseModelImpl<DDMStructureLink>
	implements DDMStructureLinkModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a d d m structure link model instance should use the {@link com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink} interface instead.
	 */
	public static final String TABLE_NAME = "DDMStructureLink";
	public static final Object[][] TABLE_COLUMNS = {
			{ "structureLinkId", Types.BIGINT },
			{ "classNameId", Types.BIGINT },
			{ "classPK", Types.BIGINT },
			{ "structureId", Types.BIGINT }
		};
	public static final String TABLE_SQL_CREATE = "create table DDMStructureLink (structureLinkId LONG not null primary key,classNameId LONG,classPK LONG,structureId LONG)";
	public static final String TABLE_SQL_DROP = "drop table DDMStructureLink";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink"),
			true);
	public static long CLASSNAMEID_COLUMN_BITMASK = 1L;
	public static long CLASSPK_COLUMN_BITMASK = 2L;
	public static long STRUCTUREID_COLUMN_BITMASK = 4L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink"));

	public DDMStructureLinkModelImpl() {
	}

	public long getPrimaryKey() {
		return _structureLinkId;
	}

	public void setPrimaryKey(long primaryKey) {
		setStructureLinkId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_structureLinkId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return DDMStructureLink.class;
	}

	public String getModelClassName() {
		return DDMStructureLink.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("structureLinkId", getStructureLinkId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("structureId", getStructureId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long structureLinkId = (Long)attributes.get("structureLinkId");

		if (structureLinkId != null) {
			setStructureLinkId(structureLinkId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long structureId = (Long)attributes.get("structureId");

		if (structureId != null) {
			setStructureId(structureId);
		}
	}

	public long getStructureLinkId() {
		return _structureLinkId;
	}

	public void setStructureLinkId(long structureLinkId) {
		_structureLinkId = structureLinkId;
	}

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
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

	public long getStructureId() {
		return _structureId;
	}

	public void setStructureId(long structureId) {
		_columnBitmask |= STRUCTUREID_COLUMN_BITMASK;

		if (!_setOriginalStructureId) {
			_setOriginalStructureId = true;

			_originalStructureId = _structureId;
		}

		_structureId = structureId;
	}

	public long getOriginalStructureId() {
		return _originalStructureId;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public DDMStructureLink toEscapedModel() {
		if (_escapedModelProxy == null) {
			_escapedModelProxy = (DDMStructureLink)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelProxyInterfaces,
					new AutoEscapeBeanHandler(this));
		}

		return _escapedModelProxy;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(0,
			DDMStructureLink.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		DDMStructureLinkImpl ddmStructureLinkImpl = new DDMStructureLinkImpl();

		ddmStructureLinkImpl.setStructureLinkId(getStructureLinkId());
		ddmStructureLinkImpl.setClassNameId(getClassNameId());
		ddmStructureLinkImpl.setClassPK(getClassPK());
		ddmStructureLinkImpl.setStructureId(getStructureId());

		ddmStructureLinkImpl.resetOriginalValues();

		return ddmStructureLinkImpl;
	}

	public int compareTo(DDMStructureLink ddmStructureLink) {
		long primaryKey = ddmStructureLink.getPrimaryKey();

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

		DDMStructureLink ddmStructureLink = null;

		try {
			ddmStructureLink = (DDMStructureLink)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = ddmStructureLink.getPrimaryKey();

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
		DDMStructureLinkModelImpl ddmStructureLinkModelImpl = this;

		ddmStructureLinkModelImpl._originalClassNameId = ddmStructureLinkModelImpl._classNameId;

		ddmStructureLinkModelImpl._setOriginalClassNameId = false;

		ddmStructureLinkModelImpl._originalClassPK = ddmStructureLinkModelImpl._classPK;

		ddmStructureLinkModelImpl._setOriginalClassPK = false;

		ddmStructureLinkModelImpl._originalStructureId = ddmStructureLinkModelImpl._structureId;

		ddmStructureLinkModelImpl._setOriginalStructureId = false;

		ddmStructureLinkModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<DDMStructureLink> toCacheModel() {
		DDMStructureLinkCacheModel ddmStructureLinkCacheModel = new DDMStructureLinkCacheModel();

		ddmStructureLinkCacheModel.structureLinkId = getStructureLinkId();

		ddmStructureLinkCacheModel.classNameId = getClassNameId();

		ddmStructureLinkCacheModel.classPK = getClassPK();

		ddmStructureLinkCacheModel.structureId = getStructureId();

		return ddmStructureLinkCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{structureLinkId=");
		sb.append(getStructureLinkId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", structureId=");
		sb.append(getStructureId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(16);

		sb.append("<model><model-name>");
		sb.append(
			"com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>structureLinkId</column-name><column-value><![CDATA[");
		sb.append(getStructureLinkId());
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
			"<column><column-name>structureId</column-name><column-value><![CDATA[");
		sb.append(getStructureId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = DDMStructureLink.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			DDMStructureLink.class
		};
	private long _structureLinkId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private long _structureId;
	private long _originalStructureId;
	private boolean _setOriginalStructureId;
	private long _columnBitmask;
	private DDMStructureLink _escapedModelProxy;
}