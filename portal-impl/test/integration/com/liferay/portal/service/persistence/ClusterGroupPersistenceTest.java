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

import com.liferay.portal.NoSuchClusterGroupException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.ClusterGroup;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

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
public class ClusterGroupPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (ClusterGroupPersistence)PortalBeanLocatorUtil.locate(ClusterGroupPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ClusterGroup clusterGroup = _persistence.create(pk);

		Assert.assertNotNull(clusterGroup);

		Assert.assertEquals(clusterGroup.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ClusterGroup newClusterGroup = addClusterGroup();

		_persistence.remove(newClusterGroup);

		ClusterGroup existingClusterGroup = _persistence.fetchByPrimaryKey(newClusterGroup.getPrimaryKey());

		Assert.assertNull(existingClusterGroup);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addClusterGroup();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ClusterGroup newClusterGroup = _persistence.create(pk);

		newClusterGroup.setName(ServiceTestUtil.randomString());

		newClusterGroup.setClusterNodeIds(ServiceTestUtil.randomString());

		newClusterGroup.setWholeCluster(ServiceTestUtil.randomBoolean());

		_persistence.update(newClusterGroup, false);

		ClusterGroup existingClusterGroup = _persistence.findByPrimaryKey(newClusterGroup.getPrimaryKey());

		Assert.assertEquals(existingClusterGroup.getClusterGroupId(),
			newClusterGroup.getClusterGroupId());
		Assert.assertEquals(existingClusterGroup.getName(),
			newClusterGroup.getName());
		Assert.assertEquals(existingClusterGroup.getClusterNodeIds(),
			newClusterGroup.getClusterNodeIds());
		Assert.assertEquals(existingClusterGroup.getWholeCluster(),
			newClusterGroup.getWholeCluster());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ClusterGroup newClusterGroup = addClusterGroup();

		ClusterGroup existingClusterGroup = _persistence.findByPrimaryKey(newClusterGroup.getPrimaryKey());

		Assert.assertEquals(existingClusterGroup, newClusterGroup);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchClusterGroupException");
		}
		catch (NoSuchClusterGroupException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ClusterGroup newClusterGroup = addClusterGroup();

		ClusterGroup existingClusterGroup = _persistence.fetchByPrimaryKey(newClusterGroup.getPrimaryKey());

		Assert.assertEquals(existingClusterGroup, newClusterGroup);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ClusterGroup missingClusterGroup = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingClusterGroup);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ClusterGroup newClusterGroup = addClusterGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClusterGroup.class,
				ClusterGroup.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("clusterGroupId",
				newClusterGroup.getClusterGroupId()));

		List<ClusterGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ClusterGroup existingClusterGroup = result.get(0);

		Assert.assertEquals(existingClusterGroup, newClusterGroup);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClusterGroup.class,
				ClusterGroup.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("clusterGroupId",
				ServiceTestUtil.nextLong()));

		List<ClusterGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ClusterGroup newClusterGroup = addClusterGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClusterGroup.class,
				ClusterGroup.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"clusterGroupId"));

		Object newClusterGroupId = newClusterGroup.getClusterGroupId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("clusterGroupId",
				new Object[] { newClusterGroupId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingClusterGroupId = result.get(0);

		Assert.assertEquals(existingClusterGroupId, newClusterGroupId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClusterGroup.class,
				ClusterGroup.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"clusterGroupId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("clusterGroupId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ClusterGroup addClusterGroup() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ClusterGroup clusterGroup = _persistence.create(pk);

		clusterGroup.setName(ServiceTestUtil.randomString());

		clusterGroup.setClusterNodeIds(ServiceTestUtil.randomString());

		clusterGroup.setWholeCluster(ServiceTestUtil.randomBoolean());

		_persistence.update(clusterGroup, false);

		return clusterGroup;
	}

	private ClusterGroupPersistence _persistence;
}