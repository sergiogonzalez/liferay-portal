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

package com.liferay.ratings.demo.data.creator.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserModel;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.ratings.demo.data.creator.MultipleRatingsDemoDataCreator;
import com.liferay.ratings.demo.data.creator.RatingsDemoDataCreator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true, property = {"type=thumbs"})
public class ThumbsMultipleRatingsDemoDataCreator
	implements MultipleRatingsDemoDataCreator {

	@Override
	public void create(ClassedModel classedModel) throws PortalException {
		List<User> users = _userLocalService.getUsers(
			0, Math.min(_userLocalService.getUsersCount(), _MAX_USERS));

		Stream<User> usersStream = users.stream();

		List<Long> userIds = usersStream.filter(
			this::_isRegularUser
		).map(
			UserModel::getUserId
		).collect(
			Collectors.toList()
		);

		_addRatings(userIds, classedModel);
	}

	@Override
	public void delete() throws PortalException {
		_ratingsDemoDataCreator.delete();
	}

	private void _addRatings(List<Long> userIds, ClassedModel classedModel)
		throws PortalException {

		Collections.shuffle(userIds);

		int maxRatings = RandomUtil.nextInt(userIds.size());

		for (int i = 0; i < maxRatings; i++) {
			_ratingsDemoDataCreator.create(userIds.get(i), classedModel);
		}
	}

	private boolean _isRegularUser(User user) {
		return !_excludedUsers.contains(user.getEmailAddress());
	}

	private static final int _MAX_USERS = 100;

	private static final List<String> _excludedUsers = Arrays.asList(
		"test@liferay.com", "default@liferay.com");

	@Reference(target = "(type=thumbs)")
	private RatingsDemoDataCreator _ratingsDemoDataCreator;

	@Reference
	private UserLocalService _userLocalService;

}