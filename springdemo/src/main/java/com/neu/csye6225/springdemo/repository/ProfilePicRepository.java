package com.neu.csye6225.springdemo.repository;

import com.neu.csye6225.springdemo.model.ProfilePic;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProfilePicRepository extends JpaRepository<ProfilePic, String> {

    @Query(value = "Select * from profile_pic where uuid = ?1", nativeQuery=true)
    ProfilePic findByUser_id(String id);

    @Modifying
    @Transactional
    @Query(value = "delete from profile_pic where uuid = ?1", nativeQuery=true)
    void deleteByUser_id(String id);

}
