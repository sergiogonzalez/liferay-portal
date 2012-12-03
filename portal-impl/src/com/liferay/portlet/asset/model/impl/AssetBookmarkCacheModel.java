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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.asset.model.AssetBookmark;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AssetBookmark in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetBookmark
 * @generated
 */
public class AssetBookmarkCacheModel implements CacheModel<AssetBookmark>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", bookmarkId=");
		sb.append(bookmarkId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append("}");

		return sb.toString();
	}

	public AssetBookmark toEntityModel() {
		AssetBookmarkImpl assetBookmarkImpl = new AssetBookmarkImpl();

		if (uuid == null) {
			assetBookmarkImpl.setUuid(StringPool.BLANK);
		}
		else {
			assetBookmarkImpl.setUuid(uuid);
		}

		assetBookmarkImpl.setBookmarkId(bookmarkId);
		assetBookmarkImpl.setUserId(userId);
		assetBookmarkImpl.setClassNameId(classNameId);
		assetBookmarkImpl.setClassPK(classPK);

		assetBookmarkImpl.resetOriginalValues();

		return assetBookmarkImpl;
	}

	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		bookmarkId = objectInput.readLong();
		userId = objectInput.readLong();
		classNameId = objectInput.readLong();
		classPK = objectInput.readLong();
	}

	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(bookmarkId);
		objectOutput.writeLong(userId);
		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(classPK);
	}

	public String uuid;
	public long bookmarkId;
	public long userId;
	public long classNameId;
	public long classPK;
}