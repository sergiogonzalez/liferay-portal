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

<%@ include file="/unsubscribe/init.jsp" %>

<%
String email = GetterUtil.getString(request.getAttribute("email"));
String subscriptionTitle = GetterUtil.getString(request.getAttribute("subscriptionTitle"));

PortletURL manageSubscriptionsURL = SubscriptionsUtil.getManageSubscriptionsURL(request);

PortletURL unsubscribeURL = renderResponse.createRenderURL();
unsubscribeURL.setParameter("key", request.getParameter("key"));
unsubscribeURL.setParameter("userId", request.getParameter("userId"));
unsubscribeURL.setParameter("mvcRenderCommandName", UnsubscribeMVCRenderCommand.COMMAND_NAME);
%>

<div class="successful">
	<liferay-ui:icon
		cssClass="unsubscribe-success-icon"
		icon="check-circle"
		markupView="lexicon"
	/>

	<h3>
		<liferay-ui:message key="subscribe-successful" />
	</h3>

	<p>
		<liferay-ui:message arguments="<%= subscriptionTitle %>" key="your-subscription-to-x-has-been-enabled-again" />
		<br>
		<liferay-ui:message arguments="<%= email %>" key="you-will-keep-receiving-emails-to-x" />
	</p>

	<p class="help">
		<h4>
			<liferay-ui:message key="did-you-resubscribe-by-accident" />
		</h4>

		<a href="<%= unsubscribeURL.toString() %>"><liferay-ui:message key="unsubscribe" /></a>

		<c:if test="<%= manageSubscriptionsURL != null %>">
			<span class="text-lowercase">
				<liferay-ui:message key="or" />
			</span>

			<a href="<%= manageSubscriptionsURL.toString() %>">
				<liferay-ui:message key="manage-your-subcriptions" />
			</a>
		</c:if>
	</p>
</div>