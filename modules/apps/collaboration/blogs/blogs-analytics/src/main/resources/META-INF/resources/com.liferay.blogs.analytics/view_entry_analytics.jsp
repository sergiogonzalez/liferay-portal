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

<%@ include file="/com.liferay.blogs.analytics/init.jsp" %>

<%
BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

long entryId = ParamUtil.getLong(request, "entryId", entry.getEntryId());
%>

<aui:script require="metal-dom/src/all/dom as dom">
	Analytics.registerMiddleware(
		function(request, analytics) {
			request.context['referrer'] = document.referrer;
			request.context['userId'] = '<%= user.getUserId() %>';

			return request;
		}
	);

	Analytics.send(
		'visits',
		'com.liferay.blogs',
		{
			entryId: '<%= entryId %>'
		}
	);

	dom.delegate(
		document.body,
		'click',
		'.social-bookmark',
		function(event) {
			Analytics.send(
				'social',
				'com.liferay.blogs',
				{
					entryId: '<%= entryId %>',
					network: event.delegateTarget.id.substr(event.delegateTarget.id.lastIndexOf('_') + 1)
				}
			);
		}
	);

	var scrollSessionId = new Date().toISOString();

	var entry = document.querySelector('.entry');

	var throttle = function(fn, wait) {
		var time = Date.now();

		return function() {
			if ((time + wait - Date.now()) < 0) {
				fn();
				time = Date.now();
			}
		}
	};

	Analytics.registerPlugin(
		function(analytics) {
			document.addEventListener(
				'scroll',
				throttle(
					function() {
						var currentScrollPosition = window.pageYOffset || document.documentElement.scrollTop;
						var entryBoundingClientRect = entry.getBoundingClientRect();

						var depth = Math.trunc(100 * (currentScrollPosition - entryBoundingClientRect.top) / entryBoundingClientRect.height);

						if (depth >= 0 && depth <= 100) {
							Analytics.send(
								'depth',
								'com.liferay.blogs',
								{
									depth: depth,
									entryId: '<%= entryId %>',
									sessionId: scrollSessionId
								}
							);
						}
					},
					500
				)
			);
		}
	);
</aui:script>