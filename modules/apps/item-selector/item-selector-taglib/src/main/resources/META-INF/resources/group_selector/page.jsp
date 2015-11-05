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
int groupCount = GetterUtil.getInteger(request.getAttribute("liferay-item-selector:group-selector:groupCount"));
List<Group> groups = (List<Group>)request.getAttribute("liferay-item-selector:group-selector:groups");
ItemSelector itemSelector = (ItemSelector)request.getAttribute("liferay-item-selector:group-selector:itemSelector");

RequestBackedPortletURLFactory requestBackedPortletURLFactory = RequestBackedPortletURLFactoryUtil.create(request);

String itemSelectedEventName = ParamUtil.getString(request, "itemSelectedEventName");

List<ItemSelectorCriterion> criteria = itemSelector.getItemSelectorCriteria(liferayPortletRequest.getParameterMap());

PortletURL iteratorURL = itemSelector.getItemSelectorURL(requestBackedPortletURLFactory, itemSelectedEventName, criteria.toArray(new ItemSelectorCriterion[criteria.size()]));

iteratorURL.setParameter("selectedTab", ParamUtil.getString(request, "selectedTab"));
iteratorURL.setParameter("showGroupSelector", String.valueOf(true));

SearchContainer searchContainer = new GroupSearch(liferayPortletRequest, iteratorURL);
%>

<liferay-frontend:management-bar />

<div class="container-fluid-1280 lfr-item-viewer">
	<liferay-ui:search-container
		searchContainer="<%= searchContainer %>"
		total="<%= groupCount %>"
		var="listSearchContainer"
	>
		<liferay-ui:search-container-results
			results="<%= groups %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Group"
			modelVar="curGroup"
		>

			<%
			row.setCssClass("col-md-3");

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

			long refererGroupId = themeDisplay.getRefererGroupId() != 0 ? themeDisplay.getRefererGroupId() : themeDisplay.getScopeGroupId();

			PortletURL viewGroupURL = itemSelector.getItemSelectorURL(requestBackedPortletURLFactory, curGroup, refererGroupId, itemSelectedEventName, criteria.toArray(new ItemSelectorCriterion[criteria.size()]));

			viewGroupURL.setParameter("selectedTab", ParamUtil.getString(request, "selectedTab"));
			%>

			<liferay-ui:search-container-column-text colspan="<%= 3 %>">
				<liferay-frontend:horizontal-card
					icon="icon-folder-close-alt"
					imageCSSClass="icon-monospaced"
					resultRow="<%= row %>"
					text="<%= HtmlUtil.escape(curGroup.getDescriptiveName(locale)) %>"
					url="<%= viewGroupURL.toString() %>"
				/>
			</liferay-ui:search-container-column-text>

		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="icon" markupView="lexicon" paginate="<%= false %>" searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</liferay-ui:search-container>
</div>