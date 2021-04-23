package com.ng0cth1nh.management.service.impl;

import com.ng0cth1nh.management.model.User;
import com.ng0cth1nh.management.repository.UserRepository;
import com.ng0cth1nh.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Integer id) {
         userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User updateUser(Integer id,String username,String name,
                           String password,Integer companyId,Boolean active) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "User with id " + id + " does not exists"));

        if (username != null
                && username.length() > 0
                && !Objects.equals(user.getUsername(), username)){
            User userOptional = userRepository
                    .findByUsername(username);
            if(userOptional != null){
                throw new IllegalStateException("Username has taken!");
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
            if(!newPass.equals(user.getPassword())){
                user.setPassword(newPass);
            }
        }

        if(companyId != null){
            user.setCompanyId(companyId);
        }
        if(active != null){
            user.setActive(active);
        }

       return userRepository.saveAndFlush(user);

    }
}
