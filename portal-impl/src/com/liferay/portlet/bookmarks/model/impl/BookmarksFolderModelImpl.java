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

package com.liferay.portlet.bookmarks.model.impl;

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

import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderModel;
import com.liferay.portlet.bookmarks.model.BookmarksFolderSoap;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The base model implementation for the BookmarksFolder service. Represents a row in the &quot;BookmarksFolder&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.bookmarks.model.BookmarksFolderModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link BookmarksFolderImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksFolderImpl
 * @see com.liferay.portlet.bookmarks.model.BookmarksFolder
 * @see com.liferay.portlet.bookmarks.model.BookmarksFolderModel
 * @generated
 */
@JSON(strict = true)
public class BookmarksFolderModelImpl extends BaseModelImpl<BookmarksFolder>
	implements BookmarksFolderModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a bookmarks folder model instance should use the {@link com.liferay.portlet.bookmarks.model.BookmarksFolder} interface instead.
	 */
	public static final String TABLE_NAME = "BookmarksFolder";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "folderId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "resourceBlockId", Types.BIGINT },
			{ "parentFolderId", Types.BIGINT },
			{ "name", Types.VARCHAR },
			{ "description", Types.VARCHAR }
		};
	public static final String TABLE_SQL_CREATE = "create table BookmarksFolder (uuid_ VARCHAR(75) null,folderId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,resourceBlockId LONG,parentFolderId LONG,name VARCHAR(75) null,description STRING null)";
	public static final String TABLE_SQL_DROP = "drop table BookmarksFolder";
	public static final String ORDER_BY_JPQL = " ORDER BY bookmarksFolder.parentFolderId ASC, bookmarksFolder.name ASC";
	public static final String ORDER_BY_SQL = " ORDER BY BookmarksFolder.parentFolderId ASC, BookmarksFolder.name ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.bookmarks.model.BookmarksFolder"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.bookmarks.model.BookmarksFolder"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portlet.bookmarks.model.BookmarksFolder"),
			true);
	public static long PARENTFOLDERID_COLUMN_BITMASK = 1L;
	public static long COMPANYID_COLUMN_BITMASK = 2L;
	public static long GROUPID_COLUMN_BITMASK = 4L;
	public static long UUID_COLUMN_BITMASK = 8L;
	public static long RESOURCEBLOCKID_COLUMN_BITMASK = 16L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static BookmarksFolder toModel(BookmarksFolderSoap soapModel) {
		BookmarksFolder model = new BookmarksFolderImpl();

		model.setUuid(soapModel.getUuid());
		model.setFolderId(soapModel.getFolderId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setResourceBlockId(soapModel.getResourceBlockId());
		model.setParentFolderId(soapModel.getParentFolderId());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<BookmarksFolder> toModels(
		BookmarksFolderSoap[] soapModels) {
		List<BookmarksFolder> models = new ArrayList<BookmarksFolder>(soapModels.length);

		for (BookmarksFolderSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.bookmarks.model.BookmarksFolder"));

	public BookmarksFolderModelImpl() {
	}

	public long getPrimaryKey() {
		return _folderId;
	}

	public void setPrimaryKey(long primaryKey) {
		setFolderId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_folderId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return BookmarksFolder.class;
	}

	public String getModelClassName() {
		return BookmarksFolder.class.getName();
	}

	@JSON
	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	public long getFolderId() {
		return _folderId;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
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
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
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
	public long getResourceBlockId() {
		return _resourceBlockId;
	}

	public void setResourceBlockId(long resourceBlockId) {
		_columnBitmask |= RESOURCEBLOCKID_COLUMN_BITMASK;

		if (!_setOriginalResourceBlockId) {
			_setOriginalResourceBlockId = true;

			_originalResourceBlockId = _resourceBlockId;
		}

		_resourceBlockId = resourceBlockId;
	}

	public long getOriginalResourceBlockId() {
		return _originalResourceBlockId;
	}

	@JSON
	public long getParentFolderId() {
		return _parentFolderId;
	}

	public void setParentFolderId(long parentFolderId) {
		_columnBitmask |= PARENTFOLDERID_COLUMN_BITMASK;

		if (!_setOriginalParentFolderId) {
			_setOriginalParentFolderId = true;

			_originalParentFolderId = _parentFolderId;
		}

		_parentFolderId = parentFolderId;
	}

	public long getOriginalParentFolderId() {
		return _originalParentFolderId;
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
		_name = name;
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

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public BookmarksFolder toEscapedModel() {
		if (isEscapedModel()) {
			return (BookmarksFolder)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (BookmarksFolder)ProxyUtil.newProxyInstance(_classLoader,
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
					BookmarksFolder.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		BookmarksFolderImpl bookmarksFolderImpl = new BookmarksFolderImpl();

		bookmarksFolderImpl.setUuid(getUuid());
		bookmarksFolderImpl.setFolderId(getFolderId());
		bookmarksFolderImpl.setGroupId(getGroupId());
		bookmarksFolderImpl.setCompanyId(getCompanyId());
		bookmarksFolderImpl.setUserId(getUserId());
		bookmarksFolderImpl.setUserName(getUserName());
		bookmarksFolderImpl.setCreateDate(getCreateDate());
		bookmarksFolderImpl.setModifiedDate(getModifiedDate());
		bookmarksFolderImpl.setResourceBlockId(getResourceBlockId());
		bookmarksFolderImpl.setParentFolderId(getParentFolderId());
		bookmarksFolderImpl.setName(getName());
		bookmarksFolderImpl.setDescription(getDescription());

		bookmarksFolderImpl.resetOriginalValues();

		return bookmarksFolderImpl;
	}

	public int compareTo(BookmarksFolder bookmarksFolder) {
		int value = 0;

		if (getParentFolderId() < bookmarksFolder.getParentFolderId()) {
			value = -1;
		}
		else if (getParentFolderId() > bookmarksFolder.getParentFolderId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = getName().toLowerCase()
					.compareTo(bookmarksFolder.getName().toLowerCase());

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

		BookmarksFolder bookmarksFolder = null;

		try {
			bookmarksFolder = (BookmarksFolder)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = bookmarksFolder.getPrimaryKey();

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
		BookmarksFolderModelImpl bookmarksFolderModelImpl = this;

		bookmarksFolderModelImpl._originalUuid = bookmarksFolderModelImpl._uuid;

		bookmarksFolderModelImpl._originalGroupId = bookmarksFolderModelImpl._groupId;

		bookmarksFolderModelImpl._setOriginalGroupId = false;

		bookmarksFolderModelImpl._originalCompanyId = bookmarksFolderModelImpl._companyId;

		bookmarksFolderModelImpl._setOriginalCompanyId = false;

		bookmarksFolderModelImpl._originalResourceBlockId = bookmarksFolderModelImpl._resourceBlockId;

		bookmarksFolderModelImpl._setOriginalResourceBlockId = false;

		bookmarksFolderModelImpl._originalParentFolderId = bookmarksFolderModelImpl._parentFolderId;

		bookmarksFolderModelImpl._setOriginalParentFolderId = false;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<BookmarksFolder> toCacheModel() {
		BookmarksFolderCacheModel bookmarksFolderCacheModel = new BookmarksFolderCacheModel();

		bookmarksFolderCacheModel.uuid = getUuid();

		String uuid = bookmarksFolderCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			bookmarksFolderCacheModel.uuid = null;
		}

		bookmarksFolderCacheModel.folderId = getFolderId();

		bookmarksFolderCacheModel.groupId = getGroupId();

		bookmarksFolderCacheModel.companyId = getCompanyId();

		bookmarksFolderCacheModel.userId = getUserId();

		bookmarksFolderCacheModel.userName = getUserName();

		String userName = bookmarksFolderCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			bookmarksFolderCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			bookmarksFolderCacheModel.createDate = createDate.getTime();
		}
		else {
			bookmarksFolderCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			bookmarksFolderCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			bookmarksFolderCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		bookmarksFolderCacheModel.resourceBlockId = getResourceBlockId();

		bookmarksFolderCacheModel.parentFolderId = getParentFolderId();

		bookmarksFolderCacheModel.name = getName();

		String name = bookmarksFolderCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			bookmarksFolderCacheModel.name = null;
		}

		bookmarksFolderCacheModel.description = getDescription();

		String description = bookmarksFolderCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			bookmarksFolderCacheModel.description = null;
		}

		return bookmarksFolderCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", folderId=");
		sb.append(getFolderId());
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
		sb.append(", resourceBlockId=");
		sb.append(getResourceBlockId());
		sb.append(", parentFolderId=");
		sb.append(getParentFolderId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(40);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.bookmarks.model.BookmarksFolder");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>folderId</column-name><column-value><![CDATA[");
		sb.append(getFolderId());
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
			"<column><column-name>resourceBlockId</column-name><column-value><![CDATA[");
		sb.append(getResourceBlockId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>parentFolderId</column-name><column-value><![CDATA[");
		sb.append(getParentFolderId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = BookmarksFolder.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			BookmarksFolder.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _folderId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _resourceBlockId;
	private long _originalResourceBlockId;
	private boolean _setOriginalResourceBlockId;
	private long _parentFolderId;
	private long _originalParentFolderId;
	private boolean _setOriginalParentFolderId;
	private String _name;
	private String _description;
	private transient ExpandoBridge _expandoBridge;
	private long _columnBitmask;
	private BookmarksFolder _escapedModelProxy;
}