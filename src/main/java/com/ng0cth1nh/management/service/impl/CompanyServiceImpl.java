package com.ng0cth1nh.management.service.impl;

import com.ng0cth1nh.management.model.Company;
import com.ng0cth1nh.management.repository.CompanyRepository;
import com.ng0cth1nh.management.service.CompanyService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;


    @Override
    @Transactional
    public Company createCompany(Company company) {
        return companyRepository.saveAndFlush(company);
    }

    @Override
    public Company findById(Integer id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company with ID : " + id + " Not Found!"));
    }

    @Override
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    @Override
    @Transactional
    public Company updateCompany(Integer id, String name, Boolean active) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company with ID : " + id + " Not Found!"));
        if (name != null
                && name.length() > 0
                && !Objects.equals(company.getName(), name)) {
            company.setName(name);
        }

        if (active != null) {
            company.setActive(active);
        }

        return companyRepository.saveAndFlush(company);
    }

    @Override
    @Transactional
    public void deleteCompany(Integer id) {
        companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company with ID : " + id + " Not Found!"));
        companyRepository.deleteById(id);
    }
}
