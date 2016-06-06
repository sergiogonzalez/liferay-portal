<%--
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
--%>

<%@ include file="/admin/common/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

List<FileEntry> attachmentsFileEntries = new ArrayList<FileEntry>();

if (kbArticle != null) {
	attachmentsFileEntries = kbArticle.getAttachmentsFileEntries();
}
%>

<c:if test="<%= !attachmentsFileEntries.isEmpty() %>">
	<div class="kb-attachments">
		<p><liferay-ui:message key="attachments" />:</p>

		<%
		DLMimeTypeDisplayContext dlMimeTypeDisplayContext = (DLMimeTypeDisplayContext)request.getAttribute(KBWebKeys.DL_MIME_TYPE_DISPLAY_CONTEXT);

		for (FileEntry fileEntry : attachmentsFileEntries) {
			String clipURL = DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK);
		%>

			<div class="col-md-4">
				<div class="card card-horizontal">
					<div class="card-row card-row-padded">
						<div class="card-col-field">
							<span class="sticker sticker-static <%= (dlMimeTypeDisplayContext != null) ? dlMimeTypeDisplayContext.getCssClassFileMimeType(fileEntry.getMimeType()) : "file-icon-color-0" %>">
								<%= StringUtil.shorten(StringUtil.upperCase(fileEntry.getExtension()), 3, StringPool.BLANK) %>
							</span>
						</div>

						<div class="card-col-content card-col-gutters">
							<aui:a href="<%= clipURL.toString() %>">
								<%= HtmlUtil.escape(fileEntry.getTitle()) %>
							</aui:a>
						</div>
					</div>
				</div>
			</div>

		<%
		}
		%>

	</div>
</c:if>