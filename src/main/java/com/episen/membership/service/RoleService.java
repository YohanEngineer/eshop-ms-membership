package com.episen.membership.service;

import com.episen.membership.model.Role;

import java.util.List;

public interface RoleService {
    Role add(Role role);

    Role update(Role user, String rolename);

    void delete(String username);

    List<Role> getAll();

}
