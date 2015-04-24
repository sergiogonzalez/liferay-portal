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

package com.liferay.wiki.engine.input.editor.common;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.SessionClicks;
import com.liferay.wiki.engine.BaseWikiEngine;
import com.liferay.wiki.model.WikiPage;

import java.io.IOException;

import javax.portlet.RenderResponse;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Iván Zaera
 */
public abstract class InputEditorWikiEngine extends BaseWikiEngine {

	public static final String INPUT_EDITOR_WIKI_ENGINE =
		"inputEditorWikiEngine";

	public static InputEditorWikiEngine getInputEditorWikiEngine(
		ServletRequest servletRequest) {

		return (InputEditorWikiEngine)servletRequest.getAttribute(
			INPUT_EDITOR_WIKI_ENGINE);
	}

	public abstract String getEditorName();

	public String getHelpPage() {
		return _helpPage;
	}

	public String getHelpURL() {
		return _helpURL;
	}

	public String getToggleId(PageContext pageContext) {
		RenderResponse renderResponse =
			(RenderResponse)pageContext.getAttribute("renderResponse");

		return renderResponse.getNamespace() + "toggle_id_wiki_editor_help";
	}

	public boolean isHelpPageDefined() {
		if (Validator.isNotNull(_helpPage)) {
			return true;
		}

		return false;
	}

	public boolean isSyntaxHelpVisible(PageContext pageContext) {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		String toggleValue = SessionClicks.get(
			request, getToggleId(pageContext), null);

		if ((toggleValue != null) && toggleValue.equals("block")) {
			return true;
		}

		return false;
	}

	@Override
	public void renderEditPage(
			ServletRequest request, ServletResponse response, WikiPage wikiPage)
		throws IOException, ServletException {

		request.setAttribute(INPUT_EDITOR_WIKI_ENGINE, this);

		super.renderEditPage(request, response, wikiPage);
	}

	protected InputEditorWikiEngine(
		String editJsp, String helpPage, String helpURL) {

		super(_getEditJsp(editJsp));

		_helpPage = helpPage;
		_helpURL = helpURL;
	}

	private static String _getEditJsp(String editJsp) {
		if (editJsp == null) {
			return "/wiki-engine-input-editor-common/edit_page.jsp";
		}

		return editJsp;
	}

	private final String _helpPage;
	private final String _helpURL;

}