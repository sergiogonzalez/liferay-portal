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

package com.liferay.sharing.search.internal.permission;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.permission.SearchPermissionDocumentContributor;
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
	immediate = true, property = "service.ranking:Integer=101",
	service = SearchPermissionDocumentContributor.class
)
public class SharingEntrySearchPermissionDocumentContributorImpl
	implements SearchPermissionDocumentContributor {

	@Override
	public void addPermissionFields(long companyId, Document document) {
		long groupId = GetterUtil.getLong(document.get(Field.GROUP_ID));

		String className = document.get(Field.ENTRY_CLASS_NAME);
		String classPK = document.get(Field.ENTRY_CLASS_PK);

		if (Validator.isNull(className) && Validator.isNull(classPK)) {
			className = document.get(Field.ROOT_ENTRY_CLASS_NAME);
			classPK = document.get(Field.ROOT_ENTRY_CLASS_PK);
		}

		boolean relatedEntry = GetterUtil.getBoolean(
			document.get(Field.RELATED_ENTRY));

		if (relatedEntry) {
			long classNameId = GetterUtil.getLong(
				document.get(Field.CLASS_NAME_ID));

			if (classNameId > 0) {
				className = _portal.getClassName(classNameId);
				classPK = document.get(Field.CLASS_PK);
			}
		}

		addPermissionFields(
			companyId, groupId, className, GetterUtil.getLong(classPK),
			document);
	}

	@Override
	public void addPermissionFields(
		long companyId, long groupId, String className, long classPK,
		Document document) {

		_searchPermissionDocumentContributor.addPermissionFields(
			companyId, groupId, className, classPK, document);

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

		document.addKeyword(
			"sharedToUserId",
			sharedToUserIds.toArray(new Long[sharedToUserIds.size()]));
	}

	@Reference
	private Portal _portal;

	@Reference(
		target = "(!(component.name=com.liferay.sharing.search.internal.permission.SharingEntrySearchPermissionDocumentContributorImpl))"
	)
	private SearchPermissionDocumentContributor
		_searchPermissionDocumentContributor;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}