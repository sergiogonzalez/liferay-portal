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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

		_searchResults = new HashMap<SearchResultKey, SearchResult>();

		_searchResultSummaryFactory = new SearchResultSummaryFactoryImpl(
			assetRendererFactoryByClassName, indexerByClassName);
	}

	public List<SearchResult> translate(Document[] documents) {
		for (Document document : documents) {
			try {
				add(document);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					long entryClassPK = GetterUtil.getLong(
						document.get(Field.ENTRY_CLASS_PK));
					_log.warn(
						"Search index is stale and contains entry {" +
							entryClassPK + "}");
				}
			}
		}

		return getSearchResultList();
	}

	protected void add(Document document)
		throws PortalException, SystemException {

		String entryClassName = GetterUtil.getString(
			document.get(Field.ENTRY_CLASS_NAME));
		long entryClassPK = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		SearchResultContributor contributor = null;
		SearchResultKey key = null;

		Indexer indexer = _indexerByClassName.apply(entryClassName);

		if (indexer != null) {
			boolean useContributor = true;

			boolean isKeyInDocumentRequiredToUseSearchResultContributor =
				entryClassName.equals(DLFileEntry.class.getName()) ||
					entryClassName.equals(MBMessage.class.getName());

			if (isKeyInDocumentRequiredToUseSearchResultContributor) {
				SearchResultKey keyInDocument = getSearchResultKey(document);

				if (keyInDocument != null) {
					key = keyInDocument;
				}
				else {
					useContributor = false;
				}
			}

			if (useContributor) {
				contributor = indexer.getSearchResultContributor(
					entryClassPK, _locale, _portletURL,
					_searchResultSummaryFactory);
			}
		}

		if (key == null) {
			key = new SearchResultKey(entryClassName, entryClassPK);
		}

		SearchResult searchResult = _searchResults.get(key);

		if (searchResult == null) {
			searchResult = new SearchResult(key);
			_searchResults.put(key, searchResult);
		}

		if (contributor != null) {
			contributor.contributeTo(
				searchResult, document, _portletRequest, _portletResponse);
		}

		boolean isSummaryOfDocumentPreferred = entryClassName.equals(
			JournalArticle.class.getName());

		if ((contributor == null) || isSummaryOfDocumentPreferred) {
			Summary summary = _searchResultSummaryFactory.getSummary(
				document, searchResult.getClassName(),
				searchResult.getClassPK(), _locale, _portletURL,
				_portletRequest, _portletResponse);

			searchResult.setSummary(summary);
		}
		else {
			if (searchResult.getSummary() == null) {
				Summary summary = _searchResultSummaryFactory.getSummary(
					searchResult.getClassName(), searchResult.getClassPK(),
					_locale, _portletURL);

				searchResult.setSummary(summary);
			}
		}
	}

	protected SearchResultKey getSearchResultKey(Document document) {
		long classPK = GetterUtil.getLong(document.get(Field.CLASS_PK));
		long classNameId = GetterUtil.getLong(
			document.get(Field.CLASS_NAME_ID));

		if ((classPK > 0) && (classNameId > 0)) {
			return new SearchResultKey(
				PortalUtil.getClassName(classNameId), classPK);
		}

		return null;
	}

	protected List<SearchResult> getSearchResultList() {
		return new ArrayList<SearchResult>(_searchResults.values());
	}

	private static Log _log = LogFactoryUtil.getLog(
		SearchDocumentsToResultsTranslator.class);

	private Function<String, Indexer> _indexerByClassName;
	private Locale _locale;
	private PortletRequest _portletRequest;
	private PortletResponse _portletResponse;
	private PortletURL _portletURL;
	private Map<SearchResultKey, SearchResult> _searchResults;
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