package com.ng0cth1nh.management.service;

import com.ng0cth1nh.management.model.*;

import java.util.List;


public interface UserService {

    User createUser(User user);

    String login(User user);


    User findByUsername(String username);

    User findById(Integer id);

    List<User> getUsers();

    void deleteUser(Integer id);

    User updateUser(Integer id,String username,String name,
                    String password,Integer companyId,Boolean active);
}
