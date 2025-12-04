package com.example.lab.labyaz.l.m.config;

import com.example.lab.labyaz.l.m.model.Role;
import com.example.lab.labyaz.l.m.model.User;
import com.example.lab.labyaz.l.m.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        String adminUsername = "admin";
        if (userService.findByUsername(adminUsername).isEmpty()) {
            User u = new User();
            u.setUsername(adminUsername);
            u.setPassword("admin"); // default password, will be encoded by UserService
            u.setRole(Role.ROLE_ADMIN);
            u.setFullName("Default Admin");
            userService.create(u);
            System.out.println("Default admin user created: username=admin password=admin");
        }
    }
}

