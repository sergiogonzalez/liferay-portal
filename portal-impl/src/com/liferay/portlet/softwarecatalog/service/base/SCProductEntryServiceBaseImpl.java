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

package com.liferay.portlet.softwarecatalog.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.BaseServiceImpl;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.ImagePersistence;
import com.liferay.portal.service.persistence.SubscriptionPersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.messageboards.service.persistence.MBMessageFinder;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsFinder;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryService;
import com.liferay.portlet.softwarecatalog.service.persistence.SCLicensePersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductScreenshotPersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionPersistence;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the s c product entry remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.softwarecatalog.service.impl.SCProductEntryServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.softwarecatalog.service.impl.SCProductEntryServiceImpl
 * @see com.liferay.portlet.softwarecatalog.service.SCProductEntryServiceUtil
 * @generated
 */
public abstract class SCProductEntryServiceBaseImpl extends BaseServiceImpl
	implements SCProductEntryService, IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.softwarecatalog.service.SCProductEntryServiceUtil} to access the s c product entry remote service.
	 */

	/**
	 * Returns the s c product entry local service.
	 *
	 * @return the s c product entry local service
	 */
	public com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService getSCProductEntryLocalService() {
		return scProductEntryLocalService;
	}

	/**
	 * Sets the s c product entry local service.
	 *
	 * @param scProductEntryLocalService the s c product entry local service
	 */
	public void setSCProductEntryLocalService(
		com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService scProductEntryLocalService) {
		this.scProductEntryLocalService = scProductEntryLocalService;
	}

	/**
	 * Returns the s c product entry remote service.
	 *
	 * @return the s c product entry remote service
	 */
	public SCProductEntryService getSCProductEntryService() {
		return scProductEntryService;
	}

	/**
	 * Sets the s c product entry remote service.
	 *
	 * @param scProductEntryService the s c product entry remote service
	 */
	public void setSCProductEntryService(
		SCProductEntryService scProductEntryService) {
		this.scProductEntryService = scProductEntryService;
	}

	/**
	 * Returns the s c product entry persistence.
	 *
	 * @return the s c product entry persistence
	 */
	public SCProductEntryPersistence getSCProductEntryPersistence() {
		return scProductEntryPersistence;
	}

	/**
	 * Sets the s c product entry persistence.
	 *
	 * @param scProductEntryPersistence the s c product entry persistence
	 */
	public void setSCProductEntryPersistence(
		SCProductEntryPersistence scProductEntryPersistence) {
		this.scProductEntryPersistence = scProductEntryPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the group local service.
	 *
	 * @return the group local service
	 */
	public com.liferay.portal.service.GroupLocalService getGroupLocalService() {
		return groupLocalService;
	}

	/**
	 * Sets the group local service.
	 *
	 * @param groupLocalService the group local service
	 */
	public void setGroupLocalService(
		com.liferay.portal.service.GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	/**
	 * Returns the group remote service.
	 *
	 * @return the group remote service
	 */
	public com.liferay.portal.service.GroupService getGroupService() {
		return groupService;
	}

	/**
	 * Sets the group remote service.
	 *
	 * @param groupService the group remote service
	 */
	public void setGroupService(
		com.liferay.portal.service.GroupService groupService) {
		this.groupService = groupService;
	}

	/**
	 * Returns the group persistence.
	 *
	 * @return the group persistence
	 */
	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	/**
	 * Sets the group persistence.
	 *
	 * @param groupPersistence the group persistence
	 */
	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	/**
	 * Returns the group finder.
	 *
	 * @return the group finder
	 */
	public GroupFinder getGroupFinder() {
		return groupFinder;
	}

	/**
	 * Sets the group finder.
	 *
	 * @param groupFinder the group finder
	 */
	public void setGroupFinder(GroupFinder groupFinder) {
		this.groupFinder = groupFinder;
	}

	/**
	 * Returns the image local service.
	 *
	 * @return the image local service
	 */
	public com.liferay.portal.service.ImageLocalService getImageLocalService() {
		return imageLocalService;
	}

	/**
	 * Sets the image local service.
	 *
	 * @param imageLocalService the image local service
	 */
	public void setImageLocalService(
		com.liferay.portal.service.ImageLocalService imageLocalService) {
		this.imageLocalService = imageLocalService;
	}

	/**
	 * Returns the image remote service.
	 *
	 * @return the image remote service
	 */
	public com.liferay.portal.service.ImageService getImageService() {
		return imageService;
	}

	/**
	 * Sets the image remote service.
	 *
	 * @param imageService the image remote service
	 */
	public void setImageService(
		com.liferay.portal.service.ImageService imageService) {
		this.imageService = imageService;
	}

	/**
	 * Returns the image persistence.
	 *
	 * @return the image persistence
	 */
	public ImagePersistence getImagePersistence() {
		return imagePersistence;
	}

	/**
	 * Sets the image persistence.
	 *
	 * @param imagePersistence the image persistence
	 */
	public void setImagePersistence(ImagePersistence imagePersistence) {
		this.imagePersistence = imagePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the subscription local service.
	 *
	 * @return the subscription local service
	 */
	public com.liferay.portal.service.SubscriptionLocalService getSubscriptionLocalService() {
		return subscriptionLocalService;
	}

	/**
	 * Sets the subscription local service.
	 *
	 * @param subscriptionLocalService the subscription local service
	 */
	public void setSubscriptionLocalService(
		com.liferay.portal.service.SubscriptionLocalService subscriptionLocalService) {
		this.subscriptionLocalService = subscriptionLocalService;
	}

	/**
	 * Returns the subscription persistence.
	 *
	 * @return the subscription persistence
	 */
	public SubscriptionPersistence getSubscriptionPersistence() {
		return subscriptionPersistence;
	}

	/**
	 * Sets the subscription persistence.
	 *
	 * @param subscriptionPersistence the subscription persistence
	 */
	public void setSubscriptionPersistence(
		SubscriptionPersistence subscriptionPersistence) {
		this.subscriptionPersistence = subscriptionPersistence;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the user finder.
	 *
	 * @return the user finder
	 */
	public UserFinder getUserFinder() {
		return userFinder;
	}

	/**
	 * Sets the user finder.
	 *
	 * @param userFinder the user finder
	 */
	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	/**
	 * Returns the message-boards message local service.
	 *
	 * @return the message-boards message local service
	 */
	public com.liferay.portlet.messageboards.service.MBMessageLocalService getMBMessageLocalService() {
		return mbMessageLocalService;
	}

	/**
	 * Sets the message-boards message local service.
	 *
	 * @param mbMessageLocalService the message-boards message local service
	 */
	public void setMBMessageLocalService(
		com.liferay.portlet.messageboards.service.MBMessageLocalService mbMessageLocalService) {
		this.mbMessageLocalService = mbMessageLocalService;
	}

	/**
	 * Returns the message-boards message remote service.
	 *
	 * @return the message-boards message remote service
	 */
	public com.liferay.portlet.messageboards.service.MBMessageService getMBMessageService() {
		return mbMessageService;
	}

	/**
	 * Sets the message-boards message remote service.
	 *
	 * @param mbMessageService the message-boards message remote service
	 */
	public void setMBMessageService(
		com.liferay.portlet.messageboards.service.MBMessageService mbMessageService) {
		this.mbMessageService = mbMessageService;
	}

	/**
	 * Returns the message-boards message persistence.
	 *
	 * @return the message-boards message persistence
	 */
	public MBMessagePersistence getMBMessagePersistence() {
		return mbMessagePersistence;
	}

	/**
	 * Sets the message-boards message persistence.
	 *
	 * @param mbMessagePersistence the message-boards message persistence
	 */
	public void setMBMessagePersistence(
		MBMessagePersistence mbMessagePersistence) {
		this.mbMessagePersistence = mbMessagePersistence;
	}

	/**
	 * Returns the message-boards message finder.
	 *
	 * @return the message-boards message finder
	 */
	public MBMessageFinder getMBMessageFinder() {
		return mbMessageFinder;
	}

	/**
	 * Sets the message-boards message finder.
	 *
	 * @param mbMessageFinder the message-boards message finder
	 */
	public void setMBMessageFinder(MBMessageFinder mbMessageFinder) {
		this.mbMessageFinder = mbMessageFinder;
	}

	/**
	 * Returns the ratings stats local service.
	 *
	 * @return the ratings stats local service
	 */
	public com.liferay.portlet.ratings.service.RatingsStatsLocalService getRatingsStatsLocalService() {
		return ratingsStatsLocalService;
	}

	/**
	 * Sets the ratings stats local service.
	 *
	 * @param ratingsStatsLocalService the ratings stats local service
	 */
	public void setRatingsStatsLocalService(
		com.liferay.portlet.ratings.service.RatingsStatsLocalService ratingsStatsLocalService) {
		this.ratingsStatsLocalService = ratingsStatsLocalService;
	}

	/**
	 * Returns the ratings stats persistence.
	 *
	 * @return the ratings stats persistence
	 */
	public RatingsStatsPersistence getRatingsStatsPersistence() {
		return ratingsStatsPersistence;
	}

	/**
	 * Sets the ratings stats persistence.
	 *
	 * @param ratingsStatsPersistence the ratings stats persistence
	 */
	public void setRatingsStatsPersistence(
		RatingsStatsPersistence ratingsStatsPersistence) {
		this.ratingsStatsPersistence = ratingsStatsPersistence;
	}

	/**
	 * Returns the ratings stats finder.
	 *
	 * @return the ratings stats finder
	 */
	public RatingsStatsFinder getRatingsStatsFinder() {
		return ratingsStatsFinder;
	}

	/**
	 * Sets the ratings stats finder.
	 *
	 * @param ratingsStatsFinder the ratings stats finder
	 */
	public void setRatingsStatsFinder(RatingsStatsFinder ratingsStatsFinder) {
		this.ratingsStatsFinder = ratingsStatsFinder;
	}

	/**
	 * Returns the s c license local service.
	 *
	 * @return the s c license local service
	 */
	public com.liferay.portlet.softwarecatalog.service.SCLicenseLocalService getSCLicenseLocalService() {
		return scLicenseLocalService;
	}

	/**
	 * Sets the s c license local service.
	 *
	 * @param scLicenseLocalService the s c license local service
	 */
	public void setSCLicenseLocalService(
		com.liferay.portlet.softwarecatalog.service.SCLicenseLocalService scLicenseLocalService) {
		this.scLicenseLocalService = scLicenseLocalService;
	}

	/**
	 * Returns the s c license remote service.
	 *
	 * @return the s c license remote service
	 */
	public com.liferay.portlet.softwarecatalog.service.SCLicenseService getSCLicenseService() {
		return scLicenseService;
	}

	/**
	 * Sets the s c license remote service.
	 *
	 * @param scLicenseService the s c license remote service
	 */
	public void setSCLicenseService(
		com.liferay.portlet.softwarecatalog.service.SCLicenseService scLicenseService) {
		this.scLicenseService = scLicenseService;
	}

	/**
	 * Returns the s c license persistence.
	 *
	 * @return the s c license persistence
	 */
	public SCLicensePersistence getSCLicensePersistence() {
		return scLicensePersistence;
	}

	/**
	 * Sets the s c license persistence.
	 *
	 * @param scLicensePersistence the s c license persistence
	 */
	public void setSCLicensePersistence(
		SCLicensePersistence scLicensePersistence) {
		this.scLicensePersistence = scLicensePersistence;
	}

	/**
	 * Returns the s c product screenshot local service.
	 *
	 * @return the s c product screenshot local service
	 */
	public com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalService getSCProductScreenshotLocalService() {
		return scProductScreenshotLocalService;
	}

	/**
	 * Sets the s c product screenshot local service.
	 *
	 * @param scProductScreenshotLocalService the s c product screenshot local service
	 */
	public void setSCProductScreenshotLocalService(
		com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalService scProductScreenshotLocalService) {
		this.scProductScreenshotLocalService = scProductScreenshotLocalService;
	}

	/**
	 * Returns the s c product screenshot persistence.
	 *
	 * @return the s c product screenshot persistence
	 */
	public SCProductScreenshotPersistence getSCProductScreenshotPersistence() {
		return scProductScreenshotPersistence;
	}

	/**
	 * Sets the s c product screenshot persistence.
	 *
	 * @param scProductScreenshotPersistence the s c product screenshot persistence
	 */
	public void setSCProductScreenshotPersistence(
		SCProductScreenshotPersistence scProductScreenshotPersistence) {
		this.scProductScreenshotPersistence = scProductScreenshotPersistence;
	}

	/**
	 * Returns the s c product version local service.
	 *
	 * @return the s c product version local service
	 */
	public com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalService getSCProductVersionLocalService() {
		return scProductVersionLocalService;
	}

	/**
	 * Sets the s c product version local service.
	 *
	 * @param scProductVersionLocalService the s c product version local service
	 */
	public void setSCProductVersionLocalService(
		com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalService scProductVersionLocalService) {
		this.scProductVersionLocalService = scProductVersionLocalService;
	}

	/**
	 * Returns the s c product version remote service.
	 *
	 * @return the s c product version remote service
	 */
	public com.liferay.portlet.softwarecatalog.service.SCProductVersionService getSCProductVersionService() {
		return scProductVersionService;
	}

	/**
	 * Sets the s c product version remote service.
	 *
	 * @param scProductVersionService the s c product version remote service
	 */
	public void setSCProductVersionService(
		com.liferay.portlet.softwarecatalog.service.SCProductVersionService scProductVersionService) {
		this.scProductVersionService = scProductVersionService;
	}

	/**
	 * Returns the s c product version persistence.
	 *
	 * @return the s c product version persistence
	 */
	public SCProductVersionPersistence getSCProductVersionPersistence() {
		return scProductVersionPersistence;
	}

	/**
	 * Sets the s c product version persistence.
	 *
	 * @param scProductVersionPersistence the s c product version persistence
	 */
	public void setSCProductVersionPersistence(
		SCProductVersionPersistence scProductVersionPersistence) {
		this.scProductVersionPersistence = scProductVersionPersistence;
	}

	public void afterPropertiesSet() {
	}

	public void destroy() {
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	protected Class<?> getModelClass() {
		return SCProductEntry.class;
	}

	protected String getModelClassName() {
		return SCProductEntry.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = scProductEntryPersistence.getDataSource();

			DB db = DBFactoryUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService.class)
	protected com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService scProductEntryLocalService;
	@BeanReference(type = com.liferay.portlet.softwarecatalog.service.SCProductEntryService.class)
	protected SCProductEntryService scProductEntryService;
	@BeanReference(type = SCProductEntryPersistence.class)
	protected SCProductEntryPersistence scProductEntryPersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.GroupLocalService.class)
	protected com.liferay.portal.service.GroupLocalService groupLocalService;
	@BeanReference(type = com.liferay.portal.service.GroupService.class)
	protected com.liferay.portal.service.GroupService groupService;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = GroupFinder.class)
	protected GroupFinder groupFinder;
	@BeanReference(type = com.liferay.portal.service.ImageLocalService.class)
	protected com.liferay.portal.service.ImageLocalService imageLocalService;
	@BeanReference(type = com.liferay.portal.service.ImageService.class)
	protected com.liferay.portal.service.ImageService imageService;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.service.SubscriptionLocalService.class)
	protected com.liferay.portal.service.SubscriptionLocalService subscriptionLocalService;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = com.liferay.portlet.messageboards.service.MBMessageLocalService.class)
	protected com.liferay.portlet.messageboards.service.MBMessageLocalService mbMessageLocalService;
	@BeanReference(type = com.liferay.portlet.messageboards.service.MBMessageService.class)
	protected com.liferay.portlet.messageboards.service.MBMessageService mbMessageService;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = MBMessageFinder.class)
	protected MBMessageFinder mbMessageFinder;
	@BeanReference(type = com.liferay.portlet.ratings.service.RatingsStatsLocalService.class)
	protected com.liferay.portlet.ratings.service.RatingsStatsLocalService ratingsStatsLocalService;
	@BeanReference(type = RatingsStatsPersistence.class)
	protected RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(type = RatingsStatsFinder.class)
	protected RatingsStatsFinder ratingsStatsFinder;
	@BeanReference(type = com.liferay.portlet.softwarecatalog.service.SCLicenseLocalService.class)
	protected com.liferay.portlet.softwarecatalog.service.SCLicenseLocalService scLicenseLocalService;
	@BeanReference(type = com.liferay.portlet.softwarecatalog.service.SCLicenseService.class)
	protected com.liferay.portlet.softwarecatalog.service.SCLicenseService scLicenseService;
	@BeanReference(type = SCLicensePersistence.class)
	protected SCLicensePersistence scLicensePersistence;
	@BeanReference(type = com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalService.class)
	protected com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalService scProductScreenshotLocalService;
	@BeanReference(type = SCProductScreenshotPersistence.class)
	protected SCProductScreenshotPersistence scProductScreenshotPersistence;
	@BeanReference(type = com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalService.class)
	protected com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalService scProductVersionLocalService;
	@BeanReference(type = com.liferay.portlet.softwarecatalog.service.SCProductVersionService.class)
	protected com.liferay.portlet.softwarecatalog.service.SCProductVersionService scProductVersionService;
	@BeanReference(type = SCProductVersionPersistence.class)
	protected SCProductVersionPersistence scProductVersionPersistence;
	private String _beanIdentifier;
}