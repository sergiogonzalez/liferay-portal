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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.message.boards.kernel.constants.MBConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Adolfo Pérez
 */
public class UpgradeMessageBoardsRepository extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateRepositoryPortletId();
	}

	protected void updateRepositoryPortletId() throws Exception {
		runSQL(
			"update Repository set portletId = '" +
				MBConstants.SERVICE_NAME + "' where portletId = " +
					_OLD_MESSAGE_BOARDS_PORTLET_ID);
	}

	private static final String _OLD_MESSAGE_BOARDS_PORTLET_ID = "19";

}