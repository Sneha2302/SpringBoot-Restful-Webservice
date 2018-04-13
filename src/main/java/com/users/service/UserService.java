package com.users.service;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.users.model.User;

@Service
public class UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserService.class);
    Hashtable<String, User> users = new Hashtable<String, User>();
    public UserService() {

        User p = new User("jsmith","John Smith","Sales");
        users.put("jsmith", p);

        p = new User("jdoe","John Doe","Development");
        users.put("jdoe", p);
    }
    public User getUser(String username) {
        if (users.containsKey(username))
            return users.get(username);
        else
            return null;
    }
    public Hashtable<String, User> getAll() {
        return users;
    }

    public void saveUser(User user) {
        users.put(user.getUsername(), user);
        logger.info("User {} added", user.getUsername());
    }


    public void deleteUser(String username) {
        logger.info("Deleting User with username {}", username);
        Set<String> keys = users.keySet();

        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ) {
            String str = iterator.next();
            if (str.equals(username)) {
                logger.info("User {} deleted", username);
                iterator.remove();
            }
        }
    }
}