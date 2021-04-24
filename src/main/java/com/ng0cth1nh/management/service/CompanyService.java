package com.ng0cth1nh.management.service;


import com.ng0cth1nh.management.model.Company;

import java.util.List;

public interface CompanyService {

    Company findById(Integer id);

    List<Company> getCompanies();

    Company updateCompany(Integer id,String name,Boolean active);
}
