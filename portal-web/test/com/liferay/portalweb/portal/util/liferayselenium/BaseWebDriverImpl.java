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

package com.liferay.portalweb.portal.util.liferayselenium;

import com.liferay.portalweb.portal.util.TestPropsValues;

import org.openqa.selenium.WebDriver;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseWebDriverImpl
	extends WebDriverToSeleniumBridge implements LiferaySelenium {

	public BaseWebDriverImpl(
		String projectDir, String browserURL, WebDriver webDriver) {

		super(webDriver);

		_projectDir = projectDir;

		webDriver.get(browserURL);
	}

	public void downloadTempFile(String value) {
		LiferaySeleniumHelper.downloadTempFile(value);
	}

	public String getCurrentDay() {
		return null;
	}

	public String getCurrentMonth() {
		return null;
	}

	public String getCurrentYear() {
		return null;
	}

	public String getFirstNumber(String locator) {
		return null;
	}

	public String getFirstNumberIncrement(String locator) {
		return null;
	}

	public boolean isPartialText(String locator, String value) {
		return false;
	}

	public void saveScreenShotAndSource() throws Exception {
	}

	public void setBrowserOption() {
		LiferaySeleniumHelper.setBrowserOption();
	}

	public void uploadCommonFile(String location, String value) {
		uploadFile(
			location,
			_projectDir + "portal-web\\test\\com\\liferay\\portalweb\\" +
				"dependencies\\" + value);
	}

	public void uploadTempFile(String location, String value) {
		uploadFile(location, TestPropsValues.OUTPUT_DIR + value);
	}

	protected void uploadFile(String location, String value) {
	}

	private String _projectDir;

}