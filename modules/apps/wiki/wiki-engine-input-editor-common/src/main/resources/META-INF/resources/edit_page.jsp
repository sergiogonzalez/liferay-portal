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
BaseInputEditorWikiEngine baseInputEditorWikiEngine = BaseInputEditorWikiEngine.getBaseInputEditorWikiEngine(request);

WikiPage wikiPage = BaseInputEditorWikiEngine.getWikiPage(request);

String content = BeanParamUtil.getString(wikiPage, request, "content");
%>

<div>
	<aui:row>
		<aui:col>
			<%@ include file="/editor_config.jspf" %>

			<liferay-ui:input-editor
				configParams="<%= configParams %>"
				contents="<%= content %>"
				editorName="<%= baseInputEditorWikiEngine.getEditorName() %>"
				fileBrowserParams="<%= fileBrowserParams %>"
				toolbarSet="<%= baseInputEditorWikiEngine.getToolbarSet() %>"
			/>

			<aui:input name="content" type="hidden" />
		</aui:col>
	</aui:row>

	<aui:row>
		<aui:col>
			<c:if test="<%= baseInputEditorWikiEngine.isSyntaxHelpPageDefined() %>">
				<div align="right">
					<a href="javascript:;" id="<%= baseInputEditorWikiEngine.getSyntaxHelpLinkId(pageContext) %>"><liferay-ui:message key="show-syntax-help" /> &raquo;</a>
				</div>

				<%
				String helpPageHTML = baseInputEditorWikiEngine.getSyntaxHelpPageHTML(pageContext);
				String syntaxHelpTitle = baseInputEditorWikiEngine.getSyntaxHelpPageTitle(request);
				%>

				<aui:script use="liferay-util-window">
					var helpPageAction = A.one('#<%= baseInputEditorWikiEngine.getSyntaxHelpLinkId(pageContext) %>');

					helpPageAction.on(
						'click',
						function(event) {
							event.preventDefault();

							var helpPageDialog = Liferay.Util.Window.getWindow(
								{
									dialog: {
										bodyContent: '<%= HtmlUtil.escapeJS(helpPageHTML) %>',
										destroyOnHide: true
									},
									title: '<%= HtmlUtil.escapeJS(syntaxHelpTitle) %>'
								}
							);

							helpPageDialog.render();
						}
					);
				</aui:script>
			</c:if>
		</aui:col>
	</aui:row>
</div>