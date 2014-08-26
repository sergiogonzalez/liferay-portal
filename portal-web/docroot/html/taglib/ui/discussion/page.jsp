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

<%@ include file="/html/taglib/ui/discussion/init.jsp" %>

<%
String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;

boolean assetEntryVisible = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:discussion:assetEntryVisible"));
String className = (String)request.getAttribute("liferay-ui:discussion:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:discussion:classPK"));
String formAction = (String)request.getAttribute("liferay-ui:discussion:formAction");
String formName = (String)request.getAttribute("liferay-ui:discussion:formName");
boolean hideControls = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:discussion:hideControls"));
String permissionClassName = (String)request.getAttribute("liferay-ui:discussion:permissionClassName");
long permissionClassPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:discussion:permissionClassPK"));
boolean ratingsEnabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:discussion:ratingsEnabled"));
String redirect = (String)request.getAttribute("liferay-ui:discussion:redirect");
long userId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:discussion:userId"));

String strutsAction = ParamUtil.getString(request, "struts_action");

DiscussionThreadView discussionThreadView = DiscussionThreadView.valueOf(StringUtil.toUpperCase(PropsValues.DISCUSSION_THREAD_VIEW));

CommentManager commentManager = CommentManagerUtil.getCommentManager();

CommentSectionDisplay commentSectionDisplay = commentManager.createCommentSectionDisplay(company.getCompanyId(), userId, scopeGroupId, className, classPK, permissionClassName, permissionClassPK, permissionChecker, hideControls, ratingsEnabled, discussionThreadView, themeDisplay);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<div class="hide lfr-message-response" id="<portlet:namespace />discussion-status-messages"></div>

