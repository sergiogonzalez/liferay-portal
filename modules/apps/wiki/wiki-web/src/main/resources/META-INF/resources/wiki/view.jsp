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

<%@ include file="/wiki/init.jsp" %>

<liferay-util:dynamic-include key="com.liferay.wiki.web#/wiki/view.jsp#pre" />

<%
boolean followRedirect = ParamUtil.getBoolean(request, "followRedirect", true);

WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

WikiPage originalPage = null;
WikiPage redirectPage = wikiPage.getRedirectPage();

if (followRedirect && (redirectPage != null)) {
	originalPage = wikiPage;
	wikiPage = redirectPage;
}

String title = wikiPage.getTitle();

String parentTitle = StringPool.BLANK;

if (wikiPage != null) {
	parentTitle = wikiPage.getParentTitle();
}

List childPages = wikiPage.getViewableChildPages();

int attachmentsFileEntriesCount = 0;

if (wikiPage != null) {
	attachmentsFileEntriesCount = wikiPage.getAttachmentsFileEntriesCount();
}

boolean preview = false;
boolean print = ParamUtil.getString(request, "viewMode").equals(Constants.PRINT);

PortletURL viewPageURL = renderResponse.createRenderURL();

if (portletName.equals(WikiPortletKeys.WIKI_DISPLAY)) {
	viewPageURL.setParameter("mvcRenderCommandName", "/wiki/view_page");
}
else {
	viewPageURL.setParameter("mvcRenderCommandName", "/wiki/view");
}

viewPageURL.setParameter("nodeName", node.getName());
viewPageURL.setParameter("title", title);

PortletURL viewParentPageURL = null;

if (Validator.isNotNull(parentTitle)) {
	viewParentPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

	viewParentPageURL.setParameter("title", parentTitle);

	parentTitle = StringUtil.shorten(parentTitle, 20);
}

PortletURL addPageURL = renderResponse.createRenderURL();

addPageURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
addPageURL.setParameter("redirect", currentURL);
addPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
addPageURL.setParameter("title", StringPool.BLANK);
addPageURL.setParameter("editTitle", "1");

if (wikiPage != null) {
	addPageURL.setParameter("parentTitle", wikiPage.getTitle());
}

PortletURL editPageURL = renderResponse.createRenderURL();

editPageURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
editPageURL.setParameter("redirect", currentURL);
editPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
editPageURL.setParameter("title", title);

PortletURL printPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

printPageURL.setParameter("viewMode", Constants.PRINT);
printPageURL.setWindowState(LiferayWindowState.POP_UP);

PortletURL categorizedPagesURL = renderResponse.createRenderURL();

categorizedPagesURL.setParameter("mvcRenderCommandName", "/wiki/view_categorized_pages");
categorizedPagesURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

PortletURL taggedPagesURL = renderResponse.createRenderURL();

taggedPagesURL.setParameter("mvcRenderCommandName", "/wiki/view_tagged_pages");
taggedPagesURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

PortletURL viewAttachmentsURL = PortletURLUtil.clone(viewPageURL, renderResponse);

viewAttachmentsURL.setParameter("mvcRenderCommandName", "/wiki/view_page_attachments");
viewAttachmentsURL.setParameter("redirect", currentURL);

AssetEntryServiceUtil.incrementViewCounter(WikiPage.class.getName(), wikiPage.getResourcePrimKey());

if (Validator.isNotNull(ParamUtil.getString(request, "title"))) {
	AssetUtil.addLayoutTags(request, AssetTagLocalServiceUtil.getTags(WikiPage.class.getName(), wikiPage.getResourcePrimKey()));
}

AssetEntry layoutAssetEntry = null;

if (wikiPage != null) {
	layoutAssetEntry = AssetEntryLocalServiceUtil.getEntry(WikiPage.class.getName(), wikiPage.getResourcePrimKey());

	request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, layoutAssetEntry);
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

WikiURLHelper wikiURLHelper = new WikiURLHelper(wikiRequestHelper, renderResponse, wikiGroupServiceConfiguration);

