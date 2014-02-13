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
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Beslic
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class MBCategoryLocalServiceTest {

	@Test
	public void testGetParentCategory() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		MBCategory parentCategory = MBCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			ServiceTestUtil.randomString(), StringPool.BLANK, serviceContext);

		MBCategory childCategory = MBCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), parentCategory.getCategoryId(),
			ServiceTestUtil.randomString(), StringPool.BLANK, serviceContext);

		Assert.assertNotNull(childCategory.getParentCategory());
		Assert.assertNull(parentCategory.getParentCategory());
	}

	@Test
	public void testGetParentDiscussionCategory() throws Exception {
		MBCategory discussionCategory = MBCategoryLocalServiceUtil.getCategory(
			MBCategoryConstants.DISCUSSION_CATEGORY_ID);

		Assert.assertNotNull(discussionCategory);
		Assert.assertNull(discussionCategory.getParentCategory());
	}

	@Before
	public void setUp() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_groupId = group.getGroupId();
	}

	@Test
	@Transactional
	public void testGetCategoriesCountExcludingCategories() throws Exception {
		int initialCategoriesCount =
			MBCategoryServiceUtil.getCategoriesCount(
				_groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		MBTestUtil.addCategory(_groupId);

		MBCategory excludedCategory1 = MBTestUtil.addCategory(_groupId);
		MBCategory excludedCategory2 = MBTestUtil.addCategory(_groupId);

		Assert.assertEquals(
			3 + initialCategoriesCount,
			MBCategoryServiceUtil.getCategoriesCount(
				_groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID));

		Assert.assertEquals(
			1 + initialCategoriesCount,
			MBCategoryServiceUtil.getCategoriesCount(
				_groupId,
				new long[] {
					excludedCategory1.getCategoryId(),
					excludedCategory2.getCategoryId()},
				new long[] {MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID},
				WorkflowConstants.STATUS_ANY));
	}

	@Test
	@Transactional
	public void testGetCategoriesCountExcludingCategory() throws Exception {
		int initialCategoriesCount =
			MBCategoryServiceUtil.getCategoriesCount(
				_groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		MBTestUtil.addCategory(_groupId);
		MBTestUtil.addCategory(_groupId);

		MBCategory excludedCategory = MBTestUtil.addCategory(_groupId);

		Assert.assertEquals(
			3 + initialCategoriesCount,
			MBCategoryServiceUtil.getCategoriesCount(
				_groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID));

		Assert.assertEquals(
			2 + initialCategoriesCount,
			MBCategoryServiceUtil.getCategoriesCount(
				_groupId, excludedCategory.getCategoryId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_ANY));
	}

	@Test
	@Transactional
	public void testGetCategoriesCountWithStatusApprovedExcludingCategories()
		throws Exception {

		int initialCategoriesCount =
			MBCategoryServiceUtil.getCategoriesCount(
				_groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED);

		MBTestUtil.addCategory(_groupId);

		MBCategory excludedCategory1 = MBTestUtil.addCategory(_groupId);
		MBCategory excludedCategory2 = MBTestUtil.addCategory(_groupId);

		MBCategory draftCategory = MBTestUtil.addCategory(_groupId);

		MBCategoryLocalServiceUtil.updateStatus(
			draftCategory.getUserId(), draftCategory.getCategoryId(),
			WorkflowConstants.STATUS_DRAFT);

		Assert.assertEquals(
			3 + initialCategoriesCount,
			MBCategoryServiceUtil.getCategoriesCount(
				_groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED));

		Assert.assertEquals(
			1 + initialCategoriesCount,
			MBCategoryServiceUtil.getCategoriesCount(
				_groupId,
				new long[] {
					excludedCategory1.getCategoryId(),
					excludedCategory2.getCategoryId()},
				new long[] {MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID},
				WorkflowConstants.STATUS_APPROVED));
	}

	@Test
	@Transactional
	public void testGetCategoriesCountWithStatusApprovedExcludingCategory()
		throws Exception {

		int initialCategoriesCount =
			MBCategoryServiceUtil.getCategoriesCount(
				_groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED);

		MBTestUtil.addCategory(_groupId);

		MBCategory excludedCategory = MBTestUtil.addCategory(_groupId);

		MBCategory draftCategory = MBTestUtil.addCategory(_groupId);

		MBCategoryLocalServiceUtil.updateStatus(
			draftCategory.getUserId(), draftCategory.getCategoryId(),
			WorkflowConstants.STATUS_DRAFT);

		Assert.assertEquals(
			2 + initialCategoriesCount,
			MBCategoryServiceUtil.getCategoriesCount(
				_groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED));

		Assert.assertEquals(
			1 + initialCategoriesCount,
			MBCategoryServiceUtil.getCategoriesCount(
				_groupId, excludedCategory.getCategoryId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED));
	}

	@Test
	@Transactional
	public void testGetCategoriesExcludingCategories() throws Exception {
		List<MBCategory> initialCategories =
			MBCategoryServiceUtil.getCategories(_groupId);

		List<MBCategory> expectedCategories = new ArrayList<MBCategory>();

		expectedCategories.addAll(initialCategories);

		expectedCategories.add(MBTestUtil.addCategory(_groupId));

		MBCategory excludedCategory1 = MBTestUtil.addCategory(_groupId);
		MBCategory excludedCategory2 = MBTestUtil.addCategory(_groupId);

		expectedCategories.add(excludedCategory1);
		expectedCategories.add(excludedCategory2);

		List<MBCategory> categories = MBCategoryServiceUtil.getCategories(
			_groupId);

		Assert.assertEquals(expectedCategories.size(), categories.size());
		Assert.assertTrue(expectedCategories.containsAll(categories));

		expectedCategories.remove(excludedCategory1);
		expectedCategories.remove(excludedCategory2);

		categories = MBCategoryServiceUtil.getCategories(
			_groupId,
			new long[] {
				excludedCategory1.getCategoryId(),
				excludedCategory2.getCategoryId()},
			new long[] {MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID},
			WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(expectedCategories.size(), categories.size());
		Assert.assertTrue(expectedCategories.containsAll(categories));
	}

	@Test
	@Transactional
	public void testGetCategoriesExcludingCategory() throws Exception {
		List<MBCategory> initialCategories =
			MBCategoryServiceUtil.getCategories(_groupId);

		List<MBCategory> expectedCategories = new ArrayList<MBCategory>();

		expectedCategories.addAll(initialCategories);

		expectedCategories.add(MBTestUtil.addCategory(_groupId));
		expectedCategories.add(MBTestUtil.addCategory(_groupId));

		MBCategory excludedCategory = MBTestUtil.addCategory(_groupId);

		expectedCategories.add(excludedCategory);

		List<MBCategory> categories = MBCategoryServiceUtil.getCategories(
			_groupId);

		Assert.assertEquals(expectedCategories.size(), categories.size());
		Assert.assertTrue(expectedCategories.containsAll(categories));

		expectedCategories.remove(excludedCategory);

		categories = MBCategoryServiceUtil.getCategories(
			_groupId, excludedCategory.getCategoryId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(expectedCategories.size(), categories.size());
		Assert.assertTrue(expectedCategories.containsAll(categories));
	}

	@Test
	@Transactional
	public void testGetCategoriesWithStatusApprovedExcludingCategories()
		throws Exception {

		List<MBCategory> initialCategories =
			MBCategoryServiceUtil.getCategories(
				_groupId,  WorkflowConstants.STATUS_APPROVED);

		List<MBCategory> expectedCategories = new ArrayList<MBCategory>();

		expectedCategories.addAll(initialCategories);

		expectedCategories.add(MBTestUtil.addCategory(_groupId));

		MBCategory excludedCategory1 = MBTestUtil.addCategory(_groupId);
		MBCategory excludedCategory2 = MBTestUtil.addCategory(_groupId);

		expectedCategories.add(excludedCategory1);
		expectedCategories.add(excludedCategory2);

		MBCategory draftCategory = MBTestUtil.addCategory(_groupId);

		MBCategoryLocalServiceUtil.updateStatus(
			draftCategory.getUserId(), draftCategory.getCategoryId(),
			WorkflowConstants.STATUS_DRAFT);

		List<MBCategory> categories =
			MBCategoryServiceUtil.getCategories(
				_groupId, WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(expectedCategories.size(), categories.size());
		Assert.assertTrue(expectedCategories.containsAll(categories));

		expectedCategories.remove(excludedCategory1);
		expectedCategories.remove(excludedCategory2);

		categories = MBCategoryServiceUtil.getCategories(
			_groupId,
			new long[] {
				excludedCategory1.getCategoryId(),
				excludedCategory2.getCategoryId()},
			new long[] {MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID},
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(expectedCategories.size(), categories.size());
		Assert.assertTrue(expectedCategories.containsAll(categories));
	}

	@Test
	@Transactional
	public void testGetCategoriesWithStatusApprovedExcludingCategory()
		throws Exception {

		List<MBCategory> initialCategories =
			MBCategoryServiceUtil.getCategories(
				_groupId, WorkflowConstants.STATUS_APPROVED);

		List<MBCategory> expectedCategories = new ArrayList<MBCategory>();

		expectedCategories.addAll(initialCategories);
		expectedCategories.add(MBTestUtil.addCategory(_groupId));

		MBCategory excludedCategory = MBTestUtil.addCategory(_groupId);

		expectedCategories.add(excludedCategory);

		MBCategory draftCategory = MBTestUtil.addCategory(_groupId);

		MBCategoryLocalServiceUtil.updateStatus(
			draftCategory.getUserId(), draftCategory.getCategoryId(),
			WorkflowConstants.STATUS_DRAFT);

		List<MBCategory> categories =
			MBCategoryServiceUtil.getCategories(
				_groupId, WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(expectedCategories.size(), categories.size());
		Assert.assertTrue(expectedCategories.containsAll(categories));

		expectedCategories.remove(excludedCategory);

		categories = MBCategoryServiceUtil.getCategories(
			_groupId, excludedCategory.getCategoryId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(expectedCategories.size(), categories.size());
		Assert.assertTrue(expectedCategories.containsAll(categories));
	}

	private long _groupId;

}