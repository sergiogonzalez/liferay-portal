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

package com.liferay.portalweb.socialofficeprofile.profile.addusersoaddress;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserSOAddressTest extends BaseTestCase {
	public void testAddUserSOAddress() throws Exception {
		selenium.open("/user/joebloggs/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_keywords']",
			RuntimeVariables.replace("socialofficefriendfn"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("socialofficefriendfn"),
			selenium.getText("//td[2]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("socialofficefriendfn"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"socialofficefriendfn socialofficefriendmn socialofficefriendln"),
			selenium.getText("//div[2]/h1/span"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//a[@id='_125_addressesLink']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isPartialText("//a[@id='_125_addressesLink']",
				"Addresses"));
		selenium.clickAt("//a[@id='_125_addressesLink']",
			RuntimeVariables.replace("Addresses"));
		selenium.type("//input[@id='_125_addressStreet1_0']",
			RuntimeVariables.replace("123 Liferay Ln."));
		selenium.saveScreenShotAndSource();
		selenium.select("//select[@id='_125_addressCountryId0']",
			RuntimeVariables.replace("label=United States"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isPartialText(
							"//select[@id='_125_addressRegionId0']",
							"California")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.select("//select[@id='_125_addressRegionId0']",
			RuntimeVariables.replace("label=California"));
		selenium.type("//input[@id='_125_addressZip0']",
			RuntimeVariables.replace("91234"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_addressCity0']",
			RuntimeVariables.replace("Ray of Light"));
		selenium.saveScreenShotAndSource();
		selenium.select("//select[@id='_125_addressTypeId0']",
			RuntimeVariables.replace("label=Personal"));
		selenium.clickAt("//input[@id='_125_addressMailing0Checkbox']",
			RuntimeVariables.replace("Mailing Checkbox"));
		selenium.clickAt("//input[@id='_125_addressPrimary0']",
			RuntimeVariables.replace("Primary Button"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='portlet-msg-success']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("123 Liferay Ln.",
			selenium.getValue("//input[@id='_125_addressStreet1_0']"));
		assertEquals("Personal",
			selenium.getSelectedLabel("//select[@id='_125_addressTypeId0']"));
		assertEquals("91234",
			selenium.getValue("//input[@id='_125_addressZip0']"));
		assertEquals("Ray of Light",
			selenium.getValue("//input[@id='_125_addressCity0']"));
		assertEquals("United States",
			selenium.getSelectedLabel("//select[@id='_125_addressCountryId0']"));
		assertEquals("California",
			selenium.getSelectedLabel("//select[@id='_125_addressRegionId0']"));
		assertTrue(selenium.isChecked("//input[@id='_125_addressPrimary0']"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isChecked(
				"//input[@id='_125_addressMailing0Checkbox']"));
		selenium.saveScreenShotAndSource();
	}
}