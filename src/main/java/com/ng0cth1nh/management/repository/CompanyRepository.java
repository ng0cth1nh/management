package com.ng0cth1nh.management.repository;

import com.ng0cth1nh.management.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {
}
