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

<%@ include file="/admin/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "email-from");

kbGroupServiceConfiguration = ParameterMapUtil.setParameterMap(KBGroupServiceConfiguration.class, kbGroupServiceConfiguration, request.getParameterMap(), "preferences--", "--");

String tabs2Names = "email-from,article-added-email,article-updated-email,suggestion-received-email,suggestion-in-progress-email,suggestion-resolved-email";

if (PortalUtil.isRSSFeedsEnabled()) {
	tabs2Names += ",rss";
}
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<portlet:param name="serviceName" value="<%= KBConstants.SERVICE_NAME %>" />
	<portlet:param name="settingsScope" value="group" />
</liferay-portlet:actionURL>

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />

	<liferay-ui:error key="emailKBArticleAddedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailKBArticleAddedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailKBArticleUpdatedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailKBArticleUpdatedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
	<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />

	<liferay-ui:tabs
		names="<%= tabs2Names %>"
		param="tabs2"
		refresh="<%= false %>"
		type="tabs nav-tabs-default"
	>
		<liferay-ui:section>
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<aui:input label="name" name="preferences--emailFromName--" value="<%= kbGroupServiceConfiguration.emailFromName() %>" wrapperCssClass="lfr-input-text-container" />

					<aui:input label="address" name="preferences--emailFromAddress--" value="<%= kbGroupServiceConfiguration.emailFromAddress() %>" wrapperCssClass="lfr-input-text-container" />

					<div class="definition-of-terms">
						<h4><liferay-ui:message key="definition-of-terms" /></h4>

						<dl>
							<dt>
								[$ARTICLE_USER_ADDRESS$]
							</dt>
							<dd>
								<liferay-ui:message key="the-email-address-of-the-user-who-added-the-article" />
							</dd>
							<dt>
								[$ARTICLE_USER_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-user-who-added-the-article" />
							</dd>
							<dt>
								[$CATEGORY_TITLE$]
							</dt>
							<dd>
								<liferay-ui:message key="category.kb" />
							</dd>
							<dt>
								[$COMPANY_ID$]
							</dt>
							<dd>
								<liferay-ui:message key="the-company-id-associated-with-the-article" />
							</dd>
							<dt>
								[$COMPANY_MX$]
							</dt>
							<dd>
								<liferay-ui:message key="the-company-mx-associated-with-the-article" />
							</dd>
							<dt>
								[$COMPANY_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-company-name-associated-with-the-article" />
							</dd>
							<dt>
								[$SITE_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-site-name-associated-with-the-article" />
							</dd>
						</dl>
					</div>
				</aui:fieldset>
			</aui:fieldset-group>
		</liferay-ui:section>

		<liferay-ui:section>
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<aui:input label="enabled" name="preferences--emailKBArticleAddedEnabled--" type="checkbox" value="<%= kbGroupServiceConfiguration.emailKBArticleAddedEnabled() %>" />

					<aui:input label="subject" name="preferences--emailKBArticleAddedSubject--" value="<%= kbGroupServiceConfiguration.emailKBArticleAddedSubject() %>" wrapperCssClass="lfr-input-text-container" />

					<aui:input label="body" name="preferences--emailKBArticleAddedBody--" type="textarea" value="<%= kbGroupServiceConfiguration.emailKBArticleAddedBody() %>" wrapperCssClass="lfr-textarea-container" />

					<div class="definition-of-terms">
						<h4><liferay-ui:message key="definition-of-terms" /></h4>

						<dl>
							<dt>
								[$ARTICLE_ATTACHMENTS$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-attachments-file-names" />
							</dd>
							<dt>
								[$ARTICLE_CONTENT$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-content" />
							</dd>
							<dt>
								[$ARTICLE_CONTENT_DIFF$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-content-diff" />
							</dd>
							<dt>
								[$ARTICLE_TITLE$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-title" />
							</dd>
							<dt>
								[$ARTICLE_TITLE_DIFF$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-title-diff" />
							</dd>
							<dt>
								[$ARTICLE_URL$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-url" />
							</dd>
							<dt>
								[$ARTICLE_USER_ADDRESS$]
							</dt>
							<dd>
								<liferay-ui:message key="the-email-address-of-the-user-who-added-the-article" />
							</dd>
							<dt>
								[$ARTICLE_USER_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-user-who-added-the-article" />
							</dd>
							<dt>
								[$ARTICLE_VERSION$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-version" />
							</dd>
							<dt>
								[$CATEGORY_TITLE$]
							</dt>
							<dd>
								<liferay-ui:message key="category.kb" />
							</dd>
							<dt>
								[$COMPANY_ID$]
							</dt>
							<dd>
								<liferay-ui:message key="the-company-id-associated-with-the-article" />
							</dd>
							<dt>
								[$COMPANY_MX$]
							</dt>
							<dd>
								<liferay-ui:message key="the-company-mx-associated-with-the-article" />
							</dd>
							<dt>
								[$COMPANY_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-company-name-associated-with-the-article" />
							</dd>
							<dt>
								[$FROM_ADDRESS$]
							</dt>
							<dd>
								<%= HtmlUtil.escape(kbGroupServiceConfiguration.emailFromAddress()) %>
							</dd>
							<dt>
								[$FROM_NAME$]
							</dt>
							<dd>
								<%= HtmlUtil.escape(kbGroupServiceConfiguration.emailFromName()) %>
							</dd>
							<dt>
								[$PORTAL_URL$]
							</dt>
							<dd>
								<%= PortalUtil.getPortalURL(themeDisplay) %>
							</dd>
							<dt>
								[$SITE_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-site-name-associated-with-the-article" />
							</dd>
							<dt>
								[$TO_ADDRESS$]
							</dt>
							<dd>
								<liferay-ui:message key="the-address-of-the-email-recipient" />
							</dd>
							<dt>
								[$TO_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-name-of-the-email-recipient" />
							</dd>
						</dl>
					</div>
				</aui:fieldset>
			</aui:fieldset-group>
		</liferay-ui:section>

		<liferay-ui:section>
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<aui:input label="enabled" name="preferences--emailKBArticleUpdatedEnabled--" type="checkbox" value="<%= kbGroupServiceConfiguration.emailKBArticleUpdatedEnabled() %>" />

					<aui:input label="subject" name="preferences--emailKBArticleUpdatedSubject--" value="<%= kbGroupServiceConfiguration.emailKBArticleUpdatedSubject() %>" wrapperCssClass="lfr-input-text-container" />

					<aui:input label="body" name="preferences--emailKBArticleUpdatedBody--" type="textarea" value="<%= kbGroupServiceConfiguration.emailKBArticleUpdatedBody() %>" wrapperCssClass="lfr-textarea-container" />

					<div class="definition-of-terms">
						<h4><liferay-ui:message key="definition-of-terms" /></h4>

						<dl>
							<dt>
								[$ARTICLE_ATTACHMENTS$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-attachments-file-names" />
							</dd>
							<dt>
								[$ARTICLE_CONTENT$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-content" />
							</dd>
							<dt>
								[$ARTICLE_CONTENT_DIFF$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-content-diff" />
							</dd>
							<dt>
								[$ARTICLE_TITLE$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-title" />
							</dd>
							<dt>
								[$ARTICLE_TITLE_DIFF$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-title-diff" />
							</dd>
							<dt>
								[$ARTICLE_URL$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-url" />
							</dd>
							<dt>
								[$ARTICLE_USER_ADDRESS$]
							</dt>
							<dd>
								<liferay-ui:message key="the-email-address-of-the-user-who-added-the-article" />
							</dd>
							<dt>
								[$ARTICLE_USER_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-user-who-added-the-article" />
							</dd>
							<dt>
								[$ARTICLE_VERSION$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-version" />
							</dd>
							<dt>
								[$CATEGORY_TITLE$]
							</dt>
							<dd>
								<liferay-ui:message key="category.kb" />
							</dd>
							<dt>
								[$COMPANY_ID$]
							</dt>
							<dd>
								<liferay-ui:message key="the-company-id-associated-with-the-article" />
							</dd>
							<dt>
								[$COMPANY_MX$]
							</dt>
							<dd>
								<liferay-ui:message key="the-company-mx-associated-with-the-article" />
							</dd>
							<dt>
								[$COMPANY_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-company-name-associated-with-the-article" />
							</dd>
							<dt>
								[$FROM_ADDRESS$]
							</dt>
							<dd>
								<%= HtmlUtil.escape(kbGroupServiceConfiguration.emailFromAddress()) %>
							</dd>
							<dt>
								[$FROM_NAME$]
							</dt>
							<dd>
								<%= HtmlUtil.escape(kbGroupServiceConfiguration.emailFromName()) %>
							</dd>
							<dt>
								[$PORTAL_URL$]
							</dt>
							<dd>
								<%= PortalUtil.getPortalURL(themeDisplay) %>
							</dd>
							<dt>
								[$SITE_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-site-name-associated-with-the-article" />
							</dd>
							<dt>
								[$TO_ADDRESS$]
							</dt>
							<dd>
								<liferay-ui:message key="the-address-of-the-email-recipient" />
							</dd>
							<dt>
								[$TO_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-name-of-the-email-recipient" />
							</dd>
						</dl>
					</div>
				</aui:fieldset>
			</aui:fieldset-group>
		</liferay-ui:section>

		<liferay-ui:section>
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<aui:input label="enabled" name="preferences--emailKBArticleSuggestionInProgressEnabled--" type="checkbox" value="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionInProgressEnabled() %>" />

					<aui:input cssClass="lfr-input-text-container" label="subject" name="preferences--emailKBArticleSuggestionInProgressSubject--" value="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionInProgressSubject() %>" />

					<aui:input cssClass="lfr-textarea-container" label="body" name="preferences--emailKBArticleSuggestionInProgressBody--" type="textarea" value="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionInProgressBody() %>" />

					<div class="definition-of-terms">
						<h4><liferay-ui:message key="definition-of-terms" /></h4>

						<dl>
							<dt>
								[$ARTICLE_CONTENT$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-content" />
							</dd>
							<dt>
								[$ARTICLE_TITLE$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-title" />
							</dd>
							<dt>
								[$ARTICLE_URL$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-url" />
							</dd>
							<dt>
								[$COMMENT_CONTENT$]
							</dt>
							<dd>
								<liferay-ui:message key="the-comment-content" />
							</dd>
							<dt>
								[$COMMENT_CREATE_DATE$]
							</dt>
							<dd>
								<liferay-ui:message key="the-comment-create-date" />
							</dd>
							<dt>
								[$TO_ADDRESS$]
							</dt>
							<dd>
								<liferay-ui:message key="the-address-of-the-email-recipient" />
							</dd>
							<dt>
								[$TO_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-name-of-the-email-recipient" />
							</dd>
						</dl>
					</div>
				</aui:fieldset>
			</aui:fieldset-group>
		</liferay-ui:section>

		<liferay-ui:section>
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<aui:input label="enabled" name="preferences--emailKBArticleSuggestionReceivedEnabled--" type="checkbox" value="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionReceivedEnabled() %>" />

					<aui:input cssClass="lfr-input-text-container" label="subject" name="preferences--emailKBArticleSuggestionReceivedSubject--" value="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionReceivedSubject() %>" />

					<aui:input cssClass="lfr-textarea-container" label="body" name="preferences--emailKBArticleSuggestionReceivedBody--" type="textarea" value="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionReceivedBody() %>" />

					<div class="definition-of-terms">
						<h4><liferay-ui:message key="definition-of-terms" /></h4>

						<dl>
							<dt>
								[$ARTICLE_CONTENT$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-content" />
							</dd>
							<dt>
								[$ARTICLE_TITLE$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-title" />
							</dd>
							<dt>
								[$ARTICLE_URL$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-url" />
							</dd>
							<dt>
								[$COMMENT_CONTENT$]
							</dt>
							<dd>
								<liferay-ui:message key="the-comment-content" />
							</dd>
							<dt>
								[$COMMENT_CREATE_DATE$]
							</dt>
							<dd>
								<liferay-ui:message key="the-comment-create-date" />
							</dd>
							<dt>
								[$TO_ADDRESS$]
							</dt>
							<dd>
								<liferay-ui:message key="the-address-of-the-email-recipient" />
							</dd>
							<dt>
								[$TO_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-name-of-the-email-recipient" />
							</dd>
						</dl>
					</div>
				</aui:fieldset>
			</aui:fieldset-group>
		</liferay-ui:section>

		<liferay-ui:section>
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<aui:input label="enabled" name="preferences--emailKBArticleSuggestionResolvedEnabled--" type="checkbox" value="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionResolvedEnabled() %>" />

					<aui:input cssClass="lfr-input-text-container" label="subject" name="preferences--emailKBArticleSuggestionResolvedSubject--" value="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionResolvedSubject() %>" />

					<aui:input cssClass="lfr-textarea-container" label="body" name="preferences--emailKBArticleSuggestionResolvedBody--" type="textarea" value="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionResolvedBody() %>" />

					<div class="definition-of-terms">
						<h4><liferay-ui:message key="definition-of-terms" /></h4>

						<dl>
							<dt>
								[$ARTICLE_CONTENT$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-content" />
							</dd>
							<dt>
								[$ARTICLE_TITLE$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-title" />
							</dd>
							<dt>
								[$ARTICLE_URL$]
							</dt>
							<dd>
								<liferay-ui:message key="the-article-url" />
							</dd>
							<dt>
								[$COMMENT_CONTENT$]
							</dt>
							<dd>
								<liferay-ui:message key="the-comment-content" />
							</dd>
							<dt>
								[$COMMENT_CREATE_DATE$]
							</dt>
							<dd>
								<liferay-ui:message key="the-comment-create-date" />
							</dd>
							<dt>
								[$TO_ADDRESS$]
							</dt>
							<dd>
								<liferay-ui:message key="the-address-of-the-email-recipient" />
							</dd>
							<dt>
								[$TO_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-name-of-the-email-recipient" />
							</dd>
						</dl>
					</div>
				</aui:fieldset>
			</aui:fieldset-group>
		</liferay-ui:section>

		<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
			<liferay-ui:section>
				<aui:fieldset-group markupView="lexicon">
					<liferay-ui:rss-settings
						delta="<%= kbGroupServiceConfiguration.rssDelta() %>"
						displayStyle="<%= kbGroupServiceConfiguration.rssDisplayStyle() %>"
						enabled="<%= kbGroupServiceConfiguration.enableRSS() %>"
						feedType="<%= kbGroupServiceConfiguration.rssFeedType() %>"
					/>
				</aui:fieldset-group>
			</liferay-ui:section>
		</c:if>
	</liferay-ui:tabs>

	<aui:button-row cssClass="kb-submit-buttons">
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>