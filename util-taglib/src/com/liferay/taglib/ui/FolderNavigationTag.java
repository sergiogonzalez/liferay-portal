/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class FolderNavigationTag extends IncludeTag {

	@Override
	protected String getPage() {
		return _PAGE;
	}

	public void setActionFolderJSP(String actionFolderJSP) {
		_actionFolderJSP = actionFolderJSP;
	}

	public void setCurFolderId(long curFolderId) {
		_curFolderId = curFolderId;
	}

	public void setCurFolderName(String curFolderName) {
		_curFolderName = curFolderName;
	}

	public void setExpandNode(boolean expandNode) {
		_expandNode = expandNode;
	}

	public void setImageNode(String imageNode) {
		_imageNode = imageNode;
	}

	public void setSelectedFolder(boolean selectedFolder) {
		_selectedFolder = selectedFolder;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public void setViewURL(PortletURL viewURL) {
		_viewURL = viewURL;
	}

	@Override
	protected void cleanUp() {
		_actionFolderJSP = null;
		_curFolderId = 0;
		_curFolderName = null;
		_expandNode = false;
		_imageNode = null;
		_selectedFolder = false;
		_repositoryId = 0;
		_viewURL = null;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:folder-navigator:actionFolderJSP", _actionFolderJSP);
		request.setAttribute(
			"liferay-ui:folder-navigator:curFolderId", _curFolderId);
		request.setAttribute(
			"liferay-ui:folder-navigator:curFolderName", _curFolderName);
		request.setAttribute(
			"liferay-ui:folder-navigator:expandNode", _expandNode);
		request.setAttribute(
			"liferay-ui:folder-navigator:imageNode", _imageNode);
		request.setAttribute(
			"liferay-ui:folder-navigator:selectedFolder", _selectedFolder);
		request.setAttribute(
			"liferay-ui:folder-navigator:repositoryId", _repositoryId);
		request.setAttribute("liferay-ui:folder-navigator:viewURL", _viewURL);
	}

	private static final String _PAGE =
		"/html/taglib/ui/folder_navigation/page.jsp";

	private String _actionFolderJSP;
	private long _curFolderId;
	private String _curFolderName;
	private boolean _expandNode;
	private String _imageNode;
	private boolean _selectedFolder;
	private long _repositoryId;
	private PortletURL _viewURL;
}