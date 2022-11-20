package com.episen.membership.service;

import com.episen.membership.model.User;

import java.util.List;

public interface UserService {
    User add(User user);

    void addRoleToUser(String username, String roleName);

    User getByUsername(String username);

    List<User> getAll();

    User update(User user);

    void delete(String username);
}
