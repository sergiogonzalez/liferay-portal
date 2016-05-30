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

<%@ include file="/section/init.jsp" %>

<%
kbSectionPortletInstanceConfiguration = ParameterMapUtil.setParameterMap(KBSectionPortletInstanceConfiguration.class, kbSectionPortletInstanceConfiguration, request.getParameterMap(), "preferences--", "--");

String tabs2 = ParamUtil.getString(request, "tabs2", "general");

String tabs2Names = "general,display-settings";
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
</liferay-portlet:renderURL>

<liferay-ui:tabs
	names="<%= tabs2Names %>"
	param="tabs2"
	url="<%= configurationRenderURL %>"
/>

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />

	<liferay-ui:error key="kbArticlesSections" message="please-select-at-least-one-section" />

	<aui:fieldset>
		<c:choose>
			<c:when test='<%= tabs2.equals("general") %>'>
				<aui:input label="show-sections-title" name="preferences--showKBArticlesSectionsTitle--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.showKBArticlesSectionsTitle() %>" />

				<aui:select cssClass="kb-field-wrapper" ignoreRequestValue="<%= true %>" label="sections" multiple="<%= true %>" name="kbArticlesSections">

					<%
					Map<String, String> sectionsMap = new TreeMap<String, String>();

					for (String section : kbSectionPortletInstanceConfiguration.adminKBArticleSections()) {
						sectionsMap.put(LanguageUtil.get(request, section), section);
					}

					for (Map.Entry<String, String> entry : sectionsMap.entrySet()) {
					%>

						<aui:option label="<%= entry.getKey() %>" selected="<%= ArrayUtil.contains(kbSectionPortletInstanceConfiguration.kbArticlesSections(), entry.getValue()) %>" value="<%= entry.getValue() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select label="article-display-style" name="preferences--kbArticleDisplayStyle--" value="<%= kbSectionPortletInstanceConfiguration.kbArticleDisplayStyle() %>">
					<aui:option label="title" />
					<aui:option label="abstract" />
				</aui:select>

				<aui:select label="article-window-state" name="preferences--kbArticleWindowState--" value="<%= kbSectionPortletInstanceConfiguration.kbArticleWindowState() %>">
					<aui:option label="maximized" value="<%= WindowState.MAXIMIZED.toString() %>" />
					<aui:option label="normal" value="<%= WindowState.NORMAL.toString() %>" />
				</aui:select>

				<div class="kb-block-labels kb-field-wrapper">
					<aui:select inlineField="<%= true %>" label="order-by" name="preferences--kbArticlesOrderByCol--" value="<%= kbSectionPortletInstanceConfiguration.kbArticlesOrderByCol() %>">
						<aui:option label="create-date" />
						<aui:option label="modified-date" />
						<aui:option label="priority" />
						<aui:option label="title" />
						<aui:option label="view-count" />
					</aui:select>

					<aui:select inlineField="<%= true %>" label="<%= StringPool.NBSP %>" name="preferences--kbArticlesOrderByType--" value="<%= kbSectionPortletInstanceConfiguration.kbArticlesOrderByType() %>">
						<aui:option label="ascending" value="asc" />
						<aui:option label="descending" value="desc" />
					</aui:select>
				</div>

				<aui:select cssClass="kb-field-wrapper" label="items-per-page" name="preferences--kbArticlesDelta--">

					<%
					int[] pageDeltaValues = GetterUtil.getIntegerValues(PropsUtil.getArray(PropsKeys.SEARCH_CONTAINER_PAGE_DELTA_VALUES));

					Arrays.sort(pageDeltaValues);

					for (int pageDeltaValue : pageDeltaValues) {
					%>

						<aui:option label="<%= pageDeltaValue %>" selected="<%= kbArticlesDelta == pageDeltaValue %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input label="show-pagination" name="preferences--showKBArticlesPagination--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.showKBArticlesPagination() %>" />
			</c:when>
			<c:when test='<%= tabs2.equals("display-settings") %>'>
				<aui:input label="enable-description" name="preferences--enableKBArticleDescription--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.enableKBArticleDescription() %>" />

				<aui:input label="enable-ratings" name="preferences--enableKBArticleRatings--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.enableKBArticleRatings() %>" />

				<aui:input label="show-asset-entries" name="preferences--showKBArticleAssetEntries--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.showKBArticleAssetEntries() %>" />

				<aui:input label="show-attachments" name="preferences--showKBArticleAttachments--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.showKBArticleAttachments() %>" />

				<aui:input label="enable-related-assets" name="preferences--enableKBArticleAssetLinks--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.enableKBArticleAssetLinks() %>" />

				<aui:input label="enable-view-count-increment" name="preferences--enableKBArticleViewCountIncrement--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.enableKBArticleViewCountIncrement() %>" />

				<aui:input label="enable-subscriptions" name="preferences--enableKBArticleSubscriptions--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.enableKBArticleSubscriptions() %>" />

				<aui:input label="enable-history" name="preferences--enableKBArticleHistory--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.enableKBArticleHistory() %>" />

				<aui:input label="enable-print" name="preferences--enableKBArticlePrint--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.enableKBArticlePrint() %>" />

				<liferay-ui:social-bookmarks-settings
					displayPosition="<%= kbSectionPortletInstanceConfiguration.socialBookmarksDisplayPosition() %>"
					displayStyle="<%= kbSectionPortletInstanceConfiguration.socialBookmarksDisplayStyle() %>"
					enabled="<%= kbSectionPortletInstanceConfiguration.enableSocialBookmarks() %>"
					types="<%= kbSectionPortletInstanceConfiguration.socialBookmarksTypes() %>"
				/>
			</c:when>
		</c:choose>

		<aui:button-row cssClass="kb-submit-buttons">
			<aui:button type="submit" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>