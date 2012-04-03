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

package com.liferay.portalweb.properties.calendar.eventratings.viewcalendareventratingsno;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewCalendarEventRatingsNoTest extends BaseTestCase {
	public void testViewCalendarEventRatingsNo() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Calendar Test Page",
			RuntimeVariables.replace("Calendar Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Test Event"),
			selenium.getText(
				"//table[contains(@data-searchcontainerid,'SearchContainer')]/tbody/tr[3]/td[2]/a"));
		selenium.clickAt("//table[contains(@data-searchcontainerid,'SearchContainer')]/tbody/tr[3]/td[2]/a",
			RuntimeVariables.replace("Test Event"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertFalse(selenium.isTextPresent("Your Rating"));
		assertFalse(selenium.isTextPresent("Average (0 Votes)"));
	}
}