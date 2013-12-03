<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%
String className = (String)request.getAttribute("liferay-ui:asset-categories-summary:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:asset-categories-summary:classPK"));
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-ui:asset-categories-summary:portletURL");

List<AssetVocabulary> vocabularies = AssetVocabularyServiceUtil.getGroupsVocabularies(new long[] {themeDisplay.getSiteGroupId(), themeDisplay.getCompanyGroupId()});
List<AssetCategory> categories = AssetCategoryServiceUtil.getCategories(className, classPK);

for (AssetVocabulary vocabulary : vocabularies) {
	vocabulary = vocabulary.toEscapedModel();

	String vocabularyTitle = vocabulary.getTitle(locale);

	List<AssetCategory> curCategories = _filterCategories(categories, vocabulary);
%>

	<c:if test="<%= !curCategories.isEmpty() %>">
		<span class="taglib-asset-categories-summary">
			<%= vocabularyTitle %>:

			<c:choose>
				<c:when test="<%= portletURL != null %>">

					<%
					for (AssetCategory category : curCategories) {
						category = category.toEscapedModel();

						portletURL.setParameter("categoryId", String.valueOf(category.getCategoryId()));
					%>

						<a class="asset-category" href="<%= HtmlUtil.escape(portletURL.toString()) %>"><%= _buildCategoryPath(category, locale) %></a>

					<%
					}
					%>

				</c:when>
				<c:otherwise>

					<%
					for (AssetCategory category : curCategories) {
						category = category.toEscapedModel();
					%>

						<span class="asset-category">
							<%= _buildCategoryPath(category, locale) %>
						</span>

					<%
					}
					%>

				</c:otherwise>
			</c:choose>
		</span>
	</c:if>

<%
}
%>

<%!
private String _buildCategoryPath(AssetCategory category, Locale locale) throws PortalException, SystemException {
	List<AssetCategory> ancestorCategories = category.getAncestors();

	if (ancestorCategories.isEmpty()) {
		return category.getTitle(locale);
	}

	Collections.reverse(ancestorCategories);

	StringBundler sb = new StringBundler(ancestorCategories.size() * 2 + 1);

	for (AssetCategory ancestorCategory : ancestorCategories) {
		ancestorCategory = ancestorCategory.toEscapedModel();

		sb.append(ancestorCategory.getTitle(locale));
		sb.append(" &raquo; ");
	}

	sb.append(category.getTitle(locale));

	return sb.toString();
}

private List<AssetCategory> _filterCategories(List<AssetCategory> categories, AssetVocabulary vocabulary) {
	List<AssetCategory> filteredCategories = new ArrayList<AssetCategory>();

	for (AssetCategory category : categories) {
		if (category.getVocabularyId() == vocabulary.getVocabularyId()) {
			filteredCategories.add(category);
		}
	}

	return filteredCategories;
}
%>