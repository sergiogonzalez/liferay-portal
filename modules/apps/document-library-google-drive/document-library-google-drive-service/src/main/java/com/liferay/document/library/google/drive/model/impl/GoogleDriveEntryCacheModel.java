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

package com.liferay.document.library.google.drive.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.google.drive.model.GoogleDriveEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing GoogleDriveEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see GoogleDriveEntry
 * @generated
 */
@ProviderType
public class GoogleDriveEntryCacheModel implements CacheModel<GoogleDriveEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof GoogleDriveEntryCacheModel)) {
			return false;
		}

		GoogleDriveEntryCacheModel googleDriveEntryCacheModel = (GoogleDriveEntryCacheModel)obj;

		if (entryId == googleDriveEntryCacheModel.entryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, entryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{entryId=");
		sb.append(entryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", googleDriveId=");
		sb.append(googleDriveId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public GoogleDriveEntry toEntityModel() {
		GoogleDriveEntryImpl googleDriveEntryImpl = new GoogleDriveEntryImpl();

		googleDriveEntryImpl.setEntryId(entryId);
		googleDriveEntryImpl.setGroupId(groupId);
		googleDriveEntryImpl.setCompanyId(companyId);
		googleDriveEntryImpl.setUserId(userId);

		if (userName == null) {
			googleDriveEntryImpl.setUserName("");
		}
		else {
			googleDriveEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			googleDriveEntryImpl.setCreateDate(null);
		}
		else {
			googleDriveEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			googleDriveEntryImpl.setModifiedDate(null);
		}
		else {
			googleDriveEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		googleDriveEntryImpl.setFileEntryId(fileEntryId);

		if (googleDriveId == null) {
			googleDriveEntryImpl.setGoogleDriveId("");
		}
		else {
			googleDriveEntryImpl.setGoogleDriveId(googleDriveId);
		}

		googleDriveEntryImpl.resetOriginalValues();

		return googleDriveEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		entryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		fileEntryId = objectInput.readLong();
		googleDriveId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(entryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(fileEntryId);

		if (googleDriveId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(googleDriveId);
		}
	}

	public long entryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long fileEntryId;
	public String googleDriveId;
}