<%--
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
--%>

<%@ include file="/com.liferay.document.library.analytics/init.jsp" %>

<aui:script require="metal-dom/src/all/dom as dom">
	if (window.Analytics) {
		dom.delegate(
			document.body,
			'click',
			'a[data-download="true"]',
			function(event) {
				var anchor = event.delegateTarget;

				Analytics.send(
					'DOWNLOAD',
					'DocumentLibrary',
					{
						fileEntryId: anchor.dataset['fileEntryId'],
						fileVersionId: anchor.dataset['fileVersionId']
					}
				);
			}
		);
	}
</aui:script>