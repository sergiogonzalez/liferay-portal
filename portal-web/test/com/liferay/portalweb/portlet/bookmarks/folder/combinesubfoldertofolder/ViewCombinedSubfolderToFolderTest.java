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

package com.liferay.portalweb.portlet.bookmarks.folder.combinesubfoldertofolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewCombinedSubfolderToFolderTest extends BaseTestCase {
	public void testViewCombinedSubfolderToFolder() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Bookmarks Test Page",
			RuntimeVariables.replace("Bookmarks Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Bookmark Folder Name"),
			selenium.getText(
				"//tr[contains(.,'Bookmark Folder Name')]/td[1]/a/strong"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[contains(.,'Bookmark Folder Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//tr[contains(.,'Bookmark Folder Name')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Bookmark Folder Name')]/td[4]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'Bookmark Folder Name')]/td[1]/a/strong",
			RuntimeVariables.replace("Bookmark Folder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Bookmark Folder Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Bookmark Name"),
			selenium.getText("//tr[contains(.,'Bookmark Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("http://www.liferay.com"),
			selenium.getText("//tr[contains(.,'Bookmark Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[contains(.,'Bookmark Name')]/td[3]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Bookmark Name')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Bookmark Name')]/td[5]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'Bookmark Name')]/td[1]/a",
			RuntimeVariables.replace("Bookmark Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span/a/span[contains(.,'Home')]"));
		assertEquals(RuntimeVariables.replace("Recent"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span/a/span[contains(.,'Recent')]"));
		assertEquals(RuntimeVariables.replace("Mine"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span/a/span[contains(.,'Mine')]"));
		assertTrue(selenium.isVisible("//input[@title='Search Bookmarks']"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertEquals(RuntimeVariables.replace("Bookmark Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']"));
		assertEquals(RuntimeVariables.replace("http://www.liferay.com"),
			selenium.getText("//div[@class='lfr-asset-url']/a"));
		assertEquals(RuntimeVariables.replace("Created by Joe Bloggs"),
			selenium.getText("//div[@class='lfr-asset-icon lfr-asset-author']"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-asset-icon lfr-asset-date']"));
		assertEquals(RuntimeVariables.replace("0 Visits"),
			selenium.getText(
				"//div[@class='lfr-asset-icon lfr-asset-downloads last']"));
		assertTrue(selenium.isPartialText(
				"//div[contains(@id,'ratingStarContent')]", "Your Rating"));
		assertEquals(RuntimeVariables.replace("Average (0 Votes)"),
			selenium.getText("//div[contains(@id,'ratingScoreContent')]"));
		assertTrue(selenium.isVisible("//span[@class='lfr-asset-avatar']/a/img"));
		assertEquals(RuntimeVariables.replace("Bookmark Name"),
			selenium.getText("//div[@class='lfr-asset-name']/a"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Edit')]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Permissions')]"));
		assertEquals(RuntimeVariables.replace("Subscribe"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Subscribe')]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Delete')]"));
	}
}