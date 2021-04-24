package com.ng0cth1nh.management.service;


import com.ng0cth1nh.management.model.Company;

import java.util.List;

public interface CompanyService {

    Company createCompany(Company company);

    Company findById(Integer id);

    List<Company> getCompanies();

    Company updateCompany(Integer id,String name,Boolean active);

    void deleteCompany(Integer id);
}
