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

package com.liferay.subscription.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the local service interface for Subscription. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see SubscriptionLocalServiceUtil
 * @see com.liferay.subscription.service.base.SubscriptionLocalServiceBaseImpl
 * @see com.liferay.subscription.service.impl.SubscriptionLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SubscriptionLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SubscriptionLocalServiceUtil} to access the subscription local service. Add custom service methods to {@link com.liferay.subscription.service.impl.SubscriptionLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns <code>true</code> if the user is subscribed to the entity.
	*
	* @param companyId the primary key of the company
	* @param userId the primary key of the user
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	* @return <code>true</code> if the user is subscribed to the entity;
	<code>false</code> otherwise
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isSubscribed(long companyId, long userId,
		java.lang.String className, long classPK);

	/**
	* Returns <code>true</code> if the user is subscribed to any of the
	* entities.
	*
	* @param companyId the primary key of the company
	* @param userId the primary key of the user
	* @param className the entity's class name
	* @param classPKs the primary key of the entities
	* @return <code>true</code> if the user is subscribed to any of the
	entities; <code>false</code> otherwise
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isSubscribed(long companyId, long userId,
		java.lang.String className, long[] classPKs);

	/**
	* Subscribes the user to the entity, notifying him the instant the entity
	* is created, deleted, or modified.
	*
	* <p>
	* If there is no asset entry with the class name and class PK a new asset
	* entry is created.
	* </p>
	*
	* <p>
	* A social activity for the subscription is created using the asset entry
	* associated with the class name and class PK, or the newly created asset
	* entry.
	* </p>
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the entity's group
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	* @return the subscription
	*/
	public Subscription addSubscription(long userId, long groupId,
		java.lang.String className, long classPK) throws PortalException;

	/**
	* Subscribes the user to the entity, notifying him at the given frequency.
	*
	* <p>
	* If there is no asset entry with the class name and class PK a new asset
	* entry is created.
	* </p>
	*
	* <p>
	* A social activity for the subscription is created using the asset entry
	* associated with the class name and class PK, or the newly created asset
	* entry.
	* </p>
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the entity's group
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	* @param frequency the frequency for notifications
	* @return the subscription
	*/
	public Subscription addSubscription(long userId, long groupId,
		java.lang.String className, long classPK, java.lang.String frequency)
		throws PortalException;

	/**
	* Deletes the subscription. A social activity with the unsubscribe action
	* is created.
	*
	* @param subscription the subscription
	* @return the subscription that was removed
	*/
	public Subscription deleteSubscription(Subscription subscription)
		throws PortalException;

	/**
	* Deletes the subscription with the primary key. A social activity with the
	* unsubscribe action is created.
	*
	* @param subscriptionId the primary key of the subscription
	* @return the subscription that was removed
	*/
	public Subscription deleteSubscription(long subscriptionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Subscription fetchSubscription(long companyId, long userId,
		java.lang.String className, long classPK);

	/**
	* Returns the subscription of the user to the entity.
	*
	* @param companyId the primary key of the company
	* @param userId the primary key of the user
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	* @return the subscription of the user to the entity
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Subscription getSubscription(long companyId, long userId,
		java.lang.String className, long classPK) throws PortalException;

	/**
	* Returns the number of subscriptions of the user.
	*
	* @param userId the primary key of the user
	* @return the number of subscriptions of the user
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserSubscriptionsCount(long userId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Returns all the subscriptions to the entity.
	*
	* @param companyId the primary key of the company
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	* @return the subscriptions to the entity
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Subscription> getSubscriptions(long companyId,
		java.lang.String className, long classPK);

	/**
	* Returns all the subscriptions of the user to the entities.
	*
	* @param companyId the primary key of the company
	* @param userId the primary key of the user
	* @param className the entity's class name
	* @param classPKs the primary key of the entities
	* @return the subscriptions of the user to the entities
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Subscription> getSubscriptions(long companyId, long userId,
		java.lang.String className, long[] classPKs);

	/**
	* Returns an ordered range of all the subscriptions of the user.
	*
	* @param userId the primary key of the user
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param orderByComparator the comparator to order the subscriptions
	* @return the range of subscriptions of the user
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Subscription> getUserSubscriptions(long userId, int start,
		int end, OrderByComparator<Subscription> orderByComparator);

	/**
	* Returns all the subscriptions of the user to the entities with the class
	* name.
	*
	* @param userId the primary key of the user
	* @param className the entity's class name
	* @return the subscriptions of the user to the entities with the class name
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Subscription> getUserSubscriptions(long userId,
		java.lang.String className);

	/**
	* Deletes the user's subscription to the entity. A social activity with the
	* unsubscribe action is created.
	*
	* @param userId the primary key of the user
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	*/
	public void deleteSubscription(long userId, java.lang.String className,
		long classPK) throws PortalException;

	/**
	* Deletes all the subscriptions to the entity.
	*
	* @param companyId the primary key of the company
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	*/
	public void deleteSubscriptions(long companyId, java.lang.String className,
		long classPK) throws PortalException;

	/**
	* Deletes all the subscriptions of the user.
	*
	* @param userId the primary key of the user
	*/
	public void deleteSubscriptions(long userId) throws PortalException;

	public void deleteSubscriptions(long userId, long groupId)
		throws PortalException;
}