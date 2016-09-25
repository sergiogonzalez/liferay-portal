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

import com.liferay.document.library.kernel.util.comparator.RepositoryModelCreateDateComparator;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelModifiedDateComparator;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelSizeComparator;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelTitleComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sergiogonzalez on 26/07/16.
 */
@XmlRootElement
public class RepositoryContentObject {

	public static final Map<String, Function<Boolean, OrderByComparator<Object>>>
		comparators =
			new HashMap<String, Function<Boolean, OrderByComparator<Object>>>() { {
				put("title", RepositoryModelTitleComparator::new);
				put("createDate", RepositoryModelCreateDateComparator::new);
				put("modifiedDate", RepositoryModelModifiedDateComparator::new);
				put("size", RepositoryModelSizeComparator::new);
			}};

	public RepositoryContentObject() {
	}

	public RepositoryContentObject(
		long id, String title, String url, RepositoryContentType type,
		Date createDate, Date modifiedDate) {

		_id = id;
		_title = title;
		_url = url;
		_type = type;
		_createDate = createDate;
		_modifiedDate = modifiedDate;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public long getId() {
		return _id;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public String getTitle() {
		return _title;
	}

	public RepositoryContentType getType() {
		return _type;
	}

	public String getUrl() {
		return _url;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setType(RepositoryContentType type) {
		_type = type;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public enum RepositoryContentType {

		FILE, FOLDER, SHORTCUT;
	}

	private Date _createDate;
	private long _id;
	private Date _modifiedDate;
	private String _title;
	private RepositoryContentType _type;
	private String _url;

}