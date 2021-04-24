package com.ng0cth1nh.management.service.impl;

import com.ng0cth1nh.management.model.Role;
import com.ng0cth1nh.management.model.User;
import com.ng0cth1nh.management.repository.UserRepository;
import com.ng0cth1nh.management.security.JwtUtil;
import com.ng0cth1nh.management.service.UserService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    @Transactional
    public User createUser(User user) {


        User u = userRepository.findUserByUsername(user.getUsername()).orElse(null);
        if (u != null) {
            throw new IllegalStateException("Username: " + user.getUsername() + " has been taken!");
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setActive(true);
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2));
        user.setRoles(roles);

        return userRepository.saveAndFlush(user);
    }


    @Override
    public String login(User user) {
        User u = userRepository.findUserByUsername(user.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Username or password is incorrect!"));
        if (!new BCryptPasswordEncoder().matches(user.getPassword(),
                u.getPassword())) {
            throw new ResourceNotFoundException("Username or password is incorrect!");
        }
        return jwtUtil.generateToken(user.getUsername());
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username + " NOT Found!"));
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID : " + id + " Not Found!"));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID : " + id + " Not Found!"));
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User updateUser(Integer id, String username, String name,
                           String password, Integer companyId, Boolean active) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("User with ID: " + id + " Not Found!"));

        if (username != null
                && username.length() > 0
                && !Objects.equals(user.getUsername(), username)) {
            User userOptional = userRepository
                    .findUserByUsername(username).orElse(null);
            if (userOptional != null) {
                throw new IllegalStateException("Username:" + username + " has been taken!");
            }
        }

        if (name != null
                && name.length() > 0
                && !Objects.equals(user.getName(), name)) {
            user.setName(name);
        }

        if (password != null
                && password.length() > 0) {
            String newPass = new BCryptPasswordEncoder().encode(password);
            if (!newPass.equals(user.getPassword())) {
                user.setPassword(newPass);
            }
        }

        if (companyId != null) {
            user.setCompanyId(companyId);
        }
        if (active != null) {
            user.setActive(active);
        }

        return userRepository.saveAndFlush(user);

    }
}
