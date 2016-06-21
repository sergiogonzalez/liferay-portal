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

package com.liferay.knowledge.base.model.impl.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBFolderLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
@Sync
public class KBFolderImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = TestPropsValues.getUser();
	}

	@Test
	public void testGetAncestorFolderIds() throws Exception {
		KBFolder kbFolder1 = addKbFolder(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		KBFolder kbFolder2 = addKbFolder(kbFolder1.getKbFolderId());

		KBFolder kbFolder3 = addKbFolder(kbFolder2.getKbFolderId());

		KBFolder kbFolder4 = addKbFolder(kbFolder3.getKbFolderId());

		KBFolder kbFolder5 = addKbFolder(kbFolder4.getKbFolderId());

		List<KBFolder> kbFolderList = new ArrayList<>();

		kbFolderList.add(kbFolder5);
		kbFolderList.add(kbFolder4);
		kbFolderList.add(kbFolder3);
		kbFolderList.add(kbFolder2);
		kbFolderList.add(kbFolder1);

		List<Long> ancestorFolderIds = kbFolder5.getAncestorFolderIds();

		Assert.assertEquals(kbFolderList.size(), ancestorFolderIds.size());

		for (int i = 0; i < kbFolderList.size(); i++) {
			KBFolder curKBFolder = kbFolderList.get(i);

			Assert.assertEquals(
				curKBFolder.getKbFolderId(), (long)ancestorFolderIds.get(i));
		}
	}

	protected KBFolder addKbFolder(long parentResourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		return KBFolderLocalServiceUtil.addKBFolder(
			_user.getUserId(), _group.getGroupId(),
			PortalUtil.getClassNameId(KBFolderConstants.getClassName()),
			parentResourcePrimKey, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

	private User _user;

}