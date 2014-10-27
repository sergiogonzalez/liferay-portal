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

package com.liferay.portlet.journal.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleModelImpl;
import com.liferay.portlet.journal.service.persistence.JournalArticlePersistence;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the journal article service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticlePersistence
 * @see JournalArticleUtil
 * @generated
 */
@ProviderType
public class JournalArticlePersistenceImpl extends BasePersistenceImpl<JournalArticle>
	implements JournalArticlePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link JournalArticleUtil} to access the journal article persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = JournalArticleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			JournalArticleModelImpl.UUID_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the journal articles where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByUuid(String uuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
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

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if (!Validator.equals(uuid, journalArticle.getUuid())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByUuid_First(String uuid,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByUuid_First(uuid,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByUuid_First(String uuid,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByUuid_Last(String uuid,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByUuid_Last(uuid, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByUuid_Last(String uuid,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where uuid = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByUuid_PrevAndNext(long id, String uuid,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByUuid_PrevAndNext(session, journalArticle, uuid,
					orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByUuid_PrevAndNext(session, journalArticle, uuid,
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

	protected JournalArticle getByUuid_PrevAndNext(Session session,
		JournalArticle journalArticle, String uuid,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (JournalArticle journalArticle : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "journalArticle.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "journalArticle.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(journalArticle.uuid IS NULL OR journalArticle.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			JournalArticleModelImpl.UUID_COLUMN_BITMASK |
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the journal article where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchArticleException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByUUID_G(String uuid, long groupId)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByUUID_G(uuid, groupId);

		if (journalArticle == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchArticleException(msg.toString());
		}

		return journalArticle;
	}

	/**
	 * Returns the journal article where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the journal article where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof JournalArticle) {
			JournalArticle journalArticle = (JournalArticle)result;

			if (!Validator.equals(uuid, journalArticle.getUuid()) ||
					(groupId != journalArticle.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<JournalArticle> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					JournalArticle journalArticle = list.get(0);

					result = journalArticle;

					cacheResult(journalArticle);

					if ((journalArticle.getUuid() == null) ||
							!journalArticle.getUuid().equals(uuid) ||
							(journalArticle.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, journalArticle);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
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
			return (JournalArticle)result;
		}
	}

	/**
	 * Removes the journal article where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the journal article that was removed
	 */
	@Override
	public JournalArticle removeByUUID_G(String uuid, long groupId)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByUUID_G(uuid, groupId);

		return remove(journalArticle);
	}

	/**
	 * Returns the number of journal articles where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "journalArticle.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "journalArticle.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(journalArticle.uuid IS NULL OR journalArticle.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "journalArticle.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			JournalArticleModelImpl.UUID_COLUMN_BITMASK |
			JournalArticleModelImpl.COMPANYID_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the journal articles where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if (!Validator.equals(uuid, journalArticle.getUuid()) ||
						(companyId != journalArticle.getCompanyId())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByUuid_C_PrevAndNext(long id, String uuid,
		long companyId, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, journalArticle, uuid,
					companyId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByUuid_C_PrevAndNext(session, journalArticle, uuid,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByUuid_C_PrevAndNext(Session session,
		JournalArticle journalArticle, String uuid, long companyId,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (JournalArticle journalArticle : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "journalArticle.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "journalArticle.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(journalArticle.uuid IS NULL OR journalArticle.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "journalArticle.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_RESOURCEPRIMKEY =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByResourcePrimKey",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByResourcePrimKey",
			new String[] { Long.class.getName() },
			JournalArticleModelImpl.RESOURCEPRIMKEY_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByResourcePrimKey", new String[] { Long.class.getName() });

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByResourcePrimKey(long resourcePrimKey) {
		return findByResourcePrimKey(resourcePrimKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByResourcePrimKey(long resourcePrimKey,
		int start, int end) {
		return findByResourcePrimKey(resourcePrimKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByResourcePrimKey(long resourcePrimKey,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY;
			finderArgs = new Object[] { resourcePrimKey };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_RESOURCEPRIMKEY;
			finderArgs = new Object[] {
					resourcePrimKey,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((resourcePrimKey != journalArticle.getResourcePrimKey())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByResourcePrimKey_First(long resourcePrimKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByResourcePrimKey_First(resourcePrimKey,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByResourcePrimKey_First(long resourcePrimKey,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByResourcePrimKey(resourcePrimKey, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByResourcePrimKey_Last(long resourcePrimKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByResourcePrimKey_Last(resourcePrimKey,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByResourcePrimKey_Last(long resourcePrimKey,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByResourcePrimKey(resourcePrimKey);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByResourcePrimKey(resourcePrimKey,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByResourcePrimKey_PrevAndNext(long id,
		long resourcePrimKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByResourcePrimKey_PrevAndNext(session,
					journalArticle, resourcePrimKey, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByResourcePrimKey_PrevAndNext(session,
					journalArticle, resourcePrimKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByResourcePrimKey_PrevAndNext(Session session,
		JournalArticle journalArticle, long resourcePrimKey,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourcePrimKey);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where resourcePrimKey = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 */
	@Override
	public void removeByResourcePrimKey(long resourcePrimKey) {
		for (JournalArticle journalArticle : findByResourcePrimKey(
				resourcePrimKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByResourcePrimKey(long resourcePrimKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY;

		Object[] finderArgs = new Object[] { resourcePrimKey };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

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

	private static final String _FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2 =
		"journalArticle.resourcePrimKey = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the journal articles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByGroupId(long groupId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByGroupId_First(long groupId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByGroupId_First(groupId,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByGroupId_First(long groupId,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByGroupId_Last(long groupId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByGroupId_Last(long groupId,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, journalArticle,
					groupId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByGroupId_PrevAndNext(session, journalArticle,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByGroupId_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByGroupId(long groupId, int start,
		int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByGroupId_PrevAndNext(long id,
		long groupId, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(id, groupId, orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, journalArticle,
					groupId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByGroupId_PrevAndNext(session, journalArticle,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle filterGetByGroupId_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (JournalArticle journalArticle : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "journalArticle.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			JournalArticleModelImpl.COMPANYID_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the journal articles where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByCompanyId(long companyId, int start,
		int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((companyId != journalArticle.getCompanyId())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByCompanyId_First(long companyId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByCompanyId_First(long companyId,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByCompanyId_Last(long companyId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByCompanyId_Last(long companyId,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where companyId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByCompanyId_PrevAndNext(long id,
		long companyId, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, journalArticle,
					companyId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByCompanyId_PrevAndNext(session, journalArticle,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByCompanyId_PrevAndNext(Session session,
		JournalArticle journalArticle, long companyId,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (JournalArticle journalArticle : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "journalArticle.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_DDMSTRUCTUREKEY =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByDDMStructureKey",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_DDMSTRUCTUREKEY =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByDDMStructureKey",
			new String[] { String.class.getName() },
			JournalArticleModelImpl.DDMSTRUCTUREKEY_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_DDMSTRUCTUREKEY = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByDDMStructureKey", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_DDMSTRUCTUREKEY =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByDDMStructureKey",
			new String[] { String.class.getName() });

	/**
	 * Returns all the journal articles where DDMStructureKey = &#63;.
	 *
	 * @param DDMStructureKey the d d m structure key
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMStructureKey(String DDMStructureKey) {
		return findByDDMStructureKey(DDMStructureKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where DDMStructureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param DDMStructureKey the d d m structure key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMStructureKey(String DDMStructureKey,
		int start, int end) {
		return findByDDMStructureKey(DDMStructureKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where DDMStructureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param DDMStructureKey the d d m structure key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMStructureKey(String DDMStructureKey,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_DDMSTRUCTUREKEY;
			finderArgs = new Object[] { DDMStructureKey };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_DDMSTRUCTUREKEY;
			finderArgs = new Object[] {
					DDMStructureKey,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if (!Validator.equals(DDMStructureKey,
							journalArticle.getDDMStructureKey())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			boolean bindDDMStructureKey = false;

			if (DDMStructureKey == null) {
				query.append(_FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_1);
			}
			else if (DDMStructureKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_3);
			}
			else {
				bindDDMStructureKey = true;

				query.append(_FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindDDMStructureKey) {
					qPos.add(DDMStructureKey);
				}

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where DDMStructureKey = &#63;.
	 *
	 * @param DDMStructureKey the d d m structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByDDMStructureKey_First(String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByDDMStructureKey_First(DDMStructureKey,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("DDMStructureKey=");
		msg.append(DDMStructureKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where DDMStructureKey = &#63;.
	 *
	 * @param DDMStructureKey the d d m structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByDDMStructureKey_First(String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByDDMStructureKey(DDMStructureKey, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where DDMStructureKey = &#63;.
	 *
	 * @param DDMStructureKey the d d m structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByDDMStructureKey_Last(String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByDDMStructureKey_Last(DDMStructureKey,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("DDMStructureKey=");
		msg.append(DDMStructureKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where DDMStructureKey = &#63;.
	 *
	 * @param DDMStructureKey the d d m structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByDDMStructureKey_Last(String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByDDMStructureKey(DDMStructureKey);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByDDMStructureKey(DDMStructureKey,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where DDMStructureKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param DDMStructureKey the d d m structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByDDMStructureKey_PrevAndNext(long id,
		String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByDDMStructureKey_PrevAndNext(session,
					journalArticle, DDMStructureKey, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByDDMStructureKey_PrevAndNext(session,
					journalArticle, DDMStructureKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByDDMStructureKey_PrevAndNext(Session session,
		JournalArticle journalArticle, String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		boolean bindDDMStructureKey = false;

		if (DDMStructureKey == null) {
			query.append(_FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_1);
		}
		else if (DDMStructureKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_3);
		}
		else {
			bindDDMStructureKey = true;

			query.append(_FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_2);
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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindDDMStructureKey) {
			qPos.add(DDMStructureKey);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles where DDMStructureKey = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param DDMStructureKeies the d d m structure keies
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMStructureKey(
		String[] DDMStructureKeies) {
		return findByDDMStructureKey(DDMStructureKeies, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where DDMStructureKey = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param DDMStructureKeies the d d m structure keies
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMStructureKey(
		String[] DDMStructureKeies, int start, int end) {
		return findByDDMStructureKey(DDMStructureKeies, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where DDMStructureKey = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param DDMStructureKeies the d d m structure keies
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMStructureKey(
		String[] DDMStructureKeies, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (DDMStructureKeies == null) {
			DDMStructureKeies = new String[0];
		}
		else {
			DDMStructureKeies = ArrayUtil.distinct(DDMStructureKeies,
					NULL_SAFE_STRING_COMPARATOR);
		}

		if (DDMStructureKeies.length == 1) {
			return findByDDMStructureKey(DDMStructureKeies[0], start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { StringUtil.merge(DDMStructureKeies) };
		}
		else {
			finderArgs = new Object[] {
					StringUtil.merge(DDMStructureKeies),
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_DDMSTRUCTUREKEY,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if (!ArrayUtil.contains(DDMStructureKeies,
							journalArticle.getDDMStructureKey())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			if (DDMStructureKeies.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < DDMStructureKeies.length; i++) {
					String DDMStructureKey = DDMStructureKeies[i];

					if (DDMStructureKey == null) {
						query.append(_FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_1);
					}
					else if (DDMStructureKey.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_2);
					}

					if ((i + 1) < DDMStructureKeies.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				for (String DDMStructureKey : DDMStructureKeies) {
					if ((DDMStructureKey != null) &&
							!DDMStructureKey.isEmpty()) {
						qPos.add(DDMStructureKey);
					}
				}

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_DDMSTRUCTUREKEY,
					finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_DDMSTRUCTUREKEY,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the journal articles where DDMStructureKey = &#63; from the database.
	 *
	 * @param DDMStructureKey the d d m structure key
	 */
	@Override
	public void removeByDDMStructureKey(String DDMStructureKey) {
		for (JournalArticle journalArticle : findByDDMStructureKey(
				DDMStructureKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where DDMStructureKey = &#63;.
	 *
	 * @param DDMStructureKey the d d m structure key
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByDDMStructureKey(String DDMStructureKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_DDMSTRUCTUREKEY;

		Object[] finderArgs = new Object[] { DDMStructureKey };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			boolean bindDDMStructureKey = false;

			if (DDMStructureKey == null) {
				query.append(_FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_1);
			}
			else if (DDMStructureKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_3);
			}
			else {
				bindDDMStructureKey = true;

				query.append(_FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindDDMStructureKey) {
					qPos.add(DDMStructureKey);
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

	/**
	 * Returns the number of journal articles where DDMStructureKey = any &#63;.
	 *
	 * @param DDMStructureKeies the d d m structure keies
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByDDMStructureKey(String[] DDMStructureKeies) {
		if (DDMStructureKeies == null) {
			DDMStructureKeies = new String[0];
		}
		else {
			DDMStructureKeies = ArrayUtil.distinct(DDMStructureKeies,
					NULL_SAFE_STRING_COMPARATOR);
		}

		Object[] finderArgs = new Object[] { StringUtil.merge(DDMStructureKeies) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_DDMSTRUCTUREKEY,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			if (DDMStructureKeies.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < DDMStructureKeies.length; i++) {
					String DDMStructureKey = DDMStructureKeies[i];

					if (DDMStructureKey == null) {
						query.append(_FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_1);
					}
					else if (DDMStructureKey.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_2);
					}

					if ((i + 1) < DDMStructureKeies.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				for (String DDMStructureKey : DDMStructureKeies) {
					if ((DDMStructureKey != null) &&
							!DDMStructureKey.isEmpty()) {
						qPos.add(DDMStructureKey);
					}
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_DDMSTRUCTUREKEY,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_DDMSTRUCTUREKEY,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_1 =
		"journalArticle.DDMStructureKey IS NULL";
	private static final String _FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_2 =
		"journalArticle.DDMStructureKey = ?";
	private static final String _FINDER_COLUMN_DDMSTRUCTUREKEY_DDMSTRUCTUREKEY_3 =
		"(journalArticle.DDMStructureKey IS NULL OR journalArticle.DDMStructureKey = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_DDMTEMPLATEKEY =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByDDMTemplateKey",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_DDMTEMPLATEKEY =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByDDMTemplateKey",
			new String[] { String.class.getName() },
			JournalArticleModelImpl.DDMTEMPLATEKEY_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_DDMTEMPLATEKEY = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByDDMTemplateKey",
			new String[] { String.class.getName() });

	/**
	 * Returns all the journal articles where DDMTemplateKey = &#63;.
	 *
	 * @param DDMTemplateKey the d d m template key
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMTemplateKey(String DDMTemplateKey) {
		return findByDDMTemplateKey(DDMTemplateKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param DDMTemplateKey the d d m template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMTemplateKey(String DDMTemplateKey,
		int start, int end) {
		return findByDDMTemplateKey(DDMTemplateKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param DDMTemplateKey the d d m template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByDDMTemplateKey(String DDMTemplateKey,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_DDMTEMPLATEKEY;
			finderArgs = new Object[] { DDMTemplateKey };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_DDMTEMPLATEKEY;
			finderArgs = new Object[] {
					DDMTemplateKey,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if (!Validator.equals(DDMTemplateKey,
							journalArticle.getDDMTemplateKey())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			boolean bindDDMTemplateKey = false;

			if (DDMTemplateKey == null) {
				query.append(_FINDER_COLUMN_DDMTEMPLATEKEY_DDMTEMPLATEKEY_1);
			}
			else if (DDMTemplateKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_DDMTEMPLATEKEY_DDMTEMPLATEKEY_3);
			}
			else {
				bindDDMTemplateKey = true;

				query.append(_FINDER_COLUMN_DDMTEMPLATEKEY_DDMTEMPLATEKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindDDMTemplateKey) {
					qPos.add(DDMTemplateKey);
				}

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where DDMTemplateKey = &#63;.
	 *
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByDDMTemplateKey_First(String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByDDMTemplateKey_First(DDMTemplateKey,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("DDMTemplateKey=");
		msg.append(DDMTemplateKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where DDMTemplateKey = &#63;.
	 *
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByDDMTemplateKey_First(String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByDDMTemplateKey(DDMTemplateKey, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where DDMTemplateKey = &#63;.
	 *
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByDDMTemplateKey_Last(String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByDDMTemplateKey_Last(DDMTemplateKey,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("DDMTemplateKey=");
		msg.append(DDMTemplateKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where DDMTemplateKey = &#63;.
	 *
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByDDMTemplateKey_Last(String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByDDMTemplateKey(DDMTemplateKey);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByDDMTemplateKey(DDMTemplateKey,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where DDMTemplateKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByDDMTemplateKey_PrevAndNext(long id,
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByDDMTemplateKey_PrevAndNext(session, journalArticle,
					DDMTemplateKey, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByDDMTemplateKey_PrevAndNext(session, journalArticle,
					DDMTemplateKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByDDMTemplateKey_PrevAndNext(Session session,
		JournalArticle journalArticle, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		boolean bindDDMTemplateKey = false;

		if (DDMTemplateKey == null) {
			query.append(_FINDER_COLUMN_DDMTEMPLATEKEY_DDMTEMPLATEKEY_1);
		}
		else if (DDMTemplateKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_DDMTEMPLATEKEY_DDMTEMPLATEKEY_3);
		}
		else {
			bindDDMTemplateKey = true;

			query.append(_FINDER_COLUMN_DDMTEMPLATEKEY_DDMTEMPLATEKEY_2);
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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindDDMTemplateKey) {
			qPos.add(DDMTemplateKey);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where DDMTemplateKey = &#63; from the database.
	 *
	 * @param DDMTemplateKey the d d m template key
	 */
	@Override
	public void removeByDDMTemplateKey(String DDMTemplateKey) {
		for (JournalArticle journalArticle : findByDDMTemplateKey(
				DDMTemplateKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where DDMTemplateKey = &#63;.
	 *
	 * @param DDMTemplateKey the d d m template key
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByDDMTemplateKey(String DDMTemplateKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_DDMTEMPLATEKEY;

		Object[] finderArgs = new Object[] { DDMTemplateKey };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			boolean bindDDMTemplateKey = false;

			if (DDMTemplateKey == null) {
				query.append(_FINDER_COLUMN_DDMTEMPLATEKEY_DDMTEMPLATEKEY_1);
			}
			else if (DDMTemplateKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_DDMTEMPLATEKEY_DDMTEMPLATEKEY_3);
			}
			else {
				bindDDMTemplateKey = true;

				query.append(_FINDER_COLUMN_DDMTEMPLATEKEY_DDMTEMPLATEKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindDDMTemplateKey) {
					qPos.add(DDMTemplateKey);
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

	private static final String _FINDER_COLUMN_DDMTEMPLATEKEY_DDMTEMPLATEKEY_1 = "journalArticle.DDMTemplateKey IS NULL";
	private static final String _FINDER_COLUMN_DDMTEMPLATEKEY_DDMTEMPLATEKEY_2 = "journalArticle.DDMTemplateKey = ?";
	private static final String _FINDER_COLUMN_DDMTEMPLATEKEY_DDMTEMPLATEKEY_3 = "(journalArticle.DDMTemplateKey IS NULL OR journalArticle.DDMTemplateKey = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTUUID =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLayoutUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTUUID =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLayoutUuid",
			new String[] { String.class.getName() },
			JournalArticleModelImpl.LAYOUTUUID_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LAYOUTUUID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLayoutUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the journal articles where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByLayoutUuid(String layoutUuid) {
		return findByLayoutUuid(layoutUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByLayoutUuid(String layoutUuid, int start,
		int end) {
		return findByLayoutUuid(layoutUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByLayoutUuid(String layoutUuid, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTUUID;
			finderArgs = new Object[] { layoutUuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTUUID;
			finderArgs = new Object[] { layoutUuid, start, end, orderByComparator };
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if (!Validator.equals(layoutUuid, journalArticle.getLayoutUuid())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			boolean bindLayoutUuid = false;

			if (layoutUuid == null) {
				query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_1);
			}
			else if (layoutUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_3);
			}
			else {
				bindLayoutUuid = true;

				query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLayoutUuid) {
					qPos.add(layoutUuid);
				}

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByLayoutUuid_First(String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByLayoutUuid_First(layoutUuid,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutUuid=");
		msg.append(layoutUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByLayoutUuid_First(String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByLayoutUuid(layoutUuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByLayoutUuid_Last(String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByLayoutUuid_Last(layoutUuid,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutUuid=");
		msg.append(layoutUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByLayoutUuid_Last(String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByLayoutUuid(layoutUuid);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByLayoutUuid(layoutUuid, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where layoutUuid = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByLayoutUuid_PrevAndNext(long id,
		String layoutUuid, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByLayoutUuid_PrevAndNext(session, journalArticle,
					layoutUuid, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByLayoutUuid_PrevAndNext(session, journalArticle,
					layoutUuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByLayoutUuid_PrevAndNext(Session session,
		JournalArticle journalArticle, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		boolean bindLayoutUuid = false;

		if (layoutUuid == null) {
			query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_1);
		}
		else if (layoutUuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_2);
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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindLayoutUuid) {
			qPos.add(layoutUuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where layoutUuid = &#63; from the database.
	 *
	 * @param layoutUuid the layout uuid
	 */
	@Override
	public void removeByLayoutUuid(String layoutUuid) {
		for (JournalArticle journalArticle : findByLayoutUuid(layoutUuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByLayoutUuid(String layoutUuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_LAYOUTUUID;

		Object[] finderArgs = new Object[] { layoutUuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			boolean bindLayoutUuid = false;

			if (layoutUuid == null) {
				query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_1);
			}
			else if (layoutUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_3);
			}
			else {
				bindLayoutUuid = true;

				query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLayoutUuid) {
					qPos.add(layoutUuid);
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

	private static final String _FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_1 = "journalArticle.layoutUuid IS NULL";
	private static final String _FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_2 = "journalArticle.layoutUuid = ?";
	private static final String _FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_3 = "(journalArticle.layoutUuid IS NULL OR journalArticle.layoutUuid = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SMALLIMAGEID =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBySmallImageId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SMALLIMAGEID =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySmallImageId",
			new String[] { Long.class.getName() },
			JournalArticleModelImpl.SMALLIMAGEID_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SMALLIMAGEID = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySmallImageId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the journal articles where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findBySmallImageId(long smallImageId) {
		return findBySmallImageId(smallImageId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findBySmallImageId(long smallImageId,
		int start, int end) {
		return findBySmallImageId(smallImageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findBySmallImageId(long smallImageId,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SMALLIMAGEID;
			finderArgs = new Object[] { smallImageId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SMALLIMAGEID;
			finderArgs = new Object[] {
					smallImageId,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((smallImageId != journalArticle.getSmallImageId())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findBySmallImageId_First(long smallImageId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchBySmallImageId_First(smallImageId,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("smallImageId=");
		msg.append(smallImageId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchBySmallImageId_First(long smallImageId,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findBySmallImageId(smallImageId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findBySmallImageId_Last(long smallImageId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchBySmallImageId_Last(smallImageId,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("smallImageId=");
		msg.append(smallImageId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchBySmallImageId_Last(long smallImageId,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countBySmallImageId(smallImageId);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findBySmallImageId(smallImageId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where smallImageId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findBySmallImageId_PrevAndNext(long id,
		long smallImageId, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getBySmallImageId_PrevAndNext(session, journalArticle,
					smallImageId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getBySmallImageId_PrevAndNext(session, journalArticle,
					smallImageId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getBySmallImageId_PrevAndNext(Session session,
		JournalArticle journalArticle, long smallImageId,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(smallImageId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where smallImageId = &#63; from the database.
	 *
	 * @param smallImageId the small image ID
	 */
	@Override
	public void removeBySmallImageId(long smallImageId) {
		for (JournalArticle journalArticle : findBySmallImageId(smallImageId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countBySmallImageId(long smallImageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SMALLIMAGEID;

		Object[] finderArgs = new Object[] { smallImageId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

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

	private static final String _FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2 = "journalArticle.smallImageId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_R_I = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByR_I",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_I = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_I",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			JournalArticleModelImpl.RESOURCEPRIMKEY_COLUMN_BITMASK |
			JournalArticleModelImpl.INDEXABLE_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_I = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_I",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I(long resourcePrimKey,
		boolean indexable) {
		return findByR_I(resourcePrimKey, indexable, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I(long resourcePrimKey,
		boolean indexable, int start, int end) {
		return findByR_I(resourcePrimKey, indexable, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I(long resourcePrimKey,
		boolean indexable, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_I;
			finderArgs = new Object[] { resourcePrimKey, indexable };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_R_I;
			finderArgs = new Object[] {
					resourcePrimKey, indexable,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((resourcePrimKey != journalArticle.getResourcePrimKey()) ||
						(indexable != journalArticle.getIndexable())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_R_I_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_I_INDEXABLE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(indexable);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByR_I_First(long resourcePrimKey,
		boolean indexable, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByR_I_First(resourcePrimKey,
				indexable, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(", indexable=");
		msg.append(indexable);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByR_I_First(long resourcePrimKey,
		boolean indexable, OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByR_I(resourcePrimKey, indexable, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByR_I_Last(long resourcePrimKey,
		boolean indexable, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByR_I_Last(resourcePrimKey,
				indexable, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(", indexable=");
		msg.append(indexable);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByR_I_Last(long resourcePrimKey,
		boolean indexable, OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByR_I(resourcePrimKey, indexable);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByR_I(resourcePrimKey, indexable,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByR_I_PrevAndNext(long id,
		long resourcePrimKey, boolean indexable,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByR_I_PrevAndNext(session, journalArticle,
					resourcePrimKey, indexable, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByR_I_PrevAndNext(session, journalArticle,
					resourcePrimKey, indexable, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByR_I_PrevAndNext(Session session,
		JournalArticle journalArticle, long resourcePrimKey, boolean indexable,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_R_I_RESOURCEPRIMKEY_2);

		query.append(_FINDER_COLUMN_R_I_INDEXABLE_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourcePrimKey);

		qPos.add(indexable);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where resourcePrimKey = &#63; and indexable = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 */
	@Override
	public void removeByR_I(long resourcePrimKey, boolean indexable) {
		for (JournalArticle journalArticle : findByR_I(resourcePrimKey,
				indexable, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByR_I(long resourcePrimKey, boolean indexable) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_R_I;

		Object[] finderArgs = new Object[] { resourcePrimKey, indexable };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_R_I_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_I_INDEXABLE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(indexable);

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

	private static final String _FINDER_COLUMN_R_I_RESOURCEPRIMKEY_2 = "journalArticle.resourcePrimKey = ? AND ";
	private static final String _FINDER_COLUMN_R_I_INDEXABLE_2 = "journalArticle.indexable = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_R_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByR_ST",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_ST",
			new String[] { Long.class.getName(), Integer.class.getName() },
			JournalArticleModelImpl.RESOURCEPRIMKEY_COLUMN_BITMASK |
			JournalArticleModelImpl.STATUS_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_ST",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_R_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByR_ST",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_ST(long resourcePrimKey, int status) {
		return findByR_ST(resourcePrimKey, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_ST(long resourcePrimKey, int status,
		int start, int end) {
		return findByR_ST(resourcePrimKey, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_ST(long resourcePrimKey, int status,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_ST;
			finderArgs = new Object[] { resourcePrimKey, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_R_ST;
			finderArgs = new Object[] {
					resourcePrimKey, status,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((resourcePrimKey != journalArticle.getResourcePrimKey()) ||
						(status != journalArticle.getStatus())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_R_ST_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_ST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(status);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByR_ST_First(long resourcePrimKey, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByR_ST_First(resourcePrimKey,
				status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByR_ST_First(long resourcePrimKey, int status,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByR_ST(resourcePrimKey, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByR_ST_Last(long resourcePrimKey, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByR_ST_Last(resourcePrimKey,
				status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByR_ST_Last(long resourcePrimKey, int status,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByR_ST(resourcePrimKey, status);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByR_ST(resourcePrimKey, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByR_ST_PrevAndNext(long id,
		long resourcePrimKey, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByR_ST_PrevAndNext(session, journalArticle,
					resourcePrimKey, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByR_ST_PrevAndNext(session, journalArticle,
					resourcePrimKey, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByR_ST_PrevAndNext(Session session,
		JournalArticle journalArticle, long resourcePrimKey, int status,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_R_ST_RESOURCEPRIMKEY_2);

		query.append(_FINDER_COLUMN_R_ST_STATUS_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourcePrimKey);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param statuses the statuses
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_ST(long resourcePrimKey, int[] statuses) {
		return findByR_ST(resourcePrimKey, statuses, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_ST(long resourcePrimKey,
		int[] statuses, int start, int end) {
		return findByR_ST(resourcePrimKey, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_ST(long resourcePrimKey,
		int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (statuses == null) {
			statuses = new int[0];
		}
		else {
			statuses = ArrayUtil.unique(statuses);
		}

		if (statuses.length == 1) {
			return findByR_ST(resourcePrimKey, statuses[0], start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] {
					resourcePrimKey, StringUtil.merge(statuses)
				};
		}
		else {
			finderArgs = new Object[] {
					resourcePrimKey, StringUtil.merge(statuses),
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_R_ST,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((resourcePrimKey != journalArticle.getResourcePrimKey()) ||
						!ArrayUtil.contains(statuses, journalArticle.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_R_ST_RESOURCEPRIMKEY_2);

			if (statuses.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_R_ST_STATUS_7);

				query.append(StringUtil.merge(statuses));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_R_ST,
					finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_R_ST,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the journal articles where resourcePrimKey = &#63; and status = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 */
	@Override
	public void removeByR_ST(long resourcePrimKey, int status) {
		for (JournalArticle journalArticle : findByR_ST(resourcePrimKey,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByR_ST(long resourcePrimKey, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_R_ST;

		Object[] finderArgs = new Object[] { resourcePrimKey, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_R_ST_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_ST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(status);

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

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63; and status = any &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param statuses the statuses
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByR_ST(long resourcePrimKey, int[] statuses) {
		if (statuses == null) {
			statuses = new int[0];
		}
		else {
			statuses = ArrayUtil.unique(statuses);
		}

		Object[] finderArgs = new Object[] {
				resourcePrimKey, StringUtil.merge(statuses)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_R_ST,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_R_ST_RESOURCEPRIMKEY_2);

			if (statuses.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_R_ST_STATUS_7);

				query.append(StringUtil.merge(statuses));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_R_ST,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_R_ST,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_R_ST_RESOURCEPRIMKEY_2 = "journalArticle.resourcePrimKey = ? AND ";
	private static final String _FINDER_COLUMN_R_ST_STATUS_2 = "journalArticle.status = ?";
	private static final String _FINDER_COLUMN_R_ST_STATUS_7 = "journalArticle.status IN (";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.USERID_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the journal articles where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_U(long groupId, long userId) {
		return findByG_U(groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_U(long groupId, long userId, int start,
		int end) {
		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U;
			finderArgs = new Object[] { groupId, userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U;
			finderArgs = new Object[] {
					groupId, userId,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						(userId != journalArticle.getUserId())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_U_First(long groupId, long userId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_U_First(groupId, userId,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_U_First(long groupId, long userId,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByG_U(groupId, userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_U_Last(long groupId, long userId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_U_Last(groupId, userId,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_U_Last(long groupId, long userId,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByG_U(groupId, userId);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByG_U(groupId, userId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByG_U_PrevAndNext(long id, long groupId,
		long userId, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_U_PrevAndNext(session, journalArticle, groupId,
					userId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_U_PrevAndNext(session, journalArticle, groupId,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_U_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, long userId,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_U(long groupId, long userId) {
		return filterFindByG_U(groupId, userId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_U(long groupId, long userId,
		int start, int end) {
		return filterFindByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_U(long groupId, long userId,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U(groupId, userId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByG_U_PrevAndNext(long id, long groupId,
		long userId, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_PrevAndNext(id, groupId, userId, orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByG_U_PrevAndNext(session, journalArticle,
					groupId, userId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByG_U_PrevAndNext(session, journalArticle,
					groupId, userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle filterGetByG_U_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, long userId,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByG_U(long groupId, long userId) {
		for (JournalArticle journalArticle : findByG_U(groupId, userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_U(long groupId, long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U;

		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_U(long groupId, long userId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U(groupId, userId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "journalArticle.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F",
			new String[] { Long.class.getName(), Long.class.getName() },
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.FOLDERID_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the journal articles where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F(long groupId, long folderId) {
		return findByG_F(groupId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F(long groupId, long folderId,
		int start, int end) {
		return findByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F(long groupId, long folderId,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F;
			finderArgs = new Object[] { groupId, folderId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F;
			finderArgs = new Object[] {
					groupId, folderId,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						(folderId != journalArticle.getFolderId())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_F_First(long groupId, long folderId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_F_First(groupId, folderId,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_F_First(long groupId, long folderId,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByG_F(groupId, folderId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_F_Last(long groupId, long folderId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_F_Last(groupId, folderId,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_F_Last(long groupId, long folderId,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByG_F(groupId, folderId);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByG_F(groupId, folderId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByG_F_PrevAndNext(long id, long groupId,
		long folderId, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_F_PrevAndNext(session, journalArticle, groupId,
					folderId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_F_PrevAndNext(session, journalArticle, groupId,
					folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_F_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, long folderId,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F(long groupId, long folderId) {
		return filterFindByG_F(groupId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F(long groupId, long folderId,
		int start, int end) {
		return filterFindByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F(long groupId, long folderId,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F(groupId, folderId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByG_F_PrevAndNext(long id, long groupId,
		long folderId, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_PrevAndNext(id, groupId, folderId,
				orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByG_F_PrevAndNext(session, journalArticle,
					groupId, folderId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByG_F_PrevAndNext(session, journalArticle,
					groupId, folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle filterGetByG_F_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, long folderId,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F(long groupId, long[] folderIds) {
		return filterFindByG_F(groupId, folderIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F(long groupId, long[] folderIds,
		int start, int end) {
		return filterFindByG_F(groupId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F(long groupId, long[] folderIds,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F(groupId, folderIds, start, end, orderByComparator);
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else {
			folderIds = ArrayUtil.unique(folderIds);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		if (folderIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_7);

			query.append(StringUtil.merge(folderIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F(long groupId, long[] folderIds) {
		return findByG_F(groupId, folderIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F(long groupId, long[] folderIds,
		int start, int end) {
		return findByG_F(groupId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F(long groupId, long[] folderIds,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		if (folderIds == null) {
			folderIds = new long[0];
		}
		else {
			folderIds = ArrayUtil.unique(folderIds);
		}

		if (folderIds.length == 1) {
			return findByG_F(groupId, folderIds[0], start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { groupId, StringUtil.merge(folderIds) };
		}
		else {
			finderArgs = new Object[] {
					groupId, StringUtil.merge(folderIds),
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						!ArrayUtil.contains(folderIds,
							journalArticle.getFolderId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			if (folderIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_F_FOLDERID_7);

				query.append(StringUtil.merge(folderIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F,
					finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and folderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 */
	@Override
	public void removeByG_F(long groupId, long folderId) {
		for (JournalArticle journalArticle : findByG_F(groupId, folderId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_F(long groupId, long folderId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F;

		Object[] finderArgs = new Object[] { groupId, folderId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

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

	/**
	 * Returns the number of journal articles where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_F(long groupId, long[] folderIds) {
		if (folderIds == null) {
			folderIds = new long[0];
		}
		else {
			folderIds = ArrayUtil.unique(folderIds);
		}

		Object[] finderArgs = new Object[] { groupId, StringUtil.merge(folderIds) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			if (folderIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_F_FOLDERID_7);

				query.append(StringUtil.merge(folderIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_F(long groupId, long folderId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_F(long groupId, long[] folderIds) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderIds);
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else {
			folderIds = ArrayUtil.unique(folderIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		if (folderIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_7);

			query.append(StringUtil.merge(folderIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FOLDERID_2 = "journalArticle.folderId = ?";
	private static final String _FINDER_COLUMN_G_F_FOLDERID_7 = "journalArticle.folderId IN (";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] { Long.class.getName(), String.class.getName() },
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A(long groupId, String articleId) {
		return findByG_A(groupId, articleId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A(long groupId, String articleId,
		int start, int end) {
		return findByG_A(groupId, articleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A(long groupId, String articleId,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] { groupId, articleId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] {
					groupId, articleId,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						!Validator.equals(articleId,
							journalArticle.getArticleId())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			boolean bindArticleId = false;

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_1);
			}
			else if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				query.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindArticleId) {
					qPos.add(articleId);
				}

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_A_First(long groupId, String articleId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_A_First(groupId, articleId,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", articleId=");
		msg.append(articleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_A_First(long groupId, String articleId,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByG_A(groupId, articleId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_A_Last(long groupId, String articleId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_A_Last(groupId, articleId,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", articleId=");
		msg.append(articleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_A_Last(long groupId, String articleId,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByG_A(groupId, articleId);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByG_A(groupId, articleId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByG_A_PrevAndNext(long id, long groupId,
		String articleId, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_A_PrevAndNext(session, journalArticle, groupId,
					articleId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_A_PrevAndNext(session, journalArticle, groupId,
					articleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_A_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String articleId,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ARTICLEID_1);
		}
		else if (articleId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			query.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindArticleId) {
			qPos.add(articleId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A(long groupId, String articleId) {
		return filterFindByG_A(groupId, articleId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A(long groupId, String articleId,
		int start, int end) {
		return filterFindByG_A(groupId, articleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A(long groupId, String articleId,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A(groupId, articleId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ARTICLEID_1);
		}
		else if (articleId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			query.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindArticleId) {
				qPos.add(articleId);
			}

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByG_A_PrevAndNext(long id, long groupId,
		String articleId, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_PrevAndNext(id, groupId, articleId,
				orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByG_A_PrevAndNext(session, journalArticle,
					groupId, articleId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByG_A_PrevAndNext(session, journalArticle,
					groupId, articleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle filterGetByG_A_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String articleId,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ARTICLEID_1);
		}
		else if (articleId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			query.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindArticleId) {
			qPos.add(articleId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 */
	@Override
	public void removeByG_A(long groupId, String articleId) {
		for (JournalArticle journalArticle : findByG_A(groupId, articleId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_A(long groupId, String articleId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_A;

		Object[] finderArgs = new Object[] { groupId, articleId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			boolean bindArticleId = false;

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_1);
			}
			else if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				query.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindArticleId) {
					qPos.add(articleId);
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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_A(long groupId, String articleId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A(groupId, articleId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ARTICLEID_1);
		}
		else if (articleId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			query.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindArticleId) {
				qPos.add(articleId);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ARTICLEID_1 = "journalArticle.articleId IS NULL";
	private static final String _FINDER_COLUMN_G_A_ARTICLEID_2 = "journalArticle.articleId = ?";
	private static final String _FINDER_COLUMN_G_A_ARTICLEID_3 = "(journalArticle.articleId IS NULL OR journalArticle.articleId = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_UT = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_UT",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_UT = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_UT",
			new String[] { Long.class.getName(), String.class.getName() },
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.URLTITLE_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_UT = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_UT",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_UT(long groupId, String urlTitle) {
		return findByG_UT(groupId, urlTitle, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_UT(long groupId, String urlTitle,
		int start, int end) {
		return findByG_UT(groupId, urlTitle, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_UT(long groupId, String urlTitle,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_UT;
			finderArgs = new Object[] { groupId, urlTitle };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_UT;
			finderArgs = new Object[] {
					groupId, urlTitle,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						!Validator.equals(urlTitle, journalArticle.getUrlTitle())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_UT_First(long groupId, String urlTitle,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_UT_First(groupId, urlTitle,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", urlTitle=");
		msg.append(urlTitle);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_UT_First(long groupId, String urlTitle,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByG_UT(groupId, urlTitle, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_UT_Last(long groupId, String urlTitle,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_UT_Last(groupId, urlTitle,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", urlTitle=");
		msg.append(urlTitle);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_UT_Last(long groupId, String urlTitle,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByG_UT(groupId, urlTitle);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByG_UT(groupId, urlTitle, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByG_UT_PrevAndNext(long id, long groupId,
		String urlTitle, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_UT_PrevAndNext(session, journalArticle, groupId,
					urlTitle, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_UT_PrevAndNext(session, journalArticle, groupId,
					urlTitle, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_UT_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String urlTitle,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

		boolean bindUrlTitle = false;

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
		}
		else if (urlTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
		}
		else {
			bindUrlTitle = true;

			query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindUrlTitle) {
			qPos.add(urlTitle);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_UT(long groupId, String urlTitle) {
		return filterFindByG_UT(groupId, urlTitle, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_UT(long groupId, String urlTitle,
		int start, int end) {
		return filterFindByG_UT(groupId, urlTitle, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_UT(long groupId, String urlTitle,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_UT(groupId, urlTitle, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

		boolean bindUrlTitle = false;

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
		}
		else if (urlTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
		}
		else {
			bindUrlTitle = true;

			query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindUrlTitle) {
				qPos.add(urlTitle);
			}

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByG_UT_PrevAndNext(long id, long groupId,
		String urlTitle, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_UT_PrevAndNext(id, groupId, urlTitle,
				orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByG_UT_PrevAndNext(session, journalArticle,
					groupId, urlTitle, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByG_UT_PrevAndNext(session, journalArticle,
					groupId, urlTitle, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle filterGetByG_UT_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String urlTitle,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

		boolean bindUrlTitle = false;

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
		}
		else if (urlTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
		}
		else {
			bindUrlTitle = true;

			query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindUrlTitle) {
			qPos.add(urlTitle);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 */
	@Override
	public void removeByG_UT(long groupId, String urlTitle) {
		for (JournalArticle journalArticle : findByG_UT(groupId, urlTitle,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_UT(long groupId, String urlTitle) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_UT;

		Object[] finderArgs = new Object[] { groupId, urlTitle };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_UT(long groupId, String urlTitle) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_UT(groupId, urlTitle);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

		boolean bindUrlTitle = false;

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
		}
		else if (urlTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
		}
		else {
			bindUrlTitle = true;

			query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindUrlTitle) {
				qPos.add(urlTitle);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_UT_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_1 = "journalArticle.urlTitle IS NULL";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_2 = "journalArticle.urlTitle = ?";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_3 = "(journalArticle.urlTitle IS NULL OR journalArticle.urlTitle = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_DDMSK = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_DDMSK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_DDMSK =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_DDMSK",
			new String[] { Long.class.getName(), String.class.getName() },
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.DDMSTRUCTUREKEY_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_DDMSK = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_DDMSK",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the journal articles where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the d d m structure key
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_DDMSK(long groupId,
		String DDMStructureKey) {
		return findByG_DDMSK(groupId, DDMStructureKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the d d m structure key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_DDMSK(long groupId,
		String DDMStructureKey, int start, int end) {
		return findByG_DDMSK(groupId, DDMStructureKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the d d m structure key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_DDMSK(long groupId,
		String DDMStructureKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_DDMSK;
			finderArgs = new Object[] { groupId, DDMStructureKey };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_DDMSK;
			finderArgs = new Object[] {
					groupId, DDMStructureKey,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						!Validator.equals(DDMStructureKey,
							journalArticle.getDDMStructureKey())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_DDMSK_GROUPID_2);

			boolean bindDDMStructureKey = false;

			if (DDMStructureKey == null) {
				query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_1);
			}
			else if (DDMStructureKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_3);
			}
			else {
				bindDDMStructureKey = true;

				query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindDDMStructureKey) {
					qPos.add(DDMStructureKey);
				}

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the d d m structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_DDMSK_First(long groupId,
		String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_DDMSK_First(groupId,
				DDMStructureKey, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", DDMStructureKey=");
		msg.append(DDMStructureKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the d d m structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_DDMSK_First(long groupId,
		String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByG_DDMSK(groupId, DDMStructureKey, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the d d m structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_DDMSK_Last(long groupId,
		String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_DDMSK_Last(groupId,
				DDMStructureKey, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", DDMStructureKey=");
		msg.append(DDMStructureKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the d d m structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_DDMSK_Last(long groupId,
		String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByG_DDMSK(groupId, DDMStructureKey);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByG_DDMSK(groupId, DDMStructureKey,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param DDMStructureKey the d d m structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByG_DDMSK_PrevAndNext(long id, long groupId,
		String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_DDMSK_PrevAndNext(session, journalArticle,
					groupId, DDMStructureKey, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_DDMSK_PrevAndNext(session, journalArticle,
					groupId, DDMStructureKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_DDMSK_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_DDMSK_GROUPID_2);

		boolean bindDDMStructureKey = false;

		if (DDMStructureKey == null) {
			query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_1);
		}
		else if (DDMStructureKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_3);
		}
		else {
			bindDDMStructureKey = true;

			query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_2);
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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindDDMStructureKey) {
			qPos.add(DDMStructureKey);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the d d m structure key
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_DDMSK(long groupId,
		String DDMStructureKey) {
		return filterFindByG_DDMSK(groupId, DDMStructureKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the d d m structure key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_DDMSK(long groupId,
		String DDMStructureKey, int start, int end) {
		return filterFindByG_DDMSK(groupId, DDMStructureKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the d d m structure key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_DDMSK(long groupId,
		String DDMStructureKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_DDMSK(groupId, DDMStructureKey, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_DDMSK_GROUPID_2);

		boolean bindDDMStructureKey = false;

		if (DDMStructureKey == null) {
			query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_1);
		}
		else if (DDMStructureKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_3);
		}
		else {
			bindDDMStructureKey = true;

			query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindDDMStructureKey) {
				qPos.add(DDMStructureKey);
			}

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param DDMStructureKey the d d m structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByG_DDMSK_PrevAndNext(long id,
		long groupId, String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_DDMSK_PrevAndNext(id, groupId, DDMStructureKey,
				orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByG_DDMSK_PrevAndNext(session, journalArticle,
					groupId, DDMStructureKey, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByG_DDMSK_PrevAndNext(session, journalArticle,
					groupId, DDMStructureKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle filterGetByG_DDMSK_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_DDMSK_GROUPID_2);

		boolean bindDDMStructureKey = false;

		if (DDMStructureKey == null) {
			query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_1);
		}
		else if (DDMStructureKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_3);
		}
		else {
			bindDDMStructureKey = true;

			query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindDDMStructureKey) {
			qPos.add(DDMStructureKey);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and DDMStructureKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the d d m structure key
	 */
	@Override
	public void removeByG_DDMSK(long groupId, String DDMStructureKey) {
		for (JournalArticle journalArticle : findByG_DDMSK(groupId,
				DDMStructureKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the d d m structure key
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_DDMSK(long groupId, String DDMStructureKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_DDMSK;

		Object[] finderArgs = new Object[] { groupId, DDMStructureKey };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_DDMSK_GROUPID_2);

			boolean bindDDMStructureKey = false;

			if (DDMStructureKey == null) {
				query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_1);
			}
			else if (DDMStructureKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_3);
			}
			else {
				bindDDMStructureKey = true;

				query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindDDMStructureKey) {
					qPos.add(DDMStructureKey);
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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the d d m structure key
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_DDMSK(long groupId, String DDMStructureKey) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_DDMSK(groupId, DDMStructureKey);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_DDMSK_GROUPID_2);

		boolean bindDDMStructureKey = false;

		if (DDMStructureKey == null) {
			query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_1);
		}
		else if (DDMStructureKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_3);
		}
		else {
			bindDDMStructureKey = true;

			query.append(_FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindDDMStructureKey) {
				qPos.add(DDMStructureKey);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_DDMSK_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_1 = "journalArticle.DDMStructureKey IS NULL";
	private static final String _FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_2 = "journalArticle.DDMStructureKey = ?";
	private static final String _FINDER_COLUMN_G_DDMSK_DDMSTRUCTUREKEY_3 = "(journalArticle.DDMStructureKey IS NULL OR journalArticle.DDMStructureKey = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_DDMTK = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_DDMTK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_DDMTK =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_DDMTK",
			new String[] { Long.class.getName(), String.class.getName() },
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.DDMTEMPLATEKEY_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_DDMTK = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_DDMTK",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the d d m template key
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_DDMTK(long groupId,
		String DDMTemplateKey) {
		return findByG_DDMTK(groupId, DDMTemplateKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the d d m template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_DDMTK(long groupId,
		String DDMTemplateKey, int start, int end) {
		return findByG_DDMTK(groupId, DDMTemplateKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the d d m template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_DDMTK(long groupId,
		String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_DDMTK;
			finderArgs = new Object[] { groupId, DDMTemplateKey };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_DDMTK;
			finderArgs = new Object[] {
					groupId, DDMTemplateKey,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						!Validator.equals(DDMTemplateKey,
							journalArticle.getDDMTemplateKey())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_DDMTK_GROUPID_2);

			boolean bindDDMTemplateKey = false;

			if (DDMTemplateKey == null) {
				query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_1);
			}
			else if (DDMTemplateKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_3);
			}
			else {
				bindDDMTemplateKey = true;

				query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindDDMTemplateKey) {
					qPos.add(DDMTemplateKey);
				}

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_DDMTK_First(long groupId,
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_DDMTK_First(groupId,
				DDMTemplateKey, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", DDMTemplateKey=");
		msg.append(DDMTemplateKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_DDMTK_First(long groupId,
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByG_DDMTK(groupId, DDMTemplateKey, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_DDMTK_Last(long groupId,
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_DDMTK_Last(groupId,
				DDMTemplateKey, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", DDMTemplateKey=");
		msg.append(DDMTemplateKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_DDMTK_Last(long groupId,
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByG_DDMTK(groupId, DDMTemplateKey);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByG_DDMTK(groupId, DDMTemplateKey,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByG_DDMTK_PrevAndNext(long id, long groupId,
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_DDMTK_PrevAndNext(session, journalArticle,
					groupId, DDMTemplateKey, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_DDMTK_PrevAndNext(session, journalArticle,
					groupId, DDMTemplateKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_DDMTK_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_DDMTK_GROUPID_2);

		boolean bindDDMTemplateKey = false;

		if (DDMTemplateKey == null) {
			query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_1);
		}
		else if (DDMTemplateKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_3);
		}
		else {
			bindDDMTemplateKey = true;

			query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_2);
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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindDDMTemplateKey) {
			qPos.add(DDMTemplateKey);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the d d m template key
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_DDMTK(long groupId,
		String DDMTemplateKey) {
		return filterFindByG_DDMTK(groupId, DDMTemplateKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the d d m template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_DDMTK(long groupId,
		String DDMTemplateKey, int start, int end) {
		return filterFindByG_DDMTK(groupId, DDMTemplateKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the d d m template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_DDMTK(long groupId,
		String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_DDMTK(groupId, DDMTemplateKey, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_DDMTK_GROUPID_2);

		boolean bindDDMTemplateKey = false;

		if (DDMTemplateKey == null) {
			query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_1);
		}
		else if (DDMTemplateKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_3);
		}
		else {
			bindDDMTemplateKey = true;

			query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindDDMTemplateKey) {
				qPos.add(DDMTemplateKey);
			}

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByG_DDMTK_PrevAndNext(long id,
		long groupId, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_DDMTK_PrevAndNext(id, groupId, DDMTemplateKey,
				orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByG_DDMTK_PrevAndNext(session, journalArticle,
					groupId, DDMTemplateKey, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByG_DDMTK_PrevAndNext(session, journalArticle,
					groupId, DDMTemplateKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle filterGetByG_DDMTK_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_DDMTK_GROUPID_2);

		boolean bindDDMTemplateKey = false;

		if (DDMTemplateKey == null) {
			query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_1);
		}
		else if (DDMTemplateKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_3);
		}
		else {
			bindDDMTemplateKey = true;

			query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindDDMTemplateKey) {
			qPos.add(DDMTemplateKey);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and DDMTemplateKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the d d m template key
	 */
	@Override
	public void removeByG_DDMTK(long groupId, String DDMTemplateKey) {
		for (JournalArticle journalArticle : findByG_DDMTK(groupId,
				DDMTemplateKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the d d m template key
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_DDMTK(long groupId, String DDMTemplateKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_DDMTK;

		Object[] finderArgs = new Object[] { groupId, DDMTemplateKey };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_DDMTK_GROUPID_2);

			boolean bindDDMTemplateKey = false;

			if (DDMTemplateKey == null) {
				query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_1);
			}
			else if (DDMTemplateKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_3);
			}
			else {
				bindDDMTemplateKey = true;

				query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindDDMTemplateKey) {
					qPos.add(DDMTemplateKey);
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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the d d m template key
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_DDMTK(long groupId, String DDMTemplateKey) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_DDMTK(groupId, DDMTemplateKey);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_DDMTK_GROUPID_2);

		boolean bindDDMTemplateKey = false;

		if (DDMTemplateKey == null) {
			query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_1);
		}
		else if (DDMTemplateKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_3);
		}
		else {
			bindDDMTemplateKey = true;

			query.append(_FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindDDMTemplateKey) {
				qPos.add(DDMTemplateKey);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_DDMTK_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_1 = "journalArticle.DDMTemplateKey IS NULL";
	private static final String _FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_2 = "journalArticle.DDMTemplateKey = ?";
	private static final String _FINDER_COLUMN_G_DDMTK_DDMTEMPLATEKEY_3 = "(journalArticle.DDMTemplateKey IS NULL OR journalArticle.DDMTemplateKey = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_L = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_L",
			new String[] { Long.class.getName(), String.class.getName() },
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.LAYOUTUUID_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_L = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_L",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the journal articles where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_L(long groupId, String layoutUuid) {
		return findByG_L(groupId, layoutUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_L(long groupId, String layoutUuid,
		int start, int end) {
		return findByG_L(groupId, layoutUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_L(long groupId, String layoutUuid,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L;
			finderArgs = new Object[] { groupId, layoutUuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_L;
			finderArgs = new Object[] {
					groupId, layoutUuid,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						!Validator.equals(layoutUuid,
							journalArticle.getLayoutUuid())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_L_GROUPID_2);

			boolean bindLayoutUuid = false;

			if (layoutUuid == null) {
				query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_1);
			}
			else if (layoutUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_3);
			}
			else {
				bindLayoutUuid = true;

				query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindLayoutUuid) {
					qPos.add(layoutUuid);
				}

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_L_First(long groupId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_L_First(groupId, layoutUuid,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutUuid=");
		msg.append(layoutUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_L_First(long groupId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByG_L(groupId, layoutUuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_L_Last(long groupId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_L_Last(groupId, layoutUuid,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutUuid=");
		msg.append(layoutUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_L_Last(long groupId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByG_L(groupId, layoutUuid);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByG_L(groupId, layoutUuid, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByG_L_PrevAndNext(long id, long groupId,
		String layoutUuid, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_L_PrevAndNext(session, journalArticle, groupId,
					layoutUuid, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_L_PrevAndNext(session, journalArticle, groupId,
					layoutUuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_L_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_L_GROUPID_2);

		boolean bindLayoutUuid = false;

		if (layoutUuid == null) {
			query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_1);
		}
		else if (layoutUuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_2);
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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindLayoutUuid) {
			qPos.add(layoutUuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_L(long groupId, String layoutUuid) {
		return filterFindByG_L(groupId, layoutUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_L(long groupId,
		String layoutUuid, int start, int end) {
		return filterFindByG_L(groupId, layoutUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_L(long groupId,
		String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L(groupId, layoutUuid, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_L_GROUPID_2);

		boolean bindLayoutUuid = false;

		if (layoutUuid == null) {
			query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_1);
		}
		else if (layoutUuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindLayoutUuid) {
				qPos.add(layoutUuid);
			}

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByG_L_PrevAndNext(long id, long groupId,
		String layoutUuid, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L_PrevAndNext(id, groupId, layoutUuid,
				orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByG_L_PrevAndNext(session, journalArticle,
					groupId, layoutUuid, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByG_L_PrevAndNext(session, journalArticle,
					groupId, layoutUuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle filterGetByG_L_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_L_GROUPID_2);

		boolean bindLayoutUuid = false;

		if (layoutUuid == null) {
			query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_1);
		}
		else if (layoutUuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindLayoutUuid) {
			qPos.add(layoutUuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and layoutUuid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 */
	@Override
	public void removeByG_L(long groupId, String layoutUuid) {
		for (JournalArticle journalArticle : findByG_L(groupId, layoutUuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_L(long groupId, String layoutUuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_L;

		Object[] finderArgs = new Object[] { groupId, layoutUuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_L_GROUPID_2);

			boolean bindLayoutUuid = false;

			if (layoutUuid == null) {
				query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_1);
			}
			else if (layoutUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_3);
			}
			else {
				bindLayoutUuid = true;

				query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindLayoutUuid) {
					qPos.add(layoutUuid);
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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_L(long groupId, String layoutUuid) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_L(groupId, layoutUuid);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_L_GROUPID_2);

		boolean bindLayoutUuid = false;

		if (layoutUuid == null) {
			query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_1);
		}
		else if (layoutUuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			query.append(_FINDER_COLUMN_G_L_LAYOUTUUID_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindLayoutUuid) {
				qPos.add(layoutUuid);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_L_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_L_LAYOUTUUID_1 = "journalArticle.layoutUuid IS NULL";
	private static final String _FINDER_COLUMN_G_L_LAYOUTUUID_2 = "journalArticle.layoutUuid = ?";
	private static final String _FINDER_COLUMN_G_L_LAYOUTUUID_3 = "(journalArticle.layoutUuid IS NULL OR journalArticle.layoutUuid = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_ST",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_ST",
			new String[] { Long.class.getName(), Integer.class.getName() },
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.STATUS_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_ST",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ST(long groupId, int status) {
		return findByG_ST(groupId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ST(long groupId, int status, int start,
		int end) {
		return findByG_ST(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_ST(long groupId, int status, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_ST;
			finderArgs = new Object[] { groupId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_ST;
			finderArgs = new Object[] {
					groupId, status,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						(status != journalArticle.getStatus())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_ST_GROUPID_2);

			query.append(_FINDER_COLUMN_G_ST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_ST_First(long groupId, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_ST_First(groupId, status,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_ST_First(long groupId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByG_ST(groupId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_ST_Last(long groupId, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_ST_Last(groupId, status,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_ST_Last(long groupId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByG_ST(groupId, status);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByG_ST(groupId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByG_ST_PrevAndNext(long id, long groupId,
		int status, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_ST_PrevAndNext(session, journalArticle, groupId,
					status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_ST_PrevAndNext(session, journalArticle, groupId,
					status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_ST_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, int status,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_ST_GROUPID_2);

		query.append(_FINDER_COLUMN_G_ST_STATUS_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_ST(long groupId, int status) {
		return filterFindByG_ST(groupId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_ST(long groupId, int status,
		int start, int end) {
		return filterFindByG_ST(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_ST(long groupId, int status,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_ST(groupId, status, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_ST_GROUPID_2);

		query.append(_FINDER_COLUMN_G_ST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByG_ST_PrevAndNext(long id, long groupId,
		int status, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_ST_PrevAndNext(id, groupId, status, orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByG_ST_PrevAndNext(session, journalArticle,
					groupId, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByG_ST_PrevAndNext(session, journalArticle,
					groupId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle filterGetByG_ST_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, int status,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_ST_GROUPID_2);

		query.append(_FINDER_COLUMN_G_ST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	@Override
	public void removeByG_ST(long groupId, int status) {
		for (JournalArticle journalArticle : findByG_ST(groupId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_ST(long groupId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_ST;

		Object[] finderArgs = new Object[] { groupId, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_ST_GROUPID_2);

			query.append(_FINDER_COLUMN_G_ST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_ST(long groupId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_ST(groupId, status);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_ST_GROUPID_2);

		query.append(_FINDER_COLUMN_G_ST_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_ST_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_ST_STATUS_2 = "journalArticle.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_V = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_V",
			new String[] {
				Long.class.getName(), Double.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_V = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_V",
			new String[] { Long.class.getName(), Double.class.getName() },
			JournalArticleModelImpl.COMPANYID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_V = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_V",
			new String[] { Long.class.getName(), Double.class.getName() });

	/**
	 * Returns all the journal articles where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_V(long companyId, double version) {
		return findByC_V(companyId, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_V(long companyId, double version,
		int start, int end) {
		return findByC_V(companyId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_V(long companyId, double version,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_V;
			finderArgs = new Object[] { companyId, version };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_V;
			finderArgs = new Object[] {
					companyId, version,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((companyId != journalArticle.getCompanyId()) ||
						(version != journalArticle.getVersion())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_C_V_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_V_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(version);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByC_V_First(long companyId, double version,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByC_V_First(companyId, version,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", version=");
		msg.append(version);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByC_V_First(long companyId, double version,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByC_V(companyId, version, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByC_V_Last(long companyId, double version,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByC_V_Last(companyId, version,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", version=");
		msg.append(version);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByC_V_Last(long companyId, double version,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByC_V(companyId, version);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByC_V(companyId, version, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByC_V_PrevAndNext(long id, long companyId,
		double version, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByC_V_PrevAndNext(session, journalArticle, companyId,
					version, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByC_V_PrevAndNext(session, journalArticle, companyId,
					version, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByC_V_PrevAndNext(Session session,
		JournalArticle journalArticle, long companyId, double version,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_C_V_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_V_VERSION_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(version);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where companyId = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 */
	@Override
	public void removeByC_V(long companyId, double version) {
		for (JournalArticle journalArticle : findByC_V(companyId, version,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByC_V(long companyId, double version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_V;

		Object[] finderArgs = new Object[] { companyId, version };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_C_V_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_V_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_C_V_COMPANYID_2 = "journalArticle.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_V_VERSION_2 = "journalArticle.version = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_ST",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_ST",
			new String[] { Long.class.getName(), Integer.class.getName() },
			JournalArticleModelImpl.COMPANYID_COLUMN_BITMASK |
			JournalArticleModelImpl.STATUS_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_ST",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the journal articles where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_ST(long companyId, int status) {
		return findByC_ST(companyId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_ST(long companyId, int status,
		int start, int end) {
		return findByC_ST(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_ST(long companyId, int status,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_ST;
			finderArgs = new Object[] { companyId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_ST;
			finderArgs = new Object[] {
					companyId, status,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((companyId != journalArticle.getCompanyId()) ||
						(status != journalArticle.getStatus())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_C_ST_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_ST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByC_ST_First(long companyId, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByC_ST_First(companyId, status,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByC_ST_First(long companyId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByC_ST(companyId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByC_ST_Last(long companyId, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByC_ST_Last(companyId, status,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByC_ST_Last(long companyId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByC_ST(companyId, status);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByC_ST(companyId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByC_ST_PrevAndNext(long id, long companyId,
		int status, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByC_ST_PrevAndNext(session, journalArticle,
					companyId, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByC_ST_PrevAndNext(session, journalArticle,
					companyId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByC_ST_PrevAndNext(Session session,
		JournalArticle journalArticle, long companyId, int status,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_C_ST_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_ST_STATUS_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_ST(long companyId, int status) {
		for (JournalArticle journalArticle : findByC_ST(companyId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByC_ST(long companyId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_ST;

		Object[] finderArgs = new Object[] { companyId, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_C_ST_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_ST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_C_ST_COMPANYID_2 = "journalArticle.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_ST_STATUS_2 = "journalArticle.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_NOTST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_NotST",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_NOTST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_NotST",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the journal articles where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_NotST(long companyId, int status) {
		return findByC_NotST(companyId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_NotST(long companyId, int status,
		int start, int end) {
		return findByC_NotST(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_NotST(long companyId, int status,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_NOTST;
		finderArgs = new Object[] {
				companyId, status,
				
				start, end, orderByComparator
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((companyId != journalArticle.getCompanyId()) ||
						(status == journalArticle.getStatus())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_C_NOTST_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_NOTST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByC_NotST_First(long companyId, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByC_NotST_First(companyId, status,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByC_NotST_First(long companyId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByC_NotST(companyId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByC_NotST_Last(long companyId, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByC_NotST_Last(companyId, status,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByC_NotST_Last(long companyId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByC_NotST(companyId, status);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByC_NotST(companyId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByC_NotST_PrevAndNext(long id, long companyId,
		int status, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByC_NotST_PrevAndNext(session, journalArticle,
					companyId, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByC_NotST_PrevAndNext(session, journalArticle,
					companyId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByC_NotST_PrevAndNext(Session session,
		JournalArticle journalArticle, long companyId, int status,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_C_NOTST_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_NOTST_STATUS_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_NotST(long companyId, int status) {
		for (JournalArticle journalArticle : findByC_NotST(companyId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByC_NotST(long companyId, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_NOTST;

		Object[] finderArgs = new Object[] { companyId, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_C_NOTST_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_NOTST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_C_NOTST_COMPANYID_2 = "journalArticle.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_NOTST_STATUS_2 = "journalArticle.status != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_DDMTK = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_DDMTK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_DDMTK =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_DDMTK",
			new String[] { Long.class.getName(), String.class.getName() },
			JournalArticleModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			JournalArticleModelImpl.DDMTEMPLATEKEY_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_DDMTK = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_DDMTK",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the journal articles where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_DDMTK(long classNameId,
		String DDMTemplateKey) {
		return findByC_DDMTK(classNameId, DDMTemplateKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_DDMTK(long classNameId,
		String DDMTemplateKey, int start, int end) {
		return findByC_DDMTK(classNameId, DDMTemplateKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_DDMTK(long classNameId,
		String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_DDMTK;
			finderArgs = new Object[] { classNameId, DDMTemplateKey };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_DDMTK;
			finderArgs = new Object[] {
					classNameId, DDMTemplateKey,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((classNameId != journalArticle.getClassNameId()) ||
						!Validator.equals(DDMTemplateKey,
							journalArticle.getDDMTemplateKey())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_C_DDMTK_CLASSNAMEID_2);

			boolean bindDDMTemplateKey = false;

			if (DDMTemplateKey == null) {
				query.append(_FINDER_COLUMN_C_DDMTK_DDMTEMPLATEKEY_1);
			}
			else if (DDMTemplateKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_DDMTK_DDMTEMPLATEKEY_3);
			}
			else {
				bindDDMTemplateKey = true;

				query.append(_FINDER_COLUMN_C_DDMTK_DDMTEMPLATEKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				if (bindDDMTemplateKey) {
					qPos.add(DDMTemplateKey);
				}

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByC_DDMTK_First(long classNameId,
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByC_DDMTK_First(classNameId,
				DDMTemplateKey, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", DDMTemplateKey=");
		msg.append(DDMTemplateKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByC_DDMTK_First(long classNameId,
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByC_DDMTK(classNameId, DDMTemplateKey,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByC_DDMTK_Last(long classNameId,
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByC_DDMTK_Last(classNameId,
				DDMTemplateKey, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", DDMTemplateKey=");
		msg.append(DDMTemplateKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByC_DDMTK_Last(long classNameId,
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByC_DDMTK(classNameId, DDMTemplateKey);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByC_DDMTK(classNameId, DDMTemplateKey,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByC_DDMTK_PrevAndNext(long id,
		long classNameId, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByC_DDMTK_PrevAndNext(session, journalArticle,
					classNameId, DDMTemplateKey, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByC_DDMTK_PrevAndNext(session, journalArticle,
					classNameId, DDMTemplateKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByC_DDMTK_PrevAndNext(Session session,
		JournalArticle journalArticle, long classNameId, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_C_DDMTK_CLASSNAMEID_2);

		boolean bindDDMTemplateKey = false;

		if (DDMTemplateKey == null) {
			query.append(_FINDER_COLUMN_C_DDMTK_DDMTEMPLATEKEY_1);
		}
		else if (DDMTemplateKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_C_DDMTK_DDMTEMPLATEKEY_3);
		}
		else {
			bindDDMTemplateKey = true;

			query.append(_FINDER_COLUMN_C_DDMTK_DDMTEMPLATEKEY_2);
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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		if (bindDDMTemplateKey) {
			qPos.add(DDMTemplateKey);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where classNameId = &#63; and DDMTemplateKey = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 */
	@Override
	public void removeByC_DDMTK(long classNameId, String DDMTemplateKey) {
		for (JournalArticle journalArticle : findByC_DDMTK(classNameId,
				DDMTemplateKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByC_DDMTK(long classNameId, String DDMTemplateKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_DDMTK;

		Object[] finderArgs = new Object[] { classNameId, DDMTemplateKey };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_C_DDMTK_CLASSNAMEID_2);

			boolean bindDDMTemplateKey = false;

			if (DDMTemplateKey == null) {
				query.append(_FINDER_COLUMN_C_DDMTK_DDMTEMPLATEKEY_1);
			}
			else if (DDMTemplateKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_DDMTK_DDMTEMPLATEKEY_3);
			}
			else {
				bindDDMTemplateKey = true;

				query.append(_FINDER_COLUMN_C_DDMTK_DDMTEMPLATEKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				if (bindDDMTemplateKey) {
					qPos.add(DDMTemplateKey);
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

	private static final String _FINDER_COLUMN_C_DDMTK_CLASSNAMEID_2 = "journalArticle.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_DDMTK_DDMTEMPLATEKEY_1 = "journalArticle.DDMTemplateKey IS NULL";
	private static final String _FINDER_COLUMN_C_DDMTK_DDMTEMPLATEKEY_2 = "journalArticle.DDMTemplateKey = ?";
	private static final String _FINDER_COLUMN_C_DDMTK_DDMTEMPLATEKEY_3 = "(journalArticle.DDMTemplateKey IS NULL OR journalArticle.DDMTemplateKey = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LTD_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLtD_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_LTD_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtD_S",
			new String[] { Date.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the journal articles where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByLtD_S(Date displayDate, int status) {
		return findByLtD_S(displayDate, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByLtD_S(Date displayDate, int status,
		int start, int end) {
		return findByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByLtD_S(Date displayDate, int status,
		int start, int end, OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LTD_S;
		finderArgs = new Object[] {
				displayDate, status,
				
				start, end, orderByComparator
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((displayDate.getTime() <= journalArticle.getDisplayDate()
																.getTime()) ||
						(status != journalArticle.getStatus())) {
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

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_LTD_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByLtD_S_First(Date displayDate, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByLtD_S_First(displayDate, status,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByLtD_S_First(Date displayDate, int status,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByLtD_S(displayDate, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByLtD_S_Last(Date displayDate, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByLtD_S_Last(displayDate, status,
				orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByLtD_S_Last(Date displayDate, int status,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByLtD_S(displayDate, status);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByLtD_S(displayDate, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByLtD_S_PrevAndNext(long id, Date displayDate,
		int status, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByLtD_S_PrevAndNext(session, journalArticle,
					displayDate, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByLtD_S_PrevAndNext(session, journalArticle,
					displayDate, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByLtD_S_PrevAndNext(Session session,
		JournalArticle journalArticle, Date displayDate, int status,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_LTD_S_STATUS_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByLtD_S(Date displayDate, int status) {
		for (JournalArticle journalArticle : findByLtD_S(displayDate, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByLtD_S(Date displayDate, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_LTD_S;

		Object[] finderArgs = new Object[] { displayDate, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_LTD_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_1 = "journalArticle.displayDate < NULL AND ";
	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_2 = "journalArticle.displayDate < ? AND ";
	private static final String _FINDER_COLUMN_LTD_S_STATUS_2 = "journalArticle.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_R_I_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByR_I_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_I_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_I_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			JournalArticleModelImpl.RESOURCEPRIMKEY_COLUMN_BITMASK |
			JournalArticleModelImpl.INDEXABLE_COLUMN_BITMASK |
			JournalArticleModelImpl.STATUS_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_I_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_I_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_R_I_S = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByR_I_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I_S(long resourcePrimKey,
		boolean indexable, int status) {
		return findByR_I_S(resourcePrimKey, indexable, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I_S(long resourcePrimKey,
		boolean indexable, int status, int start, int end) {
		return findByR_I_S(resourcePrimKey, indexable, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I_S(long resourcePrimKey,
		boolean indexable, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_I_S;
			finderArgs = new Object[] { resourcePrimKey, indexable, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_R_I_S;
			finderArgs = new Object[] {
					resourcePrimKey, indexable, status,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((resourcePrimKey != journalArticle.getResourcePrimKey()) ||
						(indexable != journalArticle.getIndexable()) ||
						(status != journalArticle.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_R_I_S_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_I_S_INDEXABLE_2);

			query.append(_FINDER_COLUMN_R_I_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(indexable);

				qPos.add(status);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByR_I_S_First(long resourcePrimKey,
		boolean indexable, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByR_I_S_First(resourcePrimKey,
				indexable, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(", indexable=");
		msg.append(indexable);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByR_I_S_First(long resourcePrimKey,
		boolean indexable, int status,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByR_I_S(resourcePrimKey, indexable,
				status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByR_I_S_Last(long resourcePrimKey,
		boolean indexable, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByR_I_S_Last(resourcePrimKey,
				indexable, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(", indexable=");
		msg.append(indexable);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByR_I_S_Last(long resourcePrimKey,
		boolean indexable, int status,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByR_I_S(resourcePrimKey, indexable, status);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByR_I_S(resourcePrimKey, indexable,
				status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByR_I_S_PrevAndNext(long id,
		long resourcePrimKey, boolean indexable, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByR_I_S_PrevAndNext(session, journalArticle,
					resourcePrimKey, indexable, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByR_I_S_PrevAndNext(session, journalArticle,
					resourcePrimKey, indexable, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByR_I_S_PrevAndNext(Session session,
		JournalArticle journalArticle, long resourcePrimKey, boolean indexable,
		int status, OrderByComparator<JournalArticle> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_R_I_S_RESOURCEPRIMKEY_2);

		query.append(_FINDER_COLUMN_R_I_S_INDEXABLE_2);

		query.append(_FINDER_COLUMN_R_I_S_STATUS_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourcePrimKey);

		qPos.add(indexable);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param statuses the statuses
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I_S(long resourcePrimKey,
		boolean indexable, int[] statuses) {
		return findByR_I_S(resourcePrimKey, indexable, statuses,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I_S(long resourcePrimKey,
		boolean indexable, int[] statuses, int start, int end) {
		return findByR_I_S(resourcePrimKey, indexable, statuses, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByR_I_S(long resourcePrimKey,
		boolean indexable, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (statuses == null) {
			statuses = new int[0];
		}
		else {
			statuses = ArrayUtil.unique(statuses);
		}

		if (statuses.length == 1) {
			return findByR_I_S(resourcePrimKey, indexable, statuses[0], start,
				end, orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] {
					resourcePrimKey, indexable, StringUtil.merge(statuses)
				};
		}
		else {
			finderArgs = new Object[] {
					resourcePrimKey, indexable, StringUtil.merge(statuses),
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_R_I_S,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((resourcePrimKey != journalArticle.getResourcePrimKey()) ||
						(indexable != journalArticle.getIndexable()) ||
						!ArrayUtil.contains(statuses, journalArticle.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_R_I_S_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_I_S_INDEXABLE_2);

			if (statuses.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_R_I_S_STATUS_7);

				query.append(StringUtil.merge(statuses));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(indexable);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_R_I_S,
					finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_R_I_S,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 */
	@Override
	public void removeByR_I_S(long resourcePrimKey, boolean indexable,
		int status) {
		for (JournalArticle journalArticle : findByR_I_S(resourcePrimKey,
				indexable, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByR_I_S(long resourcePrimKey, boolean indexable, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_R_I_S;

		Object[] finderArgs = new Object[] { resourcePrimKey, indexable, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_R_I_S_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_I_S_INDEXABLE_2);

			query.append(_FINDER_COLUMN_R_I_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(indexable);

				qPos.add(status);

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

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = any &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param statuses the statuses
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByR_I_S(long resourcePrimKey, boolean indexable,
		int[] statuses) {
		if (statuses == null) {
			statuses = new int[0];
		}
		else {
			statuses = ArrayUtil.unique(statuses);
		}

		Object[] finderArgs = new Object[] {
				resourcePrimKey, indexable, StringUtil.merge(statuses)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_R_I_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_R_I_S_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_I_S_INDEXABLE_2);

			if (statuses.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_R_I_S_STATUS_7);

				query.append(StringUtil.merge(statuses));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(indexable);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_R_I_S,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_R_I_S,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_R_I_S_RESOURCEPRIMKEY_2 = "journalArticle.resourcePrimKey = ? AND ";
	private static final String _FINDER_COLUMN_R_I_S_INDEXABLE_2 = "journalArticle.indexable = ? AND ";
	private static final String _FINDER_COLUMN_R_I_S_STATUS_2 = "journalArticle.status = ?";
	private static final String _FINDER_COLUMN_R_I_S_STATUS_7 = "journalArticle.status IN (";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_C = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_U_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.USERID_COLUMN_BITMASK |
			JournalArticleModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_C = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_U_C(long groupId, long userId,
		long classNameId) {
		return findByG_U_C(groupId, userId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_U_C(long groupId, long userId,
		long classNameId, int start, int end) {
		return findByG_U_C(groupId, userId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_U_C(long groupId, long userId,
		long classNameId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C;
			finderArgs = new Object[] { groupId, userId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_C;
			finderArgs = new Object[] {
					groupId, userId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						(userId != journalArticle.getUserId()) ||
						(classNameId != journalArticle.getClassNameId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_U_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_C_USERID_2);

			query.append(_FINDER_COLUMN_G_U_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_U_C_First(long groupId, long userId,
		long classNameId, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_U_C_First(groupId, userId,
				classNameId, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_U_C_First(long groupId, long userId,
		long classNameId, OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByG_U_C(groupId, userId, classNameId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_U_C_Last(long groupId, long userId,
		long classNameId, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_U_C_Last(groupId, userId,
				classNameId, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_U_C_Last(long groupId, long userId,
		long classNameId, OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByG_U_C(groupId, userId, classNameId);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByG_U_C(groupId, userId, classNameId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByG_U_C_PrevAndNext(long id, long groupId,
		long userId, long classNameId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_U_C_PrevAndNext(session, journalArticle, groupId,
					userId, classNameId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_U_C_PrevAndNext(session, journalArticle, groupId,
					userId, classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_U_C_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, long userId,
		long classNameId, OrderByComparator<JournalArticle> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_U_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_C_USERID_2);

		query.append(_FINDER_COLUMN_G_U_C_CLASSNAMEID_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_U_C(long groupId, long userId,
		long classNameId) {
		return filterFindByG_U_C(groupId, userId, classNameId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_U_C(long groupId, long userId,
		long classNameId, int start, int end) {
		return filterFindByG_U_C(groupId, userId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_U_C(long groupId, long userId,
		long classNameId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_C(groupId, userId, classNameId, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_C_USERID_2);

		query.append(_FINDER_COLUMN_G_U_C_CLASSNAMEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(classNameId);

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByG_U_C_PrevAndNext(long id,
		long groupId, long userId, long classNameId,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_C_PrevAndNext(id, groupId, userId, classNameId,
				orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByG_U_C_PrevAndNext(session, journalArticle,
					groupId, userId, classNameId, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByG_U_C_PrevAndNext(session, journalArticle,
					groupId, userId, classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle filterGetByG_U_C_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, long userId,
		long classNameId, OrderByComparator<JournalArticle> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_C_USERID_2);

		query.append(_FINDER_COLUMN_G_U_C_CLASSNAMEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByG_U_C(long groupId, long userId, long classNameId) {
		for (JournalArticle journalArticle : findByG_U_C(groupId, userId,
				classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_U_C(long groupId, long userId, long classNameId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_C;

		Object[] finderArgs = new Object[] { groupId, userId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_U_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_C_USERID_2);

			query.append(_FINDER_COLUMN_G_U_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_C(long groupId, long userId, long classNameId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_C(groupId, userId, classNameId);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_U_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_C_USERID_2);

		query.append(_FINDER_COLUMN_G_U_C_CLASSNAMEID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(classNameId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_U_C_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_USERID_2 = "journalArticle.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_C_CLASSNAMEID_2 = "journalArticle.classNameId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F_ST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_ST =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F_ST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.FOLDERID_COLUMN_BITMASK |
			JournalArticleModelImpl.STATUS_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_ST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_F_ST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_ST(long groupId, long folderId,
		int status) {
		return findByG_F_ST(groupId, folderId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_ST(long groupId, long folderId,
		int status, int start, int end) {
		return findByG_F_ST(groupId, folderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_ST(long groupId, long folderId,
		int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_ST;
			finderArgs = new Object[] { groupId, folderId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_ST;
			finderArgs = new Object[] {
					groupId, folderId, status,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						(folderId != journalArticle.getFolderId()) ||
						(status != journalArticle.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_ST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(status);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_F_ST_First(long groupId, long folderId,
		int status, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_F_ST_First(groupId, folderId,
				status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_F_ST_First(long groupId, long folderId,
		int status, OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByG_F_ST(groupId, folderId, status, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_F_ST_Last(long groupId, long folderId,
		int status, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_F_ST_Last(groupId, folderId,
				status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_F_ST_Last(long groupId, long folderId,
		int status, OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByG_F_ST(groupId, folderId, status);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByG_F_ST(groupId, folderId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByG_F_ST_PrevAndNext(long id, long groupId,
		long folderId, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_F_ST_PrevAndNext(session, journalArticle,
					groupId, folderId, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_F_ST_PrevAndNext(session, journalArticle,
					groupId, folderId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_F_ST_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, long folderId, int status,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_ST_STATUS_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F_ST(long groupId, long folderId,
		int status) {
		return filterFindByG_F_ST(groupId, folderId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F_ST(long groupId, long folderId,
		int status, int start, int end) {
		return filterFindByG_F_ST(groupId, folderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F_ST(long groupId, long folderId,
		int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_ST(groupId, folderId, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_ST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			qPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByG_F_ST_PrevAndNext(long id,
		long groupId, long folderId, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_ST_PrevAndNext(id, groupId, folderId, status,
				orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByG_F_ST_PrevAndNext(session, journalArticle,
					groupId, folderId, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByG_F_ST_PrevAndNext(session, journalArticle,
					groupId, folderId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle filterGetByG_F_ST_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, long folderId, int status,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_ST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F_ST(long groupId, long folderId,
		int[] statuses) {
		return filterFindByG_F_ST(groupId, folderId, statuses,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F_ST(long groupId, long folderId,
		int[] statuses, int start, int end) {
		return filterFindByG_F_ST(groupId, folderId, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_F_ST(long groupId, long folderId,
		int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_ST(groupId, folderId, statuses, start, end,
				orderByComparator);
		}

		if (statuses == null) {
			statuses = new int[0];
		}
		else {
			statuses = ArrayUtil.unique(statuses);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

		if (statuses.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_F_ST_STATUS_7);

			query.append(StringUtil.merge(statuses));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_ST(long groupId, long folderId,
		int[] statuses) {
		return findByG_F_ST(groupId, folderId, statuses, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_ST(long groupId, long folderId,
		int[] statuses, int start, int end) {
		return findByG_F_ST(groupId, folderId, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_F_ST(long groupId, long folderId,
		int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (statuses == null) {
			statuses = new int[0];
		}
		else {
			statuses = ArrayUtil.unique(statuses);
		}

		if (statuses.length == 1) {
			return findByG_F_ST(groupId, folderId, statuses[0], start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] {
					groupId, folderId, StringUtil.merge(statuses)
				};
		}
		else {
			finderArgs = new Object[] {
					groupId, folderId, StringUtil.merge(statuses),
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_ST,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						(folderId != journalArticle.getFolderId()) ||
						!ArrayUtil.contains(statuses, journalArticle.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

			if (statuses.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_F_ST_STATUS_7);

				query.append(StringUtil.merge(statuses));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_ST,
					finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_ST,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 */
	@Override
	public void removeByG_F_ST(long groupId, long folderId, int status) {
		for (JournalArticle journalArticle : findByG_F_ST(groupId, folderId,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_F_ST(long groupId, long folderId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F_ST;

		Object[] finderArgs = new Object[] { groupId, folderId, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_ST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(status);

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

	/**
	 * Returns the number of journal articles where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_F_ST(long groupId, long folderId, int[] statuses) {
		if (statuses == null) {
			statuses = new int[0];
		}
		else {
			statuses = ArrayUtil.unique(statuses);
		}

		Object[] finderArgs = new Object[] {
				groupId, folderId, StringUtil.merge(statuses)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F_ST,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

			if (statuses.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_F_ST_STATUS_7);

				query.append(StringUtil.merge(statuses));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F_ST,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F_ST,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_F_ST(long groupId, long folderId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_ST(groupId, folderId, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_ST_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_F_ST(long groupId, long folderId, int[] statuses) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_ST(groupId, folderId, statuses);
		}

		if (statuses == null) {
			statuses = new int[0];
		}
		else {
			statuses = ArrayUtil.unique(statuses);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_F_ST_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_ST_FOLDERID_2);

		if (statuses.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_F_ST_STATUS_7);

			query.append(StringUtil.merge(statuses));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_F_ST_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_ST_FOLDERID_2 = "journalArticle.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_ST_STATUS_2 = "journalArticle.status = ?";
	private static final String _FINDER_COLUMN_G_F_ST_STATUS_7 = "journalArticle.status IN (";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			JournalArticleModelImpl.CLASSPK_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_C(long groupId, long classNameId,
		long classPK) {
		return findByG_C_C(groupId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end) {
		return findByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C;
			finderArgs = new Object[] { groupId, classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C;
			finderArgs = new Object[] {
					groupId, classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						(classNameId != journalArticle.getClassNameId()) ||
						(classPK != journalArticle.getClassPK())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_C_C_First(long groupId, long classNameId,
		long classPK, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_C_C_First(groupId,
				classNameId, classPK, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_C_C_First(long groupId, long classNameId,
		long classPK, OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByG_C_C(groupId, classNameId, classPK,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_C_C_Last(long groupId, long classNameId,
		long classPK, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_C_C_Last(groupId, classNameId,
				classPK, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_C_C_Last(long groupId, long classNameId,
		long classPK, OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByG_C_C(groupId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByG_C_C(groupId, classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByG_C_C_PrevAndNext(long id, long groupId,
		long classNameId, long classPK,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_C_C_PrevAndNext(session, journalArticle, groupId,
					classNameId, classPK, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_C_C_PrevAndNext(session, journalArticle, groupId,
					classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_C_C_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, long classNameId,
		long classPK, OrderByComparator<JournalArticle> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_C(long groupId,
		long classNameId, long classPK) {
		return filterFindByG_C_C(groupId, classNameId, classPK,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end) {
		return filterFindByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C(groupId, classNameId, classPK, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			qPos.add(classPK);

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByG_C_C_PrevAndNext(long id,
		long groupId, long classNameId, long classPK,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_PrevAndNext(id, groupId, classNameId, classPK,
				orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByG_C_C_PrevAndNext(session, journalArticle,
					groupId, classNameId, classPK, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByG_C_C_PrevAndNext(session, journalArticle,
					groupId, classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle filterGetByG_C_C_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, long classNameId,
		long classPK, OrderByComparator<JournalArticle> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 */
	@Override
	public void removeByG_C_C(long groupId, long classNameId, long classPK) {
		for (JournalArticle journalArticle : findByG_C_C(groupId, classNameId,
				classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C;

		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C(long groupId, long classNameId, long classPK) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C(groupId, classNameId, classPK);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			qPos.add(classPK);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 = "journalArticle.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 = "journalArticle.classPK = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_DDMSK = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_DDMSK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			JournalArticleModelImpl.DDMSTRUCTUREKEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_DDMSK = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_DDMSK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the journal article where groupId = &#63; and classNameId = &#63; and DDMStructureKey = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchArticleException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMStructureKey the d d m structure key
	 * @return the matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_C_DDMSK(long groupId, long classNameId,
		String DDMStructureKey) throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_C_DDMSK(groupId, classNameId,
				DDMStructureKey);

		if (journalArticle == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", DDMStructureKey=");
			msg.append(DDMStructureKey);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchArticleException(msg.toString());
		}

		return journalArticle;
	}

	/**
	 * Returns the journal article where groupId = &#63; and classNameId = &#63; and DDMStructureKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMStructureKey the d d m structure key
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_C_DDMSK(long groupId, long classNameId,
		String DDMStructureKey) {
		return fetchByG_C_DDMSK(groupId, classNameId, DDMStructureKey, true);
	}

	/**
	 * Returns the journal article where groupId = &#63; and classNameId = &#63; and DDMStructureKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMStructureKey the d d m structure key
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_C_DDMSK(long groupId, long classNameId,
		String DDMStructureKey, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, classNameId, DDMStructureKey };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_C_DDMSK,
					finderArgs, this);
		}

		if (result instanceof JournalArticle) {
			JournalArticle journalArticle = (JournalArticle)result;

			if ((groupId != journalArticle.getGroupId()) ||
					(classNameId != journalArticle.getClassNameId()) ||
					!Validator.equals(DDMStructureKey,
						journalArticle.getDDMStructureKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_C_DDMSK_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_DDMSK_CLASSNAMEID_2);

			boolean bindDDMStructureKey = false;

			if (DDMStructureKey == null) {
				query.append(_FINDER_COLUMN_G_C_DDMSK_DDMSTRUCTUREKEY_1);
			}
			else if (DDMStructureKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_DDMSK_DDMSTRUCTUREKEY_3);
			}
			else {
				bindDDMStructureKey = true;

				query.append(_FINDER_COLUMN_G_C_DDMSK_DDMSTRUCTUREKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (bindDDMStructureKey) {
					qPos.add(DDMStructureKey);
				}

				List<JournalArticle> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_DDMSK,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"JournalArticlePersistenceImpl.fetchByG_C_DDMSK(long, long, String, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					JournalArticle journalArticle = list.get(0);

					result = journalArticle;

					cacheResult(journalArticle);

					if ((journalArticle.getGroupId() != groupId) ||
							(journalArticle.getClassNameId() != classNameId) ||
							(journalArticle.getDDMStructureKey() == null) ||
							!journalArticle.getDDMStructureKey()
											   .equals(DDMStructureKey)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_DDMSK,
							finderArgs, journalArticle);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_DDMSK,
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
			return (JournalArticle)result;
		}
	}

	/**
	 * Removes the journal article where groupId = &#63; and classNameId = &#63; and DDMStructureKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMStructureKey the d d m structure key
	 * @return the journal article that was removed
	 */
	@Override
	public JournalArticle removeByG_C_DDMSK(long groupId, long classNameId,
		String DDMStructureKey) throws NoSuchArticleException {
		JournalArticle journalArticle = findByG_C_DDMSK(groupId, classNameId,
				DDMStructureKey);

		return remove(journalArticle);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMStructureKey the d d m structure key
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_C_DDMSK(long groupId, long classNameId,
		String DDMStructureKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_DDMSK;

		Object[] finderArgs = new Object[] { groupId, classNameId, DDMStructureKey };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_C_DDMSK_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_DDMSK_CLASSNAMEID_2);

			boolean bindDDMStructureKey = false;

			if (DDMStructureKey == null) {
				query.append(_FINDER_COLUMN_G_C_DDMSK_DDMSTRUCTUREKEY_1);
			}
			else if (DDMStructureKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_DDMSK_DDMSTRUCTUREKEY_3);
			}
			else {
				bindDDMStructureKey = true;

				query.append(_FINDER_COLUMN_G_C_DDMSK_DDMSTRUCTUREKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (bindDDMStructureKey) {
					qPos.add(DDMStructureKey);
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

	private static final String _FINDER_COLUMN_G_C_DDMSK_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_DDMSK_CLASSNAMEID_2 = "journalArticle.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_DDMSK_DDMSTRUCTUREKEY_1 = "journalArticle.DDMStructureKey IS NULL";
	private static final String _FINDER_COLUMN_G_C_DDMSK_DDMSTRUCTUREKEY_2 = "journalArticle.DDMStructureKey = ?";
	private static final String _FINDER_COLUMN_G_C_DDMSK_DDMSTRUCTUREKEY_3 = "(journalArticle.DDMStructureKey IS NULL OR journalArticle.DDMStructureKey = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_DDMTK =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C_DDMTK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_DDMTK =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_DDMTK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			JournalArticleModelImpl.DDMTEMPLATEKEY_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_DDMTK = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_DDMTK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_DDMTK(long groupId, long classNameId,
		String DDMTemplateKey) {
		return findByG_C_DDMTK(groupId, classNameId, DDMTemplateKey,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_DDMTK(long groupId, long classNameId,
		String DDMTemplateKey, int start, int end) {
		return findByG_C_DDMTK(groupId, classNameId, DDMTemplateKey, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_DDMTK(long groupId, long classNameId,
		String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_DDMTK;
			finderArgs = new Object[] { groupId, classNameId, DDMTemplateKey };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_DDMTK;
			finderArgs = new Object[] {
					groupId, classNameId, DDMTemplateKey,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						(classNameId != journalArticle.getClassNameId()) ||
						!Validator.equals(DDMTemplateKey,
							journalArticle.getDDMTemplateKey())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_C_DDMTK_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_DDMTK_CLASSNAMEID_2);

			boolean bindDDMTemplateKey = false;

			if (DDMTemplateKey == null) {
				query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_1);
			}
			else if (DDMTemplateKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_3);
			}
			else {
				bindDDMTemplateKey = true;

				query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (bindDDMTemplateKey) {
					qPos.add(DDMTemplateKey);
				}

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_C_DDMTK_First(long groupId, long classNameId,
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_C_DDMTK_First(groupId,
				classNameId, DDMTemplateKey, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", DDMTemplateKey=");
		msg.append(DDMTemplateKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_C_DDMTK_First(long groupId,
		long classNameId, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByG_C_DDMTK(groupId, classNameId,
				DDMTemplateKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_C_DDMTK_Last(long groupId, long classNameId,
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_C_DDMTK_Last(groupId,
				classNameId, DDMTemplateKey, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", DDMTemplateKey=");
		msg.append(DDMTemplateKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_C_DDMTK_Last(long groupId, long classNameId,
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByG_C_DDMTK(groupId, classNameId, DDMTemplateKey);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByG_C_DDMTK(groupId, classNameId,
				DDMTemplateKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByG_C_DDMTK_PrevAndNext(long id, long groupId,
		long classNameId, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_C_DDMTK_PrevAndNext(session, journalArticle,
					groupId, classNameId, DDMTemplateKey, orderByComparator,
					true);

			array[1] = journalArticle;

			array[2] = getByG_C_DDMTK_PrevAndNext(session, journalArticle,
					groupId, classNameId, DDMTemplateKey, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_C_DDMTK_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, long classNameId,
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_C_DDMTK_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_DDMTK_CLASSNAMEID_2);

		boolean bindDDMTemplateKey = false;

		if (DDMTemplateKey == null) {
			query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_1);
		}
		else if (DDMTemplateKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_3);
		}
		else {
			bindDDMTemplateKey = true;

			query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_2);
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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		if (bindDDMTemplateKey) {
			qPos.add(DDMTemplateKey);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_DDMTK(long groupId,
		long classNameId, String DDMTemplateKey) {
		return filterFindByG_C_DDMTK(groupId, classNameId, DDMTemplateKey,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_DDMTK(long groupId,
		long classNameId, String DDMTemplateKey, int start, int end) {
		return filterFindByG_C_DDMTK(groupId, classNameId, DDMTemplateKey,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_DDMTK(long groupId,
		long classNameId, String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_DDMTK(groupId, classNameId, DDMTemplateKey, start,
				end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_DDMTK_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_DDMTK_CLASSNAMEID_2);

		boolean bindDDMTemplateKey = false;

		if (DDMTemplateKey == null) {
			query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_1);
		}
		else if (DDMTemplateKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_3);
		}
		else {
			bindDDMTemplateKey = true;

			query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			if (bindDDMTemplateKey) {
				qPos.add(DDMTemplateKey);
			}

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByG_C_DDMTK_PrevAndNext(long id,
		long groupId, long classNameId, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_DDMTK_PrevAndNext(id, groupId, classNameId,
				DDMTemplateKey, orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByG_C_DDMTK_PrevAndNext(session,
					journalArticle, groupId, classNameId, DDMTemplateKey,
					orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByG_C_DDMTK_PrevAndNext(session,
					journalArticle, groupId, classNameId, DDMTemplateKey,
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

	protected JournalArticle filterGetByG_C_DDMTK_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, long classNameId,
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_DDMTK_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_DDMTK_CLASSNAMEID_2);

		boolean bindDDMTemplateKey = false;

		if (DDMTemplateKey == null) {
			query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_1);
		}
		else if (DDMTemplateKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_3);
		}
		else {
			bindDDMTemplateKey = true;

			query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		if (bindDDMTemplateKey) {
			qPos.add(DDMTemplateKey);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 */
	@Override
	public void removeByG_C_DDMTK(long groupId, long classNameId,
		String DDMTemplateKey) {
		for (JournalArticle journalArticle : findByG_C_DDMTK(groupId,
				classNameId, DDMTemplateKey, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_C_DDMTK(long groupId, long classNameId,
		String DDMTemplateKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_DDMTK;

		Object[] finderArgs = new Object[] { groupId, classNameId, DDMTemplateKey };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_C_DDMTK_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_DDMTK_CLASSNAMEID_2);

			boolean bindDDMTemplateKey = false;

			if (DDMTemplateKey == null) {
				query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_1);
			}
			else if (DDMTemplateKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_3);
			}
			else {
				bindDDMTemplateKey = true;

				query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (bindDDMTemplateKey) {
					qPos.add(DDMTemplateKey);
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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the d d m template key
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_DDMTK(long groupId, long classNameId,
		String DDMTemplateKey) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_DDMTK(groupId, classNameId, DDMTemplateKey);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_C_DDMTK_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_DDMTK_CLASSNAMEID_2);

		boolean bindDDMTemplateKey = false;

		if (DDMTemplateKey == null) {
			query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_1);
		}
		else if (DDMTemplateKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_3);
		}
		else {
			bindDDMTemplateKey = true;

			query.append(_FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			if (bindDDMTemplateKey) {
				qPos.add(DDMTemplateKey);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_C_DDMTK_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_DDMTK_CLASSNAMEID_2 = "journalArticle.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_1 = "journalArticle.DDMTemplateKey IS NULL";
	private static final String _FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_2 = "journalArticle.DDMTemplateKey = ?";
	private static final String _FINDER_COLUMN_G_C_DDMTK_DDMTEMPLATEKEY_3 = "(journalArticle.DDMTemplateKey IS NULL OR journalArticle.DDMTemplateKey = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_L = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_L = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			JournalArticleModelImpl.LAYOUTUUID_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_L = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_L(long groupId, long classNameId,
		String layoutUuid) {
		return findByG_C_L(groupId, classNameId, layoutUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_L(long groupId, long classNameId,
		String layoutUuid, int start, int end) {
		return findByG_C_L(groupId, classNameId, layoutUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_C_L(long groupId, long classNameId,
		String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_L;
			finderArgs = new Object[] { groupId, classNameId, layoutUuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_L;
			finderArgs = new Object[] {
					groupId, classNameId, layoutUuid,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						(classNameId != journalArticle.getClassNameId()) ||
						!Validator.equals(layoutUuid,
							journalArticle.getLayoutUuid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_L_CLASSNAMEID_2);

			boolean bindLayoutUuid = false;

			if (layoutUuid == null) {
				query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_1);
			}
			else if (layoutUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_3);
			}
			else {
				bindLayoutUuid = true;

				query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (bindLayoutUuid) {
					qPos.add(layoutUuid);
				}

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_C_L_First(long groupId, long classNameId,
		String layoutUuid, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_C_L_First(groupId,
				classNameId, layoutUuid, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", layoutUuid=");
		msg.append(layoutUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_C_L_First(long groupId, long classNameId,
		String layoutUuid, OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByG_C_L(groupId, classNameId,
				layoutUuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_C_L_Last(long groupId, long classNameId,
		String layoutUuid, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_C_L_Last(groupId, classNameId,
				layoutUuid, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", layoutUuid=");
		msg.append(layoutUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_C_L_Last(long groupId, long classNameId,
		String layoutUuid, OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByG_C_L(groupId, classNameId, layoutUuid);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByG_C_L(groupId, classNameId,
				layoutUuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByG_C_L_PrevAndNext(long id, long groupId,
		long classNameId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_C_L_PrevAndNext(session, journalArticle, groupId,
					classNameId, layoutUuid, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_C_L_PrevAndNext(session, journalArticle, groupId,
					classNameId, layoutUuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_C_L_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, long classNameId,
		String layoutUuid, OrderByComparator<JournalArticle> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_L_CLASSNAMEID_2);

		boolean bindLayoutUuid = false;

		if (layoutUuid == null) {
			query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_1);
		}
		else if (layoutUuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_2);
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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		if (bindLayoutUuid) {
			qPos.add(layoutUuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_L(long groupId,
		long classNameId, String layoutUuid) {
		return filterFindByG_C_L(groupId, classNameId, layoutUuid,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_L(long groupId,
		long classNameId, String layoutUuid, int start, int end) {
		return filterFindByG_C_L(groupId, classNameId, layoutUuid, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_C_L(long groupId,
		long classNameId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_L(groupId, classNameId, layoutUuid, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_L_CLASSNAMEID_2);

		boolean bindLayoutUuid = false;

		if (layoutUuid == null) {
			query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_1);
		}
		else if (layoutUuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			if (bindLayoutUuid) {
				qPos.add(layoutUuid);
			}

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByG_C_L_PrevAndNext(long id,
		long groupId, long classNameId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_L_PrevAndNext(id, groupId, classNameId,
				layoutUuid, orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByG_C_L_PrevAndNext(session, journalArticle,
					groupId, classNameId, layoutUuid, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByG_C_L_PrevAndNext(session, journalArticle,
					groupId, classNameId, layoutUuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle filterGetByG_C_L_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, long classNameId,
		String layoutUuid, OrderByComparator<JournalArticle> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_L_CLASSNAMEID_2);

		boolean bindLayoutUuid = false;

		if (layoutUuid == null) {
			query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_1);
		}
		else if (layoutUuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		if (bindLayoutUuid) {
			qPos.add(layoutUuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 */
	@Override
	public void removeByG_C_L(long groupId, long classNameId, String layoutUuid) {
		for (JournalArticle journalArticle : findByG_C_L(groupId, classNameId,
				layoutUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_C_L(long groupId, long classNameId, String layoutUuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_L;

		Object[] finderArgs = new Object[] { groupId, classNameId, layoutUuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_L_CLASSNAMEID_2);

			boolean bindLayoutUuid = false;

			if (layoutUuid == null) {
				query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_1);
			}
			else if (layoutUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_3);
			}
			else {
				bindLayoutUuid = true;

				query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (bindLayoutUuid) {
					qPos.add(layoutUuid);
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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_L(long groupId, long classNameId,
		String layoutUuid) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_L(groupId, classNameId, layoutUuid);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_L_CLASSNAMEID_2);

		boolean bindLayoutUuid = false;

		if (layoutUuid == null) {
			query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_1);
		}
		else if (layoutUuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			query.append(_FINDER_COLUMN_G_C_L_LAYOUTUUID_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			if (bindLayoutUuid) {
				qPos.add(layoutUuid);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_C_L_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_L_CLASSNAMEID_2 = "journalArticle.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_L_LAYOUTUUID_1 = "journalArticle.layoutUuid IS NULL";
	private static final String _FINDER_COLUMN_G_C_L_LAYOUTUUID_2 = "journalArticle.layoutUuid = ?";
	private static final String _FINDER_COLUMN_G_C_L_LAYOUTUUID_3 = "(journalArticle.layoutUuid IS NULL OR journalArticle.layoutUuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_A_V = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_A_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			},
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A_V = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			});

	/**
	 * Returns the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchArticleException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @return the matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_A_V(long groupId, String articleId,
		double version) throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_A_V(groupId, articleId, version);

		if (journalArticle == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", articleId=");
			msg.append(articleId);

			msg.append(", version=");
			msg.append(version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchArticleException(msg.toString());
		}

		return journalArticle;
	}

	/**
	 * Returns the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_A_V(long groupId, String articleId,
		double version) {
		return fetchByG_A_V(groupId, articleId, version, true);
	}

	/**
	 * Returns the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_A_V(long groupId, String articleId,
		double version, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, articleId, version };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_A_V,
					finderArgs, this);
		}

		if (result instanceof JournalArticle) {
			JournalArticle journalArticle = (JournalArticle)result;

			if ((groupId != journalArticle.getGroupId()) ||
					!Validator.equals(articleId, journalArticle.getArticleId()) ||
					(version != journalArticle.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_A_V_GROUPID_2);

			boolean bindArticleId = false;

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_1);
			}
			else if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_2);
			}

			query.append(_FINDER_COLUMN_G_A_V_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindArticleId) {
					qPos.add(articleId);
				}

				qPos.add(version);

				List<JournalArticle> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V,
						finderArgs, list);
				}
				else {
					JournalArticle journalArticle = list.get(0);

					result = journalArticle;

					cacheResult(journalArticle);

					if ((journalArticle.getGroupId() != groupId) ||
							(journalArticle.getArticleId() == null) ||
							!journalArticle.getArticleId().equals(articleId) ||
							(journalArticle.getVersion() != version)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V,
							finderArgs, journalArticle);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_A_V,
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
			return (JournalArticle)result;
		}
	}

	/**
	 * Removes the journal article where groupId = &#63; and articleId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @return the journal article that was removed
	 */
	@Override
	public JournalArticle removeByG_A_V(long groupId, String articleId,
		double version) throws NoSuchArticleException {
		JournalArticle journalArticle = findByG_A_V(groupId, articleId, version);

		return remove(journalArticle);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and articleId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_A_V(long groupId, String articleId, double version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_A_V;

		Object[] finderArgs = new Object[] { groupId, articleId, version };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_A_V_GROUPID_2);

			boolean bindArticleId = false;

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_1);
			}
			else if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				query.append(_FINDER_COLUMN_G_A_V_ARTICLEID_2);
			}

			query.append(_FINDER_COLUMN_G_A_V_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindArticleId) {
					qPos.add(articleId);
				}

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_G_A_V_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_V_ARTICLEID_1 = "journalArticle.articleId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_A_V_ARTICLEID_2 = "journalArticle.articleId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_V_ARTICLEID_3 = "(journalArticle.articleId IS NULL OR journalArticle.articleId = '') AND ";
	private static final String _FINDER_COLUMN_G_A_V_VERSION_2 = "journalArticle.version = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_A_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_ST =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.STATUS_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_A_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_A_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_ST(long groupId, String articleId,
		int status) {
		return findByG_A_ST(groupId, articleId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_ST(long groupId, String articleId,
		int status, int start, int end) {
		return findByG_A_ST(groupId, articleId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_ST(long groupId, String articleId,
		int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_ST;
			finderArgs = new Object[] { groupId, articleId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A_ST;
			finderArgs = new Object[] {
					groupId, articleId, status,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						!Validator.equals(articleId,
							journalArticle.getArticleId()) ||
						(status != journalArticle.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

			boolean bindArticleId = false;

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_1);
			}
			else if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
			}

			query.append(_FINDER_COLUMN_G_A_ST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindArticleId) {
					qPos.add(articleId);
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_A_ST_First(long groupId, String articleId,
		int status, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_A_ST_First(groupId, articleId,
				status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", articleId=");
		msg.append(articleId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_A_ST_First(long groupId, String articleId,
		int status, OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByG_A_ST(groupId, articleId, status, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_A_ST_Last(long groupId, String articleId,
		int status, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_A_ST_Last(groupId, articleId,
				status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", articleId=");
		msg.append(articleId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_A_ST_Last(long groupId, String articleId,
		int status, OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByG_A_ST(groupId, articleId, status);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByG_A_ST(groupId, articleId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByG_A_ST_PrevAndNext(long id, long groupId,
		String articleId, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_A_ST_PrevAndNext(session, journalArticle,
					groupId, articleId, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_A_ST_PrevAndNext(session, journalArticle,
					groupId, articleId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_A_ST_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String articleId,
		int status, OrderByComparator<JournalArticle> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_1);
		}
		else if (articleId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
		}

		query.append(_FINDER_COLUMN_G_A_ST_STATUS_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindArticleId) {
			qPos.add(articleId);
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_ST(long groupId,
		String articleId, int status) {
		return filterFindByG_A_ST(groupId, articleId, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_ST(long groupId,
		String articleId, int status, int start, int end) {
		return filterFindByG_A_ST(groupId, articleId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_ST(long groupId,
		String articleId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_ST(groupId, articleId, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_1);
		}
		else if (articleId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
		}

		query.append(_FINDER_COLUMN_G_A_ST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindArticleId) {
				qPos.add(articleId);
			}

			qPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByG_A_ST_PrevAndNext(long id,
		long groupId, String articleId, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_ST_PrevAndNext(id, groupId, articleId, status,
				orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByG_A_ST_PrevAndNext(session, journalArticle,
					groupId, articleId, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByG_A_ST_PrevAndNext(session, journalArticle,
					groupId, articleId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle filterGetByG_A_ST_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String articleId,
		int status, OrderByComparator<JournalArticle> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_1);
		}
		else if (articleId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
		}

		query.append(_FINDER_COLUMN_G_A_ST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindArticleId) {
			qPos.add(articleId);
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_ST(long groupId,
		String articleId, int[] statuses) {
		return filterFindByG_A_ST(groupId, articleId, statuses,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_ST(long groupId,
		String articleId, int[] statuses, int start, int end) {
		return filterFindByG_A_ST(groupId, articleId, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_ST(long groupId,
		String articleId, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_ST(groupId, articleId, statuses, start, end,
				orderByComparator);
		}

		if (statuses == null) {
			statuses = new int[0];
		}
		else {
			statuses = ArrayUtil.unique(statuses);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_1);
		}
		else if (articleId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
		}

		if (statuses.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_A_ST_STATUS_7);

			query.append(StringUtil.merge(statuses));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindArticleId) {
				qPos.add(articleId);
			}

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_ST(long groupId, String articleId,
		int[] statuses) {
		return findByG_A_ST(groupId, articleId, statuses, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_ST(long groupId, String articleId,
		int[] statuses, int start, int end) {
		return findByG_A_ST(groupId, articleId, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_ST(long groupId, String articleId,
		int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (statuses == null) {
			statuses = new int[0];
		}
		else {
			statuses = ArrayUtil.unique(statuses);
		}

		if (statuses.length == 1) {
			return findByG_A_ST(groupId, articleId, statuses[0], start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] {
					groupId, articleId, StringUtil.merge(statuses)
				};
		}
		else {
			finderArgs = new Object[] {
					groupId, articleId, StringUtil.merge(statuses),
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A_ST,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						!Validator.equals(articleId,
							journalArticle.getArticleId()) ||
						!ArrayUtil.contains(statuses, journalArticle.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

			boolean bindArticleId = false;

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_1);
			}
			else if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
			}

			if (statuses.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_A_ST_STATUS_7);

				query.append(StringUtil.merge(statuses));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindArticleId) {
					qPos.add(articleId);
				}

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A_ST,
					finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A_ST,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 */
	@Override
	public void removeByG_A_ST(long groupId, String articleId, int status) {
		for (JournalArticle journalArticle : findByG_A_ST(groupId, articleId,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_A_ST(long groupId, String articleId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_A_ST;

		Object[] finderArgs = new Object[] { groupId, articleId, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

			boolean bindArticleId = false;

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_1);
			}
			else if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
			}

			query.append(_FINDER_COLUMN_G_A_ST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindArticleId) {
					qPos.add(articleId);
				}

				qPos.add(status);

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

	/**
	 * Returns the number of journal articles where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_A_ST(long groupId, String articleId, int[] statuses) {
		if (statuses == null) {
			statuses = new int[0];
		}
		else {
			statuses = ArrayUtil.unique(statuses);
		}

		Object[] finderArgs = new Object[] {
				groupId, articleId, StringUtil.merge(statuses)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_A_ST,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

			boolean bindArticleId = false;

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_1);
			}
			else if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
			}

			if (statuses.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_A_ST_STATUS_7);

				query.append(StringUtil.merge(statuses));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindArticleId) {
					qPos.add(articleId);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_A_ST,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_A_ST,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_A_ST(long groupId, String articleId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A_ST(groupId, articleId, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_1);
		}
		else if (articleId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
		}

		query.append(_FINDER_COLUMN_G_A_ST_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindArticleId) {
				qPos.add(articleId);
			}

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_A_ST(long groupId, String articleId,
		int[] statuses) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A_ST(groupId, articleId, statuses);
		}

		if (statuses == null) {
			statuses = new int[0];
		}
		else {
			statuses = ArrayUtil.unique(statuses);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_A_ST_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_1);
		}
		else if (articleId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			query.append(_FINDER_COLUMN_G_A_ST_ARTICLEID_2);
		}

		if (statuses.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_A_ST_STATUS_7);

			query.append(StringUtil.merge(statuses));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindArticleId) {
				qPos.add(articleId);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_A_ST_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ST_ARTICLEID_1 = "journalArticle.articleId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_A_ST_ARTICLEID_2 = "journalArticle.articleId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ST_ARTICLEID_3 = "(journalArticle.articleId IS NULL OR journalArticle.articleId = '') AND ";
	private static final String _FINDER_COLUMN_G_A_ST_STATUS_2 = "journalArticle.status = ?";
	private static final String _FINDER_COLUMN_G_A_ST_STATUS_7 = "journalArticle.status IN (";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A_NOTST =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_A_NotST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_A_NOTST =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_A_NotST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_NotST(long groupId, String articleId,
		int status) {
		return findByG_A_NotST(groupId, articleId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_NotST(long groupId, String articleId,
		int status, int start, int end) {
		return findByG_A_NotST(groupId, articleId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_A_NotST(long groupId, String articleId,
		int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A_NOTST;
		finderArgs = new Object[] {
				groupId, articleId, status,
				
				start, end, orderByComparator
			};

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						!Validator.equals(articleId,
							journalArticle.getArticleId()) ||
						(status == journalArticle.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_A_NOTST_GROUPID_2);

			boolean bindArticleId = false;

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_1);
			}
			else if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_2);
			}

			query.append(_FINDER_COLUMN_G_A_NOTST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindArticleId) {
					qPos.add(articleId);
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_A_NotST_First(long groupId, String articleId,
		int status, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_A_NotST_First(groupId,
				articleId, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", articleId=");
		msg.append(articleId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_A_NotST_First(long groupId,
		String articleId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByG_A_NotST(groupId, articleId, status,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_A_NotST_Last(long groupId, String articleId,
		int status, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_A_NotST_Last(groupId,
				articleId, status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", articleId=");
		msg.append(articleId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_A_NotST_Last(long groupId, String articleId,
		int status, OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByG_A_NotST(groupId, articleId, status);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByG_A_NotST(groupId, articleId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByG_A_NotST_PrevAndNext(long id, long groupId,
		String articleId, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_A_NotST_PrevAndNext(session, journalArticle,
					groupId, articleId, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_A_NotST_PrevAndNext(session, journalArticle,
					groupId, articleId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_A_NotST_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String articleId,
		int status, OrderByComparator<JournalArticle> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_A_NOTST_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_1);
		}
		else if (articleId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_2);
		}

		query.append(_FINDER_COLUMN_G_A_NOTST_STATUS_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindArticleId) {
			qPos.add(articleId);
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_NotST(long groupId,
		String articleId, int status) {
		return filterFindByG_A_NotST(groupId, articleId, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_NotST(long groupId,
		String articleId, int status, int start, int end) {
		return filterFindByG_A_NotST(groupId, articleId, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_A_NotST(long groupId,
		String articleId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_NotST(groupId, articleId, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_NOTST_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_1);
		}
		else if (articleId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_2);
		}

		query.append(_FINDER_COLUMN_G_A_NOTST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindArticleId) {
				qPos.add(articleId);
			}

			qPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByG_A_NotST_PrevAndNext(long id,
		long groupId, String articleId, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_NotST_PrevAndNext(id, groupId, articleId, status,
				orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByG_A_NotST_PrevAndNext(session,
					journalArticle, groupId, articleId, status,
					orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByG_A_NotST_PrevAndNext(session,
					journalArticle, groupId, articleId, status,
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

	protected JournalArticle filterGetByG_A_NotST_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String articleId,
		int status, OrderByComparator<JournalArticle> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_NOTST_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_1);
		}
		else if (articleId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_2);
		}

		query.append(_FINDER_COLUMN_G_A_NOTST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindArticleId) {
			qPos.add(articleId);
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 */
	@Override
	public void removeByG_A_NotST(long groupId, String articleId, int status) {
		for (JournalArticle journalArticle : findByG_A_NotST(groupId,
				articleId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_A_NotST(long groupId, String articleId, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_A_NOTST;

		Object[] finderArgs = new Object[] { groupId, articleId, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_A_NOTST_GROUPID_2);

			boolean bindArticleId = false;

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_1);
			}
			else if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_2);
			}

			query.append(_FINDER_COLUMN_G_A_NOTST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindArticleId) {
					qPos.add(articleId);
				}

				qPos.add(status);

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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_A_NotST(long groupId, String articleId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A_NotST(groupId, articleId, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_A_NOTST_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_1);
		}
		else if (articleId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			query.append(_FINDER_COLUMN_G_A_NOTST_ARTICLEID_2);
		}

		query.append(_FINDER_COLUMN_G_A_NOTST_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindArticleId) {
				qPos.add(articleId);
			}

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_A_NOTST_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_NOTST_ARTICLEID_1 = "journalArticle.articleId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_A_NOTST_ARTICLEID_2 = "journalArticle.articleId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_NOTST_ARTICLEID_3 = "(journalArticle.articleId IS NULL OR journalArticle.articleId = '') AND ";
	private static final String _FINDER_COLUMN_G_A_NOTST_STATUS_2 = "journalArticle.status != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_UT_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_UT_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_UT_ST =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_UT_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			JournalArticleModelImpl.GROUPID_COLUMN_BITMASK |
			JournalArticleModelImpl.URLTITLE_COLUMN_BITMASK |
			JournalArticleModelImpl.STATUS_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_UT_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_UT_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_UT_ST(long groupId, String urlTitle,
		int status) {
		return findByG_UT_ST(groupId, urlTitle, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_UT_ST(long groupId, String urlTitle,
		int status, int start, int end) {
		return findByG_UT_ST(groupId, urlTitle, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByG_UT_ST(long groupId, String urlTitle,
		int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_UT_ST;
			finderArgs = new Object[] { groupId, urlTitle, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_UT_ST;
			finderArgs = new Object[] {
					groupId, urlTitle, status,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((groupId != journalArticle.getGroupId()) ||
						!Validator.equals(urlTitle, journalArticle.getUrlTitle()) ||
						(status != journalArticle.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_UT_ST_GROUPID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_2);
			}

			query.append(_FINDER_COLUMN_G_UT_ST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_UT_ST_First(long groupId, String urlTitle,
		int status, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_UT_ST_First(groupId, urlTitle,
				status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", urlTitle=");
		msg.append(urlTitle);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_UT_ST_First(long groupId, String urlTitle,
		int status, OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByG_UT_ST(groupId, urlTitle, status, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByG_UT_ST_Last(long groupId, String urlTitle,
		int status, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByG_UT_ST_Last(groupId, urlTitle,
				status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", urlTitle=");
		msg.append(urlTitle);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByG_UT_ST_Last(long groupId, String urlTitle,
		int status, OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByG_UT_ST(groupId, urlTitle, status);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByG_UT_ST(groupId, urlTitle, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByG_UT_ST_PrevAndNext(long id, long groupId,
		String urlTitle, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByG_UT_ST_PrevAndNext(session, journalArticle,
					groupId, urlTitle, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByG_UT_ST_PrevAndNext(session, journalArticle,
					groupId, urlTitle, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByG_UT_ST_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String urlTitle,
		int status, OrderByComparator<JournalArticle> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_UT_ST_GROUPID_2);

		boolean bindUrlTitle = false;

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_1);
		}
		else if (urlTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_3);
		}
		else {
			bindUrlTitle = true;

			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_2);
		}

		query.append(_FINDER_COLUMN_G_UT_ST_STATUS_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindUrlTitle) {
			qPos.add(urlTitle);
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_UT_ST(long groupId,
		String urlTitle, int status) {
		return filterFindByG_UT_ST(groupId, urlTitle, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_UT_ST(long groupId,
		String urlTitle, int status, int start, int end) {
		return filterFindByG_UT_ST(groupId, urlTitle, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	@Override
	public List<JournalArticle> filterFindByG_UT_ST(long groupId,
		String urlTitle, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_UT_ST(groupId, urlTitle, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_UT_ST_GROUPID_2);

		boolean bindUrlTitle = false;

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_1);
		}
		else if (urlTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_3);
		}
		else {
			bindUrlTitle = true;

			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_2);
		}

		query.append(_FINDER_COLUMN_G_UT_ST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindUrlTitle) {
				qPos.add(urlTitle);
			}

			qPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] filterFindByG_UT_ST_PrevAndNext(long id,
		long groupId, String urlTitle, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_UT_ST_PrevAndNext(id, groupId, urlTitle, status,
				orderByComparator);
		}

		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = filterGetByG_UT_ST_PrevAndNext(session, journalArticle,
					groupId, urlTitle, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = filterGetByG_UT_ST_PrevAndNext(session, journalArticle,
					groupId, urlTitle, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle filterGetByG_UT_ST_PrevAndNext(Session session,
		JournalArticle journalArticle, long groupId, String urlTitle,
		int status, OrderByComparator<JournalArticle> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_UT_ST_GROUPID_2);

		boolean bindUrlTitle = false;

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_1);
		}
		else if (urlTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_3);
		}
		else {
			bindUrlTitle = true;

			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_2);
		}

		query.append(_FINDER_COLUMN_G_UT_ST_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalArticleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalArticleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalArticleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindUrlTitle) {
			qPos.add(urlTitle);
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 */
	@Override
	public void removeByG_UT_ST(long groupId, String urlTitle, int status) {
		for (JournalArticle journalArticle : findByG_UT_ST(groupId, urlTitle,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByG_UT_ST(long groupId, String urlTitle, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_UT_ST;

		Object[] finderArgs = new Object[] { groupId, urlTitle, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_G_UT_ST_GROUPID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_2);
			}

			query.append(_FINDER_COLUMN_G_UT_ST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				qPos.add(status);

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

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	@Override
	public int filterCountByG_UT_ST(long groupId, String urlTitle, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_UT_ST(groupId, urlTitle, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_G_UT_ST_GROUPID_2);

		boolean bindUrlTitle = false;

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_1);
		}
		else if (urlTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_3);
		}
		else {
			bindUrlTitle = true;

			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_2);
		}

		query.append(_FINDER_COLUMN_G_UT_ST_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalArticle.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindUrlTitle) {
				qPos.add(urlTitle);
			}

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_UT_ST_GROUPID_2 = "journalArticle.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_UT_ST_URLTITLE_1 = "journalArticle.urlTitle IS NULL AND ";
	private static final String _FINDER_COLUMN_G_UT_ST_URLTITLE_2 = "journalArticle.urlTitle = ? AND ";
	private static final String _FINDER_COLUMN_G_UT_ST_URLTITLE_3 = "(journalArticle.urlTitle IS NULL OR journalArticle.urlTitle = '') AND ";
	private static final String _FINDER_COLUMN_G_UT_ST_STATUS_2 = "journalArticle.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_V_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_V_ST",
			new String[] {
				Long.class.getName(), Double.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_V_ST =
		new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_V_ST",
			new String[] {
				Long.class.getName(), Double.class.getName(),
				Integer.class.getName()
			},
			JournalArticleModelImpl.COMPANYID_COLUMN_BITMASK |
			JournalArticleModelImpl.VERSION_COLUMN_BITMASK |
			JournalArticleModelImpl.STATUS_COLUMN_BITMASK |
			JournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_V_ST = new FinderPath(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_V_ST",
			new String[] {
				Long.class.getName(), Double.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @return the matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_V_ST(long companyId, double version,
		int status) {
		return findByC_V_ST(companyId, version, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_V_ST(long companyId, double version,
		int status, int start, int end) {
		return findByC_V_ST(companyId, version, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	@Override
	public List<JournalArticle> findByC_V_ST(long companyId, double version,
		int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_V_ST;
			finderArgs = new Object[] { companyId, version, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_V_ST;
			finderArgs = new Object[] {
					companyId, version, status,
					
					start, end, orderByComparator
				};
		}

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalArticle journalArticle : list) {
				if ((companyId != journalArticle.getCompanyId()) ||
						(version != journalArticle.getVersion()) ||
						(status != journalArticle.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_C_V_ST_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_V_ST_VERSION_2);

			query.append(_FINDER_COLUMN_C_V_ST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(version);

				qPos.add(status);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByC_V_ST_First(long companyId, double version,
		int status, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByC_V_ST_First(companyId, version,
				status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", version=");
		msg.append(version);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByC_V_ST_First(long companyId, double version,
		int status, OrderByComparator<JournalArticle> orderByComparator) {
		List<JournalArticle> list = findByC_V_ST(companyId, version, status, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a matching journal article could not be found
	 */
	@Override
	public JournalArticle findByC_V_ST_Last(long companyId, double version,
		int status, OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByC_V_ST_Last(companyId, version,
				status, orderByComparator);

		if (journalArticle != null) {
			return journalArticle;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", version=");
		msg.append(version);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchArticleException(msg.toString());
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	@Override
	public JournalArticle fetchByC_V_ST_Last(long companyId, double version,
		int status, OrderByComparator<JournalArticle> orderByComparator) {
		int count = countByC_V_ST(companyId, version, status);

		if (count == 0) {
			return null;
		}

		List<JournalArticle> list = findByC_V_ST(companyId, version, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle[] findByC_V_ST_PrevAndNext(long id, long companyId,
		double version, int status,
		OrderByComparator<JournalArticle> orderByComparator)
		throws NoSuchArticleException {
		JournalArticle journalArticle = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalArticle[] array = new JournalArticleImpl[3];

			array[0] = getByC_V_ST_PrevAndNext(session, journalArticle,
					companyId, version, status, orderByComparator, true);

			array[1] = journalArticle;

			array[2] = getByC_V_ST_PrevAndNext(session, journalArticle,
					companyId, version, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalArticle getByC_V_ST_PrevAndNext(Session session,
		JournalArticle journalArticle, long companyId, double version,
		int status, OrderByComparator<JournalArticle> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE);

		query.append(_FINDER_COLUMN_C_V_ST_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_V_ST_VERSION_2);

		query.append(_FINDER_COLUMN_C_V_ST_STATUS_2);

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
			query.append(JournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(version);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal articles where companyId = &#63; and version = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 */
	@Override
	public void removeByC_V_ST(long companyId, double version, int status) {
		for (JournalArticle journalArticle : findByC_V_ST(companyId, version,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	@Override
	public int countByC_V_ST(long companyId, double version, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_V_ST;

		Object[] finderArgs = new Object[] { companyId, version, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALARTICLE_WHERE);

			query.append(_FINDER_COLUMN_C_V_ST_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_V_ST_VERSION_2);

			query.append(_FINDER_COLUMN_C_V_ST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(version);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_C_V_ST_COMPANYID_2 = "journalArticle.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_V_ST_VERSION_2 = "journalArticle.version = ? AND ";
	private static final String _FINDER_COLUMN_C_V_ST_STATUS_2 = "journalArticle.status = ?";

	public JournalArticlePersistenceImpl() {
		setModelClass(JournalArticle.class);
	}

	/**
	 * Caches the journal article in the entity cache if it is enabled.
	 *
	 * @param journalArticle the journal article
	 */
	@Override
	public void cacheResult(JournalArticle journalArticle) {
		EntityCacheUtil.putResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImpl.class, journalArticle.getPrimaryKey(),
			journalArticle);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { journalArticle.getUuid(), journalArticle.getGroupId() },
			journalArticle);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_DDMSK,
			new Object[] {
				journalArticle.getGroupId(), journalArticle.getClassNameId(),
				journalArticle.getDDMStructureKey()
			}, journalArticle);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V,
			new Object[] {
				journalArticle.getGroupId(), journalArticle.getArticleId(),
				journalArticle.getVersion()
			}, journalArticle);

		journalArticle.resetOriginalValues();
	}

	/**
	 * Caches the journal articles in the entity cache if it is enabled.
	 *
	 * @param journalArticles the journal articles
	 */
	@Override
	public void cacheResult(List<JournalArticle> journalArticles) {
		for (JournalArticle journalArticle : journalArticles) {
			if (EntityCacheUtil.getResult(
						JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
						JournalArticleImpl.class, journalArticle.getPrimaryKey()) == null) {
				cacheResult(journalArticle);
			}
			else {
				journalArticle.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all journal articles.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(JournalArticleImpl.class.getName());
		}

		EntityCacheUtil.clearCache(JournalArticleImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the journal article.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(JournalArticle journalArticle) {
		EntityCacheUtil.removeResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImpl.class, journalArticle.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(journalArticle);
	}

	@Override
	public void clearCache(List<JournalArticle> journalArticles) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (JournalArticle journalArticle : journalArticles) {
			EntityCacheUtil.removeResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
				JournalArticleImpl.class, journalArticle.getPrimaryKey());

			clearUniqueFindersCache(journalArticle);
		}
	}

	protected void cacheUniqueFindersCache(JournalArticle journalArticle) {
		if (journalArticle.isNew()) {
			Object[] args = new Object[] {
					journalArticle.getUuid(), journalArticle.getGroupId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				journalArticle);

			args = new Object[] {
					journalArticle.getGroupId(), journalArticle.getClassNameId(),
					journalArticle.getDDMStructureKey()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_DDMSK, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_DDMSK, args,
				journalArticle);

			args = new Object[] {
					journalArticle.getGroupId(), journalArticle.getArticleId(),
					journalArticle.getVersion()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_A_V, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V, args,
				journalArticle);
		}
		else {
			JournalArticleModelImpl journalArticleModelImpl = (JournalArticleModelImpl)journalArticle;

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticle.getUuid(), journalArticle.getGroupId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					journalArticle);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_C_DDMSK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticle.getGroupId(),
						journalArticle.getClassNameId(),
						journalArticle.getDDMStructureKey()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_DDMSK, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_DDMSK, args,
					journalArticle);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_A_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticle.getGroupId(),
						journalArticle.getArticleId(),
						journalArticle.getVersion()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_A_V, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A_V, args,
					journalArticle);
			}
		}
	}

	protected void clearUniqueFindersCache(JournalArticle journalArticle) {
		JournalArticleModelImpl journalArticleModelImpl = (JournalArticleModelImpl)journalArticle;

		Object[] args = new Object[] {
				journalArticle.getUuid(), journalArticle.getGroupId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((journalArticleModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					journalArticleModelImpl.getOriginalUuid(),
					journalArticleModelImpl.getOriginalGroupId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		args = new Object[] {
				journalArticle.getGroupId(), journalArticle.getClassNameId(),
				journalArticle.getDDMStructureKey()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_DDMSK, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_DDMSK, args);

		if ((journalArticleModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_DDMSK.getColumnBitmask()) != 0) {
			args = new Object[] {
					journalArticleModelImpl.getOriginalGroupId(),
					journalArticleModelImpl.getOriginalClassNameId(),
					journalArticleModelImpl.getOriginalDDMStructureKey()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_DDMSK, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_DDMSK, args);
		}

		args = new Object[] {
				journalArticle.getGroupId(), journalArticle.getArticleId(),
				journalArticle.getVersion()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_A_V, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_A_V, args);

		if ((journalArticleModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_A_V.getColumnBitmask()) != 0) {
			args = new Object[] {
					journalArticleModelImpl.getOriginalGroupId(),
					journalArticleModelImpl.getOriginalArticleId(),
					journalArticleModelImpl.getOriginalVersion()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_A_V, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_A_V, args);
		}
	}

	/**
	 * Creates a new journal article with the primary key. Does not add the journal article to the database.
	 *
	 * @param id the primary key for the new journal article
	 * @return the new journal article
	 */
	@Override
	public JournalArticle create(long id) {
		JournalArticle journalArticle = new JournalArticleImpl();

		journalArticle.setNew(true);
		journalArticle.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalArticle.setUuid(uuid);

		return journalArticle;
	}

	/**
	 * Removes the journal article with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the journal article
	 * @return the journal article that was removed
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle remove(long id) throws NoSuchArticleException {
		return remove((Serializable)id);
	}

	/**
	 * Removes the journal article with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the journal article
	 * @return the journal article that was removed
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle remove(Serializable primaryKey)
		throws NoSuchArticleException {
		Session session = null;

		try {
			session = openSession();

			JournalArticle journalArticle = (JournalArticle)session.get(JournalArticleImpl.class,
					primaryKey);

			if (journalArticle == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchArticleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(journalArticle);
		}
		catch (NoSuchArticleException nsee) {
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
	protected JournalArticle removeImpl(JournalArticle journalArticle) {
		journalArticle = toUnwrappedModel(journalArticle);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(journalArticle)) {
				journalArticle = (JournalArticle)session.get(JournalArticleImpl.class,
						journalArticle.getPrimaryKeyObj());
			}

			if (journalArticle != null) {
				session.delete(journalArticle);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (journalArticle != null) {
			clearCache(journalArticle);
		}

		return journalArticle;
	}

	@Override
	public JournalArticle updateImpl(
		com.liferay.portlet.journal.model.JournalArticle journalArticle) {
		journalArticle = toUnwrappedModel(journalArticle);

		boolean isNew = journalArticle.isNew();

		JournalArticleModelImpl journalArticleModelImpl = (JournalArticleModelImpl)journalArticle;

		if (Validator.isNull(journalArticle.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalArticle.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (journalArticle.isNew()) {
				session.save(journalArticle);

				journalArticle.setNew(false);
			}
			else {
				session.merge(journalArticle);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !JournalArticleModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { journalArticleModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalUuid(),
						journalArticleModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						journalArticleModelImpl.getUuid(),
						journalArticleModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalResourcePrimKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY,
					args);

				args = new Object[] { journalArticleModelImpl.getResourcePrimKey() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { journalArticleModelImpl.getGroupId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { journalArticleModelImpl.getCompanyId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_DDMSTRUCTUREKEY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalDDMStructureKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_DDMSTRUCTUREKEY,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_DDMSTRUCTUREKEY,
					args);

				args = new Object[] { journalArticleModelImpl.getDDMStructureKey() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_DDMSTRUCTUREKEY,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_DDMSTRUCTUREKEY,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_DDMTEMPLATEKEY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalDDMTemplateKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_DDMTEMPLATEKEY,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_DDMTEMPLATEKEY,
					args);

				args = new Object[] { journalArticleModelImpl.getDDMTemplateKey() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_DDMTEMPLATEKEY,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_DDMTEMPLATEKEY,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTUUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalLayoutUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LAYOUTUUID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTUUID,
					args);

				args = new Object[] { journalArticleModelImpl.getLayoutUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LAYOUTUUID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTUUID,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SMALLIMAGEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalSmallImageId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SMALLIMAGEID,
					args);

				args = new Object[] { journalArticleModelImpl.getSmallImageId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SMALLIMAGEID,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_I.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalResourcePrimKey(),
						journalArticleModelImpl.getOriginalIndexable()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_R_I, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_I,
					args);

				args = new Object[] {
						journalArticleModelImpl.getResourcePrimKey(),
						journalArticleModelImpl.getIndexable()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_R_I, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_I,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_ST.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalResourcePrimKey(),
						journalArticleModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_R_ST, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_ST,
					args);

				args = new Object[] {
						journalArticleModelImpl.getResourcePrimKey(),
						journalArticleModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_R_ST, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_ST,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalGroupId(),
						journalArticleModelImpl.getOriginalUserId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
					args);

				args = new Object[] {
						journalArticleModelImpl.getGroupId(),
						journalArticleModelImpl.getUserId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalGroupId(),
						journalArticleModelImpl.getOriginalFolderId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);

				args = new Object[] {
						journalArticleModelImpl.getGroupId(),
						journalArticleModelImpl.getFolderId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalGroupId(),
						journalArticleModelImpl.getOriginalArticleId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);

				args = new Object[] {
						journalArticleModelImpl.getGroupId(),
						journalArticleModelImpl.getArticleId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_UT.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalGroupId(),
						journalArticleModelImpl.getOriginalUrlTitle()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_UT, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_UT,
					args);

				args = new Object[] {
						journalArticleModelImpl.getGroupId(),
						journalArticleModelImpl.getUrlTitle()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_UT, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_UT,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_DDMSK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalGroupId(),
						journalArticleModelImpl.getOriginalDDMStructureKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_DDMSK, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_DDMSK,
					args);

				args = new Object[] {
						journalArticleModelImpl.getGroupId(),
						journalArticleModelImpl.getDDMStructureKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_DDMSK, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_DDMSK,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_DDMTK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalGroupId(),
						journalArticleModelImpl.getOriginalDDMTemplateKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_DDMTK, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_DDMTK,
					args);

				args = new Object[] {
						journalArticleModelImpl.getGroupId(),
						journalArticleModelImpl.getDDMTemplateKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_DDMTK, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_DDMTK,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalGroupId(),
						journalArticleModelImpl.getOriginalLayoutUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_L, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L,
					args);

				args = new Object[] {
						journalArticleModelImpl.getGroupId(),
						journalArticleModelImpl.getLayoutUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_L, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_ST.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalGroupId(),
						journalArticleModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_ST, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_ST,
					args);

				args = new Object[] {
						journalArticleModelImpl.getGroupId(),
						journalArticleModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_ST, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_ST,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalCompanyId(),
						journalArticleModelImpl.getOriginalVersion()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_V, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_V,
					args);

				args = new Object[] {
						journalArticleModelImpl.getCompanyId(),
						journalArticleModelImpl.getVersion()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_V, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_V,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_ST.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalCompanyId(),
						journalArticleModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_ST, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_ST,
					args);

				args = new Object[] {
						journalArticleModelImpl.getCompanyId(),
						journalArticleModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_ST, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_ST,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_DDMTK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalClassNameId(),
						journalArticleModelImpl.getOriginalDDMTemplateKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_DDMTK, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_DDMTK,
					args);

				args = new Object[] {
						journalArticleModelImpl.getClassNameId(),
						journalArticleModelImpl.getDDMTemplateKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_DDMTK, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_DDMTK,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_I_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalResourcePrimKey(),
						journalArticleModelImpl.getOriginalIndexable(),
						journalArticleModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_R_I_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_I_S,
					args);

				args = new Object[] {
						journalArticleModelImpl.getResourcePrimKey(),
						journalArticleModelImpl.getIndexable(),
						journalArticleModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_R_I_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_I_S,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalGroupId(),
						journalArticleModelImpl.getOriginalUserId(),
						journalArticleModelImpl.getOriginalClassNameId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C,
					args);

				args = new Object[] {
						journalArticleModelImpl.getGroupId(),
						journalArticleModelImpl.getUserId(),
						journalArticleModelImpl.getClassNameId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_C,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_ST.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalGroupId(),
						journalArticleModelImpl.getOriginalFolderId(),
						journalArticleModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F_ST, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_ST,
					args);

				args = new Object[] {
						journalArticleModelImpl.getGroupId(),
						journalArticleModelImpl.getFolderId(),
						journalArticleModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F_ST, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_ST,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalGroupId(),
						journalArticleModelImpl.getOriginalClassNameId(),
						journalArticleModelImpl.getOriginalClassPK()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C,
					args);

				args = new Object[] {
						journalArticleModelImpl.getGroupId(),
						journalArticleModelImpl.getClassNameId(),
						journalArticleModelImpl.getClassPK()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_DDMTK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalGroupId(),
						journalArticleModelImpl.getOriginalClassNameId(),
						journalArticleModelImpl.getOriginalDDMTemplateKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_DDMTK,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_DDMTK,
					args);

				args = new Object[] {
						journalArticleModelImpl.getGroupId(),
						journalArticleModelImpl.getClassNameId(),
						journalArticleModelImpl.getDDMTemplateKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_DDMTK,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_DDMTK,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_L.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalGroupId(),
						journalArticleModelImpl.getOriginalClassNameId(),
						journalArticleModelImpl.getOriginalLayoutUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_L, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_L,
					args);

				args = new Object[] {
						journalArticleModelImpl.getGroupId(),
						journalArticleModelImpl.getClassNameId(),
						journalArticleModelImpl.getLayoutUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_L, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_L,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_ST.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalGroupId(),
						journalArticleModelImpl.getOriginalArticleId(),
						journalArticleModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_A_ST, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_ST,
					args);

				args = new Object[] {
						journalArticleModelImpl.getGroupId(),
						journalArticleModelImpl.getArticleId(),
						journalArticleModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_A_ST, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_ST,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_UT_ST.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalGroupId(),
						journalArticleModelImpl.getOriginalUrlTitle(),
						journalArticleModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_UT_ST, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_UT_ST,
					args);

				args = new Object[] {
						journalArticleModelImpl.getGroupId(),
						journalArticleModelImpl.getUrlTitle(),
						journalArticleModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_UT_ST, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_UT_ST,
					args);
			}

			if ((journalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_V_ST.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleModelImpl.getOriginalCompanyId(),
						journalArticleModelImpl.getOriginalVersion(),
						journalArticleModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_V_ST, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_V_ST,
					args);

				args = new Object[] {
						journalArticleModelImpl.getCompanyId(),
						journalArticleModelImpl.getVersion(),
						journalArticleModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_V_ST, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_V_ST,
					args);
			}
		}

		EntityCacheUtil.putResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleImpl.class, journalArticle.getPrimaryKey(),
			journalArticle, false);

		clearUniqueFindersCache(journalArticle);
		cacheUniqueFindersCache(journalArticle);

		journalArticle.resetOriginalValues();

		return journalArticle;
	}

	protected JournalArticle toUnwrappedModel(JournalArticle journalArticle) {
		if (journalArticle instanceof JournalArticleImpl) {
			return journalArticle;
		}

		JournalArticleImpl journalArticleImpl = new JournalArticleImpl();

		journalArticleImpl.setNew(journalArticle.isNew());
		journalArticleImpl.setPrimaryKey(journalArticle.getPrimaryKey());

		journalArticleImpl.setUuid(journalArticle.getUuid());
		journalArticleImpl.setId(journalArticle.getId());
		journalArticleImpl.setResourcePrimKey(journalArticle.getResourcePrimKey());
		journalArticleImpl.setGroupId(journalArticle.getGroupId());
		journalArticleImpl.setCompanyId(journalArticle.getCompanyId());
		journalArticleImpl.setUserId(journalArticle.getUserId());
		journalArticleImpl.setUserName(journalArticle.getUserName());
		journalArticleImpl.setCreateDate(journalArticle.getCreateDate());
		journalArticleImpl.setModifiedDate(journalArticle.getModifiedDate());
		journalArticleImpl.setFolderId(journalArticle.getFolderId());
		journalArticleImpl.setClassNameId(journalArticle.getClassNameId());
		journalArticleImpl.setClassPK(journalArticle.getClassPK());
		journalArticleImpl.setTreePath(journalArticle.getTreePath());
		journalArticleImpl.setArticleId(journalArticle.getArticleId());
		journalArticleImpl.setVersion(journalArticle.getVersion());
		journalArticleImpl.setTitle(journalArticle.getTitle());
		journalArticleImpl.setUrlTitle(journalArticle.getUrlTitle());
		journalArticleImpl.setDescription(journalArticle.getDescription());
		journalArticleImpl.setContent(journalArticle.getContent());
		journalArticleImpl.setDDMStructureKey(journalArticle.getDDMStructureKey());
		journalArticleImpl.setDDMTemplateKey(journalArticle.getDDMTemplateKey());
		journalArticleImpl.setLayoutUuid(journalArticle.getLayoutUuid());
		journalArticleImpl.setDisplayDate(journalArticle.getDisplayDate());
		journalArticleImpl.setExpirationDate(journalArticle.getExpirationDate());
		journalArticleImpl.setReviewDate(journalArticle.getReviewDate());
		journalArticleImpl.setIndexable(journalArticle.isIndexable());
		journalArticleImpl.setSmallImage(journalArticle.isSmallImage());
		journalArticleImpl.setSmallImageId(journalArticle.getSmallImageId());
		journalArticleImpl.setSmallImageURL(journalArticle.getSmallImageURL());
		journalArticleImpl.setStatus(journalArticle.getStatus());
		journalArticleImpl.setStatusByUserId(journalArticle.getStatusByUserId());
		journalArticleImpl.setStatusByUserName(journalArticle.getStatusByUserName());
		journalArticleImpl.setStatusDate(journalArticle.getStatusDate());

		return journalArticleImpl;
	}

	/**
	 * Returns the journal article with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal article
	 * @return the journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle findByPrimaryKey(Serializable primaryKey)
		throws NoSuchArticleException {
		JournalArticle journalArticle = fetchByPrimaryKey(primaryKey);

		if (journalArticle == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchArticleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return journalArticle;
	}

	/**
	 * Returns the journal article with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchArticleException} if it could not be found.
	 *
	 * @param id the primary key of the journal article
	 * @return the journal article
	 * @throws com.liferay.portlet.journal.NoSuchArticleException if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle findByPrimaryKey(long id)
		throws NoSuchArticleException {
		return findByPrimaryKey((Serializable)id);
	}

	/**
	 * Returns the journal article with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal article
	 * @return the journal article, or <code>null</code> if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle fetchByPrimaryKey(Serializable primaryKey) {
		JournalArticle journalArticle = (JournalArticle)EntityCacheUtil.getResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
				JournalArticleImpl.class, primaryKey);

		if (journalArticle == _nullJournalArticle) {
			return null;
		}

		if (journalArticle == null) {
			Session session = null;

			try {
				session = openSession();

				journalArticle = (JournalArticle)session.get(JournalArticleImpl.class,
						primaryKey);

				if (journalArticle != null) {
					cacheResult(journalArticle);
				}
				else {
					EntityCacheUtil.putResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
						JournalArticleImpl.class, primaryKey,
						_nullJournalArticle);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
					JournalArticleImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return journalArticle;
	}

	/**
	 * Returns the journal article with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the journal article
	 * @return the journal article, or <code>null</code> if a journal article with the primary key could not be found
	 */
	@Override
	public JournalArticle fetchByPrimaryKey(long id) {
		return fetchByPrimaryKey((Serializable)id);
	}

	@Override
	public Map<Serializable, JournalArticle> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, JournalArticle> map = new HashMap<Serializable, JournalArticle>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			JournalArticle journalArticle = fetchByPrimaryKey(primaryKey);

			if (journalArticle != null) {
				map.put(primaryKey, journalArticle);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			JournalArticle journalArticle = (JournalArticle)EntityCacheUtil.getResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
					JournalArticleImpl.class, primaryKey);

			if (journalArticle == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, journalArticle);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_JOURNALARTICLE_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (JournalArticle journalArticle : (List<JournalArticle>)q.list()) {
				map.put(journalArticle.getPrimaryKeyObj(), journalArticle);

				cacheResult(journalArticle);

				uncachedPrimaryKeys.remove(journalArticle.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(JournalArticleModelImpl.ENTITY_CACHE_ENABLED,
					JournalArticleImpl.class, primaryKey, _nullJournalArticle);
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
	 * Returns all the journal articles.
	 *
	 * @return the journal articles
	 */
	@Override
	public List<JournalArticle> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal articles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of journal articles
	 */
	@Override
	public List<JournalArticle> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal articles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal articles
	 */
	@Override
	public List<JournalArticle> findAll(int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {
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

		List<JournalArticle> list = (List<JournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_JOURNALARTICLE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_JOURNALARTICLE;

				if (pagination) {
					sql = sql.concat(JournalArticleModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticle>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the journal articles from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (JournalArticle journalArticle : findAll()) {
			remove(journalArticle);
		}
	}

	/**
	 * Returns the number of journal articles.
	 *
	 * @return the number of journal articles
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_JOURNALARTICLE);

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

	@Override
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Initializes the journal article persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		EntityCacheUtil.removeCache(JournalArticleImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_JOURNALARTICLE = "SELECT journalArticle FROM JournalArticle journalArticle";
	private static final String _SQL_SELECT_JOURNALARTICLE_WHERE_PKS_IN = "SELECT journalArticle FROM JournalArticle journalArticle WHERE id_ IN (";
	private static final String _SQL_SELECT_JOURNALARTICLE_WHERE = "SELECT journalArticle FROM JournalArticle journalArticle WHERE ";
	private static final String _SQL_COUNT_JOURNALARTICLE = "SELECT COUNT(journalArticle) FROM JournalArticle journalArticle";
	private static final String _SQL_COUNT_JOURNALARTICLE_WHERE = "SELECT COUNT(journalArticle) FROM JournalArticle journalArticle WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "journalArticle.resourcePrimKey";
	private static final String _FILTER_SQL_SELECT_JOURNALARTICLE_WHERE = "SELECT DISTINCT {journalArticle.*} FROM JournalArticle journalArticle WHERE ";
	private static final String _FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {JournalArticle.*} FROM (SELECT DISTINCT journalArticle.id_ FROM JournalArticle journalArticle WHERE ";
	private static final String _FILTER_SQL_SELECT_JOURNALARTICLE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN JournalArticle ON TEMP_TABLE.id_ = JournalArticle.id_";
	private static final String _FILTER_SQL_COUNT_JOURNALARTICLE_WHERE = "SELECT COUNT(DISTINCT journalArticle.id_) AS COUNT_VALUE FROM JournalArticle journalArticle WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "journalArticle";
	private static final String _FILTER_ENTITY_TABLE = "JournalArticle";
	private static final String _ORDER_BY_ENTITY_ALIAS = "journalArticle.";
	private static final String _ORDER_BY_ENTITY_TABLE = "JournalArticle.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JournalArticle exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JournalArticle exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static final Log _log = LogFactoryUtil.getLog(JournalArticlePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "id"
			});
	private static final JournalArticle _nullJournalArticle = new JournalArticleImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<JournalArticle> toCacheModel() {
				return _nullJournalArticleCacheModel;
			}
		};

	private static final CacheModel<JournalArticle> _nullJournalArticleCacheModel =
		new CacheModel<JournalArticle>() {
			@Override
			public JournalArticle toEntityModel() {
				return _nullJournalArticle;
			}
		};
}