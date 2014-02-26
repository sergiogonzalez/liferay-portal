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
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.blogs.NoSuchEntryException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryService;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;
import com.liferay.portlet.blogs.util.LinkbackConsumerUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeFactory;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.Arrays;
import java.util.Collections;
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
	UserLocalServiceUtil.class, MBMessageLocalServiceUtil.class,
	BlogsEntryServiceUtil.class, LinkbackConsumerUtil.class
})
public class TrackbackActionTest {

	public static void addNewTrackback(
		long messageId, String url, String entryUrl) {

		_trackback = Arrays.<Object>asList(messageId, url, entryUrl).toString();
	}

	@Before
	public void setUp() throws Exception {
		_prepareMocks();
	}

	@Test
	public void testDisabledComments() throws Exception {

		doReturn("false").when(
			_mockPortletPreferences).getValue("enableComments", null);

		_addTrackback();

		_assertError("Comments have been disabled for this blog entry.");
	}

	@Test
	public void testMismatchedIpAddress() throws Exception {

		_initUrl("BOGUS-IP");

		_addTrackback();

		_assertError(
			"Remote IP 127.0.0.1 does not match trackback URL's IP BOGUS-IP.");
	}

	@Test
	public void testMissingURL() throws Exception {

		_addTrackback();

		_assertError("Trackback requires a valid permanent URL.");
	}

	@Test
	public void testNoSuchEntryException() throws Exception {

		_doThrowWhenGetEntry(NoSuchEntryException.class);

		_initValidUrl();

		try {
			_addTrackback();
			Assert.fail();
		}
		catch (NoSuchEntryException nsee) {

			// Test pass. The expected outcome is the exception being thrown,
			// instead of the error response XML being produced.

		}
	}

