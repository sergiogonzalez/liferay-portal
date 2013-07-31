/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.test;

import org.junit.runners.model.InitializationError;

import java.util.Arrays;
import java.util.List;

/**
 * @author Miguel Pastor
 */
public class NullableThreadLocalCacheJUnitTestRunner
	extends CustomizableSpringContextJUnitTestRunner {

	public NullableThreadLocalCacheJUnitTestRunner(
		Class<?> clazz)
		throws InitializationError {

		super(clazz);
	}

	@Override
	public List<String> getExtraConfigLocations() {
		return Arrays.asList("META-INF/test-thread-local-spring.xml");
	}

	@Override
	public void doAfterApplicationContextInit() {
	}

}