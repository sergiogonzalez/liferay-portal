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

package com.liferay.blogs.demo;

import com.liferay.blogs.data.creator.BlogsLayoutDataCreator;
import com.liferay.blogs.data.creator.FullBlogsEntryDataCreator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.users.admin.data.creator.BasicUserDataCreator;
import com.liferay.users.admin.data.creator.OmniAdminUserDataCreator;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true)
public class BlogsDemo {

	@Activate
	protected void activate(BundleContext bundleContext) {
		try {
			Company company = _companyLocalService.getCompanyByWebId(
				"liferay.com");

			User omniAdminUser = _omniAdminUserDataCreator.create(
				company.getCompanyId(), "sergio.gonzalez@liferay.com");

			_basicUserDataCreator.create(
				company.getCompanyId(), "sharon.choi@liferay.com");
			_basicUserDataCreator.create(
				company.getCompanyId(), "nikki.prudencio@liferay.com");

			Group guestGroup = _groupLocalService.getGroup(
				company.getCompanyId(), "Guest");

			_fullBlogsEntryDataCreator.create(
				omniAdminUser.getUserId(), guestGroup.getGroupId(),
				"Esta es mi primera entrada de prueba");
			_fullBlogsEntryDataCreator.create(
				omniAdminUser.getUserId(), guestGroup.getGroupId(),
				"Esta es mi segunda entrada de blog");

			_blogsLayoutDataCreator.create(
				omniAdminUser.getUserId(), guestGroup.getGroupId(), "Blogs");
		}
		catch (PortalException pe) {
			pe.printStackTrace();
		}
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		_omniAdminUserDataCreator.delete();
		_basicUserDataCreator.delete();
		_fullBlogsEntryDataCreator.delete();
		_blogsLayoutDataCreator.delete();
	}

	@Reference(unbind = "-")
	protected void setBasicUserDataCreator(
		BasicUserDataCreator basicUserDataCreator) {

		_basicUserDataCreator = basicUserDataCreator;
	}

	@Reference(unbind = "-")
	protected void setBlogsLayoutDataCreator(
		BlogsLayoutDataCreator blogsLayoutDataCreator) {

		_blogsLayoutDataCreator = blogsLayoutDataCreator;
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setFullBlogsEntryDataCreator(
		FullBlogsEntryDataCreator fullBlogsEntryDataCreator) {

		_fullBlogsEntryDataCreator = fullBlogsEntryDataCreator;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setOmniAdminUserDataCreator(
		OmniAdminUserDataCreator omniAdminUserDataCreator) {

		_omniAdminUserDataCreator = omniAdminUserDataCreator;
	}

	private BasicUserDataCreator _basicUserDataCreator;
	private BlogsLayoutDataCreator _blogsLayoutDataCreator;
	private CompanyLocalService _companyLocalService;
	private FullBlogsEntryDataCreator _fullBlogsEntryDataCreator;
	private GroupLocalService _groupLocalService;
	private OmniAdminUserDataCreator _omniAdminUserDataCreator;

}