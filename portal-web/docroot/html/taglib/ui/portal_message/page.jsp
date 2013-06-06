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

<%@ include file="/html/taglib/init.jsp" %>

<%
String cssClass = GetterUtil.getString(SessionMessages.get(request, "portalMessageCssClass"), "alert-info");
String message = GetterUtil.getString(SessionMessages.get(request, "portalMessageMessage"));
int timeout = GetterUtil.getInteger(SessionMessages.get(request, "portalMessageTimeout"), 10000);
boolean useAnimation = GetterUtil.getBoolean(SessionMessages.get(request, "portalMessageAnimation"), true);
%>

<c:if test="<%= Validator.isNotNull(message) %>">
	<aui:script use="liferay-notice">
		var banner = new Liferay.Notice(
			{
				animationConfig:
					{
						duration: 2,
						top: '0px'
					},
				closeText: false,
				content: '<liferay-ui:message key="<%= message %>" /><button type="button" class="close">&times;</button>',
				noticeClass: 'hide taglib-portal-message <%= cssClass %>',
				timeout: <%= timeout %>,
				toggleText: false,
				useAnimation: <%= useAnimation %>
			}
		);

		banner.show();
	</aui:script>
</c:if>