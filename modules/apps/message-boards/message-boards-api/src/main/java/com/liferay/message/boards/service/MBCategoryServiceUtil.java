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

package com.liferay.message.boards.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for MBCategory. This utility wraps
 * {@link com.liferay.message.boards.service.impl.MBCategoryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MBCategoryService
 * @see com.liferay.message.boards.service.base.MBCategoryServiceBaseImpl
 * @see com.liferay.message.boards.service.impl.MBCategoryServiceImpl
 * @generated
 */
@ProviderType
public class MBCategoryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.message.boards.service.impl.MBCategoryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.message.boards.model.MBCategory addCategory(
		long userId, long parentCategoryId, String name, String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCategory(userId, parentCategoryId, name, description,
			serviceContext);
	}

	public static com.liferay.message.boards.model.MBCategory addCategory(
		long parentCategoryId, String name, String description,
		String displayStyle, String emailAddress, String inProtocol,
		String inServerName, int inServerPort, boolean inUseSSL,
		String inUserName, String inPassword, int inReadInterval,
		String outEmailAddress, boolean outCustom, String outServerName,
		int outServerPort, boolean outUseSSL, String outUserName,
		String outPassword, boolean mailingListActive,
		boolean allowAnonymousEmail,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCategory(parentCategoryId, name, description,
			displayStyle, emailAddress, inProtocol, inServerName, inServerPort,
			inUseSSL, inUserName, inPassword, inReadInterval, outEmailAddress,
			outCustom, outServerName, outServerPort, outUseSSL, outUserName,
			outPassword, mailingListActive, allowAnonymousEmail, serviceContext);
	}

	public static void deleteCategory(long categoryId,
		boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCategory(categoryId, includeTrashedEntries);
	}

	public static void deleteCategory(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCategory(groupId, categoryId);
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory> getCategories(
		long groupId) {
		return getService().getCategories(groupId);
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory> getCategories(
		long groupId, int status) {
		return getService().getCategories(groupId, status);
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end) {
		return getService().getCategories(groupId, parentCategoryId, start, end);
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory> getCategories(
		long groupId, long parentCategoryId, int status, int start, int end) {
		return getService()
				   .getCategories(groupId, parentCategoryId, status, start, end);
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory> getCategories(
		long groupId, long excludedCategoryId, long parentCategoryId,
		int status, int start, int end) {
		return getService()
				   .getCategories(groupId, excludedCategoryId,
			parentCategoryId, status, start, end);
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory> getCategories(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.message.boards.model.MBCategory> queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCategories(groupId, parentCategoryId, queryDefinition);
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int start, int end) {
		return getService().getCategories(groupId, parentCategoryIds, start, end);
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory> getCategories(
		long groupId, long[] parentCategoryIds, int status, int start, int end) {
		return getService()
				   .getCategories(groupId, parentCategoryIds, status, start, end);
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory> getCategories(
		long groupId, long[] excludedCategoryIds, long[] parentCategoryIds,
		int status, int start, int end) {
		return getService()
				   .getCategories(groupId, excludedCategoryIds,
			parentCategoryIds, status, start, end);
	}

	public static java.util.List<Object> getCategoriesAndThreads(long groupId,
		long categoryId) {
		return getService().getCategoriesAndThreads(groupId, categoryId);
	}

	public static java.util.List<Object> getCategoriesAndThreads(long groupId,
		long categoryId, int status) {
		return getService().getCategoriesAndThreads(groupId, categoryId, status);
	}

	public static java.util.List<Object> getCategoriesAndThreads(long groupId,
		long categoryId, int status, int start, int end) {
		return getService()
				   .getCategoriesAndThreads(groupId, categoryId, status, start,
			end);
	}

	public static java.util.List<Object> getCategoriesAndThreads(long groupId,
		long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc) {
		return getService()
				   .getCategoriesAndThreads(groupId, categoryId, status, start,
			end, obc);
	}

	public static java.util.List<Object> getCategoriesAndThreads(long groupId,
		long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCategoriesAndThreads(groupId, categoryId, queryDefinition);
	}

	public static int getCategoriesAndThreadsCount(long groupId, long categoryId) {
		return getService().getCategoriesAndThreadsCount(groupId, categoryId);
	}

	public static int getCategoriesAndThreadsCount(long groupId,
		long categoryId, int status) {
		return getService()
				   .getCategoriesAndThreadsCount(groupId, categoryId, status);
	}

	public static int getCategoriesAndThreadsCount(long groupId,
		long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCategoriesAndThreadsCount(groupId, categoryId,
			queryDefinition);
	}

	public static int getCategoriesCount(long groupId, long parentCategoryId) {
		return getService().getCategoriesCount(groupId, parentCategoryId);
	}

	public static int getCategoriesCount(long groupId, long parentCategoryId,
		int status) {
		return getService().getCategoriesCount(groupId, parentCategoryId, status);
	}

	public static int getCategoriesCount(long groupId, long excludedCategoryId,
		long parentCategoryId, int status) {
		return getService()
				   .getCategoriesCount(groupId, excludedCategoryId,
			parentCategoryId, status);
	}

	public static int getCategoriesCount(long groupId, long parentCategoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCategoriesCount(groupId, parentCategoryId,
			queryDefinition);
	}

	public static int getCategoriesCount(long groupId, long[] parentCategoryIds) {
		return getService().getCategoriesCount(groupId, parentCategoryIds);
	}

	public static int getCategoriesCount(long groupId,
		long[] parentCategoryIds, int status) {
		return getService()
				   .getCategoriesCount(groupId, parentCategoryIds, status);
	}

	public static int getCategoriesCount(long groupId,
		long[] excludedCategoryIds, long[] parentCategoryIds, int status) {
		return getService()
				   .getCategoriesCount(groupId, excludedCategoryIds,
			parentCategoryIds, status);
	}

	public static com.liferay.message.boards.model.MBCategory getCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCategory(categoryId);
	}

	public static long[] getCategoryIds(long groupId, long categoryId) {
		return getService().getCategoryIds(groupId, categoryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<Long> getSubcategoryIds(
		java.util.List<Long> categoryIds, long groupId, long categoryId) {
		return getService().getSubcategoryIds(categoryIds, groupId, categoryId);
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory> getSubscribedCategories(
		long groupId, long userId, int start, int end) {
		return getService().getSubscribedCategories(groupId, userId, start, end);
	}

	public static int getSubscribedCategoriesCount(long groupId, long userId) {
		return getService().getSubscribedCategoriesCount(groupId, userId);
	}

	public static com.liferay.message.boards.model.MBCategory moveCategory(
		long categoryId, long parentCategoryId, boolean mergeWithParentCategory)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .moveCategory(categoryId, parentCategoryId,
			mergeWithParentCategory);
	}

	public static com.liferay.message.boards.model.MBCategory moveCategoryFromTrash(
		long categoryId, long newCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveCategoryFromTrash(categoryId, newCategoryId);
	}

	public static com.liferay.message.boards.model.MBCategory moveCategoryToTrash(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveCategoryToTrash(categoryId);
	}

	public static void restoreCategoryFromTrash(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreCategoryFromTrash(categoryId);
	}

	public static void subscribeCategory(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().subscribeCategory(groupId, categoryId);
	}

	public static void unsubscribeCategory(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unsubscribeCategory(groupId, categoryId);
	}

	public static com.liferay.message.boards.model.MBCategory updateCategory(
		long categoryId, long parentCategoryId, String name,
		String description, String displayStyle, String emailAddress,
		String inProtocol, String inServerName, int inServerPort,
		boolean inUseSSL, String inUserName, String inPassword,
		int inReadInterval, String outEmailAddress, boolean outCustom,
		String outServerName, int outServerPort, boolean outUseSSL,
		String outUserName, String outPassword, boolean mailingListActive,
		boolean allowAnonymousEmail, boolean mergeWithParentCategory,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCategory(categoryId, parentCategoryId, name,
			description, displayStyle, emailAddress, inProtocol, inServerName,
			inServerPort, inUseSSL, inUserName, inPassword, inReadInterval,
			outEmailAddress, outCustom, outServerName, outServerPort,
			outUseSSL, outUserName, outPassword, mailingListActive,
			allowAnonymousEmail, mergeWithParentCategory, serviceContext);
	}

	public static MBCategoryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MBCategoryService, MBCategoryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(MBCategoryService.class);

		ServiceTracker<MBCategoryService, MBCategoryService> serviceTracker = new ServiceTracker<MBCategoryService, MBCategoryService>(bundle.getBundleContext(),
				MBCategoryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}