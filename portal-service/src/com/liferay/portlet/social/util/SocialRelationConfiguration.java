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

package com.liferay.portlet.social.util;

/**
 * @author Adolfo Pérez
 */
public class SocialRelationConfiguration {

	public SocialRelationConfiguration(
		boolean isAnyUserEnabled, boolean isInteractionsEnabled,
		boolean isSameSitesEnabled, boolean isSocialRelationTypesEnabled,
		String socialRelationTypesString, int[] socialRelationTypes) {

		_anyUserEnabled = isAnyUserEnabled;
		_interactionsEnabled = isInteractionsEnabled;
		_sameSitesEnabled = isSameSitesEnabled;
		_socialRelationTypesEnabled = isSocialRelationTypesEnabled;
		_socialRelationTypes = socialRelationTypes;
		_socialRelationTypesString = socialRelationTypesString;
	}

	public int[] getSocialRelationTypes() {
		return _socialRelationTypes;
	}

	public String getSocialRelationTypesString() {
		return _socialRelationTypesString;
	}

	public boolean isAnyUserEnabled() {
		return _anyUserEnabled;
	}

	public boolean isInteractionsEnabled() {
		return _interactionsEnabled;
	}

	public boolean isSameSitesEnabled() {
		return _sameSitesEnabled;
	}

	public boolean isSocialRelationTypesEnabled() {
		return _socialRelationTypesEnabled;
	}

	private boolean _anyUserEnabled;
	private boolean _interactionsEnabled;
	private boolean _sameSitesEnabled;
	private int[] _socialRelationTypes;
	private boolean _socialRelationTypesEnabled;
	private String _socialRelationTypesString;

}