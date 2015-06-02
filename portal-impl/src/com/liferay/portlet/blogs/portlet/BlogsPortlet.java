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

package com.liferay.portlet.blogs.portlet;

import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.spring.transaction.TransactionAttributeBuilder;
import com.liferay.portal.spring.transaction.TransactionHandlerUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.blogs.BlogsEntryAttachmentFileEntryHelper;
import com.liferay.portlet.blogs.BlogsEntryAttachmentFileEntryReference;
import com.liferay.portlet.blogs.EntryDescriptionException;
import com.liferay.portlet.blogs.NoSuchEntryException;
import com.liferay.portlet.blogs.action.ActionUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;
import com.liferay.portlet.documentlibrary.FileSizeException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author Adolfo Pérez
 */
public class BlogsPortlet extends MVCPortlet {

	public void editEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Throwable {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		doUpdateEntry(actionRequest, actionResponse, cmd);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			ActionUtil.getEntry(renderRequest);

			if (PropsValues.BLOGS_PINGBACK_ENABLED) {
				BlogsEntry entry = (BlogsEntry)renderRequest.getAttribute(
					WebKeys.BLOGS_ENTRY);

				if ((entry != null) && entry.isAllowPingbacks()) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(renderResponse);

					response.addHeader(
						"X-Pingback",
						PortalUtil.getPortalURL(renderRequest) +
							"/xmlrpc/pingback");
				}
			}

			long assetCategoryId = ParamUtil.getLong(
				renderRequest, "categoryId");
			String assetCategoryName = ParamUtil.getString(
				renderRequest, "tag");

			if ((assetCategoryId > 0) ||
				Validator.isNotNull(assetCategoryName)) {

				include(
					"/html/portlet/blogs/view.jsp", renderRequest,
					renderResponse);

				return;
			}

