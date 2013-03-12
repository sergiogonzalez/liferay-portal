package ${packagePath}.service.persistence;

import ${packagePath}.model.${entity.name};
import ${packagePath}.service.${entity.name}LocalServiceUtil;

import com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

/**
 * @author ${author}
 * @generated
 */
public abstract class ${entity.name}ActionableDynamicQuery
	extends BaseActionableDynamicQuery {

	public ${entity.name}ActionableDynamicQuery() throws SystemException {
		setBaseLocalService(${entity.name}LocalServiceUtil.getService());
		setClass(${entity.name}.class);

		<#if pluginName != "">
			setClassLoader(${packagePath}.service.ClpSerializer.class.getClassLoader());
		<#else>
			setClassLoader(PortalClassLoaderUtil.getClassLoader());
		</#if>

		<#if entity.hasPrimitivePK()>
			setPrimaryKeyPropertyName("${entity.PKVarName}");
		<#else>
			<#assign pkColumn = entity.getPKList()?first>

			setPrimaryKeyPropertyName("primaryKey.${pkColumn.name}");
		</#if>
	}

}