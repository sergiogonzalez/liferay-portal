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

<%@ include file="/init.jsp" %>

<%
String cmd = ParamUtil.getString(request, Constants.CMD, Constants.PUBLISH_TO_LIVE);

long exportImportConfigurationId = 0;

ExportImportConfiguration exportImportConfiguration = null;

Map<String, Serializable> exportImportConfigurationSettingsMap = Collections.emptyMap();

Map<String, String[]> parameterMap = Collections.emptyMap();

if (SessionMessages.contains(liferayPortletRequest, portletDisplay.getId() + "exportImportConfigurationId")) {
	exportImportConfigurationId = (Long)SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "exportImportConfigurationId");

	if (exportImportConfigurationId > 0) {
		exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);
	}

	exportImportConfigurationSettingsMap = (Map<String, Serializable>)SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "settingsMap");

	parameterMap = (Map<String, String[]>)exportImportConfigurationSettingsMap.get("parameterMap");
}
else {
	exportImportConfigurationId = ParamUtil.getLong(request, "exportImportConfigurationId");

	if (exportImportConfigurationId > 0) {
		exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);

		exportImportConfigurationSettingsMap = exportImportConfiguration.getSettingsMap();

		parameterMap = (Map<String, String[]>)exportImportConfigurationSettingsMap.get("parameterMap");
	}
}

boolean configuredPublish = (exportImportConfiguration == null) ? false : true;

long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");
String layoutSetBranchName = ParamUtil.getString(request, "layoutSetBranchName");

boolean localPublishing = true;

if ((liveGroup.isStaged() && liveGroup.isStagedRemotely()) || cmd.equals(Constants.PUBLISH_TO_REMOTE)) {
	localPublishing = false;
}

String treeId = "liveLayoutsTree";

if (liveGroup.isStaged()) {
	if (!liveGroup.isStagedRemotely()) {
		treeId = "stageLayoutsTree";
	}
	else {
		treeId = "remoteLayoutsTree";
	}
}

treeId = treeId + liveGroupId;

String publishMessageKey = "publish-to-live";

if (cmd.equals(Constants.PUBLISH_TO_REMOTE)) {
	publishMessageKey = "publish-to-remote-live";
}

long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

Layout selLayout = null;

try {
	selLayout = LayoutLocalServiceUtil.getLayout(selPlid);
}
catch (NoSuchLayoutException nsle) {
}

treeId = treeId + privateLayout + layoutSetBranchId;

long[] selectedLayoutIds = null;

String openNodes = SessionTreeJSClicks.getOpenNodes(request, treeId + "SelectedNode");

if (openNodes == null) {
	selectedLayoutIds = ExportImportHelperUtil.getAllLayoutIds(stagingGroupId, privateLayout);

	for (long selectedLayoutId : selectedLayoutIds) {
		SessionTreeJSClicks.openLayoutNodes(request, treeId + "SelectedNode", privateLayout, selectedLayoutId, true);
	}
}
else {
	selectedLayoutIds = GetterUtil.getLongValues(StringUtil.split(openNodes, ','));
}

UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();

PortletURL portletURL = renderResponse.createActionURL();

if (group.isStaged() && group.isStagedRemotely()) {
	cmd = Constants.PUBLISH_TO_REMOTE;
}

portletURL.setParameter(ActionRequest.ACTION_NAME, "publishLayouts");
portletURL.setParameter("mvcRenderCommandName", "publishLayouts");
portletURL.setParameter("groupId", String.valueOf(stagingGroupId));
portletURL.setParameter("stagingGroupId", String.valueOf(stagingGroupId));
portletURL.setParameter("privateLayout", String.valueOf(privateLayout));

PortletURL renderURL = renderResponse.createRenderURL();

renderURL.setParameter("mvcRenderCommandName", "publishLayouts");
renderURL.setParameter("groupId", String.valueOf(stagingGroupId));
renderURL.setParameter("layoutSetBranchId", String.valueOf(layoutSetBranchId));
renderURL.setParameter("layoutSetBranchName", layoutSetBranchName);
renderURL.setParameter("privateLayout", String.valueOf(privateLayout));

