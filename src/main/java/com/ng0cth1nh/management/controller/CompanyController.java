package com.ng0cth1nh.management.controller;


import java.util.List;

import com.ng0cth1nh.management.exceptionHandling.RecordNotFoundException;
import com.ng0cth1nh.management.model.Company;
import com.ng0cth1nh.management.model.User;
import com.ng0cth1nh.management.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;


@RestController
@RequestMapping(path = "/api/v1/")
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not found")
    })

    
    @PostMapping("companies")
    @ApiOperation(value = "create new company",response = Company.class)
    public ResponseEntity<?> createCompany(@RequestBody Company company) {
        return new ResponseEntity<>(companyService.createCompany(company), HttpStatus.OK);
    }

    @GetMapping("companies/{id}")
    @ApiOperation(value = "return company with corresponding ID",
                    response = Company.class,
                    authorizations = { @Authorization(value="JWT") })
    public ResponseEntity<Company> getCompany(@PathVariable Integer id) {
        return new ResponseEntity<Company>(companyService.findById(id), HttpStatus.OK);
    }

    @GetMapping("companies")
    @ApiOperation(value = "return list of companies", 
                    response = List.class, 
                    authorizations = {@Authorization(value="JWT")})
    public ResponseEntity<Object> getCompanies() {
            return new ResponseEntity<Object>(companyService.getCompanies(), HttpStatus.OK);
    }

    @PutMapping("companies/{id}")
    @ApiOperation(value = "edit company with corresponding ID",
                    response = Company.class,
                    authorizations = { @Authorization(value="JWT") })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') and @utilSecurity.isUserInCompany(authentication,#id)")
    public ResponseEntity<Company> updateCompany(@PathVariable Integer id,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) Boolean active
            , Authentication authentication) {
        return new ResponseEntity<Company>(companyService.updateCompany(id,name,active), HttpStatus.OK);
    }


    @DeleteMapping("companies/{id}")
    @ApiOperation(value = "delete company with corresponding ID",
                    response = String.class,
                    authorizations = { @Authorization(value="JWT")})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') and @utilSecurity.isUserInCompany(authentication,#id)")
    public ResponseEntity<String> deleteCompany(@PathVariable Integer id, Authentication authentication) {
        companyService.deleteCompany(id);
        return new ResponseEntity<String>("Deleted company with Id: " + id + " and all users of this company.", HttpStatus.OK );
    }

}
