<#setting number_format = "0">

${writerRepositoryCSV.write(group.groupId + ", " + group.name + "\n")}

<#assign ddmStructure = dataFactory.newDDMStructure(groupId, companyId, sampleUserId, dataFactory.DLFileEntryClassNameId)>

<#assign createDate = dataFactory.getDateString(ddmStructure.createDate)>

<#if (maxDLFolderCount > 0)>
	insert into DDMStructure values ('${portalUUIDUtil.generate()}', ${ddmStructure.structureId}, ${ddmStructure.groupId}, ${ddmStructure.companyId}, ${ddmStructure.userId}, '', '${createDate}', '${createDate}', 0, ${ddmStructure.classNameId}, 'HttpHeaders', '<?xml version="1.0" encoding="UTF-8"?><root available-locales="en_US" default-locale="en_US"><Name language-id="en_US">HttpHeaders</Name></root>', '<?xml version="1.0" encoding="UTF-8"?><root available-locales="en_US" default-locale="en_US"><Description language-id="en_US">HttpHeaders</Description></root>', '<root available-locales="en_US" default-locale="en_US"><dynamic-element dataType="string" name="CONTENT_ENCODING" type="text"><meta-data locale="en_US"><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_ENCODING]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_LANGUAGE" type="text"><meta-data locale="en_US"><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_LANGUAGE]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_LENGTH" type="text"><meta-data locale="en_US"><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_LENGTH]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_LOCATION" type="text"><meta-data locale="en_US"><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_LOCATION]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_DISPOSITION" type="text"><meta-data locale="en_US"><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_DISPOSITION]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_MD5" type="text"><meta-data locale="en_US"><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_MD5]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="CONTENT_TYPE" type="text"><meta-data locale="en_US"><entry name="label"><![CDATA[metadata.HttpHeaders.CONTENT_TYPE]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="LAST_MODIFIED" type="text"><meta-data locale="en_US"><entry name="label"><![CDATA[metadata.HttpHeaders.LAST_MODIFIED]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType="string" name="LOCATION" type="text"><meta-data locale="en_US"><entry name="label"><![CDATA[metadata.HttpHeaders.LOCATION]]></entry><entry name="predefinedValue"><![CDATA[]]></entry><entry name="required"><![CDATA[false]]></entry><entry name="showLabel"><![CDATA[true]]></entry></meta-data></dynamic-element></root>', 'xml', 0);

	${sampleSQLBuilder.insertDLFolders(groupId, 0, 1, ddmStructure)}
</#if>