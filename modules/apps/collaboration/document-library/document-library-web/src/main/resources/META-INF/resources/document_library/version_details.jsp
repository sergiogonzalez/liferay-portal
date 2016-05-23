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

<%@ include file="/document_library/init.jsp" %>

<%
boolean checkedOut = GetterUtil.getBoolean(request.getAttribute("edit_file_entry.jsp-checkedOut"));
%>

<div id="<portlet:namespace />versionDetails" style="display: none">
	<aui:fieldset>
		<h5 class="control-label"><liferay-ui:message key="select-whether-this-is-a-major-or-minor-version" /></h5>

		<div class="file-version-type-container">
			<aui:input checked="<%= checkedOut %>" cssClass="file-version-type" label="major-version" name="versionDetailsMajorVersion" type="radio" value="<%= true %>" />

			<aui:input checked="<%= !checkedOut %>" cssClass="file-version-type" label="minor-version" name="versionDetailsMajorVersion" type="radio" value="<%= false %>" />
		</div>

		<aui:input label="change-log" name="versionDetailsChangeLog" type="textarea" />
	</aui:fieldset>
</div>