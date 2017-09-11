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

package com.liferay.vulcan.liferay.portal.identifier;

import com.liferay.vulcan.identifier.Identifier;

/**
 * Instances of this identifier represents an identifier with a class name and a
 * <code>classPK</code>.
 *
 * <p>
 * These values can be retrieved using {@link #getClassName()} {@link
 * #getClassPK()} methods.
 * </p>
 *
 * @author Alejandro Hernández
 */
public interface ClassNameClassPKIdentifier extends Identifier {

	/**
	 * Returns the class name.
	 *
	 * @return the class name.
	 */
	public String getClassName();

	/**
	 * Returns the <code>classPK</code>.
	 *
	 * @return the <code>classPK</code>.
	 */
	public long getClassPK();

}