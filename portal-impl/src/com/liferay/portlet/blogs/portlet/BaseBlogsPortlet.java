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
import com.liferay.portal.kernel.sanitizer.SanitizerException;
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
import com.liferay.portal.model.TrashedModel;
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
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.blogs.BlogsEntryAttachmentFileEntryHelper;
import com.liferay.portlet.blogs.BlogsEntryAttachmentFileEntryReference;
import com.liferay.portlet.blogs.EntryContentException;
import com.liferay.portlet.blogs.EntryDescriptionException;
import com.liferay.portlet.blogs.EntryDisplayDateException;
import com.liferay.portlet.blogs.EntrySmallImageNameException;
import com.liferay.portlet.blogs.EntrySmallImageSizeException;
import com.liferay.portlet.blogs.EntryTitleException;
import com.liferay.portlet.blogs.NoSuchEntryException;
import com.liferay.portlet.blogs.action.ActionUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;
import com.liferay.portlet.trash.util.TrashUtil;

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
public class BaseBlogsPortlet extends MVCPortlet {

	public void deleteEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		boolean moveToTrash = true;

		if (cmd.equals(Constants.DELETE)) {
			moveToTrash = false;
		}

		deleteEntries(actionRequest, moveToTrash);
	}

	@Override
	public void doDispatch(
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

			super.doDispatch(renderRequest, renderResponse);
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

	public void editEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Throwable {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		doUpdateEntry(actionRequest, actionResponse, cmd);
	}

	public void restoreTrashEntries(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] restoreTrashEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		for (long restoreTrashEntryId : restoreTrashEntryIds) {
			TrashEntryServiceUtil.restoreEntry(restoreTrashEntryId);
		}
	}

	protected void deleteEntries(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long[] deleteEntryIds = null;

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		if (entryId > 0) {
			deleteEntryIds = new long[] {entryId};
		}
		else {
			deleteEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteEntryIds"), 0L);
		}

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (long deleteEntryId : deleteEntryIds) {
			if (moveToTrash) {
				BlogsEntry entry = BlogsEntryServiceUtil.moveEntryToTrash(
					deleteEntryId);

				trashedModels.add(entry);
			}
			else {
				BlogsEntryServiceUtil.deleteEntry(deleteEntryId);
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
			TrashUtil.addTrashSessionMessages(actionRequest, trashedModels);

			hideDefaultSuccessMessage(actionRequest);
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

		boolean updateRedirect = Validator.isNotNull(oldUrlTitle);

		if (updateRedirect) {
			redirect = updateRedirect(
				portletId, oldUrlTitle, redirect, updateEntryResult);
		}

		boolean ajax = ParamUtil.getBoolean(actionRequest, "ajax");

		if (ajax) {
			sendAjaxResponse(
				actionRequest, actionResponse, redirect, updateRedirect,
				updateEntryResult);

			return;
		}

		redirect = getSaveRedirect(
			actionRequest, redirect, cmd, portletId, updateEntryResult);

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
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
			portletURL.setParameter(
				"mvcPath", "/html/portlet/blogs_admin/edit_entry.jsp");
		}
		else {
			portletURL.setParameter(
				"mvcPath", "/html/portlet/blogs/edit_entry.jsp");
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

	protected String getSaveRedirect(
			ActionRequest actionRequest, String redirect, String cmd,
			String portletId, UpdateEntryResult updateEntryResult)
		throws Exception {

		BlogsEntry entry = updateEntryResult.getEntry();

		int workflowAction = ParamUtil.getInteger(
			actionRequest, "workflowAction",
			WorkflowConstants.ACTION_SAVE_DRAFT);

		if (workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT) {
			redirect = getSaveAndContinueRedirect(
				getPortletConfig(), actionRequest, entry, redirect);
		}

		WindowState windowState = actionRequest.getWindowState();

		if (windowState.equals(LiferayWindowState.POP_UP)) {
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
			}
		}

		return redirect;
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
		if (cause instanceof AssetCategoryException ||
			cause instanceof AssetTagException ||
			cause instanceof EntryContentException ||
			cause instanceof EntryDescriptionException ||
			cause instanceof EntryDisplayDateException ||
			cause instanceof EntrySmallImageNameException ||
			cause instanceof EntrySmallImageSizeException ||
			cause instanceof EntryTitleException ||
			cause instanceof FileSizeException ||
			cause instanceof LiferayFileItemException ||
			cause instanceof NoSuchEntryException ||
			cause instanceof PrincipalException ||
			cause instanceof SanitizerException) {

			return true;
		}

		Throwable innerCause = cause.getCause();

		if (innerCause instanceof SanitizerException) {
			return true;
		}

		return false;
	}

	protected void sendAjaxResponse(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String redirect, boolean updateRedirect,
			UpdateEntryResult updateEntryResult)
		throws IOException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<BlogsEntryAttachmentFileEntryReference>
			blogsEntryAttachmentFileEntryReferences =
				updateEntryResult.getBlogsEntryAttachmentFileEntryReferences();

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

	protected String updateRedirect(
		String portletId, String oldUrlTitle, String redirect,
		UpdateEntryResult updateEntryResult) {

		BlogsEntry entry = updateEntryResult.getEntry();

		String oldRedirectParam =
			PortalUtil.getPortletNamespace(portletId) + "redirect";

		String oldRedirect = HttpUtil.getParameter(
			redirect, oldRedirectParam, false);

		if (Validator.isNotNull(oldRedirect)) {
			String newRedirect = HttpUtil.decodeURL(oldRedirect);

			newRedirect = StringUtil.replace(
				newRedirect, oldUrlTitle, entry.getUrlTitle());
			newRedirect = StringUtil.replace(
				newRedirect, oldRedirectParam, "redirect");

			return StringUtil.replace(redirect, oldRedirect, newRedirect);
		}
		else if (redirect.endsWith("/blogs/" + oldUrlTitle) ||
				 redirect.contains("/blogs/" + oldUrlTitle + "?") ||
				 redirect.contains("/blog/" + oldUrlTitle + "?")) {

			return StringUtil.replace(
				redirect, oldUrlTitle, entry.getUrlTitle());
		}

		return redirect;
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