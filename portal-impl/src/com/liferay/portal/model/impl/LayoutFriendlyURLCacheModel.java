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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.LayoutFriendlyURL;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing LayoutFriendlyURL in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutFriendlyURL
 * @generated
 */
public class LayoutFriendlyURLCacheModel implements CacheModel<LayoutFriendlyURL>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", layoutFriendlyURLId=");
		sb.append(layoutFriendlyURLId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", privateLayout=");
		sb.append(privateLayout);
		sb.append(", friendlyURL=");
		sb.append(friendlyURL);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append("}");

		return sb.toString();
	}

	public LayoutFriendlyURL toEntityModel() {
		LayoutFriendlyURLImpl layoutFriendlyURLImpl = new LayoutFriendlyURLImpl();

		if (uuid == null) {
			layoutFriendlyURLImpl.setUuid(StringPool.BLANK);
		}
		else {
			layoutFriendlyURLImpl.setUuid(uuid);
		}

		layoutFriendlyURLImpl.setLayoutFriendlyURLId(layoutFriendlyURLId);
		layoutFriendlyURLImpl.setGroupId(groupId);
		layoutFriendlyURLImpl.setCompanyId(companyId);
		layoutFriendlyURLImpl.setPlid(plid);
		layoutFriendlyURLImpl.setPrivateLayout(privateLayout);

		if (friendlyURL == null) {
			layoutFriendlyURLImpl.setFriendlyURL(StringPool.BLANK);
		}
		else {
			layoutFriendlyURLImpl.setFriendlyURL(friendlyURL);
		}

		if (languageId == null) {
			layoutFriendlyURLImpl.setLanguageId(StringPool.BLANK);
		}
		else {
			layoutFriendlyURLImpl.setLanguageId(languageId);
		}

		layoutFriendlyURLImpl.resetOriginalValues();

		return layoutFriendlyURLImpl;
	}

	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		layoutFriendlyURLId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		plid = objectInput.readLong();
		privateLayout = objectInput.readBoolean();
		friendlyURL = objectInput.readUTF();
		languageId = objectInput.readUTF();
	}

	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(layoutFriendlyURLId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(plid);
		objectOutput.writeBoolean(privateLayout);

		if (friendlyURL == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(friendlyURL);
		}

		if (languageId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(languageId);
		}
	}

	public String uuid;
	public long layoutFriendlyURLId;
	public long groupId;
	public long companyId;
	public long plid;
	public boolean privateLayout;
	public String friendlyURL;
	public String languageId;
}