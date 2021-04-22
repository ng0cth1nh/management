package com.ng0cth1nh.management.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ng0cth1nh.management.exceptionHandling.RecordNotFoundException;
import com.ng0cth1nh.management.security.JwtUtil;
import com.ng0cth1nh.management.model.*;
import com.ng0cth1nh.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/")
public class AppController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("user/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String result = "";
        HttpStatus httpStatus = null;
        try {
            User userPrincipal = userService.findByUsername(user.getUsername());
            if (null == user
                    || userPrincipal == null
                    || !new BCryptPasswordEncoder().matches(user.getPassword(),
                    userPrincipal.getPassword())
                    ) {
                result = "Wrong userId and password";
                httpStatus = HttpStatus.BAD_REQUEST;
            }else{
                result = jwtUtil.generateToken(user.getUsername());
                httpStatus = HttpStatus.OK;
            }
        } catch (Exception ex) {
            result = "Server Error";
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping("user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getUser(@PathVariable Integer id){
        User user = null;
        try{
            user =  userService.findById(id).get();
        }catch (Exception e){
            throw new RecordNotFoundException("Invalid User id : " + id);
        }
        return new ResponseEntity<Object>(user,HttpStatus.OK);
    }


    @GetMapping("users")
    @PreAuthorize("hasAnyAuthority('USER_READ')")
    public ResponseEntity<Object> getUsers(){
        List<User> users =  userService.getUsers();
        if(users != null){
            return new ResponseEntity<Object>(users,HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Not Found User",HttpStatus.NOT_FOUND);
    }

}
