/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.jspc.common;

import java.io.File;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Minhchau Dang
 */
public class TimestampUpdater {

	public static void main(String[] args) {
		if (args.length == 1) {
			new TimestampUpdater(args[0]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public TimestampUpdater(String classDir) {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(classDir);
		directoryScanner.setIncludes(new String[] {"**\\*.java"});

		directoryScanner.scan();

		String[] fileNames = directoryScanner.getIncludedFiles();

		for (String fileName : fileNames) {
			File javaFile = new File(classDir, fileName);

			String fileNameWithoutExtension = fileName.substring(
				0, fileName.length() - 5);

			String classFileName = fileNameWithoutExtension.concat(".class");

			File classFile = new File(classDir, classFileName);

			classFile.setLastModified(javaFile.lastModified());
		}
	}

}