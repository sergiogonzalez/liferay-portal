/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.util.SocialConfigurationUtil;

import java.io.InputStream;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class SocialActivitySettingLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/liferay-social.xml");

		String xml = new String(FileUtil.getBytes(inputStream));

		SocialConfigurationUtil.read(
			clazz.getClassLoader(), new String[] {xml});

		_activityDefinition = SocialConfigurationUtil.getActivityDefinition(
			TEST_MODEL, 1);

		_group = GroupLocalServiceUtil.getGroup(
			PortalUtil.getDefaultCompanyId(), GroupConstants.GUEST);
	}

	@Test
	public void testGetActivityDefinition() throws Exception {
		SocialActivityDefinition activityDefinition =
			SocialActivitySettingLocalServiceUtil.getActivityDefinition(
				_group.getGroupId(), TEST_MODEL, 1);

		Assert.assertEquals(_activityDefinition, activityDefinition);

		List<SocialActivityDefinition> activityDefinitions =
			SocialActivitySettingLocalServiceUtil.getActivityDefinitions(
				_group.getGroupId(), TEST_MODEL);

		Assert.assertNotNull(activityDefinitions);
		Assert.assertEquals(1, activityDefinitions.size());
		Assert.assertEquals(_activityDefinition, activityDefinitions.get(0));
	}

	@Test
	public void testUpdateActivitySettings() throws Exception {
		long classNameId = PortalUtil.getClassNameId(TEST_MODEL);

		Assert.assertFalse(
			SocialActivitySettingLocalServiceUtil.isEnabled(
				_group.getGroupId(), classNameId));

		SocialActivitySettingLocalServiceUtil.updateActivitySetting(
			_group.getGroupId(), TEST_MODEL, true);

		Assert.assertTrue(
			SocialActivitySettingLocalServiceUtil.isEnabled(
			_group.getGroupId(), classNameId));
	}

	private static final String TEST_MODEL = "TEST_MODEL";

	private SocialActivityDefinition _activityDefinition;
	private Group _group;

}