response.setHeader("Ajax-ID", request.getHeader("Ajax-ID"));

renderResponse.setTitle(!configuredPublish ? LanguageUtil.get(request, "new-publication") : LanguageUtil.format(request, "new-publication-based-on-x", exportImportConfiguration.getName(), false));
%>

<c:if test='<%= SessionMessages.contains(renderRequest, "requestProcessed") %>'>

	<%
	String successMessage = (String)SessionMessages.get(renderRequest, "requestProcessed");
	%>

	<c:if test='<%= Validator.isNotNull(successMessage) && !successMessage.equals("request_processed") %>'>
		<div class="alert alert-success">
			<%= HtmlUtil.escape(successMessage) %>
		</div>
	</c:if>
</c:if>

<portlet:renderURL var="basePortletURL">
	<portlet:param name="mvcRenderCommandName" value="processesList" />
</portlet:renderURL>

<portlet:actionURL name="editPublishConfiguration" var="updatePublishConfigurationURL">
	<portlet:param name="mvcRenderCommandName" value="editPublishConfiguration" />
	<portlet:param name="groupId" value="<%= String.valueOf(stagingGroupId) %>" />
	<portlet:param name="localPublishing" value="<%= String.valueOf(localPublishing) %>" />
</portlet:actionURL>

<aui:form action='<%= portletURL.toString() + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="exportPagesFm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "publishPages();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />
	<aui:input name="currentURL" type="hidden" value="<%= currentURL %>" />
	<aui:input name="redirect" type="hidden" value="<%= basePortletURL %>" />
	<aui:input name="groupId" type="hidden" value="<%= stagingGroupId %>" />
	<aui:input name="layoutSetBranchName" type="hidden" value="<%= layoutSetBranchName %>" />
	<aui:input name="lastImportUserName" type="hidden" value="<%= user.getFullName() %>" />
	<aui:input name="lastImportUserUuid" type="hidden" value="<%= String.valueOf(user.getUserUuid()) %>" />
	<aui:input name="treeId" type="hidden" value="<%= treeId %>" />
	<aui:input name="<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL %>" type="hidden" value="<%= true %>" />
	<aui:input name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL %>" type="hidden" value="<%= true %>" />
	<aui:input name="<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>" type="hidden" value="<%= true %>" />
	<aui:input name="<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>" type="hidden" value="<%= true %>" />

	<liferay-ui:error exception="<%= DuplicateLockException.class %>" message="another-publishing-process-is-in-progress,-please-try-again-later" />

	<liferay-ui:error exception="<%= LayoutPrototypeException.class %>">

		<%
		LayoutPrototypeException lpe = (LayoutPrototypeException)errorException;
		%>

		<liferay-ui:message key="the-pages-could-not-be-published-because-one-or-more-required-page-templates-could-not-be-found-on-the-remote-system.-please-import-the-following-templates-manually" />

		<ul>

			<%
			List<Tuple> missingLayoutPrototypes = lpe.getMissingLayoutPrototypes();

			for (Tuple missingLayoutPrototype : missingLayoutPrototypes) {
				String layoutPrototypeClassName = (String)missingLayoutPrototype.getObject(0);
				String layoutPrototypeUuid = (String)missingLayoutPrototype.getObject(1);
				String layoutPrototypeName = (String)missingLayoutPrototype.getObject(2);
			%>

			<li>
				<%= ResourceActionsUtil.getModelResource(locale, layoutPrototypeClassName) %>: <strong><%= HtmlUtil.escape(layoutPrototypeName) %></strong> (<%= HtmlUtil.escape(layoutPrototypeUuid) %>)
			</li>

			<%
			}
			%>

		</ul>
	</liferay-ui:error>

	<%@ include file="/error/error_auth_exception.jspf" %>

	<%@ include file="/error/error_illegal_argument_exception.jspf" %>

	<%@ include file="/error/error_remote_export_exception.jspf" %>

	<%@ include file="/error/error_remote_options_exception.jspf" %>

	<liferay-ui:error exception="<%= SystemException.class %>">

		<%
		SystemException se = (SystemException)errorException;
		%>

		<liferay-ui:message key="<%= se.getMessage() %>" />
	</liferay-ui:error>

	<div id="<portlet:namespace />publishOptions">
		<div class="export-dialog-tree">

			<%
			String taskExecutorClassName = localPublishing ? BackgroundTaskExecutorNames.LAYOUT_STAGING_BACKGROUND_TASK_EXECUTOR : BackgroundTaskExecutorNames.LAYOUT_REMOTE_STAGING_BACKGROUND_TASK_EXECUTOR;

			int incompleteBackgroundTaskCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(stagingGroupId, taskExecutorClassName, false);

			incompleteBackgroundTaskCount += BackgroundTaskManagerUtil.getBackgroundTasksCount(liveGroupId, taskExecutorClassName, false);
			%>

			<div class="<%= incompleteBackgroundTaskCount == 0 ? "hide" : "in-progress" %>" id="<portlet:namespace />incompleteProcessMessage">
				<liferay-util:include page="/processes_list/incomplete_processes_message.jsp" servletContext="<%= application %>">
					<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
				</liferay-util:include>
			</div>

			<%
			String scheduleCMD = StringPool.BLANK;

			if (cmd.equals(Constants.PUBLISH_TO_LIVE)) {
				scheduleCMD = "schedule_publish_to_live";
			}
			else if (cmd.equals(Constants.PUBLISH_TO_REMOTE)) {
				scheduleCMD = "schedule_publish_to_remote";
			}
			else if (cmd.equals("copy_from_live")) {
				scheduleCMD = "schedule_copy_from_live";
			}
			%>

			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<c:choose>
						<c:when test="<%= exportImportConfiguration == null %>">
							<aui:input name="name" placeholder="process-name-placeholder" />
						</c:when>
						<c:otherwise>
							<aui:input name="name" value="<%= exportImportConfiguration.getName() %>" />
						</c:otherwise>
					</c:choose>
				</aui:fieldset>

				<aui:fieldset collapsible="<%= true %>" cssClass="options-group" label="date">
					<%@ include file="/new_publication/publish_layouts_scheduler.jspf" %>
				</aui:fieldset>

				<c:if test="<%= !group.isCompany() %>">
					<aui:fieldset collapsible="<%= true %>" cssClass="options-group" label="pages">
						<liferay-util:include page="/new_publication/select_pages.jsp" servletContext="<%= application %>">
							<liferay-util:param name="<%= Constants.CMD %>" value="<%= cmd %>" />
							<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
							<liferay-util:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
							<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
							<liferay-util:param name="treeId" value="<%= treeId %>" />
							<liferay-util:param name="selectedLayoutIds" value="<%= StringUtil.merge(selectedLayoutIds) %>" />
							<liferay-util:param name="disableInputs" value="<%= String.valueOf(configuredPublish) %>" />
						</liferay-util:include>
					</aui:fieldset>
				</c:if>

				<liferay-staging:content cmd="<%= cmd %>" disableInputs="<%= configuredPublish %>" parameterMap="<%= parameterMap %>" type="<%= localPublishing ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE %>" />

				<liferay-staging:deletions cmd="<%= Constants.PUBLISH %>" disableInputs="<%= configuredPublish %>" />

				<liferay-staging:permissions action="publish" descriptionCSSClass="permissions-description" disableInputs="<%= configuredPublish %>" global="<%= group.isCompany() %>" labelCSSClass="permissions-label" parameterMap="<%= parameterMap %>" />

				<c:if test="<%= !localPublishing %>">
					<aui:fieldset collapsible="<%= true %>" cssClass="options-group" label="remote-live-connection-settings">
						<%@ include file="/new_publication/publish_layouts_remote_options.jspf" %>
					</aui:fieldset>
				</c:if>
			</aui:fieldset-group>
		</div>

		<aui:button-row>
			<aui:button cssClass="btn-lg" id="addButton" onClick='<%= renderResponse.getNamespace() + "schedulePublishEvent();" %>' value="add-event" />

			<aui:button cssClass="btn-lg" id="publishButton" type="submit" value="<%= LanguageUtil.get(request, publishMessageKey) %>" />

			<aui:button cssClass="btn-lg" href="<%= basePortletURL %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>

