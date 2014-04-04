/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portlet.documentlibrary.util.validator.DLValidatorUtil;
import com.liferay.portlet.documentlibrary.util.validator.DirectoryNameValidator;
import com.liferay.portlet.documentlibrary.util.validator.FileExtensionValidator;
import com.liferay.portlet.documentlibrary.util.validator.FileNameValidator;
import com.liferay.portlet.documentlibrary.util.validator.FileSizeValidator;
import com.liferay.portlet.documentlibrary.util.validator.VersionValidator;

/**
 * @author Adolfo Pérez
 */
public class DLConfig {

	public static DLConfig getLiberalDLConfig() {
		DLConfig liberalConfig = getRestrictedDLConfig();

		liberalConfig.setAssetEnabled(true);
		liberalConfig.setCommentsEnabled(true);
		liberalConfig.setWorkflowEnabled(true);
		liberalConfig.setDLProcessorRegistryEnabled(true);
		liberalConfig.setDLSyncEventEnabled(true);

		return liberalConfig;
	}

	public static DLConfig getRestrictedDLConfig() {
		DLConfig restrictedConfig = new DLConfig();

		restrictedConfig.setAssetEnabled(false);

		String commentsEnabled = PropsUtil.get(
			PropsKeys.DL_FILE_ENTRY_COMMENTS_ENABLED);

		restrictedConfig.setCommentsEnabled(
			Boolean.valueOf(commentsEnabled));

		restrictedConfig.setWorkflowEnabled(false);
		restrictedConfig.setDLProcessorRegistryEnabled(false);
		restrictedConfig.setDLSyncEventEnabled(false);

		restrictedConfig.setFileSizeValidator(
			DLValidatorUtil.getDefaultFileSizeValidator());
		restrictedConfig.setFileNameValidator(
			DLValidatorUtil.getDefaultFileNameValidator());
		restrictedConfig.setFileNameAndExtensionValidator(
			DLValidatorUtil.getDefaultFileNameAndExtensionValidator());
		restrictedConfig.setDirectoryNameValidator(
			DLValidatorUtil.getDefaultDirectoryNameValidator());
		restrictedConfig.setVersionValidator(
			DLValidatorUtil.getDefaultVersionValidator());
		restrictedConfig.setFileExtensionValidator(
			DLValidatorUtil.getDefaultFileExtensionValidator());

		return restrictedConfig;
	}
	
	public DLConfig(DLConfig original) {
		_isAssetEnabled = original.isAssetEnabled();
		_isWorkflowEnabled = original.isWorkflowEnabled();
		_isDLProcessorRegistryEnabled = original.isDLProcessorRegistryEnabled();
		_isCommentsEnabled = original.isCommentsEnabled();
		_isDLSyncEventEnabled = original.isDLSyncEventEnabled();
		_fileSizeValidator = original.getFileSizeValidator();
		_fileNameValidator = original.getFileNameValidator();
		_fileNameAndExtensionValidator =
			original.getFileNameAndExtensionValidator();
		_directoryNameValidator = original.getDirectoryNameValidator();
		_versionValidator = original.getVersionValidator();
		_fileExtensionValidator = original.getFileExtensionValidator();
	}

	protected DLConfig() {
	}

	public DirectoryNameValidator getDirectoryNameValidator() {
		return _directoryNameValidator;
	}

	public FileExtensionValidator getFileExtensionValidator() {
		return _fileExtensionValidator;
	}

	public FileNameValidator getFileNameAndExtensionValidator() {
		return _fileNameAndExtensionValidator;
	}

	public FileNameValidator getFileNameValidator() {
		return _fileNameValidator;
	}

	public FileNameValidator getFileNameValidator(
		boolean validateFileExtension) {

		if (validateFileExtension) {
			return getFileNameAndExtensionValidator();
		}

		return getFileNameValidator();
	}

	public FileSizeValidator getFileSizeValidator() {
		return _fileSizeValidator;
	}

	public VersionValidator getVersionValidator() {
		return _versionValidator;
	}

	public boolean isAssetEnabled() {
		return _isAssetEnabled;
	}

	public boolean isCommentsEnabled() {
		return _isCommentsEnabled;
	}

	public boolean isDLProcessorRegistryEnabled() {
		return _isDLProcessorRegistryEnabled;
	}

	public boolean isDLSyncEventEnabled() {
		return _isDLSyncEventEnabled;
	}

	public boolean isWorkflowEnabled() {
		return _isWorkflowEnabled;
	}

	public void setAssetEnabled(boolean isAssetEnabled) {
		_isAssetEnabled = isAssetEnabled;
	}

	public void setCommentsEnabled(boolean isCommentsEnabled) {
		_isCommentsEnabled = isCommentsEnabled;
	}

	public void setDirectoryNameValidator(
		DirectoryNameValidator directoryNameValidator) {

		_directoryNameValidator = directoryNameValidator;
	}

	public void setDLProcessorRegistryEnabled(boolean dlProcessorRegistryEnabled) {
		_isDLProcessorRegistryEnabled = dlProcessorRegistryEnabled;
	}

	public void setDLSyncEventEnabled(boolean isDLSyncEventEnabled) {
		_isDLSyncEventEnabled = isDLSyncEventEnabled;
	}

	public void setFileExtensionValidator(
		FileExtensionValidator fileExtensionValidator) {

		_fileExtensionValidator = fileExtensionValidator;
	}

	public void setFileNameAndExtensionValidator(
		FileNameValidator fileNameValidator) {

		_fileNameAndExtensionValidator = fileNameValidator;
	}

	public void setFileNameValidator(FileNameValidator fileNameValidator) {
		_fileNameValidator = fileNameValidator;
	}

	public void setFileSizeValidator(FileSizeValidator fileSizeValidator) {
		_fileSizeValidator = fileSizeValidator;
	}

	public void setVersionValidator(VersionValidator versionValidator) {
		_versionValidator = versionValidator;
	}

	public void setWorkflowEnabled(boolean workflowEnabled) {
		_isWorkflowEnabled = workflowEnabled;
	}

	private boolean _isAssetEnabled;
	private boolean _isCommentsEnabled;
	private boolean _isDLProcessorRegistryEnabled;
	private boolean _isDLSyncEventEnabled;
	private boolean _isWorkflowEnabled;
	private DirectoryNameValidator _directoryNameValidator;
	private FileExtensionValidator _fileExtensionValidator;
	private FileNameValidator _fileNameAndExtensionValidator;
	private FileNameValidator _fileNameValidator;
	private FileSizeValidator _fileSizeValidator;
	private VersionValidator _versionValidator;

}
