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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.OrganizationTestUtil;
import com.liferay.portal.util.TestPropsValues;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Ferrer
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class OrganizationLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();
	}

	@Test
	public void testAddOrganization() throws Exception {
		User user = TestPropsValues.getUser();

		Organization organization =
			OrganizationLocalServiceUtil.addOrganization(
				user.getUserId(),
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
				"Organization", false);

		List<Organization> organizations = user.getOrganizations(true);

		Assert.assertTrue(organizations.contains(organization));

		Assert.assertFalse(
			OrganizationLocalServiceUtil.hasUserOrganization(
				user.getUserId(), organization.getOrganizationId()));
	}

	@Test
	public void testAddOrganizationWithoutSiteToParentOrganizationWithoutSite()
		throws Exception {

		Organization[] organizations = createOrganizations(
			new boolean[] {false, false, false});

		Organization organizationA = organizations[0];
		Organization organizationAA = organizations[1];

		Assert.assertEquals(
			organizationA.getOrganizationId(),
			organizationAA.getParentOrganizationId());

		Group groupAA = organizationAA.getGroup();

		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, groupAA.getParentGroupId());
	}

	@Test
	public void testAddOrganizationWithoutSiteToParentOrganizationWithSite()
		throws Exception {

		Organization[] organizations = createOrganizations(
			new boolean[] {true, false, false});

		Organization organizationA = organizations[0];
		Organization organizationAA = organizations[1];

		Assert.assertEquals(
			organizationA.getOrganizationId(),
			organizationAA.getParentOrganizationId());

		Group groupAA = organizationAA.getGroup();

		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, groupAA.getParentGroupId());
	}

	@Test
	public void testAddOrganizationWithSiteToParentOrganizationWithoutSite()
		throws Exception {

		Organization[] organizations = createOrganizations(
			new boolean[] {false, true, false});

		Organization organizationA = organizations[0];
		Organization organizationAA = organizations[1];

		Assert.assertEquals(
			organizationA.getOrganizationId(),
			organizationAA.getParentOrganizationId());

		Group groupAA = organizationAA.getGroup();

		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, groupAA.getParentGroupId());
	}

	@Test
	public void testAddOrganizationWithSiteToParentOrganizationWithSite()
		throws Exception {

		Organization[] organizations = createOrganizations(
			new boolean[] {true, true, false});

		Organization organizationA = organizations[0];
		Organization organizationAA = organizations[1];

		Assert.assertEquals(
			organizationA.getOrganizationId(),
			organizationAA.getParentOrganizationId());

		Group groupAA = organizationAA.getGroup();

		Assert.assertEquals(
			organizationA.getGroupId(), groupAA.getParentGroupId());
	}

	@Test
	public void testMoveOrganizationWithoutSiteToParentOrganizationWithoutSite()
		throws Exception {

		Organization[] organizations = createOrganizations(
			new boolean[] {false, true, false});

		Organization organizationAA = organizations[1];
		Organization organizationB = organizations[2];

		organizationAA = OrganizationLocalServiceUtil.updateOrganization(
			TestPropsValues.getCompanyId(), organizationAA.getOrganizationId(),
			organizationB.getOrganizationId(), organizationAA.getName(),
			organizationAA.getType(), organizationAA.getRegionId(),
			organizationAA.getCountryId(), organizationAA.getStatusId(),
			organizationAA.getComments(), true, null);

		Assert.assertEquals(
			organizationB.getOrganizationId(),
			organizationAA.getParentOrganizationId());

		Group groupAA = organizationAA.getGroup();

		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, groupAA.getParentGroupId());
	}

	@Test
	public void testMoveOrganizationWithoutSiteToParentOrganizationWithSite()
		throws Exception {

		Organization[] organizations = createOrganizations(
			new boolean[] {false, true, true});

		Organization organizationAA = organizations[1];
		Organization organizationB = organizations[2];

		organizationAA = OrganizationLocalServiceUtil.updateOrganization(
			TestPropsValues.getCompanyId(), organizationAA.getOrganizationId(),
			organizationB.getOrganizationId(), organizationAA.getName(),
			organizationAA.getType(), organizationAA.getRegionId(),
			organizationAA.getCountryId(), organizationAA.getStatusId(),
			organizationAA.getComments(), true, null);

		Assert.assertEquals(
			organizationB.getOrganizationId(),
			organizationAA.getParentOrganizationId());

		Group groupAA = organizationAA.getGroup();

		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, groupAA.getParentGroupId());
	}

	@Test
	public void testMoveOrganizationWithSiteToParentOrganizationWithoutSite()
		throws Exception {

		Organization[] organizations = createOrganizations(
			new boolean[] {true, true, false});

		Organization organizationAA = organizations[1];
		Organization organizationB = organizations[2];

		organizationAA = OrganizationLocalServiceUtil.updateOrganization(
			TestPropsValues.getCompanyId(), organizationAA.getOrganizationId(),
			organizationB.getOrganizationId(), organizationAA.getName(),
			organizationAA.getType(), organizationAA.getRegionId(),
			organizationAA.getCountryId(), organizationAA.getStatusId(),
			organizationAA.getComments(), true, null);

		Assert.assertEquals(
			organizationB.getOrganizationId(),
			organizationAA.getParentOrganizationId());

		Group groupAA = organizationAA.getGroup();

		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, groupAA.getParentGroupId());
	}

	@Test
	public void testMoveOrganizationWithSiteToParentOrganizationWithSite()
		throws Exception {

		Organization[] organizations = createOrganizations(
			new boolean[] {true, true, true});

		Organization organizationAA = organizations[1];
		Organization organizationB = organizations[2];

		organizationAA = OrganizationLocalServiceUtil.updateOrganization(
			TestPropsValues.getCompanyId(), organizationAA.getOrganizationId(),
			organizationB.getOrganizationId(), organizationAA.getName(),
			organizationAA.getType(), organizationAA.getRegionId(),
			organizationAA.getCountryId(), organizationAA.getStatusId(),
			organizationAA.getComments(), true, null);

		Assert.assertEquals(
			organizationB.getOrganizationId(),
			organizationAA.getParentOrganizationId());

		Group groupAA = organizationAA.getGroup();

		Assert.assertEquals(
			organizationB.getGroupId(), groupAA.getParentGroupId());
	}

	@Test
	public void testSearchCountWithFallbackToAnyParent() throws Exception {
		testSearchCount(true);
	}

	@Test
	public void testSearchCountWithoutFallbackToAnyParent() throws Exception {
		testSearchCount(false);
	}

	protected Organization[] createOrganizations(
			boolean[] associateWithMainSite)
		throws Exception {

		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", associateWithMainSite[0]);

		Organization organizationAA = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization AA",
			associateWithMainSite[1]);

		Organization organizationB = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization B", associateWithMainSite[2]);

		return new Organization[] {
			organizationA, organizationAA, organizationB};
	}

	protected void doTestSearchCount(
			int expected, long parentOrganizationId,
			List<Organization> organizations, boolean fallbackToAnyParent)
		throws Exception {

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("organizationsTree", organizations);

		int actual = OrganizationLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), parentOrganizationId, null, null,
			null, null, params, fallbackToAnyParent);

		Assert.assertEquals(expected, actual);
	}

	protected void testSearchCount(boolean fallbackToAnyParent)
		throws Exception {

		Organization[] organizations = createOrganizations(
			new boolean[] {false, false, false});

		Organization organizationA = organizations[0];
		Organization organizationAA = organizations[1];
		Organization organizationB = organizations[2];

		List<Organization> allOrganizations = ListUtil.fromArray(organizations);

		List<Organization> subOrganizations = ListUtil.fromArray(
			new Organization[] {organizationAA});

		List<Organization> toplevelOrganizations = ListUtil.fromArray(
			new Organization[] {organizationA, organizationB});

		// Test with OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID

		int expected = 2;

		if (fallbackToAnyParent) {
			expected = 3;
		}

		doTestSearchCount(
			expected, OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			allOrganizations, fallbackToAnyParent);

		expected = 0;

		if (fallbackToAnyParent) {
			expected = 1;
		}

		doTestSearchCount(
			expected, OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			subOrganizations, fallbackToAnyParent);

		expected = 2;

		if (fallbackToAnyParent) {
			expected = 3;
		}

		doTestSearchCount(
			expected, OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			toplevelOrganizations, fallbackToAnyParent);

		// Test with OrganizationConstants.ANY_PARENT_ORGANIZATION_ID

		doTestSearchCount(
			allOrganizations.size(),
			OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, allOrganizations,
			fallbackToAnyParent);

		doTestSearchCount(
			subOrganizations.size(),
			OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, subOrganizations,
			fallbackToAnyParent);

		doTestSearchCount(
			allOrganizations.size(),
			OrganizationConstants.ANY_PARENT_ORGANIZATION_ID,
			toplevelOrganizations, fallbackToAnyParent);

		// Test with organizationA.getOrganizationId()

		doTestSearchCount(
			1, organizationA.getOrganizationId(), allOrganizations,
			fallbackToAnyParent);

		doTestSearchCount(
			1, organizationA.getOrganizationId(), subOrganizations,
			fallbackToAnyParent);

		doTestSearchCount(
			1, organizationA.getOrganizationId(), toplevelOrganizations,
			fallbackToAnyParent);

		// Test with organizationB.getOrganizationId()

		doTestSearchCount(
			0, organizationB.getOrganizationId(), allOrganizations,
			fallbackToAnyParent);

		doTestSearchCount(
			0, organizationB.getOrganizationId(), subOrganizations,
			fallbackToAnyParent);

		doTestSearchCount(
			0, organizationB.getOrganizationId(), toplevelOrganizations,
			fallbackToAnyParent);

	}

}