/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetLinkFinderUtil {
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1_V(
		long entryId1, boolean visible)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByE1_V(entryId1, visible);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1_T_V(
		long entryId1, int type, boolean visible)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByE1_T_V(entryId1, type, visible);
	}

	public static AssetLinkFinder getFinder() {
		if (_finder == null) {
			_finder = (AssetLinkFinder)PortalBeanLocatorUtil.locate(AssetLinkFinder.class.getName());

			ReferenceRegistry.registerReference(AssetLinkFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(AssetLinkFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(AssetLinkFinderUtil.class, "_finder");
	}

	private static AssetLinkFinder _finder;
}