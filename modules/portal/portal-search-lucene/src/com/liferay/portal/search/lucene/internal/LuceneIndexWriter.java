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

package com.liferay.portal.search.lucene.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexWriter;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.suggest.SpellCheckIndexWriter;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import org.apache.lucene.index.Term;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Brian Wing Shun Chan
 * @author Allen Chiang
 * @author Alex Wallace
 */
@Component(immediate = true, service = LuceneIndexWriter.class)
public class LuceneIndexWriter extends BaseIndexWriter {

	@Override
	public void addDocument(SearchContext searchContext, Document document)
		throws SearchException {

		try {
			_luceneHelper.addDocument(
				searchContext.getCompanyId(), _getLuceneDocument(document));

			if (_log.isDebugEnabled()) {
				_log.debug("Added document " + document.get(Field.UID));
			}
		}
		catch (IOException ioe) {
			throw new SearchException(ioe);
		}
	}

	@Override
	public void addDocuments(
			SearchContext searchContext, Collection<Document> documents)
		throws SearchException {

		for (Document document : documents) {
			addDocument(searchContext, document);
		}
	}

	@Override
	public void deleteDocument(SearchContext searchContext, String uid)
		throws SearchException {

		try {
			_luceneHelper.deleteDocuments(
				searchContext.getCompanyId(), new Term(Field.UID, uid));

			if (_log.isDebugEnabled()) {
				_log.debug("Deleted document " + uid);
			}
		}
		catch (IOException ioe) {
			throw new SearchException(ioe);
		}
	}

	@Override
	public void deleteDocuments(
			SearchContext searchContext, Collection<String> uids)
		throws SearchException {

		for (String uid : uids) {
			deleteDocument(searchContext, uid);
		}
	}

	@Override
	public void deleteEntityDocuments(
			SearchContext searchContext, String className)
		throws SearchException {

		try {
			_luceneHelper.deleteDocuments(
				searchContext.getCompanyId(),
				new Term(Field.ENTRY_CLASS_NAME, className));
		}
		catch (IOException ioe) {
			throw new SearchException(ioe);
		}
	}

	@Override
	@Reference(service = LuceneSpellCheckIndexWriter.class)
	public void setSpellCheckIndexWriter(
		SpellCheckIndexWriter spellCheckIndexWriter) {

		super.setSpellCheckIndexWriter(spellCheckIndexWriter);
	}

	@Override
	public void updateDocument(SearchContext searchContext, Document document)
		throws SearchException {

		try {
			_luceneHelper.updateDocument(
				searchContext.getCompanyId(),
				new Term(Field.UID, document.getUID()),
				_getLuceneDocument(document));

			if (_log.isDebugEnabled()) {
				_log.debug("Updated document " + document.get(Field.UID));
			}
		}
		catch (IOException ioe) {
			throw new SearchException(ioe);
		}
	}

	@Override
	public void updateDocuments(
			SearchContext searchContext, Collection<Document> documents)
		throws SearchException {

		for (Document document : documents) {
			updateDocument(searchContext, document);
		}
	}

	@Reference
	protected void setLuceneHelper(LuceneHelper luceneHelper) {
		_luceneHelper = luceneHelper;
	}

	private void _addLuceneFieldable(
		org.apache.lucene.document.Document luceneDocument, String name,
		boolean numeric, Class<? extends Number> numericClass,
		boolean tokenized, boolean sortable, float boost, String value) {

		org.apache.lucene.document.Fieldable luceneFieldable = null;

		if (numeric) {
			luceneFieldable = LuceneFields.getNumber(name, value, numericClass);
		}
		else {
			if (tokenized) {
				luceneFieldable = LuceneFields.getText(name, value);
			}
			else {
				luceneFieldable = LuceneFields.getKeyword(name, value);
			}
		}

		if (sortable) {
			String sortableFieldName = DocumentImpl.getSortableFieldName(name);

			luceneFieldable = LuceneFields.getKeyword(sortableFieldName, value);
		}

		luceneFieldable.setBoost(boost);

		luceneDocument.add(luceneFieldable);
	}

	private org.apache.lucene.document.Document _getLuceneDocument(
		Document document) {

		org.apache.lucene.document.Document luceneDocument =
			new org.apache.lucene.document.Document();

		Collection<Field> fields = document.getFields().values();

		for (Field field : fields) {
			String name = field.getName();
			boolean numeric = field.isNumeric();
			Class<? extends Number> numericClass = field.getNumericClass();
			boolean tokenized = field.isTokenized();
			float boost = field.getBoost();

			if (!field.isLocalized()) {
				for (String value : field.getValues()) {
					if (Validator.isNull(value)) {
						continue;
					}

					_addLuceneFieldable(
						luceneDocument, name, numeric, numericClass, tokenized,
						field.isSortable(), boost, value);
				}
			}
			else {
				Map<Locale, String> localizedValues =
					field.getLocalizedValues();

				for (Map.Entry<Locale, String> entry :
						localizedValues.entrySet()) {

					String value = entry.getValue();

					if (Validator.isNull(value)) {
						continue;
					}

					Locale locale = entry.getKey();

					String languageId = LocaleUtil.toLanguageId(locale);

					String defaultLanguageId = LocaleUtil.toLanguageId(
						LocaleUtil.getDefault());

					if (languageId.equals(defaultLanguageId)) {
						_addLuceneFieldable(
							luceneDocument, name, numeric, numericClass,
							tokenized, field.isSortable(), boost, value);
					}

					String localizedName = DocumentImpl.getLocalizedName(
						locale, name);

					_addLuceneFieldable(
						luceneDocument, localizedName, numeric, numericClass,
						tokenized, field.isSortable(), boost, value);
				}
			}
		}

		return luceneDocument;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LuceneIndexWriter.class);

	private LuceneHelper _luceneHelper;

}