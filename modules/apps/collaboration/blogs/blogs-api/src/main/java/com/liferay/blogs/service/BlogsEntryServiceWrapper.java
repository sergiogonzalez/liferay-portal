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

package com.liferay.blogs.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BlogsEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntryService
 * @generated
 */
@ProviderType
public class BlogsEntryServiceWrapper implements BlogsEntryService,
	ServiceWrapper<BlogsEntryService> {
	public BlogsEntryServiceWrapper(BlogsEntryService blogsEntryService) {
		_blogsEntryService = blogsEntryService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _blogsEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public BlogsEntryService getWrappedService() {
		return _blogsEntryService;
	}

	@Override
	public void setWrappedService(BlogsEntryService blogsEntryService) {
		_blogsEntryService = blogsEntryService;
	}

	private BlogsEntryService _blogsEntryService;
}