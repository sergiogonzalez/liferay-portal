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

package com.liferay.portlet.blogs.util.test;

import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

/**
 * @author Zsolt Berentey
 */
public class BlogsTestUtil {

	public static BlogsEntry addEntryWithWorkflow(
			long userId, String title, boolean smallImage,
			ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			ImageSelector coverImageSelector = null;
			ImageSelector smallImageSelector = null;

			if (smallImage) {
				Class<?> clazz = BlogsTestUtil.class;

				ClassLoader classLoader = clazz.getClassLoader();

				InputStream inputStream = classLoader.getResourceAsStream(
					"com/liferay/portal/util/dependencies/test.jpg");

				FileEntry fileEntry = null;

				try {
					fileEntry = TempFileEntryUtil.getTempFileEntry(
						serviceContext.getScopeGroupId(), userId,
						BlogsEntry.class.getName(), "image.jpg");
				}
				catch (Exception e) {
					fileEntry = TempFileEntryUtil.addTempFileEntry(
						serviceContext.getScopeGroupId(), userId,
						BlogsEntry.class.getName(), "image.jpg", inputStream,
						MimeTypesUtil.getContentType("image.jpg"));
				}

				smallImageSelector = new ImageSelector(
					fileEntry.getFileEntryId(), StringPool.BLANK, null);
			}

			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
				userId, title, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				new Date(), true, true, new String[0], coverImageSelector,
				smallImageSelector, serviceContext);

			return updateStatus(entry, serviceContext);
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	public static void assertEquals(
		BlogsEntry expectedEntry, BlogsEntry actualEntry) {

		Assert.assertEquals(expectedEntry.getUserId(), actualEntry.getUserId());
		Assert.assertEquals(expectedEntry.getTitle(), actualEntry.getTitle());
		Assert.assertEquals(
			expectedEntry.getDescription(), actualEntry.getDescription());
		Assert.assertEquals(
			expectedEntry.getContent(), actualEntry.getContent());
		Assert.assertEquals(
			expectedEntry.getDisplayDate(), actualEntry.getDisplayDate());
		Assert.assertEquals(
			expectedEntry.isAllowPingbacks(), actualEntry.isAllowPingbacks());
		Assert.assertEquals(
			expectedEntry.isAllowTrackbacks(), actualEntry.isAllowTrackbacks());
		Assert.assertEquals(
			expectedEntry.isSmallImage(), actualEntry.isSmallImage());
	}

	public static String getTempBlogsEntryAttachmentFileEntryImgTag(
		long dataImageId, String url) {

		StringBundler sb = new StringBundler(7);

		sb.append("<img ");
		sb.append(EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);
		sb.append("=\"");
		sb.append(dataImageId);
		sb.append("\" src=\"");
		sb.append(url);
		sb.append("\"/>");

		return sb.toString();
	}

	public static BlogsEntry updateEntryWithWorkflow(
			BlogsEntry entry, String title, ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setCommand(Constants.UPDATE);
			serviceContext.setLayoutFullURL("http://localhost");
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			entry = BlogsEntryLocalServiceUtil.updateEntry(
				entry.getUserId(), entry.getEntryId(), title,
				RandomTestUtil.randomString(), serviceContext);

			return updateStatus(entry, serviceContext);
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	protected static BlogsEntry updateStatus(
			BlogsEntry entry, ServiceContext serviceContext)
		throws Exception {

		Map<String, Serializable> workflowContext = new HashMap<>();

		workflowContext.put(WorkflowConstants.CONTEXT_URL, "http://localhost");
		workflowContext.put(
			WorkflowConstants.CONTEXT_USER_PORTRAIT_URL, "http://localhost");
		workflowContext.put(
			WorkflowConstants.CONTEXT_USER_URL, "http://localhost");

		return BlogsEntryLocalServiceUtil.updateStatus(
			entry.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext, workflowContext);
	}

}