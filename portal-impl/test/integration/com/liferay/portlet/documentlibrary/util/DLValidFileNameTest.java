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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Everest Liu
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		EnvironmentExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLValidFileNameTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		if (_randomStrings.size() == 0) {
			for (int i = 0; i < 100; i++) {
				_randomStrings.add(StringUtil.randomString(20));
			}

			_randomStrings.add("._Word Work File D_1.tmp");
			_randomStrings.add("._Test.docx");
		}
	}

	@Test
	public void testNameBlacklist() throws Exception {
		for (String blacklistName : PropsValues.DL_NAME_BLACKLIST) {
			Assert.assertFalse(
				blacklistName, DLUtil.isValidName(blacklistName));
		}
	}

	@Test
	public void testNameBlacklistExtension() throws Exception {
		String testName;

		for (String blacklistName : PropsValues.DL_NAME_BLACKLIST) {
			testName = blacklistName + ".txt";
			Assert.assertFalse(testName, DLUtil.isValidName(testName));
		}
	}

	@Test
	public void testPeriodAtEnd() throws Exception {
		for (String randomString : _randomStrings) {
			randomString += "1.";

			Assert.assertFalse(randomString, DLUtil.isValidName(randomString));
		}
	}

	@Test
	public void testRandomStrings() throws Exception {
		for (String randomString : _randomStrings) {
			Assert.assertTrue(randomString, DLUtil.isValidName(randomString));
		}

		Assert.assertFalse(
			StringPool.BLANK, DLUtil.isValidName(StringPool.BLANK));
	}

	@Test
	public void testRandomStringsWithBlacklistedChar() throws Exception {
		String testName;

		for (String randomString : _randomStrings) {
			for (String blacklistChar : BLACKLIST_CHARS) {
				StringBuilder sb = new StringBuilder();

				sb.append(randomString);
				sb.insert(randomString.length() / 2, blacklistChar);

				testName = sb.toString();

				Assert.assertFalse(testName, DLUtil.isValidName(testName));

				StringBuilder sb2 = new StringBuilder();

				sb2.append(randomString);
				sb2.append(blacklistChar);
				sb2.append(randomString);
				sb2.append(".txt");

				testName = sb2.toString();

				Assert.assertFalse(testName, DLUtil.isValidName(testName));
			}
		}
	}

	@Test
	public void testSpaceAtEnd() throws Exception {
		String testName;

		for (String randomString : _randomStrings) {
			testName = randomString + " ";

			Assert.assertFalse(
				testName + "[space]", DLUtil.isValidName(testName));
		}
	}

	@Test
	public void testValidNames() throws Exception {
		String testName;

		for (String blacklistName : PropsValues.DL_NAME_BLACKLIST) {
			testName = blacklistName + "1";
			Assert.assertTrue(testName, DLUtil.isValidName(testName));

			testName = blacklistName + " .txt";
			Assert.assertTrue(testName, DLUtil.isValidName(testName));
		}
	}

	private static final String[] BLACKLIST_CHARS = new String[] {
		"<", ">", ":", "\"", "\\", "|", "?", "*", "\u0000", "\u0001", "\u0002",
		"\u0003", "\u0004", "\u0005", "\u0006", "\u0007", "\u0008", "\u0009",
		"\u000B", "\u000C", "\u000E", "\u000F", "\u0010", "\u0011", "\u0012",
		"\u0013", "\u0014", "\u0015", "\u0016", "\u0017", "\u0018", "\u0019",
		"\u001A", "\u001B", "\u001C", "\u001D", "\u001E", "\u001F"};

	private static List<String> _randomStrings = new ArrayList<String>();

}