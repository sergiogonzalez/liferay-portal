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

package com.liferay.portal.kernel.servlet.taglib.ui;

/**
 * @author Iván Zaera
 */
public class DeleteMenuItem extends MenuItem {

	@Override
	public String getId() {
		return _id;
	}

	public String getURL() {
		return _url;
	}

	public boolean isTrash() {
		return _trash;
	}

	@Override
	public void setId(String id) {
		_id = id;
	}

	public void setTrash(boolean trash) {
		_trash = trash;
	}

	public void setURL(String url) {
		_url = url;
	}

	private String _id;
	private boolean _trash;
	private String _url;

}