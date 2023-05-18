package com.bilel.SpringBoot_TP01.repos;

import com.bilel.SpringBoot_TP01.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

