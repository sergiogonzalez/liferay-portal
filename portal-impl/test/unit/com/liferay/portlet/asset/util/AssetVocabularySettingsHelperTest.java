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

package com.liferay.portlet.asset.util;

import com.liferay.portlet.asset.model.AssetCategoryConstants;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author José Manuel Navarro
 */
public class AssetVocabularySettingsHelperTest {

	@Test
	public void testHasClassNameId() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(
				true, AssetCategoryConstants.ALL_CLASS_NAME_IDS, true);

		Assert.assertTrue(vocabularySettingsHelper.hasClassNameId(1));
		Assert.assertTrue(vocabularySettingsHelper.hasClassNameId(2));

		vocabularySettingsHelper = getVocabularySettingsHelper(true, 1, true);

		Assert.assertTrue(vocabularySettingsHelper.hasClassNameId(1));
		Assert.assertFalse(vocabularySettingsHelper.hasClassNameId(2));

		vocabularySettingsHelper = getVocabularySettingsHelper(
			true, AssetCategoryConstants.ALL_CLASS_NAME_IDS, false);

		Assert.assertFalse(vocabularySettingsHelper.isClassNameIdRequired(1));
		Assert.assertFalse(vocabularySettingsHelper.isClassNameIdRequired(2));

		vocabularySettingsHelper = getVocabularySettingsHelper(
			true, AssetCategoryConstants.ALL_CLASS_NAME_IDS, true);

		Assert.assertTrue(vocabularySettingsHelper.isClassNameIdRequired(1));
		Assert.assertTrue(vocabularySettingsHelper.isClassNameIdRequired(2));

		vocabularySettingsHelper = getVocabularySettingsHelper(true, 1, false);

		Assert.assertFalse(vocabularySettingsHelper.isClassNameIdRequired(1));
		Assert.assertFalse(vocabularySettingsHelper.isClassNameIdRequired(2));

		vocabularySettingsHelper = getVocabularySettingsHelper(true, 1, true);

		Assert.assertTrue(vocabularySettingsHelper.isClassNameIdRequired(1));
		Assert.assertFalse(vocabularySettingsHelper.isClassNameIdRequired(2));

		vocabularySettingsHelper = getVocabularySettingsHelper(true, 1, true);

		vocabularySettingsHelper.setClassNameIds(
			new long[] {1}, new boolean[] {false});

		Assert.assertFalse(vocabularySettingsHelper.isClassNameIdRequired(1));
	}

	@Test
	public void testIsMultiValued() {
		AssetVocabularySettingsHelper vocabularySettingsHelper =
			getVocabularySettingsHelper(false, 1, true);

		Assert.assertFalse(vocabularySettingsHelper.isMultiValued());

		vocabularySettingsHelper = getVocabularySettingsHelper(true, 1, true);

		Assert.assertTrue(vocabularySettingsHelper.isMultiValued());
	}

	protected AssetVocabularySettingsHelper getVocabularySettingsHelper(
		boolean multiValued, long classNameId, boolean required) {

		long[] classNameIds = {classNameId};
		boolean[] requireds = {required};

		return getVocabularySettingsHelper(
			multiValued, classNameIds, requireds);
	}

	protected AssetVocabularySettingsHelper getVocabularySettingsHelper(
		boolean multiValued, long[] classNameIds, boolean[] requireds) {

		AssetVocabularySettingsHelper vocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		vocabularySettingsHelper.setClassNameIds(classNameIds, requireds);
		vocabularySettingsHelper.setMultiValued(multiValued);

		return vocabularySettingsHelper;
	}

}