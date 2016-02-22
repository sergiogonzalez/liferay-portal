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

package com.liferay.wiki.search;

import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.wiki.model.WikiPage;

import java.util.Collections;
import java.util.Map;

/**
 * @author Roberto Díaz
 */
public class WikiPageTitleSearcher extends BaseSearcher {

	public static final String[] CLASS_NAMES = {WikiPage.class.getName()};

	public static Indexer<?> getInstance() {
		return new WikiPageTitleSearcher();
	}

	public WikiPageTitleSearcher() {
		setDefaultSelectedFieldNames(Field.TITLE);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String[] getSearchClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		MatchQuery matchQuery = new MatchQuery(
			Field.TITLE, StringUtil.toLowerCase(searchContext.getKeywords()));

		matchQuery.setType(MatchQuery.Type.PHRASE_PREFIX);

		searchQuery.add(matchQuery, BooleanClauseOccur.MUST);
	}

	protected Map<String, Query> addSearchKeywords(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		return Collections.emptyMap();
	}

}