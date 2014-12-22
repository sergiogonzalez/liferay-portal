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
String redirect = (String)request.getAttribute("liferay-ui:discussion:redirect");
long userId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:discussion:userId"));

MBMessageDisplay messageDisplay = MBMessageLocalServiceUtil.getDiscussionMessageDisplay(userId, scopeGroupId, className, classPK, WorkflowConstants.STATUS_ANY);

MBCategory category = messageDisplay.getCategory();
MBThread thread = messageDisplay.getThread();
MBTreeWalker treeWalker = messageDisplay.getTreeWalker();
MBMessage rootMessage = treeWalker.getRoot();
List<MBMessage> messages = treeWalker.getMessages();
int messagesCount = messages.size();
SearchContainer searchContainer = null;
%>

<div class="hide lfr-message-response" id="<portlet:namespace />discussion-status-messages"></div>

<c:if test="<%= (messagesCount > 1) || MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, permissionClassName, permissionClassPK, userId, ActionKeys.VIEW) %>">
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
			<aui:input name="threadId" type="hidden" value="<%= thread.getThreadId() %>" />
			<aui:input name="parentMessageId" type="hidden" />
			<aui:input name="body" type="hidden" />
			<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_PUBLISH) %>" />
			<aui:input name="ajax" type="hidden" value="<%= true %>" />

			<%
			MBMessage message = rootMessage;
			%>

			<c:if test="<%= !hideControls && MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, permissionClassName, permissionClassPK, userId, ActionKeys.ADD_DISCUSSION) %>">
				<aui:fieldset cssClass="add-comment" id='<%= randomNamespace + "messageScroll0" %>'>
					<div id="<%= randomNamespace %>messageScroll<%= message.getMessageId() %>">
						<aui:input name="messageId0" type="hidden" value="<%= message.getMessageId() %>" />
						<aui:input name="parentMessageId0" type="hidden" value="<%= message.getMessageId() %>" />
					</div>

					<%
					String taglibPostReplyURL = "javascript:" + randomNamespace + "showForm('" + randomNamespace + "postReplyForm0', '" + namespace + randomNamespace + "postReplyBody0');";
					%>

					<c:choose>
						<c:when test="<%= TrashUtil.isInTrash(className, classPK) %>">
							<div class="alert alert-warning">
								<liferay-ui:message key="commenting-is-disabled-because-this-entry-is-in-the-recycle-bin" />
							</div>
						</c:when>
						<c:otherwise>
							<c:if test="<%= messagesCount == 1 %>">
								<c:choose>
									<c:when test="<%= themeDisplay.isSignedIn() || !SSOUtil.isLoginRedirectRequired(themeDisplay.getCompanyId()) %>">
										<liferay-ui:message key="no-comments-yet" /> <a href="<%= taglibPostReplyURL %>"><liferay-ui:message key="be-the-first" /></a>
									</c:when>
									<c:otherwise>
										<liferay-ui:message key="no-comments-yet" /> <a href="<%= themeDisplay.getURLSignIn() %>"><liferay-ui:message key="please-sign-in-to-comment" /></a>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:otherwise>
					</c:choose>

					<%
					boolean subscribed = SubscriptionLocalServiceUtil.isSubscribed(company.getCompanyId(), user.getUserId(), className, classPK);

					String subscriptionURL = "javascript:" + randomNamespace + "subscribeToComments(" + !subscribed + ");";
					%>

					<c:if test="<%= themeDisplay.isSignedIn() && !TrashUtil.isInTrash(className, classPK) %>">
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

					<c:choose>
						<c:when test="<%= themeDisplay.isSignedIn() || !SSOUtil.isLoginRedirectRequired(themeDisplay.getCompanyId()) %>">
							<aui:row fluid="<%= true %>">
								<aui:col cssClass="lfr-discussion-details" width="<%= 25 %>">
									<liferay-ui:user-display
										displayStyle="2"
										userId="<%= user.getUserId() %>"
										userName="<%= user.getFullName() %>"
									/>
								</aui:col>

								<aui:col cssClass="lfr-discussion-body" width="<%= 75 %>">
									<liferay-ui:input-editor contents="" editorImpl="<%= EDITOR_TEXT_IMPL_KEY %>" name="postReplyBody0" placeholder="type-your-comment-here" />

									<aui:input name="postReplyBody0" type="hidden" />

									<aui:button-row>
										<aui:button cssClass="btn-comment btn-primary" id='<%= namespace + randomNamespace + "postReplyButton0" %>' onClick='<%= randomNamespace + "postReply(0);" %>' value='<%= LanguageUtil.get(request, "reply") %>' />
									</aui:button-row>
								</aui:col>
							</aui:row>
						</c:when>
						<c:otherwise>
							<liferay-ui:icon
								iconCssClass="icon-reply"
								label="<%= true %>"
								message="please-sign-in-to-comment"
								url="<%= themeDisplay.getURLSignIn() %>"
							/>
						</c:otherwise>
					</c:choose>
				</aui:fieldset>
			</c:if>

			<c:if test="<%= messagesCount > 1 %>">
				<a name="<%= randomNamespace %>messages_top"></a>

				<aui:row>

					<%
					if (messages != null) {
						messages = ListUtil.copy(messages);

						messages.remove(0);
					}
					else {
						PortletURL currentURLObj = PortletURLUtil.getCurrent(renderRequest, renderResponse);

						searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, currentURLObj, null, null);

						searchContainer.setTotal(messagesCount - 1);

						messages = MBMessageLocalServiceUtil.getThreadRepliesMessages(message.getThreadId(), WorkflowConstants.STATUS_ANY, searchContainer.getStart(), searchContainer.getEnd());

						searchContainer.setResults(messages);
					}

					List<Long> classPKs = new ArrayList<Long>();

					for (MBMessage curMessage : messages) {
						classPKs.add(curMessage.getMessageId());
					}

					List<RatingsEntry> ratingsEntries = RatingsEntryLocalServiceUtil.getEntries(themeDisplay.getUserId(), MBDiscussion.class.getName(), classPKs);
					List<RatingsStats> ratingsStatsList = RatingsStatsLocalServiceUtil.getStats(MBDiscussion.class.getName(), classPKs);

					int[] range = treeWalker.getChildrenRange(rootMessage);

					for (int j = range[0] - 1; j < range[1] - 1; j++) {
						message = (MBMessage)messages.get(j);

						boolean lastChildNode = false;

						if ((j + 1) == range[1]) {
							lastChildNode = true;
						}

						request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
						request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY, category);
						request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, message);
						request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, new Integer(0));
						request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, Boolean.valueOf(lastChildNode));
						request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, rootMessage);
						request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD, thread);

						request.setAttribute("page.jsp-randomNamespace", randomNamespace);
						request.setAttribute("page.jsp-rootMessage", rootMessage);

						request.setAttribute("page.jsp-ratingsEntries", ratingsEntries);
						request.setAttribute("page.jsp-ratingsStatsList", ratingsStatsList);
					%>

						<liferay-util:include page="/html/taglib/ui/discussion/view_message_thread.jsp" />

					<%
					}
					%>

				</aui:row>

				<c:if test="<%= (searchContainer != null) && (searchContainer.getTotal() > searchContainer.getDelta()) %>">
					<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
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
		}

		function <%= randomNamespace %>scrollIntoView(messageId) {
			document.getElementById('<%= randomNamespace %>messageScroll' + messageId).scrollIntoView();
		}

		function <%= randomNamespace %>showForm(rowId, textAreaId) {
			document.getElementById(rowId).style.display = 'block';
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

				var body = window['<%= namespace %>postReplyBody0Editor'].getHTML();
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

				var body = window['<%= namespace %>editReplyBody' + i + 'Editor'].getHTML();
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

<%!
public static final String EDITOR_TEXT_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.blogs.edit_entry.text.jsp";

private RatingsEntry getRatingsEntry(List<RatingsEntry> ratingEntries, long classPK) {
	for (RatingsEntry ratingsEntry : ratingEntries) {
		if (ratingsEntry.getClassPK() == classPK) {
			return ratingsEntry;
		}
	}

	return null;
}

private RatingsStats getRatingsStats(List<RatingsStats> ratingsStatsList, long classPK) {
	for (RatingsStats ratingsStats : ratingsStatsList) {
		if (ratingsStats.getClassPK() == classPK) {
			return ratingsStats;
		}
	}

	return RatingsStatsUtil.create(0);
}
%>