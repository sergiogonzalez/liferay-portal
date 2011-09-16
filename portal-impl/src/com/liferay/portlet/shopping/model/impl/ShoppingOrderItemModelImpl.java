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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.shopping.model.ShoppingOrderItem;
import com.liferay.portlet.shopping.model.ShoppingOrderItemModel;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;

/**
 * The base model implementation for the ShoppingOrderItem service. Represents a row in the &quot;ShoppingOrderItem&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.shopping.model.ShoppingOrderItemModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ShoppingOrderItemImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderItemImpl
 * @see com.liferay.portlet.shopping.model.ShoppingOrderItem
 * @see com.liferay.portlet.shopping.model.ShoppingOrderItemModel
 * @generated
 */
public class ShoppingOrderItemModelImpl extends BaseModelImpl<ShoppingOrderItem>
	implements ShoppingOrderItemModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a shopping order item model instance should use the {@link com.liferay.portlet.shopping.model.ShoppingOrderItem} interface instead.
	 */
	public static final String TABLE_NAME = "ShoppingOrderItem";
	public static final Object[][] TABLE_COLUMNS = {
			{ "orderItemId", Types.BIGINT },
			{ "orderId", Types.BIGINT },
			{ "itemId", Types.VARCHAR },
			{ "sku", Types.VARCHAR },
			{ "name", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "properties", Types.VARCHAR },
			{ "price", Types.DOUBLE },
			{ "quantity", Types.INTEGER },
			{ "shippedDate", Types.TIMESTAMP }
		};
	public static final String TABLE_SQL_CREATE = "create table ShoppingOrderItem (orderItemId LONG not null primary key,orderId LONG,itemId VARCHAR(75) null,sku VARCHAR(75) null,name VARCHAR(200) null,description STRING null,properties STRING null,price DOUBLE,quantity INTEGER,shippedDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table ShoppingOrderItem";
	public static final String ORDER_BY_JPQL = " ORDER BY shoppingOrderItem.name ASC, shoppingOrderItem.description ASC";
	public static final String ORDER_BY_SQL = " ORDER BY ShoppingOrderItem.name ASC, ShoppingOrderItem.description ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.shopping.model.ShoppingOrderItem"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.shopping.model.ShoppingOrderItem"),
			true);

	public Class<?> getModelClass() {
		return ShoppingOrderItem.class;
	}

	public String getModelClassName() {
		return ShoppingOrderItem.class.getName();
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.shopping.model.ShoppingOrderItem"));

	public ShoppingOrderItemModelImpl() {
	}

	public long getPrimaryKey() {
		return _orderItemId;
	}

	public void setPrimaryKey(long primaryKey) {
		setOrderItemId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_orderItemId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public long getOrderItemId() {
		return _orderItemId;
	}

	public void setOrderItemId(long orderItemId) {
		_orderItemId = orderItemId;
	}

	public long getOrderId() {
		return _orderId;
	}

	public void setOrderId(long orderId) {
		_orderId = orderId;
	}

	public String getItemId() {
		if (_itemId == null) {
			return StringPool.BLANK;
		}
		else {
			return _itemId;
		}
	}

	public void setItemId(String itemId) {
		_itemId = itemId;
	}

	public String getSku() {
		if (_sku == null) {
			return StringPool.BLANK;
		}
		else {
			return _sku;
		}
	}

	public void setSku(String sku) {
		_sku = sku;
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

	public String getProperties() {
		if (_properties == null) {
			return StringPool.BLANK;
		}
		else {
			return _properties;
		}
	}

	public void setProperties(String properties) {
		_properties = properties;
	}

	public double getPrice() {
		return _price;
	}

	public void setPrice(double price) {
		_price = price;
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public Date getShippedDate() {
		return _shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		_shippedDate = shippedDate;
	}

	@Override
	public ShoppingOrderItem toEscapedModel() {
		if (isEscapedModel()) {
			return (ShoppingOrderItem)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (ShoppingOrderItem)ProxyUtil.newProxyInstance(_classLoader,
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
					ShoppingOrderItem.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		ShoppingOrderItemImpl shoppingOrderItemImpl = new ShoppingOrderItemImpl();

		shoppingOrderItemImpl.setOrderItemId(getOrderItemId());
		shoppingOrderItemImpl.setOrderId(getOrderId());
		shoppingOrderItemImpl.setItemId(getItemId());
		shoppingOrderItemImpl.setSku(getSku());
		shoppingOrderItemImpl.setName(getName());
		shoppingOrderItemImpl.setDescription(getDescription());
		shoppingOrderItemImpl.setProperties(getProperties());
		shoppingOrderItemImpl.setPrice(getPrice());
		shoppingOrderItemImpl.setQuantity(getQuantity());
		shoppingOrderItemImpl.setShippedDate(getShippedDate());

		shoppingOrderItemImpl.resetOriginalValues();

		return shoppingOrderItemImpl;
	}

	public int compareTo(ShoppingOrderItem shoppingOrderItem) {
		int value = 0;

		value = getName().compareTo(shoppingOrderItem.getName());

		if (value != 0) {
			return value;
		}

		value = getDescription().compareTo(shoppingOrderItem.getDescription());

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

		ShoppingOrderItem shoppingOrderItem = null;

		try {
			shoppingOrderItem = (ShoppingOrderItem)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = shoppingOrderItem.getPrimaryKey();

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
	public CacheModel<ShoppingOrderItem> toCacheModel() {
		ShoppingOrderItemCacheModel shoppingOrderItemCacheModel = new ShoppingOrderItemCacheModel();

		shoppingOrderItemCacheModel.orderItemId = getOrderItemId();

		shoppingOrderItemCacheModel.orderId = getOrderId();

		shoppingOrderItemCacheModel.itemId = getItemId();

		String itemId = shoppingOrderItemCacheModel.itemId;

		if ((itemId != null) && (itemId.length() == 0)) {
			shoppingOrderItemCacheModel.itemId = null;
		}

		shoppingOrderItemCacheModel.sku = getSku();

		String sku = shoppingOrderItemCacheModel.sku;

		if ((sku != null) && (sku.length() == 0)) {
			shoppingOrderItemCacheModel.sku = null;
		}

		shoppingOrderItemCacheModel.name = getName();

		String name = shoppingOrderItemCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			shoppingOrderItemCacheModel.name = null;
		}

		shoppingOrderItemCacheModel.description = getDescription();

		String description = shoppingOrderItemCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			shoppingOrderItemCacheModel.description = null;
		}

		shoppingOrderItemCacheModel.properties = getProperties();

		String properties = shoppingOrderItemCacheModel.properties;

		if ((properties != null) && (properties.length() == 0)) {
			shoppingOrderItemCacheModel.properties = null;
		}

		shoppingOrderItemCacheModel.price = getPrice();

		shoppingOrderItemCacheModel.quantity = getQuantity();

		Date shippedDate = getShippedDate();

		if (shippedDate != null) {
			shoppingOrderItemCacheModel.shippedDate = shippedDate.getTime();
		}
		else {
			shoppingOrderItemCacheModel.shippedDate = Long.MIN_VALUE;
		}

		return shoppingOrderItemCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{orderItemId=");
		sb.append(getOrderItemId());
		sb.append(", orderId=");
		sb.append(getOrderId());
		sb.append(", itemId=");
		sb.append(getItemId());
		sb.append(", sku=");
		sb.append(getSku());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", properties=");
		sb.append(getProperties());
		sb.append(", price=");
		sb.append(getPrice());
		sb.append(", quantity=");
		sb.append(getQuantity());
		sb.append(", shippedDate=");
		sb.append(getShippedDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.shopping.model.ShoppingOrderItem");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>orderItemId</column-name><column-value><![CDATA[");
		sb.append(getOrderItemId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>orderId</column-name><column-value><![CDATA[");
		sb.append(getOrderId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>itemId</column-name><column-value><![CDATA[");
		sb.append(getItemId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>sku</column-name><column-value><![CDATA[");
		sb.append(getSku());
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
			"<column><column-name>properties</column-name><column-value><![CDATA[");
		sb.append(getProperties());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>price</column-name><column-value><![CDATA[");
		sb.append(getPrice());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>quantity</column-name><column-value><![CDATA[");
		sb.append(getQuantity());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>shippedDate</column-name><column-value><![CDATA[");
		sb.append(getShippedDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = ShoppingOrderItem.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			ShoppingOrderItem.class
		};
	private long _orderItemId;
	private long _orderId;
	private String _itemId;
	private String _sku;
	private String _name;
	private String _description;
	private String _properties;
	private double _price;
	private int _quantity;
	private Date _shippedDate;
	private transient ExpandoBridge _expandoBridge;
	private ShoppingOrderItem _escapedModelProxy;
}