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

package com.liferay.item.selector.criteria.upload.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Ambrín Chaudhary
 * @author Roberto Díaz
 */
public class UploadItemSelectorCriterion extends BaseItemSelectorCriterion {

	public UploadItemSelectorCriterion() {
	}

	public UploadItemSelectorCriterion(String url, String repositoryName) {
		this(
			url, repositoryName,
			PropsValues.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE,
			StringUtil.merge(PropsValues.DL_FILE_EXTENSIONS));
	}

	public UploadItemSelectorCriterion(
		String url, String repositoryName, long maxFileSize,
		String validExtensions) {

		_url = url;
		_repositoryName = repositoryName;
		_maxFileSize = maxFileSize;
		_validExtensions = validExtensions;
	}

	public long getMaxFileSize() {
		return _maxFileSize;
	}

	public String getRepositoryName() {
		return _repositoryName;
	}

	public String getURL() {
		return _url;
	}

	public String getValidExtensions() {
		return _validExtensions;
	}

	public void setMaxFileSize(long maxFileSize) {
		_maxFileSize = maxFileSize;
	}

	public void setRepositoryName(String repositoryName) {
		_repositoryName = repositoryName;
	}

	public void setURL(String url) {
		_url = url;
	}

	public void setValidExtensions(String validExtensions) {
		_validExtensions = validExtensions;
	}

	private long _maxFileSize;
	private String _repositoryName;
	private String _url;
	private String _validExtensions;

}