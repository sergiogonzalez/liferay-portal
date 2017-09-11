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

package com.liferay.vulcan.application.internal.identifier;

import com.liferay.vulcan.identifier.Identifier;

/**
 * @author Alejandro Hernández
 */
public class IdentifierImpl implements Identifier {

	public IdentifierImpl(String path, String id) {
		_type = path;
		_id = id;
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public String getType() {
		return _type;
	}

	private final String _id;
	private final String _type;

}