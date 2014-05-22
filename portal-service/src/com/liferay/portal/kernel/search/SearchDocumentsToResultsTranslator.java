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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.messageboards.model.MBMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Eudaldo Alonso
 * @author André de Oliveira
 */
public class SearchDocumentsToResultsTranslator {

	public SearchDocumentsToResultsTranslator(
		Locale locale, PortletURL portletURL, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		this(
			locale, portletURL, portletRequest, portletResponse,
			new AssetRendererFactoryByClassName(), new IndexerByClassName());
	}

	public SearchDocumentsToResultsTranslator(
		Locale locale, PortletURL portletURL, PortletRequest portletRequest,
		PortletResponse portletResponse,
		Function<String, AssetRendererFactory> assetRendererFactoryByClassName,
		Function<String, Indexer> indexerByClassName) {

		_indexerByClassName = indexerByClassName;
		_locale = locale;
		_portletURL = portletURL;
		_portletRequest = portletRequest;
		_portletResponse = portletResponse;

		_searchResults = new ArrayList<SearchResult>();
		_searchResultSummaryFactory = new SearchResultSummaryFactoryImpl(
			assetRendererFactoryByClassName, indexerByClassName);
	}

	public List<SearchResult> translate(Document[] documents) {
		for (Document document : documents) {
			String entryClassName = GetterUtil.getString(
				document.get(Field.ENTRY_CLASS_NAME));
			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			try {
				String className = entryClassName;
				long classPK = entryClassPK;

				SearchResultContributor contributor = null;

				if (entryClassName.equals(DLFileEntry.class.getName()) ||
					entryClassName.equals(MBMessage.class.getName())) {

					classPK = GetterUtil.getLong(document.get(Field.CLASS_PK));
					long classNameId = GetterUtil.getLong(
						document.get(Field.CLASS_NAME_ID));

					if ((classPK > 0) && (classNameId > 0)) {
						className = PortalUtil.getClassName(classNameId);

						Indexer indexer = _indexerByClassName.apply(
							entryClassName);

						contributor = indexer.getSearchResultContributor(
							entryClassPK, _locale, _portletURL,
							_searchResultSummaryFactory);
					}
					else {
						className = entryClassName;
						classPK = entryClassPK;
					}
				}

				SearchResult searchResult = new SearchResult(
					className, classPK);

				int index = _searchResults.indexOf(searchResult);

				if (index < 0) {
					_searchResults.add(searchResult);
				}
				else {
					searchResult = _searchResults.get(index);
				}

				if (contributor != null) {
					contributor.contributeTo(
						searchResult, document, _portletRequest,
						_portletResponse);
				}

				if (entryClassName.equals(JournalArticle.class.getName())) {
					String version = document.get(Field.VERSION);

					searchResult.addVersion(version);
				}

				if (contributor == null) {
					Summary summary = _searchResultSummaryFactory.getSummary(
						document, className, classPK, _locale, _portletURL,
						_portletRequest, _portletResponse);

					searchResult.setSummary(summary);
				}
				else {
					if (searchResult.getSummary() == null) {
						Summary summary =
							_searchResultSummaryFactory.getSummary(
								className, classPK, _locale, _portletURL);

						searchResult.setSummary(summary);
					}
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Search index is stale and contains entry {" +
							entryClassPK + "}");
				}
			}
		}

		return _searchResults;
	}

	private static Log _log = LogFactoryUtil.getLog(
		SearchDocumentsToResultsTranslator.class);

	private Function<String, Indexer> _indexerByClassName;
	private Locale _locale;
	private PortletRequest _portletRequest;
	private PortletResponse _portletResponse;
	private PortletURL _portletURL;
	private List<SearchResult> _searchResults;
	private SearchResultSummaryFactory _searchResultSummaryFactory;

	private static class AssetRendererFactoryByClassName
		implements Function<String, AssetRendererFactory> {

		@Override
		public AssetRendererFactory apply(String className) {
			return AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassName(className);
		}

	}

	private static class IndexerByClassName
		implements Function<String, Indexer> {

		@Override
		public Indexer apply(String className) {
			return IndexerRegistryUtil.getIndexer(className);
		}

	}

}