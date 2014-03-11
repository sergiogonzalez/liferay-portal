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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * Checks permissions with respect to subscriptions.
 *
 * @author Mate Thurzo
 * @author Raymond Augé
 * @author Roberto Díaz
 */
public interface SubscriptionPermission {

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #check(PermissionChecker,
	 *             String, long, String, long)}
	 */
	@Deprecated
	public void check(
			PermissionChecker permissionChecker, String className, long classPK)
		throws PortalException, SystemException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #check(PermissionChecker,
	 *             Subscription, String, long)} )}
	 */
	public void check(
			PermissionChecker permissionChecker, String subscriptionClassName,
			long subscriptionClassPK, String inferredClassName,
			long inferredClassPK)
		throws PortalException, SystemException;

	/**
	 * Checks if the user has permission to subscribe to the subscription entity
	 * and view the entity.
	 *
	 * <p>
	 * The entity is the subject of the notification.
	 * </p>
	 *
	 * @param  permissionChecker the permission checker
	 * @param  subscription the current subscription entity
	 * @param  className the class name of the subject entity of the
	 *         notification
	 * @param  classPK the primary key of the subject entity of the
	 *         notification
	 * @throws PortalException if the user did not have permission to view the
	 *         entity or receive notifications about the subscribed
	 *         entity, or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 * @see    #contains(PermissionChecker, String, long, String, long)
	 */
	void check(
			PermissionChecker permissionChecker, Subscription subscription,
			String className, long classPK)
		throws PortalException, SystemException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #contains(PermissionChecker,
	 *             String, long, String, long)}
	 */
	@Deprecated
	public boolean contains(
			PermissionChecker permissionChecker, String className, long classPK)
		throws PortalException, SystemException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #contains(PermissionChecker,
	 *             Subscription, String, long)} )}
	 */
	@Deprecated
	public boolean contains(
			PermissionChecker permissionChecker, String subscriptionClassName,
			long subscriptionClassPK, String inferredClassName,
			long inferredClassPK)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if the user has permission to subscribe to the
	 * subscribed entity and receive notifications about the entity.
	 *
	 * <p>
	 * If the subscribed entity is a container and if an entity (presumably
	 * within the container) is specified, a view permission check is performed
	 * on the entity. The entity is the subject of the notification. A failed
	 * view check on the entity short-circuits further permission checks and
	 * prevents notifications from being sent. Checking the view permission on
	 * the entity is useful for enforcing permissions for private subtrees
	 * within larger container entities to which the user is subscribed.
	 * </p>
	 *
	 *
	 * @param  permissionChecker the permission checker
	 * @param  subscription the primary key of the subscribed entity
	 * @param  className the class name of the subject entity of the
	 *         notification
	 * @param  classPK the primary key of the subject entity of the
	 *         notification
	 * @return <code>true</code> if the user has permission to subscribe to the
	 *         subscribed entity and receive notifications about the inferred
	 *         entity; <code>false</code> otherwise
	 * @throws PortalException if the user did not have permission to view the
	 *         inferred entity or receive notifications about it via the
	 *         subscribed entity, or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public boolean contains(
			PermissionChecker permissionChecker, Subscription subscription,
			String className, long classPK)
		throws PortalException, SystemException;

}