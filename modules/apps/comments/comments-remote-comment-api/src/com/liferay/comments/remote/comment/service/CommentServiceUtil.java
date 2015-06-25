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

package com.liferay.comments.remote.comment.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for Comment. This utility wraps
 * {@link com.liferay.comments.remote.comment.service.impl.CommentServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see CommentService
 * @see com.liferay.comments.remote.comment.service.base.CommentServiceBaseImpl
 * @see com.liferay.comments.remote.comment.service.impl.CommentServiceImpl
 * @generated
 */
@ProviderType
public class CommentServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.comments.remote.comment.service.impl.CommentServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static long addComment(long groupId, java.lang.String className,
		long classPK, java.lang.String body)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addComment(groupId, className, classPK, body);
	}

	public static void deleteComment(long commentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteComment(commentId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	public static java.util.List<com.liferay.comments.remote.comment.model.Comment> getComments(
		long groupId, java.lang.String className, long classPK, int start,
		int end) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getComments(groupId, className, classPK, start, end);
	}

	public static int getCommentsCount(long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommentsCount(groupId, className, classPK);
	}

	public static boolean hasDiscussion(long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().hasDiscussion(groupId, className, classPK);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static void subscribeDiscussion(long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().subscribeDiscussion(groupId, className, classPK);
	}

	public static void unsubscribeDiscussion(long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unsubscribeDiscussion(groupId, className, classPK);
	}

	public static long updateComment(java.lang.String className, long classPK,
		long commentId, java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateComment(className, classPK, commentId, subject, body);
	}

	public static CommentService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(CommentService service) {
	}

	private static ServiceTracker<CommentService, CommentService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommentServiceUtil.class);

		_serviceTracker = new ServiceTracker<CommentService, CommentService>(bundle.getBundleContext(),
				CommentService.class, null);

		_serviceTracker.open();
	}
}