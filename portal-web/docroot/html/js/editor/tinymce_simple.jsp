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

<%@ include file="/html/taglib/init.jsp" %>

<%
String portletId = portletDisplay.getRootPortletId();

boolean autoCreate =  GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:autoCreate"));
String contents = (String)request.getAttribute("liferay-ui:input-editor:contents");
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClass"));
String editorImpl = (String)request.getAttribute("liferay-ui:input-editor:editorImpl");
String initMethod = (String)request.getAttribute("liferay-ui:input-editor:initMethod");
String name = namespace + GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:name"));

String onChangeMethod = (String)request.getAttribute("liferay-ui:input-editor:onChangeMethod");

if (Validator.isNotNull(onChangeMethod)) {
	onChangeMethod = namespace + onChangeMethod;
}

String onInitMethod = (String)request.getAttribute("liferay-ui:input-editor:onInitMethod");

if (Validator.isNotNull(onInitMethod)) {
	onInitMethod = namespace + onInitMethod;
}

boolean resizable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:resizable"));
boolean showSource = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:showSource"));
boolean skipEditorLoading = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:skipEditorLoading"));
%>

<liferay-util:buffer var="editor">
	<textarea id="<%= name %>" name="<%= name %>" style="height: 100%; visibility: hidden; width: 100%;"><%= (contents != null) ? contents : StringPool.BLANK %></textarea>
</liferay-util:buffer>

<c:if test="<%= !skipEditorLoading %>">
	<liferay-util:html-top outputKey="js_editor_tinymce">

		<%
		long javaScriptLastModified = ServletContextUtil.getLastModified(application, "/html/js/", true);
		%>

		<script src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNHost() + themeDisplay.getPathJavaScript() + "/editor/tiny_mce/tiny_mce.js", javaScriptLastModified)) %>" type="text/javascript"></script>

		<script type="text/javascript">
			Liferay.namespace('EDITORS')['<%= editorImpl %>'] = true;
		</script>
	</liferay-util:html-top>
</c:if>

<div class="<%= cssClass %>" id="<%= name %>_container">
	<c:if test="<%= autoCreate %>">
		<%= editor %>
	</c:if>
</div>

<aui:script use="aui-node-base">
	window['<%= name %>'] = {
		onChangeCallbackCounter: 0,

		init: function(value) {
			if (typeof value != 'string') {
				value = '';
			}

			window['<%= name %>'].setHTML(value);
		},

		create: function() {
			var editorEl = A.Node.create('<%= HtmlUtil.escapeJS(editor) %>');

			var editorContainer = A.one('#<%= name %>_container');

			editorContainer.appendChild(editorEl);

			window['<%= name %>'].initEditor();
		},

		destroy: function() {
			window['<%= name %>'].dispose();

			window['<%= name %>'] = null;
		},

		dispose: function() {
			var editorEl= A.one('textarea#<%= name %>');

			if (editorEl) {
				editorEl.remove();
			}

			var tinyMCEEditor = tinyMCE.editors['<%= name %>'];

			if (tinyMCEEditor) {
				tinyMCEEditor.remove();

				tinyMCEEditor.destroy();
			}
		},

		fileBrowserCallback: function(field_name, url, type) {
		},

		focus: function() {
			tinyMCE.editors['<%= name %>'].focus();
		},

		getHTML: function() {
			var data;

			var contents = '<%= contents %>';

			if ((contents === '') && !window['<%= name %>'].instanceReady && window['<%= HtmlUtil.escape(namespace + initMethod) %>']) {
				data = <%= HtmlUtil.escape(namespace + initMethod) %>();
			}
			else {
				data = tinyMCE.editors['<%= name %>'].getContent();
			}

			return data;
		},

		initEditor: function() {
			tinyMCE.init(
				{
					content_css: '<%= HtmlUtil.escapeJS(themeDisplay.getPathThemeCss()) %>/aui.css,<%= HtmlUtil.escapeJS(themeDisplay.getPathThemeCss()) %>/main.css',
					convert_urls: false,
					elements: '<%= name %>',
					extended_valid_elements: 'a[name|href|target|title|onclick],img[class|src|border=0|alt|title|hspace|vspace|width|height|align|onmouseover|onmouseout|name|usemap],hr[class|width|size|noshade],font[face|size|color|style],span[class|align|style]',
					file_browser_callback: window['<%= name %>'].fileBrowserCallback,
					init_instance_callback: window['<%= name %>'].initInstanceCallback,
					invalid_elements: 'script',
					language: '<%= HtmlUtil.escape(locale.getLanguage()) %>',
					mode: 'textareas',

					<%
					if (Validator.isNotNull(onChangeMethod)) {
					%>

						onchange_callback: window['<%= name %>'].onChangeCallback,

					<%
					}
					%>

					plugins: 'preview,print,contextmenu',
					relative_urls: false,
					remove_script_host: false,
					theme: 'advanced',
					theme_advanced_buttons2: '',
					theme_advanced_buttons3: '',
					theme_advanced_buttons1_add: '<c:if test="<%= showSource %>">code,</c:if>preview,print',
					theme_advanced_disable: 'formatselect,styleselect,help,strikethrough',
					theme_advanced_resize_horizontal: '<%= resizable %>',
					theme_advanced_toolbar_align: 'left',
					theme_advanced_toolbar_location: 'top'
				}
			);
		},

		initInstanceCallback: function() {
			<c:if test="<%= (contents == null) && Validator.isNotNull(initMethod) %>">
				window['<%= name %>'].init(<%= HtmlUtil.escape(namespace + initMethod) %>());
			</c:if>

			var iframe = A.one('#<%= name %>_ifr');

			if (iframe) {
				var iframeWin = iframe.getDOM().contentWindow;

				if (iframeWin) {
					var iframeDoc = iframeWin.document.documentElement;

					A.one(iframeDoc).addClass('aui');
				}
			}

			<c:if test="<%= Validator.isNotNull(onInitMethod) %>">
				window['<%= HtmlUtil.escapeJS(namespace + onInitMethod) %>']();
			</c:if>

			window['<%= name %>'].instanceReady = true;
		},

		instanceReady: false,

		<%
		if (Validator.isNotNull(onChangeMethod)) {
		%>

			onChangeCallback: function(tinyMCE) {

				// This purposely ignores the first callback event because each call
				// to setContent triggers an undo level which fires the callback
				// when no changes have yet been made.

				// setContent is not really the correct way of initializing this
				// editor with content. The content should be placed statically
				// (from the editor's perspective) within the textarea. This is a
				// problem from the portal's perspective because it's passing the
				// content via a javascript method (initMethod).

				var onChangeCallbackCounter = window['<%= name %>'].onChangeCallbackCounter;

				if (onChangeCallbackCounter > 0) {

					<%= HtmlUtil.escapeJS(onChangeMethod) %>(window['<%= name %>'].getHTML());

				}

				window['<%= name %>'].onChangeCallbackCounter++;
			},

		<%
		}
		%>

		setHTML: function(value) {
			tinyMCE.editors['<%= name %>'].setContent(value);
		}
	};

	<c:if test="<%= autoCreate %>">
		window['<%= name %>'].initEditor();
	</c:if>

	var destroyInstance = function(event) {
		if (event.portletId === '<%= portletId %>') {
			window['<%= name %>'].destroy();

			Liferay.detach('destroyPortlet', destroyInstance);
		}
	};

	Liferay.on('destroyPortlet', destroyInstance);
</aui:script>