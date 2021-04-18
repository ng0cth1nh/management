package com.ng0cth1nh.management.controller;


import com.ng0cth1nh.management.model.Company;
import com.ng0cth1nh.management.model.User;
import com.ng0cth1nh.management.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/")
public class AppController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;


    @GetMapping("company")
    public List<Company> getAllCompanies() {
        return companyService.getCompanies();
    }


    @GetMapping("user")
    public User getUser(@RequestParam(name = "id", required = true) Integer id) {
        return userService.getUser(id);
    }

    @PutMapping("user")
    public void updateUser(@RequestParam(name = "id", required = true) Integer id,
                           @RequestParam(name = "name", required = false) String name,
                           @RequestParam(name = "password", required = false) String password,
                           @RequestParam(name = "company-id", required = false) Integer companyId,
                           @RequestParam(name = "is-active", required = false) boolean active) {
        userService.updateUser(id,name,password,companyId,active);
    }

    @GetMapping("users")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }
}
