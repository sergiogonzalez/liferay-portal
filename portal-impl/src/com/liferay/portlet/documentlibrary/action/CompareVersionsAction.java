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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.kernel.diff.DiffResult;
import com.liferay.portal.kernel.diff.DiffUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.portlet.bridges.mvc.action.ActionContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.action.MVCPortletAction;
import com.liferay.portal.kernel.portlet.bridges.mvc.action.RenderContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.action.ResourceContext;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Bruno Farache
 */
public class CompareVersionsAction implements MVCPortletAction {

	@Override
	public String processAction(
			ActionRequest actionRequest, ActionResponse actionResponse,
			ActionContext actionContext)
		throws PortletException {

		return null;
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			RenderContext renderContext)
		throws IOException, PortletException {

		try {
			compareVersions(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/html/portlet/document_library/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/html/portlet/document_library/compare_versions.jsp";
	}

	@Override
	public String serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			ResourceContext resourceContext)
		throws IOException, PortletException {

		return null;
	}

	protected void compareVersions(RenderRequest renderRequest)
		throws Exception {

		long sourceFileVersionId = ParamUtil.getLong(
			renderRequest, "sourceFileVersionId");
		long targetFileVersionId = ParamUtil.getLong(
			renderRequest, "targetFileVersionId");

		FileVersion sourceFileVersion = DLAppServiceUtil.getFileVersion(
			sourceFileVersionId);

		InputStream sourceIs = sourceFileVersion.getContentStream(false);

		String sourceExtension = sourceFileVersion.getExtension();

		if (sourceExtension.equals("css") || sourceExtension.equals("htm") ||
			sourceExtension.equals("html") || sourceExtension.equals("js") ||
			sourceExtension.equals("txt") || sourceExtension.equals("xml")) {

			String sourceContent = HtmlUtil.escape(StringUtil.read(sourceIs));

			sourceIs = new UnsyncByteArrayInputStream(
				sourceContent.getBytes(StringPool.UTF8));
		}

		FileVersion targetFileVersion = DLAppLocalServiceUtil.getFileVersion(
			targetFileVersionId);

		InputStream targetIs = targetFileVersion.getContentStream(false);

		String targetExtension = targetFileVersion.getExtension();

		if (targetExtension.equals("css") || targetExtension.equals("htm") ||
			targetExtension.equals("html") || targetExtension.equals("js") ||
			targetExtension.equals("txt") || targetExtension.equals("xml")) {

			String targetContent = HtmlUtil.escape(StringUtil.read(targetIs));

			targetIs = new UnsyncByteArrayInputStream(
				targetContent.getBytes(StringPool.UTF8));
		}

		if (DocumentConversionUtil.isEnabled()) {
			if (DocumentConversionUtil.isConvertBeforeCompare(
					sourceExtension)) {

				String sourceTempFileId = DLUtil.getTempFileId(
					sourceFileVersion.getFileEntryId(),
					sourceFileVersion.getVersion());

				sourceIs = new FileInputStream(
					DocumentConversionUtil.convert(
						sourceTempFileId, sourceIs, sourceExtension, "txt"));
			}

			if (DocumentConversionUtil.isConvertBeforeCompare(
					targetExtension)) {

				String targetTempFileId = DLUtil.getTempFileId(
					targetFileVersion.getFileEntryId(),
					targetFileVersion.getVersion());

				targetIs = new FileInputStream(
					DocumentConversionUtil.convert(
						targetTempFileId, targetIs, targetExtension, "txt"));
			}
		}

		List<DiffResult>[] diffResults = DiffUtil.diff(
			new InputStreamReader(sourceIs), new InputStreamReader(targetIs));

		renderRequest.setAttribute(
			WebKeys.SOURCE_NAME,
			sourceFileVersion.getTitle() + StringPool.SPACE +
				sourceFileVersion.getVersion());
		renderRequest.setAttribute(
			WebKeys.TARGET_NAME,
			targetFileVersion.getTitle() + StringPool.SPACE +
				targetFileVersion.getVersion());
		renderRequest.setAttribute(WebKeys.DIFF_RESULTS, diffResults);
	}

}