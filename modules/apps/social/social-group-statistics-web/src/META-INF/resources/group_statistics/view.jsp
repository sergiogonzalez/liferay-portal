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

<%@ include file="/group_statistics/init.jsp" %>

<%
int displayActivityCounterNameIndexCount = groupStatisticsPortletInstanceConfiguration.displayActivityCounterName().length;

for (int displayActivityCounterNameIndex = 0; displayActivityCounterNameIndex < displayActivityCounterNameIndexCount; displayActivityCounterNameIndex++) {
	String displayActivityCounterName = groupStatisticsPortletInstanceConfiguration.displayActivityCounterName()[displayActivityCounterNameIndex];//PrefsParamUtil.getString(portletPreferences, request, "displayActivityCounterName" + displayActivityCounterNameIndex);

	if (Validator.isNull(displayActivityCounterName)) {
		continue;
	}

	String chartType = GetterUtil.getString(groupStatisticsPortletInstanceConfiguration.chartType()[displayActivityCounterNameIndex], "area");//PrefsParamUtil.getString(portletPreferences, request, "chartType" + displayActivityCounterNameIndex, "area");
	int chartWidth = GetterUtil.getInteger(groupStatisticsPortletInstanceConfiguration.chartWidth()[displayActivityCounterNameIndex], 35);//PrefsParamUtil.getInteger(portletPreferences, request, "chartWidth" + displayActivityCounterNameIndex, 35);
	String dataRange = GetterUtil.getString(groupStatisticsPortletInstanceConfiguration.dataRange()[displayActivityCounterNameIndex], "year");//PrefsParamUtil.getString(portletPreferences, request, "dataRange" + displayActivityCounterNameIndex, "year");

	List<AssetTag> assetTags = null;

	List<SocialActivityCounter> activityCounters = null;

	String title = LanguageUtil.get(request, "site-statistics") + StringPool.SPACE;

	int dataSize = 0;
	int displayHeight = 80;

	if (chartType.equals("tag-cloud")) {
		if (dataRange.equals("year")) {
			assetTags = AssetTagLocalServiceUtil.getSocialActivityCounterPeriodTags(scopeGroupId, displayActivityCounterName, SocialCounterPeriodUtil.getFirstActivityDayOfYear(), SocialCounterPeriodUtil.getEndPeriod());
		}
		else {
			assetTags = AssetTagLocalServiceUtil.getSocialActivityCounterOffsetTags(scopeGroupId, displayActivityCounterName, -12, 0);
		}

		title = title + LanguageUtil.format(request, "tag-cloud-for-x", LanguageUtil.get(request, "group.statistics.title." + displayActivityCounterName), false);

		dataSize = assetTags.size();
	}
	else {
		if (chartType.equals("pie")) {
			if (dataRange.equals("year")) {
				activityCounters = SocialActivityCounterLocalServiceUtil.getPeriodDistributionActivityCounters(scopeGroupId, displayActivityCounterName, SocialCounterPeriodUtil.getFirstActivityDayOfYear(), SocialCounterPeriodUtil.getEndPeriod());
			}
			else {
				activityCounters = SocialActivityCounterLocalServiceUtil.getOffsetDistributionActivityCounters(scopeGroupId, displayActivityCounterName, -12, 0);
			}

			displayHeight = Math.max((activityCounters.size() + 1) * 18, displayHeight);
		}
		else {
			if (dataRange.equals("year")) {
				activityCounters = SocialActivityCounterLocalServiceUtil.getPeriodActivityCounters(scopeGroupId, displayActivityCounterName, SocialCounterPeriodUtil.getFirstActivityDayOfYear(), SocialCounterPeriodUtil.getEndPeriod());
			}
			else {
				activityCounters = SocialActivityCounterLocalServiceUtil.getOffsetActivityCounters(scopeGroupId, displayActivityCounterName, -12, 0);
			}
		}

		dataSize = activityCounters.size();

		title = title + LanguageUtil.get(request, "group.statistics.title." + displayActivityCounterName);
	}

	if (dataSize == 0) {
		displayHeight = 40;
	}
%>

	<div class="group-statistics-container">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id='<%= "groupStatisticsPanel" + displayActivityCounterNameIndex %>' persistState="<%= true %>" title="<%= title %>">
			<div class="chart-<%= HtmlUtil.escapeAttribute(chartType) %> group-statistics-body" style="min-height: <%= displayHeight %>px;">
				<c:choose>
					<c:when test="<%= dataSize > 0 %>">
						<c:choose>
							<c:when test='<%= chartType.equals("pie") %>'>
								<%@ include file="/group_statistics/chart/pie.jspf" %>
							</c:when>
							<c:when test='<%= chartType.equals("tag-cloud") %>'>
								<%@ include file="/group_statistics/chart/tag_cloud.jspf" %>
							</c:when>
							<c:otherwise>
								<%@ include file="/group_statistics/chart/other.jspf" %>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="alert alert-info portlet-configuration">
							<liferay-ui:message key="there-is-not-enough-data-to-display-for-this-counter" />
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</liferay-ui:panel>
	</div>

<%
}
%>

<c:if test="<%= displayActivityCounterNameIndexCount == 0 %>">
	<div class="alert alert-info portlet-configuration">
		<a href="<%= portletDisplay.getURLConfiguration() %>" onClick="<%= portletDisplay.getURLConfigurationJS() %>">
			<liferay-ui:message key="please-configure-this-portlet-and-select-at-least-one-activity-counter" />
		</a>
	</div>
</c:if>