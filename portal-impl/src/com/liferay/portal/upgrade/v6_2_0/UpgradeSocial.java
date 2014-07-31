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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.social.BlogsActivityKeys;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.social.BookmarksActivityKeys;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.social.WikiActivityKeys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Sergio Sanchez
 * @author Zsolt Berentey
 */
public class UpgradeSocial extends UpgradeProcess {

	protected void addActivity(
			long activityId, long groupId, long companyId, long userId,
			Timestamp modifiedDate, long mirrorActivityId, long classNameId,
			long classPK, int type, long receiverUserId, String title,
			double version, Set<String> keys)
		throws Exception {

		modifiedDate = getUniqueModifiedDate(
			keys, groupId, userId, modifiedDate, classNameId, classPK, type);

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", title);

		if (version != -1) {
			extraDataJSONObject.put("version", version);
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("insert into SocialActivity (activityId, groupId, ");
			sb.append("companyId, userId, createDate, mirrorActivityId, ");
			sb.append("classNameId, classPK, type_, extraData, ");
			sb.append("receiverUserId) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append("?)");

			ps = con.prepareStatement(sb.toString());

			ps.setLong(1, activityId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setLong(5, modifiedDate.getTime());
			ps.setLong(6, mirrorActivityId);
			ps.setLong(7, classNameId);
			ps.setLong(8, classPK);
			ps.setInt(9, type);
			ps.setString(10, extraDataJSONObject.toString());
			ps.setLong(11, receiverUserId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to add activity " + activityId, e);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateBlogsEntryActivities();
		updateBookmarksActivities();
		updateCalEventActivities();
		updateDLFileVersionActivities();
		updateJournalActivities();
		updateMBMessageActivities();
		updateSOSocialActivities();
		updateWikiPageActivities();
	}

	protected Timestamp getUniqueModifiedDate(
		Set<String> keys, long groupId, long userId, Timestamp modifiedDate,
		long classNameId, long resourcePrimKey, double type) {

		while (true) {
			StringBundler sb = new StringBundler(11);

			sb.append(groupId);
			sb.append(StringPool.DASH);
			sb.append(userId);
			sb.append(StringPool.DASH);
			sb.append(modifiedDate);
			sb.append(StringPool.DASH);
			sb.append(classNameId);
			sb.append(StringPool.DASH);
			sb.append(resourcePrimKey);
			sb.append(StringPool.DASH);
			sb.append(type);

			String key = sb.toString();

			modifiedDate = new Timestamp(modifiedDate.getTime() + 1);

			if (!keys.contains(key)) {
				keys.add(key);

				return modifiedDate;
			}
		}
	}

	protected void updateBlogsEntryActivities() throws Exception {
		long classNameId = PortalUtil.getClassNameId(BlogsEntry.class);

		runSQL(
			"delete from SocialActivity where classNameId = " + classNameId +
				" and type_ != " + SocialActivityConstants.TYPE_ADD_COMMENT);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Set<String> keys = new HashSet<String>();

			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId, companyId, userId, modifiedDate, entryId, " +
					"title from BlogsEntry where status = ? or status = ?");

			ps.setInt(1, WorkflowConstants.STATUS_APPROVED);
			ps.setInt(2, WorkflowConstants.STATUS_SCHEDULED);

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long entryId = rs.getLong("entryId");
				String title = rs.getString("title");

				addActivity(
					increment(), groupId, companyId, userId, modifiedDate, 0,
					classNameId, entryId, BlogsActivityKeys.ADD_ENTRY, 0, title,
					-1, keys);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateBookmarksActivities() throws Exception {
		long classNameId = PortalUtil.getClassNameId(BookmarksEntry.class);

		runSQL(
			"delete from SocialActivity where classNameId = " + classNameId +
				" and type_ != " + SocialActivityConstants.TYPE_ADD_COMMENT);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Set<String> keys = new HashSet<String>();

			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId, companyId, userId, modifiedDate, entryId, " +
					"name from BookmarksEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long entryId = rs.getLong("entryId");
				String name = rs.getString("name");

				addActivity(
					increment(), groupId, companyId, userId, modifiedDate, 0,
					classNameId, entryId, BookmarksActivityKeys.ADD_ENTRY, 0,
					name, -1, keys);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateCalEventActivities() throws Exception {
		long classNameId = PortalUtil.getClassNameId(CalEvent.class);

		runSQL(
			"delete from SocialActivity where classNameId = " + classNameId +
				" and type_ != " + SocialActivityConstants.TYPE_ADD_COMMENT);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Set<String> keys = new HashSet<String>();

			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId, companyId, userId, modifiedDate, eventId, " +
					"title from CalEvent");

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long eventId = rs.getLong("eventId");
				String title = rs.getString("title");

				addActivity(
					increment(), groupId, companyId, userId, modifiedDate, 0,
					classNameId, eventId, 1, 0, title, -1, keys);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateDLFileVersionActivities() throws Exception {
		long classNameId = PortalUtil.getClassNameId(DLFileEntry.class);

		runSQL(
			"delete from SocialActivity where classNameId = " + classNameId +
				" and type_ != " + SocialActivityConstants.TYPE_ADD_COMMENT);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Set<String> keys = new HashSet<String>();

			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId, companyId, userId, modifiedDate, " +
					"fileEntryId, title, version from DLFileVersion " +
						"where status = ?");

			ps.setInt(1, WorkflowConstants.STATUS_APPROVED);

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long fileEntryId = rs.getLong("fileEntryId");
				String title = rs.getString("title");
				double version = rs.getDouble("version");

				int type = DLActivityKeys.ADD_FILE_ENTRY;

				if (version > 1.0) {
					type = DLActivityKeys.UPDATE_FILE_ENTRY;
				}

				addActivity(
					increment(), groupId, companyId, userId, modifiedDate, 0,
					classNameId, fileEntryId, type, 0, title, -1, keys);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateJournalActivities() throws Exception {
		long classNameId = PortalUtil.getClassNameId(JournalArticle.class);

		String[] tableNames = {"SocialActivity", "SocialActivityCounter"};

		for (String tableName : tableNames) {
			StringBundler sb = new StringBundler(7);

			sb.append("update ");
			sb.append(tableName);
			sb.append(" set classPK = (select resourcePrimKey ");
			sb.append("from JournalArticle where id_ = ");
			sb.append(tableName);
			sb.append(".classPK) where classNameId = ");
			sb.append(classNameId);

			runSQL(sb.toString());
		}
	}

	protected void updateMBMessageActivities() throws Exception {
		long classNameId = PortalUtil.getClassNameId(MBMessage.class);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			long defaultParentMessageId =
				MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID;
			int type = SocialActivityConstants.TYPE_ADD_COMMENT;

			StringBundler sb = new StringBundler(7);

			sb.append("select messageId, classPK, subject from MBMessage");
			sb.append(" where messageId in");
			sb.append(" (select classPK from SocialActivity");
			sb.append(" where classNameId = ?)");
			sb.append(" or (classPK in (select classPK from SocialActivity");
			sb.append(" where type_ = ?)");
			sb.append(" and parentMessageId != ?)");

			ps = con.prepareStatement(sb.toString());

			ps.setLong(1, classNameId);
			ps.setInt(2, type);
			ps.setLong(3, defaultParentMessageId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long messageId = rs.getLong("messageId");
				long classPK = rs.getLong("classPK");
				String subject = rs.getString("subject");

				JSONObject extraDataJSONObject =
					JSONFactoryUtil.createJSONObject();

				extraDataJSONObject.put("title", subject);

				if (classPK > 0) {
					extraDataJSONObject.put("messageId", messageId);
				}

				sb = new StringBundler(11);

				sb.append("update SocialActivity set extraData = ");
				sb.append("'");
				sb.append(extraDataJSONObject.toString());
				sb.append("'");

				if (classPK > 0) {
					sb.append(" where classPK = ");
					sb.append(classPK);
					sb.append(" and type_ = ");
					sb.append(type);
					sb.append(" and extraData like '%\"messageId\":");
					sb.append(messageId);
					sb.append("%'");
				}
				else {
					sb.append(" where classPK = ");
					sb.append(messageId);
				}

				runSQL(sb.toString());
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateSOSocialActivities() throws Exception {
		if (!hasTable("SO_SocialActivity")) {
			return;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select activityId, activitySetId from SO_SocialActivity");

			rs = ps.executeQuery();

			while (rs.next()) {
				long activityId = rs.getLong("activityId");
				long activitySetId = rs.getLong("activitySetId");

				StringBundler sb = new StringBundler(4);

				sb.append("update SocialActivity set activitySetId = ");
				sb.append(activitySetId);
				sb.append(" where activityId = ");
				sb.append(activityId);

				runSQL(sb.toString());
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		runSQL("drop table SO_SocialActivity");
	}

	protected void updateWikiPageActivities() throws Exception {
		long classNameId = PortalUtil.getClassNameId(WikiPage.class);

		runSQL(
			"delete from SocialActivity where classNameId = " + classNameId +
				" and type_ != " + SocialActivityConstants.TYPE_ADD_COMMENT);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Set<String> keys = new HashSet<String>();

			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId, companyId, userId, modifiedDate, title, " +
					"resourcePrimKey, version from WikiPage " +
						"where status = ?");

			ps.setInt(1, WorkflowConstants.STATUS_APPROVED);

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String title = rs.getString("title");
				long resourcePrimKey = rs.getLong("resourcePrimKey");
				double version = rs.getDouble("version");

				int type = WikiActivityKeys.ADD_PAGE;

				if (version > 1.0) {
					type = WikiActivityKeys.UPDATE_PAGE;
				}

				addActivity(
					increment(), groupId, companyId, userId, modifiedDate, 0,
					classNameId, resourcePrimKey, type, 0, title, version,
					keys);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeSocial.class);

}