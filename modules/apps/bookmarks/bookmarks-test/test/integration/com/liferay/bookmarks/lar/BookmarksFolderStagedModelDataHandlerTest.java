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

package com.liferay.bookmarks.lar;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.bookmarks.util.BookmarksTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Daniel Kocsis
 */
public class BookmarksFolderStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		addDependentStagedModel(
			dependentStagedModelsMap, BookmarksFolder.class, folder);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			BookmarksFolder.class.getSimpleName());

		BookmarksFolder folder = (BookmarksFolder)dependentStagedModels.get(0);

		return BookmarksTestUtil.addFolder(
			group.getGroupId(), folder.getFolderId(),
			RandomTestUtil.randomString());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return BookmarksFolderLocalServiceUtil.
				getBookmarksFolderByUuidAndGroupId(uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return BookmarksFolder.class;
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			BookmarksFolder.class.getSimpleName());

		Assert.assertEquals(1, dependentStagedModels.size());

		BookmarksFolder folder = (BookmarksFolder)dependentStagedModels.get(0);

		BookmarksFolderLocalServiceUtil.getBookmarksFolderByUuidAndGroupId(
			folder.getUuid(), group.getGroupId());
	}

}