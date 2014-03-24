/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.action;

import static org.junit.Assert.assertEquals;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.stub;

import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.blogs.NoSuchEntryException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.trackback.Trackback;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.CallsRealMethods;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author André de Oliveira
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( {
	ActionUtil.class
})
public class TrackbackActionTest {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpActionRequest();
		setUpActionUtil();
		setUpBlogsEntry();
		setUpPortal();
	}

	@Test
	public void testDisabledComments() throws Exception {
		when(
			_portletPreferences.getValue("enableComments", null)
		).thenReturn(
			"false"
		);

		addTrackback();

		assertError("Comments have been disabled for this blog entry.");
	}

	@Test
	public void testMismatchedIpAddress() throws Exception {
		initUrl("BOGUS-IP");

		addTrackback();

		assertError(
			"Remote IP 127.0.0.1 does not match trackback URL's IP BOGUS-IP.");
	}

	@Test
	public void testMissingURL() throws Exception {
		addTrackback();

		assertError("Trackback requires a valid permanent URL.");
	}

	@Test
	public void testNoSuchEntryException() throws Exception {
		whenGetEntryThenThrow(new NoSuchEntryException());

		initValidUrl();

		try {
			addTrackback();

			Assert.fail();
		}
		catch (NoSuchEntryException nsee) {
		}
	}

	@Test
	public void testPrincipalException() throws Exception {
		whenGetEntryThenThrow(new PrincipalException());

		initValidUrl();

		addTrackback();

		assertError(
			"Blog entry must have guest view permissions to enable trackbacks."
		);
	}

	@Test
	public void testSuccess() throws Exception {

		// Prepare

		_mockOriginalServletRequest.setParameter("blog_name", "__blogName__");
		_mockOriginalServletRequest.setParameter("title", "__title__");
		_mockOriginalServletRequest.setParameter("excerpt", "__excerpt__");

		initValidUrl();

		// Execute

		addTrackback();

		// Verify

		assertSuccess();

		verify(
			_trackback
		).addTrackback(
			same(_blogsEntry), same(_themeDisplay), eq("__excerpt__"),
			eq("__url__"), eq("__blogName__"), eq("__title__"),
			(Function<String, ServiceContext>)any()
		);
	}

	@Test
	public void testTrackbacksNotEnabled() throws Exception {
		when(
			_blogsEntry.isAllowTrackbacks()
		).thenReturn(
			false
		);

		initValidUrl();

		addTrackback();

		assertError("Trackbacks are not enabled on this blog entry.");
	}

	protected void addTrackback() throws Exception {
		TrackbackAction trackbackAction = new TrackbackAction(_trackback);

		trackbackAction.addTrackback(_actionRequest, _actionResponse);
	}

	protected void assertError(String msg) throws Exception {
		assertResponseContent(
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<response><error>1</error><message>" +
				msg +
				"</message></response>"
		);
	}

	protected void assertResponseContent(String expected) throws Exception {
		assertEquals(expected, _mockHttpServletResponse.getContentAsString());
	}

	protected void assertSuccess() throws Exception {
		assertResponseContent(
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<response><error>0</error></response>"
		);
	}

	protected void initUrl(String remoteIp) {
		String url = "__url__";

		when(
			_http.getIpAddress(url)
		).thenReturn(
			remoteIp
		);

		_mockOriginalServletRequest.addParameter("url", url);
	}

	protected void initValidUrl() {
		initUrl(_mockHttpServletRequest.getRemoteAddr());
	}

	protected void setUpActionRequest() {
		when(
			_actionRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);

		when(
			_actionRequest.getPreferences()
		).thenReturn(
			_portletPreferences
		);

		when(
			_actionRequest.getAttribute(WebKeys.BLOGS_ENTRY)
		).thenReturn(
			_blogsEntry
		);
	}

	protected void setUpActionUtil() {
		mockStatic(ActionUtil.class, new CallsRealMethods());
	}

	protected void setUpBlogsEntry() {
		when(
			_blogsEntry.isAllowTrackbacks()
		).thenReturn(
			true
		);
	}

	protected void setUpPortal() {
		Portal portal = mock(Portal.class);

		when(
			portal.getOriginalServletRequest((HttpServletRequest)any())
		).thenReturn(
			_mockOriginalServletRequest
		);

		when(
			portal.getHttpServletRequest((PortletRequest)any())
		).thenReturn(
			_mockHttpServletRequest
		);

		when(
			portal.getHttpServletResponse((PortletResponse)any())
		).thenReturn(
			_mockHttpServletResponse
		);

		new PortalUtil().setPortal(portal);

		PropsUtil.setProps(mock(Props.class));

		new HttpUtil().setHttp(_http);
	}

	protected void whenGetEntryThenThrow(Throwable toBeThrown)
		throws Exception {

		stub(
			method(ActionUtil.class, "getEntry", PortletRequest.class)
		).toThrow(
			toBeThrown
		);
	}

	@Mock
	private ActionRequest _actionRequest;

	@Mock
	private ActionResponse _actionResponse;

	@Mock
	private BlogsEntry _blogsEntry;

	@Mock
	private Http _http;

	private MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();
	private MockHttpServletResponse _mockHttpServletResponse =
		new MockHttpServletResponse();
	private MockHttpServletRequest _mockOriginalServletRequest =
		new MockHttpServletRequest();

	@Mock
	private PortletPreferences _portletPreferences;

	private ThemeDisplay _themeDisplay = new ThemeDisplay();

	@Mock
	private Trackback _trackback;

}