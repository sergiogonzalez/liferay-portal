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
String email = GetterUtil.getString(request.getAttribute("email"));
String subscriptionTitle = GetterUtil.getString(request.getAttribute("subscriptionTitle"));

PortletURL manageSubscriptionsURL = renderResponse.createRenderURL();
manageSubscriptionsURL.setParameter("mvcRenderCommandName", "/");

PortletURL resubscribeURL = renderResponse.createRenderURL();
resubscribeURL.setParameter("mvcRenderCommandName", ResubscribeMVCRenderCommand.COMMAND_NAME);
%>

<div class="unsubscribe">
	<liferay-ui:icon
		icon="check-circle"
		markupView="lexicon"
	/>

	<h3>
		<liferay-ui:message key="unsubscribe-successful" />
	</h3>

	<p>
		<liferay-ui:message arguments="<%= subscriptionTitle %>" key="you-have-been-removed-from-x" />
		<br>
		<liferay-ui:message arguments="<%= email %>" key="we-wont-send-you-mails-to-x-anymore" />
	</p>

	<p>
		<h4>
			<liferay-ui:message key="did-you-unsubscribe-by-accident" />
		</h4>

		<a href="<%= resubscribeURL.toString() %>">
			<liferay-ui:message key="resubscribe" />
		</a>

		<liferay-ui:message key="or" />

		<a href="<%= manageSubscriptionsURL.toString() %>">
			<liferay-ui:message key="manage-your-subcriptions" />
		</a>
	</p>
</div>