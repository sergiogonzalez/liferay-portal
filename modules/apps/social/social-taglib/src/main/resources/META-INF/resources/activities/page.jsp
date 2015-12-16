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

<div class="taglib-social-activities">
	<c:choose>
		<c:when test="<%= !activities.isEmpty() %>">
			<div class="list-group">

			<%
			ServiceContext serviceContext = ServiceContextFactory.getInstance(request);

			boolean hasActivities = false;

			Date now = new Date();

			int daysBetween = -1;

			for (SocialActivity activity : activities) {
				SocialActivityFeedEntry activityFeedEntry = SocialActivityInterpreterLocalServiceUtil.interpret(selector, activity, serviceContext);

				if (activityFeedEntry == null) {
					continue;
				}

				if (!hasActivities) {
					hasActivities = true;
				}

				Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), activityFeedEntry.getPortletId());

				int curDaysBetween = DateUtil.getDaysBetween(new Date(activity.getCreateDate()), now, timeZone);
			%>

				<c:if test="<%= curDaysBetween > daysBetween %>">

					<%
					daysBetween = curDaysBetween;
					%>

					<div class="list-group-heading">
						<c:choose>
							<c:when test="<%= curDaysBetween == 0 %>">
								<liferay-ui:message key="today" />
							</c:when>
							<c:when test="<%= curDaysBetween == 1 %>">
								<liferay-ui:message key="yesterday" />
							</c:when>
							<c:otherwise>
								<%= dateFormatDate.format(activity.getCreateDate()) %>
							</c:otherwise>
						</c:choose>
					</div>
				</c:if>

				<div class="list-group-item">
					<div class="list-group-item-field portlet-icon">
						<liferay-portlet:icon-portlet portlet="<%= portlet %>" />
					</div>
					<div class="activity-data list-group-item-content">
						<div class="activity-title">
							<%= activityFeedEntry.getTitle() %>
						</div>
						<div class="activity-body">
							<span class="time"><%= timeFormatDate.format(activity.getCreateDate()) %></span>

							<%= activityFeedEntry.getBody() %>
						</div>
					</div>
				</div>

			<%
			}
			%>

			</div>

			<c:if test="<%= feedEnabled %>">
				<div class="separator"><!-- --></div>

				<liferay-ui:rss
					delta="<%= feedDelta %>"
					displayStyle="<%= feedDisplayStyle %>"
					feedType="<%= feedType %>"
					message="<%= feedURLMessage %>"
					name="<%= feedTitle %>"
					resourceURL="<%= feedResourceURL %>"
					url="<%= feedURL %>"
				/>
			</c:if>
		</c:when>
		<c:otherwise>
			<liferay-ui:message key="there-are-no-recent-activities" />
		</c:otherwise>
	</c:choose>
</div>