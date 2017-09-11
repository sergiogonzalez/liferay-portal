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

package com.liferay.vulcan.liferay.portal.identifier.creator;

import com.liferay.vulcan.liferay.portal.identifier.ClassNameClassPKIdentifier;

/**
 * This creator can be used to create a new instance of a {@link
 * ClassNameClassPKIdentifier} from a combination of className/classPK;
 *
 * @author Alejandro Hernández
 */
public interface ClassNameClassPKIdentifierCreator {

	/**
	 * Returns a new {@code ClassNameClassPKIdentifier} from a combination of
	 * className/classPK.
	 *
	 * @param  className the className of the identifier.
	 * @param  classPK the classPK of the identifier.
	 * @return the {@code ClassNameClassPKIdentifier}.
	 */
	public ClassNameClassPKIdentifier create(String className, long classPK);

}