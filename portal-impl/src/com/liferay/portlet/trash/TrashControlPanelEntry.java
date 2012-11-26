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

package com.liferay.portlet.trash;

import com.liferay.portal.model.Portlet;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.DefaultControlPanelEntry;
import com.liferay.portlet.trash.util.TrashUtil;

/**
 * @author Eudaldo Alonso
 */
public class TrashControlPanelEntry extends DefaultControlPanelEntry {

	public boolean hasPermissionDenied(
		Portlet portlet, String category, ThemeDisplay themeDisplay)
		throws Exception {

		return !TrashUtil.isTrashEnabled(themeDisplay.getScopeGroupId());
	}

}