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

<%@ include file="/group_selector/init.jsp" %>

<%
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-item-selector:group-selector:portletURL");
SearchContainer<Group> searchContainer = (SearchContainer<Group>)request.getAttribute("liferay-item-selector:group-selector:searchContainer");
%>

<liferay-frontend:management-bar />

<div class="container-fluid-1280 lfr-item-viewer">
	<liferay-ui:search-container
		searchContainer="<%= searchContainer %>"
		total="<%= searchContainer.getTotal() %>"
		var="listSearchContainer"
	>
		<liferay-ui:search-container-results
			results="<%= searchContainer.getResults() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Group"
			modelVar="curGroup"
		>

			<%
			row.setCssClass("col-md-3 card-horizontal-dm");

			PortletURL viewGroupURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

			viewGroupURL.setParameter("repositoryId", String.valueOf(curGroup.getGroupId()));

			if (curGroup.hasStagingGroup()) {
				Group stagingGroup = curGroup.getStagingGroup();

				long doAsGroupId = themeDisplay.getDoAsGroupId();

				String portletId = ParamUtil.getString(request, "p_p_id");

				if ((layout.isTypeControlPanel() ||
					(stagingGroup.getGroupId() == doAsGroupId)) &&
					 curGroup.isStagedPortlet(portletId) &&
					 !curGroup.isStagedRemotely()) {

					curGroup = stagingGroup;
				}
			}
			%>

			<liferay-ui:search-container-column-text colspan="<%= 3 %>">
				<liferay-frontend:card
					horizontal="<%= true %>"
					imageCSSClass="icon-monospaced"
					imageUrl="icon-folder-close-alt"
					resultRow="<%= row %>"
					title="<%= HtmlUtil.escape(curGroup.getDescriptiveName(locale)) %>"
					url="<%= viewGroupURL.toString() %>"
				/>
			</liferay-ui:search-container-column-text>

		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="icon" markupView="lexicon" paginate="<%= false %>" searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</liferay-ui:search-container>
</div>