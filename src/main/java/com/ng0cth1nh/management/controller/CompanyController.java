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


@RestController
@RequestMapping(path = "/api/v1/")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("companies/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable Integer id) {

        return new ResponseEntity<Company>(companyService.findById(id), HttpStatus.OK);
    }

    @GetMapping("companies")
    public ResponseEntity<Object> getCompanies() {
            return new ResponseEntity<Object>(companyService.getCompanies(), HttpStatus.OK);
    }

    @PutMapping("companies/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') and @utilSecurity.isUserInCompany(authentication,#id)")
    public ResponseEntity<Company> updateUser(@PathVariable Integer id,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) Boolean active
            , Authentication authentication) {

        return new ResponseEntity<Company>(companyService.updateCompany(id,name,active), HttpStatus.OK);
    }
}
