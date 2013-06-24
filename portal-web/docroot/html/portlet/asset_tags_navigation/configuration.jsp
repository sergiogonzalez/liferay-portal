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

<%@ include file="/html/portlet/asset_tags_navigation/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<aui:fieldset>
		<ul class="lfr-tree unstyled">
			<li class="tree-item">
				<aui:input label="show-tags-with-zero-assets" name="preferences--showZeroAssetCount--" type="checkbox" value="<%= showZeroAssetCount %>" />
			</li>

			<li class="tree-item">
				<aui:input name="preferences--showAssetCount--" type="checkbox" value="<%= showAssetCount %>" />

				<ul class="lfr-tree hide unstyled" id="<portlet:namespace />assetCountOptions">
					<li class="tree-item">
						<aui:select helpMessage="asset-type-asset-count-help" label="asset-type" name="preferences--classNameId--">
							<aui:option label="any" value="<%= classNameId == 0 %>" />

							<%
							List<AssetRendererFactory> assetRendererFactories = AssetRendererFactoryRegistryUtil.getAssetRendererFactories(company.getCompanyId());

							for (AssetRendererFactory assetRendererFactory : assetRendererFactories) {
							%>

								<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, assetRendererFactory.getClassName()) %>" selected="<%= classNameId == assetRendererFactory.getClassNameId() %>" value="<%= assetRendererFactory.getClassNameId() %>" />

							<%
							}
							%>

						</aui:select>
					</li>
					<li class="tree-item">

						<aui:fieldset>
							<aui:select label="scope" name="preferences--assetScopeType--">
								<aui:option label="default" selected="<%= Validator.isNull(assetScopeType) %>" value="" />
								<aui:option label="global" selected='<%= assetScopeType.equals("company") %>' value="company" />
								<aui:option label="select-layout" selected='<%= assetScopeType.equals("layout") %>' value="layout" />
							</aui:select>

							<div id="<portlet:namespace />assetScopeLayoutUuidContainer">
								<aui:select label="scope-layout"  name="preferences--assetScopeLayoutUuid--">

									<%
									for (Layout curLayout : LayoutLocalServiceUtil.getScopeGroupLayouts(layout.getGroupId(), layout.isPrivateLayout())) {
									%>

										<aui:option label="<%= HtmlUtil.escape(curLayout.getName(locale)) %>" selected="<%= assetScopeLayoutUuid.equals(curLayout.getUuid()) %>" value="<%= curLayout.getUuid() %>" />

									<%
									}
									%>

								</aui:select>
							</div>
						</aui:fieldset>

					</li>
				</ul>
			</li>

			<li class="tree-item">
				<ul class="lfr-tree unstyled" id="<portlet:namespace />displayTemplateSettings">
					<div class="display-template">

						<%
						TemplateHandler templateHandler = TemplateHandlerRegistryUtil.getTemplateHandler(AssetTag.class.getName());

						List<String> displayStyles = new ArrayList<String>();

						displayStyles.add("number");
						displayStyles.add("cloud");
						%>

						<liferay-ui:ddm-template-selector
							classNameId="<%= PortalUtil.getClassNameId(templateHandler.getClassName()) %>"
							displayStyles="<%= displayStyles %>"
							preferenceValue="<%= displayStyle %>"
							refreshURL="<%= currentURL %>"
						/>
					</div>
				</ul>
			</li>

			<li class="tree-item">
				<aui:input label="max-num-of-tags" name="preferences--maxAssetTags--" type="text" value="<%= maxAssetTags %>" />
			</li>
		</ul>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base">
	var showAssetCount = A.one('#<portlet:namespace />showAssetCountCheckbox');

	function showHiddenFields() {
		var assetCountOptions = A.one('#<portlet:namespace />assetCountOptions');

		if (showAssetCount && assetCountOptions) {
			if (showAssetCount.get('checked')) {
				assetCountOptions.show();
			}
			else {
				assetCountOptions.hide();
			}
		}
	}

	showHiddenFields();

	showAssetCount.on('change', showHiddenFields);

	Liferay.Util.toggleSelectBox('<portlet:namespace />assetScopeType', 'layout', '<portlet:namespace />assetScopeLayoutUuidContainer');

</aui:script>