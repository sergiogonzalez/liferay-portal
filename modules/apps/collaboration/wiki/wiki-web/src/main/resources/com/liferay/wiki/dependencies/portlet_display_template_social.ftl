<#assign wikiPageClassName = "com.liferay.wiki.model.WikiPage" />

<#assign assetRenderer = assetEntry.getAssetRenderer() />

<div class="taglib-header">
	<h1 class="header-title">${entry.getTitle()}</h1>
</div>

<div style="float: right;">
	<@getAddChildPageIcon />

	<@getEditIcon />

	<@getPageDetailsIcon />

	<@getPrintIcon />
</div>

<div class="wiki-body">
	<div class="wiki-info">
		<span class="stats">${assetEntry.getViewCount()} <@liferay.language key="views" /></span> |

		<span class="date"><@liferay.language key="last-modified" /> ${dateUtil.getDate(entry.getModifiedDate(), "dd MMM yyyy - HH:mm:ss", locale)}</span>

		<span class="author"><@liferay.language key="by" /> ${htmlUtil.escape(portalUtil.getUserName(entry.getUserId(), entry.getUserName()))}</span>
	</div>

	<div class="wiki-content">
		<@liferay_ui["social-bookmarks"]
			displayStyle="horizontal"
			target="_blank"
			title=entry.getTitle()
			url=viewURL
		/>

		${formattedContent}
	</div>

	<div class="page-actions">
		<div class="page-categorization">
			<div class="page-categories">
				<#assign viewCategorizedPagesURL = renderResponse.createRenderURL() />

				${viewCategorizedPagesURL.setParameter("mvcRenderCommandName", "/wiki/view_categorized_pages")}
				${viewCategorizedPagesURL.setParameter("nodeId", entry.getNodeId()?string)}

				<@liferay_ui["asset-categories-summary"]
					className=wikiPageClassName
					classPK=entry.getResourcePrimKey()
					portletURL=viewCategorizedPagesURL
				/>
			</div>

			<div class="page-tags">
				<#assign viewTaggedPagesURL = renderResponse.createRenderURL() />

				${viewTaggedPagesURL.setParameter("mvcRenderCommandName", "/wiki/view_tagged_pages")}
				${viewTaggedPagesURL.setParameter("nodeId", entry.getNodeId()?string)}

				<@liferay_ui["asset-tags-summary"]
					className=wikiPageClassName
					classPK=entry.getResourcePrimKey()
					portletURL=viewTaggedPagesURL
				/>
			</div>
		</div>

		<#if wikiPortletInstanceOverriddenConfiguration.enablePageRatings()>
			<div class="page-attachments">
				<@liferay_ui["ratings"]
					className=wikiPageClassName
					classPK=entry.getResourcePrimKey()
				/>
			</div>
		</#if>

		<div class="page-attachments">
			<h5><@liferay.language key="attachments" /></h5>

			<div class="row">
				<#assign attachments = entry.getAttachmentsFileEntries() />

				<#list attachments as attachment>
					<#assign rowURL = portletFileRepositoryUtil.getDownloadPortletFileEntryURL(themeDisplay, attachment, "status=" + 0) />

					<div class="col-md-4">
					   <div class="control-label">
							<div class="card card-horizontal">
								<div class="card-row card-row-padded">
									<div class="card-col-field">
										<span class="icon-monospaced file-icon-color-0">${stringUtil.shorten(stringUtil.upperCase(attachment.getExtension()), 3, "")}</span>
									</div>
									<div class="card-col-content card-col-gutters clamp-horizontal">
										<div class="clamp-container">
											<span class="h4 truncate-text" title="${htmlUtil.escape(attachment.getTitle())}">
												<a href="${rowURL}">${htmlUtil.escape(attachment.getTitle())}" </a>
											</span>
										</div>
									</div>
								</div>
							</div>
						 </div>
					</div>
				</#list>
			</div>
		</div>

		<div class="entry-links">
			<@getRelatedAssets />
		</div>
	</div>
</div>

<#assign childPages = entry.getChildPages() />

