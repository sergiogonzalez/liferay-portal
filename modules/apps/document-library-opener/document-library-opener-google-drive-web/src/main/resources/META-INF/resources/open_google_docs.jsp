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

<%@ include file="/init.jsp" %>

<%
DLOpenerGoogleDriveFileReference googleDriveFileReference = (DLOpenerGoogleDriveFileReference)request.getAttribute(DLOpenerGoogleDriveWebKeys.DL_OPENER_GOOGLE_DRIVE_FILE_REFERENCE);
%>

<!DOCTYPE html>
<html>
<head>
	<meta content="initial-scale=1.0, width=device-width" name="viewport">
	<style type="text/css">
		body {
			overflow: hidden;
		}

		.google-docs-toolbar {
			background-color: #0b5fff;
			color: #fff;
			height: 3rem;
			max-height: 3rem;
		}

		.google-docs-iframe {
			height: calc(100vh - 3rem);
			width: 100%;
		}
	</style>

	<link href="/o/frontend-css-web/main.css?browserId=other&amp;themeId=admin_WAR_admintheme&amp;languageId=en_US&amp;b=7100&amp;t=1535541653363" id="liferayPortalCSS" rel="stylesheet" type="text/css" />
	<link href="/o/admin-theme/css/clay.css?browserId=other&themeId=admin_WAR_admintheme&languageId=en_US&b=7100&t=1535546086145" id="liferayPortalCSS" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="autofit-padded autofit-row autofit-row-center google-docs-toolbar">
	<div class="autofit-col autofit-col-expand">
		<div class="autofit-section">
			<clay:button
				icon="angle-left"
				id="closeAndCheckinBtn"
				label='<%= LanguageUtil.format(resourceBundle, "save-and-return-to-x", themeDisplay.getSiteGroupName()) %>'
				size="sm"
			/>
		</div>
	</div>

	<div class="autofit-col">
		<clay:button
			label='<%= LanguageUtil.get(resourceBundle, "discard-changes") %>'
			size="sm"
		/>
	</div>
</div>

<iframe class="google-docs-iframe" frameborder="0" id="<portlet:namespace />gDocsIFrame" src="<%= googleDriveFileReference.getGoogleDocsEditURL() %>"></iframe>

<portlet:actionURL name="/document_library/edit_in_google_docs" var="checkInURL">
	<portlet:param name="fileEntryId" value="<%= String.valueOf(googleDriveFileReference.getFileEntryId()) %>" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= DLOpenerGoogleDriveWebConstants.GOOGLE_DRIVE_CHECKIN %>" />
</portlet:actionURL>

<script type="application/javascript">
	(function() {
		var btn = document.getElementById("closeAndCheckinBtn");

		btn.onclick = function() {
			fetch(
				'<%= checkInURL %>',
				{
					credentials: 'include',
					method: 'POST'
				}
			).then(function(response) {
				if (response.ok) {
					window.close();
				}
			});
		};
	})();
</script>
</body>
</html>