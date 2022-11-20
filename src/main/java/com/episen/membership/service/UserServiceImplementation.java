package com.episen.membership.service;

import com.episen.membership.model.Role;
import com.episen.membership.model.User;
import com.episen.membership.repository.RoleRepo;
import com.episen.membership.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role));
            });
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }

    @Override
    public User add(User user) {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getEmail())) {
            throw new RuntimeException("User exception");
        }
        if (userRepo.getUserByUsername(user.getUsername()) != null) {
            throw new RuntimeException("User already exist");
        }
        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.add(user);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        User user = userRepo.getUserByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().forEach(r -> {
            if (r.equals(roleName)) {
                throw new RuntimeException("User already have this role!");
            }
        });
        user.getRoles().add(role.getName());
    }

    @Override
    public User getByUsername(String username) {
        User user = userRepo.getUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        log.info("Fetching user {}", username);
        return user;
    }

    @Override
    public List<User> getAll() {
        log.info("Retrieving all users.");
        return userRepo.getAll();
    }

    @Override
    public User update(User user) {
        User userToBeUpdate = userRepo.getUserByUsername(user.getUsername());
        if (userToBeUpdate == null) {
            throw new RuntimeException("User not found");
        }
        log.info("Updating user...");
        String pass = user.getPassword();
        user.setPassword(passwordEncoder.encode(pass));
        return userRepo.update(user);
    }

    @Override
    public void delete(String username) {
        User user = userRepo.getUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        log.info("Deleting user...");
        userRepo.delete(username);
    }
}
