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

package com.liferay.sharing.internal.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.sharing.security.permission.SharingPermissionChecker;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = SharingPermissionCheckerTracker.class)
public class SharingPermissionCheckerTracker {

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SharingPermissionChecker.class,
			"(model.class.name=*)",
			(serviceReference, emitter) -> {
				emitter.emit(
					classNameLocalService.getClassNameId(
						(String)serviceReference.getProperty(
							"model.class.name")));
			});
	}

	@Deactivate
	public void deactivate() {
		_serviceTrackerMap.close();
	}

	public SharingPermissionChecker getSharingPermissionChecker(
		long classNameId) {

		return _serviceTrackerMap.getService(classNameId);
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	private ServiceTrackerMap<Long, SharingPermissionChecker>
		_serviceTrackerMap;

}