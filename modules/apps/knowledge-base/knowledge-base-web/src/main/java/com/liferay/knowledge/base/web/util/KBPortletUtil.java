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

package com.liferay.knowledge.base.web.util;

import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.util.comparator.KBCommentModifiedDateComparator;
import com.liferay.knowledge.base.util.comparator.KBCommentStatusComparator;
import com.liferay.knowledge.base.util.comparator.KBCommentUserComparator;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Roberto Díaz
 */
public class KBPortletUtil {

	public static OrderByComparator<KBComment> getKBCommentOrderByComparator(
		String orderByCol, String orderByType) {

		if (Validator.isNull(orderByType)) {
			return new KBCommentStatusComparator();
		}

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<KBComment> orderByComparator = null;

		if (orderByCol.equals("modifiedDate")) {
			orderByComparator = new KBCommentModifiedDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("user")) {
			orderByComparator = new KBCommentUserComparator(orderByAsc);
		}

		return orderByComparator;
	}

}