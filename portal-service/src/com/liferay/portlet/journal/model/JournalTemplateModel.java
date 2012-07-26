/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.model;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * The base model interface for the JournalTemplate service. Represents a row in the &quot;JournalTemplate&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.journal.model.impl.JournalTemplateImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalTemplate
 * @see com.liferay.portlet.journal.model.impl.JournalTemplateImpl
 * @see com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl
 * @generated
 */
public interface JournalTemplateModel extends BaseModel<JournalTemplate>,
	GroupedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a journal template model instance should use the {@link JournalTemplate} interface instead.
	 */

	/**
	 * Returns the primary key of this journal template.
	 *
	 * @return the primary key of this journal template
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this journal template.
	 *
	 * @param primaryKey the primary key of this journal template
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this journal template.
	 *
	 * @return the uuid of this journal template
	 */
	@AutoEscape
	public String getUuid();

	/**
	 * Sets the uuid of this journal template.
	 *
	 * @param uuid the uuid of this journal template
	 */
	public void setUuid(String uuid);

	/**
	 * Returns the ID of this journal template.
	 *
	 * @return the ID of this journal template
	 */
	public long getId();

	/**
	 * Sets the ID of this journal template.
	 *
	 * @param id the ID of this journal template
	 */
	public void setId(long id);

	/**
	 * Returns the group ID of this journal template.
	 *
	 * @return the group ID of this journal template
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this journal template.
	 *
	 * @param groupId the group ID of this journal template
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this journal template.
	 *
	 * @return the company ID of this journal template
	 */
	public long getCompanyId();

	/**
	 * Sets the company ID of this journal template.
	 *
	 * @param companyId the company ID of this journal template
	 */
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this journal template.
	 *
	 * @return the user ID of this journal template
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this journal template.
	 *
	 * @param userId the user ID of this journal template
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this journal template.
	 *
	 * @return the user uuid of this journal template
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this journal template.
	 *
	 * @param userUuid the user uuid of this journal template
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this journal template.
	 *
	 * @return the user name of this journal template
	 */
	@AutoEscape
	public String getUserName();

	/**
	 * Sets the user name of this journal template.
	 *
	 * @param userName the user name of this journal template
	 */
	public void setUserName(String userName);

	/**
	 * Returns the create date of this journal template.
	 *
	 * @return the create date of this journal template
	 */
	public Date getCreateDate();

	/**
	 * Sets the create date of this journal template.
	 *
	 * @param createDate the create date of this journal template
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this journal template.
	 *
	 * @return the modified date of this journal template
	 */
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this journal template.
	 *
	 * @param modifiedDate the modified date of this journal template
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the template ID of this journal template.
	 *
	 * @return the template ID of this journal template
	 */
	public String getTemplateId();

	/**
	 * Sets the template ID of this journal template.
	 *
	 * @param templateId the template ID of this journal template
	 */
	public void setTemplateId(String templateId);

	/**
	 * Returns the structure ID of this journal template.
	 *
	 * @return the structure ID of this journal template
	 */
	public String getStructureId();

	/**
	 * Sets the structure ID of this journal template.
	 *
	 * @param structureId the structure ID of this journal template
	 */
	public void setStructureId(String structureId);

	/**
	 * Returns the name of this journal template.
	 *
	 * @return the name of this journal template
	 */
	public String getName();

	/**
	 * Returns the localized name of this journal template in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this journal template
	 */
	@AutoEscape
	public String getName(Locale locale);

	/**
	 * Returns the localized name of this journal template in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this journal template. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getName(Locale locale, boolean useDefault);

	/**
	 * Returns the localized name of this journal template in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this journal template
	 */
	@AutoEscape
	public String getName(String languageId);

	/**
	 * Returns the localized name of this journal template in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this journal template
	 */
	@AutoEscape
	public String getName(String languageId, boolean useDefault);

	@AutoEscape
	public String getNameCurrentLanguageId();

	@AutoEscape
	public String getNameCurrentValue();

	/**
	 * Returns a map of the locales and localized names of this journal template.
	 *
	 * @return the locales and localized names of this journal template
	 */
	public Map<Locale, String> getNameMap();

	/**
	 * Sets the name of this journal template.
	 *
	 * @param name the name of this journal template
	 */
	public void setName(String name);

	/**
	 * Sets the localized name of this journal template in the language.
	 *
	 * @param name the localized name of this journal template
	 * @param locale the locale of the language
	 */
	public void setName(String name, Locale locale);

	/**
	 * Sets the localized name of this journal template in the language, and sets the default locale.
	 *
	 * @param name the localized name of this journal template
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setName(String name, Locale locale, Locale defaultLocale);

	public void setNameCurrentLanguageId(String languageId);

	/**
	 * Sets the localized names of this journal template from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this journal template
	 */
	public void setNameMap(Map<Locale, String> nameMap);

	/**
	 * Sets the localized names of this journal template from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this journal template
	 * @param defaultLocale the default locale
	 */
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale);

	/**
	 * Returns the description of this journal template.
	 *
	 * @return the description of this journal template
	 */
	public String getDescription();

	/**
	 * Returns the localized description of this journal template in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this journal template
	 */
	@AutoEscape
	public String getDescription(Locale locale);

	/**
	 * Returns the localized description of this journal template in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this journal template. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getDescription(Locale locale, boolean useDefault);

	/**
	 * Returns the localized description of this journal template in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this journal template
	 */
	@AutoEscape
	public String getDescription(String languageId);

	/**
	 * Returns the localized description of this journal template in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this journal template
	 */
	@AutoEscape
	public String getDescription(String languageId, boolean useDefault);

	@AutoEscape
	public String getDescriptionCurrentLanguageId();

	@AutoEscape
	public String getDescriptionCurrentValue();

	/**
	 * Returns a map of the locales and localized descriptions of this journal template.
	 *
	 * @return the locales and localized descriptions of this journal template
	 */
	public Map<Locale, String> getDescriptionMap();

	/**
	 * Sets the description of this journal template.
	 *
	 * @param description the description of this journal template
	 */
	public void setDescription(String description);

	/**
	 * Sets the localized description of this journal template in the language.
	 *
	 * @param description the localized description of this journal template
	 * @param locale the locale of the language
	 */
	public void setDescription(String description, Locale locale);

	/**
	 * Sets the localized description of this journal template in the language, and sets the default locale.
	 *
	 * @param description the localized description of this journal template
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setDescription(String description, Locale locale,
		Locale defaultLocale);

	public void setDescriptionCurrentLanguageId(String languageId);

	/**
	 * Sets the localized descriptions of this journal template from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this journal template
	 */
	public void setDescriptionMap(Map<Locale, String> descriptionMap);

	/**
	 * Sets the localized descriptions of this journal template from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this journal template
	 * @param defaultLocale the default locale
	 */
	public void setDescriptionMap(Map<Locale, String> descriptionMap,
		Locale defaultLocale);

	/**
	 * Returns the xsl of this journal template.
	 *
	 * @return the xsl of this journal template
	 */
	@AutoEscape
	public String getXsl();

	/**
	 * Sets the xsl of this journal template.
	 *
	 * @param xsl the xsl of this journal template
	 */
	public void setXsl(String xsl);

	/**
	 * Returns the lang type of this journal template.
	 *
	 * @return the lang type of this journal template
	 */
	@AutoEscape
	public String getLangType();

	/**
	 * Sets the lang type of this journal template.
	 *
	 * @param langType the lang type of this journal template
	 */
	public void setLangType(String langType);

	/**
	 * Returns the cacheable of this journal template.
	 *
	 * @return the cacheable of this journal template
	 */
	public boolean getCacheable();

	/**
	 * Returns <code>true</code> if this journal template is cacheable.
	 *
	 * @return <code>true</code> if this journal template is cacheable; <code>false</code> otherwise
	 */
	public boolean isCacheable();

	/**
	 * Sets whether this journal template is cacheable.
	 *
	 * @param cacheable the cacheable of this journal template
	 */
	public void setCacheable(boolean cacheable);

	/**
	 * Returns the small image of this journal template.
	 *
	 * @return the small image of this journal template
	 */
	public boolean getSmallImage();

	/**
	 * Returns <code>true</code> if this journal template is small image.
	 *
	 * @return <code>true</code> if this journal template is small image; <code>false</code> otherwise
	 */
	public boolean isSmallImage();

	/**
	 * Sets whether this journal template is small image.
	 *
	 * @param smallImage the small image of this journal template
	 */
	public void setSmallImage(boolean smallImage);

	/**
	 * Returns the small image ID of this journal template.
	 *
	 * @return the small image ID of this journal template
	 */
	public long getSmallImageId();

	/**
	 * Sets the small image ID of this journal template.
	 *
	 * @param smallImageId the small image ID of this journal template
	 */
	public void setSmallImageId(long smallImageId);

	/**
	 * Returns the small image u r l of this journal template.
	 *
	 * @return the small image u r l of this journal template
	 */
	@AutoEscape
	public String getSmallImageURL();

	/**
	 * Sets the small image u r l of this journal template.
	 *
	 * @param smallImageURL the small image u r l of this journal template
	 */
	public void setSmallImageURL(String smallImageURL);

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public Serializable getPrimaryKeyObj();

	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException;

	public Object clone();

	public int compareTo(JournalTemplate journalTemplate);

	public int hashCode();

	public CacheModel<JournalTemplate> toCacheModel();

	public JournalTemplate toEscapedModel();

	public String toString();

	public String toXmlString();
}