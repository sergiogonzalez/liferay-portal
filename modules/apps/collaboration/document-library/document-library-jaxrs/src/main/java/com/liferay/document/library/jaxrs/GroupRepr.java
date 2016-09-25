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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Carlos Sierra Andrés
 */
@XmlRootElement
public class GroupRepr {

	public GroupRepr() {
	}

	public GroupRepr(
		long id, String url, String name, String friendlyUrl, int type) {

		_id = id;
		_url = url;
		_name = name;
		_friendlyUrl = friendlyUrl;
		_type = type;
	}

	public String getFriendlyUrl() {
		return _friendlyUrl;
	}

	public long getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public int getType() {
		return _type;
	}

	public String getUrl() {
		return _url;
	}

	public void setFriendlyUrl(String friendlyUrl) {
		_friendlyUrl = friendlyUrl;
	}

	public void setId(long id) {
		this._id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setType(int type) {
		_type = type;
	}

	public void setUrl(String url) {
		this._url = url;
	}

	private String _friendlyUrl;
	private long _id;
	private String _name;
	private int _type;
	private String _url;

}