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

package com.liferay.document.library.web.social;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;
import com.liferay.social.kernel.model.BaseSocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.social.kernel.model.SocialActivityInterpreter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ryan Park
 * @author Zsolt Berentey
 */
@Component(
	property = {"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY},
	service = SocialActivityInterpreter.class
)
public class DLFileEntryActivityInterpreter
	extends BaseSocialActivityInterpreter {

	@Override
	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getBody(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(
			activity.getClassPK());

		if (fileEntry.getModel() instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			if (dlFileEntry.isInTrash()) {
				return StringPool.BLANK;
			}
		}

		StringBundler sb = new StringBundler(3);

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				DLFileEntry.class.getName());

		AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(
			fileEntry.getFileEntryId());

		String fileEntryLink = assetRenderer.getURLDownload(
			serviceContext.getThemeDisplay());

		sb.append(wrapLink(fileEntryLink, "download-file", serviceContext));

		sb.append(StringPool.SPACE);

		String folderLink = getFolderLink(fileEntry, serviceContext);

		folderLink = addNoSuchEntryRedirect(
			folderLink, DLFolder.class.getName(), fileEntry.getFolderId(),
			serviceContext);

		sb.append(wrapLink(folderLink, "go-to-folder", serviceContext));

		return sb.toString();
	}

	protected String getFolderLink(
		FileEntry fileEntry, ServiceContext serviceContext) {

		StringBundler sb = new StringBundler(6);

		sb.append(serviceContext.getPortalURL());
		sb.append(serviceContext.getPathMain());
		sb.append("/document_library/find_folder?groupId=");
		sb.append(fileEntry.getRepositoryId());
		sb.append("&folderId=");
		sb.append(fileEntry.getFolderId());

		return sb.toString();
	}

	@Override
	protected String getPath(
		SocialActivity activity, ServiceContext serviceContext) {

		return "/document_library/find_file_entry?fileEntryId=" +
			activity.getClassPK();
	}

	@Override
	protected ResourceBundleLoader getResourceBundleLoader() {
		return _resourceBundleLoader;
	}

	@Override
	protected Object[] getTitleArguments(
			String groupName, SocialActivity activity, String link,
			String title, ServiceContext serviceContext)
		throws Exception {

		if (activity.getType() == SocialActivityConstants.TYPE_ADD_COMMENT) {
			String creatorUserName = getUserName(
				activity.getUserId(), serviceContext);
			String receiverUserName = getUserName(
				activity.getReceiverUserId(), serviceContext);

			return new Object[] {
				groupName, creatorUserName, receiverUserName,
				wrapLink(link, title)
			};
		}
		else {
			return super.getTitleArguments(
				groupName, activity, link, title, serviceContext);
		}
	}

	@Override
	protected String getTitlePattern(
		String groupName, SocialActivity activity) {

		int activityType = activity.getType();

		if (activityType == DLActivityKeys.ADD_FILE_ENTRY) {
			if (Validator.isNull(groupName)) {
				return "activity-document-library-file-add-file";
			}
			else {
				return "activity-document-library-file-add-file-in";
			}
		}
		else if (activityType == DLActivityKeys.UPDATE_FILE_ENTRY) {
			if (Validator.isNull(groupName)) {
				return "activity-document-library-file-update-file";
			}
			else {
				return "activity-document-library-file-update-file-in";
			}
		}
		else if (activityType == SocialActivityConstants.TYPE_ADD_COMMENT) {
			if (Validator.isNull(groupName)) {
				return "activity-document-library-file-add-comment";
			}
			else {
				return "activity-document-library-file-add-comment-in";
			}
		}
		else if (activityType == SocialActivityConstants.TYPE_MOVE_TO_TRASH) {
			if (Validator.isNull(groupName)) {
				return "activity-document-library-file-move-to-trash";
			}
			else {
				return "activity-document-library-file-move-to-trash-in";
			}
		}
		else if (activityType ==
					SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) {

			if (Validator.isNull(groupName)) {
				return "activity-document-library-file-restore-from-trash";
			}
			else {
				return "activity-document-library-file-restore-from-trash-in";
			}
		}

		return null;
	}

	@Override
	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ServiceContext serviceContext)
		throws Exception {

		return DLFileEntryPermission.contains(
			permissionChecker, activity.getClassPK(), actionId);
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.document.library.web)",
		unbind = "-"
	)
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = new AggregateResourceBundleLoader(
			resourceBundleLoader,
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
	}

	private static final String[] _CLASS_NAMES = {DLFileEntry.class.getName()};

	private DLAppLocalService _dlAppLocalService;
	private ResourceBundleLoader _resourceBundleLoader;

}