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
GoogleDriveFileReference googleDriveFileReference = (GoogleDriveFileReference)request.getAttribute(GoogleDriveOpenerWebKeys.GOOGLE_DRIVE_FILE_REFERENCE);
%>

<!DOCTYPE html>
<html>
	<head>
		<style type="text/css">
			body {
				margin-left: 0;
			}

			iframe {
				height: 100vh;
				width: 100vw;
			}

			#closeAndCheckinBtn {
				float: right;
			}
		</style>
	</head>

	<body>
		<div>
			<button id="closeAndCheckinBtn" type="button">
				Check in and close Google Docs
			</button>
		</div>

		<div>
			<iframe frameborder="0" id="<portlet:namespace />gDocsIFrame" src="<%= googleDriveFileReference.getUrl() %>">
			</iframe>
		</div>

		<portlet:actionURL name="/document_library/edit_in_google_docs" var="checkInURL">
			<portlet:param name="fileEntryId" value="<%= String.valueOf(googleDriveFileReference.getFileEntryId()) %>" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= GoogleDriveOpenerWebConstants.GOOGLE_DRIVE_CHECKIN %>" />
		</portlet:actionURL>

		<script type="application/javascript">
			(function() {
				var btn = document.getElementById("closeAndCheckinBtn");

				btn.onclick = function() {
					var xhr = new XMLHttpRequest();

					xhr.onreadystatechange = function() {
						if (this.readyState == XMLHttpRequest.DONE) {
							window.close();
						}
					};

					xhr.open('POST', '<%= checkInURL %>');
					xhr.send();
				};
			})();
		</script>
	</body>
</html>