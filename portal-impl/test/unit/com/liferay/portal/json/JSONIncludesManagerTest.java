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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONFactoryUtil;

import junit.framework.TestCase;

/**
 * @author Igor Spasic
 */
public class JSONIncludesManagerTest extends TestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		new JSONFactoryUtil().setJSONFactory(new JSONFactoryImpl());
	}

	public void testExtendsOne() {
		JSONIncludesManager jsonIncludesManager = new JSONIncludesManager();

		String[] excludes = jsonIncludesManager.lookupExcludes(
			ExtendsOne.class);

		assertEquals(1, excludes.length);
		assertEquals("*", excludes[0]);

		String[] includes = jsonIncludesManager.lookupIncludes(
			ExtendsOne.class);

		assertEquals(1, includes.length);
		assertEquals("ftwo", includes[0]);
	}

	public void testExtendsTwo() {
		JSONIncludesManager jsonIncludesManager = new JSONIncludesManager();

		String[] excludes = jsonIncludesManager.lookupExcludes(
			ExtendsTwo.class);

		assertEquals(1, excludes.length);
		assertEquals("*", excludes[0]);

		String[] includes = jsonIncludesManager.lookupIncludes(
			ExtendsTwo.class);

		assertEquals(1, includes.length);
		assertEquals("ftwo", includes[0]);
	}

	public void testOne() {
		JSONIncludesManager jsonIncludesManager = new JSONIncludesManager();

		String[] excludes = jsonIncludesManager.lookupExcludes(One.class);

		assertEquals(1, excludes.length);
		assertEquals("not", excludes[0]);

		String[] includes = jsonIncludesManager.lookupIncludes(One.class);

		assertEquals(1, includes.length);
		assertEquals("ftwo", includes[0]);
	}

	public void testThree() {
		JSONIncludesManager jsonIncludesManager = new JSONIncludesManager();

		String[] excludes = jsonIncludesManager.lookupExcludes(Three.class);

		assertEquals(1, excludes.length);
		assertEquals("ignore", excludes[0]);

		String[] includes = jsonIncludesManager.lookupIncludes(Three.class);

		assertEquals(0, includes.length);
	}

	public void testTwo() {
		JSONIncludesManager jsonIncludesManager = new JSONIncludesManager();

		String[] excludes = jsonIncludesManager.lookupExcludes(Two.class);

		assertEquals(1, excludes.length);
		assertEquals("*", excludes[0]);

		String[] includes = jsonIncludesManager.lookupIncludes(Two.class);

		assertEquals(1, includes.length);
		assertEquals("ftwo", includes[0]);
	}

}