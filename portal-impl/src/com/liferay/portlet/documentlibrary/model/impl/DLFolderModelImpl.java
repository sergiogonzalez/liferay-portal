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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
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

import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderModel;
import com.liferay.portlet.documentlibrary.model.DLFolderSoap;
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
 * The base model implementation for the DLFolder service. Represents a row in the &quot;DLFolder&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.documentlibrary.model.DLFolderModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DLFolderImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFolderImpl
 * @see com.liferay.portlet.documentlibrary.model.DLFolder
 * @see com.liferay.portlet.documentlibrary.model.DLFolderModel
 * @generated
 */
@JSON(strict = true)
public class DLFolderModelImpl extends BaseModelImpl<DLFolder>
	implements DLFolderModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a document library folder model instance should use the {@link com.liferay.portlet.documentlibrary.model.DLFolder} interface instead.
	 */
	public static final String TABLE_NAME = "DLFolder";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "folderId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "repositoryId", Types.BIGINT },
			{ "mountPoint", Types.BOOLEAN },
			{ "parentFolderId", Types.BIGINT },
			{ "treePath", Types.VARCHAR },
			{ "name", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "lastPostDate", Types.TIMESTAMP },
			{ "defaultFileEntryTypeId", Types.BIGINT },
			{ "hidden_", Types.BOOLEAN },
			{ "overrideFileEntryTypes", Types.BOOLEAN },
			{ "status", Types.INTEGER },
			{ "statusByUserId", Types.BIGINT },
			{ "statusByUserName", Types.VARCHAR },
			{ "statusDate", Types.TIMESTAMP }
		};
	public static final String TABLE_SQL_CREATE = "create table DLFolder (uuid_ VARCHAR(75) null,folderId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,repositoryId LONG,mountPoint BOOLEAN,parentFolderId LONG,treePath STRING null,name VARCHAR(100) null,description STRING null,lastPostDate DATE null,defaultFileEntryTypeId LONG,hidden_ BOOLEAN,overrideFileEntryTypes BOOLEAN,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table DLFolder";
	public static final String ORDER_BY_JPQL = " ORDER BY dlFolder.parentFolderId ASC, dlFolder.name ASC";
	public static final String ORDER_BY_SQL = " ORDER BY DLFolder.parentFolderId ASC, DLFolder.name ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.documentlibrary.model.DLFolder"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.documentlibrary.model.DLFolder"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portlet.documentlibrary.model.DLFolder"),
			true);
	public static long COMPANYID_COLUMN_BITMASK = 1L;
	public static long FOLDERID_COLUMN_BITMASK = 2L;
	public static long GROUPID_COLUMN_BITMASK = 4L;
	public static long HIDDEN_COLUMN_BITMASK = 8L;
	public static long MOUNTPOINT_COLUMN_BITMASK = 16L;
	public static long NAME_COLUMN_BITMASK = 32L;
	public static long PARENTFOLDERID_COLUMN_BITMASK = 64L;
	public static long REPOSITORYID_COLUMN_BITMASK = 128L;
	public static long STATUS_COLUMN_BITMASK = 256L;
	public static long UUID_COLUMN_BITMASK = 512L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static DLFolder toModel(DLFolderSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		DLFolder model = new DLFolderImpl();

		model.setUuid(soapModel.getUuid());
		model.setFolderId(soapModel.getFolderId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setRepositoryId(soapModel.getRepositoryId());
		model.setMountPoint(soapModel.getMountPoint());
		model.setParentFolderId(soapModel.getParentFolderId());
		model.setTreePath(soapModel.getTreePath());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setLastPostDate(soapModel.getLastPostDate());
		model.setDefaultFileEntryTypeId(soapModel.getDefaultFileEntryTypeId());
		model.setHidden(soapModel.getHidden());
		model.setOverrideFileEntryTypes(soapModel.getOverrideFileEntryTypes());
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
	public static List<DLFolder> toModels(DLFolderSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<DLFolder> models = new ArrayList<DLFolder>(soapModels.length);

		for (DLFolderSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final String MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_NAME = "DLFileEntryTypes_DLFolders";
	public static final Object[][] MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_COLUMNS =
		{
			{ "fileEntryTypeId", Types.BIGINT },
			{ "folderId", Types.BIGINT }
		};
	public static final String MAPPING_TABLE_DLFILEENTRYTYPES_DLFOLDERS_SQL_CREATE =
		"create table DLFileEntryTypes_DLFolders (fileEntryTypeId LONG not null,folderId LONG not null,primary key (fileEntryTypeId, folderId))";
	public static final boolean FINDER_CACHE_ENABLED_DLFILEENTRYTYPES_DLFOLDERS = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.DLFileEntryTypes_DLFolders"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.documentlibrary.model.DLFolder"));

	public DLFolderModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _folderId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setFolderId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _folderId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return DLFolder.class;
	}

	@Override
	public String getModelClassName() {
		return DLFolder.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("folderId", getFolderId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("mountPoint", getMountPoint());
		attributes.put("parentFolderId", getParentFolderId());
		attributes.put("treePath", getTreePath());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("lastPostDate", getLastPostDate());
		attributes.put("defaultFileEntryTypeId", getDefaultFileEntryTypeId());
		attributes.put("hidden", getHidden());
		attributes.put("overrideFileEntryTypes", getOverrideFileEntryTypes());
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

		Long folderId = (Long)attributes.get("folderId");

		if (folderId != null) {
			setFolderId(folderId);
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

		Long repositoryId = (Long)attributes.get("repositoryId");

		if (repositoryId != null) {
			setRepositoryId(repositoryId);
		}

		Boolean mountPoint = (Boolean)attributes.get("mountPoint");

		if (mountPoint != null) {
			setMountPoint(mountPoint);
		}

		Long parentFolderId = (Long)attributes.get("parentFolderId");

		if (parentFolderId != null) {
			setParentFolderId(parentFolderId);
		}

		String treePath = (String)attributes.get("treePath");

		if (treePath != null) {
			setTreePath(treePath);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Date lastPostDate = (Date)attributes.get("lastPostDate");

		if (lastPostDate != null) {
			setLastPostDate(lastPostDate);
		}

		Long defaultFileEntryTypeId = (Long)attributes.get(
				"defaultFileEntryTypeId");

		if (defaultFileEntryTypeId != null) {
			setDefaultFileEntryTypeId(defaultFileEntryTypeId);
		}

		Boolean hidden = (Boolean)attributes.get("hidden");

		if (hidden != null) {
			setHidden(hidden);
		}

		Boolean overrideFileEntryTypes = (Boolean)attributes.get(
				"overrideFileEntryTypes");

		if (overrideFileEntryTypes != null) {
			setOverrideFileEntryTypes(overrideFileEntryTypes);
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
	public long getFolderId() {
		return _folderId;
	}

	@Override
	public void setFolderId(long folderId) {
		_columnBitmask |= FOLDERID_COLUMN_BITMASK;

		if (!_setOriginalFolderId) {
			_setOriginalFolderId = true;

			_originalFolderId = _folderId;
		}

		_folderId = folderId;
	}

	public long getOriginalFolderId() {
		return _originalFolderId;
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
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
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
	public long getRepositoryId() {
		return _repositoryId;
	}

	@Override
	public void setRepositoryId(long repositoryId) {
		_columnBitmask |= REPOSITORYID_COLUMN_BITMASK;

		if (!_setOriginalRepositoryId) {
			_setOriginalRepositoryId = true;

			_originalRepositoryId = _repositoryId;
		}

		_repositoryId = repositoryId;
	}

	public long getOriginalRepositoryId() {
		return _originalRepositoryId;
	}

	@JSON
	@Override
	public boolean getMountPoint() {
		return _mountPoint;
	}

	@Override
	public boolean isMountPoint() {
		return _mountPoint;
	}

	@Override
	public void setMountPoint(boolean mountPoint) {
		_columnBitmask |= MOUNTPOINT_COLUMN_BITMASK;

		if (!_setOriginalMountPoint) {
			_setOriginalMountPoint = true;

			_originalMountPoint = _mountPoint;
		}

		_mountPoint = mountPoint;
	}

	public boolean getOriginalMountPoint() {
		return _originalMountPoint;
	}

	@JSON
	@Override
	public long getParentFolderId() {
		return _parentFolderId;
	}

	@Override
	public void setParentFolderId(long parentFolderId) {
		_columnBitmask = -1L;

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
	@Override
	public String getTreePath() {
		if (_treePath == null) {
			return StringPool.BLANK;
		}
		else {
			return _treePath;
		}
	}

	@Override
	public void setTreePath(String treePath) {
		_treePath = treePath;
	}

	@JSON
	@Override
	public String getName() {
		if (_name == null) {
			return StringPool.BLANK;
		}
		else {
			return _name;
		}
	}

	@Override
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
	public Date getLastPostDate() {
		return _lastPostDate;
	}

	@Override
	public void setLastPostDate(Date lastPostDate) {
		_lastPostDate = lastPostDate;
	}

	@JSON
	@Override
	public long getDefaultFileEntryTypeId() {
		return _defaultFileEntryTypeId;
	}

	@Override
	public void setDefaultFileEntryTypeId(long defaultFileEntryTypeId) {
		_defaultFileEntryTypeId = defaultFileEntryTypeId;
	}

	@JSON
	@Override
	public boolean getHidden() {
		return _hidden;
	}

	@Override
	public boolean isHidden() {
		return _hidden;
	}

	@Override
	public void setHidden(boolean hidden) {
		_columnBitmask |= HIDDEN_COLUMN_BITMASK;

		if (!_setOriginalHidden) {
			_setOriginalHidden = true;

			_originalHidden = _hidden;
		}

		_hidden = hidden;
	}

	public boolean getOriginalHidden() {
		return _originalHidden;
	}

	@JSON
	@Override
	public boolean getOverrideFileEntryTypes() {
		return _overrideFileEntryTypes;
	}

	@Override
	public boolean isOverrideFileEntryTypes() {
		return _overrideFileEntryTypes;
	}

	@Override
	public void setOverrideFileEntryTypes(boolean overrideFileEntryTypes) {
		_overrideFileEntryTypes = overrideFileEntryTypes;
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
	public String getStatusByUserUuid() {
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
	public long getContainerModelId() {
		return getFolderId();
	}

	@Override
	public void setContainerModelId(long containerModelId) {
		_folderId = containerModelId;
	}

	@Override
	public long getParentContainerModelId() {
		return getParentFolderId();
	}

	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		_parentFolderId = parentContainerModelId;
	}

	@Override
	public String getContainerModelName() {
		return String.valueOf(getName());
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				DLFolder.class.getName()));
	}

	@Override
	public TrashEntry getTrashEntry() throws PortalException {
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
	public boolean isInTrashExplicitly() {
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
	public boolean isInTrashImplicitly() {
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
			DLFolder.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public DLFolder toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (DLFolder)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		DLFolderImpl dlFolderImpl = new DLFolderImpl();

		dlFolderImpl.setUuid(getUuid());
		dlFolderImpl.setFolderId(getFolderId());
		dlFolderImpl.setGroupId(getGroupId());
		dlFolderImpl.setCompanyId(getCompanyId());
		dlFolderImpl.setUserId(getUserId());
		dlFolderImpl.setUserName(getUserName());
		dlFolderImpl.setCreateDate(getCreateDate());
		dlFolderImpl.setModifiedDate(getModifiedDate());
		dlFolderImpl.setRepositoryId(getRepositoryId());
		dlFolderImpl.setMountPoint(getMountPoint());
		dlFolderImpl.setParentFolderId(getParentFolderId());
		dlFolderImpl.setTreePath(getTreePath());
		dlFolderImpl.setName(getName());
		dlFolderImpl.setDescription(getDescription());
		dlFolderImpl.setLastPostDate(getLastPostDate());
		dlFolderImpl.setDefaultFileEntryTypeId(getDefaultFileEntryTypeId());
		dlFolderImpl.setHidden(getHidden());
		dlFolderImpl.setOverrideFileEntryTypes(getOverrideFileEntryTypes());
		dlFolderImpl.setStatus(getStatus());
		dlFolderImpl.setStatusByUserId(getStatusByUserId());
		dlFolderImpl.setStatusByUserName(getStatusByUserName());
		dlFolderImpl.setStatusDate(getStatusDate());

		dlFolderImpl.resetOriginalValues();

		return dlFolderImpl;
	}

	@Override
	public int compareTo(DLFolder dlFolder) {
		int value = 0;

		if (getParentFolderId() < dlFolder.getParentFolderId()) {
			value = -1;
		}
		else if (getParentFolderId() > dlFolder.getParentFolderId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = getName().compareToIgnoreCase(dlFolder.getName());

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

		if (!(obj instanceof DLFolder)) {
			return false;
		}

		DLFolder dlFolder = (DLFolder)obj;

		long primaryKey = dlFolder.getPrimaryKey();

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
		DLFolderModelImpl dlFolderModelImpl = this;

		dlFolderModelImpl._originalUuid = dlFolderModelImpl._uuid;

		dlFolderModelImpl._originalFolderId = dlFolderModelImpl._folderId;

		dlFolderModelImpl._setOriginalFolderId = false;

		dlFolderModelImpl._originalGroupId = dlFolderModelImpl._groupId;

		dlFolderModelImpl._setOriginalGroupId = false;

		dlFolderModelImpl._originalCompanyId = dlFolderModelImpl._companyId;

		dlFolderModelImpl._setOriginalCompanyId = false;

		dlFolderModelImpl._originalRepositoryId = dlFolderModelImpl._repositoryId;

		dlFolderModelImpl._setOriginalRepositoryId = false;

		dlFolderModelImpl._originalMountPoint = dlFolderModelImpl._mountPoint;

		dlFolderModelImpl._setOriginalMountPoint = false;

		dlFolderModelImpl._originalParentFolderId = dlFolderModelImpl._parentFolderId;

		dlFolderModelImpl._setOriginalParentFolderId = false;

		dlFolderModelImpl._originalName = dlFolderModelImpl._name;

		dlFolderModelImpl._originalHidden = dlFolderModelImpl._hidden;

		dlFolderModelImpl._setOriginalHidden = false;

		dlFolderModelImpl._originalStatus = dlFolderModelImpl._status;

		dlFolderModelImpl._setOriginalStatus = false;

		dlFolderModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<DLFolder> toCacheModel() {
		DLFolderCacheModel dlFolderCacheModel = new DLFolderCacheModel();

		dlFolderCacheModel.uuid = getUuid();

		String uuid = dlFolderCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			dlFolderCacheModel.uuid = null;
		}

		dlFolderCacheModel.folderId = getFolderId();

		dlFolderCacheModel.groupId = getGroupId();

		dlFolderCacheModel.companyId = getCompanyId();

		dlFolderCacheModel.userId = getUserId();

		dlFolderCacheModel.userName = getUserName();

		String userName = dlFolderCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			dlFolderCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			dlFolderCacheModel.createDate = createDate.getTime();
		}
		else {
			dlFolderCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			dlFolderCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			dlFolderCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		dlFolderCacheModel.repositoryId = getRepositoryId();

		dlFolderCacheModel.mountPoint = getMountPoint();

		dlFolderCacheModel.parentFolderId = getParentFolderId();

		dlFolderCacheModel.treePath = getTreePath();

		String treePath = dlFolderCacheModel.treePath;

		if ((treePath != null) && (treePath.length() == 0)) {
			dlFolderCacheModel.treePath = null;
		}

		dlFolderCacheModel.name = getName();

		String name = dlFolderCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			dlFolderCacheModel.name = null;
		}

		dlFolderCacheModel.description = getDescription();

		String description = dlFolderCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			dlFolderCacheModel.description = null;
		}

		Date lastPostDate = getLastPostDate();

		if (lastPostDate != null) {
			dlFolderCacheModel.lastPostDate = lastPostDate.getTime();
		}
		else {
			dlFolderCacheModel.lastPostDate = Long.MIN_VALUE;
		}

		dlFolderCacheModel.defaultFileEntryTypeId = getDefaultFileEntryTypeId();

		dlFolderCacheModel.hidden = getHidden();

		dlFolderCacheModel.overrideFileEntryTypes = getOverrideFileEntryTypes();

		dlFolderCacheModel.status = getStatus();

		dlFolderCacheModel.statusByUserId = getStatusByUserId();

		dlFolderCacheModel.statusByUserName = getStatusByUserName();

		String statusByUserName = dlFolderCacheModel.statusByUserName;

		if ((statusByUserName != null) && (statusByUserName.length() == 0)) {
			dlFolderCacheModel.statusByUserName = null;
		}

		Date statusDate = getStatusDate();

		if (statusDate != null) {
			dlFolderCacheModel.statusDate = statusDate.getTime();
		}
		else {
			dlFolderCacheModel.statusDate = Long.MIN_VALUE;
		}

		return dlFolderCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(45);

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
		sb.append(", repositoryId=");
		sb.append(getRepositoryId());
		sb.append(", mountPoint=");
		sb.append(getMountPoint());
		sb.append(", parentFolderId=");
		sb.append(getParentFolderId());
		sb.append(", treePath=");
		sb.append(getTreePath());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", lastPostDate=");
		sb.append(getLastPostDate());
		sb.append(", defaultFileEntryTypeId=");
		sb.append(getDefaultFileEntryTypeId());
		sb.append(", hidden=");
		sb.append(getHidden());
		sb.append(", overrideFileEntryTypes=");
		sb.append(getOverrideFileEntryTypes());
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
		StringBundler sb = new StringBundler(70);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.documentlibrary.model.DLFolder");
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
			"<column><column-name>repositoryId</column-name><column-value><![CDATA[");
		sb.append(getRepositoryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>mountPoint</column-name><column-value><![CDATA[");
		sb.append(getMountPoint());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>parentFolderId</column-name><column-value><![CDATA[");
		sb.append(getParentFolderId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>treePath</column-name><column-value><![CDATA[");
		sb.append(getTreePath());
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
			"<column><column-name>lastPostDate</column-name><column-value><![CDATA[");
		sb.append(getLastPostDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>defaultFileEntryTypeId</column-name><column-value><![CDATA[");
		sb.append(getDefaultFileEntryTypeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>hidden</column-name><column-value><![CDATA[");
		sb.append(getHidden());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>overrideFileEntryTypes</column-name><column-value><![CDATA[");
		sb.append(getOverrideFileEntryTypes());
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

	private static ClassLoader _classLoader = DLFolder.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			DLFolder.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _folderId;
	private long _originalFolderId;
	private boolean _setOriginalFolderId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _repositoryId;
	private long _originalRepositoryId;
	private boolean _setOriginalRepositoryId;
	private boolean _mountPoint;
	private boolean _originalMountPoint;
	private boolean _setOriginalMountPoint;
	private long _parentFolderId;
	private long _originalParentFolderId;
	private boolean _setOriginalParentFolderId;
	private String _treePath;
	private String _name;
	private String _originalName;
	private String _description;
	private Date _lastPostDate;
	private long _defaultFileEntryTypeId;
	private boolean _hidden;
	private boolean _originalHidden;
	private boolean _setOriginalHidden;
	private boolean _overrideFileEntryTypes;
	private int _status;
	private int _originalStatus;
	private boolean _setOriginalStatus;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
	private long _columnBitmask;
	private DLFolder _escapedModel;
}