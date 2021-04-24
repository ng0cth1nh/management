package com.ng0cth1nh.management.controller;

import com.ng0cth1nh.management.model.*;
import com.ng0cth1nh.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("users")
    public ResponseEntity<Object> getUsers() {
        return new ResponseEntity<Object>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PutMapping("users/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or @utilSecurity.hasUserId(authentication,#id)")
    public ResponseEntity<User> updateUser(@PathVariable Integer id,
                                             @RequestParam(required = false) String password,
                                             @RequestParam(required = false) String username,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) Integer companyId,
                                             @RequestParam(required = false) Boolean active
            , Authentication authentication) {
        return new ResponseEntity<User>(userService.updateUser(id, username,
                name, password, companyId, active), HttpStatus.OK);
    }


    @DeleteMapping("users/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or @utilSecurity.hasUserId(authentication,#id)")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id, Authentication authentication) {
        userService.deleteUser(id);
        return new ResponseEntity<String>("Deleted user with Id: " + id, HttpStatus.OK);
    }

    @PostMapping("users")
    public ResponseEntity<?> register(@RequestBody User user) {

        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }


    @PostMapping("users/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        String result = userService.login(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
