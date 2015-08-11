/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.poshi.runner.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * @author Brian Wing Shun Chan
 */
public class InternetExplorerWebDriverImpl extends BaseWebDriverImpl {

	public InternetExplorerWebDriverImpl(
		String projectDirName, String browserURL) {

		super(projectDirName, browserURL, new InternetExplorerDriver());
	}

	@Override
	public void javaScriptMouseDown(String locator) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		StringBuilder sb = new StringBuilder(4);

		sb.append("var element = arguments[0];");
		sb.append("var event = document.createEvent('MouseEvents');");
		sb.append("event.initEvent('pointerdown', true, false);");
		sb.append("element.dispatchEvent(event);");

		javascriptExecutor.executeScript(sb.toString(), webElement);
	}

	@Override
	public void javaScriptMouseUp(String locator) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		StringBuilder sb = new StringBuilder(5);

		sb.append("var element = arguments[0];");
		sb.append("var event = document.createEvent('MouseEvents');");
		sb.append("event.initEvent('pointerup', true, false);");
		sb.append("element.dispatchEvent(event)");

		javascriptExecutor.executeScript(sb.toString(), webElement);
	}

}