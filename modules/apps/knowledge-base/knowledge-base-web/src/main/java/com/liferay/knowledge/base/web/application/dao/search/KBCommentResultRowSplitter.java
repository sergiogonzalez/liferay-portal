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

package com.liferay.knowledge.base.web.application.dao.search;

import com.liferay.knowledge.base.constants.KBCommentConstants;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergio González
 */
public class KBCommentResultRowSplitter implements ResultRowSplitter {

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		List<ResultRow> completedResultRows = new ArrayList<>();
		List<ResultRow> inProgressResultRows = new ArrayList<>();
		List<ResultRow> newResultRows = new ArrayList<>();

		for (ResultRow resultRow : resultRows) {
			KBComment kbComment = (KBComment)resultRow.getObject();

			if (kbComment.getStatus() == KBCommentConstants.STATUS_NEW) {
				newResultRows.add(resultRow);
			}
			else if (kbComment.getStatus() ==
						KBCommentConstants.STATUS_IN_PROGRESS) {

				inProgressResultRows.add(resultRow);
			}
			else {
				completedResultRows.add(resultRow);
			}
		}

		if (!newResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry("new", newResultRows));
		}

		if (!inProgressResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"in-progress", inProgressResultRows));
		}

		if (!completedResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry("completed", completedResultRows));
		}

		return resultRowSplitterEntries;
	}

}