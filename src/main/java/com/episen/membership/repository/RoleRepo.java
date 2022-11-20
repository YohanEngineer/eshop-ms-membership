package com.episen.membership.repository;

import com.episen.membership.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class RoleRepo {
    private Map<String, Role> roleInMemory = new HashMap<String, Role>();

    public Role add(Role role) {
        roleInMemory.put(role.getName(), role);
        return role;
    }

    public Role findByName(String roleName) {
        return roleInMemory.get(roleName);
    }


    public List<Role> getAll() {
        return new ArrayList<>(roleInMemory.values());
    }

    public Role update(Role roleToUpdate, String rolename) {
        roleInMemory.put(rolename, roleToUpdate);
        return roleToUpdate;
    }

    public void delete(String rolename) {
        roleInMemory.remove(rolename);
    }

}
