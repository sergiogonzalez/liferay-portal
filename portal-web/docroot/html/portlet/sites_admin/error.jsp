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

<liferay-ui:header
	backURL="javascript:history.go(-1);"
	title="error"
/>

<liferay-ui:error exception="<%= MembershipException.class %>">

	<%
	MembershipException me = (MembershipException)errorException;

	Group group = me.getGroup();

	List<User> errorUsers = me.getErrorUsers();
	%>

	<c:choose>
		<c:when test="<%= errorUsers.size() == 1 %>">
			<c:if test="<%= me.getType() == MembershipException.MEMBERSHIP_MANDATORY %>">
				<liferay-ui:message arguments="<%= new Object[] {errorUsers.get(0).getFullName(), group.getDescriptiveName(locale)} %>" key="x-is-not-allowed-to-leave-x" />
			</c:if>

			<c:if test="<%= me.getType() == MembershipException.MEMBERSHIP_NOT_ALLOWED %>">
				<liferay-ui:message arguments="<%= new Object[] {errorUsers.get(0).getFullName(), group.getDescriptiveName(locale)} %>" key="x-is-not-allowed-to-join-x" />
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="<%= me.getType() == MembershipException.MEMBERSHIP_MANDATORY %>">
				<liferay-ui:message arguments='<%= new Object[] {group.getDescriptiveName(locale), ListUtil.toString(errorUsers, "fullName", StringPool.COMMA_AND_SPACE)} %>' key="the-following-users-are-not-allowed-to-leave-site-x-x" />
			</c:if>

			<c:if test="<%= me.getType() == MembershipException.MEMBERSHIP_NOT_ALLOWED %>">
				<liferay-ui:message arguments='<%= new Object[] {group.getDescriptiveName(locale), ListUtil.toString(errorUsers, "fullName", StringPool.COMMA_AND_SPACE)} %>' key="the-following-users-are-not-allowed-to-join-site-x-x" />
			</c:if>
		</c:otherwise>
	</c:choose>
</liferay-ui:error>

<liferay-ui:error exception="<%= NoSuchGroupException.class %>" message="the-site-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchLayoutException.class %>" message="the-page-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchRoleException.class %>" message="the-role-could-not-be-found" />
<liferay-ui:error exception="<%= PrincipalException.class %>" message="you-do-not-have-the-required-permissions" />