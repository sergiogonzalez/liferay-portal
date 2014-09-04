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

package com.liferay.portal.comment;

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentPermissionChecker;
import com.liferay.portal.kernel.comment.CommentSectionDisplay;
import com.liferay.portal.kernel.comment.CommentTreeNode;
import com.liferay.portal.kernel.comment.CommentTreeNodeDisplay;
import com.liferay.portal.kernel.comment.CommentsContainer;
import com.liferay.portal.kernel.comment.DiscussionDisplay;
import com.liferay.portal.kernel.comment.DiscussionPage;
import com.liferay.portal.kernel.comment.DiscussionRootComment;
import com.liferay.portal.kernel.comment.DiscussionTree;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.parsers.bbcode.BBCodeUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.RatingsEntryLocalService;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;
import com.liferay.portlet.ratings.service.RatingsStatsLocalService;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;
import com.liferay.portlet.ratings.service.persistence.RatingsEntryUtil;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * @author André de Oliveira
 */
@PrepareForTest( {
	BBCodeUtil.class, CommentSectionDisplayImpl.class, PortletURLUtil.class,
	RatingsEntryLocalServiceUtil.class, RatingsStatsLocalServiceUtil.class,
	RatingsEntryUtil.class, RatingsStatsUtil.class, SearchContainer.class,
	WorkflowDefinitionLinkLocalServiceUtil.class
})
@RunWith(PowerMockRunner.class)
public class CommentSectionDisplayImplTest {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpCommentSectionDisplay();
		setUpPortletURLUtil();
		setUpPropsUtil();
		setUpRatings();
		setUpRenderRequest();
		setUpWorkflowDefinitionLinkLocalServiceUtil();
	}

	@Test
	public void testGetBodyFormatted() throws Exception {
		Mockito.when(_comment.isFormatBBCode()).thenReturn(false);

		final String body = RandomTestUtil.randomString();

		Mockito.when(_comment.getBody()).thenReturn(body);

		Assert.assertEquals(
			body, _commentSectionDisplay.getBodyFormatted(_comment));

		Mockito.when(_comment.isFormatBBCode()).thenReturn(true);

		final String pathThemeImages = RandomTestUtil.randomString();

		Mockito.when(
			_themeDisplay.getPathThemeImages()).thenReturn(pathThemeImages);

		final String bbCodeHTML = RandomTestUtil.randomString();

		PowerMockito.replace(
			PowerMockito.method(
				BBCodeUtil.class, "getBBCodeHTML", String.class, String.class)
		).with(
			new InvocationHandler() {

				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {

					if (args[0].equals(body) &&
						args[1].equals(pathThemeImages)) {

						return bbCodeHTML;
					}

					throw new IllegalArgumentException();
				}
			}
		);

		Assert.assertEquals(
			bbCodeHTML, _commentSectionDisplay.getBodyFormatted(_comment));
	}

	@Test
	public void testGetCommentTreeNodeDisplays() throws Exception {
		setUpDiscussionRootComment(DiscussionTree.class);

		CommentTreeNode rootCommentTreeNode = Mockito.mock(
			CommentTreeNode.class);

		Mockito.when(
			((DiscussionTree)_discussionRootComment).getRootCommentTreeNode()
		).thenReturn(
			rootCommentTreeNode
		);

		List<CommentTreeNodeDisplay> commentTreeNodeDisplays =
			_commentSectionDisplay.getCommentTreeNodeDisplays();
		Assert.assertTrue(commentTreeNodeDisplays.isEmpty());

		CommentTreeNode childCommentTreeNode1 = Mockito.mock(
			CommentTreeNode.class);
		Mockito.when(
			rootCommentTreeNode.getChildren()
		).thenReturn(
			Arrays.asList(childCommentTreeNode1)
		);

		commentTreeNodeDisplays =
			_commentSectionDisplay.getCommentTreeNodeDisplays();
		Assert.assertEquals(1, commentTreeNodeDisplays.size());

		CommentTreeNodeDisplay commentTreeNodeDisplay1 =
			commentTreeNodeDisplays.get(0);
		Assert.assertSame(
			childCommentTreeNode1.getComment(),
			commentTreeNodeDisplay1.getComment());
		Assert.assertTrue(commentTreeNodeDisplay1.isLastNode());
		Assert.assertEquals(0, commentTreeNodeDisplay1.getDepth());

		CommentTreeNode childCommentTreeNode2 = Mockito.mock(
			CommentTreeNode.class);
		Mockito.when(
			rootCommentTreeNode.getChildren()
		).thenReturn(
			Arrays.asList(childCommentTreeNode1, childCommentTreeNode2)
		);

		commentTreeNodeDisplays =
			_commentSectionDisplay.getCommentTreeNodeDisplays();
		Assert.assertEquals(2, commentTreeNodeDisplays.size());

		commentTreeNodeDisplay1 = commentTreeNodeDisplays.get(0);
		Assert.assertSame(
			childCommentTreeNode1.getComment(),
			commentTreeNodeDisplay1.getComment());
		Assert.assertFalse(commentTreeNodeDisplay1.isLastNode());
		Assert.assertEquals(0, commentTreeNodeDisplay1.getDepth());

		CommentTreeNodeDisplay commentTreeNodeDisplay2 =
			commentTreeNodeDisplays.get(1);
		Assert.assertSame(
			childCommentTreeNode2.getComment(),
			commentTreeNodeDisplay2.getComment());
		Assert.assertTrue(commentTreeNodeDisplay2.isLastNode());
		Assert.assertEquals(0, commentTreeNodeDisplay2.getDepth());
	}

	@Test
	public void testGetParentComment() throws Exception {
		Comment parentComment = Mockito.mock(Comment.class);

		Mockito.when(
			_discussionDisplay.getParent(_comment)
		).thenReturn(
			parentComment
		);

		Assert.assertSame(
			parentComment, _commentSectionDisplay.getParentComment(_comment));
	}

	@Test
	public void testGetRatingsClassName() throws Exception {
		String ratingsClassName = RandomTestUtil.randomString();
		Mockito.when(
			_discussionDisplay.getRatingsClassName()
		).thenReturn(ratingsClassName);

		Assert.assertEquals(
			ratingsClassName, _commentSectionDisplay.getRatingsClassName());
	}

	@Test
	public void testGetRatingsEntry() throws Exception {
		long userId = RandomTestUtil.randomLong();

		Mockito.when(_themeDisplay.getUserId()).thenReturn(userId);

		String className = RandomTestUtil.randomString();

		Mockito.when(
			_discussionDisplay.getRatingsClassName()).thenReturn(className);

		long ratingsClassPK = RandomTestUtil.randomLong();

		Mockito.when(_comment.getRatingsClassPK()).thenReturn(ratingsClassPK);

		List<Long> classPKs = Arrays.asList(ratingsClassPK);

		Mockito.when(_commentsContainer.getClassPKs()).thenReturn(classPKs);

		RatingsEntry ratingsEntry = Mockito.mock(RatingsEntry.class);

		Mockito.when(ratingsEntry.getClassPK()).thenReturn(ratingsClassPK);

		List<RatingsEntry> ratingsEntries = Arrays.asList(ratingsEntry);

		Mockito.when(
			_ratingsEntryLocalService.getEntries(userId, className, classPKs)
		).thenReturn(ratingsEntries);

		setUpDiscussionRootCommentTreeInitComments();

		Assert.assertSame(
			ratingsEntry, _commentSectionDisplay.getRatingsEntry(_comment));

		Comment comment = Mockito.mock(Comment.class);

		Mockito.when(
			comment.getRatingsClassPK()).thenReturn(ratingsClassPK + 1);

		_commentSectionDisplay.getRatingsEntry(comment);

		PowerMockito.verifyStatic();
		RatingsEntryUtil.create(0);
	}

	@Test
	public void testGetRatingsStats() throws Exception {
		long userId = RandomTestUtil.randomLong();

		Mockito.when(_themeDisplay.getUserId()).thenReturn(userId);

		String className = RandomTestUtil.randomString();

		Mockito.when(
			_discussionDisplay.getRatingsClassName()).thenReturn(className);

		long ratingsClassPK = RandomTestUtil.randomLong();

		Mockito.when(_comment.getRatingsClassPK()).thenReturn(ratingsClassPK);

		List<Long> classPKs = Arrays.asList(ratingsClassPK);

		Mockito.when(_commentsContainer.getClassPKs()).thenReturn(classPKs);

		RatingsStats ratingsStats = Mockito.mock(RatingsStats.class);

		Mockito.when(ratingsStats.getClassPK()).thenReturn(ratingsClassPK);

		List<RatingsStats> ratingsStatses = Arrays.asList(ratingsStats);

		Mockito.when(
			_ratingsStatsLocalService.getStats(className, classPKs)
		).thenReturn(ratingsStatses);

		setUpDiscussionRootCommentTreeInitComments();

		Assert.assertSame(
			ratingsStats, _commentSectionDisplay.getRatingsStats(_comment));

		Comment comment = Mockito.mock(Comment.class);

		Mockito.when(
			comment.getRatingsClassPK()).thenReturn(ratingsClassPK + 1);

		_commentSectionDisplay.getRatingsStats(comment);

		PowerMockito.verifyStatic();
		RatingsStatsUtil.create(0);
	}

	@Test
	public void testGetRootCommentMessageId() throws Exception {
		long id = RandomTestUtil.randomLong();

		Mockito.when(_discussionRootComment.getRootCommentId()).thenReturn(id);

		Assert.assertEquals(
			id, _commentSectionDisplay.getRootCommentMessageId());
	}

	@Test
	public void testGetSearchContainer() throws Exception {
		setUpDiscussionRootCommentTreeInitComments();

		Assert.assertNull(_commentSectionDisplay.getSearchContainer());

		SearchContainer searchContainer = Mockito.mock(SearchContainer.class);

		PowerMockito.whenNew(
			SearchContainer.class
		).withArguments(
			_renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM,
			SearchContainer.DEFAULT_DELTA, _portletURL, null, null
		).thenReturn(
			searchContainer
		);

		int commentsCount = RandomTestUtil.randomInt();

		Mockito.when(searchContainer.getEnd()).thenReturn(commentsCount);

		setUpDiscussionRootCommentPageInitComments(commentsCount);

		Assert.assertSame(
			searchContainer, _commentSectionDisplay.getSearchContainer());
	}

	@Test
	public void testGetThreadId() throws Exception {
		long id = RandomTestUtil.randomLong();

		Mockito.when(_discussionDisplay.getThreadId()).thenReturn(id);

		Assert.assertEquals(id, _commentSectionDisplay.getThreadId());
	}

	@Test
	public void testHasAddPermission() throws Exception {
		Mockito.when(
			_commentPermissionChecker.hasAddPermission()).thenReturn(false);

		Assert.assertFalse(_commentSectionDisplay.hasAddPermission());

		Mockito.when(
			_commentPermissionChecker.hasAddPermission()).thenReturn(true);

		Assert.assertTrue(_commentSectionDisplay.hasAddPermission());
	}

	@Test
	public void testHasComments() throws Exception {
		Mockito.when(_discussionRootComment.getCommentsCount()).thenReturn(0);

		Assert.assertFalse(_commentSectionDisplay.hasComments());

		Mockito.when(_discussionRootComment.getCommentsCount()).thenReturn(1);

		Assert.assertTrue(_commentSectionDisplay.hasComments());
	}

	@Test
	public void testHasDeletePermission() throws Exception {
		Mockito.when(
			_commentPermissionChecker.hasDeletePermission(_comment)
		).thenReturn(
			false
		);

		Assert.assertFalse(
			_commentSectionDisplay.hasDeletePermission(_comment));

		Mockito.when(
			_commentPermissionChecker.hasDeletePermission(_comment)
		).thenReturn(
			true
		);

		Assert.assertTrue(_commentSectionDisplay.hasDeletePermission(_comment));
	}

	@Test
	public void testHasEditPermission() throws Exception {
		Mockito.when(
			_commentPermissionChecker.hasEditPermission(_comment)
		).thenReturn(
			false
		);

		Assert.assertFalse(_commentSectionDisplay.hasEditPermission(_comment));

		Mockito.when(
			_commentPermissionChecker.hasEditPermission(_comment)
		).thenReturn(
			true
		);

		Assert.assertTrue(_commentSectionDisplay.hasEditPermission(_comment));
	}

	@Test
	public void testHasWorkflowDefinitionLink() throws Exception {
		long companyId = RandomTestUtil.randomLong();
		long scopeGroupId = RandomTestUtil.randomLong();
		String workflowDefinitionLinkClassName = RandomTestUtil.randomString();

		setUpCommentSectionDisplayScopeGroupId(scopeGroupId);

		Mockito.when(_themeDisplay.getCompanyId()).thenReturn(companyId);
		Mockito.when(
			_discussionDisplay.getWorkflowDefinitionLinkClassName()
		).thenReturn(
			workflowDefinitionLinkClassName
		);
		Mockito.when(
			_workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
				companyId, scopeGroupId, workflowDefinitionLinkClassName)
			).thenReturn(false);
		Assert.assertFalse(_commentSectionDisplay.hasWorkflowDefinitionLink());

		Mockito.when(
			_workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
				companyId, scopeGroupId, workflowDefinitionLinkClassName)
			).thenReturn(true);
		Assert.assertTrue(_commentSectionDisplay.hasWorkflowDefinitionLink());
	}

	@Test
	public void testIsDiscussionActionsVisible() throws Exception {
		setUpCommentSectionDisplayHideControls(true);

		Assert.assertFalse(
			_commentSectionDisplay.isDiscussionActionsVisible(_comment));

		Mockito.when(
			_discussionDisplay.isInTrash(_comment)
		).thenReturn(
			true
		);
		setUpCommentSectionDisplayHideControls(false);

		Assert.assertFalse(
			_commentSectionDisplay.isDiscussionActionsVisible(_comment));

		Mockito.when(
			_discussionDisplay.isInTrash(_comment)
		).thenReturn(
			false
		);
		setUpCommentSectionDisplayHideControls(false);

		Assert.assertTrue(
			_commentSectionDisplay.isDiscussionActionsVisible(_comment));
	}

	@Test
	public void testIsDiscussionVisible() {
		Mockito.when(
			_commentPermissionChecker.hasViewPermission()
		).thenReturn(
			false
		);
		Mockito.when(
			_discussionRootComment.getCommentsCount()
		).thenReturn(
			0
		);

		Assert.assertFalse(_commentSectionDisplay.isDiscussionVisible());

		Mockito.when(
			_commentPermissionChecker.hasViewPermission()
		).thenReturn(
			false
		);
		Mockito.when(
			_discussionRootComment.getCommentsCount()
		).thenReturn(
			1
		);

		Assert.assertTrue(_commentSectionDisplay.isDiscussionVisible());

		Mockito.when(
			_commentPermissionChecker.hasViewPermission()
		).thenReturn(
			true
		);
		Mockito.when(
			_discussionRootComment.getCommentsCount()
		).thenReturn(
			0
		);

		Assert.assertTrue(_commentSectionDisplay.isDiscussionVisible());
	}

	@Test
	public void testIsRatingsVisible() throws Exception {
		setUpCommentSectionDisplayRatingsEnabled(false);

		Assert.assertFalse(_commentSectionDisplay.isRatingsVisible(_comment));

		setUpCommentSectionDisplayRatingsEnabled(true);
		Mockito.when(
			_discussionDisplay.isInTrash(_comment)
		).thenReturn(
			true
		);

		Assert.assertFalse(_commentSectionDisplay.isRatingsVisible(_comment));

		setUpCommentSectionDisplayRatingsEnabled(true);
		Mockito.when(
			_discussionDisplay.isInTrash(_comment)
		).thenReturn(
			false
		);

		Assert.assertTrue(_commentSectionDisplay.isRatingsVisible(_comment));
	}

	@Test
	public void testIsSearchPaginatorVisiblePage() throws Exception {
		int commentsCount = 10;

		int defaultDelta = SearchContainer.DEFAULT_DELTA;
		Whitebox.setInternalState(
			SearchContainer.class, "DEFAULT_DELTA", commentsCount);
		try {
			setUpDiscussionRootCommentPageInitComments(commentsCount);

			Assert.assertFalse(
				_commentSectionDisplay.isSearchPaginatorVisible());

			Mockito.when(
				_discussionRootComment.getCommentsCount()
			).thenReturn(
				commentsCount + 1
			);
			_commentSectionDisplay.initComments(
				_renderRequest, _renderResponse);

			Assert.assertTrue(
				_commentSectionDisplay.isSearchPaginatorVisible());
		}
		finally {
			Whitebox.setInternalState(
				SearchContainer.class, "DEFAULT_DELTA", defaultDelta);
		}
	}

	@Test
	public void testIsSearchPaginatorVisibleTree() throws Exception {
		setUpDiscussionRootCommentTreeInitComments();

		Assert.assertFalse(_commentSectionDisplay.isSearchPaginatorVisible());
	}

	@Test
	public void testIsSubscriptionButtonVisible() throws Exception {
		Mockito.when(_themeDisplay.isSignedIn()).thenReturn(false);

		Assert.assertFalse(
			_commentSectionDisplay.isSubscriptionButtonVisible());

		Mockito.when(_themeDisplay.isSignedIn()).thenReturn(true);
		Mockito.when(_discussionDisplay.isInTrash()).thenReturn(true);

		Assert.assertFalse(
			_commentSectionDisplay.isSubscriptionButtonVisible());

		Mockito.when(_themeDisplay.isSignedIn()).thenReturn(true);
		Mockito.when(_discussionDisplay.isInTrash()).thenReturn(false);

		Assert.assertTrue(_commentSectionDisplay.isSubscriptionButtonVisible());
	}

	@Test
	public void testIsThreadedRepliesVisible() throws Exception {
		setUpDiscussionRootComment(DiscussionPage.class);

		Assert.assertFalse(_commentSectionDisplay.isThreadedRepliesVisible());

		setUpDiscussionRootComment(DiscussionTree.class);

		Assert.assertTrue(_commentSectionDisplay.isThreadedRepliesVisible());
	}

	@Test
	public void testIsTopChild() throws Exception {
		long id = RandomTestUtil.randomLong();
		Mockito.when(_discussionRootComment.getRootCommentId()).thenReturn(id);

		Mockito.when(_comment.isChildOf(id)).thenReturn(false);
		Assert.assertFalse(_commentSectionDisplay.isTopChild(_comment));

		Mockito.when(_comment.isChildOf(id)).thenReturn(true);
		Assert.assertTrue(_commentSectionDisplay.isTopChild(_comment));
	}

	@Test
	public void testIsVisible() throws Exception {
		long userId = RandomTestUtil.randomLong();
		Mockito.when(_comment.getUserId()).thenReturn(userId);

		long scopeGroupId = RandomTestUtil.randomLong();

		setUpCommentSectionDisplayScopeGroupId(scopeGroupId);

		Mockito.when(_comment.isApproved()).thenReturn(true);
		Mockito.when(
			_commentPermissionChecker.hasViewPermission()).thenReturn(true);

		Assert.assertTrue(_commentSectionDisplay.isVisible(_comment));

		Mockito.when(_comment.isApproved()).thenReturn(true);
		Mockito.when(
			_commentPermissionChecker.hasViewPermission()).thenReturn(false);

		Assert.assertFalse(_commentSectionDisplay.isVisible(_comment));

		Mockito.when(_comment.isApproved()).thenReturn(false);
		Mockito.when(_user.getUserId()).thenReturn(userId);
		Mockito.when(_user.isDefaultUser()).thenReturn(true);
		Mockito.when(
			_permissionChecker.isGroupAdmin(scopeGroupId)).thenReturn(false);
		Mockito.when(
			_commentPermissionChecker.hasViewPermission()).thenReturn(true);

		Assert.assertFalse(_commentSectionDisplay.isVisible(_comment));

		Mockito.when(_comment.isApproved()).thenReturn(false);
		Mockito.when(_user.getUserId()).thenReturn(userId + 1);
		Mockito.when(_user.isDefaultUser()).thenReturn(false);
		Mockito.when(
			_permissionChecker.isGroupAdmin(scopeGroupId)).thenReturn(false);
		Mockito.when(
			_commentPermissionChecker.hasViewPermission()).thenReturn(true);

		Assert.assertFalse(_commentSectionDisplay.isVisible(_comment));

		Mockito.when(_comment.isApproved()).thenReturn(false);
		Mockito.when(_user.getUserId()).thenReturn(userId);
		Mockito.when(_user.isDefaultUser()).thenReturn(false);
		Mockito.when(
			_permissionChecker.isGroupAdmin(scopeGroupId)).thenReturn(false);
		Mockito.when(
			_commentPermissionChecker.hasViewPermission()).thenReturn(true);

		Assert.assertTrue(_commentSectionDisplay.isVisible(_comment));

		Mockito.when(_comment.isApproved()).thenReturn(false);
		Mockito.when(_user.getUserId()).thenReturn(userId + 1);
		Mockito.when(_user.isDefaultUser()).thenReturn(false);
		Mockito.when(
			_permissionChecker.isGroupAdmin(scopeGroupId)).thenReturn(true);
		Mockito.when(
			_commentPermissionChecker.hasViewPermission()).thenReturn(true);

		Assert.assertTrue(_commentSectionDisplay.isVisible(_comment));
	}

	@Test
	public void testIsWorkflowStatusVisible() throws Exception {
		Mockito.when(_comment.isApproved()).thenReturn(true);

		Assert.assertFalse(
			_commentSectionDisplay.isWorkflowStatusVisible(_comment));

		Mockito.when(_comment.isApproved()).thenReturn(false);

		Assert.assertTrue(
			_commentSectionDisplay.isWorkflowStatusVisible(_comment));
	}

	protected void setUpCommentSectionDisplay() throws PortalException {
		Mockito.when(
			_discussionDisplay.createDiscussionRootComment()
		).thenReturn(
			_discussionRootComment
		);

		_commentSectionDisplay = new CommentSectionDisplayImpl(
			0, _user, false, false, _discussionDisplay, _themeDisplay,
			_commentPermissionChecker, _permissionChecker);
	}

	protected void setUpCommentSectionDisplayHideControls(boolean hideControls)
		throws Exception {

		_commentSectionDisplay = new CommentSectionDisplayImpl(
			0, _user, hideControls, false, _discussionDisplay, _themeDisplay,
			_commentPermissionChecker, _permissionChecker);
	}

	protected void setUpCommentSectionDisplayRatingsEnabled(
			boolean ratingsEnabled)
		throws Exception {

		_commentSectionDisplay = new CommentSectionDisplayImpl(
			0, _user, false, ratingsEnabled, _discussionDisplay, _themeDisplay,
			_commentPermissionChecker, _permissionChecker);
	}

	protected void setUpCommentSectionDisplayScopeGroupId(long scopeGroupId)
		throws PortalException {

		_commentSectionDisplay = new CommentSectionDisplayImpl(
			scopeGroupId, _user, false, false, _discussionDisplay,
			_themeDisplay, _commentPermissionChecker, _permissionChecker);
	}

	protected void setUpDiscussionRootComment(Class... interfaces)
		throws Exception {

		_discussionRootComment = Mockito.mock(
			DiscussionRootComment.class,
			Mockito.withSettings().extraInterfaces(interfaces));

		setUpCommentSectionDisplay();
	}

	protected void setUpDiscussionRootCommentPageInitComments(int commentsCount)
		throws Exception {

		setUpDiscussionRootComment(DiscussionPage.class);

		Mockito.when(
			((DiscussionPage)_discussionRootComment).createCommentsContainer(
				0, commentsCount)
		).thenReturn(
			_commentsContainer
		);

		Mockito.when(
			_discussionRootComment.getCommentsCount()
		).thenReturn(
			commentsCount
		);

		_commentSectionDisplay.initComments(_renderRequest, _renderResponse);
	}

	protected void setUpDiscussionRootCommentTreeInitComments()
		throws Exception {

		setUpDiscussionRootComment(DiscussionTree.class);

		Mockito.when(
			((DiscussionTree)_discussionRootComment).createCommentsContainer()
		).thenReturn(
			_commentsContainer
		);

		_commentSectionDisplay.initComments(_renderRequest, _renderResponse);
	}

	protected void setUpPortletURLUtil() {
		PowerMockito.stub(
			PowerMockito.method(
				PortletURLUtil.class, "getCurrent", RenderRequest.class,
				RenderResponse.class)
		).toReturn(
			_portletURL
		);
	}

	protected void setUpPropsUtil() {
		PropsUtil.setProps(_props);
	}

	protected void setUpRatings() {
		PowerMockito.stub(
			PowerMockito.method(
				RatingsEntryLocalServiceUtil.class, "getService")
		).toReturn(
			_ratingsEntryLocalService
		);

		PowerMockito.stub(
			PowerMockito.method(
				RatingsStatsLocalServiceUtil.class, "getService")
		).toReturn(
			_ratingsStatsLocalService
		);

		PowerMockito.mockStatic(RatingsEntryUtil.class);
		PowerMockito.mockStatic(RatingsStatsUtil.class);
	}

	protected void setUpRenderRequest() {
		Mockito.when(
			_renderRequest.getParameter("resetCur")).thenReturn("true");
	}

	protected void setUpWorkflowDefinitionLinkLocalServiceUtil() {
		PowerMockito.stub(
			PowerMockito.method(
				WorkflowDefinitionLinkLocalServiceUtil.class, "getService")
		).toReturn(
			_workflowDefinitionLinkLocalService
		);
	}

	@Mock
	private Comment _comment;

	@Mock
	private CommentPermissionChecker _commentPermissionChecker;

	@Mock
	private CommentsContainer _commentsContainer;

	private CommentSectionDisplay _commentSectionDisplay;

	@Mock
	private Company _company;

	@Mock
	private DiscussionDisplay _discussionDisplay;

	@Mock
	private DiscussionRootComment _discussionRootComment;

	@Mock
	private PermissionChecker _permissionChecker;

	@Mock
	private PortletURL _portletURL;

	@Mock
	private Props _props;

	@Mock
	private RatingsEntryLocalService _ratingsEntryLocalService;

	@Mock
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Mock
	private RenderRequest _renderRequest;

	@Mock
	private RenderResponse _renderResponse;

	@Mock
	private ThemeDisplay _themeDisplay;

	@Mock
	private User _user;

	@Mock
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}