/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFileVersionModel;
import com.liferay.portlet.documentlibrary.model.DLFileVersionSoap;
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
 * The base model implementation for the DLFileVersion service. Represents a row in the &quot;DLFileVersion&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.documentlibrary.model.DLFileVersionModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DLFileVersionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionImpl
 * @see com.liferay.portlet.documentlibrary.model.DLFileVersion
 * @see com.liferay.portlet.documentlibrary.model.DLFileVersionModel
 * @generated
 */
@JSON(strict = true)
public class DLFileVersionModelImpl extends BaseModelImpl<DLFileVersion>
	implements DLFileVersionModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a document library file version model instance should use the {@link com.liferay.portlet.documentlibrary.model.DLFileVersion} interface instead.
	 */
	public static final String TABLE_NAME = "DLFileVersion";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "fileVersionId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "repositoryId", Types.BIGINT },
			{ "folderId", Types.BIGINT },
			{ "treePath", Types.VARCHAR },
			{ "fileEntryId", Types.BIGINT },
			{ "extension", Types.VARCHAR },
			{ "mimeType", Types.VARCHAR },
			{ "title", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "changeLog", Types.VARCHAR },
			{ "extraSettings", Types.CLOB },
			{ "fileEntryTypeId", Types.BIGINT },
			{ "version", Types.VARCHAR },
			{ "size_", Types.BIGINT },
			{ "checksum", Types.VARCHAR },
			{ "status", Types.INTEGER },
			{ "statusByUserId", Types.BIGINT },
			{ "statusByUserName", Types.VARCHAR },
			{ "statusDate", Types.TIMESTAMP }
		};
	public static final String TABLE_SQL_CREATE = "create table DLFileVersion (uuid_ VARCHAR(75) null,fileVersionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,repositoryId LONG,folderId LONG,treePath STRING null,fileEntryId LONG,extension VARCHAR(75) null,mimeType VARCHAR(75) null,title VARCHAR(255) null,description STRING null,changeLog VARCHAR(75) null,extraSettings TEXT null,fileEntryTypeId LONG,version VARCHAR(75) null,size_ LONG,checksum VARCHAR(75) null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table DLFileVersion";
	public static final String ORDER_BY_JPQL = " ORDER BY dlFileVersion.fileEntryId DESC, dlFileVersion.createDate DESC";
	public static final String ORDER_BY_SQL = " ORDER BY DLFileVersion.fileEntryId DESC, DLFileVersion.createDate DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.documentlibrary.model.DLFileVersion"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.documentlibrary.model.DLFileVersion"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portlet.documentlibrary.model.DLFileVersion"),
			true);
	public static long COMPANYID_COLUMN_BITMASK = 1L;
	public static long FILEENTRYID_COLUMN_BITMASK = 2L;
	public static long FOLDERID_COLUMN_BITMASK = 4L;
	public static long GROUPID_COLUMN_BITMASK = 8L;
	public static long MIMETYPE_COLUMN_BITMASK = 16L;
	public static long STATUS_COLUMN_BITMASK = 32L;
	public static long TITLE_COLUMN_BITMASK = 64L;
	public static long UUID_COLUMN_BITMASK = 128L;
	public static long VERSION_COLUMN_BITMASK = 256L;
	public static long CREATEDATE_COLUMN_BITMASK = 512L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static DLFileVersion toModel(DLFileVersionSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		DLFileVersion model = new DLFileVersionImpl();

		model.setUuid(soapModel.getUuid());
		model.setFileVersionId(soapModel.getFileVersionId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setRepositoryId(soapModel.getRepositoryId());
		model.setFolderId(soapModel.getFolderId());
		model.setTreePath(soapModel.getTreePath());
		model.setFileEntryId(soapModel.getFileEntryId());
		model.setExtension(soapModel.getExtension());
		model.setMimeType(soapModel.getMimeType());
		model.setTitle(soapModel.getTitle());
		model.setDescription(soapModel.getDescription());
		model.setChangeLog(soapModel.getChangeLog());
		model.setExtraSettings(soapModel.getExtraSettings());
		model.setFileEntryTypeId(soapModel.getFileEntryTypeId());
		model.setVersion(soapModel.getVersion());
		model.setSize(soapModel.getSize());
		model.setChecksum(soapModel.getChecksum());
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
	public static List<DLFileVersion> toModels(DLFileVersionSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<DLFileVersion> models = new ArrayList<DLFileVersion>(soapModels.length);

		for (DLFileVersionSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.documentlibrary.model.DLFileVersion"));

	public DLFileVersionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _fileVersionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setFileVersionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _fileVersionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return DLFileVersion.class;
	}

	@Override
	public String getModelClassName() {
		return DLFileVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("fileVersionId", getFileVersionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("folderId", getFolderId());
		attributes.put("treePath", getTreePath());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("extension", getExtension());
		attributes.put("mimeType", getMimeType());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("changeLog", getChangeLog());
		attributes.put("extraSettings", getExtraSettings());
		attributes.put("fileEntryTypeId", getFileEntryTypeId());
		attributes.put("version", getVersion());
		attributes.put("size", getSize());
		attributes.put("checksum", getChecksum());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long fileVersionId = (Long)attributes.get("fileVersionId");

		if (fileVersionId != null) {
			setFileVersionId(fileVersionId);
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

		Long folderId = (Long)attributes.get("folderId");

		if (folderId != null) {
			setFolderId(folderId);
		}

		String treePath = (String)attributes.get("treePath");

		if (treePath != null) {
			setTreePath(treePath);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		String extension = (String)attributes.get("extension");

		if (extension != null) {
			setExtension(extension);
		}

		String mimeType = (String)attributes.get("mimeType");

		if (mimeType != null) {
			setMimeType(mimeType);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String changeLog = (String)attributes.get("changeLog");

		if (changeLog != null) {
			setChangeLog(changeLog);
		}

		String extraSettings = (String)attributes.get("extraSettings");

		if (extraSettings != null) {
			setExtraSettings(extraSettings);
		}

		Long fileEntryTypeId = (Long)attributes.get("fileEntryTypeId");

		if (fileEntryTypeId != null) {
			setFileEntryTypeId(fileEntryTypeId);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Long size = (Long)attributes.get("size");

		if (size != null) {
			setSize(size);
		}

		String checksum = (String)attributes.get("checksum");

		if (checksum != null) {
			setChecksum(checksum);
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
	public long getFileVersionId() {
		return _fileVersionId;
	}

	@Override
	public void setFileVersionId(long fileVersionId) {
		_fileVersionId = fileVersionId;
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
	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
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
	public long getRepositoryId() {
		return _repositoryId;
	}

	@Override
	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
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
	public long getFileEntryId() {
		return _fileEntryId;
	}

	@Override
	public void setFileEntryId(long fileEntryId) {
		_columnBitmask = -1L;

		if (!_setOriginalFileEntryId) {
			_setOriginalFileEntryId = true;

			_originalFileEntryId = _fileEntryId;
		}

		_fileEntryId = fileEntryId;
	}

	public long getOriginalFileEntryId() {
		return _originalFileEntryId;
	}

	@JSON
	@Override
	public String getExtension() {
		if (_extension == null) {
			return StringPool.BLANK;
		}
		else {
			return _extension;
		}
	}

	@Override
	public void setExtension(String extension) {
		_extension = extension;
	}

	@JSON
	@Override
	public String getMimeType() {
		if (_mimeType == null) {
			return StringPool.BLANK;
		}
		else {
			return _mimeType;
		}
	}

	@Override
	public void setMimeType(String mimeType) {
		_columnBitmask |= MIMETYPE_COLUMN_BITMASK;

		if (_originalMimeType == null) {
			_originalMimeType = _mimeType;
		}

		_mimeType = mimeType;
	}

	public String getOriginalMimeType() {
		return GetterUtil.getString(_originalMimeType);
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
		_columnBitmask |= TITLE_COLUMN_BITMASK;

		if (_originalTitle == null) {
			_originalTitle = _title;
		}

		_title = title;
	}

	public String getOriginalTitle() {
		return GetterUtil.getString(_originalTitle);
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
	public String getChangeLog() {
		if (_changeLog == null) {
			return StringPool.BLANK;
		}
		else {
			return _changeLog;
		}
	}

	@Override
	public void setChangeLog(String changeLog) {
		_changeLog = changeLog;
	}

	@JSON
	@Override
	public String getExtraSettings() {
		if (_extraSettings == null) {
			return StringPool.BLANK;
		}
		else {
			return _extraSettings;
		}
	}

	@Override
	public void setExtraSettings(String extraSettings) {
		_extraSettings = extraSettings;
	}

	@JSON
	@Override
	public long getFileEntryTypeId() {
		return _fileEntryTypeId;
	}

	@Override
	public void setFileEntryTypeId(long fileEntryTypeId) {
		_fileEntryTypeId = fileEntryTypeId;
	}

	@JSON
	@Override
	public String getVersion() {
		if (_version == null) {
			return StringPool.BLANK;
		}
		else {
			return _version;
		}
	}

	@Override
	public void setVersion(String version) {
		_columnBitmask |= VERSION_COLUMN_BITMASK;

		if (_originalVersion == null) {
			_originalVersion = _version;
		}

		_version = version;
	}

	public String getOriginalVersion() {
		return GetterUtil.getString(_originalVersion);
	}

	@JSON
	@Override
	public long getSize() {
		return _size;
	}

	@Override
	public void setSize(long size) {
		_size = size;
	}

	@JSON
	@Override
	public String getChecksum() {
		if (_checksum == null) {
			return StringPool.BLANK;
		}
		else {
			return _checksum;
		}
	}

	@Override
	public void setChecksum(String checksum) {
		_checksum = checksum;
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
		return PortalUtil.getUserValue(getStatusByUserId(), "uuid",
			_statusByUserUuid);
	}

	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		_statusByUserUuid = statusByUserUuid;
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
				DLFileVersion.class.getName()));
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #isApproved}
	 */
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
	public boolean isInTrash() {
		if (getStatus() == WorkflowConstants.STATUS_IN_TRASH) {
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
			DLFileVersion.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public DLFileVersion toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (DLFileVersion)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		DLFileVersionImpl dlFileVersionImpl = new DLFileVersionImpl();

		dlFileVersionImpl.setUuid(getUuid());
		dlFileVersionImpl.setFileVersionId(getFileVersionId());
		dlFileVersionImpl.setGroupId(getGroupId());
		dlFileVersionImpl.setCompanyId(getCompanyId());
		dlFileVersionImpl.setUserId(getUserId());
		dlFileVersionImpl.setUserName(getUserName());
		dlFileVersionImpl.setCreateDate(getCreateDate());
		dlFileVersionImpl.setModifiedDate(getModifiedDate());
		dlFileVersionImpl.setRepositoryId(getRepositoryId());
		dlFileVersionImpl.setFolderId(getFolderId());
		dlFileVersionImpl.setTreePath(getTreePath());
		dlFileVersionImpl.setFileEntryId(getFileEntryId());
		dlFileVersionImpl.setExtension(getExtension());
		dlFileVersionImpl.setMimeType(getMimeType());
		dlFileVersionImpl.setTitle(getTitle());
		dlFileVersionImpl.setDescription(getDescription());
		dlFileVersionImpl.setChangeLog(getChangeLog());
		dlFileVersionImpl.setExtraSettings(getExtraSettings());
		dlFileVersionImpl.setFileEntryTypeId(getFileEntryTypeId());
		dlFileVersionImpl.setVersion(getVersion());
		dlFileVersionImpl.setSize(getSize());
		dlFileVersionImpl.setChecksum(getChecksum());
		dlFileVersionImpl.setStatus(getStatus());
		dlFileVersionImpl.setStatusByUserId(getStatusByUserId());
		dlFileVersionImpl.setStatusByUserName(getStatusByUserName());
		dlFileVersionImpl.setStatusDate(getStatusDate());

		dlFileVersionImpl.resetOriginalValues();

		return dlFileVersionImpl;
	}

	@Override
	public int compareTo(DLFileVersion dlFileVersion) {
		int value = 0;

		if (getFileEntryId() < dlFileVersion.getFileEntryId()) {
			value = -1;
		}
		else if (getFileEntryId() > dlFileVersion.getFileEntryId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		value = value * -1;

		if (value != 0) {
			return value;
		}

		value = DateUtil.compareTo(getCreateDate(),
				dlFileVersion.getCreateDate());

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

		if (!(obj instanceof DLFileVersion)) {
			return false;
		}

		DLFileVersion dlFileVersion = (DLFileVersion)obj;

		long primaryKey = dlFileVersion.getPrimaryKey();

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
		DLFileVersionModelImpl dlFileVersionModelImpl = this;

		dlFileVersionModelImpl._originalUuid = dlFileVersionModelImpl._uuid;

		dlFileVersionModelImpl._originalGroupId = dlFileVersionModelImpl._groupId;

		dlFileVersionModelImpl._setOriginalGroupId = false;

		dlFileVersionModelImpl._originalCompanyId = dlFileVersionModelImpl._companyId;

		dlFileVersionModelImpl._setOriginalCompanyId = false;

		dlFileVersionModelImpl._originalFolderId = dlFileVersionModelImpl._folderId;

		dlFileVersionModelImpl._setOriginalFolderId = false;

		dlFileVersionModelImpl._originalFileEntryId = dlFileVersionModelImpl._fileEntryId;

		dlFileVersionModelImpl._setOriginalFileEntryId = false;

		dlFileVersionModelImpl._originalMimeType = dlFileVersionModelImpl._mimeType;

		dlFileVersionModelImpl._originalTitle = dlFileVersionModelImpl._title;

		dlFileVersionModelImpl._originalVersion = dlFileVersionModelImpl._version;

		dlFileVersionModelImpl._originalStatus = dlFileVersionModelImpl._status;

		dlFileVersionModelImpl._setOriginalStatus = false;

		dlFileVersionModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<DLFileVersion> toCacheModel() {
		DLFileVersionCacheModel dlFileVersionCacheModel = new DLFileVersionCacheModel();

		dlFileVersionCacheModel.uuid = getUuid();

		String uuid = dlFileVersionCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			dlFileVersionCacheModel.uuid = null;
		}

		dlFileVersionCacheModel.fileVersionId = getFileVersionId();

		dlFileVersionCacheModel.groupId = getGroupId();

		dlFileVersionCacheModel.companyId = getCompanyId();

		dlFileVersionCacheModel.userId = getUserId();

		dlFileVersionCacheModel.userName = getUserName();

		String userName = dlFileVersionCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			dlFileVersionCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			dlFileVersionCacheModel.createDate = createDate.getTime();
		}
		else {
			dlFileVersionCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			dlFileVersionCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			dlFileVersionCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		dlFileVersionCacheModel.repositoryId = getRepositoryId();

		dlFileVersionCacheModel.folderId = getFolderId();

		dlFileVersionCacheModel.treePath = getTreePath();

		String treePath = dlFileVersionCacheModel.treePath;

		if ((treePath != null) && (treePath.length() == 0)) {
			dlFileVersionCacheModel.treePath = null;
		}

		dlFileVersionCacheModel.fileEntryId = getFileEntryId();

		dlFileVersionCacheModel.extension = getExtension();

		String extension = dlFileVersionCacheModel.extension;

		if ((extension != null) && (extension.length() == 0)) {
			dlFileVersionCacheModel.extension = null;
		}

		dlFileVersionCacheModel.mimeType = getMimeType();

		String mimeType = dlFileVersionCacheModel.mimeType;

		if ((mimeType != null) && (mimeType.length() == 0)) {
			dlFileVersionCacheModel.mimeType = null;
		}

		dlFileVersionCacheModel.title = getTitle();

		String title = dlFileVersionCacheModel.title;

		if ((title != null) && (title.length() == 0)) {
			dlFileVersionCacheModel.title = null;
		}

		dlFileVersionCacheModel.description = getDescription();

		String description = dlFileVersionCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			dlFileVersionCacheModel.description = null;
		}

		dlFileVersionCacheModel.changeLog = getChangeLog();

		String changeLog = dlFileVersionCacheModel.changeLog;

		if ((changeLog != null) && (changeLog.length() == 0)) {
			dlFileVersionCacheModel.changeLog = null;
		}

		dlFileVersionCacheModel.extraSettings = getExtraSettings();

		String extraSettings = dlFileVersionCacheModel.extraSettings;

		if ((extraSettings != null) && (extraSettings.length() == 0)) {
			dlFileVersionCacheModel.extraSettings = null;
		}

		dlFileVersionCacheModel.fileEntryTypeId = getFileEntryTypeId();

		dlFileVersionCacheModel.version = getVersion();

		String version = dlFileVersionCacheModel.version;

		if ((version != null) && (version.length() == 0)) {
			dlFileVersionCacheModel.version = null;
		}

		dlFileVersionCacheModel.size = getSize();

		dlFileVersionCacheModel.checksum = getChecksum();

		String checksum = dlFileVersionCacheModel.checksum;

		if ((checksum != null) && (checksum.length() == 0)) {
			dlFileVersionCacheModel.checksum = null;
		}

		dlFileVersionCacheModel.status = getStatus();

		dlFileVersionCacheModel.statusByUserId = getStatusByUserId();

		dlFileVersionCacheModel.statusByUserName = getStatusByUserName();

		String statusByUserName = dlFileVersionCacheModel.statusByUserName;

		if ((statusByUserName != null) && (statusByUserName.length() == 0)) {
			dlFileVersionCacheModel.statusByUserName = null;
		}

		Date statusDate = getStatusDate();

		if (statusDate != null) {
			dlFileVersionCacheModel.statusDate = statusDate.getTime();
		}
		else {
			dlFileVersionCacheModel.statusDate = Long.MIN_VALUE;
		}

		return dlFileVersionCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(53);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", fileVersionId=");
		sb.append(getFileVersionId());
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
		sb.append(", folderId=");
		sb.append(getFolderId());
		sb.append(", treePath=");
		sb.append(getTreePath());
		sb.append(", fileEntryId=");
		sb.append(getFileEntryId());
		sb.append(", extension=");
		sb.append(getExtension());
		sb.append(", mimeType=");
		sb.append(getMimeType());
		sb.append(", title=");
		sb.append(getTitle());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", changeLog=");
		sb.append(getChangeLog());
		sb.append(", extraSettings=");
		sb.append(getExtraSettings());
		sb.append(", fileEntryTypeId=");
		sb.append(getFileEntryTypeId());
		sb.append(", version=");
		sb.append(getVersion());
		sb.append(", size=");
		sb.append(getSize());
		sb.append(", checksum=");
		sb.append(getChecksum());
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
		StringBundler sb = new StringBundler(82);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.documentlibrary.model.DLFileVersion");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>fileVersionId</column-name><column-value><![CDATA[");
		sb.append(getFileVersionId());
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
			"<column><column-name>folderId</column-name><column-value><![CDATA[");
		sb.append(getFolderId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>treePath</column-name><column-value><![CDATA[");
		sb.append(getTreePath());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>fileEntryId</column-name><column-value><![CDATA[");
		sb.append(getFileEntryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>extension</column-name><column-value><![CDATA[");
		sb.append(getExtension());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>mimeType</column-name><column-value><![CDATA[");
		sb.append(getMimeType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>title</column-name><column-value><![CDATA[");
		sb.append(getTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>changeLog</column-name><column-value><![CDATA[");
		sb.append(getChangeLog());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>extraSettings</column-name><column-value><![CDATA[");
		sb.append(getExtraSettings());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>fileEntryTypeId</column-name><column-value><![CDATA[");
		sb.append(getFileEntryTypeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>version</column-name><column-value><![CDATA[");
		sb.append(getVersion());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>size</column-name><column-value><![CDATA[");
		sb.append(getSize());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>checksum</column-name><column-value><![CDATA[");
		sb.append(getChecksum());
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

	private static ClassLoader _classLoader = DLFileVersion.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			DLFileVersion.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _fileVersionId;
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
	private long _repositoryId;
	private long _folderId;
	private long _originalFolderId;
	private boolean _setOriginalFolderId;
	private String _treePath;
	private long _fileEntryId;
	private long _originalFileEntryId;
	private boolean _setOriginalFileEntryId;
	private String _extension;
	private String _mimeType;
	private String _originalMimeType;
	private String _title;
	private String _originalTitle;
	private String _description;
	private String _changeLog;
	private String _extraSettings;
	private long _fileEntryTypeId;
	private String _version;
	private String _originalVersion;
	private long _size;
	private String _checksum;
	private int _status;
	private int _originalStatus;
	private boolean _setOriginalStatus;
	private long _statusByUserId;
	private String _statusByUserUuid;
	private String _statusByUserName;
	private Date _statusDate;
	private long _columnBitmask;
	private DLFileVersion _escapedModel;
}