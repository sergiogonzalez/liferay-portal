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

package com.liferay.blogs.lar;

import com.liferay.blogs.exportimport.content.processor.BlogsEntryExportImportContentProcessor;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Image;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ImageLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalService;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.io.InputStream;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zsolt Berentey
 * @author Roberto Díaz
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class BlogsEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<BlogsEntry> {

	public static final String[] CLASS_NAMES = {BlogsEntry.class.getName()};

	@Override
	public void deleteStagedModel(BlogsEntry entry) throws PortalException {
		_blogsEntryLocalService.deleteEntry(entry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		BlogsEntry entry = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (entry != null) {
			deleteStagedModel(entry);
		}
	}

	@Override
	public BlogsEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _blogsEntryLocalService.fetchBlogsEntryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<BlogsEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _blogsEntryLocalService.getBlogsEntriesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<BlogsEntry>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(BlogsEntry entry) {
		return entry.getTitle();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, BlogsEntry entry)
		throws Exception {

		Element entryElement = portletDataContext.getExportDataElement(entry);

		if (entry.isSmallImage()) {
			Image smallImage = _imageLocalService.fetchImage(
				entry.getSmallImageId());

			if (Validator.isNotNull(entry.getSmallImageURL())) {
				String smallImageURL =
					_blogsEntryExportImportContentProcessor.
						replaceExportContentReferences(
							portletDataContext, entry,
							entry.getSmallImageURL() + StringPool.SPACE, true,
							true);

				entry.setSmallImageURL(smallImageURL);
			}
			else if (smallImage != null) {
				String smallImagePath = ExportImportPathUtil.getModelPath(
					entry,
					smallImage.getImageId() + StringPool.PERIOD +
						smallImage.getType());

				entryElement.addAttribute("small-image-path", smallImagePath);

				entry.setSmallImageType(smallImage.getType());

				portletDataContext.addZipEntry(
					smallImagePath, smallImage.getTextObj());
			}
		}

		if (entry.getSmallImageFileEntryId() != 0) {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				entry.getSmallImageFileEntryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, entry, fileEntry,
				PortletDataContext.REFERENCE_TYPE_WEAK);
		}

		if (entry.getCoverImageFileEntryId() != 0) {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				entry.getCoverImageFileEntryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, entry, fileEntry,
				PortletDataContext.REFERENCE_TYPE_WEAK);
		}

		String content =
			_blogsEntryExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, entry, entry.getContent(),
					portletDataContext.getBooleanParameter(
						"blogs", "referenced-content"),
					true);

		entry.setContent(content);

		portletDataContext.addClassedModel(
			entryElement, ExportImportPathUtil.getModelPath(entry), entry);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long entryId)
		throws Exception {

		BlogsEntry existingEntry = fetchMissingReference(uuid, groupId);

		if (existingEntry == null) {
			return;
		}

		Map<Long, Long> entryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				BlogsEntry.class);

		entryIds.put(entryId, existingEntry.getEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, BlogsEntry entry)
		throws Exception {

		long userId = portletDataContext.getUserId(entry.getUserUuid());

		Element entryElement =
			portletDataContext.getImportDataStagedModelElement(entry);

		String content =
			_blogsEntryExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, entry, entry.getContent());

		entry.setContent(content);

		Calendar displayDateCal = CalendarFactoryUtil.getCalendar();

		displayDateCal.setTime(entry.getDisplayDate());

		int displayDateMonth = displayDateCal.get(Calendar.MONTH);
		int displayDateDay = displayDateCal.get(Calendar.DATE);
		int displayDateYear = displayDateCal.get(Calendar.YEAR);
		int displayDateHour = displayDateCal.get(Calendar.HOUR);
		int displayDateMinute = displayDateCal.get(Calendar.MINUTE);

		if (displayDateCal.get(Calendar.AM_PM) == Calendar.PM) {
			displayDateHour += 12;
		}

		boolean allowPingbacks = entry.isAllowPingbacks();
		boolean allowTrackbacks = entry.isAllowTrackbacks();
		String[] trackbacks = StringUtil.split(entry.getTrackbacks());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			entry);

		BlogsEntry importedEntry = null;

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setAttribute("urlTitle", entry.getUrlTitle());

			BlogsEntry existingEntry = fetchStagedModelByUuidAndGroupId(
				entry.getUuid(), portletDataContext.getScopeGroupId());

			if (existingEntry == null) {
				serviceContext.setUuid(entry.getUuid());

				importedEntry = _blogsEntryLocalService.addEntry(
					userId, entry.getTitle(), entry.getSubtitle(),
					entry.getDescription(), entry.getContent(),
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, allowPingbacks,
					allowTrackbacks, trackbacks, entry.getCoverImageCaption(),
					null, null, serviceContext);
			}
			else {
				importedEntry = _blogsEntryLocalService.updateEntry(
					userId, existingEntry.getEntryId(), entry.getTitle(),
					entry.getSubtitle(), entry.getDescription(),
					entry.getContent(), displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					allowPingbacks, allowTrackbacks, trackbacks,
					entry.getCoverImageCaption(), new ImageSelector(),
					new ImageSelector(), serviceContext);
			}
		}
		else {
			importedEntry = _blogsEntryLocalService.addEntry(
				userId, entry.getTitle(), entry.getSubtitle(),
				entry.getDescription(), entry.getContent(), displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
				entry.getCoverImageCaption(), null, null, serviceContext);
		}

		if ((entry.getCoverImageFileEntryId() == 0) &&
			(entry.getSmallImageFileEntryId() == 0) &&
			Validator.isNull(entry.getCoverImageURL()) &&
			Validator.isNull(entry.getSmallImageURL()) &&
			!entry.isSmallImage()) {

			portletDataContext.importClassedModel(entry, importedEntry);

			return;
		}

		long smallImageFileEntryId = 0;

		// Image

		if (entry.isSmallImage()) {
			String smallImagePath = entryElement.attributeValue(
				"small-image-path");

			if (Validator.isNotNull(entry.getSmallImageURL())) {
				String smallImageURL =
					_blogsEntryExportImportContentProcessor.
						replaceImportContentReferences(
							portletDataContext, entry,
							entry.getSmallImageURL());

				entry.setSmallImageURL(smallImageURL);
			}
			else if (Validator.isNotNull(smallImagePath)) {
				String smallImageFileName =
					entry.getSmallImageId() + StringPool.PERIOD +
						entry.getSmallImageType();

				InputStream inputStream = null;

				try {
					inputStream = portletDataContext.getZipEntryAsInputStream(
						smallImagePath);

					ImageSelector imageSelector = new ImageSelector(
						FileUtil.getBytes(inputStream), smallImageFileName,
						MimeTypesUtil.getContentType(smallImageFileName), null);

					smallImageFileEntryId =
						_blogsEntryLocalService.addSmallImageFileEntry(
							userId, portletDataContext.getScopeGroupId(),
							importedEntry.getEntryId(), imageSelector);
				}
				finally {
					StreamUtil.cleanUp(inputStream);
				}
			}
		}

		// FileEntries

		long coverImageFileEntryId = 0;

		FileEntry oldSmallImageFileEntry = null;

		if (entry.isSmallImage() && (entry.getSmallImageFileEntryId() != 0)) {
			oldSmallImageFileEntry =
				PortletFileRepositoryUtil.getPortletFileEntry(
					entry.getSmallImageFileEntryId());
		}

		FileEntry oldCoverImageFileEntry = null;

		if (entry.getCoverImageFileEntryId() != 0) {
			oldCoverImageFileEntry =
				PortletFileRepositoryUtil.getPortletFileEntry(
					entry.getCoverImageFileEntryId());
		}

		List<Element> attachmentElements =
			portletDataContext.getReferenceDataElements(
				entry, DLFileEntry.class,
				PortletDataContext.REFERENCE_TYPE_WEAK);

		for (Element attachmentElement : attachmentElements) {
			Attribute uuidAttribute = attachmentElement.attribute("uuid");

			String uuidAttributeValue = uuidAttribute.getValue();

			if ((oldSmallImageFileEntry != null) &&
				uuidAttributeValue.equals(oldSmallImageFileEntry.getUuid())) {

				smallImageFileEntryId =
					_blogsEntryLocalService.addSmallImageFileEntry(
						userId, portletDataContext.getScopeGroupId(),
						importedEntry.getEntryId(),
						getImageSelector(
							portletDataContext, attachmentElement));
			}

			if ((oldCoverImageFileEntry != null) &&
				uuidAttributeValue.equals(oldCoverImageFileEntry.getUuid())) {

				coverImageFileEntryId =
					_blogsEntryLocalService.addCoverImageFileEntry(
						userId, portletDataContext.getScopeGroupId(),
						importedEntry.getEntryId(),
						getImageSelector(
							portletDataContext, attachmentElement));
			}
		}

		importedEntry.setCoverImageFileEntryId(coverImageFileEntryId);
		importedEntry.setCoverImageURL(entry.getCoverImageURL());
		importedEntry.setSmallImage(entry.isSmallImage());
		importedEntry.setSmallImageFileEntryId(smallImageFileEntryId);
		importedEntry.setSmallImageURL(entry.getSmallImageURL());

		_blogsEntryPersistece.update(importedEntry);

		portletDataContext.importClassedModel(entry, importedEntry);
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, BlogsEntry entry)
		throws Exception {

		long userId = portletDataContext.getUserId(entry.getUserUuid());

		BlogsEntry existingEntry = fetchStagedModelByUuidAndGroupId(
			entry.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingEntry == null) || !existingEntry.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = existingEntry.getTrashHandler();

		if (trashHandler.isRestorable(existingEntry.getEntryId())) {
			trashHandler.restoreTrashEntry(userId, existingEntry.getEntryId());
		}
	}

	protected ImageSelector getImageSelector(
			PortletDataContext portletDataContext, Element attachmentElement)
		throws Exception {

		String path = attachmentElement.attributeValue("path");

		FileEntry fileEntry = (FileEntry)portletDataContext.getZipEntryAsObject(
			path);

		return new ImageSelector(
			FileUtil.getBytes(fileEntry.getContentStream()),
			fileEntry.getFileName(), fileEntry.getMimeType(), null);
	}

	protected InputStream getSmallImageInputStream(
		PortletDataContext portletDataContext, Element attachmentElement) {

		InputStream inputStream = null;

		String path = attachmentElement.attributeValue("path");

		FileEntry fileEntry = (FileEntry)portletDataContext.getZipEntryAsObject(
			path);

		String binPath = attachmentElement.attributeValue("bin-path");

		if (Validator.isNull(binPath) &&
			portletDataContext.isPerformDirectBinaryImport()) {

			try {
				inputStream = FileEntryUtil.getContentStream(fileEntry);
			}
			catch (Exception e) {
			}
		}
		else {
			inputStream = portletDataContext.getZipEntryAsInputStream(binPath);
		}

		if (inputStream == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to import small image file entry " +
						fileEntry.getFileEntryId());
			}
		}

		return inputStream;
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryExportImportContentProcessor(
		BlogsEntryExportImportContentProcessor
			blogsEntryExportImportContentProcessor) {

		_blogsEntryExportImportContentProcessor =
			blogsEntryExportImportContentProcessor;
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryPersistence(
		BlogsEntryPersistence blogsEntryPersistence) {

		_blogsEntryPersistece = blogsEntryPersistence;
	}

	@Reference(unbind = "-")
	protected void setImageLocalService(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsEntryStagedModelDataHandler.class);

	private volatile BlogsEntryExportImportContentProcessor
		_blogsEntryExportImportContentProcessor;
	private volatile BlogsEntryLocalService _blogsEntryLocalService;
	private volatile BlogsEntryPersistence _blogsEntryPersistece;
	private volatile ImageLocalService _imageLocalService;

}