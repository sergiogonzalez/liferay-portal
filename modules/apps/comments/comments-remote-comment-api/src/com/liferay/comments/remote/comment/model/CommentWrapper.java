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

package com.liferay.comments.remote.comment.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Comment}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Comment
 * @generated
 */
@ProviderType
public class CommentWrapper implements Comment, ModelWrapper<Comment> {
	public CommentWrapper(Comment comment) {
		_comment = comment;
	}

	@Override
	public Class<?> getModelClass() {
		return Comment.class;
	}

	@Override
	public String getModelClassName() {
		return Comment.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commentId", getCommentId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("body", getBody());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commentId = (Long)attributes.get("commentId");

		if (commentId != null) {
			setCommentId(commentId);
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

		String body = (String)attributes.get("body");

		if (body != null) {
			setBody(body);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommentWrapper((Comment)_comment.clone());
	}

	@Override
	public int compareTo(
		com.liferay.comments.remote.comment.model.Comment comment) {
		return _comment.compareTo(comment);
	}

	/**
	* Returns the body of this comment.
	*
	* @return the body of this comment
	*/
	@Override
	public java.lang.String getBody() {
		return _comment.getBody();
	}

	/**
	* Returns the comment ID of this comment.
	*
	* @return the comment ID of this comment
	*/
	@Override
	public long getCommentId() {
		return _comment.getCommentId();
	}

	/**
	* Returns the create date of this comment.
	*
	* @return the create date of this comment
	*/
	@Override
	public Date getCreateDate() {
		return _comment.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _comment.getExpandoBridge();
	}

	/**
	* Returns the modified date of this comment.
	*
	* @return the modified date of this comment
	*/
	@Override
	public Date getModifiedDate() {
		return _comment.getModifiedDate();
	}

	/**
	* Returns the primary key of this comment.
	*
	* @return the primary key of this comment
	*/
	@Override
	public long getPrimaryKey() {
		return _comment.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _comment.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this comment.
	*
	* @return the user ID of this comment
	*/
	@Override
	public long getUserId() {
		return _comment.getUserId();
	}

	/**
	* Returns the user name of this comment.
	*
	* @return the user name of this comment
	*/
	@Override
	public java.lang.String getUserName() {
		return _comment.getUserName();
	}

	/**
	* Returns the user uuid of this comment.
	*
	* @return the user uuid of this comment
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _comment.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _comment.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _comment.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _comment.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _comment.isNew();
	}

	/**
	* Sets the body of this comment.
	*
	* @param body the body of this comment
	*/
	@Override
	public void setBody(java.lang.String body) {
		_comment.setBody(body);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_comment.setCachedModel(cachedModel);
	}

	/**
	* Sets the comment ID of this comment.
	*
	* @param commentId the comment ID of this comment
	*/
	@Override
	public void setCommentId(long commentId) {
		_comment.setCommentId(commentId);
	}

	/**
	* Sets the create date of this comment.
	*
	* @param createDate the create date of this comment
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_comment.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_comment.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_comment.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_comment.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this comment.
	*
	* @param modifiedDate the modified date of this comment
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_comment.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_comment.setNew(n);
	}

	/**
	* Sets the primary key of this comment.
	*
	* @param primaryKey the primary key of this comment
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_comment.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_comment.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this comment.
	*
	* @param userId the user ID of this comment
	*/
	@Override
	public void setUserId(long userId) {
		_comment.setUserId(userId);
	}

	/**
	* Sets the user name of this comment.
	*
	* @param userName the user name of this comment
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_comment.setUserName(userName);
	}

	/**
	* Sets the user uuid of this comment.
	*
	* @param userUuid the user uuid of this comment
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_comment.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.comments.remote.comment.model.Comment> toCacheModel() {
		return _comment.toCacheModel();
	}

	@Override
	public com.liferay.comments.remote.comment.model.Comment toEscapedModel() {
		return new CommentWrapper(_comment.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _comment.toString();
	}

	@Override
	public com.liferay.comments.remote.comment.model.Comment toUnescapedModel() {
		return new CommentWrapper(_comment.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _comment.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommentWrapper)) {
			return false;
		}

		CommentWrapper commentWrapper = (CommentWrapper)obj;

		if (Validator.equals(_comment, commentWrapper._comment)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public Comment getWrappedComment() {
		return _comment;
	}

	@Override
	public Comment getWrappedModel() {
		return _comment;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _comment.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _comment.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_comment.resetOriginalValues();
	}

	private final Comment _comment;
}