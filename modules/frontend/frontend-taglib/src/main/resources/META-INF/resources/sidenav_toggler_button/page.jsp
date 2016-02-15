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

<%@ include file="/sidenav_toggler_button/init.jsp" %>

<%
Map<String, Object> data = new HashMap<String, Object>();

data.put("toggle", sidenavId);
%>

<aui:button cssClass="btn btn-default" data="<%= data %>" icon="<%= icon %>" id="<%= id %>" value="<%= label %>" />

<aui:script>
	var sidenavSlider = $('#<%= sidenavId %>');

	if (!sidenavSlider.sideNavigation('instance')) {
		sidenavSlider.sideNavigation(
			{
				gutter: 15,
				position: 'right',
				toggler: '[data-toggle="<%= sidenavId %>"]',
				type: 'relative',
				typeMobile: 'fixed',
				width: 320
			}
		);

		sidenavSlider.on(
			'closed.lexicon.sidenav',
			function(event) {
				Liferay.Store('com.liferay.info.panel_<%= sidenavId %>', 'closed');
			}
		);

		sidenavSlider.on(
			'open.lexicon.sidenav',
			function(event) {
				Liferay.Store('com.liferay.info.panel_<%= sidenavId %>', 'open');
			}
		);
	}
</aui:script>