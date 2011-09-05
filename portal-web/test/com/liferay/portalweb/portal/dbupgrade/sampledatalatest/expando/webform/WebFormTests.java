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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.expando.webform;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WebFormTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddCommunityExpandoWebFormTest.class);
		testSuite.addTestSuite(AddPageWFTest.class);
		testSuite.addTestSuite(AddPortletWFTest.class);
		testSuite.addTestSuite(AddFormFieldRadioTest.class);
		testSuite.addTestSuite(AddFormFieldParagraphTest.class);
		testSuite.addTestSuite(AddFormFieldCheckboxTest.class);
		testSuite.addTestSuite(SendForm1Test.class);
		testSuite.addTestSuite(SendForm2Test.class);
		testSuite.addTestSuite(SendForm3Test.class);
		testSuite.addTestSuite(ViewWebFormTest.class);

		return testSuite;
	}

}