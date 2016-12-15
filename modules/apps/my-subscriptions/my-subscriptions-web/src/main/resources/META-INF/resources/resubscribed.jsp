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
String email = GetterUtil.getString(request.getParameter("email"));
String subscriptionTitle = GetterUtil.getString(request.getParameter("subscriptionTitle"));

LiferayPortletURL manageSubscriptionsURL = PortletURLFactoryUtil.create(request, MySubscriptionsPortletKeys.MY_SUBSCRIPTIONS, PortletRequest.RENDER_PHASE);
manageSubscriptionsURL.setWindowState(LiferayWindowState.MAXIMIZED);
%>

<div class="resubscribe">
	<liferay-ui:icon
		icon="check-circle"
		markupView="lexicon"
	/>

	<h3>Subscription successful</h3>

	<p>Your subscription to <%= subscriptionTitle %> has been enabled again, you will keep receiving emails to <%= email %></p>

	<a href="<%= manageSubscriptionsURL.toString() %>">Manage your subscriptions</a>.
</div>