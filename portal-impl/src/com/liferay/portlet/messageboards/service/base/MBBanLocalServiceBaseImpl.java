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

package com.liferay.portlet.messageboards.service.base;

import com.liferay.counter.service.CounterLocalService;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.service.MBBanLocalService;
import com.liferay.portlet.messageboards.service.MBBanService;
import com.liferay.portlet.messageboards.service.MBCategoryLocalService;
import com.liferay.portlet.messageboards.service.MBCategoryService;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalService;
import com.liferay.portlet.messageboards.service.MBMailingListLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageService;
import com.liferay.portlet.messageboards.service.MBStatsUserLocalService;
import com.liferay.portlet.messageboards.service.MBThreadFlagLocalService;
import com.liferay.portlet.messageboards.service.MBThreadLocalService;
import com.liferay.portlet.messageboards.service.MBThreadService;
import com.liferay.portlet.messageboards.service.persistence.MBBanPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryFinder;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBMailingListPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFinder;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBThreadFinder;
import com.liferay.portlet.messageboards.service.persistence.MBThreadFlagPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * The base implementation of the message boards ban local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.messageboards.service.impl.MBBanLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.messageboards.service.impl.MBBanLocalServiceImpl
 * @see com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil
 * @generated
 */
public abstract class MBBanLocalServiceBaseImpl extends BaseLocalServiceImpl
	implements MBBanLocalService, IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil} to access the message boards ban local service.
	 */

	/**
	 * Adds the message boards ban to the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbBan the message boards ban
	 * @return the message boards ban that was added
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	public MBBan addMBBan(MBBan mbBan) throws SystemException {
		mbBan.setNew(true);

		return mbBanPersistence.update(mbBan, false);
	}

	/**
	 * Creates a new message boards ban with the primary key. Does not add the message boards ban to the database.
	 *
	 * @param banId the primary key for the new message boards ban
	 * @return the new message boards ban
	 */
	public MBBan createMBBan(long banId) {
		return mbBanPersistence.create(banId);
	}

	/**
	 * Deletes the message boards ban with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param banId the primary key of the message boards ban
	 * @return the message boards ban that was removed
	 * @throws PortalException if a message boards ban with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	public MBBan deleteMBBan(long banId)
		throws PortalException, SystemException {
		return mbBanPersistence.remove(banId);
	}

	/**
	 * Deletes the message boards ban from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbBan the message boards ban
	 * @return the message boards ban that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	public MBBan deleteMBBan(MBBan mbBan) throws SystemException {
		return mbBanPersistence.remove(mbBan);
	}

	public DynamicQuery dynamicQuery() {
		return DynamicQueryFactoryUtil.forClass(MBBan.class, getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return mbBanPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return mbBanPersistence.findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return mbBanPersistence.findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return mbBanPersistence.countWithDynamicQuery(dynamicQuery);
	}

	public MBBan fetchMBBan(long banId) throws SystemException {
		return mbBanPersistence.fetchByPrimaryKey(banId);
	}

	/**
	 * Returns the message boards ban with the primary key.
	 *
	 * @param banId the primary key of the message boards ban
	 * @return the message boards ban
	 * @throws PortalException if a message boards ban with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public MBBan getMBBan(long banId) throws PortalException, SystemException {
		return mbBanPersistence.findByPrimaryKey(banId);
	}

	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException, SystemException {
		return mbBanPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the message boards bans.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards bans
	 * @param end the upper bound of the range of message boards bans (not inclusive)
	 * @return the range of message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public List<MBBan> getMBBans(int start, int end) throws SystemException {
		return mbBanPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of message boards bans.
	 *
	 * @return the number of message boards bans
	 * @throws SystemException if a system exception occurred
	 */
	public int getMBBansCount() throws SystemException {
		return mbBanPersistence.countAll();
	}

	/**
	 * Updates the message boards ban in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param mbBan the message boards ban
	 * @return the message boards ban that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	public MBBan updateMBBan(MBBan mbBan) throws SystemException {
		return updateMBBan(mbBan, true);
	}

	/**
	 * Updates the message boards ban in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param mbBan the message boards ban
	 * @param merge whether to merge the message boards ban with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	 * @return the message boards ban that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	public MBBan updateMBBan(MBBan mbBan, boolean merge)
		throws SystemException {
		mbBan.setNew(false);

		return mbBanPersistence.update(mbBan, merge);
	}

	/**
	 * Returns the message boards ban local service.
	 *
	 * @return the message boards ban local service
	 */
	public MBBanLocalService getMBBanLocalService() {
		return mbBanLocalService;
	}

	/**
	 * Sets the message boards ban local service.
	 *
	 * @param mbBanLocalService the message boards ban local service
	 */
	public void setMBBanLocalService(MBBanLocalService mbBanLocalService) {
		this.mbBanLocalService = mbBanLocalService;
	}

	/**
	 * Returns the message boards ban remote service.
	 *
	 * @return the message boards ban remote service
	 */
	public MBBanService getMBBanService() {
		return mbBanService;
	}

	/**
	 * Sets the message boards ban remote service.
	 *
	 * @param mbBanService the message boards ban remote service
	 */
	public void setMBBanService(MBBanService mbBanService) {
		this.mbBanService = mbBanService;
	}

	/**
	 * Returns the message boards ban persistence.
	 *
	 * @return the message boards ban persistence
	 */
	public MBBanPersistence getMBBanPersistence() {
		return mbBanPersistence;
	}

	/**
	 * Sets the message boards ban persistence.
	 *
	 * @param mbBanPersistence the message boards ban persistence
	 */
	public void setMBBanPersistence(MBBanPersistence mbBanPersistence) {
		this.mbBanPersistence = mbBanPersistence;
	}

	/**
	 * Returns the message boards category local service.
	 *
	 * @return the message boards category local service
	 */
	public MBCategoryLocalService getMBCategoryLocalService() {
		return mbCategoryLocalService;
	}

	/**
	 * Sets the message boards category local service.
	 *
	 * @param mbCategoryLocalService the message boards category local service
	 */
	public void setMBCategoryLocalService(
		MBCategoryLocalService mbCategoryLocalService) {
		this.mbCategoryLocalService = mbCategoryLocalService;
	}

	/**
	 * Returns the message boards category remote service.
	 *
	 * @return the message boards category remote service
	 */
	public MBCategoryService getMBCategoryService() {
		return mbCategoryService;
	}

	/**
	 * Sets the message boards category remote service.
	 *
	 * @param mbCategoryService the message boards category remote service
	 */
	public void setMBCategoryService(MBCategoryService mbCategoryService) {
		this.mbCategoryService = mbCategoryService;
	}

	/**
	 * Returns the message boards category persistence.
	 *
	 * @return the message boards category persistence
	 */
	public MBCategoryPersistence getMBCategoryPersistence() {
		return mbCategoryPersistence;
	}

	/**
	 * Sets the message boards category persistence.
	 *
	 * @param mbCategoryPersistence the message boards category persistence
	 */
	public void setMBCategoryPersistence(
		MBCategoryPersistence mbCategoryPersistence) {
		this.mbCategoryPersistence = mbCategoryPersistence;
	}

	/**
	 * Returns the message boards category finder.
	 *
	 * @return the message boards category finder
	 */
	public MBCategoryFinder getMBCategoryFinder() {
		return mbCategoryFinder;
	}

	/**
	 * Sets the message boards category finder.
	 *
	 * @param mbCategoryFinder the message boards category finder
	 */
	public void setMBCategoryFinder(MBCategoryFinder mbCategoryFinder) {
		this.mbCategoryFinder = mbCategoryFinder;
	}

	/**
	 * Returns the message boards discussion local service.
	 *
	 * @return the message boards discussion local service
	 */
	public MBDiscussionLocalService getMBDiscussionLocalService() {
		return mbDiscussionLocalService;
	}

	/**
	 * Sets the message boards discussion local service.
	 *
	 * @param mbDiscussionLocalService the message boards discussion local service
	 */
	public void setMBDiscussionLocalService(
		MBDiscussionLocalService mbDiscussionLocalService) {
		this.mbDiscussionLocalService = mbDiscussionLocalService;
	}

	/**
	 * Returns the message boards discussion persistence.
	 *
	 * @return the message boards discussion persistence
	 */
	public MBDiscussionPersistence getMBDiscussionPersistence() {
		return mbDiscussionPersistence;
	}

	/**
	 * Sets the message boards discussion persistence.
	 *
	 * @param mbDiscussionPersistence the message boards discussion persistence
	 */
	public void setMBDiscussionPersistence(
		MBDiscussionPersistence mbDiscussionPersistence) {
		this.mbDiscussionPersistence = mbDiscussionPersistence;
	}

	/**
	 * Returns the message boards mailing list local service.
	 *
	 * @return the message boards mailing list local service
	 */
	public MBMailingListLocalService getMBMailingListLocalService() {
		return mbMailingListLocalService;
	}

	/**
	 * Sets the message boards mailing list local service.
	 *
	 * @param mbMailingListLocalService the message boards mailing list local service
	 */
	public void setMBMailingListLocalService(
		MBMailingListLocalService mbMailingListLocalService) {
		this.mbMailingListLocalService = mbMailingListLocalService;
	}

	/**
	 * Returns the message boards mailing list persistence.
	 *
	 * @return the message boards mailing list persistence
	 */
	public MBMailingListPersistence getMBMailingListPersistence() {
		return mbMailingListPersistence;
	}

	/**
	 * Sets the message boards mailing list persistence.
	 *
	 * @param mbMailingListPersistence the message boards mailing list persistence
	 */
	public void setMBMailingListPersistence(
		MBMailingListPersistence mbMailingListPersistence) {
		this.mbMailingListPersistence = mbMailingListPersistence;
	}

	/**
	 * Returns the message-boards message local service.
	 *
	 * @return the message-boards message local service
	 */
	public MBMessageLocalService getMBMessageLocalService() {
		return mbMessageLocalService;
	}

	/**
	 * Sets the message-boards message local service.
	 *
	 * @param mbMessageLocalService the message-boards message local service
	 */
	public void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {
		this.mbMessageLocalService = mbMessageLocalService;
	}

	/**
	 * Returns the message-boards message remote service.
	 *
	 * @return the message-boards message remote service
	 */
	public MBMessageService getMBMessageService() {
		return mbMessageService;
	}

	/**
	 * Sets the message-boards message remote service.
	 *
	 * @param mbMessageService the message-boards message remote service
	 */
	public void setMBMessageService(MBMessageService mbMessageService) {
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
	 * Returns the message boards stats user local service.
	 *
	 * @return the message boards stats user local service
	 */
	public MBStatsUserLocalService getMBStatsUserLocalService() {
		return mbStatsUserLocalService;
	}

	/**
	 * Sets the message boards stats user local service.
	 *
	 * @param mbStatsUserLocalService the message boards stats user local service
	 */
	public void setMBStatsUserLocalService(
		MBStatsUserLocalService mbStatsUserLocalService) {
		this.mbStatsUserLocalService = mbStatsUserLocalService;
	}

	/**
	 * Returns the message boards stats user persistence.
	 *
	 * @return the message boards stats user persistence
	 */
	public MBStatsUserPersistence getMBStatsUserPersistence() {
		return mbStatsUserPersistence;
	}

	/**
	 * Sets the message boards stats user persistence.
	 *
	 * @param mbStatsUserPersistence the message boards stats user persistence
	 */
	public void setMBStatsUserPersistence(
		MBStatsUserPersistence mbStatsUserPersistence) {
		this.mbStatsUserPersistence = mbStatsUserPersistence;
	}

	/**
	 * Returns the message boards thread local service.
	 *
	 * @return the message boards thread local service
	 */
	public MBThreadLocalService getMBThreadLocalService() {
		return mbThreadLocalService;
	}

	/**
	 * Sets the message boards thread local service.
	 *
	 * @param mbThreadLocalService the message boards thread local service
	 */
	public void setMBThreadLocalService(
		MBThreadLocalService mbThreadLocalService) {
		this.mbThreadLocalService = mbThreadLocalService;
	}

	/**
	 * Returns the message boards thread remote service.
	 *
	 * @return the message boards thread remote service
	 */
	public MBThreadService getMBThreadService() {
		return mbThreadService;
	}

	/**
	 * Sets the message boards thread remote service.
	 *
	 * @param mbThreadService the message boards thread remote service
	 */
	public void setMBThreadService(MBThreadService mbThreadService) {
		this.mbThreadService = mbThreadService;
	}

	/**
	 * Returns the message boards thread persistence.
	 *
	 * @return the message boards thread persistence
	 */
	public MBThreadPersistence getMBThreadPersistence() {
		return mbThreadPersistence;
	}

	/**
	 * Sets the message boards thread persistence.
	 *
	 * @param mbThreadPersistence the message boards thread persistence
	 */
	public void setMBThreadPersistence(MBThreadPersistence mbThreadPersistence) {
		this.mbThreadPersistence = mbThreadPersistence;
	}

	/**
	 * Returns the message boards thread finder.
	 *
	 * @return the message boards thread finder
	 */
	public MBThreadFinder getMBThreadFinder() {
		return mbThreadFinder;
	}

	/**
	 * Sets the message boards thread finder.
	 *
	 * @param mbThreadFinder the message boards thread finder
	 */
	public void setMBThreadFinder(MBThreadFinder mbThreadFinder) {
		this.mbThreadFinder = mbThreadFinder;
	}

	/**
	 * Returns the message boards thread flag local service.
	 *
	 * @return the message boards thread flag local service
	 */
	public MBThreadFlagLocalService getMBThreadFlagLocalService() {
		return mbThreadFlagLocalService;
	}

	/**
	 * Sets the message boards thread flag local service.
	 *
	 * @param mbThreadFlagLocalService the message boards thread flag local service
	 */
	public void setMBThreadFlagLocalService(
		MBThreadFlagLocalService mbThreadFlagLocalService) {
		this.mbThreadFlagLocalService = mbThreadFlagLocalService;
	}

	/**
	 * Returns the message boards thread flag persistence.
	 *
	 * @return the message boards thread flag persistence
	 */
	public MBThreadFlagPersistence getMBThreadFlagPersistence() {
		return mbThreadFlagPersistence;
	}

	/**
	 * Sets the message boards thread flag persistence.
	 *
	 * @param mbThreadFlagPersistence the message boards thread flag persistence
	 */
	public void setMBThreadFlagPersistence(
		MBThreadFlagPersistence mbThreadFlagPersistence) {
		this.mbThreadFlagPersistence = mbThreadFlagPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(UserService userService) {
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

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.portlet.messageboards.model.MBBan",
			mbBanLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portlet.messageboards.model.MBBan");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	protected Class<?> getModelClass() {
		return MBBan.class;
	}

	protected String getModelClassName() {
		return MBBan.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = mbBanPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = MBBanLocalService.class)
	protected MBBanLocalService mbBanLocalService;
	@BeanReference(type = MBBanService.class)
	protected MBBanService mbBanService;
	@BeanReference(type = MBBanPersistence.class)
	protected MBBanPersistence mbBanPersistence;
	@BeanReference(type = MBCategoryLocalService.class)
	protected MBCategoryLocalService mbCategoryLocalService;
	@BeanReference(type = MBCategoryService.class)
	protected MBCategoryService mbCategoryService;
	@BeanReference(type = MBCategoryPersistence.class)
	protected MBCategoryPersistence mbCategoryPersistence;
	@BeanReference(type = MBCategoryFinder.class)
	protected MBCategoryFinder mbCategoryFinder;
	@BeanReference(type = MBDiscussionLocalService.class)
	protected MBDiscussionLocalService mbDiscussionLocalService;
	@BeanReference(type = MBDiscussionPersistence.class)
	protected MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(type = MBMailingListLocalService.class)
	protected MBMailingListLocalService mbMailingListLocalService;
	@BeanReference(type = MBMailingListPersistence.class)
	protected MBMailingListPersistence mbMailingListPersistence;
	@BeanReference(type = MBMessageLocalService.class)
	protected MBMessageLocalService mbMessageLocalService;
	@BeanReference(type = MBMessageService.class)
	protected MBMessageService mbMessageService;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = MBMessageFinder.class)
	protected MBMessageFinder mbMessageFinder;
	@BeanReference(type = MBStatsUserLocalService.class)
	protected MBStatsUserLocalService mbStatsUserLocalService;
	@BeanReference(type = MBStatsUserPersistence.class)
	protected MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(type = MBThreadLocalService.class)
	protected MBThreadLocalService mbThreadLocalService;
	@BeanReference(type = MBThreadService.class)
	protected MBThreadService mbThreadService;
	@BeanReference(type = MBThreadPersistence.class)
	protected MBThreadPersistence mbThreadPersistence;
	@BeanReference(type = MBThreadFinder.class)
	protected MBThreadFinder mbThreadFinder;
	@BeanReference(type = MBThreadFlagLocalService.class)
	protected MBThreadFlagLocalService mbThreadFlagLocalService;
	@BeanReference(type = MBThreadFlagPersistence.class)
	protected MBThreadFlagPersistence mbThreadFlagPersistence;
	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = ResourceLocalService.class)
	protected ResourceLocalService resourceLocalService;
	@BeanReference(type = UserLocalService.class)
	protected UserLocalService userLocalService;
	@BeanReference(type = UserService.class)
	protected UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
	private String _beanIdentifier;
}