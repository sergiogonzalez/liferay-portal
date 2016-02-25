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

List<SocialActivity> results = null;

int count = 0;
int total = 0;

int start = ParamUtil.getInteger(request, "start");
int end = start + _DELTA;

while ((count < _DELTA) && ((results == null) || !results.isEmpty())) {
	if (group.isUser()) {
		if (!layout.isPublicLayout()) {
			if (tabs1.equals("connections")) {
				results = SocialActivityLocalServiceUtil.getRelationActivities(group.getClassPK(), SocialRelationConstants.TYPE_BI_CONNECTION, start, end);
				total = SocialActivityLocalServiceUtil.getRelationActivitiesCount(group.getClassPK(), SocialRelationConstants.TYPE_BI_CONNECTION);
			}
			else if (tabs1.equals("following")) {
				results = SocialActivityLocalServiceUtil.getRelationActivities(group.getClassPK(), SocialRelationConstants.TYPE_UNI_FOLLOWER, start, end);
				total = SocialActivityLocalServiceUtil.getRelationActivitiesCount(group.getClassPK(), SocialRelationConstants.TYPE_UNI_FOLLOWER);
			}
			else if (tabs1.equals("my-sites")) {
				results = SocialActivityLocalServiceUtil.getUserGroupsActivities(group.getClassPK(), start, end);
				total = SocialActivityLocalServiceUtil.getUserGroupsActivitiesCount(group.getClassPK());
			}
			else {
				results = SocialActivityLocalServiceUtil.getUserActivities(group.getClassPK(), start, end);
				total = SocialActivityLocalServiceUtil.getUserActivitiesCount(group.getClassPK());
			}
		}
		else {
			results = SocialActivityLocalServiceUtil.getUserActivities(group.getClassPK(), start, end);
			total = SocialActivityLocalServiceUtil.getUserActivitiesCount(group.getClassPK());
		}
	}
	else {
		results = SocialActivityLocalServiceUtil.getGroupActivities(group.getGroupId(), start, end);
		total = SocialActivityLocalServiceUtil.getGroupActivitiesCount(group.getGroupId());
	}
%>

	<%@ include file="/activities/view_activities_feed.jspf" %>

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
			<c:when test="<%= total == 0 %>">
				<liferay-ui:message key="there-are-no-activities" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="there-are-no-more-activities" />
			</c:otherwise>
		</c:choose>
	</div>
</c:if>