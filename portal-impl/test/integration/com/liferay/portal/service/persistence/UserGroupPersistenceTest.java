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

import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.UserGroupModelImpl;
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
public class UserGroupPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (UserGroupPersistence)PortalBeanLocatorUtil.locate(UserGroupPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		UserGroup userGroup = _persistence.create(pk);

		Assert.assertNotNull(userGroup);

		Assert.assertEquals(userGroup.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		UserGroup newUserGroup = addUserGroup();

		_persistence.remove(newUserGroup);

		UserGroup existingUserGroup = _persistence.fetchByPrimaryKey(newUserGroup.getPrimaryKey());

		Assert.assertNull(existingUserGroup);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addUserGroup();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		UserGroup newUserGroup = _persistence.create(pk);

		newUserGroup.setCompanyId(ServiceTestUtil.nextLong());

		newUserGroup.setParentUserGroupId(ServiceTestUtil.nextLong());

		newUserGroup.setName(ServiceTestUtil.randomString());

		newUserGroup.setDescription(ServiceTestUtil.randomString());

		newUserGroup.setAddedByLDAPImport(ServiceTestUtil.randomBoolean());

		_persistence.update(newUserGroup, false);

		UserGroup existingUserGroup = _persistence.findByPrimaryKey(newUserGroup.getPrimaryKey());

		Assert.assertEquals(existingUserGroup.getUserGroupId(),
			newUserGroup.getUserGroupId());
		Assert.assertEquals(existingUserGroup.getCompanyId(),
			newUserGroup.getCompanyId());
		Assert.assertEquals(existingUserGroup.getParentUserGroupId(),
			newUserGroup.getParentUserGroupId());
		Assert.assertEquals(existingUserGroup.getName(), newUserGroup.getName());
		Assert.assertEquals(existingUserGroup.getDescription(),
			newUserGroup.getDescription());
		Assert.assertEquals(existingUserGroup.getAddedByLDAPImport(),
			newUserGroup.getAddedByLDAPImport());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		UserGroup newUserGroup = addUserGroup();

		UserGroup existingUserGroup = _persistence.findByPrimaryKey(newUserGroup.getPrimaryKey());

		Assert.assertEquals(existingUserGroup, newUserGroup);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchUserGroupException");
		}
		catch (NoSuchUserGroupException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserGroup newUserGroup = addUserGroup();

		UserGroup existingUserGroup = _persistence.fetchByPrimaryKey(newUserGroup.getPrimaryKey());

		Assert.assertEquals(existingUserGroup, newUserGroup);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		UserGroup missingUserGroup = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingUserGroup);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserGroup newUserGroup = addUserGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroup.class,
				UserGroup.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userGroupId",
				newUserGroup.getUserGroupId()));

		List<UserGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		UserGroup existingUserGroup = result.get(0);

		Assert.assertEquals(existingUserGroup, newUserGroup);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroup.class,
				UserGroup.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userGroupId",
				ServiceTestUtil.nextLong()));

		List<UserGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		UserGroup newUserGroup = addUserGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroup.class,
				UserGroup.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("userGroupId"));

		Object newUserGroupId = newUserGroup.getUserGroupId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("userGroupId",
				new Object[] { newUserGroupId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingUserGroupId = result.get(0);

		Assert.assertEquals(existingUserGroupId, newUserGroupId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroup.class,
				UserGroup.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("userGroupId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("userGroupId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		UserGroup newUserGroup = addUserGroup();

		_persistence.clearCache();

		UserGroupModelImpl existingUserGroupModelImpl = (UserGroupModelImpl)_persistence.findByPrimaryKey(newUserGroup.getPrimaryKey());

		Assert.assertEquals(existingUserGroupModelImpl.getCompanyId(),
			existingUserGroupModelImpl.getOriginalCompanyId());
		Assert.assertTrue(Validator.equals(
				existingUserGroupModelImpl.getName(),
				existingUserGroupModelImpl.getOriginalName()));
	}

	protected UserGroup addUserGroup() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		UserGroup userGroup = _persistence.create(pk);

		userGroup.setCompanyId(ServiceTestUtil.nextLong());

		userGroup.setParentUserGroupId(ServiceTestUtil.nextLong());

		userGroup.setName(ServiceTestUtil.randomString());

		userGroup.setDescription(ServiceTestUtil.randomString());

		userGroup.setAddedByLDAPImport(ServiceTestUtil.randomBoolean());

		_persistence.update(userGroup, false);

		return userGroup;
	}

	private UserGroupPersistence _persistence;
}