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

package com.liferay.portlet.blogs.trackback;

import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.util.LinkbackConsumerUtil;

import java.util.Arrays;

import javax.portlet.PortletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.CallsRealMethods;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author André de Oliveira
 */
@PrepareForTest({LinkbackConsumerUtil.class, UserLocalServiceUtil.class})
@RunWith(PowerMockRunner.class)
public class TrackbackImplTest extends PowerMockito {

	public static void addNewTrackback(
		long messageId, String url, String entryURL) {

		_linkback = String.valueOf(
			Arrays.<Object>asList(messageId, url, entryURL));
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpLinkbackConsumer();
		setUpPortal();
		setUpThemeDisplay();
		setUpServiceContext();
		setUpUserLocalService();
	}

	@Test
	public void testAddTrackback() throws Exception {
		long entryId = 142857;

		when(
			_blogsEntry.getEntryId()
		).thenReturn(
			entryId
		);

		long groupId = 16;

		when(
			_blogsEntry.getGroupId()
		).thenReturn(
			groupId
		);

		when(
			_blogsEntry.getUrlTitle()
		).thenReturn(
			"__UrlTitle__"
		);

		when(
			_portal.getLayoutFullURL(_themeDisplay)
		).thenReturn(
			"__LayoutFullURL__"
		);

		when(
			_themeDisplay.translate("read-more")
		).thenReturn(
			"__read-more__"
		);

		long userId = 42;

		when(
			_userLocalService.getDefaultUserId(Matchers.anyLong())
		).thenReturn(
			userId
		);

		when(
			_trackbackComments.addTrackbackComment(
				Matchers.anyLong(), Matchers.anyLong(), Matchers.anyString(),
				Matchers.anyLong(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(), Matchers.any(ServiceContext.class)
			)
		).thenReturn(
			99999L
		);

		Trackback trackback = new TrackbackImpl(_trackbackComments);

		trackback.addTrackback(
			_blogsEntry, _themeDisplay, "__excerpt__", "__url__",
			"__blogName__", "__title__", new ServiceContext()
		);

		Mockito.verify(
			_trackbackComments
		).addTrackbackComment(
			Matchers.eq(userId), Matchers.eq(groupId),
			Matchers.eq(BlogsEntry.class.getName()), Matchers.eq(entryId),
			Matchers.eq("__blogName__"), Matchers.eq("__title__"),
			Matchers.eq(
				"[...] __excerpt__ [...] [url=__url__]__read-more__[/url]"),
			Matchers.any(ServiceContext.class)
		);

		Assert.assertEquals(
			"[99999, __url__, __LayoutFullURL__/-/blogs/__UrlTitle__]",
			_linkback);
	}

	protected void setUpLinkbackConsumer() throws Exception {
		mockStatic(LinkbackConsumerUtil.class, new CallsRealMethods());

		Class<?> clazz = getClass();

		replace(
			method(LinkbackConsumerUtil.class, "addNewTrackback")
		).with(
			clazz.getMethod(
				"addNewTrackback", Long.TYPE,String.class, String.class)
		);
	}

	protected void setUpPortal() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	protected void setUpServiceContext() {
		mockStatic(ServiceContextFactory.class, new CallsRealMethods());

		stub(
			method(
				ServiceContextFactory.class, "getInstance", String.class,
				PortletRequest.class)
		).toReturn(
			new ServiceContext()
		);
	}

	protected void setUpThemeDisplay() throws Exception {
		_themeDisplay = PowerMockito.mock(ThemeDisplay.class);
	}

	protected void setUpUserLocalService() {
		mockStatic(UserLocalServiceUtil.class, new CallsRealMethods());

		stub(
			method(UserLocalServiceUtil.class, "getService")
		).toReturn(
			_userLocalService
		);
	}

	private static String _linkback;

	@Mock
	private BlogsEntry _blogsEntry;

	@Mock
	private Portal _portal;

	private ThemeDisplay _themeDisplay;

	@Mock
	private TrackbackComments _trackbackComments;

	@Mock
	private UserLocalService _userLocalService;

}