	@Test
	public void testPrincipalException() throws Exception {

		_doThrowWhenGetEntry(PrincipalException.class);

		_initValidUrl();

		_addTrackback();

		_assertError(
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
		long threadId = 7;
		long parentMessageId = 37;
		String title = "This is a title";

		_mockOriginalServletRequest.setParameter("blog_name", blogName);
		_mockOriginalServletRequest.setParameter("title", title);
		_mockOriginalServletRequest.setParameter(
			"excerpt", "This is an excerpt");

		doReturn(userId).when(
			_mockUserLocalService).getDefaultUserId(anyLong());

		doReturn(groupId).when(_mockBlogsEntry).getGroupId();
		doReturn(classPK).when(_mockBlogsEntry).getEntryId();
		doReturn("__UrlTitle__").when(_mockBlogsEntry).getUrlTitle();

		doReturn(threadId).when(_mockMBThread).getThreadId();
		doReturn(parentMessageId).when(_mockMBThread).getRootMessageId();

		doReturn(99999L).when(_mockMBMessage).getMessageId();

		doReturn("Read more").when(
			_mockLanguage).get((Locale)any(), eq("read-more"));

		doReturn("__LayoutFullURL__").when(
			_mockPortal).getLayoutFullURL(_mockThemeDisplay);

		_initValidUrl();

		_addTrackback();

		_assertSuccess();

		// verify APIs were indeed called

		verify(_mockMBMessageLocalService).getDiscussionMessageDisplay(
			userId, groupId, className, classPK,
			WorkflowConstants.STATUS_APPROVED);

		verify(_mockMBMessageLocalService).addDiscussionMessage(
			eq(userId), eq(blogName), eq(groupId), eq(className), eq(classPK),
			eq(threadId), eq(parentMessageId), eq(title),
			eq("[...] This is an excerpt [...] [url=__url__]Read more[/url]"),
			(ServiceContext)any());

		assertEquals(
			"[99999, __url__, __LayoutFullURL__/-/blogs/__UrlTitle__]",
			_trackback);
	}

	@Test
	public void testTrackbacksNotEnabled() throws Exception {

		doReturn(false).when(_mockBlogsEntry).isAllowTrackbacks();

		_initValidUrl();

		_addTrackback();

		_assertError("Trackbacks are not enabled on this blog entry.");
	}

	private void _addTrackback() throws Exception {

		new TrackbackAction().addTrackback(
			_mockActionRequest, _mockActionResponse);
	}

	private void _assertError(String msg) throws Exception {

		_assertResponseContent(
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
			"<response><error>1</error><message>" +
			msg +
			"</message></response>"
		);
	}

	private void _assertResponseContent(String expected) throws Exception {

		assertEquals(expected, _mockResponse.getContentAsString());
	}

	private void _assertSuccess() throws Exception {

		_assertResponseContent(
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
			"<response><error>0</error></response>"
		);
	}

	private void _doThrowWhenGetEntry(Class<? extends Throwable> toBeThrown)
		throws Exception {

		long entryId = 42;

		_mockHttpServletRequest.setParameter(
			"entryId", String.valueOf(entryId));

		doThrow(toBeThrown).when(_mockBlogsEntryService).getEntry(entryId);
	}

	private void _initUrl(String remoteIp) {

		String url = "__url__";

		doReturn(remoteIp).when(_mockHttp).getIpAddress(url);

		_mockOriginalServletRequest.addParameter("url", url);
	}

	private void _initValidUrl() {

		_initUrl(_mockHttpServletRequest.getRemoteAddr());
	}

	private void _prepareMocks() throws Exception {

		MockitoAnnotations.initMocks(this);

		doReturn(_mockOriginalServletRequest).when(
			_mockPortal).getOriginalServletRequest((HttpServletRequest)any());

		doReturn(_mockHttpServletRequest).when(
			_mockPortal).getHttpServletRequest((PortletRequest)any());

		doReturn(_mockResponse).when(
			_mockPortal).getHttpServletResponse((PortletResponse)any());

		doReturn(_mockThemeDisplay).when(
			_mockActionRequest).getAttribute(WebKeys.THEME_DISPLAY);

		doReturn(_mockPortletPreferences).when(
			_mockActionRequest).getPreferences();

		doReturn(_mockBlogsEntry).when(
			_mockActionRequest).getAttribute(WebKeys.BLOGS_ENTRY);

		doReturn(Collections.enumeration(Collections.emptySet())).when(
			_mockActionRequest).getParameterNames();

		doReturn(_mockMBMessageDisplay).when(
			_mockMBMessageLocalService).getDiscussionMessageDisplay(
				anyLong(), anyLong(), eq(BlogsEntry.class.getName()), anyLong(),
				eq(WorkflowConstants.STATUS_APPROVED));

		doReturn(_mockMBMessage).when(
			_mockMBMessageLocalService).addDiscussionMessage(
				anyLong(), anyString(), anyLong(), anyString(), anyLong(),
				anyLong(), anyLong(), anyString(), anyString(),
				(ServiceContext)any());

		doReturn(_mockMBThread).when(_mockMBMessageDisplay).getThread();

		doReturn(true).when(_mockBlogsEntry).isAllowTrackbacks();

		mockStatic(UserLocalServiceUtil.class, new CallsRealMethods());
		stub(method(UserLocalServiceUtil.class, "getService")).toReturn(
			_mockUserLocalService);

		mockStatic(MBMessageLocalServiceUtil.class, new CallsRealMethods());
		stub(method(MBMessageLocalServiceUtil.class, "getService")).toReturn(
			_mockMBMessageLocalService);

		mockStatic(BlogsEntryServiceUtil.class, new CallsRealMethods());
		stub(method(BlogsEntryServiceUtil.class, "getService")).toReturn(
			_mockBlogsEntryService);

		mockStatic(LinkbackConsumerUtil.class, new CallsRealMethods());
		replace(method(LinkbackConsumerUtil.class, "addNewTrackback")).with(
			this.getClass().getMethod(
				"addNewTrackback", Long.TYPE, String.class, String.class));

		new PortalUtil().setPortal(_mockPortal);

		PropsUtil.setProps(_mockProps);

		new HttpUtil().setHttp(_mockHttp);

		_mockThemeDisplay.setCompany(_mockCompany);
		_mockThemeDisplay.setUser(_mockUser);

		new LanguageUtil().setLanguage(_mockLanguage);

		new PortletPreferencesFactoryUtil().setPortletPreferencesFactory(
			_mockPortletPreferencesFactory);

		new ExpandoBridgeFactoryUtil().setExpandoBridgeFactory(
			_mockExpandoBridgeFactory);
	}

	private static String _trackback;

	@Mock
	private ActionRequest _mockActionRequest;

	@Mock
	private ActionResponse _mockActionResponse;

	@Mock
	private BlogsEntry _mockBlogsEntry;

	@Mock
	private BlogsEntryService _mockBlogsEntryService;

	@Mock
	private Company _mockCompany;

	@Mock
	private ExpandoBridgeFactory _mockExpandoBridgeFactory;

	@Mock
	private Http _mockHttp;

	@Mock
	private Language _mockLanguage;

	private MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();

	@Mock
	private MBMessage _mockMBMessage;

	@Mock
	private MBMessageDisplay _mockMBMessageDisplay;

	@Mock
	private MBMessageLocalService _mockMBMessageLocalService;

	@Mock
	private MBThread _mockMBThread;

	private MockHttpServletRequest _mockOriginalServletRequest =
		new MockHttpServletRequest();

	@Mock
	private Portal _mockPortal;

	@Mock
	private PortletPreferences _mockPortletPreferences;

	@Mock
	private PortletPreferencesFactory _mockPortletPreferencesFactory;

	@Mock
	private Props _mockProps;

	private MockHttpServletResponse _mockResponse =
		new MockHttpServletResponse();

	private ThemeDisplay _mockThemeDisplay = new ThemeDisplay();

	@Mock
	private User _mockUser;

	@Mock
	private UserLocalService _mockUserLocalService;

}