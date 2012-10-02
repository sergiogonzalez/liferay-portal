package com.liferay.portal.util;

import com.liferay.portal.CookieNotSupportedException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieKeysUtil {

	public static CookieKeys getCookieKeys() {
		PortalRuntimePermission.checkGetBeanProperty(CookieKeysUtil.class);

		return _cookieKeys;
	}

	public static void addCookie(
		HttpServletRequest request, HttpServletResponse response,
		Cookie cookie) {

		getCookieKeys().addCookie(request, response, cookie);
	}

	public static void addCookie(
		HttpServletRequest request, HttpServletResponse response, Cookie cookie,
		boolean secure) {

		getCookieKeys().addCookie(request, response, cookie, secure);
	}

	public static void addSupportCookie(
		HttpServletRequest request, HttpServletResponse response) {

		getCookieKeys().addSupportCookie(request, response);
	}

	public static String getCookie(HttpServletRequest request, String name) {
		return getCookieKeys().getCookie(request, name);
	}

	public static String getCookie(
		HttpServletRequest request, String name, boolean toUpperCase) {

		return getCookieKeys().getCookie(request, name, toUpperCase);
	}

	public static String getDomain(HttpServletRequest request) {
		return getCookieKeys().getDomain(request);
	}

	public static String getDomain(String host) {
		return getCookieKeys().getDomain(host);
	}

	public static boolean hasSessionId(HttpServletRequest request) {
		return getCookieKeys().hasSessionId(request);
	}

	public static boolean isEncodedCookie(String name) {
		return getCookieKeys().isEncodedCookie(name);
	}

	public void setCookieKeys(CookieKeys cookieKeys) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_cookieKeys = cookieKeys;
	}

	public static void validateSupportCookie(HttpServletRequest request)
		throws CookieNotSupportedException {

		getCookieKeys().validateSupportCookie(request);
	}

	private static CookieKeys _cookieKeys;

}
