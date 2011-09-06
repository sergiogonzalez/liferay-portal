<#setting number_format = "0">

<#if (dlFolderDepth <= maxDLFolderDepth)>
	<#list 1..maxDLFolderCount as dlFolderCount>
		<#assign dlFolder = dataFactory.addDLFolder(ddmStructure.groupId, companyId, ddmStructure.userId, parentDLFolderId, "Test Folder " + dlFolderCount, "This is a test dl folder " + dlFolderCount + ".")>

		${sampleSQLBuilder.insertDLFolder(dlFolder, ddmStructure)}

		${sampleSQLBuilder.insertDLFolders(dlFolder.folderId, dlFolderDepth + 1, ddmStructure)}
	</#list>
</#if>