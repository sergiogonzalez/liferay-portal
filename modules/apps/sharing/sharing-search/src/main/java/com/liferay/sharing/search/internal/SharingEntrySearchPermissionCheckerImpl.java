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

package com.liferay.sharing.search.internal;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.SearchPermissionCheckerConfiguration",
	immediate = true, property = "service.ranking:Integer=100",
	service = SearchPermissionChecker.class
)
public class SharingEntrySearchPermissionCheckerImpl
	implements SearchPermissionChecker {

	@Override
	public void addPermissionFields(long companyId, Document doc) {
		_searchPermissionChecker.addPermissionFields(companyId, doc);

		String className = doc.get(Field.ENTRY_CLASS_NAME);
		String classPK = doc.get(Field.ENTRY_CLASS_PK);

		if (Validator.isNull(className) && Validator.isNull(classPK)) {
			className = doc.get(Field.ROOT_ENTRY_CLASS_NAME);
			classPK = doc.get(Field.ROOT_ENTRY_CLASS_PK);
		}

		boolean relatedEntry = GetterUtil.getBoolean(
			doc.get(Field.RELATED_ENTRY));

		if (relatedEntry) {
			long classNameId = GetterUtil.getLong(doc.get(Field.CLASS_NAME_ID));

			className = _portal.getClassName(classNameId);

			classPK = doc.get(Field.CLASS_PK);
		}

		Indexer<?> indexer = _indexerRegistry.nullSafeGetIndexer(className);

		if (!indexer.isPermissionAware()) {
			return;
		}

		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getSharingEntries(
				_portal.getClassNameId(className), GetterUtil.getLong(classPK));

		List<Long> sharedToUserIds = new ArrayList<>();

		Stream<SharingEntry> sharingEntriesStream = sharingEntries.stream();

		sharingEntriesStream.map(
			SharingEntry::getToUserId
		).forEach(
			sharedToUserIds::add
		);

		doc.addKeyword(
			"sharedToUserId",
			sharedToUserIds.toArray(new Long[sharedToUserIds.size()]));
	}

	@Override
	public BooleanFilter getPermissionBooleanFilter(
		long companyId, long[] groupIds, long userId, String className,
		BooleanFilter booleanFilter, SearchContext searchContext) {

		return _searchPermissionChecker.getPermissionBooleanFilter(
			companyId, groupIds, userId, className, booleanFilter,
			searchContext);
	}

	@Override
	public void updatePermissionFields(String name, String primKey) {
		_searchPermissionChecker.updatePermissionFields(name, primKey);
	}

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(!(component.name=com.liferay.sharing.search.internal.SharingEntrySearchPermissionCheckerImpl))"
	)
	private SearchPermissionChecker _searchPermissionChecker;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}