package com.bilel.SpringBoot_TP01.services;

import com.bilel.SpringBoot_TP01.entities.Role;

import java.util.List;

public interface RoleService {
    Role addRole(Role role);
    Role updateRole(Role role);

    Role findRoleById(Long roleId);
    List<Role> getRoles();
    void deleteRole(Long roleId);
}
