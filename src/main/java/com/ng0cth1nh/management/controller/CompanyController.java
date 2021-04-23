package com.ng0cth1nh.management.controller;


import com.ng0cth1nh.management.exceptionHandling.RecordNotFoundException;
import com.ng0cth1nh.management.model.Company;
import com.ng0cth1nh.management.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("company/{id}")
    public ResponseEntity<Object> getCompany(@PathVariable Integer id) {
        Company company = null;
        try {
            company = companyService.findById(id).get();
        } catch (Exception e) {
            throw new RecordNotFoundException("Invalid company id : " + id);
        }
        return new ResponseEntity<Object>(company, HttpStatus.OK);
    }

    @GetMapping("companies")
    public ResponseEntity<Object> getCompanies() {
        List<Company> companies = companyService.getCompanies();
        if (companies != null) {
            return new ResponseEntity<Object>(companies, HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Not Found Data", HttpStatus.NOT_FOUND);
    }

    @PutMapping("company/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') and @utilSecurity.isUserInCompany(authentication,#id)")
    public ResponseEntity<Object> updateUser(@PathVariable Integer id,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) Boolean active
            , Authentication authentication) {
        Company company = null;
        try {
            company = companyService.updateCompany(id,name,active);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(company, HttpStatus.OK);
    }
}
