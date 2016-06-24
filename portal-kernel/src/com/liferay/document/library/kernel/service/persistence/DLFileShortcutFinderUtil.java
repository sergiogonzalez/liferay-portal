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

package com.liferay.document.library.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class DLFileShortcutFinderUtil {
	public static int countByF_A(long folderId, boolean active,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition) {
		return getFinder().countByF_A(folderId, active, queryDefinition);
	}

	public static int filterCountByF_A(long folderId, boolean active,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition) {
		return getFinder().filterCountByF_A(folderId, active, queryDefinition);
	}

	public static java.util.List<com.liferay.document.library.kernel.model.DLFileShortcut> filterFindByF_A(
		long folderId, boolean active,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition) {
		return getFinder().filterFindByF_A(folderId, active, queryDefinition);
	}

	public static java.util.List<com.liferay.document.library.kernel.model.DLFileShortcut> findByF_A(
		long folderId, boolean active,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition) {
		return getFinder().findByF_A(folderId, active, queryDefinition);
	}

	public static DLFileShortcutFinder getFinder() {
		if (_finder == null) {
			_finder = (DLFileShortcutFinder)PortalBeanLocatorUtil.locate(DLFileShortcutFinder.class.getName());

			ReferenceRegistry.registerReference(DLFileShortcutFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(DLFileShortcutFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(DLFileShortcutFinderUtil.class,
			"_finder");
	}

	private static DLFileShortcutFinder _finder;
}