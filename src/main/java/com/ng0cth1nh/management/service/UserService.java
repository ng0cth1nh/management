package com.ng0cth1nh.management.service;

import com.ng0cth1nh.management.model.Company;
import com.ng0cth1nh.management.model.User;
import com.ng0cth1nh.management.repository.CompanyRepository;
import com.ng0cth1nh.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Integer id) {
        return userRepository.findById(id).get();
    }

    public void updateUser(Integer id, String name,
                           String password, Integer companyid,
                           boolean active) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalStateException("user with id " + id + "does not exist!"));

        if (name != null && name.length() > 0 &&
                !Objects.equals(user.getName(), name)) {
            user.setName(name);
        }

        if (password != null && password.length() > 0 &&
                !Objects.equals(user.getPassword(), password)) {
            user.setPassword(password);
        }

        Company company = companyRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalStateException("company with id " + id + "does not exist!"));
        user.setCompanyId(companyid);

        user.setActive(active);
    }
}
