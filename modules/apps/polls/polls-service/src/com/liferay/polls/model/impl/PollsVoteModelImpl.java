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

package com.liferay.polls.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.polls.model.PollsVote;
import com.liferay.polls.model.PollsVoteModel;
import com.liferay.polls.model.PollsVoteSoap;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the PollsVote service. Represents a row in the &quot;PollsVote&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.polls.model.PollsVoteModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link PollsVoteImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PollsVoteImpl
 * @see com.liferay.polls.model.PollsVote
 * @see com.liferay.polls.model.PollsVoteModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class PollsVoteModelImpl extends BaseModelImpl<PollsVote>
	implements PollsVoteModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a polls vote model instance should use the {@link com.liferay.polls.model.PollsVote} interface instead.
	 */
	public static final String TABLE_NAME = "PollsVote";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "voteId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "questionId", Types.BIGINT },
			{ "choiceId", Types.BIGINT },
			{ "lastPublishDate", Types.TIMESTAMP },
			{ "voteDate", Types.TIMESTAMP }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("voteId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("questionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("choiceId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("voteDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE = "create table PollsVote (uuid_ VARCHAR(75) null,voteId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,questionId LONG,choiceId LONG,lastPublishDate DATE null,voteDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table PollsVote";
	public static final String ORDER_BY_JPQL = " ORDER BY pollsVote.voteId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY PollsVote.voteId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.polls.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.PollsVote"), true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.polls.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.PollsVote"), true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.polls.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.PollsVote"), true);
	public static final long CHOICEID_COLUMN_BITMASK = 1L;
	public static final long COMPANYID_COLUMN_BITMASK = 2L;
	public static final long GROUPID_COLUMN_BITMASK = 4L;
	public static final long QUESTIONID_COLUMN_BITMASK = 8L;
	public static final long USERID_COLUMN_BITMASK = 16L;
	public static final long UUID_COLUMN_BITMASK = 32L;
	public static final long VOTEID_COLUMN_BITMASK = 64L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static PollsVote toModel(PollsVoteSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		PollsVote model = new PollsVoteImpl();

		model.setUuid(soapModel.getUuid());
		model.setVoteId(soapModel.getVoteId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setQuestionId(soapModel.getQuestionId());
		model.setChoiceId(soapModel.getChoiceId());
		model.setLastPublishDate(soapModel.getLastPublishDate());
		model.setVoteDate(soapModel.getVoteDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<PollsVote> toModels(PollsVoteSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<PollsVote> models = new ArrayList<PollsVote>(soapModels.length);

		for (PollsVoteSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.polls.service.util.ServiceProps.get(
				"lock.expiration.time.PollsVote"));

	public PollsVoteModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _voteId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setVoteId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _voteId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return PollsVote.class;
	}

	@Override
	public String getModelClassName() {
		return PollsVote.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("voteId", getVoteId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("questionId", getQuestionId());
		attributes.put("choiceId", getChoiceId());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put("voteDate", getVoteDate());

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

		Long voteId = (Long)attributes.get("voteId");

		if (voteId != null) {
			setVoteId(voteId);
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

		Long questionId = (Long)attributes.get("questionId");

		if (questionId != null) {
			setQuestionId(questionId);
		}

		Long choiceId = (Long)attributes.get("choiceId");

		if (choiceId != null) {
			setChoiceId(choiceId);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}

		Date voteDate = (Date)attributes.get("voteDate");

		if (voteDate != null) {
			setVoteDate(voteDate);
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
	public long getVoteId() {
		return _voteId;
	}

	@Override
	public void setVoteId(long voteId) {
		_voteId = voteId;
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
	public long getQuestionId() {
		return _questionId;
	}

	@Override
	public void setQuestionId(long questionId) {
		_columnBitmask |= QUESTIONID_COLUMN_BITMASK;

		if (!_setOriginalQuestionId) {
			_setOriginalQuestionId = true;

			_originalQuestionId = _questionId;
		}

		_questionId = questionId;
	}

	public long getOriginalQuestionId() {
		return _originalQuestionId;
	}

	@JSON
	@Override
	public long getChoiceId() {
		return _choiceId;
	}

	@Override
	public void setChoiceId(long choiceId) {
		_columnBitmask |= CHOICEID_COLUMN_BITMASK;

		if (!_setOriginalChoiceId) {
			_setOriginalChoiceId = true;

			_originalChoiceId = _choiceId;
		}

		_choiceId = choiceId;
	}

	public long getOriginalChoiceId() {
		return _originalChoiceId;
	}

	@JSON
	@Override
	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	@JSON
	@Override
	public Date getVoteDate() {
		return _voteDate;
	}

	@Override
	public void setVoteDate(Date voteDate) {
		_voteDate = voteDate;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				PollsVote.class.getName()));
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			PollsVote.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public PollsVote toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (PollsVote)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		PollsVoteImpl pollsVoteImpl = new PollsVoteImpl();

		pollsVoteImpl.setUuid(getUuid());
		pollsVoteImpl.setVoteId(getVoteId());
		pollsVoteImpl.setGroupId(getGroupId());
		pollsVoteImpl.setCompanyId(getCompanyId());
		pollsVoteImpl.setUserId(getUserId());
		pollsVoteImpl.setUserName(getUserName());
		pollsVoteImpl.setCreateDate(getCreateDate());
		pollsVoteImpl.setModifiedDate(getModifiedDate());
		pollsVoteImpl.setQuestionId(getQuestionId());
		pollsVoteImpl.setChoiceId(getChoiceId());
		pollsVoteImpl.setLastPublishDate(getLastPublishDate());
		pollsVoteImpl.setVoteDate(getVoteDate());

		pollsVoteImpl.resetOriginalValues();

		return pollsVoteImpl;
	}

	@Override
	public int compareTo(PollsVote pollsVote) {
		long primaryKey = pollsVote.getPrimaryKey();

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

		if (!(obj instanceof PollsVote)) {
			return false;
		}

		PollsVote pollsVote = (PollsVote)obj;

		long primaryKey = pollsVote.getPrimaryKey();

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
		PollsVoteModelImpl pollsVoteModelImpl = this;

		pollsVoteModelImpl._originalUuid = pollsVoteModelImpl._uuid;

		pollsVoteModelImpl._originalGroupId = pollsVoteModelImpl._groupId;

		pollsVoteModelImpl._setOriginalGroupId = false;

		pollsVoteModelImpl._originalCompanyId = pollsVoteModelImpl._companyId;

		pollsVoteModelImpl._setOriginalCompanyId = false;

		pollsVoteModelImpl._originalUserId = pollsVoteModelImpl._userId;

		pollsVoteModelImpl._setOriginalUserId = false;

		pollsVoteModelImpl._setModifiedDate = false;

		pollsVoteModelImpl._originalQuestionId = pollsVoteModelImpl._questionId;

		pollsVoteModelImpl._setOriginalQuestionId = false;

		pollsVoteModelImpl._originalChoiceId = pollsVoteModelImpl._choiceId;

		pollsVoteModelImpl._setOriginalChoiceId = false;

		pollsVoteModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<PollsVote> toCacheModel() {
		PollsVoteCacheModel pollsVoteCacheModel = new PollsVoteCacheModel();

		pollsVoteCacheModel.uuid = getUuid();

		String uuid = pollsVoteCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			pollsVoteCacheModel.uuid = null;
		}

		pollsVoteCacheModel.voteId = getVoteId();

		pollsVoteCacheModel.groupId = getGroupId();

		pollsVoteCacheModel.companyId = getCompanyId();

		pollsVoteCacheModel.userId = getUserId();

		pollsVoteCacheModel.userName = getUserName();

		String userName = pollsVoteCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			pollsVoteCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			pollsVoteCacheModel.createDate = createDate.getTime();
		}
		else {
			pollsVoteCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			pollsVoteCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			pollsVoteCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		pollsVoteCacheModel.questionId = getQuestionId();

		pollsVoteCacheModel.choiceId = getChoiceId();

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			pollsVoteCacheModel.lastPublishDate = lastPublishDate.getTime();
		}
		else {
			pollsVoteCacheModel.lastPublishDate = Long.MIN_VALUE;
		}

		Date voteDate = getVoteDate();

		if (voteDate != null) {
			pollsVoteCacheModel.voteDate = voteDate.getTime();
		}
		else {
			pollsVoteCacheModel.voteDate = Long.MIN_VALUE;
		}

		return pollsVoteCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", voteId=");
		sb.append(getVoteId());
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
		sb.append(", questionId=");
		sb.append(getQuestionId());
		sb.append(", choiceId=");
		sb.append(getChoiceId());
		sb.append(", lastPublishDate=");
		sb.append(getLastPublishDate());
		sb.append(", voteDate=");
		sb.append(getVoteDate());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(40);

		sb.append("<model><model-name>");
		sb.append("com.liferay.polls.model.PollsVote");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>voteId</column-name><column-value><![CDATA[");
		sb.append(getVoteId());
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
			"<column><column-name>questionId</column-name><column-value><![CDATA[");
		sb.append(getQuestionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>choiceId</column-name><column-value><![CDATA[");
		sb.append(getChoiceId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastPublishDate</column-name><column-value><![CDATA[");
		sb.append(getLastPublishDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>voteDate</column-name><column-value><![CDATA[");
		sb.append(getVoteDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = PollsVote.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			PollsVote.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _voteId;
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
	private boolean _setModifiedDate;
	private long _questionId;
	private long _originalQuestionId;
	private boolean _setOriginalQuestionId;
	private long _choiceId;
	private long _originalChoiceId;
	private boolean _setOriginalChoiceId;
	private Date _lastPublishDate;
	private Date _voteDate;
	private long _columnBitmask;
	private PollsVote _escapedModel;
}