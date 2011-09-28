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
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetModel;
import com.liferay.portal.model.LayoutSetSoap;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * The base model implementation for the LayoutSet service. Represents a row in the &quot;LayoutSet&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.LayoutSetModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link LayoutSetImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetImpl
 * @see com.liferay.portal.model.LayoutSet
 * @see com.liferay.portal.model.LayoutSetModel
 * @generated
 */
@JSON(strict = true)
public class LayoutSetModelImpl extends BaseModelImpl<LayoutSet>
	implements LayoutSetModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a layout set model instance should use the {@link com.liferay.portal.model.LayoutSet} interface instead.
	 */
	public static final String TABLE_NAME = "LayoutSet";
	public static final Object[][] TABLE_COLUMNS = {
			{ "layoutSetId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "privateLayout", Types.BOOLEAN },
			{ "logo", Types.BOOLEAN },
			{ "logoId", Types.BIGINT },
			{ "themeId", Types.VARCHAR },
			{ "colorSchemeId", Types.VARCHAR },
			{ "wapThemeId", Types.VARCHAR },
			{ "wapColorSchemeId", Types.VARCHAR },
			{ "css", Types.VARCHAR },
			{ "pageCount", Types.INTEGER },
			{ "settings_", Types.VARCHAR },
			{ "layoutSetPrototypeUuid", Types.VARCHAR },
			{ "layoutSetPrototypeLinkEnabled", Types.BOOLEAN }
		};
	public static final String TABLE_SQL_CREATE = "create table LayoutSet (layoutSetId LONG not null primary key,groupId LONG,companyId LONG,privateLayout BOOLEAN,logo BOOLEAN,logoId LONG,themeId VARCHAR(75) null,colorSchemeId VARCHAR(75) null,wapThemeId VARCHAR(75) null,wapColorSchemeId VARCHAR(75) null,css STRING null,pageCount INTEGER,settings_ STRING null,layoutSetPrototypeUuid VARCHAR(75) null,layoutSetPrototypeLinkEnabled BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table LayoutSet";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.LayoutSet"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.LayoutSet"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.model.LayoutSet"),
			true);
	public static long GROUPID_COLUMN_BITMASK = 1L;
	public static long LAYOUTSETPROTOTYPEUUID_COLUMN_BITMASK = 2L;
	public static long PRIVATELAYOUT_COLUMN_BITMASK = 4L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static LayoutSet toModel(LayoutSetSoap soapModel) {
		LayoutSet model = new LayoutSetImpl();

		model.setLayoutSetId(soapModel.getLayoutSetId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setPrivateLayout(soapModel.getPrivateLayout());
		model.setLogo(soapModel.getLogo());
		model.setLogoId(soapModel.getLogoId());
		model.setThemeId(soapModel.getThemeId());
		model.setColorSchemeId(soapModel.getColorSchemeId());
		model.setWapThemeId(soapModel.getWapThemeId());
		model.setWapColorSchemeId(soapModel.getWapColorSchemeId());
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
		List<LayoutSet> models = new ArrayList<LayoutSet>(soapModels.length);

		for (LayoutSetSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.LayoutSet"));

	public LayoutSetModelImpl() {
	}

	public long getPrimaryKey() {
		return _layoutSetId;
	}

	public void setPrimaryKey(long primaryKey) {
		setLayoutSetId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_layoutSetId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return LayoutSet.class;
	}

	public String getModelClassName() {
		return LayoutSet.class.getName();
	}

	@JSON
	public long getLayoutSetId() {
		return _layoutSetId;
	}

	public void setLayoutSetId(long layoutSetId) {
		_layoutSetId = layoutSetId;
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
	public int getPageCount() {
		return _pageCount;
	}

	public void setPageCount(int pageCount) {
		_pageCount = pageCount;
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
	public LayoutSet toEscapedModel() {
		if (isEscapedModel()) {
			return (LayoutSet)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (LayoutSet)ProxyUtil.newProxyInstance(_classLoader,
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
					LayoutSet.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		LayoutSetImpl layoutSetImpl = new LayoutSetImpl();

		layoutSetImpl.setLayoutSetId(getLayoutSetId());
		layoutSetImpl.setGroupId(getGroupId());
		layoutSetImpl.setCompanyId(getCompanyId());
		layoutSetImpl.setPrivateLayout(getPrivateLayout());
		layoutSetImpl.setLogo(getLogo());
		layoutSetImpl.setLogoId(getLogoId());
		layoutSetImpl.setThemeId(getThemeId());
		layoutSetImpl.setColorSchemeId(getColorSchemeId());
		layoutSetImpl.setWapThemeId(getWapThemeId());
		layoutSetImpl.setWapColorSchemeId(getWapColorSchemeId());
		layoutSetImpl.setCss(getCss());
		layoutSetImpl.setPageCount(getPageCount());
		layoutSetImpl.setSettings(getSettings());
		layoutSetImpl.setLayoutSetPrototypeUuid(getLayoutSetPrototypeUuid());
		layoutSetImpl.setLayoutSetPrototypeLinkEnabled(getLayoutSetPrototypeLinkEnabled());

		layoutSetImpl.resetOriginalValues();

		return layoutSetImpl;
	}

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
		if (obj == null) {
			return false;
		}

		LayoutSet layoutSet = null;

		try {
			layoutSet = (LayoutSet)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

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
	public void resetOriginalValues() {
		LayoutSetModelImpl layoutSetModelImpl = this;

		layoutSetModelImpl._originalGroupId = layoutSetModelImpl._groupId;

		layoutSetModelImpl._setOriginalGroupId = false;

		layoutSetModelImpl._originalPrivateLayout = layoutSetModelImpl._privateLayout;

		layoutSetModelImpl._setOriginalPrivateLayout = false;

		layoutSetModelImpl._originalLayoutSetPrototypeUuid = layoutSetModelImpl._layoutSetPrototypeUuid;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<LayoutSet> toCacheModel() {
		LayoutSetCacheModel layoutSetCacheModel = new LayoutSetCacheModel();

		layoutSetCacheModel.layoutSetId = getLayoutSetId();

		layoutSetCacheModel.groupId = getGroupId();

		layoutSetCacheModel.companyId = getCompanyId();

		layoutSetCacheModel.privateLayout = getPrivateLayout();

		layoutSetCacheModel.logo = getLogo();

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

		layoutSetCacheModel.wapThemeId = getWapThemeId();

		String wapThemeId = layoutSetCacheModel.wapThemeId;

		if ((wapThemeId != null) && (wapThemeId.length() == 0)) {
			layoutSetCacheModel.wapThemeId = null;
		}

		layoutSetCacheModel.wapColorSchemeId = getWapColorSchemeId();

		String wapColorSchemeId = layoutSetCacheModel.wapColorSchemeId;

		if ((wapColorSchemeId != null) && (wapColorSchemeId.length() == 0)) {
			layoutSetCacheModel.wapColorSchemeId = null;
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

		layoutSetCacheModel.layoutSetPrototypeLinkEnabled = getLayoutSetPrototypeLinkEnabled();

		return layoutSetCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{layoutSetId=");
		sb.append(getLayoutSetId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", privateLayout=");
		sb.append(getPrivateLayout());
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
		sb.append(", pageCount=");
		sb.append(getPageCount());
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
		StringBundler sb = new StringBundler(49);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.LayoutSet");
		sb.append("</model-name>");

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
			"<column><column-name>privateLayout</column-name><column-value><![CDATA[");
		sb.append(getPrivateLayout());
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
		sb.append(getLayoutSetPrototypeLinkEnabled());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = LayoutSet.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			LayoutSet.class
		};
	private long _layoutSetId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private boolean _privateLayout;
	private boolean _originalPrivateLayout;
	private boolean _setOriginalPrivateLayout;
	private boolean _logo;
	private long _logoId;
	private String _themeId;
	private String _colorSchemeId;
	private String _wapThemeId;
	private String _wapColorSchemeId;
	private String _css;
	private int _pageCount;
	private String _settings;
	private String _layoutSetPrototypeUuid;
	private String _originalLayoutSetPrototypeUuid;
	private boolean _layoutSetPrototypeLinkEnabled;
	private transient ExpandoBridge _expandoBridge;
	private long _columnBitmask;
	private LayoutSet _escapedModelProxy;
}