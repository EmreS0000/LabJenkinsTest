package com.example.lab.labyaz.l.m.repository;

import com.example.lab.labyaz.l.m.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

