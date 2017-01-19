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

package com.liferay.document.library.demo.data.creator.internal;

import com.liferay.document.library.demo.data.creator.FolderDemoDataCreator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.Folder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 */
@Component(service = FolderDemoDataCreator.class)
public class FolderDemoDataCreatorImpl
	extends BaseFolderDemoDataCreatorImpl implements FolderDemoDataCreator {

	@Override
	public Folder create(long userId, long groupId, long folderId)
		throws PortalException {

		return create(userId, groupId, folderId, "Demo");
	}

	@Override
	public Folder create(long userId, long groupId, long folderId, String name)
		throws PortalException {

		return createBaseFolder(userId, groupId, folderId, name);
	}

}