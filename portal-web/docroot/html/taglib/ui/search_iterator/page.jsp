<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

boolean paginate = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:search-iterator:paginate"));
String type = (String)request.getAttribute("liferay-ui:search:type");

String id = searchContainer.getId(request, namespace);

int start = searchContainer.getStart();
int end = searchContainer.getEnd();
int total = searchContainer.getTotal();
List resultRows = searchContainer.getResultRows();
List<String> headerNames = searchContainer.getHeaderNames();
List<String> normalizedHeaderNames = searchContainer.getNormalizedHeaderNames();
Map orderableHeaders = searchContainer.getOrderableHeaders();
String emptyResultsMessage = searchContainer.getEmptyResultsMessage();
RowChecker rowChecker = searchContainer.getRowChecker();

if (end > total) {
	end = total;
}

if (rowChecker != null) {
	if (headerNames != null) {
		headerNames.add(0, rowChecker.getAllRowsCheckBox());

		normalizedHeaderNames.add(0, "rowChecker");
	}
}

String url = StringPool.BLANK;

PortletURL iteratorURL = searchContainer.getIteratorURL();

if (iteratorURL != null) {
	url = iteratorURL.toString();
	url = HttpUtil.removeParameter(url, namespace + searchContainer.getOrderByColParam());
	url = HttpUtil.removeParameter(url, namespace + searchContainer.getOrderByTypeParam());
}

List<String> primaryKeys = new ArrayList<String>();

int sortColumnIndex = -1;
%>

<c:if test="<%= resultRows.isEmpty() && (emptyResultsMessage != null) %>">
	<div class="aui-alert aui-alert-info">
		<%= LanguageUtil.get(pageContext, emptyResultsMessage) %>
	</div>
</c:if>