<aui:script>
	function <portlet:namespace />publishPages() {
		var form = AUI.$(document.<portlet:namespace />exportPagesFm);

		if (form.fm('allContent').prop('checked')) {
			form.fm('<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>').val(true);
		}

		submitForm(form);
	}

	Liferay.Util.toggleRadio('<portlet:namespace />allApplications', '<portlet:namespace />showChangeGlobalConfiguration', ['<portlet:namespace />selectApplications']);
	Liferay.Util.toggleRadio('<portlet:namespace />allContent', '<portlet:namespace />showChangeGlobalContent', ['<portlet:namespace />selectContents']);
	Liferay.Util.toggleRadio('<portlet:namespace />publishingEventNow', '<portlet:namespace />publishButton', ['<portlet:namespace />selectSchedule', '<portlet:namespace />addButton']);
	Liferay.Util.toggleRadio('<portlet:namespace />publishingEventSchedule', ['<portlet:namespace />selectSchedule', '<portlet:namespace />addButton'], '<portlet:namespace />publishButton');
	Liferay.Util.toggleRadio('<portlet:namespace />rangeAll', '', ['<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs']);
	Liferay.Util.toggleRadio('<portlet:namespace />rangeDateRange', '<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs');
	Liferay.Util.toggleRadio('<portlet:namespace />rangeLastPublish', '', ['<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs']);
	Liferay.Util.toggleRadio('<portlet:namespace />rangeLast', '<portlet:namespace />rangeLastInputs', ['<portlet:namespace />startEndDate']);
