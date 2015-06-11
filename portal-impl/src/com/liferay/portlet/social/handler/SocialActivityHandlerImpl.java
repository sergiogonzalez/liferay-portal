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

package com.liferay.portlet.social.handler;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class SocialActivityHandlerImpl implements SocialActivityHandler {

	public SocialActivityHandlerImpl(
		SocialActivityHandler defaultSocialActivityHandler) {

		_defaultSocialActivityHandler = defaultSocialActivityHandler;
	}

	@Override
	public void addActivity(
			long userId, long groupId, String className, long classPK, int type,
			String extraData, long receiverUserId)
		throws PortalException {

		SocialActivityHandler socialActivityHandler = getSocialActivityHandler(
			className);

		socialActivityHandler.addActivity(
			userId, groupId, className, classPK, type, extraData,
			receiverUserId);
	}

	@Override
	public void addUniqueActivity(
			long userId, long groupId, Date createDate, String className,
			long classPK, int type, String extraData, long receiverUserId)
		throws PortalException {

		SocialActivityHandler socialActivityHandler = getSocialActivityHandler(
			className);

		socialActivityHandler.addUniqueActivity(
			userId, groupId, createDate, className, classPK, type, extraData,
			receiverUserId);
	}

	protected SocialActivityHandler getSocialActivityHandler(String className) {
		SocialActivityHandler socialActivityHandler =
			_serviceTrackerMap.getService(className);

		if (socialActivityHandler != null) {
			return socialActivityHandler;
		}

		return _defaultSocialActivityHandler;
	}

	private final SocialActivityHandler _defaultSocialActivityHandler;

	private final ServiceTrackerMap<String, SocialActivityHandler>
		_serviceTrackerMap = ServiceTrackerCollections.singleValueMap(
			SocialActivityHandler.class, "(model.className=*)",
			new ServiceReferenceMapper<String, SocialActivityHandler>() {

				@Override
				public void map(
					ServiceReference<SocialActivityHandler> serviceReference,
					Emitter<String> emitter) {

					String modelClassName =
						(String)serviceReference.getProperty("model.className");

					emitter.emit(modelClassName);
				}

			});

}