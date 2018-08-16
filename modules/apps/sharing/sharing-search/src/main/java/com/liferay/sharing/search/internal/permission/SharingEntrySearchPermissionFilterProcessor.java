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

package com.liferay.sharing.search.internal.permission;

import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.search.spi.model.permission.SearchPermissionFilterProcessor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = SearchPermissionFilterProcessor.class)
public class SharingEntrySearchPermissionFilterProcessor
	implements SearchPermissionFilterProcessor {

	@Override
	public void process(
		BooleanFilter permissionFilter, long companyId, long[] searchGroupIds,
		long userId, PermissionChecker permissionChecker, String className) {

		TermsFilter sharedToUserIdTermsFilter = new TermsFilter(
			"sharedToUserId");

		sharedToUserIdTermsFilter.addValue(String.valueOf(userId));

		permissionFilter.add(sharedToUserIdTermsFilter);
	}

}