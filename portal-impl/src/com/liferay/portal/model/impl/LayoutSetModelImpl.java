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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetModel;
import com.liferay.portal.kernel.model.LayoutSetSoap;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the LayoutSet service. Represents a row in the &quot;LayoutSet&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link LayoutSetModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link LayoutSetImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetImpl
 * @see LayoutSet
 * @see LayoutSetModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class LayoutSetModelImpl extends BaseModelImpl<LayoutSet>
	implements LayoutSetModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a layout set model instance should use the {@link LayoutSet} interface instead.
	 */
	public static final String TABLE_NAME = "LayoutSet";
	public static final Object[][] TABLE_COLUMNS = {
			{ "mvccVersion", Types.BIGINT },
			{ "layoutSetId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "privateLayout", Types.BOOLEAN },
			{ "logoId", Types.BIGINT },
			{ "themeId", Types.VARCHAR },
			{ "colorSchemeId", Types.VARCHAR },
			{ "css", Types.CLOB },
			{ "pageCount", Types.INTEGER },
			{ "settings_", Types.CLOB },
			{ "layoutSetPrototypeUuid", Types.VARCHAR },
			{ "layoutSetPrototypeLinkEnabled", Types.BOOLEAN }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("layoutSetId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("privateLayout", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("logoId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("themeId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("colorSchemeId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("css", Types.CLOB);
		TABLE_COLUMNS_MAP.put("pageCount", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("settings_", Types.CLOB);
		TABLE_COLUMNS_MAP.put("layoutSetPrototypeUuid", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("layoutSetPrototypeLinkEnabled", Types.BOOLEAN);
	}

	public static final String TABLE_SQL_CREATE = "create table LayoutSet (mvccVersion LONG default 0 not null,layoutSetId LONG not null primary key,groupId LONG,companyId LONG,createDate DATE null,modifiedDate DATE null,privateLayout BOOLEAN,logoId LONG,themeId VARCHAR(75) null,colorSchemeId VARCHAR(75) null,css TEXT null,pageCount INTEGER,settings_ TEXT null,layoutSetPrototypeUuid VARCHAR(75) null,layoutSetPrototypeLinkEnabled BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table LayoutSet";
	public static final String ORDER_BY_JPQL = " ORDER BY layoutSet.layoutSetId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY LayoutSet.layoutSetId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.kernel.model.LayoutSet"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.kernel.model.LayoutSet"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.LayoutSet"),
			true);
	public static final long GROUPID_COLUMN_BITMASK = 1L;
	public static final long LAYOUTSETPROTOTYPEUUID_COLUMN_BITMASK = 2L;
	public static final long LOGOID_COLUMN_BITMASK = 4L;
	public static final long PRIVATELAYOUT_COLUMN_BITMASK = 8L;
	public static final long LAYOUTSETID_COLUMN_BITMASK = 16L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static LayoutSet toModel(LayoutSetSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		LayoutSet model = new LayoutSetImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setLayoutSetId(soapModel.getLayoutSetId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setPrivateLayout(soapModel.getPrivateLayout());
		model.setLogoId(soapModel.getLogoId());
		model.setThemeId(soapModel.getThemeId());
		model.setColorSchemeId(soapModel.getColorSchemeId());
		model.setCss(soapModel.getCss());
		model.setPageCount(soapModel.getPageCount());
		model.setSettings(soapModel.getSettings());
		model.setLayoutSetPrototypeUuid(soapModel.getLayoutSetPrototypeUuid());
		model.setLayoutSetPrototypeLinkEnabled(soapModel.getLayoutSetPrototypeLinkEnabled());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<LayoutSet> toModels(LayoutSetSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<LayoutSet> models = new ArrayList<LayoutSet>(soapModels.length);

		for (LayoutSetSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.kernel.model.LayoutSet"));

	public LayoutSetModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _layoutSetId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setLayoutSetId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _layoutSetId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return LayoutSet.class;
	}

	@Override
	public String getModelClassName() {
		return LayoutSet.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("layoutSetId", getLayoutSetId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("privateLayout", isPrivateLayout());
		attributes.put("logoId", getLogoId());
		attributes.put("themeId", getThemeId());
		attributes.put("colorSchemeId", getColorSchemeId());
		attributes.put("css", getCss());
		attributes.put("pageCount", getPageCount());
		attributes.put("settings", getSettings());
		attributes.put("layoutSetPrototypeUuid", getLayoutSetPrototypeUuid());
		attributes.put("layoutSetPrototypeLinkEnabled",
			isLayoutSetPrototypeLinkEnabled());

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

		Long layoutSetId = (Long)attributes.get("layoutSetId");

		if (layoutSetId != null) {
			setLayoutSetId(layoutSetId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Boolean privateLayout = (Boolean)attributes.get("privateLayout");

		if (privateLayout != null) {
			setPrivateLayout(privateLayout);
		}

		Long logoId = (Long)attributes.get("logoId");

		if (logoId != null) {
			setLogoId(logoId);
		}

		String themeId = (String)attributes.get("themeId");

		if (themeId != null) {
			setThemeId(themeId);
		}

		String colorSchemeId = (String)attributes.get("colorSchemeId");

		if (colorSchemeId != null) {
			setColorSchemeId(colorSchemeId);
		}

		String css = (String)attributes.get("css");

		if (css != null) {
			setCss(css);
		}

		Integer pageCount = (Integer)attributes.get("pageCount");

		if (pageCount != null) {
			setPageCount(pageCount);
		}

		String settings = (String)attributes.get("settings");

		if (settings != null) {
			setSettings(settings);
		}

		String layoutSetPrototypeUuid = (String)attributes.get(
				"layoutSetPrototypeUuid");

		if (layoutSetPrototypeUuid != null) {
			setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
		}

		Boolean layoutSetPrototypeLinkEnabled = (Boolean)attributes.get(
				"layoutSetPrototypeLinkEnabled");

		if (layoutSetPrototypeLinkEnabled != null) {
			setLayoutSetPrototypeLinkEnabled(layoutSetPrototypeLinkEnabled);
		}
	}

	@JSON
	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@JSON
	@Override
	public long getLayoutSetId() {
		return _layoutSetId;
	}

	@Override
	public void setLayoutSetId(long layoutSetId) {
		_layoutSetId = layoutSetId;
	}

	@JSON
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

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public boolean getPrivateLayout() {
		return _privateLayout;
	}

	@JSON
	@Override
	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	@Override
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
	@Override
	public long getLogoId() {
		return _logoId;
	}

	@Override
	public void setLogoId(long logoId) {
		_columnBitmask |= LOGOID_COLUMN_BITMASK;

		if (!_setOriginalLogoId) {
			_setOriginalLogoId = true;

			_originalLogoId = _logoId;
		}

		_logoId = logoId;
	}

	public long getOriginalLogoId() {
		return _originalLogoId;
	}

	@JSON
	@Override
	public String getThemeId() {
		if (_themeId == null) {
			return "";
		}
		else {
			return _themeId;
		}
	}

	@Override
	public void setThemeId(String themeId) {
		_themeId = themeId;
	}

	@JSON
	@Override
	public String getColorSchemeId() {
		if (_colorSchemeId == null) {
			return "";
		}
		else {
			return _colorSchemeId;
		}
	}

	@Override
	public void setColorSchemeId(String colorSchemeId) {
		_colorSchemeId = colorSchemeId;
	}

	@JSON
	@Override
	public String getCss() {
		if (_css == null) {
			return "";
		}
		else {
			return _css;
		}
	}

	@Override
	public void setCss(String css) {
		_css = css;
	}

	@JSON
	@Override
	public int getPageCount() {
		return _pageCount;
	}

	@Override
	public void setPageCount(int pageCount) {
		_pageCount = pageCount;
	}

	@JSON
	@Override
	public String getSettings() {
		if (_settings == null) {
			return "";
		}
		else {
			return _settings;
		}
	}

	@Override
	public void setSettings(String settings) {
		_settings = settings;
	}

	@JSON
	@Override
	public String getLayoutSetPrototypeUuid() {
		if (_layoutSetPrototypeUuid == null) {
			return "";
		}
		else {
			return _layoutSetPrototypeUuid;
		}
	}

	@Override
	public void setLayoutSetPrototypeUuid(String layoutSetPrototypeUuid) {
		_columnBitmask |= LAYOUTSETPROTOTYPEUUID_COLUMN_BITMASK;

		if (_originalLayoutSetPrototypeUuid == null) {
			_originalLayoutSetPrototypeUuid = _layoutSetPrototypeUuid;
		}

		_layoutSetPrototypeUuid = layoutSetPrototypeUuid;
	}

	public String getOriginalLayoutSetPrototypeUuid() {
		return GetterUtil.getString(_originalLayoutSetPrototypeUuid);
	}

	@JSON
	@Override
	public boolean getLayoutSetPrototypeLinkEnabled() {
		return _layoutSetPrototypeLinkEnabled;
	}

	@JSON
	@Override
	public boolean isLayoutSetPrototypeLinkEnabled() {
		return _layoutSetPrototypeLinkEnabled;
	}

	@Override
	public void setLayoutSetPrototypeLinkEnabled(
		boolean layoutSetPrototypeLinkEnabled) {
		_layoutSetPrototypeLinkEnabled = layoutSetPrototypeLinkEnabled;
	}

	public java.lang.String getCompanyFallbackVirtualHostname() {
		return null;
	}

	public void setCompanyFallbackVirtualHostname(
		java.lang.String companyFallbackVirtualHostname) {
	}

	public java.lang.String getVirtualHostname() {
		return null;
	}

	public void setVirtualHostname(java.lang.String virtualHostname) {
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			LayoutSet.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public LayoutSet toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (LayoutSet)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		LayoutSetImpl layoutSetImpl = new LayoutSetImpl();

		layoutSetImpl.setMvccVersion(getMvccVersion());
		layoutSetImpl.setLayoutSetId(getLayoutSetId());
		layoutSetImpl.setGroupId(getGroupId());
		layoutSetImpl.setCompanyId(getCompanyId());
		layoutSetImpl.setCreateDate(getCreateDate());
		layoutSetImpl.setModifiedDate(getModifiedDate());
		layoutSetImpl.setPrivateLayout(isPrivateLayout());
		layoutSetImpl.setLogoId(getLogoId());
		layoutSetImpl.setThemeId(getThemeId());
		layoutSetImpl.setColorSchemeId(getColorSchemeId());
		layoutSetImpl.setCss(getCss());
		layoutSetImpl.setPageCount(getPageCount());
		layoutSetImpl.setSettings(getSettings());
		layoutSetImpl.setLayoutSetPrototypeUuid(getLayoutSetPrototypeUuid());
		layoutSetImpl.setLayoutSetPrototypeLinkEnabled(isLayoutSetPrototypeLinkEnabled());

		layoutSetImpl.resetOriginalValues();

		return layoutSetImpl;
	}

	@Override
	public int compareTo(LayoutSet layoutSet) {
		long primaryKey = layoutSet.getPrimaryKey();

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

		if (!(obj instanceof LayoutSet)) {
			return false;
		}

		LayoutSet layoutSet = (LayoutSet)obj;

		long primaryKey = layoutSet.getPrimaryKey();

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
		LayoutSetModelImpl layoutSetModelImpl = this;

		layoutSetModelImpl._originalGroupId = layoutSetModelImpl._groupId;

		layoutSetModelImpl._setOriginalGroupId = false;

		layoutSetModelImpl._setModifiedDate = false;

		layoutSetModelImpl._originalPrivateLayout = layoutSetModelImpl._privateLayout;

		layoutSetModelImpl._setOriginalPrivateLayout = false;

		layoutSetModelImpl._originalLogoId = layoutSetModelImpl._logoId;

		layoutSetModelImpl._setOriginalLogoId = false;

		layoutSetModelImpl._originalLayoutSetPrototypeUuid = layoutSetModelImpl._layoutSetPrototypeUuid;

		setCompanyFallbackVirtualHostname(null);

		setVirtualHostname(null);

		layoutSetModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<LayoutSet> toCacheModel() {
		LayoutSetCacheModel layoutSetCacheModel = new LayoutSetCacheModel();

		layoutSetCacheModel.mvccVersion = getMvccVersion();

		layoutSetCacheModel.layoutSetId = getLayoutSetId();

		layoutSetCacheModel.groupId = getGroupId();

		layoutSetCacheModel.companyId = getCompanyId();

		Date createDate = getCreateDate();

		if (createDate != null) {
			layoutSetCacheModel.createDate = createDate.getTime();
		}
		else {
			layoutSetCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			layoutSetCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			layoutSetCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		layoutSetCacheModel.privateLayout = isPrivateLayout();

		layoutSetCacheModel.logoId = getLogoId();

		layoutSetCacheModel.themeId = getThemeId();

		String themeId = layoutSetCacheModel.themeId;

		if ((themeId != null) && (themeId.length() == 0)) {
			layoutSetCacheModel.themeId = null;
		}

		layoutSetCacheModel.colorSchemeId = getColorSchemeId();

		String colorSchemeId = layoutSetCacheModel.colorSchemeId;

		if ((colorSchemeId != null) && (colorSchemeId.length() == 0)) {
			layoutSetCacheModel.colorSchemeId = null;
		}

		layoutSetCacheModel.css = getCss();

		String css = layoutSetCacheModel.css;

		if ((css != null) && (css.length() == 0)) {
			layoutSetCacheModel.css = null;
		}

		layoutSetCacheModel.pageCount = getPageCount();

		layoutSetCacheModel.settings = getSettings();

		String settings = layoutSetCacheModel.settings;

		if ((settings != null) && (settings.length() == 0)) {
			layoutSetCacheModel.settings = null;
		}

		layoutSetCacheModel.layoutSetPrototypeUuid = getLayoutSetPrototypeUuid();

		String layoutSetPrototypeUuid = layoutSetCacheModel.layoutSetPrototypeUuid;

		if ((layoutSetPrototypeUuid != null) &&
				(layoutSetPrototypeUuid.length() == 0)) {
			layoutSetCacheModel.layoutSetPrototypeUuid = null;
		}

		layoutSetCacheModel.layoutSetPrototypeLinkEnabled = isLayoutSetPrototypeLinkEnabled();

		layoutSetCacheModel._companyFallbackVirtualHostname = getCompanyFallbackVirtualHostname();

		layoutSetCacheModel._virtualHostname = getVirtualHostname();

		return layoutSetCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{mvccVersion=");
		sb.append(getMvccVersion());
		sb.append(", layoutSetId=");
		sb.append(getLayoutSetId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", privateLayout=");
		sb.append(isPrivateLayout());
		sb.append(", logoId=");
		sb.append(getLogoId());
		sb.append(", themeId=");
		sb.append(getThemeId());
		sb.append(", colorSchemeId=");
		sb.append(getColorSchemeId());
		sb.append(", css=");
		sb.append(getCss());
		sb.append(", pageCount=");
		sb.append(getPageCount());
		sb.append(", settings=");
		sb.append(getSettings());
		sb.append(", layoutSetPrototypeUuid=");
		sb.append(getLayoutSetPrototypeUuid());
		sb.append(", layoutSetPrototypeLinkEnabled=");
		sb.append(isLayoutSetPrototypeLinkEnabled());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(49);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.kernel.model.LayoutSet");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>mvccVersion</column-name><column-value><![CDATA[");
		sb.append(getMvccVersion());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>layoutSetId</column-name><column-value><![CDATA[");
		sb.append(getLayoutSetId());
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
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>privateLayout</column-name><column-value><![CDATA[");
		sb.append(isPrivateLayout());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>logoId</column-name><column-value><![CDATA[");
		sb.append(getLogoId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>themeId</column-name><column-value><![CDATA[");
		sb.append(getThemeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>colorSchemeId</column-name><column-value><![CDATA[");
		sb.append(getColorSchemeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>css</column-name><column-value><![CDATA[");
		sb.append(getCss());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>pageCount</column-name><column-value><![CDATA[");
		sb.append(getPageCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>settings</column-name><column-value><![CDATA[");
		sb.append(getSettings());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>layoutSetPrototypeUuid</column-name><column-value><![CDATA[");
		sb.append(getLayoutSetPrototypeUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>layoutSetPrototypeLinkEnabled</column-name><column-value><![CDATA[");
		sb.append(isLayoutSetPrototypeLinkEnabled());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = LayoutSet.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			LayoutSet.class
		};
	private long _mvccVersion;
	private long _layoutSetId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private boolean _privateLayout;
	private boolean _originalPrivateLayout;
	private boolean _setOriginalPrivateLayout;
	private long _logoId;
	private long _originalLogoId;
	private boolean _setOriginalLogoId;
	private String _themeId;
	private String _colorSchemeId;
	private String _css;
	private int _pageCount;
	private String _settings;
	private String _layoutSetPrototypeUuid;
	private String _originalLayoutSetPrototypeUuid;
	private boolean _layoutSetPrototypeLinkEnabled;
	private long _columnBitmask;
	private LayoutSet _escapedModel;
}