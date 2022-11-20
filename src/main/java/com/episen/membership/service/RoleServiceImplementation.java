package com.episen.membership.service;

import com.episen.membership.model.Role;
import com.episen.membership.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImplementation implements RoleService {

    private final RoleRepo roleRepo;

    @Override
    public Role add(Role role) {
        if (StringUtils.isEmpty(role.getName())) {
            throw new RuntimeException("Role exception");
        }
        if (roleRepo.findByName(role.getName()) != null) {
            throw new RuntimeException("Role already exist");
        }
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.add(role);
    }

    @Override
    public List<Role> getAll() {
        log.info("Retrieving all roles.");
        return roleRepo.getAll();
    }

    @Override
    public Role update(Role role, String rolename) {
        Role roleToBeUpdate = roleRepo.findByName(rolename);
        if (roleToBeUpdate == null) {
            throw new RuntimeException("Role not found");
        }
        log.info("Updating role...");
        return roleRepo.update(role, rolename);
    }

    @Override
    public void delete(String rolename) {
        log.info("ROLENAME : " + rolename);
        Role role = roleRepo.findByName(rolename);
        if (role == null) {
            throw new RuntimeException("Role not found");
        }
        log.info("Deleting role...");
        roleRepo.delete(rolename);
    }

}
