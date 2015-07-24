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

package com.liferay.portlet.exportimport.staging;

import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.LayoutSetStagingHandler;
import com.liferay.portal.model.LayoutStagingHandler;

/**
 * @author Raymond Augé
 */
public class LayoutStagingUtil {

	public static LayoutRevision getLayoutRevision(Layout layout) {
		return _layoutStaging.getLayoutRevision(layout);
	}

	public static LayoutSetBranch getLayoutSetBranch(LayoutSet layoutSet) {
		return _layoutStaging.getLayoutSetBranch(layoutSet);
	}

	public static LayoutSetStagingHandler getLayoutSetStagingHandler(
		LayoutSet layoutSet) {

		return _layoutStaging.getLayoutSetStagingHandler(layoutSet);
	}

	public static LayoutStagingHandler getLayoutStagingHandler(Layout layout) {
		return _layoutStaging.getLayoutStagingHandler(layout);
	}

	public static boolean isBranchingLayout(Layout layout) {
		return _layoutStaging.isBranchingLayout(layout);
	}

	public static boolean isBranchingLayoutSet(
		Group group, boolean privateLayout) {

		return _layoutStaging.isBranchingLayoutSet(group, privateLayout);
	}

	private static final LayoutStaging _layoutStaging =
		ProxyFactory.newServiceTrackedInstance(LayoutStaging.class);

}