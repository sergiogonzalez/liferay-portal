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
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.replace;
import static org.powermock.api.support.membermodification.MemberModifier.stub;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.blogs.NoSuchEntryException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryService;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;
import com.liferay.portlet.blogs.trackback.TrackbackComments;
import com.liferay.portlet.blogs.util.LinkbackConsumerUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
	UserLocalServiceUtil.class, BlogsEntryServiceUtil.class,
	LinkbackConsumerUtil.class
})
public class TrackbackActionTest {

	public static void addNewTrackback(
		long messageId, String url, String entryUrl) {

		List<Object> list = Arrays.<Object>asList(messageId, url, entryUrl);

		_trackback = list.toString();
	}

	@Before
	public void setUp() throws Exception {
		doSetup();
	}

	@Test
	public void testDisabledComments() throws Exception {
		doReturn(
			"false"
		).when(
			_portletPreferences
		).getValue(
			"enableComments", null
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
		doThrowWhenGetEntry(NoSuchEntryException.class);

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
		doThrowWhenGetEntry(PrincipalException.class);

		initValidUrl();

		addTrackback();

		assertError(
			"Blog entry must have guest view permissions to enable trackbacks."
		);
	}

	@Test
	public void testSuccess() throws Exception {
		long userId = 42;
		String blogName = "This is a blog";
		long groupId = 16;
		String className = BlogsEntry.class.getName();
		long classPK = 142857;
		String title = "This is a title";

		// Prepare

		_mockOriginalServletRequest.setParameter("blog_name", blogName);
		_mockOriginalServletRequest.setParameter("title", title);
		_mockOriginalServletRequest.setParameter(
			"excerpt", "This is an excerpt");

		doReturn(
			userId
		).when(
			_userLocalService
		).getDefaultUserId(
			anyLong()
		);

		doReturn(
			groupId
		).when(
			_blogsEntry
		).getGroupId();

		doReturn(
			classPK
		).when(
			_blogsEntry
		).getEntryId();

		doReturn(
			"__UrlTitle__"
		).when(
			_blogsEntry
		).getUrlTitle();

		doReturn(
			"Read more"
		).when(
			_language
		).get(
			(Locale)any(), eq("read-more")
		);

		doReturn(
			"__LayoutFullURL__"
		).when(
			_portal
		).getLayoutFullURL(
			_themeDisplay
		);

		when(
			_trackbackComments.addTrackbackComment(
				anyLong(), anyLong(), anyString(), anyLong(), anyString(),
				anyString(), anyString(), (PortletRequest)any()
			)
		).thenReturn(
			99999L
		);

		initValidUrl();

		// Execute

		addTrackback();

		// Verify

		assertSuccess();

		verify(
			_trackbackComments
		).addTrackbackComment(
			eq(userId), eq(groupId), eq(className), eq(classPK), eq(blogName),
			eq(title),
			eq("[...] This is an excerpt [...] [url=__url__]Read more[/url]"),
			(PortletRequest)any()
		);

		assertEquals(
			"[99999, __url__, __LayoutFullURL__/-/blogs/__UrlTitle__]",
			_trackback);
	}

	@Test
	public void testTrackbacksNotEnabled() throws Exception {
		doReturn(
			false
		).when(
			_blogsEntry
		).isAllowTrackbacks();

		initValidUrl();

		addTrackback();

		assertError("Trackbacks are not enabled on this blog entry.");
	}

	protected void addTrackback() throws Exception {
		TrackbackAction trackbackAction = new TrackbackAction(
			_trackbackComments);

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

	protected void doSetup() throws Exception {
		MockitoAnnotations.initMocks(this);

		doReturn(
			_mockOriginalServletRequest
		).when(
			_portal
		).getOriginalServletRequest((HttpServletRequest)any());

		doReturn(
			_mockHttpServletRequest
		).when(
			_portal
		).getHttpServletRequest((PortletRequest)any());

		doReturn(
			_mockHttpServletResponse
		).when(
			_portal
		).getHttpServletResponse((PortletResponse)any());

		doReturn(
			_themeDisplay
		).when(
			_actionRequest
		).getAttribute(WebKeys.THEME_DISPLAY);

		doReturn(
			_portletPreferences
		).when(
			_actionRequest
		).getPreferences();

		doReturn(
			_blogsEntry
		).when(
			_actionRequest
		).getAttribute(WebKeys.BLOGS_ENTRY);

		doReturn(
			true
		).when(
			_blogsEntry
		).isAllowTrackbacks();

		mockStatic(UserLocalServiceUtil.class, new CallsRealMethods());

		stub(
			method(UserLocalServiceUtil.class, "getService")
		).toReturn(
			_userLocalService
		);

		mockStatic(BlogsEntryServiceUtil.class, new CallsRealMethods());

		stub(
			method(BlogsEntryServiceUtil.class, "getService")
		).toReturn(
			_blogsEntryService
		);

		mockStatic(LinkbackConsumerUtil.class, new CallsRealMethods());

		replace(
			method(LinkbackConsumerUtil.class, "addNewTrackback")
		).with(
			getClass().getMethod(
				"addNewTrackback", Long.TYPE,String.class, String.class)
		);

		new PortalUtil().setPortal(_portal);

		PropsUtil.setProps(_props);

		new HttpUtil().setHttp(_http);

		_themeDisplay.setCompany(_company);

		new LanguageUtil().setLanguage(_language);
	}

	protected void doThrowWhenGetEntry(Class<? extends Throwable> toBeThrown)
		throws Exception {

		long entryId = 42;

		_mockHttpServletRequest.setParameter(
			"entryId", String.valueOf(entryId));

		doThrow(
			toBeThrown
		).when(
			_blogsEntryService
		).getEntry(entryId);
	}

	protected void initUrl(String remoteIp) {
		String url = "__url__";

		doReturn(
			remoteIp
		).when(
			_http
		).getIpAddress(url);

		_mockOriginalServletRequest.addParameter("url", url);
	}

	protected void initValidUrl() {
		initUrl(_mockHttpServletRequest.getRemoteAddr());
	}

	private static String _trackback;

	@Mock
	private ActionRequest _actionRequest;

	@Mock
	private ActionResponse _actionResponse;

	@Mock
	private BlogsEntry _blogsEntry;

	@Mock
	private BlogsEntryService _blogsEntryService;

	@Mock
	private Company _company;

	@Mock
	private Http _http;

	@Mock
	private Language _language;

	private MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();
	private MockHttpServletResponse _mockHttpServletResponse =
		new MockHttpServletResponse();
	private MockHttpServletRequest _mockOriginalServletRequest =
		new MockHttpServletRequest();

	@Mock
	private Portal _portal;

	@Mock
	private PortletPreferences _portletPreferences;

	@Mock
	private Props _props;

	private ThemeDisplay _themeDisplay = new ThemeDisplay();

	@Mock
	private TrackbackComments _trackbackComments;

	@Mock
	private UserLocalService _userLocalService;

}