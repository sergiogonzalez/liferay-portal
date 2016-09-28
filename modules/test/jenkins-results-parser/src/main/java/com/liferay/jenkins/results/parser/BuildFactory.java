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

package com.liferay.jenkins.results.parser;

/**
 * @author Peter Yoo
 */
public class BuildFactory {

	public static Build newBuild(String url, Build parentBuild)
		throws Exception {

		url = JenkinsResultsParserUtil.getLocalURL(url);

		if (url.contains("AXIS_VARIABLE=")) {
			return new AxisBuild(url, (BatchBuild)parentBuild);
		}

		if (url.contains("-source")) {
			return new SourceBuild(url, parentBuild);
		}

		for (String batchIndicator : _BATCH_INDICATORS) {
			if (url.contains(batchIndicator)) {
				return new BatchBuild(url, (TopLevelBuild)parentBuild);
			}
		}

		return new TopLevelBuild(url, (TopLevelBuild)parentBuild);
	}

	private static final String[] _BATCH_INDICATORS = {"-batch", "-dist"};

}