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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommentService}.
 *
 * @author Brian Wing Shun Chan
 * @see CommentService
 * @generated
 */
@ProviderType
public class CommentServiceWrapper implements CommentService,
	ServiceWrapper<CommentService> {
	public CommentServiceWrapper(CommentService commentService) {
		_commentService = commentService;
	}

	@Override
	public long addComment(long groupId, java.lang.String className,
		long classPK, java.lang.String body)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commentService.addComment(groupId, className, classPK, body);
	}

	@Override
	public void deleteComment(long commentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commentService.deleteComment(commentId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _commentService.getBeanIdentifier();
	}

	@Override
	public java.util.List<com.liferay.comments.remote.comment.model.Comment> getComments(
		long groupId, java.lang.String className, long classPK, int start,
		int end) throws com.liferay.portal.kernel.exception.PortalException {
		return _commentService.getComments(groupId, className, classPK, start,
			end);
	}

	@Override
	public int getCommentsCount(long groupId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commentService.getCommentsCount(groupId, className, classPK);
	}

	@Override
	public boolean hasDiscussion(long groupId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commentService.hasDiscussion(groupId, className, classPK);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_commentService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public void subscribeDiscussion(long groupId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commentService.subscribeDiscussion(groupId, className, classPK);
	}

	@Override
	public void unsubscribeDiscussion(long groupId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commentService.unsubscribeDiscussion(groupId, className, classPK);
	}

	@Override
	public long updateComment(java.lang.String className, long classPK,
		long commentId, java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commentService.updateComment(className, classPK, commentId,
			subject, body);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public CommentService getWrappedCommentService() {
		return _commentService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedCommentService(CommentService commentService) {
		_commentService = commentService;
	}

	@Override
	public CommentService getWrappedService() {
		return _commentService;
	}

	@Override
	public void setWrappedService(CommentService commentService) {
		_commentService = commentService;
	}

	private CommentService _commentService;
}