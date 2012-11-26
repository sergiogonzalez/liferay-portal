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

	public void setOverrideLabelMessage(String overrideLabelMessage) {
		_overrideLabelMessage= overrideLabelMessage;
	}

	public void setRenameLabelMessage(String renameLabelMessage) {
		_renameLabelMessage= renameLabelMessage;
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
		request.setAttribute(
			"liferay-ui:restore-entry:overrideLabelMessage",
			_overrideLabelMessage);
		request.setAttribute(
			"liferay-ui:restore-entry:renameLabelMessage", _renameLabelMessage);
	}

	private static final String _PAGE =
		"/html/taglib/ui/restore_entry/page.jsp";

	private String _currentURL;
	private String _overrideLabelMessage;
	private String _renameLabelMessage;

}