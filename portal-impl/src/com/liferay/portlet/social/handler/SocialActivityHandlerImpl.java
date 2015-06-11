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

import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

/**
 * @author Adolfo Pérez
 */
public class SocialActivityHandlerImpl implements SocialActivityHandler {

	public SocialActivityHandlerImpl(
		SocialActivityHandler defaultSocialActivityHandler) {

		_defaultSocialActivityHandler = defaultSocialActivityHandler;
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