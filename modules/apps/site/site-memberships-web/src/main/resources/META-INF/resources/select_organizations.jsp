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
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectOrganizations");

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

PortletURL viewOrganizationsURL = renderResponse.createRenderURL();

viewOrganizationsURL.setParameter("mvcPath", "/select_organizations.jsp");
viewOrganizationsURL.setParameter("eventName", eventName);
viewOrganizationsURL.setParameter("groupId", String.valueOf(siteMembershipsDisplayContext.getGroupId()));

OrganizationSiteMembershipsChecker rowChecker = new OrganizationSiteMembershipsChecker(renderResponse, siteMembershipsDisplayContext.getGroup());

SearchContainer searchContainer = new OrganizationSearch(renderRequest, viewOrganizationsURL);
%>

<aui:form cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		rowChecker="<%= rowChecker %>"
		searchContainer="<%= searchContainer %>"
		var="organizationSearchContainer"
	>

		<%
		OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)organizationSearchContainer.getSearchTerms();

		long parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;

		LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();
		%>

		<liferay-ui:search-container-results>

			<%
			total = OrganizationLocalServiceUtil.searchCount(company.getCompanyId(), parentOrganizationId, searchTerms.getKeywords(), searchTerms.getType(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams);

			organizationSearchContainer.setTotal(total);

			results = OrganizationLocalServiceUtil.search(company.getCompanyId(), parentOrganizationId, searchTerms.getKeywords(), searchTerms.getType(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams, organizationSearchContainer.getStart(), organizationSearchContainer.getEnd(), organizationSearchContainer.getOrderByComparator());

			organizationSearchContainer.setResults(results);
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Organization"
			cssClass="selectable"
			escapedModel="<%= true %>"
			keyProperty="organizationId"
			modelVar="organization"
		>

			<%
			boolean selectOrganizations = true;
			%>

			<%@ include file="/organization_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	var Util = Liferay.Util;

	var form = AUI.$(document.<portlet:namespace />fm);

	$('input[name="<portlet:namespace />rowIds"]').on(
		'change',
		function(event) {
			var values = {
				data: {
					addOrganizationIds: Util.listCheckedExcept(form, '<portlet:namespace />allRowIds')
				}
			};

			Util.getOpener().Liferay.fire('<%= HtmlUtil.escapeJS(eventName) %>', values);
		}
	);
</aui:script>