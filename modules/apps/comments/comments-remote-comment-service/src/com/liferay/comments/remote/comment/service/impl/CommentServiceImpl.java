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

package com.liferay.comments.remote.comment.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.comments.remote.comment.model.Comment;
import com.liferay.comments.remote.comment.model.impl.CommentImpl;
import com.liferay.comments.remote.comment.service.base.CommentServiceBaseImpl;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.comment.DiscussionCommentIterator;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.service.ServiceContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The implementation of the comment remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.comments.remote.comment.service.CommentService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CommentServiceBaseImpl
 * @see com.liferay.comments.remote.comment.service.CommentServiceUtil
 */
@ProviderType
public class CommentServiceImpl extends CommentServiceBaseImpl {

	@Override
	public long addComment(
			long groupId, String className, long classPK, String body)
		throws PortalException {

		DiscussionPermission discussionPermission =
			CommentManagerUtil.getDiscussionPermission(getPermissionChecker());

		long companyId = getCompanyId(groupId);

		discussionPermission.checkAddPermission(
			companyId, groupId, className, classPK);

		return CommentManagerUtil.addComment(
			getUserId(), groupId, className, classPK, body,
			createServiceContextFunction(companyId));
	}

	@Override
	public void deleteComment(long commentId) throws PortalException {
		DiscussionPermission discussionPermission =
			CommentManagerUtil.getDiscussionPermission(getPermissionChecker());

		discussionPermission.checkDeletePermission(commentId);

		CommentManagerUtil.deleteComment(commentId);
	}

	@Override
	public List<Comment> getComments(
			long groupId, String className, long classPK, int start, int end)
		throws PortalException {

		DiscussionPermission discussionPermission =
			CommentManagerUtil.getDiscussionPermission(getPermissionChecker());

		discussionPermission.checkViewPermission(
			getCompanyId(groupId), groupId, className, classPK);

		Discussion discussion = CommentManagerUtil.getDiscussion(
			getUserId(), groupId, className, classPK,
			createServiceContextFunction());

		DiscussionComment rootDiscussionComment =
			discussion.getRootDiscussionComment();

		if (start == QueryUtil.ALL_POS) {
			start = 0;
		}

		DiscussionCommentIterator threadDiscussionCommentIterator =
			rootDiscussionComment.getThreadDiscussionCommentIterator(start);

		if (end == QueryUtil.ALL_POS) {
			return getAllComments(threadDiscussionCommentIterator);
		}

		int commentsCount = end - start;

		if (commentsCount <= 0) {
			return Collections.emptyList();
		}

		List<Comment> comments = new ArrayList<>(commentsCount);

		while (threadDiscussionCommentIterator.hasNext() &&
			   (commentsCount > 0)) {

			comments.add(
				new CommentImpl(threadDiscussionCommentIterator.next()));

			commentsCount--;
		}

		return comments;
	}

	@Override
	public int getCommentsCount(long groupId, String className, long classPK)
		throws PortalException {

		DiscussionPermission discussionPermission =
			CommentManagerUtil.getDiscussionPermission(getPermissionChecker());

		discussionPermission.checkViewPermission(
			getCompanyId(groupId), groupId, className, classPK);

		return CommentManagerUtil.getCommentsCount(className, classPK);
	}

	@Override
	public boolean hasDiscussion(long groupId, String className, long classPK)
		throws PortalException {

		BaseModelPermissionCheckerUtil.containsBaseModelPermission(
			getPermissionChecker(), groupId, className, classPK,
			ActionKeys.VIEW);

		return CommentManagerUtil.hasDiscussion(className, classPK);
	}

	@Override
	public void subscribeDiscussion(
			long groupId, String className, long classPK)
		throws PortalException {

		DiscussionPermission discussionPermission =
			CommentManagerUtil.getDiscussionPermission(getPermissionChecker());

		discussionPermission.checkSubscribePermission(
			getCompanyId(groupId), groupId, className, classPK);

		CommentManagerUtil.subscribeDiscussion(
			getUserId(), groupId, className, classPK);
	}

	@Override
	public void unsubscribeDiscussion(
			long groupId, String className, long classPK)
		throws PortalException {

		DiscussionPermission discussionPermission =
			CommentManagerUtil.getDiscussionPermission(getPermissionChecker());

		discussionPermission.checkSubscribePermission(
			getCompanyId(groupId), groupId, className, classPK);

		CommentManagerUtil.unsubscribeDiscussion(
			getUserId(), className, classPK);
	}

	@Override
	public long updateComment(
			String className, long classPK, long commentId, String subject,
			String body)
		throws PortalException {

		DiscussionPermission discussionPermission =
			CommentManagerUtil.getDiscussionPermission(getPermissionChecker());

		discussionPermission.checkUpdatePermission(commentId);

		return CommentManagerUtil.updateComment(
			getUserId(), className, classPK, commentId, subject, body,
			createServiceContextFunction(WorkflowConstants.ACTION_PUBLISH));
	}

	protected Function<String, ServiceContext> createServiceContextFunction() {
		return new Function<String, ServiceContext>() {

			@Override
			public ServiceContext apply(String className) {
				return new ServiceContext();
			}

		};
	}

	protected Function<String, ServiceContext> createServiceContextFunction(
		final int workflowAction) {

		return new Function<String, ServiceContext>() {

			@Override
			public ServiceContext apply(String className) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setWorkflowAction(workflowAction);

				return serviceContext;
			}

		};
	}

	protected Function<String, ServiceContext> createServiceContextFunction(
		final long companyId) {

		return new Function<String, ServiceContext>() {

			@Override
			public ServiceContext apply(String className) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setCompanyId(companyId);

				return serviceContext;
			}

		};
	}

	protected List<Comment> getAllComments(
		DiscussionCommentIterator threadDiscussionCommentIterator) {

		List<Comment> comments = new ArrayList<>();

		while (threadDiscussionCommentIterator.hasNext()) {
			comments.add(
				new CommentImpl(threadDiscussionCommentIterator.next()));
		}

		return comments;
	}

	protected long getCompanyId(long groupId) throws PortalException {
		Group group = groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	protected String getUserName() throws PortalException {
		User user = getUser();

		return user.getFullName();
	}

}