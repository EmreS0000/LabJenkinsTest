package com.example.lab.labyaz.l.m.service;

import com.example.lab.labyaz.l.m.model.User;
import com.example.lab.labyaz.l.m.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(User u) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return userRepository.save(u);
    }

    public Optional<User> findByUsername(String username) { return userRepository.findByUsername(username); }

}

