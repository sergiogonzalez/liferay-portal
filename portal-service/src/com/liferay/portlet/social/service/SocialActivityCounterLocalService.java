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

package com.liferay.portlet.social.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for SocialActivityCounter. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityCounterLocalServiceUtil
 * @see com.liferay.portlet.social.service.base.SocialActivityCounterLocalServiceBaseImpl
 * @see com.liferay.portlet.social.service.impl.SocialActivityCounterLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SocialActivityCounterLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialActivityCounterLocalServiceUtil} to access the social activity counter local service. Add custom service methods to {@link com.liferay.portlet.social.service.impl.SocialActivityCounterLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds an activity counter with a default period length.
	*
	* <p>
	* This method uses the lock service to guard against multiple threads
	* trying to insert the same counter because this service is called
	* asynchronously from the social activity service.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the entity's class this
	counter belongs to
	* @param classPK the primary key of the entity this counter belongs to
	* @param name the counter's name
	* @param ownerType the counter's owner type. Acceptable values are
	<code>TYPE_ACTOR</code>, <code>TYPE_ASSET</code> and
	<code>TYPE_CREATOR</code> defined in {@link
	SocialActivityCounterConstants}.
	* @param currentValue the counter's current value (optionally
	<code>0</code>)
	* @param totalValue the counter's total value (optionally
	<code>0</code>)
	* @param startPeriod the counter's start period
	* @param endPeriod the counter's end period
	* @return the added activity counter
	* @throws PortalException if the group or the previous activity counter
	could not be found
	* @deprecated As of 6.2.0, replaced by {@link #addActivityCounter(long,
	long, long, String, int, int, long, int)}
	*/
	@java.lang.Deprecated
	public com.liferay.portlet.social.model.SocialActivityCounter addActivityCounter(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int currentValue, int totalValue, int startPeriod,
		int endPeriod)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Adds an activity counter specifying a previous activity and period
	* length.
	*
	* <p>
	* This method uses the lock service to guard against multiple threads
	* trying to insert the same counter because this service is called
	* asynchronously from the social activity service.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the entity's class this
	counter belongs to
	* @param classPK the primary key of the entity this counter belongs to
	* @param name the counter name
	* @param ownerType the counter's owner type. Acceptable values are
	<code>TYPE_ACTOR</code>, <code>TYPE_ASSET</code> and
	<code>TYPE_CREATOR</code> defined in {@link
	SocialActivityCounterConstants}.
	* @param currentValue the current value of the counter (optionally
	<code>0</code>)
	* @param totalValue the counter's total value (optionally
	<code>0</code>)
	* @param startPeriod the counter's start period
	* @param endPeriod the counter's end period
	* @param previousActivityCounterId the primary key of the activity
	counter for the previous time period (optionally
	<code>0</code>, if this is the first)
	* @param periodLength the period length in days,
	<code>PERIOD_LENGTH_INFINITE</code> for never ending counters
	or <code>PERIOD_LENGTH_SYSTEM</code> for the period length
	defined in <code>portal-ext.properties</code>. For more
	information see {@link SocialActivityCounterConstants}.
	* @return the added activity counter
	* @throws PortalException if the group or the previous activity counter
	could not be found
	* @deprecated As of 6.2.0, replaced by {@link #addActivityCounter(long,
	long, long, String, int, int, long, int)}
	*/
	@java.lang.Deprecated
	public com.liferay.portlet.social.model.SocialActivityCounter addActivityCounter(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int currentValue, int totalValue, int startPeriod,
		int endPeriod, long previousActivityCounterId, int periodLength)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Adds an activity counter specifying a previous activity and period
	* length.
	*
	* <p>
	* This method uses the lock service to guard against multiple threads
	* trying to insert the same counter because this service is called
	* asynchronously from the social activity service.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the entity's class this counter
	belongs to
	* @param classPK the primary key of the entity this counter belongs to
	* @param name the counter name
	* @param ownerType the counter's owner type. Acceptable values are
	<code>TYPE_ACTOR</code>, <code>TYPE_ASSET</code> and
	<code>TYPE_CREATOR</code> defined in {@link
	SocialActivityCounterConstants}.
	* @param totalValue the counter's total value (optionally <code>0</code>)
	* @param previousActivityCounterId the primary key of the activity counter
	for the previous time period (optionally <code>0</code>, if this
	is the first)
	* @param periodLength the period length in days,
	<code>PERIOD_LENGTH_INFINITE</code> for never ending counters or
	<code>PERIOD_LENGTH_SYSTEM</code> for the period length defined
	in <code>portal-ext.properties</code>. For more information see
	{@link SocialActivityCounterConstants}.
	* @return the added activity counter
	* @throws PortalException if the group or the previous activity counter
	could not be found
	*/
	@com.liferay.portal.kernel.transaction.Transactional(propagation = Propagation.REQUIRES_NEW)
	public com.liferay.portlet.social.model.SocialActivityCounter addActivityCounter(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int totalValue, long previousActivityCounterId,
		int periodLength)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Adds or increments activity counters related to an activity.
	*
	* </p>
	* This method is called asynchronously from the social activity service
	* when the user performs an activity defined in
	* </code>liferay-social.xml</code>.
	* </p>
	*
	* <p>
	* This method first calls the activity processor class, if there is one
	* defined for the activity, checks for limits and increments all the
	* counters that belong to the activity. Afterwards, it processes the
	* activity with respect to achievement classes, if any. Lastly it
	* increments the built-in <code>user.activities</code> and
	* <code>asset.activities</code> counters.
	* </p>
	*
	* @param activity the social activity
	* @throws PortalException if an expected group or expected previous
	activity counters could not be found
	*/
	public void addActivityCounters(
		com.liferay.portlet.social.model.SocialActivity activity)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Adds the social activity counter to the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivityCounter the social activity counter
	* @return the social activity counter that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.social.model.SocialActivityCounter addSocialActivityCounter(
		com.liferay.portlet.social.model.SocialActivityCounter socialActivityCounter);

	/**
	* Creates an activity counter with a default period length, adding it into
	* the database.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the entity's class this
	counter belongs to
	* @param classPK the primary key of the entity this counter belongs to
	* @param name the counter's name
	* @param ownerType the counter's owner type. Acceptable values are
	<code>TYPE_ACTOR</code>, <code>TYPE_ASSET</code> and
	<code>TYPE_CREATOR</code> defined in {@link
	SocialActivityCounterConstants}.
	* @param currentValue the counter's current value (optionally
	<code>0</code>)
	* @param totalValue the counter's total value (optionally
	<code>0</code>)
	* @param startPeriod the counter's start period
	* @param endPeriod the counter's end period
	* @return the created activity counter
	* @throws PortalException if the group or a previous activity counter
	could not be found
	* @deprecated As of 6.2.0, replaced by {@link #addActivityCounter(long,
	long, long, String, int, int, long, int)}
	*/
	@java.lang.Deprecated
	@com.liferay.portal.kernel.transaction.Transactional(propagation = Propagation.REQUIRES_NEW)
	public com.liferay.portlet.social.model.SocialActivityCounter createActivityCounter(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int currentValue, int totalValue, int startPeriod,
		int endPeriod)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Creates an activity counter, adding it into the database.
	*
	* <p>
	* This method actually creates the counter in the database. It requires a
	* new transaction so that other threads can find the new counter when the
	* lock in the calling method is released.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the entity's class this
	counter belongs to
	* @param classPK the primary key of the entity this counter belongs to
	* @param name the counter's name
	* @param ownerType the counter's owner type. Acceptable values are
	<code>TYPE_ACTOR</code>, <code>TYPE_ASSET</code> and
	<code>TYPE_CREATOR</code> defined in {@link
	SocialActivityCounterConstants}.
	* @param currentValue the counter's current value (optionally
	<code>0</code>)
	* @param totalValue the counter's total value of the counter
	(optionally <code>0</code>)
	* @param startPeriod the counter's start period
	* @param endPeriod the counter's end period
	* @param previousActivityCounterId the primary key of the activity
	counter for the previous time period (optionally
	<code>0</code>, if this is the first)
	* @param periodLength the period length in days,
	<code>PERIOD_LENGTH_INFINITE</code> for never ending counters
	or <code>PERIOD_LENGTH_SYSTEM</code> for the period length
	defined in <code>portal-ext.properties</code>. For more
	information see {@link SocialActivityConstants}.
	* @return the created activity counter
	* @throws PortalException if the group or the previous activity counter
	could not be found
	* @deprecated As of 6.2.0, replaced by {@link #addActivityCounter(long,
	long, long, String, int, int, long, int)}
	*/
	@java.lang.Deprecated
	@com.liferay.portal.kernel.transaction.Transactional(propagation = Propagation.REQUIRES_NEW)
	public com.liferay.portlet.social.model.SocialActivityCounter createActivityCounter(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int currentValue, int totalValue, int startPeriod,
		int endPeriod, long previousActivityCounterId, int periodLength)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Creates a new social activity counter with the primary key. Does not add the social activity counter to the database.
	*
	* @param activityCounterId the primary key for the new social activity counter
	* @return the new social activity counter
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter createSocialActivityCounter(
		long activityCounterId);

	/**
	* Deletes all activity counters, limits, and settings related to the asset.
	*
	* <p>
	* This method subtracts the asset's popularity from the owner's
	* contribution points. It also creates a new contribution period if the
	* latest one does not belong to the current period.
	* </p>
	*
	* @param assetEntry the asset entry
	* @throws PortalException if the new contribution counter could not be
	created
	*/
	public void deleteActivityCounters(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Deletes all activity counters for the entity identified by the class name
	* and class primary key.
	*
	* @param className the entity's class name
	* @param classPK the primary key of the entity
	* @throws PortalException if the entity is an asset and its owner's
	contribution counter could not be updated
	*/
	public void deleteActivityCounters(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Deletes all activity counters, limits, and settings related to the entity
	* identified by the class name ID and class primary key.
	*
	* @param classNameId the primary key of the entity's class
	* @param classPK the primary key of the entity
	* @throws PortalException if the entity is an asset and its owner's
	contribution counter could not be updated
	*/
	public void deleteActivityCounters(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Deletes the social activity counter with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityCounterId the primary key of the social activity counter
	* @return the social activity counter that was removed
	* @throws PortalException if a social activity counter with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.social.model.SocialActivityCounter deleteSocialActivityCounter(
		long activityCounterId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Deletes the social activity counter from the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivityCounter the social activity counter
	* @return the social activity counter that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.social.model.SocialActivityCounter deleteSocialActivityCounter(
		com.liferay.portlet.social.model.SocialActivityCounter socialActivityCounter);

	/**
	* Disables all the counters of an asset identified by the class name and
	* class primary key.
	*
	* <p>
	* This method is used by the recycle bin to disable all counters of assets
	* put into the recycle bin. It adjusts the owner's contribution score.
	* </p>
	*
	* @param className the asset's class name
	* @param classPK the primary key of the asset
	* @throws PortalException if the asset owner's contribution counter could
	not be updated
	*/
	public void disableActivityCounters(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Disables all the counters of an asset identified by the class name ID and
	* class primary key.
	*
	* <p>
	* This method is used by the recycle bin to disable all counters of assets
	* put into the recycle bin. It adjusts the owner's contribution score.
	* </p>
	*
	* @param classNameId the primary key of the asset's class
	* @param classPK the primary key of the asset
	* @throws PortalException if the asset owner's contribution counter could
	not be updated
	*/
	public void disableActivityCounters(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	/**
	* Enables all the counters of an asset identified by the class name and
	* class primary key.
	*
	* <p>
	* This method is used by the recycle bin to enable all counters of assets
	* restored from the recycle bin. It adjusts the owner's contribution score.
	* </p>
	*
	* @param className the asset's class name
	* @param classPK the primary key of the asset
	* @throws PortalException if the asset owner's contribution counter could
	not be updated
	*/
	public void enableActivityCounters(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Enables all activity counters of an asset identified by the class name ID
	* and class primary key.
	*
	* <p>
	* This method is used by the recycle bin to enable all counters of assets
	* restored from the recycle bin. It adjusts the owner's contribution score.
	* </p>
	*
	* @param classNameId the primary key of the asset's class
	* @param classPK the primary key of the asset
	* @throws PortalException if the asset owner's contribution counter could
	not be updated
	*/
	public void enableActivityCounters(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the activity counter with the given name, owner, and end period
	* that belong to the given entity.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the entity's class
	* @param classPK the primary key of the entity
	* @param name the counter name
	* @param ownerType the owner type
	* @param endPeriod the end period, <code>-1</code> for the latest one
	* @return the matching activity counter
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.social.model.SocialActivityCounter fetchActivityCounterByEndPeriod(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int endPeriod);

	/**
	* Returns the activity counter with the given name, owner, and start period
	* that belong to the given entity.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the entity's class
	* @param classPK the primary key of the entity
	* @param name the counter name
	* @param ownerType the owner type
	* @param startPeriod the start period
	* @return the matching activity counter
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.social.model.SocialActivityCounter fetchActivityCounterByStartPeriod(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int startPeriod);

	/**
	* Returns the latest activity counter with the given name and owner that
	* belong to the given entity.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the entity's class
	* @param classPK the primary key of the entity
	* @param name the counter name
	* @param ownerType the owner type
	* @return the matching activity counter
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.social.model.SocialActivityCounter fetchLatestActivityCounter(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.social.model.SocialActivityCounter fetchSocialActivityCounter(
		long activityCounterId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Returns all the activity counters with the given name and period offsets.
	*
	* <p>
	* The start and end offsets can belong to different periods. This method
	* groups the counters by name and returns the sum of their current values.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param name the counter name
	* @param startOffset the offset for the start period
	* @param endOffset the offset for the end period
	* @return the matching activity counters
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> getOffsetActivityCounters(
		long groupId, java.lang.String name, int startOffset, int endOffset);

	/**
	* Returns the distribution of the activity counters with the given name and
	* period offsets.
	*
	* <p>
	* The start and end offsets can belong to different periods. This method
	* groups the counters by their owner entity (usually some asset) and
	* returns a counter for each entity class with the sum of the counters'
	* current values.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param name the counter name
	* @param startOffset the offset for the start period
	* @param endOffset the offset for the end period
	* @return the distribution of matching activity counters
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> getOffsetDistributionActivityCounters(
		long groupId, java.lang.String name, int startOffset, int endOffset);

	/**
	* Returns all the activity counters with the given name and time period.
	*
	* <p>
	* The start and end period values can belong to different periods. This
	* method groups the counters by name and returns the sum of their current
	* values.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param name the counter name
	* @param startPeriod the start period
	* @param endPeriod the end period
	* @return the matching activity counters
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> getPeriodActivityCounters(
		long groupId, java.lang.String name, int startPeriod, int endPeriod);

	/**
	* Returns the distribution of activity counters with the given name and
	* time period.
	*
	* <p>
	* The start and end period values can belong to different periods. This
	* method groups the counters by their owner entity (usually some asset) and
	* returns a counter for each entity class with the sum of the counters'
	* current values.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param name the counter name
	* @param startPeriod the start period
	* @param endPeriod the end period
	* @return the distribution of matching activity counters
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> getPeriodDistributionActivityCounters(
		long groupId, java.lang.String name, int startPeriod, int endPeriod);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the social activity counter with the primary key.
	*
	* @param activityCounterId the primary key of the social activity counter
	* @return the social activity counter
	* @throws PortalException if a social activity counter with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.social.model.SocialActivityCounter getSocialActivityCounter(
		long activityCounterId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns a range of all the social activity counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @return the range of social activity counters
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> getSocialActivityCounters(
		int start, int end);

	/**
	* Returns the number of social activity counters.
	*
	* @return the number of social activity counters
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSocialActivityCountersCount();

	/**
	* Returns the range of tuples that contain users and a list of activity
	* counters.
	*
	* <p>
	* The counters returned for each user are passed to this method in the
	* selectedNames array. The method also accepts an array of counter names
	* that are used to rank the users.
	* </p>
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param rankingNames the ranking counter names
	* @param selectedNames the counter names that will be returned with each
	user
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching tuples
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.kernel.util.Tuple> getUserActivityCounters(
		long groupId, java.lang.String[] rankingNames,
		java.lang.String[] selectedNames, int start, int end);

	/**
	* Returns the number of users having a rank based on the given counters.
	*
	* @param groupId the primary key of the group
	* @param rankingNames the ranking counter names
	* @return the number of matching users
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserActivityCountersCount(long groupId,
		java.lang.String[] rankingNames);

	/**
	* Increments the <code>user.achievements</code> counter for a user.
	*
	* <p>
	* This method should be used by an external achievement class when the
	* users unlocks an achievement.
	* </p>
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @throws PortalException if the group or an expected previous activity
	counter could not be found
	*/
	public void incrementUserAchievementCounter(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	/**
	* Updates the social activity counter in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialActivityCounter the social activity counter
	* @return the social activity counter that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.social.model.SocialActivityCounter updateSocialActivityCounter(
		com.liferay.portlet.social.model.SocialActivityCounter socialActivityCounter);
}