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

package com.liferay.portalweb.kaleo.pagecomments.comment.viewcommentcompleted;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ApproveCommentActionsTest extends BaseTestCase {
	public void testApproveCommentActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=My Workflow Tasks"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Review"),
			selenium.getText("//tr[contains(.,'PC Comment')]/td[1]/a"));
		assertTrue(selenium.isPartialText(
				"//tr[contains(.,'PC Comment')]/td[2]/a", "PC Comment"));
		assertEquals(RuntimeVariables.replace("Comments"),
			selenium.getText("//tr[contains(.,'PC Comment')]/td[3]/a"));
		assertTrue(selenium.isVisible("//tr[contains(.,'PC Comment')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//tr[contains(.,'PC Comment')]/td[5]/a"));
		assertEquals(RuntimeVariables.replace(
				"There are no pending tasks assigned to your roles."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Approve')]");
		assertEquals(RuntimeVariables.replace("Approve"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Approve')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Approve')]",
			RuntimeVariables.replace("Approve"));
		selenium.waitForVisible("//button[.='OK']");
		assertEquals(RuntimeVariables.replace("OK"),
			selenium.getText("//button[.='OK']"));
		selenium.clickAt("//button[.='OK']", RuntimeVariables.replace("OK"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"There are no pending tasks assigned to you."),
			selenium.getText("xPath=(//div[@class='portlet-msg-info'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"There are no pending tasks assigned to your roles."),
			selenium.getText("xPath=(//div[@class='portlet-msg-info'])[3]"));
		selenium.clickAt("link=Completed", RuntimeVariables.replace("Completed"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Review"),
			selenium.getText("//tr[contains(.,'PC Comment')]/td[1]/a"));
		assertTrue(selenium.isPartialText(
				"//tr[contains(.,'PC Comment')]/td[2]/a", "PC Comment"));
		assertEquals(RuntimeVariables.replace("Comments"),
			selenium.getText("//tr[contains(.,'PC Comment')]/td[3]/a"));
		assertTrue(selenium.isVisible("//tr[contains(.,'PC Comment')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//tr[contains(.,'PC Comment')]/td[5]/a"));
	}
}