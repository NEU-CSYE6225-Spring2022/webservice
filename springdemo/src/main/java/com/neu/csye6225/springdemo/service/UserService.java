package com.neu.csye6225.springdemo.service;

import com.neu.csye6225.springdemo.model.User;
import com.neu.csye6225.springdemo.request.UserRequest;

public interface UserService {

    User getUserInfo();

    User createUser(User user);

    User updateUser(UserRequest user);
}
