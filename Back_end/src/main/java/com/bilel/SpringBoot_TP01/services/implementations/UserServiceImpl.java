package com.bilel.SpringBoot_TP01.services.implementations;

import com.bilel.SpringBoot_TP01.entities.Role;
import com.bilel.SpringBoot_TP01.entities.User;
import com.bilel.SpringBoot_TP01.repos.RoleRepo;
import com.bilel.SpringBoot_TP01.repos.UserRepo;
import com.bilel.SpringBoot_TP01.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public User saveUser(User user) {
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        return this.userRepo.save(user);
    }

    @Override
    public User updateUser(User user) {
        return this.userRepo.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepo.findByUsername(username);
    }

    @Override
    public User findById(Long userId) {
        return this.userRepo.findById(userId).get();
    }

    @Override
    public User grantRoleToUser(User user, Role role) {
        user.getRoles().add(role);
        return user;
    }

    @Override
    public void revokeRoleFromUser(User user, Role role) {
        user.getRoles().remove(role);
    }

    @Override
    public List<User> findAllUsers() {
        return this.userRepo.findAll();
    }

    @Override
    public void deleteUser(Long userId) {
        User user = this.userRepo.findById(userId).get();
        CopyOnWriteArrayList<Role> roles = new CopyOnWriteArrayList<>(user.getRoles());

        for (Role role : roles) {
            revokeRoleFromUser(user, role);
        }
        this.userRepo.deleteById(userId);
    }
}

