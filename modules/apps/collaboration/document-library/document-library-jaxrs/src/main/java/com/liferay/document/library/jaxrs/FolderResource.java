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

package com.liferay.document.library.jaxrs;

import com.liferay.document.library.jaxrs.provider.OrderByComparatorSelectorUtil;
import com.liferay.document.library.jaxrs.provider.OrderBySelector;
import com.liferay.document.library.jaxrs.provider.PageContainer;
import com.liferay.document.library.jaxrs.provider.Pagination;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelTitleComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

/**
 * @author Carlos Sierra Andrés
 */
public class FolderResource {

	public FolderResource(
		DLAppService dlAppService, final Folder folder,
		UriBuilder folderUriBuilder, UriBuilder fileUriBuilder) {

		_dlAppService = dlAppService;
		_folderUriBuilder = folderUriBuilder;
		_fileUriBuilder = fileUriBuilder;
		_folderId = folder.getFolderId();

		_repositoryId = folder.getRepositoryId();
		_folderReprFunction = contents -> FolderRepr.fromFolder(
			folder, contents, _folderUriBuilder);
	}

	public FolderResource(
		DLAppService dlAppService, final long groupId,
		final Repository repository, UriBuilder repositoryUriBuilder,
		UriBuilder folderUriBuilder, UriBuilder fileUriBuilder) {

		_dlAppService = dlAppService;
		_folderUriBuilder = folderUriBuilder;
		_fileUriBuilder = fileUriBuilder;

		_repositoryId = repository.getRepositoryId();
		_folderId = 0;
		_folderReprFunction = contents -> FolderRepr.fromRepository(
			groupId, repository, contents, repositoryUriBuilder);
	}

	@POST
	public FileRepr addFile(
			@Multipart("metadata") FileRepr fileRepr,
			@Multipart("content") Attachment attachment,
			@Multipart(value = "changelog", required = false) String changelog)
		throws PortalException {

		if ((fileRepr == null) || (attachment == null)) {
			throw new WebApplicationException(
				Response.
					status(400).
					entity("Request is not properly built" +
						"We expect a multipart/form-data request with" +
						"metadata and content fields").build());
		}

		changelog = GetterUtil.getString(changelog);

		return FileRepr.fromFileEntry(
			_dlAppService.addFileEntry(
				_repositoryId, _folderId, fileRepr.getFileName(),
				attachment.getContentType().getType(), fileRepr.getTitle(),
				fileRepr.getDescription(), changelog,
				attachment.getObject(byte[].class), new ServiceContext()),
			_fileUriBuilder);
	}

	@Path("/{fileName}")
	@POST
	public FileRepr addFile(
			@Context HttpServletRequest httpServletRequest, byte[] content,
			@PathParam("fileName") String fileName,
			@QueryParam("changelog") String changelog,
			@QueryParam("title") String title,
			@QueryParam("description") String description)
		throws PortalException {

		if ((fileName == null) || (content == null)) {
			throw new WebApplicationException(
				Response.
					status(400).
					entity("Request is not properly built").build());
		}

		changelog = GetterUtil.getString(changelog);
		title = GetterUtil.getString(title, fileName);
		description = GetterUtil.getString(description, fileName);

		return FileRepr.fromFileEntry(
			_dlAppService.addFileEntry(
				_repositoryId, _folderId, fileName,
				httpServletRequest.getContentType(), title, description,
				changelog, content, new ServiceContext()), _fileUriBuilder);
	}

	@GET
	@Produces({"application/json", "application/xml"})
	public PageContainer<FolderRepr> getFolder(
			@Context Pagination pagination,
			@Context OrderBySelector orderBySelector)
		throws PortalException {

		OrderByComparator<Object> orderByComparator =
			OrderByComparatorSelectorUtil.select(
				orderBySelector, RepositoryContentObject.comparators).
				orElse(new RepositoryModelTitleComparator<>());

		List<RepositoryContentObject> repositoryContentObjects =
			ListUtil.toList(
				_dlAppService.getFoldersAndFileEntriesAndFileShortcuts(
					_repositoryId, _folderId, 0, true,
					pagination.getStartPosition(), pagination.getEndPosition(),
					orderByComparator),
				this::toObjectRepository);

		return pagination.createContainer(
			_folderReprFunction.apply(repositoryContentObjects),
			_dlAppService.getFoldersAndFileEntriesAndFileShortcutsCount(
				_repositoryId, _folderId, 0, true));
	}

	public Function<List<RepositoryContentObject>, FolderRepr>
		_folderReprFunction;

	protected RepositoryContentObject toObjectRepository(Object rco) {
		if (rco instanceof FileEntry) {
			FileEntry fileEntry = (FileEntry)rco;

			String url = _fileUriBuilder.
				build(fileEntry.getFileEntryId()).toString();

			return new RepositoryContentObject(
				fileEntry.getFileEntryId(), fileEntry.getTitle(), url,
				RepositoryContentObject.RepositoryContentType.FILE,
				fileEntry.getCreateDate(), fileEntry.getModifiedDate());
		}
		else if (rco instanceof Folder) {
			Folder folder = (Folder)rco;

			String url = _folderUriBuilder.
				build(Long.toString(folder.getFolderId())).toString();

			return new RepositoryContentObject(
				folder.getFolderId(), folder.getName(), url,
				RepositoryContentObject.RepositoryContentType.FOLDER,
				folder.getCreateDate(), folder.getModifiedDate());
		}
		else if (rco instanceof FileShortcut) {
			FileShortcut fileShortcut = (FileShortcut)rco;

			String url = _fileUriBuilder.
				build(Long.toString(fileShortcut.getToFileEntryId())).
				toString();

			return new RepositoryContentObject(
				fileShortcut.getFileShortcutId(), fileShortcut.getToTitle(),
				url, RepositoryContentObject.RepositoryContentType.SHORTCUT,
				fileShortcut.getCreateDate(), fileShortcut.getModifiedDate());
		}
		else {
			throw new IllegalArgumentException(
				"Object must be an instance of FileEntry, Folder of " +
					"FileShortcut");
		}
	}

	private final DLAppService _dlAppService;
	private final UriBuilder _fileUriBuilder;
	private final long _folderId;
	private final UriBuilder _folderUriBuilder;
	private final long _repositoryId;

}