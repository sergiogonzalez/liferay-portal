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

	<h3>Unsubscribe successful</h3>

	<p>
		You have been removed from <%= subscriptionTitle %>.
		<br>
		We won't send you mails to <%= email %> anymore.
	</p>

	<p>
		<h4>Did you unsubscribe by accident?</h4>

		<a href="<%= resubscribeURL.toString() %>">Resubscribe</a>
		or
		<a href="<%= manageSubscriptionsURL.toString() %>">Manage your subscriptions</a>.
	</p>
</div>