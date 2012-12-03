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
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.search.HitsImpl;

import junit.framework.TestCase;

/**
 * Tests for classes with non-default serialization.
 *
 * @author Igor Spasic
 */
public class JSONSerializationTest extends TestCase {

	@Override
	public void setUp() throws Exception {
		JSONInit.init();

		new JSONFactoryUtil().setJSONFactory(new JSONFactoryImpl());
	}

	public void testHitsImpl() {
		HitsImpl hits = new HitsImpl();

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		String jsonString = jsonSerializer.serialize(hits);

		System.out.println(jsonString);

		assertTrue(jsonString.contains("\"docs\":null"));
		assertTrue(jsonString.contains("\"scores\":[]"));
		assertTrue(jsonString.contains("\"snippets\":[]"));
		assertTrue(jsonString.contains("\"queryTerms\":null"));
		assertTrue(jsonString.contains("\"start\":0"));
		assertTrue(jsonString.contains("\"length\":0"));

		assertFalse(jsonString.contains("\"query\""));
	}

}