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

import java.net.URL;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class FailureMessageUtilTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_downloadTestDependencies("test-1-12", "319");
	}

	@Test
	public void testGetFailureMessage() throws Exception {
	}

	private static void _downloadTestDependencies(
			String hostName, String buildNumber)
		throws Exception {

		if (true) {
			return;
		}

		SAXReader saxReader = new SAXReader();

		String jenkinsReportURL =
			"https://" + hostName + ".liferay.com/userContent/jobs" +
				"/test-portal-acceptance-pullrequest(master)/builds/" +
					buildNumber + "/jenkins-report.html";

		Document document = saxReader.read(new URL(jenkinsReportURL));

		System.out.println(document.asXML());
	}

}