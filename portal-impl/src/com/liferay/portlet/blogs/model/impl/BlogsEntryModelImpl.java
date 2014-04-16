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

package com.liferay.portlet.blogs.model.impl;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.BlogsEntryModel;
import com.liferay.portlet.blogs.model.BlogsEntrySoap;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the BlogsEntry service. Represents a row in the &quot;BlogsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.blogs.model.BlogsEntryModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link BlogsEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntryImpl
 * @see com.liferay.portlet.blogs.model.BlogsEntry
 * @see com.liferay.portlet.blogs.model.BlogsEntryModel
 * @generated
 */
@JSON(strict = true)
public class BlogsEntryModelImpl extends BaseModelImpl<BlogsEntry>
	implements BlogsEntryModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a blogs entry model instance should use the {@link com.liferay.portlet.blogs.model.BlogsEntry} interface instead.
	 */
	public static final String TABLE_NAME = "BlogsEntry";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "entryId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "title", Types.VARCHAR },
			{ "urlTitle", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "content", Types.CLOB },
			{ "displayDate", Types.TIMESTAMP },
			{ "allowPingbacks", Types.BOOLEAN },
			{ "allowTrackbacks", Types.BOOLEAN },
			{ "trackbacks", Types.CLOB },
			{ "smallImage", Types.BOOLEAN },
			{ "smallImageId", Types.BIGINT },
			{ "smallImageURL", Types.VARCHAR },
			{ "status", Types.INTEGER },
			{ "statusByUserId", Types.BIGINT },
			{ "statusByUserName", Types.VARCHAR },
			{ "statusDate", Types.TIMESTAMP }
		};
	public static final String TABLE_SQL_CREATE = "create table BlogsEntry (uuid_ VARCHAR(75) null,entryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,title VARCHAR(150) null,urlTitle VARCHAR(150) null,description STRING null,content TEXT null,displayDate DATE null,allowPingbacks BOOLEAN,allowTrackbacks BOOLEAN,trackbacks TEXT null,smallImage BOOLEAN,smallImageId LONG,smallImageURL STRING null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table BlogsEntry";
	public static final String ORDER_BY_JPQL = " ORDER BY blogsEntry.displayDate DESC, blogsEntry.createDate DESC";
	public static final String ORDER_BY_SQL = " ORDER BY BlogsEntry.displayDate DESC, BlogsEntry.createDate DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.blogs.model.BlogsEntry"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.blogs.model.BlogsEntry"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portlet.blogs.model.BlogsEntry"),
			true);
	public static long COMPANYID_COLUMN_BITMASK = 1L;
	public static long DISPLAYDATE_COLUMN_BITMASK = 2L;
	public static long GROUPID_COLUMN_BITMASK = 4L;
	public static long STATUS_COLUMN_BITMASK = 8L;
	public static long URLTITLE_COLUMN_BITMASK = 16L;
	public static long USERID_COLUMN_BITMASK = 32L;
	public static long UUID_COLUMN_BITMASK = 64L;
	public static long CREATEDATE_COLUMN_BITMASK = 128L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static BlogsEntry toModel(BlogsEntrySoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		BlogsEntry model = new BlogsEntryImpl();

		model.setUuid(soapModel.getUuid());
		model.setEntryId(soapModel.getEntryId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setTitle(soapModel.getTitle());
		model.setUrlTitle(soapModel.getUrlTitle());
		model.setDescription(soapModel.getDescription());
		model.setContent(soapModel.getContent());
		model.setDisplayDate(soapModel.getDisplayDate());
		model.setAllowPingbacks(soapModel.getAllowPingbacks());
		model.setAllowTrackbacks(soapModel.getAllowTrackbacks());
		model.setTrackbacks(soapModel.getTrackbacks());
		model.setSmallImage(soapModel.getSmallImage());
		model.setSmallImageId(soapModel.getSmallImageId());
		model.setSmallImageURL(soapModel.getSmallImageURL());
		model.setStatus(soapModel.getStatus());
		model.setStatusByUserId(soapModel.getStatusByUserId());
		model.setStatusByUserName(soapModel.getStatusByUserName());
		model.setStatusDate(soapModel.getStatusDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<BlogsEntry> toModels(BlogsEntrySoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<BlogsEntry> models = new ArrayList<BlogsEntry>(soapModels.length);

		for (BlogsEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.blogs.model.BlogsEntry"));

	public BlogsEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _entryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _entryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return BlogsEntry.class;
	}

	@Override
	public String getModelClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("entryId", getEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("title", getTitle());
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("description", getDescription());
		attributes.put("content", getContent());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("allowPingbacks", getAllowPingbacks());
		attributes.put("allowTrackbacks", getAllowTrackbacks());
		attributes.put("trackbacks", getTrackbacks());
		attributes.put("smallImage", getSmallImage());
		attributes.put("smallImageId", getSmallImageId());
		attributes.put("smallImageURL", getSmallImageURL());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
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

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String urlTitle = (String)attributes.get("urlTitle");

		if (urlTitle != null) {
			setUrlTitle(urlTitle);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Boolean allowPingbacks = (Boolean)attributes.get("allowPingbacks");

		if (allowPingbacks != null) {
			setAllowPingbacks(allowPingbacks);
		}

		Boolean allowTrackbacks = (Boolean)attributes.get("allowTrackbacks");

		if (allowTrackbacks != null) {
			setAllowTrackbacks(allowTrackbacks);
		}

		String trackbacks = (String)attributes.get("trackbacks");

		if (trackbacks != null) {
			setTrackbacks(trackbacks);
		}

		Boolean smallImage = (Boolean)attributes.get("smallImage");

		if (smallImage != null) {
			setSmallImage(smallImage);
		}

		Long smallImageId = (Long)attributes.get("smallImageId");

		if (smallImageId != null) {
			setSmallImageId(smallImageId);
		}

		String smallImageURL = (String)attributes.get("smallImageURL");

		if (smallImageURL != null) {
			setSmallImageURL(smallImageURL);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	@Override
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
	@Override
	public long getEntryId() {
		return _entryId;
	}

	@Override
	public void setEntryId(long entryId) {
		_entryId = entryId;
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
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() throws SystemException {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return StringPool.BLANK;
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_columnBitmask = -1L;

		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public String getTitle() {
		if (_title == null) {
			return StringPool.BLANK;
		}
		else {
			return _title;
		}
	}

	@Override
	public void setTitle(String title) {
		_title = title;
	}

	@JSON
	@Override
	public String getUrlTitle() {
		if (_urlTitle == null) {
			return StringPool.BLANK;
		}
		else {
			return _urlTitle;
		}
	}

	@Override
	public void setUrlTitle(String urlTitle) {
		_columnBitmask |= URLTITLE_COLUMN_BITMASK;

		if (_originalUrlTitle == null) {
			_originalUrlTitle = _urlTitle;
		}

		_urlTitle = urlTitle;
	}

	public String getOriginalUrlTitle() {
		return GetterUtil.getString(_originalUrlTitle);
	}

	@JSON
	@Override
	public String getDescription() {
		if (_description == null) {
			return StringPool.BLANK;
		}
		else {
			return _description;
		}
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@JSON
	@Override
	public String getContent() {
		if (_content == null) {
			return StringPool.BLANK;
		}
		else {
			return _content;
		}
	}

	@Override
	public void setContent(String content) {
		_content = content;
	}

	@JSON
	@Override
	public Date getDisplayDate() {
		return _displayDate;
	}

	@Override
	public void setDisplayDate(Date displayDate) {
		_columnBitmask = -1L;

		if (_originalDisplayDate == null) {
			_originalDisplayDate = _displayDate;
		}

		_displayDate = displayDate;
	}

	public Date getOriginalDisplayDate() {
		return _originalDisplayDate;
	}

	@JSON
	@Override
	public boolean getAllowPingbacks() {
		return _allowPingbacks;
	}

	@Override
	public boolean isAllowPingbacks() {
		return _allowPingbacks;
	}

	@Override
	public void setAllowPingbacks(boolean allowPingbacks) {
		_allowPingbacks = allowPingbacks;
	}

	@JSON
	@Override
	public boolean getAllowTrackbacks() {
		return _allowTrackbacks;
	}

	@Override
	public boolean isAllowTrackbacks() {
		return _allowTrackbacks;
	}

	@Override
	public void setAllowTrackbacks(boolean allowTrackbacks) {
		_allowTrackbacks = allowTrackbacks;
	}

	@JSON
	@Override
	public String getTrackbacks() {
		if (_trackbacks == null) {
			return StringPool.BLANK;
		}
		else {
			return _trackbacks;
		}
	}

	@Override
	public void setTrackbacks(String trackbacks) {
		_trackbacks = trackbacks;
	}

	@JSON
	@Override
	public boolean getSmallImage() {
		return _smallImage;
	}

	@Override
	public boolean isSmallImage() {
		return _smallImage;
	}

	@Override
	public void setSmallImage(boolean smallImage) {
		_smallImage = smallImage;
	}

	@JSON
	@Override
	public long getSmallImageId() {
		return _smallImageId;
	}

	@Override
	public void setSmallImageId(long smallImageId) {
		_smallImageId = smallImageId;
	}

	@JSON
	@Override
	public String getSmallImageURL() {
		if (_smallImageURL == null) {
			return StringPool.BLANK;
		}
		else {
			return _smallImageURL;
		}
	}

	@Override
	public void setSmallImageURL(String smallImageURL) {
		_smallImageURL = smallImageURL;
	}

	@JSON
	@Override
	public int getStatus() {
		return _status;
	}

	@Override
	public void setStatus(int status) {
		_columnBitmask |= STATUS_COLUMN_BITMASK;

		if (!_setOriginalStatus) {
			_setOriginalStatus = true;

			_originalStatus = _status;
		}

		_status = status;
	}

	public int getOriginalStatus() {
		return _originalStatus;
	}

	@JSON
	@Override
	public long getStatusByUserId() {
		return _statusByUserId;
	}

	@Override
	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	@Override
	public String getStatusByUserUuid() throws SystemException {
		try {
			User user = UserLocalServiceUtil.getUserById(getStatusByUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}
	}

	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
	}

	@JSON
	@Override
	public String getStatusByUserName() {
		if (_statusByUserName == null) {
			return StringPool.BLANK;
		}
		else {
			return _statusByUserName;
		}
	}

	@Override
	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	@JSON
	@Override
	public Date getStatusDate() {
		return _statusDate;
	}

	@Override
	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				BlogsEntry.class.getName()));
	}

	@Override
	public TrashEntry getTrashEntry() throws PortalException, SystemException {
		if (!isInTrash()) {
			return null;
		}

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.fetchEntry(getModelClassName(),
				getTrashEntryClassPK());

		if (trashEntry != null) {
			return trashEntry;
		}

		TrashHandler trashHandler = getTrashHandler();

		if (!Validator.isNull(trashHandler.getContainerModelClassName())) {
			ContainerModel containerModel = null;

			try {
				containerModel = trashHandler.getParentContainerModel(this);
			}
			catch (NoSuchModelException nsme) {
				return null;
			}

			while (containerModel != null) {
				if (containerModel instanceof TrashedModel) {
					TrashedModel trashedModel = (TrashedModel)containerModel;

					return trashedModel.getTrashEntry();
				}

				trashHandler = TrashHandlerRegistryUtil.getTrashHandler(trashHandler.getContainerModelClassName());

				if (trashHandler == null) {
					return null;
				}

				containerModel = trashHandler.getContainerModel(containerModel.getParentContainerModelId());
			}
		}

		return null;
	}

	@Override
	public long getTrashEntryClassPK() {
		return getPrimaryKey();
	}

	@Override
	public TrashHandler getTrashHandler() {
		return TrashHandlerRegistryUtil.getTrashHandler(getModelClassName());
	}

	@Override
	public boolean isInTrash() {
		if (getStatus() == WorkflowConstants.STATUS_IN_TRASH) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isInTrashContainer() {
		TrashHandler trashHandler = getTrashHandler();

		if ((trashHandler == null) ||
				Validator.isNull(trashHandler.getContainerModelClassName())) {
			return false;
		}

		try {
			ContainerModel containerModel = trashHandler.getParentContainerModel(this);

			if (containerModel == null) {
				return false;
			}

			if (containerModel instanceof TrashedModel) {
				return ((TrashedModel)containerModel).isInTrash();
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isInTrashExplicitly() throws SystemException {
		if (!isInTrash()) {
			return false;
		}

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.fetchEntry(getModelClassName(),
				getTrashEntryClassPK());

		if (trashEntry != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isInTrashImplicitly() throws SystemException {
		if (!isInTrash()) {
			return false;
		}

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.fetchEntry(getModelClassName(),
				getTrashEntryClassPK());

		if (trashEntry != null) {
			return false;
		}

		return true;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #isApproved}
	 */
	@Deprecated
	@Override
	public boolean getApproved() {
		return isApproved();
	}

	@Override
	public boolean isApproved() {
		if (getStatus() == WorkflowConstants.STATUS_APPROVED) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isDenied() {
		if (getStatus() == WorkflowConstants.STATUS_DENIED) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isDraft() {
		if (getStatus() == WorkflowConstants.STATUS_DRAFT) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isExpired() {
		if (getStatus() == WorkflowConstants.STATUS_EXPIRED) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isInactive() {
		if (getStatus() == WorkflowConstants.STATUS_INACTIVE) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isIncomplete() {
		if (getStatus() == WorkflowConstants.STATUS_INCOMPLETE) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isPending() {
		if (getStatus() == WorkflowConstants.STATUS_PENDING) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isScheduled() {
		if (getStatus() == WorkflowConstants.STATUS_SCHEDULED) {
			return true;
		}
		else {
			return false;
		}
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			BlogsEntry.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public BlogsEntry toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (BlogsEntry)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		BlogsEntryImpl blogsEntryImpl = new BlogsEntryImpl();

		blogsEntryImpl.setUuid(getUuid());
		blogsEntryImpl.setEntryId(getEntryId());
		blogsEntryImpl.setGroupId(getGroupId());
		blogsEntryImpl.setCompanyId(getCompanyId());
		blogsEntryImpl.setUserId(getUserId());
		blogsEntryImpl.setUserName(getUserName());
		blogsEntryImpl.setCreateDate(getCreateDate());
		blogsEntryImpl.setModifiedDate(getModifiedDate());
		blogsEntryImpl.setTitle(getTitle());
		blogsEntryImpl.setUrlTitle(getUrlTitle());
		blogsEntryImpl.setDescription(getDescription());
		blogsEntryImpl.setContent(getContent());
		blogsEntryImpl.setDisplayDate(getDisplayDate());
		blogsEntryImpl.setAllowPingbacks(getAllowPingbacks());
		blogsEntryImpl.setAllowTrackbacks(getAllowTrackbacks());
		blogsEntryImpl.setTrackbacks(getTrackbacks());
		blogsEntryImpl.setSmallImage(getSmallImage());
		blogsEntryImpl.setSmallImageId(getSmallImageId());
		blogsEntryImpl.setSmallImageURL(getSmallImageURL());
		blogsEntryImpl.setStatus(getStatus());
		blogsEntryImpl.setStatusByUserId(getStatusByUserId());
		blogsEntryImpl.setStatusByUserName(getStatusByUserName());
		blogsEntryImpl.setStatusDate(getStatusDate());

		blogsEntryImpl.resetOriginalValues();

		return blogsEntryImpl;
	}

	@Override
	public int compareTo(BlogsEntry blogsEntry) {
		int value = 0;

		value = DateUtil.compareTo(getDisplayDate(), blogsEntry.getDisplayDate());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		value = DateUtil.compareTo(getCreateDate(), blogsEntry.getCreateDate());

		value = value * -1;

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

		if (!(obj instanceof BlogsEntry)) {
			return false;
		}

		BlogsEntry blogsEntry = (BlogsEntry)obj;

		long primaryKey = blogsEntry.getPrimaryKey();

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
		BlogsEntryModelImpl blogsEntryModelImpl = this;

		blogsEntryModelImpl._originalUuid = blogsEntryModelImpl._uuid;

		blogsEntryModelImpl._originalGroupId = blogsEntryModelImpl._groupId;

		blogsEntryModelImpl._setOriginalGroupId = false;

		blogsEntryModelImpl._originalCompanyId = blogsEntryModelImpl._companyId;

		blogsEntryModelImpl._setOriginalCompanyId = false;

		blogsEntryModelImpl._originalUserId = blogsEntryModelImpl._userId;

		blogsEntryModelImpl._setOriginalUserId = false;

		blogsEntryModelImpl._originalUrlTitle = blogsEntryModelImpl._urlTitle;

		blogsEntryModelImpl._originalDisplayDate = blogsEntryModelImpl._displayDate;

		blogsEntryModelImpl._originalStatus = blogsEntryModelImpl._status;

		blogsEntryModelImpl._setOriginalStatus = false;

		blogsEntryModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<BlogsEntry> toCacheModel() {
		BlogsEntryCacheModel blogsEntryCacheModel = new BlogsEntryCacheModel();

		blogsEntryCacheModel.uuid = getUuid();

		String uuid = blogsEntryCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			blogsEntryCacheModel.uuid = null;
		}

		blogsEntryCacheModel.entryId = getEntryId();

		blogsEntryCacheModel.groupId = getGroupId();

		blogsEntryCacheModel.companyId = getCompanyId();

		blogsEntryCacheModel.userId = getUserId();

		blogsEntryCacheModel.userName = getUserName();

		String userName = blogsEntryCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			blogsEntryCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			blogsEntryCacheModel.createDate = createDate.getTime();
		}
		else {
			blogsEntryCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			blogsEntryCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			blogsEntryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		blogsEntryCacheModel.title = getTitle();

		String title = blogsEntryCacheModel.title;

		if ((title != null) && (title.length() == 0)) {
			blogsEntryCacheModel.title = null;
		}

		blogsEntryCacheModel.urlTitle = getUrlTitle();

		String urlTitle = blogsEntryCacheModel.urlTitle;

		if ((urlTitle != null) && (urlTitle.length() == 0)) {
			blogsEntryCacheModel.urlTitle = null;
		}

		blogsEntryCacheModel.description = getDescription();

		String description = blogsEntryCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			blogsEntryCacheModel.description = null;
		}

		blogsEntryCacheModel.content = getContent();

		String content = blogsEntryCacheModel.content;

		if ((content != null) && (content.length() == 0)) {
			blogsEntryCacheModel.content = null;
		}

		Date displayDate = getDisplayDate();

		if (displayDate != null) {
			blogsEntryCacheModel.displayDate = displayDate.getTime();
		}
		else {
			blogsEntryCacheModel.displayDate = Long.MIN_VALUE;
		}

		blogsEntryCacheModel.allowPingbacks = getAllowPingbacks();

		blogsEntryCacheModel.allowTrackbacks = getAllowTrackbacks();

		blogsEntryCacheModel.trackbacks = getTrackbacks();

		String trackbacks = blogsEntryCacheModel.trackbacks;

		if ((trackbacks != null) && (trackbacks.length() == 0)) {
			blogsEntryCacheModel.trackbacks = null;
		}

		blogsEntryCacheModel.smallImage = getSmallImage();

		blogsEntryCacheModel.smallImageId = getSmallImageId();

		blogsEntryCacheModel.smallImageURL = getSmallImageURL();

		String smallImageURL = blogsEntryCacheModel.smallImageURL;

		if ((smallImageURL != null) && (smallImageURL.length() == 0)) {
			blogsEntryCacheModel.smallImageURL = null;
		}

		blogsEntryCacheModel.status = getStatus();

		blogsEntryCacheModel.statusByUserId = getStatusByUserId();

		blogsEntryCacheModel.statusByUserName = getStatusByUserName();

		String statusByUserName = blogsEntryCacheModel.statusByUserName;

		if ((statusByUserName != null) && (statusByUserName.length() == 0)) {
			blogsEntryCacheModel.statusByUserName = null;
		}

		Date statusDate = getStatusDate();

		if (statusDate != null) {
			blogsEntryCacheModel.statusDate = statusDate.getTime();
		}
		else {
			blogsEntryCacheModel.statusDate = Long.MIN_VALUE;
		}

		return blogsEntryCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(47);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", entryId=");
		sb.append(getEntryId());
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
		sb.append(", title=");
		sb.append(getTitle());
		sb.append(", urlTitle=");
		sb.append(getUrlTitle());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", content=");
		sb.append(getContent());
		sb.append(", displayDate=");
		sb.append(getDisplayDate());
		sb.append(", allowPingbacks=");
		sb.append(getAllowPingbacks());
		sb.append(", allowTrackbacks=");
		sb.append(getAllowTrackbacks());
		sb.append(", trackbacks=");
		sb.append(getTrackbacks());
		sb.append(", smallImage=");
		sb.append(getSmallImage());
		sb.append(", smallImageId=");
		sb.append(getSmallImageId());
		sb.append(", smallImageURL=");
		sb.append(getSmallImageURL());
		sb.append(", status=");
		sb.append(getStatus());
		sb.append(", statusByUserId=");
		sb.append(getStatusByUserId());
		sb.append(", statusByUserName=");
		sb.append(getStatusByUserName());
		sb.append(", statusDate=");
		sb.append(getStatusDate());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(73);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.blogs.model.BlogsEntry");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>entryId</column-name><column-value><![CDATA[");
		sb.append(getEntryId());
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
			"<column><column-name>title</column-name><column-value><![CDATA[");
		sb.append(getTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>urlTitle</column-name><column-value><![CDATA[");
		sb.append(getUrlTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>content</column-name><column-value><![CDATA[");
		sb.append(getContent());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>displayDate</column-name><column-value><![CDATA[");
		sb.append(getDisplayDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>allowPingbacks</column-name><column-value><![CDATA[");
		sb.append(getAllowPingbacks());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>allowTrackbacks</column-name><column-value><![CDATA[");
		sb.append(getAllowTrackbacks());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>trackbacks</column-name><column-value><![CDATA[");
		sb.append(getTrackbacks());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>smallImage</column-name><column-value><![CDATA[");
		sb.append(getSmallImage());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>smallImageId</column-name><column-value><![CDATA[");
		sb.append(getSmallImageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>smallImageURL</column-name><column-value><![CDATA[");
		sb.append(getSmallImageURL());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>status</column-name><column-value><![CDATA[");
		sb.append(getStatus());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusByUserId</column-name><column-value><![CDATA[");
		sb.append(getStatusByUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusByUserName</column-name><column-value><![CDATA[");
		sb.append(getStatusByUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusDate</column-name><column-value><![CDATA[");
		sb.append(getStatusDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = BlogsEntry.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			BlogsEntry.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _entryId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _title;
	private String _urlTitle;
	private String _originalUrlTitle;
	private String _description;
	private String _content;
	private Date _displayDate;
	private Date _originalDisplayDate;
	private boolean _allowPingbacks;
	private boolean _allowTrackbacks;
	private String _trackbacks;
	private boolean _smallImage;
	private long _smallImageId;
	private String _smallImageURL;
	private int _status;
	private int _originalStatus;
	private boolean _setOriginalStatus;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
	private long _columnBitmask;
	private BlogsEntry _escapedModel;
}