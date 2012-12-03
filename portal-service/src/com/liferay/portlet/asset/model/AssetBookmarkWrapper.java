/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AssetBookmark}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetBookmark
 * @generated
 */
public class AssetBookmarkWrapper implements AssetBookmark,
	ModelWrapper<AssetBookmark> {
	public AssetBookmarkWrapper(AssetBookmark assetBookmark) {
		_assetBookmark = assetBookmark;
	}

	public Class<?> getModelClass() {
		return AssetBookmark.class;
	}

	public String getModelClassName() {
		return AssetBookmark.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("bookmarkId", getBookmarkId());
		attributes.put("userId", getUserId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long bookmarkId = (Long)attributes.get("bookmarkId");

		if (bookmarkId != null) {
			setBookmarkId(bookmarkId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}
	}

	/**
	* Returns the primary key of this asset bookmark.
	*
	* @return the primary key of this asset bookmark
	*/
	public long getPrimaryKey() {
		return _assetBookmark.getPrimaryKey();
	}

	/**
	* Sets the primary key of this asset bookmark.
	*
	* @param primaryKey the primary key of this asset bookmark
	*/
	public void setPrimaryKey(long primaryKey) {
		_assetBookmark.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this asset bookmark.
	*
	* @return the uuid of this asset bookmark
	*/
	public java.lang.String getUuid() {
		return _assetBookmark.getUuid();
	}

	/**
	* Sets the uuid of this asset bookmark.
	*
	* @param uuid the uuid of this asset bookmark
	*/
	public void setUuid(java.lang.String uuid) {
		_assetBookmark.setUuid(uuid);
	}

	/**
	* Returns the bookmark ID of this asset bookmark.
	*
	* @return the bookmark ID of this asset bookmark
	*/
	public long getBookmarkId() {
		return _assetBookmark.getBookmarkId();
	}

	/**
	* Sets the bookmark ID of this asset bookmark.
	*
	* @param bookmarkId the bookmark ID of this asset bookmark
	*/
	public void setBookmarkId(long bookmarkId) {
		_assetBookmark.setBookmarkId(bookmarkId);
	}

	/**
	* Returns the user ID of this asset bookmark.
	*
	* @return the user ID of this asset bookmark
	*/
	public long getUserId() {
		return _assetBookmark.getUserId();
	}

	/**
	* Sets the user ID of this asset bookmark.
	*
	* @param userId the user ID of this asset bookmark
	*/
	public void setUserId(long userId) {
		_assetBookmark.setUserId(userId);
	}

	/**
	* Returns the user uuid of this asset bookmark.
	*
	* @return the user uuid of this asset bookmark
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetBookmark.getUserUuid();
	}

	/**
	* Sets the user uuid of this asset bookmark.
	*
	* @param userUuid the user uuid of this asset bookmark
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_assetBookmark.setUserUuid(userUuid);
	}

	/**
	* Returns the fully qualified class name of this asset bookmark.
	*
	* @return the fully qualified class name of this asset bookmark
	*/
	public java.lang.String getClassName() {
		return _assetBookmark.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_assetBookmark.setClassName(className);
	}

	/**
	* Returns the class name ID of this asset bookmark.
	*
	* @return the class name ID of this asset bookmark
	*/
	public long getClassNameId() {
		return _assetBookmark.getClassNameId();
	}

	/**
	* Sets the class name ID of this asset bookmark.
	*
	* @param classNameId the class name ID of this asset bookmark
	*/
	public void setClassNameId(long classNameId) {
		_assetBookmark.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this asset bookmark.
	*
	* @return the class p k of this asset bookmark
	*/
	public long getClassPK() {
		return _assetBookmark.getClassPK();
	}

	/**
	* Sets the class p k of this asset bookmark.
	*
	* @param classPK the class p k of this asset bookmark
	*/
	public void setClassPK(long classPK) {
		_assetBookmark.setClassPK(classPK);
	}

	public boolean isNew() {
		return _assetBookmark.isNew();
	}

	public void setNew(boolean n) {
		_assetBookmark.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetBookmark.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetBookmark.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetBookmark.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetBookmark.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_assetBookmark.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetBookmark.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetBookmark.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new AssetBookmarkWrapper((AssetBookmark)_assetBookmark.clone());
	}

	public int compareTo(
		com.liferay.portlet.asset.model.AssetBookmark assetBookmark) {
		return _assetBookmark.compareTo(assetBookmark);
	}

	@Override
	public int hashCode() {
		return _assetBookmark.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.asset.model.AssetBookmark> toCacheModel() {
		return _assetBookmark.toCacheModel();
	}

	public com.liferay.portlet.asset.model.AssetBookmark toEscapedModel() {
		return new AssetBookmarkWrapper(_assetBookmark.toEscapedModel());
	}

	public com.liferay.portlet.asset.model.AssetBookmark toUnescapedModel() {
		return new AssetBookmarkWrapper(_assetBookmark.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _assetBookmark.toString();
	}

	public java.lang.String toXmlString() {
		return _assetBookmark.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetBookmark.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public AssetBookmark getWrappedAssetBookmark() {
		return _assetBookmark;
	}

	public AssetBookmark getWrappedModel() {
		return _assetBookmark;
	}

	public void resetOriginalValues() {
		_assetBookmark.resetOriginalValues();
	}

	private AssetBookmark _assetBookmark;
}