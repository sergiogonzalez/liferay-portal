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

package com.liferay.portlet.documentlibrary.service.persistence.impl;

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.service.persistence.DLFileShortcutFinder;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Roberto Díaz
 */
public class DLFileShortcutFinderImpl
	extends DLFileShortcutFinderBaseImpl implements DLFileShortcutFinder {

	public static final String COUNT_BY_F_A =
		DLFileShortcutFinder.class.getName() + ".countByF_A";

	public static final String FIND_BY_F_A =
		DLFileShortcutFinder.class.getName() + ".findByF_A";

	@Override
	public int countByF_A(
		long folderId, boolean active, QueryDefinition<?> queryDefinition) {

		return doCountByF_A(folderId, active, queryDefinition, false);
	}

	@Override
	public int filterCountByF_A(
		long folderId, boolean active, QueryDefinition<?> queryDefinition) {

		return doCountByF_A(folderId, active, queryDefinition, true);
	}

	@Override
	public List<DLFileShortcut> filterFindByF_A(
		long folderId, boolean active, QueryDefinition<?> queryDefinition) {

		return doFindByF_A(folderId, active, queryDefinition, true);
	}

	@Override
	public List<DLFileShortcut> findByF_A(
		long folderId, boolean active, QueryDefinition<?> queryDefinition) {

		return doFindByF_A(folderId, active, queryDefinition, false);
	}

	protected int doCountByF_A(
		long folderId, boolean active, QueryDefinition<?> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(
				COUNT_BY_F_A, queryDefinition, DLFileShortcutImpl.TABLE_NAME);

			if (inlineSQLHelper && InlineSQLHelperUtil.isEnabled()) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, DLFileShortcut.class.getName(),
					"DLFileShortcut.fileShortcutId");
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(folderId);
			qPos.add(active);

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

	protected List<DLFileShortcut> doFindByF_A(
		long folderId, boolean active, QueryDefinition<?> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(
				FIND_BY_F_A, queryDefinition, DLFileShortcutImpl.TABLE_NAME);

			if (inlineSQLHelper && InlineSQLHelperUtil.isEnabled()) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, DLFileShortcut.class.getName(),
					"DLFileShortcut.fileShortcutId");
			}

			sql = CustomSQLUtil.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(
				DLFileShortcutImpl.TABLE_NAME, DLFileShortcutImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(folderId);
			qPos.add(active);
			qPos.add(queryDefinition.getStatus());

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}