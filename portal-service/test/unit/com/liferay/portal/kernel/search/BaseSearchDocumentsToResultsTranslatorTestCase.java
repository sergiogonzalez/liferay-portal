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

import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;

import java.util.List;

import javax.portlet.PortletURL;

import org.apache.commons.lang.math.RandomUtils;

import org.junit.Assert;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author André de Oliveira
 */
public abstract class BaseSearchDocumentsToResultsTranslatorTestCase
	extends PowerMockito {

	protected void doSetUp() {
		MockitoAnnotations.initMocks(this);

		setUpFastDateFormatFactory();
		setUpPortal();
		setUpProps();
	}

	protected Document newDocument(String entryClassName) {
		return newDocument(entryClassName, ENTRY_CLASS_PK);
	}

	protected Document newDocument(String entryClassName, long entryClassPk) {
		Document document = new DocumentImpl();

		document.add(
			new Field(Field.ENTRY_CLASS_PK, String.valueOf(entryClassPk)));
		document.add(new Field(Field.ENTRY_CLASS_NAME, entryClassName));

		return document;
	}

	protected Document newDocumentWithAlternateKey(String entryClassName) {
		Document document = newDocument(entryClassName);

		setKeyInDocument(document);

		return document;
	}

	protected void setKeyInDocument(Document document) {
		document.add(
			new Field(
				Field.CLASS_NAME_ID, String.valueOf(DOCUMENT_CLASS_NAME_ID)));
		document.add(
			new Field(Field.CLASS_PK, String.valueOf(DOCUMENT_CLASS_PK)));
	}

	protected void setUpFastDateFormatFactory() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			mock(FastDateFormatFactory.class));
	}

	protected void setUpPortal() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portal);

		when(
			portal.getClassName(DOCUMENT_CLASS_NAME_ID)
		).thenReturn(
			DOCUMENT_CLASS_NAME
		);
	}

	protected void setUpProps() {
		PropsUtil.setProps(props);
	}

	protected List<SearchResult> translate(Document... documents) {
		SearchDocumentsToResultsTranslator translator =
			new SearchDocumentsToResultsTranslator(
				null, portletURL, null, null, assetRendererFactoryByClassName,
				indexerByClassName);

		return translator.translate(documents);
	}

	protected void translateSingleDocument(Document document) {
		List<SearchResult> searchResults = translate(document);

		Assert.assertEquals("one hit, one result", 1, searchResults.size());

		result = searchResults.get(0);
	}

	protected static final String DOCUMENT_CLASS_NAME =
		"com.liferay.ClassInDocument";

	protected static final long DOCUMENT_CLASS_NAME_ID = RandomUtils.nextLong();

	protected static final long DOCUMENT_CLASS_PK = RandomUtils.nextLong();

	protected static final long ENTRY_CLASS_PK = RandomUtils.nextLong();

	protected static final String SUMMARY_CONTENT =
		"A long time ago, in a galaxy far, far away...";

	protected static final String SUMMARY_TITLE = "S.R. Wars";

	@Mock
	protected AssetRenderer assetRenderer;

	@Mock
	protected AssetRendererFactory assetRendererFactory;

	@Mock
	protected Function<String, AssetRendererFactory>
		assetRendererFactoryByClassName;

	@Mock
	protected Function<String, Indexer> indexerByClassName;

	@Mock
	protected Portal portal;

	@Mock
	protected PortletURL portletURL;

	@Mock
	protected Props props;

	protected SearchResult result;

}