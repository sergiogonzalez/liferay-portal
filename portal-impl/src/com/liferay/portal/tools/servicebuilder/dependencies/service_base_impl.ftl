package ${packagePath}.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import ${beanLocatorUtil};
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.Base${sessionTypeName}ServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

<#if entity.hasColumns()>
	<#if entity.hasCompoundPK()>
		import ${packagePath}.service.persistence.${entity.name}PK;
	</#if>

	import ${packagePath}.model.${entity.name};

	<#list entity.blobList as column>
		<#if column.lazy>
			import ${packagePath}.model.${entity.name}${column.methodName}BlobModel;
		</#if>
	</#list>

	import ${packagePath}.model.impl.${entity.name}Impl;
</#if>

<#list referenceList as tempEntity>
	<#if tempEntity.hasLocalService()>
		import ${tempEntity.packagePath}.service.${tempEntity.name}LocalService;
	</#if>

	<#if tempEntity.hasRemoteService()>
		import ${tempEntity.packagePath}.service.${tempEntity.name}Service;
	</#if>

	<#if tempEntity.hasColumns() && (entity.name == "Counter" || tempEntity.name != "Counter")>
		import ${tempEntity.packagePath}.service.persistence.${tempEntity.name}Persistence;
		import ${tempEntity.packagePath}.service.persistence.${tempEntity.name}Util;
	</#if>

	<#if tempEntity.hasFinderClass() && (entity.name == "Counter" || tempEntity.name != "Counter")>
		import ${tempEntity.packagePath}.service.persistence.${tempEntity.name}Finder;
		import ${tempEntity.packagePath}.service.persistence.${tempEntity.name}FinderUtil;
	</#if>
</#list>

