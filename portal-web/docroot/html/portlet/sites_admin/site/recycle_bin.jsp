<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
Group liveGroup = (Group)request.getAttribute("site.liveGroup");

UnicodeProperties groupTypeSettings = null;

if (liveGroup != null) {
	groupTypeSettings = liveGroup.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}

boolean trashEnabled = true;

int trashEnabledInt = PrefsPropsUtil.getInteger(company.getCompanyId(), PropsKeys.TRASH_ENABLED);

if ((trashEnabledInt == TrashUtil.TRASH_DISABLED) || (trashEnabledInt == TrashUtil.TRASH_DISABLED_BY_DEFAULT)) {
	trashEnabled = false;
}

trashEnabled = PropertiesParamUtil.getBoolean(groupTypeSettings, request, "trashEnabled", trashEnabled);

int trashEntriesMaxAge = PropertiesParamUtil.getInteger(groupTypeSettings, request, "trashEntriesMaxAge", PrefsPropsUtil.getInteger(company.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE));
%>

<aui:fieldset>
	<aui:input label="enable-recycle-bin" name="trashEnabled" type="checkbox" value="<%= trashEnabled %>" />

	<aui:input label="number-of-days-that-files-will-be-kept-in-the-recycle-bin" name="trashEntriesMaxAge" type="text" value="<%= trashEntriesMaxAge %>">
		<aui:validator name="number" />
	</aui:input>
</aui:fieldset>