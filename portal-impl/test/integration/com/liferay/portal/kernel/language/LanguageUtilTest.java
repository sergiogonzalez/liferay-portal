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

package com.liferay.portal.kernel.language;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díazu
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class LanguageUtilTest {

	@Test
	public void testFormatWithOneArgument() {
		Assert.assertEquals(
			"Choisir ce/cette Texte",
			LanguageUtil.format(Locale.FRANCE, "choose-this-x", "test"));
	}

	@Test
	public void testFormatWithOneArgumentAndNonExistentKey() {
		Assert.assertEquals(
			"non-existent-language-key",
			LanguageUtil.format(Locale.FRANCE, "non-existent-language-key"));
	}

	@Test
	public void testFormatWithOneNonTranslatedArgument() {
		Assert.assertEquals(
			"Choisir ce/cette test",
			LanguageUtil.format(Locale.FRANCE, "choose-this-x", "test", false));
	}

	@Test
	public void testFormatWithOneNonTranslatedArgumentAndNonExistentKey() {
		Assert.assertEquals(
			"non-existent-language-key",
			LanguageUtil.format(
					Locale.FRANCE, "non-existent-language-key", "test", false));
	}

	@Test
	public void testFormatWithTwoArguments() {
		Assert.assertEquals(
			"Comparaison des versions Texte et Texte",
			LanguageUtil.format(
					Locale.FRANCE, "comparing-versions-x-and-x",
					new String[] {"test", "test"}));
	}

	@Test
	public void testFormatWithTwoArgumentsAndNonExistentKey() {
		Assert.assertEquals(
			"non-existent-language-key",
			LanguageUtil.format(
					Locale.FRANCE, "non-existent-language-key",
					new String[] {"test", "test"}));
	}

	@Test
	public void testFormatWithTwoNonTranslatedArguments() {
		Assert.assertEquals(
			"Comparaison des versions test et test",
			LanguageUtil.format(
					Locale.FRANCE, "comparing-versions-x-and-x",
					new String[] {"test", "test"}, false));
	}

	@Test
	public void testFormatWithTwoNonTranslatedArgumentsAndNonExistentKey() {
		Assert.assertEquals(
			"non-existent-language-key",
			LanguageUtil.format(
					Locale.FRANCE, "non-existent-language-key",
					new String[]{"test", "test"}, false));
	}

	@Test
	public void testGet() {
		Assert.assertEquals("Texte", LanguageUtil.get(Locale.FRANCE, "test"));
	}

	@Test
	public void testGetWithNonExistentKey() {
		Assert.assertEquals(
			"Default Value",
			LanguageUtil.get(
				Locale.FRANCE, "non-existent-language-key", "Default Value"));
	}

}