package com.users.controller;


import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import com.users.model.User;
import com.users.service.UserService;
import com.users.util.CustomErrorType;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
@RequestMapping("/users")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService ps;

    /*@RequestMapping(method = RequestMethod.GET, value = "/")
    public Hashtable<String, User> getAll() {
        return ps.getAll();
    }*/

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Hashtable<String, User>> getAll() {
        Hashtable<String, User> users = ps.getAll();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<Hashtable<String, User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("username") String username) {
        logger.info("Fetching User with username {}", username);
        User user = ps.getUser(username);
        if (user == null) {
            logger.error("User with username {} not found.", username);
            return new ResponseEntity(new CustomErrorType("User with username " + username
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{username}")
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Adding User : {}", user);

        User user1 = ps.getUser(user.getUsername());
        if (user1 != null) {
            logger.error("Unable to create. A User with name {} already exist", user.getUsername());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " +
                    user.getUsername() + " already exist."),HttpStatus.CONFLICT);
        }
        ps.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{username}").buildAndExpand(user.getUsername()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        logger.info("Fetching & Deleting User with username {}", username);

        User user = ps.getUser(username);
        if (user == null) {
            logger.error("Unable to delete. User with username {} not found.", username);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with username " + username + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        ps.deleteUser(username);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

}