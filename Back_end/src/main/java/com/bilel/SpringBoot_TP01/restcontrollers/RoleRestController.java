package com.bilel.SpringBoot_TP01.restcontrollers;

import com.bilel.SpringBoot_TP01.entities.Role;
import com.bilel.SpringBoot_TP01.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin
public class RoleRestController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public List<Role> getRoles() {
        return this.roleService.getRoles();
    }

    @GetMapping("/get/{roleId}")
    public Role getRoles(@PathVariable("roleId") Long roleId) {
        return this.roleService.findRoleById(roleId);
    }

    @PostMapping("/create")
    public Role addRole(@RequestBody Role role) {
        return this.roleService.addRole(role);
    }

    @PatchMapping("/update/{roleId}")
    public Role updateRole(@RequestBody Role role, @PathVariable("roleId") Long roleId) {
        role.setRole_id(roleId);
        return this.roleService.updateRole(role);
    }

    @DeleteMapping("/delete/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId) {
        this.roleService.deleteRole(roleId);
    }
}
