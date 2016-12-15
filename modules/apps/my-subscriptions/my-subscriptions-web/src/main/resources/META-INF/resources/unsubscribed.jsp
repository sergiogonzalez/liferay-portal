<%@ page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@ page import="com.liferay.portal.kernel.model.User" %>
<%@ page import="com.liferay.portal.kernel.portlet.LiferayPortletURL" %>
<%@ page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil" %>
<%@ page
	import="com.liferay.my.subscriptions.web.internal.constants.MySubscriptionsPortletKeys" %>
<%@ page import="javax.portlet.PortletRequest" %><%--
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
long userId = GetterUtil.getLong(request.getParameter("userId"));

User unsubcribedUser = UserLocalServiceUtil.getUser(userId);

Subscription subscription = (Subscription)request.getSession().getAttribute(MySubscriptionsPortletKeys.LAST_UNSUBSCRIBED_SUBSCRIPTION_KEY);

LiferayPortletURL manageSubscriptionsURL = PortletURLFactoryUtil.create(request, MySubscriptionsPortletKeys.MY_SUBSCRIPTIONS, PortletRequest.RENDER_PHASE);

AssetRenderer assetRenderer = MySubscriptionsUtil.getAssetRenderer(subscription.getClassName(), subscription.getClassPK());

String subscriptionTitle = MySubscriptionsUtil.getTitleText(locale, subscription.getClassName(), subscription.getClassPK(), ((assetRenderer != null) ? assetRenderer.getTitle(locale) : null));
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
		We won't send you mails to <%= unsubcribedUser.getEmailAddress() %> anymore.
	</p>

	<p>
		<h4>Did you unsubscribe by accident?</h4>
		<a href="/c/portal/resubscribe">Resubscribe</a> or <a href="<%= manageSubscriptionsURL.toString() %>">Manage your subscriptions</a>.
	</p>
</div>