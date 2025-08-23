package com.flipfit.bean;

/**
 * @author Flipfit Team
 * @description Represents a role in the Flipfit system, such as Admin, Customer, or Owner.
 */
public class FlipfitRole {
    public int RoleId;
    public String RoleName;

    /**
     * @method getRoleId
     * @description Gets the role ID.
     * @return The unique role ID.
     */
    public int getRoleId() {
        return RoleId;
    }

    /**
     * @method setRoleId
     * @parameter roleId The unique role ID to set.
     * @description Sets the role ID.
     */
    public void setRoleId(int roleId) {
        RoleId = roleId;
    }

    /**
     * @method getRoleName
     * @description Gets the role name.
     * @return The name of the role.
     */
    public String getRoleName() {
        return RoleName;
    }

    /**
     * @method setRoleName
     * @parameter roleName The name of the role to set.
     * @description Sets the role name.
     */
    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

}