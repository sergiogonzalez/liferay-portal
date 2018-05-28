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

<liferay-util:dynamic-include key="com.liferay.blogs.web#/blogs/asset/full_content.jsp#pre" />

<%
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute(WebKeys.ASSET_RENDERER);

BlogsPortletInstanceConfiguration blogsPortletInstanceConfiguration = ConfigurationProviderUtil.getConfiguration(BlogsPortletInstanceConfiguration.class, new PortletInstanceSettingsLocator(themeDisplay.getLayout(), BlogsPortletKeys.BLOGS));

BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

String entryTitle = BlogsEntryUtil.getDisplayTitle(resourceBundle, entry);

String viewEntryURL = assetRenderer.getURLView(liferayPortletResponse, WindowState.MAXIMIZED);
%>

<div class="widget-mode-simple" data-analytics-asset-id="<%= String.valueOf(entry.getEntryId()) %>" data-analytics-asset-title="<%= HtmlUtil.escapeAttribute(entryTitle) %>" data-analytics-asset-type="blog">
	<div class="widget-mode-simple-entry">
		<div class="autofit-row widget-topbar">
			<div class="autofit-col autofit-col-expand">
				<aui:a cssClass="title-link" href="<%= viewEntryURL %>"><h3 class="title"><%= HtmlUtil.escape(BlogsEntryUtil.getDisplayTitle(resourceBundle, entry)) %></h3></aui:a>

				<%
				String subtitle = entry.getSubtitle();
				%>

				<c:if test="<%= Validator.isNotNull(subtitle) %>">
					<h4 class="sub-title"><%= HtmlUtil.escape(subtitle) %></h4>
				</c:if>
			</div>
		</div>

		<div class="autofit-row widget-metadata">
			<div class="autofit-col inline-item-before">

				<%
				User entryUser = UserLocalServiceUtil.fetchUser(entry.getUserId());

				String entryUserURL = StringPool.BLANK;

				if ((entryUser != null) && !entryUser.isDefaultUser()) {
					entryUserURL = entryUser.getDisplayURL(themeDisplay);
				}
				%>

				<liferay-ui:user-portrait
					cssClass="user-icon-lg"
					user="<%= entryUser %>"
				/>
			</div>

			<div class="autofit-col autofit-col-expand">
				<div class="autofit-row">
					<div class="autofit-col autofit-col-expand">
						<a class="username" href="<%= entryUserURL %>"><%= entry.getUserName() %></a>

						<div class="text-secondary">
							<span class="hide-accessible"><liferay-ui:message key="published-date" /></span><liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - entry.getStatusDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />

							<c:if test="<%= blogsPortletInstanceConfiguration.enableReadingTime() %>">
								- <liferay-reading-time:reading-time displayStyle="descriptive" model="<%= entry %>" />
							</c:if>

							<c:if test="<%= blogsPortletInstanceConfiguration.enableViewCount() %>">

								<%
								AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(BlogsEntry.class.getName(), entry.getEntryId());
								%>

								- <liferay-ui:message arguments="<%= assetEntry.getViewCount() %>" key='<%= assetEntry.getViewCount() == 1 ? "x-view" : "x-views" %>' />
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="widget-content" id="<portlet:namespace /><%= entry.getEntryId() %>">

			<%
			String coverImageURL = entry.getCoverImageURL(themeDisplay);
			%>

			<c:if test="<%= Validator.isNotNull(coverImageURL) %>">
				<a href="<%= viewEntryURL %>">
					<div class="aspect-ratio aspect-ratio-8-to-3 aspect-ratio-bg-cover cover-image" style="background-image: url(<%= coverImageURL %>)"></div>
				</a>
			</c:if>

			<%= entry.getContent() %>
		</div>

		<liferay-expando:custom-attributes-available
			className="<%= BlogsEntry.class.getName() %>"
		>
			<liferay-expando:custom-attribute-list
				className="<%= BlogsEntry.class.getName() %>"
				classPK="<%= (entry != null) ? entry.getEntryId() : 0 %>"
				editable="<%= false %>"
				label="<%= true %>"
			/>
		</liferay-expando:custom-attributes-available>
	</div>
</div>

<liferay-util:dynamic-include key="com.liferay.blogs.web#/blogs/asset/full_content.jsp#post" />