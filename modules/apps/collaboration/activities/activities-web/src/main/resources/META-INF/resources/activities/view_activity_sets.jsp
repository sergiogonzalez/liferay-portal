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

<%@ include file="/activities/init.jsp" %>

<%
Group group = themeDisplay.getScopeGroup();

List<SocialActivitySet> results = null;

int count = 0;

int start = ParamUtil.getInteger(request, "start");
int end = start + _DELTA;

while ((count < _DELTA) && ((results == null) || !results.isEmpty())) {
	results = activitiesDisplayContext.getSocialActivitySets(start, end);
%>

	<%@ include file="/activities/view_activity_sets_feed.jspf" %>

<%
	end = start + _DELTA;
}
%>

<aui:script>
	<portlet:namespace />start = <%= start %>;
</aui:script>

<c:if test="<%= results.isEmpty() %>">
	<div class="no-activities">
		<c:choose>
			<c:when test="<%= activitiesDisplayContext.getSocialActivitySetsCount() == 0 %>">
				<liferay-ui:message key="there-are-no-activities" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="there-are-no-more-activities" />
			</c:otherwise>
		</c:choose>
	</div>
</c:if>