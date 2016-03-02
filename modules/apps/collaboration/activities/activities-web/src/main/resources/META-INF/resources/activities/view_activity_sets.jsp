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
List<SocialActivitySet> results = activitiesDisplayContext.getSocialActivitySets();

for (SocialActivitySet activitySet : results) {
	SocialActivityFeedEntry activityFeedEntry = activitiesDisplayContext.getSocialActivityFeedEntry(activitySet);

	if (activityFeedEntry == null) {
		continue;
	}
%>

	<div class="activity-item clearfix <%= activitiesDisplayContext.getActivityItemCssClassWrapper(activityFeedEntry) %>-activity" id="<portlet:namespace /><%= activitySet.getActivitySetId() %>">
		<liferay-ui:user-display
			showUserDetails="<%= false %>"
			showUserName="<%= false %>"
			userId="<%= activitySet.getUserId() %>"
		/>

		<%= activityFeedEntry.getTitle() %>

		<div class="activity-block">

			<%= activityFeedEntry.getBody() %>

			<div class="activity-footer">
				<c:if test="<%= activitiesDisplayContext.isActivityFooterVisible(activitySet) %>">
					<div class="activity-footer-toolbar">
						<c:if test="<%= activitiesDisplayContext.isActivityLinkVisible(activityFeedEntry) %>">
							<span class="action"><%= activityFeedEntry.getLink() %></span>
						</c:if>

						<c:if test="<%= activitiesDisplayContext.isMicroblogsRepostActionVisible(activitySet) %>">
							<span class="action repost">
								<a data-microblogsEntryId="<%= activitySet.getClassPK() %>" href="javascript:;"><liferay-ui:message key="repost" /></a>
							</span>
						</c:if>
					</div>

					<liferay-ui:discussion
						className="<%= activitiesDisplayContext.getDiscussionClassName(activitySet) %>"
						classPK="<%= activitiesDisplayContext.getDiscussionClassPK(activitySet) %>"
						userId="<%= user.getUserId() %>"
					/>
				</c:if>
			</div>
		</div>
	</div>

	<aui:script use="aui-base">
		var entry = A.one('#<portlet:namespace /><%= activitySet.getActivitySetId() %>');

		var subentry = entry.one('.activity-subentry');

		if (subentry != null) {
			var body = entry.one('.grouped-activity-body');

			if (body.outerHeight() > (subentry.outerHeight() * 3)) {
				var toggle = entry.one('.toggle');

				toggle.removeClass('hide');

				entry.addClass('toggler-content-collapsed')
			}
			else {
				var bodyContainer = entry.one('.grouped-activity-body-container');

				bodyContainer.setStyle('height', 'auto');
			}
		}
	</aui:script>

<%
}
%>

<aui:script>
	<portlet:namespace />start = <%= activitiesRequestHelper.getStart() + results.size() %>;
</aui:script>

<c:if test ="<%= results.isEmpty() %>">
	<div class ="no-activities">
		<c:choose>
			<c:when test ="<%= activitiesDisplayContext.getSocialActivitySetsCount() == 0 %>">
				<liferay-ui:message key ="there-are-no-activities" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key ="there-are-no-more-activities" />
			</c:otherwise>
		</c:choose>
	</div>
</c:if>