</aui:script>

<aui:script use="liferay-export-import">
	new Liferay.ExportImport(
		{
			commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>',
			deletionsNode: '#<%= PortletDataHandlerKeys.DELETIONS %>',
			form: document.<portlet:namespace />exportPagesFm,
			incompleteProcessMessageNode: '#<portlet:namespace />incompleteProcessMessage',
			locale: '<%= locale.toLanguageTag() %>',
			namespace: '<portlet:namespace />',
			pageTreeId: '<%= treeId %>',
			processesNode: '#publishProcesses',
			rangeAllNode: '#rangeAll',
			rangeDateRangeNode: '#rangeDateRange',
			rangeLastNode: '#rangeLast',
			rangeLastPublishNode: '#rangeLastPublish',
			ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>',
			remoteAddressNode: '#<portlet:namespace />remoteAddress',
			remoteGroupIdNode: '#<portlet:namespace />remoteGroupId',
			remotePathContextNode: '#<portlet:namespace />remotePathContext',
			remotePortNode: '#<portlet:namespace />remotePort',
			secureConnectionNode: '#secureConnection',
			setupNode: '#<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>',
			timeZone: '<%= timeZone.getID() %>',
			userPreferencesNode: '#<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>'
		}
	);

	var clickHandler = function(event) {
		var dataValue = event.target.ancestor('li').attr('data-value');

		processDataValue(dataValue);
	};

	var processDataValue = function(dataValue) {
		var customConfiguration = A.one('#<portlet:namespace />customConfiguration');
		var savedConfigurations = A.one('#<portlet:namespace />savedConfigurations');

		if (dataValue === 'custom') {
			savedConfigurations.hide();

			customConfiguration.show();
		}
		else if (dataValue === 'saved') {
			customConfiguration.hide();

			savedConfigurations.show();
		}
	};

	var publishConfigurationButtons = A.one('#<portlet:namespace />publishConfigurationButtons');

	if (publishConfigurationButtons) {
		publishConfigurationButtons.delegate('click', clickHandler, 'li a');
	}
</aui:script>