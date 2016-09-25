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

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
public class FileResource {

	public FileResource(
		DLAppService dlAppService, UriBuilder fileUriBuilder,
		FileEntry fileEntry) {

		_dlAppService = dlAppService;
		_fileUriBuilder = fileUriBuilder;
		_fileEntry = fileEntry;
	}

	@GET
	@Path("/")
	@Produces({"application/json", "application/xml"})
	public FileRepr getFileEntry() {
		return FileRepr.fromFileEntry(_fileEntry, _fileUriBuilder);
	}

	@GET
	@Path("/content")
	public Response getFileEntryContent() throws PortalException {
		return Response.
			status(200).
			type(_fileEntry.getMimeType()).
			entity(_fileEntry.getContentStream()).build();
	}

	@Path("/content")
	@PUT
	public FileRepr updateFile(
			@Context HttpServletRequest httpServletRequest, byte[] bytes,
			@QueryParam("changelog") String changelog,
			@QueryParam("majorVersion") boolean majorVersion)
		throws PortalException {

		changelog = GetterUtil.get(changelog, StringPool.BLANK);

		return FileRepr.fromFileEntry(
			_dlAppService.updateFileEntry(
				_fileEntry.getFileEntryId(), _fileEntry.getFileName(),
				httpServletRequest.getContentType(), _fileEntry.getTitle(),
				_fileEntry.getDescription(), changelog, majorVersion, bytes,
				new ServiceContext()), _fileUriBuilder);
	}

	@Consumes("multipart/form-data")
	@PUT
	public FileRepr updateMetadata(
			@Multipart("content") Attachment attachment,
			@Multipart("metadata") FileRepr fileRepr,
			@Multipart(value = "changelog", required = false) String changelog,
			@Multipart(value = "majorVersion", required = false)
				boolean majorVersion)
		throws PortalException {

		if ((fileRepr == null) || (attachment == null)) {
			throw new WebApplicationException(
				Response.
					status(400).
					entity("Request is not properly encoded").build());
		}

		changelog = GetterUtil.getString(changelog);

		return FileRepr.fromFileEntry(
			_dlAppService.updateFileEntry(
				_fileEntry.getFileEntryId(), fileRepr.getFileName(),
				attachment.getContentType().getType(), fileRepr.getTitle(),
				fileRepr.getDescription(), changelog, majorVersion,
				attachment.getObject(byte[].class), new ServiceContext()),
			_fileUriBuilder);
	}

	@Consumes({"application/json", "application/xml"})
	@PUT
	public FileRepr updateMetadata(
			FileRepr fileRepr, @QueryParam("changelog") String changelog,
			@QueryParam("majorVersion") boolean majorVersion)
		throws PortalException {

		return FileRepr.fromFileEntry(
			_dlAppService.updateFileEntry(
				_fileEntry.getFileEntryId(), fileRepr.getFileName(),
				_fileEntry.getMimeType(), fileRepr.getTitle(),
				fileRepr.getDescription(), changelog, majorVersion,
				_fileEntry.getContentStream(), _fileEntry.getSize(),
				new ServiceContext()), _fileUriBuilder);
	}

	private final DLAppService _dlAppService;
	private final FileEntry _fileEntry;
	private final UriBuilder _fileUriBuilder;

}