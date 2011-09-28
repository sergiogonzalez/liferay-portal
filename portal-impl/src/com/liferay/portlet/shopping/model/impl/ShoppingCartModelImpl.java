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

package com.liferay.portlet.shopping.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
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
import com.liferay.portlet.shopping.model.ShoppingCart;
import com.liferay.portlet.shopping.model.ShoppingCartModel;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;

/**
 * The base model implementation for the ShoppingCart service. Represents a row in the &quot;ShoppingCart&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.shopping.model.ShoppingCartModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ShoppingCartImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCartImpl
 * @see com.liferay.portlet.shopping.model.ShoppingCart
 * @see com.liferay.portlet.shopping.model.ShoppingCartModel
 * @generated
 */
public class ShoppingCartModelImpl extends BaseModelImpl<ShoppingCart>
	implements ShoppingCartModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a shopping cart model instance should use the {@link com.liferay.portlet.shopping.model.ShoppingCart} interface instead.
	 */
	public static final String TABLE_NAME = "ShoppingCart";
	public static final Object[][] TABLE_COLUMNS = {
			{ "cartId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "itemIds", Types.VARCHAR },
			{ "couponCodes", Types.VARCHAR },
			{ "altShipping", Types.INTEGER },
			{ "insure", Types.BOOLEAN }
		};
	public static final String TABLE_SQL_CREATE = "create table ShoppingCart (cartId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,itemIds STRING null,couponCodes VARCHAR(75) null,altShipping INTEGER,insure BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table ShoppingCart";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.shopping.model.ShoppingCart"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.shopping.model.ShoppingCart"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portlet.shopping.model.ShoppingCart"),
			true);
	public static long GROUPID_COLUMN_BITMASK = 1L;
	public static long USERID_COLUMN_BITMASK = 2L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.shopping.model.ShoppingCart"));

	public ShoppingCartModelImpl() {
	}

	public long getPrimaryKey() {
		return _cartId;
	}

	public void setPrimaryKey(long primaryKey) {
		setCartId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_cartId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return ShoppingCart.class;
	}

	public String getModelClassName() {
		return ShoppingCart.class.getName();
	}

	public long getCartId() {
		return _cartId;
	}

	public void setCartId(long cartId) {
		_cartId = cartId;
	}

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

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

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

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getItemIds() {
		if (_itemIds == null) {
			return StringPool.BLANK;
		}
		else {
			return _itemIds;
		}
	}

	public void setItemIds(String itemIds) {
		_itemIds = itemIds;
	}

	public String getCouponCodes() {
		if (_couponCodes == null) {
			return StringPool.BLANK;
		}
		else {
			return _couponCodes;
		}
	}

	public void setCouponCodes(String couponCodes) {
		_couponCodes = couponCodes;
	}

	public int getAltShipping() {
		return _altShipping;
	}

	public void setAltShipping(int altShipping) {
		_altShipping = altShipping;
	}

	public boolean getInsure() {
		return _insure;
	}

	public boolean isInsure() {
		return _insure;
	}

	public void setInsure(boolean insure) {
		_insure = insure;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ShoppingCart toEscapedModel() {
		if (isEscapedModel()) {
			return (ShoppingCart)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (ShoppingCart)ProxyUtil.newProxyInstance(_classLoader,
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
					ShoppingCart.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		ShoppingCartImpl shoppingCartImpl = new ShoppingCartImpl();

		shoppingCartImpl.setCartId(getCartId());
		shoppingCartImpl.setGroupId(getGroupId());
		shoppingCartImpl.setCompanyId(getCompanyId());
		shoppingCartImpl.setUserId(getUserId());
		shoppingCartImpl.setUserName(getUserName());
		shoppingCartImpl.setCreateDate(getCreateDate());
		shoppingCartImpl.setModifiedDate(getModifiedDate());
		shoppingCartImpl.setItemIds(getItemIds());
		shoppingCartImpl.setCouponCodes(getCouponCodes());
		shoppingCartImpl.setAltShipping(getAltShipping());
		shoppingCartImpl.setInsure(getInsure());

		shoppingCartImpl.resetOriginalValues();

		return shoppingCartImpl;
	}

	public int compareTo(ShoppingCart shoppingCart) {
		long primaryKey = shoppingCart.getPrimaryKey();

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

		ShoppingCart shoppingCart = null;

		try {
			shoppingCart = (ShoppingCart)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = shoppingCart.getPrimaryKey();

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
		ShoppingCartModelImpl shoppingCartModelImpl = this;

		shoppingCartModelImpl._originalGroupId = shoppingCartModelImpl._groupId;

		shoppingCartModelImpl._setOriginalGroupId = false;

		shoppingCartModelImpl._originalUserId = shoppingCartModelImpl._userId;

		shoppingCartModelImpl._setOriginalUserId = false;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<ShoppingCart> toCacheModel() {
		ShoppingCartCacheModel shoppingCartCacheModel = new ShoppingCartCacheModel();

		shoppingCartCacheModel.cartId = getCartId();

		shoppingCartCacheModel.groupId = getGroupId();

		shoppingCartCacheModel.companyId = getCompanyId();

		shoppingCartCacheModel.userId = getUserId();

		shoppingCartCacheModel.userName = getUserName();

		String userName = shoppingCartCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			shoppingCartCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			shoppingCartCacheModel.createDate = createDate.getTime();
		}
		else {
			shoppingCartCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			shoppingCartCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			shoppingCartCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		shoppingCartCacheModel.itemIds = getItemIds();

		String itemIds = shoppingCartCacheModel.itemIds;

		if ((itemIds != null) && (itemIds.length() == 0)) {
			shoppingCartCacheModel.itemIds = null;
		}

		shoppingCartCacheModel.couponCodes = getCouponCodes();

		String couponCodes = shoppingCartCacheModel.couponCodes;

		if ((couponCodes != null) && (couponCodes.length() == 0)) {
			shoppingCartCacheModel.couponCodes = null;
		}

		shoppingCartCacheModel.altShipping = getAltShipping();

		shoppingCartCacheModel.insure = getInsure();

		return shoppingCartCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{cartId=");
		sb.append(getCartId());
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
		sb.append(", itemIds=");
		sb.append(getItemIds());
		sb.append(", couponCodes=");
		sb.append(getCouponCodes());
		sb.append(", altShipping=");
		sb.append(getAltShipping());
		sb.append(", insure=");
		sb.append(getInsure());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(37);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.shopping.model.ShoppingCart");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>cartId</column-name><column-value><![CDATA[");
		sb.append(getCartId());
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
			"<column><column-name>itemIds</column-name><column-value><![CDATA[");
		sb.append(getItemIds());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>couponCodes</column-name><column-value><![CDATA[");
		sb.append(getCouponCodes());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>altShipping</column-name><column-value><![CDATA[");
		sb.append(getAltShipping());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>insure</column-name><column-value><![CDATA[");
		sb.append(getInsure());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = ShoppingCart.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			ShoppingCart.class
		};
	private long _cartId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _itemIds;
	private String _couponCodes;
	private int _altShipping;
	private boolean _insure;
	private transient ExpandoBridge _expandoBridge;
	private long _columnBitmask;
	private ShoppingCart _escapedModelProxy;
}