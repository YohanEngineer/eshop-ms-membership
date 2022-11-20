package com.episen.membership;

import com.episen.membership.model.Role;
import com.episen.membership.model.User;
import com.episen.membership.service.RoleService;
import com.episen.membership.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Slf4j
@SpringBootApplication
public class MembershipApplication {


    public static void main(String[] args) {
        SpringApplication.run(MembershipApplication.class, args);
        log.info("Membership API started...");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService, RoleService roleService) {
        return args -> {
            roleService.add(new Role("GUEST"));
            roleService.add(new Role("VIP"));
            roleService.add(new Role("ADMIN"));
            roleService.add(new Role("MANAGER"));
            roleService.add(new Role("CLIENT"));
            userService.add(new User("Yohan Bouali", "Yohan", "yohan.bouali@gmail.com", "MonSuperPassword", List.of("ADMIN")));
        };
    }

}
