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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.ratings.demo.data.creator.RatingsDemoDataCreator;
import com.liferay.ratings.kernel.exception.NoSuchEntryException;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true, property = {"type=thumbs"})
public class ThumbsRatingsDemoDataCreator implements RatingsDemoDataCreator {

	@Override
	public RatingsEntry create(long userId, ClassedModel classedModel)
		throws PortalException {

		User user = _userLocalService.fetchUser(userId);

		String className = classedModel.getModelClassName();
		Long classPK = (long)classedModel.getPrimaryKeyObj();

		int score = RandomUtil.nextInt(2);

		RatingsEntry ratingsEntry = _ratingsEntryLocalService.updateEntry(
			user.getUserId(), className, classPK, (double)score,
			new ServiceContext());

		_ratingsEntryIds.add(ratingsEntry.getEntryId());

		return ratingsEntry;
	}

	@Override
	public void delete() throws PortalException {
		for (long commentId : _ratingsEntryIds) {
			try {
				_ratingsEntryLocalService.deleteRatingsEntry(commentId);
			}
			catch (NoSuchEntryException nsee) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsee, nsee);
				}
			}

			_ratingsEntryIds.remove(commentId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThumbsRatingsDemoDataCreator.class);

	private final List<Long> _ratingsEntryIds = new CopyOnWriteArrayList<>();

	@Reference
	private RatingsEntryLocalService _ratingsEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}