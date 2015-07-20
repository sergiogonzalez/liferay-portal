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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.document.library.web.util.DLBreadcrumbUtil" %><%@
page import="com.liferay.portal.InvalidRepositoryException" %><%@
page import="com.liferay.portal.NoSuchRepositoryException" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.RowChecker" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.lock.DuplicateLockException" %><%@
page import="com.liferay.portal.kernel.log.Log" %><%@
page import="com.liferay.portal.kernel.log.LogFactoryUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.repository.LocalRepository" %><%@
page import="com.liferay.portal.kernel.repository.RepositoryException" %><%@
page import="com.liferay.portal.kernel.repository.RepositoryProviderUtil" %><%@
page import="com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability" %><%@
page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portal.kernel.search.Field" %><%@
page import="com.liferay.portal.kernel.search.Hits" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.search.SearchContext" %><%@
page import="com.liferay.portal.kernel.search.SearchContextFactory" %><%@
page import="com.liferay.portal.kernel.search.SearchResult" %><%@
page import="com.liferay.portal.kernel.search.SearchResultUtil" %><%@
page import="com.liferay.portal.kernel.search.Summary" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem" %><%@
page import="com.liferay.portal.kernel.upload.LiferayFileItemException" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.HttpUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.MathUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsPropsUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsKeys" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.UnicodeFormatter" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.model.Group" %><%@
page import="com.liferay.portal.model.Portlet" %><%@
page import="com.liferay.portal.model.User" %><%@
page import="com.liferay.portal.repository.registry.RepositoryClassDefinition" %><%@
page import="com.liferay.portal.repository.registry.RepositoryClassDefinitionCatalogUtil" %><%@
page import="com.liferay.portal.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.upload.LiferayFileItem" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.portal.util.WebKeys" %><%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.portlet.admin.util.PortalAdministrationApplicationType" %><%@
page import="com.liferay.portlet.asset.model.AssetEntry" %><%@
page import="com.liferay.portlet.asset.model.AssetRenderer" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryServiceUtil" %><%@
page import="com.liferay.portlet.asset.util.AssetUtil" %><%@
page import="com.liferay.portlet.documentlibrary.DLGroupServiceSettings" %><%@
page import="com.liferay.portlet.documentlibrary.DLPortletInstanceSettings" %><%@
page import="com.liferay.portlet.documentlibrary.DuplicateFileEntryTypeException" %><%@
page import="com.liferay.portlet.documentlibrary.DuplicateFolderNameException" %><%@
page import="com.liferay.portlet.documentlibrary.DuplicateRepositoryNameException" %><%@
page import="com.liferay.portlet.documentlibrary.FileMimeTypeException" %><%@
page import="com.liferay.portlet.documentlibrary.FileShortcutPermissionException" %><%@
page import="com.liferay.portlet.documentlibrary.FolderNameException" %><%@
page import="com.liferay.portlet.documentlibrary.InvalidFileVersionException" %><%@
page import="com.liferay.portlet.documentlibrary.InvalidFolderException" %><%@
page import="com.liferay.portlet.documentlibrary.NoSuchFileEntryException" %><%@
page import="com.liferay.portlet.documentlibrary.NoSuchFolderException" %><%@
page import="com.liferay.portlet.documentlibrary.NoSuchMetadataSetException" %><%@
page import="com.liferay.portlet.documentlibrary.RepositoryNameException" %><%@
page import="com.liferay.portlet.documentlibrary.RequiredFileEntryTypeException" %><%@
page import="com.liferay.portlet.documentlibrary.SourceFileNameException" %><%@
page import="com.liferay.portlet.documentlibrary.display.context.DLDisplayContextProviderUtil" %><%@
page import="com.liferay.portlet.documentlibrary.display.context.DLEditFileEntryDisplayContext" %><%@
page import="com.liferay.portlet.documentlibrary.display.context.DLFilePicker" %><%@
page import="com.liferay.portlet.documentlibrary.display.context.DLViewFileVersionDisplayContext" %><%@
page import="com.liferay.portlet.documentlibrary.display.context.logic.DLPortletInstanceSettingsHelper" %><%@
page import="com.liferay.portlet.documentlibrary.display.context.logic.DLVisualizationHelper" %><%@
page import="com.liferay.portlet.documentlibrary.display.context.util.DLRequestHelper" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntryType" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileShortcutConstants" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileVersion" %><%@
page import="com.liferay.portlet.documentlibrary.search.EntriesChecker" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLFileEntryTypeServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLFileEntryTypePermission" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLFileShortcutPermission" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission" %><%@
page import="com.liferay.portlet.documentlibrary.service.permission.DLPermission" %><%@
page import="com.liferay.portlet.documentlibrary.util.AudioProcessorUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.DL" %><%@
page import="com.liferay.portlet.documentlibrary.util.DLConstants" %><%@
page import="com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.ImageProcessorUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.PDFProcessorUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.RawMetadataProcessor" %><%@
page import="com.liferay.portlet.documentlibrary.util.VideoProcessorUtil" %><%@
page import="com.liferay.portlet.documentlibrarydisplay.display.context.util.DLDisplayRequestHelper" %><%@
page import="com.liferay.portlet.dynamicdatamapping.DDMStructure" %><%@
page import="com.liferay.portlet.dynamicdatamapping.DDMStructureManager" %><%@
page import="com.liferay.portlet.dynamicdatamapping.DDMStructureManagerUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StorageEngineManagerUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureDefinitionException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureDuplicateElementException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StructureNameException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.search.StructureSearch" %><%@
page import="com.liferay.portlet.dynamicdatamapping.search.StructureSearchTerms" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues" %><%@
page import="com.liferay.portlet.imagegallerydisplay.display.context.IGDisplayContextProviderUtil" %><%@
page import="com.liferay.portlet.imagegallerydisplay.display.context.IGViewFileVersionDisplayContext" %><%@
page import="com.liferay.portlet.imagegallerydisplay.display.context.util.IGRequestHelper" %><%@
page import="com.liferay.portlet.imagegallerydisplay.util.IGUtil" %><%@
page import="com.liferay.portlet.trash.util.TrashUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Date" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<portlet:defineObjects />

<liferay-theme:defineObjects />

<%
WindowState windowState = liferayPortletRequest.getWindowState();

PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);

String currentURL = currentURLObj.toString();

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/init-ext.jsp" %>