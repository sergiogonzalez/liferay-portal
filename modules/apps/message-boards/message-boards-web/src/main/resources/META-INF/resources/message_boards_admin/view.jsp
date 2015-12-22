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

<%@ include file="/message_boards/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "message-boards-home");

String entriesNavigation = ParamUtil.getString(request, "entriesNavigation", "all");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);

MBCategoryDisplay categoryDisplay = new MBCategoryDisplayImpl(scopeGroupId, categoryId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/message_boards/view");
portletURL.setParameter("topLink", topLink);
portletURL.setParameter("mbCategoryId", String.valueOf(categoryId));

request.setAttribute("view.jsp-viewCategory", Boolean.TRUE.toString());

if ((category != null) && layout.isTypeControlPanel()) {
	MBUtil.addPortletBreadcrumbEntries(category, request, renderResponse);
}

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(MBPortletKeys.MESSAGE_BOARDS, "display-style", "descriptive");
}
else {
	portalPreferences.setValue(MBPortletKeys.MESSAGE_BOARDS, "display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}
%>

<portlet:actionURL name="/message_boards/edit_category" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<%
PortletURL navigationURL = renderResponse.createRenderURL();

navigationURL.setParameter("mvcRenderCommandName", "/message_boards/view");
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		navigationURL.setParameter("top-link", "message-boards-home");
		navigationURL.setParameter("tag", StringPool.BLANK);
		%>

		<aui:nav-item
			href="<%= navigationURL.toString() %>"
			label="message-boards-home"
			selected='<%= topLink.equals("message-boards-home") %>'
		/>

		<%
		navigationURL.setParameter("topLink", "statistics");
		%>

		<aui:nav-item
			href="<%= navigationURL.toString() %>"
			label="statistics"
			selected='<%= topLink.equals("statistics") %>'
		/>

		<%
		navigationURL.setParameter("topLink", "banned-users");
		%>

		<aui:nav-item
			href="<%= navigationURL.toString() %>"
			label="banned-users"
			selected='<%= topLink.equals("banned-users") %>'
		/>
	</aui:nav>

	<liferay-portlet:renderURL varImpl="searchURL">
		<portlet:param name="mvcRenderCommandName" value="/message_boards/search" />
	</liferay-portlet:renderURL>

	<aui:nav-bar-search>
		<aui:form action="<%= searchURL %>" name="searchFm">
			<liferay-portlet:renderURLParams varImpl="searchURL" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="breadcrumbsCategoryId" type="hidden" value="<%= categoryId %>" />
			<aui:input name="searchCategoryId" type="hidden" value="<%= categoryId %>" />

			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="mbEntries"
>

	<%
	PortletURL displayStyleURL = renderResponse.createRenderURL();

	displayStyleURL.setParameter("mvcRenderCommandName", "/message_boards/view");

	displayStyleURL.setParameter("categoryId", String.valueOf(categoryId));
	%>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive", "list"} %>'
			portletURL="<%= displayStyleURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<portlet:renderURL var="viewEntriesHomeURL">
		<portlet:param name="categoryId" value="<%= String.valueOf(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) %>" />
	</portlet:renderURL>

	<liferay-frontend:management-bar-filters>

		<%
		PortletURL navigationPortletURL = renderResponse.createRenderURL();

		navigationPortletURL.setParameter("categoryId", String.valueOf(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID));
		%>

		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all", "recent"} %>'
			navigationParam="entriesNavigation"
			portletURL="<%= navigationPortletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>

		<%
		String taglibURL = "javascript:" + renderResponse.getNamespace() + "deleteEntries();";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" icon='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "trash" : "times" %>' label='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "recycle-bin" : "delete" %>' />

		<%
		taglibURL = "javascript:" + renderResponse.getNamespace() + "lockEntries();";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" icon="lock" label="lock" />

		<%
		taglibURL = "javascript:" + renderResponse.getNamespace() + "unlockEntries();";
		%>

		<liferay-frontend:management-bar-button href="<%= taglibURL %>" icon="unlock" label="unlock" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<c:choose>
		<c:when test='<%= topLink.equals("message-boards-home") %>'>
			<c:if test="<%= category != null %>">

				<%
				long parentCategoryId = category.getParentCategoryId();
				String parentCategoryName = LanguageUtil.get(request, "message-boards-home");

				if (!category.isRoot()) {
					MBCategory parentCategory = MBCategoryLocalServiceUtil.getCategory(parentCategoryId);

					parentCategoryId = parentCategory.getCategoryId();
					parentCategoryName = parentCategory.getName();
				}
				%>

				<portlet:renderURL var="backURL">
					<portlet:param name="mvcRenderCommandName" value="/message_boards/view" />
					<portlet:param name="mbCategoryId" value="<%= String.valueOf(parentCategoryId) %>" />
				</portlet:renderURL>

				<liferay-ui:header
					backLabel="<%= parentCategoryName %>"
					backURL="<%= backURL.toString() %>"
					localizeTitle="<%= false %>"
					title="<%= category.getName() %>"
				/>
			</c:if>

			<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />

				<%
				long groupThreadsUserId = ParamUtil.getLong(request, "groupThreadsUserId");

				if (groupThreadsUserId > 0) {
					portletURL.setParameter("groupThreadsUserId", String.valueOf(groupThreadsUserId));
				}

				Calendar calendar = Calendar.getInstance();

				int offset = GetterUtil.getInteger(recentPostsDateOffset);

				calendar.add(Calendar.DATE, -offset);

				int entriesTotal = 0;

				if (entriesNavigation.equals("all")) {
					entriesTotal = MBCategoryLocalServiceUtil.getCategoriesAndThreadsCount(scopeGroupId, categoryId);
				}
				else if (entriesNavigation.equals("recent")) {
					entriesTotal = MBThreadServiceUtil.getGroupThreadsCount(scopeGroupId, groupThreadsUserId, calendar.getTime(), WorkflowConstants.STATUS_APPROVED);
				}
				%>

				<liferay-ui:search-container
					curParam="cur1"
					emptyResultsMessage="there-are-no-threads-nor-categories"
					id="mbEntries"
					iteratorURL="<%= portletURL %>"
					rowChecker="<%= new EntriesChecker(liferayPortletRequest, liferayPortletResponse) %>"
					total="<%= entriesTotal %>"
					var="categorySearchContainer"
				>

					<%
					int status = WorkflowConstants.STATUS_APPROVED;

					if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
						status = WorkflowConstants.STATUS_ANY;
					}

					List entriesResults = null;

					if (entriesNavigation.equals("all")) {
						entriesResults = MBCategoryServiceUtil.getCategoriesAndThreads(scopeGroupId, categoryId, status, categorySearchContainer.getStart(), categorySearchContainer.getEnd());
					}
					else if (entriesNavigation.equals("recent")) {
						entriesResults = MBThreadServiceUtil.getGroupThreads(scopeGroupId, groupThreadsUserId, calendar.getTime(), WorkflowConstants.STATUS_APPROVED, categorySearchContainer.getStart(), categorySearchContainer.getEnd());
					}
					%>

					<liferay-ui:search-container-results
						results="<%= entriesResults %>"
					/>

					<liferay-ui:search-container-row
						className="Object"
						escapedModel="<%= true %>"
						keyProperty="categoryId"
						modelVar="result"
					>

						<%@ include file="/message_boards/cast_result.jspf" %>

						<c:choose>
							<c:when test="<%= curCategory != null %>">

								<%
								row.setPrimaryKey(String.valueOf(curCategory.getCategoryId()));
								%>

								<liferay-portlet:renderURL varImpl="rowURL">
									<portlet:param name="mvcRenderCommandName" value="/message_boards/view" />
									<portlet:param name="mbCategoryId" value="<%= String.valueOf(curCategory.getCategoryId()) %>" />
								</liferay-portlet:renderURL>

								<c:choose>
									<c:when test='<%= displayStyle.equals("descriptive") %>'>
										<liferay-ui:search-container-column-icon
											icon="folder"
											toggleRowChecker="<%= true %>"
										/>

										<liferay-ui:search-container-column-text colspan="<%= 2 %>">
											<h4>
												<aui:a href="<%= rowURL.toString() %>">
													<%= HtmlUtil.escape(curCategory.getName()) %>
												</aui:a>
											</h4>

											<h5 class="text-default">
												<%= HtmlUtil.escape(curCategory.getDescription()) %>
											</h5>

											<%
											int subcategoriesCount = MBCategoryServiceUtil.getCategoriesCount(scopeGroupId, curCategory.getCategoryId());
											int threadsCount = MBThreadServiceUtil.getGroupThreadsCount(scopeGroupId, themeDisplay.getUserId(), WorkflowConstants.STATUS_APPROVED);
											%>

											<span class="h6">
												<liferay-ui:message arguments="<%= subcategoriesCount %>" key='<%= subcategoriesCount == 1 ? "x-subcategory" : "x-subcategories" %>' />
											</span>

											<span class="h6">
												<liferay-ui:message arguments="<%= threadsCount %>" key='<%= threadsCount == 1 ? "x-thread" : "x-threads" %>' />
											</span>
										</liferay-ui:search-container-column-text>
									</c:when>
									<c:otherwise>
										<liferay-ui:search-container-column-text
											name="category[message-board]"
										>
											<a href="<%= rowURL %>">
												<span class="category-title">
													<%= curCategory.getName() %>
												</span>

												<c:if test="<%= Validator.isNotNull(curCategory.getDescription()) %>">
													<span class="category-description"><%= curCategory.getDescription() %></span>
												</c:if>
											</a>

											<%
											List subcategories = MBCategoryServiceUtil.getCategories(scopeGroupId, curCategory.getCategoryId(), WorkflowConstants.STATUS_APPROVED, 0, 5);

											int subcategoriesCount = MBCategoryServiceUtil.getCategoriesCount(scopeGroupId, curCategory.getCategoryId(), WorkflowConstants.STATUS_APPROVED);
											%>

											<c:if test="<%= subcategoriesCount > 0 %>">
												<span class="subcategories">
													<liferay-ui:message key="subcategories[message-board]" />:
												</span>

												<%
												for (int j = 0; j < subcategories.size(); j++) {
													MBCategory subcategory = (MBCategory)subcategories.get(j);

													rowURL.setParameter("mbCategoryId", String.valueOf(subcategory.getCategoryId()));

													String name = HtmlUtil.escape(subcategory.getName());

													if (((j + 1) < subcategories.size()) || (subcategoriesCount > subcategories.size())) {
														name += StringPool.COMMA_AND_SPACE;
													}
												%>

													<a href="<%= rowURL %>"><%= name %></a>

												<%
												}

												rowURL.setParameter("mbCategoryId", String.valueOf(curCategory.getCategoryId()));
												%>

												<c:if test="<%= subcategoriesCount > subcategories.size() %>">
													<a href="<%= rowURL %>"><liferay-ui:message key="more" /> &raquo;</a>
												</c:if>
											</c:if>
										</liferay-ui:search-container-column-text>

										<liferay-ui:search-container-column-text
											href="<%= rowURL %>"
											name="categories[message-board]"
											value="<%= String.valueOf(categoryDisplay.getSubcategoriesCount(curCategory)) %>"
										/>

										<liferay-ui:search-container-column-text
											href="<%= rowURL %>"
											name="threads"
											value="<%= String.valueOf(categoryDisplay.getSubcategoriesThreadsCount(curCategory)) %>"
										/>

										<liferay-ui:search-container-column-text
											href="<%= rowURL %>"
											name="posts"
											value="<%= String.valueOf(categoryDisplay.getSubcategoriesMessagesCount(curCategory)) %>"
										/>
									</c:otherwise>
								</c:choose>

								<liferay-ui:search-container-column-jsp
									path="/message_boards/category_action.jsp"
								/>
							</c:when>
							<c:otherwise>

								<%
								MBMessage message = MBMessageLocalServiceUtil.fetchMBMessage(thread.getRootMessageId());

								if (message == null) {
									_log.error("Thread requires missing root message id " + thread.getRootMessageId());

									message = new MBMessageImpl();

									row.setSkip(true);
								}

								message = message.toEscapedModel();

								row.setBold(!MBThreadFlagLocalServiceUtil.hasThreadFlag(themeDisplay.getUserId(), thread));
								row.setPrimaryKey(String.valueOf(thread.getThreadId()));
								row.setRestricted(!MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW));
								%>

								<liferay-portlet:renderURL varImpl="rowURL">
									<portlet:param name="mvcRenderCommandName" value="/message_boards/view_message" />
									<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
								</liferay-portlet:renderURL>

								<c:choose>
									<c:when test='<%= displayStyle.equals("descriptive") %>'>
										<liferay-ui:search-container-column-user
											cssClass="user-icon-lg"
											showDetails="<%= false %>"
											userId="<%= message.getUserId() %>"
										/>

										<liferay-ui:search-container-column-text colspan="<%= 2 %>">

											<%
											Date modifiedDate = message.getModifiedDate();

											String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
											%>

											<h5 class="text-default">
												<liferay-ui:message arguments="<%= new String[] {message.getUserName(), modifiedDateDescription} %>" key="x-modified-x-ago" />
											</h5>

											<h4>
												<aui:a href="<%= rowURL.toString() %>">
													<%= message.getSubject() %>
												</aui:a>
											</h4>

											<%
											int messageCount = thread.getMessageCount();
											int viewCount = thread.getViewCount();
											%>

											<span class="h6">
												<liferay-ui:message arguments="<%= messageCount %>" key='<%= messageCount == 1 ? "x-post" : "x-posts" %>' />
											</span>

											<span class="h6">
												<liferay-ui:message arguments="<%= viewCount %>" key='<%= viewCount == 1 ? "x-view" : "x-views" %>' />
											</span>
										</liferay-ui:search-container-column-text>
									</c:when>
									<c:otherwise>
										<%@ include file="/message_boards/thread_priority.jspf" %>

										<liferay-ui:search-container-column-text
											href="<%= rowURL %>"
											name="flag"
										>
											<c:choose>
												<c:when test="<%= MBThreadLocalServiceUtil.hasAnswerMessage(thread.getThreadId()) %>">
													<liferay-ui:message key="resolved" />
												</c:when>
												<c:when test="<%= thread.isQuestion() %>">
													<liferay-ui:message key="waiting-for-an-answer" />
												</c:when>
											</c:choose>
										</liferay-ui:search-container-column-text>

										<liferay-ui:search-container-column-text
											href="<%= rowURL %>"
											name="started-by"
											value='<%= message.isAnonymous() ? LanguageUtil.get(request, "anonymous") : PortalUtil.getUserName(message) %>'
										/>

										<liferay-ui:search-container-column-text
											href="<%= rowURL %>"
											name="posts"
											value="<%= String.valueOf(thread.getMessageCount()) %>"
										/>

										<liferay-ui:search-container-column-text
											href="<%= rowURL %>"
											name="views"
											value="<%= String.valueOf(thread.getViewCount()) %>"
										/>

										<liferay-ui:search-container-column-user
											date="<%= thread.getLastPostDate() %>"
											name="last-post"
											property="lastPostByUserId"
										/>

										<liferay-ui:search-container-column-status
											href="<%= rowURL %>"
											name="status"
											status="<%= thread.getStatus() %>"
										/>
									</c:otherwise>
								</c:choose>

								<%
								row.setObject(new Object[] {message});
								%>

								<liferay-ui:search-container-column-jsp
									path="/message_boards/message_action.jsp"
								/>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" resultRowSplitter="<%= new MBResultRowSplitter() %>" />
				</liferay-ui:search-container>
			</aui:form>

			<%
			if (category != null) {
				PortalUtil.setPageSubtitle(category.getName(), request);
				PortalUtil.setPageDescription(category.getDescription(), request);

				MBUtil.addPortletBreadcrumbEntries(category, request, renderResponse);
			}
			%>

		</c:when>
		<c:when test='<%= topLink.equals("statistics") %>'>
			<liferay-ui:panel-container cssClass="statistics-panel" extended="<%= false %>" id="messageBoardsStatisticsPanelContainer" persistState="<%= true %>">
				<liferay-ui:panel collapsible="<%= true %>" cssClass="statistics-panel-content" extended="<%= true %>" id="messageBoardsGeneralStatisticsPanel" persistState="<%= true %>" title="general">
					<dl>
						<dt>
							<liferay-ui:message key="num-of-categories" />:
						</dt>
						<dd>
							<%= numberFormat.format(categoryDisplay.getAllCategoriesCount()) %>
						</dd>
						<dt>
							<liferay-ui:message key="num-of-posts" />:
						</dt>
						<dd>
							<%= numberFormat.format(MBStatsUserLocalServiceUtil.getMessageCountByGroupId(scopeGroupId)) %>
						</dd>
						<dt>
							<liferay-ui:message key="num-of-participants" />:
						</dt>
						<dd>
							<%= numberFormat.format(MBStatsUserLocalServiceUtil.getStatsUsersByGroupIdCount(scopeGroupId)) %>
						</dd>
					</dl>
				</liferay-ui:panel>

				<liferay-ui:panel collapsible="<%= true %>" cssClass="statistics-panel-content" extended="<%= true %>" id="messageBoardsTopPostersPanel" persistState="<%= true %>" title="top-posters">
					<liferay-ui:search-container
						emptyResultsMessage="there-are-no-top-posters"
						iteratorURL="<%= portletURL %>"
						total="<%= MBStatsUserLocalServiceUtil.getStatsUsersByGroupIdCount(scopeGroupId) %>"
					>
						<liferay-ui:search-container-results
							results="<%= MBStatsUserLocalServiceUtil.getStatsUsersByGroupId(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd()) %>"
						/>

						<liferay-ui:search-container-row
							className="com.liferay.portlet.messageboards.model.MBStatsUser"
							keyProperty="statsUserId"
							modelVar="statsUser"
						>
							<liferay-ui:search-container-column-jsp
								path="/message_boards/top_posters_user_display.jsp"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
					</liferay-ui:search-container>
				</liferay-ui:panel>
			</liferay-ui:panel-container>

			<%
			PortalUtil.setPageSubtitle(LanguageUtil.get(request, StringUtil.replace(topLink, StringPool.UNDERLINE, StringPool.DASH)), request);
			PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format(topLink, TextFormatter.O)), portletURL.toString());
			%>

		</c:when>
		<c:when test='<%= topLink.equals("banned-users") %>'>
			<liferay-ui:search-container
				emptyResultsMessage="there-are-no-banned-users"
				headerNames="banned-user,banned-by,ban-date"
				iteratorURL="<%= portletURL %>"
				total="<%= MBBanLocalServiceUtil.getBansCount(scopeGroupId) %>"
			>
				<liferay-ui:search-container-results
					results="<%= MBBanLocalServiceUtil.getBans(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd()) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portlet.messageboards.model.MBBan"
					keyProperty="banId"
					modelVar="ban"
				>
					<liferay-ui:search-container-column-text
						name="banned-user"
						value="<%= HtmlUtil.escape(PortalUtil.getUserName(ban.getBanUserId(), StringPool.BLANK)) %>"
					/>

					<liferay-ui:search-container-column-text
						name="banned-by"
						value="<%= HtmlUtil.escape(PortalUtil.getUserName(ban.getUserId(), StringPool.BLANK)) %>"
					/>

					<liferay-ui:search-container-column-date
						name="ban-date"
						value="<%= ban.getCreateDate() %>"
					/>

					<c:if test="<%= PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL > 0 %>">
						<liferay-ui:search-container-column-text
							name="unban-date"
							value="<%= dateFormatDateTime.format(MBUtil.getUnbanDate(ban, PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL)) %>"
						/>
					</c:if>

					<liferay-ui:search-container-column-jsp
						align="right"
						cssClass="entry-action"
						path="/message_boards/ban_user_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
			</liferay-ui:search-container>

			<%
			PortalUtil.setPageSubtitle(LanguageUtil.get(request, StringUtil.replace(topLink, StringPool.UNDERLINE, StringPool.DASH)), request);
			PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format(topLink, TextFormatter.O)), portletURL.toString());
			%>

		</c:when>
	</c:choose>
</div>

<liferay-util:include page="/message_boards_admin/add_button.jsp" servletContext="<%= application %>" />

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (<%= TrashUtil.isTrashEnabled(scopeGroupId) %> || confirm('<%= UnicodeLanguageUtil.get(request, TrashUtil.isTrashEnabled(scopeGroupId) ? "are-you-sure-you-want-to-move-the-selected-entries-to-the-recycle-bin" : "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>');

			submitForm(form, '<portlet:actionURL name="/message_boards/edit_entry" />');
		}
	}

	function <portlet:namespace />lockEntries() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.attr('method', 'post');
		form.fm('<%= Constants.CMD %>').val('<%= Constants.LOCK %>');

		submitForm(form, '<portlet:actionURL name="/message_boards/edit_entry" />');
	}

	function <portlet:namespace />unlockEntries() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.attr('method', 'post');
		form.fm('<%= Constants.CMD %>').val('<%= Constants.UNLOCK %>');

		submitForm(form, '<portlet:actionURL name="/message_boards/edit_entry" />');
	}
</aui:script>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_message_boards_web.message_boards_admin.view_jsp");
%>