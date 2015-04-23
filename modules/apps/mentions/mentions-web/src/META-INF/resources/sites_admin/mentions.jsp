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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

<%@ page import="com.liferay.mentions.constants.MentionsWebKeys" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>

<%
boolean groupMentionsEnabled = GetterUtil.getBoolean(request.getAttribute(MentionsWebKeys.GROUP_MENTIONS_ENABLED));
boolean companyMentionsEnabled = GetterUtil.getBoolean(request.getAttribute(MentionsWebKeys.COMPANY_MENTIONS_ENABLED));
%>

<h3><liferay-ui:message key="mentions" /></h3>

<c:if test="<%= !companyMentionsEnabled %>">
	<div class="alert alert-warning">
		<liferay-ui:message key="mentions-are-disabled-in-the-portal" />
	</div>
</c:if>

<aui:input checked="<%= groupMentionsEnabled %>" disabled="<%= !companyMentionsEnabled %>" label="enable-mentions" name="TypeSettingsProperties--mentionsEnabled--" type="checkbox" value="<%= groupMentionsEnabled %>" />