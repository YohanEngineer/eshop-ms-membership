package com.episen.membership.resource;

import com.episen.membership.model.Role;
import com.episen.membership.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "roles", produces = {"application/json"})
@RequiredArgsConstructor
public class RoleResource {

    private final RoleService roleService;

    @PostMapping()
    public ResponseEntity<Role> add(@RequestBody Role role) {
        return new ResponseEntity<>(roleService.add(role), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Role>> getAll() {
        return new ResponseEntity<>(roleService.getAll(), HttpStatus.OK);

    }

    @PutMapping("{rolename}")
    public ResponseEntity<Role> put(@RequestBody Role role, @PathVariable("rolename") String rolename) {
        return new ResponseEntity<>(roleService.update(role, rolename), HttpStatus.OK);
    }


    @DeleteMapping("{rolename}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("rolename") String rolename) {
        roleService.delete(rolename);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
