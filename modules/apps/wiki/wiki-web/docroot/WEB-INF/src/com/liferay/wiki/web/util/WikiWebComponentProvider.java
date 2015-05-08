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

package com.liferay.wiki.web.util;

import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;

import com.liferay.wiki.web.display.context.WikiDisplayContextProvider;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true
)
public class WikiWebComponentProvider {

	private WikiDisplayContextProvider _wikiDisplayContextProvider;

	public static WikiWebComponentProvider getWikiWebComponentProvider() {
		return _wikiWebComponentProvider;
	}

	public SettingsFactory getSettingsFactory() {
		return _settingsFactory;
	}

	public WikiGroupServiceConfiguration getWikiGroupServiceConfiguration() {
		return _wikiGroupServiceConfiguration;
	}

	public WikiDisplayContextProvider getWikiDisplayContextProvider() {
		return _wikiDisplayContextProvider;
	}

	@Activate
	protected void activate() {
		_wikiWebComponentProvider = this;
	}

	@Deactivate
	protected void deactivate() {
		_wikiWebComponentProvider = null;
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	@Reference
	protected void setWikiGroupServiceConfiguration(
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiGroupServiceConfiguration = wikiGroupServiceConfiguration;
	}

	protected void unsetWikiGroupServiceConfiguration(
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiGroupServiceConfiguration = null;
	}

	@Reference(unbind = "-")
	public void setWikiDisplayContextProvider(
		WikiDisplayContextProvider wikiDisplayContextProvider) {

		_wikiDisplayContextProvider = wikiDisplayContextProvider;
	}

	private static WikiWebComponentProvider _wikiWebComponentProvider;

	private SettingsFactory _settingsFactory;
	private WikiGroupServiceConfiguration _wikiGroupServiceConfiguration;

}