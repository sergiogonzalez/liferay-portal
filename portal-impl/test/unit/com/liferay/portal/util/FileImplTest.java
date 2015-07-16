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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.StringPool;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 */
public class FileImplTest {

	@Test
	public void testAppendSuffix() throws Exception {
		String fileName1 = _fileImpl.appendSuffix("test.jsp", "1");
		String fileName2 = _fileImpl.appendSuffix("test.jsp", "1111111");
		String fileName3 = _fileImpl.appendSuffix("test.jsp", "A");
		String fileName4 = _fileImpl.appendSuffix("test.jsp", "AAAAAAA");
		String fileName5 = _fileImpl.appendSuffix("test.jsp", "1!$eae1");
		String fileName6 = _fileImpl.appendSuffix("test(1).jsp", "1");

		Assert.assertEquals("test(1).jsp", fileName1);
		Assert.assertEquals("test(1111111).jsp", fileName2);
		Assert.assertEquals("test(A).jsp", fileName3);
		Assert.assertEquals("test(AAAAAAA).jsp", fileName4);
		Assert.assertEquals("test(1!$eae1).jsp", fileName5);
		Assert.assertEquals("test(1)(1).jsp", fileName6);
	}

	@Test
	public void testGetPathBackSlashForwardSlash() {
		Assert.assertEquals(
			"aaa\\bbb/ccc\\ddd",
			_fileImpl.getPath("aaa\\bbb/ccc\\ddd/eee.fff"));
	}

	@Test
	public void testGetPathForwardSlashBackSlash() {
		Assert.assertEquals(
			"aaa/bbb\\ccc/ddd", _fileImpl.getPath("aaa/bbb\\ccc/ddd\\eee.fff"));
	}

	@Test
	public void testGetPathNoPath() {
		Assert.assertEquals(StringPool.SLASH, _fileImpl.getPath("aaa.bbb"));
	}

	@Test
	public void testGetShortFileNameBackSlashForwardSlash() {
		Assert.assertEquals(
			"eee.fff", _fileImpl.getShortFileName("aaa\\bbb/ccc\\ddd/eee.fff"));
	}

	@Test
	public void testGetShortFileNameForwardSlashBackSlash() {
		Assert.assertEquals(
			"eee.fff", _fileImpl.getShortFileName("aaa/bbb\\ccc/ddd\\eee.fff"));
	}

	@Test
	public void testGetShortFileNameNoPath() {
		Assert.assertEquals("aaa.bbb", _fileImpl.getShortFileName("aaa.bbb"));
	}

	@Test
	public void testStripSuffix() throws Exception {
		String fileName1 = _fileImpl.appendSuffix("test.jsp", "1");
		String fileName2 = _fileImpl.appendSuffix("test.jsp", "1111111");
		String fileName3 = _fileImpl.appendSuffix("test.jsp", "A");
		String fileName4 = _fileImpl.appendSuffix("test.jsp", "AAAAAAA");
		String fileName5 = _fileImpl.appendSuffix("test.jsp", "1!$eae1");
		String fileName6 = _fileImpl.appendSuffix("test(1).jsp", "1");

		Assert.assertEquals("test.jsp", _fileImpl.stripSuffix(fileName1));
		Assert.assertEquals("test.jsp", _fileImpl.stripSuffix(fileName2));
		Assert.assertEquals("test.jsp", _fileImpl.stripSuffix(fileName3));
		Assert.assertEquals("test.jsp", _fileImpl.stripSuffix(fileName4));
		Assert.assertEquals("test.jsp", _fileImpl.stripSuffix(fileName5));
		Assert.assertEquals("test(1).jsp", _fileImpl.stripSuffix(fileName6));
		Assert.assertEquals("test.jsp", _fileImpl.stripSuffix("test.jsp"));
		Assert.assertEquals("()test.jsp", _fileImpl.stripSuffix("()test.jsp"));
		Assert.assertEquals("test(1.jsp", _fileImpl.stripSuffix("test(1.jsp"));
		Assert.assertEquals(
			"test)1(.jsp", _fileImpl.stripSuffix("test)1(.jsp"));
	}

	private final FileImpl _fileImpl = new FileImpl();

}