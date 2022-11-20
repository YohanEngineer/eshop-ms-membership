package com.episen.membership.repository;

import com.episen.membership.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class UserRepo {
    private Map<String, User> userInMemory = new HashMap<String, User>();

    public User add(User user) {
        userInMemory.put(user.getUsername(), user);
        return user;
    }

    public User getUserByUsername(String username) {
        return userInMemory.get(username);
    }

    public List<User> getAll() {
        return new ArrayList<>(userInMemory.values());
    }

    public User update(User userToUpdate) {
        userInMemory.put(userToUpdate.getUsername(), userToUpdate);
        return userToUpdate;
    }

    public void delete(String username) {
        userInMemory.remove(username);
    }
}
