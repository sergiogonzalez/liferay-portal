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

package com.liferay.document.library.display.context;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;

/**
 * @author Mauro Mariuzzo
 */
public class BaseDLViewFileHistoryDisplayContext
	extends BaseDLDisplayContext<DLViewFileHistoryDisplayContext>
	implements DLViewFileHistoryDisplayContext {

	public BaseDLViewFileHistoryDisplayContext(
		UUID uuid, DLViewFileHistoryDisplayContext parentDLDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		super(uuid, parentDLDisplayContext, request, response);

		this.fileVersion = fileVersion;
	}

	@Override
	public Menu getMenu() throws PortalException {
		return parentDisplayContext.getMenu();
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		return parentDisplayContext.getToolbarItems();
	}

	protected FileVersion fileVersion;

}