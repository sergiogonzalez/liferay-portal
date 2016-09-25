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

import java.util.Date;

import javax.ws.rs.core.UriBuilder;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Carlos Sierra Andrés
 */
@XmlRootElement
public class RepositoryRepr {

	public static RepositoryRepr fromRepository(
		Repository repository, UriBuilder uriBuilder) {

		return new RepositoryRepr(
			repository.getRepositoryId(), new Date(), "repository",
			"repository", "uuid",
			uriBuilder.build(repository.getRepositoryId()).toString());
	}

	public RepositoryRepr() {
	}

	public RepositoryRepr(
		long repositoryId, Date createDate, String description, String name,
		String uuid, String url) {

		_repositoryId = repositoryId;
		_createDate = createDate;
		_description = description;
		_name = name;
		_uuid = uuid;
		_url = url;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getDescription() {
		return _description;
	}

	public String getName() {
		return _name;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public String getUrl() {
		return _url;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	private Date _createDate;
	private String _description;
	private String _name;
	private long _repositoryId;
	private String _url;
	private String _uuid;

}