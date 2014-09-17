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
import com.liferay.portal.kernel.comment.CommentSectionDisplay;
import com.liferay.portal.kernel.comment.CommentTreeNodeDisplay;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;

import java.util.Collections;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author André de Oliveira
 */
public class DummyCommentSectionDisplayImpl implements CommentSectionDisplay {

	@Override
	public String getBodyFormatted(Comment comment) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<CommentTreeNodeDisplay> getCommentTreeNodeDisplays() {
		return Collections.<CommentTreeNodeDisplay>emptyList();
	}

	@Override
	public Comment getParentComment(Comment comment) throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRatingsClassName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public RatingsEntry getRatingsEntry(Comment comment) {
		throw new UnsupportedOperationException();
	}

	@Override
	public RatingsStats getRatingsStats(Comment comment) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getRootCommentMessageId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SearchContainer getSearchContainer() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getThreadId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasAddPermission() {
		return false;
	}

	@Override
	public boolean hasComments() {
		return false;
	}

	@Override
	public boolean hasDeletePermission(Comment comment) throws PortalException {
		return false;
	}

	@Override
	public boolean hasEditPermission(Comment comment) throws PortalException {
		return false;
	}

	@Override
	public boolean hasWorkflowDefinitionLink() {
		return false;
	}

	@Override
	public List<Comment> initComments(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return Collections.<Comment>emptyList();
	}

	@Override
	public boolean isDiscussionActionsVisible(Comment comment)
		throws PortalException {

		return false;
	}

	@Override
	public boolean isDiscussionVisible() {
		return false;
	}

	@Override
	public boolean isRatingsVisible(Comment comment) throws PortalException {
		return false;
	}

	@Override
	public boolean isSearchPaginatorVisible() {
		return false;
	}

	@Override
	public boolean isSubscriptionButtonVisible() throws PortalException {
		return false;
	}

	@Override
	public boolean isThreadedRepliesVisible() {
		return false;
	}

	@Override
	public boolean isTopChild(Comment comment) throws PortalException {
		return false;
	}

	@Override
	public boolean isVisible(Comment comment) {
		return false;
	}

	@Override
	public boolean isWorkflowStatusVisible(Comment comment) {
		return false;
	}

}