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

package com.liferay.portlet.documentlibrary.web.lar;

import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portlet.documentlibrary.web.constants.DLWebKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera
 */
@Component(
	property = { "javax.portlet.name=" + DLWebKeys.DOCUMENT_LIBRARY_ADMIN },
	service = PortletDataHandler.class
)
public class DLAdminPortletDataHandler extends DLPortletDataHandler {
}