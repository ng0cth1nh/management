package com.ng0cth1nh.management.service;

import com.ng0cth1nh.management.model.*;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);

    User findByUsername(String username);

    Optional<User> findById(Integer id);

    List<User> getUsers();

    User updateUser(Integer id,String username,String name,
                    String password,Integer companyId,Boolean active);
}
