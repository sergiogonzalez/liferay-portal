/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.trash.TrashConstants;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.CategoryNameException;
import com.liferay.portlet.messageboards.NoSuchMailingListException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.service.base.MBCategoryLocalServiceBaseImpl;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.TrashVersion;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class MBCategoryLocalServiceImpl extends MBCategoryLocalServiceBaseImpl {

	@Override
	public MBCategory addCategory(
			long userId, long parentCategoryId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addCategory(
			userId, parentCategoryId, name, description,
			MBCategoryConstants.DEFAULT_DISPLAY_STYLE, null, null, null, 0,
			false, null, null, 0, null, false, null, 0, false, null, null,
			false, false, serviceContext);
	}

	@Override
	public MBCategory addCategory(
			long userId, long parentCategoryId, String name, String description,
			String displayStyle, String emailAddress, String inProtocol,
			String inServerName, int inServerPort, boolean inUseSSL,
			String inUserName, String inPassword, int inReadInterval,
			String outEmailAddress, boolean outCustom, String outServerName,
			int outServerPort, boolean outUseSSL, String outUserName,
			String outPassword, boolean allowAnonymous,
			boolean mailingListActive, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Category

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		parentCategoryId = getParentCategoryId(groupId, parentCategoryId);
		Date now = new Date();

		validate(name);

		long categoryId = counterLocalService.increment();

		MBCategory category = mbCategoryPersistence.create(categoryId);

		category.setUuid(serviceContext.getUuid());
		category.setGroupId(groupId);
		category.setCompanyId(user.getCompanyId());
		category.setUserId(user.getUserId());
		category.setUserName(user.getFullName());
		category.setCreateDate(serviceContext.getCreateDate(now));
		category.setModifiedDate(serviceContext.getModifiedDate(now));
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);
		category.setDisplayStyle(displayStyle);
		category.setExpandoBridgeAttributes(serviceContext);

		mbCategoryPersistence.update(category);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addCategoryResources(
				category, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addCategoryResources(
				category, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Mailing list

		mbMailingListLocalService.addMailingList(
			userId, groupId, category.getCategoryId(), emailAddress, inProtocol,
			inServerName, inServerPort, inUseSSL, inUserName, inPassword,
			inReadInterval, outEmailAddress, outCustom, outServerName,
			outServerPort, outUseSSL, outUserName, outPassword, allowAnonymous,
			mailingListActive, serviceContext);

		return category;
	}

	@Override
	public void addCategoryResources(
			long categoryId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		if ((categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(categoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return;
		}

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		addCategoryResources(
			category, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addCategoryResources(
			long categoryId, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		if ((categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(categoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return;
		}

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		addCategoryResources(category, groupPermissions, guestPermissions);
	}

	@Override
	public void addCategoryResources(
			MBCategory category, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), MBCategory.class.getName(),
			category.getCategoryId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addCategoryResources(
			MBCategory category, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), MBCategory.class.getName(),
			category.getCategoryId(), groupPermissions, guestPermissions);
	}

	@Override
	public void deleteCategories(long groupId)
		throws PortalException, SystemException {

		List<MBCategory> categories = mbCategoryPersistence.findByGroupId(
			groupId);

		for (MBCategory category : categories) {
			mbCategoryLocalService.deleteCategory(category);
		}
	}

	@Override
	public void deleteCategory(long categoryId)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		mbCategoryLocalService.deleteCategory(category);
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE)
	public void deleteCategory(MBCategory category)
		throws PortalException, SystemException {

		deleteCategory(category, true);
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE)
	public void deleteCategory(
			MBCategory category, boolean includeTrashedEntries)
		throws PortalException, SystemException {

		// Categories

		List<MBCategory> categories = mbCategoryPersistence.findByG_P(
			category.getGroupId(), category.getCategoryId());

		for (MBCategory curCategory : categories) {
			if (includeTrashedEntries || !curCategory.isInTrash()) {
				deleteCategory(curCategory, includeTrashedEntries);
			}
		}

		// Indexer

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			MBMessage.class);

		indexer.delete(category);

		// Threads

		mbThreadLocalService.deleteThreads(
			category.getGroupId(), category.getCategoryId(),
			includeTrashedEntries);

		// Mailing list

		try {
			mbMailingListLocalService.deleteCategoryMailingList(
				category.getGroupId(), category.getCategoryId());
		}
		catch (NoSuchMailingListException nsmle) {
		}

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			category.getCompanyId(), MBCategory.class.getName(),
			category.getCategoryId());

		// Expando

		expandoRowLocalService.deleteRows(category.getCategoryId());

		// Resources

		resourceLocalService.deleteResource(
			category.getCompanyId(), MBCategory.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, category.getCategoryId());

		// Trash

		trashEntryLocalService.deleteEntry(
			MBCategory.class.getName(), category.getCategoryId());

		// Category

		mbCategoryPersistence.remove(category);
	}

	@Override
	public List<MBCategory> getCategories(long groupId) throws SystemException {
		return mbCategoryPersistence.findByGroupId(groupId);
	}

	@Override
	public List<MBCategory> getCategories(long groupId, int status)
		throws SystemException {

		return mbCategoryPersistence.findByG_S(groupId, status);
	}

	@Override
	public List<MBCategory> getCategories(
			long groupId, long parentCategoryId, int start, int end)
		throws SystemException {

		return mbCategoryPersistence.findByG_P(
			groupId, parentCategoryId, start, end);
	}

	@Override
	public List<MBCategory> getCategories(
			long groupId, long parentCategoryId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.findByG_P(
				groupId, parentCategoryId, start, end);
		}

		return mbCategoryPersistence.findByG_P_S(
			groupId, parentCategoryId, status, start, end);
	}

	@Override
	public List<MBCategory> getCategories(
			long groupId, long[] parentCategoryIds, int start, int end)
		throws SystemException {

		return mbCategoryPersistence.findByG_P(
			groupId, parentCategoryIds, start, end);
	}

	@Override
	public List<MBCategory> getCategories(
			long groupId, long[] parentCategoryIds, int status, int start,
			int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.findByG_P(
				groupId, parentCategoryIds, start, end);
		}

		return mbCategoryPersistence.findByG_P_S(
			groupId, parentCategoryIds, status, start, end);
	}

	@Override
	public List<Object> getCategoriesAndThreads(long groupId, long categoryId)
		throws SystemException {

		List<Object> categoriesAndThreads = new ArrayList<Object>();

		List<MBCategory> categories = getCategories(
			groupId, categoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		categoriesAndThreads.addAll(categories);

		List<MBThread> threads = mbThreadLocalService.getThreads(
			groupId, categoryId, WorkflowConstants.STATUS_ANY,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		categoriesAndThreads.addAll(threads);

		return categoriesAndThreads;
	}

	@Override
	public int getCategoriesCount(long groupId) throws SystemException {
		return mbCategoryPersistence.countByGroupId(groupId);
	}

	@Override
	public int getCategoriesCount(long groupId, int status)
		throws SystemException {

		return mbCategoryPersistence.countByG_S(groupId, status);
	}

	@Override
	public int getCategoriesCount(long groupId, long parentCategoryId)
		throws SystemException {

		return mbCategoryPersistence.countByG_P(groupId, parentCategoryId);
	}

	@Override
	public int getCategoriesCount(
			long groupId, long parentCategoryId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.countByG_P(groupId, parentCategoryId);
		}

		return mbCategoryPersistence.countByG_P_S(
			groupId, parentCategoryId, status);
	}

	@Override
	public int getCategoriesCount(long groupId, long[] parentCategoryIds)
		throws SystemException {

		return mbCategoryPersistence.countByG_P(groupId, parentCategoryIds);
	}

	@Override
	public int getCategoriesCount(
			long groupId, long[] parentCategoryIds, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbCategoryPersistence.countByG_P(groupId, parentCategoryIds);
		}

		return mbCategoryPersistence.countByG_P_S(
			groupId, parentCategoryIds, status);
	}

	@Override
	public MBCategory getCategory(long categoryId)
		throws PortalException, SystemException {

		MBCategory category = null;

		if ((categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(categoryId != MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			category = mbCategoryPersistence.findByPrimaryKey(categoryId);
		}
		else {
			category = new MBCategoryImpl();

			category.setCategoryId(categoryId);
			category.setParentCategoryId(categoryId);
		}

		return category;
	}

	@Override
	public List<MBCategory> getCompanyCategories(
			long companyId, int start, int end)
		throws SystemException {

		return mbCategoryPersistence.findByCompanyId(companyId, start, end);
	}

	@Override
	public int getCompanyCategoriesCount(long companyId)
		throws SystemException {

		return mbCategoryPersistence.countByCompanyId(companyId);
	}

	@Override
	public List<Long> getSubcategoryIds(
			List<Long> categoryIds, long groupId, long categoryId)
		throws SystemException {

		List<MBCategory> categories = mbCategoryPersistence.findByG_P(
			groupId, categoryId);

		for (MBCategory category : categories) {
			categoryIds.add(category.getCategoryId());

			getSubcategoryIds(
				categoryIds, category.getGroupId(), category.getCategoryId());
		}

		return categoryIds;
	}

	@Override
	public List<MBCategory> getSubscribedCategories(
			long groupId, long userId, int start, int end)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_ANY, start, end, null);

		return mbCategoryFinder.findByS_G_U_P(
			groupId, userId, null, queryDefinition);
	}

	@Override
	public int getSubscribedCategoriesCount(long groupId, long userId)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_ANY);

		return mbCategoryFinder.countByS_G_U_P(
			groupId, userId, null, queryDefinition);
	}

	@Override
	public void moveCategoriesToTrash(long groupId, long userId)
		throws PortalException, SystemException {

		List<MBCategory> categories = mbCategoryPersistence.findByGroupId(
			groupId);

		for (MBCategory category : categories) {
			moveCategoryToTrash(userId, category.getCategoryId());
		}
	}

	@Override
	public MBCategory moveCategory(
			long categoryId, long parentCategoryId,
			boolean mergeWithParentCategory)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		parentCategoryId = getParentCategoryId(category, parentCategoryId);

		if (mergeWithParentCategory &&
			(categoryId != parentCategoryId) &&
			(parentCategoryId !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(parentCategoryId != MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			mergeCategories(category, parentCategoryId);

			return category;
		}

		category.setParentCategoryId(parentCategoryId);

		return mbCategoryPersistence.update(category);
	}

	@Override
	public MBCategory moveCategoryFromTrash(
			long userId, long categoryId, long newCategoryId)
		throws PortalException, SystemException {

		restoreCategoryFromTrash(userId, categoryId);

		return moveCategory(categoryId, newCategoryId, false);
	}

	@Override
	public MBCategory moveCategoryToTrash(long userId, long categoryId)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		// Trash

		TrashEntry trashEntry = trashEntryLocalService.addTrashEntry(
			userId, category.getGroupId(), MBCategory.class.getName(),
			categoryId, category.getUuid(), null,
			WorkflowConstants.STATUS_APPROVED, null, null);

		ServiceContext trashContext = new ServiceContext();

		trashContext.setAttribute(TrashConstants.TRASH_ENTRY, trashEntry);

		// Category

		category = moveCategoryToTrash(userId, category, trashContext);

		HashSet<Long> userIds = (HashSet<Long>)trashContext.getAttribute(
			"userIds", new HashSet<Long>());

		for (long curUserId : userIds) {
			mbStatsUserLocalService.updateStatsUser(
				category.getGroupId(), curUserId);
		}

		return category;
	}

	@Override
	public MBCategory moveCategoryToTrash(
			long userId, MBCategory category, ServiceContext serviceContext)
		throws PortalException, SystemException {

		TrashEntry trashEntry = (TrashEntry)serviceContext.getAttribute(
			TrashConstants.TRASH_ENTRY);

		if (!category.isApproved() && !trashEntry.isTrashEntry(category)) {
			trashVersionLocalService.addTrashVersion(
				trashEntry.getEntryId(), MBCategory.class.getName(),
				category.getCategoryId(), category.getStatus());
		}

		category = updateStatus(
			userId, category, WorkflowConstants.STATUS_IN_TRASH,
			serviceContext);

		moveDependentsToTrash(
			category.getGroupId(), userId, category.getCategoryId(),
			serviceContext);

		return category;
	}

	@Override
	public void restoreCategoryFromTrash(long userId, long categoryId)
		throws PortalException, SystemException {

		// Category

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		TrashEntry trashEntry = category.getTrashEntry();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute(TrashConstants.TRASH_ENTRY, trashEntry);

		int status = WorkflowConstants.STATUS_APPROVED;

		if (trashEntry.isTrashEntry(category)) {
			status = trashEntry.getStatus();
		}
		else {
			TrashVersion trashVersion =
				trashVersionLocalService.fetchVersion(
					trashEntry.getEntryId(), MBCategory.class.getName(),
					category.getCategoryId());

			if (trashVersion != null) {
				status = trashVersion.getStatus();
			}
		}

		// Dependents

		TrashUtil.addDependentStatus(trashEntry.getEntryId(), serviceContext);

		TrashUtil.addTrashEntries(
			trashEntry.getGroupId(), MBCategory.class.getName(),
			serviceContext);

		TrashUtil.addTrashEntries(
			trashEntry.getGroupId(), MBThread.class.getName(), serviceContext);

		restoreCategoryFromTrash(userId, category, status, serviceContext);

		HashSet<Long> userIds = (HashSet<Long>)serviceContext.getAttribute(
			"userIds", new HashSet<Long>());

		for (long curUserId : userIds) {
			mbStatsUserLocalService.updateStatsUser(
				trashEntry.getGroupId(), curUserId);
		}

		// Trash

		if (trashEntry.isTrashEntry(category)) {
			trashEntryLocalService.deleteEntry(trashEntry.getEntryId());
		}
	}

	@Override
	public void restoreCategoryFromTrash(
			long userId, MBCategory category, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		updateStatus(userId, category, status, serviceContext);

		restoreDependentsFromTrash(
			category.getGroupId(), userId, category.getCategoryId(),
			serviceContext);
	}

	@Override
	public void subscribeCategory(long userId, long groupId, long categoryId)
		throws PortalException, SystemException {

		if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			categoryId = groupId;
		}

		subscriptionLocalService.addSubscription(
			userId, groupId, MBCategory.class.getName(), categoryId);
	}

	@Override
	public void unsubscribeCategory(long userId, long groupId, long categoryId)
		throws PortalException, SystemException {

		if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			categoryId = groupId;
		}

		subscriptionLocalService.deleteSubscription(
			userId, MBCategory.class.getName(), categoryId);
	}

	@Override
	public MBCategory updateCategory(
			long categoryId, long parentCategoryId, String name,
			String description, String displayStyle, String emailAddress,
			String inProtocol, String inServerName, int inServerPort,
			boolean inUseSSL, String inUserName, String inPassword,
			int inReadInterval, String outEmailAddress, boolean outCustom,
			String outServerName, int outServerPort, boolean outUseSSL,
			String outUserName, String outPassword, boolean allowAnonymous,
			boolean mailingListActive, boolean mergeWithParentCategory,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Merge categories

		if ((categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(categoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return null;
		}

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		parentCategoryId = getParentCategoryId(category, parentCategoryId);

		if (mergeWithParentCategory &&
			(categoryId != parentCategoryId) &&
			(parentCategoryId !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(parentCategoryId != MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			mergeCategories(category, parentCategoryId);

			return category;
		}

		// Category

		validate(name);

		category.setModifiedDate(serviceContext.getModifiedDate(null));
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);

		if (!displayStyle.equals(category.getDisplayStyle())) {
			category.setDisplayStyle(displayStyle);

			updateChildCategoriesDisplayStyle(category, displayStyle);
		}

		category.setExpandoBridgeAttributes(serviceContext);

		mbCategoryPersistence.update(category);

		// Mailing list

		MBMailingList mailingList = mbMailingListPersistence.fetchByG_C(
			category.getGroupId(), category.getCategoryId());

		if (mailingList != null) {
			mbMailingListLocalService.updateMailingList(
				mailingList.getMailingListId(), emailAddress, inProtocol,
				inServerName, inServerPort, inUseSSL, inUserName, inPassword,
				inReadInterval, outEmailAddress, outCustom, outServerName,
				outServerPort, outUseSSL, outUserName, outPassword,
				allowAnonymous, mailingListActive, serviceContext);
		}
		else {
			mbMailingListLocalService.addMailingList(
				category.getUserId(), category.getGroupId(),
				category.getCategoryId(), emailAddress, inProtocol,
				inServerName, inServerPort, inUseSSL, inUserName, inPassword,
				inReadInterval, outEmailAddress, outCustom, outServerName,
				outServerPort, outUseSSL, outUserName, outPassword,
				allowAnonymous, mailingListActive, serviceContext);
		}

		return category;
	}

	@Override
	public MBCategory updateStatus(
			long userId, MBCategory category, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		TrashEntry trashEntry = (TrashEntry)serviceContext.getAttribute(
			TrashConstants.TRASH_ENTRY);

		// Category

		User user = userPersistence.findByPrimaryKey(userId);

		category.setStatus(status);

		if ((trashEntry == null) || trashEntry.isTrashEntry(category)) {
			category.setStatusByUserId(user.getUserId());
			category.setStatusByUserName(user.getFullName());
			category.setStatusDate(new Date());
		}

		mbCategoryPersistence.update(category);

		return category;
	}

	protected long getParentCategoryId(long groupId, long parentCategoryId)
		throws SystemException {

		if ((parentCategoryId !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(parentCategoryId != MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			MBCategory parentCategory = mbCategoryPersistence.fetchByPrimaryKey(
				parentCategoryId);

			if ((parentCategory == null) ||
				(groupId != parentCategory.getGroupId())) {

				parentCategoryId =
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;
			}
		}

		return parentCategoryId;
	}

	protected long getParentCategoryId(
			MBCategory category, long parentCategoryId)
		throws SystemException {

		if ((parentCategoryId ==
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(parentCategoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return parentCategoryId;
		}

		if (category.getCategoryId() == parentCategoryId) {
			return category.getParentCategoryId();
		}

		MBCategory parentCategory = mbCategoryPersistence.fetchByPrimaryKey(
			parentCategoryId);

		if ((parentCategory == null) ||
			(category.getGroupId() != parentCategory.getGroupId())) {

			return category.getParentCategoryId();
		}

		List<Long> subcategoryIds = new ArrayList<Long>();

		getSubcategoryIds(
			subcategoryIds, category.getGroupId(), category.getCategoryId());

		if (subcategoryIds.contains(parentCategoryId)) {
			return category.getParentCategoryId();
		}

		return parentCategoryId;
	}

	protected void mergeCategories(MBCategory fromCategory, long toCategoryId)
		throws PortalException, SystemException {

		if ((toCategoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(toCategoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return;
		}

		List<MBCategory> categories = mbCategoryPersistence.findByG_P(
			fromCategory.getGroupId(), fromCategory.getCategoryId());

		for (MBCategory category : categories) {
			mergeCategories(category, toCategoryId);
		}

		List<MBThread> threads = mbThreadPersistence.findByG_C(
			fromCategory.getGroupId(), fromCategory.getCategoryId());

		for (MBThread thread : threads) {

			// Thread

			thread.setCategoryId(toCategoryId);

			mbThreadPersistence.update(thread);

			List<MBMessage> messages = mbMessagePersistence.findByThreadId(
				thread.getThreadId());

			for (MBMessage message : messages) {

				// Message

				message.setCategoryId(toCategoryId);

				mbMessagePersistence.update(message);

				// Indexer

				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					MBMessage.class);

				indexer.reindex(message);
			}
		}

		MBCategory toCategory = mbCategoryPersistence.findByPrimaryKey(
			toCategoryId);

		toCategory.setThreadCount(
			fromCategory.getThreadCount() + toCategory.getThreadCount());
		toCategory.setMessageCount(
			fromCategory.getMessageCount() + toCategory.getMessageCount());

		mbCategoryPersistence.update(toCategory);

		deleteCategory(fromCategory);
	}

	protected void moveDependentsToTrash(
			long groupId, long userId, long parentCategoryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<MBCategory> categories = getCategories(
			groupId, parentCategoryId, WorkflowConstants.STATUS_ANY,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (MBCategory category : categories) {
			if (category.isInTrash()) {
				continue;
			}

			moveCategoryToTrash(userId, category, serviceContext);
		}

		List<MBThread> threads = mbThreadLocalService.getThreads(
			groupId, parentCategoryId, WorkflowConstants.STATUS_ANY,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (MBThread thread : threads) {
			if (thread.isInTrash()) {
				continue;
			}

			mbThreadLocalService.moveThreadToTrash(
				userId, thread, serviceContext);
		}
	}

	protected void restoreDependentsFromTrash(
			long groupId, long userId, long parentCategoryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<MBCategory> categories = getCategories(
			groupId, parentCategoryId, WorkflowConstants.STATUS_ANY,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (MBCategory category : categories) {
			if (TrashUtil.hasTrashEntry(
					MBCategory.class.getName(), category.getCategoryId(),
					serviceContext)) {

				continue;
			}

			Integer oldStatus = TrashUtil.getDependentStatus(
				MBCategory.class.getName(), category.getCategoryId(),
				serviceContext);

			restoreCategoryFromTrash(
				userId, category, oldStatus, serviceContext);
		}

		List<MBThread> threads = mbThreadLocalService.getThreads(
			groupId, parentCategoryId, WorkflowConstants.STATUS_ANY,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (MBThread thread : threads) {
			if (TrashUtil.hasTrashEntry(
					MBThread.class.getName(), thread.getThreadId(),
					serviceContext)) {

				continue;
			}

			Integer oldStatus = TrashUtil.getDependentStatus(
				MBThread.class.getName(), thread.getThreadId(), serviceContext);

			mbThreadLocalService.restoreThreadFromTrash(
				userId, thread, oldStatus, serviceContext);
		}
	}

	protected void updateChildCategoriesDisplayStyle(
			MBCategory category, String displayStyle)
		throws PortalException, SystemException {

		List<MBCategory> categories = getCategories(
			category.getGroupId(), category.getCategoryId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		for (MBCategory curCategory : categories) {
			updateChildCategoriesDisplayStyle(curCategory, displayStyle);

			curCategory.setDisplayStyle(displayStyle);

			mbCategoryPersistence.update(curCategory);
		}
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new CategoryNameException();
		}
	}

}