<#if sessionTypeName == "Local">
/**
 * The base implementation of the ${entity.humanName} local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link ${packagePath}.service.impl.${entity.name}LocalServiceImpl}.
 * </p>
 *
 * @author ${author}
 * @see ${packagePath}.service.impl.${entity.name}LocalServiceImpl
 * @see ${packagePath}.service.${entity.name}LocalServiceUtil
 * @generated
 */
	public abstract class ${entity.name}LocalServiceBaseImpl extends BaseLocalServiceImpl implements ${entity.name}LocalService, IdentifiableBean {

		/*
		 * NOTE FOR DEVELOPERS:
		 *
		 * Never modify or reference this class directly. Always use {@link ${packagePath}.service.${entity.name}LocalServiceUtil} to access the ${entity.humanName} local service.
		 */
<#else>
/**
 * The base implementation of the ${entity.humanName} remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link ${packagePath}.service.impl.${entity.name}ServiceImpl}.
 * </p>
 *
 * @author ${author}
 * @see ${packagePath}.service.impl.${entity.name}ServiceImpl
 * @see ${packagePath}.service.${entity.name}ServiceUtil
 * @generated
 */
	public abstract class ${entity.name}ServiceBaseImpl extends BaseServiceImpl implements ${entity.name}Service, IdentifiableBean {

		/*
		 * NOTE FOR DEVELOPERS:
		 *
		 * Never modify or reference this class directly. Always use {@link ${packagePath}.service.${entity.name}ServiceUtil} to access the ${entity.humanName} remote service.
		 */
</#if>

	<#if (sessionTypeName == "Local") && entity.hasColumns()>
		<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "add" + entity.name, [packagePath + ".model." + entity.name], ["SystemException"])>

		/**
		 * Adds the ${entity.humanName} to the database. Also notifies the appropriate model listeners.
		 *
		 * @param ${entity.varName} the ${entity.humanName}
		 * @return the ${entity.humanName} that was added
		<#list serviceBaseExceptions as exception>
		<#if exception == "SystemException">
		 * @throws SystemException if a system exception occurred
		<#else>
		 * @throws ${exception}
		</#if>
		</#list>
		 */
		@Indexable(type = IndexableType.REINDEX)
		public ${entity.name} add${entity.name}(${entity.name} ${entity.varName}) throws ${stringUtil.merge(serviceBaseExceptions)} {
			${entity.varName}.setNew(true);

			return ${entity.varName}Persistence.update(${entity.varName});
		}

		/**
		 * Creates a new ${entity.humanName} with the primary key. Does not add the ${entity.humanName} to the database.
		 *
		 * @param ${entity.PKVarName} the primary key for the new ${entity.humanName}
		 * @return the new ${entity.humanName}
		 */
		public ${entity.name} create${entity.name}(${entity.PKClassName} ${entity.PKVarName}) {
			return ${entity.varName}Persistence.create(${entity.PKVarName});
		}

		<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "delete" + entity.name, [entity.PKClassName], ["PortalException", "SystemException"])>

		/**
		 * Deletes the ${entity.humanName} with the primary key from the database. Also notifies the appropriate model listeners.
		 *
		 * @param ${entity.PKVarName} the primary key of the ${entity.humanName}
		 * @return the ${entity.humanName} that was removed
		<#list serviceBaseExceptions as exception>
		<#if exception == "PortalException">
		 * @throws PortalException if a ${entity.humanName} with the primary key could not be found
		<#elseif exception == "SystemException">
		 * @throws SystemException if a system exception occurred
		<#else>
		 * @throws ${exception}
		</#if>
		</#list>
		 */
		@Indexable(type = IndexableType.DELETE)
		public ${entity.name} delete${entity.name}(${entity.PKClassName} ${entity.PKVarName}) throws ${stringUtil.merge(serviceBaseExceptions)} {
			return ${entity.varName}Persistence.remove(${entity.PKVarName});
		}

		<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "delete" + entity.name, [packagePath + ".model." + entity.name], ["SystemException"])>

		/**
		 * Deletes the ${entity.humanName} from the database. Also notifies the appropriate model listeners.
		 *
		 * @param ${entity.varName} the ${entity.humanName}
		 * @return the ${entity.humanName} that was removed
		<#list serviceBaseExceptions as exception>
		<#if exception == "SystemException">
		 * @throws SystemException if a system exception occurred
		<#else>
		 * @throws ${exception}
		</#if>
		</#list>
		 */
		@Indexable(type = IndexableType.DELETE)
		public ${entity.name} delete${entity.name}(${entity.name} ${entity.varName}) throws ${stringUtil.merge(serviceBaseExceptions)} {
			return ${entity.varName}Persistence.remove(${entity.varName});
		}

		public DynamicQuery dynamicQuery() {
			Class<?> clazz = getClass();

			return DynamicQueryFactoryUtil.forClass(${entity.name}.class, clazz.getClassLoader());
		}

		/**
		 * Performs a dynamic query on the database and returns the matching rows.
		 *
		 * @param dynamicQuery the dynamic query
		 * @return the matching rows
		 * @throws SystemException if a system exception occurred
		 */
		@SuppressWarnings("rawtypes")
		public List dynamicQuery(DynamicQuery dynamicQuery) throws SystemException {
			return ${entity.varName}Persistence.findWithDynamicQuery(dynamicQuery);
		}

		/**
		 * Performs a dynamic query on the database and returns a range of the matching rows.
		 *
		 * <p>
		 * <#include "range_comment.ftl">
		 * </p>
		 *
		 * @param dynamicQuery the dynamic query
		 * @param start the lower bound of the range of model instances
		 * @param end the upper bound of the range of model instances (not inclusive)
		 * @return the range of matching rows
		 * @throws SystemException if a system exception occurred
		 */
		@SuppressWarnings("rawtypes")
		public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end) throws SystemException {
			return ${entity.varName}Persistence.findWithDynamicQuery(dynamicQuery, start, end);
		}

		/**
		 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
		 *
		 * <p>
		 * <#include "range_comment.ftl">
		 * </p>
		 *
		 * @param dynamicQuery the dynamic query
		 * @param start the lower bound of the range of model instances
		 * @param end the upper bound of the range of model instances (not inclusive)
		 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
		 * @return the ordered range of matching rows
		 * @throws SystemException if a system exception occurred
		 */
		@SuppressWarnings("rawtypes")
		public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end, OrderByComparator orderByComparator) throws SystemException {
			return ${entity.varName}Persistence.findWithDynamicQuery(dynamicQuery, start, end, orderByComparator);
		}

		/**
		 * Returns the number of rows that match the dynamic query.
		 *
		 * @param dynamicQuery the dynamic query
		 * @return the number of rows that match the dynamic query
		 * @throws SystemException if a system exception occurred
		 */
		public long dynamicQueryCount(DynamicQuery dynamicQuery) throws SystemException {
			return ${entity.varName}Persistence.countWithDynamicQuery(dynamicQuery);
		}

		<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "fetch" + entity.name, [entity.PKClassName], ["SystemException"])>

		public ${entity.name} fetch${entity.name}(${entity.PKClassName} ${entity.PKVarName}) throws ${stringUtil.merge(serviceBaseExceptions)} {
			return ${entity.varName}Persistence.fetchByPrimaryKey(${entity.PKVarName});
		}

		<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "get" + entity.name, [entity.PKClassName], ["PortalException", "SystemException"])>

		/**
		 * Returns the ${entity.humanName} with the primary key.
		 *
		 * @param ${entity.PKVarName} the primary key of the ${entity.humanName}
		 * @return the ${entity.humanName}
		<#list serviceBaseExceptions as exception>
		<#if exception == "PortalException">
		 * @throws PortalException if a ${entity.humanName} with the primary key could not be found
		<#elseif exception == "SystemException">
		 * @throws SystemException if a system exception occurred
		<#else>
		 * @throws ${exception}
		</#if>
		</#list>
		 */
		public ${entity.name} get${entity.name}(${entity.PKClassName} ${entity.PKVarName}) throws ${stringUtil.merge(serviceBaseExceptions)} {
			return ${entity.varName}Persistence.findByPrimaryKey(${entity.PKVarName});
		}

		public PersistedModel getPersistedModel(Serializable primaryKeyObj) throws PortalException, SystemException {
			return ${entity.varName}Persistence.findByPrimaryKey(primaryKeyObj);
		}

		<#if entity.hasUuid() && entity.hasColumn("groupId")>
			<#if entity.name == "Layout">
				/**
				 * @param uuid the UUID of ${entity.humanName}
				 * @param groupId the group id of the ${entity.humanName}
				 * @param privateLayout whether the ${entity.humanName} is private to the group
				 * @return the ${entity.humanName}
				<#list serviceBaseExceptions as exception>
				<#if exception == "PortalException">
				 * @throws PortalException if a ${entity.humanName} with the UUID in the group and privateLayout could not be found
				<#elseif exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public ${entity.name} get${entity.name}ByUuidAndGroupId(String uuid, long groupId, boolean privateLayout) throws ${stringUtil.merge(serviceBaseExceptions)} {
					return ${entity.varName}Persistence.findByUUID_G_P(uuid, groupId, privateLayout);
				}
			<#else>
				/**
				 * Returns the ${entity.humanName} with the UUID in the group.
				 *
				 * @param uuid the UUID of ${entity.humanName}
				 * @param groupId the group id of the ${entity.humanName}
				 * @return the ${entity.humanName}
				<#list serviceBaseExceptions as exception>
				<#if exception == "PortalException">
				 * @throws PortalException if a ${entity.humanName} with the UUID in the group could not be found
				<#elseif exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public ${entity.name} get${entity.name}ByUuidAndGroupId(String uuid, long groupId) throws ${stringUtil.merge(serviceBaseExceptions)} {
					return ${entity.varName}Persistence.findByUUID_G(uuid, groupId);
				}
			</#if>
		</#if>

		/**
		 * Returns a range of all the ${entity.humanNames}.
		 *
		 * <p>
		 * <#include "range_comment.ftl">
		 * </p>
		 *
		 * @param start the lower bound of the range of ${entity.humanNames}
		 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
		 * @return the range of ${entity.humanNames}
		 * @throws SystemException if a system exception occurred
		 */
		public List<${entity.name}> get${entity.names}(int start, int end) throws SystemException {
			return ${entity.varName}Persistence.findAll(start, end);
		}

		/**
		 * Returns the number of ${entity.humanNames}.
		 *
		 * @return the number of ${entity.humanNames}
		 * @throws SystemException if a system exception occurred
		 */
		public int get${entity.names}Count() throws SystemException {
			return ${entity.varName}Persistence.countAll();
		}

		<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "update" + entity.name, [packagePath + ".model." + entity.name], ["SystemException"])>

		/**
		 * Updates the ${entity.humanName} in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
		 *
		 * @param ${entity.varName} the ${entity.humanName}
		 * @return the ${entity.humanName} that was updated
		<#list serviceBaseExceptions as exception>
		<#if exception == "SystemException">
		 * @throws SystemException if a system exception occurred
		<#else>
		 * @throws ${exception}
		</#if>
		</#list>
		 */
		@Indexable(type = IndexableType.REINDEX)
		public ${entity.name} update${entity.name}(${entity.name} ${entity.varName}) throws ${stringUtil.merge(serviceBaseExceptions)} {
			return ${entity.varName}Persistence.update(${entity.varName});
		}

		<#list entity.blobList as column>
			<#if column.lazy>
				public ${entity.name}${column.methodName}BlobModel get${column.methodName}BlobModel(Serializable primaryKey) throws SystemException {
					Session session = null;

					try {
						session = ${entity.varName}Persistence.openSession();

						return (${packagePath}.model.${entity.name}${column.methodName}BlobModel)session.get(${entity.name}${column.methodName}BlobModel.class, primaryKey);
					}
					catch (Exception e) {
						throw ${entity.varName}Persistence.processException(e);
					}
					finally {
						${entity.varName}Persistence.closeSession(session);
					}
				}
			</#if>
		</#list>

		<#list entity.columnList as column>
			<#if column.isCollection() && column.isMappingManyToMany()>
				<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "add" + tempEntity.name + entity.name, [tempEntity.PKClassName, entity.PKClassName], ["SystemException"])>

				/**
				<#list serviceBaseExceptions as exception>
				<#if exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public void add${tempEntity.name}${entity.name}(${tempEntity.PKClassName} ${tempEntity.PKVarName}, ${entity.PKClassName} ${entity.PKVarName}) throws ${stringUtil.merge(serviceBaseExceptions)} {
					${tempEntity.varName}Persistence.add${entity.name}(${tempEntity.PKVarName}, ${entity.PKVarName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "add" + tempEntity.name + entity.name, [tempEntity.PKClassName, packagePath + ".model." + entity.name], ["SystemException"])>

				/**
				<#list serviceBaseExceptions as exception>
				<#if exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public void add${tempEntity.name}${entity.name}(${tempEntity.PKClassName} ${tempEntity.PKVarName}, ${entity.name} ${entity.varName}) throws ${stringUtil.merge(serviceBaseExceptions)} {
					${tempEntity.varName}Persistence.add${entity.name}(${tempEntity.PKVarName}, ${entity.varName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "add" + tempEntity.name + entity.names, [tempEntity.PKClassName, entity.PKClassName + "[]"], ["SystemException"])>

				/**
				<#list serviceBaseExceptions as exception>
				<#if exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public void add${tempEntity.name}${entity.names}(${tempEntity.PKClassName} ${tempEntity.PKVarName}, ${entity.PKClassName}[] ${entity.PKVarNames}) throws ${stringUtil.merge(serviceBaseExceptions)} {
					${tempEntity.varName}Persistence.add${entity.names}(${tempEntity.PKVarName}, ${entity.PKVarNames});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "add" + tempEntity.name + entity.names, [tempEntity.PKClassName, "java.util.List<" + entity.name + ">"], ["SystemException"])>

				/**
				<#list serviceBaseExceptions as exception>
				<#if exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public void add${tempEntity.name}${entity.names}(${tempEntity.PKClassName} ${tempEntity.PKVarName}, List<${entity.name}> ${entity.names}) throws ${stringUtil.merge(serviceBaseExceptions)} {
					${tempEntity.varName}Persistence.add${entity.names}(${tempEntity.PKVarName}, ${entity.names});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "clear" + tempEntity.name + entity.names, [tempEntity.PKClassName], ["SystemException"])>

				/**
				<#list serviceBaseExceptions as exception>
				<#if exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public void clear${tempEntity.name}${entity.names}(${tempEntity.PKClassName} ${tempEntity.PKVarName}) throws ${stringUtil.merge(serviceBaseExceptions)} {
					${tempEntity.varName}Persistence.clear${entity.names}(${tempEntity.PKVarName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "delete" + tempEntity.name + entity.name, [tempEntity.PKClassName, entity.PKClassName], ["SystemException"])>

				/**
				<#list serviceBaseExceptions as exception>
				<#if exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public void delete${tempEntity.name}${entity.name}(${tempEntity.PKClassName} ${tempEntity.PKVarName}, ${entity.PKClassName} ${entity.PKVarName}) throws ${stringUtil.merge(serviceBaseExceptions)} {
					${tempEntity.varName}Persistence.remove${entity.name}(${tempEntity.PKVarName}, ${entity.PKVarName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "delete" + tempEntity.name + entity.name, [tempEntity.PKClassName, packagePath + ".model." + entity.name], ["SystemException"])>

				/**
				<#list serviceBaseExceptions as exception>
				<#if exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public void delete${tempEntity.name}${entity.name}(${tempEntity.PKClassName} ${tempEntity.PKVarName}, ${entity.name} ${entity.varName}) throws ${stringUtil.merge(serviceBaseExceptions)} {
					${tempEntity.varName}Persistence.remove${entity.name}(${tempEntity.PKVarName}, ${entity.varName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "delete" + tempEntity.name + entity.names, [tempEntity.PKClassName, entity.PKClassName + "[]"], ["SystemException"])>

				/**
				<#list serviceBaseExceptions as exception>
				<#if exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public void delete${tempEntity.name}${entity.names}(${tempEntity.PKClassName} ${tempEntity.PKVarName}, ${entity.PKClassName}[] ${entity.PKVarNames}) throws ${stringUtil.merge(serviceBaseExceptions)} {
					${tempEntity.varName}Persistence.remove${entity.names}(${tempEntity.PKVarName}, ${entity.PKVarNames});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "delete" + tempEntity.name + entity.names, [tempEntity.PKClassName, "java.util.List<" + entity.name + ">"], ["SystemException"])>

				/**
				<#list serviceBaseExceptions as exception>
				<#if exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public void delete${tempEntity.name}${entity.names}(${tempEntity.PKClassName} ${tempEntity.PKVarName}, List<${entity.name}> ${entity.names}) throws ${stringUtil.merge(serviceBaseExceptions)} {
					${tempEntity.varName}Persistence.remove${entity.names}(${tempEntity.PKVarName}, ${entity.names});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "get" + tempEntity.name + entity.names, [tempEntity.PKClassName], ["SystemException"])>

				/**
				<#list serviceBaseExceptions as exception>
				<#if exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public List<${entity.name}> get${tempEntity.name}${entity.names}(${tempEntity.PKClassName} ${tempEntity.PKVarName}) throws ${stringUtil.merge(serviceBaseExceptions)} {
					return ${tempEntity.varName}Persistence.get${entity.names}(${tempEntity.PKVarName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "get" + tempEntity.name + entity.names, [tempEntity.PKClassName, "int", "int"], ["SystemException"])>

				/**
				<#list serviceBaseExceptions as exception>
				<#if exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public List<${entity.name}> get${tempEntity.name}${entity.names}(${tempEntity.PKClassName} ${tempEntity.PKVarName}, int start, int end) throws ${stringUtil.merge(serviceBaseExceptions)} {
					return ${tempEntity.varName}Persistence.get${entity.names}(${tempEntity.PKVarName}, start, end);
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "get" + tempEntity.name + entity.names, [tempEntity.PKClassName, "int", "int", "com.liferay.portal.kernel.util.OrderByComparator"], ["SystemException"])>

				/**
				<#list serviceBaseExceptions as exception>
				<#if exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public List<${entity.name}> get${tempEntity.name}${entity.names}(${tempEntity.PKClassName} ${tempEntity.PKVarName}, int start, int end, OrderByComparator orderByComparator) throws ${stringUtil.merge(serviceBaseExceptions)} {
					return ${tempEntity.varName}Persistence.get${entity.names}(${tempEntity.PKVarName}, start, end, orderByComparator);
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "get" + tempEntity.name + entity.names + "Count", [tempEntity.PKClassName], ["SystemException"])>

				/**
				<#list serviceBaseExceptions as exception>
				<#if exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public int get${tempEntity.name}${entity.names}Count(${tempEntity.PKClassName} ${tempEntity.PKVarName}) throws ${stringUtil.merge(serviceBaseExceptions)} {
					return ${tempEntity.varName}Persistence.get${entity.names}Size(${tempEntity.PKVarName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "has" + tempEntity.name + entity.name, [tempEntity.PKClassName, entity.PKClassName], ["SystemException"])>

				/**
				<#list serviceBaseExceptions as exception>
				<#if exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public boolean has${tempEntity.name}${entity.name}(${tempEntity.PKClassName} ${tempEntity.PKVarName}, ${entity.PKClassName} ${entity.PKVarName}) throws ${stringUtil.merge(serviceBaseExceptions)} {
					return ${tempEntity.varName}Persistence.contains${entity.name}(${tempEntity.PKVarName}, ${entity.PKVarName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "has" + tempEntity.name + entity.names, [tempEntity.PKClassName], ["SystemException"])>

				/**
				<#list serviceBaseExceptions as exception>
				<#if exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public boolean has${tempEntity.name}${entity.names}(${tempEntity.PKClassName} ${tempEntity.PKVarName}) throws ${stringUtil.merge(serviceBaseExceptions)} {
					return ${tempEntity.varName}Persistence.contains${entity.names}(${tempEntity.PKVarName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "set" + tempEntity.name + entity.names, [tempEntity.PKClassName, entity.PKClassName + "[]"], ["SystemException"])>

				/**
				<#list serviceBaseExceptions as exception>
				<#if exception == "SystemException">
				 * @throws SystemException if a system exception occurred
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				public void set${tempEntity.name}${entity.names}(${tempEntity.PKClassName} ${tempEntity.PKVarName}, ${entity.PKClassName}[] ${entity.PKVarNames}) throws ${stringUtil.merge(serviceBaseExceptions)} {
					${tempEntity.varName}Persistence.set${entity.names}(${tempEntity.PKVarName}, ${entity.PKVarNames});
				}
			</#if>
		</#list>
	</#if>

	<#list referenceList as tempEntity>
		<#if tempEntity.hasLocalService()>
			/**
			 * Returns the ${tempEntity.humanName} local service.
			 *
			 * @return the ${tempEntity.humanName} local service
			 */
			public ${tempEntity.name}LocalService get${tempEntity.name}LocalService() {
				return ${tempEntity.varName}LocalService;
			}

			/**
			 * Sets the ${tempEntity.humanName} local service.
			 *
			 * @param ${tempEntity.varName}LocalService the ${tempEntity.humanName} local service
			 */
			public void set${tempEntity.name}LocalService(${tempEntity.name}LocalService ${tempEntity.varName}LocalService) {
				this.${tempEntity.varName}LocalService = ${tempEntity.varName}LocalService;
			}
		</#if>

		<#if tempEntity.hasRemoteService()>
			/**
			 * Returns the ${tempEntity.humanName} remote service.
			 *
			 * @return the ${tempEntity.humanName} remote service
			 */
			public ${tempEntity.name}Service get${tempEntity.name}Service() {
				return ${tempEntity.varName}Service;
			}

			/**
			 * Sets the ${tempEntity.humanName} remote service.
			 *
			 * @param ${tempEntity.varName}Service the ${tempEntity.humanName} remote service
			 */
			public void set${tempEntity.name}Service(${tempEntity.name}Service ${tempEntity.varName}Service) {
				this.${tempEntity.varName}Service = ${tempEntity.varName}Service;
			}
		</#if>

		<#if tempEntity.hasColumns() && (entity.name == "Counter" || tempEntity.name != "Counter")>
			/**
			 * Returns the ${tempEntity.humanName} persistence.
			 *
			 * @return the ${tempEntity.humanName} persistence
			 */
			public ${tempEntity.name}Persistence get${tempEntity.name}Persistence() {
				return ${tempEntity.varName}Persistence;
			}

			/**
			 * Sets the ${tempEntity.humanName} persistence.
			 *
			 * @param ${tempEntity.varName}Persistence the ${tempEntity.humanName} persistence
			 */
			public void set${tempEntity.name}Persistence(${tempEntity.name}Persistence ${tempEntity.varName}Persistence) {
				this.${tempEntity.varName}Persistence = ${tempEntity.varName}Persistence;
			}
		</#if>

		<#if tempEntity.hasFinderClass() && (entity.name == "Counter" || tempEntity.name != "Counter")>
			/**
			 * Returns the ${tempEntity.humanName} finder.
			 *
			 * @return the ${tempEntity.humanName} finder
			 */
			public ${tempEntity.name}Finder get${tempEntity.name}Finder() {
				return ${tempEntity.varName}Finder;
			}

			/**
			 * Sets the ${tempEntity.humanName} finder.
			 *
			 * @param ${tempEntity.varName}Finder the ${tempEntity.humanName} finder
			 */
			public void set${tempEntity.name}Finder(${tempEntity.name}Finder ${tempEntity.varName}Finder) {
				this.${tempEntity.varName}Finder = ${tempEntity.varName}Finder;
			}
		</#if>
	</#list>

	public void afterPropertiesSet() {
		<#if pluginName != "">
			Class<?> clazz = getClass();

			_classLoader = clazz.getClassLoader();
		</#if>

		<#if (sessionTypeName == "Local") && entity.hasColumns()>
			<#if pluginName != "">
				PersistedModelLocalServiceRegistryUtil.register("${packagePath}.model.${entity.name}", ${entity.varName}LocalService);
			<#else>
				persistedModelLocalServiceRegistry.register("${packagePath}.model.${entity.name}", ${entity.varName}LocalService);
			</#if>
		</#if>
	}

	public void destroy() {
		<#if (sessionTypeName == "Local") && entity.hasColumns()>
			<#if pluginName != "">
				PersistedModelLocalServiceRegistryUtil.unregister("${packagePath}.model.${entity.name}");
			<#else>
				persistedModelLocalServiceRegistry.unregister("${packagePath}.model.${entity.name}");
			</#if>
		</#if>
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	<#if pluginName != "">
		public Object invokeMethod(
				String name, String[] parameterTypes, Object[] arguments)
			throws Throwable {

			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader = currentThread.getContextClassLoader();

			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(_classLoader);
			}

			try {
				return _clpInvoker.invokeMethod(name, parameterTypes, arguments);
			}
			finally {
				if (contextClassLoader != _classLoader) {
					currentThread.setContextClassLoader(contextClassLoader);
				}
			}
		}
	</#if>

	<#if entity.hasColumns()>
		protected Class<?> getModelClass() {
			return ${entity.name}.class;
		}

		protected String getModelClassName() {
			return ${entity.name}.class.getName();
		}
	</#if>

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			<#if entity.hasColumns()>
				DataSource dataSource = ${entity.varName}Persistence.getDataSource();
			<#else>
				DataSource dataSource = InfrastructureUtil.getDataSource();
			</#if>

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource, sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	<#list referenceList as tempEntity>
		<#if tempEntity.hasLocalService()>
			@BeanReference(type = ${tempEntity.name}LocalService.class)
			protected ${tempEntity.name}LocalService ${tempEntity.varName}LocalService;
		</#if>

		<#if tempEntity.hasRemoteService()>
			@BeanReference(type = ${tempEntity.name}Service.class)
			protected ${tempEntity.name}Service ${tempEntity.varName}Service;
		</#if>

		<#if tempEntity.hasColumns() && (entity.name == "Counter" || tempEntity.name != "Counter")>
			@BeanReference(type = ${tempEntity.name}Persistence.class)
			protected ${tempEntity.name}Persistence ${tempEntity.varName}Persistence;
		</#if>

		<#if tempEntity.hasFinderClass() && (entity.name == "Counter" || tempEntity.name != "Counter")>
			@BeanReference(type = ${tempEntity.name}Finder.class)
			protected ${tempEntity.name}Finder ${tempEntity.varName}Finder;
		</#if>
	</#list>

	<#if (sessionTypeName == "Local") && entity.hasColumns()>
		<#if pluginName == "">
			@BeanReference(type = PersistedModelLocalServiceRegistry.class)
			protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
		</#if>
	</#if>

	private String _beanIdentifier;

	<#if pluginName != "">
		private ClassLoader _classLoader;
		private ${entity.name}${sessionTypeName}ServiceClpInvoker _clpInvoker = new ${entity.name}${sessionTypeName}ServiceClpInvoker();
	</#if>

}