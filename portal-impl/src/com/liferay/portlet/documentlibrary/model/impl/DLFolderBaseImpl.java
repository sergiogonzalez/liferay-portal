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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * The extended model base implementation for the DLFolder service. Represents a row in the &quot;DLFolder&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DLFolderImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFolderImpl
 * @see com.liferay.portlet.documentlibrary.model.DLFolder
 * @generated
 */
public abstract class DLFolderBaseImpl extends DLFolderModelImpl
	implements DLFolder {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a document library folder model instance should use the {@link DLFolder} interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			DLFolderLocalServiceUtil.addDLFolder(this);
		}
		else {
			DLFolderLocalServiceUtil.updateDLFolder(this);
		}
	}

	@Override
	@SuppressWarnings("unused")
	public String buildTreePath() throws PortalException {
		List<DLFolder> dlFolders = new ArrayList<DLFolder>();

		DLFolder dlFolder = this;

		while (dlFolder != null) {
			dlFolders.add(dlFolder);

			dlFolder = DLFolderLocalServiceUtil.fetchDLFolder(dlFolder.getParentFolderId());
		}

		StringBundler sb = new StringBundler((dlFolders.size() * 2) + 1);

		sb.append(StringPool.SLASH);

		for (int i = dlFolders.size() - 1; i >= 0; i--) {
			dlFolder = dlFolders.get(i);

			sb.append(dlFolder.getFolderId());
			sb.append(StringPool.SLASH);
		}

		return sb.toString();
	}

	@Override
	public void updateTreePath(String treePath) {
		DLFolder dlFolder = this;

		dlFolder.setTreePath(treePath);

		DLFolderLocalServiceUtil.updateDLFolder(dlFolder);
	}
}