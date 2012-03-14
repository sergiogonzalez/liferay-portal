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

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Brian Wing Shun Chan
 */
public class WebDriverWrapper implements WebDriver {

	public WebDriverWrapper(WebDriver webDriver) {
		_webDriver = webDriver;
	}

	public void close() {
		_webDriver.close();
	}

	public WebElement findElement(By by) {
		return _webDriver.findElement(by);
	}

	public List<WebElement> findElements(By by) {
		return _webDriver.findElements(by);
	}

	public void get(String url) {
		_webDriver.get(url);
	}

	public String getCurrentUrl() {
		return _webDriver.getCurrentUrl();
	}

	public String getPageSource() {
		return _webDriver.getPageSource();
	}

	public String getTitle() {
		return _webDriver.getTitle();
	}

	public String getWindowHandle() {
		return _webDriver.getWindowHandle();
	}

	public Set<String> getWindowHandles() {
		return _webDriver.getWindowHandles();
	}

	public WebDriver getWrappedWebDriver() {
		return _webDriver;
	}

	public Options manage() {
		return _webDriver.manage();
	}

	public Navigation navigate() {
		return _webDriver.navigate();
	}

	public void quit() {
		_webDriver.quit();
	}

	public TargetLocator switchTo() {
		return _webDriver.switchTo();
	}

	private WebDriver _webDriver;

}