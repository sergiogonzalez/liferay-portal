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

package com.liferay.portalweb.portlet.wiki.lar.importlar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ImportLARTest extends BaseTestCase {
	public void testImportLAR() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 90) {
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

				selenium.clickAt("link=Wiki Test Page",
					RuntimeVariables.replace("Wiki Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Export / Import"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a",
					RuntimeVariables.replace("Export / Import"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Import",
					RuntimeVariables.replace("Import"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@id='_86_importFileName']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@id='_86_importFileName']",
					RuntimeVariables.replace(
						"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portlet\\wiki\\lar\\importlar\\dependencies\\Wiki-Selenium.portlet.lar"));

				boolean deleteBeforeChecked = selenium.isChecked(
						"_86_DELETE_PORTLET_DATACheckbox");

				if (deleteBeforeChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("_86_DELETE_PORTLET_DATACheckbox",
					RuntimeVariables.replace(
						"Delete portlet data before importing."));

			case 2:

				boolean dataChecked = selenium.isChecked(
						"_86_PORTLET_DATACheckbox");

				if (dataChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("_86_PORTLET_DATACheckbox",
					RuntimeVariables.replace(""));

			case 3:
				selenium.clickAt("//input[@value='Import']",
					RuntimeVariables.replace("Import"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}