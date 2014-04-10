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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.SourceFileNameException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.internal.stubbing.answers.Returns;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Adolfo Pérez
 */
@PrepareForTest({FileUtil.class, PropsUtil.class, ModelHintsUtil.class})
@RunWith(PowerMockRunner.class)
public class DefaultExtensionValidatorTest extends PowerMockito {

	@Before
	public void setUp() {
		mockStatic(FileUtil.class, PropsUtil.class);
		mockStatic(ModelHintsUtil.class, new Returns(Integer.MAX_VALUE));

		when(
			FileUtil.getExtension("valid.ext")
		).thenReturn(
			"ext"
		);

		when(
			FileUtil.getExtension("wrong.xyz")
		).thenReturn(
			"xyz"
		);

		when(
			PropsUtil.get(PropsKeys.DL_FILE_EXTENSIONS_STRICT_CHECK)
		).thenReturn(
			"true"
		);
	}

	@Test(expected = FileExtensionException.class)
	public void testExtensionLongerThanMaxLengthFails()
		throws PortalException, SystemException {

		when(
			ModelHintsUtil.getMaxLength(
				DLFileEntry.class.getName(), "extension")
		).thenReturn(
			1
		);

		_fileExtensionValidator.validateExtension("wrong", "ext", null);
	}

	@Test
	public void testValidExtensionSucceeds()
		throws PortalException, SystemException {

		_fileExtensionValidator.validateExtension("valid", "ext", "valid.ext");
	}

	@Test
	public void testWithNoStrictCheckingExtensionsMayNotMatch()
		throws PortalException, SystemException {

		when(
			PropsUtil.get(PropsKeys.DL_FILE_EXTENSIONS_STRICT_CHECK)
		).thenReturn(
			"false"
		);

		_fileExtensionValidator.validateExtension("valid", "xyz", "valid.ext");
	}

	@Test(expected = SourceFileNameException.class)
	public void testWithStrictCheckingExtensionsShouldMatch()
		throws PortalException, SystemException {

		_fileExtensionValidator.validateExtension("wrong", "ext", "wrong.xyz");
	}

	private final FileExtensionValidator _fileExtensionValidator =
		new DefaultExtensionValidator();

}