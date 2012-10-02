package com.liferay.portal.util;

import com.liferay.portal.CookieNotSupportedException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CookieKeys {

	public static final String COMPANY_ID = "COMPANY_ID";

	public static final String COOKIE_SUPPORT = "COOKIE_SUPPORT";

	public static final String GUEST_LANGUAGE_ID = "GUEST_LANGUAGE_ID";

	public static final String ID = "ID";

	public static final String JSESSIONID = "JSESSIONID";

	public static final String LOGIN = "LOGIN";

	public static final int MAX_AGE = 31536000;

	public static final String PASSWORD = "PASSWORD";

	public static final String REMEMBER_ME = "REMEMBER_ME";

	public static final String SCREEN_NAME = "SCREEN_NAME";

	public static final String USER_UUID = "USER_UUID";

	public static final int VERSION = 0;

	public void addCookie(
		HttpServletRequest request, HttpServletResponse response,
		Cookie cookie);

	public void addCookie(
		HttpServletRequest request, HttpServletResponse response, Cookie cookie,
		boolean secure);

	public void addSupportCookie(
		HttpServletRequest request, HttpServletResponse response);

	public String getCookie(HttpServletRequest request, String name);

	public String getCookie(
		HttpServletRequest request, String name, boolean toUpperCase);

	public String getDomain(HttpServletRequest request);

	public String getDomain(String host);

	public boolean hasSessionId(HttpServletRequest request);

	public boolean isEncodedCookie(String name);

	public void validateSupportCookie(HttpServletRequest request)
		throws CookieNotSupportedException;

}
