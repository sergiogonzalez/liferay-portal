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

package com.liferay.comment.demo.data.creator.internal;

import com.liferay.comment.demo.data.creator.CommentDemoDataCreator;
import com.liferay.comment.demo.data.creator.MultipleCommentDemoDataCreator;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserModel;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(service = MultipleCommentDemoDataCreator.class)
public class MultipleCommentDemoDataCreatorImpl
	implements MultipleCommentDemoDataCreator {

	@Override
	public void create(String className, long classPK) throws PortalException {
		int commentsCount = RandomUtil.nextInt(_MAX_COMMENTS);

		int totalUsers = _userLocalService.getUsersCount();

		List<Long> userIds =
			_userLocalService.getUsers(0, totalUsers).stream().filter(
				user -> !_excludedUsers.contains(user.getEmailAddress())).map(
				UserModel::getUserId).collect(Collectors.toList());

		_addComments(
			userIds, className, classPK, _ROOT_COMMENT, commentsCount, 1);
	}

	@Override
	public void delete() throws PortalException {
		_commentDemoDataCreator.delete();
	}

	@Reference(unbind = "-")
	public void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Reference(unbind = "-")
	protected void setCommentDemoDataCreator(
		CommentDemoDataCreator commentDemoDataCreator) {

		_commentDemoDataCreator = commentDemoDataCreator;
	}

	private int _addComments(
			List<Long> userIds, String className, long classPK, long commentId,
			int commentsCount, int level)
		throws PortalException {

		int commentsCreated = 0;

		int repliesCount = RandomUtil.nextInt(_MAX_REPLIES / level);

		while ((commentsCount > commentsCreated) && (repliesCount != 0)) {
			long userId = _getRandomElement(userIds);

			Comment comment = null;

			if (commentId == _ROOT_COMMENT) {
				comment = _commentDemoDataCreator.create(
					userId, className, classPK);
			}
			else {
				comment = _commentDemoDataCreator.create(userId, commentId);
				repliesCount--;
			}

			commentsCreated++;

			if (level < _MAX_LEVEL) {
				int maxComments = commentsCount - commentsCreated;

				commentsCreated += _addComments(
					userIds, className, classPK, comment.getCommentId(),
					maxComments, level + 1);
			}
		}

		return commentsCreated;
	}

	private <T> T _getRandomElement(List<T> list) {
		return list.get(RandomUtil.nextInt(list.size()));
	}

	private static final int _MAX_COMMENTS = 100;

	private static final int _MAX_LEVEL = 3;

	private static final int _MAX_REPLIES = 10;

	private static final int _ROOT_COMMENT = 0;

	private static final List<String> _excludedUsers = new ArrayList<>();

	static {
		_excludedUsers.add("test@liferay.com");
		_excludedUsers.add("default@liferay.com");
	}

	private CommentDemoDataCreator _commentDemoDataCreator;
	private UserLocalService _userLocalService;

}