<#if (childPages?has_content)>
	<div class="child-pages">
		<h2><@liferay.language key="children-pages" /></h2>

		<table class="taglib-search-iterator">
			<tr class="portlet-section-header results-header">
				<th>
					<@liferay.language key="page" />
				</th>
				<th>
					<@liferay.language key="last-modified" />
				</th>
				<th>
					<@liferay.language key="ratings" />
				</th>
			</tr>

			<#list childPages as childPage>
				<tr class="results-row">
					<#assign viewPageURL = renderResponse.createRenderURL() />

					${viewPageURL.setParameter("mvcRenderCommandName", "/wiki/view")}

					<#assign childNode = childPage.getNode() />

					${viewPageURL.setParameter("nodeName", childNode.getName())}
					${viewPageURL.setParameter("title", childPage.getTitle())}

					<td>
						<a href="${viewPageURL}">${childPage.getTitle()}</a>
					</td>
					<td>
						<a href="${viewPageURL}">${dateUtil.getDate(childPage.getModifiedDate(),"dd MMM yyyy - HH:mm:ss", locale)} <@liferay.language key="by" /> ${htmlUtil.escape(portalUtil.getUserName(childPage.getUserId(), childPage.getUserName()))}</a>
					</td>
					<td>
						<@getRatings
							cssClass="child-ratings"
							entry=childPage
						/>
					</td>
				</tr>
			</#list>
		</table>
	</div>
</#if>

<@getDiscussion />

<#macro getAddChildPageIcon>
	<#if assetRenderer.hasEditPermission(themeDisplay.getPermissionChecker())>
		<#assign addPageURL = renderResponse.createRenderURL() />

		${addPageURL.setParameter("mvcRenderCommandName", "/wiki/edit_page")}
		${addPageURL.setParameter("redirect", currentURL)}
		${addPageURL.setParameter("nodeId", entry.getNodeId()?string)}
		${addPageURL.setParameter("title", "")}
		${addPageURL.setParameter("editTitle", "1")}
		${addPageURL.setParameter("parentTitle", entry.getTitle())}

		<@liferay_ui["icon"]
			iconCssClass="icon-plus"
			label=true
			message="add-child-page"
			url=addPageURL?string
		/>
	</#if>
</#macro>

<#macro getDiscussion>
	<#if validator.isNotNull(assetRenderer.getDiscussionPath()) && wikiPortletInstanceOverriddenConfiguration.enableComments()>
		<br />

		<#assign discussionURL = renderResponse.createActionURL() />

		${discussionURL.setParameter("javax.portlet.action", "/wiki/" + assetRenderer.getDiscussionPath())}

		<@liferay_ui["discussion"]
			className=wikiPageClassName
			classPK=entry.getResourcePrimKey()
			formAction=discussionURL?string
			formName="fm2"
			ratingsEnabled=wikiPortletInstanceOverriddenConfiguration.enableCommentRatings()
			redirect=currentURL
			subject=assetRenderer.getTitle(locale)
			userId=assetRenderer.getUserId()
		/>
	</#if>
</#macro>

<#macro getEditIcon>
	<#if assetRenderer.hasEditPermission(themeDisplay.getPermissionChecker())>
		<#assign editPageURL = renderResponse.createRenderURL() />

		${editPageURL.setParameter("mvcRenderCommandName", "/wiki/edit_page")}
		${editPageURL.setParameter("redirect", currentURL)}
		${editPageURL.setParameter("nodeId", entry.getNodeId()?string)}
		${editPageURL.setParameter("title", entry.getTitle())}

		<@liferay_ui["icon"]
			iconCssClass="icon-edit"
			label=true
			message="edit"
			url=editPageURL?string
		/>
	</#if>
</#macro>

<#macro getPageDetailsIcon>
	<#assign viewPageDetailsURL = renderResponse.createRenderURL() />

	${viewPageDetailsURL.setParameter("mvcRenderCommandName", "/wiki/view_page_details")}
	${viewPageDetailsURL.setParameter("redirect", currentURL)}

	<@liferay_ui["icon"]
		iconCssClass="icon-file-alt"
		label=true
		message="details"
		url=viewPageDetailsURL?string
	/>
</#macro>

<#macro getPrintIcon>
	<#assign printURL = renderResponse.createRenderURL() />

	${printURL.setParameter("viewMode", "print")}
	${printURL.setWindowState("pop_up")}

	<#assign title = languageUtil.format(locale, "print-x-x", ["hide-accessible", htmlUtil.escape(assetRenderer.getTitle(locale))], false) />
	<#assign taglibPrintURL = "javascript:Liferay.Util.openWindow({dialog: {width: 960}, id:'" + renderResponse.getNamespace() + "printAsset', title: '" + title + "', uri: '" + htmlUtil.escapeURL(printURL.toString()) + "'});" />

	<@liferay_ui["icon"]
		iconCssClass="icon-print"
		label=true
		message="print"
		url=taglibPrintURL
	/>
</#macro>

<#macro getRelatedAssets>
	<#if assetEntry?? && wikiPortletInstanceOverriddenConfiguration.enableRelatedAssets()>
		<@liferay_ui["asset-links"]
			assetEntryId=assetEntry.getEntryId()
		/>
	</#if>
</#macro>