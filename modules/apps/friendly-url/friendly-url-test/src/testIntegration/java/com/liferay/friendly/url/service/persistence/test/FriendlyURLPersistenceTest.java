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

package com.liferay.friendly.url.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.friendly.url.exception.NoSuchFriendlyURLException;
import com.liferay.friendly.url.model.FriendlyURL;
import com.liferay.friendly.url.service.FriendlyURLLocalServiceUtil;
import com.liferay.friendly.url.service.persistence.FriendlyURLPersistence;
import com.liferay.friendly.url.service.persistence.FriendlyURLUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class FriendlyURLPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = FriendlyURLUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FriendlyURL> iterator = _friendlyURLs.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FriendlyURL friendlyURL = _persistence.create(pk);

		Assert.assertNotNull(friendlyURL);

		Assert.assertEquals(friendlyURL.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FriendlyURL newFriendlyURL = addFriendlyURL();

		_persistence.remove(newFriendlyURL);

		FriendlyURL existingFriendlyURL = _persistence.fetchByPrimaryKey(newFriendlyURL.getPrimaryKey());

		Assert.assertNull(existingFriendlyURL);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFriendlyURL();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FriendlyURL newFriendlyURL = _persistence.create(pk);

		newFriendlyURL.setUuid(RandomTestUtil.randomString());

		newFriendlyURL.setCompanyId(RandomTestUtil.nextLong());

		newFriendlyURL.setGroupId(RandomTestUtil.nextLong());

		newFriendlyURL.setClassNameId(RandomTestUtil.nextLong());

		newFriendlyURL.setClassPK(RandomTestUtil.nextLong());

		newFriendlyURL.setFriendlyUrl(RandomTestUtil.randomString());

		newFriendlyURL.setMain(RandomTestUtil.randomBoolean());

		_friendlyURLs.add(_persistence.update(newFriendlyURL));

		FriendlyURL existingFriendlyURL = _persistence.findByPrimaryKey(newFriendlyURL.getPrimaryKey());

		Assert.assertEquals(existingFriendlyURL.getUuid(),
			newFriendlyURL.getUuid());
		Assert.assertEquals(existingFriendlyURL.getFriendlyUrlId(),
			newFriendlyURL.getFriendlyUrlId());
		Assert.assertEquals(existingFriendlyURL.getCompanyId(),
			newFriendlyURL.getCompanyId());
		Assert.assertEquals(existingFriendlyURL.getGroupId(),
			newFriendlyURL.getGroupId());
		Assert.assertEquals(existingFriendlyURL.getClassNameId(),
			newFriendlyURL.getClassNameId());
		Assert.assertEquals(existingFriendlyURL.getClassPK(),
			newFriendlyURL.getClassPK());
		Assert.assertEquals(existingFriendlyURL.getFriendlyUrl(),
			newFriendlyURL.getFriendlyUrl());
		Assert.assertEquals(existingFriendlyURL.getMain(),
			newFriendlyURL.getMain());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUUID_G(StringPool.NULL, 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByC_G_C_C() throws Exception {
		_persistence.countByC_G_C_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_G_C_C(0L, 0L, 0L, 0L);
	}

	@Test
	public void testCountByC_G_C_F() throws Exception {
		_persistence.countByC_G_C_F(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			StringPool.BLANK);

		_persistence.countByC_G_C_F(0L, 0L, 0L, StringPool.NULL);

		_persistence.countByC_G_C_F(0L, 0L, 0L, (String)null);
	}

	@Test
	public void testCountByC_G_C_C_F() throws Exception {
		_persistence.countByC_G_C_C_F(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByC_G_C_C_F(0L, 0L, 0L, 0L, StringPool.NULL);

		_persistence.countByC_G_C_C_F(0L, 0L, 0L, 0L, (String)null);
	}

	@Test
	public void testCountByC_G_C_C_M() throws Exception {
		_persistence.countByC_G_C_C_M(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByC_G_C_C_M(0L, 0L, 0L, 0L,
			RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FriendlyURL newFriendlyURL = addFriendlyURL();

		FriendlyURL existingFriendlyURL = _persistence.findByPrimaryKey(newFriendlyURL.getPrimaryKey());

		Assert.assertEquals(existingFriendlyURL, newFriendlyURL);
	}

	@Test(expected = NoSuchFriendlyURLException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<FriendlyURL> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("FriendlyURL", "uuid", true,
			"friendlyUrlId", true, "companyId", true, "groupId", true,
			"classNameId", true, "classPK", true, "friendlyUrl", true, "main",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FriendlyURL newFriendlyURL = addFriendlyURL();

		FriendlyURL existingFriendlyURL = _persistence.fetchByPrimaryKey(newFriendlyURL.getPrimaryKey());

		Assert.assertEquals(existingFriendlyURL, newFriendlyURL);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FriendlyURL missingFriendlyURL = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFriendlyURL);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		FriendlyURL newFriendlyURL1 = addFriendlyURL();
		FriendlyURL newFriendlyURL2 = addFriendlyURL();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURL1.getPrimaryKey());
		primaryKeys.add(newFriendlyURL2.getPrimaryKey());

		Map<Serializable, FriendlyURL> friendlyURLs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, friendlyURLs.size());
		Assert.assertEquals(newFriendlyURL1,
			friendlyURLs.get(newFriendlyURL1.getPrimaryKey()));
		Assert.assertEquals(newFriendlyURL2,
			friendlyURLs.get(newFriendlyURL2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FriendlyURL> friendlyURLs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(friendlyURLs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		FriendlyURL newFriendlyURL = addFriendlyURL();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURL.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FriendlyURL> friendlyURLs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, friendlyURLs.size());
		Assert.assertEquals(newFriendlyURL,
			friendlyURLs.get(newFriendlyURL.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FriendlyURL> friendlyURLs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(friendlyURLs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		FriendlyURL newFriendlyURL = addFriendlyURL();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURL.getPrimaryKey());

		Map<Serializable, FriendlyURL> friendlyURLs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, friendlyURLs.size());
		Assert.assertEquals(newFriendlyURL,
			friendlyURLs.get(newFriendlyURL.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = FriendlyURLLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<FriendlyURL>() {
				@Override
				public void performAction(FriendlyURL friendlyURL) {
					Assert.assertNotNull(friendlyURL);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		FriendlyURL newFriendlyURL = addFriendlyURL();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURL.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("friendlyUrlId",
				newFriendlyURL.getFriendlyUrlId()));

		List<FriendlyURL> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		FriendlyURL existingFriendlyURL = result.get(0);

		Assert.assertEquals(existingFriendlyURL, newFriendlyURL);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURL.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("friendlyUrlId",
				RandomTestUtil.nextLong()));

		List<FriendlyURL> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		FriendlyURL newFriendlyURL = addFriendlyURL();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURL.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"friendlyUrlId"));

		Object newFriendlyUrlId = newFriendlyURL.getFriendlyUrlId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("friendlyUrlId",
				new Object[] { newFriendlyUrlId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFriendlyUrlId = result.get(0);

		Assert.assertEquals(existingFriendlyUrlId, newFriendlyUrlId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURL.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"friendlyUrlId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("friendlyUrlId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		FriendlyURL newFriendlyURL = addFriendlyURL();

		_persistence.clearCache();

		FriendlyURL existingFriendlyURL = _persistence.findByPrimaryKey(newFriendlyURL.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingFriendlyURL.getUuid(),
				ReflectionTestUtil.invoke(existingFriendlyURL,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingFriendlyURL.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingFriendlyURL,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(existingFriendlyURL.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(existingFriendlyURL,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingFriendlyURL.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingFriendlyURL,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingFriendlyURL.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(existingFriendlyURL,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(existingFriendlyURL.getFriendlyUrl(),
				ReflectionTestUtil.invoke(existingFriendlyURL,
					"getOriginalFriendlyUrl", new Class<?>[0])));

		Assert.assertEquals(Long.valueOf(existingFriendlyURL.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(existingFriendlyURL,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingFriendlyURL.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingFriendlyURL,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingFriendlyURL.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(existingFriendlyURL,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingFriendlyURL.getClassPK()),
			ReflectionTestUtil.<Long>invoke(existingFriendlyURL,
				"getOriginalClassPK", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(existingFriendlyURL.getFriendlyUrl(),
				ReflectionTestUtil.invoke(existingFriendlyURL,
					"getOriginalFriendlyUrl", new Class<?>[0])));

		Assert.assertEquals(Long.valueOf(existingFriendlyURL.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(existingFriendlyURL,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingFriendlyURL.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingFriendlyURL,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingFriendlyURL.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(existingFriendlyURL,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingFriendlyURL.getClassPK()),
			ReflectionTestUtil.<Long>invoke(existingFriendlyURL,
				"getOriginalClassPK", new Class<?>[0]));
		Assert.assertEquals(Boolean.valueOf(existingFriendlyURL.getMain()),
			ReflectionTestUtil.<Boolean>invoke(existingFriendlyURL,
				"getOriginalMain", new Class<?>[0]));
	}

	protected FriendlyURL addFriendlyURL() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FriendlyURL friendlyURL = _persistence.create(pk);

		friendlyURL.setUuid(RandomTestUtil.randomString());

		friendlyURL.setCompanyId(RandomTestUtil.nextLong());

		friendlyURL.setGroupId(RandomTestUtil.nextLong());

		friendlyURL.setClassNameId(RandomTestUtil.nextLong());

		friendlyURL.setClassPK(RandomTestUtil.nextLong());

		friendlyURL.setFriendlyUrl(RandomTestUtil.randomString());

		friendlyURL.setMain(RandomTestUtil.randomBoolean());

		_friendlyURLs.add(_persistence.update(friendlyURL));

		return friendlyURL;
	}

	private List<FriendlyURL> _friendlyURLs = new ArrayList<FriendlyURL>();
	private FriendlyURLPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}