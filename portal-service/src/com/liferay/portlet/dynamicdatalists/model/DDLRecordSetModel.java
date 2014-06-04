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

package com.liferay.portlet.dynamicdatalists.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.LocalizedModel;
import com.liferay.portal.model.StagedGroupedModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * The base model interface for the DDLRecordSet service. Represents a row in the &quot;DDLRecordSet&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordSetModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordSetImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSet
 * @see com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordSetImpl
 * @see com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordSetModelImpl
 * @generated
 */
@ProviderType
public interface DDLRecordSetModel extends BaseModel<DDLRecordSet>,
	LocalizedModel, StagedGroupedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a d d l record set model instance should use the {@link DDLRecordSet} interface instead.
	 */

	/**
	 * Returns the primary key of this d d l record set.
	 *
	 * @return the primary key of this d d l record set
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this d d l record set.
	 *
	 * @param primaryKey the primary key of this d d l record set
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this d d l record set.
	 *
	 * @return the uuid of this d d l record set
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this d d l record set.
	 *
	 * @param uuid the uuid of this d d l record set
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the record set ID of this d d l record set.
	 *
	 * @return the record set ID of this d d l record set
	 */
	public long getRecordSetId();

	/**
	 * Sets the record set ID of this d d l record set.
	 *
	 * @param recordSetId the record set ID of this d d l record set
	 */
	public void setRecordSetId(long recordSetId);

	/**
	 * Returns the group ID of this d d l record set.
	 *
	 * @return the group ID of this d d l record set
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this d d l record set.
	 *
	 * @param groupId the group ID of this d d l record set
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this d d l record set.
	 *
	 * @return the company ID of this d d l record set
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this d d l record set.
	 *
	 * @param companyId the company ID of this d d l record set
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this d d l record set.
	 *
	 * @return the user ID of this d d l record set
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this d d l record set.
	 *
	 * @param userId the user ID of this d d l record set
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this d d l record set.
	 *
	 * @return the user uuid of this d d l record set
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this d d l record set.
	 *
	 * @param userUuid the user uuid of this d d l record set
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this d d l record set.
	 *
	 * @return the user name of this d d l record set
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this d d l record set.
	 *
	 * @param userName the user name of this d d l record set
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this d d l record set.
	 *
	 * @return the create date of this d d l record set
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this d d l record set.
	 *
	 * @param createDate the create date of this d d l record set
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this d d l record set.
	 *
	 * @return the modified date of this d d l record set
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this d d l record set.
	 *
	 * @param modifiedDate the modified date of this d d l record set
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the d d m structure ID of this d d l record set.
	 *
	 * @return the d d m structure ID of this d d l record set
	 */
	public long getDDMStructureId();

	/**
	 * Sets the d d m structure ID of this d d l record set.
	 *
	 * @param DDMStructureId the d d m structure ID of this d d l record set
	 */
	public void setDDMStructureId(long DDMStructureId);

	/**
	 * Returns the record set key of this d d l record set.
	 *
	 * @return the record set key of this d d l record set
	 */
	public String getRecordSetKey();

	/**
	 * Sets the record set key of this d d l record set.
	 *
	 * @param recordSetKey the record set key of this d d l record set
	 */
	public void setRecordSetKey(String recordSetKey);

	/**
	 * Returns the name of this d d l record set.
	 *
	 * @return the name of this d d l record set
	 */
	public String getName();

	/**
	 * Returns the localized name of this d d l record set in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this d d l record set
	 */
	@AutoEscape
	public String getName(Locale locale);

	/**
	 * Returns the localized name of this d d l record set in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this d d l record set. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getName(Locale locale, boolean useDefault);

	/**
	 * Returns the localized name of this d d l record set in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this d d l record set
	 */
	@AutoEscape
	public String getName(String languageId);

	/**
	 * Returns the localized name of this d d l record set in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this d d l record set
	 */
	@AutoEscape
	public String getName(String languageId, boolean useDefault);

	@AutoEscape
	public String getNameCurrentLanguageId();

	@AutoEscape
	public String getNameCurrentValue();

	/**
	 * Returns a map of the locales and localized names of this d d l record set.
	 *
	 * @return the locales and localized names of this d d l record set
	 */
	public Map<Locale, String> getNameMap();

	/**
	 * Sets the name of this d d l record set.
	 *
	 * @param name the name of this d d l record set
	 */
	public void setName(String name);

	/**
	 * Sets the localized name of this d d l record set in the language.
	 *
	 * @param name the localized name of this d d l record set
	 * @param locale the locale of the language
	 */
	public void setName(String name, Locale locale);

	/**
	 * Sets the localized name of this d d l record set in the language, and sets the default locale.
	 *
	 * @param name the localized name of this d d l record set
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setName(String name, Locale locale, Locale defaultLocale);

	public void setNameCurrentLanguageId(String languageId);

	/**
	 * Sets the localized names of this d d l record set from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this d d l record set
	 */
	public void setNameMap(Map<Locale, String> nameMap);

	/**
	 * Sets the localized names of this d d l record set from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this d d l record set
	 * @param defaultLocale the default locale
	 */
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale);

	/**
	 * Returns the description of this d d l record set.
	 *
	 * @return the description of this d d l record set
	 */
	public String getDescription();

	/**
	 * Returns the localized description of this d d l record set in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this d d l record set
	 */
	@AutoEscape
	public String getDescription(Locale locale);

	/**
	 * Returns the localized description of this d d l record set in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this d d l record set. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getDescription(Locale locale, boolean useDefault);

	/**
	 * Returns the localized description of this d d l record set in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this d d l record set
	 */
	@AutoEscape
	public String getDescription(String languageId);

	/**
	 * Returns the localized description of this d d l record set in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this d d l record set
	 */
	@AutoEscape
	public String getDescription(String languageId, boolean useDefault);

	@AutoEscape
	public String getDescriptionCurrentLanguageId();

	@AutoEscape
	public String getDescriptionCurrentValue();

	/**
	 * Returns a map of the locales and localized descriptions of this d d l record set.
	 *
	 * @return the locales and localized descriptions of this d d l record set
	 */
	public Map<Locale, String> getDescriptionMap();

	/**
	 * Sets the description of this d d l record set.
	 *
	 * @param description the description of this d d l record set
	 */
	public void setDescription(String description);

	/**
	 * Sets the localized description of this d d l record set in the language.
	 *
	 * @param description the localized description of this d d l record set
	 * @param locale the locale of the language
	 */
	public void setDescription(String description, Locale locale);

	/**
	 * Sets the localized description of this d d l record set in the language, and sets the default locale.
	 *
	 * @param description the localized description of this d d l record set
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setDescription(String description, Locale locale,
		Locale defaultLocale);

	public void setDescriptionCurrentLanguageId(String languageId);

	/**
	 * Sets the localized descriptions of this d d l record set from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this d d l record set
	 */
	public void setDescriptionMap(Map<Locale, String> descriptionMap);

	/**
	 * Sets the localized descriptions of this d d l record set from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this d d l record set
	 * @param defaultLocale the default locale
	 */
	public void setDescriptionMap(Map<Locale, String> descriptionMap,
		Locale defaultLocale);

	/**
	 * Returns the min display rows of this d d l record set.
	 *
	 * @return the min display rows of this d d l record set
	 */
	public int getMinDisplayRows();

	/**
	 * Sets the min display rows of this d d l record set.
	 *
	 * @param minDisplayRows the min display rows of this d d l record set
	 */
	public void setMinDisplayRows(int minDisplayRows);

	/**
	 * Returns the scope of this d d l record set.
	 *
	 * @return the scope of this d d l record set
	 */
	public int getScope();

	/**
	 * Sets the scope of this d d l record set.
	 *
	 * @param scope the scope of this d d l record set
	 */
	public void setScope(int scope);

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public String[] getAvailableLanguageIds();

	@Override
	public String getDefaultLanguageId();

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException;

	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException;

	@Override
	public Object clone();

	@Override
	public int compareTo(DDLRecordSet ddlRecordSet);

	@Override
	public int hashCode();

	@Override
	public CacheModel<DDLRecordSet> toCacheModel();

	@Override
	public DDLRecordSet toEscapedModel();

	@Override
	public DDLRecordSet toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}