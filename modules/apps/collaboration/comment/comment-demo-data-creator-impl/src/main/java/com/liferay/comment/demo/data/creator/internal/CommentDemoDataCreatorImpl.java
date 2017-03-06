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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.comment.demo.data.creator.CommentDemoDataCreator;
import com.liferay.message.boards.kernel.exception.NoSuchMessageException;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.IdentityServiceContextFunction;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(service = CommentDemoDataCreator.class)
public class CommentDemoDataCreatorImpl implements CommentDemoDataCreator {

	@Override
	public Comment create(long userId, ClassedModel classedModel)
		throws PortalException {

		String body = _getRandomBody();

		String className = classedModel.getModelClassName();
		Long classPK = (long)classedModel.getPrimaryKeyObj();

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			className, classPK);

		Group group = _groupLocalService.getGroup(assetEntry.getGroupId());

		IdentityServiceContextFunction serviceContextFunction =
			new IdentityServiceContextFunction(new ServiceContext());

		long commentId = _commentManager.addComment(
			userId, group.getGroupId(), className, classPK, body,
			StringPool.BLANK, body, serviceContextFunction);

		return _getComment(commentId);
	}

	@Override
	public Comment create(long userId, long parentCommentId)
		throws PortalException {

		String body = _getRandomBody();

		User user = _userLocalService.fetchUser(userId);

		IdentityServiceContextFunction serviceContextFunction =
			new IdentityServiceContextFunction(new ServiceContext());

		Comment parentComment = _commentManager.fetchComment(parentCommentId);

		long commentId = _commentManager.addComment(
			userId, parentComment.getClassName(), parentComment.getClassPK(),
			user.getFullName(), parentCommentId, StringPool.BLANK, body,
			serviceContextFunction);

		return _getComment(commentId);
	}

	@Override
	public void delete() throws PortalException {
		for (long commentId : _commentIds) {
			try {
				_commentManager.deleteComment(commentId);
			}
			catch (NoSuchMessageException nsme) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsme, nsme);
				}
			}

			_commentIds.remove(commentId);
		}
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setCommentManager(CommentManager commentManager) {
		_commentManager = commentManager;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static List<String> _read(String fileName) {
		return Arrays.asList(
			StringUtil.split(
				StringUtil.read(
					MultipleCommentDemoDataCreatorImpl.class,
					"dependencies/" + fileName + ".txt"),
				CharPool.NEW_LINE));
	}

	private Comment _getComment(long commentId) {
		_commentIds.add(commentId);

		return _commentManager.fetchComment(commentId);
	}

	private String _getRandomBody() {
		return _getRandomElement(_commentBodies);
	}

	private <T> T _getRandomElement(List<T> list) {
		return list.get(RandomUtil.nextInt(list.size()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommentDemoDataCreatorImpl.class);

	private static final List<String> _commentBodies = _read("CommentBodies");

	private AssetEntryLocalService _assetEntryLocalService;
	private final List<Long> _commentIds = new CopyOnWriteArrayList<>();
	private CommentManager _commentManager;
	private GroupLocalService _groupLocalService;
	private UserLocalService _userLocalService;

}