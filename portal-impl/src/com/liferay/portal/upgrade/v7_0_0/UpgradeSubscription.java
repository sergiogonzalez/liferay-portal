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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.model.Layout;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eduardo Garcia
 * @author Roberto Díaz
 * @author Iván Zaera
 */
public class UpgradeSubscription extends UpgradeProcess {

	public interface SQLVisitor {
		public void visit(ResultSet rs) throws Exception;

	}

	@Override
	protected void doUpgrade() throws Exception {
		updateSubscriptionClassNames(
			Folder.class.getName(), DLFolder.class.getName());
		updateSubscriptionClassNames(
			JournalArticle.class.getName(), JournalFolder.class.getName());

		updateGroupIds();
	}

	protected String getClassName(long classNameId) throws SQLException {
		String className = _classNamesMap.get(classNameId);

		if (className == null) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				con = DataAccess.getUpgradeOptimizedConnection();

				ps = con.prepareStatement(
					"select value from ClassName_ where classNameId = ?");

				ps.setLong(1, classNameId);

				rs = ps.executeQuery();

				if (rs.next()) {
					className = rs.getString("value");
				}
				else {
					className = "";
				}
			}
			finally {
				DataAccess.cleanUp(con, ps, rs);
			}

			_classNamesMap.put(classNameId, className);
		}

		return className;
	}

	protected long getLongSQL(String sql) throws Exception {
		final long[] value = new long[1];

		visitSQL(
			sql, new SQLVisitor() {
				@Override
				public void visit(ResultSet rs) throws Exception {
					value[0] = rs.getLong(1);
				}
			});

		return value[0];
	}

	protected void updateGroupIds() throws Exception {
		visitSQL(
			"select subscriptionId, classNameId, classPK from Subscription",
			new SQLVisitor() {
				@Override
				public void visit(ResultSet rs) throws Exception {
					long subscriptionId = rs.getLong("subscriptionId");
					long classNameId = rs.getLong("classNameId");
					long classPK = rs.getLong("classPK");

					String className = getClassName(classNameId);

					if (Validator.isNotNull(className)) {
						updateSubscriptionGroupId(
							subscriptionId, className, classPK);
					}
				}
			});
	}

	protected void updateSubscriptionClassNames(
			String oldClassName, String newClassName)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append("update Subscription set classNameId = ");
		sb.append(PortalUtil.getClassNameId(newClassName));
		sb.append(" where classNameId = ");
		sb.append(PortalUtil.getClassNameId(oldClassName));

		runSQL(sb.toString());
	}

	protected void updateSubscriptionGroupId(
			long subscriptionId, String className, long classPK)
		throws Exception {

		long groupId = 0;

		if (className.equals(BlogsEntry.class.getName())) {
			groupId = getLongSQL(
				"select groupId from BlogsEntry where entryId = " + classPK);
		}
		else if (className.equals(
					"com.liferay.bookmarks.model.BookmarksEntry")) {

			groupId = getLongSQL(
				"select groupId from BookmarksEntry where entryId = " +
					classPK);
		}
		else if (className.equals(
					"com.liferay.bookmarks.model.BookmarksFolder")) {

			groupId = getLongSQL(
				"select groupId from BookmarksFolder where folderId = " +
					classPK);
		}
		else if (className.equals(DDMStructure.class.getName())) {
			groupId = getLongSQL(
				"select groupId from DDMStructure where structureId = " +
					classPK);
		}
		else if (className.equals(DLFileEntry.class)) {
			groupId = getLongSQL(
				"select groupId from DLFileEntry where fileEntryId = " +
					classPK);
		}
		else if (className.equals(DLFileEntryType.class.getName())) {
			groupId = getLongSQL(
				"select groupId from DLFileEntryType where fileEntryTypeId = " +
					classPK);
		}
		else if (className.equals(DLFolder.class.getName())) {
			groupId = getLongSQL(
				"select groupId from DLFolder where folderId = " + classPK);
		}
		else if (className.equals(JournalFolder.class.getName())) {
			groupId = getLongSQL(
				"select groupId from JournalFolder where folderId = " +
					classPK);
		}
		else if (className.equals(Layout.class.getName())) {
			groupId = getLongSQL(
				"select groupId from Layout where plid = " + classPK);
		}
		else if (className.equals(MBCategory.class.getName())) {
			groupId = getLongSQL(
				"select groupId from MBCategory where categoryId = " + classPK);
		}
		else if (className.equals(MBThread.class.getName())) {
			groupId = getLongSQL(
				"select groupId from MBThread where threadId = " + classPK);
		}
		else if (className.equals(SCProductEntry.class.getName())) {
			groupId = getLongSQL(
				"select groupId from SCProductEntry where productEntryId = " +
					classPK);
		}
		else if (className.equals(ShoppingOrder.class.getName())) {
			groupId = getLongSQL(
				"select groupId from ShoppingOrder where orderId = " + classPK);
		}
		else if (className.equals("com.liferay.portlet.wiki.model.WikiNode")) {
			groupId = getLongSQL(
				"select groupId from WikiNode where nodeId = " + classPK);
		}
		else if (className.equals("com.liferay.portlet.wiki.model.WikiPage")) {
			groupId = getLongSQL(
				"select groupId from WikiPage where resourcePrimKey = " +
					classPK);
		}
		else if (className.equals(WorkflowInstance.class.getName())) {
			groupId = getLongSQL(
				"select groupId from WorkflowInstance where " +
					"workflowInstanceId = " + classPK);
		}

		if (groupId == 0) {
			groupId = getLongSQL(
				"select groupId from Group_ where groupId = " + classPK);
		}

		if (groupId != 0) {
			runSQL(
				"update Subscription set groupId = " + groupId + " where " +
					"subscriptionId = " + subscriptionId);
		}
	}

	protected void visitSQL(String sql, SQLVisitor visitor) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				visitor.visit(rs);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private final Map<Long, String> _classNamesMap = new HashMap<>();

}