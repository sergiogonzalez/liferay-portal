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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Organization;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.OrganizationTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shinn Lok
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class OrganizationImplTest {

	@Before
	public void setUp() throws Exception {
		_organization1 = OrganizationTestUtil.addOrganization();
		_organization2 = OrganizationTestUtil.addOrganization(
			_organization1.getOrganizationId(), RandomTestUtil.randomString(),
			false);
		_organization3 = OrganizationTestUtil.addOrganization(
			_organization2.getOrganizationId(), RandomTestUtil.randomString(),
			false);
		_organization4 = OrganizationTestUtil.addOrganization(
			_organization3.getOrganizationId(), RandomTestUtil.randomString(),
			false);
	}

	@Test
	public void testGetAncestorOrganizationIds() throws Exception {
		Assert.assertArrayEquals(
			new long[] {
				_organization1.getOrganizationId(),
				_organization2.getOrganizationId(),
				_organization3.getOrganizationId()
			},
			_organization4.getAncestorOrganizationIds());
	}

	@Test
	public void testGetAncestorOrganizationIdsWithNullTreePath()
		throws Exception {

		_organization4.setTreePath(null);

		Assert.assertArrayEquals(
			new long[] {
				_organization1.getOrganizationId(),
				_organization2.getOrganizationId(),
				_organization3.getOrganizationId()
			},
			_organization4.getAncestorOrganizationIds());
	}

	@DeleteAfterTestRun
	private Organization _organization1;

	@DeleteAfterTestRun
	private Organization _organization2;

	@DeleteAfterTestRun
	private Organization _organization3;

	@DeleteAfterTestRun
	private Organization _organization4;

}