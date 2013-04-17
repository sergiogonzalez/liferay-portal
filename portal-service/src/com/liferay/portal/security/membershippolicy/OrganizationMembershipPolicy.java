/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the Organization Membership Policy interface, allowing customization
 * of user membership regarding organizations and organization roles.
 *
 * <p>
 * Organization Membership Policies define the organizations a user is allowed
 * to be a member of, the organizations the user must be a member of, the
 * organization roles the user is allowed to be assigned, and the organization
 * roles the user must be assigned.
 * </p>
 *
 * <p>
 * An implementation may include any number of policies and actions to enforce
 * those policies. The implementation may include policies and actions like the
 * following:
 * </p>
 *
 * <ul>
 * <li>
 * If a user is a member of the organization he will automatically be a member
 * of all its child organizations.
 * </li>
 * <li>
 * Only the members of the parent organization can become a member of this
 * organization.
 * </li>
 * <li>
 * If a user doesn't have the custom attribute A, he cannot be assigned to
 * organization
 * B.
 * </li>
 * <li>
 * If the user is added to organization A, he will automatically be added to
 * organization B.
 * </li>
 * <li>
 * The user must have the Administrator Role in order to be added to
 * organization "Admin organization".
 * </li>
 * <li>
 * All users with the custom attribute A will automatically have the
 * organization role B.
 * </li>
 * <li>
 * All the users with organization role A cannot have organization role B
 * (incompatible roles).
 * </li>
 * </ul>
 *
 * <p>
 * Liferay's core services invoke {@link #checkMembership(long[], long[],
 * long[])} to detect policy violations before adding the users to and removing
 * the users from the organizations. On passing the check, the service proceeds
 * with the changes and propagates appropriate related actions in the portal by
 * invoking {@link #propagateMembership(long[], long[], long[])}. On failing the
 * check, the service foregoes making the changes. For example, Liferay executes
 * this logic when adding and updating organizations, adding and removing users
 * with respect to organizations, and adding and removing organization roles
 * with respect to users.
 * </p>
 *
 * <p>
 * Liferay's UI calls the "is*" methods, such as {@link
 * #isMembershipAllowed(long, long)}, to determine appropriate options to
 * display to the user. For example, the UI calls {@link
 * #isMembershipAllowed(long, long)} to decide whether to display the "Join"
 * link to the user.
 * </p>
 *
 * @author Roberto Díaz
 * @author Sergio González
 */
public interface OrganizationMembershipPolicy {

	/**
	 * Checks if the users can be added to and removed from the respective
	 * organizations.
	 *
	 * <p>
	 * Liferay's core services call this method before adding the users to and
	 * removing the users from the respective organizations. If this method
	 * throws an exception, the service foregoes making the changes.
	 * </p>
	 *
	 * @param  userIds the primary keys of the users to be added and removed
	 *         from the organizations
	 * @param  addOrganizationIds the primary keys of the organizations to which
	 *         the users are to be added (optionally <code>null</code>)
	 * @param  removeOrganizationIds the primary keys of the organizations from
	 *         which the users are to be removed (optionally <code>null</code>)
	 * @throws PortalException if any one user could not be added to a
	 *         organization, if any one user could not be removed from a
	 *         organization, or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void checkMembership(
			long[] userIds, long[] addOrganizationIds,
			long[] removeOrganizationIds)
		throws PortalException, SystemException;

	/**
	 * Checks if the organization roles can be added to or removed from their
	 * users.
	 *
	 * <p>
	 * Liferay's core services call this method before adding the users to and
	 * removing the users from the respective organization roles. If this method
	 * throws an exception, the service foregoes making the changes.
	 * </p>
	 *
	 * @param  addUserGroupRoles the user group roles to be added
	 * @param  removeUserGroupRoles the user group roles to be removed
	 * @throws PortalException if any one user group role violated the policy or
	 *         if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void checkRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if the user can be added to the organization.
	 *
	 * @param  userId the primary key of the user
	 * @param  organizationId the primary key of the organization
	 * @return <code>true</code> if the user can be added to the organization;
	 *         <code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isMembershipAllowed(long userId, long organizationId)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if the policy prevents the user from being
	 * removed from the organization by the user associated with the
	 * permission checker.
	 *
	 * @param  permissionChecker the permission checker referencing a user
	 * @param  userId the primary key of the user to check for protection
	 * @param  organizationId the primary key of the organization
	 * @return <code>true</code> if the policy prevents the user from being
	 *         removed from the organization by the user associated with the
	 *         permission checker; <code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isMembershipProtected(
			PermissionChecker permissionChecker, long userId,
			long organizationId)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if organization membership for the user is
	 * mandatory. Liferay's UI, for example, calls this method in deciding
	 * whether to display the option to leave the organization.
	 *
	 * @param  userId the primary key of the user
	 * @param  organizationId the primary key of the organization
	 * @return <code>true</code> if organization membership for the user is
	 *         mandatory; <code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isMembershipRequired(long userId, long organizationId)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if the role can be added to the user on the
	 * organization. Liferay's UI calls this method.
	 *
	 * @param  userId the primary key of the user
	 * @param  organizationId the primary key of the organization
	 * @param  roleId the primary key of the role
	 * @return <code>true</code> if the role can be added to the user on the
	 *         organization; <code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isRoleAllowed(long userId, long organizationId, long roleId)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if the policy prevents the user from being
	 * removed from the role by the user associated with the permission checker.
	 *
	 * @param  permissionChecker the permission checker referencing a user
	 * @param  userId the primary key of the user to check for protection
	 * @param  organizationId the primary key of the organization
	 * @param  roleId the primary key of the role
	 * @return <code>true</code> if the policy prevents the user from being
	 *         removed from the role by the user associated with the permission
	 *         checker; <code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isRoleProtected(
			PermissionChecker permissionChecker, long userId,
			long organizationId, long roleId)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if the role is mandatory for the user on the
	 * organization. If <code>true</code>, nobody can remove the role from this
	 * user on the organization. Liferay's UI calls this method.
	 *
	 * @param  userId the primary key of the user
	 * @param  organizationId the primary key of the organization
	 * @param  roleId the primary key of the role
	 * @return <code>true</code> if the role is mandatory for the user on the
	 *         organization; <code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isRoleRequired(long userId, long organizationId, long roleId)
		throws PortalException, SystemException;

	/**
	 * Performs membership policy related actions after the users are added to
	 * and removed from the respective organizations. Liferay's core services
	 * call this method after adding and removing the users to and from the
	 * respective organizations.
	 *
	 * <p>
	 * The actions must ensure the integrity of each organization's membership
	 * policy. For example, some actions for implementations to consider
	 * performing are:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * Adding the users to the child organizations of each organization to which
	 * the users
	 * were added.
	 * </li>
	 * <li>
	 * Removing the users from the child organizations of each organization from
	 * which the users
	 * were removed.
	 * </li>
	 * </ul>
	 *
	 * @param  userIds the primary key of the users to be added or removed
	 * @param  addOrganizationIds the primary keys of the organizations to which
	 *         the users were added (optionally <code>null</code>)
	 * @param  removeOrganizationIds the primary keys of the organizations from
	 *         which the users were removed (optionally <code>null</code>)
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void propagateMembership(
			long[] userIds, long[] addOrganizationIds,
			long[] removeOrganizationIds)
		throws PortalException, SystemException;

	/**
	 * Performs membership policy related actions after the respective
	 * organization roles are added to and removed from the affected users.
	 * Liferay's core services call this method after the roles are added to and
	 * removed from the users.
	 *
	 * <p>
	 * The actions must ensure the membership policy of each organization role.
	 * For example, some actions for implementations to consider performing are:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * If the role A is added to a user, role B should be added too.
	 * </li>
	 * <li>
	 * If the role A is removed from a user, role B should be removed too.
	 * </li>
	 * </ul>
	 *
	 * @param  adduserGroupRoles the user group roles added
	 * @param  removeUserGroupRoles the user group roles removed
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void propagateRoles(
			List<UserGroupRole> adduserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException, SystemException;

	/**
	 * Checks the integrity of the membership policy of each of the portal's
	 * organizations and performs operations necessary for the compliance of
	 * each organization and organization role. This method is called when
	 * upgrading Liferay and can also be triggered manually from the Control
	 * Panel.
	 *
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void verifyPolicy() throws PortalException, SystemException;

	/**
	 * Checks the integrity of the membership policy of the organization and
	 * performs operations necessary for the compliance of the organization and
	 * its organization roles.
	 *
	 * @param  organization the organization to verify
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void verifyPolicy(Organization organization)
		throws PortalException, SystemException;

	/**
	 * Checks the integrity of the membership policy of the organization,
	 * with respect to its new attributes, categories, tags and/or expando
	 * attributes, and performs operations necessary for the compliance of the
	 * organization and its organization roles. Liferay calls this method when
	 * adding and updating organizations.
	 *
	 * <p>
	 * The actions must ensure the integrity of the organization's membership
	 * policy based on what has changed in the attributes, categories, tags
	 * and/or expando attributes.
	 * </p>
	 *
	 * <p>
	 * For example, if the membership policy is that organizations with the
	 * "admnistrator" tag should only allow administrators as users, then this
	 * method could enforce that policy using the following logic:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * If the old tags include the "administrator" tag and the new tags include
	 * it too, then no action needs to be performed regarding the
	 * policy. Note, the new tags can be obtained by calling
	 * <code>assetTagLocalService.getTags(Group.class.getName(),
	 * group.getGroupId());</code>.
	 * </li>
	 * <li>
	 * If the old tags include the "administrator" tag and the new tags don't
	 * include it,
	 * then no action needs to be performed regarding the
	 * policy, as non-administrator users need not be removed.
	 * </li>
	 * <li>
	 * However, if the old tags don't include the "administrator" tag, but the
	 * new tags include it, any organization user that does not have the
	 * Administrator role must be removed from the organization.
	 * </li>
	 *
	 * @param  organization the added or updated organization to verify
	 * @param  oldOrganization the old organization
	 * @param  oldAssetCategories the old categories
	 * @param  oldAssetTags the old tags
	 * @param  oldExpandoAttributes the old expando attributes
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void verifyPolicy(
			Organization organization, Organization oldOrganization,
			List<AssetCategory> oldAssetCategories, List<AssetTag> oldAssetTags,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException, SystemException;

	/**
	 * Checks the integrity of the membership policy of the role and performs
	 * operations necessary for the compliance of the organization and its
	 * organization roles.
	 *
	 * @param  role the role to verify
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void verifyPolicy(Role role) throws PortalException, SystemException;

	/**
	 * Checks the integrity of the membership policy of the role, with respect
	 * to its expando attributes, and performs operations necessary for the
	 * compliance of the organization and its organization roles. Liferay calls
	 * this method when adding and updating roles.
	 *
	 * @param  role the added or updated role to verify
	 * @param  oldRole the old role
	 * @param  oldExpandoAttributes the old expando attributes
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void verifyPolicy(
			Role role, Role oldRole,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException, SystemException;

}