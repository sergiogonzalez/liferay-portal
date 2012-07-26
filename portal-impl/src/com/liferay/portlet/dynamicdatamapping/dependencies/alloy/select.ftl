<#include "../init.ftl">

<#assign multiple = false>

<#if fieldStructure.multiple?? && (fieldStructure.multiple == "true")>
	<#assign multiple = true>
</#if>

<#if required>
	<#assign label = label + " (" + languageUtil.get(locale, "required") + ")">
</#if>

<@aui.select cssClass=cssClass helpMessage=escape(fieldStructure.tip) label=escape(label) multiple=multiple name=namespacedFieldName>
	${fieldStructure.children}
</@aui.select>