PortletURL backToViewPagesURL = wikiURLHelper.getBackToViewPagesURL(node);

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack((viewParentPageURL != null) ? viewParentPageURL.toString() : backToViewPagesURL.toString());

	renderResponse.setTitle(wikiPage.getTitle());
}
%>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<c:choose>
		<c:when test="<%= print %>">
			<div class="popup-print">
				<liferay-ui:icon
					iconCssClass="icon-print"
					label="<%= true %>"
					message="print"
					url="javascript:print();"
				/>
			</div>

			<aui:script>
				print();
			</aui:script>
		</c:when>
		<c:otherwise>
			<aui:script>
				function <portlet:namespace />printPage() {
					window.open('<%= printPageURL %>', '', 'directories=0,height=480,left=80,location=1,menubar=1,resizable=1,scrollbars=yes,status=0,toolbar=0,top=180,width=640');
				}
			</aui:script>
		</c:otherwise>
	</c:choose>

	<c:if test="<%= !portletTitleBasedNavigation %>">
		<liferay-util:include page="/wiki/top_links.jsp" servletContext="<%= application %>" />
	</c:if>

	<%
	List entries = new ArrayList();

	entries.add(wikiPage);

	String formattedContent = null;

	try {
		formattedContent = WikiUtil.getFormattedContent(renderRequest, renderResponse, wikiPage, viewPageURL, editPageURL, title, preview);
	}
	catch (Exception e) {
		formattedContent = wikiPage.getContent();
	}

	Map<String, Object> contextObjects = new HashMap<String, Object>();

	contextObjects.put("assetEntry", layoutAssetEntry);
	contextObjects.put("formattedContent", formattedContent);
	contextObjects.put("wikiPortletInstanceOverriddenConfiguration", wikiPortletInstanceOverriddenConfiguration);
	%>

	<liferay-ddm:template-renderer className="<%= WikiPage.class.getName() %>" contextObjects="<%= contextObjects %>" displayStyle="<%= wikiPortletInstanceSettingsHelper.getDisplayStyle() %>" displayStyleGroupId="<%= wikiPortletInstanceSettingsHelper.getDisplayStyleGroupId() %>" entries="<%= entries %>">
		<c:if test="<%= !portletTitleBasedNavigation %>">
			<liferay-ui:header
				backLabel="<%= parentTitle %>"
				backURL="<%= (viewParentPageURL != null) ? viewParentPageURL.toString() : null %>"
				localizeTitle="<%= false %>"
				title="<%= title %>"
			/>
		</c:if>

		<c:if test="<%= !print %>">
			<div class="page-actions top-actions">
				<c:if test="<%= WikiPagePermissionChecker.contains(permissionChecker, wikiPage, ActionKeys.UPDATE) %>">
					<c:if test="<%= followRedirect || (redirectPage == null) %>">
						<liferay-ui:icon
							iconCssClass="icon-edit"
							label="<%= true %>"
							message="edit"
							url="<%= editPageURL.toString() %>"
						/>
					</c:if>
				</c:if>

				<%
				PortletURL viewPageDetailsURL = PortletURLUtil.clone(viewPageURL, renderResponse);

				viewPageDetailsURL.setParameter("mvcRenderCommandName", "/wiki/view_page_details");
				viewPageDetailsURL.setParameter("redirect", currentURL);
				%>

				<liferay-ui:icon
					iconCssClass="icon-file-alt"
					label="<%= true %>"
					message="details"
					method="get"
					url="<%= viewPageDetailsURL.toString() %>"
				/>

				<liferay-ui:icon
					iconCssClass="icon-print"
					label="<%= true %>"
					message="print"
					url='<%= "javascript:" + renderResponse.getNamespace() + "printPage();" %>'
				/>
			</div>
		</c:if>

		<c:if test="<%= originalPage != null %>">

			<%
			PortletURL originalViewPageURL = renderResponse.createRenderURL();

			originalViewPageURL.setParameter("mvcRenderCommandName", "/wiki/view");
			originalViewPageURL.setParameter("nodeName", node.getName());
			originalViewPageURL.setParameter("title", originalPage.getTitle());
			originalViewPageURL.setParameter("followRedirect", "false");
			%>

			<div class="page-redirect" onClick="location.href = '<%= originalViewPageURL.toString() %>';">
				(<liferay-ui:message arguments="<%= originalPage.getTitle() %>" key="redirected-from-x" translateArguments="<%= false %>" />)
			</div>
		</c:if>

		<c:if test="<%= !wikiPage.isHead() %>">
			<div class="page-old-version">
				(<liferay-ui:message key="you-are-viewing-an-archived-version-of-this-page" /> (<%= wikiPage.getVersion() %>), <aui:a href="<%= viewPageURL.toString() %>" label="go-to-the-latest-version" />)
			</div>
		</c:if>

		<div class="page-categorization">
			<div class="page-categories">
				<liferay-ui:asset-categories-summary
					className="<%= WikiPage.class.getName() %>"
					classPK="<%= wikiPage.getResourcePrimKey() %>"
					portletURL="<%= PortletURLUtil.clone(categorizedPagesURL, renderResponse) %>"
				/>
			</div>

			<div class="page-tags">
				<liferay-ui:asset-tags-summary
					className="<%= WikiPage.class.getName() %>"
					classPK="<%= wikiPage.getResourcePrimKey() %>"
					message="tags"
					portletURL="<%= PortletURLUtil.clone(taggedPagesURL, renderResponse) %>"
				/>
			</div>
		</div>

		<div>
			<%@ include file="/wiki/view_page_content.jspf" %>
		</div>

		<liferay-ui:custom-attributes-available className="<%= WikiPage.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= WikiPage.class.getName() %>"
				classPK="<%= (wikiPage != null) ? wikiPage.getPrimaryKey() : 0 %>"
				editable="<%= false %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<c:if test="<%= (wikiPage != null) && Validator.isNotNull(formattedContent) && (followRedirect || (redirectPage == null)) %>">
			<c:if test="<%= !childPages.isEmpty() %>">
				<div class="child-pages">
					<h2><liferay-ui:message key="children-pages" /></h2>

					<ul>

						<%
						PortletURL curPageURL = PortletURLUtil.clone(viewPageURL, renderResponse);

						for (int i = 0; i < childPages.size(); i++) {
							WikiPage curPage = (WikiPage)childPages.get(i);

							curPageURL.setParameter("title", curPage.getTitle());
						%>

							<c:if test="<%= Validator.isNull(curPage.getRedirectTitle()) %>">
								<li>
									<aui:a href="<%= curPageURL.toString() %>"><%= curPage.getTitle() %></aui:a>
								</li>
							</c:if>

						<%
						}
						%>

					</ul>
				</div>
			</c:if>

			<div class="page-actions">
				<div class="article-actions">
					<c:if test="<%= WikiNodePermissionChecker.contains(permissionChecker, node, ActionKeys.ADD_PAGE) %>">
						<liferay-ui:icon
							iconCssClass="icon-plus"
							label="<%= true %>"
							message="add-child-page"
							method="get"
							url="<%= addPageURL.toString() %>"
						/>,
					</c:if>

					<liferay-ui:icon
						iconCssClass="icon-paperclip"
						label="<%= true %>"
						message='<%= attachmentsFileEntriesCount + " " + LanguageUtil.get(request, (attachmentsFileEntriesCount == 1) ? "attachment" : "attachments") %>'
						method="get"
						url="<%= viewAttachmentsURL.toString() %>"
					/>
				</div>

				<div class="stats">

					<%
					AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(WikiPage.class.getName(), wikiPage.getResourcePrimKey());
					%>

					<c:choose>
						<c:when test="<%= assetEntry.getViewCount() == 1 %>">
							<%= assetEntry.getViewCount() %> <liferay-ui:message key="view" />
						</c:when>
						<c:when test="<%= assetEntry.getViewCount() > 1 %>">
							<%= assetEntry.getViewCount() %> <liferay-ui:message key="views" />
						</c:when>
					</c:choose>
				</div>
			</div>

			<c:if test="<%= wikiPortletInstanceSettingsHelper.isEnableRelatedAssets() %>">
				<div class="entry-links">
					<liferay-ui:asset-links
						assetEntryId="<%= assetEntry.getEntryId() %>"
					/>
				</div>
			</c:if>

			<c:if test="<%= wikiPortletInstanceSettingsHelper.isEnablePageRatings() %>">
				<div class="page-ratings">
					<liferay-ui:ratings
						className="<%= WikiPage.class.getName() %>"
						classPK="<%= wikiPage.getResourcePrimKey() %>"
					/>
				</div>
			</c:if>

			<c:if test="<%= wikiPortletInstanceSettingsHelper.isEnableComments() %>">
				<liferay-ui:panel-container extended="<%= false %>" id="wikiCommentsPanelContainer" persistState="<%= true %>">
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="wikiCommentsPanel" persistState="<%= true %>" title="comments">

						<liferay-ui:discussion
							className="<%= WikiPage.class.getName() %>"
							classPK="<%= wikiPage.getResourcePrimKey() %>"
							formName="fm2"
							ratingsEnabled="<%= wikiPortletInstanceSettingsHelper.isEnableCommentRatings() %>"
							redirect="<%= currentURL %>"
							userId="<%= wikiPage.getUserId() %>"
						/>
					</liferay-ui:panel>
				</liferay-ui:panel-container>
			</c:if>
		</c:if>
	</liferay-ddm:template-renderer>

	<aui:script sandbox="<%= true %>">
		var toc = $('#p_p_id<portlet:namespace /> .toc');

		var index = toc.find('.toc-index');

		toc.find('a.toc-trigger').on(
			'click',
			function(event) {
				index.toggleClass('hide');
			}
		);
	</aui:script>

	<%
	if ((wikiPage != null) && !wikiPage.getTitle().equals(wikiGroupServiceConfiguration.frontPageName())) {
		if (!portletName.equals(WikiPortletKeys.WIKI_DISPLAY)) {
			PortalUtil.setPageSubtitle(wikiPage.getTitle(), request);

			String description = wikiPage.getContent();

			if (wikiPage.getFormat().equals("html")) {
				description = HtmlUtil.stripHtml(description);
			}

			description = StringUtil.shorten(description, 200);

			PortalUtil.setPageDescription(description, request);
			PortalUtil.setPageKeywords(AssetUtil.getAssetKeywords(WikiPage.class.getName(), wikiPage.getResourcePrimKey()), request);
		}

		List<WikiPage> parentPages = wikiPage.getViewableParentPages();

		for (WikiPage curParentPage : parentPages) {
			viewPageURL.setParameter("title", curParentPage.getTitle());

			PortalUtil.addPortletBreadcrumbEntry(request, curParentPage.getTitle(), viewPageURL.toString());
		}

		viewPageURL.setParameter("title", wikiPage.getTitle());

		PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), viewPageURL.toString());
	}
	%>

	<liferay-util:dynamic-include key="com.liferay.wiki.web#/wiki/view.jsp#post" />
</div>