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

package com.liferay.blogs.data.creator.impl;

import com.liferay.blogs.data.creator.BlogsLayoutDataCreator;
import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = BlogsLayoutDataCreator.class)
public class BlogsLayoutDataCreatorImpl implements BlogsLayoutDataCreator {

	public Layout create(long userId, long groupId, String title)
		throws PortalException {

		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(title);

		Layout layout = _layoutLocalService.addLayout(
			userId, groupId, false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			title, title, StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			friendlyURL, new ServiceContext());

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(0, "2_columns_iii", false);

		String portletId = layoutTypePortlet.addPortletId(
			0, BlogsPortletKeys.BLOGS, "column-1", -1, false);

		_layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		Portlet portlet = _portletLocalService.getPortletById(
			layout.getCompanyId(), portletId);

		PortalUtil.addPortletDefaultResource(
			layout.getCompanyId(), layout, portlet);

		_layouts.add(layout);

		return layout;
	}

	public void delete() throws PortalException {
		for (Layout layout : _layouts) {
			_layoutLocalService.deleteLayout(layout);
		}
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	private LayoutLocalService _layoutLocalService;
	private final List<Layout> _layouts = new ArrayList();
	private PortletLocalService _portletLocalService;

}