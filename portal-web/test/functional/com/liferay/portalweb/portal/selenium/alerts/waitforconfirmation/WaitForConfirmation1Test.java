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

package com.liferay.portalweb.portal.selenium.alerts.waitforconfirmation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class WaitForConfirmation1Test extends BaseTestCase {
	public void testWaitForConfirmation1() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//li[@id='_145_addContent']/a/span"));
		selenium.mouseOver("//li[@id='_145_addContent']/a/span");
		selenium.waitForVisible("//a[@id='addPage']");
		assertEquals(RuntimeVariables.replace("Page"),
			selenium.getText("//a[@id='addPage']"));
		selenium.clickAt("//a[@id='addPage']", RuntimeVariables.replace("Page"));
		selenium.waitForVisible("//input[@type='text']");
		selenium.type("//input[@type='text']",
			RuntimeVariables.replace("Test Page1"));
		selenium.clickAt("//button[contains(@id,'Save')]",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("link=Test Page1");
		selenium.clickAt("link=Test Page1",
			RuntimeVariables.replace("Test Page1"));
		selenium.waitForPageToLoad("30000");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//nav[@id='navigation']",
			RuntimeVariables.replace("Navigation"));
		selenium.waitForVisible(
			"xPath=(//li[contains(@class,'lfr-nav-deletable')])[1]/a/span");
		selenium.mouseOver(
			"xPath=(//li[contains(@class,'lfr-nav-deletable')])[2]/a/span");
		selenium.waitForVisible(
			"xPath=(//li[contains(@class,'lfr-nav-deletable')])[2]/span[@class='delete-tab']");
		assertEquals(RuntimeVariables.replace("X"),
			selenium.getText(
				"xPath=(//li[contains(@class,'lfr-nav-deletable')])[2]/span[@class='delete-tab']"));
		selenium.click(
			"xPath=(//li[contains(@class,'lfr-nav-deletable')])[2]/span[@class='delete-tab']");
		selenium.waitForConfirmation(
			"Are you sure you want to delete this page?");
		selenium.waitForElementNotPresent(
			"xPath=(//li[contains(@class,'lfr-nav-deletable')])[2]/span[@class='delete-tab']");
	}
}