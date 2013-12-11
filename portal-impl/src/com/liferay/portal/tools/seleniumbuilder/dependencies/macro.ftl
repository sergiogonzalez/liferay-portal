package ${seleniumBuilderContext.getMacroPackageName(macroName)};

import com.liferay.portalweb.portal.util.RuntimeVariables;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;
import com.liferay.portalweb2.util.block.macro.BaseMacro;

<#assign rootElement = seleniumBuilderContext.getMacroRootElement(macroName)>

<#if rootElement.attributeValue("extends")??>
	<#assign extendsName = rootElement.attributeValue("extends")>

	import ${seleniumBuilderContext.getMacroClassName(extendsName)};
</#if>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "action")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getActionClassName(childElementAttributeValue)};
</#list>

<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(rootElement, "macro")>

<#list childElementAttributeValues as childElementAttributeValue>
	import ${seleniumBuilderContext.getMacroClassName(childElementAttributeValue)};
</#list>

import java.util.HashMap;
import java.util.Map;

public class ${seleniumBuilderContext.getMacroSimpleClassName(macroName)}

<#if extendsName??>
	extends ${extendsName}Macro {
<#else>
	extends BaseMacro {
</#if>

	public ${seleniumBuilderContext.getMacroSimpleClassName(macroName)}(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);

		<#if rootElement.element("var")??>
			<#assign varElements = rootElement.elements("var")>

			<#assign context = "definitionScopeVariables">

			<#list varElements as varElement>
				<#assign lineNumber = varElement.attributeValue("line-number")>

				liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pending", ${context});

				<#include "var_element.ftl">

				liferaySelenium.sendLogger("${macroName?uncap_first}Macro${lineNumber}", "pass", ${context});
			</#list>
		</#if>
	}

	<#assign commandElements = rootElement.elements("command")>

	<#list commandElements as commandElement>
		<#assign commandName = commandElement.attributeValue("name")>

		public
			<#if commandName?starts_with("is")>
				boolean
			<#else>
				void
			</#if>
		${commandName}(Map<String, String> environmentScopeVariables) throws Exception {
			commandScopeVariables = new HashMap<String, String>();

			commandScopeVariables.putAll(definitionScopeVariables);

			commandScopeVariables.putAll(environmentScopeVariables);

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(commandElement, "action")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Action ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Action = new ${childElementAttributeValue}Action(liferaySelenium);
			</#list>

			<#assign childElementAttributeValues = seleniumBuilderFileUtil.getChildElementAttributeValues(commandElement, "macro")>

			<#list childElementAttributeValues as childElementAttributeValue>
				${childElementAttributeValue}Macro ${seleniumBuilderFileUtil.getVariableName(childElementAttributeValue)}Macro = new ${childElementAttributeValue}Macro(liferaySelenium);
			</#list>

			<#assign blockElement = commandElement>

			<#assign blockLevel = "macro">

			<#include "block_element.ftl">
		}
	</#list>

	private int _whileCount;

}