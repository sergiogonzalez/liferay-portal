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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchPortletItemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.impl.PortletItemModelImpl;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PropsValues;

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
public class PortletItemPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (PortletItemPersistence)PortalBeanLocatorUtil.locate(PortletItemPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		PortletItem portletItem = _persistence.create(pk);

		Assert.assertNotNull(portletItem);

		Assert.assertEquals(portletItem.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PortletItem newPortletItem = addPortletItem();

		_persistence.remove(newPortletItem);

		PortletItem existingPortletItem = _persistence.fetchByPrimaryKey(newPortletItem.getPrimaryKey());

		Assert.assertNull(existingPortletItem);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPortletItem();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		PortletItem newPortletItem = _persistence.create(pk);

		newPortletItem.setGroupId(ServiceTestUtil.nextLong());

		newPortletItem.setCompanyId(ServiceTestUtil.nextLong());

		newPortletItem.setUserId(ServiceTestUtil.nextLong());

		newPortletItem.setUserName(ServiceTestUtil.randomString());

		newPortletItem.setCreateDate(ServiceTestUtil.nextDate());

		newPortletItem.setModifiedDate(ServiceTestUtil.nextDate());

		newPortletItem.setName(ServiceTestUtil.randomString());

		newPortletItem.setPortletId(ServiceTestUtil.randomString());

		newPortletItem.setClassNameId(ServiceTestUtil.nextLong());

		_persistence.update(newPortletItem, false);

		PortletItem existingPortletItem = _persistence.findByPrimaryKey(newPortletItem.getPrimaryKey());

		Assert.assertEquals(existingPortletItem.getPortletItemId(),
			newPortletItem.getPortletItemId());
		Assert.assertEquals(existingPortletItem.getGroupId(),
			newPortletItem.getGroupId());
		Assert.assertEquals(existingPortletItem.getCompanyId(),
			newPortletItem.getCompanyId());
		Assert.assertEquals(existingPortletItem.getUserId(),
			newPortletItem.getUserId());
		Assert.assertEquals(existingPortletItem.getUserName(),
			newPortletItem.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingPortletItem.getCreateDate()),
			Time.getShortTimestamp(newPortletItem.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingPortletItem.getModifiedDate()),
			Time.getShortTimestamp(newPortletItem.getModifiedDate()));
		Assert.assertEquals(existingPortletItem.getName(),
			newPortletItem.getName());
		Assert.assertEquals(existingPortletItem.getPortletId(),
			newPortletItem.getPortletId());
		Assert.assertEquals(existingPortletItem.getClassNameId(),
			newPortletItem.getClassNameId());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PortletItem newPortletItem = addPortletItem();

		PortletItem existingPortletItem = _persistence.findByPrimaryKey(newPortletItem.getPrimaryKey());

		Assert.assertEquals(existingPortletItem, newPortletItem);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchPortletItemException");
		}
		catch (NoSuchPortletItemException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PortletItem newPortletItem = addPortletItem();

		PortletItem existingPortletItem = _persistence.fetchByPrimaryKey(newPortletItem.getPrimaryKey());

		Assert.assertEquals(existingPortletItem, newPortletItem);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		PortletItem missingPortletItem = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPortletItem);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PortletItem newPortletItem = addPortletItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				PortletItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portletItemId",
				newPortletItem.getPortletItemId()));

		List<PortletItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		PortletItem existingPortletItem = result.get(0);

		Assert.assertEquals(existingPortletItem, newPortletItem);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				PortletItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portletItemId",
				ServiceTestUtil.nextLong()));

		List<PortletItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PortletItem newPortletItem = addPortletItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				PortletItem.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portletItemId"));

		Object newPortletItemId = newPortletItem.getPortletItemId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("portletItemId",
				new Object[] { newPortletItemId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPortletItemId = result.get(0);

		Assert.assertEquals(existingPortletItemId, newPortletItemId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				PortletItem.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portletItemId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("portletItemId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PortletItem newPortletItem = addPortletItem();

		_persistence.clearCache();

		PortletItemModelImpl existingPortletItemModelImpl = (PortletItemModelImpl)_persistence.findByPrimaryKey(newPortletItem.getPrimaryKey());

		Assert.assertEquals(existingPortletItemModelImpl.getGroupId(),
			existingPortletItemModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingPortletItemModelImpl.getName(),
				existingPortletItemModelImpl.getOriginalName()));
		Assert.assertTrue(Validator.equals(
				existingPortletItemModelImpl.getPortletId(),
				existingPortletItemModelImpl.getOriginalPortletId()));
		Assert.assertEquals(existingPortletItemModelImpl.getClassNameId(),
			existingPortletItemModelImpl.getOriginalClassNameId());
	}

	protected PortletItem addPortletItem() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		PortletItem portletItem = _persistence.create(pk);

		portletItem.setGroupId(ServiceTestUtil.nextLong());

		portletItem.setCompanyId(ServiceTestUtil.nextLong());

		portletItem.setUserId(ServiceTestUtil.nextLong());

		portletItem.setUserName(ServiceTestUtil.randomString());

		portletItem.setCreateDate(ServiceTestUtil.nextDate());

		portletItem.setModifiedDate(ServiceTestUtil.nextDate());

		portletItem.setName(ServiceTestUtil.randomString());

		portletItem.setPortletId(ServiceTestUtil.randomString());

		portletItem.setClassNameId(ServiceTestUtil.nextLong());

		_persistence.update(portletItem, false);

		return portletItem;
	}

	private PortletItemPersistence _persistence;
}