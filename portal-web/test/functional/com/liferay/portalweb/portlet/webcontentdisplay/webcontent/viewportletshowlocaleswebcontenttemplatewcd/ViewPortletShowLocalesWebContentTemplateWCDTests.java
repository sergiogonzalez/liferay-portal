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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewportletshowlocaleswebcontenttemplatewcd;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructure.TearDownWCStructureTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructure.TearDownWCTemplateStructureTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcstructurewebcontentlocalized.AddStructureLocalizedTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcstructurewebcontentlocalized.AddTemplateLocalizedTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontent.TearDownWCWebContentTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.language.AddPageLanguageTest;
import com.liferay.portalweb.portlet.language.AddPortletLanguageTest;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.addportletwcd.AddPageWCDTest;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.addportletwcd.AddPortletWCDTest;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletenablecommentratings.TearDownPortletSetupTest;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletshowavailablelocales.ConfigurePortletShowAvailableLocalesTest;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.localizewcwebcontenttemplatewcd.AddWCWebContentTemplateWCDTest;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.localizewcwebcontenttemplatewcd.LocalizeWCWebContentTemplateWCDTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletShowLocalesWebContentTemplateWCDTests
	extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageWCDTest.class);
		testSuite.addTestSuite(AddPortletWCDTest.class);
		testSuite.addTestSuite(AddPageLanguageTest.class);
		testSuite.addTestSuite(AddPortletLanguageTest.class);
		testSuite.addTestSuite(ConfigurePortletShowAvailableLocalesTest.class);
		testSuite.addTestSuite(AddStructureLocalizedTest.class);
		testSuite.addTestSuite(AddTemplateLocalizedTest.class);
		testSuite.addTestSuite(AddWCWebContentTemplateWCDTest.class);
		testSuite.addTestSuite(LocalizeWCWebContentTemplateWCDTest.class);
		testSuite.addTestSuite(ViewPortletShowLocalesWebContentTemplateWCDTest.class);
		testSuite.addTestSuite(TearDownLanguageTest.class);
		testSuite.addTestSuite(TearDownPortletSetupTest.class);
		testSuite.addTestSuite(TearDownWCWebContentTest.class);
		testSuite.addTestSuite(TearDownWCTemplateStructureTest.class);
		testSuite.addTestSuite(TearDownWCStructureTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}