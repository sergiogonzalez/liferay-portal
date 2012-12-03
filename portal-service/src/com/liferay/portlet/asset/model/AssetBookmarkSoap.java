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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.asset.service.http.AssetBookmarkServiceSoap}.
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.asset.service.http.AssetBookmarkServiceSoap
 * @generated
 */
public class AssetBookmarkSoap implements Serializable {
	public static AssetBookmarkSoap toSoapModel(AssetBookmark model) {
		AssetBookmarkSoap soapModel = new AssetBookmarkSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setBookmarkId(model.getBookmarkId());
		soapModel.setUserId(model.getUserId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());

		return soapModel;
	}

	public static AssetBookmarkSoap[] toSoapModels(AssetBookmark[] models) {
		AssetBookmarkSoap[] soapModels = new AssetBookmarkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetBookmarkSoap[][] toSoapModels(AssetBookmark[][] models) {
		AssetBookmarkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AssetBookmarkSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AssetBookmarkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetBookmarkSoap[] toSoapModels(List<AssetBookmark> models) {
		List<AssetBookmarkSoap> soapModels = new ArrayList<AssetBookmarkSoap>(models.size());

		for (AssetBookmark model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AssetBookmarkSoap[soapModels.size()]);
	}

	public AssetBookmarkSoap() {
	}

	public long getPrimaryKey() {
		return _bookmarkId;
	}

	public void setPrimaryKey(long pk) {
		setBookmarkId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getBookmarkId() {
		return _bookmarkId;
	}

	public void setBookmarkId(long bookmarkId) {
		_bookmarkId = bookmarkId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	private String _uuid;
	private long _bookmarkId;
	private long _userId;
	private long _classNameId;
	private long _classPK;
}