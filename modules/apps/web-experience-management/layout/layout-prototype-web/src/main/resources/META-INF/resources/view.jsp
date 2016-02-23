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
String navigation = ParamUtil.getString(request, "navigation");

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

String orderByCol = ParamUtil.getString(request, "orderByCol", "create-date");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

Boolean active = null;

if (navigation.equals("active")) {
	active = true;
}
else if (navigation.equals("inactive")) {
	active = false;
}

int totalVar = LayoutPrototypeLocalServiceUtil.searchCount(company.getCompanyId(), active);

PortletURL portletURL = renderResponse.createRenderURL();
%>

<liferay-ui:error exception="<%= RequiredLayoutPrototypeException.class %>" message="you-cannot-delete-page-templates-that-are-used-by-a-page" />

<aui:nav-bar markupView="lexicon">
	<portlet:renderURL var="mainURL" />

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="templates" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<liferay-frontend:management-bar
	disabled="<%= totalVar <= 0 %>"
	includeCheckBox="<%= true %>"
	searchContainerId="layoutPrototype"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all", "active", "inactive"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteSelectedLayoutPrototypes" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="deleteLayoutPrototypes" var="deleteLayoutPrototypesURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteLayoutPrototypesURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-page-templates.-you-can-add-a-page-template-by-clicking-the-plus-button-on-the-bottom-right-corner"
		headerNames="name"
		id="layoutPrototype"
		iteratorURL="<%= portletURL %>"
		rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
		total="<%= totalVar %>"
	>

		<%
		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<LayoutPrototype> orderByComparator = new LayoutPrototypeCreateDateComparator(orderByAsc);

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(orderByType);
		%>

		<liferay-ui:search-container-results
			results="<%= LayoutPrototypeLocalServiceUtil.search(company.getCompanyId(), active, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.LayoutPrototype"
			cssClass="selectable"
			escapedModel="<%= true %>"
			keyProperty="layoutPrototypeId"
			modelVar="layoutPrototype"
		>

			<%
			Group layoutPrototypeGroup = layoutPrototype.getGroup();
			%>

			<c:choose>
				<c:when test='<%= displayStyle.equals("descriptive") %>'>
					<liferay-ui:search-container-column-icon
						icon="edit-layout"
						toggleRowChecker="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>

						<%
						Date createDate = layoutPrototype.getModifiedDate();

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
						%>

						<h6 class="text-default">
							<span><liferay-ui:message arguments="<%= modifiedDateDescription %>" key="modified-x-ago" /></span>
						</h6>

						<h5>
							<aui:a href="<%= layoutPrototypeGroup.getDisplayURL(themeDisplay, true) %>" target="_blank"><%= layoutPrototype.getName(locale) %></aui:a>
						</h5>

						<h6 class="text-default">
							<c:choose>
								<c:when test="<%= layoutPrototype.isActive() %>">
									<span><liferay-ui:message key="active" /></span>
								</c:when>
								<c:otherwise>
									<span><liferay-ui:message key="not-active" /></span>
								</c:otherwise>
							</c:choose>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/layout_prototype_action.jsp"
					/>
				</c:when>
				<c:when test='<%= displayStyle.equals("icon") %>'>

					<%
					row.setCssClass("col-md-2 col-sm-4 col-xs-6");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							actionJsp="/layout_prototype_action.jsp"
							actionJspServletContext="<%= application %>"
							cssClass="entry-display-style"
							icon="edit-layout"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= layoutPrototype.getName(locale) %>"
							url="<%= layoutPrototypeGroup.getDisplayURL(themeDisplay, true) %>"
						>
							<liferay-frontend:vertical-card-header>

								<%
								Date createDate = layoutPrototype.getModifiedDate();

								String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
								%>

								<label class="text-default">
									<liferay-ui:message arguments="<%= modifiedDateDescription %>" key="modified-x-ago" />
								</label>
							</liferay-frontend:vertical-card-header>

							<liferay-frontend:vertical-card-footer>
								<label class="text-default">
									<c:choose>
										<c:when test="<%= layoutPrototype.isActive() %>">
											<liferay-ui:message key="active" />
										</c:when>
										<c:otherwise>
											<liferay-ui:message key="not-active" />
										</c:otherwise>
									</c:choose>
								</label>
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:icon-vertical-card>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= displayStyle.equals("list") %>'>
					<liferay-ui:search-container-column-text
						cssClass="text-strong"
						name="name"
					>
						<aui:a href="<%= layoutPrototypeGroup.getDisplayURL(themeDisplay, true) %>" target="_blank"><%= layoutPrototype.getName(locale) %></aui:a>

						<%
						int mergeFailCount = SitesUtil.getMergeFailCount(layoutPrototype);
						%>

						<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">
							<liferay-ui:message arguments='<%= new Object[] {mergeFailCount, LanguageUtil.get(request, "page-template")} %>' key="the-propagation-of-changes-from-the-x-has-been-disabled-temporarily-after-x-errors" translateArguments="<%= false %>" />
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="description"
						value="<%= layoutPrototype.getDescription(locale) %>"
					/>

					<liferay-ui:search-container-column-date
						name="create-date"
						property="createDate"
					/>

					<liferay-ui:search-container-column-text
						cssClass="list-group-item-field"
						name="active"
						value='<%= LanguageUtil.get(request, layoutPrototype.isActive()? "yes" : "no") %>'
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="list-group-item-field"
						path="/layout_prototype_action.jsp"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_LAYOUT_PROTOTYPE) %>">
	<portlet:renderURL var="addLayoutPrototypeURL">
		<portlet:param name="mvcPath" value="/edit_layout_prototype.jsp" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add") %>' url="<%= addLayoutPrototypeURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />deleteSelectedLayoutPrototypes').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>