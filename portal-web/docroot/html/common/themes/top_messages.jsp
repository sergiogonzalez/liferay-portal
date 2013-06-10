<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/common/init.jsp" %>

<c:if test="<%= ShutdownUtil.isInProcess() %>">
	<div class="alert alert-block" id="lfrShutdownMessage">
		<span class="notice-label"><liferay-ui:message key="maintenance-alert" /></span> <span class="notice-date"><%= FastDateFormatFactoryUtil.getTime(locale).format(Time.getDate(CalendarFactoryUtil.getCalendar(timeZone))) %> <%= timeZone.getDisplayName(false, TimeZone.SHORT, locale) %></span>
		<span class="notice-message"><%= LanguageUtil.format(pageContext, "the-portal-will-shutdown-for-maintenance-in-x-minutes", String.valueOf(ShutdownUtil.getInProcess() / Time.MINUTE), false) %></span>

		<c:if test="<%= Validator.isNotNull(ShutdownUtil.getMessage()) %>">
			<span class="custom-shutdown-message"><%= HtmlUtil.escape(ShutdownUtil.getMessage()) %></span>
		</c:if>
	</div>
</c:if>

<%
String jspPath = GetterUtil.getString(request.getAttribute(WebKeys.PORTAL_MESSAGE_JSP_PATH));
String message = GetterUtil.getString(request.getAttribute(WebKeys.PORTAL_MESSAGE_MESSAGE));

if (Validator.isNotNull(jspPath) || Validator.isNotNull(message)) {
	String cssClass = GetterUtil.getString(request.getAttribute(WebKeys.PORTAL_MESSAGE_CSS_CLASS), "alert-info");
	String portletId = GetterUtil.getString(request.getAttribute(WebKeys.PORTAL_MESSAGE_PORTLET_ID));
	int timeout = GetterUtil.getInteger(request.getAttribute(WebKeys.PORTAL_MESSAGE_TIMEOUT), 10000);
	boolean useAnimation = GetterUtil.getBoolean(request.getAttribute(WebKeys.PORTAL_MESSAGE_ANIMATION), true);
%>

	<div class="hide <%= cssClass %>" id="portalMessageContainer">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(jspPath) %>">
				<liferay-util:include page="<%= jspPath %>" portletId="<%= portletId %>" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="<%= message %>" /><button type="button" class="close">&times;</button>
			</c:otherwise>
		</c:choose>
	</div>

	<aui:script use="liferay-notice">
		var portalMessageContainer = A.one('#portalMessageContainer');

		var banner = new Liferay.Notice(
			{
				animationConfig:
					{
						duration: 2,
						top: '0px'
					},
				closeText: false,
				content: portalMessageContainer.html(),
				noticeClass: 'hide taglib-portal-message <%= cssClass %>',
				timeout: <%= timeout %>,
				toggleText: false,
				useAnimation: <%= useAnimation %>
			}
		);

		banner.show();
	</aui:script>

<%
}
%>