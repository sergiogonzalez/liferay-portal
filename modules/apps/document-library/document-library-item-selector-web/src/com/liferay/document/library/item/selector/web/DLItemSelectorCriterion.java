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

package com.liferay.document.library.item.selector.web;

import com.liferay.item.selector.BaseItemSelectorCriterion;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Base64;

import java.net.URL;

import java.util.List;

/**
 * @author Roberto Díaz
 */
public class DLItemSelectorCriterion extends BaseItemSelectorCriterion {

	public DLItemSelectorCriterion() {
		super(_AVAILABLE_RETURN_TYPES);
	}

	public DLItemSelectorCriterion(
		long folderId, long repositoryId, String type, String[] mimeTypes) {

		super(_AVAILABLE_RETURN_TYPES);

		_folderId = folderId;
		_repositoryId = repositoryId;
		_type = type;
		_mimeTypes = mimeTypes;
	}

	public long getFolderId() {
		return _folderId;
	}

	public String[] getMimeTypes() {
		return _mimeTypes;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public String getType() {
		return _type;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
	}

	public void setMimeTypes(String[] mimeTypes) {
		_mimeTypes = mimeTypes;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public void setType(String type) {
		_type = type;
	}

	private static final List<Class<?>> _AVAILABLE_RETURN_TYPES =
		getInmutableList(Base64.class, FileEntry.class, URL.class);

	private long _folderId;
	private String[] _mimeTypes;
	private long _repositoryId;
	private String _type;

}