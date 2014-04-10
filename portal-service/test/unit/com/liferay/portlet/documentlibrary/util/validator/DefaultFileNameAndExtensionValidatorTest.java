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
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileNameException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.Returns;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Adolfo Pérez
 */
@PrepareForTest({PropsUtil.class})
@RunWith(PowerMockRunner.class)
public class DefaultFileNameAndExtensionValidatorTest extends PowerMockito {

	@Before
	public void setUp() {
		mockStatic(
			PropsUtil.class, new Returns(new String[] { StringPool.STAR }));

		_fileNameValidator = mock(FileNameValidator.class);
		_fileNameAndExtensionValidator =
			new DefaultFileNameAndExtensionValidator(_fileNameValidator);
	}

	@Test(expected = FileExtensionException.class)
	public void testInvalidExtensionFails()
		throws PortalException, SystemException {

		when(
			PropsUtil.getArray(PropsKeys.DL_FILE_EXTENSIONS)
		).thenReturn(
			new String[0]
		);

		_fileNameAndExtensionValidator.validate("invalid.ext");

		Mockito.verify(_fileNameValidator).validate("invalid.ext");
		verifyStatic();
	}

	@Test(expected = FileNameException.class)
	public void testInvalidFileNameFails()
		throws PortalException, SystemException {

		doThrow(
			new FileNameException()
		).when(
			_fileNameValidator
		).validate(
			"invalid.ext"
		);

		_fileNameValidator.validate("invalid.ext");

		verifyStatic();
	}

	@Test
	public void testValidFileNameSucceeds()
		throws PortalException, SystemException {

		_fileNameAndExtensionValidator.validate("valid.ext");

		Mockito.verify(_fileNameValidator).validate("valid.ext");
		verifyStatic();
	}

	private FileNameValidator _fileNameAndExtensionValidator;
	private FileNameValidator _fileNameValidator;

}