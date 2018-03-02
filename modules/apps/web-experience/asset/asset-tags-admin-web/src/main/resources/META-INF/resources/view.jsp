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

<liferay-portlet:renderURL varImpl="portletURL">
	<liferay-portlet:param name="keywords" value="<%= assetTagsDisplayContext.getKeywords() %>" />
</liferay-portlet:renderURL>

<portlet:renderURL var="editTagURL">
	<portlet:param name="mvcPath" value="/edit_tag.jsp" />
</portlet:renderURL>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= assetTagsDisplayContext.getNavigationItems() %>"
/>

<%
JSPDropdownItemList orderByDropdownItemList = new JSPDropdownItemList(pageContext) {
	{
		add(
			dropdownItem -> {
				PortletURL nameURL = renderResponse.createRenderURL();

				nameURL.setParameter("keywords", assetTagsDisplayContext.getKeywords());
				nameURL.setParameter("orderByType", assetTagsDisplayContext.getOrderByType());
				nameURL.setParameter("orderByCol", "name");

				dropdownItem.setHref(nameURL);
				dropdownItem.setLabel(LanguageUtil.get(request, "name"));
			}
		);

		add(
			dropdownItem -> {
				PortletURL usagesURL = renderResponse.createRenderURL();

				usagesURL.setParameter("keywords", assetTagsDisplayContext.getKeywords());
				usagesURL.setParameter("orderByType", assetTagsDisplayContext.getOrderByType());
				usagesURL.setParameter("orderByCol", "usages");

				dropdownItem.setHref(usagesURL);
				dropdownItem.setLabel(LanguageUtil.get(request, "usages"));
			}
		);
	}
};
%>

<clay:management-toolbar
	actionItems="<%=
		new JSPDropdownItemList(pageContext) {
			{
				add(
					dropdownItem -> {
						dropdownItem.setIcon("change");
						dropdownItem.setId("merge");
						dropdownItem.setLabel(LanguageUtil.get(request, "merge"));
						dropdownItem.setQuickAction(true);
					}
				);

				add(
					dropdownItem -> {
						dropdownItem.setIcon("trash");
						dropdownItem.setId("delete");
						dropdownItem.setLabel(LanguageUtil.get(request, "delete"));
						dropdownItem.setQuickAction(true);
					}
				);
			}
		}
	%>"
	componentId="assetTagsManagementToolbar"
	creationMenu="<%= assetTagsDisplayContext.isShowAddButton() ? editTagURL : null %>"
	filterItems="<%=
		new JSPDropdownItemList(pageContext) {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setLabel("Order By");
						dropdownGroupItem.setDropdownItems(orderByDropdownItemList);
					}
				);
			}
		}
	%>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= portletURL.toString() %>"
	searchContainerId="assetTags"
	searchFormName="searchFm"
	searchInputName="keywords"
	showSearch="<%= assetTagsDisplayContext.isShowTagsSearch() %>"
	sortingOrder="<%= assetTagsDisplayContext.getOrderByType() %>"
	totalItems="<%= assetTagsDisplayContext.getTagsSearchContainer().getTotal() %>"
	viewTypes="<%=
		new JSPViewTypeItemList(pageContext) {
			{
				addCardViewType(
					viewTypeItem -> {
						viewTypeItem.setActive(Objects.equals(assetTagsDisplayContext.getDisplayStyle(), "icon"));
						viewTypeItem.setHref(renderResponse.createActionURL(), "redirect", PortalUtil.getCurrentURL(request), "displayStyle", "icon");
						viewTypeItem.setLabel("Cards");
					}
				);

				addListViewType(
					viewTypeItem -> {
						viewTypeItem.setActive(Objects.equals(assetTagsDisplayContext.getDisplayStyle(), "descriptive"));
						viewTypeItem.setHref(renderResponse.createActionURL(), "redirect", PortalUtil.getCurrentURL(request), "displayStyle", "descriptive");
						viewTypeItem.setLabel("List");
					}
				);

				addTableViewType(
					viewTypeItem -> {
						viewTypeItem.setActive(Objects.equals(assetTagsDisplayContext.getDisplayStyle(), "list"));
						viewTypeItem.setHref(renderResponse.createActionURL(), "redirect", PortalUtil.getCurrentURL(request), "displayStyle", "list");
						viewTypeItem.setLabel("Table");
					}
				);
			}
		}
	%>"
/>

<portlet:actionURL name="deleteTag" var="deleteTagURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteTagURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="assetTags"
		searchContainer="<%= assetTagsDisplayContext.getTagsSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.kernel.model.AssetTag"
			keyProperty="tagId"
			modelVar="tag"
		>

			<%
			long fullTagsCount = assetTagsDisplayContext.getFullTagsCount(tag);
			%>

			<c:choose>
				<c:when test='<%= Objects.equals(assetTagsDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-icon
						icon="tag"
						toggleRowChecker="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5>
							<%= tag.getName() %>
						</h5>

						<h6 class="text-default">
							<strong><liferay-ui:message key="usages" /></strong>: <span><%= String.valueOf(fullTagsCount) %></span>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/tag_action.jsp"
					/>
				</c:when>
				<c:when test='<%= Objects.equals(assetTagsDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							actionJsp="/tag_action.jsp"
							actionJspServletContext="<%= application %>"
							icon="tag"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= tag.getName() %>"
						>
							<liferay-frontend:vertical-card-footer>
								<strong><liferay-ui:message key="usages" /></strong>: <span><%= String.valueOf(fullTagsCount) %></span>
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:icon-vertical-card>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(assetTagsDisplayContext.getDisplayStyle(), "list") %>'>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="name"
						value="<%= tag.getName() %>"
					/>

					<liferay-ui:search-container-column-text
						name="usages"
						value="<%= String.valueOf(fullTagsCount) %>"
					/>

					<liferay-ui:search-container-column-jsp
						path="/tag_action.jsp"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= assetTagsDisplayContext.getDisplayStyle() %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.componentReady('assetTagsManagementToolbar').then(
		managementToolbar => {
			managementToolbar.on(
				'actionClicked',
				action => {
					if (action.id == 'merge') {
						<portlet:renderURL var="mergeURL">
							<portlet:param name="mvcPath" value="/merge_tag.jsp" />
							<portlet:param name="mergeTagIds" value="[$MERGE_TAGS_IDS$]" />
						</portlet:renderURL>

						let mergeURL = '<%= mergeURL %>';

						location.href = mergeURL.replace(
							escape('[$MERGE_TAGS_IDS$]'),
							Liferay.Util.listCheckedExcept(document.querySelector('#<portlet:namespace />fm'),
							'<portlet:namespace />allRowIds')
						);
					}
					else if (action.id == 'delete') {
						if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
							submitForm(form);
						}
					}
				}
			);
		}
	);
</aui:script>