package com.ng0cth1nh.management.service;

import com.ng0cth1nh.management.model.Company;
import com.ng0cth1nh.management.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getCompanies(){
        return companyRepository.findAll();
    }
}
