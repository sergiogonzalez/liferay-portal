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

package com.liferay.trash.service.impl;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.trash.kernel.util.TrashUtil;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.model.TrashVersion;
import com.liferay.trash.model.impl.TrashEntryImpl;
import com.liferay.trash.service.base.TrashEntryLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Provides the local service for accessing, adding, checking, and deleting
 * trash entries in the Recycle Bin.
 *
 * @author Zsolt Berentey
 */
public class TrashEntryLocalServiceImpl extends TrashEntryLocalServiceBaseImpl {

	/**
	 * Moves an entry to trash.
	 *
	 * @param  userId the primary key of the user removing the entity
	 * @param  groupId the primary key of the entry's group
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @param  classUuid the UUID of the entity's class
	 * @param  referrerClassName the referrer class name used to add a deletion
	 *         {@link SystemEvent}
	 * @param  status the status of the entity prior to being moved to trash
	 * @param  statusOVPs the primary keys and statuses of any of the entry's
	 *         versions (e.g., {@link
	 *         com.liferay.portlet.documentlibrary.model.DLFileVersion})
	 * @param  typeSettingsProperties the type settings properties
	 * @return the trashEntry
	 */
	@Override
	public TrashEntry addTrashEntry(
			long userId, long groupId, String className, long classPK,
			String classUuid, String referrerClassName, int status,
			List<ObjectValuePair<Long, Integer>> statusOVPs,
			UnicodeProperties typeSettingsProperties)
		throws PortalException {

		User user = userLocalService.getUserById(userId);
		long classNameId = classNameLocalService.getClassNameId(className);

		TrashEntry trashEntry = trashEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (trashEntry != null) {
			return trashEntry;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		SystemEvent systemEvent = trashHandler.addDeletionSystemEvent(
			userId, groupId, classPK, classUuid, referrerClassName);

		long entryId = counterLocalService.increment();

		trashEntry = trashEntryPersistence.create(entryId);

		trashEntry.setGroupId(groupId);
		trashEntry.setCompanyId(user.getCompanyId());
		trashEntry.setUserId(user.getUserId());
		trashEntry.setUserName(user.getFullName());
		trashEntry.setCreateDate(new Date());
		trashEntry.setClassNameId(classNameId);
		trashEntry.setClassPK(classPK);

		if (systemEvent != null) {
			trashEntry.setSystemEventSetKey(systemEvent.getSystemEventSetKey());
		}

		if (typeSettingsProperties != null) {
			trashEntry.setTypeSettingsProperties(typeSettingsProperties);
		}

		trashEntry.setStatus(status);

		trashEntryPersistence.update(trashEntry);

		if (statusOVPs != null) {
			for (ObjectValuePair<Long, Integer> statusOVP : statusOVPs) {
				long versionId = counterLocalService.increment();

				TrashVersion trashVersion = trashVersionPersistence.create(
					versionId);

				trashVersion.setEntryId(entryId);
				trashVersion.setClassNameId(classNameId);
				trashVersion.setClassPK(statusOVP.getKey());
				trashVersion.setStatus(statusOVP.getValue());

				trashVersionPersistence.update(trashVersion);
			}
		}

		return trashEntry;
	}

	@Override
	public void checkEntries() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			trashEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<TrashEntry>() {

				@Override
				public void performAction(TrashEntry trashEntry)
					throws PortalException {

					Date createDate = trashEntry.getCreateDate();

					Group group = groupLocalService.fetchGroup(
						trashEntry.getGroupId());

					if (group == null) {
						return;
					}

					Date date = getMaxAge(group);

					if (createDate.before(date) ||
						!TrashUtil.isTrashEnabled(group)) {

						TrashHandler trashHandler =
							TrashHandlerRegistryUtil.getTrashHandler(
								trashEntry.getClassName());

						if (trashHandler != null) {
							try {
								trashHandler.deleteTrashEntry(
									trashEntry.getClassPK());
							}
							catch (Exception e) {
								_log.error(e, e);
							}
						}
					}
				}

			});
		actionableDynamicQuery.setTransactionConfig(
			DefaultActionableDynamicQuery.REQUIRES_NEW_TRANSACTION_CONFIG);

