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

import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.Folder;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Carlos Sierra Andrés
 */
@XmlRootElement
public class FolderRepr {

	public static FolderRepr fromFolder(
		Folder folder, List<RepositoryContentObject> repositoryContentObjects,
		UriBuilder uriBuilder) {

		String url = uriBuilder.
			build(Long.toString(folder.getFolderId())).toString();

		return new FolderRepr(
			folder.getRepositoryId(), folder.getFolderId(),
			folder.getDescription(), folder.getCreateDate(), folder.getName(),
			repositoryContentObjects, url);
	}

	public static FolderRepr fromRepository(
		long groupId, Repository repository,
		List<RepositoryContentObject> repositoryContentObjects,
		UriBuilder uriBuilder) {

		String url = uriBuilder.
			build(
				Long.toString(groupId),
				Long.toString(repository.getRepositoryId())).toString();

		return new FolderRepr(
			repository.getRepositoryId(), 0, "", new Date(), "",
			repositoryContentObjects, url);
	}

	public FolderRepr() {
	}

	public FolderRepr(
		long repositoryId, long folderId, String description, Date createDate,
		String name, List<RepositoryContentObject> repositoryContentObjects,
		String url) {

		_repositoryId = repositoryId;
		_folderId = folderId;
		_description = description;
		_createDate = createDate;
		_name = name;
		_repositoryContentObjects = repositoryContentObjects;
		_url = url;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getDescription() {
		return _description;
	}

	public long getFolderId() {
		return _folderId;
	}

	public String getName() {
		return _name;
	}

	public List<RepositoryContentObject> getRepositoryContentObjects() {
		return _repositoryContentObjects;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public String getUrl() {
		return _url;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRepositoryContentObjects(
		List<RepositoryContentObject> repositoryContentObjects) {

		_repositoryContentObjects = repositoryContentObjects;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public void setUrl(String url) {
		_url = url;
	}

	private Date _createDate;
	private String _description;
	private long _folderId;
	private String _name;
	private List<RepositoryContentObject> _repositoryContentObjects;
	private long _repositoryId;
	private String _url;

}