			super.render(renderRequest, renderResponse);
		}
		catch (NoSuchEntryException | PrincipalException e) {
			SessionErrors.add(renderRequest, e.getClass());

			include(
				"/html/portlet/blogs/error.jsp", renderRequest, renderResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected void doUpdateEntry(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String cmd)
		throws Throwable {

		handleUploadException(actionRequest);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		String portletId = HttpUtil.getParameter(redirect, "p_p_id", false);

		Callable<UpdateEntryResult> updateEntryCallable =
			new UpdateEntryCallable(actionRequest);

		UpdateEntryResult updateEntryResult = TransactionHandlerUtil.invoke(
			_transactionAttribute, updateEntryCallable);

		String oldUrlTitle = updateEntryResult.getOldUrlTitle();

		boolean updateRedirect = false;

		if (Validator.isNotNull(oldUrlTitle)) {
			String oldRedirectParam =
				PortalUtil.getPortletNamespace(portletId) + "redirect";

			String oldRedirect = HttpUtil.getParameter(
				redirect, oldRedirectParam, false);

			if (Validator.isNotNull(oldRedirect)) {
				BlogsEntry entry = updateEntryResult.getEntry();

				String newRedirect = HttpUtil.decodeURL(oldRedirect);

				newRedirect = StringUtil.replace(
					newRedirect, oldUrlTitle, entry.getUrlTitle());
				newRedirect = StringUtil.replace(
					newRedirect, oldRedirectParam, "redirect");

				redirect = StringUtil.replace(
					redirect, oldRedirect, newRedirect);
			}
			else if (redirect.endsWith("/blogs/" + oldUrlTitle) ||
					 redirect.contains("/blogs/" + oldUrlTitle + "?") ||
					 redirect.contains("/blog/" + oldUrlTitle + "?")) {

				BlogsEntry entry = updateEntryResult.getEntry();

				redirect = StringUtil.replace(
					redirect, oldUrlTitle, entry.getUrlTitle());
			}

			updateRedirect = true;
		}

		boolean ajax = ParamUtil.getBoolean(actionRequest, "ajax");

		if (ajax) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			List<BlogsEntryAttachmentFileEntryReference>
				blogsEntryAttachmentFileEntryReferences =
					updateEntryResult.
						getBlogsEntryAttachmentFileEntryReferences();

			for (BlogsEntryAttachmentFileEntryReference
				blogsEntryAttachmentFileEntryReference :
				blogsEntryAttachmentFileEntryReferences) {

				JSONObject blogsEntryFileEntryReferencesJSONObject =
					JSONFactoryUtil.createJSONObject();

				blogsEntryFileEntryReferencesJSONObject.put(
					"attributeDataImageId",
					EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);
				blogsEntryFileEntryReferencesJSONObject.put(
					"fileEntryId",
					String.valueOf(
						blogsEntryAttachmentFileEntryReference.
							getTempBlogsEntryAttachmentFileEntryId()));
				blogsEntryFileEntryReferencesJSONObject.put(
					"fileEntryUrl",
					PortletFileRepositoryUtil.getPortletFileEntryURL(
						null,
						blogsEntryAttachmentFileEntryReference.
							getBlogsEntryAttachmentFileEntry(),
						StringPool.BLANK));

				jsonArray.put(blogsEntryFileEntryReferencesJSONObject);
			}

			jsonObject.put("blogsEntryAttachmentReferences", jsonArray);

			BlogsEntry entry = updateEntryResult.getEntry();

			jsonObject.put("entryId", entry.getEntryId());
			jsonObject.put("redirect", redirect);
			jsonObject.put("updateRedirect", updateRedirect);

			writeJSON(actionRequest, actionResponse, jsonObject);

			return;
		}

		BlogsEntry entry = updateEntryResult.getEntry();

		int workflowAction = ParamUtil.getInteger(
			actionRequest, "workflowAction",
			WorkflowConstants.ACTION_SAVE_DRAFT);

		if (workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT) {
			redirect = getSaveAndContinueRedirect(
				getPortletConfig(), actionRequest, entry, redirect);

			actionResponse.sendRedirect(redirect);
		}

		WindowState windowState = actionRequest.getWindowState();

		if (!windowState.equals(LiferayWindowState.POP_UP)) {
			actionResponse.sendRedirect(redirect);
		}
		else {
			redirect = PortalUtil.escapeRedirect(redirect);

			if (Validator.isNotNull(redirect)) {
				if (cmd.equals(Constants.ADD)) {
					String namespace = PortalUtil.getPortletNamespace(
						portletId);

					redirect = HttpUtil.addParameter(
						redirect, namespace + "className",
						BlogsEntry.class.getName());
					redirect = HttpUtil.addParameter(
						redirect, namespace + "classPK", entry.getEntryId());
				}

				actionResponse.sendRedirect(redirect);
			}
		}
	}

	protected String getSaveAndContinueRedirect(
			PortletConfig portletConfig, ActionRequest actionRequest,
			BlogsEntry entry, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String backURL = ParamUtil.getString(actionRequest, "backURL");

		PortletURLImpl portletURL = new PortletURLImpl(
			actionRequest, portletConfig.getPortletName(),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		String portletName = portletConfig.getPortletName();

		if (portletName.equals(PortletKeys.BLOGS_ADMIN)) {
			portletURL.setParameter("struts_action", "/blogs_admin/edit_entry");
		}
		else {
			portletURL.setParameter("struts_action", "/blogs/edit_entry");
		}

		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter("backURL", backURL, false);
		portletURL.setParameter(
			"groupId", String.valueOf(entry.getGroupId()), false);
		portletURL.setParameter(
			"entryId", String.valueOf(entry.getEntryId()), false);
		portletURL.setWindowState(actionRequest.getWindowState());

		return portletURL.toString();
	}

	protected void handleUploadException(PortletRequest portletRequest)
		throws PortalException {

		UploadException uploadException =
			(UploadException)portletRequest.getAttribute(
				WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			if (uploadException.isExceededLiferayFileItemSizeLimit()) {
				throw new LiferayFileItemException();
			}
			else if (uploadException.isExceededSizeLimit()) {
				throw new FileSizeException(uploadException.getCause());
			}

			throw new PortalException(uploadException.getCause());
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof NoSuchEntryException ||
			cause instanceof PrincipalException) {

			return true;
		}

		return false;
	}

	protected UpdateEntryResult updateEntry(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long entryId = ParamUtil.getLong(portletRequest, "entryId");

		String title = ParamUtil.getString(portletRequest, "title");
		String subtitle = ParamUtil.getString(portletRequest, "subtitle");

		String description = StringPool.BLANK;

		boolean customAbstract = ParamUtil.getBoolean(
			portletRequest, "customAbstract");

		if (customAbstract) {
			description = ParamUtil.getString(portletRequest, "description");

			if (Validator.isNull(description)) {
				throw new EntryDescriptionException();
			}
		}

		String content = ParamUtil.getString(portletRequest, "content");

		int displayDateMonth = ParamUtil.getInteger(
			portletRequest, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(
			portletRequest, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(
			portletRequest, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(
			portletRequest, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(
			portletRequest, "displayDateMinute");
		int displayDateAmPm = ParamUtil.getInteger(
			portletRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		boolean allowPingbacks = ParamUtil.getBoolean(
			portletRequest, "allowPingbacks");
		boolean allowTrackbacks = ParamUtil.getBoolean(
			portletRequest, "allowTrackbacks");
		String[] trackbacks = StringUtil.split(
			ParamUtil.getString(portletRequest, "trackbacks"));

		long coverImageFileEntryId = ParamUtil.getLong(
			portletRequest, "coverImageFileEntryId");
		String coverImageURL = ParamUtil.getString(
			portletRequest, "coverImageURL");
		String coverImageFileEntryCropRegion = ParamUtil.getString(
			portletRequest, "coverImageFileEntryCropRegion");

		String coverImageCaption = ParamUtil.getString(
			portletRequest, "coverImageCaption");

		ImageSelector coverImageImageSelector = new ImageSelector(
			coverImageFileEntryId, coverImageURL,
			coverImageFileEntryCropRegion);

		long smallImageFileEntryId = ParamUtil.getLong(
			portletRequest, "smallImageFileEntryId");
		String smallImageURL = ParamUtil.getString(
			portletRequest, "smallImageURL");

		ImageSelector smallImageImageSelector = new ImageSelector(
			smallImageFileEntryId, smallImageURL, null);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			BlogsEntry.class.getName(), portletRequest);

		BlogsEntry entry = null;
		String oldUrlTitle = StringPool.BLANK;
		List<BlogsEntryAttachmentFileEntryReference>
			blogsEntryAttachmentFileEntryReferences = new ArrayList<>();

		if (entryId <= 0) {

			// Add entry

			entry = BlogsEntryServiceUtil.addEntry(
				title, subtitle, description, content, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
				coverImageCaption, coverImageImageSelector,
				smallImageImageSelector, serviceContext);

			BlogsEntryAttachmentFileEntryHelper
				blogsEntryAttachmentFileEntryHelper =
					new BlogsEntryAttachmentFileEntryHelper();

			List<FileEntry> tempBlogsEntryAttachments =
				blogsEntryAttachmentFileEntryHelper.
					getTempBlogsEntryAttachmentFileEntries(content);

			if (!tempBlogsEntryAttachments.isEmpty()) {
				blogsEntryAttachmentFileEntryReferences =
					blogsEntryAttachmentFileEntryHelper.
						addBlogsEntryAttachmentFileEntries(
							entry.getGroupId(), themeDisplay.getUserId(),
							entry.getEntryId(), tempBlogsEntryAttachments);

				content = blogsEntryAttachmentFileEntryHelper.updateContent(
					content, blogsEntryAttachmentFileEntryReferences);

				entry.setContent(content);

				BlogsEntryLocalServiceUtil.updateBlogsEntry(entry);
			}

			for (FileEntry tempBlogsEntryAttachment :
					tempBlogsEntryAttachments) {

				PortletFileRepositoryUtil.deletePortletFileEntry(
					tempBlogsEntryAttachment.getFileEntryId());
			}
		}
		else {

			// Update entry

			boolean sendEmailEntryUpdated = ParamUtil.getBoolean(
				portletRequest, "sendEmailEntryUpdated");

			serviceContext.setAttribute(
				"sendEmailEntryUpdated", sendEmailEntryUpdated);

			String emailEntryUpdatedComment = ParamUtil.getString(
				portletRequest, "emailEntryUpdatedComment");

			serviceContext.setAttribute(
				"emailEntryUpdatedComment", emailEntryUpdatedComment);

			entry = BlogsEntryLocalServiceUtil.getEntry(entryId);

			String tempOldUrlTitle = entry.getUrlTitle();

			BlogsEntryAttachmentFileEntryHelper blogsEntryAttachmentHelper =
				new BlogsEntryAttachmentFileEntryHelper();

			List<FileEntry> tempBlogsEntryAttachmentFileEntries =
				blogsEntryAttachmentHelper.
					getTempBlogsEntryAttachmentFileEntries(content);

			if (!tempBlogsEntryAttachmentFileEntries.isEmpty()) {
				blogsEntryAttachmentFileEntryReferences =
					blogsEntryAttachmentHelper.
						addBlogsEntryAttachmentFileEntries(
							entry.getGroupId(), themeDisplay.getUserId(),
							entry.getEntryId(),
							tempBlogsEntryAttachmentFileEntries);

				content = blogsEntryAttachmentHelper.updateContent(
					content, blogsEntryAttachmentFileEntryReferences);
			}

			entry = BlogsEntryServiceUtil.updateEntry(
				entryId, title, subtitle, description, content,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, allowPingbacks,
				allowTrackbacks, trackbacks, coverImageCaption,
				coverImageImageSelector, smallImageImageSelector,
				serviceContext);

			for (FileEntry tempBlogsEntryAttachmentFileEntry :
					tempBlogsEntryAttachmentFileEntries) {

				PortletFileRepositoryUtil.deletePortletFileEntry(
					tempBlogsEntryAttachmentFileEntry.getFileEntryId());
			}

			if (!tempOldUrlTitle.equals(entry.getUrlTitle())) {
				oldUrlTitle = tempOldUrlTitle;
			}
		}

		return new UpdateEntryResult(
			entry, oldUrlTitle, blogsEntryAttachmentFileEntryReferences);
	}

	private final TransactionAttribute _transactionAttribute =
		TransactionAttributeBuilder.build(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	private static class UpdateEntryResult {

		public List<BlogsEntryAttachmentFileEntryReference>
			getBlogsEntryAttachmentFileEntryReferences() {

			return _blogsEntryAttachmentFileEntryReferences;
		}

		public BlogsEntry getEntry() {
			return _entry;
		}

		public String getOldUrlTitle() {
			return _oldUrlTitle;
		}

		private UpdateEntryResult(
			BlogsEntry entry, String oldUrlTitle,
			List<BlogsEntryAttachmentFileEntryReference>
				blogsEntryAttachmentFileEntryReferences) {

			_entry = entry;
			_oldUrlTitle = oldUrlTitle;
			_blogsEntryAttachmentFileEntryReferences =
				blogsEntryAttachmentFileEntryReferences;
		}

		private final List<BlogsEntryAttachmentFileEntryReference>
			_blogsEntryAttachmentFileEntryReferences;
		private final BlogsEntry _entry;
		private final String _oldUrlTitle;

	}

	private class UpdateEntryCallable implements Callable<UpdateEntryResult> {

		@Override
		public UpdateEntryResult call() throws Exception {
			return updateEntry(_portletRequest);
		}

		private UpdateEntryCallable(PortletRequest portletRequest) {
			_portletRequest = portletRequest;
		}

		private final PortletRequest _portletRequest;

	}

}