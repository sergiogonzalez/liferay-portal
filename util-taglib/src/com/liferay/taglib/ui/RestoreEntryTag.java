package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Diaz
 */
public class RestoreEntryTag extends IncludeTag {

	public void setCurrentURL(String currentURL) {
		_currentURL= currentURL;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		request.setAttribute(
			"liferay-ui:restore-entry:currentURL", _currentURL);
	}

	private static final String _PAGE =
		"/html/taglib/ui/restore_entry/page.jsp";

	private String _currentURL;

}