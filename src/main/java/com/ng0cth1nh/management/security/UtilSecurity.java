package com.ng0cth1nh.management.security;

import com.ng0cth1nh.management.repository.UserRepository;
import com.ng0cth1nh.management.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;

@Component("utilSecurity")
public class UtilSecurity {

    @Autowired
    private UserRepository userRepository;

    public boolean hasUserId(Authentication authentication, Integer userId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("userdetails username " + userDetails.getUsername());
        User user  = userRepository.findByUsername(userDetails.getUsername());
        return user.getId() == userId;
    }
}