<c:if test="<%= commentSectionDisplay.isDiscussionVisible() %>">
	<div class="taglib-discussion" id="<portlet:namespace />discussion-container">
		<aui:form action="<%= formAction %>" method="post" name="<%= formName %>">
			<aui:input name="randomNamespace" type="hidden" value="<%= randomNamespace %>" />
			<aui:input id="<%= randomNamespace + Constants.CMD %>" name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="contentURL" type="hidden" value="<%= PortalUtil.getCanonicalURL(redirect, themeDisplay, layout) %>" />
			<aui:input name="assetEntryVisible" type="hidden" value="<%= assetEntryVisible %>" />
			<aui:input name="className" type="hidden" value="<%= className %>" />
			<aui:input name="classPK" type="hidden" value="<%= classPK %>" />
			<aui:input name="permissionClassName" type="hidden" value="<%= permissionClassName %>" />
			<aui:input name="permissionClassPK" type="hidden" value="<%= permissionClassPK %>" />
			<aui:input name="permissionOwnerId" type="hidden" value="<%= String.valueOf(userId) %>" />
			<aui:input name="messageId" type="hidden" />
			<aui:input name="threadId" type="hidden" value="<%= commentSectionDisplay.getThreadId() %>" />
			<aui:input name="parentMessageId" type="hidden" />
			<aui:input name="body" type="hidden" />
			<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_PUBLISH) %>" />
			<aui:input name="ajax" type="hidden" value="<%= true %>" />

			<%
			int i = 0;
			%>

			<c:if test="<%= !hideControls && commentSectionDisplay.hasAddPermission() %>">
				<aui:fieldset cssClass="add-comment" id='<%= randomNamespace + "messageScroll0" %>'>

					<%
					long rootMessageId = commentSectionDisplay.getRootCommentMessageId();
					%>

					<div id="<%= randomNamespace %>messageScroll<%= rootMessageId %>">
						<aui:input name='<%= "messageId" + i %>' type="hidden" value="<%= rootMessageId %>" />
						<aui:input name='<%= "parentMessageId" + i %>' type="hidden" value="<%= rootMessageId %>" />
					</div>

					<%
					String taglibPostReplyURL = "javascript:" + randomNamespace + "showForm('" + randomNamespace + "postReplyForm" + i + "', '" + namespace + randomNamespace + "postReplyBody" + i + "');";
					%>

					<c:choose>
						<c:when test="<%= TrashUtil.isInTrash(className, classPK) %>">
							<div class="alert alert-warning">
								<liferay-ui:message key="commenting-is-disabled-because-this-entry-is-in-the-recycle-bin" />
							</div>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="<%= !commentSectionDisplay.hasComments() %>">
									<liferay-ui:message key="no-comments-yet" /> <a href="<%= taglibPostReplyURL %>"><liferay-ui:message key="be-the-first" /></a>
								</c:when>
								<c:otherwise>
									<liferay-ui:icon
										iconCssClass="icon-reply"
										label="<%= true %>"
										message="add-comment"
										url="<%= taglibPostReplyURL %>"
									/>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>

					<%
					boolean subscribed = SubscriptionLocalServiceUtil.isSubscribed(company.getCompanyId(), user.getUserId(), className, classPK);

					String subscriptionURL = "javascript:" + randomNamespace + "subscribeToComments(" + !subscribed + ");";
					%>

					<c:if test="<%= commentSectionDisplay.isSubscriptionButtonVisible() %>">
						<c:choose>
							<c:when test="<%= subscribed %>">
								<liferay-ui:icon
									cssClass="subscribe-link"
									iconCssClass="icon-remove-sign"
									label="<%= true %>"
									message="unsubscribe-from-comments"
									url="<%= subscriptionURL %>"
								/>
							</c:when>
							<c:otherwise>
								<liferay-ui:icon
									cssClass="subscribe-link"
									iconCssClass="icon-ok-sign"
									label="<%= true %>"
									message="subscribe-to-comments"
									url="<%= subscriptionURL %>"
								/>
							</c:otherwise>
						</c:choose>
					</c:if>

					<aui:input name="emailAddress" type="hidden" />

					<div id="<%= randomNamespace %>postReplyForm<%= i %>" style="display: none;">
						<aui:input id='<%= randomNamespace + "postReplyBody" + i %>' label="comment" name='<%= "postReplyBody" + i %>' type="textarea" wrap="soft" wrapperCssClass="lfr-textarea-container" />

						<%
						String postReplyButtonLabel = LanguageUtil.get(request, "reply");

						if (!themeDisplay.isSignedIn()) {
							postReplyButtonLabel = LanguageUtil.get(request, "reply-as");
						}

						if (commentSectionDisplay.hasWorkflowDefinitionLink() && !strutsAction.contains("workflow")) {
							postReplyButtonLabel = LanguageUtil.get(request, "submit-for-publication");
						}
						%>

						<c:if test="<%= !subscribed && themeDisplay.isSignedIn() %>">
							<aui:input helpMessage="comments-subscribe-me-help" label="subscribe-me" name="subscribe" type="checkbox" value="<%= PropsValues.DISCUSSION_SUBSCRIBE_BY_DEFAULT %>" />
						</c:if>

						<aui:button-row>
							<aui:button cssClass="btn-comment btn-primary" id='<%= namespace + randomNamespace + "postReplyButton" + i %>' onClick='<%= randomNamespace + "postReply(" + i + ");" %>' value="<%= postReplyButtonLabel %>" />

							<%
							String taglibCancel = "document.getElementById('" + randomNamespace + "postReplyForm" + i + "').style.display = 'none'; document.getElementById('" + namespace + randomNamespace + "postReplyBody" + i + "').value = ''; void('');";
							%>

							<aui:button cssClass="btn-comment" onClick="<%= taglibCancel %>" type="cancel" />
						</aui:button-row>
					</div>
				</aui:fieldset>
			</c:if>

			<c:if test="<%= commentSectionDisplay.hasComments() %>">
				<a name="<%= randomNamespace %>messages_top"></a>

				<c:if test="<%= commentSectionDisplay.isThreadedRepliesVisible() %>">
					<table class="table table-bordered table-hover table-striped tree-walker">
						<thead class="table-columns">
							<tr>
								<th class="table-header" colspan="2">
									<liferay-ui:message key="threaded-replies" />
								</th>
								<th class="table-header" colspan="2">
									<liferay-ui:message key="author" />
								</th>
								<th class="table-header">
									<liferay-ui:message key="date" />
								</th>
							</tr>
						</thead>
						<tbody class="table-data">

						<%
						for (CommentTreeNodeDisplay commentTreeNodeDisplay : commentSectionDisplay.getCommentTreeNodeDisplays()) {
							request.setAttribute(DiscussionWebKeys.COMMENT_TREE_NODE_DISPLAY, commentTreeNodeDisplay);
						%>

							<liferay-util:include page="/html/taglib/ui/discussion/view_message_thread.jsp" />

						<%
						}
						%>

						</tbody>
					</table>
					<br />
				</c:if>

				<aui:row>

					<%
					List<Comment> comments = commentSectionDisplay.initComments(renderRequest, renderResponse);

					int size = comments.size();

					for (i = 1; i <= size; i++) {
						Comment message = comments.get(i - 1);

						if (!commentSectionDisplay.isVisible(message)) {
							continue;
						}

						String cssClass = StringPool.BLANK;

						if (i == 1) {
							cssClass = "first";
						}
						else if (i == size) {
							cssClass = "last";
						}
					%>

						<div class="lfr-discussion <%= cssClass %>">

							<%
							long commentId = message.getMessageId();
							%>

							<div id="<%= randomNamespace %>messageScroll<%= commentId %>">
								<a name="<%= randomNamespace %>message_<%= commentId %>"></a>

								<aui:input name='<%= "messageId" + i %>' type="hidden" value="<%= commentId %>" />
								<aui:input name='<%= "parentMessageId" + i %>' type="hidden" value="<%= commentId %>" />
							</div>

							<aui:row fluid="<%= true %>">
								<aui:col cssClass="lfr-discussion-details" width="25">
									<liferay-ui:user-display
										displayStyle="2"
										userId="<%= message.getUserId() %>"
										userName="<%= HtmlUtil.escape(message.getUserName()) %>"
									/>
								</aui:col>

								<aui:col cssClass="lfr-discussion-body" width="75">
									<c:if test="<%= commentSectionDisplay.isWorkflowStatusVisible(message) %>">
										<aui:model-context bean="<%= message %>" model="<%= message.getWorkflowStatusModelContextClass() %>" />

										<div>
											<aui:workflow-status model="<%= message.getWorkflowStatusModelClass() %>" status="<%= message.getStatus() %>" />
										</div>
									</c:if>

									<div class="lfr-discussion-message">
										<%= commentSectionDisplay.getBodyFormatted(message) %>
									</div>

									<div class="lfr-discussion-controls">
										<c:if test="<%= commentSectionDisplay.isRatingsVisible(message) %>">
											<liferay-ui:ratings
												className="<%= commentSectionDisplay.getRatingsClassName() %>"
												classPK="<%= message.getRatingsClassPK() %>"
												ratingsEntry="<%= commentSectionDisplay.getRatingsEntry(message) %>"
												ratingsStats="<%= commentSectionDisplay.getRatingsStats(message) %>"
												type="thumbs"
											/>
										</c:if>

										<c:if test="<%= commentSectionDisplay.isDiscussionActionsVisible(message) %>">
											<ul class="lfr-discussion-actions">
												<c:if test="<%= commentSectionDisplay.hasAddPermission() %>">
													<li class="lfr-discussion-reply-to">

														<%
														String taglibPostReplyURL = "javascript:" + randomNamespace + "showForm('" + randomNamespace + "postReplyForm" + i + "', '" + namespace + randomNamespace + "postReplyBody" + i + "'); " + randomNamespace + "hideForm('" + randomNamespace + "editForm" + i + "', '" + namespace + randomNamespace + "editReplyBody" + i + "', '" + HtmlUtil.escapeJS(message.getBody()) + "');";
														%>

														<liferay-ui:icon
															iconCssClass="icon-reply"
															label="<%= true %>"
															message="post-reply"
															url="<%= taglibPostReplyURL %>"
														/>
													</li>
												</c:if>

												<c:if test="<%= i > 0 %>">

													<%
													String taglibTopURL = "#" + randomNamespace + "messages_top";
													%>

													<li class="lfr-discussion-top-link">
														<liferay-ui:icon
															iconCssClass="icon-long-arrow-up"
															label="<%= true %>"
															message="top"
															url="<%= taglibTopURL %>"
														/>
													</li>

													<c:if test="<%= commentSectionDisplay.hasEditPermission(message) %>">

														<%
														String taglibEditURL = "javascript:" + randomNamespace + "showForm('" + randomNamespace + "editForm" + i + "', '" + namespace + randomNamespace + "editReplyBody" + i + "');" + randomNamespace + "hideForm('" + randomNamespace + "postReplyForm" + i + "', '" + namespace + randomNamespace + "postReplyBody" + i + "', '')";
														%>

														<li class="lfr-discussion-edit">
															<liferay-ui:icon
																iconCssClass="icon-edit"
																label="<%= true %>"
																message="edit"
																url="<%= taglibEditURL %>"
															/>
														</li>
													</c:if>

													<c:if test="<%= commentSectionDisplay.hasDeletePermission(message) %>">

														<%
														String taglibDeleteURL = "javascript:" + randomNamespace + "deleteMessage(" + i + ");";
														%>

														<li class="lfr-discussion-delete">
															<liferay-ui:icon-delete
																label="<%= true %>"
																url="<%= taglibDeleteURL %>"
															/>
														</li>
													</c:if>
												</c:if>
											</ul>
										</c:if>
									</div>
								</aui:col>
							</aui:row>

							<aui:row cssClass="lfr-discussion-form-container" fluid="<%= true %>">
								<div class="col-md-12 lfr-discussion-form lfr-discussion-form-reply" id="<%= randomNamespace %>postReplyForm<%= i %>" style='<%= "display: none; max-width: " + ModelHintsConstants.TEXTAREA_DISPLAY_WIDTH + "px;" %>'>

									<liferay-ui:user-display
										displayStyle="2"
										userId="<%= user.getUserId() %>"
										userName="<%= HtmlUtil.escape(PortalUtil.getUserName(user.getUserId(), StringPool.BLANK)) %>"
									/>

									<aui:input id='<%= randomNamespace + "postReplyBody" + i %>' label="" name='<%= "postReplyBody" + i %>' style='<%= "height: " + ModelHintsConstants.TEXTAREA_DISPLAY_HEIGHT + "px;" %>' title="reply-body" type="textarea" wrap="soft" />

									<aui:button-row>
										<aui:button cssClass="btn-comment btn-primary" id='<%= namespace + randomNamespace + "postReplyButton" + i %>' onClick='<%= randomNamespace + "postReply(" + i + ");" %>' value='<%= themeDisplay.isSignedIn() ? "reply" : "reply-as" %>' />

										<%
										String taglibCancel = randomNamespace + "hideForm('" + randomNamespace + "postReplyForm" + i + "', '" + namespace + randomNamespace + "postReplyBody" + i + "', '');";
										%>

										<aui:button cssClass="btn-comment" onClick="<%= taglibCancel %>" type="cancel" />
									</aui:button-row>
								</div>

								<c:if test="<%= !hideControls && commentSectionDisplay.hasEditPermission(message) %>">
									<div class="col-md-12 lfr-discussion-form lfr-discussion-form-edit" id="<%= randomNamespace %>editForm<%= i %>" style='<%= "display: none; max-width: " + ModelHintsConstants.TEXTAREA_DISPLAY_WIDTH + "px;" %>'>
										<aui:input id='<%= randomNamespace + "editReplyBody" + i %>' label="" name='<%= "editReplyBody" + i %>' style='<%= "height: " + ModelHintsConstants.TEXTAREA_DISPLAY_HEIGHT + "px;" %>' title="reply-body" type="textarea" value="<%= message.getBody() %>" wrap="soft" />

										<%
										String publishButtonLabel = LanguageUtil.get(request, "publish");

										if (commentSectionDisplay.hasWorkflowDefinitionLink()) {
											if (message.isPending()) {
												publishButtonLabel = "save";
											}
											else {
												publishButtonLabel = LanguageUtil.get(request, "submit-for-publication");
											}
										}
										%>

										<aui:button-row>
											<aui:button name='<%= randomNamespace + "editReplyButton" + i %>' onClick='<%= randomNamespace + "updateMessage(" + i + ");" %>' value="<%= publishButtonLabel %>" />

											<%
											String taglibCancel = randomNamespace + "hideForm('" + randomNamespace + "editForm" + i + "', '" + namespace + randomNamespace + "editReplyBody" + i + "', '" + HtmlUtil.escapeJS(message.getBody()) + "');";
											%>

											<aui:button onClick="<%= taglibCancel %>" type="cancel" />
										</aui:button-row>
									</div>
								</c:if>
							</aui:row>

							<div class="lfr-discussion-posted-on">
								<c:choose>
									<c:when test="<%= commentSectionDisplay.isTopChild(message) %>">
										<%= LanguageUtil.format(request, "posted-on-x", dateFormatDateTime.format(message.getModifiedDate()), false) %>
									</c:when>
									<c:otherwise>

										<%
										Comment parentMessage = commentSectionDisplay.getParentComment(message);
										%>

										<liferay-util:buffer var="buffer">

											<%
											User parentMessageUser = UserLocalServiceUtil.fetchUser(parentMessage.getUserId());

											boolean male = (parentMessageUser == null) ? true : parentMessageUser.isMale();
											long portraitId = (parentMessageUser == null) ? 0 : parentMessageUser.getPortraitId();
											String userUuid = (parentMessageUser == null) ? null : parentMessageUser.getUserUuid();
											%>

											<span id="lfr-discussion-reply-user-info">
												<div class="lfr-discussion-reply-user-avatar">
													<img alt="<%= HtmlUtil.escapeAttribute(parentMessage.getUserName()) %>" class="user-status-avatar-image" src="<%= UserConstants.getPortraitURL(themeDisplay.getPathImage(), male, portraitId, userUuid) %>" width="30" />
												</div>

												<div class="lfr-discussion-reply-user-name">
														<%= parentMessage.getUserName() %>
												</div>

												<div class="lfr-discussion-reply-creation-date">
													<%= dateFormatDateTime.format(parentMessage.getCreateDate()) %>
												</div>
											</span>
										</liferay-util:buffer>

										<%
										StringBundler sb = new StringBundler(7);

										sb.append("<a class=\"lfr-discussion-parent-link\" data-title=\"");
										sb.append(HtmlUtil.escape(buffer));
										sb.append("\"data-metaData=\"");
										sb.append(HtmlUtil.escape(parentMessage.getBody()));
										sb.append("\">");
										sb.append(HtmlUtil.escape(parentMessage.getUserName()));
										sb.append("</a>");
										%>

										<%= LanguageUtil.format(request, "posted-on-x-in-reply-to-x", new Object[] {dateFormatDateTime.format(message.getModifiedDate()), sb.toString()}, false) %>
									</c:otherwise>
								</c:choose>
							</div>
						</div>

					<%
					}
					%>

				</aui:row>

				<c:if test="<%= commentSectionDisplay.isSearchPaginatorVisible() %>">
					<liferay-ui:search-paginator searchContainer="<%= commentSectionDisplay.getSearchContainer() %>" />
				</c:if>
			</c:if>
		</aui:form>
	</div>

	<%
	PortletURL loginURL = PortletURLFactoryUtil.create(request, PortletKeys.FAST_LOGIN, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

	loginURL.setParameter("saveLastPath", Boolean.FALSE.toString());
	loginURL.setParameter("struts_action", "/login/login");
	loginURL.setPortletMode(PortletMode.VIEW);
	loginURL.setWindowState(LiferayWindowState.POP_UP);
	%>

	<aui:script>
		function <%= randomNamespace %>hideForm(rowId, textAreaId, textAreaValue) {
			document.getElementById(rowId).style.display = 'none';
			document.getElementById(textAreaId).value = textAreaValue;
		}

		function <%= randomNamespace %>scrollIntoView(messageId) {
			document.getElementById('<%= randomNamespace %>messageScroll' + messageId).scrollIntoView();
		}

		function <%= randomNamespace %>showForm(rowId, textAreaId) {
			document.getElementById(rowId).style.display = 'block';
			document.getElementById(textAreaId).focus();
		}

		Liferay.provide(
			window,
			'<%= randomNamespace %>afterLogin',
			function(emailAddress, anonymousAccount) {
				var A = AUI();

				var form = A.one('#<%= namespace %><%= HtmlUtil.escapeJS(formName) %>');

				form.one('#<%= namespace %>emailAddress').val(emailAddress);

				<portlet:namespace />sendMessage(form, !anonymousAccount);
			},
			['aui-base']
		);

		Liferay.provide(
			window,
			'<%= randomNamespace %>deleteMessage',
			function(i) {
				var A = AUI();

				var form = A.one('#<%= namespace %><%= HtmlUtil.escapeJS(formName) %>');

				var messageId = form.one('#<%= namespace %>messageId' + i).val();

				form.one('#<%= namespace %><%= randomNamespace %><%= Constants.CMD %>').val('<%= Constants.DELETE %>');
				form.one('#<%= namespace %>messageId').val(messageId);

				<portlet:namespace />sendMessage(form);
			},
			['aui-base']
		);

		Liferay.provide(
			window,
			'<portlet:namespace />onMessagePosted',
			function(response, refreshPage) {
				Liferay.after(
					'<%= portletDisplay.getId() %>:portletRefreshed',
					function(event) {
						var A = AUI();

						<portlet:namespace />showStatusMessage('success', '<%= UnicodeLanguageUtil.get(request, "your-request-processed-successfully") %>');

						location.hash = '#' + A.one('#<portlet:namespace />randomNamespace').val() + 'message_' + response.messageId;
					}
				);

				if (refreshPage) {
					window.location.reload();
				}
				else {
					Liferay.Portlet.refresh('#p_p_id_<%= portletDisplay.getId() %>_');
				}
			},
			['aui-base']
		);

		Liferay.provide(
			window,
			'<%= randomNamespace %>postReply',
			function(i) {
				var A = AUI();

				var form = A.one('#<%= namespace %><%= HtmlUtil.escapeJS(formName) %>');

				var body = form.one('#<%= namespace %><%= randomNamespace%>postReplyBody' + i).val();
				var parentMessageId = form.one('#<%= namespace %>parentMessageId' + i).val();

				form.one('#<%= namespace %><%= randomNamespace %><%= Constants.CMD %>').val('<%= Constants.ADD %>');
				form.one('#<%= namespace %>parentMessageId').val(parentMessageId);
				form.one('#<%= namespace %>body').val(body);

				if (!themeDisplay.isSignedIn()) {
					window.namespace = '<%= namespace %>';
					window.randomNamespace = '<%= randomNamespace %>';

					Liferay.Util.openWindow(
						{
							dialog: {
								height: 460,
								width: 770
							},
							id: '<%= namespace %>signInDialog',
							title: '<%= UnicodeLanguageUtil.get(request, "sign-in") %>',
							uri: '<%= loginURL.toString() %>'
						}
					);
				}
				else {
					<portlet:namespace />sendMessage(form);
				}
			},
			['aui-base']
		);

		Liferay.provide(
			window,
			'<portlet:namespace />sendMessage',
			function(form, refreshPage) {
				var A = AUI();

				var Util = Liferay.Util;

				form = A.one(form);

				var commentButtonList = form.all('.btn-comment');

				A.io.request(
					form.attr('action'),
					{
						dataType: 'JSON',
						form: {
							id: form
						},
						on: {
							complete: function(event, id, obj) {
								Util.toggleDisabled(commentButtonList, false);
							},
							failure: function(event, id, obj) {
								<portlet:namespace />showStatusMessage('error', '<%= UnicodeLanguageUtil.get(request, "your-request-failed-to-complete") %>');
							},
							start: function() {
								Util.toggleDisabled(commentButtonList, true);
							},
							success: function(event, id, obj) {
								var response = this.get('responseData');

								var exception = response.exception;

								if (!exception) {
									Liferay.after(
										'<%= portletDisplay.getId() %>:messagePosted',
										function(event) {
											<portlet:namespace />onMessagePosted(response, refreshPage);
										}
									);

									Liferay.fire('<%= portletDisplay.getId() %>:messagePosted', response);
								}
								else {
									var errorKey = '<%= UnicodeLanguageUtil.get(request, "your-request-failed-to-complete") %>';

									if (exception.indexOf('MessageBodyException') > -1) {
										errorKey = '<%= UnicodeLanguageUtil.get(request, "please-enter-a-valid-message") %>';
									}
									else if (exception.indexOf('NoSuchMessageException') > -1) {
										errorKey = '<%= UnicodeLanguageUtil.get(request, "the-message-could-not-be-found") %>';
									}
									else if (exception.indexOf('PrincipalException') > -1) {
										errorKey = '<%= UnicodeLanguageUtil.get(request, "you-do-not-have-the-required-permissions") %>';
									}
									else if (exception.indexOf('RequiredMessageException') > -1) {
										errorKey = '<%= UnicodeLanguageUtil.get(request, "you-cannot-delete-a-root-message-that-has-more-than-one-immediate-reply") %>';
									}

									<portlet:namespace />showStatusMessage('error', errorKey);
								}
							}
						}
					}
				);
			},
			['aui-io']
		);

		Liferay.provide(
			window,
			'<portlet:namespace />showStatusMessage',
			function(type, message) {
				var A = AUI();

				var messageContainer = A.one('#<portlet:namespace />discussion-status-messages');

				messageContainer.removeClass('alert-danger').removeClass('alert-success');

				messageContainer.addClass('alert alert-' + type);

				messageContainer.html(message);

				messageContainer.show();
			},
			['aui-base']
		);

		Liferay.provide(
			window,
			'<%= randomNamespace %>subscribeToComments',
			function(subscribe) {
				var A = AUI();

				var form = A.one('#<%= namespace %><%= HtmlUtil.escapeJS(formName) %>');

				var cmd = form.one('#<%= namespace %><%= randomNamespace %><%= Constants.CMD %>');

				var cmdVal = '<%= Constants.UNSUBSCRIBE_FROM_COMMENTS %>';

				if (subscribe) {
					cmdVal = '<%= Constants.SUBSCRIBE_TO_COMMENTS %>';
				}

				cmd.val(cmdVal);

				<portlet:namespace />sendMessage(form);
			},
			['aui-base']
		);

		Liferay.provide(
			window,
			'<%= randomNamespace %>updateMessage',
			function(i, pending) {
				var A = AUI();

				var form = A.one('#<%= namespace %><%= HtmlUtil.escapeJS(formName) %>');

				var body = form.one('#<%= namespace %><%= randomNamespace%>editReplyBody' + i).val();
				var messageId = form.one('#<%= namespace %>messageId' + i).val();

				if (pending) {
					form.one('#<%= namespace %>workflowAction').val('<%= WorkflowConstants.ACTION_SAVE_DRAFT %>');
				}

				form.one('#<%= namespace %><%= randomNamespace %><%= Constants.CMD %>').val('<%= Constants.UPDATE %>');
				form.one('#<%= namespace %>messageId').val(messageId);
				form.one('#<%= namespace %>body').val(body);

				<portlet:namespace />sendMessage(form);
			},
			['aui-base']
		);
	</aui:script>

	<aui:script use="aui-popover,event-outside">
		var discussionContainer = A.one('#<portlet:namespace />discussion-container');

		var popover = new A.Popover(
			{
				constrain: true,
				cssClass: 'lfr-discussion-reply',
				position: 'top',
				visible: false,
				width: 400,
				zIndex: Liferay.zIndex.TOOLTIP
			}
		).render(discussionContainer);

		var handle;

		var boundingBox = popover.get('boundingBox');

		discussionContainer.delegate(
			'click',
			function(event) {
				event.stopPropagation();

				if (handle) {
					handle.detach();

					handle = null;
				}

				handle = boundingBox.once('clickoutside', popover.hide, popover);

				popover.hide();

				var currentTarget = event.currentTarget;

				popover.set('align.node', currentTarget);
				popover.set('bodyContent', currentTarget.attr('data-metaData'));
				popover.set('headerContent', currentTarget.attr('data-title'));

				popover.show();
			},
			'.lfr-discussion-parent-link'
		);
	</aui:script>
</c:if>