		actionableDynamicQuery.performActions();
	}

	@Override
	public void deleteEntries(long groupId) {
		deleteEntries(groupId, false);
	}

	@Override
	public void deleteEntries(long groupId, boolean deleteTrashedModels) {
		List<TrashEntry> entries = getEntries(groupId);

		for (TrashEntry entry : entries) {
			deleteEntry(entry);

			if (deleteTrashedModels) {
				TrashHandler trashHandler =
					TrashHandlerRegistryUtil.getTrashHandler(
						entry.getClassName());

				if (trashHandler != null) {
					try {
						trashHandler.deleteTrashEntry(entry.getClassPK());
					}
					catch (Exception e) {
						_log.error(e, e);
					}
				}
			}
		}
	}

	/**
	 * Deletes the trash entry with the primary key.
	 *
	 * @param  entryId the primary key of the trash entry
	 * @return the trash entry with the primary key
	 */
	@Override
	public TrashEntry deleteEntry(long entryId) {
		TrashEntry entry = trashEntryPersistence.fetchByPrimaryKey(entryId);

		return deleteEntry(entry);
	}

	/**
	 * Deletes the trash entry with the entity class name and primary key.
	 *
	 * @param  className the class name of entity
	 * @param  classPK the primary key of the entry
	 * @return the trash entry with the entity class name and primary key
	 */
	@Override
	public TrashEntry deleteEntry(String className, long classPK) {
		long classNameId = classNameLocalService.getClassNameId(className);

		TrashEntry entry = trashEntryPersistence.fetchByC_C(
			classNameId, classPK);

		return deleteEntry(entry);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public TrashEntry deleteEntry(TrashEntry trashEntry) {
		if (trashEntry != null) {
			trashVersionPersistence.removeByEntryId(trashEntry.getEntryId());

			trashEntry = trashEntryPersistence.remove(trashEntry);

			systemEventLocalService.deleteSystemEvents(
				trashEntry.getGroupId(), trashEntry.getSystemEventSetKey());
		}

		return trashEntry;
	}

	/**
	 * Returns the trash entry with the primary key.
	 *
	 * @param  entryId the primary key of the entry
	 * @return the trash entry with the primary key
	 */
	@Override
	public TrashEntry fetchEntry(long entryId) {
		return trashEntryPersistence.fetchByPrimaryKey(entryId);
	}

	/**
	 * Returns the trash entry with the entity class name and primary key.
	 *
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @return the trash entry with the entity class name and primary key
	 */
	@Override
	public TrashEntry fetchEntry(String className, long classPK) {
		long classNameId = classNameLocalService.getClassNameId(className);

		return trashEntryPersistence.fetchByC_C(classNameId, classPK);
	}

	/**
	 * Returns the trash entries with the matching group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @return the trash entries with the group ID
	 */
	@Override
	public List<TrashEntry> getEntries(long groupId) {
		return trashEntryPersistence.findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the trash entries matching the group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @param  start the lower bound of the range of trash entries to return
	 * @param  end the upper bound of the range of trash entries to return (not
	 *         inclusive)
	 * @return the range of matching trash entries
	 */
	@Override
	public List<TrashEntry> getEntries(long groupId, int start, int end) {
		return trashEntryPersistence.findByGroupId(groupId, start, end);
	}

	/**
	 * Returns a range of all the trash entries matching the group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @param  start the lower bound of the range of trash entries to return
	 * @param  end the upper bound of the range of trash entries to return (not
	 *         inclusive)
	 * @param  obc the comparator to order the trash entries (optionally
	 *         <code>null</code>)
	 * @return the range of matching trash entries ordered by comparator
	 *         <code>obc</code>
	 */
	@Override
	public List<TrashEntry> getEntries(
		long groupId, int start, int end, OrderByComparator<TrashEntry> obc) {

		return trashEntryPersistence.findByGroupId(groupId, start, end, obc);
	}

	@Override
	public List<TrashEntry> getEntries(long groupId, String className) {
		long classNameId = classNameLocalService.getClassNameId(className);

		return trashEntryPersistence.findByG_C(groupId, classNameId);
	}

	/**
	 * Returns the number of trash entries with the group ID.
	 *
	 * @param  groupId the primary key of the group
	 * @return the number of matching trash entries
	 */
	@Override
	public int getEntriesCount(long groupId) {
		return trashEntryPersistence.countByGroupId(groupId);
	}

	/**
	 * Returns the trash entry with the primary key.
	 *
	 * @param  entryId the primary key of the trash entry
	 * @return the trash entry with the primary key
	 */
	@Override
	public TrashEntry getEntry(long entryId) throws PortalException {
		return trashEntryPersistence.findByPrimaryKey(entryId);
	}

	/**
	 * Returns the entry with the entity class name and primary key.
	 *
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @return the trash entry with the entity class name and primary key
	 */
	@Override
	public TrashEntry getEntry(String className, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		return trashEntryPersistence.findByC_C(classNameId, classPK);
	}

	@Override
	public Hits search(
		long companyId, long groupId, long userId, String keywords, int start,
		int end, Sort sort) {

		try {
			Indexer<TrashEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(TrashEntry.class);

			SearchContext searchContext = buildSearchContext(
				companyId, groupId, userId, keywords, start, end, sort);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public BaseModelSearchResult<TrashEntry> searchTrashEntries(
		long companyId, long groupId, long userId, String keywords, int start,
		int end, Sort sort) {

		try {
			Indexer<TrashEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(TrashEntry.class);

			SearchContext searchContext = buildSearchContext(
				companyId, groupId, userId, keywords, start, end, sort);

			Hits hits = indexer.search(searchContext);

			List<TrashEntry> trashEntries = _getEntries(hits);

			return new BaseModelSearchResult<>(trashEntries, hits.getLength());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, long userId, String keywords, int start,
		int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setKeywords(keywords);
		searchContext.setGroupIds(new long[] {groupId});

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);
		searchContext.setUserId(userId);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected Date getMaxAge(Group group) throws PortalException {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(new Date());

		int maxAge = TrashUtil.getMaxAge(group);

		calendar.add(Calendar.MINUTE, -maxAge);

		return calendar.getTime();
	}

	private List<TrashEntry> _getEntries(Hits hits) {
		List<TrashEntry> entries = new ArrayList<>();

		for (Document document : hits.getDocs()) {
			String entryClassName = GetterUtil.getString(
				document.get(Field.ENTRY_CLASS_NAME));
			long classPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			TrashEntry entry = fetchEntry(entryClassName, classPK);

			if (entry != null) {
				entries.add(entry);

				continue;
			}

			try {
				String userName = GetterUtil.getString(
					document.get(Field.REMOVED_BY_USER_NAME));

				Date removedDate = document.getDate(Field.REMOVED_DATE);

				entry = new TrashEntryImpl();

				entry.setUserName(userName);
				entry.setCreateDate(removedDate);

				TrashHandler trashHandler =
					TrashHandlerRegistryUtil.getTrashHandler(entryClassName);

				TrashRenderer trashRenderer = trashHandler.getTrashRenderer(
					classPK);

				entry.setClassName(trashRenderer.getClassName());
				entry.setClassPK(trashRenderer.getClassPK());

				String rootEntryClassName = GetterUtil.getString(
					document.get(Field.ROOT_ENTRY_CLASS_NAME));
				long rootEntryClassPK = GetterUtil.getLong(
					document.get(Field.ROOT_ENTRY_CLASS_PK));

				TrashEntry rootTrashEntry = fetchEntry(
					rootEntryClassName, rootEntryClassPK);

				if (rootTrashEntry != null) {
					entry.setRootEntry(rootTrashEntry);
				}

				entries.add(entry);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to find trash entry for ", entryClassName,
							" with primary key ", String.valueOf(classPK)));
				}
			}
		}

		return entries;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TrashEntryLocalServiceImpl.class);

}