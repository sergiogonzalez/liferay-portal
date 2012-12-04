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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsImpl;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Igor Spasic
 * @author Raymond Augé
 */
@PowerMockIgnore("javax.xml.datatype.*")
@PrepareForTest(PortalUtil.class)
@RunWith(PowerMockRunner.class)
public class JSONWebServiceServiceActionTest
	extends BaseJSONWebServiceTestCase {

	@BeforeClass
	public static void init() throws Exception {
		initPortalServices();

		Class<?> clazz = JSONWebServiceServiceAction.class;

		PortalClassLoaderUtil.setClassLoader(clazz.getClassLoader());

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		PropsUtil.setProps(new PropsImpl());

		ServletContext servletContext = new MockServletContext();

		_jsonWebServiceServiceAction = new JSONWebServiceServiceAction(
			servletContext, null);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		PortalClassLoaderUtil.setClassLoader(null);
	}

	@Before
	public void setUp() {
		spy(PortalUtil.class);

		when(
			PortalUtil.getPortalLibDir()
		).thenReturn(
			""
		);
	}

	@Test
	public void testInvokerNullCall() throws Exception {
		registerActionClass(FooService.class);

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/null-return", params);

		String json = toJSON(map);

		MockHttpServletRequest mockHttpServletRequest =
			createInvokerHttpServletRequest(json);
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		json = _jsonWebServiceServiceAction.getJSON(
			new ActionMapping(), new DynaActionForm(), mockHttpServletRequest,
			mockHttpServletResponse);

		Assert.assertEquals("{}", json);
	}

	@Test
	public void testInvokerSimpleCall() throws Exception {
		registerActionClass(FooService.class);

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/hello-world", params);

		params.put("userId", 173);
		params.put("worldName", "Jupiter");

		String json = toJSON(map);

		MockHttpServletRequest mockHttpServletRequest =
			createInvokerHttpServletRequest(json);
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		json = _jsonWebServiceServiceAction.getJSON(
			new ActionMapping(), new DynaActionForm(), mockHttpServletRequest,
			mockHttpServletResponse);

		Assert.assertEquals("\"Welcome 173 to Jupiter\"", json);
	}

	@Test
	public void testServletContextRequestParams1() throws Exception {
		registerActionClass(FooService.class, "/somectx");

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/hello-world");

		mockHttpServletRequest.setParameter("userId", "173");
		mockHttpServletRequest.setParameter("worldName", "Jupiter");

		mockHttpServletRequest.setMethod(HttpMethods.GET);
		mockHttpServletRequest.setContextPath("/somectx");

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		String json = _jsonWebServiceServiceAction.getJSON(
			new ActionMapping(), new DynaActionForm(), mockHttpServletRequest,
			mockHttpServletResponse);

		Assert.assertEquals("\"Welcome 173 to Jupiter\"", json);
	}

	@Test
	public void testServletContextRequestParams2() throws Exception {
		registerActionClass(FooService.class, "/somectx");

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/somectx.foo/hello-world");

		mockHttpServletRequest.setParameter("userId", "173");
		mockHttpServletRequest.setParameter("worldName", "Jupiter");

		mockHttpServletRequest.setMethod(HttpMethods.GET);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		String json = _jsonWebServiceServiceAction.getJSON(
			new ActionMapping(), new DynaActionForm(), mockHttpServletRequest,
			mockHttpServletResponse);

		Assert.assertEquals("\"Welcome 173 to Jupiter\"", json);
	}

	@Test
	public void testServletContextUrl1() throws Exception {
		registerActionClass(FooService.class, "/somectx");

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/foo/hello-world/user-id/173/world-name/Jupiter");

		mockHttpServletRequest.setMethod(HttpMethods.GET);
		mockHttpServletRequest.setContextPath("/somectx");

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		String json = _jsonWebServiceServiceAction.getJSON(
			new ActionMapping(), new DynaActionForm(), mockHttpServletRequest,
			mockHttpServletResponse);

		Assert.assertEquals("\"Welcome 173 to Jupiter\"", json);
	}

	@Test
	public void testServletContextUrl2() throws Exception {
		registerActionClass(FooService.class, "/somectx");

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/somectx.foo/hello-world/user-id/173/world-name/Jupiter");

		mockHttpServletRequest.setMethod(HttpMethods.GET);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		String json = _jsonWebServiceServiceAction.getJSON(
			new ActionMapping(), new DynaActionForm(), mockHttpServletRequest,
			mockHttpServletResponse);

		Assert.assertEquals("\"Welcome 173 to Jupiter\"", json);
	}

	@Test
	public void testServletContextInvoker1() throws Exception {
		registerActionClass(FooService.class, "/somectx");

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/foo/hello-world", params);

		params.put("userId", 173);
		params.put("worldName", "Jupiter");

		String json = toJSON(map);

		MockHttpServletRequest mockHttpServletRequest =
			createInvokerHttpServletRequest(json);
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		mockHttpServletRequest.setContextPath("/somectx");

		json = _jsonWebServiceServiceAction.getJSON(
			new ActionMapping(), new DynaActionForm(), mockHttpServletRequest,
			mockHttpServletResponse);

		Assert.assertEquals("\"Welcome 173 to Jupiter\"", json);
	}

	@Test
	public void testServletContextInvoker2() throws Exception {
		registerActionClass(FooService.class, "/somectx");

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		Map<String, Object> params = new LinkedHashMap<String, Object>();

		map.put("/somectx.foo/hello-world", params);

		params.put("userId", 173);
		params.put("worldName", "Jupiter");

		String json = toJSON(map);

		MockHttpServletRequest mockHttpServletRequest =
			createInvokerHttpServletRequest(json);
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		json = _jsonWebServiceServiceAction.getJSON(
			new ActionMapping(), new DynaActionForm(), mockHttpServletRequest,
			mockHttpServletResponse);

		Assert.assertEquals("\"Welcome 173 to Jupiter\"", json);
	}

	protected MockHttpServletRequest createInvokerHttpServletRequest(
		String content) {

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/invoke");

		mockHttpServletRequest.setContent(content.getBytes());
		mockHttpServletRequest.setMethod(HttpMethods.POST);
		mockHttpServletRequest.setRemoteUser("root");

		return mockHttpServletRequest;
	}

	private static JSONWebServiceServiceAction _jsonWebServiceServiceAction;

}