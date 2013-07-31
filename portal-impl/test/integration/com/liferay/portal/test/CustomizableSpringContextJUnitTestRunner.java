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

import com.liferay.portal.util.InitUtil;
import org.junit.runners.model.InitializationError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Pastor
 */
public abstract class CustomizableSpringContextJUnitTestRunner
	extends LiferayIntegrationJUnitTestRunner {

	public CustomizableSpringContextJUnitTestRunner(Class<?> clazz)
		throws InitializationError {

		super(clazz);
	}

	public abstract List<String> getExtraConfigLocations();

	public abstract void doAfterApplicationContextInit();

	@Override
	public void initApplicationContext() {
		System.setProperty("catalina.base", ".");

		InitUtil.initWithSpring(getExtraConfigLocations());
	}

}