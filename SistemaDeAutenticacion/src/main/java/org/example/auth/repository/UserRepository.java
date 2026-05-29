package org.example.auth.repository;

import org.example.auth.model.User;

import java.util.*;

public class UserRepository {

    private Map<String, User> users = new HashMap<>();

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public void save(User user) {
        users.put(user.getUsername(), user);
    }


}
