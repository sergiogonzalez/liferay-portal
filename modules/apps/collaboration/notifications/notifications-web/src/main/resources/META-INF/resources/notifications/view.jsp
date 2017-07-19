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
boolean actionRequired = ParamUtil.getBoolean(request, "actionRequired");
String filterBy = ParamUtil.getString(request, "filterBy", "all");
String orderByCol = ParamUtil.getString(request, "orderByCol", "date");
String orderByType = ParamUtil.getString(request, "orderByType", "desc");

SearchContainer notificationsSearchContainer = new SearchContainer(renderRequest, currentURLObj, null, actionRequired ? "you-do-not-have-any-requests" : "you-do-not-have-any-notifications");
notificationsSearchContainer.setId("userNotificationEvents");

int allNotificationEventsCount =
	UserNotificationEventLocalServiceUtil.
		getDeliveredUserNotificationEventsCount(
			themeDisplay.getUserId(),
			UserNotificationDeliveryConstants.TYPE_WEBSITE,
			true, actionRequired);

if ("read".equals(filterBy) || "unread".equals(filterBy)) {
	boolean archived = "read".equals(filterBy);

	notificationsSearchContainer.setTotal(
		UserNotificationEventLocalServiceUtil.
			getArchivedUserNotificationEventsCount(
				themeDisplay.getUserId(),
				UserNotificationDeliveryConstants.TYPE_WEBSITE,
				actionRequired, archived));

	notificationsSearchContainer.setResults(
		UserNotificationEventLocalServiceUtil.
			getArchivedUserNotificationEvents(
				themeDisplay.getUserId(),
				UserNotificationDeliveryConstants.TYPE_WEBSITE, actionRequired,
				archived, notificationsSearchContainer.getStart(),
				notificationsSearchContainer.getEnd()));
}
else {
	notificationsSearchContainer.setTotal(allNotificationEventsCount);
	notificationsSearchContainer.setResults(
		UserNotificationEventLocalServiceUtil.
			getDeliveredUserNotificationEvents(
				themeDisplay.getUserId(),
				UserNotificationDeliveryConstants.TYPE_WEBSITE, true,
				actionRequired, notificationsSearchContainer.getStart(),
				notificationsSearchContainer.getEnd()));
}
%>

<aui:nav-bar markupView="lexicon">
	<liferay-portlet:renderURL var="viewNotificationsURL">
		<liferay-portlet:param name="actionRequired" value="<%= StringPool.FALSE %>" />
	</liferay-portlet:renderURL>

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item
			href="<%= viewNotificationsURL %>"
			label="notifications-list"
			selected="<%= !actionRequired %>"
		/>
	</aui:nav>

	<liferay-portlet:renderURL var="viewRequestsURL">
		<liferay-portlet:param name="actionRequired" value="<%= StringPool.TRUE %>" />
	</liferay-portlet:renderURL>

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item
			href="<%= viewRequestsURL %>"
			label="requests-list"
			selected="<%= actionRequired %>"
		/>
	</aui:nav>
</aui:nav-bar>

<liferay-frontend:management-bar
	disabled="<%= allNotificationEventsCount == 0 %>"
	includeCheckBox="<%= true %>"
	searchContainerId="userNotificationEvents"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive"} %>'
			portletURL="<%= currentURLObj %>"
			selectedDisplayStyle="descriptive"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all", "unread", "read"} %>'
			navigationParam="filterBy"
			portletURL="<%= PortletURLUtil.clone(currentURLObj, renderResponse) %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"date"} %>'
			portletURL="<%= PortletURLUtil.clone(currentURLObj, renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "markAsRead();" %>' icon="times" label="mark-as-read" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280 main-content-body">
	<aui:form action="<%= currentURL %>" cssClass="row" method="get" name="fm">
		<div class="user-notifications">
			<liferay-ui:search-container
				rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
				searchContainer="<%= notificationsSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.UserNotificationEvent"
					keyProperty="userNotificationEventId"
					modelVar="userNotificationEvent"
				>
					<%@ include file="/notifications/user_notification_entry.jspf" %>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" />
			</liferay-ui:search-container>
		</div>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />markAsRead() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.attr('method', 'post');

		submitForm(form, '<portlet:actionURL name="markAllAsRead" />');
	}
</aui:script>

<aui:script use="aui-base,liferay-notice">
	var form = A.one('#<portlet:namespace />fm');

	form.delegate(
		'click',
		function(event) {
			event.preventDefault();

			var currentTarget = event.currentTarget;

			A.io.request(
				currentTarget.attr('href'),
				{
					dataType: 'JSON',
					on: {
						success: function() {
							var responseData = this.get('responseData');

							if (responseData.success) {
								var notificationContainer = currentTarget.ancestor('li.list-group-item');

								if (notificationContainer) {
									var markAsReadURL = notificationContainer.one('a').attr('href');

									A.io.request(markAsReadURL);

									notificationContainer.remove();
								}
							}
							else {
								getNotice().show();
							}
						}
					}
				}
			);
		},
		'.user-notification-action'
	);

	var notice;

	function getNotice() {
		if (!notice) {
			notice = new Liferay.Notice(
				{
					closeText: false,
					content: '<liferay-ui:message key="an-unexpected-error-occurred"/><button class="close" type="button">&times;</button>',
					timeout: 5000,
					toggleText: false,
					type: 'warning',
					useAnimation: false
				}
			);
		}

		return notice;
	}
</aui:script>