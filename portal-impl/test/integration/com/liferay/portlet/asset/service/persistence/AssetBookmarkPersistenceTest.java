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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.asset.NoSuchBookmarkException;
import com.liferay.portlet.asset.model.AssetBookmark;
import com.liferay.portlet.asset.model.impl.AssetBookmarkModelImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class AssetBookmarkPersistenceTest {
	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetBookmark assetBookmark = _persistence.create(pk);

		Assert.assertNotNull(assetBookmark);

		Assert.assertEquals(assetBookmark.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetBookmark newAssetBookmark = addAssetBookmark();

		_persistence.remove(newAssetBookmark);

		AssetBookmark existingAssetBookmark = _persistence.fetchByPrimaryKey(newAssetBookmark.getPrimaryKey());

		Assert.assertNull(existingAssetBookmark);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetBookmark();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetBookmark newAssetBookmark = _persistence.create(pk);

		newAssetBookmark.setUuid(ServiceTestUtil.randomString());

		newAssetBookmark.setUserId(ServiceTestUtil.nextLong());

		newAssetBookmark.setClassNameId(ServiceTestUtil.nextLong());

		newAssetBookmark.setClassPK(ServiceTestUtil.nextLong());

		_persistence.update(newAssetBookmark);

		AssetBookmark existingAssetBookmark = _persistence.findByPrimaryKey(newAssetBookmark.getPrimaryKey());

		Assert.assertEquals(existingAssetBookmark.getUuid(),
			newAssetBookmark.getUuid());
		Assert.assertEquals(existingAssetBookmark.getBookmarkId(),
			newAssetBookmark.getBookmarkId());
		Assert.assertEquals(existingAssetBookmark.getUserId(),
			newAssetBookmark.getUserId());
		Assert.assertEquals(existingAssetBookmark.getClassNameId(),
			newAssetBookmark.getClassNameId());
		Assert.assertEquals(existingAssetBookmark.getClassPK(),
			newAssetBookmark.getClassPK());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetBookmark newAssetBookmark = addAssetBookmark();

		AssetBookmark existingAssetBookmark = _persistence.findByPrimaryKey(newAssetBookmark.getPrimaryKey());

		Assert.assertEquals(existingAssetBookmark, newAssetBookmark);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchBookmarkException");
		}
		catch (NoSuchBookmarkException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetBookmark newAssetBookmark = addAssetBookmark();

		AssetBookmark existingAssetBookmark = _persistence.fetchByPrimaryKey(newAssetBookmark.getPrimaryKey());

		Assert.assertEquals(existingAssetBookmark, newAssetBookmark);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetBookmark missingAssetBookmark = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetBookmark);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetBookmark newAssetBookmark = addAssetBookmark();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetBookmark.class,
				AssetBookmark.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("bookmarkId",
				newAssetBookmark.getBookmarkId()));

		List<AssetBookmark> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetBookmark existingAssetBookmark = result.get(0);

		Assert.assertEquals(existingAssetBookmark, newAssetBookmark);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetBookmark.class,
				AssetBookmark.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("bookmarkId",
				ServiceTestUtil.nextLong()));

		List<AssetBookmark> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetBookmark newAssetBookmark = addAssetBookmark();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetBookmark.class,
				AssetBookmark.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("bookmarkId"));

		Object newBookmarkId = newAssetBookmark.getBookmarkId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("bookmarkId",
				new Object[] { newBookmarkId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingBookmarkId = result.get(0);

		Assert.assertEquals(existingBookmarkId, newBookmarkId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetBookmark.class,
				AssetBookmark.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("bookmarkId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("bookmarkId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AssetBookmark newAssetBookmark = addAssetBookmark();

		_persistence.clearCache();

		AssetBookmarkModelImpl existingAssetBookmarkModelImpl = (AssetBookmarkModelImpl)_persistence.findByPrimaryKey(newAssetBookmark.getPrimaryKey());

		Assert.assertEquals(existingAssetBookmarkModelImpl.getUserId(),
			existingAssetBookmarkModelImpl.getOriginalUserId());
		Assert.assertEquals(existingAssetBookmarkModelImpl.getClassPK(),
			existingAssetBookmarkModelImpl.getOriginalClassPK());

		Assert.assertEquals(existingAssetBookmarkModelImpl.getUserId(),
			existingAssetBookmarkModelImpl.getOriginalUserId());
		Assert.assertEquals(existingAssetBookmarkModelImpl.getClassNameId(),
			existingAssetBookmarkModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingAssetBookmarkModelImpl.getClassPK(),
			existingAssetBookmarkModelImpl.getOriginalClassPK());
	}

	protected AssetBookmark addAssetBookmark() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetBookmark assetBookmark = _persistence.create(pk);

		assetBookmark.setUuid(ServiceTestUtil.randomString());

		assetBookmark.setUserId(ServiceTestUtil.nextLong());

		assetBookmark.setClassNameId(ServiceTestUtil.nextLong());

		assetBookmark.setClassPK(ServiceTestUtil.nextLong());

		_persistence.update(assetBookmark);

		return assetBookmark;
	}

	private static Log _log = LogFactoryUtil.getLog(AssetBookmarkPersistenceTest.class);
	private AssetBookmarkPersistence _persistence = (AssetBookmarkPersistence)PortalBeanLocatorUtil.locate(AssetBookmarkPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}