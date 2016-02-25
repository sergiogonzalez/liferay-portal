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

package com.liferay.activities.web.portlet;

import com.liferay.activities.web.util.ActivitiesUtil;
import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.microblogs.model.MicroblogsEntryConstants;
import com.liferay.microblogs.service.MicroblogsEntryLocalServiceUtil;
import com.liferay.microblogs.service.MicroblogsEntryServiceUtil;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextFunction;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.social.kernel.model.SocialActivitySet;
import com.liferay.social.kernel.service.SocialActivitySetLocalServiceUtil;

import java.io.IOException;

import java.text.DateFormat;
import java.text.Format;

import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Matthew Kong
 */
public class ActivitiesPortlet extends MVCPortlet {

	public void getMBComments(
			SocialActivitySet activitySet, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Object[] commentsClassNameAndClassPK =
			ActivitiesUtil.getCommentsClassNameAndClassPK(activitySet);

		String className = (String)commentsClassNameAndClassPK[0];
		long classPK = (Long)commentsClassNameAndClassPK[1];

		Discussion discussion = CommentManagerUtil.getDiscussion(
			themeDisplay.getUserId(), activitySet.getGroupId(), className,
			classPK, new ServiceContextFunction(resourceRequest));

		DiscussionComment discussionComment =
			discussion.getRootDiscussionComment();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DiscussionComment comment :
				discussionComment.getThreadComments()) {

			JSONObject messageJSONObject = getJSONObject(
				comment.getCommentId(), comment.getBody(),
				comment.getModifiedDate(), comment.getUserId(),
				comment.getUserName(), themeDisplay);

			jsonArray.put(messageJSONObject);
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("comments", jsonArray);

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	public void getMicroblogsComments(
			SocialActivitySet activitySet, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		List<MicroblogsEntry> microblogsEntries =
			MicroblogsEntryLocalServiceUtil.
				getParentMicroblogsEntryMicroblogsEntries(
					MicroblogsEntryConstants.TYPE_REPLY,
					activitySet.getClassPK(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (MicroblogsEntry microblogsEntry : microblogsEntries) {
			JSONObject microblogsEntryJSONObject = getJSONObject(
				microblogsEntry.getMicroblogsEntryId(),
				microblogsEntry.getContent(), microblogsEntry.getModifiedDate(),
				microblogsEntry.getUserId(), microblogsEntry.getUserName(),
				themeDisplay);

			jsonArray.put(microblogsEntryJSONObject);
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("comments", jsonArray);
		jsonObject.put("commentsCount", microblogsEntries.size());

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		String actionName = ParamUtil.getString(
			actionRequest, ActionRequest.ACTION_NAME);

		try {
			if (actionName.equals("repostMicroblogsEntry")) {
				repostMicroblogsEntry(actionRequest, actionResponse);
			}
			else if (actionName.equals("updateComment")) {
				String className = ParamUtil.getString(
					actionRequest, "className");

				if (className.equals(MicroblogsEntry.class.getName())) {
					updateMicroblogsComment(actionRequest, actionResponse);
				}
				else {
					updateMBComment(actionRequest, actionResponse);
				}
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void repostMicroblogsEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long microblogsEntryId = ParamUtil.getLong(
			actionRequest, "microblogsEntryId");

		MicroblogsEntry microblogsEntry =
			MicroblogsEntryLocalServiceUtil.getMicroblogsEntry(
				microblogsEntryId);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			MicroblogsEntry.class.getName(), actionRequest);

		MicroblogsEntryServiceUtil.addMicroblogsEntry(
			themeDisplay.getUserId(), microblogsEntry.getContent(),
			MicroblogsEntryConstants.TYPE_REPOST,
			microblogsEntry.getMicroblogsEntryId(),
			microblogsEntry.getSocialRelationType(), serviceContext);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			String resourceID = resourceRequest.getResourceID();

			if (resourceID.equals("getComments")) {
				long activitySetId = ParamUtil.getLong(
					resourceRequest, "activitySetId");

				SocialActivitySet activitySet =
					SocialActivitySetLocalServiceUtil.fetchSocialActivitySet(
						activitySetId);

				if (activitySet == null) {
					return;
				}

				String className = activitySet.getClassName();

				if (className.equals(MicroblogsEntry.class.getName())) {
					getMicroblogsComments(
						activitySet, resourceRequest, resourceResponse);
				}
				else {
					getMBComments(
						activitySet, resourceRequest, resourceResponse);
				}
			}
			else {
				super.serveResource(resourceRequest, resourceResponse);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void updateMBComment(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		long mbMessageId = ParamUtil.getLong(
			actionRequest, "mbMessageIdOrMicroblogsEntryId");
		String body = ParamUtil.getString(actionRequest, "body");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long groupId = themeDisplay.getScopeGroupId();

			ServiceContextFunction serviceContextFunction =
				new ServiceContextFunction(actionRequest);

			long commentId = 0;

			if (cmd.equals(Constants.DELETE)) {
				CommentManagerUtil.deleteComment(mbMessageId);
			}
			else if (cmd.equals(Constants.EDIT) && (mbMessageId > 0)) {
				commentId = CommentManagerUtil.updateComment(
					themeDisplay.getUserId(), className, classPK, mbMessageId,
					StringPool.BLANK, body, serviceContextFunction);
			}
			else {
				commentId = CommentManagerUtil.addComment(
					themeDisplay.getUserId(), groupId, className, classPK, body,
					serviceContextFunction);
			}

			if (commentId != 0) {
				Comment comment = CommentManagerUtil.fetchComment(commentId);

				jsonObject = getJSONObject(
					comment.getCommentId(), comment.getBody(),
					comment.getModifiedDate(), comment.getUserId(),
					comment.getUserName(), themeDisplay);
			}

			jsonObject.put("success", Boolean.TRUE);
		}
		catch (Exception e) {
			jsonObject.put("success", Boolean.FALSE);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void updateMicroblogsComment(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		long microblogsEntryId = ParamUtil.getLong(
			actionRequest, "mbMessageIdOrMicroblogsEntryId");
		String body = ParamUtil.getString(actionRequest, "body");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			MicroblogsEntry microblogsEntry = null;

			if (cmd.equals(Constants.DELETE)) {
				MicroblogsEntryServiceUtil.deleteMicroblogsEntry(
					microblogsEntryId);
			}
			else if (classPK > 0) {
				MicroblogsEntry currentMicroblogsEntry =
					MicroblogsEntryLocalServiceUtil.getMicroblogsEntry(classPK);

				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(
						MicroblogsEntry.class.getName(), actionRequest);

				if (cmd.equals(Constants.EDIT) && (microblogsEntryId > 0)) {
					microblogsEntry =
						MicroblogsEntryServiceUtil.updateMicroblogsEntry(
							microblogsEntryId, body,
							currentMicroblogsEntry.getSocialRelationType(),
							serviceContext);
				}
				else {
					microblogsEntry =
						MicroblogsEntryServiceUtil.addMicroblogsEntry(
							themeDisplay.getUserId(), body,
							MicroblogsEntryConstants.TYPE_REPLY, classPK,
							currentMicroblogsEntry.getSocialRelationType(),
							serviceContext);
				}
			}

			if (microblogsEntry != null) {
				jsonObject = getJSONObject(
					microblogsEntry.getMicroblogsEntryId(),
					microblogsEntry.getContent(),
					microblogsEntry.getModifiedDate(),
					microblogsEntry.getUserId(), microblogsEntry.getUserName(),
					themeDisplay);
			}

			jsonObject.put("success", Boolean.TRUE);
		}
		catch (Exception e) {
			jsonObject.put("success", Boolean.FALSE);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	protected JSONObject getJSONObject(
			long mbMessageIdOrMicroblogsEntryId, String body, Date modifiedDate,
			long userId, String userName, ThemeDisplay themeDisplay)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("body", HtmlUtil.escape(body));

		if ((userId <= 0) || (userId != themeDisplay.getUserId())) {
			jsonObject.put("commentControlsClass", "hide");
		}

		jsonObject.put(
			"mbMessageIdOrMicroblogsEntryId", mbMessageIdOrMicroblogsEntryId);

		Format dateFormat = FastDateFormatFactoryUtil.getDate(
			DateFormat.FULL, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());

		jsonObject.put(
			"modifiedDate",
			Time.getRelativeTimeDescription(
				modifiedDate.getTime(), themeDisplay.getLocale(),
				themeDisplay.getTimeZone(), dateFormat));

		User user = UserLocalServiceUtil.fetchUser(userId);

		if (user != null) {
			jsonObject.put("userDisplayURL", user.getDisplayURL(themeDisplay));
			jsonObject.put(
				"userPortraitURL",
				HtmlUtil.escape(user.getPortraitURL(themeDisplay)));
		}

		jsonObject.put("userName", HtmlUtil.escape(userName));

		return jsonObject;
	}

}