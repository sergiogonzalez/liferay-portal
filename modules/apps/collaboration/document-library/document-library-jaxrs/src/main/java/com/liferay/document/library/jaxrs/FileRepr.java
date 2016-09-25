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

package com.liferay.document.library.jaxrs;

import com.liferay.portal.kernel.repository.model.FileEntry;

import javax.ws.rs.core.UriBuilder;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Carlos Sierra Andrés
 */
@XmlRootElement
public class FileRepr {

	public static FileRepr fromFileEntry(
		FileEntry fileEntry, UriBuilder uriBuilder) {

		return new FileRepr(
			fileEntry.getFileEntryId(), fileEntry.getDescription(),
			fileEntry.getFileName(), fileEntry.getTitle(), fileEntry.getSize(),
			uriBuilder.build(fileEntry.getUuid()).toString());
	}

	public FileRepr() {
	}

	public FileRepr(
		long id, String description, String fileName, String title, long size,
		String url) {

		_id = id;
		_description = description;
		_fileName = fileName;
		_title = title;
		_size = size;
		_url = url;
	}

	public String getDescription() {
		return _description;
	}

	public String getFileName() {
		return _fileName;
	}

	public long getId() {
		return _id;
	}

	public long getSize() {
		return _size;
	}

	public String getTitle() {
		return _title;
	}

	public String getUrl() {
		return _url;
	}

	public long getUuid() {
		return _id;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setFileName(String fileName) {
		_fileName = fileName;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setSize(long size) {
		_size = size;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setUuid(long uuid) {
		_id = uuid;
	}

	private String _description;
	private String _fileName;
	private long _id;
	private long _size;
	private String _title;
	private String _url;

}