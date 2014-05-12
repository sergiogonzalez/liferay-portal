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

package com.liferay.portlet.blogs.pingback;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalService;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
 * @author Roberto Díaz
 */
@PrepareForTest(
	{BlogsEntryLocalServiceUtil.class, LanguageUtil.class,
	MBMessageLocalServiceUtil.class, PortalUtil.class,
	PortletLocalServiceUtil.class, UserLocalServiceUtil.class})
@RunWith(PowerMockRunner.class)
public class PingbackCommentsImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpBlogs();
		setUpLanguage();
		setUpMessageBoards();
		setUpPortal();
		setupPortlet();
		setUpUser();
	}

	@Test
	public void testAddComment() throws Exception {
		addComment();

		Mockito.verify(
			_mbMessageLocalService
		).getDiscussionMessageDisplay(
			_USER_ID, _GROUP_ID, BlogsEntry.class.getName(), _ENTRY_ID,
			WorkflowConstants.STATUS_APPROVED
		);

		Mockito.verify(
			_mbMessageLocalService
		).getThreadMessages(
			_THREAD_ID, WorkflowConstants.STATUS_APPROVED
		);

		Mockito.verify(
			_mbMessageLocalService
		).addDiscussionMessage(
			Matchers.eq(_USER_ID), Matchers.eq(StringPool.BLANK),
			Matchers.eq(_GROUP_ID), Matchers.eq(BlogsEntry.class.getName()),
			Matchers.eq(_ENTRY_ID), Matchers.eq(_THREAD_ID),
			Matchers.eq(_PARENT_MESSAGE_ID), Matchers.eq(StringPool.BLANK),
			Matchers.eq("__body__"), Matchers.any(ServiceContext.class)
		);
	}

	@Test(expected = DuplicateCommentException.class)
	public void testAddDuplicateComment() throws Exception {
		MBMessage message = Mockito.mock(MBMessage.class);

		when(
			message.getBody()
		).thenReturn(
			"__body__"
		);

		List<MBMessage> messages = Collections.singletonList(message);

		when(
			_mbMessageLocalService.getThreadMessages(
				_THREAD_ID, WorkflowConstants.STATUS_APPROVED)
		).thenReturn(
			messages
		);

		addComment();

		Assert.fail();
	}

	protected void addComment() throws Exception {
		_pingbackComments.addComment(
			_COMPANY_ID, _GROUP_ID, BlogsEntry.class.getName(), _ENTRY_ID,
			"__body__", _URL_TITLE);
	}

	protected void setUpBlogs() throws Exception {
		when(
			_blogsEntryLocalService.getBlogsEntry(Matchers.anyLong())
		).thenReturn(
			_entry
		);

		when(
			_entry.getGroupId()
		).thenReturn(
			_GROUP_ID
		);

		when(
			_entry.getEntryId()
		).thenReturn(
			_ENTRY_ID
		);

		when(
			_entry.getUrlTitle()
		).thenReturn(
			_URL_TITLE
		);

		mockStatic(BlogsEntryLocalServiceUtil.class, new CallsRealMethods());

		stub(
			method(BlogsEntryLocalServiceUtil.class, "getService")
		).toReturn(
			_blogsEntryLocalService
		);
	}

	protected void setUpLanguage() throws Exception {
		mockStatic(LanguageUtil.class, new CallsRealMethods());

		stub(
			method(
				LanguageUtil.class, "get", Locale.class, String.class)
		).toReturn(
			_USER_NAME
		);
	}

	protected void setUpMessageBoards() throws Exception {
		when(
			_mbMessageDisplay.getThread()
		).thenReturn(
			_mbThread
		);

		when(
			_mbMessageLocalService.getDiscussionMessageDisplay(
				Matchers.anyLong(), Matchers.anyLong(),
				Matchers.eq(BlogsEntry.class.getName()), Matchers.anyLong(),
				Matchers.eq(WorkflowConstants.STATUS_APPROVED))
		).thenReturn(
			_mbMessageDisplay
		);

		when(
			_mbThread.getRootMessageId()
		).thenReturn(
			_PARENT_MESSAGE_ID
		);

		when(
			_mbThread.getThreadId()
		).thenReturn(
			_THREAD_ID
		);

		mockStatic(MBMessageLocalServiceUtil.class, new CallsRealMethods());

		stub(
			method(MBMessageLocalServiceUtil.class, "getService")
		).toReturn(
			_mbMessageLocalService
		);
	}

	protected void setUpPortal() throws Exception {
		when(
			_portal.getLayoutFullURL(
				Matchers.anyLong(), Matchers.anyString(), Matchers.anyBoolean())
		).thenReturn(
			_LAYOUT_FULL_URL
		);

		mockStatic(PortalUtil.class, new CallsRealMethods());

		stub(
			method(
				PortalUtil.class, "getPortal")
		).toReturn(
			_portal
		);
	}

	protected void setupPortlet() throws Exception {
		when(
			_portletLocalService.getPortletById(
				Matchers.anyLong(), Matchers.anyString())
		).thenReturn(
			_portlet
		);

		when(
			_portlet.getFriendlyURLMapping()
		).thenReturn(
			"blogs"
		);

		mockStatic(PortletLocalServiceUtil.class, new CallsRealMethods());

		stub(
			method(
				PortletLocalServiceUtil.class, "getService")
		).toReturn(
			_portletLocalService
		);
	}

	protected void setUpUser() throws Exception {
		when(
			_userLocalService.getDefaultUserId(Matchers.anyLong())
		).thenReturn(
			_USER_ID
		);

		mockStatic(UserLocalServiceUtil.class, new CallsRealMethods());

		stub(
			method(UserLocalServiceUtil.class, "getService")
		).toReturn(
			_userLocalService
		);
	}

	private static final long _COMPANY_ID = ServiceTestUtil.randomLong();

	private static final long _ENTRY_ID = ServiceTestUtil.randomLong();

	private static final long _GROUP_ID = ServiceTestUtil.randomLong();

	private static final String _LAYOUT_FULL_URL = ServiceTestUtil.randomString(
		10);

	private static final long _PARENT_MESSAGE_ID = ServiceTestUtil.randomLong();

	private static final long _THREAD_ID = ServiceTestUtil.randomLong();

	private static final String _URL_TITLE = ServiceTestUtil.randomString(25);

	private static final long _USER_ID = ServiceTestUtil.randomLong();

	private static final String _USER_NAME = ServiceTestUtil.randomString(5);

	@Mock
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Mock
	private BlogsEntry _entry;

	@Mock
	private LanguageUtil _languageUtil;

	@Mock
	private MBMessageDisplay _mbMessageDisplay;

	@Mock
	private MBMessageLocalService _mbMessageLocalService;

	@Mock
	private MBThread _mbThread;

	private PingbackComments _pingbackComments = new PingbackCommentsImpl();

	@Mock
	private Portal _portal;

	@Mock
	private Portlet _portlet;

	@Mock
	private PortletLocalService _portletLocalService;

	@Mock
	private UserLocalService _userLocalService;

}