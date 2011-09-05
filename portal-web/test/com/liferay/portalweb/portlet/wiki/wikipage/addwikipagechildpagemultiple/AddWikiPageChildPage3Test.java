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

package com.liferay.portalweb.portlet.wiki.wikipage.addwikipagechildpagemultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWikiPageChildPage3Test extends BaseTestCase {
	public void testAddWikiPageChildPage3() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Wiki Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=All Pages", RuntimeVariables.replace("All Pages"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Wiki Page Title"),
			selenium.getText("//tr[6]/td[1]/a"));
		selenium.clickAt("//tr[6]/td[1]/a",
			RuntimeVariables.replace("Wiki Page Title"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Add Child Page"),
			selenium.getText("//div[7]/div[1]/span[1]/a/span"));
		selenium.clickAt("//div[7]/div[1]/span[1]/a/span",
			RuntimeVariables.replace("Add Child Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_36_title']",
			RuntimeVariables.replace("Wiki Page Child Page3 Title"));
		selenium.saveScreenShotAndSource();
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//td[@id='cke_contents__36_editor']/iframe")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.selectFrame("//td[@id='cke_contents__36_editor']/iframe");
		selenium.type("//body",
			RuntimeVariables.replace("Wiki Page Child Page3 Content"));
		selenium.selectFrame("relative=top");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Wiki Page Child Page1 Title"),
			selenium.getText("xPath=(//div[@class='child-pages']/ul/li/a)[1]"));
		assertEquals(RuntimeVariables.replace("Wiki Page Child Page2 Title"),
			selenium.getText("xPath=(//div[@class='child-pages']/ul/li/a)[2]"));
		assertEquals(RuntimeVariables.replace("Wiki Page Child Page3 Title"),
			selenium.getText("xPath=(//div[@class='child-pages']/ul/li/a)[3]"));
		selenium.clickAt("xPath=(//div[@class='child-pages']/ul/li/a)[3]",
			RuntimeVariables.replace("Wiki Page Child Page3 Title"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Wiki Page Child Page3 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Wiki Page Child Page3 Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
	}
}