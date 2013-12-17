/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class CategoryMovingException extends PortalException {

	public void addCategoryName(String categoyName) {
		_categoryName = categoyName;
	}

	public void addParentCategoryName(String parentCategoryName) {
		_parentCategoryName = parentCategoryName;
	}

	public String getCategoryName() {
		return _categoryName;
	}

	public String getParentCategoryName() {
		return _parentCategoryName;
	}

	private String _categoryName;
	private String _parentCategoryName;

}