package com.ng0cth1nh.management.repository;
import com.ng0cth1nh.management.model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
  //  @Query("SELECT u.username,u.password From User u where u.username = :username")
    User findByUsername(String username);
}
