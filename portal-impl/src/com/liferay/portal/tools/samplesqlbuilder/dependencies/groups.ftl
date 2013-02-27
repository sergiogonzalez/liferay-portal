<#setting number_format = "0">

${sampleSQLBuilder.insertGroup(dataFactory.guestGroup, [dataFactory.newLayout(1, "Welcome", "/welcome", "58,", "47,")])}

<#list 1..maxGroupCount as groupCount>
	<#assign groupId = groupCount>

	<#assign group = dataFactory.newGroup(groupId, dataFactory.groupClassNameId, groupId, "Community " + groupCount, "/community" + groupCount, true)>

	<#assign publicLayouts = [
		dataFactory.newLayout(1, "Welcome", "/welcome", "58,", "47,"),
		dataFactory.newLayout(2, "Blogs", "/blogs", "", "33,")
		dataFactory.newLayout(3, "Document Library", "/document_library", "", "20,")
		dataFactory.newLayout(4, "Forums", "/forums", "", "19,")
		dataFactory.newLayout(5, "Wiki", "/wiki", "", "36,")
	]>

	<#include "users.ftl">

	<#include "blogs.ftl">

	<#include "ddl.ftl">

	<#include "dl.ftl">

	<#include "journal_article.ftl">

	<#include "mb.ftl">

	<#include "wiki.ftl">

	${sampleSQLBuilder.insertGroup(group, publicLayouts)}
</#list>