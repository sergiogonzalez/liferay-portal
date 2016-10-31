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

package com.liferay.knowledge.base.internal.util;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.persistence.KBArticlePersistence;
import com.liferay.knowledge.base.util.comparator.KBArticlePriorityComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class KBArticleSiblingNavigationHelper {

	public KBArticleSiblingNavigationHelper(
		KBArticlePersistence kbArticlePersistence) {

		_kbArticlePersistence = kbArticlePersistence;
	}

	public KBArticle[] getPreviousAndNextKBArticles(long kbArticleId)
		throws PortalException {

		KBArticle kbArticle = _kbArticlePersistence.findByPrimaryKey(
			kbArticleId);

		KBArticle[] previousAndNextKBArticles = getPreviousAndNextKBArticles(
			kbArticle);

		KBArticle previousKBArticle = getPreviousKBArticle(
			kbArticle, previousAndNextKBArticles[0]);
		KBArticle nextKBArticle = getNextKBArticle(
			kbArticle, previousAndNextKBArticles[2]);

		return new KBArticle[] {previousKBArticle, kbArticle, nextKBArticle};
	}

	protected KBArticle fetchFirstChildKBArticle(KBArticle kbArticle) {
		return _kbArticlePersistence.fetchByG_P_M_First(
			kbArticle.getGroupId(), kbArticle.getResourcePrimKey(), true,
			new KBArticlePriorityComparator(true));
	}

	protected KBArticle fetchLastChildKBArticle(KBArticle previousKBArticle) {
		return _kbArticlePersistence.fetchByG_P_M_Last(
			previousKBArticle.getGroupId(),
			previousKBArticle.getResourcePrimKey(), true,
			new KBArticlePriorityComparator(true));
	}

	protected List<KBArticle> findChildKBArticles(KBArticle kbArticle) {
		return _kbArticlePersistence.findByG_P_M(
			kbArticle.getGroupId(), kbArticle.getParentResourcePrimKey(), true,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new KBArticlePriorityComparator(true));
	}

	protected KBArticle getNextAncestorKBArticle(
			long kbArticleId, KBArticle nextKBArticle)
		throws PortalException {

		if (nextKBArticle != null) {
			return nextKBArticle;
		}

		KBArticle kbArticle = _kbArticlePersistence.findByPrimaryKey(
			kbArticleId);

		KBArticle parentKBArticle = kbArticle.getParentKBArticle();

		if (parentKBArticle == null) {
			return null;
		}

		KBArticle[] previousAndNextKBArticles = getPreviousAndNextKBArticles(
			parentKBArticle);

		return getNextAncestorKBArticle(
			parentKBArticle.getKbArticleId(), previousAndNextKBArticles[2]);
	}

	protected KBArticle getNextKBArticle(
			KBArticle kbArticle, KBArticle nextKBArticle)
		throws PortalException {

		KBArticle firstChildKBArticle = fetchFirstChildKBArticle(kbArticle);

		if (firstChildKBArticle != null) {
			return firstChildKBArticle;
		}

		return getNextAncestorKBArticle(
			kbArticle.getKbArticleId(), nextKBArticle);
	}

	protected KBArticle[] getPreviousAndNextKBArticles(KBArticle kbArticle) {
		List<KBArticle> kbArticles = findChildKBArticles(kbArticle);

		int index = kbArticles.indexOf(kbArticle);

		KBArticle[] previousAndNextKBArticles = {null, kbArticle, null};

		if (index > 0) {
			previousAndNextKBArticles[0] = kbArticles.get(index - 1);
		}

		if (index < (kbArticles.size() - 1)) {
			previousAndNextKBArticles[2] = kbArticles.get(index + 1);
		}

		return previousAndNextKBArticles;
	}

	protected KBArticle getPreviousKBArticle(
			KBArticle kbArticle, KBArticle previousKBArticle)
		throws PortalException {

		if (previousKBArticle == null) {
			return kbArticle.getParentKBArticle();
		}

		KBArticle lastSiblingChildKBArticle = fetchLastChildKBArticle(
			previousKBArticle);

		if (lastSiblingChildKBArticle == null) {
			return previousKBArticle;
		}

		return lastSiblingChildKBArticle;
	}

	private final KBArticlePersistence _kbArticlePersistence;

}