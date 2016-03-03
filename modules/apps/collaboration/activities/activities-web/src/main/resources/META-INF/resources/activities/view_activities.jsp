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
List<SocialActivity> results = activitiesDisplayContext.getSocialActivities();
%>

<%
for (SocialActivity activity : results) {
	SocialActivityFeedEntry activityFeedEntry = activitiesDisplayContext.getSocialActivityFeedEntry(activity);

	if (activityFeedEntry == null) {
		continue;
	}
%>

	<h3><liferay-ui:message key="<%= dateFormatDate.format(activity.getCreateDate()) %>" /></h3>

	<div class="activity-item clearfix">
		<liferay-ui:user-display
			showUserDetails="<%= false %>"
			showUserName="<%= false %>"
			userId="<%= activity.getUserId() %>"
		>
			<liferay-portlet:icon-portlet portlet="<%= activitiesDisplayContext.getSocialActivityFeedEntryPortlet(activityFeedEntry) %>" />
		</liferay-ui:user-display>

		<div class="activity-title">
			<%= activityFeedEntry.getTitle() %>
		</div>

		<div class="activity-block">
			<div class="activity-body">
				<span class="time">
					<%= timeFormatDate.format(activity.getCreateDate()) %>
				</span>

				<%= activityFeedEntry.getBody() %>
			</div>
		</div>
	</div>

<%
}
%>

<aui:script>
	<portlet:namespace />start = <%= activitiesRequestHelper.getStart() + results.size() %>;
</aui:script>

<c:if test="<%= results.isEmpty() %>">
	<div class="no-activities">
		<c:choose>
			<c:when test="<%= activitiesDisplayContext.getSocialActivitiesCount() == 0 %>">
				<liferay-ui:message key="there-are-no-activities" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="there-are-no-more-activities" />
			</c:otherwise>
		</c:choose>
	</div>
</c:if>