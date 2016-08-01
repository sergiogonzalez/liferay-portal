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

package com.liferay.document.library.jaxrs.provider;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ext.Provider;

import com.liferay.portal.kernel.util.PortalUtil;
import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	immediate = true, service = CompanyContextProvider.class
)
@Provider
public class CompanyContextProvider implements ContextProvider<Company> {

	@Override
	public Company createContext(Message message) {
		try {
			return PortalUtil.getCompany(
				(HttpServletRequest)message.getContextualProperty(
					"HTTP.REQUEST"));
		}
		catch (PortalException e) {
			return null;
		}
	}

}