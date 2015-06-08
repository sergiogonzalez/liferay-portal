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

package com.liferay.portlet.blogs.util;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.struts.BaseFindActionHelper;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class BlogsFindEntryHelper extends BaseFindActionHelper {

	@Override
	public long getGroupId(long primaryKey) throws Exception {
		BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(primaryKey);

		return entry.getGroupId();
	}

	@Override
	public String getPrimaryKeyParameterName() {
		return "entryId";
	}

	@Override
	public String[] initPortletIds() {

		// Order is important. See LPS-23770.

		return new String[] {
			PortletKeys.BLOGS_ADMIN, PortletKeys.BLOGS,
			PortletKeys.BLOGS_AGGREGATOR
		};
	}

	@Override
	public PortletURL processPortletURL(
			HttpServletRequest request, PortletURL portletURL)
		throws Exception {

		return portletURL;
	}

	@Override
	public void setPrimaryKeyParameter(PortletURL portletURL, long primaryKey)
		throws Exception {

		BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(primaryKey);

		portletURL.setParameter("urlTitle", entry.getUrlTitle());
	}

	@Override
	protected void addRequiredParameters(
		HttpServletRequest request, String portletId, PortletURL portletURL) {

		String strutsAction = StringPool.BLANK;

		if (portletId.equals(PortletKeys.BLOGS_ADMIN)) {
			strutsAction = "/blogs_admin";
		}
		else if (portletId.equals(PortletKeys.BLOGS)) {
			strutsAction = "/blogs";
		}
		else {
			strutsAction = "/blogs_aggregator";
		}

		boolean showAllEntries = ParamUtil.getBoolean(
			request, "showAllEntries");

		if (showAllEntries) {
			strutsAction += "/view";
		}
		else {
			strutsAction += "/view_entry";
		}
	}

}