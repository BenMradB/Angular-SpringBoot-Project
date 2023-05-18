package com.bilel.SpringBoot_TP01.services;

import com.bilel.SpringBoot_TP01.entities.Role;
import com.bilel.SpringBoot_TP01.entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User updateUser(User user);
    User findByUsername(String username);
    User findById(Long userId);
    User grantRoleToUser(User user, Role role);
    void revokeRoleFromUser(User user, Role role);
    List<User> findAllUsers();

    void deleteUser(Long userId);
}
