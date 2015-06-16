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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.net.HttpCookie;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class CookieUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructor() {
		new CookieUtil();
	}

	@Test
	public void testEquals() {

		// Comment

		Cookie cookie1 = new Cookie("name", null);

		cookie1.setComment("comment");

		Cookie cookie2 = new Cookie("name2", null);

		cookie2.setComment("comment2");

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2.setComment("comment");

		// Domain

		cookie1.setDomain("domain");
		cookie2.setDomain("domain2");

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2.setDomain("domain");

		// Max age

		cookie1.setMaxAge(1);
		cookie2.setMaxAge(2);

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2.setMaxAge(1);

		// Name

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2 = new Cookie("name", null);

		cookie2.setComment("comment");
		cookie2.setDomain("domain");
		cookie2.setMaxAge(1);

		// Path

		cookie1.setPath("path");
		cookie2.setPath("path2");

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2.setPath("path");

		// Secure

		cookie1.setSecure(true);
		cookie2.setSecure(false);

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2.setSecure(true);

		// Value

		cookie1.setValue("value");
		cookie2.setValue("value2");

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2.setValue("value");

		// Version

		cookie1.setVersion(1);
		cookie2.setVersion(2);

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2.setVersion(1);

		// HTTP only

		cookie1.setHttpOnly(true);
		cookie2.setHttpOnly(false);

		Assert.assertFalse(CookieUtil.equals(cookie1, cookie2));

		cookie2.setHttpOnly(true);

		// Equals

		Assert.assertTrue(CookieUtil.equals(cookie1, cookie2));
	}

	@Test
	public void testParseHttpCookies() {
		HttpServletResponse response = new MockHttpServletResponse();

		response.setHeader(HttpHeaders.SET_COOKIE, "name1=value1,name2=value2");

		Map<String, HttpCookie> httpCookies = CookieUtil.parseHttpCookies(
			response.getHeader(HttpHeaders.SET_COOKIE));

		Assert.assertEquals(2, httpCookies.size());
		Assert.assertEquals(
			new HttpCookie("name1", "value1"), httpCookies.get("name1"));
		Assert.assertEquals(
			new HttpCookie("name2", "value2"), httpCookies.get("name2"));
	}

	@Test
	public void testSerializationAndDeserialization() {
		Cookie cookie1 = new Cookie("name1", null);

		byte[] bytes = CookieUtil.serialize(cookie1);

		Assert.assertTrue(
			CookieUtil.equals(cookie1, CookieUtil.deserialize(bytes)));

		Cookie cookie2 = new Cookie("name2", "value");

		cookie2.setComment("comment");
		cookie2.setDomain("domain");
		cookie2.setHttpOnly(true);
		cookie2.setMaxAge(1);
		cookie2.setPath("path");
		cookie2.setSecure(true);
		cookie2.setVersion(1);

		bytes = CookieUtil.serialize(cookie2);

		Assert.assertTrue(
			CookieUtil.equals(cookie2, CookieUtil.deserialize(bytes)));
	}

	@Test
	public void testToHttpCookie() {
		Cookie cookie = new Cookie("name", "value");

		cookie.setComment("comment");
		cookie.setDomain("domain");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(1);
		cookie.setPath("path");
		cookie.setSecure(true);
		cookie.setVersion(1);

		HttpCookie httpCookie = CookieUtil.toHttpCookie(cookie);

		Assert.assertEquals(cookie.getComment(), httpCookie.getComment());
		Assert.assertEquals(cookie.getDomain(), httpCookie.getDomain());
		Assert.assertEquals(cookie.getMaxAge(), httpCookie.getMaxAge());
		Assert.assertEquals(cookie.getName(), httpCookie.getName());
		Assert.assertEquals(cookie.getPath(), httpCookie.getPath());
		Assert.assertEquals(cookie.getSecure(), httpCookie.getSecure());
		Assert.assertEquals(cookie.getValue(), httpCookie.getValue());
		Assert.assertEquals(cookie.getVersion(), httpCookie.getVersion());
	}

	@Test
	public void testToString() {
		Cookie cookie = new Cookie("name", "value");

		cookie.setComment("comment");
		cookie.setDomain("domain");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(1);
		cookie.setPath("path");
		cookie.setSecure(true);
		cookie.setVersion(1);

		Assert.assertEquals(
			"{comment=comment, domain=domain, httpOnly=true, maxAge=1, " +
				"name=name, path=path, secure=true, value=value, version=1}",
			CookieUtil.toString(cookie));
	}

}