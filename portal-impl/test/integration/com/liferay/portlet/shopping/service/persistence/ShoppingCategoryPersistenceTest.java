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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import com.liferay.portlet.shopping.NoSuchCategoryException;
import com.liferay.portlet.shopping.model.ShoppingCategory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class ShoppingCategoryPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (ShoppingCategoryPersistence)PortalBeanLocatorUtil.locate(ShoppingCategoryPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ShoppingCategory shoppingCategory = _persistence.create(pk);

		Assert.assertNotNull(shoppingCategory);

		Assert.assertEquals(shoppingCategory.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		_persistence.remove(newShoppingCategory);

		ShoppingCategory existingShoppingCategory = _persistence.fetchByPrimaryKey(newShoppingCategory.getPrimaryKey());

		Assert.assertNull(existingShoppingCategory);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addShoppingCategory();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ShoppingCategory newShoppingCategory = _persistence.create(pk);

		newShoppingCategory.setGroupId(ServiceTestUtil.nextLong());

		newShoppingCategory.setCompanyId(ServiceTestUtil.nextLong());

		newShoppingCategory.setUserId(ServiceTestUtil.nextLong());

		newShoppingCategory.setUserName(ServiceTestUtil.randomString());

		newShoppingCategory.setCreateDate(ServiceTestUtil.nextDate());

		newShoppingCategory.setModifiedDate(ServiceTestUtil.nextDate());

		newShoppingCategory.setParentCategoryId(ServiceTestUtil.nextLong());

		newShoppingCategory.setName(ServiceTestUtil.randomString());

		newShoppingCategory.setDescription(ServiceTestUtil.randomString());

		_persistence.update(newShoppingCategory, false);

		ShoppingCategory existingShoppingCategory = _persistence.findByPrimaryKey(newShoppingCategory.getPrimaryKey());

		Assert.assertEquals(existingShoppingCategory.getCategoryId(),
			newShoppingCategory.getCategoryId());
		Assert.assertEquals(existingShoppingCategory.getGroupId(),
			newShoppingCategory.getGroupId());
		Assert.assertEquals(existingShoppingCategory.getCompanyId(),
			newShoppingCategory.getCompanyId());
		Assert.assertEquals(existingShoppingCategory.getUserId(),
			newShoppingCategory.getUserId());
		Assert.assertEquals(existingShoppingCategory.getUserName(),
			newShoppingCategory.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingCategory.getCreateDate()),
			Time.getShortTimestamp(newShoppingCategory.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingCategory.getModifiedDate()),
			Time.getShortTimestamp(newShoppingCategory.getModifiedDate()));
		Assert.assertEquals(existingShoppingCategory.getParentCategoryId(),
			newShoppingCategory.getParentCategoryId());
		Assert.assertEquals(existingShoppingCategory.getName(),
			newShoppingCategory.getName());
		Assert.assertEquals(existingShoppingCategory.getDescription(),
			newShoppingCategory.getDescription());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		ShoppingCategory existingShoppingCategory = _persistence.findByPrimaryKey(newShoppingCategory.getPrimaryKey());

		Assert.assertEquals(existingShoppingCategory, newShoppingCategory);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchCategoryException");
		}
		catch (NoSuchCategoryException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		ShoppingCategory existingShoppingCategory = _persistence.fetchByPrimaryKey(newShoppingCategory.getPrimaryKey());

		Assert.assertEquals(existingShoppingCategory, newShoppingCategory);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ShoppingCategory missingShoppingCategory = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingShoppingCategory);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCategory.class,
				ShoppingCategory.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId",
				newShoppingCategory.getCategoryId()));

		List<ShoppingCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ShoppingCategory existingShoppingCategory = result.get(0);

		Assert.assertEquals(existingShoppingCategory, newShoppingCategory);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCategory.class,
				ShoppingCategory.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId",
				ServiceTestUtil.nextLong()));

		List<ShoppingCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCategory.class,
				ShoppingCategory.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		Object newCategoryId = newShoppingCategory.getCategoryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { newCategoryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCategoryId = result.get(0);

		Assert.assertEquals(existingCategoryId, newCategoryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCategory.class,
				ShoppingCategory.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ShoppingCategory addShoppingCategory() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ShoppingCategory shoppingCategory = _persistence.create(pk);

		shoppingCategory.setGroupId(ServiceTestUtil.nextLong());

		shoppingCategory.setCompanyId(ServiceTestUtil.nextLong());

		shoppingCategory.setUserId(ServiceTestUtil.nextLong());

		shoppingCategory.setUserName(ServiceTestUtil.randomString());

		shoppingCategory.setCreateDate(ServiceTestUtil.nextDate());

		shoppingCategory.setModifiedDate(ServiceTestUtil.nextDate());

		shoppingCategory.setParentCategoryId(ServiceTestUtil.nextLong());

		shoppingCategory.setName(ServiceTestUtil.randomString());

		shoppingCategory.setDescription(ServiceTestUtil.randomString());

		_persistence.update(shoppingCategory, false);

		return shoppingCategory;
	}

	private ShoppingCategoryPersistence _persistence;
}