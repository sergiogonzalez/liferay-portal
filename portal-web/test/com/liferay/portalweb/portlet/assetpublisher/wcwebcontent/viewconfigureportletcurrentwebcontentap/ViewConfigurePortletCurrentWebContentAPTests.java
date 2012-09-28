/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.assetpublisher.wcwebcontent.viewconfigureportletcurrentwebcontentap;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontent.TearDownWCWebContentTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletap.AddPageAPTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletap.AddPortletAPTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletavailablewebcontent.ConfigurePortletAvailableWebContentTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletcurrentwebcontent.ConfigurePortletCurrentWebContentTest;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontent.addnewwcwebcontentapactions.AddNewWCWebContentAPActionsTest;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontent.viewconfigureportletavailablewebcontentap.ViewConfigurePortletAvailableWebContentAPTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewConfigurePortletCurrentWebContentAPTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageAPTest.class);
		testSuite.addTestSuite(AddPortletAPTest.class);
		testSuite.addTestSuite(AddNewWCWebContentAPActionsTest.class);
		testSuite.addTestSuite(ConfigurePortletAvailableWebContentTest.class);
		testSuite.addTestSuite(ViewConfigurePortletAvailableWebContentAPTest.class);
		testSuite.addTestSuite(ConfigurePortletCurrentWebContentTest.class);
		testSuite.addTestSuite(ViewConfigurePortletCurrentWebContentAPTest.class);
		testSuite.addTestSuite(TearDownWCWebContentTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}