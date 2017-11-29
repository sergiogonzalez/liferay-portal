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

<%@ include file="/html/taglib/init.jsp" %>

<%
String displayStyle = (String)request.getAttribute("liferay-ui:social-bookmarks-settings:displayStyle");
String types = (String)request.getAttribute("liferay-ui:social-bookmarks-settings:types");

String[] displayStyles = PropsUtil.getArray(PropsKeys.SOCIAL_BOOKMARK_DISPLAY_STYLES);

if (Validator.isNull(displayStyle)) {
	displayStyle = displayStyles[0];
}
%>

<aui:fieldset>
	<aui:row cssClass="social-boomarks-options" id="socialBookmarksOptions">
		<aui:col width="<%= 50 %>">
			<aui:select label="display-style" name="preferences--socialBookmarksDisplayStyle--">

				<%
				for (String curDisplayStyle : PropsUtil.getArray(PropsKeys.SOCIAL_BOOKMARK_DISPLAY_STYLES)) {
				%>

					<aui:option label="<%= curDisplayStyle %>" selected="<%= displayStyle.equals(curDisplayStyle) %>" />

				<%
				}
				%>

			</aui:select>
		</aui:col>

		<div class="row">
			<c:if test="<%= Validator.isNotNull(types) %>">
				<aui:field-wrapper label="social-bookmarks">

					<%
					String[] typesArray = StringUtil.split(types);

					for (String type : PropsUtil.getArray(PropsKeys.SOCIAL_BOOKMARK_TYPES)) {
					%>

						<aui:input checked="<%= ArrayUtil.contains(typesArray, type) %>" id='<%= "socialBookmarksTypes" + type %>' ignoreRequestValue="<%= true %>" label="<%= type %>" name="preferences--socialBookmarksTypes--" type="checkbox" value="<%= type %>" />

					<%
					}
					%>

				</aui:field-wrapper>
			</c:if>
		</div>
	</aui:row>
</aui:fieldset>