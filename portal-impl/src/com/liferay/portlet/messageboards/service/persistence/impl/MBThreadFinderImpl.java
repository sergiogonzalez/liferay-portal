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

package com.liferay.portlet.messageboards.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.exception.NoSuchThreadException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBThreadImpl;
import com.liferay.portlet.messageboards.service.persistence.MBThreadFinder;
import com.liferay.portlet.messageboards.service.persistence.MBThreadUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class MBThreadFinderImpl
	extends MBThreadFinderBaseImpl implements MBThreadFinder {

	public static final String COUNT_BY_G_U =
		MBThreadFinder.class.getName() + ".countByG_U";

	public static final String COUNT_BY_G_C =
		MBThreadFinder.class.getName() + ".countByG_C";

	public static final String COUNT_BY_G_U_C =
		MBThreadFinder.class.getName() + ".countByG_U_C";

	public static final String COUNT_BY_G_U_LPD =
		MBThreadFinder.class.getName() + ".countByG_U_LPD";

	public static final String COUNT_BY_G_U_A =
		MBThreadFinder.class.getName() + ".countByG_U_A";

	public static final String COUNT_BY_S_G_U =
		MBThreadFinder.class.getName() + ".countByS_G_U";

	public static final String COUNT_BY_G_U_C_A =
		MBThreadFinder.class.getName() + ".countByG_U_C_A";

	public static final String COUNT_BY_S_G_U_C =
		MBThreadFinder.class.getName() + ".countByS_G_U_C";

	public static final String FIND_BY_NO_ASSETS =
		MBThreadFinder.class.getName() + ".findByNoAssets";

	public static final String FIND_BY_G_U =
		MBThreadFinder.class.getName() + ".findByG_U";

	public static final String FIND_BY_G_C =
		MBThreadFinder.class.getName() + ".findByG_C";

	public static final String FIND_BY_G_C_S_PREVANDNEXT =
		MBThreadFinder.class.getName() + ".findByG_C_S_PrevAndNext";

	public static final String FIND_BY_G_C_NOTS_PREVANDNEXT =
		MBThreadFinder.class.getName() + ".findByG_C_NotS_PrevAndNext";

	public static final String FIND_BY_G_U_C =
		MBThreadFinder.class.getName() + ".findByG_U_C";

	public static final String FIND_BY_G_U_LPD =
		MBThreadFinder.class.getName() + ".findByG_U_LPD";

	public static final String FIND_BY_G_U_A =
		MBThreadFinder.class.getName() + ".findByG_U_A";

	public static final String FIND_BY_S_G_U =
		MBThreadFinder.class.getName() + ".findByS_G_U";

	public static final String FIND_BY_G_U_C_A =
		MBThreadFinder.class.getName() + ".findByG_U_C_A";

	public static final String FIND_BY_S_G_U_C =
		MBThreadFinder.class.getName() + ".findByS_G_U_C";

	@Override
	public int countByG_U(
		long groupId, long userId, QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_U);

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countByG_C(
		long groupId, long categoryId,
		QueryDefinition<MBThread> queryDefinition) {

		return doCountByG_C(groupId, categoryId, queryDefinition, false);
	}

	@Override
	public int countByG_U_C(
		long groupId, long userId, long[] categoryIds,
		QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_U_C);

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(MBThread.categoryId = ?) AND", StringPool.BLANK);
			}
			else {
				sql = StringUtil.replace(
					sql, "MBThread.categoryId = ?",
					"MBThread.categoryId = " +
						StringUtil.merge(
							categoryIds, " OR MBThread.categoryId = "));
			}

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countByG_U_LPD(
		long groupId, long userId, Date lastPostDate,
		QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_U_LPD);

			if (userId <= 0) {
				sql = StringUtil.replace(
					sql, _INNER_JOIN_SQL, StringPool.BLANK);
				sql = StringUtil.replace(sql, _USER_ID_SQL, StringPool.BLANK);
			}

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(lastPostDate);

			if (userId > 0) {
				qPos.add(userId);
			}

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countByG_U_A(
		long groupId, long userId, boolean anonymous,
		QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_U_A);

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);
			qPos.add(anonymous);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countByS_G_U(
		long groupId, long userId, QueryDefinition<MBThread> queryDefinition) {

		return doCountByS_G_U(groupId, userId, queryDefinition);
	}

	@Override
	public int countByG_U_C_A(
		long groupId, long userId, long[] categoryIds, boolean anonymous,
		QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_U_C);

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(MBThread.categoryId = ?) AND", StringPool.BLANK);
			}
			else {
				sql = StringUtil.replace(
					sql, "MBThread.categoryId = ?",
					"MBThread.categoryId = " +
						StringUtil.merge(
							categoryIds, " OR MBThread.categoryId = "));
			}

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);
			qPos.add(anonymous);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		QueryDefinition<MBThread> queryDefinition) {

		return doCountByS_G_U_C(
			groupId, userId, categoryIds, queryDefinition, false);
	}

	@Override
	public int filterCountByG_C(long groupId, long categoryId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return MBThreadUtil.countByG_C(groupId, categoryId);
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_C);

			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, MBMessage.class.getName(), "MBThread.rootMessageId",
				groupId);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(categoryId);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int filterCountByG_C(
		long groupId, long categoryId,
		QueryDefinition<MBThread> queryDefinition) {

		return doCountByG_C(groupId, categoryId, queryDefinition, true);
	}

	@Override
	public int filterCountByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		QueryDefinition<MBThread> queryDefinition) {

		return doCountByS_G_U_C(
			groupId, userId, categoryIds, queryDefinition, true);
	}

	@Override
	public List<MBThread> filterFindByG_C(
		long groupId, long categoryId, int start, int end) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return MBThreadUtil.findByG_C(groupId, categoryId, start, end);
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_C);

			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, MBMessage.class.getName(), "MBThread.rootMessageId",
				groupId);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(categoryId);

			return (List<MBThread>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<MBThread> filterFindByG_C(
		long groupId, long categoryId,
		QueryDefinition<MBThread> queryDefinition) {

		return doFindByG_C(groupId, categoryId, queryDefinition, true);
	}

	public MBThread[] filterFindByG_C_S_PrevAndNext(
			long threadId, long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return mbThreadPersistence.findByG_C_S_PrevAndNext(
				threadId, groupId, categoryId, status, orderByComparator);
		}

		MBThread mbThread = mbThreadPersistence.findByPrimaryKey(threadId);

		MBThread[] array = new MBThreadImpl[3];

		array[0] = doFindByG_C_S_PrevAndNext(mbThread, groupId,
			categoryId, status, orderByComparator, true);

		array[1] = mbThread;

		array[2] = doFindByG_C_S_PrevAndNext(mbThread, groupId,
			categoryId, status, orderByComparator, false);

		return array;
	}

	public MBThread[] filterFindByG_C_NotS_PrevAndNext(
			long threadId, long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return mbThreadPersistence.findByG_C_NotS_PrevAndNext(
				threadId, groupId, categoryId, status, orderByComparator);
		}

		MBThread mbThread = mbThreadPersistence.findByPrimaryKey(threadId);

		MBThread[] array = new MBThreadImpl[3];

		array[0] = doFindByG_C_NotS_PrevAndNext(mbThread, groupId,
			categoryId, status, orderByComparator, true);

		array[1] = mbThread;

		array[2] = doFindByG_C_NotS_PrevAndNext(mbThread, groupId,
			categoryId, status, orderByComparator, false);

		return array;
	}

	@Override
	public List<MBThread> filterFindByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		QueryDefinition<MBThread> queryDefinition) {

		return doFindByS_G_U_C(
			groupId, userId, categoryIds, queryDefinition, true);
	}

	@Override
	public List<MBThread> findByNoAssets() {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_ASSETS);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<MBThread> findByG_U(
		long groupId, long userId, QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_U);

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<MBThread>)QueryUtil.list(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<MBThread> findByG_C(
		long groupId, long categoryId,
		QueryDefinition<MBThread> queryDefinition) {

		return doFindByG_C(groupId, categoryId, queryDefinition, false);
	}

	@Override
	public List<MBThread> findByG_U_C(
		long groupId, long userId, long[] categoryIds,
		QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_U_C);

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(MBThread.categoryId = ?) AND", StringPool.BLANK);
			}
			else {
				sql = StringUtil.replace(
					sql, "MBThread.categoryId = ?",
					"MBThread.categoryId = " +
						StringUtil.merge(
							categoryIds, " OR MBThread.categoryId = "));
			}

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<MBThread>)QueryUtil.list(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<MBThread> findByG_U_LPD(
		long groupId, long userId, Date lastPostDate,
		QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_U_LPD);

			if (userId <= 0) {
				sql = StringUtil.replace(
					sql, _INNER_JOIN_SQL, StringPool.BLANK);
				sql = StringUtil.replace(sql, _USER_ID_SQL, StringPool.BLANK);
			}

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(lastPostDate);

			if (userId > 0) {
				qPos.add(userId);
			}

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<MBThread>)QueryUtil.list(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<MBThread> findByG_U_A(
		long groupId, long userId, boolean anonymous,
		QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_U_A);

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);
			qPos.add(anonymous);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<MBThread>)QueryUtil.list(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<MBThread> findByS_G_U(
		long groupId, long userId, QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_S_G_U);

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(PortalUtil.getClassNameId(MBThread.class.getName()));
			qPos.add(groupId);
			qPos.add(userId);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<MBThread>)QueryUtil.list(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<MBThread> findByG_U_C_A(
		long groupId, long userId, long[] categoryIds, boolean anonymous,
		QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_U_C_A);

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(MBThread.categoryId = ?) AND", StringPool.BLANK);
			}
			else {
				sql = StringUtil.replace(
					sql, "MBThread.categoryId = ?",
					"MBThread.categoryId = " +
						StringUtil.merge(
							categoryIds, " OR MBThread.categoryId = "));
			}

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(userId);
			qPos.add(anonymous);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<MBThread>)QueryUtil.list(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<MBThread> findByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		QueryDefinition<MBThread> queryDefinition) {

		return doFindByS_G_U_C(
			groupId, userId, categoryIds, queryDefinition, false);
	}

	protected String appendOrderByComparator(
		String sql, OrderByComparator<MBThread> orderByComparator, boolean previous){

		if (orderByComparator == null) {
			return sql;
		}

		StringBundler query = new StringBundler();

		String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

		if (orderByConditionFields.length > 0) {
			query.append(WHERE_AND);
		}

		for (int i = 0; i < orderByConditionFields.length; i++) {
			query.append("MBThread.");

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
			query.append("MBThread.");
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

		int pos = sql.indexOf(ORDER_BY_CLAUSE);

		if ((pos != -1) && (pos < sql.length())) {
			return sql.substring(0, pos).concat(query.toString());
		}
		else {
			return sql.concat(query.toString());
		}
	}

	protected int doCountByG_C(
		long groupId, long categoryId,
		QueryDefinition<MBThread> queryDefinition, boolean inlineSQLHelper) {

		if (!inlineSQLHelper || !InlineSQLHelperUtil.isEnabled(groupId)) {
			if (queryDefinition.isExcludeStatus()) {
				return MBThreadUtil.countByG_C_NotS(
					groupId, categoryId, queryDefinition.getStatus());
			}
			else {
				if (queryDefinition.getStatus() !=
						WorkflowConstants.STATUS_ANY) {

					return MBThreadUtil.countByG_C_S(
						groupId, categoryId, queryDefinition.getStatus());
				}
				else {
					return MBThreadUtil.countByG_C(groupId, categoryId);
				}
			}
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_C);

			sql = updateSQL(sql, queryDefinition);

			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, MBMessage.class.getName(), "MBThread.rootMessageId",
				groupId);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(categoryId);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected int doCountByS_G_U(
		long groupId, long userId, QueryDefinition<MBThread> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_S_G_U);

			sql = updateSQL(sql, queryDefinition);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(PortalUtil.getClassNameId(MBThread.class.getName()));
			qPos.add(groupId);
			qPos.add(userId);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected int doCountByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		QueryDefinition<MBThread> queryDefinition, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_S_G_U_C);

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(MBThread.categoryId = ?) AND", StringPool.BLANK);
			}
			else {
				sql = StringUtil.replace(
					sql, "MBThread.categoryId = ?",
					"MBThread.categoryId = " +
						StringUtil.merge(
							categoryIds, " OR MBThread.categoryId = "));
			}

			sql = updateSQL(sql, queryDefinition);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, MBMessage.class.getName(), "MBThread.rootMessageId",
					groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(PortalUtil.getClassNameId(MBThread.class.getName()));
			qPos.add(groupId);
			qPos.add(userId);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<MBThread> doFindByG_C(
		long groupId, long categoryId,
		QueryDefinition<MBThread> queryDefinition, boolean inlineSQLHelper) {

		if (!inlineSQLHelper || !InlineSQLHelperUtil.isEnabled(groupId)) {
			if (queryDefinition.isExcludeStatus()) {
				return MBThreadUtil.findByG_C_NotS(
					groupId, categoryId, queryDefinition.getStatus(),
					queryDefinition.getStart(), queryDefinition.getEnd());
			}
			else {
				if (queryDefinition.getStatus() !=
						WorkflowConstants.STATUS_ANY) {

					return MBThreadUtil.findByG_C_S(
						groupId, categoryId, queryDefinition.getStatus(),
						queryDefinition.getStart(), queryDefinition.getEnd());
				}
				else {
					return MBThreadUtil.findByG_C(
						groupId, categoryId, queryDefinition.getStart(),
						queryDefinition.getEnd());
				}
			}
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_C);

			sql = updateSQL(sql, queryDefinition);

			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, MBMessage.class.getName(), "MBThread.rootMessageId",
				groupId);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(categoryId);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<MBThread>)QueryUtil.list(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread doFindByG_C_S_PrevAndNext(
			MBThread mbThread, long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator, boolean previous) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_C_S_PREVANDNEXT);

			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, MBMessage.class.getName(), "MBThread.rootMessageId",
				groupId);

			sql = appendOrderByComparator(sql, orderByComparator, previous);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.setFirstResult(0);
			q.setMaxResults(2);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(status);

			if (orderByComparator != null) {
				Object[] values = orderByComparator.getOrderByConditionValues(
					mbThread);

				for (Object value : values) {
					qPos.add(value);
				}
			}

			List<MBThread> list = q.list();

			if (list.size() == 2) {
				return list.get(1);
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread doFindByG_C_NotS_PrevAndNext(
			MBThread mbThread, long groupId, long categoryId, int status,
			OrderByComparator<MBThread> orderByComparator, boolean previous) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_C_NOTS_PREVANDNEXT);

			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, MBMessage.class.getName(), "MBThread.rootMessageId",
				groupId);

			sql = appendOrderByComparator(sql, orderByComparator, previous);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.setFirstResult(0);
			q.setMaxResults(2);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(status);

			if (orderByComparator != null) {
				Object[] values = orderByComparator.getOrderByConditionValues(
					mbThread);

				for (Object value : values) {
					qPos.add(value);
				}
			}

			List<MBThread> list = q.list();

			if (list.size() == 2) {
				return list.get(1);
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<MBThread> doFindByS_G_U_C(
		long groupId, long userId, long[] categoryIds,
		QueryDefinition<MBThread> queryDefinition, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_S_G_U_C);

			if (ArrayUtil.isEmpty(categoryIds)) {
				sql = StringUtil.replace(
					sql, "(MBThread.categoryId = ?) AND", StringPool.BLANK);
			}
			else {
				sql = StringUtil.replace(
					sql, "MBThread.categoryId = ?",
					"MBThread.categoryId = " +
						StringUtil.merge(
							categoryIds, " OR MBThread.categoryId = "));
			}

			sql = updateSQL(sql, queryDefinition);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, MBMessage.class.getName(), "MBThread.rootMessageId",
					groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MBThread", MBThreadImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(PortalUtil.getClassNameId(MBThread.class.getName()));
			qPos.add(groupId);
			qPos.add(userId);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				qPos.add(queryDefinition.getStatus());
			}

			return (List<MBThread>)QueryUtil.list(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String updateSQL(
		String sql, QueryDefinition<MBThread> queryDefinition) {

		if (queryDefinition.getStatus() == WorkflowConstants.STATUS_ANY) {
			return sql;
		}

		if (queryDefinition.isExcludeStatus()) {
			return CustomSQLUtil.appendCriteria(
				sql, "AND (MBThread.status != ?)");
		}

		return CustomSQLUtil.appendCriteria(sql, "AND (MBThread.status = ?)");
	}

	private static final String _INNER_JOIN_SQL =
		"INNER JOIN MBMessage ON (MBThread.threadId = MBMessage.threadId)";

	private static final String _USER_ID_SQL = "AND (MBMessage.userId = ?)";

}