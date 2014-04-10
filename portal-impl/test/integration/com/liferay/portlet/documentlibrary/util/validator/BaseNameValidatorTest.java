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

package com.liferay.portlet.documentlibrary.util.validator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.bookmarks.FolderNameException;

import org.junit.Test;

import org.testng.Assert;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseNameValidatorTest {

	@Test(expected = FolderNameException.class)
	public void testValidationNullFails()
		throws PortalException, SystemException {

		getNameValidator().validate(null);
	}

	@Test
	public void testValidationWithBlacklistedNamesAndExtensionFails()
		throws PortalException, SystemException {

		String[] blacklist = PropsUtil.getArray(PropsKeys.DL_NAME_BLACKLIST);

		int n = _countValidationFailures("txt", blacklist);

		Assert.assertEquals(n, blacklist.length);
	}

	@Test
	public void testValidationWithBlacklistedNamesFails()
		throws PortalException, SystemException {

		String[] blacklist = PropsUtil.getArray(PropsKeys.DL_NAME_BLACKLIST);

		int n = _countValidationFailures(blacklist);

		Assert.assertEquals(n, blacklist.length);
	}

	@Test
	public void testValidationWithExtensionSucceeds()
		throws PortalException, SystemException {

		getNameValidator().validate("dir.ext");
	}

	@Test
	public void testValidationWithInvalidCharacterFails()
		throws PortalException, SystemException {

		String[] blacklistedChars = PropsUtil.getArray(
			PropsKeys.DL_CHAR_BLACKLIST);

		int n = _countValidationFailures(blacklistedChars);

		Assert.assertEquals(n, blacklistedChars.length);
	}

	@Test
	public void testValidationWithInvalidTrailingCharacterFails()
		throws PortalException, SystemException {

		String[] blacklistedChars = PropsUtil.getArray(
			PropsKeys.DL_CHAR_LAST_BLACKLIST);

		int n = _countValidationFailures(blacklistedChars);

		Assert.assertEquals(n, blacklistedChars.length);
	}

	@Test
	public void testValidationWithNoExtensionSucceeds()
		throws PortalException, SystemException {

		getNameValidator().validate("dir");
	}

	protected abstract NameValidator getNameValidator();

	private int _countValidationFailures(String postfix, String[] blacklist)
		throws PortalException, SystemException {

		int n = 0;

		for (String dirName : blacklist) {
			try {
				getNameValidator().validate(dirName + postfix);
			}
			catch (PortalException e) {
				n++;
			}
		}

		return n;
	}

	private int _countValidationFailures(String[] blacklist)
		throws PortalException, SystemException {

		return _countValidationFailures(StringPool.BLANK, blacklist);
	}

}