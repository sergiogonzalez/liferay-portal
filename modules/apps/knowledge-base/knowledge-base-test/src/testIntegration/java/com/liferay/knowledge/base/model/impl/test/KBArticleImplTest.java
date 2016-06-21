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
import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
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
public class KBArticleImplTest {

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
		KBArticle kbArticle1 = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		KBArticle kbArticle2 = addKBArticle(kbArticle1.getKbFolderId());

		KBArticle kbArticle3 = addKBArticle(kbArticle2.getKbFolderId());

		KBArticle kbArticle4 = addKBArticle(kbArticle3.getKbFolderId());

		KBArticle kbArticle5 = addKBArticle(kbArticle4.getKbFolderId());

		List<KBArticle> kbArticleList = new ArrayList<>();

		kbArticleList.add(kbArticle5);
		kbArticleList.add(kbArticle4);
		kbArticleList.add(kbArticle3);
		kbArticleList.add(kbArticle2);
		kbArticleList.add(kbArticle1);

		List<Long> ancestorResourcePrimaryKeys =
			kbArticle5.getAncestorResourcePrimaryKeys();

		Assert.assertEquals(
			kbArticleList.size(), ancestorResourcePrimaryKeys.size());

		for (int i = 0; i < kbArticleList.size(); i++) {
			KBArticle curKBArticle = kbArticleList.get(i);

			Assert.assertEquals(
				curKBArticle.getParentResourcePrimKey(),
				(long)ancestorResourcePrimaryKeys.get(i));
		}
	}

	protected KBArticle addKBArticle(long parentResourcePrimKey)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		return KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(),
			PortalUtil.getClassNameId(KBArticleConstants.getClassName()),
			parentResourcePrimKey, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, new String[0], new String[0],
			serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

	private User _user;

}