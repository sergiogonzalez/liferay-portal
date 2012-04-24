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

package com.liferay.portalweb.portal.dbupgrade.sampledata610.tags.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchTagsTest extends BaseTestCase {
	public void testSearchTags() throws Exception {
		selenium.open("/web/tags-message-board-community/");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Message Boards Page",
			RuntimeVariables.replace("Message Boards Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_19_keywords1']",
			RuntimeVariables.replace("selenium1"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isVisible("link=Message1 Tag1 Test1"));
		assertFalse(selenium.isElementPresent("link=Message2 Tag2 Test2"));
		assertFalse(selenium.isElementPresent("link=Message3 Tag3 Test3"));
		assertFalse(selenium.isElementPresent("link=MessageA TagA TestA"));
		assertFalse(selenium.isElementPresent("link=MessageB TagB TestB"));
		assertFalse(selenium.isElementPresent("link=MessageC TagC TestC"));
		selenium.open("/web/tags-message-board-community/");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Message Boards Page",
			RuntimeVariables.replace("Message Boards Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_19_keywords1']",
			RuntimeVariables.replace("selenium2"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isVisible("link=Message2 Tag2 Test2"));
		assertFalse(selenium.isElementPresent("link=Message1 Tag1 Test1"));
		assertFalse(selenium.isElementPresent("link=Message3 Tag3 Test3"));
		assertFalse(selenium.isElementPresent("link=MessageA TagA TestA"));
		assertFalse(selenium.isElementPresent("link=MessageB TagB TestB"));
		assertFalse(selenium.isElementPresent("link=MessageC TagC TestC"));
		selenium.open("/web/tags-message-board-community/");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Message Boards Page",
			RuntimeVariables.replace("Message Boards Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_19_keywords1']",
			RuntimeVariables.replace("selenium3"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isVisible("link=Message3 Tag3 Test3"));
		assertFalse(selenium.isElementPresent("link=Message1 Tag1 Test1"));
		assertFalse(selenium.isElementPresent("link=Message2 Tag2 Test2"));
		assertFalse(selenium.isElementPresent("link=MessageA TagA TestA"));
		assertFalse(selenium.isElementPresent("link=MessageB TagB TestB"));
		assertFalse(selenium.isElementPresent("link=MessageC TagC TestC"));
		selenium.open("/web/tags-message-board-community/");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Message Boards Page",
			RuntimeVariables.replace("Message Boards Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_19_keywords1']",
			RuntimeVariables.replace("selenium"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isVisible("link=MessageA TagA TestA"));
		assertTrue(selenium.isVisible("link=MessageB TagB TestB"));
		assertTrue(selenium.isVisible("link=MessageC TagC TestC"));
		assertFalse(selenium.isElementPresent("link=Message1 Tag1 Test1"));
		assertFalse(selenium.isElementPresent("link=Message2 Tag2 Test2"));
		assertFalse(selenium.isElementPresent("link=Message3 Tag3 Test3"));
	}
}