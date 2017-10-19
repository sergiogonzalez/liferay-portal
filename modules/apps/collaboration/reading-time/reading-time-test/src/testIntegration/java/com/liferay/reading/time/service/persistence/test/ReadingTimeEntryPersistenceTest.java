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

package com.liferay.reading.time.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import com.liferay.reading.time.exception.NoSuchReadingTimeEntryException;
import com.liferay.reading.time.model.ReadingTimeEntry;
import com.liferay.reading.time.service.ReadingTimeEntryLocalServiceUtil;
import com.liferay.reading.time.service.persistence.ReadingTimeEntryPersistence;
import com.liferay.reading.time.service.persistence.ReadingTimeEntryUtil;

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
public class ReadingTimeEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.reading.time.service"));

	@Before
	public void setUp() {
		_persistence = ReadingTimeEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ReadingTimeEntry> iterator = _readingTimeEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ReadingTimeEntry readingTimeEntry = _persistence.create(pk);

		Assert.assertNotNull(readingTimeEntry);

		Assert.assertEquals(readingTimeEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ReadingTimeEntry newReadingTimeEntry = addReadingTimeEntry();

		_persistence.remove(newReadingTimeEntry);

		ReadingTimeEntry existingReadingTimeEntry = _persistence.fetchByPrimaryKey(newReadingTimeEntry.getPrimaryKey());

		Assert.assertNull(existingReadingTimeEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addReadingTimeEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ReadingTimeEntry newReadingTimeEntry = _persistence.create(pk);

		newReadingTimeEntry.setUuid(RandomTestUtil.randomString());

		newReadingTimeEntry.setCompanyId(RandomTestUtil.nextLong());

		newReadingTimeEntry.setCreateDate(RandomTestUtil.nextDate());

		newReadingTimeEntry.setModifiedDate(RandomTestUtil.nextDate());

		newReadingTimeEntry.setClassNameId(RandomTestUtil.nextLong());

		newReadingTimeEntry.setClassPK(RandomTestUtil.nextLong());

		newReadingTimeEntry.setReadingTimeInSeconds(RandomTestUtil.nextLong());

		_readingTimeEntries.add(_persistence.update(newReadingTimeEntry));

		ReadingTimeEntry existingReadingTimeEntry = _persistence.findByPrimaryKey(newReadingTimeEntry.getPrimaryKey());

		Assert.assertEquals(existingReadingTimeEntry.getUuid(),
			newReadingTimeEntry.getUuid());
		Assert.assertEquals(existingReadingTimeEntry.getEntryId(),
			newReadingTimeEntry.getEntryId());
		Assert.assertEquals(existingReadingTimeEntry.getCompanyId(),
			newReadingTimeEntry.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingReadingTimeEntry.getCreateDate()),
			Time.getShortTimestamp(newReadingTimeEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingReadingTimeEntry.getModifiedDate()),
			Time.getShortTimestamp(newReadingTimeEntry.getModifiedDate()));
		Assert.assertEquals(existingReadingTimeEntry.getClassNameId(),
			newReadingTimeEntry.getClassNameId());
		Assert.assertEquals(existingReadingTimeEntry.getClassPK(),
			newReadingTimeEntry.getClassPK());
		Assert.assertEquals(existingReadingTimeEntry.getReadingTimeInSeconds(),
			newReadingTimeEntry.getReadingTimeInSeconds());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ReadingTimeEntry newReadingTimeEntry = addReadingTimeEntry();

		ReadingTimeEntry existingReadingTimeEntry = _persistence.findByPrimaryKey(newReadingTimeEntry.getPrimaryKey());

		Assert.assertEquals(existingReadingTimeEntry, newReadingTimeEntry);
	}

	@Test(expected = NoSuchReadingTimeEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ReadingTimeEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ReadingTimeEntry", "uuid",
			true, "entryId", true, "companyId", true, "createDate", true,
			"modifiedDate", true, "classNameId", true, "classPK", true,
			"readingTimeInSeconds", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ReadingTimeEntry newReadingTimeEntry = addReadingTimeEntry();

		ReadingTimeEntry existingReadingTimeEntry = _persistence.fetchByPrimaryKey(newReadingTimeEntry.getPrimaryKey());

		Assert.assertEquals(existingReadingTimeEntry, newReadingTimeEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ReadingTimeEntry missingReadingTimeEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingReadingTimeEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ReadingTimeEntry newReadingTimeEntry1 = addReadingTimeEntry();
		ReadingTimeEntry newReadingTimeEntry2 = addReadingTimeEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newReadingTimeEntry1.getPrimaryKey());
		primaryKeys.add(newReadingTimeEntry2.getPrimaryKey());

		Map<Serializable, ReadingTimeEntry> readingTimeEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, readingTimeEntries.size());
		Assert.assertEquals(newReadingTimeEntry1,
			readingTimeEntries.get(newReadingTimeEntry1.getPrimaryKey()));
		Assert.assertEquals(newReadingTimeEntry2,
			readingTimeEntries.get(newReadingTimeEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ReadingTimeEntry> readingTimeEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(readingTimeEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ReadingTimeEntry newReadingTimeEntry = addReadingTimeEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newReadingTimeEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ReadingTimeEntry> readingTimeEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, readingTimeEntries.size());
		Assert.assertEquals(newReadingTimeEntry,
			readingTimeEntries.get(newReadingTimeEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ReadingTimeEntry> readingTimeEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(readingTimeEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ReadingTimeEntry newReadingTimeEntry = addReadingTimeEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newReadingTimeEntry.getPrimaryKey());

		Map<Serializable, ReadingTimeEntry> readingTimeEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, readingTimeEntries.size());
		Assert.assertEquals(newReadingTimeEntry,
			readingTimeEntries.get(newReadingTimeEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ReadingTimeEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<ReadingTimeEntry>() {
				@Override
				public void performAction(ReadingTimeEntry readingTimeEntry) {
					Assert.assertNotNull(readingTimeEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ReadingTimeEntry newReadingTimeEntry = addReadingTimeEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ReadingTimeEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newReadingTimeEntry.getEntryId()));

		List<ReadingTimeEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ReadingTimeEntry existingReadingTimeEntry = result.get(0);

		Assert.assertEquals(existingReadingTimeEntry, newReadingTimeEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ReadingTimeEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				RandomTestUtil.nextLong()));

		List<ReadingTimeEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ReadingTimeEntry newReadingTimeEntry = addReadingTimeEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ReadingTimeEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		Object newEntryId = newReadingTimeEntry.getEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { newEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		Assert.assertEquals(existingEntryId, newEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ReadingTimeEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ReadingTimeEntry newReadingTimeEntry = addReadingTimeEntry();

		_persistence.clearCache();

		ReadingTimeEntry existingReadingTimeEntry = _persistence.findByPrimaryKey(newReadingTimeEntry.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingReadingTimeEntry.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(existingReadingTimeEntry,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingReadingTimeEntry.getClassPK()),
			ReflectionTestUtil.<Long>invoke(existingReadingTimeEntry,
				"getOriginalClassPK", new Class<?>[0]));
	}

	protected ReadingTimeEntry addReadingTimeEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ReadingTimeEntry readingTimeEntry = _persistence.create(pk);

		readingTimeEntry.setUuid(RandomTestUtil.randomString());

		readingTimeEntry.setCompanyId(RandomTestUtil.nextLong());

		readingTimeEntry.setCreateDate(RandomTestUtil.nextDate());

		readingTimeEntry.setModifiedDate(RandomTestUtil.nextDate());

		readingTimeEntry.setClassNameId(RandomTestUtil.nextLong());

		readingTimeEntry.setClassPK(RandomTestUtil.nextLong());

		readingTimeEntry.setReadingTimeInSeconds(RandomTestUtil.nextLong());

		_readingTimeEntries.add(_persistence.update(readingTimeEntry));

		return readingTimeEntry;
	}

	private List<ReadingTimeEntry> _readingTimeEntries = new ArrayList<ReadingTimeEntry>();
	private ReadingTimeEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}