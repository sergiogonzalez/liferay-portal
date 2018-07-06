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

package com.liferay.document.library.google.drive.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.google.drive.exception.NoSuchEntryException;
import com.liferay.document.library.google.drive.model.GoogleDriveEntry;
import com.liferay.document.library.google.drive.model.impl.GoogleDriveEntryImpl;
import com.liferay.document.library.google.drive.model.impl.GoogleDriveEntryModelImpl;
import com.liferay.document.library.google.drive.service.persistence.GoogleDriveEntryPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the google drive entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GoogleDriveEntryPersistence
 * @see com.liferay.document.library.google.drive.service.persistence.GoogleDriveEntryUtil
 * @generated
 */
@ProviderType
public class GoogleDriveEntryPersistenceImpl extends BasePersistenceImpl<GoogleDriveEntry>
	implements GoogleDriveEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link GoogleDriveEntryUtil} to access the google drive entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = GoogleDriveEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(GoogleDriveEntryModelImpl.ENTITY_CACHE_ENABLED,
			GoogleDriveEntryModelImpl.FINDER_CACHE_ENABLED,
			GoogleDriveEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(GoogleDriveEntryModelImpl.ENTITY_CACHE_ENABLED,
			GoogleDriveEntryModelImpl.FINDER_CACHE_ENABLED,
			GoogleDriveEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(GoogleDriveEntryModelImpl.ENTITY_CACHE_ENABLED,
			GoogleDriveEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F = new FinderPath(GoogleDriveEntryModelImpl.ENTITY_CACHE_ENABLED,
			GoogleDriveEntryModelImpl.FINDER_CACHE_ENABLED,
			GoogleDriveEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_F",
			new String[] { Long.class.getName(), Long.class.getName() },
			GoogleDriveEntryModelImpl.GROUPID_COLUMN_BITMASK |
			GoogleDriveEntryModelImpl.FILEENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(GoogleDriveEntryModelImpl.ENTITY_CACHE_ENABLED,
			GoogleDriveEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the google drive entry where groupId = &#63; and fileEntryId = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param fileEntryId the file entry ID
	 * @return the matching google drive entry
	 * @throws NoSuchEntryException if a matching google drive entry could not be found
	 */
	@Override
	public GoogleDriveEntry findByG_F(long groupId, long fileEntryId)
		throws NoSuchEntryException {
		GoogleDriveEntry googleDriveEntry = fetchByG_F(groupId, fileEntryId);

		if (googleDriveEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", fileEntryId=");
			msg.append(fileEntryId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return googleDriveEntry;
	}

	/**
	 * Returns the google drive entry where groupId = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fileEntryId the file entry ID
	 * @return the matching google drive entry, or <code>null</code> if a matching google drive entry could not be found
	 */
	@Override
	public GoogleDriveEntry fetchByG_F(long groupId, long fileEntryId) {
		return fetchByG_F(groupId, fileEntryId, true);
	}

	/**
	 * Returns the google drive entry where groupId = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fileEntryId the file entry ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching google drive entry, or <code>null</code> if a matching google drive entry could not be found
	 */
	@Override
	public GoogleDriveEntry fetchByG_F(long groupId, long fileEntryId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, fileEntryId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_F,
					finderArgs, this);
		}

		if (result instanceof GoogleDriveEntry) {
			GoogleDriveEntry googleDriveEntry = (GoogleDriveEntry)result;

			if ((groupId != googleDriveEntry.getGroupId()) ||
					(fileEntryId != googleDriveEntry.getFileEntryId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_GOOGLEDRIVEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FILEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(fileEntryId);

				List<GoogleDriveEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_F, finderArgs,
						list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"GoogleDriveEntryPersistenceImpl.fetchByG_F(long, long, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					GoogleDriveEntry googleDriveEntry = list.get(0);

					result = googleDriveEntry;

					cacheResult(googleDriveEntry);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (GoogleDriveEntry)result;
		}
	}

	/**
	 * Removes the google drive entry where groupId = &#63; and fileEntryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fileEntryId the file entry ID
	 * @return the google drive entry that was removed
	 */
	@Override
	public GoogleDriveEntry removeByG_F(long groupId, long fileEntryId)
		throws NoSuchEntryException {
		GoogleDriveEntry googleDriveEntry = findByG_F(groupId, fileEntryId);

		return remove(googleDriveEntry);
	}

	/**
	 * Returns the number of google drive entries where groupId = &#63; and fileEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fileEntryId the file entry ID
	 * @return the number of matching google drive entries
	 */
	@Override
	public int countByG_F(long groupId, long fileEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F;

		Object[] finderArgs = new Object[] { groupId, fileEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GOOGLEDRIVEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FILEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(fileEntryId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "googleDriveEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FILEENTRYID_2 = "googleDriveEntry.fileEntryId = ?";

	public GoogleDriveEntryPersistenceImpl() {
		setModelClass(GoogleDriveEntry.class);
	}

	/**
	 * Caches the google drive entry in the entity cache if it is enabled.
	 *
	 * @param googleDriveEntry the google drive entry
	 */
	@Override
	public void cacheResult(GoogleDriveEntry googleDriveEntry) {
		entityCache.putResult(GoogleDriveEntryModelImpl.ENTITY_CACHE_ENABLED,
			GoogleDriveEntryImpl.class, googleDriveEntry.getPrimaryKey(),
			googleDriveEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_F,
			new Object[] {
				googleDriveEntry.getGroupId(), googleDriveEntry.getFileEntryId()
			}, googleDriveEntry);

		googleDriveEntry.resetOriginalValues();
	}

	/**
	 * Caches the google drive entries in the entity cache if it is enabled.
	 *
	 * @param googleDriveEntries the google drive entries
	 */
	@Override
	public void cacheResult(List<GoogleDriveEntry> googleDriveEntries) {
		for (GoogleDriveEntry googleDriveEntry : googleDriveEntries) {
			if (entityCache.getResult(
						GoogleDriveEntryModelImpl.ENTITY_CACHE_ENABLED,
						GoogleDriveEntryImpl.class,
						googleDriveEntry.getPrimaryKey()) == null) {
				cacheResult(googleDriveEntry);
			}
			else {
				googleDriveEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all google drive entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(GoogleDriveEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the google drive entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(GoogleDriveEntry googleDriveEntry) {
		entityCache.removeResult(GoogleDriveEntryModelImpl.ENTITY_CACHE_ENABLED,
			GoogleDriveEntryImpl.class, googleDriveEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((GoogleDriveEntryModelImpl)googleDriveEntry,
			true);
	}

	@Override
	public void clearCache(List<GoogleDriveEntry> googleDriveEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (GoogleDriveEntry googleDriveEntry : googleDriveEntries) {
			entityCache.removeResult(GoogleDriveEntryModelImpl.ENTITY_CACHE_ENABLED,
				GoogleDriveEntryImpl.class, googleDriveEntry.getPrimaryKey());

			clearUniqueFindersCache((GoogleDriveEntryModelImpl)googleDriveEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		GoogleDriveEntryModelImpl googleDriveEntryModelImpl) {
		Object[] args = new Object[] {
				googleDriveEntryModelImpl.getGroupId(),
				googleDriveEntryModelImpl.getFileEntryId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_F, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_F, args,
			googleDriveEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		GoogleDriveEntryModelImpl googleDriveEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					googleDriveEntryModelImpl.getGroupId(),
					googleDriveEntryModelImpl.getFileEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F, args);
		}

		if ((googleDriveEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_F.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					googleDriveEntryModelImpl.getOriginalGroupId(),
					googleDriveEntryModelImpl.getOriginalFileEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F, args);
		}
	}

	/**
	 * Creates a new google drive entry with the primary key. Does not add the google drive entry to the database.
	 *
	 * @param entryId the primary key for the new google drive entry
	 * @return the new google drive entry
	 */
	@Override
	public GoogleDriveEntry create(long entryId) {
		GoogleDriveEntry googleDriveEntry = new GoogleDriveEntryImpl();

		googleDriveEntry.setNew(true);
		googleDriveEntry.setPrimaryKey(entryId);

		googleDriveEntry.setCompanyId(companyProvider.getCompanyId());

		return googleDriveEntry;
	}

	/**
	 * Removes the google drive entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the google drive entry
	 * @return the google drive entry that was removed
	 * @throws NoSuchEntryException if a google drive entry with the primary key could not be found
	 */
	@Override
	public GoogleDriveEntry remove(long entryId) throws NoSuchEntryException {
		return remove((Serializable)entryId);
	}

	/**
	 * Removes the google drive entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the google drive entry
	 * @return the google drive entry that was removed
	 * @throws NoSuchEntryException if a google drive entry with the primary key could not be found
	 */
	@Override
	public GoogleDriveEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {
		Session session = null;

		try {
			session = openSession();

			GoogleDriveEntry googleDriveEntry = (GoogleDriveEntry)session.get(GoogleDriveEntryImpl.class,
					primaryKey);

			if (googleDriveEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(googleDriveEntry);
		}
		catch (NoSuchEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected GoogleDriveEntry removeImpl(GoogleDriveEntry googleDriveEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(googleDriveEntry)) {
				googleDriveEntry = (GoogleDriveEntry)session.get(GoogleDriveEntryImpl.class,
						googleDriveEntry.getPrimaryKeyObj());
			}

			if (googleDriveEntry != null) {
				session.delete(googleDriveEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (googleDriveEntry != null) {
			clearCache(googleDriveEntry);
		}

		return googleDriveEntry;
	}

	@Override
	public GoogleDriveEntry updateImpl(GoogleDriveEntry googleDriveEntry) {
		boolean isNew = googleDriveEntry.isNew();

		if (!(googleDriveEntry instanceof GoogleDriveEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(googleDriveEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(googleDriveEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in googleDriveEntry proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom GoogleDriveEntry implementation " +
				googleDriveEntry.getClass());
		}

		GoogleDriveEntryModelImpl googleDriveEntryModelImpl = (GoogleDriveEntryModelImpl)googleDriveEntry;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (googleDriveEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				googleDriveEntry.setCreateDate(now);
			}
			else {
				googleDriveEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!googleDriveEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				googleDriveEntry.setModifiedDate(now);
			}
			else {
				googleDriveEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (googleDriveEntry.isNew()) {
				session.save(googleDriveEntry);

				googleDriveEntry.setNew(false);
			}
			else {
				googleDriveEntry = (GoogleDriveEntry)session.merge(googleDriveEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!GoogleDriveEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(GoogleDriveEntryModelImpl.ENTITY_CACHE_ENABLED,
			GoogleDriveEntryImpl.class, googleDriveEntry.getPrimaryKey(),
			googleDriveEntry, false);

		clearUniqueFindersCache(googleDriveEntryModelImpl, false);
		cacheUniqueFindersCache(googleDriveEntryModelImpl);

		googleDriveEntry.resetOriginalValues();

		return googleDriveEntry;
	}

	/**
	 * Returns the google drive entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the google drive entry
	 * @return the google drive entry
	 * @throws NoSuchEntryException if a google drive entry with the primary key could not be found
	 */
	@Override
	public GoogleDriveEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {
		GoogleDriveEntry googleDriveEntry = fetchByPrimaryKey(primaryKey);

		if (googleDriveEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return googleDriveEntry;
	}

	/**
	 * Returns the google drive entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	 *
	 * @param entryId the primary key of the google drive entry
	 * @return the google drive entry
	 * @throws NoSuchEntryException if a google drive entry with the primary key could not be found
	 */
	@Override
	public GoogleDriveEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException {
		return findByPrimaryKey((Serializable)entryId);
	}

	/**
	 * Returns the google drive entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the google drive entry
	 * @return the google drive entry, or <code>null</code> if a google drive entry with the primary key could not be found
	 */
	@Override
	public GoogleDriveEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(GoogleDriveEntryModelImpl.ENTITY_CACHE_ENABLED,
				GoogleDriveEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		GoogleDriveEntry googleDriveEntry = (GoogleDriveEntry)serializable;

		if (googleDriveEntry == null) {
			Session session = null;

			try {
				session = openSession();

				googleDriveEntry = (GoogleDriveEntry)session.get(GoogleDriveEntryImpl.class,
						primaryKey);

				if (googleDriveEntry != null) {
					cacheResult(googleDriveEntry);
				}
				else {
					entityCache.putResult(GoogleDriveEntryModelImpl.ENTITY_CACHE_ENABLED,
						GoogleDriveEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(GoogleDriveEntryModelImpl.ENTITY_CACHE_ENABLED,
					GoogleDriveEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return googleDriveEntry;
	}

	/**
	 * Returns the google drive entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the google drive entry
	 * @return the google drive entry, or <code>null</code> if a google drive entry with the primary key could not be found
	 */
	@Override
	public GoogleDriveEntry fetchByPrimaryKey(long entryId) {
		return fetchByPrimaryKey((Serializable)entryId);
	}

	@Override
	public Map<Serializable, GoogleDriveEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, GoogleDriveEntry> map = new HashMap<Serializable, GoogleDriveEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			GoogleDriveEntry googleDriveEntry = fetchByPrimaryKey(primaryKey);

			if (googleDriveEntry != null) {
				map.put(primaryKey, googleDriveEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(GoogleDriveEntryModelImpl.ENTITY_CACHE_ENABLED,
					GoogleDriveEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (GoogleDriveEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_GOOGLEDRIVEENTRY_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (GoogleDriveEntry googleDriveEntry : (List<GoogleDriveEntry>)q.list()) {
				map.put(googleDriveEntry.getPrimaryKeyObj(), googleDriveEntry);

				cacheResult(googleDriveEntry);

				uncachedPrimaryKeys.remove(googleDriveEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(GoogleDriveEntryModelImpl.ENTITY_CACHE_ENABLED,
					GoogleDriveEntryImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the google drive entries.
	 *
	 * @return the google drive entries
	 */
	@Override
	public List<GoogleDriveEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the google drive entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GoogleDriveEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of google drive entries
	 * @param end the upper bound of the range of google drive entries (not inclusive)
	 * @return the range of google drive entries
	 */
	@Override
	public List<GoogleDriveEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the google drive entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GoogleDriveEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of google drive entries
	 * @param end the upper bound of the range of google drive entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of google drive entries
	 */
	@Override
	public List<GoogleDriveEntry> findAll(int start, int end,
		OrderByComparator<GoogleDriveEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the google drive entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GoogleDriveEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of google drive entries
	 * @param end the upper bound of the range of google drive entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of google drive entries
	 */
	@Override
	public List<GoogleDriveEntry> findAll(int start, int end,
		OrderByComparator<GoogleDriveEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<GoogleDriveEntry> list = null;

		if (retrieveFromCache) {
			list = (List<GoogleDriveEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_GOOGLEDRIVEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_GOOGLEDRIVEENTRY;

				if (pagination) {
					sql = sql.concat(GoogleDriveEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<GoogleDriveEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<GoogleDriveEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the google drive entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (GoogleDriveEntry googleDriveEntry : findAll()) {
			remove(googleDriveEntry);
		}
	}

	/**
	 * Returns the number of google drive entries.
	 *
	 * @return the number of google drive entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_GOOGLEDRIVEENTRY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return GoogleDriveEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the google drive entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(GoogleDriveEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_GOOGLEDRIVEENTRY = "SELECT googleDriveEntry FROM GoogleDriveEntry googleDriveEntry";
	private static final String _SQL_SELECT_GOOGLEDRIVEENTRY_WHERE_PKS_IN = "SELECT googleDriveEntry FROM GoogleDriveEntry googleDriveEntry WHERE entryId IN (";
	private static final String _SQL_SELECT_GOOGLEDRIVEENTRY_WHERE = "SELECT googleDriveEntry FROM GoogleDriveEntry googleDriveEntry WHERE ";
	private static final String _SQL_COUNT_GOOGLEDRIVEENTRY = "SELECT COUNT(googleDriveEntry) FROM GoogleDriveEntry googleDriveEntry";
	private static final String _SQL_COUNT_GOOGLEDRIVEENTRY_WHERE = "SELECT COUNT(googleDriveEntry) FROM GoogleDriveEntry googleDriveEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "googleDriveEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No GoogleDriveEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No GoogleDriveEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(GoogleDriveEntryPersistenceImpl.class);
}