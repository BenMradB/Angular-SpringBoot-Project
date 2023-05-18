package com.bilel.SpringBoot_TP01.services.implementations;

import com.bilel.SpringBoot_TP01.entities.Role;
import com.bilel.SpringBoot_TP01.repos.RoleRepo;
import com.bilel.SpringBoot_TP01.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public Role addRole(Role role) {
        role.setRole(role.getRole().toUpperCase());
        return this.roleRepo.save(role);
    }

    @Override
    public Role updateRole(Role role) {
        return this.roleRepo.save(role);
    }

    @Override
    public Role findRoleById(Long roleId) {
        return this.roleRepo.findById(roleId).get();
    }

    @Override
    public List<Role> getRoles() {
        return this.roleRepo.findAll();
    }

    @Override
    public void deleteRole(Long roleId) {
        this.roleRepo.deleteById(roleId);
    }
}
