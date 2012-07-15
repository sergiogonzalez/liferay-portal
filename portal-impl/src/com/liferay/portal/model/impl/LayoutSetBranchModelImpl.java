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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			{ "master", Types.BOOLEAN },
			{ "logo", Types.BOOLEAN },
			{ "logoId", Types.BIGINT },
			{ "themeId", Types.VARCHAR },
			{ "colorSchemeId", Types.VARCHAR },
			{ "wapThemeId", Types.VARCHAR },
			{ "wapColorSchemeId", Types.VARCHAR },
			{ "css", Types.VARCHAR },
			{ "settings_", Types.VARCHAR },
			{ "layoutSetPrototypeUuid", Types.VARCHAR },
			{ "layoutSetPrototypeLinkEnabled", Types.BOOLEAN }
		};
	public static final String TABLE_SQL_CREATE = "create table LayoutSetBranch (layoutSetBranchId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,privateLayout BOOLEAN,name VARCHAR(75) null,description STRING null,master BOOLEAN,logo BOOLEAN,logoId LONG,themeId VARCHAR(75) null,colorSchemeId VARCHAR(75) null,wapThemeId VARCHAR(75) null,wapColorSchemeId VARCHAR(75) null,css STRING null,settings_ STRING null,layoutSetPrototypeUuid VARCHAR(75) null,layoutSetPrototypeLinkEnabled BOOLEAN)";
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
		if (soapModel == null) {
			return null;
		}

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
		model.setLogo(soapModel.getLogo());
		model.setLogoId(soapModel.getLogoId());
		model.setThemeId(soapModel.getThemeId());
		model.setColorSchemeId(soapModel.getColorSchemeId());
		model.setWapThemeId(soapModel.getWapThemeId());
		model.setWapColorSchemeId(soapModel.getWapColorSchemeId());
		model.setCss(soapModel.getCss());
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
	public static List<LayoutSetBranch> toModels(
		LayoutSetBranchSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

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

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("layoutSetBranchId", getLayoutSetBranchId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("privateLayout", getPrivateLayout());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("master", getMaster());
		attributes.put("logo", getLogo());
		attributes.put("logoId", getLogoId());
		attributes.put("themeId", getThemeId());
		attributes.put("colorSchemeId", getColorSchemeId());
		attributes.put("wapThemeId", getWapThemeId());
		attributes.put("wapColorSchemeId", getWapColorSchemeId());
		attributes.put("css", getCss());
		attributes.put("settings", getSettings());
		attributes.put("layoutSetPrototypeUuid", getLayoutSetPrototypeUuid());
		attributes.put("layoutSetPrototypeLinkEnabled",
			getLayoutSetPrototypeLinkEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long layoutSetBranchId = (Long)attributes.get("layoutSetBranchId");

		if (layoutSetBranchId != null) {
			setLayoutSetBranchId(layoutSetBranchId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Boolean master = (Boolean)attributes.get("master");

		if (master != null) {
			setMaster(master);
		}

		Boolean logo = (Boolean)attributes.get("logo");

		if (logo != null) {
			setLogo(logo);
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

		String wapThemeId = (String)attributes.get("wapThemeId");

		if (wapThemeId != null) {
			setWapThemeId(wapThemeId);
		}

		String wapColorSchemeId = (String)attributes.get("wapColorSchemeId");

		if (wapColorSchemeId != null) {
			setWapColorSchemeId(wapColorSchemeId);
		}

		String css = (String)attributes.get("css");

		if (css != null) {
			setCss(css);
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

	@JSON
	public boolean getLogo() {
		return _logo;
	}

	public boolean isLogo() {
		return _logo;
	}

	public void setLogo(boolean logo) {
		_logo = logo;
	}

	@JSON
	public long getLogoId() {
		return _logoId;
	}

	public void setLogoId(long logoId) {
		_logoId = logoId;
	}

	@JSON
	public String getThemeId() {
		if (_themeId == null) {
			return StringPool.BLANK;
		}
		else {
			return _themeId;
		}
	}

	public void setThemeId(String themeId) {
		_themeId = themeId;
	}

	@JSON
	public String getColorSchemeId() {
		if (_colorSchemeId == null) {
			return StringPool.BLANK;
		}
		else {
			return _colorSchemeId;
		}
	}

	public void setColorSchemeId(String colorSchemeId) {
		_colorSchemeId = colorSchemeId;
	}

	@JSON
	public String getWapThemeId() {
		if (_wapThemeId == null) {
			return StringPool.BLANK;
		}
		else {
			return _wapThemeId;
		}
	}

	public void setWapThemeId(String wapThemeId) {
		_wapThemeId = wapThemeId;
	}

	@JSON
	public String getWapColorSchemeId() {
		if (_wapColorSchemeId == null) {
			return StringPool.BLANK;
		}
		else {
			return _wapColorSchemeId;
		}
	}

	public void setWapColorSchemeId(String wapColorSchemeId) {
		_wapColorSchemeId = wapColorSchemeId;
	}

	@JSON
	public String getCss() {
		if (_css == null) {
			return StringPool.BLANK;
		}
		else {
			return _css;
		}
	}

	public void setCss(String css) {
		_css = css;
	}

	@JSON
	public String getSettings() {
		if (_settings == null) {
			return StringPool.BLANK;
		}
		else {
			return _settings;
		}
	}

	public void setSettings(String settings) {
		_settings = settings;
	}

	@JSON
	public String getLayoutSetPrototypeUuid() {
		if (_layoutSetPrototypeUuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _layoutSetPrototypeUuid;
		}
	}

	public void setLayoutSetPrototypeUuid(String layoutSetPrototypeUuid) {
		_layoutSetPrototypeUuid = layoutSetPrototypeUuid;
	}

	@JSON
	public boolean getLayoutSetPrototypeLinkEnabled() {
		return _layoutSetPrototypeLinkEnabled;
	}

	public boolean isLayoutSetPrototypeLinkEnabled() {
		return _layoutSetPrototypeLinkEnabled;
	}

	public void setLayoutSetPrototypeLinkEnabled(
		boolean layoutSetPrototypeLinkEnabled) {
		_layoutSetPrototypeLinkEnabled = layoutSetPrototypeLinkEnabled;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public LayoutSetBranch toEscapedModel() {
		if (_escapedModelProxy == null) {
			_escapedModelProxy = (LayoutSetBranch)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelProxyInterfaces,
					new AutoEscapeBeanHandler(this));
		}

		return _escapedModelProxy;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			LayoutSetBranch.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
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
		layoutSetBranchImpl.setLogo(getLogo());
		layoutSetBranchImpl.setLogoId(getLogoId());
		layoutSetBranchImpl.setThemeId(getThemeId());
		layoutSetBranchImpl.setColorSchemeId(getColorSchemeId());
		layoutSetBranchImpl.setWapThemeId(getWapThemeId());
		layoutSetBranchImpl.setWapColorSchemeId(getWapColorSchemeId());
		layoutSetBranchImpl.setCss(getCss());
		layoutSetBranchImpl.setSettings(getSettings());
		layoutSetBranchImpl.setLayoutSetPrototypeUuid(getLayoutSetPrototypeUuid());
		layoutSetBranchImpl.setLayoutSetPrototypeLinkEnabled(getLayoutSetPrototypeLinkEnabled());

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

		layoutSetBranchModelImpl._columnBitmask = 0;
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

		layoutSetBranchCacheModel.logo = getLogo();

		layoutSetBranchCacheModel.logoId = getLogoId();

		layoutSetBranchCacheModel.themeId = getThemeId();

		String themeId = layoutSetBranchCacheModel.themeId;

		if ((themeId != null) && (themeId.length() == 0)) {
			layoutSetBranchCacheModel.themeId = null;
		}

		layoutSetBranchCacheModel.colorSchemeId = getColorSchemeId();

		String colorSchemeId = layoutSetBranchCacheModel.colorSchemeId;

		if ((colorSchemeId != null) && (colorSchemeId.length() == 0)) {
			layoutSetBranchCacheModel.colorSchemeId = null;
		}

		layoutSetBranchCacheModel.wapThemeId = getWapThemeId();

		String wapThemeId = layoutSetBranchCacheModel.wapThemeId;

		if ((wapThemeId != null) && (wapThemeId.length() == 0)) {
			layoutSetBranchCacheModel.wapThemeId = null;
		}

		layoutSetBranchCacheModel.wapColorSchemeId = getWapColorSchemeId();

		String wapColorSchemeId = layoutSetBranchCacheModel.wapColorSchemeId;

		if ((wapColorSchemeId != null) && (wapColorSchemeId.length() == 0)) {
			layoutSetBranchCacheModel.wapColorSchemeId = null;
		}

		layoutSetBranchCacheModel.css = getCss();

		String css = layoutSetBranchCacheModel.css;

		if ((css != null) && (css.length() == 0)) {
			layoutSetBranchCacheModel.css = null;
		}

		layoutSetBranchCacheModel.settings = getSettings();

		String settings = layoutSetBranchCacheModel.settings;

		if ((settings != null) && (settings.length() == 0)) {
			layoutSetBranchCacheModel.settings = null;
		}

		layoutSetBranchCacheModel.layoutSetPrototypeUuid = getLayoutSetPrototypeUuid();

		String layoutSetPrototypeUuid = layoutSetBranchCacheModel.layoutSetPrototypeUuid;

		if ((layoutSetPrototypeUuid != null) &&
				(layoutSetPrototypeUuid.length() == 0)) {
			layoutSetBranchCacheModel.layoutSetPrototypeUuid = null;
		}

		layoutSetBranchCacheModel.layoutSetPrototypeLinkEnabled = getLayoutSetPrototypeLinkEnabled();

		return layoutSetBranchCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(43);

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
		sb.append(", logo=");
		sb.append(getLogo());
		sb.append(", logoId=");
		sb.append(getLogoId());
		sb.append(", themeId=");
		sb.append(getThemeId());
		sb.append(", colorSchemeId=");
		sb.append(getColorSchemeId());
		sb.append(", wapThemeId=");
		sb.append(getWapThemeId());
		sb.append(", wapColorSchemeId=");
		sb.append(getWapColorSchemeId());
		sb.append(", css=");
		sb.append(getCss());
		sb.append(", settings=");
		sb.append(getSettings());
		sb.append(", layoutSetPrototypeUuid=");
		sb.append(getLayoutSetPrototypeUuid());
		sb.append(", layoutSetPrototypeLinkEnabled=");
		sb.append(getLayoutSetPrototypeLinkEnabled());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(67);

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
		sb.append(
			"<column><column-name>logo</column-name><column-value><![CDATA[");
		sb.append(getLogo());
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
			"<column><column-name>wapThemeId</column-name><column-value><![CDATA[");
		sb.append(getWapThemeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>wapColorSchemeId</column-name><column-value><![CDATA[");
		sb.append(getWapColorSchemeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>css</column-name><column-value><![CDATA[");
		sb.append(getCss());
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
		sb.append(getLayoutSetPrototypeLinkEnabled());
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
	private boolean _logo;
	private long _logoId;
	private String _themeId;
	private String _colorSchemeId;
	private String _wapThemeId;
	private String _wapColorSchemeId;
	private String _css;
	private String _settings;
	private String _layoutSetPrototypeUuid;
	private boolean _layoutSetPrototypeLinkEnabled;
	private long _columnBitmask;
	private LayoutSetBranch _escapedModelProxy;
}