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

package com.liferay.portal.service;

import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.TestPropsValues;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class LayoutLocalServiceTest {

	@Test
	public void testDeleteGuestGroupLastPrivateLayout() throws Exception {
		testDeleteGuestGroupLastLayout(true);
	}

	@Test
	public void testDeleteGuestGroupLastPublicLayout() throws Exception {
		testDeleteGuestGroupLastLayout(false);
	}

	protected void testDeleteGuestGroupLastLayout(boolean privateLayout)
		throws Exception {

		List<Layout> layouts = new ArrayList<Layout>();

		Group group = GroupLocalServiceUtil.getGroup(
			TestPropsValues.getCompanyId(), GroupConstants.GUEST);

		layouts.addAll(
			LayoutLocalServiceUtil.getLayouts(
				group.getGroupId(), privateLayout,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID));

		if (layouts.isEmpty()) {
			layouts.add(
				LayoutTestUtil.addLayout(
					group.getGroupId(), StringUtil.randomString(),
					privateLayout));
		}

		ServiceContext serviceContext = new ServiceContext();

		try {
			LayoutLocalServiceUtil.deleteLayouts(
				group.getGroupId(), privateLayout, serviceContext);
		}
		catch (RequiredLayoutException rle) {
			if (privateLayout) {
				Assert.fail("Guest group must not have any private pages");
			}

			return;
		}

		if (!privateLayout) {
			Assert.fail("Guest group must have at least one public page");
		}
	}

}