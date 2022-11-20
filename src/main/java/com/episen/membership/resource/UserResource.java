package com.episen.membership.resource;

import com.episen.membership.model.User;
import com.episen.membership.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "users", produces = {"application/json"})
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);

    }


    @GetMapping("{username}")
    public ResponseEntity<User> getOne(@PathVariable("username") String username) {

        return new ResponseEntity<>(userService.getByUsername(username), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user) {
        userService.add(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> put(@RequestBody User user) {
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }


    @DeleteMapping("{username}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("username") String username) {
        userService.delete(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}