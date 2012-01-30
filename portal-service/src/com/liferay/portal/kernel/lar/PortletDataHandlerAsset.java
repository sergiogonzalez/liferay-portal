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

package com.liferay.portal.kernel.lar;

/**
 * @author Raymond Augé
 */
public class PortletDataHandlerAsset extends PortletDataHandlerBoolean {

	public PortletDataHandlerAsset(String namespace, String controlName) {
		super(namespace, controlName);
	}

	public PortletDataHandlerAsset(
		String namespace, String controlName, boolean defaultState) {

		super(namespace, controlName, defaultState);
	}

	public PortletDataHandlerAsset(
		String namespace, String controlName, boolean defaultState,
		boolean disabled) {

		super(namespace, controlName, defaultState, disabled);
	}

	public PortletDataHandlerAsset(
		String namespace, String controlName, boolean defaultState,
		PortletDataHandlerControl[] children) {

		super(namespace, controlName, defaultState, children);
	}

	public PortletDataHandlerAsset(
		String namespace, String controlName, boolean defaultState,
		boolean disabled, PortletDataHandlerControl[] children) {

		super(namespace, controlName, defaultState, disabled, children);
	}

}