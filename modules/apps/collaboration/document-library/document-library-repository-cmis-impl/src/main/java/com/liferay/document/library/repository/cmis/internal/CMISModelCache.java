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

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class CMISModelCache {

	public FileEntry getFileEntry(long fileEntryId) {
		Map<Long, FileEntry> fileEntryMap = _fileEntryCache.get();

		return fileEntryMap.get(fileEntryId);
	}

	public Folder getFolder(long folderId) {
		Map<Long, Folder> folderMap = _folderCache.get();

		return folderMap.get(folderId);
	}

	public void putFileEntry(FileEntry fileEntry) {
		Map<Long, FileEntry> fileEntryMap = _fileEntryCache.get();

		fileEntryMap.put(fileEntry.getFileEntryId(), fileEntry);
	}

	public void putFolder(Folder folder) {
		Map<Long, Folder> folderMap = _folderCache.get();

		folderMap.put(folder.getFolderId(), folder);
	}

	private final ThreadLocal<Map<Long, FileEntry>> _fileEntryCache =
		new AutoResetThreadLocal<Map<Long, FileEntry>>(
			CMISRepository.class + "._fileEntryCache",
			new HashMap<Long, FileEntry>());
	private final ThreadLocal<Map<Long, Folder>> _folderCache =
		new AutoResetThreadLocal<Map<Long, Folder>>(
			CMISRepository.class + "._folderCache",
			new HashMap<Long, Folder>());

}