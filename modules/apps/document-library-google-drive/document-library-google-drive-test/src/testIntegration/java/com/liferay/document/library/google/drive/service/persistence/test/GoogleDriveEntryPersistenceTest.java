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

package com.liferay.document.library.google.drive.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.document.library.google.drive.exception.NoSuchEntryException;
import com.liferay.document.library.google.drive.model.GoogleDriveEntry;
import com.liferay.document.library.google.drive.service.GoogleDriveEntryLocalServiceUtil;
import com.liferay.document.library.google.drive.service.persistence.GoogleDriveEntryPersistence;
import com.liferay.document.library.google.drive.service.persistence.GoogleDriveEntryUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

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
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class GoogleDriveEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.document.library.google.drive.service"));

	@Before
	public void setUp() {
		_persistence = GoogleDriveEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<GoogleDriveEntry> iterator = _googleDriveEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		GoogleDriveEntry googleDriveEntry = _persistence.create(pk);

		Assert.assertNotNull(googleDriveEntry);

		Assert.assertEquals(googleDriveEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		GoogleDriveEntry newGoogleDriveEntry = addGoogleDriveEntry();

		_persistence.remove(newGoogleDriveEntry);

		GoogleDriveEntry existingGoogleDriveEntry = _persistence.fetchByPrimaryKey(newGoogleDriveEntry.getPrimaryKey());

		Assert.assertNull(existingGoogleDriveEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addGoogleDriveEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		GoogleDriveEntry newGoogleDriveEntry = _persistence.create(pk);

		newGoogleDriveEntry.setGroupId(RandomTestUtil.nextLong());

		newGoogleDriveEntry.setCompanyId(RandomTestUtil.nextLong());

		newGoogleDriveEntry.setUserId(RandomTestUtil.nextLong());

		newGoogleDriveEntry.setUserName(RandomTestUtil.randomString());

		newGoogleDriveEntry.setCreateDate(RandomTestUtil.nextDate());

		newGoogleDriveEntry.setModifiedDate(RandomTestUtil.nextDate());

		newGoogleDriveEntry.setFileEntryId(RandomTestUtil.nextLong());

		newGoogleDriveEntry.setGoogleDriveId(RandomTestUtil.randomString());

		_googleDriveEntries.add(_persistence.update(newGoogleDriveEntry));

		GoogleDriveEntry existingGoogleDriveEntry = _persistence.findByPrimaryKey(newGoogleDriveEntry.getPrimaryKey());

		Assert.assertEquals(existingGoogleDriveEntry.getEntryId(),
			newGoogleDriveEntry.getEntryId());
		Assert.assertEquals(existingGoogleDriveEntry.getGroupId(),
			newGoogleDriveEntry.getGroupId());
		Assert.assertEquals(existingGoogleDriveEntry.getCompanyId(),
			newGoogleDriveEntry.getCompanyId());
		Assert.assertEquals(existingGoogleDriveEntry.getUserId(),
			newGoogleDriveEntry.getUserId());
		Assert.assertEquals(existingGoogleDriveEntry.getUserName(),
			newGoogleDriveEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingGoogleDriveEntry.getCreateDate()),
			Time.getShortTimestamp(newGoogleDriveEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingGoogleDriveEntry.getModifiedDate()),
			Time.getShortTimestamp(newGoogleDriveEntry.getModifiedDate()));
		Assert.assertEquals(existingGoogleDriveEntry.getFileEntryId(),
			newGoogleDriveEntry.getFileEntryId());
		Assert.assertEquals(existingGoogleDriveEntry.getGoogleDriveId(),
			newGoogleDriveEntry.getGoogleDriveId());
	}

	@Test
	public void testCountByG_F() throws Exception {
		_persistence.countByG_F(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_F(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		GoogleDriveEntry newGoogleDriveEntry = addGoogleDriveEntry();

		GoogleDriveEntry existingGoogleDriveEntry = _persistence.findByPrimaryKey(newGoogleDriveEntry.getPrimaryKey());

		Assert.assertEquals(existingGoogleDriveEntry, newGoogleDriveEntry);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<GoogleDriveEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("GoogleDriveEntry",
			"entryId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"fileEntryId", true, "googleDriveId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		GoogleDriveEntry newGoogleDriveEntry = addGoogleDriveEntry();

		GoogleDriveEntry existingGoogleDriveEntry = _persistence.fetchByPrimaryKey(newGoogleDriveEntry.getPrimaryKey());

		Assert.assertEquals(existingGoogleDriveEntry, newGoogleDriveEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		GoogleDriveEntry missingGoogleDriveEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingGoogleDriveEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		GoogleDriveEntry newGoogleDriveEntry1 = addGoogleDriveEntry();
		GoogleDriveEntry newGoogleDriveEntry2 = addGoogleDriveEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGoogleDriveEntry1.getPrimaryKey());
		primaryKeys.add(newGoogleDriveEntry2.getPrimaryKey());

		Map<Serializable, GoogleDriveEntry> googleDriveEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, googleDriveEntries.size());
		Assert.assertEquals(newGoogleDriveEntry1,
			googleDriveEntries.get(newGoogleDriveEntry1.getPrimaryKey()));
		Assert.assertEquals(newGoogleDriveEntry2,
			googleDriveEntries.get(newGoogleDriveEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, GoogleDriveEntry> googleDriveEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(googleDriveEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		GoogleDriveEntry newGoogleDriveEntry = addGoogleDriveEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGoogleDriveEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, GoogleDriveEntry> googleDriveEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, googleDriveEntries.size());
		Assert.assertEquals(newGoogleDriveEntry,
			googleDriveEntries.get(newGoogleDriveEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, GoogleDriveEntry> googleDriveEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(googleDriveEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		GoogleDriveEntry newGoogleDriveEntry = addGoogleDriveEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGoogleDriveEntry.getPrimaryKey());

		Map<Serializable, GoogleDriveEntry> googleDriveEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, googleDriveEntries.size());
		Assert.assertEquals(newGoogleDriveEntry,
			googleDriveEntries.get(newGoogleDriveEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = GoogleDriveEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<GoogleDriveEntry>() {
				@Override
				public void performAction(GoogleDriveEntry googleDriveEntry) {
					Assert.assertNotNull(googleDriveEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		GoogleDriveEntry newGoogleDriveEntry = addGoogleDriveEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(GoogleDriveEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newGoogleDriveEntry.getEntryId()));

		List<GoogleDriveEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		GoogleDriveEntry existingGoogleDriveEntry = result.get(0);

		Assert.assertEquals(existingGoogleDriveEntry, newGoogleDriveEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(GoogleDriveEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				RandomTestUtil.nextLong()));

		List<GoogleDriveEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		GoogleDriveEntry newGoogleDriveEntry = addGoogleDriveEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(GoogleDriveEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		Object newEntryId = newGoogleDriveEntry.getEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { newEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		Assert.assertEquals(existingEntryId, newEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(GoogleDriveEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		GoogleDriveEntry newGoogleDriveEntry = addGoogleDriveEntry();

		_persistence.clearCache();

		GoogleDriveEntry existingGoogleDriveEntry = _persistence.findByPrimaryKey(newGoogleDriveEntry.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingGoogleDriveEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingGoogleDriveEntry,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingGoogleDriveEntry.getFileEntryId()),
			ReflectionTestUtil.<Long>invoke(existingGoogleDriveEntry,
				"getOriginalFileEntryId", new Class<?>[0]));
	}

	protected GoogleDriveEntry addGoogleDriveEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		GoogleDriveEntry googleDriveEntry = _persistence.create(pk);

		googleDriveEntry.setGroupId(RandomTestUtil.nextLong());

		googleDriveEntry.setCompanyId(RandomTestUtil.nextLong());

		googleDriveEntry.setUserId(RandomTestUtil.nextLong());

		googleDriveEntry.setUserName(RandomTestUtil.randomString());

		googleDriveEntry.setCreateDate(RandomTestUtil.nextDate());

		googleDriveEntry.setModifiedDate(RandomTestUtil.nextDate());

		googleDriveEntry.setFileEntryId(RandomTestUtil.nextLong());

		googleDriveEntry.setGoogleDriveId(RandomTestUtil.randomString());

		_googleDriveEntries.add(_persistence.update(googleDriveEntry));

		return googleDriveEntry;
	}

	private List<GoogleDriveEntry> _googleDriveEntries = new ArrayList<GoogleDriveEntry>();
	private GoogleDriveEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}