/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.document.library.jaxrs;

import com.liferay.portal.kernel.repository.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author Carlos Sierra Andrés
 */
@XmlRootElement
public class RepositoryRepr {

	private Date _createDate;
	private String _description;
	private String _name;

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	private String _uuid;
	private long _repositoryId;

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

	public void setUrl(String url) {
		_url = url;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	private String _url;

	public long getRepositoryId() {
		return _repositoryId;
	}

	public String getUrl() {
		return _url;
	}

	public static RepositoryRepr fromRepository(
		Repository repository, HttpServletRequest request) {

		return new RepositoryRepr(
			repository.getRepositoryId(),
			new Date(),
			"repository",
			"repository",
			"uuid",
			request.getRequestURI() + "/" + repository.getRepositoryId()
			);
	}

	public RepositoryRepr() {
	}
}
