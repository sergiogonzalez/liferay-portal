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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for Subscription. This utility wraps
 * {@link com.liferay.subscription.service.impl.SubscriptionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SubscriptionLocalService
 * @see com.liferay.subscription.service.base.SubscriptionLocalServiceBaseImpl
 * @see com.liferay.subscription.service.impl.SubscriptionLocalServiceImpl
 * @generated
 */
@ProviderType
public class SubscriptionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.subscription.service.impl.SubscriptionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
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
	public static boolean isSubscribed(long companyId, long userId,
		java.lang.String className, long classPK) {
		return getService().isSubscribed(companyId, userId, className, classPK);
	}

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
	public static boolean isSubscribed(long companyId, long userId,
		java.lang.String className, long[] classPKs) {
		return getService().isSubscribed(companyId, userId, className, classPKs);
	}

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
	public static com.liferay.portal.kernel.model.Subscription addSubscription(
		long userId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addSubscription(userId, groupId, className, classPK);
	}

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
	public static com.liferay.portal.kernel.model.Subscription addSubscription(
		long userId, long groupId, java.lang.String className, long classPK,
		java.lang.String frequency)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSubscription(userId, groupId, className, classPK,
			frequency);
	}

	/**
	* Deletes the subscription. A social activity with the unsubscribe action
	* is created.
	*
	* @param subscription the subscription
	* @return the subscription that was removed
	*/
	public static com.liferay.portal.kernel.model.Subscription deleteSubscription(
		com.liferay.portal.kernel.model.Subscription subscription)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSubscription(subscription);
	}

	/**
	* Deletes the subscription with the primary key. A social activity with the
	* unsubscribe action is created.
	*
	* @param subscriptionId the primary key of the subscription
	* @return the subscription that was removed
	*/
	public static com.liferay.portal.kernel.model.Subscription deleteSubscription(
		long subscriptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSubscription(subscriptionId);
	}

	public static com.liferay.portal.kernel.model.Subscription fetchSubscription(
		long companyId, long userId, java.lang.String className, long classPK) {
		return getService()
				   .fetchSubscription(companyId, userId, className, classPK);
	}

	public static com.liferay.portal.kernel.model.Subscription fetchSubscription(
		long subscriptionId) {
		return getService().fetchSubscription(subscriptionId);
	}

	/**
	* Returns the subscription of the user to the entity.
	*
	* @param companyId the primary key of the company
	* @param userId the primary key of the user
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	* @return the subscription of the user to the entity
	*/
	public static com.liferay.portal.kernel.model.Subscription getSubscription(
		long companyId, long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getSubscription(companyId, userId, className, classPK);
	}

	/**
	* Returns the number of subscriptions of the user.
	*
	* @param userId the primary key of the user
	* @return the number of subscriptions of the user
	*/
	public static int getUserSubscriptionsCount(long userId) {
		return getService().getUserSubscriptionsCount(userId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Returns all the subscriptions to the entity.
	*
	* @param companyId the primary key of the company
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	* @return the subscriptions to the entity
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Subscription> getSubscriptions(
		long companyId, java.lang.String className, long classPK) {
		return getService().getSubscriptions(companyId, className, classPK);
	}

	/**
	* Returns all the subscriptions of the user to the entities.
	*
	* @param companyId the primary key of the company
	* @param userId the primary key of the user
	* @param className the entity's class name
	* @param classPKs the primary key of the entities
	* @return the subscriptions of the user to the entities
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Subscription> getSubscriptions(
		long companyId, long userId, java.lang.String className, long[] classPKs) {
		return getService()
				   .getSubscriptions(companyId, userId, className, classPKs);
	}

	/**
	* Returns an ordered range of all the subscriptions of the user.
	*
	* @param userId the primary key of the user
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @param orderByComparator the comparator to order the subscriptions
	* @return the range of subscriptions of the user
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Subscription> getUserSubscriptions(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Subscription> orderByComparator) {
		return getService()
				   .getUserSubscriptions(userId, start, end, orderByComparator);
	}

	/**
	* Returns all the subscriptions of the user to the entities with the class
	* name.
	*
	* @param userId the primary key of the user
	* @param className the entity's class name
	* @return the subscriptions of the user to the entities with the class name
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Subscription> getUserSubscriptions(
		long userId, java.lang.String className) {
		return getService().getUserSubscriptions(userId, className);
	}

	/**
	* Deletes the user's subscription to the entity. A social activity with the
	* unsubscribe action is created.
	*
	* @param userId the primary key of the user
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	*/
	public static void deleteSubscription(long userId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteSubscription(userId, className, classPK);
	}

	/**
	* Deletes all the subscriptions to the entity.
	*
	* @param companyId the primary key of the company
	* @param className the entity's class name
	* @param classPK the primary key of the entity's instance
	*/
	public static void deleteSubscriptions(long companyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteSubscriptions(companyId, className, classPK);
	}

	/**
	* Deletes all the subscriptions of the user.
	*
	* @param userId the primary key of the user
	*/
	public static void deleteSubscriptions(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteSubscriptions(userId);
	}

	public static void deleteSubscriptions(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteSubscriptions(userId, groupId);
	}

	public static SubscriptionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SubscriptionLocalService, SubscriptionLocalService> _serviceTracker =
		ServiceTrackerFactory.open(SubscriptionLocalService.class);
}