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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchBookmarkException;
import com.liferay.portlet.asset.model.AssetBookmark;
import com.liferay.portlet.asset.model.impl.AssetBookmarkImpl;
import com.liferay.portlet.asset.model.impl.AssetBookmarkModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the asset bookmark service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetBookmarkPersistence
 * @see AssetBookmarkUtil
 * @generated
 */
public class AssetBookmarkPersistenceImpl extends BasePersistenceImpl<AssetBookmark>
	implements AssetBookmarkPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetBookmarkUtil} to access the asset bookmark persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetBookmarkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkModelImpl.FINDER_CACHE_ENABLED,
			AssetBookmarkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkModelImpl.FINDER_CACHE_ENABLED,
			AssetBookmarkImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkModelImpl.FINDER_CACHE_ENABLED,
			AssetBookmarkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkModelImpl.FINDER_CACHE_ENABLED,
			AssetBookmarkImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] { String.class.getName() },
			AssetBookmarkModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the asset bookmarks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetBookmark> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset bookmarks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetBookmarkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset bookmarks
	 * @param end the upper bound of the range of asset bookmarks (not inclusive)
	 * @return the range of matching asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetBookmark> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset bookmarks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetBookmarkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset bookmarks
	 * @param end the upper bound of the range of asset bookmarks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetBookmark> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<AssetBookmark> list = (List<AssetBookmark>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetBookmark assetBookmark : list) {
				if (!Validator.equals(uuid, assetBookmark.getUuid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ASSETBOOKMARK_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetBookmarkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<AssetBookmark>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetBookmark>(list);
				}
				else {
					list = (List<AssetBookmark>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset bookmark in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset bookmark
	 * @throws com.liferay.portlet.asset.NoSuchBookmarkException if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchBookmarkException, SystemException {
		AssetBookmark assetBookmark = fetchByUuid_First(uuid, orderByComparator);

		if (assetBookmark != null) {
			return assetBookmark;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBookmarkException(msg.toString());
	}

	/**
	 * Returns the first asset bookmark in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset bookmark, or <code>null</code> if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetBookmark> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset bookmark in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset bookmark
	 * @throws com.liferay.portlet.asset.NoSuchBookmarkException if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchBookmarkException, SystemException {
		AssetBookmark assetBookmark = fetchByUuid_Last(uuid, orderByComparator);

		if (assetBookmark != null) {
			return assetBookmark;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBookmarkException(msg.toString());
	}

	/**
	 * Returns the last asset bookmark in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset bookmark, or <code>null</code> if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<AssetBookmark> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset bookmarks before and after the current asset bookmark in the ordered set where uuid = &#63;.
	 *
	 * @param bookmarkId the primary key of the current asset bookmark
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset bookmark
	 * @throws com.liferay.portlet.asset.NoSuchBookmarkException if a asset bookmark with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark[] findByUuid_PrevAndNext(long bookmarkId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchBookmarkException, SystemException {
		AssetBookmark assetBookmark = findByPrimaryKey(bookmarkId);

		Session session = null;

		try {
			session = openSession();

			AssetBookmark[] array = new AssetBookmarkImpl[3];

			array[0] = getByUuid_PrevAndNext(session, assetBookmark, uuid,
					orderByComparator, true);

			array[1] = assetBookmark;

			array[2] = getByUuid_PrevAndNext(session, assetBookmark, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetBookmark getByUuid_PrevAndNext(Session session,
		AssetBookmark assetBookmark, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETBOOKMARK_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AssetBookmarkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetBookmark);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetBookmark> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset bookmarks where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (AssetBookmark assetBookmark : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(assetBookmark);
		}
	}

	/**
	 * Returns the number of asset bookmarks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETBOOKMARK_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "assetBookmark.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "assetBookmark.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(assetBookmark.uuid IS NULL OR assetBookmark.uuid = ?)";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkModelImpl.FINDER_CACHE_ENABLED,
			AssetBookmarkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkModelImpl.FINDER_CACHE_ENABLED,
			AssetBookmarkImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUserId", new String[] { Long.class.getName() },
			AssetBookmarkModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the asset bookmarks where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetBookmark> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset bookmarks where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetBookmarkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of asset bookmarks
	 * @param end the upper bound of the range of asset bookmarks (not inclusive)
	 * @return the range of matching asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetBookmark> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset bookmarks where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetBookmarkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of asset bookmarks
	 * @param end the upper bound of the range of asset bookmarks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetBookmark> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<AssetBookmark> list = (List<AssetBookmark>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetBookmark assetBookmark : list) {
				if ((userId != assetBookmark.getUserId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ASSETBOOKMARK_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetBookmarkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<AssetBookmark>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetBookmark>(list);
				}
				else {
					list = (List<AssetBookmark>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset bookmark in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset bookmark
	 * @throws com.liferay.portlet.asset.NoSuchBookmarkException if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchBookmarkException, SystemException {
		AssetBookmark assetBookmark = fetchByUserId_First(userId,
				orderByComparator);

		if (assetBookmark != null) {
			return assetBookmark;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBookmarkException(msg.toString());
	}

	/**
	 * Returns the first asset bookmark in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset bookmark, or <code>null</code> if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetBookmark> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset bookmark in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset bookmark
	 * @throws com.liferay.portlet.asset.NoSuchBookmarkException if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchBookmarkException, SystemException {
		AssetBookmark assetBookmark = fetchByUserId_Last(userId,
				orderByComparator);

		if (assetBookmark != null) {
			return assetBookmark;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBookmarkException(msg.toString());
	}

	/**
	 * Returns the last asset bookmark in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset bookmark, or <code>null</code> if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<AssetBookmark> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset bookmarks before and after the current asset bookmark in the ordered set where userId = &#63;.
	 *
	 * @param bookmarkId the primary key of the current asset bookmark
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset bookmark
	 * @throws com.liferay.portlet.asset.NoSuchBookmarkException if a asset bookmark with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark[] findByUserId_PrevAndNext(long bookmarkId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchBookmarkException, SystemException {
		AssetBookmark assetBookmark = findByPrimaryKey(bookmarkId);

		Session session = null;

		try {
			session = openSession();

			AssetBookmark[] array = new AssetBookmarkImpl[3];

			array[0] = getByUserId_PrevAndNext(session, assetBookmark, userId,
					orderByComparator, true);

			array[1] = assetBookmark;

			array[2] = getByUserId_PrevAndNext(session, assetBookmark, userId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetBookmark getByUserId_PrevAndNext(Session session,
		AssetBookmark assetBookmark, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETBOOKMARK_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AssetBookmarkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetBookmark);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetBookmark> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset bookmarks where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (AssetBookmark assetBookmark : findByUserId(userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetBookmark);
		}
	}

	/**
	 * Returns the number of asset bookmarks where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETBOOKMARK_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "assetBookmark.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_CNI = new FinderPath(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkModelImpl.FINDER_CACHE_ENABLED,
			AssetBookmarkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU_CNI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_CNI = new FinderPath(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkModelImpl.FINDER_CACHE_ENABLED,
			AssetBookmarkImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByU_CNI",
			new String[] { Long.class.getName(), Long.class.getName() },
			AssetBookmarkModelImpl.USERID_COLUMN_BITMASK |
			AssetBookmarkModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_CNI = new FinderPath(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_CNI",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the asset bookmarks where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the matching asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetBookmark> findByU_CNI(long userId, long classNameId)
		throws SystemException {
		return findByU_CNI(userId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset bookmarks where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetBookmarkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset bookmarks
	 * @param end the upper bound of the range of asset bookmarks (not inclusive)
	 * @return the range of matching asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetBookmark> findByU_CNI(long userId, long classNameId,
		int start, int end) throws SystemException {
		return findByU_CNI(userId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset bookmarks where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetBookmarkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset bookmarks
	 * @param end the upper bound of the range of asset bookmarks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetBookmark> findByU_CNI(long userId, long classNameId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_CNI;
			finderArgs = new Object[] { userId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_CNI;
			finderArgs = new Object[] {
					userId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<AssetBookmark> list = (List<AssetBookmark>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetBookmark assetBookmark : list) {
				if ((userId != assetBookmark.getUserId()) ||
						(classNameId != assetBookmark.getClassNameId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ASSETBOOKMARK_WHERE);

			query.append(_FINDER_COLUMN_U_CNI_USERID_2);

			query.append(_FINDER_COLUMN_U_CNI_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetBookmarkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<AssetBookmark>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetBookmark>(list);
				}
				else {
					list = (List<AssetBookmark>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset bookmark in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset bookmark
	 * @throws com.liferay.portlet.asset.NoSuchBookmarkException if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark findByU_CNI_First(long userId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchBookmarkException, SystemException {
		AssetBookmark assetBookmark = fetchByU_CNI_First(userId, classNameId,
				orderByComparator);

		if (assetBookmark != null) {
			return assetBookmark;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBookmarkException(msg.toString());
	}

	/**
	 * Returns the first asset bookmark in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset bookmark, or <code>null</code> if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark fetchByU_CNI_First(long userId, long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetBookmark> list = findByU_CNI(userId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset bookmark in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset bookmark
	 * @throws com.liferay.portlet.asset.NoSuchBookmarkException if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark findByU_CNI_Last(long userId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchBookmarkException, SystemException {
		AssetBookmark assetBookmark = fetchByU_CNI_Last(userId, classNameId,
				orderByComparator);

		if (assetBookmark != null) {
			return assetBookmark;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBookmarkException(msg.toString());
	}

	/**
	 * Returns the last asset bookmark in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset bookmark, or <code>null</code> if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark fetchByU_CNI_Last(long userId, long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByU_CNI(userId, classNameId);

		List<AssetBookmark> list = findByU_CNI(userId, classNameId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset bookmarks before and after the current asset bookmark in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param bookmarkId the primary key of the current asset bookmark
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset bookmark
	 * @throws com.liferay.portlet.asset.NoSuchBookmarkException if a asset bookmark with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark[] findByU_CNI_PrevAndNext(long bookmarkId,
		long userId, long classNameId, OrderByComparator orderByComparator)
		throws NoSuchBookmarkException, SystemException {
		AssetBookmark assetBookmark = findByPrimaryKey(bookmarkId);

		Session session = null;

		try {
			session = openSession();

			AssetBookmark[] array = new AssetBookmarkImpl[3];

			array[0] = getByU_CNI_PrevAndNext(session, assetBookmark, userId,
					classNameId, orderByComparator, true);

			array[1] = assetBookmark;

			array[2] = getByU_CNI_PrevAndNext(session, assetBookmark, userId,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetBookmark getByU_CNI_PrevAndNext(Session session,
		AssetBookmark assetBookmark, long userId, long classNameId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETBOOKMARK_WHERE);

		query.append(_FINDER_COLUMN_U_CNI_USERID_2);

		query.append(_FINDER_COLUMN_U_CNI_CLASSNAMEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AssetBookmarkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetBookmark);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetBookmark> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset bookmarks where userId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_CNI(long userId, long classNameId)
		throws SystemException {
		for (AssetBookmark assetBookmark : findByU_CNI(userId, classNameId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetBookmark);
		}
	}

	/**
	 * Returns the number of asset bookmarks where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the number of matching asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_CNI(long userId, long classNameId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_CNI;

		Object[] finderArgs = new Object[] { userId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETBOOKMARK_WHERE);

			query.append(_FINDER_COLUMN_U_CNI_USERID_2);

			query.append(_FINDER_COLUMN_U_CNI_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_U_CNI_USERID_2 = "assetBookmark.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_CNI_CLASSNAMEID_2 = "assetBookmark.classNameId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_U_CPK = new FinderPath(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkModelImpl.FINDER_CACHE_ENABLED,
			AssetBookmarkImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByU_CPK",
			new String[] { Long.class.getName(), Long.class.getName() },
			AssetBookmarkModelImpl.USERID_COLUMN_BITMASK |
			AssetBookmarkModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_CPK = new FinderPath(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_CPK",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the asset bookmark where userId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchBookmarkException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param classPK the class p k
	 * @return the matching asset bookmark
	 * @throws com.liferay.portlet.asset.NoSuchBookmarkException if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark findByU_CPK(long userId, long classPK)
		throws NoSuchBookmarkException, SystemException {
		AssetBookmark assetBookmark = fetchByU_CPK(userId, classPK);

		if (assetBookmark == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchBookmarkException(msg.toString());
		}

		return assetBookmark;
	}

	/**
	 * Returns the asset bookmark where userId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param classPK the class p k
	 * @return the matching asset bookmark, or <code>null</code> if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark fetchByU_CPK(long userId, long classPK)
		throws SystemException {
		return fetchByU_CPK(userId, classPK, true);
	}

	/**
	 * Returns the asset bookmark where userId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching asset bookmark, or <code>null</code> if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark fetchByU_CPK(long userId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { userId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_CPK,
					finderArgs, this);
		}

		if (result instanceof AssetBookmark) {
			AssetBookmark assetBookmark = (AssetBookmark)result;

			if ((userId != assetBookmark.getUserId()) ||
					(classPK != assetBookmark.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETBOOKMARK_WHERE);

			query.append(_FINDER_COLUMN_U_CPK_USERID_2);

			query.append(_FINDER_COLUMN_U_CPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classPK);

				List<AssetBookmark> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_CPK,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"AssetBookmarkPersistenceImpl.fetchByU_CPK(long, long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					AssetBookmark assetBookmark = list.get(0);

					result = assetBookmark;

					cacheResult(assetBookmark);

					if ((assetBookmark.getUserId() != userId) ||
							(assetBookmark.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_CPK,
							finderArgs, assetBookmark);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_CPK,
					finderArgs);

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
			return (AssetBookmark)result;
		}
	}

	/**
	 * Removes the asset bookmark where userId = &#63; and classPK = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classPK the class p k
	 * @return the asset bookmark that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark removeByU_CPK(long userId, long classPK)
		throws NoSuchBookmarkException, SystemException {
		AssetBookmark assetBookmark = findByU_CPK(userId, classPK);

		return remove(assetBookmark);
	}

	/**
	 * Returns the number of asset bookmarks where userId = &#63; and classPK = &#63;.
	 *
	 * @param userId the user ID
	 * @param classPK the class p k
	 * @return the number of matching asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_CPK(long userId, long classPK)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_CPK;

		Object[] finderArgs = new Object[] { userId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETBOOKMARK_WHERE);

			query.append(_FINDER_COLUMN_U_CPK_USERID_2);

			query.append(_FINDER_COLUMN_U_CPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_U_CPK_USERID_2 = "assetBookmark.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_CPK_CLASSPK_2 = "assetBookmark.classPK = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_U_C_C = new FinderPath(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkModelImpl.FINDER_CACHE_ENABLED,
			AssetBookmarkImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			AssetBookmarkModelImpl.USERID_COLUMN_BITMASK |
			AssetBookmarkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AssetBookmarkModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C_C = new FinderPath(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the asset bookmark where userId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchBookmarkException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching asset bookmark
	 * @throws com.liferay.portlet.asset.NoSuchBookmarkException if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark findByU_C_C(long userId, long classNameId, long classPK)
		throws NoSuchBookmarkException, SystemException {
		AssetBookmark assetBookmark = fetchByU_C_C(userId, classNameId, classPK);

		if (assetBookmark == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchBookmarkException(msg.toString());
		}

		return assetBookmark;
	}

	/**
	 * Returns the asset bookmark where userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching asset bookmark, or <code>null</code> if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark fetchByU_C_C(long userId, long classNameId,
		long classPK) throws SystemException {
		return fetchByU_C_C(userId, classNameId, classPK, true);
	}

	/**
	 * Returns the asset bookmark where userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching asset bookmark, or <code>null</code> if a matching asset bookmark could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark fetchByU_C_C(long userId, long classNameId,
		long classPK, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { userId, classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_C_C,
					finderArgs, this);
		}

		if (result instanceof AssetBookmark) {
			AssetBookmark assetBookmark = (AssetBookmark)result;

			if ((userId != assetBookmark.getUserId()) ||
					(classNameId != assetBookmark.getClassNameId()) ||
					(classPK != assetBookmark.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_ASSETBOOKMARK_WHERE);

			query.append(_FINDER_COLUMN_U_C_C_USERID_2);

			query.append(_FINDER_COLUMN_U_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<AssetBookmark> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"AssetBookmarkPersistenceImpl.fetchByU_C_C(long, long, long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					AssetBookmark assetBookmark = list.get(0);

					result = assetBookmark;

					cacheResult(assetBookmark);

					if ((assetBookmark.getUserId() != userId) ||
							(assetBookmark.getClassNameId() != classNameId) ||
							(assetBookmark.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C,
							finderArgs, assetBookmark);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_C_C,
					finderArgs);

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
			return (AssetBookmark)result;
		}
	}

	/**
	 * Removes the asset bookmark where userId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the asset bookmark that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark removeByU_C_C(long userId, long classNameId,
		long classPK) throws NoSuchBookmarkException, SystemException {
		AssetBookmark assetBookmark = findByU_C_C(userId, classNameId, classPK);

		return remove(assetBookmark);
	}

	/**
	 * Returns the number of asset bookmarks where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_C_C(long userId, long classNameId, long classPK)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_C_C;

		Object[] finderArgs = new Object[] { userId, classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_ASSETBOOKMARK_WHERE);

			query.append(_FINDER_COLUMN_U_C_C_USERID_2);

			query.append(_FINDER_COLUMN_U_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_U_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_U_C_C_USERID_2 = "assetBookmark.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_CLASSNAMEID_2 = "assetBookmark.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_C_CLASSPK_2 = "assetBookmark.classPK = ?";

	/**
	 * Caches the asset bookmark in the entity cache if it is enabled.
	 *
	 * @param assetBookmark the asset bookmark
	 */
	public void cacheResult(AssetBookmark assetBookmark) {
		EntityCacheUtil.putResult(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkImpl.class, assetBookmark.getPrimaryKey(),
			assetBookmark);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_CPK,
			new Object[] {
				Long.valueOf(assetBookmark.getUserId()),
				Long.valueOf(assetBookmark.getClassPK())
			}, assetBookmark);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C,
			new Object[] {
				Long.valueOf(assetBookmark.getUserId()),
				Long.valueOf(assetBookmark.getClassNameId()),
				Long.valueOf(assetBookmark.getClassPK())
			}, assetBookmark);

		assetBookmark.resetOriginalValues();
	}

	/**
	 * Caches the asset bookmarks in the entity cache if it is enabled.
	 *
	 * @param assetBookmarks the asset bookmarks
	 */
	public void cacheResult(List<AssetBookmark> assetBookmarks) {
		for (AssetBookmark assetBookmark : assetBookmarks) {
			if (EntityCacheUtil.getResult(
						AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
						AssetBookmarkImpl.class, assetBookmark.getPrimaryKey()) == null) {
				cacheResult(assetBookmark);
			}
			else {
				assetBookmark.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset bookmarks.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(AssetBookmarkImpl.class.getName());
		}

		EntityCacheUtil.clearCache(AssetBookmarkImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset bookmark.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetBookmark assetBookmark) {
		EntityCacheUtil.removeResult(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkImpl.class, assetBookmark.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(assetBookmark);
	}

	@Override
	public void clearCache(List<AssetBookmark> assetBookmarks) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetBookmark assetBookmark : assetBookmarks) {
			EntityCacheUtil.removeResult(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
				AssetBookmarkImpl.class, assetBookmark.getPrimaryKey());

			clearUniqueFindersCache(assetBookmark);
		}
	}

	protected void clearUniqueFindersCache(AssetBookmark assetBookmark) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_CPK,
			new Object[] {
				Long.valueOf(assetBookmark.getUserId()),
				Long.valueOf(assetBookmark.getClassPK())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_C_C,
			new Object[] {
				Long.valueOf(assetBookmark.getUserId()),
				Long.valueOf(assetBookmark.getClassNameId()),
				Long.valueOf(assetBookmark.getClassPK())
			});
	}

	/**
	 * Creates a new asset bookmark with the primary key. Does not add the asset bookmark to the database.
	 *
	 * @param bookmarkId the primary key for the new asset bookmark
	 * @return the new asset bookmark
	 */
	public AssetBookmark create(long bookmarkId) {
		AssetBookmark assetBookmark = new AssetBookmarkImpl();

		assetBookmark.setNew(true);
		assetBookmark.setPrimaryKey(bookmarkId);

		String uuid = PortalUUIDUtil.generate();

		assetBookmark.setUuid(uuid);

		return assetBookmark;
	}

	/**
	 * Removes the asset bookmark with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param bookmarkId the primary key of the asset bookmark
	 * @return the asset bookmark that was removed
	 * @throws com.liferay.portlet.asset.NoSuchBookmarkException if a asset bookmark with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark remove(long bookmarkId)
		throws NoSuchBookmarkException, SystemException {
		return remove(Long.valueOf(bookmarkId));
	}

	/**
	 * Removes the asset bookmark with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset bookmark
	 * @return the asset bookmark that was removed
	 * @throws com.liferay.portlet.asset.NoSuchBookmarkException if a asset bookmark with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetBookmark remove(Serializable primaryKey)
		throws NoSuchBookmarkException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetBookmark assetBookmark = (AssetBookmark)session.get(AssetBookmarkImpl.class,
					primaryKey);

			if (assetBookmark == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBookmarkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetBookmark);
		}
		catch (NoSuchBookmarkException nsee) {
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
	protected AssetBookmark removeImpl(AssetBookmark assetBookmark)
		throws SystemException {
		assetBookmark = toUnwrappedModel(assetBookmark);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetBookmark)) {
				assetBookmark = (AssetBookmark)session.get(AssetBookmarkImpl.class,
						assetBookmark.getPrimaryKeyObj());
			}

			if (assetBookmark != null) {
				session.delete(assetBookmark);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetBookmark != null) {
			clearCache(assetBookmark);
		}

		return assetBookmark;
	}

	@Override
	public AssetBookmark updateImpl(
		com.liferay.portlet.asset.model.AssetBookmark assetBookmark)
		throws SystemException {
		assetBookmark = toUnwrappedModel(assetBookmark);

		boolean isNew = assetBookmark.isNew();

		AssetBookmarkModelImpl assetBookmarkModelImpl = (AssetBookmarkModelImpl)assetBookmark;

		if (Validator.isNull(assetBookmark.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetBookmark.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (assetBookmark.isNew()) {
				session.save(assetBookmark);

				assetBookmark.setNew(false);
			}
			else {
				session.merge(assetBookmark);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !AssetBookmarkModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((assetBookmarkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetBookmarkModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { assetBookmarkModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((assetBookmarkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetBookmarkModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(assetBookmarkModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((assetBookmarkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_CNI.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetBookmarkModelImpl.getOriginalUserId()),
						Long.valueOf(assetBookmarkModelImpl.getOriginalClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_CNI, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_CNI,
					args);

				args = new Object[] {
						Long.valueOf(assetBookmarkModelImpl.getUserId()),
						Long.valueOf(assetBookmarkModelImpl.getClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_CNI, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_CNI,
					args);
			}
		}

		EntityCacheUtil.putResult(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
			AssetBookmarkImpl.class, assetBookmark.getPrimaryKey(),
			assetBookmark);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_CPK,
				new Object[] {
					Long.valueOf(assetBookmark.getUserId()),
					Long.valueOf(assetBookmark.getClassPK())
				}, assetBookmark);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C,
				new Object[] {
					Long.valueOf(assetBookmark.getUserId()),
					Long.valueOf(assetBookmark.getClassNameId()),
					Long.valueOf(assetBookmark.getClassPK())
				}, assetBookmark);
		}
		else {
			if ((assetBookmarkModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_U_CPK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetBookmarkModelImpl.getOriginalUserId()),
						Long.valueOf(assetBookmarkModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_CPK, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_CPK, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_CPK,
					new Object[] {
						Long.valueOf(assetBookmark.getUserId()),
						Long.valueOf(assetBookmark.getClassPK())
					}, assetBookmark);
			}

			if ((assetBookmarkModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_U_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetBookmarkModelImpl.getOriginalUserId()),
						Long.valueOf(assetBookmarkModelImpl.getOriginalClassNameId()),
						Long.valueOf(assetBookmarkModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_C_C, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_C_C, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_C_C,
					new Object[] {
						Long.valueOf(assetBookmark.getUserId()),
						Long.valueOf(assetBookmark.getClassNameId()),
						Long.valueOf(assetBookmark.getClassPK())
					}, assetBookmark);
			}
		}

		return assetBookmark;
	}

	protected AssetBookmark toUnwrappedModel(AssetBookmark assetBookmark) {
		if (assetBookmark instanceof AssetBookmarkImpl) {
			return assetBookmark;
		}

		AssetBookmarkImpl assetBookmarkImpl = new AssetBookmarkImpl();

		assetBookmarkImpl.setNew(assetBookmark.isNew());
		assetBookmarkImpl.setPrimaryKey(assetBookmark.getPrimaryKey());

		assetBookmarkImpl.setUuid(assetBookmark.getUuid());
		assetBookmarkImpl.setBookmarkId(assetBookmark.getBookmarkId());
		assetBookmarkImpl.setUserId(assetBookmark.getUserId());
		assetBookmarkImpl.setClassNameId(assetBookmark.getClassNameId());
		assetBookmarkImpl.setClassPK(assetBookmark.getClassPK());

		return assetBookmarkImpl;
	}

	/**
	 * Returns the asset bookmark with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset bookmark
	 * @return the asset bookmark
	 * @throws com.liferay.portal.NoSuchModelException if a asset bookmark with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetBookmark findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asset bookmark with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchBookmarkException} if it could not be found.
	 *
	 * @param bookmarkId the primary key of the asset bookmark
	 * @return the asset bookmark
	 * @throws com.liferay.portlet.asset.NoSuchBookmarkException if a asset bookmark with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark findByPrimaryKey(long bookmarkId)
		throws NoSuchBookmarkException, SystemException {
		AssetBookmark assetBookmark = fetchByPrimaryKey(bookmarkId);

		if (assetBookmark == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + bookmarkId);
			}

			throw new NoSuchBookmarkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				bookmarkId);
		}

		return assetBookmark;
	}

	/**
	 * Returns the asset bookmark with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset bookmark
	 * @return the asset bookmark, or <code>null</code> if a asset bookmark with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetBookmark fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asset bookmark with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param bookmarkId the primary key of the asset bookmark
	 * @return the asset bookmark, or <code>null</code> if a asset bookmark with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetBookmark fetchByPrimaryKey(long bookmarkId)
		throws SystemException {
		AssetBookmark assetBookmark = (AssetBookmark)EntityCacheUtil.getResult(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
				AssetBookmarkImpl.class, bookmarkId);

		if (assetBookmark == _nullAssetBookmark) {
			return null;
		}

		if (assetBookmark == null) {
			Session session = null;

			try {
				session = openSession();

				assetBookmark = (AssetBookmark)session.get(AssetBookmarkImpl.class,
						Long.valueOf(bookmarkId));

				if (assetBookmark != null) {
					cacheResult(assetBookmark);
				}
				else {
					EntityCacheUtil.putResult(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
						AssetBookmarkImpl.class, bookmarkId, _nullAssetBookmark);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(AssetBookmarkModelImpl.ENTITY_CACHE_ENABLED,
					AssetBookmarkImpl.class, bookmarkId);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return assetBookmark;
	}

	/**
	 * Returns all the asset bookmarks.
	 *
	 * @return the asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetBookmark> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset bookmarks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetBookmarkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset bookmarks
	 * @param end the upper bound of the range of asset bookmarks (not inclusive)
	 * @return the range of asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetBookmark> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset bookmarks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetBookmarkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset bookmarks
	 * @param end the upper bound of the range of asset bookmarks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetBookmark> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<AssetBookmark> list = (List<AssetBookmark>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ASSETBOOKMARK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETBOOKMARK;

				if (pagination) {
					sql = sql.concat(AssetBookmarkModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AssetBookmark>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetBookmark>(list);
				}
				else {
					list = (List<AssetBookmark>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset bookmarks from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (AssetBookmark assetBookmark : findAll()) {
			remove(assetBookmark);
		}
	}

	/**
	 * Returns the number of asset bookmarks.
	 *
	 * @return the number of asset bookmarks
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETBOOKMARK);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the asset bookmark persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.asset.model.AssetBookmark")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetBookmark>> listenersList = new ArrayList<ModelListener<AssetBookmark>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetBookmark>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(AssetBookmarkImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AssetBookmarkPersistence.class)
	protected AssetBookmarkPersistence assetBookmarkPersistence;
	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(type = AssetCategoryPropertyPersistence.class)
	protected AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetLinkPersistence.class)
	protected AssetLinkPersistence assetLinkPersistence;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = AssetTagPropertyPersistence.class)
	protected AssetTagPropertyPersistence assetTagPropertyPersistence;
	@BeanReference(type = AssetTagStatsPersistence.class)
	protected AssetTagStatsPersistence assetTagStatsPersistence;
	@BeanReference(type = AssetVocabularyPersistence.class)
	protected AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_ASSETBOOKMARK = "SELECT assetBookmark FROM AssetBookmark assetBookmark";
	private static final String _SQL_SELECT_ASSETBOOKMARK_WHERE = "SELECT assetBookmark FROM AssetBookmark assetBookmark WHERE ";
	private static final String _SQL_COUNT_ASSETBOOKMARK = "SELECT COUNT(assetBookmark) FROM AssetBookmark assetBookmark";
	private static final String _SQL_COUNT_ASSETBOOKMARK_WHERE = "SELECT COUNT(assetBookmark) FROM AssetBookmark assetBookmark WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetBookmark.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetBookmark exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetBookmark exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(AssetBookmarkPersistenceImpl.class);
	private static AssetBookmark _nullAssetBookmark = new AssetBookmarkImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AssetBookmark> toCacheModel() {
				return _nullAssetBookmarkCacheModel;
			}
		};

	private static CacheModel<AssetBookmark> _nullAssetBookmarkCacheModel = new CacheModel<AssetBookmark>() {
			public AssetBookmark toEntityModel() {
				return _nullAssetBookmark;
			}
		};
}