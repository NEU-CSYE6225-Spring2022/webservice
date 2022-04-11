package com.neu.csye6225.springdemo.repository;

import com.neu.csye6225.springdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findByUsername(String username);

    User findByUsernameAndAccountVerified(String username, boolean verified);
}
