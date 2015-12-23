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

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.SessionClicks;
import com.liferay.taglib.servlet.PipingServletResponse;
import com.liferay.wiki.engine.BaseWikiEngine;
import com.liferay.wiki.engine.input.editor.common.util.WikiEngineInputEditorCommonComponentProvider;
import com.liferay.wiki.model.WikiPage;

import java.io.IOException;

import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Iván Zaera
 */
public abstract class BaseInputEditorWikiEngine extends BaseWikiEngine {

	public static BaseInputEditorWikiEngine getBaseInputEditorWikiEngine(
		ServletRequest servletRequest) {

		return (BaseInputEditorWikiEngine)servletRequest.getAttribute(
			_BASE_INPUT_EDITOR_WIKI_ENGINE);
	}

	public abstract String getEditorName();

	public String getSyntaxHelpLinkId(PageContext pageContext) {
		RenderResponse renderResponse =
			(RenderResponse)pageContext.getAttribute("renderResponse");

		return renderResponse.getNamespace() + "syntax_help_link_id";
	}

	public String getSyntaxHelpPageHTML(PageContext pageContext)
		throws IOException, ServletException {

		if (!isSyntaxHelpPageDefined()) {
			return StringPool.BLANK;
		}

		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			response, unsyncStringWriter);

		ServletContext servletContext = getSyntaxHelpPageServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(getHelpPageJSP());

		requestDispatcher.include(
			pageContext.getRequest(), pipingServletResponse);

		StringBundler sb = unsyncStringWriter.getStringBundler();

		return sb.toString();
	}

	public abstract String getSyntaxHelpPageLinkURL();

	public abstract String getSyntaxHelpPageTitle(HttpServletRequest request);

	public boolean isSyntaxHelpLinkVisible(PageContext pageContext) {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		String toggleValue = SessionClicks.get(
			request, getSyntaxHelpLinkId(pageContext), null);

		if ((toggleValue != null) && toggleValue.equals("block")) {
			return true;
		}

		return false;
	}

	public boolean isSyntaxHelpPageDefined() {
		if ((getSyntaxHelpPageServletContext() == null) ||
			Validator.isNull(getHelpPageJSP())) {

			return false;
		}

		return true;
	}

	@Override
	public void renderEditPage(
			ServletRequest servletRequest, ServletResponse servletResponse,
			WikiPage page)
		throws IOException, ServletException {

		servletRequest.setAttribute(_BASE_INPUT_EDITOR_WIKI_ENGINE, this);

		super.renderEditPage(servletRequest, servletResponse, page);
	}

	@Override
	protected ServletContext getEditPageServletContext() {
		WikiEngineInputEditorCommonComponentProvider
			wikiEngineInputEditorCommonComponentProvider =
				WikiEngineInputEditorCommonComponentProvider.
					getWikiEngineInputEditorCommonComponentProvider();

		return wikiEngineInputEditorCommonComponentProvider.getServletContext();
	}

	protected String getHelpPageJSP() {
		return "/help_page.jsp";
	}

	protected abstract ServletContext getSyntaxHelpPageServletContext();

	private static final String _BASE_INPUT_EDITOR_WIKI_ENGINE =
		BaseInputEditorWikiEngine.class.getName() +
			"#BASE_INPUT_EDITOR_WIKI_ENGINE";

}