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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<liferay-ui:error-marker key="errorSection" value="relations" />
<liferay-ui:error key="restrictedRelationInvalid" message="you-must-restrict-by-site-or-type" />

<h3><liferay-ui:message key="relations" /></h3>

<%
SocialRelationConfiguration socialRelationConfiguration = SocialRelationConfigurationUtil.getGroupSettings(company.getCompanyId(), scopeGroupId);
%>

<c:choose>
	<c:when test="<%= !socialRelationConfiguration.isInteractionsEnabled() %>">
		<div class="alert alert-warning">
			<liferay-ui:message key="interactions-are-disabled-in-the-portal" />
		</div>
	</c:when>
	<c:otherwise>
		<aui:input checked="<%= socialRelationConfiguration.isInteractionsEnabled() %>" label="enable-interactions" name="TypeSettingsProperties--interactionsEnabled--" type="checkbox" value="<%= socialRelationConfiguration.isInteractionsEnabled() %>" />

		<div class="interactions-settings" id="<portlet:namespace />interactionsSettings">
			<aui:input checked="<%= socialRelationConfiguration.isAnyUserEnabled() %>" id="interactionsAnyUser" label="each-user-can-interact-with-any-user" name="TypeSettingsProperties--interactionsAnyUser--" type="radio" value="<%= true %>" />

			<aui:input checked="<%= !socialRelationConfiguration.isAnyUserEnabled() %>" id="interactionsChooseUsers" label="each-user-can-interact-only-with" name="TypeSettingsProperties--interactionsAnyUser--" type="radio" value="<%= false %>" />

			<div class="interactions-users" id="<portlet:namespace />interactionsUsersWrapper">
				<aui:input checked="<%= socialRelationConfiguration.isSameSitesEnabled() %>" label="users-that-belong-to-the-sites-that-the-user-also-belongs-to" name="TypeSettingsProperties--interactionsSitesEnabled--" type="checkbox" value="<%= socialRelationConfiguration.isSameSitesEnabled() %>" />

				<aui:input checked="<%= socialRelationConfiguration.isSocialRelationTypesEnabled() %>" label="users-with-the-following-social-relations" name="TypeSettingsProperties--interactionsSocialRelationTypesEnabled--" type="checkbox" value="<%= socialRelationConfiguration.isSocialRelationTypesEnabled() %>" />

				<aui:input name="TypeSettingsProperties--interactionsSocialRelationTypes--" type="hidden" value="<%= socialRelationConfiguration.getSocialRelationTypesString() %>" />

				<%
					List<Integer> allSocialRelationTypes = SocialRelationConstants.getAllSocialRelationTypes();
				%>

				<div class="social-relations" id="<portlet:namespace />socialRelations">
					<aui:field-wrapper>

						<%

						// Left list

						List leftList = new ArrayList();

						int[] socialRelationTypesArray = socialRelationConfiguration.getSocialRelationTypes();

						for (int socialRelationType : socialRelationTypesArray) {
							leftList.add(new KeyValuePair(Integer.toString(socialRelationType), LanguageUtil.get(pageContext, SocialRelationConstants.getTypeLabel(socialRelationType))));
						}

						// Right list

						List rightList = new ArrayList();

						for (int socialRelationType : allSocialRelationTypes) {
							if (Arrays.binarySearch(socialRelationTypesArray, socialRelationType) < 0) {
								rightList.add(new KeyValuePair(Integer.toString(socialRelationType), LanguageUtil.get(pageContext, SocialRelationConstants.getTypeLabel(socialRelationType))));
							}
						}
						%>

						<liferay-ui:input-move-boxes
							leftBoxName="currentSocialRelationTypes"
							leftList="<%= leftList %>"
							leftTitle="current"
							rightBoxName="availableSocialRelationTypes"
							rightList="<%= rightList %>"
							rightTitle="available"
						/>
					</aui:field-wrapper>
				</div>
			</div>
		</div>

		<aui:script use="aui-base,liferay-util-list-fields">
			Liferay.Util.toggleBoxes('<portlet:namespace />interactionsEnabledCheckbox','<portlet:namespace />interactionsSettings');
			Liferay.Util.toggleBoxes('<portlet:namespace />interactionsSocialRelationTypesEnabledCheckbox','<portlet:namespace />socialRelations');

			Liferay.Util.toggleRadio('<portlet:namespace />interactionsAnyUser', '', '<portlet:namespace />interactionsUsersWrapper');
			Liferay.Util.toggleRadio('<portlet:namespace />interactionsChooseUsers','<portlet:namespace />interactionsUsersWrapper', '');

			var form = A.one('#<portlet:namespace />fm');

			form.on('submit', function(e) {
				var socialRelationTypesInputName = '<portlet:namespace />TypeSettingsProperties--interactionsSocialRelationTypes--';

				document.<portlet:namespace />fm[socialRelationTypesInputName].value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentSocialRelationTypes);
			});
		</aui:script>
	</c:otherwise>
</c:choose>