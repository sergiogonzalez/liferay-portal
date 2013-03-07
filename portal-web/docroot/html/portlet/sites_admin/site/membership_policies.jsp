<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
Group group = (Group)request.getAttribute(WebKeys.GROUP);
%>

<aui:fieldset>
	<c:choose>
		<c:when test="<%= group != null %>">
			<aui:select label='<%= UnicodeLanguageUtil.format(pageContext, "select-membership-policy-for-x", group.getName()) %>' name="membership-policy-type">
				<aui:option label="independent-membership-policy" selected="true" value="1" />
				<aui:option label="inherit-parent-site-membership"  value="2" />
				<aui:option label="only-allow-parent-site-memberships"  value="3" />
			</aui:select>
		</c:when>
		<c:otherwise>
			<aui:select label="select-membership-policy-for-this-site" name="membership-policy-type">
				<aui:option label="independent-membership-policy" selected="true" value="1" />
				<aui:option label="inherit-parent-site-membership"  value="2" />
				<aui:option label="only-allow-parent-site-memberships"  value="3" />
			</aui:select>
		</c:otherwise>
	</c:choose>
</aui:fieldset>