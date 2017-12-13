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

package com.liferay.css.builder;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Andrea Di Giorgi
 */
@RunWith(Parameterized.class)
public class CSSBuilderTest extends BaseCSSBuilderTestCase {

	@Parameters(name = "{0}")
	public static String[] getSeparators() {
		return new String[] {StringPool.EQUAL, StringPool.SPACE};
	}

	public CSSBuilderTest(String separator) {
		_separator = separator;
	}

	@Override
	protected void executeCSSBuilder(
			String dirName, Path docrootDirPath, boolean generateSourceMap,
			String outputDirName, Path portalCommonPath, int precision,
			String[] rtlExcludedPathRegexps, String sassCompilerClassName)
		throws Exception {

		List<String> args = new ArrayList<>();

		args.add("includes" + _separator + dirName);
		args.add("base-dir" + _separator + docrootDirPath.toAbsolutePath());
		args.add("generate-source-map" + _separator + generateSourceMap);
		args.add("output-dir" + _separator + outputDirName);
		args.add("import-dir" + _separator + portalCommonPath.toAbsolutePath());
		args.add("precision" + _separator + precision);
		args.add(
			"rtl-excluded-path-regexps" + _separator +
				StringUtil.merge(rtlExcludedPathRegexps));
		args.add("compiler" + _separator + sassCompilerClassName);

		CSSBuilder.main(args.toArray(new String[0]));
	}

	private final String _separator;

}