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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.DoAsUserThread;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessageConstants;

import java.io.InputStream;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MBMessageServiceTest {

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddMessagesConcurrently() throws Exception {
		long[] userIds = new long[ServiceTestUtil.THREAD_COUNT];

		DoAsUserThread[] doAsUserThreads = new DoAsUserThread[userIds.length];

		for (int i = 0; i < ServiceTestUtil.THREAD_COUNT; i++) {
			User user = UserTestUtil.addUser(
				ServiceTestUtil.randomString(), _group.getGroupId());

			userIds[i] = user.getUserId();

			doAsUserThreads[i] = new AddMessageThread(
				userIds[i], _group.getGroupId());
		}

		for (DoAsUserThread doAsUserThread : doAsUserThreads) {
			doAsUserThread.start();
		}

		for (DoAsUserThread doAsUserThread : doAsUserThreads) {
			doAsUserThread.join();
		}

		int successCount = 0;

		for (DoAsUserThread doAsUserThread : doAsUserThreads) {
			if (doAsUserThread.isSuccess()) {
				successCount++;
			}
		}

		long userId = PrincipalThreadLocal.getUserId();

		PrincipalThreadLocal.setName(null);

		for (int i = 0; i < doAsUserThreads.length; i++) {
			UserLocalServiceUtil.deleteUser(userIds[i]);
		}

		GroupLocalServiceUtil.deleteGroup(_group);

		PrincipalThreadLocal.setName(userId);

		Assert.assertTrue(
			"Only " + successCount + " out of " + userIds.length +
				" threads added messages successfully",
			successCount == userIds.length);
	}

	private Group _group;

	private class AddMessageThread extends DoAsUserThread {

		public AddMessageThread(long userId, long groupId) {
			super(userId);

			_groupId = groupId;
		}

		@Override
		protected void doRun() throws Exception {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
				Collections.emptyList();

			MBMessageLocalServiceUtil.addMessage(
				PrincipalThreadLocal.getUserId(),
				ServiceTestUtil.randomString(), _groupId,
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, 0, 0,
				ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
				MBMessageConstants.DEFAULT_FORMAT, inputStreamOVPs, false, 0.0,
				false, serviceContext);
		}

		private long _groupId;

	}

}