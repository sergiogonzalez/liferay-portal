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

package com.liferay.friendly.url.exportimport.util;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.model.StagedModel;

/**
 * @author Adolfo Pérez
 */
public interface FriendlyURLExportImportHelper {

	public void exportFriendlyURLs(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws PortletDataException;

	public void importFriendlyURLs(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws PortletDataException;

}