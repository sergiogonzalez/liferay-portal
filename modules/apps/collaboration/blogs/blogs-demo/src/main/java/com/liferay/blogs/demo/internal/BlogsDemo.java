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

package com.liferay.blogs.demo.internal;

import com.liferay.blogs.demo.data.creator.BlogsEntryDemoDataCreator;
import com.liferay.blogs.demo.data.creator.BlogsEntryDemoDataCreatorBuilder;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.users.admin.demo.data.creator.BasicUserDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.OmniAdminUserDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.SiteAdminUserDemoDataCreator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class BlogsDemo extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		Group guestGroup = _groupLocalService.getGroup(
			company.getCompanyId(), "Guest");

		final User user1 = _basicUserDemoDataCreator.create(
			company.getCompanyId(), "nikki.prudencio@liferay.com");

		final User user2 = _omniAdminUserDemoDataCreator.create(
			company.getCompanyId(), "sergio.gonzalez@liferay.com");

		final User user3 = _siteAdminUserDemoDataCreator.create(
			guestGroup.getGroupId(), "sharon.choi@liferay.com");

		List<User> users = new ArrayList<User>() {
			{
				add(user1);
				add(user2);
				add(user3);
			}
		};

		for (int i = 0; i < 30; i++) {
			users.add(_basicUserDemoDataCreator.create(company.getCompanyId()));
		}

		for (User user : users) {
			_createRandomBlogsEntry(guestGroup, user);
		}
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		for (BlogsEntryDemoDataCreator blogsEntryDemoDataCreator :
				_blogsEntryDemoDataCreators) {

			blogsEntryDemoDataCreator.delete();
		}

		_basicUserDemoDataCreator.delete();
		_omniAdminUserDemoDataCreator.delete();
		_siteAdminUserDemoDataCreator.delete();
	}

	private void _createRandomBlogsEntry(Group guestGroup, User user)
		throws IOException, PortalException {

		BlogsEntryDemoDataCreator blogsEntryDemoDataCreator = _getRandomElement(
			_blogsEntryDemoDataCreators);

		BlogsEntryDemoDataCreatorBuilder blogsEntryDemoDataCreatorBuilder =
			blogsEntryDemoDataCreator.newBuilder();

		blogsEntryDemoDataCreatorBuilder.withComments(
		).withRatings(
		).build(
			user.getUserId(), guestGroup.getGroupId()
		);
	}

	private <T> T _getRandomElement(List<T> list) {
		return list.get(RandomUtil.nextInt(list.size()));
	}

	@Reference
	private BasicUserDemoDataCreator _basicUserDemoDataCreator;

	@Reference
	private List<BlogsEntryDemoDataCreator> _blogsEntryDemoDataCreators;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private OmniAdminUserDemoDataCreator _omniAdminUserDemoDataCreator;

	@Reference
	private SiteAdminUserDemoDataCreator _siteAdminUserDemoDataCreator;

}