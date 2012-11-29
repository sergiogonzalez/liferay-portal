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

	public void setDuplicationCheckURLAction(String duplicationCheckURLAction) {
		_duplicationCheckURLAction= duplicationCheckURLAction;
	}

	public void setOverrideLabelMessage(String overrideLabelMessage) {
		_overrideLabelMessage= overrideLabelMessage;
	}

	public void setRenameLabelMessage(String renameLabelMessage) {
		_renameLabelMessage= renameLabelMessage;
	}

	public void setRestoreURLAction( String restoreURLAction) {
		_restoreURLAction= restoreURLAction;
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
			"liferay-ui:restore-entry:duplicationCheckURLAction",
			_duplicationCheckURLAction);
		request.setAttribute(
			"liferay-ui:restore-entry:overrideLabelMessage",
			_overrideLabelMessage);
		request.setAttribute(
			"liferay-ui:restore-entry:renameLabelMessage", _renameLabelMessage);
		request.setAttribute(
			"liferay-ui:restore-entry:restoreURLAction", _restoreURLAction);
	}

	private static final String _PAGE =
		"/html/taglib/ui/restore_entry/page.jsp";

	private String _currentURL;
	private String _duplicationCheckURLAction;
	private String _overrideLabelMessage;
	private String _renameLabelMessage;
	private String _restoreURLAction;

}