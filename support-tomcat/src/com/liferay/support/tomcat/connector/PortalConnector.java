/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.support.tomcat.connector;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;

/**
 * @author Minhchau Dang
 */
public class PortalConnector extends Connector {

	public PortalConnector(Connector connector) throws Exception {
		super(connector.getProtocol());

		_copyConnector(connector);
	}

	@Override
	public Request createRequest() {
		Request request = new PortalRequest();

		request.setConnector(this);

		return request;
	}

	private void _copyConnector(Connector connector) {
		protocolHandler = connector.getProtocolHandler();

		setAllowTrace(connector.getAllowTrace());
		setEnableLookups(connector.getEnableLookups());
		setMaxPostSize(connector.getMaxPostSize());
		setMaxSavePostSize(connector.getMaxSavePostSize());
		setPort(connector.getPort());
		setProtocol(connector.getProtocol());
		setProxyName(connector.getProxyName());
		setProxyPort(connector.getProxyPort());
		setRedirectPort(connector.getRedirectPort());
		setScheme(connector.getScheme());
		setSecure(connector.getSecure());
		setURIEncoding(connector.getURIEncoding());
		setUseBodyEncodingForURI(connector.getUseBodyEncodingForURI());
		setUseIPVHosts(connector.getUseIPVHosts());
		setXpoweredBy(connector.getXpoweredBy());
	}

}