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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.MBBanModel;
import com.liferay.portlet.messageboards.model.MBBanSoap;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The base model implementation for the MBBan service. Represents a row in the &quot;MBBan&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.messageboards.model.MBBanModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link MBBanImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBBanImpl
 * @see com.liferay.portlet.messageboards.model.MBBan
 * @see com.liferay.portlet.messageboards.model.MBBanModel
 * @generated
 */
@JSON(strict = true)
public class MBBanModelImpl extends BaseModelImpl<MBBan> implements MBBanModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a message boards ban model instance should use the {@link com.liferay.portlet.messageboards.model.MBBan} interface instead.
	 */
	public static final String TABLE_NAME = "MBBan";
	public static final Object[][] TABLE_COLUMNS = {
			{ "banId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "banUserId", Types.BIGINT }
		};
	public static final String TABLE_SQL_CREATE = "create table MBBan (banId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,banUserId LONG)";
	public static final String TABLE_SQL_DROP = "drop table MBBan";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.messageboards.model.MBBan"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.messageboards.model.MBBan"),
			true);

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static MBBan toModel(MBBanSoap soapModel) {
		MBBan model = new MBBanImpl();

		model.setBanId(soapModel.getBanId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setBanUserId(soapModel.getBanUserId());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<MBBan> toModels(MBBanSoap[] soapModels) {
		List<MBBan> models = new ArrayList<MBBan>(soapModels.length);

		for (MBBanSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public Class<?> getModelClass() {
		return MBBan.class;
	}

	public String getModelClassName() {
		return MBBan.class.getName();
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.messageboards.model.MBBan"));

	public MBBanModelImpl() {
	}

	public long getPrimaryKey() {
		return _banId;
	}

	public void setPrimaryKey(long primaryKey) {
		setBanId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_banId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@JSON
	public long getBanId() {
		return _banId;
	}

	public void setBanId(long banId) {
		_banId = banId;
	}

	@JSON
	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
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
	public long getBanUserId() {
		return _banUserId;
	}

	public void setBanUserId(long banUserId) {
		if (!_setOriginalBanUserId) {
			_setOriginalBanUserId = true;

			_originalBanUserId = _banUserId;
		}

		_banUserId = banUserId;
	}

	public String getBanUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getBanUserId(), "uuid", _banUserUuid);
	}

	public void setBanUserUuid(String banUserUuid) {
		_banUserUuid = banUserUuid;
	}

	public long getOriginalBanUserId() {
		return _originalBanUserId;
	}

	@Override
	public MBBan toEscapedModel() {
		if (isEscapedModel()) {
			return (MBBan)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (MBBan)ProxyUtil.newProxyInstance(_classLoader,
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
					MBBan.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		MBBanImpl mbBanImpl = new MBBanImpl();

		mbBanImpl.setBanId(getBanId());
		mbBanImpl.setGroupId(getGroupId());
		mbBanImpl.setCompanyId(getCompanyId());
		mbBanImpl.setUserId(getUserId());
		mbBanImpl.setUserName(getUserName());
		mbBanImpl.setCreateDate(getCreateDate());
		mbBanImpl.setModifiedDate(getModifiedDate());
		mbBanImpl.setBanUserId(getBanUserId());

		mbBanImpl.resetOriginalValues();

		return mbBanImpl;
	}

	public int compareTo(MBBan mbBan) {
		long primaryKey = mbBan.getPrimaryKey();

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

		MBBan mbBan = null;

		try {
			mbBan = (MBBan)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = mbBan.getPrimaryKey();

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
		MBBanModelImpl mbBanModelImpl = this;

		mbBanModelImpl._originalGroupId = mbBanModelImpl._groupId;

		mbBanModelImpl._setOriginalGroupId = false;

		mbBanModelImpl._originalBanUserId = mbBanModelImpl._banUserId;

		mbBanModelImpl._setOriginalBanUserId = false;
	}

	@Override
	public CacheModel<MBBan> toCacheModel() {
		MBBanCacheModel mbBanCacheModel = new MBBanCacheModel();

		mbBanCacheModel.banId = getBanId();

		mbBanCacheModel.groupId = getGroupId();

		mbBanCacheModel.companyId = getCompanyId();

		mbBanCacheModel.userId = getUserId();

		mbBanCacheModel.userName = getUserName();

		String userName = mbBanCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			mbBanCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			mbBanCacheModel.createDate = createDate.getTime();
		}
		else {
			mbBanCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			mbBanCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			mbBanCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		mbBanCacheModel.banUserId = getBanUserId();

		return mbBanCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{banId=");
		sb.append(getBanId());
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
		sb.append(", banUserId=");
		sb.append(getBanUserId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(28);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.messageboards.model.MBBan");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>banId</column-name><column-value><![CDATA[");
		sb.append(getBanId());
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
			"<column><column-name>banUserId</column-name><column-value><![CDATA[");
		sb.append(getBanUserId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = MBBan.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			MBBan.class
		};
	private long _banId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _banUserId;
	private String _banUserUuid;
	private long _originalBanUserId;
	private boolean _setOriginalBanUserId;
	private transient ExpandoBridge _expandoBridge;
	private MBBan _escapedModelProxy;
}