<div class="lfr-search-container <%= resultRows.isEmpty() ? "aui-hide" : StringPool.BLANK %>">
	<c:if test="<%= PropsValues.SEARCH_CONTAINER_SHOW_PAGINATION_TOP && (resultRows.size() > 10) && paginate %>">
		<div class="taglib-search-iterator-page-iterator-top">
			<liferay-ui:search-paginator id='<%= id + "PageIteratorTop" %>' searchContainer="<%= searchContainer %>" type="<%= type %>" />
		</div>
	</c:if>

	<div id="<%= namespace + id %>SearchContainer">
		<table class="aui-table aui-table-bordered aui-table-hover aui-table-striped">

		<c:if test="<%= headerNames != null %>">
			<thead class="aui-table-columns">
				<tr>

				<%
				for (int i = 0; i < headerNames.size(); i++) {
					String headerName = headerNames.get(i);

					String normalizedHeaderName = null;

					if (i < normalizedHeaderNames.size()) {
						normalizedHeaderName = normalizedHeaderNames.get(i);
					}

					if (Validator.isNull(normalizedHeaderName)) {
						normalizedHeaderName = String.valueOf(i +1);
					}

					String orderKey = null;
					String orderByType = null;
					boolean orderCurrentHeader = false;

					if (orderableHeaders != null) {
						orderKey = (String)orderableHeaders.get(headerName);

						if (orderKey != null) {
							orderByType = searchContainer.getOrderByType();

							if (orderKey.equals(searchContainer.getOrderByCol())) {
								orderCurrentHeader = true;
							}
						}
					}

					String cssClass = StringPool.BLANK;

					if (headerNames.size() == 1) {
						cssClass = "only";
					}
					else if (i == 0) {
						cssClass = "aui-table-first-header";
					}
					else if (i == (headerNames.size() - 1)) {
						cssClass = "aui-table-last-header";
					}

					if (orderCurrentHeader) {
						cssClass += " aui-table-sortable-column aui-table-sorted";

						if (HtmlUtil.escapeAttribute(orderByType).equals("desc")) {
							cssClass += " aui-table-sorted-desc";
						}

						sortColumnIndex = i;

						if (orderByType.equals("asc")) {
							orderByType = "desc";
						}
						else {
							orderByType = "asc";
						}
					}
				%>

					<th class="aui-table-header <%= cssClass %>" id="<%= namespace + id %>_col-<%= normalizedHeaderName %>"

						<%--

						// Maximize the width of the second column if and only if the first
						// column is a row checker and there is only one second column.

						--%>

						<c:if test="<%= (rowChecker != null) && (headerNames.size() == 2) && (i == 1) %>">
							width="95%"
						</c:if>
					>

						<c:if test="<%= orderKey != null %>">
							<div class="aui-table-sort-liner">


								<%
								String orderByJS = searchContainer.getOrderByJS();
								%>

								<c:choose>
									<c:when test="<%= Validator.isNull(orderByJS) %>">

										<%
										url = HttpUtil.setParameter(url, namespace + searchContainer.getOrderByColParam(), orderKey);
										url = HttpUtil.setParameter(url, namespace + searchContainer.getOrderByTypeParam(), orderByType);
										%>

										<a href="<%= url %>">
									</c:when>
									<c:otherwise>
										<a href="<%= StringUtil.replace(orderByJS, new String[] { "orderKey", "orderByType" }, new String[] { orderKey, orderByType }) %>">
									</c:otherwise>
								</c:choose>
						</c:if>

							<%
							String headerNameValue = null;

							if ((rowChecker == null) || (i > 0)) {
								headerNameValue = LanguageUtil.get(pageContext, HtmlUtil.escape(headerName));
							}
							else {
								headerNameValue = headerName;
							}
							%>

							<c:choose>
								<c:when test="<%= Validator.isNull(headerNameValue) %>">
									<%= StringPool.NBSP %>
								</c:when>
								<c:otherwise>
									<%= headerNameValue %>
								</c:otherwise>
							</c:choose>

						<c:if test="<%= orderKey != null %>">
								</a>

								<span class="aui-table-sort-indicator"></span>
							</div>
						</c:if>
					</th>

				<%
				}
				%>

				</tr>
			</thead>
		</c:if>

		<tbody class="aui-table-data">

		<c:if test="<%= resultRows.isEmpty() && (emptyResultsMessage != null) %>">
			<tr>
				<td class="aui-table-cell">
					<%= LanguageUtil.get(pageContext, emptyResultsMessage) %>
				</td>
			</tr>
		</c:if>

		<%
		boolean allRowsIsChecked = true;

		for (int i = 0; i < resultRows.size(); i++) {
			ResultRow row = (ResultRow)resultRows.get(i);

			primaryKeys.add(HtmlUtil.escape(row.getPrimaryKey()));

			request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW, row);

			List entries = row.getEntries();

			if (rowChecker != null) {
				boolean rowIsChecked = rowChecker.isChecked(row.getObject());
				boolean rowIsDisabled = rowChecker.isDisabled(row.getObject());

				if (!rowIsChecked) {
					allRowsIsChecked = false;
				}

				TextSearchEntry textSearchEntry = new TextSearchEntry();

				textSearchEntry.setAlign(rowChecker.getAlign());
				textSearchEntry.setColspan(rowChecker.getColspan());
				textSearchEntry.setCssClass(rowChecker.getCssClass());
				textSearchEntry.setName(rowChecker.getRowCheckBox(request, rowIsChecked, rowIsDisabled, row.getPrimaryKey()));
				textSearchEntry.setValign(rowChecker.getValign());

				row.addSearchEntry(0, textSearchEntry);
			}

			request.setAttribute("liferay-ui:search-container-row:rowId", id.concat(StringPool.UNDERLINE.concat(row.getRowId())));

			Map<String, Object> data = row.getData();
		%>

			<tr <%= AUIUtil.buildData(data) %>>

			<%
			for (int j = 0; j < entries.size(); j++) {
				SearchEntry entry = (SearchEntry)entries.get(j);

				String normalizedHeaderName = null;

				if ((normalizedHeaderNames != null) && (j < normalizedHeaderNames.size())) {
					normalizedHeaderName = normalizedHeaderNames.get(j);
				}

				if (Validator.isNull(normalizedHeaderName)) {
					normalizedHeaderName = String.valueOf(j + 1);
				}

				entry.setIndex(j);

				request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY, entry);

				String columnClassName = entry.getCssClass();

				if (entries.size() == 1) {
					columnClassName += " only";
				}
				else if (j == 0) {
					columnClassName += " first";
				}
				else if ((j + 1) == entries.size()) {
					columnClassName += " last";
				}

				if (j == sortColumnIndex) {
					columnClassName += " aui-table-sortable-column";
				}
			%>

				<td class="aui-table-cell">

					<%
					entry.print(pageContext);
					%>

				</td>

			<%
			}
			%>

			</tr>

		<%
			request.removeAttribute("liferay-ui:search-container-row:rowId");
		}
		%>

		<c:if test="<%= headerNames != null %>">
			<tr class="lfr-template">

				<%
				for (int i = 0; i < headerNames.size(); i++) {
				%>

					<td class="aui-table-cell"></td>

				<%
				}
				%>

			</tr>
		</c:if>

		</tbody>
		</table>
	</div>

	<c:if test="<%= PropsValues.SEARCH_CONTAINER_SHOW_PAGINATION_BOTTOM && paginate %>">
		<div class="taglib-search-iterator-page-iterator-bottom">
			<liferay-ui:search-paginator id='<%= id + "PageIteratorBottom" %>' searchContainer="<%= searchContainer %>" type="<%= type %>" />
		</div>
	</c:if>
</div>

<c:if test="<%= (rowChecker != null) && !resultRows.isEmpty() && Validator.isNotNull(rowChecker.getAllRowsId()) && allRowsIsChecked %>">
	<aui:script>
		document.<%= rowChecker.getFormName() %>.<%= rowChecker.getAllRowsId() %>.checked = true;
	</aui:script>
</c:if>

<c:if test="<%= Validator.isNotNull(id) %>">
	<input id="<%= namespace + id %>PrimaryKeys" name="<%= id %>PrimaryKeys" type="hidden" value="<%= StringUtil.merge(primaryKeys) %>" />

	<aui:script use="liferay-search-container">
		new Liferay.SearchContainer(
			{
				classNameHover: '<%= _CLASS_NAME_HOVER %>',
				hover: <%= searchContainer.isHover() %>,
				id: '<%= namespace + id %>',
				rowClassNameAlternate: '<%= _ROW_CLASS_NAME_ALTERNATE %>',
				rowClassNameAlternateHover: '<%= _ROW_CLASS_NAME_ALTERNATE_HOVER %>',
				rowClassNameBody: '<%= _ROW_CLASS_NAME_BODY %>',
				rowClassNameBodyHover: '<%= _ROW_CLASS_NAME_BODY %>'
			}
		).render();
	</aui:script>
</c:if>

<%!
private static final String _CLASS_NAME_HOVER = "hover";

private static final String _ROW_CLASS_NAME_ALTERNATE = "";

private static final String _ROW_CLASS_NAME_ALTERNATE_HOVER = "-hover";

private static final String _ROW_CLASS_NAME_BODY = "";

private static final String _ROW_CLASS_NAME_BODY_HOVER = "-hover";
%>