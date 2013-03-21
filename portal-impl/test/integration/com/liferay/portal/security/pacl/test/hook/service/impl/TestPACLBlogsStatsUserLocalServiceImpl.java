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

package com.liferay.portal.security.pacl.test.hook.service.impl;

import com.liferay.portlet.blogs.service.BlogsStatsUserLocalService;
import com.liferay.portlet.blogs.service.BlogsStatsUserLocalServiceWrapper;

/**
 * @author Brian Wing Shun Chan
 */
public class TestPACLBlogsStatsUserLocalServiceImpl
	extends BlogsStatsUserLocalServiceWrapper {

	public TestPACLBlogsStatsUserLocalServiceImpl(
		BlogsStatsUserLocalService blogsStatsUserLocalService) {

		super(blogsStatsUserLocalService);
	}

	@Override
	public int getBlogsStatsUsersCount() {
		return -456;
	}

}