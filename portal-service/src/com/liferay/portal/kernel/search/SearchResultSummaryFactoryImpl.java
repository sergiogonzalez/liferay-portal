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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Eudaldo Alonso
 * @author André de Oliveira
 */
public class SearchResultSummaryFactoryImpl
	implements SearchResultSummaryFactory {

	public SearchResultSummaryFactoryImpl(
		Function<String, AssetRendererFactory> assetRendererFactoryByClassName,
		Function<String, Indexer> indexerByClassName) {

		_assetRendererFactoryByClassName = assetRendererFactoryByClassName;
		_indexerByClassName = indexerByClassName;
	}

	@Override
	public Summary getSummary(
		Document document, String className, long classPK, Locale locale,
		PortletURL portletURL, PortletRequest portletRequest,
		PortletResponse portletResponse)
	throws PortalException, SystemException {

		Indexer indexer = _indexerByClassName.apply(className);

		if (indexer != null) {
			String snippet = document.get(Field.SNIPPET);

			return indexer.getSummary(
				document, snippet, portletURL, portletRequest, portletResponse);
		}

		return getSummary(className, classPK, locale, portletURL);
	}

	@Override
	public Summary getSummary(
			String className, long classPK, Locale locale,
			PortletURL portletURL)
		throws PortalException, SystemException {

		AssetRendererFactory assetRendererFactory =
			_assetRendererFactoryByClassName.apply(className);

		if (assetRendererFactory == null) {
			return null;
		}

		AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
			classPK);

		if (assetRenderer == null) {
			return null;
		}

		Summary summary = new Summary(
			assetRenderer.getTitle(locale),
			assetRenderer.getSearchSummary(locale), portletURL);

		summary.setMaxContentLength(200);
		summary.setPortletURL(portletURL);

		return summary;
	}

	private Function<String, AssetRendererFactory>
		_assetRendererFactoryByClassName;
	private Function<String, Indexer> _indexerByClassName;

}