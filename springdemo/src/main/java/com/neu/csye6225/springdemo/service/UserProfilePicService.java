package com.neu.csye6225.springdemo.service;

import com.neu.csye6225.springdemo.model.ProfilePic;

import java.io.InputStream;

public interface UserProfilePicService {

    ProfilePic getUserProfilePic();

    void deleteProfilePic();

    ProfilePic uploadUserProfilePic(InputStream inputStream, String fileName);
}
