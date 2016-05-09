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

package com.liferay.wiki.service.permission.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.permission.test.BasePermissionTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.permission.WikiPagePermissionChecker;
import com.liferay.wiki.service.permission.WikiResourcePermissionChecker;
import com.liferay.wiki.util.test.WikiTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
@Sync
public class WikiPagePermissionCheckerTest extends BasePermissionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testGuestCouldNotSeeDraftPage() throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			_groupAdminUser.getUserId(), group.getGroupId(), _node.getNodeId(),
			RandomTestUtil.randomString(), false);

		Assert.assertFalse(
			WikiPagePermissionChecker.contains(
				permissionChecker, page, ActionKeys.VIEW));
	}

	@Test
	public void testGuestCouldSeeHeadPage() throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			_groupAdminUser.getUserId(), group.getGroupId(), _node.getNodeId(),
			RandomTestUtil.randomString(), true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		WikiPageLocalServiceUtil.updatePage(
			_groupAdminUser.getUserId(), page.getNodeId(), page.getTitle(),
			page.getVersion(), RandomTestUtil.randomString(), StringPool.BLANK,
			true, page.getFormat(), page.getParentTitle(),
			page.getRedirectTitle(), serviceContext);

		WikiPage draftPage = WikiPageLocalServiceUtil.updateStatus(
			page.getUserId(), page.getResourcePrimKey(),
			WorkflowConstants.STATUS_DRAFT, serviceContext);

		Assert.assertTrue(
			WikiPagePermissionChecker.contains(
				permissionChecker, draftPage, ActionKeys.VIEW));
	}

	protected void addPortletModelViewPermission() throws Exception {
		return;
	}

	protected void doSetUp() throws Exception {
		_node = WikiTestUtil.addNode(group.getGroupId());

		_groupAdminUser = UserTestUtil.addGroupAdminUser(group);
	}

	protected String getResourceName() {
		return WikiResourcePermissionChecker.RESOURCE_NAME;
	}

	private User _groupAdminUser;
	private WikiNode _node;

}