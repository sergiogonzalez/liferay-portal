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
WikiPage wikiPage = (WikiPage)request.getAttribute("wikiPage");

String content = BeanParamUtil.getString(wikiPage, request, "content");

String toggleId = renderResponse.getNamespace() + "toggle_id_wiki_editor_help";

String toggleValue = SessionClicks.get(request, toggleId, null);

boolean showSyntaxHelp = ((toggleValue != null) && toggleValue.equals("block"));
%>

<div align="right">
	<liferay-ui:toggle
		defaultShowContent="<%= false %>"
		hideMessage='<%= LanguageUtil.get(request, "hide-syntax-help") + " &raquo;" %>'
		id="<%= toggleId %>"
		showMessage='<%= "&laquo; " + LanguageUtil.get(request, "show-syntax-help") %>'
	/>
</div>

<div>
	<aui:row>
		<aui:col id="wikiEditorContainer" width="<%= showSyntaxHelp ? 70 : 100 %>">

			<%@ include file="/editor_config.jspf" %>

			<liferay-ui:input-editor
				configParams="<%= configParams %>"
				contents="<%= content %>"
				editorName="<%= wikiGroupServiceConfiguration.getCreoleEditor() %>"
				fileBrowserParams="<%= fileBrowserParams %>"
				toolbarSet="creole"
				width="100%"
			/>

			<aui:input name="content" type="hidden" />
		</aui:col>

		<aui:col cssClass="syntax-help" id="toggle_id_wiki_editor_help" style='<%= showSyntaxHelp ? StringPool.BLANK : "display: none" %>' width="<%= 30 %>">
			<h3>
				<liferay-ui:message key="syntax-help" />
			</h3>

			<liferay-util:include page="/help_page.jsp" servletContext="<%= application %>" />

			<aui:a href="http://www.wikicreole.org/wiki/Creole1.0" target="_blank"><liferay-ui:message key="learn-more" /> &raquo;</aui:a>
		</aui:col>
	</aui:row>
</div>

<aui:script sandbox="<%= true %>">
	var CSS_EDITOR_WIDTH = 'col-md-8';

	var CSS_EDITOR_WIDTH_EXPANDED = 'col-md-12';

	Liferay.on(
		'toggle:stateChange',
		function(event) {
			if (event.id === '<%= toggleId %>') {
				var classSrc = CSS_EDITOR_WIDTH;
				var classDest = CSS_EDITOR_WIDTH_EXPANDED;

				if (event.state === 1) {
					classSrc = CSS_EDITOR_WIDTH_EXPANDED;
					classDest = CSS_EDITOR_WIDTH;
				}

				var editorContainer = $('#<portlet:namespace />wikiEditorContainer');

				editorContainer.addClass(classDest);
				editorContainer.removeClass(classSrc);

				var editorInstance = window['<portlet:namespace />editor'];

				if (editorInstance) {
					editorInstance.focus();
				}
			}
		}
	);
</aui:script>