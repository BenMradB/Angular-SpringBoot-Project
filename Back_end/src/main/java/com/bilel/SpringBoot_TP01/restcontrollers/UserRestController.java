package com.bilel.SpringBoot_TP01.restcontrollers;

import com.bilel.SpringBoot_TP01.entities.Role;
import com.bilel.SpringBoot_TP01.entities.User;
import com.bilel.SpringBoot_TP01.services.RoleService;
import com.bilel.SpringBoot_TP01.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public List<User> getUsers() {
        return this.userService.findAllUsers();
    }

    @GetMapping("/get/{userId}")
    public User getUser(@PathVariable("userId") Long userId) {
        return this.userService.findById(userId);
    }

    @PostMapping("/create")
    public User addUser(@RequestBody User user) {
        System.out.println(user);
        return this.userService.saveUser(user);
    }

    @PatchMapping("/update/{userId}")
    public User updateUser(@RequestBody User user, @PathVariable("userId") Long userId) {
        user.setUser_id(userId);
        return this.userService.updateUser(user);
    }

    @PostMapping("/grant/role/{roleId}/user/{userId}")
    public User grantRoleToUser(@PathVariable("roleId") Long roleId, @PathVariable("userId") Long userId) {
        User user = this.userService.findById(userId);
        Role role = this.roleService.findRoleById(roleId);
        return this.userService.grantRoleToUser(user, role);
    }

    @PostMapping("/revoke/role/{roleId}/user/{userId}")
    public void revokeRoleFromUser(@PathVariable("roleId") Long roleId, @PathVariable("userId") Long userId) {
        User user = this.userService.findById(userId);
        Role role = this.roleService.findRoleById(roleId);
        this.userService.revokeRoleFromUser(user, role);
    }

    @DeleteMapping("delete/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        this.userService.deleteUser(userId);
    }
}

