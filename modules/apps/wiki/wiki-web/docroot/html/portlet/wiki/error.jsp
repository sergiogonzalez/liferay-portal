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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<liferay-ui:error-header />

<liferay-ui:error exception="<%= NoSuchNodeException.class %>" message="please-enter-a-valid-page-title" />

<c:if test="<%= SessionErrors.contains(renderRequest, NoSuchPageException.class.getName()) %>">

	<%
	long nodeId = ParamUtil.getLong(request, "nodeId");

	if (nodeId == 0) {
		WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);

		if (node != null) {
			nodeId = node.getNodeId();
		}
	}

	WikiPage wikiPage = (WikiPage)renderRequest.getAttribute(WikiWebKeys.WIKI_PAGE);

	String title = StringPool.BLANK;
	String redirectPageTitle = StringPool.BLANK;

	if (wikiPage != null) {
		title = wikiPage.getTitle();
		redirectPageTitle = wikiPage.getRedirectTitle();
	}
	else {
		title = ParamUtil.getString(request, "title");
	}

	boolean hasDraftPage = false;

	if (nodeId > 0) {
		hasDraftPage = WikiPageLocalServiceUtil.hasDraftPage(nodeId, title);
	}
	%>

	<c:choose>
		<c:when test="<%= hasDraftPage %>">

			<%
			WikiPage draftPage = WikiPageLocalServiceUtil.getDraftPage(nodeId, title);

			boolean editableDraft = false;

			if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId) || (draftPage.getUserId() == user.getUserId())) {
				editableDraft = true;
			}
			%>

			<c:choose>
				<c:when test="<%= editableDraft %>">
					<div class="alert alert-info">
						<liferay-ui:message key="this-page-has-an-associated-draft-that-is-not-yet-published" />
					</div>

					<div class="btn-toolbar">

						<%
						PortletURL editDraftPageURL = renderResponse.createRenderURL();

						editDraftPageURL.setParameter("struts_action", "/wiki/edit_page");
						editDraftPageURL.setParameter("redirect", currentURL);
						editDraftPageURL.setParameter("nodeId", String.valueOf(nodeId));
						editDraftPageURL.setParameter("title", title);

						String taglibEditPage = "location.href = '" + editDraftPageURL.toString() + "';";
						%>

						<aui:button onClick="<%= taglibEditPage %>" value="edit-draft" />
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-info">
						<liferay-ui:message key="this-page-has-already-been-started-by-another-author" />
					</div>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<c:if test="<%= Validator.isNotNull(redirectPageTitle) %>">
				<div class="page-redirect-link">

					<%
					PortletURL viewPageURL = renderResponse.createRenderURL();

					viewPageURL.setParameter("struts_action", "/wiki/view");
					viewPageURL.setParameter("nodeId", String.valueOf(nodeId));
					viewPageURL.setParameter("title", wikiPage.getTitle());
					viewPageURL.setParameter("followRedirect", "false");
					%>

					<div class="page-redirect" onClick="location.href = '<%= viewPageURL.toString() %>';">
						(<%= LanguageUtil.format(request, "redirected-from-x", wikiPage.getTitle(), false) %>)
					</div>
				</div>
			</c:if>

			<div class="alert alert-info">
				<liferay-ui:message key="this-page-is-empty.-use-the-buttons-below-to-create-it-or-to-search-for-the-words-in-the-title" />
			</div>

			<div class="btn-toolbar">

				<%
				PortletURL searchURL = renderResponse.createRenderURL();

				searchURL.setParameter("struts_action", "/wiki/search");
				searchURL.setParameter("redirect", currentURL);
				searchURL.setParameter("nodeId", String.valueOf(nodeId));
				searchURL.setParameter("keywords", Validator.isNotNull(redirectPageTitle) ? redirectPageTitle : title);

				String taglibSearch = "location.href = '" + searchURL.toString() + "';";
				%>

				<aui:button onClick="<%= taglibSearch %>" value='<%= LanguageUtil.format(request, "search-for-x", HtmlUtil.escapeAttribute(Validator.isNotNull(redirectPageTitle) ? redirectPageTitle : title), false) %>' />

				<%
				PortletURL editPageURL = renderResponse.createRenderURL();

				editPageURL.setParameter("struts_action", "/wiki/edit_page");
				editPageURL.setParameter("redirect", currentURL);
				editPageURL.setParameter("nodeId", String.valueOf(nodeId));
				editPageURL.setParameter("title", Validator.isNotNull(redirectPageTitle) ? redirectPageTitle : title);

				String taglibEditPage = "location.href = '" + editPageURL.toString() + "';";
				%>

				<aui:button onClick="<%= taglibEditPage %>" value='<%= LanguageUtil.format(request, "create-page-x", HtmlUtil.escapeAttribute(Validator.isNotNull(redirectPageTitle) ? redirectPageTitle : title), false) %>' />
			</div>
		</c:otherwise>
	</c:choose>
</c:if>

<liferay-ui:error exception="<%= PageTitleException.class %>" message="please-enter-a-valid-page-title" />
<liferay-ui:error exception="<%= PrincipalException.class %>" message="you-do-not-have-the-required-permissions" />