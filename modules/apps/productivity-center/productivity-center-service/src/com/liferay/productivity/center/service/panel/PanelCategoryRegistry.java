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

package com.liferay.productivity.center.service.panel;

import com.liferay.productivity.center.panel.PanelCategory;
import com.liferay.productivity.center.service.util.ParentPanelCategoryServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Collections;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class PanelCategoryRegistry {

	public static Iterable<PanelCategory> getPanelCategories(
		PanelCategory parentPanelCategory) {

		return _instance._getPanelCategories(parentPanelCategory);
	}

	private PanelCategoryRegistry() {
		_parentCategoryServiceTrackerMap.open();
	}

	private Iterable<PanelCategory> _getPanelCategories(
		PanelCategory parentPanelCategory) {

		Iterable<PanelCategory> panelCategories =
			_parentCategoryServiceTrackerMap.getService(
				parentPanelCategory.getKey());

		if (panelCategories == null) {
			return Collections.emptyList();
		}

		return panelCategories;
	}

	private static final PanelCategoryRegistry _instance =
		new PanelCategoryRegistry();

	private final ServiceTrackerMap<String, List<PanelCategory>>
		_parentCategoryServiceTrackerMap =
			ServiceTrackerCollections.multiValueMap(
				PanelCategory.class, "(panel.category=*)",
				ParentPanelCategoryServiceReferenceMapper.<PanelCategory>create());

}