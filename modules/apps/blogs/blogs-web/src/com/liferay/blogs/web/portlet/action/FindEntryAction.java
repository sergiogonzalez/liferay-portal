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

package com.liferay.blogs.web.portlet.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.struts.FindActionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.PDFProcessorUtil;
import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, property = "path=/document_library/oembed",
	service = StrutsAction.class
)
public class FindEntryAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String callback = ParamUtil.getString(request, "callback");
		String url = ParamUtil.getString(request, "url");

		url = HttpUtil.decodeURL(url);

		String[] pathArray = StringUtil.split(
			url.substring(url.indexOf("/documents")), StringPool.SLASH);

		if (pathArray.length != 6) {
			return null;
		}

		long groupId = GetterUtil.getLong(pathArray[2]);
		String uuid = pathArray[5];

		FileEntry fileEntry = DLAppServiceUtil.getFileEntryByUuidAndGroupId(
			uuid, groupId);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		FileVersion fileVersion = fileEntry.getFileVersion();

		String previewFileURL = DLUtil.getPreviewURL(
			fileEntry, fileVersion, null, StringPool.BLANK,
			false, false);

		String random = StringUtil.randomString();

		boolean hasPDFImages = PDFProcessorUtil.hasImages(fileVersion);

		if (!hasPDFImages) {
			return null;
		}

		int previewFileCount = PDFProcessorUtil.getPreviewFileCount(fileVersion);

		String html = StringUtil.replace(_TEMPLATE, "{random}", random);
		html = StringUtil.replace(html, "{previewFileURL}", previewFileURL);
		html = StringUtil.replace(
			html, "{previewFileCount}", String.valueOf(previewFileCount));

		jsonObject.put("title", fileEntry.getTitle());
		jsonObject.put("userName", fileEntry.getUserName());
		jsonObject.put(
			"html", html);

		response.setContentType(ContentTypes.APPLICATION_JSON);

		StringBundler sb = new StringBundler(6);

		sb.append(callback);
		sb.append(" && ");
		sb.append(callback);
		sb.append(" (");
		sb.append(jsonObject.toString());
		sb.append(");");

		ServletResponseUtil.write(response, sb.toString());

		return null;
	}

	private static final String _TEMPLATE =
		"<div class=\"lfr-preview-file\" id=\"{random}previewFile\">" +
			"<div class=\"lfr-preview-file-content\" id=\"{random}previewFileContent\">" +
				"<div class=\"lfr-preview-file-image-current-column\">" +
					"<div class=\"lfr-preview-file-image-container\">" +
						"<img alt=\"preview\" class=\"lfr-preview-file-image-current\" id=\"{random}previewFileImage\" src=\"{previewFileURL}1\" />" +
					"</div>" +
					"<span class=\"hide lfr-preview-file-actions\" id=\"{random}previewFileActions\">" +
						"<span class=\"lfr-preview-file-toolbar\" id=\"{random}previewToolbar\"></span>" +
						"<span class=\"lfr-preview-file-info\">" +
							"<span class=\"lfr-preview-file-index\" id=\"{random}previewFileIndex\">1</span> of <span class=\"lfr-preview-file-count\">{previewFileCount}</span>" +
						"</span>" +
					"</span>" +
				"</div>" +
				"<div class=\"lfr-preview-file-images\" id=\"{random}previewImagesContent\">" +
					"<div class=\"lfr-preview-file-images-content\"></div>" +
				"</div>" +
			"</div>" +
		"</div>" +
		"<script>" +
			"AUI().use('liferay-preview', function(A) {" +
				"new Liferay.Preview(" +
					"{" +
						"actionContent: '#{random}previewFileActions'," +
						"baseImageURL: '{previewFileURL}'," +
						"boundingBox: '#{random}previewFile'," +
						"contentBox: '#{random}previewFileContent'," +
						"currentPreviewImage: '#{random}previewFileImage'," +
						"imageListContent: '#{random}previewImagesContent'," +
						"maxIndex: {previewFileCount}," +
						"previewFileIndexNode: '#{random}previewFileIndex'," +
						"toolbar: '#{random}previewToolbar'" +
					"}" +
				").render();" +
			"});" +
		"</script>";

}