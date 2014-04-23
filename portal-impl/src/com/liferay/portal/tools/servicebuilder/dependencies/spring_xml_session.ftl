<bean id="${packagePath}.service.${entity.name}${sessionType}Service" class="${packagePath}.service.impl.${entity.name}${sessionType}ServiceImpl" />

<#if osgiModule>
	<osgi:service interface="${packagePath}.service.${entity.name}${sessionType}Service" ref="${packagePath}.service.${entity.name}${sessionType}Service" />
</#if>