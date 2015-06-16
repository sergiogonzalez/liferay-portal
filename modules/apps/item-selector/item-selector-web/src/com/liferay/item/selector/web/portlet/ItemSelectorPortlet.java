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

package com.liferay.item.selector.web.portlet;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorRendering;
import com.liferay.item.selector.exception.NoSuchItemSelectorViewException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jose A. Jimenez
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-item-selector",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=false",
		"javax.portlet.display-name=Document Selector",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class ItemSelectorPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		ItemSelectorRendering itemSelectorRendering =
			_itemSelector.getItemSelectorRendering(
				renderRequest, renderResponse);

		LocalizedItemSelectorRendering localizedItemSelectorRendering =
			new LocalizedItemSelectorRendering(
				renderRequest.getLocale(), itemSelectorRendering);

		localizedItemSelectorRendering.store(renderRequest);

		_checkItemSelectorViews(renderRequest, localizedItemSelectorRendering);

		super.render(renderRequest, renderResponse);
	}

	@Reference
	protected void setItemSelector(ItemSelector itemSelector) {
		_itemSelector = itemSelector;
	}

	private void _checkItemSelectorViews(
		RenderRequest renderRequest,
		LocalizedItemSelectorRendering localizedItemSelectorRendering) {

		List<String> titles = localizedItemSelectorRendering.getTitles();

		if (titles.isEmpty()) {
			String[] criteria = ParamUtil.getParameterValues(
				renderRequest, "criteria");

			String criteriaNames = StringUtil.merge(criteria, StringPool.COMMA);

			if (_log.isWarnEnabled()) {
				_log.warn(
					"No item selector views were founded for " + criteriaNames);
			}

			SessionErrors.add(
				renderRequest, NoSuchItemSelectorViewException.class);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ItemSelectorPortlet.class);

	private ItemSelector _itemSelector;

}