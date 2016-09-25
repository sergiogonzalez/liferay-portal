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
import com.liferay.document.library.jaxrs.provider.VariantUtil;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.comparator.GroupFriendlyURLComparator;
import com.liferay.portal.kernel.util.comparator.GroupIdComparator;
import com.liferay.portal.kernel.util.comparator.GroupNameComparator;
import com.liferay.portal.kernel.util.comparator.GroupTypeComparator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Variant;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Api
@Component(immediate = true, service = DocumentLibraryRootResource.class)
@Path("/")
public class DocumentLibraryRootResource {

	public static final HashMap<
		String, Function<Boolean, OrderByComparator<Group>>> COMPARATORS =
		new HashMap<String, Function<Boolean, OrderByComparator<Group>>>() { {
			put("name", GroupNameComparator::new);
			put("id", GroupIdComparator::new);
			put("type", GroupTypeComparator::new);
			put("url", GroupFriendlyURLComparator::new);
		}};

	@Path("/objects/files/{fileId}")
	public FileResource getFileResource(@PathParam("fileId") long fileId)
		throws PortalException {

		return new FileResource(
			dlAppService, uriInfo.getRequestUriBuilder(),
			dlAppService.getFileEntry(fileId));
	}

	@Path("/objects/folders/{folderId}")
	public FolderResource getFolderResource(
			@PathParam("folderId") long folderId)
		throws PortalException {

		UriBuilder baseUriBuilder = uriInfo.getBaseUriBuilder();

		return new FolderResource(
			dlAppService, dlAppService.getFolder(folderId),
			baseUriBuilder.clone().path(
				DocumentLibraryRootResource.class, "getFolderResource"),
			baseUriBuilder.clone().path(
				DocumentLibraryRootResource.class, "getFileResource"));
	}

	@Path("/{groupId}")
	public DocumentLibraryGroupResource getGroupResource(
		@PathParam("groupId") long groupId) {

		return new DocumentLibraryGroupResource(groupId);
	}

	@ApiOperation(
		value = "List groups", responseContainer = "List",
		response = GroupRepr.class
	)
	@GET
	@Produces({"application/json", "application/xml"})
	public PageContainer<List<GroupRepr>> listGroups(
			@Context Company company, @Context OrderBySelector orderBySelector,
			@Context Pagination pagination, @Context Request request,
			@Context UriInfo uriInfo)
		throws PortalException {

		Variant.VariantListBuilder variantListBuilder =
			Variant.VariantListBuilder.newInstance();

		variantListBuilder = variantListBuilder.languages(
			LanguageUtil.getAvailableLocales().toArray(new Locale[0])).add();

		List<Variant> variants = variantListBuilder.build();

		Locale locale =
			VariantUtil.safeSelectVariant(request, variants).
				map(Variant::getLanguage).orElse(LocaleUtil.getDefault());

		OrderByComparator<Group> groupOrderByComparator =
			OrderByComparatorSelectorUtil.select(
				orderBySelector, COMPARATORS).orElseGet(GroupIdComparator::new);

		List<Group> userSitesGroups = groupService.getUserSitesGroups();

		int maxSize =
			pagination.getEndPosition() - pagination.getStartPosition();

		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("{groupId}");

		return pagination.createContainer(
			userSitesGroups.
				stream().
				skip(pagination.getStartPosition()).
				limit(maxSize).
				sorted(groupOrderByComparator).
				map(group ->
					new GroupRepr(
						group.getGroupId(),
						uriBuilder.build(group.getGroupId()).toString(),
						group.getName(locale, true), group.getFriendlyURL(),
						group.getType())).
				collect(Collectors.toList()),
			userSitesGroups.size());
	}

	/**
	 * @author Carlos Sierra Andrés
	 */
	public class DocumentLibraryGroupResource {

		public DocumentLibraryGroupResource(long groupId) {
			_groupId = groupId;
		}

		@GET
		@Path("/")
		@Produces({"application/json", "application/xml"})
		public List<RepositoryRepr> getDefaultRepositoryHandler()
			throws PortalException {

			return ListUtil.toList(
				repositoryProvider.getGroupRepositories(_groupId),
				r -> RepositoryRepr.fromRepository(
					r, uriInfo.getRequestUriBuilder().path("{id}")));
		}

		@Path("/{repositoryId}")
		public FolderResource getRepositoryHandler(
				@PathParam("repositoryId") long repositoryId)
			throws PortalException {

			UriBuilder baseUriBuilder = uriInfo.getBaseUriBuilder();

			return new FolderResource(
				dlAppService, _groupId,
				repositoryProvider.getRepository(repositoryId),
					uriInfo.getRequestUriBuilder(),
				baseUriBuilder.clone().path(
					DocumentLibraryRootResource.class, "getFolderResource"),
				baseUriBuilder.clone().path(
					DocumentLibraryRootResource.class, "getFileResource"));
		}

		private final long _groupId;

	}

	@Reference
	protected DLAppService dlAppService;

	@Reference
	protected GroupService groupService;

	@Reference
	protected RepositoryProvider repositoryProvider;

	@Context
	protected UriInfo uriInfo;

}